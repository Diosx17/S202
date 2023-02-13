import search_engine.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class SearchEngineTests 
{
  public static void main(String[] args)  
  {
    /* 
    IndexedPage page1 = new IndexedPage(new String[] {"http://fr.example.org","hello:10", "world:5"});
    System.out.println(page1);
    System.out.println("l'url de la page : "+page1.getUrl());
    System.out.println(page1.getPonderation("hello")); 
    System.out.println(page1.getPonderation("other"));
    IndexedPage page2 = new IndexedPage(new String[] {"http://fr.example2.org", "hello:5", "france:2"});
    IndexedPage requete = new IndexedPage("hello hello hello hello hello france france");
    System.out.println("Le mot france dans la page requete : " + requete.getCount("france"));
    System.out.println("Le mot bonjour dans la page requete : " + requete.getCount("bonjour"));
    System.out.println("Degre de similarite des deux pages : " + page1.proximity(page2));
    System.out.println("Degre de similarite de la page 1 avec la page 2 :" + page1.proximity(page2));
    
    Path fichier_test = Paths.get("./INDEX/0.txt");
    IndexedPage requeteTestPath = new IndexedPage("sandwich");
    IndexedPage test_chemin = new IndexedPage(fichier_test);
    System.out.println("Le mot chausson dans la page test_chemin : " + test_chemin.getCount("sandwich"));
    System.out.println("Degre de similarite de la requete test avec le path : "+ requeteTestPath.proximity(test_chemin));
    
    SearchEngine moteur = new SearchEngine(Paths.get("./INDEX/"));
    System.out.println("Le nombre de pages indexees : " + moteur.getPagesNumber());
    */

    

    switch (args.length) {

      case 0:
      try ( Scanner scanner = new Scanner( System.in ) ) {
        SearchEngine moteur = new SearchEngine(Paths.get("./INDEX/"));
        while (true)
        { // true c'est pour faire une boucle infinie et s'arrêter uniquement quand on exit
            System.out.print("Veuillez saisir votre requete : (exit pour fermer)");
            String entree = scanner.nextLine();
            
            if (entree.equals("exit")){
              break;
            }
            
            SearchResult[] results = moteur.launchRequest(entree);
            System.out.println(Arrays.toString(results));
        }
      }
      break;

      default:
          String requete = "";
          for(int i = 0; i<args.length; ++i)
          {
              requete += args[i] + " ";
          }
          
          SearchEngine moteur = new SearchEngine(Paths.get("./INDEX/"));
          SearchResult[] results = moteur.launchRequest(requete);
          moteur.printResults(requete);
        break;
    }
      
  }
}
