function genEventSingle(error) {
  genEvent(error, $(error.selector + "Error"));
}

function genEvent(error, errorLabel) {
  error.focus(function(){
    errorLabel.hide();
  });
  error.blur(function(){
    if(error.val().length == 0 && errorLabel.text().length > 0) {
      errorLabel.show();
    }
  });
}