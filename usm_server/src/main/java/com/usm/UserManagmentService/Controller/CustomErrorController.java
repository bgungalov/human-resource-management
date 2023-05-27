package com.usm.UserManagmentService.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController, com.usm.UserManagmentService.Service.CustomErrorController {

    /**
     * Request method when error occurs.
     * If an error occurs, return a 500 error with the message 'Error occurred!'
     *
     * @param request The request object.
     * @return A ResponseEntity object with a message and a status code.
     */
    @RequestMapping("/error")
    @ResponseBody
    String error(HttpServletRequest request) {
        return "<h1>Error occurred</h1>";
    }

    /**
     * If the user requests a page that doesn't exist, return the error page.
     *
     * @return The path to the error page.
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}