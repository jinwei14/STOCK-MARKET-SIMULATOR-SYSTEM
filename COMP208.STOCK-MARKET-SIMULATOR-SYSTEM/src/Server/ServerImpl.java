package Server;

import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

import api.queryProcessor;

@SuppressWarnings("serial")
public class ServerImpl extends UnicastRemoteObject implements queryProcessor{
	
	private QueryExecutor queryEx;
	
	public ServerImpl() throws RemoteException {
		super();
		queryEx = new QueryExecutor();
	}

	public int addPersonalInformation(String username, String password, String phone, String bankcard, String postcode) {
		return queryEx.addPersonalInformation(username, password, phone, bankcard, postcode);
	}

	public boolean changePersonalUsername(int user_id, String username) {
		return queryEx.changePersonalUsername(user_id, username);
	}

	public boolean changePersonalPassword(int user_id, String password) {
		return queryEx.changePersonalPassword(user_id, password);
	}

	public boolean changePersonalInformation(int user_id, String username, String password) {
		return queryEx.changePersonalInformation(user_id, username, password);
	}

	public boolean changePersonalStock(int user_id, int Stock_id, int share_num) {
		return queryEx.changePersonalStock(user_id, Stock_id, share_num);
	}

	public List<String> viewPersonalInformation(int user_id) {
		return queryEx.viewPersonalInformation(user_id);
	}
	
	public List<List<String>> viewPersonalStock(int user_id) {
		return queryEx.viewPersonalStock(user_id);
	}
	
	public boolean deletePersonalInformation() {
		return false;
	}
	
	public List<List<String>> getPeOrder(int order) {
		return queryEx.getPeOrder(order);
	}
	
	public List<String> getStockAllInfo(String stock_id) {
		return queryEx.getStockAllInfo(stock_id);
	}
	
	@Override
	public boolean buyStock(int user_id, String stock_id, int share_num) {
		try {
			return queryEx.buyStock(user_id, stock_id, share_num);
		} catch (SQLException e) {
			ErrorExecutor.writeError(e.getMessage());
			return false;
		}
	}

	/**
	 * user wants to sell the chosen stock
	 * @param user_id the user id
	 * @param stock_id the chosen stock
	 * @param share_num how many does the user want to sell
	 * @return the number of money that the user earned. -1-->exceed upbound -2-->sqlexception
	 * @throws SQLException
	 */
	public double sellStock(int user_id, String stock_id, int share_num) {
		try {
			return queryEx.sellStock(user_id, stock_id, share_num);
		} catch (SQLException e) {
			ErrorExecutor.writeError(e.getMessage());
			return -2;
		}
	}
	
	public boolean topup(int user_id, double money) {
		return queryEx.topup(user_id, money);
	}

	public BufferedImage getStockHisChart(String stock_id) {
		return queryEx.getStockHisChart(stock_id);
	}

	public BufferedImage[] getPredictChart(String stock_id, int days) {
		return queryEx.getPredictChart(stock_id, days);
	}

	public BufferedImage getVeriChart(String stock_id) {
		return queryEx.getVeriChart(stock_id);
	}

	public BufferedImage getDailyChart(String stock_id) {
		return queryEx.getDaiylChart(stock_id);
	}

}
