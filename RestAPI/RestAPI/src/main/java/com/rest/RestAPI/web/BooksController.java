package com.rest.RestAPI.web;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rest.RestAPI.model.dto.BookDTO;
import com.rest.RestAPI.service.impl.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/books")
@CrossOrigin
@Api(value = "books", description = "REST API for Books", tags = { "books" })
public class BooksController {

	private final BookService bookService;

	@Autowired
	public BooksController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/all")
	@ApiOperation(value = "Find all Books", notes = "Find all books and their authors", response = BookDTO.class)
	public ResponseEntity<List<BookDTO>> getAllBooks() {
		List<BookDTO> books = bookService.getAllBooks();

		return ResponseEntity.ok(books);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Find book by id", notes = "Find book by id", response = BookDTO.class)
	public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
		Optional<BookDTO> book = bookService.getBookById(id);

		if (book.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(book.get());
	}

	@PostMapping
	@ApiOperation(value = "Create a book", notes = "Create a book and author", response = BookDTO.class)
	public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO book, UriComponentsBuilder builder) {
		Long id = bookService.createBook(book);

		URI location = builder.path("/books/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update a book", notes = "Update a book and their author", response = BookDTO.class)
	public ResponseEntity<BookDTO> updateBookById(@PathVariable Long id, @Valid @RequestBody BookDTO book) {
		book.setId(id);
		Long updatedId = bookService.updateBookById(book);
		return updatedId == null ? ResponseEntity.notFound().build() : ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a book by id", notes = "Delete a book by id", response = BookDTO.class)
	public ResponseEntity<BookDTO> deleteBook(@PathVariable Long id) {
		boolean isDeleted = bookService.deleteBook(id);

		if (!isDeleted) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

}
