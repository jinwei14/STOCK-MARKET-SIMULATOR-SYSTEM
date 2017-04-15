package ClientCrawler;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import Server.ErrorExecutor;

public class NewsCrawl1 implements Runnable {
	
	public NewsCrawl1() {
		run();
	}
	
	public void crawlYahoo() {
		String regex = "href=.*>?";
		Pattern pattern = Pattern.compile(regex);
		
		org.jsoup.nodes.Document doc;
		String html;
		try {
			html = Jsoup.connect("http://ftr.fivefilters.org/makefulltextfeed.php?url=http%3A%2F%2Ffinance.yahoo.com%2Fnews%2Fprovider-ap%2Frss&max=1")
					.timeout(10*1000)
					.ignoreHttpErrors(true)
					.get()
					.html();
			doc = Jsoup.parse(html);
			
			String title = doc.select("title").text();
			String description = doc.select("description").text();
			Matcher matcher = pattern.matcher(description);
			if(matcher.find()){
				System.out.println(title);
				System.out.println(matcher.group());
				CrawlerMain.addNews(title, matcher.group());
			}
		} catch (IOException e) {
			ErrorExecutor.writeError(e.getMessage());
		}
	}

	@Override
	public void run() {
		crawlYahoo();
	}

	public static void main(String args[]){
		new NewsCrawl1();
	}
}
