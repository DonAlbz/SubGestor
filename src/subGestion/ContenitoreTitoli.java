package subGestion;

import java.io.Serializable;

public class ContenitoreTitoli implements Serializable {
	public  String[] nomeFile;

	public ContenitoreTitoli(String [] nomeFile) {
		this.nomeFile=nomeFile;		
	}
	
	public void infoTitoli(){
		for(int i = 0; i < nomeFile.length; i++){
		System.out.println (nomeFile[i]);
		}	
	}
	
	public String [] getNomiFile(){
		return nomeFile;
	}
}
