<g:render template="/shared/header" />

<div class="container">
    <div class="row">
        <div class="col-sm-3">
            <ul id="menu" class="unstyled accordion collapse in">
                <li id="cart" class="accordion-group cart"><a
                        data-parent="#menu" data-toggle="collapse"
                        class="accordion-toggle cart" data-target="#collection_basket_list">
                    <i class="icon-shopping-cart icon-large cart"></i> Collection
                    Basket <span class="label label-inverse pull-right">0</span>
                </a>
                    <ul class="collapse cart" id="collection_basket_list">
                        <li><g:link action="show" params="[id: 1]"
                                    controller="CollectionBasket">
                            <i class="icon-angle-right"></i> view collection </g:link></li>
                    </ul></li>
                <li class="accordion-group "><a data-parent="#menu"
                                                data-toggle="collapse" class="accordion-toggle"
                                                data-target="#collections-nav"> <i
                            class="icon-list-ol icon-large"></i> Collections
                </a>
                    <ul class="collapse" id="collections-nav">
                        <li><g:link action="list" controller="Collection">
                            <i class="icon-angle-right"></i> List </g:link></li>
                        <li><g:link action="create" controller="Collection">
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
                                                data-target="#dataElementConcepts-nav"> <i
                            class="icon-sitemap icon-large"></i> Data Element Concepts
                </a>
                    <ul class="collapse " id="dataElementConcepts-nav">
                        <li><g:link action="list" controller="DataElementConcept">
                            <i class="icon-angle-right"></i> List </g:link></li>
                        <li><g:link action="create" controller="DataElementConcept">
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
                <li class="accordion-group "><a data-parent="#menu"
                                                data-toggle="collapse" class="accordion-toggle"
                                                data-target="#documents-nav"> <i class="icon-book icon-large"></i>
                    Documents
                </a>
                    <ul class="collapse " id="documents-nav">
                        <li><g:link action="list" controller="Document">
                            <i class="icon-angle-right"></i> List </g:link></li>
                        <li><g:link action="create" controller="Document">
                            <i class="icon-angle-right"></i> Create </g:link></li>
                    </ul></li>
                <li class="accordion-group "><a data-parent="#menu"
                                                data-toggle="collapse" class="accordion-toggle"
                                                data-target="#externalReference-nav"> <i
                            class="icon-external-link icon-large"></i> External References
                </a>
                    <ul class="collapse " id="externalReference-nav">
                        <li><g:link action="list" controller="ExternalReference">
                            <i class="icon-angle-right"></i> List </g:link></li>
                        <li><g:link action="create" controller="ExternalReference">
                            <i class="icon-angle-right"></i> Create </g:link></li>
                    </ul></li>
            </ul>
        </div>
        <div class="col-sm-9">
            <g:layoutBody />
        </div>
    </div>
</div>
<g:render template="/shared/footer" />