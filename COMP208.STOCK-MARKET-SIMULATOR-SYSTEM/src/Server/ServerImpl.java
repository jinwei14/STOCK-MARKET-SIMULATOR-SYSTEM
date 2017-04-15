package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import GraphGenerator.GraphGenerator;
import Main.Predictor;
import Main.diagramGenerator;
import Main.queryProcessor;
import Predictor.PreExecutor;

@SuppressWarnings("serial")
public class ServerImpl extends UnicastRemoteObject implements queryProcessor, Predictor, diagramGenerator{
	
	private QueryExecutor queryEx;
	private PreExecutor predictEx;
	private GraphGenerator graphEx;
	
	public ServerImpl() throws RemoteException {
		super();
		queryEx = new QueryExecutor();
		predictEx = new PreExecutor();
		graphEx = new GraphGenerator();
	}

	public boolean addPersonalInformation(String username, String password, String discription) {
		return queryEx.addPersonalInformation(username, password, discription);
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
	
	@Override
	public boolean buyStock(int user_id, String stock_id, int share_num, float price) {
		try {
			return queryEx.buyStock(user_id, stock_id, share_num, price);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public double sellStock(int user_id, String stock_id, int share_num, float price) {
		try {
			return queryEx.sellStock(user_id, stock_id, share_num, price);
		} catch (SQLException e) {
			e.printStackTrace();
			return -2;
		}
	}


	public boolean getLinearPrediction(String stockCode, int days) {
		return predictEx.getLinearPrediction(stockCode, days);
	}

	public void getSVMPrediction(String stockCode, int days) {
		predictEx.getSVMPrediction(stockCode, days);
	}

	public ArrayList<Float> verifivation() {
		return null;
	}

	public boolean drawHistGraph(String stockName) {
		return graphEx.drawHistGraph(stockName);
	}

	public boolean drawVeriGraph(String stockName) {
		return graphEx.drawVeriGraph(stockName);
	}

	public boolean drawDailyGraph(String stockName) {
		return graphEx.drawDailyGraph(stockName);
	}

	public boolean drawPredGraph(String stockName) {
		return graphEx.drawPredGraph(stockName);
	}

}
