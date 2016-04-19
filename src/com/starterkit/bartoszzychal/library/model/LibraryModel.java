package com.starterkit.bartoszzychal.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.databinding.observable.list.WritableList;

import com.starterkit.bartoszzychal.library.dataProvider.DataProvider;
import com.starterkit.bartoszzychal.library.dataProvider.data.Book;

public class LibraryModel {

	
	public final static LibraryModel INSTANCE = new LibraryModel();
	private final List<Book> books = new ArrayList<>();
	private final WritableList writableBooksList = new WritableList(books, Book.class);
	private final Book addNewBook = new Book();
	private final Book bookDetails = new Book();
	private final DataProvider dataProvider = new DataProvider();

	private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

	private LibraryModel() {
	}

	public void update() {
		writableBooksList.clear();
		writableBooksList.addAll(dataProvider.allBook());
	}

	public void addNewBook() {
		LOG.info(addNewBook.toString());
		Book addedBook = dataProvider.addNewBook(addNewBook);
		clearAddNewBookModel();
		if (addedBook != null) {
			update();
		}
	}

	public void clearAddNewBookModel() {
		addNewBook.setAuthors("");
		addNewBook.setTitle("");
	}

	public WritableList getBooksModel() {
		return writableBooksList;
	}

	public Book getBookToAddModel() {
		return addNewBook;
	}

	public Book getBookDetailsModel() {
		return bookDetails;
	}

	public void setBookDetailsModel(Book bookDetails) {
		this.bookDetails.setId(bookDetails.getId());
		this.bookDetails.setTitle(bookDetails.getTitle());
		this.bookDetails.setAuthors(bookDetails.getAuthors());
	}
	
}
