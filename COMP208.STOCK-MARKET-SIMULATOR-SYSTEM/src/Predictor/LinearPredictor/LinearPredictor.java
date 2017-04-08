package Predictor.LinearPredictor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database.Database;

public class LinearPredictor{
	
	private double[] deviation;
	private double[][] testData;
	
	private Database executor;
	
	public LinearPredictor(String stockCode) throws SQLException{
		List<Float[]> prices = executor.getStockHisPrice(stockCode);
		double[] high = new double[prices.size()];
		double[] low = new double[prices.size()];
		double[] open = new double[prices.size()];
		double[] close = new double[prices.size()];
		for(int i=0; i<prices.size(); i ++){
			high[i] = prices.get(i)[0];
			low[i] = prices.get(i)[1];
			open[i] = prices.get(i)[2];
			close[i] = prices.get(i)[3];
		}
		testData = new double[4][];
		testData[0] = high;
		testData[1] = low;
		testData[2] = open;
		testData[3] = close;

		deviation = new LinearDeviation(testData).getDeviation();
		
	}

	public ArrayList<Float[]> getLinearPrediction(int days) {
		ArrayList<Float[]> stockPrediction = new ArrayList<Float[]>();
		for(int i=0; i<deviation.length; i++){
			Float[] uniquePre = new Float[days];
			uniquePre = new Executor(days, testData[i], deviation[i]).getPrediction();
			stockPrediction.add(uniquePre);
		}
		
		return stockPrediction;
	}

}
