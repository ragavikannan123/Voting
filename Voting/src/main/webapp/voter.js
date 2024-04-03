var jsonData; // Declare jsonData variable

window.onload = function() {
    GetDetails(function() {
        // Callback function to execute other functions after fetching details
        Elections();
        CurrentElections();
        Graphs();
    });
};

function GetDetails(callback) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                	if(response.message == "Loggedout"){
						window.location.href = "Authentication.html";
					}
					else{
						jsonData = response;
                    callback();
                
					}
                    
            } else {
                console.error("Error: " + xhr.status);
            }
        }
    };

    xhr.open("POST", "http://localhost:8080/Voting/GetDetailsForUser", true);
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
		if (sectionToShow.id === "dashBoard" || sectionToShow.id === "elections") {
                sectionToShow.style.display = 'flex';
            } else {
                sectionToShow.style.display = 'block';
            }
    }
}




   /* var jsonData = {
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
        "chart":{
			"Upcoming":2,
			"Ongoing":1,
			"Finished":5
		},
		"org":{
			"Title":"ZOHO Corporation",
			"Name":"Ragavi K",
			"Email":"ragavi@gmail.com"
		},
		"profile":{
			"name":"Kavi",
			"email":"kavi@gmail.com",
			"photo":"images (7).jpeg",
			"dob":"2005-11-05",
			"gender":"Female",
			"phone":"8796578564"
		},
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
				],
				"isCandidate":"none",
				"giveVote":"none"

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
    };
*/

function Graphs(){
	console.log(jsonData);
var chartData = jsonData.message.chart;
var labels = Object.keys(chartData);
var data = Object.values(chartData);
var pieChartData = {
    labels: labels,
    datasets: [{
        label: 'Pie Chart',
        data: data,
        backgroundColor: [
            'rgba(255, 99, 132, 0.5)',
            'rgba(54, 162, 235, 0.5)',
            'rgba(255, 206, 86, 0.5)'
        ],
        borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)'
        ],
        borderWidth: 1
    }]
};
var pieCanvas = document.getElementById('pieChart');
var pieChart = new Chart(pieCanvas, {
    type: 'pie',
    data: pieChartData,
    options: {}
});

var orgData = jsonData.message.org;
document.getElementById("orgName").textContent = orgData.Title;
document.getElementById("adminName").textContent = "Admin: " + orgData.Name;
document.getElementById("adminEmail").textContent = "Email: " + orgData.Email;

