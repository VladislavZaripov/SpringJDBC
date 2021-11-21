package jdbctemplate;

public class TestTableRef {

    private Long id;
    private Long value;
    private String description;

    public TestTableRef() {
    }

    public TestTableRef(Long value, String description) {
        this.value = value;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}