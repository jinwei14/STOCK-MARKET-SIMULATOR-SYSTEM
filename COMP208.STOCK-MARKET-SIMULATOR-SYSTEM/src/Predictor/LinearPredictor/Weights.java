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
	 * ����һ��ʼ���ǲ���֪��ÿһ��weight�ľ���ֵ�Ƕ��٣�
	 * ������ǽ�weight�ĳ�ʼֵ�趨 ��1/ʱ�䴰����Ԫ�ظ����� ���ý������ĳ���ȥ�Զ�����weightֵ��
	 * @return
	 */
	public void getInitWeights(){
		
		for(int i = 0;i < weights.length;i ++){//���������ʼ��Ȩ��ϵ��
			weights[i]=1.0/weights.length;
		}

		for(int i=Constant.Window_Size;i<testData.length-Constant.Window_Size;i++){
			double predictData=0;
			
			for(int k=0;k<Constant.Window_Size;k++){
				predictData=predictData+weights[k]*testData[i-Constant.Window_Size+k];//����Ȩ��ϵ��������һ�����
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
