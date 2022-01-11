import java.util.HashMap;

public class Main {
    public static DataBase currentDB;
    public static HashMap<String, DataBase> DataBases;

    Main() {
        DataBases = new HashMap<>();
        DBUtils.checkForDataBases(DataBases);
    }
}