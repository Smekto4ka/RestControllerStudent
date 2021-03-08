package ru.oogis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import ru.oogis.services.UserService;

import java.util.Arrays;

@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
        System.out.println(new SecurityProperties().getUser().getPassword());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/auth/**")//по данному пути
                .authenticated()// пускать только аутентифицированных
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")
                .and()
                //.httpBasic() // базовая аутентификация, спринг выкенет окно регистрации, когда пойдешь по запретному пути
                .formLogin()
                //.loginProcessingUrl("/helloLog")//отправлять данные аутентификации на этот post url
                //.defaultSuccessUrl("/") // перекинуть после успешного захода , иначе на URL куда шел изначально
                .and()
                .logout().logoutSuccessUrl("/hello"); // куда отправить после отмены регистрации*-*- // не работает у меня/ что прописать url?
    }

    // добавить в память дефолтных юзеров , не база // не работает
/*    @Bean
    public UserDetailsService user() {
        UserDetails user = new User("admin", "admin", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        return new InMemoryUserDetailsManager(Arrays.asList(user));
    }*/


    @Override// подключение сервиса , в котором идет работа с юзерами
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
}
