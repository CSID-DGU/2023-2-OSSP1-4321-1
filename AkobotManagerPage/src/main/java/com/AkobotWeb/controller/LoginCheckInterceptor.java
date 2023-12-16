package com.AkobotWeb.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;


// 매 페이지에서 로그인 여부를 확인하고
// 아니면 로그인 페이지로 redirect하도록 수정하기 위해
// 인터셉터 추가
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if ("/api/v1/**".equals(requestURI) || "/".equals(requestURI) || "/login".equals(requestURI) || "/oauth2/**".equals(requestURI) || "/dongguk".equals(requestURI) || "".equals(requestURI) || "/add".equals(requestURI) || "/vendor/**".equals(requestURI) || "/css/**".equals(requestURI) || "/img/**".equals(requestURI) || "/js/**".equals(requestURI) || "/h2/**".equals(requestURI) || "/h2-console/**".equals(requestURI)) {
            return true;
        }

        // 세션의 pwSuccess 이름에 해당하는 데이터가 false이거나 null이면 firstpage 로 redirect하도록 한다.
        Boolean pwSuccess = (Boolean) request.getSession().getAttribute("pwSuccess");

        if (pwSuccess == null || !pwSuccess) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}

