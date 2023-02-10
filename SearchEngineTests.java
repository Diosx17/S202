import search_engine.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SearchEngineTests 
{
  public static void main(String[] args)  
  {
    IndexedPage page1 = new IndexedPage(new String[] {"http://fr.example.org","hello:10", "world:5"});
    System.out.println(page1);
    System.out.println("l'url de la page : "+page1.getUrl());
    System.out.println(page1.getPonderation("hello")); 
    System.out.println(page1.getPonderation("other"));
    IndexedPage page2 = new IndexedPage(new String[] {"http://fr.example2.org", "hello:5", "france:2"});
    IndexedPage requete = new IndexedPage("hello hello hello hello hello france france");
    System.out.println("Le mot france dans la page requ�te : " + requete.getCount("france"));
    System.out.println("Le mot bonjour dans la page requ�te : " + requete.getCount("bonjour"));
    System.out.println("Degre de similarite des deux pages : " + page1.proximity(page2));
    System.out.println("Degre de similarite de la requete avec la page 1 :" + page1.proximity(page2));
    
    Path fichier_test = Paths.get("./INDEX/0.txt");
    IndexedPage requeteTestPath = new IndexedPage("chausson");
    IndexedPage test_chemin = new IndexedPage(fichier_test);
    System.out.println("Le mot chausson dans la page test_chemin : " + test_chemin.getCount("chausson"));
    System.out.println("Degre de similarite de la requete test avec le path : "+ requeteTestPath.proximity(test_chemin));
    
    SearchEngine moteur = new SearchEngine(Paths.get("./INDEX/"));
    System.out.println("Le nombre de pages indexees : " + moteur.getPagesNumber());

    System.out.println("Le contenu de la page 0 : " + moteur.getPage(0));

    moteur.launchRequest("vikidia.org chausson pomme");

  }
}
