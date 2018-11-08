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
 * 날짜를 문자열로 변환
 * @param date
 * @returns {number}
 */
function dateToDaystring(date){
  try
  {
    var date = new Date(date);

    var year = date.getFullYear();
    var month = (date.getMonth() + 1).toString();
    var day = date.getDate().toString();

    var monthStr = month.leftPad("0",2);
    var dayStr = day.leftPad("0",2);

    return(year + "-"+ monthStr  + "-"+ dayStr );
  }
  catch(e)
  {
    //alert('udfMainFrm.js : '+e);
  }
}

/**
 * 문자열을 날짜로 변환
 * @param date
 * @returns {string}
 */
function stringToDate(str){
  try
  {
    var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

    var dateStr = str.replace(regExp, "");

    var y = dateStr.substr(0, 4);
    var m = dateStr.substr(4, 2);
    var d = dateStr.substr(6, 2);

    return new Date(y,m-1,d);
  }
  catch(e)
  {
    //alert('udfMainFrm.js : '+e);
  }
}

