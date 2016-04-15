package com.starterkit.bartoszzychal.restConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import com.starterkit.bartoszzychal.library.dataProvider.data.Book;
import com.starterkit.bartoszzychal.restConnection.mapper.Mapper;

public class RestConnection {

	private final String FIND_BOOKS_URL = "http://localhost:9721/workshop/books-by?title=&authors=";
	private final String ADD_BOOK_URL = "http://localhost:9721/workshop/book?action=save";
	private final Logger LOG = Logger.getLogger(RestConnection.class.getName());
	
	public Book addNewBook(Book book) {
		HttpURLConnection connection = null;
		Book responseBook = null;
		try {
			connection = prepareConnection(new URL(ADD_BOOK_URL), "POST");
			String mapJson = Mapper.<Book>map(book);
			postJSON(mapJson, connection);
			LOG.info("POST JSON: "+ mapJson);
			if(checkResponse(connection)){
				String json = getJSON(connection);
				LOG.info("GET JSON:" + json);
				responseBook = Mapper.map(json,Book.class);
				LOG.info("JSON->OBJECTS: "+responseBook.toString());
			}else{
				LOG.warning("OBJECT NOT RESPONSE ");
			}
		} catch (IOException e) {
			LOG.warning(e.getMessage());
		} finally {
			connection.disconnect();
		}
		return responseBook;
	}
	
	public List<Book> getBooks(){
		HttpURLConnection connection = null;
		List<Book> books = null;
		try {
			connection = prepareConnection(new URL(FIND_BOOKS_URL), "GET");
			String json = getJSON(connection);
			LOG.info("GET JSON: " + json);
			books = Mapper.map2List(json, Book.class);
			LOG.info("JSON->OBJECTS: " + books);
		} catch (IOException e) {
			LOG.warning(e.getMessage());
		} finally {
			connection.disconnect();
		}
		return books;
	}

	private HttpURLConnection prepareConnection(URL url, String requestMethod) throws IOException, ProtocolException {
		HttpURLConnection connection;
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestMethod(requestMethod);
		if("POST".equals(requestMethod)){
			connection.setDoOutput(true);			
		}else if("GET".equals(requestMethod)){
			connection.setDoInput(true);			
		}
		return connection;
	}

	private void postJSON(String json, HttpURLConnection connection) throws IOException {
		try(OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream())){
			output.write(json);			
		}
	}
	private String getJSON( HttpURLConnection connection) throws UnsupportedEncodingException, IOException {
		String response = null;
		try(InputStreamReader input = new InputStreamReader(connection.getInputStream(),"utf-8")){
			response = getResponseMassage(input);			
		}
		return response;
	}

	private Boolean checkResponse(HttpURLConnection connection) throws IOException, UnsupportedEncodingException {
		int HttpResult = connection.getResponseCode();
		boolean response = false;
		if (HttpResult == HttpURLConnection.HTTP_OK) {
			response = true;
			LOG.info("HTTP RESPONSE" + HttpURLConnection.HTTP_OK);
		} else {
			response = false;
			LOG.warning("HTTP RESPONSE" + HttpURLConnection.HTTP_NO_CONTENT);
		}
		return response;
	}
	
	private String getResponseMassage(InputStreamReader input){
		StringBuilder sb = new StringBuilder();
		try(BufferedReader br = new BufferedReader(input)){
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			LOG.warning(e.getMessage());
		}
		return sb.toString();
	}

}
