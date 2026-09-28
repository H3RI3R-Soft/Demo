package com.test.studentCRUD.Student.Controller;

import com.test.studentCRUD.Configuration.ResponseGlobal;
import com.test.studentCRUD.Student.Service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {
    private final LoginService loginService;

    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/StudentLogin")
    public ResponseGlobal<?> login(@RequestParam String email, String password) {
        return loginService.login(email, password);
    }

}
