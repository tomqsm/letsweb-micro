package biz.letsweb.micro.h2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Csv;
import org.h2.tools.SimpleResultSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 *
 * @author tomasz
 */
public class H2InMemoryTest {

    public H2InMemoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInMemorySetup() throws IOException, SQLException {
        final JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL("jdbc:h2:mem:test");
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("sa");
        final File testSql = new File("src/test/resources/schema-h2.sql");
        final List<String> readLines = FileUtils.readLines(testSql, Charset.forName("UTF-8"));
        final String createTableProperties = readLines.get(5);
        final String insertNameValuePair = readLines.get(6);

        final String[] linesToRun = new String[]{createTableProperties, insertNameValuePair};
        PreparedStatement preparedStatement = null;
        try (Connection connection = jdbcDataSource.getConnection()) {
            for (String line : linesToRun) {
                preparedStatement = connection.prepareStatement(line);
                preparedStatement.execute();
            }
            preparedStatement = connection.prepareStatement("select * from property;");
            final ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                System.out.println(results.getString("name"));
                Assert.notNull(results.getString("name"));
                System.out.println(results.getString("value"));
                Assert.notNull(results.getString("value"));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    /**
     * see
     * http://www.h2database.com/html/features.html#execute_sql_on_connection
     * the default transaction isolation level 'read committed', read locks are
     * already released after each statement
     *
     * @throws java.sql.SQLException
     */
    @Test
    public void testH2Initialisationfeature() throws SQLException {
        String url = "jdbc:h2:mem:test;INIT=create schema if not exists test\\;runscript from 'src/test/resources/schema-h2.sql'";
        final JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(url);
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("sa");
        PreparedStatement preparedStatement = null;
        try (Connection connection = jdbcDataSource.getConnection()) {
            preparedStatement = connection.prepareStatement("select * from property;");
            final ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                System.out.println(results.getString("name"));
                Assert.notNull(results.getString("name"));
                System.out.println(results.getString("value"));
                Assert.notNull(results.getString("value"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Test
    public void testH2TablePopulationFromCSV() throws SQLException {
        final String url = "jdbc:h2:mem:test;INIT=create schema if not exists test\\;runscript from 'src/test/resources/property-input.sql'";
        final JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(url);
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("sa");
        PreparedStatement preparedStatement = null;
        try (Connection connection = jdbcDataSource.getConnection()) {
            preparedStatement = connection.prepareStatement("select * from property;");
            final ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                System.out.println(String.format("%d : %s : %s : %s", results.getInt("id"), results.getString("name"), results.getString("value"), results.getTimestamp("inserted")));
                Assert.notNull(results.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Test
    public void testCSV() throws SQLException {
        final SimpleResultSet rs = new SimpleResultSet();
//        "ID","NAME","VALUE","INSERTED"
        rs.addColumn("ID", Types.INTEGER, 0, 0);
        rs.addColumn("NAME", Types.VARCHAR, 255, 0);
        rs.addColumn("VALUE", Types.VARCHAR, 255, 0);
        rs.addColumn("INSERTED", Types.TIMESTAMP, 0, 0);
        rs.addRow(1, "Bob Meier", "bob.meier@abcde.abc", new Timestamp(System.currentTimeMillis()));
        rs.addRow(2, "John Jones", "john.jones@abcde.abc", new Timestamp(System.currentTimeMillis()));
        new Csv().write("src/test/resources/test.csv", rs, "UTF-8");
    }

    @Test
    public void testConnectionPool() {
        System.out.println("#testConnectionPool");
        final String url = "jdbc:h2:mem:test;INIT=create schema if not exists test\\;runscript from 'src/test/resources/property-input.sql'";
        final String sql = "select * from property";
        final JdbcConnectionPool cp = JdbcConnectionPool.create(url, "sa", "sa");
        try (Connection conn = cp.getConnection()) {
            final ResultSet results = conn.createStatement().executeQuery(sql);
            while (results.next()) {
                System.out.println(String.format("%d : %s : %s : %s", results.getInt("id"), results.getString("name"), results.getString("value"), results.getTimestamp("inserted")));
                Assert.notNull(results.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(H2InMemoryTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cp.dispose();
        }
    }
}
