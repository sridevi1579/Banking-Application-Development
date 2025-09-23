<%@ page import="java.sql.*" %>
<%
String family_id = request.getParameter("fid");
String cus_id = request.getParameter("cid");
String num_of_points = request.getParameter("npoints");

String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    Connection conn = DriverManager.getConnection(url, "ssumathi", "pycedsee");
    String query = "UPDATE Point_Accounts SET num_of_points = num_of_points + " + num_of_points + "WHERE family_id = " + family_id + "AND cid != " + cus_id;
    Statement stmt=conn.createStatement();
    int updatedrows = stmt.executeUpdate(query);
    
    if (updatedrows > 0) {
        out.print("Point accounts of the family members of the customer have been updated successfully.");
    } else {
        out.println("Update failed");
    }
    
    conn.close();
} catch (Exception e) {
    out.println("Exception: " + e.getMessage());
    e.printStackTrace();
}
%>



