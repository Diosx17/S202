package search_engine;

import java.util.Arrays;
import java.nio.file.Path;
import java.nio.file.Paths; 

public class SearchEngine {
	private Path indexation_directory;
	private IndexedPage[] pages; // attention IndexedPage ou IndexedPages ? 
	
	public SearchEngine(Path indexation_directory)
	{
		this.indexation_directory=indexation_directory;
	}
	
	public IndexedPage getPage(int i) {
		//Paths.get concatène les deux chaînes pour construire un chemin de type Path 
		Path file_path = Paths.get(indexation_directory.toString(), Integer.toString(i)); // on créé file_path de type Path qui sera égal au dossier suivi de l'entier du fichier
		return IndexedPage(file_path);
	}
	
	public int getPagesNumber() {
		File[] files = indexation_directory.listFiles();
		int count = 0;
		for (File file : files) {
		  if (file.isFile()) {
		    count++;
		  }
		}
		return count; 
	}
	
	public SearchResult[] launchRequest(String requestString) {
		
	}
	
	public void printResults(String requestString) {
		
	}
	
}
