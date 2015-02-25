<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Pearson Qualification Tool</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

    <!-- Optional theme -->
<%--
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
--%>


    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="//www.fuelcdn.com/fuelux/3.0.1/css/fuelux.min.css">


    <!-- Morris -->
    <link href="static/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">

    <link href="static/css/animate.css" rel="stylesheet">
    <link href="static/css/style.css" rel="stylesheet">

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

<div id="wrapper">
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element"> <span>
                            <img alt="image" class="img-circle" src="static/img/logo.png"/>
                             </span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">Paul Winser</strong>
                             </span> <span class="text-muted text-xs block">Administrator <b class="caret"></b></span> </span> </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="profile.html">Profile</a></li>
                            <li class="divider"></li>
                            <li><a href="login.html">Logout</a></li>
                        </ul>
                    </div>
                    <div class="logo-element">
                        IN+
                    </div>
                </li>
                <li class="active">
                    <a href="dashboard"><i class="fa fa-th-large fa-5x"></i><br/> <span class="nav-label">Dashboards</span><%--<span class="label label-info">NEW</span> <span class="fa arrow"></span>--%></a>
<%--                    <ul class="nav nav-second-level">
                        <li class="active"><a href="tranforms_recent">Recent transforms</a></li>
                        <li><a href="transforms_history">Transform history</a></li>
                    </ul>--%>
                </li>
                <li>
                    <a href="downloads"><i class="fa fa-download fa-5x"></i> <br/><span class="nav-label">Downloads</span><%--<span class="fa arrow"></span>--%><span class="label label-warning">16</span></a>
<%--                    <ul class="nav nav-second-level">
                        <li><a href="downloads_recent">Recent</a></li>
                        <li><a href="downloads_history">Historic</a></li>
                    </ul>--%>
                </li>
                <li>
                    <a href="wizards"><i class="fa fa-magic fa-5x"></i><br/> <span class="nav-label">Wizards </span></a>
                </li>
                <li>
                    <a href="batchconversion"><i class="fa fa-gears fa-5x"></i><br/> <span class="nav-label">Batch conversion</span> </a>
                </li>
                <li>
                    <a href="transformdetails"><i class="fa fa-desktop fa-5x"></i><br/> <span class="nav-label">Transform details</span></a>
