package com.AkobotWeb.config;

import com.AkobotWeb.config.auth.LoginUserArgumentResolver;
import com.AkobotWeb.controller.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    /*
    * HandlerMethodArgumentResolver는 항상 WebMvcConfigurer 의 addArguementResolvers()를 통해 추가해야한다.
    *
    * */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(loginUserArgumentResolver);
    }

    //LoginCheckInterceptor()를 인터셉트로 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .excludePathPatterns("/","/api/v1/**","/login", "/oauth2/**","/dongguk","","/add" ,"/vendor/**","/css/**", "/img/**", "/js/**", "/h2/**", "/h2-console/**"); // 로그인 및 OAuth2 인증과 관련된 경로는 제외
    }
}
