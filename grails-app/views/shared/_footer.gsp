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
  
  <!--<div class="modal-dialog" id="dialog-confirm" style="visibility: hidden"></div>-->
  
  <!-- Confirm Modal -->
    <div id="dialog-confirm" class="modal fade hide" id="dialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" >
                <div class="modal-header">
                    
                    <h4 class="modal-title" id="myModalLabel"></h4>
                </div>
                <div class="modal-footer">
                    <button type="button" id="deleteModalButton" class="btn btn-primary">Confirm</button>
                    <button type="button" class="closeModalLink btn btn-primary">Cancel</button>
                </div>
            </div><!-- /.modal-content -->
      </div> <!-- /.modal-dialog -->
   </div><!-- /.modal -->

<!-- Add Google Analytics support (only for production, of course!) -->
<g:if env="production" test="${cacheEnabled}">
	<script>
		(function(i, s, o, g, r, a, m) {
			i['GoogleAnalyticsObject'] = r;
			i[r] = i[r] || function() {
				(i[r].q = i[r].q || []).push(arguments)
			}, i[r].l = 1 * new Date();
			a = s.createElement(o), m = s.getElementsByTagName(o)[0];
			a.async = 1;
			a.src = g;
			m.parentNode.insertBefore(a, m)
		})(window, document, 'script',
				'//www.google-analytics.com/analytics.js', 'ga');

		ga('create', 'UA-46307853-1', '54.194.47.178');
		ga('send', 'pageview');
	</script>
</g:if>
<!-- Add Google Analytics support -->

<r:layoutResources />
  
  </body>
  </html>