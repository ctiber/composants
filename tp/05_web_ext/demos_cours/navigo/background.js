function logging(details) {
  console.log("Navigation to: " + details.url);
  console.log("Transition type: "+details.transitionType);
  chrome.tabs.sendMessage(details.tabId,{"url":details.url,"type":details.transitionType});
}

chrome.webNavigation.onCommitted.addListener(logging);
alert(OS.File);
