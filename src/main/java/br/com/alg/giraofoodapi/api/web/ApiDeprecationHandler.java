package br.com.alg.giraofoodapi.api.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiDeprecationHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().startsWith("/v1")) {
            //response.setStatus(HttpStatus.GONE.value());
            //return false;
            response.addHeader("X-Gifood-Deprecated", "Esta versão da API está depreciada e deixará de existir a partir de 01/01/2023. Use a versão mais atual da API");
        }
        return true;
    }
}
