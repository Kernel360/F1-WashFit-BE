package com.kernel360.main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainContoller {
    @GetMapping("")
    ResponseEntity<String> mainPage(){
        return ResponseEntity.status(HttpStatus.OK).body("main page입니다.");
    }

}
