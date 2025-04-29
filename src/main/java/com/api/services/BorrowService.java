package com.api.services;

import java.util.List;

import com.api.dto.BorrowedBookDTO;

public interface BorrowService {

	String borrowBook(int pid, int bookId);
	String returnBook(int pid, int bookId);
	List<BorrowedBookDTO> getBooksBorrowedByPatron(int pid) throws Exception;
    
}