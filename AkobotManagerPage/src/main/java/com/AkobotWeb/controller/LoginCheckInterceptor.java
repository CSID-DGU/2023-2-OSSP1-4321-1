package com.AkobotWeb.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;


// �� ���������� �α��� ���θ� Ȯ���ϰ�
// �ƴϸ� �α��� �������� redirect�ϵ��� �����ϱ� ����
// ���ͼ��� �߰�
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if ("/api/v1/**".equals(requestURI) || "/".equals(requestURI) || "/login".equals(requestURI) || "/oauth2/**".equals(requestURI) || "/dongguk".equals(requestURI) || "".equals(requestURI) || "/add".equals(requestURI) || "/vendor/**".equals(requestURI) || "/css/**".equals(requestURI) || "/img/**".equals(requestURI) || "/js/**".equals(requestURI) || "/h2/**".equals(requestURI) || "/h2-console/**".equals(requestURI)) {
            return true;
        }

        // ������ pwSuccess �̸��� �ش��ϴ� �����Ͱ� false�̰ų� null�̸� firstpage �� redirect�ϵ��� �Ѵ�.
        Boolean pwSuccess = (Boolean) request.getSession().getAttribute("pwSuccess");

        if (pwSuccess == null || !pwSuccess) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}

