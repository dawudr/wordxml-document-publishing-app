package com.pearson.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    ServletContext context;

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView browsefolder(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        LOGGER.debug("In DashboardController GET Request");

        ModelAndView modelAndView = new ModelAndView("/dashboard");
        return modelAndView;
    }

}