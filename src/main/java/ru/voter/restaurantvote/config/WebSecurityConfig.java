package ru.voter.restaurantvote.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import ru.voter.restaurantvote.model.User;
import ru.voter.restaurantvote.repository.UserRepository;
import ru.voter.restaurantvote.web.AuthUser;

import java.util.Optional;

@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            log.debug("Authenticating {}", email);
            Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
            return new AuthUser(optionalUser.orElseThrow(
                    () -> new UsernameNotFoundException("User '" + email +  "' was not found")
            ));
        };
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }
}
