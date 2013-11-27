<!-- --------------------------------------------------------------- -->
<!-- ----- Build using the opensource METIS - twitter bootstrap dashboard----------- -->
<!-- --------------------------------------------------------------- -->
<%@ page import="org.codehaus.groovy.grails.plugins.PluginManagerHolder"%>
<%@ page
  import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils"%>
<%@ page import="grails.plugins.springsecurity.SecurityConfigType"%>

<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
  href="${resource(dir: 'css', file: 'bootstrap.min.css')}"
  type="text/css">
<link rel="stylesheet"
  href="${resource(dir: 'css', file: 'bootstrap-responsive.min.css')}"
  type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'style.css')}"
  type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'theme.css')}"
  type="text/css">
<link rel="stylesheet"
  href="${resource(dir: 'css', file: 'datatable/jquery.dataTables.css')}"
  type="text/css">
  
<g:javascript library="jquery_lib" />
<g:javascript library="jquery" plugin="jquery"/><!--  FIXME, there's duplication here -->
<g:javascript library="modernizr_lib" />
<g:javascript>
        window.appContext = '${request.contextPath}';
        var root = location.protocol + '//' + location.host + window.appContext;
    </g:javascript>
    


<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
  <meta name='layout' content='register'/>
  <title><g:message code='spring.security.ui.register.title'/></title>

<g:layoutHead />
<r:layoutResources />
  

</head>

  <div id="wrap">


    <!-- BEGIN TOP BAR -->
    <div id="top">
      <!-- .navbar -->
      <div class="navbar navbar-inverse navbar-static-top">
        <div class="navbar-inner">
          <div class="container-fluid">
          
          </div>
          </div>
          </div>
          </div>


    <!-- BEGIN MAIN CONTENT -->
  <div id="content" class="registrationbox">

    <!-- .outer -->
    <div class="container-fluid outer">
      <div class="row-fluid">
        <!-- .inner -->
        <div class="span12 inner">

			<g:layoutBody />

			<s2ui:showFlash />

          <!-- /.row-fluid -->
          <!--END LATEST COMMENT-->
        </div>
        <!-- /.inner -->
      </div>
      <!-- /.row-fluid -->
    </div>
    <!-- /.outer -->
  </div>
  <!-- END CONTENT -->
</div>



<g:render template="/shared/footer" />

