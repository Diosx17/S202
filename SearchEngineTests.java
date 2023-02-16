import search_engine.*;

import java.nio.file.Paths;
import java.util.Scanner;

public class SearchEngineTests 
{
  public static void main(String[] args)  
  {
    switch (args.length) {

      case 0:
        try (Scanner scanner = new Scanner(System.in))
        {
          SearchEngine moteur = new SearchEngine(Paths.get("./INDEX/"));
          while (true)
          { // true c'est pour faire une boucle infinie et s'arrêter uniquement quand on exit
              System.out.print("Veuillez saisir votre requete : (exit pour fermer)");
              String entree = scanner.nextLine();
              
              if (entree.equals("exit"))
              {
                break;
              }
              moteur.printResults(entree);
              break;
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
}
