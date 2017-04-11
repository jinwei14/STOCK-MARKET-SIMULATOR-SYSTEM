package Predictor.LinearPredictor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database.Database;

public class LinearPredictor{
	
	private double[] deviation;
	private double[][] testData;
	
	
	public LinearPredictor(String stockCode) throws SQLException{
		List<Float[]> prices = Database.getStockHisPrice(stockCode);
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

	public boolean getLinearPrediction(int days, int txtId) {
		ArrayList<Float[]> stockPrediction = new ArrayList<Float[]>();
		for(int i=0; i<deviation.length; i++){
			Float[] uniquePre = new Float[days];
			uniquePre = new Executor(days, testData[i], deviation[i]).getPrediction();
			stockPrediction.add(uniquePre);
		}
		
		File file = new File("PredText\\l"+txtId);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
			for(int i=0; i<stockPrediction.get(0).length; i++){
				writer.write(String.valueOf(stockPrediction.get(0)[i])+"\t"+
						String.valueOf(stockPrediction.get(1)[i])+"\t"+
						String.valueOf(stockPrediction.get(2)[i])+"\t"+
						String.valueOf(stockPrediction.get(3)[i])+"\n");
			}
			writer.flush();
			writer.close();
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
