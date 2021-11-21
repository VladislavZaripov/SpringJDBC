package dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class InsertTestTable extends SqlUpdate {

    private static final String SQL = "INSERT INTO TESTTABLE " +
            "(FIRSTNAME, LASTNAME, TEXT) " +
            "VALUES " +
            "(:firstName, :lastName, :text)";

    public InsertTestTable(DataSource dataSource) {
        super(dataSource, SQL);
        super.declareParameter(new SqlParameter("firstName", Types.VARCHAR));
        super.declareParameter(new SqlParameter("lastName", Types.VARCHAR));
        super.declareParameter(new SqlParameter("text", Types.VARCHAR));
        super.setGeneratedKeysColumnNames("id");
        super.setReturnGeneratedKeys(true);
    }
}