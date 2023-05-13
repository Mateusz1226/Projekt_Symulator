package pl.projekt_symulator.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.projekt_symulator.dto.PasswordDto;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.entity.User;
import pl.projekt_symulator.service.UserService;

//@Controller
//@RequestMapping("/api/simulator/user")
/*
//public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/resetPassword")
    public String getResetPasswordLink(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "/resetPassword";
    }
    @PostMapping ("/resetPassword")
    public String sendResetPasswordLink(@ModelAttribute("user") UserDto user, Model model, BindingResult result) {

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
    ResponseEntity<String> postChangePassword(@Valid @ModelAttribute("passwordDto") PasswordDto passwordDto, Model model,
                                              @AuthenticationPrincipal UserDetails userDetails, BindingResult result){


        if (result.hasErrors()) {
            model.addAttribute("passwordDto", passwordDto);
            return new ResponseEntity<String>("Problem z formularzem", HttpStatus.NOT_FOUND);
        }



        String respons = userService.changePassword(userDetails,passwordDto.getOldPassword(),passwordDto.getNewPassword1(),passwordDto.getNewPassword2());
        return new ResponseEntity<String>(respons, HttpStatus.OK );
    }

}
*/
