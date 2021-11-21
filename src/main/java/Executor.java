import configuration.DBConfigTest;
import dao.Test_Dao;
import jdbctemplate.Test_JdbcTemplate;
import jdbctemplate.Test_NamedParameterJdbcTemplate;
import jdbctemplate.Test_ResultSetExtractor;
import jdbctemplate.Test_RowMapper;

public class Executor {

    public static void main(String[] args) {
        connect();
        jdbcTemplate();
        dao();
    }

    public static void connect() {
        System.out.println("---CONFIGURATION---");

        DBConfigTest dbConfigTest = new DBConfigTest();
        dbConfigTest.testXMLConfiguration();
        dbConfigTest.testJavaConfiguration();
    }

    public static void jdbcTemplate() {
        System.out.println("\n---JdbcTemplate---");

        Test_JdbcTemplate test_JdbcTemplate = new Test_JdbcTemplate();
        test_JdbcTemplate.execute();

        Test_NamedParameterJdbcTemplate test_NamedParameterJdbcTemplate = new Test_NamedParameterJdbcTemplate();
        test_NamedParameterJdbcTemplate.execute();

        Test_RowMapper test_RowMapper = new Test_RowMapper();
        test_RowMapper.execute();

        Test_ResultSetExtractor test_resultSetExtractor = new Test_ResultSetExtractor();
        test_resultSetExtractor.execute();
    }

    public static void dao(){
        System.out.println("\n\n---DAO---");

        Test_Dao test_dao = new Test_Dao();
        test_dao.execute();
    }

}