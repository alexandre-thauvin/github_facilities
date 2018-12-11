package alexandre.thauvin.github_facilities;

public class Post {
    private String title;
    private String user;
    private String url;

    public Post(String title, String user, String url){
        this.title = title;

        this.user = user;
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
