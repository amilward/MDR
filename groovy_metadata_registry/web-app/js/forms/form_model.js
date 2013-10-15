var question1 = {
		id : 'question1',
		label: "First Name",
		style: "",
		cardinality: 1,
		rule: "",
		type: "Text_Field",
		defaultValue : "",
		maximumCharacterQuantity : "",
		unitOfMeasure : "",
		datatype : {},
		format : ""
}


var question2 = {
		id : 'question2',
		label: "Last Name",
		style: "",
		cardinality: 1,
		rule: "",
		type: "Text_Field",
		defaultValue : "",
		maximumCharacterQuantity : "",
		unitOfMeasure : "",
		datatype : {},
		format : ""
}

var question3 = {
		id : 'question3',
		label: "What is your name?",
		style: "",
		cardinality: 1,
		rule: "",
		type: "Text_Field",
		defaultValue : "",
		maximumCharacterQuantity : "",
		unitOfMeasure : "",
		datatype : {},
		format : ""
}

var question4 = {
		id : 'question4',
		label: "What is your current gender?",
		style: "",
		cardinality: 1,
		rule: "",
		type: "List_Field",
		defaultValue : "",
		maximumCharacterQuantity : "",
		unitOfMeasure : "",
		datatype : {},
		format : "",
		listItems : [{
			code: 0,
			definition : 'Not known'
		},
		{
			code: 1,
			definition : 'Male'
		},
		{
			code: 2,
			definition : 'Female'
		},
		{
			code: 3,
			definition : 'Not specified'
		}]
}


var question5 = {
    source : "COSD",
    name : "CR0150",
    description : "ETHNIC CATEGORY",
    format : "an2",
    label : "Ethnicity:",
    labelVisible : "Yes",
    editable : "Yes",
    style : "",
	type: "List_Field",
    listItems : [
    
    {    code: "A",
        definition : "(White) British"
    },
    {    code: "B",
        definition : "(White) Irish"
    },
    {    code: "C",
        definition : "Any other White Background"
    },
    {    code: "D",
        definition : "White and Black African"
    },
    {    code: "E",
        definition : "White and Black African"
    },
    {    code: "F",
        definition : "White and Asian"
    },
    {    code: "G",
        definition : "Any other mixed background"
    },
    {    code: "H",
        definition : "Indian"
    },
    {    code: "J",
        definition : "Pakistani"
    },
    {    code: "K",
        definition : "Bangladeshi"
    },
    {    code: "L",
        definition : "Any other Asian background"
    },
    {    code: "M",
        definition : "Caribbean"
    },
    {    code: "N",
        definition : "African"
    },
    {    code: "P",
        definition : "Any other Black background"
    },
    {    code: "R",
        definition : "Chinese"
    },
    {    code: "S",
        definition : "Any other ethnic group"
    },
    {    code: "Z",
        definition : "Not stated"
    },
    {    code: "99",
        definition : "Not known"
    },]
}

var question6 = {
		id : 'question6',
		label: "What is your favourite colour?",
		style: "",
		cardinality: 1,
		rule: "",
		type: "List_Field",
		defaultValue : "",
		maximumCharacterQuantity : "",
		unitOfMeasure : "",
		datatype : {},
		format : "",
		listItems : [{
			code: 0,
			definition : 'Black'
		},
		{
			code: 1,
			definition : 'White'
		},
		{
			code: 2,
			definition : 'Red'
		},
		{
			code: 3,
			definition : 'Blue'
		},
		{
			code: 4,
			definition : 'Green'
		}]
}

var question7 = {
		id : 'question7',
		label: "Who put the bom in bom bom diddleye-bom?",
		style: "",
		cardinality: 1,
		rule: "",
		type: "Text_Field",
		defaultValue : "",
		maximumCharacterQuantity : "",
		unitOfMeasure : "",
		datatype : {},
		format : ""
}



var form_model = {
		id : '127631723981',
		name : 'Patient Demographics',
		header : {
			type : "Additional_Text Element",
			id : "header",
			text: "Welcome to the form."
		},
		footer : {
			type : "Additional_Text Element",
			id : "header",
			text: "That's the end of the form.  Nothing more to complete."
		},
		containedElements : [{
			type : "Section Element",
			label : "Section 1",
			id : "section1",
			containedElements : [question1, question2, question5]
		},
		{
			type : "Section Element",
			label : "Section 2",
			id : "section2",
			containedElements : [question3, question4],
			rules : {
				display : "question1 > 5"
			}
		},
		{
			type : "Section Element",
			label : "Section 3",
			id : "section3",
			containedElements : [question6, question7],
		}]
} 
