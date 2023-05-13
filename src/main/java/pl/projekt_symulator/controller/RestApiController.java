package pl.projekt_symulator.controller;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.projekt_symulator.dto.UserDto;
import pl.projekt_symulator.service.RestUserService;

@RestController
@RequestMapping("/restapi/simulator")
public class RestApiController {

    private final RestUserService userService;

    public RestApiController(RestUserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userDto) {
        userService.addUser(userDto);
        return ResponseEntity.ok(userDto);
    }

   @GetMapping("/{id}")
   public ResponseEntity<UserDto>  getUser(@PathVariable Long id) {
       UserDto dto = userService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
   }

    @PutMapping("/deactivateUser/{id}")
    public ResponseEntity<UserDto> deactivateUser(@PathVariable Long id,@RequestBody @Valid UserDto userDto){
        UserDto deactivateUser  = userService.deactivateUser(id, userDto);
        return ResponseEntity.ok(deactivateUser);
    }

    @PutMapping("/change/UserPhone/{id}")
    public ResponseEntity<UserDto> changeUserPhone(@PathVariable Long id,@RequestBody @Valid UserDto userDto){
        UserDto deactivateUser  = userService.changeUserPhone(id, userDto);
        return ResponseEntity.ok(deactivateUser);
    }
    @PutMapping("/change/MarketingAgreement/{id}")
    public ResponseEntity<UserDto> changeMarketingAgreement(@PathVariable Long id,@RequestBody @Valid UserDto userDto){
        UserDto deactivateUser  = userService.changeMarketingAgreement(id, userDto);
        return ResponseEntity.ok(deactivateUser);
    }
}
