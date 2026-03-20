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
                        // 1. PÁGINAS PÚBLICAS (Accesibles para TODO el mundo, incluso tras logout)
                        .requestMatchers("/section", "/section/**", "/images/**", "/assets/**", "/favicon.ico")
                        .permitAll()
                        .requestMatchers("/search")
                        .permitAll()
                        .requestMatchers("/section/*/more/*").permitAll()  
                        .requestMatchers("/", "/error", "/login", "/register", "/loginerror", "/confirmation", "/system-error", "/search")
                        .permitAll()
                        .requestMatchers("/section/peces", "/section/insectos", "/section/fosiles", "/section/arte", "/welcome-user")
                        .permitAll()
                        .requestMatchers("/object/*")
                        .permitAll()

                        // 2. PÁGINAS PARA LOGUEADOS (USER o ADMIN)
                        .requestMatchers( "/statistics").hasAnyRole("USER", "ADMIN")

                        // 3. SOLO PARA USUARIOS (USER)
                        //.requestMatchers("/objects/*/favorite", "/objects/*/seen").hasRole("USER")
                        .requestMatchers("/notes/**", "/profile/**", "/edit-profile").hasRole("USER")

                        // 4. SOLO PARA ADMINISTRADORES (ADMIN)
                        .requestMatchers("/objects/new", "/objects/edit/**", "/objects/delete/**", "/admin/**")
                        .hasRole("ADMIN")

                        // Cualquier otra ruta no especificada requiere autenticación
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/loginerror")
                        .successHandler((request, response, authentication) -> {
                            // Simplificado: Ambos van al mismo sitio
                            response.sendRedirect("/welcome-user");
                        })
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/confirmation?action=logout")
                        .invalidateHttpSession(true) // Borra la sesión
                        .deleteCookies("JSESSIONID") // Borra la cookie
                        .permitAll());

        return http.build();
    }

}         



