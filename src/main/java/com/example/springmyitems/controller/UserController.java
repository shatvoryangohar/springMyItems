package com.example.springmyitems.controller;

import com.example.springmyitems.entity.User;
import com.example.springmyitems.service.MailService;
import com.example.springmyitems.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/addUser")
    public String addUserPage() {
        return "saveUser";
    }

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute User user, ModelMap map, Locale locale) throws MessagingException {
        List<String> errorMsg = new ArrayList<>();

        if (user.getName() == null || user.getName().equals("")) {
            errorMsg.add("name is required");
        }
        if (user.getSurname() == null || user.getSurname().equals("")) {
            errorMsg.add("surname is required");
        }

        if (user.getEmail() == null || user.getEmail().equals("")) {
            errorMsg.add("email is required");
        }
        if (!errorMsg.isEmpty()) {
            map.addAttribute("errors", errorMsg);
            return "saveUser";
        }
        user.setActive(false);
        user.setToken(UUID.randomUUID().toString());
        user.setTokenCreatedDate(LocalDateTime.now());
        userService.save(user);
        mailService.sendHtmlEmail(user.getEmail(),
                "Welcome," + user.getSurname(),
user," http://localhost:8080/user/activate?token=" + user.getToken(),"verifyTemplate",locale);

        return "redirect:/";
    }


    @GetMapping("/user/activate")
    public String activateUser(ModelMap map, @RequestParam String token) {

        Optional<User> user = userService.findByToken(token);
        if (!user.isPresent()) {
            map.addAttribute("message", "User Does not exists");
            return "activateUser";
        }
        User userFromDB = user.get();
        if (userFromDB.isActive()) {
            map.addAttribute("message", "User already active");
            return "activateUser";
        }
        userFromDB.setActive(true);
        userFromDB.setToken(null);
        userFromDB.setTokenCreatedDate(null);
        userService.save(userFromDB);
        map.addAttribute("message", "User activated,please login");
        return "activateUser";
    }

    @GetMapping("/editUser/{id}")
    public String editUserPage(ModelMap map, @PathVariable("id") int id) {
        map.addAttribute("user", userService.findById(id));
        return "saveUser";

    }


}

