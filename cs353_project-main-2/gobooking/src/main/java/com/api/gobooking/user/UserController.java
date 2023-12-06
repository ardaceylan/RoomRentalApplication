package com.api.gobooking.user;

import com.api.gobooking.user.appuser.AppUserRequest;
import com.api.gobooking.user.appuser.AppUserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("gobooking/user")
@AllArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final AppUserService appUserService;
    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping(path = "/id/{userId}")
    public User getUserById(@PathVariable(name = "userId") int userId){
        return userService.getUser(userId);
    }

    @GetMapping(path = "/email/{email}")
    public User getUserByEmail(@PathVariable(name = "email") String email){return userService.getUserByEmail(email);}
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session){

        // Perform authentication logic here, such as validating username and password
        if (userService.getUserByEmail(email) != null && userService.passwordMatches(password,
                                                        userService.getUserByEmail(email).getPassword())
                ) {
            // Authentication successful, set user information in session
            session.setAttribute("userId", userService.getUserByEmail(email).getId());
            if(userService.getUserByEmail(email).getRole() == Role.ADMIN){ return "redirect:/admin_dashboard";}
            else{return  "redirect:/home";}
        } else {
            // Authentication failed, redirect back to login page with an error message
            return "redirect:/login";
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestParam("name") String name,
                         @RequestParam("surname") String surname,
                         @RequestParam("birthdate") String birthdate,
                         @RequestParam("email") String email,
                         @RequestParam("password") String password,
                         @RequestParam("city") String city,
                         HttpSession session){

        if (userService.getUserByEmail(email) != null) {
            // User with the same email already exists, redirect back to signup page with an error message
            return "redirect:/signup?error=emailExists";
        }

        // Create a new appUserRequest
        AppUserRequest appUserRequest = new AppUserRequest(name, surname, email, password, Timestamp.valueOf(birthdate), 0.0, city);

        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setBirthDate(Timestamp.valueOf(birthdate));
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(Role.APP_USER);

        // Save the new user to the database
        //userService.saveUser(newUser);
        appUserService.addAppUser(appUserRequest);
        // Set user information in session
        session.setAttribute("userId", newUser.getId());

        // Redirect to the home page or any other desired destination
        return "redirect:/home";
    }


}
