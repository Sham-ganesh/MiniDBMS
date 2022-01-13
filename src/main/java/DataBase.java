import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {
    public String DBName;
    public String filePath;
    public String tableDetailsPath;
    public HashMap<String, Table> tables = new HashMap<>();

    DataBase(String DBName, String filePath) {
        this.DBName = DBName;
        this.filePath = filePath;
        tableDetailsPath = filePath+"/tableDetails.txt";
        checkForTables();
    }
    public void processCreateQuery(String query) {
        String[] str = query.split(" ");

        //demo create (id-int,name-String,phn-int,sal-double)
        String[] fi=new String[20];
        String[] dt=new String[20];
        String temp=str[2];
        temp=temp.substring(1,temp.length()-1);
        System.out.println(temp);
        String[] temp2=new String[20];
        temp2=temp.split(",");
        for(int i=0;i<temp2.length;i++)
        {
            String[] val=new String[3];
            val=temp2[i].split("-");
            fi[i]=val[0];
            dt[i]=val[1];
            System.out.println(fi[i]+ " "+ dt[i]);
        }
        String linet1="",linet2="";
        for(int i=0;i< fi.length;i++)
        {
            if(fi[i]==null)
                break;
            linet1+=fi[i]+",";
            if(dt[i].equals("int")) {
                linet2 = linet2 + "\\d+,";
            }
            else if(dt[i].equals("double")) {
                linet2 = linet2 + "\\d*[.]\\d+,";
            }
            else if(dt[i].equals("date")) {
                linet2 = linet2 + "\\d{2}[/]\\d{2}[/]\\d{4},";
            }
            else if(dt[i].equals("String")) {
                linet2 = linet2 + "String,";
            }
            else{
                System.out.println("Invalid Datatype!!\n" + dt[i] +" dataype not found!" );
                return;
            }
        }
        linet1=linet1.substring(0,linet1.length()-1);
        linet2=linet2.substring(0,linet2.length()-1);

        createTable(str[0], linet1, linet2);
        tables.put(str[0], new Table(str[0], filePath+"/"+str[0]+".txt"));
    }
    private void createTable(String tableName, String colNames, String colTypes) {
        File table = new File(filePath+"/"+tableName+".txt");
        if( table.exists() ) {
            System.out.println("Table already exists");
            return;
        }
        else {
            try {
                table.createNewFile();
                File tableDetailFile = new File(tableDetailsPath);
                if(!tableDetailFile.exists())
                    tableDetailFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(tableDetailFile, true));
                writer.write(tableName+"\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath+"/"+tableName+".txt", true));
            writer.write(colNames+"\n");
            writer.write(colTypes+"\n");
            writer.close();
        }catch (IOException e ) {
            System.out.println(e);
        }
    }
    public void dropTable(String tableName) {
        File table = new File(filePath+"/"+tableName+".txt");
        if(table.delete()) {
            System.out.println("Table " + tableName + " Dropped successfully");
        }

        ArrayList<String> nameList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tableDetailsPath));
            String str;
            while ((str = reader.readLine()) != null) {
                if (str.equals(tableName))
                    continue;
                nameList.add(str);
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(tableDetailsPath));
            writer.write("");
            writer.close();

            writer = new BufferedWriter(new FileWriter(tableDetailsPath, true));
            for (int i = 0; i < nameList.size(); i++) {
                System.out.println(nameList.get(i));
                writer.write(nameList.get(i) + "\n");
            }
            writer.close();

            tables.remove(tableName);
        }catch (IOException e) {
            System.out.println(e);
        }
    }
    private void checkForTables() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tableDetailsPath));
            String str;
            while((str = reader.readLine()) != null) {
                tables.put(str, new Table(str,filePath+"/"+str+".txt"));
            }
        }catch (IOException e) {
            System.out.println(e);
        }
    }
    public void generateReport() {

    }
}