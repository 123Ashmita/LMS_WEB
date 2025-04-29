package com.api.services;

import java.util.List;

import com.api.entities.Patron;

public interface PatronService {

	Patron addPatron(Patron patron);

	List<Patron> getList();
	public void deletePatron(int pid);

	boolean existByPatronId(int pid);
}