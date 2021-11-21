package dao;

import jdbctemplate.TestTable;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectAllTestTable extends MappingSqlQuery<TestTable> {

    private static final String SQL = "SELECT ID, FIRSTNAME, LASTNAME, TEXT FROM TESTTABLE";

    public SelectAllTestTable(DataSource dataSource) {
        super(dataSource, SQL);
    }

    @Override
    protected TestTable mapRow(ResultSet resultSet, int i) throws SQLException {
        TestTable testTable = new TestTable();

        testTable.setId(resultSet.getLong("ID"));
        testTable.setFirstname(resultSet.getString("FIRSTNAME"));
        testTable.setLastname(resultSet.getString("LASTNAME"));
        testTable.setText(resultSet.getString("TEXT"));

        return testTable;
    }
}