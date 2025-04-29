package com.api.services;

import com.api.entities.Book;

import com.api.entities.Borrow;
import com.api.entities.Patron;
import com.api.dao.BookRepository;
import com.api.dao.BorrowRepository;
import com.api.dao.PatronRepository;
import com.api.dto.BorrowedBookDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;
   
    @Override
    public String borrowBook(int pid, int bookId) {
        Patron patron = patronRepository.findById(pid).orElse(null); 
        
        if (patron == null) {
            return "Patron not found";
        }

        Book book = bookRepository.findById(bookId).orElse(null); 
        if (book == null || book.getQuantity() == 0) {
            return "Book not available";
        }
        Borrow borrow = new Borrow();
        borrow.setPatron(patron);
        borrow.setBook(book);
        borrowRepository.save(borrow);
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        return "Book borrowed successfully!";
    }
    public String returnBook(int pid, int bookId) {
        Patron patron = patronRepository.findById(pid).orElseThrow(() -> new EntityNotFoundException("Patron not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found"));

        List<Borrow> borrows = borrowRepository.findByBookAndPatron(book, patron);
        if (borrows.isEmpty()) {
            throw new IllegalStateException("This book was not borrowed by this patron with ID: " + pid);
        }
        Borrow borrow=borrows.get(0);
        borrowRepository.delete(borrow);
 
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book); 

        return "Book returned successfully by Patron ID " + patron.getPid();
    }

    public List<BorrowedBookDTO> getBooksBorrowedByPatron(int pid) throws Exception {
    	Patron patron = patronRepository.findById(pid).orElse(null); 
        if (patron == null) {
        	throw new Exception("Patron with ID " + pid + " does not exist.");
        }
    	
        List<Borrow> borrows = borrowRepository.findByPatronPid(pid);
        Map<Integer, BorrowedBookDTO> bookMap = new HashMap<>();

        for (Borrow borrow : borrows) {
            Book book = borrow.getBook();
            int bookId = book.getBookId();
            if (bookMap.containsKey(bookId)) {
                BorrowedBookDTO dto = bookMap.get(bookId);
                dto.setQuantity(dto.getQuantity() + 1);
            } else {
                bookMap.put(bookId, new BorrowedBookDTO(
                    bookId,
                    book.getTitle(),
                    book.getAuthor(),
                    1
                ));
            }
        }
        return new ArrayList<>(bookMap.values());
    }


}