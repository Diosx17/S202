package search_engine;

import java.util.HashMap;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.io.BufferedReader;

public class IndexedPage 
{
	private String url; // url de la page à indexer
	private HashMap<String, Integer> words; // HashMap qui contient les mots de la page et leur nombre d'occurences
    public IndexedPage(String[] lines) 
    {
        this.url = lines[0]; // on recupere l'url qui se trouve a l'index  0 du tab
        this.words = new HashMap<String, Integer>(); // on initialise la HashMap
        for (int i=1; i < lines.length; ++i) // on boucle sur toutes les lignes sauf la première pour ne pas prendre l'url (donc i=1 et pas 0)
        {
            this.words.put(lines[i].split(":")[0],Integer.parseInt(lines[i].split(":")[1]));
        }
        
    }

    public IndexedPage(String text)
    {
        // Constructeur qui prend en parametre le texte d'une requete utilisateur
        this.words = new HashMap<String, Integer>(); // on initialise la HashMap
        this.url = null;
        String[] mots = text.split(" "); // on split le texte en mots
        for (int i=0; i < mots.length; ++i) // on boucle sur tous les mots
        {
            if(this.words.containsKey(mots[i])) // si le mot est deja dans la HashMap
            {
                this.words.put(mots[i],this.words.get(mots[i])+1); // on incremente le count
            }
            else
            {
                this.words.put(mots[i],1); // sinon on ajoute le mot a la HashMap avec un count de 1
            }
        }
       
    }

	public IndexedPage(Path path) 
    {
        this.words = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) 
        {
            String line = reader.readLine();
            this.url = line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(":");
                this.words.put(parts[0], Integer.parseInt(parts[1]));
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

	public String getUrl() // Getter
	{
		return this.url; // on retourne l'url de la page
	}

	public double getNorm() //Getter
    {
        int norm = 0; 
        for(String word : this.words.keySet())
        {
            norm += this.words.get(word) * this.words.get(word); // on calcule la norme
        }
        return Math.sqrt(norm); // on retourne la racine carree de la norme
    }

	public int getCount(String word) // Getter
    {
        if(this.words.containsKey(word)) // si le mot est dans la HashMap
        {
            return this.words.get(word); // on retourne le count
        }
        return 0; // sinon on retourne 0
    }

	public double getPonderation(String word) // Getter
    {
        if(this.words.containsKey(word)) // si le mot est dans la HashMap
        {
            return this.words.get(word) / this.getNorm(); // on retourne la ponderation
        }
        return 0; 
    }
	

	public double proximity(IndexedPage page)
    {
        double proximity = 0;
        for(String word : this.words.keySet())
        {
            proximity += this.getPonderation(word) * page.getPonderation(word); // on calcule la proximite
        }
        return proximity ; // on retourne la proximite divisee par la norme de la page et de la requete donc le cosinus de l'angle entre les deux vecteurs
    }

	public String toString() 
	{
		return "IndexedPage [url=" + this.url + "]"; // on affiche sous cette forme la page
	}
}