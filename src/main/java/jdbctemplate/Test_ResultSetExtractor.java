package jdbctemplate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test_ResultSetExtractor {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void execute() {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(Configuration_Oracle.class);

        Test_ResultSetExtractor testRowMapper = ctx.getBean("test_resultSetExtractor", Test_ResultSetExtractor.class);

        List<TestTable> testTables = testRowMapper.findAllWithRef();

        System.out.print("\nResultSetExtractor: ");
        testTables.forEach(x ->
                {
                    System.out.print("\n" + x.getId() + " " + x.getFirstname() + " " + x.getLastname() + " " + x.getText() + " with ");
                    x.getTestTableRefList().forEach(r -> System.out.print(r.getValue() + " " + r.getDescription() + " | "));
                }
        );
    }

    public List<TestTable> findAllWithRef() {
        String sql = "SELECT T.ID, T.FIRSTNAME, T.LASTNAME, T.TEXT, R.ID AS REFID, R.VALUE, R.DESCRIPTION FROM TESTTABLE T LEFT JOIN TESTTABLEREF R ON T.ID = R.ID";

        return namedParameterJdbcTemplate.query(sql, new TestTableWithRef());
    }

    private static final class TestTableWithRef implements ResultSetExtractor<List<TestTable>> {

        @Override
        public List<TestTable> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, TestTable> map = new HashMap<>();

            TestTable testTable;
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                testTable = map.get(id);
                if (testTable == null) {
                    testTable = new TestTable();
                    testTable.setId(resultSet.getLong("ID"));
                    testTable.setFirstname(resultSet.getString("FIRSTNAME"));
                    testTable.setLastname(resultSet.getString("LASTNAME"));
                    testTable.setText(resultSet.getString("TEXT"));
                    testTable.setTestTableRefList(new ArrayList<>());
                    map.put(id, testTable);
                }

                Long refId = resultSet.getLong("REFID");
                if (refId > 0) {
                    TestTableRef testTableRef = new TestTableRef();
                    testTableRef.setId(refId);
                    testTableRef.setValue(resultSet.getLong("VALUE"));
                    testTableRef.setDescription(resultSet.getString("DESCRIPTION"));
                    testTable.getTestTableRefList().add(testTableRef);
                }
            }
            return new ArrayList<>(map.values());
        }
    }
}