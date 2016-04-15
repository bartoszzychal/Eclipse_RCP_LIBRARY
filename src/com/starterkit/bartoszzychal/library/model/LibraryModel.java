package com.starterkit.bartoszzychal.library.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;

import com.starterkit.bartoszzychal.library.dataProvider.DataProvider;
import com.starterkit.bartoszzychal.library.dataProvider.data.Book;

public class LibraryModel  {
	
	public final static LibraryModel INSTANCE = new LibraryModel();
	private final static List<Book> books = new ArrayList<>();
	private final static WritableList writableList = new WritableList(books,Book.class);
	private final DataProvider dataProvider = new DataProvider();
	
	private LibraryModel(){	
	}
	
	public void update(){
		writableList.clear();
		writableList.addAll(dataProvider.allBook());
	}

	public WritableList getBooks() {
		return writableList;
	}
	
	public void addNewBook(Book book){
		dataProvider.addNewBook(book);
		update();
	}
		
}
