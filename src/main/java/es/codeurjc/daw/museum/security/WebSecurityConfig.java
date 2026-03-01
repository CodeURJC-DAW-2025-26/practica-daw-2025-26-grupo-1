package es.codeurjc.daw.museum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
                .authorizeHttpRequests(authorize -> authorize
                        // Public pages
                        .requestMatchers("/sections", "/sections/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/assets/**").permitAll() 
                        .requestMatchers("/favicon.ico").permitAll()
                        .requestMatchers("/", "/login", "/register", "/loginerror", "/welcome-anonymous", "/section/peces", "/section/insectos", "/section/fosiles", "/section/arte").permitAll()

                        // Registered user (USER)
                        .requestMatchers("/welcome-registered").hasRole("USER")
                        .requestMatchers("/objects/*/favorite").hasRole("USER")
                        .requestMatchers("/objects/*/seen").hasRole("USER")
                        .requestMatchers("/notes/**").hasRole("USER")
                        .requestMatchers("/profile/**").hasRole("USER")

                        // Admin (ADMIN)
                        .requestMatchers("/objects/new", "/objects/edit/**").hasRole("ADMIN")
                        .requestMatchers("/objects/delete/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN"))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/loginerror")
                        /* .defaultSuccessUrl("/") */
                        .successHandler((request, response, authentication) -> {
        var authorities = authentication.getAuthorities();

        if (authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            response.sendRedirect("/welcome-admin");
        } else {
            response.sendRedirect("/welcome-registered");
        }
    })
    .permitAll())
            
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll());

        return http.build();
    }

}
