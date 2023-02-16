package test03;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class test03 {

    //LectureFichier
    public static String lire(String nomFichier) throws Exception {
        String contenu = "";
        java.io.File fichier = new java.io.File(nomFichier);
        java.util.Scanner lecteur = new java.util.Scanner(fichier);
        while (lecteur.hasNextLine()) {
            contenu += lecteur.nextLine() + " ";
        }
        lecteur.close();

        return contenu;
    }

    //majuscule en minuscule
    public static String minuscule(String chaine) {
        String chaineMinuscule = "";
        for (int i = 0; i < chaine.length(); i++) {
            //si le caractère est une majuscule on le transforme en minuscule
            chaineMinuscule += Character.toLowerCase(chaine.charAt(i));
        }
        return chaineMinuscule;
    }

    //Fonction pour enlever tous les caractères n'étant pas des lettres
    public static String enleverCaracteresSpeciaux(String chaine) {
        String chaineSansCaracteresSpeciaux = "";
        for (int i = 0; i < chaine.length(); i++) {
            //si le caractère est une apostrophe ou des guillemets, on le remplace par un espace
            if (chaine.charAt(i) == '\'' || chaine.charAt(i) == '"' || chaine.charAt(i) == '’') {
                chaineSansCaracteresSpeciaux += " ";
            }
            //si le caractère est une lettre ou un espace on l'ajoute a la chaine
            else if (Character.isLetter(chaine.charAt(i)) || chaine.charAt(i) == ' ') {
                chaineSansCaracteresSpeciaux += chaine.charAt(i);
            }
        }
        return chaineSansCaracteresSpeciaux;
    }

    //Fonction permettant d'enlever les mots qui appartiennent à ListeNoire.txt
    public static String enleverMotsListeNoire(String chaine) throws Exception {
        String listeNoire = lire("S202/src/test03/ListeNoire.txt");
        listeNoire = enleverCaracteresSpeciaux(listeNoire);
        String[] listeNoireTableau = listeNoire.split(" ");
        String[] chaineTableau = chaine.split(" ");
        String chaineSansMotsListeNoire = "";
        for (int i = 0; i < chaineTableau.length; i++) {
            if (!Arrays.asList(listeNoireTableau).contains(chaineTableau[i])) {
                chaineSansMotsListeNoire += chaineTableau[i] + " ";
            }
        }
        return chaineSansMotsListeNoire;
    }

    //enlever mot de 1 et 2 lettres
    public static String enleverMotsPetits(String chaine) {
        String[] chaineTableau = chaine.split(" ");
        String chaineSansMotsPetits = "";
        for (int i = 0; i < chaineTableau.length; i++) {
            if (chaineTableau[i].length() > 2) {
                chaineSansMotsPetits += chaineTableau[i] + " ";
            }
        }
        return chaineSansMotsPetits;
    }

    //lemmatise
    public static String lemmatise(String chaine) throws Exception {
        String dictPath = "S202/src/test03/dictiofr.txt";
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
        String chaineLemmatisée = "";
        for(int i = 0; i < chaineTableau.length; i++) {
            // On regarde si le mot est dans la hashmap avec la fonction get
            String lemma = lemmaDict.get(chaineTableau[i]);
            // Si c'est le cas on ajoute le mot lemmatisé et un espace à la suite
            if (lemma != null) {
                chaineLemmatisée += lemma + " ";
            }
            // Sinon on ajoute le mot inchangé et un espace à la suite
            else {
                chaineLemmatisée += chaineTableau[i] + " ";
            }
        }
        return chaineLemmatisée;
    }

    public static void main(String[] args) throws Exception {
        //On ouvre le fichier a l'aide de la fonction lire
        //puis on va appeler les différentes fonctions
        //et on va afficher le contenu du fichier une fois les modifications effectuées
        String nomFichier = "S202/src/test03/test03.txt";
        String contenu = lire(nomFichier);
        contenu = enleverCaracteresSpeciaux(contenu);
        contenu = enleverMotsListeNoire(contenu);
        contenu = minuscule(contenu);
        contenu = enleverMotsPetits(contenu);
        System.out.println("Contenu du fichier " + nomFichier + " :\n" + contenu);
        //On lemmatise le fichier et on l'affiche une nouvelle fois
        contenu = lemmatise(contenu);
        System.out.println("Contenu du fichier lemmatisé " + nomFichier + " :\n" + contenu);
    }
}