package Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import Main.Predictor;
import Main.queryProcessor;

/**
 * This class is set in the client peer.
 * @author zjpd
 *
 */
public class Client {
	
	private Predictor predictor;
	private queryProcessor queryPro;
	
	public Client(){
		
		try {
			predictor = (Predictor) Naming.lookup("rmi://localhost/castVote");
			queryPro = (queryProcessor) Naming.lookup("rmi://localhost/queryProcessor");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
}
