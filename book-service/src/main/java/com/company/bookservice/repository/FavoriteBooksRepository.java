package com.company.bookservice.repository;

import java.util.List;
import java.util.Optional;

import com.company.bookservice.entity.FavoriteBooks;
import com.company.bookservice.entity.compositeids.UserIdBookIdKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteBooksRepository extends JpaRepository<FavoriteBooks, UserIdBookIdKey> {

	Optional<List<FavoriteBooks>> findAllByUserId(Long userId);
}