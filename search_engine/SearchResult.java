package search_engine;

public class SearchResult 
{
    private String url;
    private double score;

    public SearchResult(String url, double score) 
    {
        this.url = url;
        this.score = score;
    }

    public String getUrl() 
    {
        return this.url;
    }

    public double getScore() 
    {
        return this.score;
    }

    public String toString() 
    {
        return this.url + " : " + this.score;
    }


    
}
