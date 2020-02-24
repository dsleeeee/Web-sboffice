//"use strict";
!function( win, $ ){

  !"".trim && ( String.prototype.trim = function(){ return this.replace(/^\s|\s$/g, ""); } );
  //jQuery 가 없으면 return
  if( typeof $ !== "function" || !$.prototype.jquery ) return {};

  //id=1,id2=2,id3=3, ... > { id1:1, id2:2, id3:3, ... }
  function parseObject( str, sep ) {
    if( typeof str !== "string" ) return str;
    var arr = str.split( sep || "," );
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
        url += '?sid=' + document.getElementsByName("sessionId")[0].value;
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
            //alert(msg);
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
        url += '?sid=' + document.getElementsByName("sessionId")[0].value;
      }
      return $.ajax({
        type: "POST",
        url: url,
        cache: false,
        async: true,
        dataType: "json",
        contentType : 'application/json',
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
        url += '?sid=' + document.getElementsByName("sessionId")[0].value;
      }
      return $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(data),
        cache: false,
        async:true,
        dataType: "json",
        contentType : 'application/json',
        processData: false,
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

/**
 * object 가 비었는지 체크
 * @param obj
 */
function isEmptyObject(obj) {
  for(var key in obj) {
    if(obj.hasOwnProperty(key))
      return false;
  }
  return true;
}

function getToday() {
  var today = new Date();
  var dd = today.getDate();
  var mm = today.getMonth()+1; //January is 0!
  var yyyy = today.getFullYear();

  if (dd < 10) {
    dd= '0' + dd;
  }

  if (mm < 10) {
	mm = '0' + mm;
  }

  today = yyyy + mm + dd;

  return today;
}

// 링크 태그 IE 팝업 방지( 이 웹사이트가 컴퓨터에서 앱을 열도록허용하시겠습니까? )
$(document).on('click', 'a[href="#"]', function (e) {
  e.preventDefault();
  return false;
});
