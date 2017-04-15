package Predictor.SVMPredictor;

import java.io.IOException;

import Server.ErrorExecutor;

public class SVMPredictor {
	
	private String stockCode;
	
	public SVMPredictor(String stockCode){
		this.stockCode = stockCode;
	}
	
	public void getSVMPrediction(int days, int txtId) {
		Runtime r = Runtime.getRuntime();
		String argv[] = {"cmd", "/c", "python", "SVMPredictor.py",String.valueOf(days), String.valueOf(txtId), stockCode};
		try {
			@SuppressWarnings("unused")
			Process p = r.exec(argv);
		} catch (IOException e) {
			ErrorExecutor.writeError(e.getMessage());
		}
	}

}
