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
	
	private Connection connect;
	private PreparedStatement pst;
	private ResultSet result;
	
	public Database(){
		try {
			Class.forName(name); //signed the type of connection
			connect = DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public ArrayList<String> createTable(String sql) throws SQLException{
		ArrayList<String> sqlreturn = new ArrayList<String>();
		pst = connect.prepareStatement(sql);
		pst.execute();
		result.close();
		return sqlreturn;
	}
	
	public void close(){
		try{
			this.connect.close();
			this.pst.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	public void insert(String sql) throws SQLException {
		pst = connect.prepareStatement(sql);
		pst.execute();
		result.close();
	}
	
	public List<String> getStockCode() throws SQLException {
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
	
	public List<Float[]> getStockHisPrice(String stockCode) throws SQLException {
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
	
	public boolean tableExist(String tablename) throws SQLException{
		result = connect.getMetaData().getTables(null, null, tablename, null);
		if(result.next()){
			return true;
		}
		return false;
	}
	
	public void dropTable(String sql) throws SQLException{
		pst = connect.prepareStatement(sql);
		pst.execute();
	}
}
