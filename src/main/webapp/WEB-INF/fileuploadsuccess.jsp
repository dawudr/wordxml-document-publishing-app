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

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="//www.fuelcdn.com/fuelux/3.0.1/css/fuelux.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="//www.fuelcdn.com/fuelux/3.0.1/js/fuelux.min.js"></script>

    <link href="<c:url value="css/style.css" />" rel="stylesheet" type="text/css"/>

    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css">


    <script type='text/javascript'>//<![CDATA[
    $(window).load(function(){
        $(function(){
            $('a, button').click(function() {
                $(this).toggleClass('active');
            });
        });
    });//]]>

    </script>



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
            <br/>
            <div class="alert alert-success" role="alert">
                <strong>File Uploaded Successfully </strong> - Select an action to continue
            </div>


            <table class="table table-striped">
                <tr>
                    <td>File Name</td><td>Action</td><td>OpenXML Format</td><td>IQS Format</td>
                </tr>
                <c:forEach items="${files}" var="filename">
                    <tr>
                        <form class="form-horizontal" method="post" action='convert'>
                            <div class="control-group">
                                <td>
                                    <c:out value="${filename}" />
                                    <input type="hidden" name="filename" value="<c:out value="${filename}" />"/>
                                </td>
                                <td>
                                    <button type="submit" class="btn btn-primary has-spinner" name="convert">Convert
                                        <span class="spinner"><i class="icon-spin icon-refresh"></i></span>
                                    </button>
                                </td>
                                <td><button type="submit" class="btn btn-primary"  name="action" value="download-openxml" disabled="disabled">Download</button></td>
                                <td><button type="submit" class="btn btn-primary"  name="action" value="download-iqsxml" disabled="disabled">Download</button></td>
                            </div>
                        </form>
                    </tr>
                </c:forEach>

            </table>


            <form class="form-horizontal pull-right" method="get" action='browsefolder'>
                <button type="submit" class="btn btn-success">Next</button>
            </form>
        </div>
    </div>
</div>



<!-- Modal -->
<%--
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Please wait transforming document..</h4>
            </div>
            <div class="modal-body">
                <div class="loader" data-initialize="loader"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
--%>




</body>
</html>