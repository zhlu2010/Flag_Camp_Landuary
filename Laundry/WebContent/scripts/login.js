(function() {
	function init() {
		document.querySelector('#login-form-btn').addEventListener('click',
				showLoginForm);
		document.querySelector('#register-form-btn').addEventListener('click',
				showRegisterForm);
		document.querySelector('#login-btn').addEventListener('click', login);
		document.querySelector('#register-btn').addEventListener('click',
				register);

		validateSession();
	}

	function validateSession() {
		onSessionInvalid();
		// The request parameters
		 var url = './login';
		 var req = JSON.stringify({});
		
		 // display loading message
//		 showLoadingMessage('Validating session...');
		
		 // make AJAX call
		 ajax('GET', url, req,
		 // session is still valid
		function(res) {
			 var result = JSON.parse(res);
		
			 if (result.status === 'OK') {
				 if(result.is_admin) {
					 
				 } else {
					 document.location.href = './LaundryRoom.html';
				 }
			 }
		 });
	}

	function onSessionInvalid() {
		showLoginForm();
	}

	function showLoginForm() {
		var loginForm = document.querySelector('#login-form');
		var registerForm = document.querySelector('#register-form');

		showElement(loginForm);
		hideElement(registerForm);
	}

	function showRegisterForm() {
		var loginForm = document.querySelector('#login-form');
		var registerForm = document.querySelector('#register-form');

		showElement(registerForm);
		hideElement(loginForm);
	}

	function login() {
		var username = document.querySelector('#username').value;
		var password = document.querySelector('#password').value;
		
		var url = './login';
		var req = JSON.stringify({
		      user_id : username,
		      password : password,
		    });
		ajax('POST', url, req,       // successful callback
			      function(res) {
	        var result = JSON.parse(res);

	        // successfully logged in
	        if (result.status === 'OK') {
//	          onSessionValid(result);
				 if(result.is_admin) {
					 
				 } else {
					 document.location.href = './LaundryRoom.html';
				 }
	        }
	      },

	      // error
	      function() {
	        showLoginError();
	      }, 
	      true);
	}

	function register() {
		var username = document.querySelector('#register-username').value;
		var password = document.querySelector('#register-password').value;
		var firstName = document.querySelector('#register-first-name').value;
		var lastName = document.querySelector('#register-last-name').value;
		var email = document.querySelector('#register-email').value;

		if (username === "" || password == "" || firstName === ""
				|| lastName === "" || email === "") {
			showRegisterResult('Please fill in all fields');
			return;
		}

		if (username.match(/^[a-z0-9_]+$/) === null) {
			showRegisterResult('Invalid username');
			return;
		}

		var url = './register';
		var req = JSON.stringify({
			user_id : username,
			password : password,
			first_name : firstName,
			last_name : lastName,
			email_address : email,
		});

		ajax('POST', url, req,
		// successful callback
		function(res) {
			var result = JSON.parse(res);

			// successfully logged in
			if (result.status === 'OK') {
				showRegisterResult('Succesfully registered');
				showLoginForm();
			} else {
				showRegisterResult('User already existed');
			}
		},

		// error
		function() {
			showRegisterResult('Failed to register');
		}, true);
	}
	
	function showLoginError() {
		alert('Invalid username or password');
	}

	function showRegisterResult(registerMessage) {
//		document.querySelector('#register-result').innerHTML = registerMessage;
		alert(registerMessage);
	}

	function clearRegisterResult() {
//		document.querySelector('#register-result').innerHTML = '';
	}

	function ajax(method, url, data, successCallback, errorCallback) {
		var xhr = new XMLHttpRequest();

		xhr.open(method, url, true);
		xhr.onload = function() {
			if (xhr.status === 200) {
				successCallback(xhr.responseText);
			} else {
				errorCallback();
			}
		};

		xhr.onerror = function() {
			console.error("The request couldn't be completed.");
			errorCallback();
		};

		if (data === null) {
			xhr.send();
		} else {
			xhr.setRequestHeader("Content-Type",
					"application/json;charset=utf-8");
			xhr.send(data);
		}
	}

	function hideElement(element) {
		element.style.display = 'none';
	}

	function showElement(element, style) {
		var displayStyle = style ? style : 'block';
		element.style.display = displayStyle;
	}

	init();

})();