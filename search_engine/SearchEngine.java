package search_engine;

import java.util.Arrays;
import java.nio.file.Path;
import java.nio.file.Paths; 
import java.io.File;

public class SearchEngine 
{
	private Path indexation_directory;
	private IndexedPage[] pages; // attention IndexedPage ou IndexedPages ? 
	
	public SearchEngine(Path indexation_directory)
	{
		this.indexation_directory=indexation_directory;
	}
	
	public IndexedPage getPage(int i) 
	{
		//Paths.get concat�ne les deux cha�nes pour construire un chemin de type Path 
		Path file_path = Paths.get(indexation_directory.toString(), Integer.toString(i)); // on cr�� file_path de type Path qui sera �gal au dossier suivi de l'entier du fichier
		return new IndexedPage(file_path);
	}
	
	public int getPagesNumber() 
	{
		File[] files = indexation_directory.toFile().listFiles(); // on cr�� un tableau de fichiers qui contient tous les fichiers du dossier indexation_directory
		int count = 0;
		for (File file : files) {
		  if (file.isFile()) {
		    count++;
		  }
		}
		return count; 
	}
	
	public SearchResult[] launchRequest(String requestString) 
	{
		IndexedPage request = new IndexedPage(requestString);
		SearchResult[] results = new SearchResult[getPagesNumber()];
		for (int i=0; i<getPagesNumber(); i++)
		{
			IndexedPage page = getPage(i);
			results[i] =  new SearchResult(page, page.getScore(request));
		}
		Arrays.sort(results);
		return results;		
		
	}
	
	public void printResults(String requestString) 
	{
		SearchResult[] results = launchRequest(requestString);
		for (int i=0; i<results.length; i++)
		{
			System.out.println(results[i].toString());
		}
		
	}
	
}
