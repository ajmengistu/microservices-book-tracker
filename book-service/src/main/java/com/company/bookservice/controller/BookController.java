package com.company.bookservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookController {

    @GetMapping("/books")
    public String getBook() {
        return "books";
    }

    @GetMapping("/all")
    public String getAll(){
        return "all!";
    }
}