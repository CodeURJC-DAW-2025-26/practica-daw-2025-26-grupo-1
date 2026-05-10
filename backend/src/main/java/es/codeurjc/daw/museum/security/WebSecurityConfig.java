package es.codeurjc.daw.museum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import es.codeurjc.daw.museum.security.jwt.JwtRequestFilter;
import es.codeurjc.daw.museum.security.jwt.JwtTokenProvider;
import es.codeurjc.daw.museum.security.jwt.UnauthorizedHandlerJwt;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public RepositoryUserDetailsService userDetailService;

    @Autowired
    private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    // --- API INTERFACE ---
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
                .securityMatcher("/api/**")
                .exceptionHandling(handling -> {
                    handling.authenticationEntryPoint(unauthorizedHandlerJwt);
                    handling.accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(403);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(
                                "{\"errorCode\":403,\"errorMsg\":\"No tienes el permiso necesario para acceder a este recurso.\"}");
                    });
                });

        http
            .authorizeHttpRequests(authorize -> authorize
                // Profile and Users
                .requestMatchers("/api/v1/users/me/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/users/").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()

                // Objects and Museum (Public)
                .requestMatchers(HttpMethod.GET, "/api/v1/objects/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/menu-page/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/statistics/").permitAll()

                // Objects management (Only ADMIN)
                .requestMatchers(HttpMethod.POST, "/api/v1/objects/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/objects/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/objects/**").hasRole("ADMIN")

                // Notes (Only USER)
                .requestMatchers(HttpMethod.POST, "/api/v1/notes/**").hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/notes/**").hasRole("USER")

                
                .anyRequest().permitAll()
            );

        // API-specific settings 
        http.formLogin(form -> form.disable());
        http.csrf(csrf -> csrf.disable());
        http.httpBasic(httpBasic -> httpBasic.disable());
        
        // Stateless session (JWT)
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // JWT filter before the username/password filter
        http.addFilterBefore(new JwtRequestFilter(userDetailService, jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // --- WEB INTERFACE ---
    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
            .authorizeHttpRequests(authorize -> authorize
                // Static resources and public pages
                .requestMatchers("/", "/error", "/login", "/register", "/loginerror", "/confirmation").permitAll()
                .requestMatchers("/section/**", "/object/**", "/images/**", "/assets/**", "/search").permitAll()
                .requestMatchers("/welcome-user").permitAll()

                // Private pages
                .requestMatchers("/statistics").hasRole("USER")
                .requestMatchers("/notes/**", "/profile/**", "/edit-profile").hasRole("USER")
                .requestMatchers("/objects/new", "/objects/edit/**", "/objects/delete/**", "/admin/**").hasRole("ADMIN")

                // OpenAPI / Swagger
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .failureUrl("/loginerror")
                .successHandler((req, res, auth) -> res.sendRedirect("/welcome-user"))
                .permitAll()
            )
            .logout(out -> out
                .logoutUrl("/logout")
                .logoutSuccessUrl("/confirmation?action=logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
            

        return http.build();
    }
}