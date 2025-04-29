package com.api.entities;

import jakarta.persistence.*;


@Entity
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private Patron patron;
    
    @Column(name = "name")
    private String patronName; 

    public Borrow() {}

    public Borrow(Book book, Patron patron) {
        this.book = book;
        this.patron = patron;
        this.patronName = patron.getName(); 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
        this.patronName=patron.getName();
    }
}