import java.io.*;
import java.util.HashMap;

public class DBUtils {
    public static final String DBFolderPath = "./MiniDB";
    public static final String DBDetailsFilePath = DBFolderPath + "/DBDetails.txt";

    public static void createDB(String name)  {
        File newDB = new File(DBFolderPath+"/"+name);
        if(!newDB.exists()) {
            newDB.mkdirs();
        }

        File DBDetails = new File(DBDetailsFilePath);
        if(!DBDetails.exists()) {
            try {
                DBDetails.createNewFile();
            }catch(IOException e){
                System.out.println(e);
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(DBDetailsFilePath, true));
            writer.write(name+"\n");
            writer.close();
        }catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void checkForDataBases(HashMap<String, DataBase> DataBases) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(DBDetailsFilePath));
            String str;
            while((str = reader.readLine()) != null) {
                DataBases.put(str, new DataBase(str,DBFolderPath+"/"+str));
            }
        }catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void useDB(String DBName) {

    }
}
