function DataTypeInstance(iid, eid, name, instanceOf, renderingOption, restriction, selectMultiple, options, previewRender)
{
	var self = this;
	self.iid = ko.observable(iid);
	self.eid = ko.observable(eid);
	self.name = ko.observable(name);
	self.instanceOf = ko.observable(instanceOf);
	self.renderingOption = ko.observable(renderingOption);
	self.restriction = ko.observable(restriction);
	self.selectMultiple = ko.observable(selectMultiple);
	self.options = ko.observable(options);
	self.previewRender = previewRender;
	self.clone = function() {
		return new DataTypeInstance( self.iid(), self.eid(), self.name(), 
				self.instanceOf(), self.renderingOption(), self.restriction(),
				self.selectMultiple(), self.options(), self.previewRender);
	};
	
	self.renderingOptions = function() {
		var array = [];
		$.each(self.instanceOf().prefRenderingOptions, function(i, n)
		{
			array.push({'value': n, 'text': n});
		});
		return array;
	};
	
}