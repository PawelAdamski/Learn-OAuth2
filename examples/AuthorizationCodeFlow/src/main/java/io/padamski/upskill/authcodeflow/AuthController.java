package io.padamski.upskill.authcodeflow;

import io.padamski.upskill.authcodeflow.state.LoggedUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class AuthController {

    private final OAuth2Service oAuth2Service;
    private final LoggedUserService loggedUserService;

    public AuthController(OAuth2Service oAuth2Service, LoggedUserService loggedUserService) {
        this.oAuth2Service = oAuth2Service;
        this.loggedUserService = loggedUserService;
    }

    @GetMapping("/auth/login")
    public String login() {
        return "redirect:" + oAuth2Service.buildLoginUrl();
    }

    @GetMapping("/auth/callback")
    public String callback(@RequestParam String code, HttpServletResponse response) throws IOException, InterruptedException {
        String loggedUserName = oAuth2Service.exchangeAuthorizationCodes(code);

        Cookie loggedUserCookie = new Cookie("logged-user-name", loggedUserName);
        loggedUserCookie.setPath("/");
        response.addCookie(loggedUserCookie);

        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, @CookieValue(value = "logged-user-name", defaultValue = "") String userName) {
        model.addAttribute("loggedUser", loggedUserService.getUserState(userName));
        return "home";
    }
}
