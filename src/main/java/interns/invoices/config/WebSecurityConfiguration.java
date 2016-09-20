package interns.invoices.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import interns.invoices.repositories.CompanyRepository;
import interns.invoices.repositories.UserRepository;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new RequestFilter(userRepository,companyRepository),
                AbstractPreAuthenticatedProcessingFilter.class)
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .and().csrf().disable();
    }
}
