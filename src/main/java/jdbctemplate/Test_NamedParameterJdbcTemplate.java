package jdbctemplate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class Test_NamedParameterJdbcTemplate {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void execute() {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(Configuration_Oracle.class);

        Test_NamedParameterJdbcTemplate testJdbcTemplate = ctx.getBean("test_NamedParameterJdbcTemplate", Test_NamedParameterJdbcTemplate.class);

        System.out.println("namedParameterJdbcTemplate.queryForObject: " + testJdbcTemplate.findNameById(2l));
    }

    public String findNameById(Long id) {
        String sql = "SELECT TEXT FROM TESTTABLE WHERE ID = :id";

        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("id", id);

        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, String.class);
    }
}
