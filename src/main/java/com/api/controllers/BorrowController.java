package com.api.controllers;

import com.api.dto.BorrowedBookDTO;

import com.api.entities.Borrow;
import com.api.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manage")
public class BorrowController {

    private final BookController bookController;

    @Autowired
    private BorrowService borrowService;

    BorrowController(BookController bookController) {
        this.bookController = bookController;
    }
    
    @GetMapping("")
    public String borrowPage() {
        return "borrow";
      }

    @GetMapping("/borrow")
    public String showBorrowBookForm(Model model) {
        model.addAttribute("borrow", new Borrow());
        return "borrow-book"; 
    }

    @PostMapping("/borrowBook")
    public String borrowBook(Borrow borrow, Model model) {
    	System.out.print(borrow.getPatron().getPid());
        try {
        	
            String response = borrowService.borrowBook(
                borrow.getPatron().getPid(), 
                borrow.getBook().getBookId()
            );
            model.addAttribute("message", response);
        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        
        return "borrow-book"; 
    }

    @GetMapping("/return")
    public String showReturnBookForm(Model model) {
        model.addAttribute("borrow", new Borrow());
        return "return-book"; 
    }

    @PostMapping("/returnBook")
    public String returnBook(@ModelAttribute("borrow") Borrow borrow, Model model) {
        try {
            int pid = borrow.getPatron().getPid();  
            int bookId = borrow.getBook().getBookId();  
            if (pid == 0 || bookId == 0) {
                model.addAttribute("message", "Error: Patron ID and Book ID are required. can't be 0");
                return "return-book";  
            }
            String response = borrowService.returnBook(pid, bookId);
            model.addAttribute("message", response);
        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }

        return "return-book";
    
    }

    @GetMapping("/checkborrowedbooks")
    public String showCheckBorrowedBooksForm(Model model) {
        model.addAttribute("borrow", new Borrow());
        return "check-borrowed-books";
    }

    @PostMapping("/list")
    public String listBorrowedBooks(@ModelAttribute("borrow") Borrow borrow, Model model) {
        try {
        
            int pid = borrow.getPatron().getPid();
            if (pid == 0) {
                model.addAttribute("message", "OOPS Sorry !!! Patron ID is required.");
                return "check-borrowed-books";
            }

            List<BorrowedBookDTO> borrowedBooks = borrowService.getBooksBorrowedByPatron(pid);

            if (borrowedBooks.isEmpty()) {
                model.addAttribute("message", "No books borrowed.");
            } else {
                model.addAttribute("borrowedBooks", borrowedBooks);
            }
        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }

        return "check-borrowed-books";
    }
}
