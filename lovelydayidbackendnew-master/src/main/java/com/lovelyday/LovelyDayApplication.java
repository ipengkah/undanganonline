package com.lovelyday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.lovelyday.security.JWTAuthorizationFilter;

@SpringBootApplication
public class LovelyDayApplication {
	
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				/*.antMatchers(HttpMethod.POST, "/lld_api/**").permitAll()*/
				.antMatchers("/lld_api/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin().permitAll()
				.and()
				.logout().permitAll();
			http.cors();
		}
	}
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(LovelyDayApplication.class, args);
	}

}
