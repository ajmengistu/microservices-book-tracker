package com.company.bookservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.company.bookservice.entity.AlreadyReadBooks;
import com.company.bookservice.repository.AlreadyReadBooksRepository;

import org.springframework.stereotype.Service;

@Service
public class AlreadyReadBooksService {
    private final AlreadyReadBooksRepository alreadyReadBooksRepository;

    public AlreadyReadBooksService(AlreadyReadBooksRepository alreadyReadBooksRepository) {
        this.alreadyReadBooksRepository = alreadyReadBooksRepository;
    }

    public AlreadyReadBooks saveAlreadyReadBooks(String bookId, long userId) {
        AlreadyReadBooks book = new AlreadyReadBooks();
        book.setBookId(bookId);
        book.setUserId(userId);
        book.setCreatedDate(new Date());
        return alreadyReadBooksRepository.save(book);
    }

    public List<AlreadyReadBooks> getAllAlreadyBooksByUser(Long userId) {
        Optional<List<AlreadyReadBooks>> books = alreadyReadBooksRepository.findAllByUserId(userId);
        if (!books.isPresent())
            return new ArrayList<AlreadyReadBooks>();
        return books.get();
    }
}