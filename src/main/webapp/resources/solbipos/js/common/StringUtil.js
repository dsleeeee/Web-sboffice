/**
 * Byte 자리수 리턴
 * @returns {number}
 */
String.prototype.getByteLength = function() {
    var i, c;
    for(var size=i=0;c=this.charCodeAt(i++);size+=c>>11?2:c>>7?2:1);
    return size;
};

/**
 * 오라클용 한글 바이트수 리턴
 * 오라클에서 한글을 3바이트로 인식해서 한글인 경우 3바이트로 byte length 함수가 필요하여 추가
 */
String.prototype.getByteLengthForOracle = function() {
  var i, c;
  for(var size=i=0;c=this.charCodeAt(i++);size+=c>>11?3:c>>7?2:1);
  return size;
};

/**
 * 가운데 글자 채우기
 * @param padStr
 * @param padLen
 * @returns {*}
 */
String.prototype.centerPad = function(padStr, padLen) {
    if ( parseInt(padLen) + 1 <= this.getByteLength() ) {
        return this;
    } else {
        var calcLen = padLen - this.getByteLength();
        var leftPad = Math.ceil(calcLen / 2);
        var rightPad = calcLen - rightPad + 1;
        return Array(leftPad).join(padStr).concat(this,Array(rightPad+1).join(padStr));
    }
};

/***
 * 문자열 왼쪽 문자열 채우기
 * @param padStr
 * @param padLen
 * @returns {*}
 */
String.prototype.leftPad = function(padStr, padLen) {
  if ( parseInt(padLen) + 1 <= this.getByteLength() ) {
    return this;
  } else {
    var leftPad = padLen - this.getByteLength() + 1;
    return Array(leftPad).join(padStr).concat(this);
  }
};

/**
 * 문자열 오른쪽 문자열 채우기
 * @param padStr
 * @param padLen
 * @returns {*}
 */
String.prototype.rightPad = function(padStr, padLen) {
    if ( parseInt(padLen) + 1 <= this.getByteLength() ) {
        return this;
    } else {
        var rightPad = padLen - this.getByteLength() + 1;
        return this.concat(Array(rightPad).join(padStr));
    }
};

/**
 * padding 추가
 * @param padType
 * @param padStr
 * @param padLen
 * @returns {*}
 */
String.prototype.setPadding = function(padType, padStr, padLen) {
    if ( padType === "C" ) {
        return this.centerPad(padStr, padLen);
    } else if ( padType === "L" ) {
        return this.leftPad(padStr, padLen);
    } else if ( padType === "R" ) {
        return this.rightPad(padStr, padLen);
    }
};

/**
 * 문자열 자르기
 * @param byteLen
 * @returns {*}
 */
String.prototype.splitByteLen = function(byteLen) {
    var resultArr = new Array();
    var rIndex = 0;
    var valueByteLen = this.getByteLength();
    var valueLen = this.length;
    var bIndex = valueLen - 1;
    var str = "";

    if (valueByteLen <= byteLen) {
        return resultArr[0] = this;
    } else {
        for (var i = 0; i < valueLen; i++) {
            str += this.charAt(i);
            if ((str + this.charAt(i + 1)).getByteLength() > byteLen) {
                resultArr[rIndex++] = str;
                str = "";
            }
            if (i === bIndex) {
                resultArr[rIndex++] = str;
            }
        }
        return resultArr;
    }
};

/**
 * String.trim()
 * - 공백제거
 */
String.prototype.trim = function() {
  return this.replace(/^\s+|\s+$/g, "");
}
/**
 * String.replaceAll(strSearch, strReplace)
 * - 문자열치환
 */
String.prototype.replaceAll = function(strSearch, strReplace) {
  var returnValue = this;
  while (returnValue.indexOf(strSearch) != -1) {
    returnValue = returnValue.replace(strSearch, strReplace);
  }
  return returnValue;
}
/**
 * String.startsWith(prefix)
 * - 문자열이 startStc로 시작하는지여부
 */
String.prototype.startsWith = function(prefix) {
  if (this.substring(0, prefix.length) == prefix) return true;
  else return false;
}
/**
 * Array.search(value)
 * - array search value return index(없으면 -1)
 *
 * @param value - 배열값
 * @return
 */
Array.prototype.search = function(value) {
  for (var i = 0; i < this.length; i++)
    if (this[i] == value) return i;
  return -1;
}
/**
 * Array.replaceValue(strSearch, strReplace)
 * - 배열원소값치환
 */
