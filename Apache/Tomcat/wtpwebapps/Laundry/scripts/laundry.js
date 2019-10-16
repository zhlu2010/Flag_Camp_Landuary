(function() {

	/**
	   * Initialize major event handlers
	   */
	  function init() {
	    // register event listeners

	    document.querySelector('#book-a-laundry-btn').addEventListener('click', BookALaundry);
	    document.querySelector('#my-laundry-room-btn').addEventListener('click', MyLaundryRoom);
		document.querySelector('.book_btn').addEventListener('click', clickbook);   
	  }

  // -----------------------------------
  // Helper Functions
  // -----------------------------------


    function clickbook() {
    	var btns = document.querySelectorAll('.main-nav-btn');

        // deactivate all navigation buttons
        for (var i = 0; i < btns.length; i++) {
          btns[i].className = btns[i].className.replace(/\bactive\b/, '');
        }

        // active the one that has id = btnId
        var btn = document.querySelector('#' + btnId);
        btn.className += ' active';
        
        
        
    	 var btn = document.querySelector(".book_btn");
    	  	 
    	 
    		 var flag = true; 
    		 btn.innerHTML = (flag = !flag) ? 'BOOK' : 'BOOKED';
             btn.style.background = "red";
    	     
    	 
      }

  /**
   * A helper function that makes a navigation button active
   *
   * @param btnId - The id of the navigation button
   */
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
	  

  
  
  //my laundry room
  function MyLaundryRoom(){
	  activeBtn('my-laundry-room-btn');
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
	    var item_id = item.item_id;

	    // create the <div> tag and specify the id and class attributes
	    var machine = $create('div', {
	      id: 'machine-' + item_id,
	      className: 'machine'
	    });

	    // set the data attribute ex. <li data-item_id="G5vYZ4kxGQVCR" data-favorite="true">
	    machine.dataset.item_id = item_id;
	    
	    // item image
	    var machinephoto = $create('div',{className:machine_phone});
	   	    
	    if (item.image_url) {
	      machinephoto.appendChild($create('img', { src: item.image_url }));
	    } else {
	      machinephoto.appendChild($create('img', {
	        src: 'https://s3-media3.fl.yelpcdn.com/bphoto/EmBj4qlyQaGd9Q4oXEhEeQ/ms.jpg'
	      }));
	    }    
	    machine.appendChild(machinephoto);
	    
	    // status_content
	    var status = $create('div', {className: status_content});
	    
	    //book button
	    var book_btn= $create('button',{className: 'book_btn'});
	    book_btn.innerHTML = 'BOOK';
	    status.appendChild(book_btn);
	    
	    // machine number
	    var machine_number = $create('span', {
	      className: 'machine_number',
	    });    
	    machine_number.innerHTML = 'Machine Number:'+ item_id;
	    status.appendChild(machine_number);
	 
	    // book status
	    var book_status = $create('span', {
	      className: 'book_staus',
	    });    
	    machine_number.innerHTML = 'Status:'+ item.status;
	    status.appendChild(book_status);
	  
	    // waiting_time
	    var waiting_time = $create('span', {
	      className: 'waiting_time',
	    });    
	    machine_number.innerHTML = 'Remaining Time:'+ item.remainingtime;
	    status.appendChild(waiting_time);

    machine.appendChild(status);
    itemList.appendChild(machine);
  }

  init();//函数入口

})();
