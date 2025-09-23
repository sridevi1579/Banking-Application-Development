<%@ page import="java.sql.*" %>
<%
String transaction_reference = request.getParameter("tref");
String cus_id = request.getParameter("cid");

String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    Connection conn = DriverManager.getConnection(url, "ssumathi", "pycedsee");
    String query = "SELECT c.family_id, pa.percent_added, SUM(t.t_points) AS total_points FROM Customers c JOIN Families f ON c.family_id = f.family_id JOIN Transactions t ON c.cid = t.cid JOIN Point_Accounts pa ON c.cid = pa.cid WHERE c.cid = " + cus_id + "AND t.tref = " + transaction_reference + "AND pa.family_id = c.family_id GROUP BY c.family_id, pa.percent_added";
    Statement stmt=conn.createStatement();
    ResultSet rs=stmt.executeQuery(query);
   
    while (rs.next()) { 
        out.print(rs.getObject(1) + "$" + rs.getObject(2) + "$" + rs.getObject(3));
    }
    
    conn.close();
} catch (Exception e) {
    out.println("Exception: " + e.getMessage());
    e.printStackTrace();
}
%>


