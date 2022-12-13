import java.sql.*;

public class Arrival
{
    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "buspark";
    private final String LOGIN = "root";
    private final String PASS = "12345";
    private Connection dbConn = null;

    private int id = 0;
    public void setIdArriv(int id) {
        this.id = id;
    }
    public int getIdArriv() {
        return id;
    }

    private Connection getDbConnection() throws ClassNotFoundException, SQLException
    {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
            Class.forName("com.mysql.cj.jdbc.Driver");
                dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
                    return dbConn;
    }

    public void insertIn() throws SQLException, ClassNotFoundException
    {
        String sql = "INSERT INTO `inpark` SELECT * FROM `departure`WHERE id =" + getIdArriv() + "";
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
                prSt.executeUpdate();
    }

    public void DeleteDep() throws SQLException, ClassNotFoundException
    {
        String sql = "DELETE FROM `departure`WHERE id =" + getIdArriv() + "";
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
                prSt.executeUpdate();
    }

    public void getBusDep() throws SQLException, ClassNotFoundException
    {
        String sql = "SELECT * FROM `departure` ORDER BY `id` ASC";
            Statement statement = getDbConnection().createStatement();
                ResultSet res = statement.executeQuery(sql);
                    System.out.println("");
                        System.out.println("id  -  Number  -  Name     (Departure)");
                            System.out.println("----------------------");
        while (res.next())
        {
            int id = res.getInt(1);
                String Number = res.getString(2);
                    String Name = res.getString(3);
                        System.out.printf("%d - %s - %s\n", id, Number, Name);
        }
    }

    public void getBusIn() throws SQLException, ClassNotFoundException
    {
        String sql = "SELECT * FROM `inpark` ORDER BY `id` ASC";
            Statement statement = getDbConnection().createStatement();
                ResultSet res = statement.executeQuery(sql);
                    System.out.println("");
                        System.out.println("id  -  Number  -  Name     (In Park)");
                            System.out.println("----------------------");
        while (res.next())
        {
            int id = res.getInt(1);
                String Number = res.getString(2);
                    String Name = res.getString(3);
                        System.out.printf("%d - %s - %s\n", id, Number, Name);
        }
    }
}
