package com.TDDev.Spring.Boot.Project.configuration;

import java.util.HashSet;

import com.TDDev.Spring.Boot.Project.entity.Role;
import com.TDDev.Spring.Boot.Project.repository.RoleRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.TDDev.Spring.Boot.Project.entity.User;
import com.TDDev.Spring.Boot.Project.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@Configuration
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if(roleRepository.findById("ADMIN").isEmpty()) {
                roleRepository.save(Role.builder()
                                .name("ADMIN")
                                .description("Admin role")
                        .build());
            }

            if(roleRepository.findById("CUSTOMER").isEmpty()) {
                roleRepository.save(Role.builder()
                                .name("CUSTOMER")
                                .description("Customer role")
                        .build());
            }

            if(roleRepository.findById("STAFF").isEmpty()) {
                roleRepository.save(Role.builder()
                                .name("STAFF")
                                .description("Staff role")
                        .build());
            }

            if (userRepository.findByUsername("admin").isEmpty()) {



                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123456"))
                        .role(roleRepository.findById("ADMIN").get())
                        .build();

                userRepository.save(user);
                log.info("Admin has been created with default password: 123456!");
            }
        };
    }
}
