/**
 * Function to check user authentication by sending a POST request to the server
 */

document.addEventListener('DOMContentLoaded', function() {
  document.querySelectorAll('ul li a').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
      e.preventDefault();
      const targetId = this.getAttribute('href').substring(1);
      const targetSection = document.getElementById(targetId);
      if (targetSection) {
        targetSection.scrollIntoView({ behavior: 'smooth' });
      }
    });
  });
});


function login() {
    var email = document.getElementById("loginEmail").value;
    var password = document.getElementById("loginPassword").value;
   
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() { 
		if (this.readyState == 4 && this.status == 200) {
			console.log(this.responseText)
                var response = JSON.parse(this.responseText);
                if (response.statusCode === 200) {
                    if(response.role === "Admin"){
						window.location.href = 'Admin.html';
					}
					else{
						window.location.href = 'Voter.html';
					}
                    
                } else {
                    // Login failed, display error message
                    alert(response.message);
                }
            } else {
                // Error handling for non-200 status code
               // console.error('Error: ' + xhr.status);
            }
    };

    xhr.open("POST", "http://localhost:8080/Voting/Login", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    var data = JSON.stringify({ "email": email, "password": password });
    xhr.send(data);
}

function signUp(){
	  console.log("get");
	  var isNew = document.getElementById("isNewOrg").value;
		var obj = {};
		obj["username"] = document.getElementById("name").value;
    	obj["email"] = document.getElementById("email").value;
    	obj["password"] = document.getElementById("password").value;
    	obj["dob"] = document.getElementById("dob").value;
    	obj["gender"] = document.getElementById("gender").value;
    	obj["phone"] = document.getElementById("phone").value;
    	obj["organization"] = document.getElementById("org").value;
    	var file = document.getElementById('photo').files[0];
        obj["photo"] = file.name;
        uploadImage(file);
		if(isNew == "Yes"){
			var xhr = new XMLHttpRequest();
	    	xhr.onreadystatechange = function() {
		        if (this.readyState == 4 && this.status == 200) {
		            var answer = this.responseText;
		            var json = JSON.parse(answer);
		            console.log(json);
		            if(json["statusCode"] == 200){
						window.location.href = 'Admin.html';
					}
					alert(json["message"]);
		        }
		    };
	
	    
	    	xhr.open("POST", "http://localhost:8080/Voting/SignUpAdmin", true);
	    	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
	    	xhr.send(JSON.stringify(obj));
		}
		else{
			var xhr = new XMLHttpRequest();
	    	xhr.onreadystatechange = function() {
		        if (this.readyState == 4 && this.status == 200) {
		            var answer = this.responseText;
		            var json = JSON.parse(answer);
		            if(json["statusCode"] == 200){
						window.location.href = 'Voter.html';
					}
					alert(json["message"]);
		        }
	    	};

    
		    xhr.open("POST", "http://localhost:8080/Voting/SignUpUser", true);
		    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
		    xhr.send(JSON.stringify(obj));
		}
   		
}
function showLogin() {
    document.getElementById("loginPage").style.display = "block";
    document.getElementById("signUpPage").style.display = "none";
    var whole = document.getElementById("whole");
    whole.style.position = "fixed";
    whole.style.pointerEvents = "none";
    whole.style.backgroundColor = "rgba(0, 0, 0, 0.1)";
    whole.style.backdropFilter = "blur(20px)";
}
function closePage() {
    document.getElementById("loginPage").style.display = "none";
    document.getElementById("signUpPage").style.display = "none";
    document.getElementById("register").style.display = "none";
    var whole = document.getElementById("whole");
    whole.style.position = "";
    whole.style.pointerEvents = "";
    whole.style.backgroundColor = "";
    whole.style.backdropFilter = "";
}
function showSignUp() {
    document.getElementById("signUpPage").style.display = "block";
    document.getElementById("loginPage").style.display = "none";
    var whole = document.getElementById("whole");
    whole.style.position = "fixed";
    whole.style.pointerEvents = "none";
    whole.style.backgroundColor = "rgba(0, 0, 0, 0.1)";
    whole.style.backdropFilter = "blur(20px)";
}
function continueRegister(event) {
    event.preventDefault();
    document.getElementById("signUpPage").style.display = "none";
    document.getElementById("register").style.display = "block";
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