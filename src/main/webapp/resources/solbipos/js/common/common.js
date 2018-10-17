//"use strict";
!function( win, $ ){

  !"".trim && ( String.prototype.trim = function(){ return this.replace(/^\s|\s$/g, ""); } );
  //jQuery 가 없으면 return
  if( typeof $ !== "function" || !$.prototype.jquery ) return {};

  //id=1,id2=2,id3=3, ... > { id1:1, id2:2, id3:3, ... }
  function parseObject( str, sep ) {
    if( typeof str !== "string" ) return str;
    var arr = str.split( sep || "," )
    var obj = {};
    for( var li in arr ) {
        var keyValue = arr[ li ].split( "=" )
          , key = keyValue[ 0 ] && keyValue[ 0 ].trim()
          , val = keyValue[ 1 ] && keyValue[ 1 ].trim()
          , arr;
        if( !key ) continue;
        if( !obj.hasOwnProperty(key) ){
            obj[ key ] = val;
            continue;
        }
        if( (arr = obj[key]) instanceof Array ) {
          arr[ arr.length ] = val
        }
        else {
          obj[ key ] = [ arr, val ]; //중복되는 값이 생겼을 때 배열로 생성하면서 기존 값, 새로 넣을 값을 추가
        }
    }
    return obj;
  }

  // ajax 기본 설정
  $.ajaxSetup({
    cache: false
  });

  // jQuery Method 추가
  //ex ) $.extendName();
  $.extend({
    send: function( url ){
      if( typeof url !== "string" ) return;

      var method = { "get":"GET", "post":"POST" }[ ("" + arguments[1]).toLowerCase() ] || "POST"
        , target = arguments[ 2 ] || ""
        , temp = url.split( "?" )
        , path = temp[ 0 ]
        , params = parseObject( temp[1], "&" )
        , formId = "f" + new Date().getTime()
        , textHTML = "<form id=\"" + formId + "\" method=\"" + method
                    + "\" target=\"" + target + "\" action=\"" + path + "\">";

      for( var li in params ) {
        textHTML += "<input type=\"hidden\" name=\"" + li + "\" value=\"" + params[ li ] + "\" />";
      }
      $( "body" ).append( textHTML + "</form>" );
      $( "#" + formId ).submit().remove();
    }
    , open: function( urlOrSelector ) {
        var popup
        , options = typeof arguments[ 1 ] === "object" ? arguments[ 1 ]
                                                        : ( parseObject(arguments[1], ",") || {} )
        , target = options.target || "popup" + new Date().getTime()
        , width = options.width || -1
        , height = options.height || -1
        , top = options.top || ( screen.availHeight - height.replace(/[^0-9]/g, "") ) / 2
        , left = options.left || ( screen.availWidth - width.replace(/[^0-9]/g, "") ) / 2
        , popOptions = "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left
                        + ",scrollbars=1,resizable=1,status=1,toolbar=0,menubar=0";

      popup = win.open( "about:blank", target, popOptions );

      if( popup ) {
        var $form;
        try {
          $form = $( urlOrSelector );
        } catch( e ) {
          $form = {};
        }

        if( $form.length ) {
          $form.attr( "target", target ).submit();
        }
        else {
          $.send( urlOrSelector, options.method, target );
        }
        popup.focus();
      }
    }
    // succ 펑션, fail 펑션 각각 개별적으로 움직인다.
    // 실제 HTTP Request 오류(통신오류)는 .fail 부분의 함수 수행
    , postJSON: function( url, data, succ, fail ){
      // 가상로그인시 세션활용
      if (document.getElementsByName("sessionId")[0]) {
        data['sid'] = document.getElementsByName("sessionId")[0].value;
      }
      return $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function(result) {
          if(result.status === "OK") {
            return succ(result);
          }
          else if(result.status === "FAIL") {
            return fail(result);
          }
          else if(result.status === "SESSION_EXFIRE") {
            s_alert.popOk(result.message, function() {
              location.href = result.url;
             });
          }
          else if(result.status === "SERVER_ERROR") {
            s_alert.pop(result.message);
          }
          else {
            var msg = result.status + " : " + result.message;
            alert(msg);
          }
        },
        cache: false,
        async:true,
        dataType: "json",
        beforeSend: function() {
          $("#_loadTent, #_loading").show();
        },
        complete: function() {
          $("#_loadTent, #_loading").hide();
        },
        error : function(){
          $("#_loadTent, #_loading").hide();
        }
      })
      .fail(function(){
        s_alert.pop("Ajax Fail By HTTP Request");
      });
//      return $.post( url, data, func, "json" );
    }
    , postJSONSave: function( url, data, succ, fail ){
      // 가상로그인시 세션활용
      if (document.getElementsByName("sessionId")[0]) {
        data['sid'] = document.getElementsByName("sessionId")[0].value;
      }
      return $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(data),
        success: function(result) {
          if(result.status === "OK") {
            return succ(result);
          }
          else if(result.status === "FAIL") {
            return fail(result);
          }
          else if(result.status === "SESSION_EXFIRE") {
            s_alert.popOk(result.message, function() {
              location.href = result.url;
            });
          }
          else if(result.status === "SERVER_ERROR") {
            s_alert.pop(result.message);
          }
          else {
            var msg = result.status + " : " + result.message;
            alert(msg);
          }
        },
        cache: false,
        async:true,
        dataType: "json",
        contentType : 'application/json',
        beforeSend: function() {
          $("#_saveTent, #_saving").show();
        },
        complete: function() {
          $("#_saveTent, #_saving").hide();
        },
        error : function(){
          $("#_saveTent, #_saving").hide();
        }
      })
      .fail(function(){
        s_alert.pop("Ajax Fail By HTTP Request");
      });
    }
    , postJSONArray: function( url, data, succ, fail ){
      // 가상로그인시 세션활용
      if (document.getElementsByName("sessionId")[0]) {
        data['sid'] = document.getElementsByName("sessionId")[0].value;
      }
      return $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(data),
        success: function(result) {
          if(result.status === "OK") {
            return succ(result);
          }
          else if(result.status === "FAIL") {
            return fail(result);
          }
          else if(result.status === "SESSION_EXFIRE") {
            s_alert.popOk(result.message, function() {
              location.href = result.url;
             });
          }
          else if(result.status === "SERVER_ERROR") {
            s_alert.pop(result.message);
          }
          else {
            var msg = result.status + " : " + result.message;
            alert(msg);
          }
        },
        cache: false,
        async:true,
        dataType: "json",
        contentType : 'application/json',
        processData: false,
        beforeSend: function() {
          $("#_loadTent, #_loading").show();
        },
        complete: function() {
          $("#_loadTent, #_loading").hide();
        },
        error : function(){
          $("#_loadTent, #_loading").hide();
        }
      })
      .fail(function(){
        s_alert.pop("Ajax Fail By HTTP Request");
      });
    }
    , postJSONAsync: function( url, data, func ){
      return $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: func,
        cache: false,
        async:true,
        dataType: "json",
        beforeSend: function() {

        },
        complete: function() {

        }
      });
    }
    , postJSONFile: function( url, form, succ, fail ){
      return $.ajax({
        url: url,
        type: "POST",
        data: form,
        processData: false,
        contentType: false,
        cache: false,
        success: function(result) {
          if (result.status === "OK") {
            return succ(result);
          }
          else if (result.status === "FAIL") {
            return fail(result);
          }
          else if (result.status === "SESSION_EXFIRE") {
            s_alert.popOk(result.message, function () {
              location.href = result.url;
            });
          }
          else if (result.status === "SERVER_ERROR") {
            s_alert.pop(result.message);
          }
          else {
            var msg = result.status + " : " + result.message;
            alert(msg);
          }
        },
        error : function(result){
          alert("error");
        }
      },function(){
        s_alert.pop("Ajax Fail By HTTP Request");
      });
    }
    , countUtf8Bytes: function( s ){
      for( var b = 0, i = 0, c; c = s.charCodeAt(i++); b += c >> 11 ? 3 : (c >> 7 ? 2 : 1) );
      return b;
    }

  });

  // jQuery Selector Method 추가
  // ex ) $( selector ).extendName();
  $.fn.extend({
    changeClass: function( removeClassName, addClassName ){
      this.removeClass( removeClassName ).addClass( addClassName );
    }
    , className: function(){
        return this[ 0 ].className;
    }
  });


}( "undefined" !== typeof window ? window : this, jQuery );

