package Main;


/**
 * The crawler is designed for web crawling stock data from the Internet by an
 * API, such as Yahoo Finance API. There are two main functions which are
 * realized by the crawler. Firstly, it is used to sketch the historical data of
 * the stock. Secondly, it is used to sketch stock daily information in real
 * time.
 * 
 * 
 * */
public interface Crawler<E> {
	/**
	 * @return
	 * */
	boolean fetchStock();

	/**
	 * @return
	 * */
	public E sketch_historical_data();

	/**
	 * @return
	 * */
	public E skhetch_daily_information();

}
