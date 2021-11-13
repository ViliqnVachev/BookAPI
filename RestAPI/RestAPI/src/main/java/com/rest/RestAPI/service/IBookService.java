package com.rest.RestAPI.service;

import java.util.List;
import java.util.Optional;

import com.rest.RestAPI.model.dto.BookDTO;

public interface IBookService {

	List<BookDTO> getAllBooks();

	Optional<BookDTO> getBookById(Long id);

	boolean deleteBook(Long id);

	Long createBook(BookDTO book);

	Long updateBookById(BookDTO book);

}
