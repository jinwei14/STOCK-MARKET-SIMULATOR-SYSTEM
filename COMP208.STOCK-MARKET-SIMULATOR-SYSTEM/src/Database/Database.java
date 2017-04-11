package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {
	
	private static final String url = "jdbc:mysql://127.0.0.1/StockInformation?autoReconnect=true&useSSL=false";
	private static final String name = "com.mysql.jdbc.Driver";
	private static final String user = "root";
	private static final String password = "root";
	
	private static Connection connect;
	private static PreparedStatement pst;
	private static ResultSet result;
	
	public static void startDatabase(){
		try {
			Class.forName(name); //signed the type of connection
			connect = DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * This method is used to create table in the database StockInformation
	 * @param sql represents the sql instruction that the invoker provide
	 * @return return the result of the sql instruction
	 * @throws SQLException if the connection is failed or the creation of the table is failed
	 */
	public static synchronized ArrayList<String> createTable(String sql) throws SQLException{
		ArrayList<String> sqlreturn = new ArrayList<String>();
		pst = connect.prepareStatement(sql);
		pst.execute();
		result.close();
		return sqlreturn;
	}
	
	/**
	 * close the connection with the database
	 */
	public static void close(){
		try{
			connect.close();
			pst.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * insert a new column in the assigned table
	 * @param sql the sql instruction provided by the invoker
	 * @throws SQLException
	 */
	public static synchronized void insert(String sql) throws SQLException {
		pst = connect.prepareStatement(sql);
		pst.execute();
		result.close();
	}
	
	/**
	 * verify whether the table exists or not
	 * @param tablename the assigned table 
	 * @return true or false
	 * @throws SQLException
	 */
	public static boolean tableExist(String tablename) throws SQLException{
		result = connect.getMetaData().getTables(null, null, tablename, null);
		if(result.next()){
			return true;
		}
		return false;
	}
	
	/**
	 * drop the assigned table
	 * @param sql
	 * @throws SQLException
	 */
	public static synchronized void dropTable(String sql) throws SQLException{
		pst = connect.prepareStatement(sql);
		pst.execute();
	}
	
	public static synchronized boolean update(String sql) throws SQLException{
		pst = connect.prepareStatement(sql);
		int result = pst.executeUpdate(sql);
		return result == 1 ? true:false;
	}
	
	public static double[] queryStockShare(String sql) throws SQLException {
		pst = connect.prepareStatement(sql);
		result = pst.executeQuery(sql);
		double[] tmp = new double[2];
		if(result.next()){
			tmp[0] = Double.valueOf(result.getString(1));
			tmp[1] = Double.valueOf(result.getString(2));
		}else{
			tmp[0]=0;
			tmp[1]=0;
		}
		return tmp;
	}
	
	public static List<String> queryPersonInfo(String sql) throws SQLException{
		pst = connect.prepareStatement(sql);
		ArrayList<String> sqlreturn = new ArrayList<String>();
		result = pst.executeQuery(sql);
		for(int i=1; i<=5; i++){
			sqlreturn.add(result.getString(i));
		}
		return sqlreturn;
	}
	
	public static List<List<String>> queryPersonStock(String sql) throws SQLException{
		pst = connect.prepareStatement(sql);
		List<List<String>> sqlreturn = new ArrayList<List<String>>();
		result = pst.executeQuery();
		while(result.next()){
			List<String> stock = new ArrayList<String>();
			stock.add(result.getString(1));
			stock.add(result.getString(2));
			stock.add(result.getString(3));
			stock.add(result.getString(4));
			sqlreturn.add(stock);
		}
		return sqlreturn;
	}
	
	/**
	 * get the list of the stock code which are stored in the database
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getStockCode() throws SQLException {
		String sql = "select stock_id from Stock";
		ArrayList<String> sqlreturn = new ArrayList<String>();
		pst = connect.prepareStatement(sql);
		result = pst.executeQuery();
		while(result.next()){
			String sqlres = result.getString(1);
			sqlreturn.add(sqlres);
		}
		result.close();
		return sqlreturn;
	}
	
	/**
	 * get the list of the Historical price of the assigned stock.
	 * @param stockCode the assigned stock
	 * @return the list of the stock price which includes "high, low, open and close"
	 * @throws SQLException
	 */
	public static List<Float[]> getStockHisPrice(String stockCode) throws SQLException {
		String sql = "select high,low,open,close from " +stockCode;
		Float[] prices = new Float[4];
		List<Float[]> returnlist = new ArrayList<Float[]>();
		pst = connect.prepareStatement(sql);
		result = pst.executeQuery();
		while(result.next()){
			prices[0] = Float.valueOf(result.getString(1)); //1 represent high
			prices[1] = Float.valueOf(result.getString(2)); //2 represent low
			prices[2] = Float.valueOf(result.getString(3)); //3 represent open
			prices[3] = Float.valueOf(result.getString(4)); //4 represent close
			returnlist.add(prices);
		}
		return returnlist;
	}
	
}
