package configuration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConfigTest {

    public void testXMLConfiguration() {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load(new ClassPathResource("configurations/configuration_oracle.xml"));
        ctx.refresh();

        DataSource dataSource = ctx.getBean("dataSource", DataSource.class);
        testDataSource(dataSource, "XML");
    }

    public void testJavaConfiguration() {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(Configuration_Oracle.class);

        DataSource dataSource = ctx.getBean("dataSource", DataSource.class);
        testDataSource(dataSource, "JavaClass");
    }

    private void testDataSource(DataSource dataSource, String typeConfiguration) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM TESTCONNECT");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(typeConfiguration + ": " + resultSet.getString(1));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}