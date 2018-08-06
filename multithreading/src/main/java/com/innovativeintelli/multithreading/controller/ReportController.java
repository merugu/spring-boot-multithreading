package com.innovativeintelli.multithreading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innovativeintelli.multithreading.service.ThreadExecutorService;

@RestController
@RequestMapping("/api/generatereports")
public class ReportController {
	
	@Autowired
	ThreadExecutorService threadExecutorService;
	
	@Async
	@PutMapping("/startjob")
	ResponseEntity<?> generateReports() {
		threadExecutorService.generateReportsAsync();
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
