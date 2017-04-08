package Crawler;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	
	private ExecutorService crawlers;
	
	public Main() throws SQLException{
		crawlers = Executors.newFixedThreadPool(1);
		//crawlers.execute(new HistoricalCrawler());
		crawlers.execute(new DailyCrawler());
		crawlers.shutdown();
	}
	
	public static void main(String args[]){
		try {
			new Main();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