<%--                    <ul class="nav nav-second-level">
                        <li><a href="wordspecdetail">Word Spec detail</a></li>
                        <li><a href="transformreport">Transform report</a></li>
                        <li><a href="editor">Editor</a></li>
                        <li><a href="preview">Preview</a></li>
                    </ul>--%>
                </li>
                <li>
                    <a href="#"><i class="fa fa-wrench fa-5x"></i> <br/><span class="nav-label">Configuration</span><%--<span class="fa arrow"></span>--%></a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-users fa-5x"></i><br/><span class="nav-label">User Management</span></a>
                </li>

            </ul>
        </div>
    </nav>



    <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                    <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                        <div class="form-group">
                            <input type="text" placeholder="Search for something..." class="form-control" name="top-search" id="top-search">
                        </div>
                    </form>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li>
                        <span class="m-r-sm text-muted welcome-message">Welcome to Btec Transformations.</span>
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-envelope"></i>  <span class="label label-warning">16</span>
                        </a>
                        <ul class="dropdown-menu dropdown-messages">
                            <li>
                                <div class="dropdown-messages-box">
                                    <a href="profile.html" class="pull-left">
                                        <img alt="image" class="img-circle" src="img/a7.jpg">
                                    </a>
                                    <div>
                                        <small class="pull-right">46h ago</small>
                                        <strong>Mike Loreipsum</strong> started following <strong>Monica Smith</strong>. <br>
                                        <small class="text-muted">3 days ago at 7:58 pm - 10.06.2014</small>
                                    </div>
                                </div>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="dropdown-messages-box">
                                    <a href="profile.html" class="pull-left">
                                        <img alt="image" class="img-circle" src="img/a4.jpg">
                                    </a>
                                    <div>
                                        <small class="pull-right text-navy">5h ago</small>
                                        <strong>Chris Johnatan Overtunk</strong> started following <strong>Monica Smith</strong>. <br>
                                        <small class="text-muted">Yesterday 1:21 pm - 11.06.2014</small>
                                    </div>
                                </div>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="dropdown-messages-box">
                                    <a href="profile.html" class="pull-left">
                                        <img alt="image" class="img-circle" src="img/profile.jpg">
                                    </a>
                                    <div>
                                        <small class="pull-right">23h ago</small>
                                        <strong>Monica Smith</strong> love <strong>Kim Smith</strong>. <br>
                                        <small class="text-muted">2 days ago at 2:30 am - 11.06.2014</small>
                                    </div>
                                </div>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                    <a href="mailbox.html">
                                        <i class="fa fa-envelope"></i> <strong>Read All Messages</strong>
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-bell"></i>  <span class="label label-primary">8</span>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a href="mailbox.html">
                                    <div>
                                        <i class="fa fa-envelope fa-fw"></i> You have 16 messages
                                        <span class="pull-right text-muted small">4 minutes ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="profile.html">
                                    <div>
                                        <i class="fa fa-twitter fa-fw"></i> 3 New Followers
                                        <span class="pull-right text-muted small">12 minutes ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="grid_options.html">
                                    <div>
                                        <i class="fa fa-upload fa-fw"></i> Server Rebooted
                                        <span class="pull-right text-muted small">4 minutes ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                    <a href="notifications.html">
                                        <strong>See All Alerts</strong>
                                        <i class="fa fa-angle-right"></i>
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </li>


                    <li>
                        <a href="login.html">
                            <i class="fa fa-sign-out"></i> Log out
                        </a>
                    </li>
                </ul>

            </nav>
        </div>


        <div class="wrapper wrapper-content">


            <div class="row">

                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Recent transformations</h5>
                            <div class="ibox-tools">
                                <span class="label label-primary">Updated 12.2015</span>
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                    <i class="fa fa-wrench"></i>
                                </a>
                                <ul class="dropdown-menu dropdown-user">
                                    <li><a href="#">Config option 1</a>
                                    </li>
                                    <li><a href="#">Config option 2</a>
                                    </li>
                                </ul>
                                <a class="close-link">
                                    <i class="fa fa-times"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <div class="row">
                                <div class="col-sm-9 m-b-xs">
                                    <div data-toggle="buttons" class="btn-group">
                                        <label class="btn btn-sm btn-white"> <input type="radio" id="option1" name="options"> Day </label>
                                        <label class="btn btn-sm btn-white active"> <input type="radio" id="option2" name="options"> Week </label>
                                        <label class="btn btn-sm btn-white"> <input type="radio" id="option3" name="options"> Month </label>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="input-group"><input type="text" placeholder="Search" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"> Go!</button> </span></div>
                                </div>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>

                                        <th>Qan #</th>
                                        <th>Word file </th>
                                        <th>Unit No</th>
                                        <th>Unit Title </th>
                                        <th>Author </th>
                                        <th>OpenXML </th>
                                        <th>IqsXML</th>
                                        <th>Date</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>1</td>
                                        <td>Project <small>This is example of project</small></td>
                                        <td>Patrick Smith</td>
                                        <td>0800 051213</td>
                                        <td>Inceptos Hymenaeos Ltd</td>
                                        <td><i class="fa fa-download text-navy"></i></td>
                                        <td><i class="fa fa-download text-navy"></i></td>
                                        <td>Jul 14, 2013</td>
                                        <td><a href="#"><i class="fa fa-check text-navy"></i></a></td>
                                    </tr>
                                    <tr>
                                        <td>2</td>
                                        <td>Alpha project</td>
                                        <td>Alice Jackson</td>
                                        <td>0500 780909</td>
                                        <td>Nec Euismod In Company</td>
                                        <td><i class="fa fa-download text-navy"></i></td>
                                        <td><i class="fa fa-download text-navy"></i></td>
                                        <td>Jul 16, 2013</td>
                                        <td><a href="#"><i class="fa fa-check text-navy"></i></a></td>
                                    </tr>
                                    <tr>
                                        <td>3</td>
                                        <td>Betha project</td>
                                        <td>John Smith</td>
                                        <td>0800 1111</td>
                                        <td>Erat Volutpat</td>
                                        <td><i class="fa fa-exclamation-circle text-danger"></i></td>
                                        <td><i class="fa fa-exclamation-circle text-danger"></i></td>
                                        <td>Jul 18, 2013</td>
                                        <td><a href="#"><i class="fa fa-exclamation-circle text-danger"></i></a></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                </div>

            </div>


            <div class="row">

                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Transformations History </h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                    <i class="fa fa-wrench"></i>
                                </a>
                                <ul class="dropdown-menu dropdown-user">
                                    <li><a href="#">Config option 1</a>
                                    </li>
                                    <li><a href="#">Config option 2</a>
                                    </li>
                                </ul>
                                <a class="close-link">
                                    <i class="fa fa-times"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content">
                            <div class="row">
                                <div class="col-sm-9 m-b-xs">
                                    <div data-toggle="buttons" class="btn-group">
                                        <label class="btn btn-sm btn-white"> <input type="radio" id="option1" name="options"> Day </label>
                                        <label class="btn btn-sm btn-white active"> <input type="radio" id="option2" name="options"> Week </label>
                                        <label class="btn btn-sm btn-white"> <input type="radio" id="option3" name="options"> Month </label>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="input-group"><input type="text" placeholder="Search" class="input-sm form-control"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary"> Go!</button> </span></div>
                                </div>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th>Qan #</th>
                                        <th>Word file </th>
                                        <th>Unit No</th>
                                        <th>Unit Title </th>
                                        <th>Author </th>
                                        <th>OpenXML </th>
                                        <th>IqsXML</th>
                                        <th>Date</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>1</td>
                                        <td>Project <small>This is example of project</small></td>
                                        <td>Patrick Smith</td>
                                        <td>0800 051213</td>
                                        <td>Inceptos Hymenaeos Ltd</td>
                                        <td><i class="fa fa-download text-navy"></i></td>
                                        <td><i class="fa fa-download text-navy"></i></td>
                                        <td>Jul 14, 2013</td>
                                        <td><a href="#"><i class="fa fa-check text-navy"></i></a></td>
                                    </tr>
                                    <tr>
                                        <td>2</td>
                                        <td>Alpha project</td>
                                        <td>Alice Jackson</td>
                                        <td>0500 780909</td>
                                        <td>Nec Euismod In Company</td>
                                        <td><i class="fa fa-download text-navy"></i></td>
                                        <td><i class="fa fa-download text-navy"></i></td>
                                        <td>Jul 16, 2013</td>
                                        <td><a href="#"><i class="fa fa-check text-navy"></i></a></td>
                                    </tr>
                                    <tr>
                                        <td>3</td>
                                        <td>Betha project</td>
                                        <td>John Smith</td>
                                        <td>0800 1111</td>
                                        <td>Erat Volutpat</td>
                                        <td><i class="fa fa-download text-navy"></i></td>
                                        <td><i class="fa fa-download text-navy"></i></td>
                                        <td>Jul 18, 2013</td>
                                        <td><a href="#"><i class="fa fa-check text-navy"></i></a></td>
                                    </tr>
                                    <tr>
                                        <td>4</td>
                                        <td>Gamma project</td>
                                        <td>Anna Jordan</td>
                                        <td>(016977) 0648</td>
                                        <td>Tellus Ltd</td>
                                        <td><i class="fa fa-exclamation-circle text-danger"></i></td>
                                        <td><i class="fa fa-exclamation-circle text-danger"></i></td>
                                        <td>Jul 22, 2013</td>
                                        <td><a href="#"><i class="fa fa-exclamation-circle text-danger"></i></a></td>
                                    </tr>
                                    <tr>
                                        <td>5</td>
                                        <td>Gamma project</td>
                                        <td>Anna Jordan</td>
                                        <td>(016977) 0648</td>
                                        <td>Tellus Ltd</td>
                                        <td><i class="fa fa-exclamation-circle text-danger"></i></td>
                                        <td><i class="fa fa-exclamation-circle text-danger"></i></td>
                                        <td>Jul 22, 2013</td>
                                        <td><a href="#"><i class="fa fa-exclamation-circle text-danger"></i></a></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>
                </div>

            </div>


        </div>


        <div class="footer">
            <div class="pull-right">
                10GB of <strong>250GB</strong> Free.
            </div>
            <div>
                <strong>Copyright</strong> Example Company &copy; 2014-2015
            </div>
        </div>

    </div>
</div>



<!-- Mainly scripts -->
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>--%>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<!-- Flot -->
<script src="static/js/plugins/flot/jquery.flot.js"></script>
<script src="static/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script src="static/js/plugins/flot/jquery.flot.spline.js"></script>
<script src="static/js/plugins/flot/jquery.flot.resize.js"></script>
<script src="static/js/plugins/flot/jquery.flot.pie.js"></script>
<script src="static/js/plugins/flot/jquery.flot.symbol.js"></script>
<script src="static/js/plugins/flot/curvedLines.js"></script>

<!-- Peity -->
<script src="static/js/plugins/peity/jquery.peity.min.js"></script>
<script src="static/js/demo/peity-demo.js"></script>

<!-- Custom and plugin javascript -->
<script src="static/js/inspinia.js"></script>
<script src="static/js/plugins/pace/pace.min.js"></script>

<!-- jQuery UI -->
<script src="static/js/plugins/jquery-ui/jquery-ui.min.js"></script>

<!-- Jvectormap -->
<script src="static/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="static/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>

<!-- Sparkline demo data  -->
<script src="static/js/demo/sparkline-demo.js"></script>

</body>
</html>