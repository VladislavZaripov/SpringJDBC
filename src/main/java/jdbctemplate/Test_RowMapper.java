package jdbctemplate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Test_RowMapper {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void execute() {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(Configuration_Oracle.class);

        Test_RowMapper testRowMapper = ctx.getBean("test_RowMapper", Test_RowMapper.class);

        List<TestTable> testTables = testRowMapper.findAll();

        System.out.print("TestRowMapper: ");
        testTables.forEach(x-> System.out.print(x.getId()+ " " + x.getFirstname()+ " " + x.getLastname() + " " + x.getText() + " / "));

    }

    public List<TestTable> findAll() {
        String sql = "SELECT ID, FIRSTNAME, LASTNAME, TEXT FROM TESTTABLE";

        return namedParameterJdbcTemplate.query(sql, new TestTableMapper());
    }

    private static final class TestTableMapper implements RowMapper<TestTable> {

        @Override
        public TestTable mapRow(ResultSet resultSet, int i) throws SQLException {
            TestTable testTable = new TestTable();

            testTable.setId(resultSet.getLong("ID"));
            testTable.setFirstname(resultSet.getString("FIRSTNAME"));
            testTable.setLastname(resultSet.getString("LASTNAME"));
            testTable.setText(resultSet.getString("TEXT"));

            return testTable;
        }
    }

//    public List<TestTable> findAll() {
//        String sql = "SELECT ID, FIRSTNAME, LASTNAME, TEXT FROM TESTTABLE";
//
//        return namedParameterJdbcTemplate.query(sql, (resultSet, i)-> {
//            TestTable testTable = new TestTable();
//            testTable.setId(resultSet.getLong("ID"));
//            testTable.setFirstname(resultSet.getString("FIRSTNAME"));
//            testTable.setLastname(resultSet.getString("LASTNAME"));
//            testTable.setText(resultSet.getString("TEXT"));
//            return testTable;});
//    }
}