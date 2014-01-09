<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="layout" content="main"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Model Catalogue - Home</title>


<!-- FIXME Styles in HTML is bad, bad, bad! Extract to generic CSS file.... -->
<style type="text/css">
html, body{
	font-family: helvetica,sans-serif;
	background-color: black !important;
	
}

#dashboard{
	width: 740px;
	height: 540px;
	color: #555555;
	margin-left: auto;
	margin-right: auto;
	background-color: white;
	text-align: left;
	position: relative;
	margin-top: 20px;
}

#dashboard div.dashboard-page{
	position: absolute;
	top: 0;
	left: 0;
	height: 460px;
	padding: 40px;
}

#dashboard-options{
	text-align: center;
	padding-left: 40px !important;
	padding-right: 25px !important;
	padding-top: 10px !important;
	padding-bottom: 10px !important; 
}


.dashboard_option{
	width: 200px;
	height: 200px;
	text-align: center;
	float: left;
	margin-bottom: 10px;
	cursor: pointer;
	margin-right:15px;
	font-weight: bold;
}

.dashboard_option span{
	text-align: center;
	width: 100%;
	white-space: nowrap;
}

.dashboard_option .imgbox{
	margin-bottom: 0.8em;
	margin-top: 0.4em;
	height: 160px;
	width: 160px;
	position: relative;
	margin-right: auto;
	margin-left: auto;
}

.dashboard_option .imgbox img{
	position: absolute;
	top: 0;
	left: 0;
}


.dashboard_option_highlighted{
	border: 3px solid #555555;
	color: #555555;
}
.dashboard_option_not_highlighted{
	border: 3px solid #3399FF;
	color: #3399FF;
}

.dashboard-wrapper{
	height: 460px; 
	width: 660px; 
	position: relative;
} 

ul{
	list-style-type: none;	
}

ul, li{
	margin-left: 0 !important;
	padding-left: 0 !important;
}
td{
	vertical-align: top !important;
}
.dashboard-return{
	position: absolute;
	bottom: 0;
	right: 0;
}

</style>

