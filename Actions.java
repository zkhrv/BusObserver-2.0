import java.sql.*;
import java.util.Scanner;
import java.util.regex.*;

public class Actions
{
    Scanner sc = new Scanner(System.in);
        DBInPark data2 = new DBInPark();
            Departure data3 = new Departure();
                Arrival data4 = new Arrival();
                    Delete data5 = new Delete();
                        Changes data6 = new Changes();

    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "Buspark";
    private final String LOGIN = "root";
    private final String PASS = "12345";
    private Connection dbConn = null;

    int count_bus = 0;
    int count_attempt = 3;
    String number, name;
    int NewId = 0;
    int count_action = 0;
    int idDep = 0;
    int idArriv = 0;
    int task = 0;
    int CheckExit = 0;
    int check = 0;
    int count_correction = 2;
    int delete_row = 0;
    int checkChose = 0;
    int count_correction_regex = 1;
    int correct_id1 = 0;
    int correct_id2 = 0;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException
    {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
            Class.forName("com.mysql.cj.jdbc.Driver");
                dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
                    return dbConn;
    }

    public void introduction() throws SQLException, ClassNotFoundException
    {
        for (int a = 0; a < count_attempt; a++)
        {
            System.out.println("--Выберите количество автобусов в парке на сегодняшний день:");
                count_bus = sc.nextInt();

            if (count_bus >= 1 & count_bus <= 100)
            {
                System.out.println("--Доступны обычные цифренные обозначения, а также буквенные:\n1.КМ\n2.М\n3.Н\n4.Т\n5.С");
                System.out.println("--Обратите внимание!!! Буквенные обозначения пишутся заглавными буквами! После букв, цифры пишутся без пробела!!!");

                for (int i = 0; i < count_bus; i++)
                {
                    sc.nextLine();
                    System.out.println("--Введите номер автобуса:");
                    number = sc.nextLine();
                    Pattern patternNumber = Pattern.compile("\\D[КМ]\\d{0,}||[Н]\\d{1,}||[Т]\\d{1,}||[М]\\d{1,}||[С]\\d{1,}||\\d{1,}");
                    Matcher matcherNumber = patternNumber.matcher(number);

                    if (matcherNumber.matches() == true)
                    {
                        data2.setNumber(number);
                    }

                    System.out.println("--Введите Фамилию И. О. водителя:");
                    name = sc.nextLine();
                    Pattern patternName = Pattern.compile("([А-Я]{1}[а-я]{1,45})\\s([A-Я]{1}\\W\\s[A-Я]{1}\\W)");
                    Matcher matcherName = patternName.matcher(name);

                    if (matcherName.matches() == true)
                    {
                        data2.setName(name);
                    }

                    if (matcherName.matches() == true & matcherNumber.matches() == true)
                    {
                        data2.insertIn();
                    }
                    if (matcherName.matches() == false || matcherNumber.matches() == false)
                    {
                        if (matcherNumber.matches() == false)
                        {
                            System.out.println("--Вы некорректно ввели номер автобуса. Повторите попытку ввода");
                            System.out.println("--Ошибка возникла при вводе последних данных. Вам необходимо провести процедура ввода последней строки заново");
                        }
                        if (matcherName.matches() == false)
                        {
                            System.out.println("--Вы неккоректно ввели ФИО. Повторите попытку ввода");
                            System.out.println("--Ошибка возникла при вводе последних данных. Вам необходимо провести процедура ввода последней строки заново");
                        }
                        System.out.println("--Нажмите ENTER для продолжения");
                        count_bus++;
                    }
                }
                break;
            }
            else
            {
                System.out.println("-!-!-ERROR: вводимое число автобусов не верно-!-!- (Повторите попытку еще раз)\n");
            }
            if (a == 2)
            {
                System.out.println("--Вы превысили количество допустимых попыток");
                    System.exit(0);
            }
        }
        data2.getBusIn();
    }
    private void CountCorr() throws SQLException, ClassNotFoundException
    {
        boolean marker = false;
        String sql = "SELECT `id` FROM `inpark`";
        Statement statement = getDbConnection().createStatement();
        ResultSet idres = statement.executeQuery(sql);
        while (idres.next() && !marker)
        {
            correct_id1 = idres.getInt(1);
            marker = true;
        }
        correct_id2 = correct_id1 + count_bus - 1;
    }

