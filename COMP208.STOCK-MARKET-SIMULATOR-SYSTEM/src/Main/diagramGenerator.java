package Main;

/**
 * @author sgjgong3
 *
 */
public interface diagramGenerator {
	
	/**
	 * The method to draw the history graph of a stock.
	 * 
	 * @param stockName
	 *            the name of the stock.
	 * @return true, if the graph is drawn; false, if some error happened.
	 */
	public boolean drawHistGraph(String stockName);
	
	/**
	 * The method to draw the verification graph of a stock.
	 * 
	 * @param stockName
	 *            the name of the stock.
	 * @return true, if the graph is drawn; false, if some error happened.
	 */
	public boolean drawVeriGraph(String stockName);

	/**
	 * The method to draw the daily price graph of a stock.
	 * 
	 * @param stockName
	 *            the name of the stock.
	 * @return true, if the graph is drawn; false, if some error happened.
	 */
	public boolean drawDailyGraph(String stockName);

	
	/**
	 * The method to draw the prediction graph of a stock.
	 * 
	 * @param stockName
	 *            the name of the stock.
	 * @return true, if the graph is drawn; false, if some error happened.
	 */
	public boolean drawPredGraph(String stockName);

}
