package Server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import Database.Database;

public class QueryExecutor{
	
	private static int userId = 0;
	
	public QueryExecutor(){}
	
	public boolean addPersonalInformation(String username, String password, String discription) {
		userId ++;
		username = encode(username);
		password = decode(password);
		String sql = "insert into User values ('"+userId+"','"+username+"','"+password+
						"','0'"+discription+"')";
		try {
			Database.insert(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean changePersonalUsername(int user_id, String username) {
		username = encode(username);
		String sql = "update User set username="+username+" where user_id="+user_id;
		try {
			return Database.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean changePersonalPassword(int user_id, String password) {
		password = encode(password);
		String sql = "update User set password="+password+" where user_id="+user_id;
		try {
			return Database.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean changePersonalInformation(int user_id, String username, String password) {
		username = encode(username);
		password = decode(password);
		String sql = "update User set username="+username+", password="+password+" where user_id="+user_id;
		try {
			return Database.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean changePersonalStock(int user_id, int Stock_id, int share_num) {
		String sql = "update User set share_num="+share_num+" where user_id="+user_id+
				" and stock_id="+Stock_id;
		try {
			return Database.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<String> viewPersonalInformation(int user_id) {
		List<String> result = new ArrayList<String>();
		String sql = "select * from User where user_id="+user_id;
		try {
			result = Database.queryPersonInfo(sql);
			result.set(1, decode(result.get(1))); //decode username
			result.set(2, decode(result.get(2))); //decode password
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<List<String>> viewPersonalStock(int user_id) {
		List<List<String>> result = new ArrayList<List<String>>();
		String sql = "select * from HoldStock where user_id="+user_id;
		try {
			result = Database.queryPersonStock(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean deletePersonalInformation() {
		return false;
	}
	
	public boolean buyStock(int user_id, String stock_id, int share_num, float price) throws SQLException {
		String sql1 = "select share_num, price from holdstock where exists ("+
				"select user_id from holdstock where user_id="+user_id+" and stock_id='"+stock_id+"')";
		double[] userShare = Database.queryStockShare(sql1);
		price = (float) ((userShare[0]*userShare[1]+share_num*price)/(share_num+userShare[1]));
		share_num = (int)userShare[0]+share_num;
		
		String sql2 = "insert into holdstock values ('"+user_id+"','"+stock_id+"','"+share_num+"','"+price+"')"+
					"on duplicate key update share_num = share_num+"+share_num+
					",price="+price;
		return Database.update(sql2);
	}
	
	public double sellStock(int user_id, String stock_id, int share_num, float price) throws SQLException {
		String sql1 = "select share_num from stockhold where user_id="+user_id+",stock_id="+stock_id;
		double[] userstock = Database.queryStockShare(sql1);
		int sell_num = share_num;
		if(share_num>userstock[0]){
			return -1;
		}else{
			share_num = (int)userstock[0]-share_num;
		}
		String sql2 = "update holdstock set share_num="+share_num+" where user_id="+user_id+" and stock_id="+stock_id;
		Database.update(sql2);
		return (price-userstock[1])*sell_num;
	}

	
	private String encode(String item){
		byte[] tmp = item.getBytes();
		Base64 encode = new Base64();
		tmp = encode.encode(tmp);
		return new String(tmp);
	}
	
	private String decode(String item){
		byte[] tmp = item.getBytes();
		Base64 decode = new Base64();
		tmp = decode.decode(tmp);
		return new String(tmp);
	}



}
