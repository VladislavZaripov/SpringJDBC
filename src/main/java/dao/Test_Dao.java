package dao;

import jdbctemplate.TestTable;
import jdbctemplate.TestTableRef;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("test_dao")
public class Test_Dao {

    private DataSource dataSource;
    private SelectAllTestTable selectAllTestTable;
    private SelectTestTableByFirstName selectTestTableByFirstName;
    private UpdateTestTable updateTestTable;
    private InsertTestTable insertTestTable;
    private InsertTestTableRef insertTestTableRef;
    private ExecuteFunction executeFunction;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.selectAllTestTable = new SelectAllTestTable(dataSource);
        this.selectTestTableByFirstName = new SelectTestTableByFirstName(dataSource);
        this.updateTestTable = new UpdateTestTable(dataSource);
        this.insertTestTable = new InsertTestTable(dataSource);
        this.insertTestTableRef = new InsertTestTableRef(dataSource);
        this.executeFunction = new ExecuteFunction(dataSource);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void execute() {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(Configuration_Oracle.class);
        Test_Dao test_dao = ctx.getBean("test_dao", Test_Dao.class);

        System.out.print("MappingSqlQuery.execute: ");
        List<TestTable> testTables = test_dao.findAll();
        testTables.forEach(x -> System.out.print(x.getId() + " " + x.getFirstname() + " " + x.getLastname() + " " + x.getText() + " / "));

        System.out.print("\nMappingSqlQuery.executeByNamedParam: ");
        testTables = test_dao.findByFirstName("Firstname_2");
        testTables.forEach(x -> System.out.print(x.getId() + " " + x.getFirstname() + " " + x.getLastname() + " " + x.getText() + " / "));

        System.out.print("\nSqlUpdate.updateByNamedParam: ");
        TestTable testTable = new TestTable(4l, "Firstname_4", "Lastname_4", "DDD");
        test_dao.update(testTable);
        testTables = test_dao.findByFirstName("Firstname_4");
        testTables.forEach(x -> System.out.print(x.getId() + " " + x.getFirstname() + " " + x.getLastname() + " " + x.getText() + " / "));

        System.out.print("\nSqlUpdate.updateByNamedParam: ");
        testTable = new TestTable("Firstname_5", "Lastname_5", "EEE");
        Long id = test_dao.insert(testTable);
        System.out.println(id);

        System.out.print("\nBatchSqlUpdate.updateByNamedParam: ");
        testTable = new TestTable("Firstname_x", "Lastname_x", "XXX");
        List<TestTableRef> testTableRefList = new ArrayList<>();
        testTableRefList.add(new TestTableRef(888l, "888"));
        testTableRefList.add(new TestTableRef(999l, "999"));
        testTable.setTestTableRefList(testTableRefList);
        test_dao.insertTestTableAndRef(testTable);

        System.out.print("\nSqlFunction.execute: ");
        System.out.println(test_dao.getFirstNameById(1l));

    }

    public List<TestTable> findAll() {
        return selectAllTestTable.execute();
    }

    public List<TestTable> findByFirstName(String firstName) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("firstName", firstName);
        return selectTestTableByFirstName.executeByNamedParam(paramMap);
    }

    public void update(TestTable testTable) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("firstName", testTable.getFirstname());
        paramMap.put("lastName", testTable.getLastname());
        paramMap.put("text", testTable.getText());
        paramMap.put("id", testTable.getId());
        updateTestTable.updateByNamedParam(paramMap);
    }

    public Long insert(TestTable testTable) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("firstName", testTable.getFirstname());
        paramMap.put("lastName", testTable.getLastname());
        paramMap.put("text", testTable.getText());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertTestTable.updateByNamedParam(paramMap, keyHolder);
        testTable.setId(keyHolder.getKey().longValue());
        return testTable.getId();
    }

    public void insertTestTableAndRef(TestTable testTable) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("firstName", testTable.getFirstname());
        paramMap.put("lastName", testTable.getLastname());
        paramMap.put("text", testTable.getText());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertTestTable.updateByNamedParam(paramMap, keyHolder);
        testTable.setId(keyHolder.getKey().longValue());

        List<TestTableRef> testTableRefs = testTable.getTestTableRefList();
        if (testTableRefs != null) {
            for (TestTableRef testTableRef : testTableRefs) {
                paramMap = new HashMap<>();
                paramMap.put("id", testTable.getId());
                paramMap.put("value", testTableRef.getValue());
                paramMap.put("description", testTableRef.getDescription());
                insertTestTableRef.updateByNamedParam(paramMap);
            }
        }
        insertTestTableRef.flush();
    }

    public String getFirstNameById(Long id) {
        List<String> result = executeFunction.execute(id);
        return result.get(0);
    }
}