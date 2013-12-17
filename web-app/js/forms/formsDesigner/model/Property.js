function Property(iname, ename, value){
	var self = this;

	self.iname = ko.observable(iname);
	self.ename = ko.observable(ename);
	self.value = ko.observable(value);
}