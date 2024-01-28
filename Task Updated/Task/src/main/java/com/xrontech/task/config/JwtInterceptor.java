package com.xrontech.task.config;

import com.xrontech.task.domain.user.UserRole;
import com.xrontech.task.dto.RequestMetaDTO;
import com.xrontech.task.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;
    RequestMetaDTO requestMetaDTO;

    public JwtInterceptor(RequestMetaDTO requestMetaDTO) {
        this.requestMetaDTO = requestMetaDTO;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("authorization");
        if (
                !(
                        request.getRequestURI().contains("user/register") ||
                                request.getRequestURI().contains("user/login")

                )
        ) {
            Claims claims = jwtUtils.verifyToken(auth);

            requestMetaDTO.setId(Long.parseLong(claims.get("id").toString()));
            requestMetaDTO.setName(claims.get("name").toString());
            requestMetaDTO.setEmail(claims.get("email").toString());
            requestMetaDTO.setUserRole(claims.get("role").toString());
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
