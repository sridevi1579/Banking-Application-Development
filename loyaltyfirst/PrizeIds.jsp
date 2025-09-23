<%@ page import="java.sql.*" %>
<%
String cus_id = request.getParameter("cid");

String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    Connection conn = DriverManager.getConnection(url, "ssumathi", "pycedsee");
    String query = "SELECT DISTINCT rh.prize_id FROM Redemption_History rh WHERE rh.cid = " + cus_id;
    Statement stmt=conn.createStatement();
    ResultSet rs=stmt.executeQuery(query);
    
    while (rs.next()) {
        out.print(rs.getObject(1) + "$");
    }

    conn.close();
} catch (Exception e) {
    out.println("Exception: " + e.getMessage());
    e.printStackTrace();
}
%>


