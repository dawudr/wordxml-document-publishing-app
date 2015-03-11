<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s"   uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML ng-app="inspinia">

    <!-- Force latest IE rendering engine or ChromeFrame if installed -->
    <!--[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <![endif]-->
    <meta charset="utf-8">
    <title>jQuery File Upload Demo</title>
    <meta name="description" content="File Upload widget with multiple file selection, drag&amp;drop support, progress bars, validation and preview images, audio and video for jQuery. Supports cross-domain, chunked and resumable file uploads and client-side image resizing. Works with any server-side platform (PHP, Python, Ruby on Rails, Java, Node.js, Go etc.) that supports standard HTML form file uploads.">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap styles -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <!-- Generic page styles -->
    <link rel="stylesheet" href="css/style.css">
    <!-- blueimp Gallery styles -->
    <link rel="stylesheet" href="http://blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
    <!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
    <link rel="stylesheet" href="css/plugins/jquery-file-upload/jquery.fileupload.css">
    <link rel="stylesheet" href="css/plugins/jquery-file-upload/jquery.fileupload-ui.css">
    <!-- CSS adjustments for browsers with JavaScript disabled -->
    <noscript><link rel="stylesheet" href="css/plugins/jquery-file-upload/jquery.fileupload-noscript.css"></noscript>
    <noscript><link rel="stylesheet" href="css/plugins/jquery-file-upload/jquery.fileupload-ui-noscript.css"></noscript>


<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-9">
        <h2>Transform</h2>
        <ol class="breadcrumb">
            <li>
                <a href="index.html">Home</a>
            </li>
            <li>
                Document
            </li>
            <li class="active">
                <strong>Transform</strong>
            </li>
        </ol>
    </div>
</div>




<div class="wrapper wrapper-content">



    <div class="row">
        <div class="col-lg-12">
            <div class="jumbotron">
                <div class="container">
                    <h2><strong>Perform a document transformation</strong></h2>
                    <p>This wizard will guide you through the steps to upload, extract and transform content from Microsoft Word BTEC Specification documents into XML.</p>
                </div>
            </div>
        </div>
    </div>





    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Step 1 - Dropzone Area</h5>

                    <div ibox-tools="" class="ng-scope"><div class="ibox-tools dropdown" dropdown="">
                    </div></div>
                </div>
                <div class="ibox-content">
                    <h2>
                        Upload Word document(s)
                    </h2>

                    <p>
                        Upload your BTEC Specification Word document(s) by using the 'Add files.." button OR by dropping a group of files into this browser window.
                    </p>







    <!-- The file upload form used as target for the file upload widget -->
    <form id="fileupload" action='<s:url value="/processtransform"/>' method="POST" enctype="multipart/form-data">
        <!-- Redirect browsers with JavaScript disabled to the origin page -->
        <noscript><input type="hidden" name="redirect" value="http://blueimp.github.io/jQuery-File-Upload/"></noscript>
        <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
        <div class="row fileupload-buttonbar">
            <div class="col-lg-7">
                <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span>Add files...</span>
                    <input type="file" name="files[]" multiple>
                </span>
                <button type="submit" class="btn btn-primary start">
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start upload & transform</span>
                </button>
                <button type="reset" class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel upload</span>
                </button>
                <button type="button" class="btn btn-danger delete">
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" class="toggle">
                <!-- The global file processing state -->
                <span class="fileupload-process"></span>
            </div>
            <!-- The global progress state -->
            <div class="col-lg-5 fileupload-progress fade">
                <!-- The global progress bar -->
                <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                    <div class="progress-bar progress-bar-success" style="width:0%;"></div>
                </div>
                <!-- The extended global progress state -->
                <div class="progress-extended">&nbsp;</div>
            </div>
        </div>
        <!-- The table listing the files available for upload/download -->
        <table role="presentation" class="table table-striped"><tbody class="files"></tbody></table>
    </form>









                </div>
            </div>
        </div>
    </div>




    <div class="row">


        <div class="col-lg-12">

            <div class="ibox">
                <div class="ibox-title">
                    <h5>Step 2 - Transformation status</h5>

                    <div class="ibox-tools">
                        <button type="button" id="loading-example-btn" class="btn btn-white btn-sm" ng-click="$route.reload"><i class="fa fa-refresh"></i> Refresh</button>
                    </div>
                </div>
                <div class="ibox-content">
                    <h2>
                        Verify transformed document(s)
                    </h2>

                    <p>
                        Listed are results of your document transformation(s).
                        Correct error in the Word document and repeat tranform process if error messages are displayed in the messages window.
                        <br>
                    </p>




                    <div class="project-list">

                        <table class="table table-hover">
                            <tbody>
                            <tr ng-repeat="transformation in recentTransformations">
                                <td class="project-status">
                                    <span class="label label-warning">In progress</span>
                                </td>
                                <td class="project-title">
                                    Qan: <a ui-sref="project_detail">{{transformation.qanNo}}</a>
                                    <br/>
                                    Unit No: <a ui-sref="project_detail">{{transformation.unitNo}}</a>
                                    <br/>
                                    Title: <a ui-sref="project_detail">{{transformation.unitTitle}}</a>
                                    <br/>
                                    File: <a ui-sref="project_detail">{{transformation.wordfilename}}</a>
                                    <br/>
                                    <small>Created {{transformation.date}}</small>
                                </td>
                                <td class="project-completion">
                                    <i alt="{{transformation.transformStatus}}"
                                       class="fa fa-2x" ng-class="{
                   'TRANSFORM_STATUS_FILE_UPLOAD_IN_PROGRESS' : 'fa-cloud-upload text-info',
                   'TRANSFORM_STATUS_EXTRACTION_IN_PROGRESS' : 'fa-refresh text-info',
                   'TRANSFORM_STATUS_TRANSFORM_IN_PROGRESS' : 'fa-cogs text-info',
                   'TRANSFORM_STATUS_SUCCESS' : 'fa-check text-navy',
                   'TRANSFORM_STATUS_FAIL' : 'fa-times text-danger',
                   'TRANSFORM_STATUS_FAIL_VALIDATE_WORD' : 'fa-word-file-o text-warn',
                   'TRANSFORM_STATUS_FAIL_EXTRACT_WORD_TO_XML' : 'fa-word-file-o text-danger',
                   'TRANSFORM_STATUS_FAIL_TRANSFORM' : 'fa-cogs text-danger',
                   'TRANSFORM_STATUS_FAIL_TRANSFORM_XML_TO_IQSXML_XSLT' : 'fa-file-text-o text-danger',
                   'TRANSFORM_STATUS_FAIL_XML_TO_IQSXML_XQUERY' : 'fa-file-text text-danger',
                   'TRANSFORM_STATUS_FAIL_IQS_VALIDATION' : 'fa-bell text-danger',
                   'TRANSFORM_STATUS_FAIL_FILE_WRITE' : 'fa-pencil-square-o text-danger',
                   'TRANSFORM_STATUS_SUCCESS2' : 'fa-star text-navy'
                   }[transformation.transformStatus]">
                                    </i>

                                    <small>{{transformation.transformStatus | transformStatus}}</small>
                                    <div class="progress progress-mini">
                                        <div style="width: 100%;" class="progress-bar"></div>
                                    </div>
                                </td>
                                <td class="project-people">
                                    <a href="{{transformation.wordfilename}}"><i class="fa fa-file-word-o"></i>{{transformation.wordfilename}}</a>
                                </td>
                                <td class="project-actions">
                                    <a href="#/downloadxmlhandler/{{transformation.id}}" class="btn btn-white btn-sm" alt="Open XML file"><i class="fa fa-file-code-o"></i> Open XML</a>
                                    <a href="#/downloadpqshandler/{{transformation.id}}" class="btn btn-white btn-sm" alt="IQS XML file"><i class="fa fa fa-file-text-o"></i> Open PQS</a><br/>
                                    <a href="#/document/{{transformation.id}}" class="btn btn-white btn-sm"><i class="fa fa-pencil"></i> Edit </a>
                                    <a href="#/transformation/remove/{{transformation.id}}" class="btn btn-white btn-sm"><i class="fa fa-trash"></i> Delete </a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>





    </div>


