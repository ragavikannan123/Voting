var jsonData; // Declare jsonData variable

window.onload = function() {
    GetDetails(function() {
        // Callback function to execute other functions after fetching details
        Elections();
        CurrentElections();
        Applicants();
        Voters();
        Graphs();
    });
};

function GetDetails(callback) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.statusCode === 200) {
                    // Store response data in jsonData variable
                    jsonData = response;
                    // Call the callback function to execute other functions
                    callback();
                } else {
                    console.error("Error: " + response.message);
                }
            } else {
                console.error("Error: " + xhr.status);
            }
        }
    };

    xhr.open("POST", "http://localhost:8080/Voting/GetDetails", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify({}));
}




function home(){
	window.location.href = "Authentication.html";
}

function highlight(element, section) {
    var listItems = document.querySelectorAll('#menu li');
    listItems.forEach(function(item) {
        item.classList.remove('active');
    });
    element.classList.add('active');
    
    // Call showSection function with the corresponding section
    showSection(section);
}

function showSection(section) {
	console.log(section)
    var sections = document.getElementsByClassName('menus');
    for (var i = 0; i < sections.length; i++) {
        sections[i].style.display = 'none';
    }
    
    var sectionToShow = document.getElementById(section);
    if (sectionToShow) {
		if (sectionToShow.id === "dashBoard" || sectionToShow.id === "voters" || sectionToShow.id === "elections") {
                sectionToShow.style.display = 'flex';
            } else {
                sectionToShow.style.display = 'block';
            }
    }
}
/*var jsonData = JSON.parse(localStorage.getItem("responseData"));*/

/*    var jsonData = {
      "message": {
        "current": [
          {
            "id": 1,
            "name": "Student Council Elections",
            "start_date": "2024-03-20",
            "end_date": "2024-03-25",
            "description": "Elections for the student council positions."
          },
          {
            "id": 2,
            "name": "Class Representative Elections",
            "start_date": "2024-03-15",
            "end_date": "2024-03-30",
            "description": "Elections to select class representatives for each grade level."
          }
        ],
         "year":{
			"2022":2,
			"2023":1,
			"2023":3
		},
		 "votes":{
			"School Commitee":56,
			"SPL":54,
			"Club Officer":59
		},
		"applicants": [
    		{
		      "apply_id": 1,
		      "name": "John Doe",
		      "email":"john@gmail.com",
		      "dob": "1990-05-15",
		      "gender": "Male",
		      "phone": "1234567890",
		      "photo": "images (7).jpeg",
		      "election": "Student Council Elections",
		      "logo": "images (4).jpeg"
		    }
		  ],
		  "voters": [
    		{
		      "voter_id": 1,
		      "name": "John Doe",
		      "email":"john@gmail.com",
		      "dob": "1990-05-15",
		      "gender": "Male",
		      "phone": "1234567890",
		      "photo": "images (7).jpeg"
		    },
		    {
		      "voter_id": 2,
		      "name": "Ragavi",
		      "email":"ragavi@gmail.com",
		      "dob": "2005-11-05",
		      "gender": "Female",
		      "phone": "8789665784",
		      "photo": "images (7).jpeg"
		    }
		  ],
		  "elections":[
			  {
			    "id": 1,
			    "name": "Student Council Elections",
			    "start_date": "2024-03-25",
			    "end_date": "2024-03-25",
			    "description": "Elections for the student council positions.",
			    "votes":{
				  "Jane Smith": 50,
				  "John Doe": 45
				},
				"votes":{
					"Ragavi":200,
					"Kavi":298
				},
				"candidates":[
				  {
				    "id": 1,
				    "voter_name": "John Doe",
				    "dob": "1990-05-15",
				    "gender": "Male",
				    "phone": "1234567890",
				    "photo": "images (7).jpeg",
				    "logo": "images (4).jpeg"
				  }
				]
			  },
			  {
			    "id": 2,
			    "name": "Class Representative Elections",
			    "start_date": "2024-03-15",
			    "end_date": "2024-03-30",
			    "description": "Elections to select class representatives for each grade level."
			  }
			]
      }
    };*/
