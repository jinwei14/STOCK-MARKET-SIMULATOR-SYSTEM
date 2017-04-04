package main;

/**
 * The database is used for storing the crawled data in a suitable form.
 * 
 * Firstly, every stock will have a stock id for identification, and all of this
 * id will be stored in a table as an index. Secondly, each stock will generate
 * a table for storing historical data ordered by the date time. Thirdly, each
 * stock will hold a table for storing daily data for updating in real time.
 * Fourthly, all registered user will be stored in a User table
 * 
 * */
public interface Database {
	/**
	 * delete information
	 * 
	 * */

	public void deleteRecord();
	
	/**
	 * change the record including stock information
	 * personal information and user login information
	 * */
	
	public void changeRecord();
	
	/**
	 * add the record in the database
	 * 
	 * */
	
	public void addRecord();
	
	/**
	 * 
	 * 
	 * */
	
	public void retrieveRecord();
}
