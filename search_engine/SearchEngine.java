package search_engine;

import java.util.Collections;
import java.util.ArrayList;
import java.nio.file.Path;
import java.io.File;
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
		
		IndexedPage requete = new IndexedPage(requestString);
		ArrayList<Double> scores2 = new ArrayList<Double>();
		ArrayList<String> urls = new ArrayList<String>();
	
		
		for(int i = 0; i<getPagesNumber(); ++i)
		{
			IndexedPage page = getPage(i);
			if(requete.proximity(page)>0.0)
			{
				scores2.add(requete.proximity(page));
		
			}
		}
		Collections.sort(scores2,Collections.reverseOrder());
		
		
		// LE PROBLEME EST EN DESSOUS
		
		
		double[] scores = new double[resultats.size()];
		SearchResult[] results = new SearchResult[resultats.size()];
		
		for(int i = 0; i<scores2.size(); ++i)
		{
			scores[i] = scores2.get(i);
		}

		

		for(int i = 0;i<scores.length;++i)
		{
			IndexedPage page = getPage(i);
			results[i] = new SearchResult(page.getUrl(), scores[i]);

		}

		// LE PROBLEME EST AU DESSUS
		return results;	
		
	}
	
	public void printResults(String requestString) 
	{
		SearchResult[] results = launchRequest(requestString);
		for (int i=0; i<results.length; i++)
		{
			System.out.println(results[i]);
		}
		
	}

	
	
}
