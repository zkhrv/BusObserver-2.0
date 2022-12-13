import java.sql.*;

public class Delete
{
    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "buspark";
    private final String LOGIN = "root";
    private final String PASS = "12345";
    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException
    {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
            Class.forName("com.mysql.cj.jdbc.Driver");
                dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
                    return dbConn;
    }
    public void DeleteTableDep() throws SQLException, ClassNotFoundException
    {
        String sql = "DELETE FROM `departure`";
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
                prSt.executeUpdate();
    }
    public void DeleteTablesIn() throws SQLException, ClassNotFoundException
    {
        String sql = "DELETE FROM `inpark`";
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
                prSt.executeUpdate();
    }
}

