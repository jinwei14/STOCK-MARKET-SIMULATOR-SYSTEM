package Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import api.Predictor;
import api.queryProcessor;

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
			setPredictor((Predictor) Naming.lookup("rmi://localhost/Predictor"));
			setQueryPro((queryProcessor) Naming.lookup("rmi://localhost/queryProcessor"));
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	public Predictor getPredictor() {
		return predictor;
	}

	public void setPredictor(Predictor predictor) {
		this.predictor = predictor;
	}

	public queryProcessor getQueryPro() {
		return queryPro;
	}

	public void setQueryPro(queryProcessor queryPro) {
		this.queryPro = queryPro;
	}
}
