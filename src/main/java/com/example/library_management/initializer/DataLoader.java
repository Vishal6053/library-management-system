package com.example.library_management.initializer;

import com.example.library_management.model.Role;
import com.example.library_management.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        // Check if roles already exist before creating
        if (roleRepository.findByName("ROLE_USER") == null) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName("ROLE_AUTHOR") == null) {
            Role authorRole = new Role();
            authorRole.setName("ROLE_AUTHOR");
            roleRepository.save(authorRole);
        }
    }
}