Array.prototype.replace= function(strSearch, strReplace) {
  for (var i = 0; i < this.length; i++) {
    if (typeof(this[i]) == "object" && this[i].length > 0)
      this[i].replace(strSearch, strReplace);
    else if (this[i] == strSearch)
      this[i] = strReplace;
  }
}
/**
 * Array.replaceAll(strSearch, strReplace)
 * - 배열원소문자열치환
 */
Array.prototype.replaceAll = function(strSearch, strReplace) {
  for (var i = 0; i < this.length; i++) {
    if (typeof(this[i]) == "object" && this[i].length > 0)
      this[i].replaceAll(strSearch, strReplace);
    else
      this[i] = new String(this[i]).replaceAll(strSearch, strReplace);
  }
}
/**
 * keypressNumber()
 * - onkeypress 이벤트(숫자만입력)
 *
 * @return
 */
function keypressNumber() {
  if ((event.keyCode < 48) || (event.keyCode > 57)) event.returnValue = false;
}

/**
 * keypressDotNumber()
 * - onkeypress 이벤트(숫자/dot만입력)
 *
 * @return
 */
function keypressDotNumber() {
  if (((event.keyCode < 48) || (57 < event.keyCode)) && (46 != event.keyCode)) event.returnValue=false;
}
/**
 * keyupNextTab(input, next, inputlen)
 * - onkeyup 이벤트(입력값체크후다음입력필드로이동)
 *
 * @param input
 * @param next
 * @param inputlen
 * @return
 */
function keyupNextTab(input, next, inputlen) {
  if (input.value.length == inputlen) {
    next.focus();
    return;
  }
}

/**
 * 콤마 추가
 * @param str
 * @returns {string}
 */
function addComma(str) {
  var regexp = /\B(?=(\d{3})+(?!\d))/g;
  return str.toString().replace(regexp, ',');
}

/**
 * 콤마 제거
 * @param str
 * @returns {*}
 */
function removeComma(str){
  return str.replace(/,/g , '');
}

/************************* S : CHECK VALUE *************************/

function isNull(str) {
  var returnVal = false;
  if(str === null || str === undefined || str === "") {
    return true;
  }
  return returnVal;
}



/**
 * chkByte(str)
 * - 문자열바이트체크
 *
 * @param str
 * @return
 */
function chkByte(str) {
  var i, intLength = 0;
  for (i = 0; i < str.length; i++) {
    val = escape(str.charAt(i)).length;
    if (val ==  6) intLength++;
    intLength++;
  }
  return intLength;
}
/**
 * isNumeric(str)
 * - 숫자체크
 *
 * @param str
 * @return
 */
function isNumeric(str) {
  var regExp = /\D/i;
  if (regExp.test(str)) return false;
  else return true;
}
/**
 * checkNum(obj)
 * - 숫자체크(바로 위에있는게 안돼서 090311  추가)
 */
function checkNum(obj)
{
  var kc = event.keyCode;
  if((kc < 48 || kc > 57) && (kc < 96 || kc > 105) && (kc != 8 && kc != 9))

  {
    // alert('숫자만 입력할 수 있습니다.');
    obj.focus();
    window.event.returnValue = false;
  }
}
/**
 * isAlpha(str)
 * - 영문체크
 *
 * @param str
 * @return
 */
function isAlpha(str) {
  var regExp = /[^a-z]/i;
  if (regExp.test(str)) return false;
  else return true;
}
/**
 * isAlphaNumeric(str)
 * - 영문/숫자체크
 *
 * @param str
 * @return
 */
function isAlphaNumeric(str) {
  var regExp = /[^a-z0-9]/i;
  if (regExp.test(str)) return false;
  else return true;
}
/**
 * isHangul(str)
 * - 한글체크(문자열에한글이외의값이있다면:false)
 *
 * @param str
 * @return
 */
function isHangul(str) {
  if (str.length > 0 ) {
    for (var i = 0; i < str.length; i++)
      if (str.charCodeAt(i) < 128 )
        return false;
  }
  return true;
}
/**
 * isHangulNumeric(str)
 * - 한글/숫자체크
 *
 * @param str
 * @return
 */
function isHangulNumeric(str) {
  for (var i = 0; i < str.length; i++) {
    var chr = str.substr(i, 1);
    if (!(chr < '0' || chr > '9')) continue; // 숫자
    chr = escape(chr);
    if (chr.charAt(1) == 'u') {
      chr = chr.substr(2, (chr.length - 1));
      if((chr < 'AC00') || (chr > 'D7A3'))
        return false;
    } else return false;
  }
  return true;
}
/**
 * isBlank(str)
 * - 공백문자체크(공백으로만된경우:false)
 *
 * @param str
 * @return
 */
