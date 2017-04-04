package main;

/**
 * 
 * Diagram generator will generate a diagram through the target data in a
 * succinct format to improve the visualization. The stock information is often
 * presented in curve graphs or line chart so that the trend, the low and high
 * values can be seen straightforwardly. Furthermore, the stock prediction can
 * be shown by a line chart to the user. When the user wants to verify the
 * correctness of the prediction, he/she can compare the prediction curve with
 * the actual trend curve.
 */
public interface diagramGenerator {
	/**
	 * provid user a graphic chat of the stock information. help user understand
	 * the rise and fall of stock in one day's trading
	 */
	public void drawGraph();

	/**
	 * user click the view stock button then a line chart of stock should be
	 * provided so that the return type is Graph.
	 */
	public boolean requestGraph();

	/**
	 * once user request for a stock information, the graphic generator should
	 * request to the database traverse the database then return related stock
	 * data.
	 */
	boolean requestStockData();
	
	/**
	*once generator request for stock data, database should 
	*return the relative stock detail to the generator
	*/
	public void returnData();

}
