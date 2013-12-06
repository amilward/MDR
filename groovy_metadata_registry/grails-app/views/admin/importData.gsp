<!DOCTYPE html>
<html>
  <head>
    <meta name="layout" content="main_no-sidebar">
    <title>Import data</title>
  </head>
  <body>
    <div id="content">
      <h1>Warning!</h1>
      <p>Running data imports can take a significant amount of time and will likely render the system unusable (e.g. 4 hours for NHIC at the moment).</p>
      <p><span>Be sure you want to do this!</span></p>
      <div>
        <ul>
          <li><g:link controller="dataImport" action="importDataSet" params="[datasets: ['nhic']]]">Import NHIC dataset</g:link></li>
        </ul>
      </div>
    </div>
  </body>
</html>
