package Main;

import java.util.List;

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
public interface queryProcessor {
	/**
	 * 
	 * once user need to add personal information in their account processor
	 * need to put the user entry in the database
	 */
	boolean addPersonalInformation(String username, String password, String discription);

	/**
	 * if user want to change some personal information, after the changing, all
	 * the personal information should be stored in the database. Make sure all
	 * the table which store personal information is updated //whenever the data
	 * is changed.
	 * 
	 * @return
	 */
	boolean changePersonalUsername(int user_id, String username);
	boolean changePersonalPassword(int user_id, String password);
	boolean changePersonalInformation(int user_id, String username, String password);
	boolean changePersonalStock(int user_id, int Stock_id, int share_num);
	

	/**
	 * once user request for a stock information, the graphic generator should
	 * request to the database traverse the database then return related stock
	 * data.
	 * 
	 * @return
	 */
	List<String> viewPersonalInformation(int user_id);
	List<List<String>> viewPersonalStock(int user_id);

	/**
	 * if user delete some personal information like data of birth. The related
	 * table inv database should also change.
	 * 
	 * @return
	 */
	boolean deletePersonalInformation();
	
	/**
	 * the user buy the assigned stock
	 * @param user_id who buies the stock
	 * @param stock_id which stock is chosen
	 * @param share_num how many 
	 * @param price which price does the user bought
	 * @return
	 */
	boolean buyStock(int user_id, String stock_id, int share_num, float price);
	
	/**
	 * the user wants to sell the assigned stock
	 * @param user_id who
	 * @param stock_id which stock
	 * @param share_num how many does the user want to sell
	 * @param price the selling price
	 * @return
	 */
	double sellStock(int user_id, String stock_id, int share_num, float price);
}
