package com.javeriana.proyecto.proyecto.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;

import com.javeriana.proyecto.proyecto.filter.JWTAuthorizationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig implements ISecurityConfig {




    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;

	@Override
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
	@Override
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

	
	@Override
    @Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                corsConfig.setAllowedOrigins(List.of("http://localhost:4200", "http://10.43.103.108"));
                corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                corsConfig.setAllowedHeaders(List.of("*"));
                corsConfig.setAllowCredentials(true);
                return corsConfig;
            }))
            .csrf(csrf -> csrf.ignoringRequestMatchers(ignoreSpecificRequests()))
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    
        return http.build();
    }


	private RequestMatcher ignoreSpecificRequests() {
        return new OrRequestMatcher(
            // new AntPathRequestMatcher("/indicadoressuim/api/autenticacion"),
            // new AntPathRequestMatcher("/indicadoressuim/api/peticion-mes"),
            new AntPathRequestMatcher("/jwt/security/autenticar/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/jwt/security/autenticar/**", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/jwt/security/autenticar/**", HttpMethod.PUT.name()),
            new AntPathRequestMatcher("/jwt/security/autenticar/**", HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/jwt/security/usuario/**", HttpMethod.GET.name()),
            new AntPathRequestMatcher("/jwt/security/usuario/**", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/jwt/security/usuario/**", HttpMethod.PUT.name()),
            new AntPathRequestMatcher("/jwt/security/usuario/**", HttpMethod.DELETE.name())
        );
    }
}