//트리 생성용 변수
var pNode;
var allMenu = "";
var bkmkMenu = "";
// 트리 생성
function makeTree(div, data, initMenu) {

  var tree = new wijmo.nav.TreeView(div, {
    displayMemberPath: 'nm',
    childItemsPath: 'items',
    autoCollapse: true,
    expandOnClick: false
  });

  // 트리의 아이템이 load 완료 되었을 때 이벤트
  tree.loadedItems.addHandler(function(s, e) {
    var node;
    // 아이콘 Class 추가
    for (node = s.getFirstNode(); node; node = node.nextSibling()) {
      if(!isEmpty(node)){
        wijmo.addClass(node.element, node.dataItem.icon);
      }
    }
    s.collapseToLevel(0);

    // 초기 메뉴(현재 메뉴) 설정
    if(initMenu) {
      for (node = s.getFirstNode(); node; node = node.next()) {
        if(isEmpty(node.nodes)) {
          if(!isEmpty(node.dataItem) && node.dataItem.cd === initMenu) {
            s.selectedItem = node.dataItem;
          }
        }
      }
    }
  });

  // 선택된 메뉴가 변경 되었을 때 이벤트
  tree.selectedItemChanged.addHandler(function(s, e) {
    var node;
    // 이전 메뉴의 클래스 제거
    if(pNode) {
      for (node = pNode; node; node = node.parentNode) {
        wijmo.removeClass(node.element, "on");
      }
    }
    // 선택된 메뉴에 클래스 추가
    for (node = s.selectedNode; node; node = node.parentNode) {
      wijmo.addClass(node.element, "on");
    }
    pNode = s.selectedNode;
  });

  // 아이템 클릭 시 이벤트
  tree.itemClicked.addHandler(function(s, e) {
    // URL 이 있을 경우 페이지 이동
    if(!isEmpty(s.selectedNode.dataItem.url)) {
      location.href = s.selectedNode.dataItem.url;
      // 가상로그인시 파라미터인 SessionID 설정
      if( document.getElementsByName("sessionId").length > 0 ) {
        var vSessionId = document.getElementsByName("sessionId")[0].value;
        location.href = s.selectedNode.dataItem.url + "?sid=" + vSessionId;
      }
    }
    // 같은 메뉴를 다시 선택 했을 때 메뉴 닫기 기능
    if( pNode === s.selectedNode) {
      s.selectedNode.isCollapsed = !s.selectedNode.isCollapsed;
    }
    else {
      s.selectedNode.isCollapsed = false;
    }
  });

  /* Tree 생성자에서 데이터를 넣는 경우에는 이벤트 핸들러를 생성자에 넣을 수 있다.
  데이터를 생성자에서 넣으면서 이벤트를 나중에 선언하면 생성 시 이벤트 처리 안됨
  아래 처럼 이벤트를 다 선언한 후에 데이터를 넣어야 한다.
  */
  tree.itemsSource = data;

  return tree;
}

