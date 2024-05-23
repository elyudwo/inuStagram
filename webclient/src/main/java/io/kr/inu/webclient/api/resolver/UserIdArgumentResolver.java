package io.kr.inu.webclient.api.resolver;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private String SECRET = "NANA";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == UserEmail.class;
    }

    @Override
    public UserEmail resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                     NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        try {
            String jwtToken = webRequest.getHeader("Authorization");
            log.info("토큰입니다 : " + jwtToken);
            if (jwtToken == null) {
                log.warn("야 토큰이 없대");
                return new UserEmail("sample@inu.ac.kr");
            }
            jwtToken = jwtToken.replace("Bearer ", "");
            String email = (JWT.require(Algorithm.HMAC512(SECRET)).build().verify(jwtToken).getClaim("email")).asString();

            log.info("이메일입니다 : " + email);
            return new UserEmail(email);
        } catch (Exception e) {
            return null;
        }
    }
}