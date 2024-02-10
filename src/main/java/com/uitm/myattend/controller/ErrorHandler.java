package com.uitm.myattend.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//controler to override the default whitelable error
@Controller
public class ErrorHandler implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null) {
            int errCode = Integer.parseInt(status.toString());

            //redirect the error to custom page
            if(errCode == HttpStatus.NOT_FOUND.value()) {
                return "Error/error404";
            }else if(errCode == HttpStatus.FORBIDDEN.value()){
                return "Error/error403";
            }else if(errCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "Error/error500";
            }
        }
        return "error";
    }
}
