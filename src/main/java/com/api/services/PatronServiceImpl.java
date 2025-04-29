package com.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.dao.PatronRepository;
import com.api.entities.Patron;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PatronServiceImpl implements PatronService {

	@Autowired
	private PatronRepository patronRepository;
	public Patron addPatron(Patron patron) {
		return patronRepository.save(patron);
	}
	public List<Patron> getList() {
		return patronRepository.findAll();
	}

	public void deletePatron(int pid) {
		if(patronRepository.existsById(pid)) {
		patronRepository.deleteById(pid);
	} else {
		 throw new EntityNotFoundException("Patron with ID " + pid + " not found");	
	}
	}
	public boolean existByPatronId(int pid) {
		return patronRepository.existsById(pid);
	}	
}