// Access data for the bar chart and line chart from jsonData
function Graphs() {
    var yearData = jsonData.message.year;
    var votesData = jsonData.message.votes;
    var barCanvas = document.getElementById('barChart');
    var lineCanvas = document.getElementById('lineChart');

    if (yearData == undefined) {
        var barChartData = {
            labels: [],
            datasets: [{
                label: 'Bar Chart',
                data: [],
                backgroundColor: 'rgba(255, 99, 132, 0.5)'
            }]
        }
        var barChart = new Chart(barCanvas, {
            type: 'bar',
            data: barChartData,
            options: {}
        });
    } else {
        var barChartData = {
            labels: Object.keys(yearData),
            datasets: [{
                label: 'Bar Chart',
                data: Object.values(yearData),
                backgroundColor: 'rgba(255, 99, 132, 0.5)'
            }]
        }
        var barChart = new Chart(barCanvas, {
            type: 'bar',
            data: barChartData,
            options: {}
        });
    }

    if (votesData == undefined) {
        var lineChartData = {
            labels: [],
            datasets: [{
                label: 'Line Chart',
                data: [],
                backgroundColor: 'rgba(255, 99, 132, 0.5)'
            }]
        }
        var lineChart = new Chart(lineCanvas, {
            type: 'line',
            data: lineChartData,
            options: {}
        });
    } else {
        var lineChartData = {
            labels: Object.keys(votesData),
            datasets: [{
                label: 'Line Chart',
                data: Object.values(votesData),
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1,
                fill: false
            }]
        }
        var lineChart = new Chart(lineCanvas, {
            type: 'line',
            data: lineChartData,
            options: {}
        });
    }
}

        
        
   

    // Function to generate HTML for elections
    function generateCurrentElectionHTML(election) {
	  var electiondiv = document.createElement("section");
	  electiondiv.classList.add("curEleDiv");
	  
	  var title = document.createElement("h2");
	  title.classList.add("curEleTitle");
	  title.innerText =  election.name;
	  electiondiv.appendChild(title);	
	
	  var desc = document.createElement("p");
	  desc.classList.add("curEleDesc");
	  desc.innerText =  election.description;
	  electiondiv.appendChild(desc);
	  
	  var date = document.createElement("p");
	  date.classList.add("curEleDate");
	  date.innerHTML = "Date: "+ election.start_date+" to "+election.end_date;
	  electiondiv.appendChild(date);
      return electiondiv;
    }

    // Display elections
    function CurrentElections(){
		console.log("get");
    var electionsList = document.getElementById('current');
    var currentElections = jsonData.message.current;
    console.log(currentElections.length);
    if(currentElections.length == 0){
		var message = document.createElement("p");
		message.innerText = "There is no current elections";
		electionsList.appendChild(message);
	}
    currentElections.forEach(function(election) {
      var electionHTML = generateCurrentElectionHTML(election);
      electionsList.appendChild(electionHTML);
    });
    }
    
    
        // Function to generate HTML for applicants
    function generateApplicantHTML(applicant) {
	  var applicantDiv = document.createElement("section");
	  applicantDiv.classList.add("applicantDiv");
	  
	  var img = document.createElement("section");
	  img.classList.add("applicantImg");
	  img.style.backgroundImage = "url(\"images/User/"+applicant.photo+"\")";
	  applicantDiv.appendChild(img);
	  
	  var details = document.createElement("section");
	  details.classList.add("applicantDetails");
	  
	  var array = [applicant.name,applicant.email,applicant.dob,applicant.gender,applicant.phone,applicant.election,applicant.logo];
	  var array2 = ["Name","Email","DOB","Gender","Phone","Election","Logo"];
	  for(let i=0;i<5;i++){
		  var text = document.createElement("section");
		  text.classList.add("applicantsDetailsDiv");
		  var lable = document.createElement("p");
		  lable.classList.add("applicantLable");
		  lable.innerText = array2[i];
		  text.appendChild(lable);
		  var content = document.createElement("p");
		  content.classList.add("applicantContent");
		  content.innerText = array[i];
		  text.appendChild(content);
		  details.appendChild(text);
	  }
	  applicantDiv.appendChild(details);
	  
	  var electionDetails = document.createElement("section");
	  electionDetails.classList.add("applicantDetails");
	  
	  	  var text = document.createElement("section");
		  text.classList.add("applicantsDetailsDiv");
		  var lable = document.createElement("p");
		  lable.classList.add("applicantLable");
		  lable.innerText = array2[5];
		  text.appendChild(lable);
		  var content = document.createElement("p");
		  content.classList.add("applicantContent");
		  content.innerText = array[5];
		  text.appendChild(content);
		  electionDetails.appendChild(text);
		  
		  var text = document.createElement("section");
		  text.classList.add("applicantsDetailsDiv");
		  var lable = document.createElement("p");
		  lable.classList.add("applicantLable");
		  lable.innerText = array2[6];
		  text.appendChild(lable);
		  var content = document.createElement("p");
		  content.classList.add("logo");
		  content.style.backgroundImage = "url(\"images/User/"+array[6]+"\")";
		  text.appendChild(content);
		  electionDetails.appendChild(text);
		  
		  var add = document.createElement("span");
		  add.classList.add("addApplicant");
		  add.classList.add(applicant.apply_id);
		  add.innerText = "Approve";
		  electionDetails.appendChild(add);
		  
		  add.onclick = function(){
			  AddApplicant(this.classList[1]);
		  }
		  
		  var remove = document.createElement("span");
		  remove.classList.add("removeApplicant");
		  remove.classList.add(applicant.apply_id);
		  remove.innerText = "Reject";
		  electionDetails.appendChild(remove);
		  
		  remove.onclick = function(){
			  RemoveApplicant(this.classList[1]);
		  }
		  
		  
		applicantDiv.appendChild(electionDetails);	  
	  
	  return applicantDiv;
    }

    // Display applicantss\
    function Applicants(){
    var applicantsList = document.getElementById('applicant');
    var applicants = jsonData.message.applicants;
    applicants.forEach(function(applicant) {
      var applicantHTML = generateApplicantHTML(applicant);
      applicantsList.appendChild(applicantHTML);
    });
    }
    
      // Function to generate HTML for voters
    function generateVoterHTML(voter) {
	  var applicantDiv = document.createElement("section");
	  applicantDiv.classList.add("voterDiv");
	  
	  var img = document.createElement("section");
	  img.classList.add("voterImg");
	  img.style.backgroundImage = "url(\"images/User/"+voter.photo+"\")";
	  applicantDiv.appendChild(img);
	  
	  var details = document.createElement("section");
	  details.classList.add("voterDetails");
	  
	  var array = [voter.name,voter.email,voter.dob,voter.gender,voter.phone];
	  var array2 = ["Name:","Email:","DOB:","Gender:","Phone:"];
	  for(let i=0;i<5;i++){
		  var text = document.createElement("section");
		  text.classList.add("applicantsDetailsDiv");
		  var lable = document.createElement("p");
		  lable.classList.add("applicantLable");
		  lable.innerText = array2[i];
		  text.appendChild(lable);
		  var content = document.createElement("p");
		  content.classList.add("applicantContent");
		  content.innerText = array[i];
		  text.appendChild(content);
		  details.appendChild(text);
	  }
	  applicantDiv.appendChild(details);
	  
	  var icon = document.createElement("i");
	  console.log(voter.voter_id)
	  icon.classList.add("fa-solid", "fa-trash-can", voter.voter_id+"");
	  applicantDiv.appendChild(icon);
	  
	  icon.onclick = function(){
		  RemoveVoter(this.classList[2]);
	  }

	  return applicantDiv;
    }

    // Display voters
    function Voters(){
    var votersList = document.getElementById('voters');
    var voters = jsonData.message.voters;
    voters.forEach(function(voter) {
      var votersHTML = generateVoterHTML(voter);
      votersList.appendChild(votersHTML);
    });
    }
    // Function to generate HTML for elections
