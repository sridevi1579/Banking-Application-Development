<%@ page import="java.sql.*" %>
<%
String transaction_reference = request.getParameter("tref");
String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    Connection conn = DriverManager.getConnection(url, "ssumathi", "pycedsee");
    String query = "SELECT to_char(t_date,'YYYY-MM-DD')as t_date, t.t_points, p.prod_name, p.prod_points, tp.quantity FROM Transactions t JOIN Transactions_Products tp ON t.tref = tp.tref JOIN Products p ON tp.prod_id = p.prod_id WHERE t.tref = " + transaction_reference ;
    Statement stmt=conn.createStatement();
    ResultSet rs=stmt.executeQuery(query);
    while (rs.next()) { 
        out.print(rs.getObject(1) + "$" + rs.getObject(2) + "$" + rs.getObject(3) + "$" + rs.getObject(4) + "$" + rs.getObject(5) + "@");
    }
    
    conn.close();
} catch (Exception e) {
    out.println("Exception: " + e.getMessage());
    e.printStackTrace();
}
%>

