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


public class SearchEngine 
{
	private Path indexation_directory;
	private IndexedPage[] pages;
	
	public static void main(String[] args) throws Exception 
	{
		System.setProperty("file.encoding", "UTF-8");

		switch (args.length) {

			case 0:
			  try (Scanner scanner = new Scanner(System.in))
			  {
				SearchEngine moteur = new SearchEngine(Paths.get("./txt/INDEX/"));
				while (true)
				{ // true c'est pour faire une boucle infinie et s'arrêter uniquement quand on exit
					System.out.print("Veuillez saisir votre requete : (exit pour fermer) ");
					String entree = scanner.nextLine();
					
					if (entree.equals("exit"))
					{
					  break;
					}
					moteur.printResults(entree);
				  
				}
				break;
			  }
			  
	  
			default:
				String requete = "";
				for(int i = 0; i<args.length; ++i)
				{
					requete += args[i] + " ";
				}
				
				SearchEngine moteur = new SearchEngine(Paths.get("./INDEX/"));
				moteur.printResults(requete);
			  break;
		  }
			
		
		
	}

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
		
		
		HashMap<String, Double> sortedContainer = new LinkedHashMap<>(); // on cree une nouvelle hashmap qui va contenir les elements de la liste triee, il faut qu'elle soit de type LinkedHashMap car elle garde l'ordre d'insertion
		for (Map.Entry<String, Double> entry : listeDuHashmap) // on ajoute les elements de la liste trie a la hashmap
		{
			sortedContainer.put(entry.getKey(), entry.getValue());
		}
		
	
		SearchResult[] results = new SearchResult[sortedContainer.size()]; // on cree un tableau de SearchResult qui va contenir les elements de la hashmap triee
		int i = 0;
		for (Map.Entry<String, Double> MapEntree : sortedContainer.entrySet())  // on ajoute les elements de la hashmap triee au tableau de SearchResult
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
	//LectureFichier
    public static String read(String nomFichier) throws Exception {
        String contenu = "";
        File fichier = new File(nomFichier);
        Scanner lecteur = new Scanner(fichier);
        while (lecteur.hasNextLine()) {
            contenu += lecteur.nextLine() + " ";
        }
        lecteur.close();

        return contenu;
    }

    //majuscule en minuscule
    public static String lowercase(String chaine) {
        String chaineMinuscule = "";
        for (int i = 0; i < chaine.length(); i++) {
            //si le caractère est une majuscule on le transforme en minuscule
            chaineMinuscule += Character.toLowerCase(chaine.charAt(i));
        }
        return chaineMinuscule;
    }

    //Fonction pour enlever tous les caractères n'étant pas des lettres
    public static String removeSpecialCharacters(String chaine) throws IOException { 
        String chaineSansCaracteresSpeciaux = "";    
        for (int i = 0; i < chaine.length(); i++) {
            //si le caractère est une apostrophe ou des guillemets, on le remplace par un espace
            if (chaine.charAt(i) == '\'' || chaine.charAt(i) == '"' || chaine.charAt(i) == '’')
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
    public static String removeWordsBlackList(String chaine) throws Exception {
        String listeNoire = read("./txt/blacklist.txt");
        listeNoire = removeSpecialCharacters(listeNoire);
        String[] listeNoireTableau = listeNoire.split(" ");
        String[] chaineTableau = chaine.split(" ");
        String chaineSansMotsListeNoire = "";
		//on ajoute seulement les mots qui ne sont pas dans la liste noire
        for (int i = 0; i < chaineTableau.length; i++) {
            if (!Arrays.asList(listeNoireTableau).contains(chaineTableau[i])) {
                chaineSansMotsListeNoire += chaineTableau[i] + " ";
            }
        }
        return chaineSansMotsListeNoire;
    }

    //enlever mot de 1 et 2 lettres
    public static String removeSmallWords(String chaine) {
        String[] chaineTableau = chaine.split(" ");
        String chaineSansMotsPetits = "";
		//on ajoute seulement les mots ayant une longueur superieure à 2
        for (int i = 0; i < chaineTableau.length; i++) {
            if (chaineTableau[i].length() > 2) {
                chaineSansMotsPetits += chaineTableau[i] + " ";
            }
        }
        return chaineSansMotsPetits;
    }

    //lemmatize
    public static String lemmatize(String chaine) throws Exception {
        String dictPath = "txt/dictiofr.txt";
        // On crée la hashmap
        Map<String, String> lemmaDict = new HashMap<>();
        // On lit le fichier dictiofr.txt ligne par ligne
        for (String line : Files.readAllLines(Paths.get(dictPath), StandardCharsets.UTF_8)) {
            // On split chaque ligne en deux séparées les ":" puis on ajoute le mot et sa lemmatisation dans
            // la hashmap
            String[] dictSplit = line.split(":");
            lemmaDict.put(dictSplit[0], dictSplit[1]);
        }
        String[] chaineTableau = chaine.split(" ");
        String chaineLemmatise = "";
        for(int i = 0; i < chaineTableau.length; i++) {
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
	
	
}
