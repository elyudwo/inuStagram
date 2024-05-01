package io.kr.inu.webclient.api.resolver;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Hidden
public class UserEmail {

    private final String email;

}
