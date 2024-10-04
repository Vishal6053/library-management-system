package com.example.library_management.service;

import com.example.library_management.dto.UserRegistrationDTO;
import com.example.library_management.model.Role;
import com.example.library_management.model.User;
import com.example.library_management.repository.RoleRepository;
import com.example.library_management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setFirstname(userRegistrationDTO.getFirstname());
        user.setLastname(userRegistrationDTO.getLastname());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setMobile(userRegistrationDTO.getMobile());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword())); // Hashing password

        // Set role based on the DTO
        Role role = roleRepository.findByName(userRegistrationDTO.getRole());
        if (role == null) {
            throw new RuntimeException("Role not found: " + userRegistrationDTO.getRole());
        }
        user.setRole(role);

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email); // Fetch by email
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(List.of(user.getRole()))
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