</div>










<!-- The blueimp Gallery widget -->
<div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-filter=":even">
    <div class="slides"></div>
    <h3 class="title"></h3>
    <a class="prev">‹</a>
    <a class="next">›</a>
    <a class="close">×</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>
<!-- The template to display files available for upload -->
<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="btn btn-primary start">
                    <i class="glyphicon glyphicon-upload"></i>
                    <span>Start</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<!-- The template to display files available for download -->
<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="btn btn-danger delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <i class="glyphicon glyphicon-trash"></i>
                    <span>Delete</span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="btn btn-warning cancel">
                    <i class="glyphicon glyphicon-ban-circle"></i>
                    <span>Cancel</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="js/plugins/jquery-file-upload/vendor/jquery.ui.widget.js"></script>
<!-- The Templates plugin is included to render the upload/download listings -->
<script src="js/plugins/jquery-file-upload/tmpl.min.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="js/plugins/jquery-file-upload/load-image.all.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="js/plugins/jquery-file-upload/canvas-to-blob.min.js"></script>
<!-- Bootstrap JS is not required, but included for the responsive demo navigation -->
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<!-- blueimp Gallery script -->
<script src="/js/plugins/jquery-file-upload/jquery.blueimp-gallery.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="/js/plugins/jquery-file-upload/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script src="/js/plugins/jquery-file-upload/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="/js/plugins/jquery-file-upload/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script src="/js/plugins/jquery-file-upload/jquery.fileupload-image.js"></script>
<!-- The File Upload audio preview plugin -->
<script src="/js/plugins/jquery-file-upload/jquery.fileupload-audio.js"></script>
<!-- The File Upload video preview plugin -->
<script src="/js/plugins/jquery-file-upload/jquery.fileupload-video.js"></script>
<!-- The File Upload validation plugin -->
<script src="/js/plugins/jquery-file-upload/jquery.fileupload-validate.js"></script>
<!-- The File Upload user interface plugin -->
<script src="/js/plugins/jquery-file-upload/jquery.fileupload-ui.js"></script>
<!-- The main application script -->
<script src="/js/plugins/jquery-file-upload/main.js"></script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="/js/plugins/jquery-file-upload/cors/jquery.xdr-transport.js"></script>
<![endif]-->
