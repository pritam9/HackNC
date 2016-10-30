  
     // chrome.tabs.query({'active': true, 'lastFocusedWindow': true}, function(tabs) {
       // var url = tabs[0].url;
	  d = document;
	  // var a = 'amazon.com';
	  // var keyw = 'keywords';
	  
	  // if (url.indexOf(a)!==-1 && url.indexOf(keyw)!==-1)
	  // {
		  // url = url+'&low-price=&high-price=100';
	  // }
	  
	  
	  
      var f = d.createElement('form');
      f.action = url;
      f.method = 'get';
      var i = d.createElement('input');
      i.type = 'hidden';
      i.name = 'url';
      i.value = tabs.url;
      f.appendChild(i);
      d.body.appendChild(f);
      f.submit();
	  // chrome.tabs.update(tabs[0].id, {url: url});
     // });
	
