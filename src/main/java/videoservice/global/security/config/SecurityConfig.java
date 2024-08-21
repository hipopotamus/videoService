package videoservice.global.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import videoservice.global.security.filter.JwtAuthenticationFilter;
import videoservice.global.security.filter.JwtAuthorizationFilter;
import videoservice.global.security.handler.AccountAccessDeniedHandler;
import videoservice.global.security.handler.AccountAuthenticationEntryPoint;
import videoservice.global.security.jwt.JwtProcessor;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProcessor jwtProcessor;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/accounts/login").authenticated()
                        .requestMatchers(HttpMethod.GET, "/docs/index.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/accounts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/accounts").permitAll()
                        .requestMatchers(HttpMethod.GET, "/videoFiles/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/view/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/boards/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/boards/statistics/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/dataGenerate/**").permitAll()
                        .anyRequest().authenticated()
                );


        http
                .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtProcessor))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, jwtProcessor));

        http
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(new AccountAccessDeniedHandler())
                        .authenticationEntryPoint(new AccountAuthenticationEntryPoint()));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityConfig() {
        return (web -> web
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://seb40-mainproject.s3-website.ap-northeast-2.amazonaws.com"
                ,"http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS", "PUT"));
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
