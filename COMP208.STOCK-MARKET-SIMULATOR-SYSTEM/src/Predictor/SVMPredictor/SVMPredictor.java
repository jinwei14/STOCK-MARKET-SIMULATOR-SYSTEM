package Predictor.SVMPredictor;

import java.io.IOException;

public class SVMPredictor {
	
	private String stockCode;
	
	public SVMPredictor(String stockCode){
		this.stockCode = stockCode;
	}
	
	public void getSVMPrediction(int days, int txtId) throws IOException{
		Runtime r = Runtime.getRuntime();
		String argv[] = {"cmd", "/c", "python", "SVMPredictor.py",String.valueOf(days), String.valueOf(txtId), stockCode};
		@SuppressWarnings("unused")
		Process p = r.exec(argv);
	}

}
