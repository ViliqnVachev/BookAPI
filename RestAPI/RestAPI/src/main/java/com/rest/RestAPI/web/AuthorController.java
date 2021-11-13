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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rest.RestAPI.model.dto.AuthorDTO;
import com.rest.RestAPI.service.impl.AuthorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/authors")
@CrossOrigin
@Api(value = "authors", description = "REST API for Authors", tags = { "authors" })
public class AuthorController {

	private final AuthorService authorService;

	@Autowired
	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping("/all")
	@ApiOperation(value = "Find all Authors", notes = "Find all authors ", response = AuthorDTO.class)
	public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
		List<AuthorDTO> authors = authorService.getAllAuthors();
		return ResponseEntity.ok(authors);

	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Find author by id", notes = "Find author by id", response = AuthorDTO.class)
	public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
		Optional<AuthorDTO> author = authorService.getAuthorById(id);
		if (author.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(author.get());

	}

	@PostMapping
	@ApiOperation(value = "Create an author", notes = "Create an author", response = AuthorDTO.class)
	public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO author, UriComponentsBuilder builder) {
		Long id = authorService.createAuthor(author);

		URI location = builder.path("/authors/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete an author", notes = "Delete an author and his books", response = AuthorDTO.class)
	public ResponseEntity<AuthorDTO> deleteAuthor(@PathVariable Long id) {
		boolean isDeleted = authorService.deleteAuthor(id);

		return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

	}
}
