package subGestion;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.ServizioFile;
import java.io.File;
import java.io.IOException;

public class AlbzConfiguratorMain {
	final static String DIRECTORY="D:\\download\\9";
	private static final String CORNICE = "---------------------------------------------------";
	private static final String ERRORE_ESTENSIONE_FILE = "ERRORE: l'estensione del file %s  non � compatibile o conosciuta\n";
	private static final String NOME_BACKUP_VIDEO ="ZZZ - Backup nomi file video.dat";
	private static final String NOME_BACKUP_SOTTOTITOLI ="ZZZ - Backup nomi file sottotitoli.dat";
	private static final String RICHIESTA_BACKUP = "Vuoi effettuare un backup dei titoli dei file?";
	private static final String RICHIESTA_RINOMINA_DA_BACKUP = "Vuoi rinominare i file con i titoli presenti in backup?";
	private static final String MSG_NO_CAST = "ATTENZIONE: problemi con il cast";
	private static final String MSG_NO_CARICAMENTO = "ATTENZIONE: caricamento dal backup non riuscito.";
	private static final String MSG_BYE = "\nLa modifica dei nomi dei file � stata completata.\nGrazie per aver scelto subGestion,\narrivederci!!!";	
	//private static final String ERRORE_NOME = "C'� stato un errore sul rilevamento del nome della serie TV";
	
	static File directory;
	static File file[];
	public static Elemento[] fileVideo;
	public static Elemento[] fileSottotitoli;
	public static String titoloSerieTv;
	public static boolean COMMENTI = false;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {			 
	/*	try{
			File f = new File ("C:\\Users\\vivenzi\\Desktop", "prova.txt");
			f.createNewFile();
			System.out.println(f.getCanonicalPath());
			}
		/*catch(IOException e){
			System.out.println("Problema durante la creazione del file");
		}*/
		file = creaListaFiles();
		fileVideo = new FileVideo[contaEstensione(FileVideo.ESTENSIONE_VIDEO)];
		fileSottotitoli= new FileSottotitoli[contaEstensione(FileSottotitoli.ESTENSIONE_SOTTOTITOLI)];
		
		System.out.println("is Directory?\t" + directory.isDirectory());
		System.out.println("is File?\t" + directory.isFile());
		
		creaElementi();
		
		stampaInfo(fileVideo);
		stampaInfo(fileSottotitoli);
		
		gestisciRinominazioneFile();
		
		System.out.println(MSG_BYE);
		/*titoloSerieTv = titoloSerieTv();
		//sostituisciNomi();		
		for (File file : directory.listFiles())
		{
			if (file.getName().equals("prova.txt"));
			{
				File prova2 = new File (DIRECTORY,"prova2.txt");
				file.renameTo(prova2);
			}
		}*/			
	}
	
	private static void gestisciRinominazioneFile() {	
		rinominaFile(fileSottotitoli);
		rinominaFile(fileVideo);
		
		if (InputDati.yesOrNo(RICHIESTA_BACKUP)){
			BackUpNomi(fileVideo);
			BackUpNomi(fileSottotitoli);
		}		
		
		if (InputDati.yesOrNo(RICHIESTA_RINOMINA_DA_BACKUP)){
			rinominaDaBackUp(fileSottotitoli);		
		}		
	}

	private static void rinominaDaBackUp(Elemento[] elemento) {
		ContenitoreTitoli contenitore=null;
		String [] nomiFile = null;
		
		File fileBackUp = creaFileBackUp(elemento);		
		try 
		  {			
			contenitore = (ContenitoreTitoli) ServizioFile.caricaSingoloOggetto(fileBackUp);
			nomiFile = new String [contenitore.getNomiFile().length];
			nomiFile = contenitore.getNomiFile();	
			
		   }
		  catch (ClassCastException e)
		   {
			 System.out.println(MSG_NO_CAST);
			}
	
		 finally
			{
		      if ( nomiFile != null)
		      {
		    	  ririnominaFile(elemento, nomiFile);
				 }
		      else{
		    	  System.out.println(MSG_NO_CARICAMENTO);
		      }
		}
	}

	private static void rinominaFile(Elemento[] elemento) {
		for (int i = 0; i < elemento.length ; i++){
			elemento[i].rinominaFile(DIRECTORY, elemento[i].getNuovoTitolo());
		}
	}
	
	private static void ririnominaFile(Elemento[] elemento, String[] titolo) {
		for (int i = 0; i < titolo.length ; i++){
			System.out.println(titolo[i]);			
			elemento[i].rinominaFile(DIRECTORY, titolo[i]);
		}
	}

	public static void BackUpNomi(Elemento[] elemento) {
		ContenitoreTitoli contenitore = null;
		int numerositaElemento= elemento.length;
		File fileBackUp;		
		String[] nomiFile = new String [numerositaElemento];
		
		for (int i = 0 ; i< numerositaElemento ; i++){
			nomiFile[i]=elemento[i].getNomeFile();
		}
		contenitore= new ContenitoreTitoli(nomiFile);
		fileBackUp= creaFileBackUp(elemento);
		if (!fileBackUp.exists()){
			ServizioFile.salvaSingoloOggetto(fileBackUp , contenitore);
		}
	}

