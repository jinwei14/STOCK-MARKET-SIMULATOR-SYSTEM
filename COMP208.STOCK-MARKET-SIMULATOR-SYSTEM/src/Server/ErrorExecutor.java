package Server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class ErrorExecutor {
	
	private static File file;
	private static BufferedWriter writer;

	public static void start(){

		file = new File("error_log\\error.txt");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Cannot create error file","ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Cannot create file writer","ERROR",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public static void writeError(String error){
		try {
			writer.write(error);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Cannot write into log file","ERROR",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void close(){
		try {
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Cannot close writer","ERROR",JOptionPane.ERROR_MESSAGE);
		}
	}

}
