package subGestion;

import java.io.File;

public class FileVideo extends Elemento  {
	public final static String[] ESTENSIONE_VIDEO ={"avi", "mwp" , "mp4", "mkv", "flv"};

	public FileVideo(File file, String nomePulito) {
		super(file,nomePulito, ESTENSIONE_VIDEO);		
	}	
}
