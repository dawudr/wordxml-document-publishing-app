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
            <fieldset>
                <legend>Word XML Tagged Document Upload - Select a BTEC Spec to ingest and transform to Open XML</legend>
                <form class="form-horizontal" method="post" action='fileupload' enctype="multipart/form-data">
                    <div class="control-group">
                        <label class="control-label">Upload File</label>
                        <div class="controls">
                            <input type="file" name="file1" id="file1" value="">
                        </div>
                    </div>
                    <br/>
                    <div class="control-group">
                        <label class="control-label">Upload File</label>
                        <div class="controls">
                            <input type="file" name="file2" id="file2" value="">
                        </div>
                    </div>

                    <div class="form-actions">

                        <button type="submit" class="btn btn-success">Upload Files</button>
                    </div>
                </form>
            </fieldset>
        </div>
    </div>
</div>
</body>
</html>