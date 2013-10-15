function getPathway(pathwayId){
	$.getJSON('../jsonPathways/' + pathwayId, function(data) {
		openPathways(data);
	})
	
	/*pathway_model = {
		
		nodes: [{
			id: "node1",
			name : 'Assesed for Eligibility',
			shortDescription: 'At this stage, the patient is assessed for their eligibility for the trial.  This description might include how the assessment takes place, perhaps including links to the eligibility criteria.',
			x : 5,
			y : 0,
			dataElements : [{
				id : 'Data_Element_1',
				description : 'Name in Klingon'
			},{
				id : 'Data_Element_7',
				description : 'Age of best friend'
			}]
		},
		{
			id: "node2",
			name : 'Randomized',
			shortDescription: 'Here the participant is randomized.  The randomization protocol could be accessed via a link here.',
			x : 15,
			y : 10,
			dataElements : [{
				id : 'Data_Element_10',
				description : 'How loud can you shout?'
			},{
				id : 'Data_Element_11',
				description : 'Peak tear flow'
			},{
				id : 'Data_Element_19',
				description : 'Can you stand on your head?'
			}]
		},
		{
			id: "node3",
			name : 'Allocated to Intervention 1',
			shortDescription: 'At this point in the trial, the patient is allocated to a particular intervention.  Description of the first intervention goes here.',
			x : 25,
			y : 20,
			dataElements : [{
				id : 'Data_Element_4',
				description : 'Date of Birth'
			},{
				id : 'Data_Element_9',
				description : 'Date of first Admission'
			},{
				id : 'Data_Element_23',
				description : 'Referring Clinician'
			},{
				id : 'Data_Element_23',
				description : 'No. of visits'
			}]
		},
		{
			id: "node4",
			name : 'Allocated to Intervention 2',
			shortDescription: 'At this point in the trial, the patient is allocated to a particular intervention.  Description of the second intervention goes here.',
			x : 43,
			y : 20,
			dataElements : [{
				id : 'Data_Element_15',
				description : 'Size of left foot'
			},{
				id : 'Data_Element_19',
				description : 'How high can you jump?'
			},{
				id : 'Data_Element_25',
				description : 'Largest Ear'
			}]
			
		}],
		
		links: [{
			source: "node1",
			target: "node2",
			label: "Randomization"
		},
		{
			source: "node2",
			target: "node3",
			label: "Allocated to Phase A"
		},
		{
			source: "node2",
			target: "node4",
			label: "Allocated to Phase B"
		}]


			
			

	}*/
	
}
