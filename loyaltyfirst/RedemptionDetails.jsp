<%@ page import="java.sql.*" %>
<%
String prize_id = request.getParameter("prizeid");
String cus_id = request.getParameter("cid");
String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    Connection conn = DriverManager.getConnection(url, "ssumathi", "pycedsee");
    String query = "SELECT p.p_description, p.points_needed, to_char(rh.r_date,'YYYY-MM-DD')as r_date, ec.center_name FROM Redemption_History rh JOIN Prizes p ON rh.prize_id = p.prize_id JOIN Exchgcenters ec ON rh.center_id = ec.center_id WHERE rh.cid = " + cus_id + "AND rh.prize_id = " + prize_id;
    Statement stmt=conn.createStatement();
    ResultSet rs=stmt.executeQuery(query);
   
    while (rs.next()) { 
        out.print(rs.getObject(1) + "$" + rs.getObject(2) + "$" + rs.getObject(3) + "$" + rs.getObject(4) + "@");
    }
    
    conn.close();
} catch (Exception e) {
    out.println("Exception: " + e.getMessage());
    e.printStackTrace();
}
%>


