package com.rest.RestAPI.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "autors")
public class AuthorEntity extends BaseEntity{

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "author")
	private List<BookEntity> books;

	public AuthorEntity() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(List<BookEntity> books) {
		this.books = books;
	}

}
