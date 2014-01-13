<g:render template="/shared/header" />




		<!-- BEGIN LEFT  -->
		<div id="left">
			<!-- .user-media -->
			<div class="media user-media hidden-phone">
				<a href="" class="user-link"> <g:img dir="img" file="user.gif"
						alt="" class="media-object img-polaroid user-img" />
				</a>

				<div class="media-body hidden-tablet">
					<h5 class="media-heading">
						<sec:ifLoggedIn>
							<sec:loggedInUserInfo field="username" />
						</sec:ifLoggedIn>
					</h5>
					<ul class="unstyled user-info">
						<li><sec:ifLoggedIn>
								<sec:ifAnyGranted roles="ROLE_ADMIN">Administrator</sec:ifAnyGranted>
							</sec:ifLoggedIn></li>
						<!--  <li>Last Access : <br/>
                                <small><i class="icon-calendar"></i></small>
                            </li>-->
					</ul>
				</div>
			</div>
			<!-- /.user-media -->

			<!-- BEGIN MAIN NAVIGATION -->
			<ul id="menu" class="unstyled accordion collapse in">
				<li id="cart" class="accordion-group cart"><a
					data-parent="#menu" data-toggle="collapse"
					class="accordion-toggle cart" data-target="#model_basket_list">
						<i class="icon-shopping-cart icon-large cart"></i> Model
						Basket <span class="label label-inverse pull-right">0</span>
				</a>
					<ul class="collapse cart" id="model_basket_list">
						<li><g:link action="show" params="[id: 1]"
								controller="ModelBasket">
								<i class="icon-angle-right"></i> view model </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#models-nav"> <i
						class="icon-list-ol icon-large"></i> Models
				</a>
					<ul class="collapse" id="models-nav">
						<li><g:link action="list" controller="Model">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="Model">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#dataElements-nav"> <i
						class="icon-tasks icon-large"></i> Data Elements
				</a>
					<ul class="collapse " id="dataElements-nav">
						<li><g:link action="list" controller="DataElement">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="DataElement">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#valueDomains-nav"> <i
						class="icon-th-large icon-large"></i> Value Domains
				</a>
					<ul class="collapse " id="valueDomains-nav">
						<li><g:link action="list" controller="ValueDomain">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="ValueDomain">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#conceptualDomains-nav"> <i
						class="icon-th icon-large"></i> Conceptual Domains
				</a>
					<ul class="collapse " id="conceptualDomains-nav">
						<li><g:link action="list" controller="ConceptualDomain">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="ConceptualDomain">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#dataTypes-nav"> <i
						class="icon-subscript icon-large"></i> Data Types
				</a>
					<ul class="collapse " id="dataTypes-nav">
						<li><g:link action="list" controller="DataType">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="DataType">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<!--   <li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#documents-nav"> <i class="icon-book icon-large"></i>
						Documents
				</a>
					<ul class="collapse " id="documents-nav">
						<li><g:link action="list" controller="Document">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="Document">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>-->
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#formDesign-nav"> <i
						class="icon-file-text-alt icon-large"></i> Forms
				</a>
					<ul class="collapse " id="formDesign-nav">
						<li><g:link action="list" controller="FormDesign">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="FormDesign">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
				<li class="accordion-group "><a data-parent="#menu"
					data-toggle="collapse" class="accordion-toggle"
					data-target="#pathways-nav"> <i
						class="icon-file-text-alt icon-large"></i> Pathways
				</a>
					<ul class="collapse " id="pathways-nav">
						<li><g:link action="list" controller="PathwaysModel">
								<i class="icon-angle-right"></i> List </g:link></li>
						<li><g:link action="create" controller="PathwaysModel">
								<i class="icon-angle-right"></i> Create </g:link></li>
					</ul></li>
			</ul>
			<!-- END MAIN NAVIGATION -->

		</div>
		<!-- END LEFT -->

<g:render template="/shared/content" />

<g:render template="/shared/footer" />