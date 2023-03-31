package ahah;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

public class webcontentextractoretcetc {
    public static void main(String[] args) {
        String url = "https://fr.vikidia.org/wiki/Bouteille";

        try {
            String content = fetchWebContent(url);
            String textContent = extractText(content);
            System.out.println(textContent);
        } catch (IOException e) {
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

    private static String extractText(String content) {
        StringBuilder result = new StringBuilder();
        Pattern tagPattern = Pattern.compile("<[^>]*>");
        Pattern scriptPattern = Pattern.compile("<script.*?>.*?</script>", Pattern.DOTALL);
        Pattern stylePattern = Pattern.compile("<style.*?>.*?</style>", Pattern.DOTALL);
        Pattern whitespacePattern = Pattern.compile("\\s+");

        content = scriptPattern.matcher(content).replaceAll(" ");
        content = stylePattern.matcher(content).replaceAll(" ");
        content = tagPattern.matcher(content).replaceAll(" ");
        content = whitespacePattern.matcher(content).replaceAll(" ");

        return content.trim();
    }
}
