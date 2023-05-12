package pl.projekt_symulator.controller;



import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.projekt_symulator.dto.PasswordDto;
import pl.projekt_symulator.dto.ScheduleDto;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.service.UserService;


@Controller
@RequestMapping("/api/simulator")
public class AuthController {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/index")
    public String home(){
        return "index";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }



    @PostMapping ("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDto userDto
                                            ){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<String>("Zalogowałeś się!", HttpStatus.OK);

    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }


    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "Wskazany email jest już zajęty");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
        // return new   ResponseEntity<String>("error", HttpStatus.BAD_REQUEST);
          return "/register";
        }

        String response =  userService.saveUser(userDto);
       // return new  ResponseEntity<String>(response, HttpStatus.CREATED);
       return "/schedule";

    }


    @GetMapping("/resetPassword")
    public String getResetPasswordLink(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "/resetPassword";
    }
    @PostMapping ("/resetPassword")
    public String sendResetPasswordLink(@ModelAttribute("user") UserDto user,Model model,BindingResult result) {

        if(result.hasErrors()){
            model.addAttribute("user", user);
            // return new   ResponseEntity<String>("error", HttpStatus.BAD_REQUEST);
            return "/resetPassword";
        }

       userService.creatUrlForeChangingPassword(user.getEmail());
        //  return ResponseEntity.ok(resetPasswordLink);
        return "/login";
    }

    @GetMapping("/resetPassword/newPassword/?id={userId}")
    public String getNewPassword(@PathVariable Long userId,Model model) {

        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        return "newPassword";
    }

    @PostMapping("/resetPassword/newPassword/save")
    public String saveNewPassword(@ModelAttribute("user") UserDto user,Model model,BindingResult result) {
        if(result.hasErrors()){
            model.addAttribute("user", user);
            // return new   ResponseEntity<String>("error", HttpStatus.BAD_REQUEST);
            return "/resetPassword";
        }

        userService.saveNewPassword(user);
        return "/login";
    }



    @GetMapping("/changePassword")
    public String getChangePassword(Model model) {
        PasswordDto passwordDto = new PasswordDto();
        model.addAttribute("passwordDto", passwordDto);
        return "/changePassword";
    }



    @PostMapping("/changePassword")
    ResponseEntity<String> postChangePassword(@Valid @ModelAttribute("passwordDto") PasswordDto passwordDto,Model model,
                                          @AuthenticationPrincipal UserDetails userDetails, BindingResult result){


            if (result.hasErrors()) {
                model.addAttribute("passwordDto", passwordDto);
                return new ResponseEntity<String>("Problem z formularzem", HttpStatus.NOT_FOUND);
            }



        String respons = userService.changePassword(userDetails,passwordDto.getOldPassword(),passwordDto.getNewPassword1(),passwordDto.getNewPassword2());
        return new ResponseEntity<String>(respons, HttpStatus.OK );
    }

}
