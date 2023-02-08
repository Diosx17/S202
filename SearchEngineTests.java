import search_engine.*;

public class SearchEngineTests {
  public static void main(String[] args) 
  {
    IndexedPage page1 = new IndexedPage(new String[] {"http://fr.example.org", "hello:10", "world:5"});
    System.out.println(page1);
    System.out.println(page1.getPonderation("hello")); 
    System.out.println(page1.getPonderation("world"));
    IndexedPage page2 = new IndexedPage(new String[] {"http://fr.example2.org", "hello:5", "france:2"});
    System.out.println("Degre de similarite des deux pages: " + page1.proximity(page2));
  }
}
