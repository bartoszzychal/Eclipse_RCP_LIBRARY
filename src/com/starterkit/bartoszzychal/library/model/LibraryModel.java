package com.starterkit.bartoszzychal.library.model;

import java.util.ArrayList;
import java.util.List;

import com.starterkit.bartoszzychal.library.dataProvider.DataProvider;
import com.starterkit.bartoszzychal.library.dataProvider.data.Book;

public class LibraryModel {
	
	private DataProvider dataProvider;
	private List<Book> books = new ArrayList<>();

	public LibraryModel(DataProvider dataProvider){
		this.dataProvider = dataProvider;
	}
	
	public List<Book> getBooks(){
		return books;
	}
	
	public void update(){
		books.clear();
		books.addAll(dataProvider.allBook());
	}
	
	
}
