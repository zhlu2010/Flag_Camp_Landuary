(function() {
	/**
	   * Variables
	   */
	  var user_id = 'John@laioffer.com';


	/**
	   * Initialize major event handlers
	   */
	  function init() {
	    // register event listeners

	    document.querySelector('#book-a-laundry-btn').addEventListener('click', BookALaundry);
	    document.querySelector('#my-laundry-room-btn').addEventListener('click', MyLaundryRoom);
	    
	  }

  // -----------------------------------
  // Helper Functions
  // -----------------------------------

  /**
   * A helper function that makes a navigation button active
   *
   * @param btnId - The id of the navigation button
   **/
   
  function activeBtn(btnId) {
    var btns = document.querySelectorAll('.main-nav-btn');

    // deactivate all navigation buttons
    for (var i = 0; i < btns.length; i++) {
      btns[i].className = btns[i].className.replace(/\bactive\b/, '');
    }

    // active the one that has id = btnId
    var btn = document.querySelector('#' + btnId);
    btn.className += ' active';
  }
  
  
  function showLoadingMessage(msg) {
	    var itemList = document.querySelector('#machine_list');
	    itemList.innerHTML = '<p class="notice"><i class="fa fa-spinner fa-spin"></i> ' +
	      msg + '</p>';
	  }

	  function showWarningMessage(msg) {
	    var itemList = document.querySelector('#machine_list');
	    itemList.innerHTML = '<p class="notice"><i class="fa fa-exclamation-triangle"></i> ' +
	      msg + '</p>';
	  }

	  function showErrorMessage(msg) {
	    var itemList = document.querySelector('#machine_list');
	    itemList.innerHTML = '<p class="notice"><i class="fa fa-exclamation-circle"></i> ' +
	      msg + '</p>';
	  }
  
  /**
   * A helper function that creates a DOM element <tag options...>
   * @param tag
   * @param options
   * @returns {Element}
   */
  function $create(tag, options) {
    var element = document.createElement(tag);
    for (var key in options) {
      if (options.hasOwnProperty(key)) {
        element[key] = options[key];
      }
    }
    return element;
  }

  /**
   * AJAX helper
   *
   * @param method - GET|POST|PUT|DELETE
   * @param url - API end point
   * @param data - request payload data
   * @param successCallback - Successful callback function
   * @param errorCallback - Error callback function
   */
  function ajax(method, url, data, successCallback, errorCallback) {
    var xhr = new XMLHttpRequest();

    xhr.open(method, url, true);
    
    xhr.onload = function() {
      if (xhr.status === 200) {
        successCallback(xhr.responseText);
      } 
     else if (xhr.status == 403) { 
    	errorCallback(); 
     }else {
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
  
  
 // BOOK A LAUNDRY
  function BookALaundry(){
	  activeBtn('book-a-laundry-btn');
	  
	 // The request parameters
	    var url = './allMachineStatus';
	    var data = null;
	    
	    showLoadingMessage('Loading washing machine...');
	    
	    // make AJAX call
	    ajax('GET', url, data,
	      // successful callback
	      function(res) {
	        var items = JSON.parse(res);
	        if (!items || items.length === 0) {
	          showWarningMessage('No washing machine.');
	        } else {
	          listItems(items);
	        }
	      },
	      // failed callback
	      function() {
	        showErrorMessage('Cannot load washing machine.');
	      }
	    );
	  
  }
	  

  function changeBtnColor(machineId) {        
 	 var btn = document.querySelector('#btn'+machineId);
 		btn.innerHTML = 'BOOKED';
 		btn.style.backgroundColor = "#F5D840";
 		btn.style.color = "#323F75";
 		
 }    
  
  function changeMachineState(machine_Id){
	    //var machine = document.querySelector('#machine-'+ machine_Id);
	    var bookbtn = document.querySelector('#btn'+ machine_Id);
	   // var bookstatus =machine.dataset.state;
	    	   
	    // request parameters -ex. bookLaundry?user_id=John@laioffer.com&machineId=5
	    var url = './bookLaundry' + '?' + 'user_id='+ user_id +'&machineId=' + machine_Id;	    
	    var data = null;

	    // make AJAX call
	    ajax('GET', url, data,
	      // successful callback
	        function(res) {
	        	var result = JSON.parse(res);
	        	console.log('1');
	        	if (result.status === 'OK' || result.result === 'SUCCESS') {
	        		console.log('2');
	        		changeBtnColor(machine_Id);
	        		//bookbtn.className = 'booked_btn';
	        }
	    }
	    );
  }
  
 
  
  //my laundry room
  function MyLaundryRoom(){
	  activeBtn('my-laundry-room-btn');
	  var itemList = document.querySelector('#machine_list');
	  itemList.innerHTML = 'hhhhh';
  }
  // -------------------------------------
  // Create item list
  // -------------------------------------

  /**
   * List washing machine base on the data received
   *
   * @param items - An array of item JSON objects
   */
  function listItems(items) {
    var itemList = document.querySelector('#machine_list');
    itemList.innerHTML = ''; // clear current results

    for (var i = 0; i < items.length; i++) {
      addItem(itemList, items[i]);
    }
  }

  function addItem(itemList, item) {
	// create the <div> tag and specify the id and class attributes
	  var machine_Id = item.machineId;	  
	  var machine_state = item.state;
	  var machine = $create('div', {
	      id: 'machine-' + machine_Id,
	      className: 'machine'
	    });
	  
	  // set the data attribute 
	  machine.dataset.machine_id = machine_Id;
	  machine.dataset.state = machine_state;
	 
	  // item image
	    var machinephoto = $create('div');
	   	    
	    if (item.image_url) {
	      machinephoto.appendChild($create('img', { src: item.image_url }));
	    } else {
	      machinephoto.appendChild($create('img', {
	    	  src:"img/machine.png"
	      }));
	    }
	    machine.appendChild(machinephoto);
	  
	    //machine-info
	   var status = $create('div');
	    
	    //book button
	   if (machine_state==0){
		   var block0 = $create('br');
		    var book_btn= $create('button',{id: 'btn'+ machine_Id, className: 'book_btn'});
		    book_btn.innerHTML = 'BOOK';
		    
		    
		    book_btn.onclick = function() {
		    	changeMachineState(machine_Id);
		    	//changeBtnColor(machine_Id);
		    	BookALaundry();
		      };
	   }
	   
	   else if (machine_state==2){
		   var block0 = $create('br');
		    var book_btn= $create('button',{id: 'btn'+ machine_Id, className: 'book_btn'});
		    book_btn.innerHTML = 'Book';
		    book_btn.style.background = 'grey';
		    		    
	   }
	   else if (machine_state==1){
		   var block0 = $create('br');
		    var book_btn= $create('button',{id: 'btn'+ machine_Id, className: 'book_btn'});
		    book_btn.innerHTML = 'Booked';
		    book_btn.style.backgroundColor = "#F5D840";
	 		book_btn.style.color = "#323F75";
	   }
	    
	    status.appendChild(book_btn);
	    
	    var block1 = $create('br');
	    // machine number
	    var machine_number = $create('span');    
	    machine_number.appendChild(block0);
	    machine_number.innerHTML = 'Machine '+ machine_Id;
	    machine_number.appendChild(block1);
	    status.appendChild(machine_number);
	    
	    var block2 = $create('br');
	    
	    // book status
	    var book_status = $create('span');
	    if (machine_state==1) book_status.innerHTML = 'Status: Booked';
	    if (machine_state==0) book_status.innerHTML = 'Status: Available';
	    if (machine_state==2) book_status.innerHTML = 'Status: Running';
	    book_status.appendChild(block2);
	    status.appendChild(book_status);
	        
	    // waiting_time
	    var waiting_time = $create('span', {
	      className: 'waiting_time',
	    });  
	    if (machine_state==1) waiting_time.innerHTML = 'Book Session Expires in: '+ item.timeLeft + ' min';
	    if (machine_state==0) waiting_time.innerHTML = '';
	    if (machine_state==2) waiting_time.innerHTML = 'Estimate Waiting Time: '+ item.timeLeft + ' min';

	    status.appendChild(waiting_time);
	   
	    
    machine.appendChild(status);
    itemList.appendChild(machine);
    
  }

  init();//函数入口

})();
