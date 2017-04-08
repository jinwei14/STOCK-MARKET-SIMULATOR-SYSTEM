package Predictor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Main.Predictor;
import Predictor.LinearPredictor.LinearPredictor;
import Predictor.SVMPredictor.SVMPredictor;

public class Prediction implements Predictor{
	
	private static int txtId = 0;
	
	public ArrayList<Float[]> getLinearPrediction(String stockCode, int days) {
		txtId ++;
		ArrayList<Float[]> predictResult = new ArrayList<Float[]>();
		try {
			predictResult = new LinearPredictor(stockCode).getLinearPrediction(days);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return predictResult;
	}

	@Override
	public void getSVMPrediction(String stockCode, int days) {
		txtId ++;
		try {
			new SVMPredictor(stockCode).getSVMPrediction(days, txtId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Float> verifivation() {
		return null;
	}

 }
