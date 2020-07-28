package com.company.bookservice.repository;

import com.company.bookservice.entity.WantToReadBooks;
import com.company.bookservice.entity.compositeids.UserIdBookIdKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WantToReadBooksRepository extends JpaRepository<WantToReadBooks, UserIdBookIdKey> {
    
}