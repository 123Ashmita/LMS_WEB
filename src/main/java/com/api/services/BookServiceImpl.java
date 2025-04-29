package com.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.dao.BookRepository;
import com.api.entities.Book;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookServiceImpl implements BookService {

	
	@Autowired
	private BookRepository bookRepository;

	public Book addBook(Book book) {
		return bookRepository.save(book) ;
	}
	public List<Book> getBooks() {
		return bookRepository.findAll() ;
	}

	public void deleteBook(int bookId) {
	    if (bookRepository.existsById(bookId)) {
	        bookRepository.deleteById(bookId);
	    } else {
	        throw new EntityNotFoundException("Book with ID " + bookId + " not Exist");
	    }
	}

	public boolean existsByBookId(int bookId) {
		 return bookRepository.existsById(bookId);
	}
	
	public Book getBookById(int bookId) {
		return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found"));
	}
		
}