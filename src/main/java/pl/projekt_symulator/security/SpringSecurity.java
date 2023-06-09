package pl.projekt_symulator.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity

public class SpringSecurity {


    private final UserDetailsService userDetailsService;
    private final CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    public SpringSecurity(UserDetailsService userDetailsService, CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/api/simulator/register/**").permitAll()
                                .requestMatchers("/api/simulator/index").permitAll()
                                .requestMatchers("/api/simulator/user/changePassword").authenticated()
                                .requestMatchers("/api/simulator/resetPassword/**").permitAll()
                                .requestMatchers("/api/simulator/resetPassword/newPassword/**").permitAll()
                                .requestMatchers("/api/simulator/login").permitAll()
                                .requestMatchers("/calender.html").permitAll()
                                .requestMatchers("/mail").permitAll()
                                .requestMatchers("/api/simulator/schedule/**").authenticated()
                                .requestMatchers("/api/simulator/scheduleDelete/**").permitAll()
                                .requestMatchers(AUTH_SWAGGER).permitAll()
                                .requestMatchers("/api/simulator/admin/**").hasRole("ADMIN")

                ).formLogin(
                        form -> form
                                .loginPage("/api/simulator/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/api/simulator/schedule")
                                .permitAll()

                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    private static final String[] AUTH_SWAGGER = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"

    };


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}



