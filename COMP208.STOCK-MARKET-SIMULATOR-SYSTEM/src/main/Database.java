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
public interface Database<E> {
	/**
	 * delete information
	 * 
	 * */
	boolean deleteRecord(Object o);

	/**
	 * change the record including stock information personal information and
	 * user login information
	 * */

	 boolean changeRecord(E e);

	/**
	 * add the record in the database
	 * @return
	 * */

	boolean addRecord(E e);

	/**
	 * retrieve the data in the database sql Server
	 * 
	 * */

	public E retrieveRecord();
	
	/**
     * Returns the number of record in this database.  
     *
     * @return the number of record in this database
     */
    int size();
    /**
     * Returns <tt>true</tt> if this database contains no record.
     *
     * @return <tt>true</tt> if this database contains no record
     */
    boolean isEmpty();
    /**
     * Returns <tt>true</tt> if this database contains the specified record.
     * More formally, returns <tt>true</tt> if and only if this list contains
     * at least one record <tt>e</tt> 
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     * @throws ClassCastException if the type of the specified record
     *         is incompatible with this database
     * @throws NullPointerException if the specified record is null and this
     *         list does not permit null record
     */
    boolean contains(Object o);
    
    
}
