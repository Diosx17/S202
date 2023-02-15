package search_engine;

import java.util.Collections;
import java.util.ArrayList;
import java.nio.file.Path;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

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
		File[] files = indexation_directory.toFile().listFiles(); // on cree un tableau de fichiers qui contient tous les fichiers du dossier indexation_directory
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
		HashMap<String, Double> resultContainer = new HashMap<String, Double>(); // Utilisation d'un HashMap car cela me permet de trier les valeurs 
		IndexedPage requete = new IndexedPage(requestString); 					//par ordre decroissant grace a la methode sort sans passer par un tableau de SearchResult avec lequel ca aurait ete plus fastidieux

		for(int i= 0;i<this.getPagesNumber();++i)
		{
			if(requete.proximity(this.getPage(i))>0)
			{
				resultContainer.put(this.getPage(i).getUrl(),requete.proximity(this.getPage(i)));
			}
		}	

		
		
		
	

		return results;
		
	}
	
	public void printResults(String requestString) 
	{
		SearchResult[] results = launchRequest(requestString);
		for (int i=0; i<15; i++)
		{
			if(results[i]!=null)
				System.out.println(results[i]);
			
		}
		
	}

	
	
}
