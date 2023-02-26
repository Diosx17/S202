import search_engine.*;

import java.nio.file.Paths;
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
    request.getCount("cerise");
    request.getCount("flan");
    se.printResults(requestString);

    IndexedPage request2 = new IndexedPage(new String[] {"https://vikidia.org/wiki/Flan cerise:1 flan:1"});
    request2.getCount("cerise");
    request2.getCount("flan");
    System.out.println(request2);
    
    SearchResult[] tableau_resultats = se.launchRequest(requestString);
    for (SearchResult result : tableau_resultats)
    {
      System.out.println(result.getUrl()+" : " + result.getScore());
    }
  
  }
}