    public void correction() throws SQLException, ClassNotFoundException
    {
        System.out.println("--Посчитайте количество строк, которые хотите изменить, а затем введите их количество");
            System.out.println("--Если хотите выйти из функции внесения изменений - нажмите 777");
                count_action = sc.nextInt();

                if (count_action == 777)
                {
                    work();
                }
                if (count_action <= count_bus & count_action > 0)
                {
                    System.out.println("--Если хотите полностью удалить строчку - нажмите 1");
                        System.out.println("--Если хотите внести точечные изменения - нажмите 2");
                            System.out.println("--Если хотите выйти из функции внесения изменений - нажмите 777");
                                delete_row = sc.nextInt();

                    for (int i = 0; i < count_action; i++)
                    {
                            switch (delete_row)
                            {
                                case 1:
                                    DeleteByIndex();
                                    data6.NewgetBusIn();
                                    
                                case 2:
                                    CorrectRowByIndex();
                                    data6.NewgetBusIn();
                                    break;
                                case 777:
                                    work();
                                    break;
                            }
                    }
                }
                else
                {
                    System.out.println("-!-!-ERROR: вы ввели недопустимое число (Повторите попытку еще раз)\n");
                    count_correction++;
                }
    }

    public void work() throws SQLException, ClassNotFoundException
    {
        System.out.println("");
            System.out.println("--Для отправки автобуса в рейс введите 1");
                System.out.println("--Для завершения рабочего дня введите 3");

        while (task < 3)
        {
                task = sc.nextInt();

            if (task == 3 | task > 3) {}

            switch (task)
            {
                case 1:
                    System.out.println("--Введите id водителя, которого хотите отправить в рейс:");
                        idDep = sc.nextInt();
                            data3.setIdDep(idDep);
                                data3.insertDep();
                                    data3.DeleteIn();
                                        data3.getBusDep();
                                            data3.getBusIn();
                    System.out.println("");
                        System.out.println("--Для отправки автобуса в рейс введите 1");
                            System.out.println("--Для въезда автобуса в парк нажмите 2");
                                System.out.println("--Для завершения рабочего дня введите 3");
                                    break;

                case 2:
                    System.out.println("--Введите id водителя, который завершил рейс:");
                        idArriv = sc.nextInt();
                            data4.setIdArriv(idArriv);
                                data4.insertIn();
                                    data4.DeleteDep();
                                        data4.getBusDep();
                                            data4.getBusIn();
                    System.out.println("");
                        System.out.println("--Для отправки автобуса в рейс введите 1");
                            System.out.println("--Для въезда автобуса в парк нажмите 2");
                                System.out.println("--Для завершения рабочего дня введите 3");
                                    break;
                case 3:
                    System.out.println("--Вы точно хотите завершить рабочий день?");
                        System.out.println("--Если хотите завершить - нажмите 1\n--Если хотите продолжить - нажмите 2");
                            CheckExit = sc.nextInt();

                    if (CheckExit == 1)
                    {
                        System.out.println("--Тяжелый выдался день, не так ли? До новых встреч!");
                            data5.DeleteTablesIn();
                                data5.DeleteTableDep();
                                    System.exit(0);
                    }
                    if (CheckExit == 2)
                    {
                        System.out.println("--Для отправки автобуса в рейс введите 1");
                            System.out.println("--Для въезда автобуса в парк нажмите 2");
                                System.out.println("--Для завершения рабочего дня введите 3");
                                    task = 0;
                    }
                    break;
            }
        }
    }

