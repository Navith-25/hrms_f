package com.nibm.hr.hrms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Bean for encoding passwords
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean for configuring the main security rules
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        // ... (existing rules)
                        .requestMatchers("/css/**", "/js/**", "/login").permitAll()

                        // --- ROLE-BASED RULES ---
                        .requestMatchers("/showNewEmployeeForm", "/saveEmployee", "/showFormForUpdate/**", "/deleteEmployee/**").hasAnyRole("ADMIN", "HR")
                        .requestMatchers("/leave", "/leave/request").hasAnyRole("ADMIN", "HR", "EMPLOYEE")
                        .requestMatchers("/admin/leave", "/admin/leave/**").hasAnyRole("ADMIN", "HR")
                        .requestMatchers("/admin/payroll/**").hasAnyRole("ADMIN", "HR")
                        .requestMatchers("/payslips", "/payslip/**").hasAnyRole("ADMIN", "HR", "EMPLOYEE")

                        // --- NEW PERFORMANCE RULES ---
                        .requestMatchers("/admin/performance/**").hasAnyRole("ADMIN", "HR")
                        .requestMatchers("/performance").hasAnyRole("ADMIN", "HR", "EMPLOYEE")
                        // --- END NEW PERFORMANCE RULES ---

                        .requestMatchers("/").hasAnyRole("ADMIN", "HR", "EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Use our custom login page
                        .loginPage("/login")
                        // This is the URL the form will POST to
                        .loginProcessingUrl("/login")
                        // On successful login, redirect to the homepage
                        .defaultSuccessUrl("/", true)
                        // Allow everyone to access the login page
                        .permitAll()
                )
                // AFTER (in SecurityConfig.java)
                .logout(logout -> logout
                        // Just remove the logoutRequestMatcher. The form POSTs to /logout by default.
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}