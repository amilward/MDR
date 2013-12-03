<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Model Catalogue &middot; Home</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="./css/bootstrap.min.css" rel="stylesheet">
<link href="./css/custom.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 20px;
	padding-bottom: 60px;
}

/* Custom container */
.container {
	margin: 0 auto;
	max-width: 1000px;
}

.container>hr {
	margin: 60px 0;
}

/* Main marketing message and sign up button */
.jumbotron {
	margin: 80px 0;
	text-align: center;
}

.jumbotron h1 {
	font-size: 100px;
	line-height: 1;
}

.jumbotron .lead {
	font-size: 24px;
	line-height: 1.25;
}

.jumbotron .btn {
	font-size: 21px;
	padding: 14px 24px;
}

/* Supporting marketing content */
.marketing {
	margin: 60px 0;
}

.marketing p+h4 {
	margin-top: 28px;
}

/* Customize the navbar links to be fill the entire space of the .navbar */
.navbar .navbar-inner {
	padding: 0;
}

.navbar .nav {
	margin: 0;
	display: table;
	width: 100%;
}

.navbar .nav li {
	display: table-cell;
	width: 1%;
	float: none;
}

.navbar .nav li a {
	font-weight: bold;
	text-align: center;
	border-left: 1px solid rgba(255, 255, 255, .75);
	border-right: 1px solid rgba(0, 0, 0, .1);
}

.navbar .nav li:first-child a {
	border-left: 0;
	border-radius: 3px 0 0 3px;
}

.navbar .nav li:last-child a {
	border-right: 0;
	border-radius: 0 3px 3px 0;
}
</style>
<link href="./css/bootstrap-responsive.min.css" rel="stylesheet">

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
			<a class="btn btn-large btn-success" data-toggle="modal"
				href="#login-modal">Login</a><span class="lead">&nbsp;&nbsp;or&nbsp;&nbsp;</span>
			<a class="btn btn-large btn-info" data-toggle="modal"
				href="#signup-modal">Sign Up</a>
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

	<!--  Login Modal -->
	<div class="hide fade modal" id="login-modal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>Login</h3>
		</div>

		<!-- The async form to send and replace the modals content with its response -->
		<form class="form-horizontal well"
				action='/groovy_metadata_registry/j_spring_security_check'
				method='POST' id='loginForm'>
		<div class="modal-body">
				<div class="alert alert-error" id="login-error">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					Invalid combination of username and password
				</div>
				<fieldset>
					<div class="form-label"><label for="username">Username</label></div>
					<div class="form-input">					
						<input type='text' placeholder="Username"
							class='text_ input-block-level' name='j_username' id='username' />
					</div>
					<div class="form-label"><label for="password">Password</label></div>
					<div class="form-input">
						<input type='password' placeholder="Password"
							class='text_ input-block-level' name='j_password' id='password' />
					</div>
					<div class="clearb">&nbsp;</div>
					<div class="form-label"><label
							for='remember_me'>Remember me</label></div>
					<div class="form-input">					
						<input type='checkbox' class='chk'
							name='_spring_security_remember_me' id='remember_me' />
					</div>

				</fieldset>
		</div>

		<div class="modal-footer">
			<input type='submit' class="btn btn-primary"
				id="submit" value='Login' />
			<a href="#" class="btn" data-dismiss="modal">Cancel</a>
		</div>
		</form>
	</div>

	<!--  Sign Up Modal -->
	<div class="hide fade modal" id="signup-modal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>Sign Up</h3>
		</div>

		<div class="modal-body">
			<!-- The async form to send and replace the modals content with its response -->
			<form class="form-horizontal well"
				action='/groovy_metadata_registry/j_spring_security_check'
				method='POST' id='loginForm'>
				<fieldset>
						
							<s2ui:textFieldRow name='username'
								labelCode='user.username.label' bean="${command}" size='40'
								labelCodeDefault='Username' value="" />

							<s2ui:textFieldRow name='email' bean="${command}"
								value="" size='40' labelCode='user.email.label'
								labelCodeDefault='E-mail' />

							<s2ui:passwordFieldRow name='password'
								labelCode='user.password.label' bean="${command}" size='40'
								labelCodeDefault='Password' value="" />

							<s2ui:passwordFieldRow name='password2'
								labelCode='user.password2.label' bean="${command}" size='40'
								labelCodeDefault='Password (again)' value="" />

						</tbody>
					</table>
					<p>
						<input type='submit' class="btn btn-large btn-primary btn-block"
							id="submit" value='Create your account' />
					</p>
				</fieldset>
			</form>
		</div>

		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">Cancel</a>
		</div>
	</div>

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="./js/vendor/jquery/jquery-2.0.3.js"></script>
	<script src="./js/vendor/bootstrap/bootstrap.js"></script>
	<script src="./js/application.js"></script>




</body>
</html>


