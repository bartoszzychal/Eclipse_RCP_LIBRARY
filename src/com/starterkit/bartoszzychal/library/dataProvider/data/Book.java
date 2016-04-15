package com.starterkit.bartoszzychal.library.dataProvider.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.starterkit.bartoszzychal.library.model.ModelObject;

public class Book extends ModelObject {
	
	private Long id;
	private String title;
	private String authors;

	@JsonCreator
	public Book(@JsonProperty(defaultValue = "null", value="id") Long id, @JsonProperty("title") String title,
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
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", authors=" + authors + "]";
	}
}
