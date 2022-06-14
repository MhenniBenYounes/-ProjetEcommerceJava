package fr.idprocess.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import fr.idprocess.admin.service.AdminSecurityService;

/* Pas besoin de l'annotation ( @Configuration ) du org.springframework.context.annotation
 * puisque ( @EnableWebSecurity ) du org.springframework.security.config.annotation.web.configuration
 * inclut cette annotation pour fonctionner dans le context du Spring.
*/
//@Configuration
@EnableWebSecurity
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AdminSecurityService adminSecurityService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(adminSecurityService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.antMatcher("/admin/**").authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")

				// login
				.and().formLogin().loginPage("/adminLogin").defaultSuccessUrl("/admin/")
				.loginProcessingUrl("/admin/login").and().exceptionHandling().accessDeniedPage("/adminLogin")

				// logout
				.and().logout().deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout")).logoutSuccessUrl("/adminLogin");
	}
}
