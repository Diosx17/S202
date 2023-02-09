package search_engine;

import java.util.*;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;

public class IndexedPage {
	private String url; // url de la page à indexer
	private String[] words; // tableau contenant les mots de la page indexe

	public IndexedPage(String[] lines) {
		this.url = lines[0]; // on recupere l'url qui se trouve a l'index 0 du tab
		this.words = new String[lines.length - 1]; // on cree un tableau de caracteres de longueur des lignes-1 car on
													// ne recupere pas l'url dans ce tableau
		for (int i = 1; i < lines.length; ++i) // on boucle sur toutes les lignes sauf la première pour ne pas prendre
												// l'url (donc i=1 et pas 0)
		{
			this.words[i - 1] = lines[i]; // on place les mots dans le nouveau tableau (indice i-1 pour palier au
											// décalage de la boucle)
		}
		Arrays.sort(this.words); // on trie le tableau par ordre croissant
	}

	public IndexedPage(String text) {
		this.words = text.split(" "); // on recupere les mots de la requête
		int count_words = this.words.length;
		String[] lines = new String[count_words]; // on créé le tableau lines de longueur count_words
		Arrays.sort(this.words); // on trie le tableau
		int count = 1; // nombre de mots égaux consécutifs
		int compteur_mots = 0; // permet de compter le nombre de mots différents

		for (int i = 0; i < this.words.length; ++i) {
			if (i == this.words.length - 1) // si on est a la fin du tableau
			{
				lines[compteur_mots] = this.words[i] + ":" + count; // on ajoute le mot et sa ponderation
				compteur_mots++;
			} else if (this.words[i].equals(this.words[i + 1])) // si le mot suivant est le meme que le mot actuel
			{
				count++; // on incremente le compteur
			} else // sinon
			{
				lines[compteur_mots] = this.words[i] + ":" + count; // on ajoute le mot et sa ponderation
				compteur_mots++;
				count = 1; // on reinitialise le compteur
			}
		}
		this.words = Arrays.copyOf(lines, compteur_mots); // on recopie le tableau dans un nouveau tableau de taille
															// compteur_mots
	}

	public IndexedPage(Path path) {
		List<String> liste = new ArrayList<>();
		try {
			liste = Files.readAllLines(path);
		} catch (IOException e) { // g�n�r� par Eclipse pour traiter les exceptions
			System.out.println("erreur");
		}

		String[] words_in_file = new String[liste.size()];
		words_in_file = liste.toArray(words_in_file);

		this.url = words_in_file[0];
		this.words = new String[words_in_file.length - 1];
		for (int i = 1; i < words_in_file.length; ++i) {
			this.words[i - 1] = words_in_file[i];
		}
		Arrays.sort(this.words);
	}

	public String getUrl() // Getter
	{
		return this.url; // on retourne l'url de la page
	}

	public int getNorm() // Getter
	{
		int norm = 0;
		for (String word : this.words) // pour tous les mots du tableau
		{
			String[] split_array = word.split(":"); // on separe les elements de la forme "hello:5" en ["hello","5"]
			int ponderation = Integer.parseInt(split_array[1]); // On initialise ponderation et on lui affecte le cast
																// de la ponderation en entier (ce qui suit les :)
			norm += ponderation * ponderation; // ponderation au carre
		}
		return norm;
	}

	public int getCount(String word) {
		for (String w : this.words) // pour tous les mots du tableau
		{
			String[] split_array = w.split(":");// on separe les elements de la forme "hello:5" en ["hello","5"]
			if (split_array[0].equals(word)) // on verifie si le mot correspond a l'argument
			{
				return Integer.parseInt(split_array[1]); // on retourne la ponderation avec un cast
			}
		}
		return 0;
	}

	public double getPonderation(String word) {
		for (String w : this.words) // on parcourt tous les mots du tableau
		{
			String[] split_array = w.split(":");// on separe les elements de la forme "hello:5" en ["hello","5"]
			if (split_array[0].equals(word))// on verifie si le mot correspond a l'argument (donc au mot donné)
			{
				return Integer.parseInt(split_array[1]) / Math.sqrt(this.getNorm()); // on retourne la ponderation / par
																						// la norme (sqrt car getNorm
																						// renvoie le carré de la
																						// norme)
			}
		}
		return 0; // sinon 0
	}

	public double proximity(IndexedPage page) {
		double proximity = 0;
		for (String word : this.words) // on parcourt tous les mots du tableau
		{
			String[] split_array = word.split(":");// on separe les elements de la forme "hello:5" en ["hello","5"]
			proximity += this.getPonderation(split_array[0]) * page.getPonderation(split_array[0]); // on calcule le
																									// produit scalaire
																									// entre les deux
																									// vecteurs
		}
		return proximity;
	}

	public String toString() {
		return "IndexedPage [url=" + this.url + "]"; // on affiche sous cette forme la page
	}
}