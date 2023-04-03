package search_engine;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class test {

    public static void main(String[] args) throws Exception {
        String url = "https://fr.vikidia.org/wiki/Ch%C3%A2teau";

        try 
        {
            String content = fetchWebContent(url);
            String textContent = extractContentFromParagraphs(content);
            textContent=textContent.toLowerCase();
            textContent = removeHTMLTagsFromList(textContent);
            textContent=SearchEngine.removeSmallWords(textContent);
            textContent=SearchEngine.removeSpecialCharacters(textContent);
            textContent=SearchEngine.removeWordsBlackList(textContent);
            textContent=SearchEngine.lemmatize(textContent);
            textContent=SearchEngine.removeSmallWords(textContent); 
            formatageFichier(textContent);  
                
        } 
        catch (IOException e) {
            System.err.println("Erreur lors de la récupération du contenu : " + e.getMessage());
        }
    }

    private static String fetchWebContent(String urlString) throws IOException {
        URL url = new URL(urlString);
        try (InputStream inputStream = url.openStream();
             Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    public static String extractContentFromParagraphs(String htmlContent) {
        String content = "";
        Pattern pattern = Pattern.compile("<p>(.*?)</p>", Pattern.DOTALL);
        //appliquer pattern a htmlContent
        Matcher matcher = pattern.matcher(htmlContent);
        while (matcher.find()) {
            content += matcher.group(1);
        }
        return content;
    }

    public static String removeHTMLTagsFromList(String htmlContent) {
        String textOnly = htmlContent.replaceAll("\\<.*?\\>", "");
        
        return textOnly;
    }

    //méthode qui écrit dans un fichier l'association mot et nombre d'occurence
    public static void formatageFichier(String textContent) throws IOException {
        // Séparer le texte en mots
        String[] words = textContent.split("\\s+");
    
        // Créer une map pour stocker les mots et leur nombre d'occurrences
        Map<String, Integer> wordCountMap = new HashMap<>();
    
        // Parcourir tous les mots et mettre à jour le nombre d'occurrences dans la map
        for (String word : words) {
            if (!wordCountMap.containsKey(word)) {
                wordCountMap.put(word, 1);
            } else {
                wordCountMap.put(word, wordCountMap.get(word) + 1);
            }
        }
    
        // Écrire les associations mot-nombre d'occurrences dans un fichier
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("association.txt"), "UTF-8"));
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            writer.write(entry.getKey() + " : " + entry.getValue() + "\n");
        }
        writer.close();
    }
    
    
}