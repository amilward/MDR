//This is my first theme for the forms renderer.
//I'm expecting to create many of these, and it will be a parameter
//to the rendering process.

var theme = {};

var _sectionNo = 0;
var _sections = [];

function nextPage(){
	if(_sectionNo < _sections.length - 1)
	{
		do{
			_sectionNo++;
		}while(_sections[_sectionNo].display == false && _sectionNo < _sections.length);
		displayPage();
	}
}

function prevPage(){
	if(_sectionNo > 0)
	{
		do{
			_sectionNo--;
		}
		while(_sections[_sectionNo].display == false && _sectionNo > 0);
		displayPage();
	}
}

function gotoPage(id){
	for(var i=0;i<_sections.length;i++){
		if(_sections[i].id == id)
		{
			_sectionNo = i;
			break;
		}
	}
	displayPage();
}

function displayPage(){
	//console.log("page: " + _sectionNo);
	$('.section').hide();
	$($('.section').get(_sectionNo)).show();
	
	var $selectedLi = $($('#sectionsdiv ul li').get(_sectionNo));
	$('#sectionsdiv ul li').addClass('deselected');
	$('#sectionsdiv ul li').removeClass('selected');

	$selectedLi.addClass('selected');
	$selectedLi.removeClass('deselected');

	if(_sectionNo <= 0){
		$('#prevPage').hide();
	}else{
		$('#prevPage').show();
	}

	if(_sectionNo >= _sections.length - 1){
		$('#nextPage').hide();
		$('#submit').show();
	}else{
		$('#nextPage').show();
		$('#submit').hide();
	}

}

function redrawNavigation(){
	var $sectionsUl = $('#sectionsdiv ul');
	$sectionsUl.html('');
	$.each(_sections, function(index, section){
		if(section.display){
			var $li = $('<li>');
			$li.text(section.label);
			$li.click(function(){
				gotoPage(section.id);
			});
			$sectionsUl.append($li);
		}
	});
	displayPage();
}


theme.hideSection = function(id){

	for(var i=0;i<_sections.length;i++){
		if(_sections[i].id == id)
		{
			_sections[i].display = false;
			break;
		}
	}
	redrawNavigation();
	
	
}

theme.showSection = function(id){
	for(var i=0;i<_sections.length;i++){
		if(_sections[i].id == id)
		{
			_sections[i].display = true;
			break;
		}
	}
	redrawNavigation();
}




theme.createForm = function(model){
	var $form = $('<form role="form" class="form-horizontal" id="' + model.id + '">');
	$form.append('<h3>' + model.name + '</h3>');
	return $form
}

theme.finishRenderForm = function($div){

	$buttonDiv = $('<div id="buttonDiv" style="text-align: right; display: block;">');
	$buttonDiv.append('<button id="prevPage" type="button" class="btn btn-default" onclick="prevPage();">Previous Page</button>');
	$buttonDiv.append('&nbsp;&nbsp;');
	$buttonDiv.append('<button id="nextPage" type="button" class="btn btn-primary" onclick="nextPage();">Next Page</button>');
	$buttonDiv.append('<button id="submit" type="button" class="btn btn-primary" onclick="submit();">Submit</button>');
	$('#formdiv').append($buttonDiv);

	redrawNavigation();
	return $div;

}
theme.perform = function(){
	displayPage();
}

theme.renderHeader = function(header, $div){

	var $sectionDiv = $('<div class="section">');
	_renderFormDesignElement(header, $sectionDiv)
	$sectionDiv.append("<p>Please click 'Next Page' to continue...</p>");
	_sections.push({
		label: 'Start',
		id: 'sectionHeader',
		display: true
	});

	$div.append($sectionDiv);

}

theme.renderFooter = function(footer, $div){

	var $sectionDiv = $('<div class="section">');
	_renderFormDesignElement(footer, $sectionDiv)

	_sections.push({
		label: 'Finish',
		id: 'sectionFooter',
		display: true
	});

	$div.append($sectionDiv);
}

theme.createSectionDiv = function(section){
	var $sectionDiv = $('<div class="section">');
	var $h1 = $('<h4>');
	$h1.text(section.label);
	$sectionDiv.append($h1);

	_sections.push({
		label: section.label,
		id: section.id,
		display: true
	})

	return $sectionDiv;
} 

theme.completeSectionDiv = function($sectionDiv, section){
	return $sectionDiv;
}

theme.renderTextField = function(inputField, $div ){
	var $formgroup = $('<div class="form-group">');

	var $label = $('<label class="col-lg-4 control-label" for="' + inputField.id + '">');
	$label.text(inputField.label);
	$formgroup.append($label);
	$formgroup.append($('<div class="col-lg-8"><input class="form-control" id="' + inputField.id + '"></div>'));
	$div.append($formgroup);
}

theme.renderListField = function(inputField, $div ){
	var $formgroup = $('<div class="form-group">');

	var $label = $('<label class="col-lg-4 control-label" for="' + inputField.id + '">');
	$label.text(inputField.label);
	$formgroup.append($label);
	var $inputDiv = $('<div class="col-lg-8">');
	var $select = $('<select class="form-control" id="' + inputField.id + '">');
	for(var i=0;i<inputField.listItems.length;i++)
	{
		$select.append('<option value="' + inputField.listItems[i].code + '">' + inputField.listItems[i].definition + '</option>');
	}
	
	$inputDiv.append($select);
	$formgroup.append($inputDiv);
	$div.append($formgroup);
}

theme.renderAdditionalTextElement = function(element, $div){
	var $p = $('<p>');
	$p.text(element.text);
	$div.append($p);
}