package com.bacovakuhinja.aspects;

import com.bacovakuhinja.annotations.Authorization;
import com.bacovakuhinja.model.User;
import com.bacovakuhinja.service.UserService;
import com.bacovakuhinja.utility.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
public class SecurityAspect {

    @Autowired
    UserService userService;

    @Around("@annotation(com.bacovakuhinja.annotations.Authorization) && args(request,..)")
    public Object authorized(ProceedingJoinPoint joinPoint, final HttpServletRequest request) throws Throwable {

        // Get role from annotation
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Authorization annotation = method.getAnnotation(Authorization.class);
        final String role = annotation.role();

        final String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            return new ResponseEntity<String>("{}", HttpStatus.UNAUTHORIZED);
        }

        // Authorization token
        final String token = auth.substring(7);

        final Claims claims = Jwts.parser().setSigningKey(Constants.Authorization.SECRET_KEY)
                .parseClaimsJws(token).getBody();

        // User's mail
        final String email = claims.get(Constants.Authorization.CLAIMS_BODY).toString();

        User user = userService.findOne(email);

        if ((user.getType().equals(role) || role.equals(Constants.UserRoles.EVERYONE))) {
            request.setAttribute(Constants.Authorization.LOGGED_USER, user);
            return joinPoint.proceed();
        }

        return new ResponseEntity<String>("{}", HttpStatus.UNAUTHORIZED);
    }
}
