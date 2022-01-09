import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DBUtils {
    public static final String DBFolderPath = "/Users/yogeeswar/Desktop/MiniDBMS/MiniDB";
    public static final String DBDetailsFilePath = DBFolderPath + "/DBDetails.txt";

    public static void createDB(String name)  {
        File newDB = new File(DBFolderPath+"/"+name);
        if(!newDB.exists()) {
            newDB.mkdirs();
        }else {
            System.out.println("DB already exists");
            return;
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
    public static void deleteDB(String name) {
        File table = new File(DBFolderPath+"/"+name);
        if(table.delete()) {
            System.out.println("DataBase " + name + " Deleted successfully");
        }

        ArrayList<String> nameList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(DBDetailsFilePath));
            String str;
            while((str = reader.readLine()) != null) {
                if(str.equals(name))
                    continue;
                nameList.add(str);
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(DBDetailsFilePath));
            writer.write("");
            writer.close();

            writer = new BufferedWriter(new FileWriter(DBDetailsFilePath, true));
            for(int i=0;i<nameList.size();i++) {
                System.out.println(nameList.get(i));
                writer.write(nameList.get(i) + "\n");
            }
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
        Main.currentDB = Main.DataBases.get(DBName);
    }
}
