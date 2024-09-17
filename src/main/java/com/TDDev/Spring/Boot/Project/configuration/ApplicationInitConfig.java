package com.TDDev.Spring.Boot.Project.configuration;

import com.TDDev.Spring.Boot.Project.entity.User;
import com.TDDev.Spring.Boot.Project.enums.Role;
import com.TDDev.Spring.Boot.Project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@Configuration
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());

                User user =User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123456"))
                      //  .roles(roles)
                        .build();

                userRepository.save(user);
                log.info("Admin has been created with default password: 123456!");
            }
        };
    }
}
