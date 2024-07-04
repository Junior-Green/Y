package com.y.Y.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.y.Y.error.custom_exceptions.MissingCookieException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static boolean hasHashtag(String text) throws IllegalArgumentException {
        if(text == null){
            throw new IllegalArgumentException("Method requires a non null string to be passed");
        }

        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public static Set<String> extractHashtagNames(String text) {
        Set<String> hashtagNames = new HashSet<>();
        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            hashtagNames.add(matcher.group(1));
        }
        return hashtagNames;
    }
}
