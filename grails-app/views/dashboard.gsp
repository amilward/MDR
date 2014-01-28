<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="layout" content="main"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Model Catalogue - Home</title>


    <!-- FIXME Styles in HTML is bad, bad, bad! Extract to generic CSS file.... -->
    <style type="text/css">


    .dashboard_option {
        width: 200px;
        height: 200px;
        text-align: center;
        float: left;
        margin-bottom: 10px;
        cursor: pointer;
        margin-right: 15px;
        font-weight: bold;

        opacity: 0.4;
        filter: alpha(opacity=40); /* msie */

        transition: opacity 0.5s;
        -webkit-transition: opacity 0.5s; /* Safari */

    }

    .dashboard_option:hover {
        opacity: 1;
        filter: alpha(opacity=100); /* msie */

    }

    .dashboard_option span {
        text-align: center;
        width: 100%;
        white-space: nowrap;
    }
    </style>

</head>

<body>
<div id="dashboard" min-height="300px">
    <div class="dashboard-page" id="dashboard-options">
        <div class="options-box">
            <div class="dashboard_option" id="pathways">
                <img src="images/dashboard/Pathways_colour.png"/>
                <span>Pathway Models</span>
            </div>
        </div>

        <div class="dashboard_option" id="forms">
            <img src="images/dashboard/Forms_colour.png"/>
            <span>Form Models</span>
        </div>
        <!--
			<div class="dashboard_option" id="deployments">
				<img src="images/dashboard/Deployment_colour.png"/>
				<span>Deployment Models</span>
			</div>
			<div class="dashboard_option" id="projects">
				<img src="images/dashboard/Projects_colour.png"/>
				<span>Projects</span>
			</div>
			-->
        <div class="dashboard_option" id="metadata">
            <img src="images/dashboard/Advanced_colour.png"/>
            <span>Metadata Curation</span>
        </div>
        <!--
			<div class="dashboard_option" id="profile">
				<img src="images/dashboard/Profile_colour.png"/>
				<span>My Profile</span>
			</div>
			-->
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
            <button id="dashCreatePathway" class="btn btn-default" data-toggle="modal"
                    data-target="#createPathwayModal"><i style="color: green;"
                                                         class="icon-plus"></i>&nbsp;Create a new Pathway Model</button>

            <button class="dashboard-return btn btn-default"><i
                    class="icon-arrow-left"></i>&nbsp;Return to the Dashboard</button>
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
            <button id="dashCreateForm" class="btn btn-default" data-toggle="modal" data-target="createFormModal"><i
                    style="color: green;" class="icon-plus"></i>&nbsp;Create a new Form Model</button>
            <button class="dashboard-return btn btn-default"><i
                    class="icon-arrow-left"></i>&nbsp;Return to the Dashboard</button>
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

            <button class="dashboard-return btn btn-default"><i
                    class="icon-arrow-left"></i>&nbsp;Return to the Dashboard</button>
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
            <button class="dashboard-return btn btn-default"><i
                    class="icon-arrow-left"></i>&nbsp;Return to the Dashboard</button>
        </div>
    </div>

    <div class="dashboard-page" id="dashboard-metadata" style="display: none;">
        <div class="dashboard-wrapper">
            <h2>Metadata Curation</h2>

            <h3>Under construction!</h3>
            <button class="dashboard-return btn btn-default"><i
                    class="icon-arrow-left"></i>&nbsp;Return to the Dashboard</button>
        </div>
    </div>

    <div class="dashboard-page" id="dashboard-profile" style="display: none;">
        <div class="dashboard-wrapper">
            <h2>Profile</h2>
            <br/><br/>

            <h3>Under construction!</h3>

            <p>This feature is not yet finished! This will be the place
            where you change your user profile. Please try again soon!</p>
            <button class="dashboard-return btn btn-default"><i
                    class="icon-arrow-left"></i>&nbsp;Return to the Dashboard</button>
        </div>
    </div>
</div><!-- End div dashboard -->
<g:javascript disposition="defer" library="dashboard"/>
</body>

</html>