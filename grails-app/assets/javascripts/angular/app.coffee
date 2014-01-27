
#= require jsplumb/dist/js/jquery.jsPlumb-1.5.5.js
#= require angular/angular.js
#= require angular-resource/angular-resource.js
#= require angular-xeditable/dist/js/xeditable.js
#= require angular/services.js
#= require angular/utils.js
#= require angular/pathway-editor/directives.js
#= require angular/pathway-editor/controllers.js

@grailsAppName = 'model_catalogue'
@angularAppName = 'pathway-editor'
angular.module(angularAppName, ['pathway.services', 'pathway.directives', 'pathway.controllers', "xeditable", "utils"])

.run( (editableOptions) ->
  editableOptions.theme = 'bs3'; # bootstrap3 theme. Can be also 'bs2', 'default'
)
