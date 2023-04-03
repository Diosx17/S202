package search_engine;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Arrays;
import java.net.URLDecoder;


public class SearchEngine 
{
	private Path indexation_directory;
	private ArrayList<IndexedPage> pages;
	
	public static void main(String[] args) throws Exception 
	{
		System.setProperty("file.encoding", "UTF-8");

		switch (args.length) {

			case 0:
			  try (Scanner scanner = new Scanner(System.in))
			  {
				
				SearchEngine moteur = new SearchEngine(Paths.get("./bin/txt/INDEX_FILES/"));
		
				
				while (true)
				{  
					// true c'est pour faire une boucle infinie et s'arrêter uniquement quand on exit
					
					System.out.print("Veuillez saisir votre requete : (exit pour fermer) ");
					String entree = scanner.nextLine();
					if (entree.equals("exit"))
					{
						break;
					}

	
				  
				}
				break;
				
		
			  }
			  
	  
			default:
				String requete = "";
				for(int i = 0; i<args.length; ++i)
				{
					requete += args[i] + " ";
				}
				
				SearchEngine moteur = new SearchEngine(Paths.get("./bin/txt/INDEX_FILES/"));
				moteur.printResults(requete);
			  break;
		  }
			
		
		
	}

	public SearchEngine(Path indexation_directory)
	{
		this.indexation_directory=indexation_directory;
		this.pages = new ArrayList<IndexedPage>();

		for(File fichier: this.indexation_directory.toFile().listFiles())
		{
			try 
			{
				this.pages.add(new IndexedPage(fichier.toPath()));
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public IndexedPage getPage(int i) 
	{
		return this.pages.get(i);
	}
	
	public int getPagesNumber() 
	{
		return this.pages.size();
	}
	
	public ArrayList<SearchResult> launchRequest(String requestString) 
	{
		HashMap<String, Double> resultContainer = new HashMap<String, Double>(); // Utilisation d'un HashMap car cela me permet de trier les valeurs 
		IndexedPage requete = new IndexedPage(requestString); 					//par ordre decroissant grace a la methode sort sans passer par un tableau de SearchResult avec lequel ca aurait ete plus fastidieux

		for(int i= 0;i<this.getPagesNumber();++i)
		{
			if(requete.proximity(this.getPage(i))>0)
			{
				
				resultContainer.put(URLDecoder.decode(this.getPage(i).getUrl(),StandardCharsets.UTF_8),requete.proximity(this.getPage(i))*100);
			}
		}	

		
		List<Map.Entry<String, Double>> listeDuHashmap = new ArrayList<>(resultContainer.entrySet()); // on cree une liste de Map.Entry qui contient les elements de la hashmap
		listeDuHashmap.sort(Map.Entry.comparingByValue(Comparator.reverseOrder())); // on trie la liste par ordre decroissant
		
		
		HashMap<String, Double> sortedContainer = new LinkedHashMap<>(); // on cree une nouvelle hashmap qui va contenir les elements de la liste trie, il faut qu'elle soit de type LinkedHashMap car elle garde l'ordre d'insertion
		for (Map.Entry<String, Double> entry : listeDuHashmap) // on ajoute les elements de la liste trie a la hashmap
		{
			sortedContainer.put(entry.getKey(), entry.getValue());
		}
		
	
		ArrayList<SearchResult> results = new ArrayList<>(); // on cree un tableau de SearchResult qui va contenir les elements de la hashmap trie
	
		for (Map.Entry<String, Double> MapEntree : sortedContainer.entrySet())  // on ajoute les elements de la hashmap trie au tableau de SearchResult
		{
			results.add(new SearchResult(MapEntree.getKey(), MapEntree.getValue()));
		}
	

		return results;
		
	}
	
	public void printResults(String requestString) 
	{
		ArrayList<SearchResult> resultats = launchRequest(requestString);
		if(resultats.size()<15)
		{
			for(SearchResult resultat: resultats)
			{
				System.out.println(resultat);
			}
		}
		else 
		{
			for(int i = 0; i<15; ++i)
			{
				System.out.println(resultats.get(i));
			}
		}
	

	
		
	}
	//LectureFichier
    public static String read(String nomFichier) throws Exception 
	{
        String contenu = "";
        File fichier = new File(nomFichier);
        Scanner lecteur = new Scanner(fichier);
        while (lecteur.hasNextLine()) {
            contenu += lecteur.nextLine() + " ";
        }
        lecteur.close();

        return contenu;
    }

    //Fonction pour enlever tous les caractères n'étant pas des lettres
    public static String removeSpecialCharacters(String chaine) throws IOException 
	{ 
        String chaineSansCaracteresSpeciaux = "";    
        for (int i = 0; i < chaine.length(); i++) 
		{
            //si le caractère est une apostrophe ou des guillemets, on le remplace par un espace
            if (chaine.charAt(i) == '\'' || chaine.charAt(i) == '"' || chaine.charAt(i) == '’' || chaine.charAt(i) == '.')
			{
                chaineSansCaracteresSpeciaux += " ";
            }
            //si le caractère est une lettre ou un espace ou un - on l'ajoute a la chaine (nous traiterons les '-' par la suite)
            else if (Character.isLetter(chaine.charAt(i)) || chaine.charAt(i) == ' ' || chaine.charAt(i) == '-') {
                chaineSansCaracteresSpeciaux += chaine.charAt(i);
            }
        }
        return chaineSansCaracteresSpeciaux;
    }

    //Fonction permettant d'enlever les mots qui appartiennent à ListeNoire.txt
    public static String removeWordsBlackList(String chaine) throws Exception 
	{
        String listeNoire = read("C:\\Users\\or201305\\Downloads\\src\\src\\search_engine\\blacklist.txt");
        listeNoire = removeSpecialCharacters(listeNoire);
        String[] listeNoireTableau = listeNoire.split(" ");
        String[] chaineTableau = chaine.split(" ");
        String chaineSansMotsListeNoire = "";
		//on ajoute seulement les mots qui ne sont pas dans la liste noire
        for (int i = 0; i < chaineTableau.length; i++) {
            if (!Arrays.asList(listeNoireTableau).contains(chaineTableau[i])) 
			{
                chaineSansMotsListeNoire += chaineTableau[i] + " ";
            }
        }
        return chaineSansMotsListeNoire;
    }

    //enlever mot de 1 et 2 lettres
    public static String removeSmallWords(String chaine) 
	{
        String[] chaineTableau = chaine.split(" ");
        String chaineSansMotsPetits = "";
		//on ajoute seulement les mots ayant une longueur superieure à 2
        for (int i = 0; i < chaineTableau.length; i++) 
		{
            if (chaineTableau[i].length() > 2) 
			{
                chaineSansMotsPetits += chaineTableau[i] + " ";
            }
        }
        return chaineSansMotsPetits;
    }

    //lemmatize
    public static String lemmatize(String chaine) throws Exception 
	{
        String dictPath = "C:\\Users\\or201305\\Downloads\\src\\src\\search_engine\\dictiofr.txt";
        // On crée la hashmap
        Map<String, String> lemmaDict = new HashMap<>();
        // On lit le fichier dictiofr.txt ligne par ligne
        for (String line : Files.readAllLines(Paths.get(dictPath), StandardCharsets.UTF_8)) 
		{
            // On split chaque ligne en deux séparées les ":" puis on ajoute le mot et sa lemmatisation dans
            // la hashmap
            String[] dictSplit = line.split(":");
            lemmaDict.put(dictSplit[0], dictSplit[1]);
        }
        String[] chaineTableau = chaine.split(" ");
        String chaineLemmatise = "";
        for(int i = 0; i < chaineTableau.length; i++) 
		{
            // On regarde si le mot est dans la hashmap avec la fonction get
            String lemma = lemmaDict.get(chaineTableau[i]);

            // Si c'est le cas on ajoute le mot lemmatisé et un espace à la suite
            if (lemma != null) {
				
                chaineLemmatise += lemma + " ";
            }
            // Sinon on ajoute le mot inchangé et un espace à la suite, et on split le mot en deux si il y a un -
			else 
			{
				if(chaineTableau[i].contains("-"))
				{
					String[] separate_words = chaineTableau[i].split("-"); 
					chaineLemmatise += separate_words[0] + " " + separate_words[1] + " ";
				}
				else
				{
					chaineLemmatise += chaineTableau[i] + " ";
				}
			}
        }
        return chaineLemmatise;
    }

	// Les fichiers contiennent des accents encodés différement des accents présents sur le clavier, on les remplace donc par ces derniers

	
	
}
