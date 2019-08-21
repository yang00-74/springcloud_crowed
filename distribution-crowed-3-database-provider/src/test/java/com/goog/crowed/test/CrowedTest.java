package com.goog.crowed.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CrowedTest {
	
	@Autowired
	private DataSource ds;
	
	@Test
	public void testDataConnection() throws SQLException {
		Connection c = ds.getConnection();
		System.out.println(c);
	}

}
