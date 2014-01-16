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

                <h2 class="text-center">Please Login</h2>

				<form action='${postUrl}' method='POST' id='loginForm' class="form-signin" autocomplete='off'>

					<input type='text' placeholder="Username"
						class='text_ input-block-level input-full' name='j_username' id='username' />

					<input type='password' placeholder="Password"
						class='text_ input-block-level input-full' name='j_password' id='password' />

                    <p>
                        <label id="remember_me_holder"
                               for='remember_me'>
                            <span class="checkboxtext"> <g:message
                                code="springSecurity.login.remember.me.label"
                                class="muted pull-right" />
                            </span>

                            <input type='checkbox' class='chk' name='${rememberMeParameter}'
                                id='remember_me'
                                <g:if test='${hasCookie}'>checked='checked'</g:if> />
                        </label>
                    </p>
					<p>
						<input type='submit' class="btn btn-large btn-primary btn-block"
							id="submit"
							value='${message(code: "springSecurity.login.button")}' />
					</p>
                    <a id="forgottenPasswordLink" class="btn btn-large btn-info btn-half-left" href="../register/forgotPassword">Forgot Password</a>
                    <a id="registerLink" class="btn btn-large btn-info btn-half-right" href="../register/">Signup</a>
                    <div class="clearfix"></div>
				</form>
			</div>
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
