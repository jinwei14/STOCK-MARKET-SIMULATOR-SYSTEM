package Main;

import java.util.ArrayList;

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
	 * 
	 * @param stockCode
	 * @param days
	 * @return
	 */
	public ArrayList<Float[]> getLinearPrediction(String stockCode, int days);
	/**
	 * The stockCode is needed for prediction
	 * @return the stock price prediction use SVM algorithm
	 * */
	public void getSVMPrediction(String stockCode, int days);
	
	/**
	 * 
	 *  @return
	 * */
	public ArrayList<Float> verifivation();
	

}
