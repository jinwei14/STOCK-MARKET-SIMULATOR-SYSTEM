package ClientCrawler;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import Server.ErrorExecutor;

public class NewsCrawl2 implements Runnable {

	public NewsCrawl2() {
		run();
	}
	
	public void crawlFox() {
		String regex1 = "#039;s.*&#039";
		String regex2 = "href=.*>";
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		
		org.jsoup.nodes.Document doc;
		String html;
		try {
			html = Jsoup.connect("http://ftr.fivefilters.org/makefulltextfeed.php?url=http%3A%2F%2Ffeeds.foxbusiness.com%2Ffoxbusiness%2Flatest&max=1")
					.timeout(10*1000)
					.ignoreHttpErrors(true)
					.get()
					.html();
			doc = Jsoup.parse(html);
			
			String title = doc.select("title").text();
			String description = doc.select("description").text();
			Matcher matcher = pattern1.matcher(title);
			if(matcher.find()){
				String des = matcher.group();
				matcher = pattern2.matcher(description);
				if(matcher.find()){
					String url = matcher.group();
					CrawlerMain.addNews(des, url);
				}else{
					CrawlerMain.addNews(des, "");
				}
			}
		} catch (IOException e) {
			ErrorExecutor.writeError(e.getMessage());
		}
	}

	@Override
	public void run() {
		crawlFox();
	}
}