function isBlank(str) {
  var regExp = /^[\s]+$/i;
  if (regExp.test(str) || str.length == 0) return false;
  else return true;
}
/**
 * isWithBlank(str)
 * - 공백포함체크(문자열에공백이포함된경우 :false)
 *
 * @param str
 * @return
 */
function isWithBlank(str) {
  var regExp = /\s/i;
  if (regExp.test(str)) return false;
  else return true;
}
/**
 * chkMinMaxLength(input, min, max, inName)
 * - 최소값/최대값체크
 *
 * @param input
 * @param min - 0 인경우체크안함
 * @param max - 0 인경우체크안함
 * @param inName - 항목명('주소', '이메일', ..)
 * @return
 */
function chkMinMaxLength(input, min, max, inName) {
  if (min != 0 && chkByte(input.value) < min) {
    if (!input.value) {
      alert("\"" + inName + "\" 항목을 입력해 주세요.\t\r");
      input.focus();
      return false;
    } else {
      alert("\"" + inName + "\" 항목은 " + min + "자 이상 입력하셔야 합니다.[한글:2Byte]\t\r");
      input.focus();
      return false;
    }
  }
  if (max != 0 && chkByte(input.value) > max) {
    alert("\"" + inName + "\" 항목은 " + max + "자 이내로 입력하셔야 합니다.[한글:2Byte]\t\r");
    input.focus();
    return false;
  }

  return true;
}

function chkMinMaxLength2(input, min, max, inName) {
  if (min != 0 && chkByte(input.value) < min) {
    if (!input.value) {
      alert("\"" + inName + "\" 항목을 입력해 주세요.\t\r");
      input.focus();
      return false;
    } else {
      alert("\"" + inName + "\" 항목은 " + min + "자 이상 입력하셔야 합니다.\t\r");
      input.focus();
      return false;
    }
  }
  if (max != 0 && chkByte(input.value) > max) {
    alert("\"" + inName + "\" 항목은 " + max + "자 이내로 입력하셔야 합니다.\t\r");
    input.focus();
    return false;
  }

  return true;
}
/**
 * chkMinMaxNumeric(input, min, max, inName, allowMinus)
 * - 숫자체크/최소값/최대값
 *
 * @param input
 * @param min
 * @param max
 * @param inName
 * @param allowMinus
 * @return
 */
function chkMinMaxNumeric(input, min, max, inName, allowMinus) {
  var tempStr;

  tempStr = input.value;
  tempStr = '' + tempStr.replace(/,/gi,'');
  tempStr = tempStr.replace(/(^\s*)|(\s*$)/g, '');

  if (input.value == '') {
    alert("\"" + inName + "\" 항목을 입력해 주세요.\t\r");
    input.focus();
    return false;
  }
  if (isNaN(new Number(tempStr))) {
    alert("\"" + inName + "\" 항목은 숫자만 입력해 주세요.\t\r");
    input.focus();
    return false;
  }
  if (allowMinus == undefined || allowMinus == 0) {
    if (tempStr < 0) {
      alert('마이너스 수치는은 입력할 수 없습니다.\t\r');
      input.focus();
      return false;
    }
  }

  if (min != 0 && tempStr < min) {
    alert("\"" + inName + "\" 항목의 수치는 " + min + " 이상 입력하셔야 합니다.\t\r");
    input.focus();
    return false;
  }
  if (max != 0 && tempStr > max) {
    alert("\"" + inName + "\" 항목의 수치는 " + max + " 이하로 입력하셔야 합니다.\t\r");
    input.focus();
    return false;
  }

  return true;
}
/**
 * chkId(input)
 * - 아이디입력값체크
 *
 * @param input
 * @return
 */
function chkId(input) {
  var str = input.value;
  var firstChar = str.charAt(0);

  if (str.length < 4 || str.length > 20) {
    alert("아이디를 입력해주세요.\t\r아이디는 영문자, 숫자 4자 이상 20자 이내로 입력하셔야 합니다.\t\r");
    input.value = "";
    input.focus();
    return false;
  }
  var regExp = /[^a-z]/i;
  if (regExp.test(firstChar)) {
    alert("아이디의 첫글자는 영문자로 시작해야 됩니다.\t\r");
    input.value = "";
    input.focus();
    return false;
  }
  if (!isAlphaNumeric(str)) {
    alert("아이디는 영문자와 숫자만 사용가능합니다.\t\r");
    input.value = "";
    input.focus();
    return false;
  }

  return true;
}
/**
 * chkPw(input, input2, min)
 * - 비밀번호입력값체크
 *
 * @param input
 * @param input2
 * @param min
 * @return
 */
