package search_engine;

import java.nio.file.Path;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

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
				resultContainer.put(this.getPage(i).getUrl(),requete.proximity(this.getPage(i))*100);
			}
		}	

		
		List<Map.Entry<String, Double>> listeDuHashmap = new ArrayList<>(resultContainer.entrySet()); // on cree une liste de Map.Entry qui contient les elements de la hashmap
		listeDuHashmap.sort(Map.Entry.comparingByValue(Comparator.reverseOrder())); // on trie la liste par ordre decroissant
		
		
		HashMap<String, Double> sortedContainer = new LinkedHashMap<>(); // on cree une nouvelle hashmap qui va contenir les elements de la liste trie
		for (Map.Entry<String, Double> entry : listeDuHashmap) // on ajoute les elements de la liste trie a la hashmap
		{
			sortedContainer.put(entry.getKey(), entry.getValue());
		}
		
	
		SearchResult[] results = new SearchResult[sortedContainer.size()]; // on cree un tableau de SearchResult qui va contenir les elements de la hashmap trie
		int i = 0;
		for (Map.Entry<String, Double> MapEntree : sortedContainer.entrySet())  // on ajoute les elements de la hashmap trie au tableau de SearchResult
		{
			results[i] = new SearchResult(MapEntree.getKey(), MapEntree.getValue());
			i++;
		}
	

		return results;
		
	}
	
	public void printResults(String requestString) 
	{
		SearchResult[] results = launchRequest(requestString);
		if(results.length<15)
		{
			for(int i = 0;i<results.length;++i)
			{
				System.out.println(results[i]);
			}
		}
		else
		{
			for (int i=0; i<15; i++)
			{
				System.out.println(results[i]);
				
			}
		}
		
		
	}

	
	
}