</head>
<body>
  	<div id="dashboard">
	  	<div class="dashboard-page" id="dashboard-options">
		    <h3>Welcome to the Model Catalogue!</h3>
		    <h4>Where would you like to start?</h4>
			<div class="options-box">
				<div class="dashboard_option dashboard_option_not_highlighted" id="pathways">
					<div class="imgbox">
						<img src="images/dashboard/Pathways_gray.png" class="gray"/>
						<img src="images/dashboard/Pathways_colour.png" class="colour" style="display: none;"/>
					</div>
					<span>Pathway Models</span>
				</div>
			</div>
			<div class="dashboard_option dashboard_option_not_highlighted" id="forms">
				<div class="imgbox">
					<img src="images/dashboard/Forms_gray.png" class="gray"/>
					<img src="images/dashboard/Forms_colour.png" class="colour" style="display: none;"/>
				</div>
				<span>Form Models</span>
			</div>
			<div class="dashboard_option dashboard_option_not_highlighted" id="deployments">
				<div class="imgbox">
					<img src="images/dashboard/Deployment_gray.png" class="gray"/>
					<img src="images/dashboard/Deployment_colour.png" class="colour" style="display: none;"/>
				</div>
				<span>Deployment Models</span>
			</div>
			<div class="dashboard_option dashboard_option_not_highlighted" id="projects">
				<div class="imgbox">
					<img src="images/dashboard/Projects_gray.png" class="gray"/>
					<img src="images/dashboard/Projects_colour.png" class="colour" style="display: none;"/>
				</div>
				<span>Projects</span>
			</div>
			<div class="dashboard_option dashboard_option_not_highlighted" id="metadata">
				<div class="imgbox">
					<img src="images/dashboard/Advanced_gray.png" class="gray"/>
					<img src="images/dashboard/Advanced_colour.png" class="colour" style="display: none;"/>
				</div>
				<span>Metadata Curation</span>
			</div>
			<div class="dashboard_option dashboard_option_not_highlighted" id="profile">
				<div class="imgbox">
					<img src="images/dashboard/Profile_gray.png" class="gray"/>
					<img src="images/dashboard/Profile_colour.png" class="colour" style="display: none;"/>
				</div>
				<span>My Profile</span>
			</div>
		</div>
	  	<div class="dashboard-page" id="dashboard-pathways" style="display: none;">
	  		<div class="dashboard-wrapper">
		  		<h2>Pathway Models</h2>
		  		<p>You currently have ${draftPathways.size()} pathway(s) in a 'draft' state, and you have
					previously finalised ${finalizedPathways.size()} pathway model(s). Click a model below to edit or
					view, or start creating a new pathway model by clicking the button below...
				</p>

				<table class="table table-bordered" style="width: 100%">
					<thead>
		  				<tr>
		  					<th style="width: 50%;">'Draft' Pathway Models</th>
		  					<th style="width: 50%;">Finalised Pathway Models</th>
		  				</tr>
		  			</thead>
		  			<tbody>
		  				<tr>
		  					<td>
			  					<ul>
			  						<g:each var="pathway" in="${draftPathways}">
    									<li><a href="pathwaysModel/show/${pathway.id}">${pathway.name}</a></li></p>
									</g:each>
			  					</ul>
			  				</td>
		  					<td>
			  					<ul>
			  						<g:each var="pathway" in="${finalizedPathways}">
    									<li><a href="pathwaysModel/show/${pathway.id}">${pathway.name}</a></li></p>
									</g:each>
			  					</ul>
			  				</td>
		  				</tr>
		  			</tbody>
		  		</table>
	  			<button id="dashCreatePathway" class="btn btn-default"><i style="color: green;" class="icon-plus"></i>&nbsp;Create a new Pathway Model</button>

		  		<button class="dashboard-return btn btn-default"><i class="icon-arrow-left"></i>&nbsp;Return to the Dashboard</button>
	  		</div>
	  	</div>
	  	<div class="dashboard-page" id="dashboard-forms" style="display: none;">
	  		<div class="dashboard-wrapper">
	  	 		<h2>Forms</h2>
		  		<p>You currently have ${draftForms.size()} form(s) in a 'draft' state, and you have
					previously finalised ${finalizedForms.size()} form model(s). Click a model below to edit or
					view, or start creating a new form model by clicking the button below...
				</p>

				<table class="table table-bordered" style="width: 100%">
					<thead>
		  				<tr>
		  					<th style="width: 50%;">'Draft' Form Models</th>
		  					<th style="width: 50%;">Finalised Form Models</th>
		  				</tr>
		  			</thead>
		  			<tbody>
		  				<tr>
		  					<td>
			  					<ul>
			  						<g:each var="form" in="${draftForms}">
    									<li><a href="formDesign/show/${form.id}">${form.name}</a></li></p>
									</g:each>
			  					</ul>
			  				</td>
		  					<td>
			  					<ul>
			  						<g:each var="form" in="${finalizedForms}">
    									<li><a href="formDesign/show/${form.id}">${form.name}</a></li></p>
									</g:each>
			  					</ul>
			  				</td>
		  				</tr>
		  			</tbody>
		  		</table>
	  			<button id="dashCreateForm" class="btn btn-default"><i style="color: green;" class="icon-plus"></i>&nbsp;Create a new Form Model</button>
		  		<button class="dashboard-return btn btn-default"><i class="icon-arrow-left"></i>&nbsp;Return to the Dashboard</button>
	  		</div>
	  	</div>
	  	<div class="dashboard-page" id="dashboard-deployments" style="display: none;">
	  		<div class="dashboard-wrapper">
		  		<h2>Deployment Models</h2>
		  		<br/><br/>
		  		<h3>Under construction!</h3>
		  		<p>This feature is not yet finished! In time, users will be able
					to model databases, schemas and tables, services and data-feeds,
					and physical locations. Please try again soon!</p>

		  		<button class="dashboard-return btn btn-default"><i class="icon-arrow-left"></i>&nbsp;Return to the Dashboard</button>
	  		</div>
	  	</div>
	  	<div class="dashboard-page" id="dashboard-projects" style="display: none;">
	  		<div class="dashboard-wrapper">
		  		<h2>Projects</h2>
		  		<br/><br/>
		  		<h3>Under construction!</h3>
		  		<p>This feature is not yet finished! In time, this will be the
					place to come to organise collaborations, and keep track of
					user-created and automatically-generated artefacts. Please try
					again soon!</p>
		  		<button class="dashboard-return btn btn-default"><i class="icon-arrow-left"></i>&nbsp;Return to the Dashboard</button>
	  		</div>
	  	</div>
	  	<div class="dashboard-page" id="dashboard-metadata" style="display: none;">
	  		<div class="dashboard-wrapper">
		  		<h2>Metadata Curation</h2>
		  		<button class="dashboard-return btn btn-default"><i class="icon-arrow-left"></i>&nbsp;Return to the Dashboard</button>
	  		</div>
	  	</div>
	  	<div class="dashboard-page" id="dashboard-profile" style="display: none;">
	  		<div class="dashboard-wrapper">
		  		<h2>Profile</h2>
		  		<br/><br/>
		  		<h3>Under construction!</h3>
				<p>This feature is not yet finished! This will be the place
					where you change your user profile. Please try again soon!</p>
				<button class="dashboard-return btn btn-default"><i class="icon-arrow-left"></i>&nbsp;Return to the Dashboard</button>
	  		</div>
	  	</div>
	</div><!-- End div dashboard -->
    <g:javascript disposition="defer" library="dashboard" />
</body>

</html>