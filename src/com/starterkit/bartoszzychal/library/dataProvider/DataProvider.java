package com.starterkit.bartoszzychal.library.dataProvider;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

import com.starterkit.bartoszzychal.library.dataProvider.data.Book;
import com.starterkit.bartoszzychal.restConnection.RestConnection;
import com.starterkit.bartoszzychal.restConnection.json.JSONRestConnection;

public class DataProvider{
	
	private RestConnection<Book> restConnection = new JSONRestConnection<Book>(Book.class);
	private final String ALL_BOOKS = "http://localhost:9721/workshop/books-by?title=&authors=";
	private final String ADD_BOOK = "http://localhost:9721/workshop/book?action=save";
	private final Logger LOG = Logger.getLogger(getClass().getSimpleName());

	public Book addNewBook(Book book){
		Book post = null;
		try {
			post = restConnection.postOne(new URL(ADD_BOOK), StandardCharsets.UTF_8 , book);
			LOG.info("SAVED: "+post.toString());
		} catch (Exception e) {
			LOG.warning(e.getMessage());
		}
		return post;
	}

	public List<Book> allBook(){
		List<Book> list = null;
		try {
			list = restConnection.getList(new URL(ALL_BOOKS), StandardCharsets.UTF_8);
			list.stream().forEach((book) -> LOG.info(book.toString()));
		} catch (Exception e) {
			LOG.warning(e.getMessage());
		}
		return list;
	}
}
