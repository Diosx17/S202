package search_engine_tests;

import search_engine.*;

public class SearchEngineTests {
  public static void main(String[] args) 
  {
    IndexedPage page1 = new IndexedPage(new String[] {"http://fr.example.org","hello:10", "world:5"});
    System.out.println(page1);
    System.out.println("l'url de la page : "+page1.getUrl());
    System.out.println(page1.getPonderation("hello")); 
    System.out.println(page1.getPonderation("other"));
    IndexedPage page2 = new IndexedPage(new String[] {"http://fr.example2.org", "hello:5", "france:2"});
    IndexedPage requete = new IndexedPage("hello hello hello hello hello france france");
    System.out.println("Le mot france dans la page requête : " + requete.getCount("france"));
    System.out.println("Le mot bonjour dans la page requête : " + requete.getCount("bonjour"));
    System.out.println("Degre de similarite des deux pages : " + page1.proximity(page2));
    System.out.println("Degre de similarite de la requete avec la page 1 :" + page1.proximity(requete));
  }
}
