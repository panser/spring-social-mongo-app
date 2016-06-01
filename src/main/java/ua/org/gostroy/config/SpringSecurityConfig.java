package ua.org.gostroy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;

/**
 * Created by Sergey on 6/1/2016.
 */
@Configuration
public class SpringSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Value("${app.rememberMe.privateKey}")
    private String rememberMeKey;

    @Resource
    private UserDetailsService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
            .antMatchers("/",
                    "/signup",
                    "/public/**",
                    "/users/*",
                    "/auth/**").permitAll()
            .anyRequest().authenticated();

        http
            .formLogin()
            .loginPage("/signin")
            .permitAll().and()
            .rememberMe().key(rememberMeKey).rememberMeServices(rememberMeServices()).and()
            .logout()
            .deleteCookies("JSESSIONID")
            .permitAll()
            .and().apply(new SpringSocialConfigurer());
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(rememberMeKey, userService);
        rememberMeServices.setAlwaysRemember(true);   // See http://stackoverflow.com/questions/25565809/implementing-a-remember-me-for-spring-social

        return rememberMeServices;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}
