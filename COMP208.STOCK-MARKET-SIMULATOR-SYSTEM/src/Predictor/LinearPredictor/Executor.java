/**
 * 
 */
package Predictor.LinearPredictor;

import java.util.ArrayList;

/**
 * @author zjpd
 *
 */
public class Executor {
	
	private double deviation;
	private int days;
	private ArrayList<Double> weight=new ArrayList<Double>(Constant.Window_Size);//权重系数
	private ArrayList<Double> movingWindow=new ArrayList<Double>(Constant.Window_Size);//窗口数据

	public Executor(int days, double[] testData, double deviation) {
		this.days = days;
		for(int i=0;i < Constant.Group_Size; i++){
			movingWindow.add(testData[i]);
		}
		
		weight = new Weights(testData).getWeight();
	}
	
	public double getOnePre(){
		double sum = 0;
		for(int i = 0;i < Constant.Window_Size;i ++){
			sum = sum + weight.get(i) * movingWindow.get(i);
		}
		return sum+deviation;
	}
	
	public Float[] getPrediction(){
		Float[] pres = new Float[days];
		for(int i=0; i<days; i++){
			double prediction = getOnePre();
			pres[i] = (float)prediction;
			movingWindow.remove(0);
			movingWindow.add(prediction);
			for(int k=0;k<Constant.Window_Size;k++)
			{
				weight.set(k, weight.get(k)+Constant.Learning_rate*(movingWindow.get(k)-prediction)*movingWindow.get(k));
			}
		}
		
		return pres;
	}

}
