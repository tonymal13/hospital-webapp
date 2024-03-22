package ru.mal.HospitalWebApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.mal.HospitalWebApp.services.OurUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class SecurityConfig {


    private final OurUserDetailsService ourUserDetailsService;

    @Autowired
    public SecurityConfig(OurUserDetailsService personDetailsService) {
        this.ourUserDetailsService = personDetailsService;
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(getPasswordEncoder());
        authProvider.setUserDetailsService(ourUserDetailsService);
        return authProvider;
    }


    @Bean
    protected SecurityFilterChain filterChain (HttpSecurity http)throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests)->
                        authorizeHttpRequests
                                .requestMatchers("/user/contractPage",
                                        "/user/login","/user/servicesPage","/user/visitPage","/doctor/search",
                                        "/user/doctorsPage","/error","/index","/css/**","/img/**","/js/**").permitAll()
                                .requestMatchers("/user/createVisit").hasAnyRole("USER")
                                .requestMatchers("/doctor/clientPage","/doctor/clientsPage").hasAnyRole("DOCTOR","ADMIN")
                                .requestMatchers(
                                        "/doctor/editVisit/**","/doctor/visitsPage").hasAnyRole("DOCTOR")
                                .requestMatchers("/admin/createContract","/admin/createCard",
                                        "/admin/createDoctor","/admin/editDoctor","/admin/createService",
                                        "/admin/editService","/admin/requests","/admin/showStat"
                                        ).hasAnyRole("ADMIN")
                                .requestMatchers("/user/personalAccount","/logout").authenticated()
                                .anyRequest().permitAll()

                );
        http
                .formLogin((formLogin)->formLogin.loginPage("/user/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/index",true)
                .failureUrl("/user/login?error"));
        http.logout((logout)->
                logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/index")
        );
        return http.build();
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
