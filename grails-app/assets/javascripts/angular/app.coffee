#= require jquery/jquery.js
#= require jquery-ui/ui/jquery-ui.js
#= require jsplumb/dist/js/jquery.jsPlumb-1.5.5.js
#= require angular/angular.min.js
#= require angular-resource/angular-resource.js
#= require angular/services.js
#= require angular/pathway-editor/directives.js
#= require angular/pathway-editor/controllers.js

@grailsAppName = 'model_catalogue'
@angularAppName = 'pathway-editor'
angular.module(angularAppName, ['pathway.services', 'pathway.directives', 'pathway.controllers', "xeditable"])

.run( (editableOptions) ->
  editableOptions.theme = 'bs3'; # bootstrap3 theme. Can be also 'bs2', 'default'
)
