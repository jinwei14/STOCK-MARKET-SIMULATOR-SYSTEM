package Crawler;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Server.ErrorExecutor;

public class CrawlerMain {
	
	private static ExecutorService crawlers;
	
	public static void startCrawler(){
		crawlers = Executors.newFixedThreadPool(4);
		//crawlers.execute(new HistoricalCrawler());
		try {
			crawlers.execute(new DailyCrawler());
		} catch (SQLException e) {
			ErrorExecutor.writeError(e.getMessage());
		}
		crawlers.shutdown();
	}
}
