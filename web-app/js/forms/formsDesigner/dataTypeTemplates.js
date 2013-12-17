var dataTypeTemplates = [
	 {
		 name: "numeric",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["text"],
		 allowMultiple: true,
		 simpleRestrictions: ["Integer", "PositiveInteger","NegativeInteger"],
		 useOptions: false,
		 previewRender: function(){
			 return "<span><input type=\"text\"/></span>";
		 }
	 },
	 {
		 name: "string",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["text", "textarea"],
		 allowMultiple: true,
		 useOptions: false,
		 previewRender: function(){
			 var self = this;
			 if(self.renderingOption() == "text")
				return "<span><input type=\"text\"/></span>";
			 else if(self.renderingOption() == "textarea")
				return "<span><textarea rows=\"2\" cols=\"300\" style=\"width: 40em;\"/></span>";
			 else return "";
		 }
	 },
	 {
		 name: "boolean",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["checkbox", "radio"],
		 allowMultiple: false,
		 useOptions: false,
		 previewRender: function(){
			 var self = this;
			 if(self.renderingOption() == "checkbox")
				return "<span><input type=\"checkbox\"/></span>";
				//return "<span><input type=\"text\"/></span>";
			 else if(self.renderingOption() == "radio")
				return "<span><label><input type=\"radio\"/>Yes</label><br/><label><input type=\"radio\"/>No</label></span>";
			 else return "";

		 }
	 },
	 {
		 name: "date",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["datepicker"],
		 allowMultiple: true,
		 useOptions: false,
		 previewRender: function(){
			 return "<i class=\"icon-large pull-left icon-calendar\"></i>";
		 }
	 },
	 {
		 name: "time",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["time"],
		 allowMultiple: true,
		 useOptions: false,
		 previewRender: function(){
			 return "<i class=\"icon-time pull-left icon-calendar\"></i>";
		 }
	 },
	 {
		 name: "datetime",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["datetimepicker"],
		 allowMultiple: true,
		 useOptions: false,
		 previewRender: function(){
			 return "<i class=\"icon-large pull-left icon-calendar\"></i>";
		 }
	 },
	 {
		 name: "duration",
		 isPrimitive: true,
		 isAbstract: false,
		 prefRenderingOptions: ["duration"],
		 allowMultiple: true,
		 useOptions: false,
		 previewRender: function(){
			 return "<span><input type=\"text\"/></span>";
		 }
	 },
	 {
		 name: "enumeration",
		 isPrimitive: true,
		 isAbstract: true,
		 prefRenderingOptions: ["radio","select", "checkbox","combobox"],
		 allowMultiple: true,
		 useOptions: true,
		 previewRender: function(){
			 var self = this;
			 var enumerations = "";
			 if(self.renderingOption() == "checkbox"){
					return "<span><input type=\"checkbox\"/></span>";
					//return "<span><input type=\"text\"/></span>";
			 }else if(self.renderingOption() == "radio"){
					enumerations = " <ul id=\"ui-accordion-accordion-header-1\" class=\"unstyled accordion collapse in\">";
					
					enumerations += "<li class=\"accordion-group \">" ;
					enumerations += "<a data-parent=\"#menu\" data-toggle=\"collapse\" class=\"accordion-toggle\" data-target=\"#enumerations-nav-"+self.iid() +"\">";
					enumerations += "<i class=\"icon-list-ol icon-large\"></i> Enumerations  [Radio] ";
					enumerations += "</a>";
					enumerations += "<ul class=\"collapse\" id=\"enumerations-nav-"+ self.iid() +"\">";
					$.each(self.options(),function(index, value){
						enumerations += "<input class=\"pull-left\" type=\"radio\"/><li>"+ value +" ["+index+"]</li>";

					});
					enumerations += "</ul>";
					enumerations += "</li>";
					enumerations += "</ul>";
						 
					return enumerations;
					
			 }else {
				 return enumerations;
			 }
		}
	 } 
 ];