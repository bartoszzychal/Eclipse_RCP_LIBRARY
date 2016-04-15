package com.starterkit.bartoszzychal.library.dataProvider.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Book {

	private long id;

	private String title;

	private String authors;

	@JsonCreator
	public Book(@JsonProperty("id") long id, @JsonProperty("title") String title,
			@JsonProperty("authors") String authors) {
		this.id = id;
		this.title = title;
		this.authors = authors;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthors() {
		return authors;
	}

}
