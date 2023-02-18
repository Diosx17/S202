package search_engine;

import java.util.*;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;

public class IndexedPage 
{
	private String url; // url de la page à indexer
	private String[] words; // tableau contenant les mots de la page indexe
	private int[] counts; // tableau contenant le nombre de fois que chaque mot apparait dans la page indexe

    public IndexedPage(String[] lines) 
    {
        this.url = lines[0]; // on recupere l'url qui se trouve a l'index  0 du tab
        this.words = new String[lines.length - 1]; // on cree un tableau de caracteres de longueur des lignes-1 car on ne recupere pas l'url dans ce tableau
        this.counts = new int[lines.length - 1]; 
        for (int i=1; i < lines.length; ++i) // on boucle sur toutes les lignes sauf la première pour ne pas prendre l'url (donc i=1 et pas 0)
        {
            this.words[i-1] = lines[i].split(":")[0]; // on place les mots dans le nouveau tableau (indice i-1 pour pallier au décalage de la boucle)
            this.counts[i-1] = Integer.parseInt(lines[i].split(":")[1]); // on place les counts dans le nouveau tableau 
        }
        
    }

    public IndexedPage(String text)
    {
        this.words = text.split(" "); // on recupere les mots de la requete
        this.counts = new int[this.words.length]; // on cree un tableau de counts de longueur le nombre de mots
        
        for (int i=0; i < this.words.length; ++i) // on boucle sur tous les mots
        {
            this.counts[i] = 1; // on initialise le count a 1
            for (int j=0; j < i; ++j) // on boucle sur tous les mots precedents
            {
                if (this.words[i].equals(this.words[j])) // si le mot est le meme que le mot precedent
                {
                    this.counts[j] += 1; // on incremente le count du mot precedent
                    this.counts[i] = 0; // on met le count du mot a 0
                    break; // on sort de la boucle
                }
            }
        }
        
    }

	public IndexedPage(Path path) 
	{
		try 
		{
			List<String> lines = Files.readAllLines(path); // on recupere toutes les lignes du fichier
			this.url = lines.get(0); // on recupere l'url qui se trouve a l'index  0 du tab
			this.words = new String[lines.size() - 1]; // on cree un tableau de caracteres de longueur des lignes-1 car on ne recupere pas l'url dans ce tableau
			this.counts = new int[lines.size() - 1]; 
			for (int i=1; i < lines.size(); ++i) // on boucle sur toutes les lignes sauf la première pour ne pas prendre l'url (donc i=1 et pas 0)
			{
				this.words[i-1] = lines.get(i).split(":")[0]; // on place les mots dans le nouveau tableau (indice i-1 pour pallier au décalage de la boucle)
				this.counts[i-1] = Integer.parseInt(lines.get(i).split(":")[1]); // on place les counts dans le nouveau tableau 
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
        for (int i=0; i < this.counts.length; ++i) // on boucle sur tous les counts
        {
            norm += this.counts[i] * this.counts[i]; // on calcule la norme
        }
        return Math.sqrt(norm); // on retourne la racine carree de la norme
    }

	public int getCount(String word) // Getter
    {
        for(int i = 0;i<this.words.length;++i)
        {
            if(this.words[i].equals(word))
            {
                return this.counts[i]; // on retourne le count du mot passe en parametre
            }
        }
        
        return 0;
    }

	public double getPonderation(String word) // Getter
    {
       for(int i =0;i<this.words.length;++i) // on boucle sur tous les mots
       {
            if(this.words[i].equals(word)) // si le mot est le meme que celui passe en parametre
            {
                return this.counts[i]/this.getNorm(); // on retourne le count divise par la norme
            }
       }
        return 0; 
    }
	

	public double proximity(IndexedPage page)
    {
        double proximity = 0;
        for (String word : this.words) // pour tous les mots de la page
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