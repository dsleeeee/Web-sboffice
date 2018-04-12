function genEventSingle(error) {
  genEvent(error, $(error.selector + "Error"));
}

function genEvent(error, errorLabel) {
  error.focus(function() {
    errorLabel.hide();
  });
  error.blur(function() {
    if (error.val().length == 0 && errorLabel.text().length > 0) {
      errorLabel.show();
    }
  });
}

function getDate(obj) {
  return obj.value.getFullYear().toString()
      + setZero((obj.value.getMonth() + 1).toString(), 2)
      + setZero(obj.value.getDate().toString(), 2);
}

function getTime(obj) {
  return setZero(obj.value.getHours().toString(), 2)
      + setZero(obj.value.getMinutes().toString(), 2);
}

function setZero(s, l) {
  var t = l - s.length;
  var r = "";
  for (var i = 0; i < t; i++) {
    r += "0";
  }
  return r += s;
}
