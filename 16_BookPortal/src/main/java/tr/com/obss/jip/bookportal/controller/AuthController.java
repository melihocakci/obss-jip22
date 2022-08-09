package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.bookportal.service.UserService;

@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final UserService userService;

    @GetMapping("/basicauth")
    public void basicAuth() {

    }
}
