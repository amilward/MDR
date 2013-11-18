    <!-- #push do not remove -->
    <div id="push"></div>
    <!-- /#push -->
  </div>
  <!-- END WRAP -->

  <div class="clearfix"></div>

  <!-- BEGIN FOOTER -->
  <div id="footer">
    <p>2013 © Model Catalog</p>
  </div>
  <!-- END FOOTER -->

  <!-- #helpModal -->
  <div id="helpModal" class="modal hide fade" tabindex="-1" role="dialog"
    aria-labelledby="helpModalLabel" aria-hidden="true">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal"
        aria-hidden="true">×</button>
      <h3 id="helpModalLabel">
        <i class="icon-external-link"></i> Help
      </h3>
    </div>
    <div class="modal-body">
      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
        do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
        enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi
        ut aliquip ex ea commodo consequat. Duis aute irure dolor in
        reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
        pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
        culpa qui officia deserunt mollit anim id est laborum.</p>
    </div>
    <div class="modal-footer">

      <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
    </div>
  </div>
  <!-- /#helpModal -->


  <div id="spinner" class="spinner" style="display: none;">
    <g:message code="spinner.alt" default="Loading&hellip;" />
  </div>

  <g:javascript library="application" />
  <sec:ifLoggedIn>
    <r:script>
            $(function() {
                dashboard();
            });
        </r:script>
  </sec:ifLoggedIn>
  <div id="dialog-confirm" style="display: none"></div>
  <r:layoutResources />
  
  </body>
  </html>