package Crawler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database.Database;
import Server.ErrorExecutor;

public class HistoricalCrawler implements Runnable {
	
	public static final String YAHOO_FINACE_URL =  "http://table.finance.yahoo.com/table.csv?";
	public static final String YAHOO_FINACE_URL_TODAY = "http://finance.yahoo.com/d/quotes.csv?";

	private List<String> nameList;
	
	public HistoricalCrawler() throws SQLException{
		nameList = new ArrayList<String>();
		getNameList();
		run();
	}
	
	private void getNameList() throws SQLException{
		nameList = Database.getStockCode();
	}
	
	public void run() {
		for(int i=0;i<nameList.size();i ++){
			if(Database.tableExist(nameList.get(i))){
				continue;
			}else{
				Database.createTable("create table "+nameList.get(i)+" ("
						+ "stockDate char(15) not null primary key,"
						+ "high float,"
						+ "low float,"
						+ "open float,"
						+ "close float,"
						+ "volume float);");
				crawlStock(nameList.get(i), "2008-01-01", "2017-04-01");
			}
		}
	}

	private void crawlStock(String stockName, String fromDate, String toDate) {
		
		ArrayList<StockData> list = new ArrayList<StockData>();
		String[] datefrominfo = fromDate.split("-");
		String[] toDateinfo = toDate.split("-");
		String code = stockName;
		
		String a = (Integer.valueOf(datefrominfo[1])-1) + ""; //a-起始时间，月
		String b = datefrominfo[2];  //b-起始时间，日
		String c = datefrominfo[0];  //c-起始时间，年
		String d = (Integer.valueOf(toDateinfo[1])-1)+"";// d – 结束时间，月
		String e = toDateinfo[2];// e – 结束时间，日
		String f =  toDateinfo[0];// f – 结束时间，年
		
		String params = "&a=" + a + "&b=" + b + "&c=" + c + "&d=" + d + "&e="
				+ e + "&f=" + f;
		
		String url = YAHOO_FINACE_URL  + "s=" + stockName + params;
		
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
			
			while((newLine = in.readLine())!=null){
				String stockInfo[] = newLine.trim().split(",");
				StockData sd = new StockData();
				sd.setCode(code);
				sd.setDate(stockInfo[0]);
				sd.setOpen(Float.valueOf(stockInfo[1]));
				sd.setHigh(Float.valueOf(stockInfo[2]));
				sd.setLow(Float.valueOf(stockInfo[3]));
				sd.setClose(Float.valueOf(stockInfo[4]));
				sd.setVolume(Float.valueOf(stockInfo[5]));
				sd.setAdj(Float.valueOf(stockInfo[6]));
				list.add(sd);
			}
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
			String sql = "insert into "+stockName+" values('"+list.get(i).getDate()+
						"','"+list.get(i).getHigh()+"','"+list.get(i).getLow()+"','"+
						list.get(i).getOpen()+"','"+list.get(i).getClose()+"','"+
						list.get(i).getVolume()+"')";
			Database.insert(sql);
		}
	}

}
