<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>database</title>
    <style>
        .input{
          width: 20%;
          padding: 20px;
          box-sizing: border-box;
          margin-bottom: 25px;
          border: 2px solid #dedede;
          color: black;
          font-size: 14px;
          outline: none;
          transform: all 0.5s ease;
        }

        .btn{
          width: 20%;
          background: darkblue;
          height: 60px;
          text-align: center;
          line-height: 60px;
          color: #fff;
          font-weight: bold;
          cursor: pointer;
          margin-bottom: 30px;
        }
        table, th, td {
          border: 1px solid black;
        }
        .button {
          border: none;
          color: white;
          width: 100%;
          padding: 15px 32px;
          text-align: center;
          text-decoration: none;
          display: inline-block;
          font-size: 16px;
          margin: 4px 2px;
          cursor: pointer;
        }

        .button1 {background-color: #4CAF50;} /* Green */
        .button2 {background-color: #DC143C;} /* Blue */
        .button3 {background-color: #FFA500;} /* Orange */
    </style>
</head>
<body>
    <center>
        <br>
        <h1>DATABASES</h1>
        <table style="width:70%">
            <tr>
                <th>DATABASE NAME</th>
                <th>SELECT</th>
                <th>DELETE</th>
            </tr>

            <%
                List DBNames = (List) request.getAttribute("DBNames");

                for(int i=0;i<DBNames.size();i++) {
                    String name = (String) DBNames.get(i);
                    out.print("<tr>"
                              + "<td><center>" + name + "</center></td>"
                              + "<td><a href='/MiniDBMS/database?name="+name+"&act=select'><button class='button button1'>SELECT</button></a></td>"
                              + "<td><a href='/MiniDBMS/database?name="+name+"&act=delete'><button class='button button2'>DELETE</button></a></td>"
                              + "<td><a href='/MiniDBMS/database?name="+name+"&act=export'><button class='button button3'>EXPORT</button></a></td>"
                              + "</tr>");
                }
            %>

        </table>
        <br>
        <form action="database" method="get">
            <input type="text" placeholder="Name" class="input" name="name">
            <input type="submit" value="CREATE DATABASE" name="createBtn" class="btn">
        </form>
    </center>
</body>
</html>