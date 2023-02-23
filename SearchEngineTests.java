import search_engine.*;

import java.nio.file.Paths;
import java.util.Scanner;
import java.net.URL;
import java.nio.file.Path;

public class SearchEngineTests 
{
  public static void main(String[] args) throws Exception
  {
    

    URL location = SearchEngine.class.getProtectionDomain().getCodeSource().getLocation();
    Path binFolder = Paths.get(location.toURI());
    Path indexFolder = binFolder.resolve("INDEX");

    // On crée un moteur de recherche.
    SearchEngine se = new SearchEngine(indexFolder);
   
    // On crée une requête.
    String requestString = "cerise flan";
    IndexedPage request = new IndexedPage(requestString);
    se.printResults(requestString);
  }
}
