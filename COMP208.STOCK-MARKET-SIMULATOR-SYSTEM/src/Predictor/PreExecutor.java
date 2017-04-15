package Predictor;

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
		predictResult = new LinearPredictor(stockCode).getLinearPrediction(days, txtId);
		return predictResult;
	}

	public void getSVMPrediction(String stockCode, int days) {
		addtxtId();
		new SVMPredictor(stockCode).getSVMPrediction(days, txtId);
	}

	public ArrayList<Float> verifivation() {
		return null;
	}

 }
