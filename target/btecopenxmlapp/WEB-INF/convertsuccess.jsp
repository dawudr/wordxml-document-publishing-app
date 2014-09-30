<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Pearson Qualification Tool</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

    <link href="<c:url value="css/style.css" />" rel="stylesheet" type="text/css"/>

</head>
<body>
<nav class="navbar-override navbar-default linearBg" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <img src="<c:url value="css/logo.png" />" height="50" alt="Pearson"/>

    <div class="navbar-header">

    </div>
</nav>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="page-header">
                <h2>Document Converted to Open XML Successfully - <small>Select an action to continue</small></h2>
            </div>


            <table class="table table-striped">
                <tr>
                    <td>File Name</td><td>Action</td><td>OpenXML Format</td><td>IQS Format</td>
                </tr>
                    <tr>
                            <div class="control-group">
                                <td><c:out value="${filename}" /></td>

                                <td>
                                    <form class="form-horizontal" method="post" action='transform'>
                                        <input type="hidden" name="filename" value="<c:out value="${filename}" />"/>
                                        <button type="submit" class="btn btn-success" name="action" value="transform">Transform</button>
                                    </form>
                                </td>

                                <!-- Open XML file -->
                                <td>
                                    <form class="form-horizontal" method="get" action='filedownload'>
                                        <input type="hidden" name="filename" value="<c:out value="${filename}" />"/>

                                        <c:out value="${filename}"/>
                                        <c:choose>
                                            <c:when test="${!empty filename}">
                                                <button type="submit" class="btn btn-primary" name="action" value="download-openxml">Download</button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn btn-primary" disabled="disabled">Download</button>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </td>

                                <td><button type="submit" class="btn btn-primary" name="download-iqsxml" disabled="disabled">Download</button></td>
                            </div>
                    </tr>
            </table>

            <form class="form-horizontal pull-right" method="get" action='browsefolder'>
                <button type="submit" class="btn btn-success">Next</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>