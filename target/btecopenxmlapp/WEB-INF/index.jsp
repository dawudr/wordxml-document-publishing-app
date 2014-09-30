<%--
  Created by IntelliJ IDEA.
  User: Dawud
  Date: 14/09/14
  Time: 03:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Spring File Upload Example</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <fieldset>
                <legend>Spring Mvc File Upload Example</legend>
                <form class="form-horizontal" method="post" action='fileupload' enctype="multipart/form-data">
                    <div class="control-group">
                        <label class="control-label">Upload File</label>
                        <div class="controls">
                            <input type="file" name="file1" id="file1" value="">
                        </div>
                    </div>
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