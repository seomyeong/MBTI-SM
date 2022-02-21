package com.mycompany.myapp.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InclDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
}
