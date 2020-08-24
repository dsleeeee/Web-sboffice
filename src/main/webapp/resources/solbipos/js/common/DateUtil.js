/***
 * 현재 날짜 가져오기
 * @param seperator
 * @returns {*}
 */
function getCurDate(seperator) {
  try {
    if (seperator === undefined) seperator = "";

    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();

    if (("" + month).length === 1) month = "0" + month;
    if (("" + day).length === 1) day = "0" + day;

    return (year + seperator + month + seperator + day);
  }
  catch (e) {
    //alert('udfMainFrm.js : '+e);
  }
}

/***
 * 현재 시간 가져오기
 * @param seperator
 * @returns {*}
 */
function getCurTime(seperator) {
  try {
    if (seperator === undefined) seperator = "";

    var date = new Date();
    var hour = date.getHours();
    var min = date.getMinutes();
    var second = date.getSeconds();

    if (("" + hour).length === 1) hour = "0" + hour;
    if (("" + min).length === 1) min = "0" + min;
    if (("" + second).length === 1) second = "0" + second;

    return (hour + seperator + min + seperator + second);
  }
  catch (e) {
    //alert('udfMainFrm.js : '+e);
  }
}

/***
 * 날짜 포맷
 * @param date
 * @param seperator
 * @returns {*}
 */
function getFormatDate(date, seperator)
{
    try
    {
        if(date.length < 8) return date;
        var seperator = ((seperator === undefined || seperator === '') ? '-' : seperator);
        var year  = date.substr(0,4);
        var month = date.substr(4,2);
        var day   = date.substr(6,2);

        return (year + seperator + month + seperator + day );
    }
    catch(e)
    {
        //alert('udfMainFrm.js : '+e);
    }
}

/***
 * 시간 포맷
 * @param time
 * @param displayFg h:시간 m:분 s:초
 * @param seperator
 * @returns {*}
 */
function getFormatTime(time, displayFg, seperator)
{
  try
  {
    if(time.length < 6) return time;
    var seperator = ((seperator === undefined || seperator === '') ? ':' : seperator);
    var returnValue = '';
    var hour = time.substr(0,2);
    var min  = time.substr(2,2);
    var sec  = time.substr(4,2);

    if(displayFg.indexOf('h') >= 0) {
      returnValue += hour;
    }
    if(displayFg.indexOf('m') >= 0) {
      returnValue += (returnValue === '' ? '' : seperator)+min;
    }
    if(displayFg.indexOf('s') >= 0) {
      returnValue += (returnValue === '' ? '' : seperator)+sec;
    }

    return returnValue;
  }
  catch(e)
  {
    //alert('udfMainFrm.js : '+e);
  }
}

/**
 * 날짜+시간 포맷
 * @param date
 * @param seperator_date
 * @param seperator_time
 * @returns {*}
 */
function getFormatDateTime(date, seperator_date, seperator_time)
{
    try
    {
        if(date.length < 14) return date;
        var seperator_date = ((seperator_date === undefined || seperator_date === '') ? '-' : seperator_date);
        var seperator_time = ((seperator_time === undefined || seperator_time === '') ? ':' : seperator_time);
        var year   = date.substr(0,4);
        var month  = date.substr(4,2);
        var day    = date.substr(6,2);
        var hour   = date.substr(8,2);
        var minute = date.substr(10,2);
        var second = date.substr(12,2);
        return (year + seperator_date + month + seperator_date + day + ' ' +hour + seperator_time + minute + seperator_time + second);
    }
    catch(e)
    {
        //alert('udfMainFrm.js : '+e);
    }
}

/**
 * 날짜를 날짜형태 문자열로 변형 (yyyy-mm-dd)
 * @param date
 * @returns {number}
 */
function dateToDaystring(date){

  var date = new Date(date);

  var year = date.getFullYear();
  var month = (date.getMonth() + 1).toString();
  var day = date.getDate().toString();

  var monthStr = month.leftPad("0",2);
  var dayStr = day.leftPad("0",2);

  return(year + "-"+ monthStr  + "-"+ dayStr );
}

/**
 * 날짜를 날짜형태 문자열로 변형 (yyyymmdd)
 * @param date
 * @returns {number}
 */
function getFormatDateString(date){

    var date = new Date(date);

    var year = date.getFullYear();
    var month = (date.getMonth() + 1).toString();
    var day = date.getDate().toString();

    var monthStr = month.leftPad("0",2);
    var dayStr = day.leftPad("0",2);

    return(year + monthStr  + dayStr );
}

/**
 * 문자열을 날짜로 변경
 * @param date
 * @returns {number}
 */
function stringToDate(str){

  if( isEmptyObject(str) ) {
    return;
  }

  var formatString = getFormatDate(str, '-');
  var date         = formatString.split('-');

  return new Date(date[0], date[1]-1 , date[2]);
}
