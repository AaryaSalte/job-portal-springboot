package com.jobportal.jobportal.service;

import com.jobportal.jobportal.entity.User;
import com.jobportal.jobportal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jobportal.jobportal.entity.Role;
import java.util.Set;
import com.jobportal.jobportal.repository.RoleRepository;

import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }



    private final UserRepository userRepository;

    public User saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName("USER");
        user.setRoles(Set.of(userRole));
        user.setEnabled(true);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void disableUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setEnabled(false);
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }



}
