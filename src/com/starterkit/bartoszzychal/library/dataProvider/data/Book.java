package com.starterkit.bartoszzychal.library.dataProvider.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.starterkit.bartoszzychal.library.model.ModelObject;

public class Book extends ModelObject {

	private Long id;
	private String title;
	private String authors;

	@JsonCreator
	public Book(@JsonInclude(Include.NON_NULL)
				@JsonProperty("id") Long id,
				@JsonProperty("title") String title, 
				@JsonProperty("authors") String authors) {
		this.id = id;
		this.title = title;
		this.authors = authors;
	}
	
	public Book(){
		
	}
	
	public Long getId() {
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
	
	public void setTitle(String title){
		firePropertyChange("title", this.title, this.title = title);
	}
	public void setAuthors(String authors){
		firePropertyChange("authors", this.authors, this.authors = authors);
	}

	public void setId(Long id) {
		firePropertyChange("id", this.id, this.id = id);	
	}
}
