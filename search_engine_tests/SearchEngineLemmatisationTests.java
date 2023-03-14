package search_engine_tests;

import search_engine.*;

public class SearchEngineLemmatisationTests{

    public static void main(String[] args) throws Exception
    {
        /*
         * Les tests sont effectués sur la chaine de caractères suivante :
         * - 1 on met les mots en minuscule
         * - 2 on enlève les caractères spéciaux
         * - 3 on enlève les mots de la liste noire
         * - 4 on enlève les mots de 1 et 2 lettres
         * - 5 on lemmatise la chaine en associant à chaque mot son lemme, si un mot contient un tiret après la lemmatisation,
         *  cela signifie qu'aucun lemme ne correspond à ce mot et donc nous séparons celui-ci en deux mots distincts (suppresion du tiret)
        
         */
        String chaineNormal = "salut-boui abaisse-langue adjudants-chefs abat-jour salut-salut vociférera Bonjour yacht-club j'adore coder en JAVA! car c'est pas mal, et peut-elle que : je vais faire un projet avec ça.";
        String chaineModif = testFonctions(chaineNormal);
        System.out.println("Voici la chaine finale :\n" + chaineModif + "\n");
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
        chainetexte = SearchEngine.removeSmallWords(chainetexte);
        System.out.println("Chaine sans mots de 1 et 2 lettres (+ modifs précédentes): \n"+chainetexte+ "\n");
        chainetexte =SearchEngine.lemmatize(chainetexte);
        System.out.println("Chaine lemmatisée (+ modifs précédentes): \n"+chainetexte + "\n");
        return chainetexte;
    }



}