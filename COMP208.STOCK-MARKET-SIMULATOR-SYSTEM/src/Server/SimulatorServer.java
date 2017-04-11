package Server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import Database.Database;

public class SimulatorServer {
	
	public static void main(String args[]){
		Database.startDatabase();
		
		try{
			LocateRegistry.createRegistry(1099);
			ServerImpl remote = new ServerImpl();
			
			Naming.rebind("Predictor", remote);
			Naming.rebind("queryProcessor", remote);
		}catch (Exception e){
			System.out.println("Error occurred at server" + e.getMessage());
		}
	}

}
