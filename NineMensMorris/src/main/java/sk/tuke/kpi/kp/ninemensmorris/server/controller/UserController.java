package sk.tuke.kpi.kp.ninemensmorris.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.kpi.kp.ninemensmorris.entity.Uzer;
import sk.tuke.kpi.kp.ninemensmorris.service.user.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController
{
    private String loggedUser;
    private boolean failed = false;
    @Autowired
    private UserService userService;

//    public String login(String login, String password)
//    {
//        // if (isValid()) return "redirect:/ninemensmorris/new";
//
//
//        if ("heslo".equals(password))
//        {
//            loggedUser = new User(login);
//        }
//        return "redirect:/ninemensmorris/new";
//    }
    @RequestMapping("/logout")
    public String logout()
    {
       loggedUser = null;
        return "redirect:/ninemensmorris/new";
    }

    @RequestMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password) {
        failed = false;
          if (userService.isValid(login, password))
          {
              loggedUser = login;
              return "redirect:/ninemensmorris/new";
          }
        failed = true;
        return "redirect:/ninemensmorris/new";
    }

    @RequestMapping("/register")
    public String register(@RequestParam String registerUsername, @RequestParam String registerPassword)
    {
        failed = false;
        System.out.println(registerUsername);
        if (userService.validUsername(registerUsername))
        {
            userService.addUser(new Uzer(registerUsername, registerPassword));
            loggedUser = registerUsername;
            return "redirect:/ninemensmorris/new";
        }
        failed = true;
        return "redirect:/ninemensmorris/new";
    }

    public String getLoggedUser()
    {
        return loggedUser;
    }

    public boolean isLogged()
    {
        return loggedUser != null;
    }

    public boolean isFailed()
    {
        return failed;
    }
}
