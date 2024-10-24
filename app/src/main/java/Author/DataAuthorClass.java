package Author;

public class DataAuthorClass {
    private String dataTitleAuthor;
    private String dataDesAuthor;
    private String dataDateAuthor;
    private String dataImageAuthor;
    private String key;

    public DataAuthorClass(String dataTitleAuthor, String dataDesAuthor, String dataDateAuthor, String dataImageAuthor) {
        this.dataTitleAuthor = dataTitleAuthor;
        this.dataDesAuthor = dataDesAuthor;
        this.dataDateAuthor = dataDateAuthor;
        this.dataImageAuthor = dataImageAuthor;
    }

    public String getDataTitleAuthor() {
        return dataTitleAuthor;
    }

    public String getDataDesAuthor() {
        return dataDesAuthor;
    }

    public String getDataDateAuthor() {
        return dataDateAuthor;
    }

    public String getDataImageAuthor() {
        return dataImageAuthor;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataAuthorClass() {
    }
}

