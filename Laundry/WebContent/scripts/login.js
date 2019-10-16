//(function() {
//
//    /**
//     * Variables
//     */
//    var user_id = '1111';
//    var user_fullname = 'John';
//  
//    /**
//     * Initialize major event handlers
//     */
//    function init() {
//      // register event listeners
//      document.querySelector('#login-form-btn').addEventListener('click', onSessionInvalid);
//      document.querySelector('#login-btn').addEventListener('click', login);
//      document.querySelector('#register-form-btn').addEventListener('click', showRegisterForm);
//      document.querySelector('#register-btn').addEventListener('click', register);
//      document.querySelector('#nearby-btn').addEventListener('click', loadNearbyItems);
//      document.querySelector('#fav-btn').addEventListener('click', loadFavoriteItems);
//      document.querySelector('#recommend-btn').addEventListener('click', loadRecommendedItems);
//      validateSession();
//      // onSessionValid({"user_id":"1111","name":"John Smith","status":"OK"});
//    }
//  
//    /**
//     * Session
//     */
//    function validateSession() {
//      onSessionInvalid();
//      // The request parameters
//      var url = './login';
//      var req = JSON.stringify({});
//  
//      // display loading message
//      showLoadingMessage('Validating session...');
//  
//      // make AJAX call
//      ajax('GET', url, req,
//        // session is still valid
//        function(res) {
//          var result = JSON.parse(res);
//  
//          if (result.status === 'OK') {
//            onSessionValid(result);
//          }
//        });
//    }
//  
//    function onSessionValid(result) {
//      user_id = result.user_id;
//      user_fullname = result.name;
//  
//      var loginForm = document.querySelector('#login-form');
//      var registerForm = document.querySelector('#register-form');
//      var itemNav = document.querySelector('#item-nav');
//      var itemList = document.querySelector('#item-list');
//      var avatar = document.querySelector('#avatar');
//      var welcomeMsg = document.querySelector('#welcome-msg');
//      var logoutBtn = document.querySelector('#logout-link');
//  
//      welcomeMsg.innerHTML = 'Welcome, ' + user_fullname;
//  
//      showElement(itemNav);
//      showElement(itemList);
//      showElement(avatar);
//      showElement(welcomeMsg);
//      showElement(logoutBtn, 'inline-block');
//      hideElement(loginForm);
//      hideElement(registerForm);
//  
//      initGeoLocation();
//    }
//  
//    function onSessionInvalid() {
//      var loginForm = document.querySelector('#login-form');
//      var registerForm = document.querySelector('#register-form');
//      var itemNav = document.querySelector('#item-nav');
//      var itemList = document.querySelector('#item-list');
//      var avatar = document.querySelector('#avatar');
//      var welcomeMsg = document.querySelector('#welcome-msg');
//      var logoutBtn = document.querySelector('#logout-link');
//  
//      hideElement(itemNav);
//      hideElement(itemList);
//      hideElement(avatar);
//      hideElement(logoutBtn);
//      hideElement(welcomeMsg);
//      hideElement(registerForm);
//  
//      clearLoginError();
//      showElement(loginForm);
//    }
//  
//    function hideElement(element) {
//      element.style.display = 'none';
//    }
//  
//    function showElement(element, style) {
//      var displayStyle = style ? style : 'block';
//      element.style.display = displayStyle;
//    }
//    
//    function showRegisterForm() {
//      var loginForm = document.querySelector('#login-form');
//      var registerForm = document.querySelector('#register-form');
//      var itemNav = document.querySelector('#item-nav');
//      var itemList = document.querySelector('#item-list');
//      var avatar = document.querySelector('#avatar');
//      var welcomeMsg = document.querySelector('#welcome-msg');
//      var logoutBtn = document.querySelector('#logout-link');
//  
//      hideElement(itemNav);
//      hideElement(itemList);
//      hideElement(avatar);
//      hideElement(logoutBtn);
//      hideElement(welcomeMsg);
//      hideElement(loginForm);
//      
//      clearRegisterResult();
//      showElement(registerForm);
//    }  
//  
//    // -----------------------------------
//    // Login
//    // -----------------------------------
//  
//    function login() {
//      var username = document.querySelector('#username').value;
//      var password = document.querySelector('#password').value;
//      password = md5(username + md5(password));
//  
//      // The request parameters
//      var url = './login';
//      var req = JSON.stringify({
//        user_id : username,
//        password : password,
//      });
//  
//      ajax('POST', url, req,
//        // successful callback
//        function(res) {
//          var result = JSON.parse(res);
//  
//          // successfully logged in
//          if (result.status === 'OK') {
//            onSessionValid(result);
//          }
//        },
//  
//        // error
//        function() {
//          showLoginError();
//        },
//        true);
//    }
//  
//    function showLoginError() {
//      document.querySelector('#login-error').innerHTML = 'Invalid username or password';
//    }
//  
//    function clearLoginError() {
//      document.querySelector('#login-error').innerHTML = '';
//    }
//  
//    // -----------------------------------
//    // Register
//    // -----------------------------------
//  
//    function register() {
//      var username = document.querySelector('#register-username').value;
//      var password = document.querySelector('#register-password').value;
//      var firstName = document.querySelector('#register-first-name').value;
//      var lastName = document.querySelector('#register-last-name').value;
//      
//      if (username === "" || password == "" || firstName === "" || lastName === "") {
//          showRegisterResult('Please fill in all fields');
//          return
//      }
//      
//      if (username.match(/^[a-z0-9_]+$/) === null) {
//          showRegisterResult('Invalid username');
//          return
//      }
//      
//      password = md5(username + md5(password));
//  
//      // The request parameters
//      var url = './register';
//      var req = JSON.stringify({
//        user_id : username,
//        password : password,
//        first_name: firstName,
//        last_name: lastName,
//      });
//  
//      ajax('POST', url, req,
//        // successful callback
//        function(res) {
//          var result = JSON.parse(res);
//  
//          // successfully logged in
//          if (result.status === 'OK') {
//              showRegisterResult('Succesfully registered');
//          } else {
//              showRegisterResult('User already existed');
//          }
//        },
//  
//        // error
//        function() {
//            showRegisterResult('Failed to register');
//        },
//        true);
//    }
//  
//    function showRegisterResult(registerMessage) {
//      document.querySelector('#register-result').innerHTML = registerMessage;
//    }
//  
//    function clearRegisterResult() {
//      document.querySelector('#register-result').innerHTML = '';
//    }
//  
//    init();
//  
//  })();

<script type="login.js" >
$("#login_btn").click(function() {
    console.log('login btn clicked');
    $("#registerForm").hide();
    $("#loginForm").show();


});

$("#register_btn").click(function() {
    console.log('regsiter btn clicked');
    $("#loginForm").hide();
    $("#registerForm").show();


});
//  