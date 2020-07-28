package com.company.bookservice.service;

import java.util.Date;

import com.company.bookservice.entity.AlreadyReadBooks;
import com.company.bookservice.repository.AlreadyReadBooksRepository;

import org.springframework.stereotype.Service;

@Service
public class AlreadyReadBooksService {
    private final AlreadyReadBooksRepository alreadyReadBooksRepository;

    public AlreadyReadBooksService(AlreadyReadBooksRepository alreadyReadBooksRepository) {
        this.alreadyReadBooksRepository = alreadyReadBooksRepository;
    }

    public AlreadyReadBooks saveAlreadyReadBooks(String bookId, long l) {
        AlreadyReadBooks book = new AlreadyReadBooks();
        book.setBookId(bookId);
        book.setUserId(l);
        book.setCreatedDate(new Date());
        // check book does not already exist
        return alreadyReadBooksRepository.save(book);
    }
}