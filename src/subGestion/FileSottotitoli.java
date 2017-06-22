package subGestion;

import java.io.File;

public class FileSottotitoli extends Elemento {
	
	public final static String[] ESTENSIONE_SOTTOTITOLI ={"srt", "ass"};
	
	public FileSottotitoli(File file, String nomePulito) {
		super(file, nomePulito, ESTENSIONE_SOTTOTITOLI);
	}
}
