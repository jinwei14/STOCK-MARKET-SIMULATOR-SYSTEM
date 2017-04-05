package main;

/**
 * The predictor will get a model after training historical data in the
 * database.
 * in order to get the prediction of the future trend of the stock price, the
 * linear regression algorithm and support vector machine algorithm will be
 * applied.  Users will get the prediction result if they request this function.
 * However, the prediction result will be verified for self- renewal
 * 
 * 
 * */
public interface Predictor {
	/**
	 * 这个接口我觉得没有必要，因为已经放在database类里面了，注释掉了	
	 * */
	//public void takeInStockData(Object o);
	/**
	 * The stock code is needed for prediction.
	 * @return the stock price prediction use Linear regression algorithm.
	 * */
	public ArrayList<Float> getLinearPrediction(String stockCode);
	/**
	 * The stockCode is needed for prediction
	 * @return the stock price prediction use SVM algorithm
	 * */
	public ArrayList<Float> getSVMPrediction(String stockCode);
	
	/**
	 * 
	 *  @return
	 * */
	public ArrayList<Float> verifivation();
	

}
