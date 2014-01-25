#= require jquery/jquery.min.js
#= require jquery-ui/ui/minified/jquery-ui.min.js
#= require jsplumb/dist/js/jquery.jsPlumb-1.5.5-min.js
#= require angular/angular.min.js
#= require angular-resource/angular-resource.min.js
#= require angular/services.js
#= require angular/pathway-editor/directives.js
#= require angular/pathway-editor/controllers.js

@grailsAppName = 'model_catalogue'
@angularAppName = 'pathway-editor'
angular.module angularAppName, ['pathway.services', 'pathway.directives', 'pathway.controllers']

