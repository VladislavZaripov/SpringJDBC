package dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

public class InsertTestTableRef extends BatchSqlUpdate {

    private static final String SQL = "INSERT INTO TESTTABLEREF VALUES (:id, :value, :description)";

    private static final int BATCH_SIZE = 10;

    public InsertTestTableRef(DataSource dataSource) {
        super(dataSource, SQL);
        super.declareParameter(new SqlParameter("id", Types.INTEGER));
        super.declareParameter(new SqlParameter("value", Types.INTEGER));
        super.declareParameter(new SqlParameter("description", Types.VARCHAR));
        setBatchSize(BATCH_SIZE);
    }
}