var profileData = jsonData.message.profile;
document.getElementById("name").textContent = profileData.name;
document.getElementById("email").textContent = profileData.email;
document.getElementById("dob").textContent = profileData.dob;
document.getElementById("gender").textContent = profileData.gender;
document.getElementById("phone").textContent = profileData.phone;
document.getElementById("photo").style.backgroundImage = "url(\"images/User/"+profileData.photo+"\")";
}

    
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
function CurrentElections(){
   
    var electionsList = document.getElementById('current');
    var currentElections = jsonData.message.current;
    currentElections.forEach(function(election) {
      var electionHTML = generateCurrentElectionHTML(election);
      electionsList.appendChild(electionHTML);
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
	console.log(jsonData)
var electionsList = document.getElementById('elections');
var elections = jsonData.message.elections;
elections.forEach(function(election) {
    var electionHTML = generateElectionHTML(election);
    electionsList.appendChild(electionHTML);
});
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

    if (election.candidate.length > 0) {
        election.candidate.forEach(function(candidate) {
            var candidateHTML = generateCandidateHTML(candidate, election.start_date, election.end_date,election.giveVote,election.id);
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

    var apply = document.createElement("section");
    apply.classList.add("apply");
    var candidateLabel = document.createElement("p");
        candidateLabel.classList.add("eleTitle");
        candidateLabel.innerText = "Apply for candidate";
        apply.appendChild(candidateLabel);

    if (new Date(election.start_date) > new Date() && election.isCandidate == "none") {
        var candidateLabel = document.createElement("p");
        candidateLabel.classList.add("candidateLabel");
        candidateLabel.innerText = "Upload logo";
        apply.appendChild(candidateLabel);
        var fileInput = document.createElement('input');
        fileInput.setAttribute('type', 'file');
        fileInput.classList.add('logoInput');
        fileInput.setAttribute('accept', 'image/*');
        apply.appendChild(fileInput);
        var submit = document.createElement("button");
        submit.classList.add("applySubmit");
        submit.innerText = "Apply";
        apply.appendChild(submit);
        
        submit.onclick = function(){
			ApplyCandidate(election.id);
		}
    } else {
        var candidateLabel = document.createElement("p");
        candidateLabel.classList.add("candidateLabel");
        candidateLabel.innerText = "Can't able apply";
        apply.appendChild(candidateLabel);
    }
    canside.appendChild(apply);

    var graphCanvas = document.createElement("canvas");
    graphCanvas.id = "voteGraph";
    graphCanvas.style.backgroundColor = "white";
	graphCanvas.style.marginTop = "5%";
	graphCanvas.style.borderRadius = "10px";
    canside.appendChild(graphCanvas);

    candidatesList.appendChild(canside);
    
    var close = document.createElement("i");
    close.classList.add("fa-solid", "fa-xmark","close");
    candidatesList.appendChild(close);
    
    close.onclick = function() {
    showSection("elections");
};
    generateVoteGraph(election.votes);
}

function generateVoteGraph(votes) {
    var ctx = document.getElementById('voteGraph').getContext('2d');

    // Extract candidate names and vote counts from the votes object
    var candidateNames = Object.keys(votes);
    var voteCounts = Object.values(votes);

    // Create the chart
    var voteChart = new Chart(ctx, {
        type: 'bar',
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
function generateCandidateHTML(candidate,start,end,giveVote,elecId) {
    var candidateDiv = document.createElement("section");
    candidateDiv.classList.add("candidateDiv");

    var img = document.createElement("div");
    img.classList.add("candidateImg");
    img.style.backgroundImage = "url(\"images/User/"+candidate.photo+"\")";
    candidateDiv.appendChild(img);

    var details = document.createElement("div");
    details.classList.add("canDetails");

    var array = [candidate.voter_name,candidate.logo];
    var array2 = ["Name", "Logo"];
    for (let i = 0; i < 2; i++) {
        var text = document.createElement("div");
        text.classList.add("candidateDetailsDiv");
        var label = document.createElement("p");
        label.classList.add("candidateLabel");
        label.innerText = array2[i];
        text.appendChild(label);
        var content = document.createElement("p");
        content.classList.add("candidateContent");
        if (i === 1) {
            content.classList.add("logo");
            content.style.backgroundImage = "url(\"images/User/"+array[i]+"\")";
        } else {
            content.innerText = array[i];
        }
        
        text.appendChild(content);
        details.appendChild(text);
    }
    if ((new Date(start) <= new Date() && new Date(end) >= new Date()) && giveVote == "none") {
            var vote = document.createElement("p");
            vote.classList.add("vote");
             vote.classList.add(candidate.id);
            vote.innerText = "Vote";
            details.appendChild(vote);
            
            vote.onclick = function(){
				GiveVote(elecId,candidate.id);
			}
            
    }
    candidateDiv.appendChild(details);

    return candidateDiv;
}

function ApplyCandidate(eleId) {
	var xhr = new XMLHttpRequest();
    var fileInput = document.getElementsByClassName('logoInput')[0];
    var file = fileInput.files[0];
    var image = file.name;
    uploadImage(file);
    var data = {
		"elecId":eleId,
		"logo":image
	}
    
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            if(response.statusCode == 200){
	            alert(response.message);
                setTimeout(function() {
                    location.reload();
                }, 3000);
	         }
	         else{
				 alert(response.message);
			 }
              
         } 
    };

    xhr.open("POST", "http://localhost:8080/Voting/ApplyForCandidate", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(JSON.stringify(data));
}

function uploadImage(file){
	if (file) {
                var formData = new FormData();
                formData.append('image', file);

                fetch('/Voting/AddImage', {
                    method: 'POST',
                    body: formData
                })
                .then(response => {
                    if (response.ok) {
                        alert('Image uploaded successfully');
                    } else {
                        alert('Failed to upload image');
                    }
                })
                .catch(error => {
                    alert('Error:', error);
                });
            } else {
                alert('No file selected');	
            }
	
}

function GiveVote(eleId,canId) {
    var data = {
		"elecId":eleId,
		"canId":canId
	}
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var response = JSON.parse(this.responseText);
            if(response.statusCode == 200){
	           	alert(response.message);
                setTimeout(function() {
                    location.reload();
                }, 3000);
	         }
	         else{
				 alert(response.message);
			 }
              
         } 
    };

    xhr.open("POST", "http://localhost:8080/Voting/GiveVote", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(JSON.stringify(data));
}

function logout() {
   
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            
            window.location.href = "Authentication.html";
         } 
    };

    xhr.open("POST", "http://localhost:8080/Voting/LogoutServlet", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}
