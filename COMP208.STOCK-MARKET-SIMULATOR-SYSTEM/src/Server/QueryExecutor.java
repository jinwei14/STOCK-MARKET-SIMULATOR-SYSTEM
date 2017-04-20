package Server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import Database.Database;
import GraphGenerator.GraphGenerator;
import Predictor.PreExecutor;

public class QueryExecutor{
	
	private static int userId = 0;
	private PreExecutor preEx;
	private GraphGenerator graEx;
	
	public QueryExecutor(){
		preEx = new PreExecutor();
		graEx = new GraphGenerator();
	}
	
	public int addPersonalInformation(String username, String password, String phone, String postcode, String bankcard) {
		userId ++;
		username = encode(username);
		password = decode(password);
		String sql = "insert into User values ('"+userId+"','"+username+"','"+password+
						"','"+phone+"','"+postcode+"','"+bankcard+"')";
		Database.insert(sql);
		return userId;
	}
	
	public boolean changePersonalUsername(int user_id, String username) {
		username = encode(username);
		String sql = "update User set username="+username+" where user_id="+user_id;
		return Database.update(sql);
	}

	public boolean changePersonalPassword(int user_id, String password) {
		password = encode(password);
		String sql = "update User set password="+password+" where user_id="+user_id;
		return Database.update(sql);
	}

	public boolean changePersonalInformation(int user_id, String username, String password) {
		username = encode(username);
		password = decode(password);
		String sql = "update User set username="+username+", password="+password+" where user_id="+user_id;
		return Database.update(sql);
	}

	public boolean changePersonalStock(int user_id, int Stock_id, int share_num) {
		String sql = "update User set share_num="+share_num+" where user_id="+user_id+
				" and stock_id="+Stock_id;
		return Database.update(sql);
	}
	
	public List<String> viewPersonalInformation(int user_id) {
		List<String> result = new ArrayList<String>();
		String sql = "select * from User where user_id="+user_id;
		try{
			result = Database.queryPersonInfo(sql);
			result.set(1, decode(result.get(1))); //decode username
			result.set(2, decode(result.get(2))); //decode password
			return result;
		} catch (Exception e){
			return result;
		}
		
	}
	
	public List<List<String>> viewPersonalStock(int user_id) {
		List<List<String>> result = new ArrayList<List<String>>();
		String sql = "select * from HoldStock where user_id="+user_id;
		result = Database.queryPersonStock(sql);
		return result;
	}

	public boolean deletePersonalInformation() {
		return false;
	}
	
	public List<List<String>> getPeOrder(int order){
		List<List<String>> result = new ArrayList<List<String>>();
		if(order == 0){
			String sql = "select stock_name,pe from stock order by pe asc limit 5";
			result = Database.getPEOrder(sql);
		}else{
			String sql = "select stock_name,pe from stock order by pe desc limit 5";
			result = Database.getPEOrder(sql);
		}
		return result;
	}
	
	public boolean buyStock(int user_id, String stock_id, int share_num) throws SQLException {
		String sql1 = "select share_num, price from holdstock where exists ("+
				"select user_id from holdstock where user_id="+user_id+" and stock_id='"+stock_id+"')";
		double[] userShare = Database.queryStockShare(sql1);
		double price = 0;
		if(userShare.length==0){
			price = 0;
		}else{
			List<String> stock_info = Database.getStockAllInfo(stock_id);
			price = Double.valueOf(stock_info.get(4));
			price = (float) ((userShare[0]*userShare[1]+share_num*price)/(share_num+userShare[1]));
			share_num = (int)userShare[0]+share_num;
		}
		
		String sql3 = "insert into holdstock values ('"+user_id+"','"+stock_id+"','"+share_num+"','"+price+"')"+
					"on duplicate key update share_num = share_num+"+share_num+
					",price="+price;
		return Database.update(sql3); 
	}
	
	/**
	 * user wants to sell the chosen stock
	 * @param user_id the user id
	 * @param stock_id the chosen stock
	 * @param share_num how many does the user want to sell
	 * @return the number of money that the user earned. -1-->exceed upbound
	 * @throws SQLException
	 */
	public double sellStock(int user_id, String stock_id, int share_num) throws SQLException {
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
		List<String> stock_info = Database.getStockAllInfo(stock_id);
		double price = Double.valueOf(stock_info.get(4));
		double fund = sell_num*price;
		String sql3 = "update user set surplus_fund=surplus_fund+"+fund+" where user_id="+user_id;
		Database.update(sql3);
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

	public BufferedImage getStockHisChart(String stock_id) {
		String image_name = graEx.drawHistGraph(stock_id);
		try {
			BufferedImage img = ImageIO.read(new File("\\images\\"+image_name));
			return img;
		} catch (IOException e) {
			ErrorExecutor.writeError(e.getMessage());
			return null;
		}
	}

	public BufferedImage[] getPredictChart(String stock_id, int days) {
		preEx.getLinearPrediction(stock_id, days);
		preEx.getSVMPrediction(stock_id, days);
		String image_name = graEx.drawPredGraph(stock_id);
		try {
			BufferedImage img[] = new BufferedImage[2];
			BufferedImage limg = ImageIO.read(new File("\\images\\"+image_name));
			BufferedImage simg = ImageIO.read(new File("\\images\\"+image_name));
			img[0] = limg;
			img[1] = simg;
			return img;
		} catch (IOException e) {
			ErrorExecutor.writeError(e.getMessage());
			return null;
		}
	}

	public BufferedImage getVeriChart(String stock_id) {
		String image_name = graEx.drawVeriGraph(stock_id);
		try {
			BufferedImage img = ImageIO.read(new File("\\images\\"+image_name));
			return img;
		} catch (IOException e) {
			ErrorExecutor.writeError(e.getMessage());
			return null;
		}
	}

	public BufferedImage getDaiylChart(String stock_id) {
		String image_name = graEx.drawDailyGraph(stock_id);
		try {
			BufferedImage img = ImageIO.read(new File("\\images\\"+image_name));
			return img;
		} catch (IOException e) {
			ErrorExecutor.writeError(e.getMessage());
			return null;
		}
	}

	public List<String> getStockAllInfo(String stock_id) {
		return Database.getStockAllInfo(stock_id);
	}

	public boolean topup(int user_id, double money) {
		String sql = "update user set surplus_fund=surplus_fund+"+money+" where user_id="+user_id;
		boolean result = Database.update(sql);
		if(result){
			return true;
		}else{
			return false;
		}
	}
}
