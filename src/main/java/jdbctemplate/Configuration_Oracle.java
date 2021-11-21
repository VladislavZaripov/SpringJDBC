package jdbctemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@PropertySource("configurations/jdbc.properties")
public class Configuration_Oracle {

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Lazy
    @Bean
    public DataSource dataSource() {
        try {
            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(driverClassName);
            dataSource.setDriverClass(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        } catch (Exception e) {
            return null;
        }
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public Test_JdbcTemplate test_JdbcTemplate() {
        Test_JdbcTemplate test_JdbcTemplate = new Test_JdbcTemplate();
        test_JdbcTemplate.setJdbcTemplate(jdbcTemplate());
        return test_JdbcTemplate;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public Test_NamedParameterJdbcTemplate test_NamedParameterJdbcTemplate() {
        Test_NamedParameterJdbcTemplate test_NamedParameterJdbcTemplate = new Test_NamedParameterJdbcTemplate();
        test_NamedParameterJdbcTemplate.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate());
        return test_NamedParameterJdbcTemplate;
    }

    @Bean
    public Test_RowMapper test_RowMapper() {
        Test_RowMapper test_RowMapper = new Test_RowMapper();
        test_RowMapper.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate());
        return test_RowMapper;
    }

    @Bean
    public Test_ResultSetExtractor test_resultSetExtractor() {
        Test_ResultSetExtractor test_resultSetExtractor = new Test_ResultSetExtractor();
        test_resultSetExtractor.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate());
        return test_resultSetExtractor;
    }
}