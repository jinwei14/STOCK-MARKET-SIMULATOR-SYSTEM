package Predictor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Predictor.LinearPredictor.LinearPredictor;
import Predictor.SVMPredictor.SVMPredictor;

public class PreExecutor {
	
	private static int txtId = 0;
	
	public PreExecutor(){}
	
	public static synchronized void addtxtId(){
		txtId ++;
	}
	
	public boolean getLinearPrediction(String stockCode, int days) {
		addtxtId();
		boolean predictResult = false;
		try {
			predictResult = new LinearPredictor(stockCode).getLinearPrediction(days, txtId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return predictResult;
	}

	public void getSVMPrediction(String stockCode, int days) {
		addtxtId();
		try {
			new SVMPredictor(stockCode).getSVMPrediction(days, txtId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Float> verifivation() {
		return null;
	}

 }
