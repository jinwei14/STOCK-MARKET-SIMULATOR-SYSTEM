package main;

/**
 * the query processor can translate the queries which are generated by the user
 * into transaction languages.
 * 
 * For example, the user chooses a stock for the transaction, the query will be
 * passed to the query processor and translated into SQL language, and the
 * database will understand what data is requested and send it to the user
 * 
 * @param <E>
 * */
public interface queryProcessor<E> {
	/**
	 * 
	 * once user need to add personal information in their account processor
	 * need to put the user entry in the database
	 */
	boolean addPersonalInformation();

	/**
	 * if user want to change some personal information, after the changing, all
	 * the personal information should be stored in the database. Make sure all
	 * the table which store personal information is updated //whenever the data
	 * is changed.
	 * 
	 * @return
	 */
	boolean changePersonalInformation();

	/**
	 * once user request for a stock information, the graphic generator should
	 * request to the database traverse the database then return related stock
	 * data.
	 * 
	 * @return
	 */
	boolean viewPersonalInformation();

	/**
	 * if user delete some personal information like data of birth. The related
	 * table in database should also change.
	 * 
	 * @return
	 */
	boolean deletePersonalInformation();

	/**
	 * translate the queries which are generated by the user into transaction
	 * languages.
	 * 
	 * @return
	 * */
	public E translateQueries(Object o);
	/**
	 * pass query to the database
	 * 
	 * */
	void passQuery();
}