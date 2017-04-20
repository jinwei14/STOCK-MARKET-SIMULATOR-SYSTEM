package api;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.List;

/**
 * the query processor can translate the queries which are generated by the user
 * into transaction languages.
 * 
 * For example, the user chooses a stock for the transaction, the query will be
 * passed to the query processor and translated into SQL language, and the
 * database will understand what data is requested and send it to the user
 * 
 * */
public interface queryProcessor {
	/**
	 * 
	 * once user need to add personal information in their account processor
	 * need to put the user entry in the database
	 */
	int addPersonalInformation(String username, String password, String phone, String postcode, String bankcard);

	/**
	 * if user want to change some personal information, after the changing, all
	 * the personal information should be stored in the database. Make sure all
	 * the table which store personal information is updated //whenever the data
	 * is changed.
	 * 
	 * @return
	 */
	boolean changePersonalUsername(int user_id, String username);
	
	/**
	 * if the user want to change password
	 * @param user_id the user_id that user input
	 * @param password the new password
	 * @return whether the implementation is successful
	 */
	boolean changePersonalPassword(int user_id, String password);
	
	/**
	 * if the user wants to change username and password
	 * @param user_id user's unique id
	 * @param username the username that the username wants to change
	 * @param password 
	 * @return whether the implementation is successful
	 */
	boolean changePersonalInformation(int user_id, String username, String password);
	
	/**
	 * user wants to change share_num of the chosen stock
	 * @param user_id
	 * @param Stock_id
	 * @param share_num
	 * @return whether the implementation is successful 
	 */
	boolean changePersonalStock(int user_id, int Stock_id, int share_num);
	

	/**
	 * once user request for a stock information, the graphic generator should
	 * request to the database traverse the database then return related stock
	 * data.
	 * 
	 * @return a list contains user_id, username, password, surplus_fund, phone, postcode, bankcard
	 */
	List<String> viewPersonalInformation(int user_id);
	
	/**
	 * user wants to which stocks he has and the share number of each stock
	 * @param user_id
	 * @return user_id, stock_id, share_num, price
	 */
	List<List<String>> viewPersonalStock(int user_id);

	/**
	 * if user delete some personal information like data of birth. The related
	 * table inv database should also change.
	 * 
	 * @return
	 */
	boolean deletePersonalInformation();
	
	/**
	 * Get descend or ascend order of the pe ratio.
	 * @param order 0 -- ascend, 1 -- descend
	 * @return stock_id, pe
	 */
	List<List<String>> getPeOrder(int order);
	
	/**
	 * get all information of the chosen stock
	 * @param stock_id the chosen stock
	 * @return stock_id stock_name dailyDate stock_id lastTradePrice dividentYeild pe
	 */
	List<String> getStockAllInfo(String stock_id);
	/**
	 * the user buy the assigned stock
	 * @param user_id who buies the stock
	 * @param stock_id which stock is chosen
	 * @param share_num how many 
	 * @return whether the implementation is success
	 */
	boolean buyStock(int user_id, String stock_id, int share_num);
	
	/**
	 * user wants to sell the chosen stock
	 * @param user_id the user id
	 * @param stock_id the chosen stock
	 * @param share_num how many does the user want to sell
	 * @return the number of money that the user earned. -1-->exceed upbound -2-->sqlexception
	 * @throws SQLException
	 */
	double sellStock(int user_id, String stock_id, int share_num);
	
	/**
	 * user wants to top up his account
	 * @param user_id
	 * @param money
	 * @return whether the implementation is success
	 */
	boolean topup(int user_id, double money);
	/**
	 * Get the stock historical chart
	 * @param stock_id the chosen stock
	 * @return image object
	 */
	BufferedImage getStockHisChart(String stock_id);
	
	/**
	 * Get the prediction stock chart
	 * @param stock_id the chosen stock
	 * @param days how many days
	 * @return image object
	 */
	BufferedImage[] getPredictChart(String stock_id, int days);
	
	/**
	 * Get the chosen stock's verification chart
	 * @param stock_id the chosen stock
	 * @return image object
	 */
	BufferedImage getVeriChart(String stock_id);
	
	/**
	 * Get the chosen stock's daily chart
	 * @param stock_id the chosen stock, String type
	 * @return image object
	 */
	BufferedImage getDailyChart(String stock_id);
}
