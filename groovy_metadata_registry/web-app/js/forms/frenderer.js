// In an MVC world, this would probably be some sort of controller.
// We use a 'theme', which acts as the 'view' part of the MVC.
// This provides specific details about how to render the elements on the page


function appendFunction(f, x){
    return function() {if(f) { x(); f(); } else x(); };
}

function prependFunction(f, x){
    return function() {if(f) {  f(); x();} else x(); };
}

var bindfunction = function(){
	// This is the function that will be called when an 
	// input field is changed.
	// We'll append stuff to it, later.
	
}

function render(model, $div){
	var $form = theme.createForm(model);

	// render the header on the given div
	_renderHeader(model.header, $form);
	
	// For each contained element, render it on the same div
	$.each(model.containedElements, function(index, element){
		_renderFormDesignElement(element, $form);
	});
	
	// Finally, render the footer on the div
	_renderFooter(model.footer, $form);

	$form = theme.finishRenderForm($form);
	
	$div.append($form);

	$form.find(':input').on('input', function(){
		bindfunction();
	});
	
	theme.perform();
}


function _renderHeader(header, $div){

	theme.renderHeader(header, $div);

}

function _renderFooter(footer, $div){
	theme.renderFooter(footer, $div);
}

function _renderSection(section, $div){
	var $sectionDiv = theme.createSectionDiv(section);
	
	// For each contained element, render it on the same div
	$.each(section.containedElements, function(index, element){
		_renderFormDesignElement(element, $sectionDiv);
	});
	
	$sectionDiv = theme.completeSectionDiv($sectionDiv, section);
	
	if(section.rules && section.rules.display)
	{
		bindfunction = appendFunction(bindfunction, function(){
			var answer = constraint.parse(section.rules.display);
			if(answer)
			{
				theme.hideSection(section.id);
			}
			else
			{
				theme.showSection(section.id);
			}

		});
		
	}
	$div.append($sectionDiv);
}

function _renderTextField(inputField, $div){
	theme.renderTextField(inputField, $div);
}

function _renderListField(inputField, $div){
	theme.renderListField(inputField, $div);
}

function _renderAdditionalTextElement(element, $div){
	theme.renderAdditionalTextElement(element, $div);
}

function _renderFormDesignElement(element, $div){
	switch (element.type) {
	case "Section Element":
		_renderSection(element, $div);
		break;
	case "Text_Field":
		_renderTextField(element, $div);
		break;
	case "List_Field":
		_renderListField(element, $div);
		break;
	case "Additional_Text Element":
		_renderAdditionalTextElement(element, $div);
		break;

	default:
		break;
	}
	
	
}



render(form_model, $('#formdiv'));