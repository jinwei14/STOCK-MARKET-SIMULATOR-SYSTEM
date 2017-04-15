package ClientCrawler;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrawlerMain {
	
private static ExecutorService crawlers;
	
	private static HashMap<String,String> news = new HashMap<String,String>();
	
	public static void startCrawler(){
		crawlers = Executors.newFixedThreadPool(3);
		crawlers.execute(new NewsCrawl1());
		crawlers.execute(new NewsCrawl2());
		crawlers.execute(new NewsCrawl3());
		crawlers.shutdown();
	}
	
	public static void addNews(String description, String url){
		news.put(description, url);
	}
	
	public static HashMap<String,String> getNews(){
		return news;
	}
}