function chkPw(input, input2, min) {
  if (!input.value) {
    alert("비밀번호를 입력해 주세요.\t\r");
    input.focus();
    return false;
  } else if (input.value.length < min) {
    alert("비밀번호는 " + min + "자이상 입력하셔야 합니다.\t\r");
    input.focus();
    input.value = "";
    input2.value = "";
    return false;
  } else if (!input2.value) {
    alert("비밀번호확인을 입력해 주세요\t\r");
    input2.focus();
    return false;
  } else if (input.value != input2.value) {
    alert("비밀번호와 비밀번호 확인이 틀립니다.\t\r다시 입력해주세요.\t\r");
    input2.value = "";
    input2.focus();
    return false;
  }


  return true;
}


/**
 * chkHanName(input)
 * - 한글명이름만입력받을경우
 *
 * @param input
 * @return
 */
function chkHanName(input) {
  if (!isBlank(input.value)) {
    alert("이름을 입력해 주세요.\t\r");
    input.focus();
    return false;
  }
  if (!isHangul(input.value)) {
    alert("이름 항목은 한글만 입력하셔야 합니다.\t\r");
    input.focus();
    return false;
  }

  return true;
}
/**
 * chkJumin(num)
 * - 주민등록번호유효체크
 *
 * @param num
 * @return
 */
function chkJumin(num) {
  var reg = /([0-9]{6})-?([0-9]{7})/;
  if (!reg.test(num)) return false;

  var ssn = RegExp.$1 + RegExp.$2 + RegExp.$3;
  var sum = 0;
  var digit = "234567892345";

  for (var i = 0; i < 12; i++)
    sum += parseInt(ssn.charAt(i)) * parseInt(digit.charAt(i));

  var result = (11 - (sum % 11)) % 10;
  var check = parseInt(ssn.charAt(12));
  if (result != check) return false;

  return true;
}
/**
 * chkCompNum(str)
 * - 사업자번호유효체크
 *
 * @param str
 * @return
 */
function chkCompNum(str) {
  var reg = /([0-9]{3})-?([0-9]{2})-?([0-9]{5})/;
  if (!reg.test(str)) return false;

  var strCompNum = RegExp.$1 + RegExp.$2 + RegExp.$3;
  if (strCompNum == "0000000000") return false;

  var intSum = 0;
  var strDigit = "13713713";
  var intEnd = strCompNum.charAt(9);

  for (var i = 0; i < 8; i++)
    intSum = intSum + (parseInt(strCompNum.charAt(i)) * parseInt(strDigit.charAt(i))) % 10;

  var strTemp = parseInt(strCompNum.charAt(8)) * 5 + "0";
  var intChk = parseInt(strTemp.charAt(0)) + parseInt(strTemp.charAt(1));
  var intMatch = (10 - (intSum + intChk) % 10) % 10;

  if (intEnd != intMatch) return false;

  return true;
}
/**
 * chkEmail(str)
 * - 이메일체크
 *
 * @param str
 * @return
 */
function chkEmail(str) {
  //var regExp = /^[a-z0-9]{2,}@([a-z0-9-]+){2,}(\.[a-z]{2,}){1,}$/i;
  var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
  if (regExp.test(str)) return true;
  else return false;
}
/**
 * chkExtension(String strFileName, String strExtension, boolean allowDeny) - input file 확장자 체크
 *
 * @param strFileName
 * @param strExtension - 확장자스트링 (구분자 '|')
 * @param isDeny - false : strExtension 에 등록된 확장자만 허용, true : strExtension 에             등록된것을 제외한 확장자만 허용
 * @return
 */
function chkExtension(strFileName, strExtension, isDeny) {
  var extension = "";
  var isMatches = isDeny;
  var index = strFileName.lastIndexOf('.');
  var arrayExtension = strExtension.split("|");

  if (index != -1) extension = strFileName.substring(index + 1, strFileName.length);
  if (extension.lastIndexOf('\\') != -1) extension = "";
  if (extension == "") return false;

  for (i = 0; i < arrayExtension.length; i++) {
    if (extension.toLowerCase() == arrayExtension[i]) {
      isMatches = !isMatches;
      break;
    }
  }

  return isMatches;
}
/**
 * chkSelectBox(input, inName)
 * - select box 0번째 옵션이 선댁되었는지 체크
 *
 * @param input
 * @param inName - 항목명('년', '월', ..)
 * @return
 */
function chkSelectBox(input, inName) {
  if (input.selectedIndex == 0) {
    alert("\"" + inName + "\" 항목을 선택해 주세요.\t\r");
    input.focus();
    return false;
  }
  return true;
}
/************************* E : CHECK VALUE *************************/

