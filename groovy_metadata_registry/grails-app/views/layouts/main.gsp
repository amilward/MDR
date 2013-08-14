<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		<!-- <link href='http://fonts.googleapis.com/css?family=Michroma' rel='stylesheet' type='text/css'> -->
		<!-- <link href='http://fonts.googleapis.com/css?family=Orbitron' rel='stylesheet' type='text/css'> -->
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.dataTables.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
		<g:javascript>
		    window.appContext = '${request.contextPath}';
		</g:javascript>
		<g:javascript library="jquery" plugin="jquery" />
		<r:require module="jquery-ui"/>
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
	
	
	
	<div id="maincontainer">

		<!-- <div id="topsection"><div class="innertube"><h1>CSS Liquid Layout #2.1- (Fixed-Fluid)</h1></div></div>-->
		
		<div id="contentwrapper">
			<div id="contentcolumn">
			<sec:ifLoggedIn>
						
						<div class="search" id="searchbox">
						<g:form url='[controller: "searchable", action: "index"]' class="searchform cf" id="searchableForm" name="searchableForm" method="get">
					        <g:textField name="q" value="${params.q}" size="50"/> <input type="submit" value="" />
					    </g:form>
						</div>
			</sec:ifLoggedIn>
			<g:layoutBody/>
			</div>
		</div>
		
		<div id="leftcolumn">
			<div id="left_menu">
						<div class="left_menu" id="user_menu">
							<sec:ifLoggedIn>
								<table>
									<tr><td><img src='${fam.icon(name: 'user_suit')}'/></td><td><sec:loggedInUserInfo field="username" /></td></tr>
									<tr><td><img src='${fam.icon(name: 'cog')}'/></td><td><g:link controller="Logout">logout</g:link></td></tr>
								</table>

								        
								<!-- END #login -->
							</sec:ifLoggedIn>
							<sec:ifNotLoggedIn>
							<table>
							<tr>
								<td><img src='${fam.icon(name: 'lock_open')}'/></td>
								<td><g:link controller="Login">login </g:link></td>
							</tr>
							<tr>
								<td><img src='${fam.icon(name: 'user_suit')}'/></td>
								<td><g:link controller="Register">register </g:link></td>
							</tr>
							</table>
							</sec:ifNotLoggedIn>
						</div>
						
						
						
						<sec:ifLoggedIn>
						
						<div class="left_menu">
							<div id="cart" class="cart">
								<table>
									<tr><td><img src='${fam.icon(name: 'basket_put')}'/></td><td><g:link action="show" params="[id: 1]" controller="CollectionBasket">collection basket </g:link></td></tr>		
								</table>
									<div class="ui-widget-content">
										<ol id="collection_basket_list">
										</ol>
								</div>
							</div>	
						</div>
						
						<div class="left_menu" id="mdr_menu">
						
							<table>
								<tr><td><img src='${fam.icon(name: 'folder_database')}'/></td><td><g:link controller="Collection">collections </g:link></td></tr>
							
								<tr><td><img src='${fam.icon(name: 'brick')}'/></td><td><g:link controller="ConceptualDomain">conceptual domains</g:link></td></tr>
								
								<tr><td><img src='${fam.icon(name: 'bricks')}'/></td><td><g:link controller="ValueDomain">value domains </g:link></td></tr>
								
								<tr><td><img src='${fam.icon(name: 'table')}'/></td><td><g:link controller="DataElementConcept">data element sections / concepts</g:link></td></tr>
								
								<tr><td><img src='${fam.icon(name: 'table_row_insert')}'/></td><td><g:link controller="DataElement">data elements </g:link></td></tr>
								
								<tr><td><img src='${fam.icon(name: 'textfield_key')}'/></td><td><g:link controller="DataType">data types </g:link></td></tr>
								
								<tr><td><img src='${fam.icon(name: 'page_white_stack')}'/></td><td><g:link controller="Document">documents </g:link></td></tr>
								
								<tr><td><img src='${fam.icon(name: 'database_link')}'/></td><td><g:link controller="ExternalSynonym">external synonyms </g:link></td></tr>
							
							</table>
						</div>
						</sec:ifLoggedIn>
						
			</div>
		</div>
		
		<!--  <div id="footer"><a href="#">MDC</a></div>-->

	</div>
	
	
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
		<sec:ifLoggedIn><g:javascript library="application"/></sec:ifLoggedIn>
		<r:layoutResources />
	</body>
</html>
