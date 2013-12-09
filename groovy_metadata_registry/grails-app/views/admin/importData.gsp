<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main_no-sidebar">
    <title>Import data</title>
  </head>
  <body>
    <div id="main">
      <h1>Warning!</h1>
      <p>Running data imports can take a significant amount of time and will likely render the system unusable (e.g. 4 hours for NHIC at the moment).</p>
      <p><strong>Be sure you want to do this!</strong></p>
      <div class="alert alert-warning"><strong>Be very careful to only click an item once. No error checking is carried out yet! </strong></div>
      <div>
        <ul>
          <li><g:link controller="dataImport" action="importDataSet" params="[dataset: 'nhic']">Import NHIC dataset</g:link></li>
        </ul>
      </div>
      
    </div>
    <div id="flash" class="alert alert-info"><g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/></div>
  </body>
</html>
