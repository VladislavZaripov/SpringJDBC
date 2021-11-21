package dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlFunction;

import javax.sql.DataSource;
import java.sql.Types;

public class ExecuteFunction extends SqlFunction<String> {

    private static final String SQL = "SELECT GETFIRSTNAMEBYID(?) FROM DUAL";

    public ExecuteFunction(DataSource dataSource) {
        super(dataSource, SQL);
        declareParameter(new SqlParameter(Types.INTEGER));
        compile();
    }
}