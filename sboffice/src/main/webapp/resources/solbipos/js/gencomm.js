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

/**
 * 문자열이 빈 문자열인지 체크하여 결과값을 리턴한다.
 * @param str       : 체크할 문자열
 */
function isEmpty(str) {
  if (typeof str == "undefined" || str == null || str == "")
    return true;
  else
    return false;
}
 
/**
 * 문자열이 빈 문자열인지 체크하여 기본 문자열로 리턴한다.
 * @param str           : 체크할 문자열
 * @param defaultStr    : 문자열이 비어있을경우 리턴할 기본 문자열
 */
function nvl(str, defaultStr) {
  if (typeof str == "undefined" || str == null || str == "")
    str = defaultStr;

  return str;
}
