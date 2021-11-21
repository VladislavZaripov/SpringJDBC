package jdbctemplate;

import java.util.List;

public class TestTable {
    private Long id;
    private String firstname;
    private String lastname;
    private String text;
    private List<TestTableRef> testTableRefList;

    public TestTable() {
    }

    public TestTable(String firstname, String lastname, String text) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.text = text;
    }

    public TestTable(Long id, String firstname, String lastname, String text) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TestTableRef> getTestTableRefList() {
        return testTableRefList;
    }

    public void setTestTableRefList(List<TestTableRef> testTableRefList) {
        this.testTableRefList = testTableRefList;
    }
}