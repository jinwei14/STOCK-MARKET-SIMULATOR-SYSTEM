package Predictor.LinearPredictor;

public class LinearDeviation {
	
	private double[] deviation;
	
	class IndividualDeviation{
		private double[][] testData = new double[Constant.Group_Amount][Constant.Group_Size];
		private double[] average = new double[Constant.Group_Amount];
		private double[][] modelResidual = new double[Constant.Group_Amount][Constant.Group_Size];
		private double[] residualMean = new double[Constant.Group_Amount];
		private double Ideviation = 0;
		
		
		public IndividualDeviation(double[] data){
			for(int i=0; i<Constant.Group_Amount; i++){
				for(int j=0; j<Constant.Group_Size; j++){
					testData[i][j] = data[i*Constant.Group_Amount+j];
				}
			}
			getAverage();
			getModelResidual();
			getResidualMean();
		}
		
		/**
		 * ������������֮ǰ�Ĳ�ֵ�ľ�ֵ
		 * @return
		 */
		private void getResidualMean() {
			for(int i = 0;i < Constant.Group_Amount;i ++){
				double sum = 0;
				for(int j = 0;j < Constant.Group_Size;j ++){
					sum = sum + modelResidual[i][j];
				}
				residualMean[i] = sum / Constant.Group_Size;
			}
		}

		/**
		 * �÷�����������ÿ�����ݺ;�ֵ֮��Ĳ�ֵ��Ȼ�󽫽�������һ��������
		 * @return
		 */
		private void getModelResidual() {
			for(int i = 0;i < Constant.Group_Amount;i ++){
				for(int j = 0;j < Constant.Group_Size;j ++){
					modelResidual[i][j] = testData[i][j] - average[i];
				}
			}
		}

		/**
		 * �÷�����������10������ľ�ֵ/ used to calculate the average value of 10 groups
		 * @return
		 */
		private void getAverage() {
			for(int i = 0;i < Constant.Group_Amount;i ++){
				double sum = 0;
				for(int j = 0;j < Constant.Group_Size;j ++){
					sum = sum + testData[i][j];
				}
				average[i] = sum / Constant.Group_Size;
			}
		}

		/**
		 * �÷�����������deviation, ����Ĺ�ʽ���Ǽ��㷽��
		 * @return
		 */
		public double getDeviation() {
			double sum = 0;
			for(int i = 0;i < Constant.Group_Amount;i ++){
				for(int j = 0;j < Constant.Group_Amount;j ++){
					double temp = 0;
					temp = (modelResidual[i][j] - residualMean[i]) * (modelResidual[i][j] - residualMean[i]);
					sum = sum + temp;
				}
			}
			Ideviation = Math.sqrt(sum / (Constant.Group_Amount*Constant.Group_Size));
			return Ideviation;
		}
		
	}
	
	
	public LinearDeviation(double[][] testData){
		for(int i=0;i<4;i ++){
			deviation[i] = new IndividualDeviation(testData[i]).getDeviation();
		}
	}
	
	


	public double[] getDeviation() {
		return deviation;
	}
}
