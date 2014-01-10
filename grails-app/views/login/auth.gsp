<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="springSecurity.login.title" /></title>
</head>

<body>
	<div class="container">
		<div class="text-center">
			<p class="muted text-center">MetaData OpenSourced</p>
		</div>
		<div class="tab-content">
			<div id="login" class="tab-pane active">

				<g:if test='${flash.message}'>
					<div class='login_message'>
						<p class="muted text-center">
							${flash.message}
						</p>
					</div>
				</g:if>

				<form action='${postUrl}' method='POST' id='loginForm'
					class="form-signin" autocomplete='off'>
					<p class="muted text-center">Please Login</p>

					<input type='text' placeholder="Username"
						class='text_ input-block-level' name='j_username' id='username' />

					<input type='password' placeholder="Password"
						class='text_ input-block-level' name='j_password' id='password' />


					<p id="remember_me_holder">
						<input type='checkbox' class='chk' name='${rememberMeParameter}'
							id='remember_me'
							<g:if test='${hasCookie}'>checked='checked'</g:if> /> <label
							for='remember_me'><g:message
								code="springSecurity.login.remember.me.label"
								class="muted pull-right" /></label>
					</p>

					<p>
						<input type='submit' class="btn btn-large btn-primary btn-block"
							id="submit"
							value='${message(code: "springSecurity.login.button")}' />
					</p>
				</form>
			</div>
			<div id="forgot" class="tab-pane">
				<form action="index.html" class="form-signin">
					<p class="muted text-center">Enter your valid e-mail</p>
					<input type="email" placeholder="mail@domain.com"
						required="required" class="input-block-level"> <br> <br>
					<button class="btn btn-large btn-danger btn-block" type="submit">Recover
						Password</button>
				</form>
			</div>
			<div id="signup" class="tab-pane">
				<form action="index.html" class="form-signin">
					<input type="text" placeholder="username" class="input-block-level">
					<input type="email" placeholder="mail@domain.com"
						class="input-block-level"> <input type="password"
						placeholder="password" class="input-block-level">
					<button class="btn btn-large btn-success btn-block" type="submit">Register</button>

				</form>
			</div>
		</div>
		<div class="text-center">
			<ul class="inline">
				<li><a id="forgottenPasswordLink" class="muted"
					href="../register/forgotPassword" data-toggle="tab">Forgot Password</a></li>
				<li><a id="registerLink" class="muted" href="../register/"
					data-toggle="tab">Signup</a></li>
			</ul>
		</div>


	</div>
	<!-- /container -->
	<script type='text/javascript'>
	<!--
		(function() {
			document.forms['loginForm'].elements['j_username'].focus();
		})();
	// -->
	</script>
</body>
</html>
