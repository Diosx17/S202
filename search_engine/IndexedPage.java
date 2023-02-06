package search_engine;

public class IndexedPage {
    private String url;
    private String[] words;

    public IndexedPage(String[] lines) 
    {
        this.url = lines[0];
        this.words = new String[lines.length - 1];
        for (int i=1; i < lines.length; ++i)
        {
            this.words[i-1] = lines[i];
        }
    }

    public IndexedPage(String text)
    {
        String[] lines = text.split(" ");
        this.url = lines[0];
        this.words = new String[lines.length-1];
        for (int i=1; i < lines.length; ++i)
        {
            this.words[i-1] = lines[i];
        }

    }

    public String getUrl()
    {
        return this.url;
    }

    public int getNorm()
    {
        int norm = 0;
        for (String word : this.words)
        {
            String[] split_array = word.split(":");
            int ponderation = Integer.parseInt(split_array[1]);
            norm += ponderation * ponderation;
        }
        return norm;
    }
    
    public int getCount(String word)
    {
        for (String w : this.words)
        {
            String[] split_array = w.split(":");
            if (split_array[0].equals(word))
            {
                return Integer.parseInt(split_array[1]);
            }
        }
        return 0;
    }
    public double getPonderation(String word)
    {
        for (String w : this.words)
        {
            String[] split_array = w.split(":");
            if (split_array[0].equals(word))
            {
                return Integer.parseInt(split_array[1])/Math.sqrt(this.getNorm());
            }
        }
        return 0;
    }
    public double proximity(IndexedPage page)
    {
        double proximity = 0;
        for (String word : this.words)
        {
            String[] split_array = word.split(":");
            proximity += this.getPonderation(split_array[0]) * page.getPonderation(split_array[0]);
        }
        return proximity;
    }

    public String toString()
    {
       return "IndexedPage [url=" + this.url +"]";
    }
}