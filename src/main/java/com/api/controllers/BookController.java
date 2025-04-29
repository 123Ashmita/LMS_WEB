package com.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.api.entities.Book;
import com.api.services.BookService;

@Controller
//@RequestMapping("/book")  
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
    	 Book book = new Book();
    	    book.setQuantity(1);
        model.addAttribute("book", new Book());
        return "add-book";  
    }

    @PostMapping("/addbook")
    public String addBook(Book book, Model model) {
       
        if (bookService.existsByBookId(book.getBookId())) {
            model.addAttribute("message", "Book with ID " + book.getBookId() + " already exists.");
            return "add-book";  
        }
        bookService.addBook(book);
        return "redirect:/book"; 
    }

    @GetMapping("/book")
    public String listBooks(Model model) {
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books); 
        return "book-list";  
    }

    @PostMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable int bookId, RedirectAttributes redirectAttributes) {
    	try {
            bookService.deleteBook(bookId);
            redirectAttributes.addFlashAttribute("message", "Book with ID " + bookId + " deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "can't delete because patron has borrowed book");
        }
        return "redirect:/book";
    }
}
