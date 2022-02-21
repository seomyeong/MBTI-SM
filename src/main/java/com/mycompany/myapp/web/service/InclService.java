package com.mycompany.myapp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.web.dao.InclDao;

@Service
public class InclService {
	@Autowired
	InclDao inclDao;
}
