package subGestion;
import java.io.File;


public class Elemento {
	public File file;
	public String[] possibiliEstensioni;
	public String estensione;
	public String titoloSerieTv;
	public String nuovoTitolo;
	public String nomeFile;
	public int episodio;
	public int stagione;
	public String nomePulito;
	public String episodioString;
	public String stagioneString;
	private static final String ERRORE_NOME = "C'è stato un errore sul rilevamento del nome della serie TV";
	private static final String ERRORE_STAGIONE_EPISODIO = "C'è stato un errore sul rilevamento del numero di episodio e/o della stagione";
	public final static char SPAZIO = ' ';
	private static final String ERRORE_EP = "Attenzione: nel file %s non è stato trovato il numero dell'episodio.\n" ;
	private static final String ETICHETTA_STAGIONE = "S";
	private static final String ETICHETTA_EPISODIO = "E";
	
	
	public Elemento(File file, String nomePulito, String[] possibiliEstensioni) {
		this.file = file;
		this.possibiliEstensioni = possibiliEstensioni;	
		this.nomePulito=nomePulito;
		nomeFile=file.getName();
		estensione = cercaEstensione();		
		titoloSerieTv=titoloSerieTv();
		//StagioneEpisodio();
		try{
		episodio = trovaEpisodio();
		stagione = trovaStagione();
		}
		catch (NumberFormatException n){
			if (AlbzConfiguratorMain.COMMENTI){
				System.out.printf(ERRORE_EP, nomeFile);
			}
			episodio=-1;
			stagione=-1;		
		}	
		episodioString= numeroToString(episodio);
		stagioneString= numeroToString(stagione);
		nuovoTitolo= assegnaNuovoTitolo();
	}	
	/*
	private void StagioneEpisodio() {
		String nomeTagliato=nomePulito.replaceFirst(titoloSerieTv.toLowerCase(), "").trim();
		boolean fineTrovaEpisodio = false;
		boolean fineTrovaStagione = false;
		int charParola = 0 ;
		
		StringBuffer numeroEpisodio = new StringBuffer();
		StringBuffer numeroStagione = new StringBuffer();
		
			while (!fineTrovaEpisodio || !fineTrovaStagione){		 
				if (condizioneEpisodio(nomeTagliato, charParola)){				
					//numeroEpisodio.append(nomeTagliato.charAt(inizioParola));
					charParola++;
					while (!fineTrovaEpisodio){
						numeroEpisodio.append(nomeTagliato.charAt(charParola));
						charParola++;
						if (Character.isLetter(nomeTagliato.charAt(charParola)) || trovaLettera (nomeTagliato, SPAZIO, charParola)){
							fineTrovaEpisodio=true;	
							charParola= 0;
						}
					}
					
					if(trovaLettera (nomeTagliato, 's',charParola) &&  condizioneStagione(nomeTagliato, charParola+1)){
						charParola++;
					}
					if(condizioneStagione(nomeTagliato, charParola)){
						while (!fineTrovaStagione){
							numeroStagione.append(nomeTagliato.charAt(charParola));
							charParola++;
							if (Character.isLetter(nomeTagliato.charAt(charParola)) || condizioneEpisodio(nomeTagliato, charParola)){
								fineTrovaStagione=true;
							}
						}
					}
					
				}			
				charParola++;
			}
			*/
			
	private String numeroToString(int intero) {
		StringBuffer stringDaRitornare=new StringBuffer();
		if (intero < 10){
			stringDaRitornare.append(Integer.toString(0));
		}
		stringDaRitornare.append(intero);
		return stringDaRitornare.toString();
	}

	public String cercaEstensione() {
		String estensioneRitornata = null;
		for (int i = 0; i < possibiliEstensioni.length; i++){		
			if (nomeFile.substring(nomeFile.length()-3, nomeFile.length()).equals(possibiliEstensioni[i])){
			estensioneRitornata=possibiliEstensioni[i];				
			}	
		}
	return estensioneRitornata;
	}

	private String assegnaNuovoTitolo() {
		StringBuffer titoloDaRitornare = new StringBuffer();
		titoloDaRitornare.append(titoloSerieTv);
		titoloDaRitornare.append(SPAZIO);
		titoloDaRitornare.append(ETICHETTA_STAGIONE);
		titoloDaRitornare.append(stagioneString);
		titoloDaRitornare.append(ETICHETTA_EPISODIO);
		titoloDaRitornare.append(episodioString);
		titoloDaRitornare.append(".");
		titoloDaRitornare.append(estensione);
		return titoloDaRitornare.toString();
	}

	private int trovaEpisodio() {
		String nomeTagliato=nomePulito.replaceFirst(titoloSerieTv.toLowerCase(), "").trim();
		boolean fineTrovaEpisodio = false;
		int charParola = 0 ;
		
		StringBuffer numeroEpisodio = new StringBuffer();				
			while (!fineTrovaEpisodio){	
				try{
					if (condizioneEpisodio(nomeTagliato, charParola)){				
						//numeroEpisodio.append(nomeTagliato.charAt(inizioParola));
						charParola++;							
							while (!fineTrovaEpisodio){
								numeroEpisodio.append(nomeTagliato.charAt(charParola));
								charParola++;								
								if (Character.isLetter(nomeTagliato.charAt(charParola)) || trovaLettera (nomeTagliato, SPAZIO, charParola)){
									fineTrovaEpisodio=true;	
								}							
							}					
						}
					}
					catch (StringIndexOutOfBoundsException e ) {
						fineTrovaEpisodio=true;										
					}			
					charParola++;
					
				}
	/*	if	(!fineTrovaStagioneEpisodio){
			System.out.println(ERRORE_STAGIONE_EPISODIO);
		}	*/	
		return Integer.parseInt(numeroEpisodio.toString());
	}

