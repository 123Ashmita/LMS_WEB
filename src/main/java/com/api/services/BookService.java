package com.api.services;

import java.util.List;

import com.api.entities.Book;

public interface BookService {

	Book addBook(Book book);

	List<Book> getBooks();
	
	public void deleteBook(int bookId);

	boolean existsByBookId(int bookId);

	Book getBookById(int bookId);

	
	
}