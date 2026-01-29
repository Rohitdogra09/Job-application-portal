package com.rohit.jobportal.bootstrap;


import com.rohit.jobportal.entity.Role;
import com.rohit.jobportal.entity.User;
import com.rohit.jobportal.repository.RoleRepository;
import com.rohit.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String...args){
        //roles already inserted by FLyway, we just need to load them
        Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();

        String adminEmail= "admin@jobportal.com";

        if(!userRepository.existsByEmail(adminEmail)){
            User admin = User.builder()
                    .email(adminEmail)
                    .fullName("System Admin")
                    .passwordHash(passwordEncoder.encode("Admin@12345"))
                    .enabled(true)
                    .roles(Set.of(adminRole))
                    .build();

            userRepository.save(admin);

            System.out.println("Seeded ADMIN: "+ adminEmail + "/ Admin@12345");
        }
    }
}
