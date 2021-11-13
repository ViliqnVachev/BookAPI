package com.rest.RestAPI.service;

import java.util.List;
import java.util.Optional;

import com.rest.RestAPI.model.dto.AuthorDTO;

public interface IAuthorService {

	List<AuthorDTO> getAllAuthors();

	Long createAuthor(AuthorDTO author);

	Optional<AuthorDTO> getAuthorById(Long id);

	boolean deleteAuthor(Long id);

}
