package search_engine;

import java.util.Arrays;
import java.nio.file.Path;
import java.nio.file.Paths; 
import java.io.File;

public class SearchEngine 
{
	private Path indexation_directory;
	private IndexedPage[] pages;
	
	public SearchEngine(Path indexation_directory)
	{
		this.indexation_directory=indexation_directory;
		this.pages = new IndexedPage[indexation_directory.toFile().listFiles().length];
		int i = 0;
		for(File file: indexation_directory.toFile().listFiles())
		{
			this.pages[i] = new IndexedPage(file.toPath());
			i++;
		}
	}
	
	public IndexedPage getPage(int i) 
	{
		return this.pages[i];
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
		IndexedPage requete = new IndexedPage(requestString);
		SearchResult[] results = new SearchResult[getPagesNumber()];
		for (int i=0; i<getPagesNumber(); i++)
		{
			IndexedPage page = getPage(i);
			results[i] = new SearchResult(page.getUrl(), page.proximity(requete));
			System.out.println(results[i].toString());
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
