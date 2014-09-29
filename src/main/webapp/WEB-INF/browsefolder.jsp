<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Pearson Qualification Tool</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="//www.fuelcdn.com/fuelux/3.0.1/css/fuelux.min.css">

    <link href="<c:url value="css/style.css" />" rel="stylesheet" type="text/css"/>

</head>
<body>
<nav class="navbar-override navbar-default linearBg" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <img src="<c:url value="css/logo.png" />" height="50" alt="Pearson"/>

    <div class="navbar-header">
    </div>
</nav>

<br/>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="panel panel-default">
                <div class="panel-heading text-center">
                    <h1>Word - OpenXML - IQS - PXE Transform Tool</h1>
                    <h5>for Pearson BTEC Qualifications</h5>
                    <h4>Imports and converts BTEC Specification Word XML Tagged Document to OpenXML(Universal - PXE) and
                        IQS(PQS) formats</h4>
                </div>
                <div class="panel-body">

                    <fieldset>

                        <p><h2>Upload Microsoft Word file (.doc,.docx,.docm)</h2></p>
                        <form method="post" action='fileupload' enctype="multipart/form-data">
                        <table class="table table-striped">
                                            <tr>
                                                <td>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <input type="file" name="file1" id="file1" value="">
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <%--                                            <tr>
                                                                                            <td>
                                                                                                <div class="control-group">
                                                                                                    <div class="controls">
                                                                                                        <input type="file" name="file2" id="file2" value="">
                                                                                                    </div>
                                                                                                </div>
                                                                                            </td>
                                                                                        </tr>--%>
                                        </table>
                            <div class="form-actions pull-right">
                                <button type="submit" class="btn btn-success">Upload Files</button>
                            </div>
                        </form>
                    </fieldset>
                </div>
            </div>
        </div>
    </div>

    <!-- if files exist -->
    <c:if test="${!empty browse}">


    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <h2>Uploaded File Details</h2>
                <table class="table table-striped">
                    <tr>
                        <td>File Name</td>
                        <td>Action</td>
                        <td>OpenXML Format</td>
                        <td>IQS Format</td>
                        <td>Delete</td>
                    </tr>
                    <c:forEach items="${browse}" var="fileNamesMap">
                        <tr>
                            <div class="control-group">

                                <!-- Word file -->
                                <td><c:out value="${fileNamesMap['Word']}"/></td>


                                <!-- Convert -->
                                <td>
                                    <c:choose>
                                        <c:when test="${empty fileNamesMap['OpenXml']}">
                                            <form class="form-horizontal" method="post" action='convert'>
                                                <input type="hidden" name="filename" value="<c:out value="${fileNamesMap['Word']}" />"/>
                                                <button type="submit" class="btn btn-success" name="action" value="convert">Convert</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form class="form-horizontal" method="post" action='transform'>
                                                <input type="hidden" name="filename" value="<c:out value="${fileNamesMap['OpenXml']}" />"/>
                                                <c:choose>
                                                    <c:when test="${empty fileNamesMap['IQSXml']}">
                                                        <button type="submit" class="btn btn-success" name="action" value="transform">Transform</button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button type="submit" class="btn btn-success" disabled="disabled">Transform</button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </td>


                                <!-- Open XML file -->
                                <td>
                                    <form class="form-horizontal" method="get" action='filedownload'>
                                        <input type="hidden" name="filename" value="<c:out value="${fileNamesMap['OpenXml']}" />"/>

                                        <c:out value="${fileNamesMap['OpenXml']}"/>
                                        <c:choose>
                                            <c:when test="${!empty fileNamesMap['OpenXml']}">
                                                <button type="submit" class="btn btn-primary" name="action" value="download-openxml">Download</button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn btn-primary" disabled="disabled">Download</button>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </td>


                                <!-- IQS XML file -->
                                <td>
                                    <form class="form-horizontal" method="get" action='filedownload'>
                                        <c:out value="${fileNamesMap['IQSXml']}"/>
                                        <c:choose>
                                            <c:when test="${!empty fileNamesMap['IQSXml']}">
                                                <input type="hidden" name="filename" value="<c:out value="${fileNamesMap['IQSXml']}" />"/>
                                                <button type="submit" class="btn btn-primary" name="action" value="download-iqsxml">Download</button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn btn-primary" disabled="disabled">Download</button>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </td>


                                <!-- Delete -->
                                <td>
                                    <form class="form-horizontal" method="post" action='browsefolder'>
                                        <input type="hidden" name="filename"
                                               value="<c:out value="${fileNamesMap['Word']}"/>"/>
                                        <button type="submit" class="btn btn-danger" name="action" value="delete">
                                            Delete
                                        </button>
                                    </form>
                                </td>

                            </div>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
    </c:if>

</div>


</body>
</html>