<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Model Catalogue - Home</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap-responsive.min.css')}" type="text/css">

<!-- FIXME this custom CSS file should be abstracted away so it's shared for all "big, informationy pages" -->
<link rel="stylesheet" href="${resource(dir: 'css', file: 'index_custom.css')}" type="text/css">



<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

<!-- Fav and touch icons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="../assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="../assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="../assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="../assets/ico/apple-touch-icon-57-precomposed.png">
<link rel="shortcut icon" href="../assets/ico/favicon.png">
</head>

<body>

	<div class="container">

		<div class="masthead">
			<h3 class="muted">Oxford University Clinical Informatics</h3>
			<div class="navbar">
				<div class="navbar-inner">
					<div class="container">
						<ul class="nav">
							<li class="active"><a href="#">Home</a></li>
							<li><a href="#">Introduction</a></li>
							<li><a href="#">Documentation</a></li>
							<li><a href="#">Download</a></li>
							<li><a href="#">About</a></li>
							<li><a href="#">Contact</a></li>
						</ul>
					</div>
				</div>
			</div>
			<!-- /.navbar -->
		</div>

		<!-- Jumbotron -->
		<div class="jumbotron">
			<h1>Model Catalogue</h1>
			<p class="lead">
				<b><em>Model</em></b> existing business processes and context. <b><em>Design</em></b>
				new pathways, forms, data storage, studies. <b><em>Generate</em></b> better
				software components
			</p>

			<g:link controller="login" action="auth" class="btn btn-large btn-info">Login</g:link>
			<span class="lead">&nbsp;&nbsp;or&nbsp;&nbsp;</span>
			<g:link controller="register" action="index" class="btn btn-large btn-info">Sign Up</g:link>

				
		</div>

		<hr>

		<!-- Example row of columns -->
		<div class="row-fluid">
			<div class="span4">
				<h2>Forms</h2>
				<p>Build forms from standard data elements in our friendly
					drag-n-drop interface. Export your forms to your favourite tool.</p>
				<p>
					<a class="btn" href="#">More info&hellip;</a>
				</p>
			</div>
			<div class="span4">
				<h2>Pathways</h2>
				<p>Design your workflows and visualise your patient pathways.
					Annotate nodes with data elements, forms, and decisions.
					Automatically build databases, dashboard interfaces and reporting
					data.</p>
				<p>
					<a class="btn" href="#">More info&hellip;</a>
				</p>
			</div>
			<div class="span4">
				<h2>Architecture</h2>
				<p>Track your data elements from collection - model services,
					databases and warehouses. Generate your own feeds, and generate
					components for integration engines.</p>
				<p>
					<a class="btn" href="#">More info&hellip;</a>
				</p>
			</div>
		</div>

		<hr>

		<div class="footer">
			<p>&copy; Oxford University Clinical Informatics. This software
				is open source - check us out at GitHub!</p>
		</div>

	</div>
	<!-- /container --> 

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="./js/vendor/jquery/jquery-2.0.3.js"></script>
	<script src="./js/vendor/bootstrap/bootstrap.js"></script>
	<script src="./js/application.js"></script>

</body>
</html>