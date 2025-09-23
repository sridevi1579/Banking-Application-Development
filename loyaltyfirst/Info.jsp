<%@ page import="java.sql.*" %>
<%
String cus_id = request.getParameter("cid");

String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

try {
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    Connection conn = DriverManager.getConnection(url, "ssumathi", "pycedsee");
    String query = "SELECT c.cname, p.num_of_points AS customers_points FROM customers c JOIN point_accounts p ON c.cid = p.cid WHERE c.cid = "+cus_id;
    Statement stmt=conn.createStatement();
    ResultSet rs=stmt.executeQuery(query);
    if (rs.next()) {
        out.println(rs.getString("cname") + "$" + rs.getInt("customers_points"));
    } else {
        out.println("<div style='color: black; font-size: 24px; margin-top: 10px;'>");
        out.println("<p><strong>Error :</strong>Customer not found !</p>");
        out.println("</div>");
    }

    conn.close();
} catch (Exception e) {
    out.println("Exception: " + e.getMessage());
    e.printStackTrace();
}
%>

