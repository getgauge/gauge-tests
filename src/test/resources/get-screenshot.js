"use strict";
var page = require('webpage').create(),
  system = require('system'),
  url, stepText;

if (system.args.length !== 3) {
    console.log('Usage: get-screenshot.js <url> <step_text>');
    phantom.exit(1);
}

url = system.args[1];
stepText = system.args[2];

page.onConsoleMessage = function(msg) {
    console.log(msg);
};

page.open(url, function(status) {
    if (status === "success") {
        page.evaluate(function(s) {
            [].forEach.call(document.getElementsByClassName("step-txt"), function(element) {
                if(element.innerText.indexOf(s)>=0) {
                    console.log(element.nextElementSibling.getElementsByClassName('screenshot-thumbnail')[0].getAttribute('src'));
                }
            });
        }, stepText);
        phantom.exit(0);
    } else {
      console.log("failed");
      phantom.exit(1);
    }
});