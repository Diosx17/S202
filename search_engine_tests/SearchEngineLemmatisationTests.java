package search_engine_tests;

import search_engine.*;
import java.nio.file.Path;
import java.nio.file.Paths;
public class SearchEngineLemmatisationTests{

    public static void main(String[] args) throws Exception
    {
       
        String chaineNormal = "aujourd'hui Bonjour j'adore coder en JAVA! car c'est pas mal, et peut-elle que : je vais faire un projet avec ça.";
        String chaineModif = testFonctions(chaineNormal);
    }

    public static String testFonctions(String chainetexte) throws Exception
    {
        System.out.println("Chaine de base : \n" + chainetexte + "\n");
        chainetexte = SearchEngine.lowercase(chainetexte);
        System.out.println("Chaine en minuscule : \n"+chainetexte +"\n");
        chainetexte = SearchEngine.removeSpecialCharacters(chainetexte);
        System.out.println("Chaine sans caractères spéciaux (+ modifs précédentes): \n"+chainetexte+ "\n");
        chainetexte = SearchEngine.removeWordsBlackList(chainetexte);
        System.out.println("Chaine sans mots de la liste noire (+ modifs précédentes): \n"+chainetexte+ "\n");
        chainetexte =SearchEngine.lemmatize(chainetexte);
        System.out.println("Chaine lemmatisée (+ modifs précédentes): \n"+chainetexte + "\n");
        chainetexte = SearchEngine.removeSmallWords(chainetexte);
        System.out.println("Chaine sans mots de 1 et 2 lettres (+ modifs précédentes): \n"+chainetexte+ "\n");
        return chainetexte;
    }



}