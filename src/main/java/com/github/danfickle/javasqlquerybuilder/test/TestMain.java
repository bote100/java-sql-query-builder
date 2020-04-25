package com.github.danfickle.javasqlquerybuilder.test;

import java.sql.SQLException;

/**
 * Please make sure you have assertions enabled to run these tests.
 * @author DanFickle
 */
public class TestMain
{
	public static void main(String... args) throws SQLException, ClassNotFoundException
	{
		Sample.sample();
		System.out.println("All tests passed.");
	}
}
