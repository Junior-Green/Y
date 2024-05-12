package com.y.Y.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.y.Y.error.custom_exceptions.MissingCookieException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

public final class UtilityService {

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }

    public static Cookie extractSessionCookieFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            throw new MissingCookieException(HttpStatus.UNAUTHORIZED, "session");
        }

        Cookie sessionCookie = null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("session")) {
                sessionCookie = cookie;
                break;
            }
        }

        if (sessionCookie == null){
            throw new MissingCookieException(HttpStatus.UNAUTHORIZED, "session");
        }
        return sessionCookie;
    }
}
