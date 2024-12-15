package tdc.edu.vn.project_mobile_be.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import tdc.edu.vn.project_mobile_be.commond.GuestAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SecurityConfig {
    private final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/*",
            "/api/v1/*/*",
            "/api/v1/*/*/*",
            "/api/v1/*/*/*/*",
            "/ws/**",
            "api/chat/*",
            "api/chat/*/*",
            "/api/v1/products/relate",
            "/api/v1/products",
            "/api/v1/products/filters?",
            "/ws",
            "/api/paypment/create-order",
            "/v2/create",
            "/api/*",
            "/api/*/*",
            "/carts/user/*",
            "/gemini-pro-vision",
            "/gemini-pro-vision/chat",
            "api/gemini/generate",
            "api/gemini/*",
            "/api/gemini/chat/compare",
            "/swagger-ui/*",
            "/swagger-ui.html",
            "/v3/api-docs/*",
            "/swagger-ui/oauth2-re",
            "/v3/api-docs/swagger-config"
            , "v3/api-docs"
            ,"/redis/ping"

    };



    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(request ->
                        request.requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                );
        httpSecurity
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                                .decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable);

        httpSecurity.addFilterBefore(guestAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public GuestAuthenticationFilter guestAuthenticationFilter() {
        return new GuestAuthenticationFilter();
    }
}
