package com.company.bookservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.company.bookservice.entity.FavoriteBooks;
import com.company.bookservice.repository.FavoriteBooksRepository;

import org.springframework.stereotype.Service;

@Service
public class FavoriteBooksService {
    private final FavoriteBooksRepository favoriteBooksRepository;

    public FavoriteBooksService(FavoriteBooksRepository favoriteBooksRepository) {
        this.favoriteBooksRepository = favoriteBooksRepository;
    }

    public FavoriteBooks saveFavoriteBook(String bookId, Long userId) {
        FavoriteBooks favoriteBooks = new FavoriteBooks();
        favoriteBooks.setBookId(bookId);
        favoriteBooks.setUserId(userId);
        favoriteBooks.setCreatedDate(new Date());
        return favoriteBooksRepository.save(favoriteBooks);
    }

    public List<FavoriteBooks> getAllFavoriteBooksByUser(Long userId) {
        Optional<List<FavoriteBooks>> favoriteBooks = favoriteBooksRepository.findAllByUserId(userId);
        if (!favoriteBooks.isPresent())
            return new ArrayList<>();
        return favoriteBooks.get();
    }

}