	private int trovaStagione() {
		String nomeTagliato=nomePulito.replaceFirst(titoloSerieTv.toLowerCase(), "").trim();
		boolean fineTrovaStagione = false;
		int charParola = 0 ;
		StringBuffer numeroStagione = new StringBuffer();
		
			while (!fineTrovaStagione){		
				try{
					if(trovaLettera (nomeTagliato, 's',charParola) &&  condizioneStagione(nomeTagliato, charParola+1)){
						charParola++;
					}
					if(condizioneStagione(nomeTagliato, charParola)){
						while (!fineTrovaStagione){
							numeroStagione.append(nomeTagliato.charAt(charParola));
							charParola++;
							if (Character.isLetter(nomeTagliato.charAt(charParola)) || condizioneEpisodio(nomeTagliato, charParola)){
								fineTrovaStagione=true;
							}
						}
					}
					/*else {								
					//titoloTrovato.append(nome.substring(inizioParola ,fineParola));
						inizioParola=fineParola+1;
						titoloTrovato.append(" ");				
					}*/
				}
				catch (StringIndexOutOfBoundsException e ) {
					fineTrovaStagione=true;										
				}			
					charParola++;
				}			
				
				return Integer.parseInt(numeroStagione.toString());
			}
		/*	if	(!fineTrovaStagioneEpisodio){
				System.out.println(ERRORE_STAGIONE_EPISODIO);
			}	*/	
			
	

	private boolean condizioneStagione(String nomeTagliato, int charParola) {
		return ((!Character.isLetter(nomeTagliato.charAt(charParola))  &&  !Character.isLetter(nomeTagliato.charAt(charParola+1)) && condizioneEpisodio(nomeTagliato, charParola+2)) || (!Character.isLetter(nomeTagliato.charAt(charParola)) && condizioneEpisodio(nomeTagliato, charParola+1)));
	}

	private boolean condizioneEpisodio (String nomeTagliato, int charParola) {
		//System.out.println(fineParola);	
		return (nomeTagliato.charAt(charParola) == 'e' || nomeTagliato.charAt(charParola)=='x' ) && (!Character.isLetter(nomeTagliato.charAt(charParola+1)) &&  !Character.isLetter(nomeTagliato.charAt(charParola+2)));
	}

	public String titoloSerieTv() {
		boolean fineTrovaTitolo = false;
		int fineParola = 0 ;
		int inizioParola = 0;
		StringBuffer titoloTrovato = new StringBuffer();
		//String[] nomeFile = trovaNomeFile();		
		//titoloTrovato.append(Character.toUpperCase(nome.charAt(inizioParola)));
		//inizioParola++;		
			while (!fineTrovaTitolo && fineParola<nomePulito.length()){		 
				if (trovaLettera(nomePulito,SPAZIO, fineParola)){				
					titoloTrovato.append(Character.toUpperCase(nomePulito.charAt(inizioParola)));
					inizioParola++;
					titoloTrovato.append(nomePulito.substring(inizioParola ,fineParola));				
					if(trovaLettera (nomePulito, 's',fineParola+1) && condizioneStagione(nomePulito, fineParola+2)){
						fineTrovaTitolo=true;
					}
					else {								
					//titoloTrovato.append(nome.substring(inizioParola ,fineParola));
						inizioParola=fineParola+1;
						titoloTrovato.append(" ");				
					}
				}			
			fineParola++;
			}
			if	(fineParola>=nomePulito.length()){
				return titoloTrovato.substring(0,titoloTrovato.length()-1);// per eliminare lo spazio che altrimenti si creerebbe al termine del titolo.
			}	
		return titoloTrovato.toString();
	}

	private static boolean trovaLettera(String nome, char lettera, int j) {
		/*if(nome.charAt(j) == ' '){
			//System.out.println("certo che è uno spazio, mona!");			
		}*/
		return nome.charAt(j) == lettera;	
	}
	
	/*public static boolean trovaNumeri(String nome, int j){
		return (condizioneEpisodio (nome, j+1));		
	}*/
	
	public void stampaInfo(){
		System.out.printf("Titolo file: %s" + ".\n", nomeFile);
		System.out.printf("Estensione file: .%s" + ".\n", estensione);
		System.out.printf("Titolo serie TV: %s" + ".\n", titoloSerieTv);
		System.out.printf("Numero stagione: %d" + ".\n", stagione);
		System.out.printf("Numero episodio: %d" + ".\n", episodio);	
		System.out.printf("Nuovo titolo: %s" + ".\n", nuovoTitolo);	
		
	}

	public String getNomeFile() {
		// TODO Auto-generated method stub
		return nomeFile;
	}
	
	public String getNuovoTitolo(){
		return nuovoTitolo;
	}
	

	public void rinominaFile(String cartella, String titolo) {
		file.renameTo(new File (cartella , titolo));		
	}	
}
	
	
		