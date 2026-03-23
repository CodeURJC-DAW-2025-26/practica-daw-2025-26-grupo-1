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

	@Bean
	@Order(1)
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider());

		http
				.securityMatcher("/api/**")
				.exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

		http
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/api/v1/users/me/**").hasAnyRole("USER", "ADMIN")

						// --- 2. OBJETOS DEL MUSEO ---
						// Cualquier usuario puede ver los objetos y las secciones
						.requestMatchers(HttpMethod.GET, "/api/v1/objects/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/menu-page/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/statistics/").permitAll()

						// Crear o editar objetos: Solo ADMIN
						.requestMatchers(HttpMethod.POST, "/api/v1/objects/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/v1/objects/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/objects/**").hasRole("ADMIN")

						// --- 3. NOTAS ---
						// Solo usuarios registrados pueden dejar notas o borrarlas
						.requestMatchers(HttpMethod.POST, "/api/v1/notes/**").hasRole("USER")
						.requestMatchers(HttpMethod.DELETE, "/api/v1/notes/**").hasRole("USER")

						// --- 4. REGISTRO Y LOGIN (PÚBLICO) ---
						// El POST a /users/ es para registrarse, tiene que ser público
						.requestMatchers(HttpMethod.POST, "/api/v1/users/").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()

						// Todo lo demás de la API que no hayamos dicho, por seguridad, que pida login
						.anyRequest().authenticated());

		// Disable Form login Authentication
		http.formLogin(formLogin -> formLogin.disable());

		// Disable CSRF protection (it is difficult to implement in REST APIs)
		http.csrf(csrf -> csrf.disable());

		// Disable Basic Authentication
		http.httpBasic(httpBasic -> httpBasic.disable());

		// Stateless session
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// Add JWT Token filter
		http.addFilterBefore(new JwtRequestFilter(userDetailService, jwtTokenProvider),
				UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider());

		http
				.authorizeHttpRequests(authorize -> authorize
						// PUBLIC PAGES
						.requestMatchers("/section", "/section/**", "/images/**", "/assets/**", "/favicon.ico")
						.permitAll()
						.requestMatchers("/search").permitAll()
						.requestMatchers("/section/*/more/*").permitAll()
						.requestMatchers("/", "/error", "/login", "/register", "/loginerror", "/confirmation",
								"/system-error", "/search")
						.permitAll()
						.requestMatchers("/section/peces", "/section/insectos", "/section/fosiles", "/section/arte",
								"/welcome-user")
						.permitAll()
						.requestMatchers("/object/*").permitAll()

						// PRIVATE PAGES
						.requestMatchers("/statistics").hasAnyRole("USER", "ADMIN")
						.requestMatchers("/notes/**", "/profile/**", "/edit-profile").hasRole("USER")
						.requestMatchers("/objects/new", "/objects/edit/**", "/objects/delete/**", "/admin/**")
						.hasRole("ADMIN")

						// OpenAPI
						.requestMatchers("/v3/api-docs*/**").permitAll()
						.requestMatchers("/swagger-ui.html").permitAll()
						.requestMatchers("/swagger-ui/**").permitAll())

				.formLogin(formLogin -> formLogin
						.loginPage("/login")
						.failureUrl("/loginerror")
						.successHandler((request, response, authentication) -> {
							response.sendRedirect("/welcome-user");
						})
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/confirmation?action=logout")
						.invalidateHttpSession(true) // Delete session
						.deleteCookies("JSESSIONID") // Delete cookie
						.permitAll());

		return http.build();
	}

}
