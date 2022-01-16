import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Table extends Component {
    public String tableName;
    public String filePath;

    Table(String tableName, String filePath) {
        this.tableName = tableName;
        this.filePath = filePath;
    }

    public void insert(String str) throws IOException {
        String field,type;
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
        PrintWriter out = new PrintWriter(bw);
        BufferedReader in = new BufferedReader(new FileReader(filePath));

        String[] f,d;
        field = in.readLine();
        type=in.readLine();
        f=str.split(",");
        d=type.split(",");
        int i;
        for(i=0;i<f.length;i++) {
            if(!d[i].equals("String"))
            {
                if(f[i].matches(d[i]))
                    continue;
                else {
                    System.out.println("Type_MissMatch : " + f[i] + " is not compatible!");
                    return;
                }
            }
        }

        out.println(str);

        out.close();
        in.close();
    }
    public void update(String str,String val,int i) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"));
        PrintWriter out = new PrintWriter(bw);
        BufferedReader in = new BufferedReader(new FileReader(filePath));

        String line1;
        line1=in.readLine();out.println(line1);
        line1=in.readLine();out.println(line1);
        String[] f= new String[20];
        int flag=0;
        while((line1=in.readLine())!=null) {
            f = line1.split(",");
            if (f[i].equals(val)){
                out.println(str);
                flag=1;
            }
            else
                out.println(line1);
        }
        if(flag==0)
            JOptionPane.showMessageDialog(this,
                    "No record found with Field value :" + val,
                    "NOTE", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(str+ " " + val + " " + i);
        out.close();in.close();
        bw = new BufferedWriter(new FileWriter(filePath));
        out = new PrintWriter(bw);
        in = new BufferedReader(new FileReader("temp.txt"));
        while((line1=in.readLine())!=null) {
            out.println(line1);
        }
        File tempFile = new File("temp.txt");
        tempFile.delete();
        out.close();in.close();
    }
    public void delete(String val,int i) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("temp.txt"));
        PrintWriter out = new PrintWriter(bw);
        BufferedReader in = new BufferedReader(new FileReader(filePath));

        String line1;
        line1=in.readLine();out.println(line1);
        line1=in.readLine();out.println(line1);
        String[] f= new String[20];
        int flag=0;
        while((line1=in.readLine())!=null) {
            f = line1.split(",");
            if (f[i].equals(val)) {
                flag = 1;
                continue;
            }
            else
                out.println(line1);
        }
        if(flag==0)
            JOptionPane.showMessageDialog(this,
                    "No record found with Field value " + val,
                    "NOTE", JOptionPane.INFORMATION_MESSAGE);
        System.out.println( val + " " + i);
        out.close();in.close();
        bw = new BufferedWriter(new FileWriter(filePath));
        out = new PrintWriter(bw);
        in = new BufferedReader(new FileReader("temp.txt"));
        while((line1=in.readLine())!=null) {
            out.println(line1);
        }
        File tempFile = new File("temp.txt");
        tempFile.delete();
        out.close();in.close();
    }
    public ArrayList<String> select(String[] str) throws IOException {
        //tablename select all //3
        //tablename select (rno,name) //3
        //tablename select all where rno=2 //5
        //tablename select (rno,name) where rno=2 //5
        ArrayList<String> ans= new ArrayList<String>();
        String field,val,type;int i;
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        String[] f= new String[20];
        String[] q= new String[20];

        field = in.readLine();
        type=in.readLine();
        Integer[] v=new Integer[f.length];
        for(i=0;i<20;i++)
            v[i]=0;
        f=field.split(",");
        if(str.length==3 && str[2].equals("all"))
        {
            for(i=0;i<f.length;i++)
                System.out.print(f[i] + " ");
            ans.add(field);

            while((type=in.readLine())!=null){
                f=type.split(",");
                System.out.println();
                ans.add(type);
                for(i=0;i<f.length;i++)
                    System.out.print(f[i] + " ");
            }
        }
        else if(str.length==3 ) {
            String temp="";
            str[2]=str[2].substring(1,str[2].length()-1);
            q=str[2].split(",");
            int j,flag;
            for(j=0;j<q.length;j++) {
                flag=0;
                for (i = 0; i < f.length; i++) {
                    if (q[j].equals(f[i])) {
                        flag = 1;
                        v[i] = 1;
                    }
                }
                if(flag==0) {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Syntax.....!\nField " + q[j] +" doesn't exist in the table",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
            for(i=0;i<f.length;i++)
                if(v[i]==1) {
                    temp+=f[i]+" ";
                    System.out.print(f[i] + " ");
                }
            ans.add(temp);
            while((type=in.readLine())!=null){
                temp="";
                f=type.split(",");
                for(i=0;i<f.length;i++)
                    if(v[i]==1) {
                        temp+=f[i]+" ";
                        System.out.print(f[i] + " ");
                    }
                ans.add(temp);
                System.out.println();
            }
        }
        else if( str.length==5 && str[2].equals("all") && str[3].equals("where") )
        {
            String t=str[4];int ind=-1,flag=0;
            String[] t1=str[4].split("=");
            t=t1[0];
            ans.add(field);
            for(i=0;i<f.length;i++)
                if(f[i].equals(t))
                    ind=i;
            if(ind==-1)
            {
                JOptionPane.showMessageDialog(this,
                        "Invalid Syntax.....!\nField " + t +" doesn't exist in the table",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
               System.out.println("No field named " + t);
                return null;
            }
            t=t1[1];
            while((type=in.readLine())!=null){
                f=type.split(",");

                if(f[ind].equals(t)) {
                    ans.add(type);
                    for (i = 0; i < f.length; i++)
                        System.out.print(f[i] + " ");
                    System.out.println();
                }
            }
        }
        else if(str.length==5){
            String temp="";
            str[2]=str[2].substring(1,str[2].length()-1);
            q=str[2].split(",");
            int j,flag;
            for(j=0;j<q.length;j++) {
                flag=0;
                for (i = 0; i < f.length; i++) {
                    if (q[j].equals(f[i])) {
                        v[i] = 1;
                        flag = 1;
                        break;
                    }
                }
                if(flag==0) {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Syntax.....!\nField " + q[j] +" doesn't exist in the table",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
            for(i=0;i<f.length;i++)
                if(v[i]==1) {
                    temp += f[i] + " ";
                    System.out.print(f[i] + " ");
                }
            ans.add(temp);
            String t=str[4];int ind=-1;
            String[] t1=str[4].split("=");
            t=t1[0];
            for(i=0;i<f.length;i++)
                if(f[i].equals(t))
                    ind=i;
            if(ind==-1)
            {
                JOptionPane.showMessageDialog(this,
                    "Invalid Syntax.....!\nField " + t +" doesn't exist in the table",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
                //System.out.println("No field named " + t);
                return null;
            }
            t=t1[1];
            while((type=in.readLine())!=null){
                f=type.split(",");
                if(f[ind].equals(t)) {
                    temp="";
                    for (i = 0; i < f.length; i++)
                        if (v[i] == 1) {
                            temp+=f[i]+" ";
                            System.out.print(f[i] + " ");
                        }
                    ans.add(temp);
                    System.out.println();
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Invalid Syntax.....!!!",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        //System.out.println("Answer stored in matrix :");
        //System.out.println(ans);
        return ans;
    }
    public ArrayList<String> query_processing(String q) throws IOException {
        String[] str=new String[20];
        str=q.split(" ");int i;
        String data="",chac,chav,conc,conv;
        int  chai=-1,coni=-1;
        String[] temp1=new String[20];

        System.out.println(str[1].charAt(0));
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        String[] col=new String[20];
        String line1;
        line1=in.readLine();
        col=line1.split(",");
        line1=in.readLine();
        switch(str[1].charAt(0)){
            case 'I' :
            case 'i' :
                //tablename insert values (1,sham,34)
                //table insert (rno,name) values (1,sham)
                if(str.length==4)
                {
                    str[3]=str[3].substring(1,str[3].length()-1);
                    insert(str[3]);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Invalid Syntax...Check your Syntax ",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 'S' :
            case 's' :
                return select(str);
            case 'U' :
            case 'u':
                //tablename update name=sham where rno=5;
                temp1=str[2].split("=");
                //stores name,sham
                chac=temp1[0];
                chav=temp1[1];

                //stores rno,7
                temp1=str[4].split("=");
                conc=temp1[0];
                conv=temp1[1];

                for(i=0;i<col.length;i++)
                {
                    if(col[i].equals(chac))
                        chai=i;
                    if(col[i].equals(conc))
                        coni=i;

                }
                while((line1=in.readLine())!=null) {
                    temp1= line1.split(",");
                    if(temp1[coni].equals(conv))
                    {
                        temp1[chai]=chav;
                        for(i=0;i<temp1.length-1;i++)
                            data+=temp1[i]+",";
                        data+=temp1[temp1.length-1];
                        System.out.println("parameter : "+data + "  : "+ chav + " : " + coni);
                        break;
                    }
                }
                in.close();
                update(data,conv,coni);
                break;
            case 'D' :
            case 'd' :
                //tablename delete record where rno=7
                //stores rno,7
                temp1=str[4].split("=");
                conc=temp1[0];
                conv=temp1[1];

                for(i=0;i<col.length;i++)
                    if(col[i].equals(conc))
                        coni=i;

                //System.out.println("parameter : "+ conv + " : " + coni);
                in.close();
                delete(conv,coni);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid Syntax...Check your Syntax ",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                //System.out.println("Invalid Syntax...Check your Syntax");
        }
        q="table select all";
        str=q.split(" ");
        return select(str);
    }
}