package jeff.baseproject.controller;

import jeff.baseproject.Service.UserService;
import jeff.baseproject.domain.User;
import jeff.baseproject.domain.request.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/users/register")
    public String createForm()
    {
        return "users/register";
    }

    @PostMapping("/users/register")
    public String create(UserForm userForm)
    {
        if (userForm.getPassword().equals(userForm.getRePassword())) {
            User user = new User(userForm.getName(), userForm.getEmail(), userForm.getPassword(), 0, false);
            this.userService.join(user);

        }
        return "redirect:/";
    }

}
