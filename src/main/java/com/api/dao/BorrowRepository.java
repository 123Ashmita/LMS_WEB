package com.api.dao;

import com.api.entities.Book;
import com.api.entities.Borrow;
import com.api.entities.Patron;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

	List<Borrow> findByBookAndPatron(Book book, Patron patron);
	
//	List<Borrow> findByPatronPid(int pid)

	List<Borrow> findByPatronPid(int pid);

}