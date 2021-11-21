package jdbctemplate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class Test_JdbcTemplate {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void execute() {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(Configuration_Oracle.class);

        Test_JdbcTemplate testJdbcTemplate = ctx.getBean("test_JdbcTemplate", Test_JdbcTemplate.class);

        System.out.println("jdbcTemplate.queryForObject: " + testJdbcTemplate.findNameById(2l));
    }

    public String findNameById(Long id){
        return jdbcTemplate.queryForObject("SELECT TEXT FROM TESTTABLE WHERE ID = ?", new Object[]{id}, String.class);
    }
}