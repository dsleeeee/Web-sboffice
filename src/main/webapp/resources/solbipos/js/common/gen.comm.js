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

function setGridCRUD(grid) {
  var arr = new Array();
  
  for(var i=0; i<grid.collectionView.itemsEdited.length; i++){
    grid.collectionView.itemsEdited[i].status = "U";
    arr.push(grid.collectionView.itemsEdited[i]);
  }
  for(var i=0; i<grid.collectionView.itemsAdded.length; i++){
    grid.collectionView.itemsAdded[i].status = "I";
    arr.push(grid.collectionView.itemsAdded[i]);
  }
  for(var i=0; i<grid.collectionView.itemsRemoved.length; i++){
    grid.collectionView.itemsRemoved[i].status = "D";
    arr.push(grid.collectionView.itemsRemoved[i]);
  }
  
  return arr;
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

/**
 * 문자열이 숫자형인지 아닌지 체크하여 결과값을 리턴한다.
 * @param value : 체크할 문자열
 *
 *  isNan 함수는 입력값이 숫자인지 아닌지 확인하는데 입력값이 숫자이면 false를 입력값이 숫자가 아니면 true를 반환
 *  또한 입력값이 공백문자이거나 빈값이어도 false를 반환함
 */
function isNumber(value) {
    value = String(value);
    if ( value.indexOf(" ") != -1 || value == "" ) {
        return false;
    } else if ( isNaN(value) ) {
        return false;
    } else {
        return true;
    }
}
