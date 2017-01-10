package com.daou.setlist.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmsErrorController implements ErrorController {

	private static final Logger log = LoggerFactory.getLogger(EmsErrorController.class);

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error(HttpServletRequest request, HttpServletResponse response) {
    	log.info("status : ", response.getStatus());
		
        return "error";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
