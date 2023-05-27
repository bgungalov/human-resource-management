package com.usm.UserManagmentService.Config;

import com.usm.UserManagmentService.Service.JWTUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

import static com.usm.UserManagmentService.Constants.Constants.*;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * It returns a UserDetailsService object that will be used by the Spring Security framework to load the user details
     * whenever required
     *
     * @return A UserDetailsService object.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new JWTUserDetailsService();
    }

    /**
     * The BCryptPasswordEncoder is a class that implements the PasswordEncoder interface.
     *
     * The PasswordEncoder interface is a Spring Security interface that defines a single method:
     *
     * @return A new instance of BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * "This function creates a DaoAuthenticationProvider object, sets the userDetailsService and passwordEncoder
     * properties, and returns the object."
     *
     * The DaoAuthenticationProvider object is a Spring Security class that implements the AuthenticationProvider
     * interface.
     *
     * The AuthenticationProvider interface is a Spring Security interface that defines a single method:
     *
     * @return A DaoAuthenticationProvider object.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * > This function is used to create an AuthenticationManager bean that is used to authenticate the user when they
     * log in, and to perform various role-based checks throughout the application
     *
     * @param authConfig This is the AuthenticationConfiguration bean that we created in the previous step.
     * @return An AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * A filter chain that filters the requests.
     *
     * @param http This is the main interface for configuring the security of a web application.
     * @return A SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests().antMatchers("/authenticate", "**/chat/**").permitAll()
//                .antMatchers("/actuator/health/custom","/users").hasAnyAuthority(ROLE_BASIC_USER)
//                .antMatchers("/actuator/health", "/actuator/health/**", "/actuator/**").hasAnyAuthority(ROLE_ADMIN_HELPER)
//                .antMatchers("/users", "/users/**", "/users/{id}", "/roles/**", "/roles",
//                        "/useractions", "**/useractions/**", "/statistics", "/actuator/health", "/actuator/health/**",
//                        "/actuator/**").hasAnyAuthority(ROLE_ADMIN)
//                .antMatchers("/api/test/**").permitAll() // permit the class of test
                .antMatchers("/**").permitAll() // permit all the routers after swagger-ui.html
                .anyRequest().authenticated()
                .and()
                .logout().permitAll();

        http.headers().frameOptions().sameOrigin();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues().setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));
        http.cors().configurationSource(request -> corsConfiguration);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
}