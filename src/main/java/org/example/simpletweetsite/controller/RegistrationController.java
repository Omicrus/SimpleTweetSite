package org.example.simpletweetsite.controller;

import org.example.simpletweetsite.domain.Role;
import org.example.simpletweetsite.domain.User;
import org.example.simpletweetsite.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;


@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(){
      return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model){

        User userDb = userRepository.findByUsername(user.getUsername());

        if (userDb != null){
            model.put("message", "User exist!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