	private static File creaFileBackUp(Elemento[] elemento) {
			if (elemento == fileVideo){
			return  new File(DIRECTORY , NOME_BACKUP_VIDEO);
			}
			if (elemento == fileSottotitoli){
			return  new File(DIRECTORY , NOME_BACKUP_SOTTOTITOLI);
			}
		return null;
	}

	private static int contaEstensione(String[] ESTENSIONE) {
		int dimensione=0;
		for(int i=0; i<file.length; i++){
			String nomeFileIntero = file[i].getName();
			for (int j=0; j<ESTENSIONE.length;j++){
				if (nomeFileIntero.endsWith(ESTENSIONE[j])){
					dimensione++;
				}
			}
		}		
		return dimensione;
	}
	
/** istanza di prova per poter visualizzare i titoli dei file*/
	private static void stampaInfo(Elemento[] elemento) {
		//System.out.println(fileVideo[0].nomePulito);
		for(int i = 0 ; i < elemento.length; i++){
			//System.out.println(fileVideo[i].nomePulito);
			//System.out.println(fileVideo.length);
			elemento[i].stampaInfo();
			System.out.println(CORNICE);
		}
	}
	
	private static String standardizzaNomeFile(String nomeFile) {
		nomeFile = nomeFile.toLowerCase();
		nomeFile=nomeFile.replace('.',' ');
		nomeFile=nomeFile.replace('-',' ');
		return nomeFile;
	}
	
	public static  void creaElementi() {
		String[] nomeFile = new String[file.length];
		int indiceVideo=0;
		int indiceSottotitoli=0;
		for(int i=0; i < file.length; i++){
		//	System.out.println(file[i].getName());
			File esaminato = file[i];
			//System.out.println(file.length);
			nomeFile[i] = standardizzaNomeFile(esaminato.getName());
			//System.out.println(nomeFile[i]);
			boolean estensioneTrovata=false;
			for (int j =0; j < FileVideo.ESTENSIONE_VIDEO.length;j++){					
				if(nomeFile[i].endsWith(FileVideo.ESTENSIONE_VIDEO[j])){
					fileVideo[indiceVideo]= new FileVideo(esaminato, nomeFile[i]);	
					indiceVideo++;
					estensioneTrovata=true;
				}
			}			
			for (int j =0; j < FileSottotitoli.ESTENSIONE_SOTTOTITOLI.length;j++){	
				if(nomeFile[i].endsWith(FileSottotitoli.ESTENSIONE_SOTTOTITOLI[j])){
					fileSottotitoli[indiceSottotitoli]= new FileSottotitoli(esaminato,nomeFile[i]);
					indiceSottotitoli++;
					estensioneTrovata=true;
				}					
			}
			if (!estensioneTrovata && COMMENTI){
				System.out.printf(ERRORE_ESTENSIONE_FILE, file[i].getName());
			}
		}			
	}

	/*
	private static void cercaNomeSimile(String nomeFile, int i) {
		String titolo =titoloSerieTv();
		System.out.println(titolo);
	}*/
	
	/*private String titoloSerieTv() {
		boolean fineTrovaTitolo[] = null;
		int fineParola = 0 ;
		int inizioParola = 0;
		StringBuffer titoloInRicerca = null;	
		String[] titoloTrovato = null;
		//String[] nomeFile = trovaNomeFile();
		
		//titoloTrovato.append(Character.toUpperCase(nome.charAt(inizioParola)));
		//inizioParola++;
		for (int i = 0; i < nomeFile.length; i++){
			fineTrovaTitolo[i]=false;
			while (!fineTrovaTitolo[i]){		 
				if (trovaSpazio(nomeFile[i], fineParola)){				
					titoloInRicerca.append(Character.toUpperCase(nomeFile[i].charAt(inizioParola)));
					inizioParola++;
					titoloInRicerca.append(nomeFile[i].substring(inizioParola ,fineParola));				
					if(trovaNumeri(nomeFile[i], fineParola)){
						fineTrovaTitolo[i]=true;
					}
					else {								
					//titoloTrovato.append(nome.substring(inizioParola ,fineParola));
						inizioParola=fineParola+1;
						titoloInRicerca.append(" ");				
					}
				}			
			fineParola++;
			}
			if	(!fineTrovaTitolo[i]){
				System.out.println(ERRORE_NOME);
			}
			titoloTrovato[i]=titoloInRicerca.toString();
			titoloInRicerca=null;
			fineParola = 0 ;
			inizioParola = 0;
		}
		return titoloTrovato.toString();
	}*/
	
	private static File[] creaListaFiles() {
		directory = new File (DIRECTORY);		
		return directory.listFiles();		
	}
	
	public static boolean trovaNumeri(String nome, int j){
		return (!Character.isLetter(nome.charAt(j+1)) || nome.charAt(j+1)=='e' || nome.charAt(j+1)=='s');		
	}	
}
