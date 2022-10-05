package org.example.simpletweetsite.service;

import org.example.simpletweetsite.domain.Role;
import org.example.simpletweetsite.domain.User;

import org.example.simpletweetsite.repositories.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MailSender mailSender;

    @Test
    void addUserTest() {
        User user = new User();

        user.setEmail("example@example.com");

        boolean isUserCreated = userService.addUser(user);
        assertTrue(isUserCreated);
        assertFalse(user.isActive());
        assertNotNull(user.getActivationCode());
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        verify(userRepository, Mockito.times(1)).save(user);
        verify(mailSender, Mockito.times(1)).send(
                ArgumentMatchers.matches(user.getEmail()),
                ArgumentMatchers.matches("Activation Code"),
                ArgumentMatchers.contains("Welcome to Simple Tweeter Site.")
        );
    }

    @Test
    void addUserFailTest(){
        User user = new User();
        user.setUsername("John");

        doReturn(user)
                .when(userRepository)
                .findByUsername("John");

        boolean isUserCreated = userService.addUser(user);
        assertFalse(isUserCreated);

        verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        verify(mailSender, Mockito.times(0)).send(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        );
    }

    @Test
    void activateUserTest() {
        User user = new User();

        doReturn(user)
                .when(userRepository)
                .findByActivationCode("activate");

        boolean isUserActivated = userService.activateUser("activate");

        assertTrue(isUserActivated);
        assertNull(user.getActivationCode());
        verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void activateUserFailTest(){
        User user = new User();
        user.setActivationCode("someCode");

        doReturn(user)
                .when(userRepository)
                .findByActivationCode("activate");

        boolean isUserActivated = userService.activateUser("wrongCode");
        assertFalse(isUserActivated);
        verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }
}