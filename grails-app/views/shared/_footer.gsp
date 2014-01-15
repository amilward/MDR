
  </div>
  <!-- END WRAP (from header) -->


  <!-- BEGIN FOOTER -->
    <div id="footer">
        <div class="container">
            <p class="text-muted">Model Catalogue &copy; 2014 </p>
        </div>
    </div>
  <!-- END FOOTER -->

  <!-- TODO this needs removing, I don' think it's used anywhere -->
  <div id="spinner" class="spinner" style="display: none;">
    <g:message code="spinner.alt" default="Loading&hellip;" />
  </div>

  <sec:ifLoggedIn>
    <r:script>
            $(function() {
                dashboard();
            });
        </r:script>
  </sec:ifLoggedIn>

  <!-- FIXME this needs to be moved, it doesn't belong in the footer -->
  <!-- Confirm Modal -->
    <div id="dialog-confirm" class="modal fade" id="dialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" >
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel"></h4>
                </div>
                <div class="modal-footer">
                    <button type="button" id="deleteModalButton" class="btn btn-primary">Confirm</button>
                    <button type="button" class="closeModalLink btn btn-primary" data-dismiss="modal">Cancel</button>
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