function getParam(name){
  var result = "";
  var queryString = window.location.search;
  var paramMap = {}
  if (queryString === "") {
    result = undefined;
  }
  if (typeof result !== "undefined") {
    var params = queryString.split("?")[1];
    if (params === "") {
      result = undefined;
    }
    if (typeof result !== "undefined") {
      var paramObj = params.split("&");
      for (var i=0; i<paramObj.length; i++){
        var datas = paramObj[i].split("=");
        paramMap[datas[0]] = datas[1];
      }
      result = paramMap[name];
    }
  }
  return result;
}

/** 현재 날짜 가져오기 */
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

// 날짜 포맷
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

// 날짜+시간 포맷
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

String.prototype.getByteLength = function() {
    var i, c;
    for(var size=i=0;c=this.charCodeAt(i++);size+=c>>11?2:c>>7?2:1);
    return size;
};

/** 오라클에서 한글을 3바이트로 인식해서 한글인 경우 3바이트로 byte length 함수가 필요하여 추가 */
String.prototype.getByteLengthForOracle = function() {
  var i, c;
  for(var size=i=0;c=this.charCodeAt(i++);size+=c>>11?3:c>>7?2:1);
  return size;
};

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

String.prototype.leftPad = function(padStr, padLen) {
  if ( parseInt(padLen) + 1 <= this.getByteLength() ) {
    return this;
  } else {
    var leftPad = padLen - this.getByteLength() + 1;
    return Array(leftPad).join(padStr).concat(this);
  }
};

String.prototype.rightPad = function(padStr, padLen) {
    if ( parseInt(padLen) + 1 <= this.getByteLength() ) {
        return this;
    } else {
        var rightPad = padLen - this.getByteLength() + 1;
        return this.concat(Array(rightPad).join(padStr));
    }
};

String.prototype.setPadding = function(padType, padStr, padLen) {
    if ( padType === "C" ) {
        return this.centerPad(padStr, padLen);
    } else if ( padType === "L" ) {
        return this.leftPad(padStr, padLen);
    } else if ( padType === "R" ) {
        return this.rightPad(padStr, padLen);
    }
};

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

function addComma(str) {
  var regexp = /\B(?=(\d{3})+(?!\d))/g;
  return str.toString().replace(regexp, ',');
}

// 링크 태그 IE 팝업 방지( 이 웹사이트가 컴퓨터에서 앱을 열도록허용하시겠습니까? )
$(document).on('click', 'a[href="#"]', function (e) {
  e.preventDefault();
  return false;
});
