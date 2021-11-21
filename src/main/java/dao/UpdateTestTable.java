package dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class UpdateTestTable extends SqlUpdate {

    private static final String SQL = "UPDATE TESTTABLE " +
            "SET " +
            "FIRSTNAME = :firstName, " +
            "LASTNAME = :lastName, " +
            "TEXT = :text " +
            "WHERE ID = :id";

    public UpdateTestTable(DataSource dataSource){
        super(dataSource,SQL);
        super.declareParameter(new SqlParameter("firstName", Types.VARCHAR));
        super.declareParameter(new SqlParameter("lastName", Types.VARCHAR));
        super.declareParameter(new SqlParameter("text", Types.VARCHAR));
        super.declareParameter(new SqlParameter("id", Types.INTEGER));
    }
}