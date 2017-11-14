chrome.runtime.onMessage.addListener(function(m) {
	alert("Navigation to: " + m.url + "\n Transition type: "+m.type);
});
