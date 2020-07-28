package com.company.bookservice.controller;

import javax.servlet.http.HttpServletRequest;

import com.company.bookservice.entity.AlreadyReadBooks;
import com.company.bookservice.service.AlreadyReadBooksService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/api")
public class BookController {

    private final AlreadyReadBooksService alreadyReadBooksService;
    private final RestTemplate restTemplate;

    public BookController(AlreadyReadBooksService alreadyReadBooksService, RestTemplate restTemplate) {
        this.alreadyReadBooksService = alreadyReadBooksService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/books")
    public String getBook() {
        return "books";
    }

    @GetMapping("/all")
    public String getAll() {
        return "all!";
    }

    @HystrixCommand(fallbackMethod = "saveAlreadyReadBooksFallback")
    @PostMapping("/add/already-read-book")
    public AlreadyReadBooks saveAlreadyReadBooks(@RequestBody SaveABookVM book, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:9090/api/user-id", HttpMethod.GET,
                new HttpEntity<String>("", headers), String.class);
        return alreadyReadBooksService.saveAlreadyReadBooks(book.bookId, Long.valueOf(response.getBody()));
    }

    public AlreadyReadBooks saveAlreadyReadBooksFallback(@RequestBody SaveABookVM book, HttpServletRequest request) {
        return new AlreadyReadBooks();
    }

    @GetMapping("/already-read-books")
    public String getAlreadyReadBooks() {
        // get currently authenticated user
        return "return already read books!!!";
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class SaveABookVM {
        private String bookId;
    }
}