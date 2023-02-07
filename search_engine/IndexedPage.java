package search_engine;

import java.util.Arrays;

public class IndexedPage {
    private String url; // url de la page
    private String[] words; // tableau contenant les mots de la page indexe

    public IndexedPage(String[] lines) 
    {
        this.url = lines[0]; // on recupere l'url qui se trouve a l'index  0 du tab
        this.words = new String[lines.length - 1]; // on cree un tableau de caracteres de longueur des lignes-1 car on ne recupere pas l'url dans ce tableau
        for (int i=1; i < lines.length; ++i)
        {
            this.words[i-1] = lines[i]; // on place les mots dans le nouveau tableau
        }
        Arrays.sort(this.words); // on trie le tableaus

    }

    public IndexedPage(String text)
    {
        String[] lines = text.split(" "); // On cree un tableau de chaines de caracteres separe par des espaces
        this.url = lines[0]; // on recupere l'url qui se trouve a l'index  0 du tab
        this.words = new String[lines.length-1]; // on cree un tableau de caracteres de longueur des lignes-1 car on ne recupere pas l'url dans ce tableau
        for (int i=1; i < lines.length; ++i)
        {
            this.words[i-1] = lines[i]; // on place les mots dans le nouveau tableau
        }
        Arrays.sort(this.words); // on trie le tableaus

    }

    public String getUrl()
    {
        return this.url; // on retourne l'url de la page
    }

    public int getNorm()
    {
        int norm = 0; 
        for (String word : this.words) // on parcourt les mots du tableau
        {
            String[] split_array = word.split(":"); // on separe les elements de la forme "hello:5" en ["hello","5"] 
            int ponderation = Integer.parseInt(split_array[1]); // On initialise ponderation et on lui affecte le cast de la ponderation en entier
            norm += ponderation * ponderation; // ponderation au carre
        }
        return norm; 
    }
    
    public int getCount(String word)
    {
        for (String w : this.words) // on parcourt tous les mots du tableau
        {
            String[] split_array = w.split(":");// on separe les elements de la forme "hello:5" en ["hello","5"] 
            if (split_array[0].equals(word)) // on verifie si le mot correspond a l'argument
            {
                return Integer.parseInt(split_array[1]); // on retourne la ponderation avec un cast
            }
        }
        return 0;
    }
    public double getPonderation(String word)
    {
        for (String w : this.words) // on parcourt tous les mots du tableau
        {
            String[] split_array = w.split(":");// on separe les elements de la forme "hello:5" en ["hello","5"] 
            if (split_array[0].equals(word))// on verifie si le mot correspond a l'argument
            {
                return Integer.parseInt(split_array[1])/Math.sqrt(this.getNorm()); // on retourne la ponderation / par la norme 
            }
        }
        return 0; // sinon 0
    }
    public double proximity(IndexedPage page)
    {
        double proximity = 0;
        for (String word : this.words) // on parcourt tous les mots du tableau
        {
            String[] split_array = word.split(":");// on separe les elements de la forme "hello:5" en ["hello","5"] 
            proximity += this.getPonderation(split_array[0]) * page.getPonderation(split_array[0]); // on calcule le produit scalaire entre les deux vecteurs
        }
        return proximity;
    }

    public String toString()
    {
       return "IndexedPage [url=" + this.url +"]"; // on affiche sous cette forme la page
    }
}