    public void CorrectionInDB() throws SQLException, ClassNotFoundException
    {
        for (int c = 0; c < count_correction; c++)
        {
            System.out.println("--Если хотите внести изменения - нажмите 1\n--Если готовы начать работу - нажмите 2");
                System.out.println("--//Во время работы вы не сможете изменить таблицу//--");
                    check = sc.nextInt();
            if (check == 1)
            {
                System.out.println("--Если случайно нажали 1 - нажмите 2\n--Если хотите приступить к редактированию - нажмите 1");
                    checkChose = sc.nextInt();
                        if (checkChose == 1)
                        {
                            correction();
                        }
                        if (checkChose == 2)
                        {}
                        break;
            }

            if (check == 2)
            {
                System.out.println("--Если случайно нажали 2 - нажмите 1\n--Если хотите приступить к работе - нажмите 2");
                    checkChose = sc.nextInt();
                        if (checkChose == 1)
                        {
                            correction();
                        }
                        if (checkChose == 2)
                        {}
                        break;
            }
        }
    }
    public void DeleteByIndex() throws SQLException, ClassNotFoundException
    {
        System.out.println("--Введите id строки, которую хотите удалить");
            NewId = sc.nextInt();

        for (int i=0; i<count_correction; i++)
        {
            if (NewId < correct_id1 || NewId > correct_id2)
            {
                System.out.println("--Вы ввели недопустимое число");
                System.out.println("--Повторите попытку еще раз");
                count_correction++;
                break;
            }

           if (NewId >= correct_id1 & NewId <= correct_id2)
           {
               data6.setNewId(NewId);
               data6.NewDeleteIn();
               System.out.println("--ДАННЫЕ УДАЛЕНЫ");
               count_correction = 0;
               break;
           }
           return;
        }

    }
    public void CorrectRowByIndex() throws SQLException, ClassNotFoundException
    {
        System.out.println("--Введите id строки, которую хотите изменить:");
            NewId = sc.nextInt();

        if (NewId >= correct_id1 || NewId <= correct_id2)
        {
            data6.setNewId(NewId);
            data6.NewDeleteIn();
            for (int i=0; i<count_correction_regex; i++)
            {
                sc.nextLine();
                System.out.println("--Введите номер автобуса:");
                number = sc.nextLine();
                Pattern patternNumber = Pattern.compile("\\D[КМ]\\d{0,}||[Н]\\d{1,}||[Т]\\d{1,}||[М]\\d{1,}||[С]\\d{1,}||\\d{1,}");
                Matcher matcherNumber = patternNumber.matcher(number);

                if (matcherNumber.matches() == true)
                {
                    data6.setNumber(number);
                }
                System.out.println("--Введите фамилию и.о. водителя:");
                name = sc.nextLine();
                Pattern patternName = Pattern.compile("([А-Я]{1}[а-я]{1,45})\\s([A-Я]{1}\\W\\s[A-Я]{1}\\W)");
                Matcher matcherName = patternName.matcher(name);

                if (matcherName.matches() == true)
                {
                    data6.setName(name);
                }
                if (matcherName.matches() == true & matcherNumber.matches() == true)
                {
                    data6.NewinsertIn();
                    break;
                }
                if (matcherName.matches() == false || matcherNumber.matches() == false)
                {
                    if (matcherNumber.matches() == false)
                    {
                        System.out.println("--Вы некорректно ввели номер автобуса. Повторите попытку ввода");
                        System.out.println("--Ошибка возникла при вводе последних данных. Вам необходимо провести процедура ввода последней строки заново");
                    }
                    if (matcherName.matches() == false)
                    {
                        System.out.println("--Вы неккоректно ввели ФИО. Повторите попытку ввода");
                        System.out.println("--Ошибка возникла при вводе последних данных. Вам необходимо провести процедура ввода последней строки заново");
                    }
                    System.out.println("--Нажмите ENTER для продолжения");
                    count_correction_regex++;
                }
            }
        }
        if (NewId < correct_id1 || NewId > correct_id2)
        {
            System.out.println("--Вы ввели несуществующий id");
            System.out.println("--Повторите попытку еще раз");
            count_action++;
        }

    }

}
