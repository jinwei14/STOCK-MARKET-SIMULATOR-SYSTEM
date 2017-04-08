package Predictor.LinearPredictor;

import java.util.ArrayList;

public class Weights {
	
	private double[] weights = new double[Constant.Window_Size];
	private double[] testData = new double[400];
	
	public Weights(double[] data){
		for(int i=0; i<400; i++){
			testData[i] = data[i];
		}
		
		
		getInitWeights();
	}

	/**
	 * 由于一开始我们并不知道每一个weight的具体值是多少，
	 * 因此我们将weight的初始值设定 “1/时间窗口中元素个数” ，让接下来的程序去自动调整weight值。
	 * @return
	 */
	public void getInitWeights(){
		
		for(int i = 0;i < weights.length;i ++){//用随机数初始化权重系数
			weights[i]=1.0/weights.length;
		}

		for(int i=Constant.Window_Size;i<testData.length-Constant.Window_Size;i++){
			double predictData=0;
			
			for(int k=0;k<Constant.Window_Size;k++){
				predictData=predictData+weights[k]*testData[i-Constant.Window_Size+k];//根据权重系数计算下一个输出
			}
			for(int k=0;k<Constant.Window_Size;k++){
				weights[k]=weights[k]+Constant.Learning_rate*(testData[i-Constant.Window_Size+k]-predictData)*testData[i-Constant.Window_Size+k];
			}
		}
	}
	
	public ArrayList<Double> getWeight() {
		ArrayList<Double> returnweight = new ArrayList<Double>();
		for(int i = 0;i < weights.length;i ++){
			returnweight.add( weights[i]);
		}
		return returnweight;
	}

}
