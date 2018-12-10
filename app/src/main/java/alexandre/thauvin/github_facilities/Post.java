package alexandre.thauvin.github_facilities;

public class Post {
    private String title;
    private String subreddit;
    private String url;

    public Post(String title, String subreddit, String url){
        this.title = title;

        this.subreddit = subreddit;
        this.url = url;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
