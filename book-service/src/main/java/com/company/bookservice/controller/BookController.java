package com.company.bookservice.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.company.bookservice.entity.AlreadyReadBooks;
import com.company.bookservice.entity.FavoriteBooks;
import com.company.bookservice.entity.WantToReadBooks;
import com.company.bookservice.service.AlreadyReadBooksService;
import com.company.bookservice.service.FavoriteBooksService;
import com.company.bookservice.service.WantToReadBooksService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class BookController {

    public final String AUTHORIZATION = "Authorization";
    private final AlreadyReadBooksService alreadyReadBooksService;
    private final WantToReadBooksService wantToReadBooksService;
    private final FavoriteBooksService favoriteBooksService;
    private final RestTemplate restTemplate;

    public BookController(AlreadyReadBooksService alreadyReadBooksService, RestTemplate restTemplate,
            WantToReadBooksService wantToReadBooksService, FavoriteBooksService favoriteBooksService) {
        this.alreadyReadBooksService = alreadyReadBooksService;
        this.restTemplate = restTemplate;
        this.wantToReadBooksService = wantToReadBooksService;
        this.favoriteBooksService = favoriteBooksService;
    }

    // already read book API

    @GetMapping("/already-read-book/all")
    public List<AlreadyReadBooks> getAllAlreadyReadBooks(HttpServletRequest request) {
        ResponseEntity<String> response = getCurrentlyAuthenticatedUserId(request);
        return alreadyReadBooksService.getAllAlreadyBooksByUser(Long.valueOf(response.getBody()));
    }

    @HystrixCommand(fallbackMethod = "saveAlreadyReadBooksFallback")
    @PostMapping("/already-read-book/add")
    public ResponseEntity<AlreadyReadBooks> saveAlreadyReadBooks(@RequestBody BookToSaveVM bookToSaveVM,
            HttpServletRequest request) {
        ResponseEntity<String> response = getCurrentlyAuthenticatedUserId(request);
        return ResponseEntity.ok().headers(new HttpHeaders()).body(
                alreadyReadBooksService.saveAlreadyReadBooks(bookToSaveVM.bookId, Long.valueOf(response.getBody())));
    }

    public ResponseEntity<AlreadyReadBooks> saveAlreadyReadBooksFallback(@RequestBody BookToSaveVM saveBookVM,
            HttpServletRequest request) {
        AlreadyReadBooks fallbackBook = new AlreadyReadBooks();
        fallbackBook.setBookId("");
        fallbackBook.setCreatedDate(new Date());
        fallbackBook.setUserId(0L);
        return ResponseEntity.ok(fallbackBook);
    }

    // want to read books API

    @GetMapping("/want-to-read-book/all")
    public List<WantToReadBooks> getAllWantToReadBooks(HttpServletRequest request) {
        ResponseEntity<String> response = getCurrentlyAuthenticatedUserId(request);
        return wantToReadBooksService.getAllWantToReadBooksByUser(Long.valueOf(response.getBody()));
    }

    @PostMapping("/want-to-read-book/add")
    public WantToReadBooks saveWantToReadBook(@RequestBody BookToSaveVM saveBookVM, HttpServletRequest request) {
        ResponseEntity<String> response = getCurrentlyAuthenticatedUserId(request);
        return wantToReadBooksService.saveWantToReadBook(saveBookVM.bookId, Long.valueOf(response.getBody()));
    }

    // favorite books API

    @GetMapping("/favorite-book/all")
    public List<FavoriteBooks> getAllFavoriteBooks(HttpServletRequest request) {
        ResponseEntity<String> response = getCurrentlyAuthenticatedUserId(request);
        return favoriteBooksService.getAllFavoriteBooksByUser(Long.valueOf(response.getBody()));
    }

    @PostMapping("/favorite-book/add")
    public FavoriteBooks saveFavoriteBook(@RequestBody BookToSaveVM bookToSaveVM, HttpServletRequest request) {
        ResponseEntity<String> response = getCurrentlyAuthenticatedUserId(request);
        return favoriteBooksService.saveFavoriteBook(bookToSaveVM.bookId, Long.valueOf(response.getBody()));
    }

    // all book lists for user API

    @GetMapping("/lists/all")
    public UserBookListsVM getAlBookListsByUser(HttpServletRequest request) {
        ResponseEntity<String> response = getCurrentlyAuthenticatedUserId(request);
        Long userId = Long.valueOf(response.getBody());
        UserBookListsVM userBookListsVM = new UserBookListsVM();
        userBookListsVM.setAlreadyReadBooks(alreadyReadBooksService.getAllAlreadyBooksByUser(userId));
        userBookListsVM.setWantToReadBooks(wantToReadBooksService.getAllWantToReadBooksByUser(userId));
        userBookListsVM.setFavoriteBooks(favoriteBooksService.getAllFavoriteBooksByUser(userId));
        return userBookListsVM;
    }

    /**
     * Get the currently logged in user id by making a request to the user service &
     * by passing the JWT. Note: getting a response as a String not as a Long b/c
     * there was an error.
     */
    public ResponseEntity<String> getCurrentlyAuthenticatedUserId(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, request.getHeader(AUTHORIZATION));
        return restTemplate.exchange("http://localhost:9090/api/user-id", HttpMethod.GET,
                new HttpEntity<String>("", headers), String.class);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class BookToSaveVM {
        private String bookId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class UserBookListsVM {
        private List<WantToReadBooks> wantToReadBooks;
        private List<AlreadyReadBooks> alreadyReadBooks;
        private List<FavoriteBooks> favoriteBooks;
    }
}