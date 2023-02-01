package io.padamski.upskill.authcodeflow.state;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoggedUserState {
    private boolean logged;
    private String name;
    private String nickname;
    private String picture;
}
