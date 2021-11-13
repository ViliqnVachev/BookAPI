package com.rest.RestAPI.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.RestAPI.model.dto.AuthorDTO;
import com.rest.RestAPI.model.dto.BookDTO;
import com.rest.RestAPI.model.entity.AuthorEntity;
import com.rest.RestAPI.model.entity.BookEntity;
import com.rest.RestAPI.repository.AuthorRepository;
import com.rest.RestAPI.repository.BookRepository;
import com.rest.RestAPI.service.IBookService;

@Service
public class BookService implements IBookService {

	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final ModelMapper mapper;

	@Autowired
	public BookService(BookRepository bookRepository, ModelMapper mapper, AuthorRepository authorRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.mapper = mapper;
	}

	@Override
	public List<BookDTO> getAllBooks() {
		return bookRepository.findAll().stream().map(entity -> convertToDTO(entity)).collect(Collectors.toList());
	}

	@Override
	public Optional<BookDTO> getBookById(Long id) {
		return bookRepository.findById(id).map(this::convertToDTO);
	}

	@Override
	public boolean deleteBook(Long id) {
		Optional<BookEntity> book = bookRepository.findById(id);
		if (book.isEmpty()) {
			return false;
		}
		bookRepository.delete(book.get());
		return true;
	}

	@Override
	public Long createBook(BookDTO book) {
		AuthorEntity authorEntity = null;
		Optional<AuthorEntity> author = authorRepository.findByName(book.getAuthor().getName());
		if (author.isEmpty()) {
			authorEntity = new AuthorEntity();
			authorEntity.setName(book.getAuthor().getName());
		} else {
			authorEntity = author.get();
		}

		authorRepository.save(authorEntity);

		BookEntity bookEntity = new BookEntity();
		bookEntity.setAuthor(authorEntity);
		bookEntity.setIsbn(book.getIsbn());
		bookEntity.setTitle(book.getTitle());

		return bookRepository.save(bookEntity).getId();
	}

	@Override
	public Long updateBookById(BookDTO book) {
		BookEntity bookEntity = bookRepository.findById(book.getId()).orElse(null);
		if (bookEntity == null) {
			return null;
		}
		AuthorEntity authorEntity = null;
		Optional<AuthorEntity> author = authorRepository.findByName(book.getAuthor().getName());
		if (author.isEmpty()) {
			authorEntity = new AuthorEntity();
			authorEntity.setName(book.getAuthor().getName());
		} else {
			authorEntity = author.get();
		}
		authorRepository.save(authorEntity);

		bookEntity.setAuthor(authorEntity);
		bookEntity.setIsbn(book.getIsbn());
		bookEntity.setTitle(book.getTitle());

		return bookRepository.save(bookEntity).getId();
	}
	

	private BookDTO convertToDTO(BookEntity bookEntity) {
		BookDTO book = mapper.map(bookEntity, BookDTO.class);
		AuthorDTO author = mapper.map(bookEntity.getAuthor(), AuthorDTO.class);
		book.setAuthor(author);

		return book;
	}

}
