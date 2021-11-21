package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class Configuration_Embedded {

    @Bean
    public DataSource dataSource() {
        try {
            EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
            return dbBuilder
                    .setType(EmbeddedDatabaseType.H2)
                    .addScripts("classpath:db/h2/schema.sql", "classpath:db/h2/test-data.sql")
                    .build();
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
    public Target targetDataSource() {
        Target target = new Target();
        target.setDataSource(dataSource());
        return target;
    }

    @Bean
    public Target targetJdbcTemplate() {
        Target target = new Target();
        target.setJdbcTemplate(jdbcTemplate());
        return target;
    }
}