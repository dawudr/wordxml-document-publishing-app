<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>System Web Server Information</title>
</head>
<body>

<%

String scheme = request.getScheme();             // http
String serverName = request.getServerName();     // hostname.com
int serverPort = request.getServerPort();        // 80
String contextPath = request.getContextPath();   // /mywebapp
String servletPath = request.getServletPath();   // /servlet/MyServlet
String pathInfo = request.getPathInfo();         // /a/b;c=123
String queryString = request.getQueryString();          // d=789

// Reconstruct original requesting URL
StringBuffer url =  new StringBuffer();
url.append(scheme).append("://").append(serverName);

if ((serverPort != 80) && (serverPort != 443)) {
    url.append(":").append(serverPort);
}

url.append(contextPath).append(servletPath);

if (pathInfo != null) {
    url.append(pathInfo);
}
if (queryString != null) {
    url.append("?").append(queryString);
}

out.println("This page server path: " + url);

%>

</body>
</html>