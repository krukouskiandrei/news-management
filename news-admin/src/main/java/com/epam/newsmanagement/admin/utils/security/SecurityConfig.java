package com.epam.newsmanagement.admin.utils.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@ComponentScan("com.epam.newsmanagement.common")
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
		private static final String USER_BY_USERNAME_QUERY = "SELECT LOGIN as username, " +
            "PASSWORD as password, 1 as enabled FROM CUSTOMER WHERE LOGIN = ?";
    private static final String AUTHORITIES_BY_USERNAME_QUERY = "SELECT LOGIN as username, " +
            "ROLE_NAME as authority FROM CUSTOMER customer JOIN ROLES customer_role " +
            "ON customer_role.USER_ID = customer.USER_ID WHERE customer.LOGIN = ?";
    
    @Autowired
    DataSource dataSource;
    
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception{
        authentication.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new Md5PasswordEncoder())
                .usersByUsernameQuery(USER_BY_USERNAME_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/news/**").hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login?error=1")
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("login").passwordParameter("password")
                .defaultSuccessUrl("/news", true)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }
}