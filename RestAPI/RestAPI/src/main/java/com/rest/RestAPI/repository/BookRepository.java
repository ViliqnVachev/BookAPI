package com.rest.RestAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.RestAPI.model.entity.AuthorEntity;
import com.rest.RestAPI.model.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
	List<BookEntity> findByAuthor(AuthorEntity author);
}
