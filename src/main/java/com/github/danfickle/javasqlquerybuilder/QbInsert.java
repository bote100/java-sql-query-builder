package com.github.danfickle.javasqlquerybuilder;

/**
 * An interface to generate INSERT queries.
 * @author DanFickle
 */
public interface QbInsert extends QbQuery
{
	/**
	 * Sets a database column placeholder.
	 * @param field
	 * @param placeholder
	 * @return This query builder.
	 */
	public QbInsert set(QbField field, String placeholder);

	/**
	 * Sets a database column placeholder.
	 * @param field
	 * @param placeholder
	 * @return This query builder.
	 */
	public QbInsert set(String field, String placeholder);

	/**
	 * Execute statement
	 */
	public void exec();

	/**
	 * Set value in string directly
	 * @param arg0 column name
	 * @param arg1 value to insert
	 * @return
	 */
	public QbInsert intelliSet(String arg0, Object arg1);
	
	/**
	 * Sets the table to insert into. Must be called to make a valid insert
	 * statement.
	 * @param table
	 * @return This query builder.
	 */
	public QbInsert inTable(String table); 
}