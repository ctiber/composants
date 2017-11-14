// match pattern for the URLs to redirect
var pattern = "http://www.lirmm.fr/*";

// redirect function
// returns an object with a property `redirectURL`
// set to the new URL
function redirect(requestDetails) {
  console.log("Redirecting: " + requestDetails.url);
  return {
    redirectUrl: "http://image.shutterstock.com/z/stock-vector-vector-skull-danger-sign-120892354.jpg"
  };
}

// add the listener,
// passing the filter argument and "blocking"
chrome.webRequest.onBeforeRequest.addListener(
  redirect,
  {urls:[pattern], types:["image"]},
  ["blocking"]
);
