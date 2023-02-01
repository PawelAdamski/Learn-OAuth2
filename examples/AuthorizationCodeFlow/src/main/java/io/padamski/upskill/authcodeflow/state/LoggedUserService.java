package io.padamski.upskill.authcodeflow.state;

import io.padamski.upskill.authcodeflow.dto.OAuth2UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoggedUserService {

    private final Map<String, LoggedUserState> loggedUsers;

    public LoggedUserService() {
        this.loggedUsers = new HashMap<>();
    }

    public String logUserIn(OAuth2UserDetails oAuth2UserDetails) {
        String userName = oAuth2UserDetails.getName();

        LoggedUserState loggedUser = new LoggedUserState();
        loggedUser.setLogged(true);
        loggedUser.setName(userName);
        loggedUser.setNickname(oAuth2UserDetails.getNickname());
        loggedUser.setPicture(oAuth2UserDetails.getPicture());
        loggedUsers.put(userName, loggedUser);

        return userName;
    }

    public LoggedUserState getUserState(String userName) {
        if (userName == null || userName.isBlank())
            return new LoggedUserState();

        return Optional.of(loggedUsers.get(userName)).orElse(new LoggedUserState());
    }
}
