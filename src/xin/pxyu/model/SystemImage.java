package xin.pxyu.model;

public class SystemImage{
    private int id;
    private String path;
    private String url;
    private String filename;
    private String upload_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String toString(){
        return String.format("入参为:[path=%s,filename=%s,url=%s]",this.path,this.filename,this.url);
    }
}
