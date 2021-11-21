package dao;

import jdbctemplate.TestTable;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SelectTestTableByFirstName extends MappingSqlQuery<TestTable> {

    private static final String SQL = "SELECT ID, FIRSTNAME, LASTNAME, TEXT FROM TESTTABLE WHERE FIRSTNAME = :firstName";

    public SelectTestTableByFirstName(DataSource dataSource) {
        super(dataSource, SQL);
        super.declareParameter(new SqlParameter("firstName", Types.VARCHAR));
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