function generateElectionHTML(election) {
    var electionDiv = document.createElement("section");
    electionDiv.classList.add("eleDiv");

    var title = document.createElement("h3");
    title.classList.add("eleTitle");
    title.innerText = election.name;
    electionDiv.appendChild(title);

    var desc = document.createElement("p");
    desc.classList.add("eleDesc");
    desc.innerText = election.description;
    electionDiv.appendChild(desc);

    var date = document.createElement("p");
    date.classList.add("eleDate");
    date.innerHTML = "Date: " + election.start_date + " to " + election.end_date;
    electionDiv.appendChild(date);

    electionDiv.addEventListener("click", function() {
        showCandidates(election); // Pass the election object to the showCandidates function
    });

    return electionDiv;
}

// Display elections
function Elections(){
	console.log(jsonData);
var electionsList = document.getElementById('elections');

var electionsDiv = document.createElement("div");
electionsDiv.classList.add("electionsDiv");
var elections = jsonData.message.elections;
elections.forEach(function(election) {
    var electionHTML = generateElectionHTML(election);
    electionsDiv.appendChild(electionHTML);
});
electionsList.appendChild(electionsDiv);

 var eleside = document.createElement("div");
    eleside.classList.add("eleside");

    var addElection = document.createElement("section");
    addElection.classList.add("addElection");
    
    var labels = ["Title","Description","Start","End"];
    var classes = ["addTitle","addDesc","addStart","addEnd"];
    for(let i=0;i<4;i++){
		var details = document.createElement("section");
		details.classList.add("candidateDetailsDiv");
		var label = document.createElement("p");
		label.classList.add("candidateLabel");
		label.innerText = labels[i];
		details.appendChild(label);
		var content = document.createElement("input");
		if(i < 2){
			content.setAttribute('type', 'text');
		}
		else{
			content.setAttribute('type', 'date');
		}
		content.classList.add("addInput");
		content.classList.add(classes[i]);
		details.appendChild(content);
		addElection.appendChild(details);
	}
	var submit = document.createElement("button");
	submit.classList.add("addSubmit");
	submit.innerText = "Submit";
	addElection.appendChild(submit);
	
	submit.onclick = function (){
		AddElection();
	};
	
	eleside.appendChild(addElection);

    var graphCanvas = document.createElement("canvas");
    graphCanvas.id = "votesEleGraph";
    graphCanvas.style.backgroundColor = "white";
	graphCanvas.style.marginTop = "5%";
	graphCanvas.style.height = "360px";
	graphCanvas.style.borderRadius = "10px";
    eleside.appendChild(graphCanvas);

    electionsList.appendChild(eleside);
    
    generateVoteEleGraph(jsonData.message.votes);

}
function generateVoteEleGraph(votes) {
	console.log(votes);
    var ctx = document.getElementById('votesEleGraph').getContext('2d');

    // Check if votes object is empty
    if (votes === undefined) {
        // Create an empty graph
        var emptyChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: [],
                datasets: [{
                    label: 'Votes',
                    data: [],
                    backgroundColor: 'rgba(0, 0, 0, 0)', // Transparent background
                    borderColor: 'rgba(0, 0, 0, 0)', // Transparent border color
                    borderWidth: 0
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    } else {
        // Extract candidate names and vote counts from the votes object
        var candidateNames = Object.keys(votes);
        var voteCounts = Object.values(votes);

        // Create the chart
        var voteChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: candidateNames,
                datasets: [{
                    label: 'Votes',
                    data: voteCounts,
                    backgroundColor: 'rgba(54, 162, 235, 0.6)', // Blue color
                    borderColor: 'rgba(54, 162, 235, 1)', // Border color
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    }
}







// Function to show candidates for a specific election
function showCandidates(election) {
    // Hide the list of elections
    var electionsList = document.getElementById('elections');
    electionsList.style.display = 'none';

    // Clear the previous candidates list if it exists
    var candidatesList = document.getElementById('candidates');
    if (candidatesList) {
        candidatesList.innerHTML = '';
    } else {
        candidatesList = document.createElement('div');
        candidatesList.id = 'candidates';
        electionsList.parentNode.appendChild(candidatesList); // Append to the parent of electionsList
    }
    var candidateDeatils = document.createElement("div");
    candidateDeatils.classList.add("candidateDetails");

    if (election.candidates && election.candidates.length > 0) {
        election.candidates.forEach(function(candidate) {
            var candidateHTML = generateCandidateHTML(candidate);
            candidateDeatils.appendChild(candidateHTML);
        });

    } else {
        var noCandidatesMsg = document.createElement('p');
        noCandidatesMsg.innerText = 'No candidates available for this election.';
        candidateDeatils.appendChild(noCandidatesMsg);
    }
    candidatesList.appendChild(candidateDeatils);

    var canside = document.createElement("div");
    canside.classList.add("canside");
  
  	var details = document.createElement("section");
  	details.classList.add("elecDetails")
    var title = document.createElement("h3");
    title.classList.add("eleTitle");
    title.innerText = election.name;
    details.appendChild(title);

    var desc = document.createElement("p");
    desc.classList.add("eleDesc");
    desc.innerText = election.description;
    details.appendChild(desc);

    var date = document.createElement("p");
    date.innerHTML = "Date: " + election.start_date + " to " + election.end_date;
    details.appendChild(date);
    canside.appendChild(details);

    var graph1 = document.createElement("canvas");
    graph1.id = "voteGraph";
    graph1.style.backgroundColor = "white";
	graph1.style.marginTop = "5%";
	graph1.style.borderRadius = "10px";
    canside.appendChild(graph1);
    
    candidatesList.appendChild(canside);
     var close = document.createElement("i");
    close.classList.add("fa-solid", "fa-xmark","close");
    candidatesList.appendChild(close);
    
    close.onclick = function() {
    showSection("elections");
};

    // Generate the graph
    generateVoteGraph(election.votes);

    
}

function generateVoteGraph(votes) {
    var ctx = document.getElementById('voteGraph').getContext('2d');

    // Extract candidate names and vote counts from the votes object
    var candidateNames = Object.keys(votes);
    var voteCounts = Object.values(votes);

    // Create the chart
    var voteChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: candidateNames,
            datasets: [{
                label: 'Votes',
                data: voteCounts,
                backgroundColor: 'rgba(54, 162, 235, 0.6)', // Blue color
                borderColor: 'rgba(54, 162, 235, 1)', // Border color
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}


// Function to generate HTML for candidates
function generateCandidateHTML(candidate) {
    var candidateDiv = document.createElement("section");
    candidateDiv.classList.add("candidateDiv");

    var img = document.createElement("div");
    img.classList.add("candidateImg");
    img.style.backgroundImage = "url(\"images/User/"+candidate.photo+"\")";
    candidateDiv.appendChild(img);

    var details = document.createElement("div");
    details.classList.add("canDetails");

    var array = [candidate.voter_name,candidate.email,candidate.gender,candidate.phone,candidate.logo];
    var array2 = ["Name","Email","Gender","Phone", "Logo"];
    for (let i = 0; i < 5; i++) {
        var text = document.createElement("div");
        text.classList.add("candidateDetailsDiv");
        var label = document.createElement("p");
        label.classList.add("candidateLabel");
        label.innerText = array2[i];
        text.appendChild(label);
        var content = document.createElement("p");
        content.classList.add("candidateContent");
        if (i === 4) {
            content.classList.add("canlogo");
            content.style.backgroundImage = "url(\"images/User/"+array[i]+"\")";
        } else {
            content.innerText = array[i];
        }
        
        text.appendChild(content);
        details.appendChild(text);
    }
  
    candidateDiv.appendChild(details);

    return candidateDiv;
}


function AddElection() {
    var title = document.getElementsByClassName("addTitle")[0].value;
    var desc = document.getElementsByClassName("addDesc")[0].value;
    var start = document.getElementsByClassName("addStart")[0].value;
    var end = document.getElementsByClassName("addEnd")[0].value;

    var data = {
        "name": title,
        "description": desc,
        "startDate": start,
        "endDate": end
    };

    var jsonData = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                alert(response.message);
                setTimeout(function() {
                    location.reload();
                }, 3000);
                
            } 
            else {
                console.error("Error: "+xhr.status);
            }
        }
    };
	xhr.open("POST", "http://localhost:8080/Voting/AddElection", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(jsonData);
}

function AddApplicant(id) {
    var data = {
        "applyId": id
    };

    var jsonData = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                alert(response.message);
                setTimeout(function() {
                    location.reload();
                }, 3000);
                console.log(response.message);
            } else {
                console.error("Error: " + xhr.status);
            }
        }
    };
    xhr.open("POST", "http://localhost:8080/Voting/AddCandidate", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(jsonData);
}

function RemoveApplicant(id) {
    var data = {
        "applicantId": id
    };

    var jsonData = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                alert(response.message);
                setTimeout(function() {
                    location.reload();
                }, 3000);
            } else {
                console.error("Error: " + xhr.status);
            }
        }
    };
    xhr.open("POST", "http://localhost:8080/Voting/RemoveApplicant", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(jsonData);
}

function RemoveVoter(id) {
    var data = {
        "voterId": id
    };

    var json = JSON.stringify(data);
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                alert(response.message);
                setTimeout(function() {
                    location.reload();
                }, 3000);
            } else {
                alert(response.message);
            }
        }
        else{
			console.error("Error: " + xhr.status);
		}
    };
    xhr.open("POST", "http://localhost:8080/Voting/RemoveVoter", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(json);
}
