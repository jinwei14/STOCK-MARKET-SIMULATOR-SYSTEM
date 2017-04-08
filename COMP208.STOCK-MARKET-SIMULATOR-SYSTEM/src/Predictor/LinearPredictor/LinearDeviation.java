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
		 * 方法用来计算之前的差值的均值
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
		 * 该方法用来计算每个数据和均值之间的差值，然后将结果存放在一个数组中
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
		 * 该方法用来计算10个分组的均值/ used to calculate the average value of 10 groups
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
		 * 该方法用来计算deviation, 具体的公式就是计算方差
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
