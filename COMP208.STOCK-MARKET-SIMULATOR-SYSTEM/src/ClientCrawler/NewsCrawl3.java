package ClientCrawler;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import Server.ErrorExecutor;

public class NewsCrawl3 implements Runnable {


	public NewsCrawl3() {
		run();
	}
	
	public void crawlForbes() {
		String regex = "href=.*>";
		Pattern pattern = Pattern.compile(regex);
		
		org.jsoup.nodes.Document doc;
		String html;
		try {
			html = Jsoup.connect("http://ftr.fivefilters.org/makefulltextfeed.php?url=http%3A%2F%2Fwww.forbes.com%2Fbusiness%2Findex.xml&max=1")
					.timeout(10*1000)
					.ignoreHttpErrors(true)
					.get()
					.html();
			doc = Jsoup.parse(html);
			
			String title = doc.select("title").text();
			String description = doc.select("description").text();
			Matcher matcher = pattern.matcher(description);
			if(matcher.find()){
				CrawlerMain.addNews(title, matcher.group());
			}
		} catch (IOException e) {
			ErrorExecutor.writeError(e.getMessage());
		}
	}

	@Override
	public void run() {
		crawlForbes();
	}

}
