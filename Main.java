import java.sql.SQLException;

public class Main
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        Actions data1 = new Actions();

            data1.introduction();
                data1.CorrectionInDB();
                    data1.work();
    }
}


