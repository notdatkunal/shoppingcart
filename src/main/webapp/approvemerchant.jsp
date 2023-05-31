<%@page import="com.shoppingcart.dto.Admin"%>
<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.shoppingcart.dto.Merchant"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.Query"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
EntityManagerFactory emf = Persistence.createEntityManagerFactory("kunal");
EntityManager em = emf.createEntityManager();
		
Query query = em.createQuery("select a from Merchant a where a.status != 'active' ");
List<Merchant> merchants =  query.getResultList();


if(merchants.size()==0){
	PrintWriter pw = response.getWriter();
	pw.print("<script>alert(\"no merchants to be approved\")</script>");
	request.getRequestDispatcher("adminoptions.html").include(request, response);
	
}else{
%>


<table id="table-1" border="1px" cellspacing="5px">
	<thead>
		<tr>
			<th>merchants id</th>
			<th>merchants name</th>
			<th>email</th>
			<th>status</th>
		</tr>
	</thead>
	<tbody>
	<% 
for(Merchant merchant:merchants){
	
	
	
	
%>
		<tr>
			<td><%=merchant.getId()%></td>
			<td><%=merchant.getName()%></td>
			<td><%=merchant.getEmail()%></td>
			<td><a href="merchantstatus.jsp?id=<%=merchant.getId()%>" title="<%=merchant.getStatus()%>">activate</a></td>
			
		</tr>
		<%
		
}} %>
	</tbody>
	
</table>

</body>
</html>