package GraphGenerator;

/**
 * @author sgjgong3
 *
 */
public class GraphGenerator {
	
	private Runtime r;
	private String graphType;
	private final String PYTHON_FILE = "graphGenerator.py";

	
	/**
	 * The method to draw the history graph of a stock.
	 * 
	 * @param stockName
	 *            the name of the stock.
	 * @return true, if the graph is drawn; false, if some error happened.
	 */
	public boolean drawHistGraph(String stockName) {
		graphType = "hist"; // Set the type of the graph

		return runPython(stockName, graphType);
	}

	
	/**
	 * The method to draw the verification graph of a stock.
	 * 
	 * @param stockName
	 *            the name of the stock.
	 * @return true, if the graph is drawn; false, if some error happened.
	 */
	public boolean drawVeriGraph(String stockName) {
		graphType = "veri";

		return runPython(stockName, graphType);
	}

	
	/**
	 * The method to draw the daily price graph of a stock.
	 * 
	 * @param stockName
	 *            the name of the stock.
	 * @return true, if the graph is drawn; false, if some error happened.
	 */
	public boolean drawDailyGraph(String stockName) {
		graphType = "daily"; // Set the type of the graph

		return runPython(stockName, graphType);
	}

	
	/**
	 * The method to draw the prediction graph of a stock.
	 * 
	 * @param stockName
	 *            the name of the stock.
	 * @return true, if the graph is drawn; false, if some error happened.
	 */
	public boolean drawPredGraph(String stockName) {
		graphType = "pred"; // Set the type of the graph

		return runPython(stockName, graphType);
	}

	
	/**
	 * The method to run the Python file to draw the graph of a stock.
	 * 
	 * @param stock: the name of the stock;
	 *        graph: the type of the graph to draw.
	 * 
	 * @return true, if the graph is drawn; false, if some error happened.
	 */
	private boolean runPython(String stock, String graph) {
		boolean graphDrawn = false;

		try {
			// Run the Python file to draw the history graph
			r = Runtime.getRuntime();
			String argv[] = { "cmd", "/c", "python", PYTHON_FILE, stock, graphType };
			r.exec(argv);
			graphDrawn = true;
		} catch (Exception e) {
			graphDrawn = false;
		}

		return graphDrawn;
	}

}
