package org.example.simpletweetsite.service;

import org.example.simpletweetsite.domain.Role;
import org.example.simpletweetsite.domain.User;
import org.example.simpletweetsite.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final MailSender mailSender;

    public UserService(UserRepository userRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user) {

        User userDb = userRepository.findByUsername(user.getUsername());
        if (userDb != null) {
            return false;
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if (!ObjectUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s! \n" +
                            "Welcome to Simple Tweeter Site. Please visit next link: " +
                            "http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode());
            mailSender.send(user.getEmail(), "Activation Code", message);
        }
        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }
        user.setActive(true);
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }
}
