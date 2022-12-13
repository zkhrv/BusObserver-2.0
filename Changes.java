import java.sql.*;

public class Changes
{
    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "Buspark";
    private final String LOGIN = "root";
    private final String PASS = "12345";
    private Connection dbConn = null;

    DBInPark data7 = new DBInPark();

    private String number;
    private String name;
    private int NewId = 0;
    int count_bus;
    int correct_id1 = 0;
    int correct_id2 = 0;

    public void setNumber (String Number)
    {
        this.number=Number;
    }
        public String getNumber ()
    {
        return number;
    }
    public void setName(String Name)
    {
        this.name=Name;
    }
        public String getName()
    {
        return name;
    }
    public void setNewId(int NewId)
    {
        this.NewId=NewId;
    }
        public int getNewId() {return NewId;}

    private Connection getDbConnection() throws ClassNotFoundException, SQLException
    {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
            Class.forName("com.mysql.cj.jdbc.Driver");
                dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
                    return dbConn;
    }

    public void NewDeleteIn() throws SQLException, ClassNotFoundException
    {
        String sql = "DELETE FROM `inpark`WHERE id =" + getNewId() + "";
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
                prSt.executeUpdate();
    }

    public void NewinsertIn() throws SQLException, ClassNotFoundException
    {
        String sql = "INSERT INTO `inpark`(`Number`, `Name`) VALUES ('"+getNumber()+"','"+getName() +"')";
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
                prSt.executeUpdate();
    }

    public void NewgetBusIn() throws SQLException, ClassNotFoundException
    {
        String sql = "SELECT * FROM `inpark`";
            Statement statement = getDbConnection().createStatement();
                ResultSet res = statement.executeQuery(sql);
                    System.out.println("id  -  Number  -  Name     (In Park)");
                        System.out.println("----------------------");
        while(res.next())
        {
            int id = res.getInt(1);
                String Number = res.getString(2);
                    String Name = res.getString(3);
                        System.out.printf("%d - %s - %s\n", id, Number, Name);
        }
    }

}
