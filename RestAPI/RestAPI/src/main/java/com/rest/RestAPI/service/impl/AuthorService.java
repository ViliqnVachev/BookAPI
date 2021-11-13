package com.rest.RestAPI.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.RestAPI.model.dto.AuthorDTO;
import com.rest.RestAPI.model.entity.AuthorEntity;
import com.rest.RestAPI.model.entity.BookEntity;
import com.rest.RestAPI.repository.AuthorRepository;
import com.rest.RestAPI.repository.BookRepository;
import com.rest.RestAPI.service.IAuthorService;

@Service
public class AuthorService implements IAuthorService {

	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;
	private final ModelMapper mapper;

	@Autowired
	public AuthorService(AuthorRepository authorRepository, ModelMapper mapper, BookRepository bookRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
		this.mapper = mapper;
	}

	@Override
	public List<AuthorDTO> getAllAuthors() {
		return authorRepository.findAll().stream().map(this::convertToAutorDTO).collect(Collectors.toList());

	}

	@Override
	public Long createAuthor(AuthorDTO author) {
		AuthorEntity entity = mapper.map(author, AuthorEntity.class);
		return authorRepository.save(entity).getId();
	}

	@Override
	public Optional<AuthorDTO> getAuthorById(Long id) {
		return authorRepository.findById(id).map(this::convertToAutorDTO);
	}

	@Override
	public boolean deleteAuthor(Long id) {
		Optional<AuthorEntity> author = authorRepository.findById(id);
		if (author.isEmpty()) {
			return false;
		}

		List<BookEntity> books = bookRepository.findByAuthor(author.get());

		if (!books.isEmpty()) {
			books.stream().forEach(book -> bookRepository.delete(book));
		}
		authorRepository.delete(author.get());
		return true;
	}

	private AuthorDTO convertToAutorDTO(AuthorEntity authorEntity) {
		AuthorDTO authorDTO = mapper.map(authorEntity, AuthorDTO.class);
		return authorDTO;
	}

}
