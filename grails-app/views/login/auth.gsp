<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="springSecurity.login.title" /></title>
</head>

<body>
	<div class="container">
		<div class="tab-content">
			<div id="login" class="tab-pane active">

				<g:if test='${flash.message}'>
					<div class='login_message'>
						<p class="muted text-center">
							${flash.message}
						</p>
					</div>
				</g:if>

                <p class="text-center">Please Login</p>

				<form action='${postUrl}' method='POST' id='loginForm' class="form-signin" autocomplete='off'>

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
		</div>
		<div class="text-center">
				<a id="forgottenPasswordLink" class="muted" href="../register/forgotPassword">Forgot Password</a>
        </div>
        <div class="text-center">
				<a id="registerLink" class="muted" href="../register/">Signup</a>
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
