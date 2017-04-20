package Crawler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Database.Database;
import Server.ErrorExecutor;

public class DailyCrawler implements Runnable {

	public static final String YAHOO_FINACE_URL =  "http://table.finance.yahoo.com/table.csv?";
	public static final String YAHOO_FINACE_URL_TODAY = "http://finance.yahoo.com/d/quotes.csv?";
	
	private List<String> nameList;
	private String parameter = "&f=sd1l1yr";
	
	public DailyCrawler() throws SQLException{
		
		Database.startDatabase();
		
		nameList = new ArrayList<String>();
		getNameList();
		run();
	}
	
	private void getNameList() {
		nameList = Database.getStockCode();
	}

	public void run() {
		while(true){
			for(int i=0;i<nameList.size();i ++){
				try {
					if(Database.tableExist(nameList.get(i)+"Daily")){
						crawlDailyStock(nameList.get(i));
					}else{
						Database.createTable("create table "+nameList.get(i)+"Daily ("
								+ "dailyDate char(25) not null primary key,"
								+ "stockId char(15),"
								+ "lastTradePrice float,"
								+ "dividentYeild float,"
								+ "peRatio float);");
					}
				} catch (SQLException e) {
					ErrorExecutor.writeError(e.getMessage());
				}
			}
			try {
				Thread.sleep(1000*60*5);
			} catch (InterruptedException e) {
				ErrorExecutor.writeError(e.getMessage());
			}
		}
		
	}

	private void crawlDailyStock(String stockName) throws SQLException {
		
		
		ArrayList<StockData> list = new ArrayList<StockData>();
		String url = YAHOO_FINACE_URL_TODAY + "s=" + stockName + parameter;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String getDate = sdf.format(new Date());
		
		URL myURL = null;
		URLConnection con = null;
		InputStreamReader ins = null;
		BufferedReader in = null;
		try{
			myURL = new URL(url);
			con = myURL.openConnection();
			ins = new InputStreamReader(con.getInputStream(), "UTF-8");
			in = new BufferedReader(ins);
			
			String newLine = in.readLine();
			System.out.println(newLine);
			
			System.out.println(newLine);
			String stockInfo[] = newLine.trim().split(",");
			StockData sd = new StockData();
			sd.setCode(stockName);
			sd.setLastTrade(stockInfo[1]);
			if(stockInfo[2].equals("N/A"))
				sd.setLastPrice(0);
			else
				sd.setLastPrice(Float.valueOf(stockInfo[2]));
			if(stockInfo[3].equals("N/A"))
				sd.setDivident(0);
			else
				sd.setDivident(Float.valueOf(stockInfo[3]));
			if(stockInfo[4].equals("N/A"))
				sd.setPE(0);
			else
				sd.setPE(Float.valueOf(stockInfo[4]));
			list.add(sd);
			
		}catch(Exception e1){
			ErrorExecutor.writeError(e1.getMessage());
			return;
		}finally{
			if(in!=null){
				try{
					in.close();
				}catch(IOException e2){
					ErrorExecutor.writeError(e2.getMessage());
				}
			}
		}
		
		for(int i=0;i < list.size();i ++){
			String sql = "insert into "+stockName+"Daily values('"+getDate+"','"+
						list.get(i).getCode()+"','"+list.get(i).getLastPrice()+"','"+
						list.get(i).getDivident()+"','"+list.get(i).getPE()+"')";
			System.out.println(sql);
			Database.insert(sql);
			
			String sql2 = "update stock set pe="+list.get(i).getPE();
			Database.update(sql2);
		}
	}

}
