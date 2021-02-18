package com.example.Task1.exception;

import com.example.Task1.Task1Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.xml.bind.ValidationException;

@Controller("error")
public class ExceptionController {

    private Logger logger = LoggerFactory.getLogger(Task1Application.class);

    @ExceptionHandler(ValidationException.class)
    public void handleExceptions(){
        logger.error("Validation Exception");
    }

    @ExceptionHandler(Exception.class)
    public void handleAllExceptions(){
        logger.error("Exception");
    }

}
