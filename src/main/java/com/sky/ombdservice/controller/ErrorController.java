package com.sky.ombdservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400 -> {
                errorMsg = "Http Error Code: 400. Bad Request";
            }
            case 401 -> {
                errorMsg = "Http Error Code: 401. Unauthorized";
            }
            case 404 -> {
                errorMsg = "Http Error Code: 404. Resource not found";
            }
            case 500 -> {
                errorMsg = "Http Error Code: 500. Internal Server Error";
            }
            default -> throw new IllegalStateException("Unexpected value: " + httpErrorCode);
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }
}
