package com.example.health.interceptor;

import com.example.health.data.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Component
public class UserAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json;charset=UTF-8");
//            PrintWriter out = response.getWriter();
//            ObjectMapper objectMapper = new ObjectMapper();
//            out.write(objectMapper.writeValueAsString(ApiResult.fail("Need user login")));
//            out.flush();
            return false;
        }
        return true;
    }
}
