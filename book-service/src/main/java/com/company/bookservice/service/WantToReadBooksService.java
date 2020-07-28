package com.company.bookservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.company.bookservice.entity.WantToReadBooks;
import com.company.bookservice.repository.WantToReadBooksRepository;

import org.springframework.stereotype.Service;

@Service
public class WantToReadBooksService {

    private final WantToReadBooksRepository wantToReadBooksRepository;

    public WantToReadBooksService(WantToReadBooksRepository wantToReadBooksRepository) {
        this.wantToReadBooksRepository = wantToReadBooksRepository;
    }

    public List<WantToReadBooks> getAllWantToReadBooksByUser(Long userId) {
        Optional<List<WantToReadBooks>> books = wantToReadBooksRepository.findAllByUserId(userId);
        if (!books.isPresent())
            return new ArrayList<>();
        return books.get();
    }

    public WantToReadBooks saveWantToReadBook(String bookId, Long userId) {
        WantToReadBooks wantToReadBooks = new WantToReadBooks();
        wantToReadBooks.setBookId(bookId);
        wantToReadBooks.setUserId(userId);
        wantToReadBooks.setCreatedDate(new Date());
        return wantToReadBooksRepository.save(wantToReadBooks);
    }
}