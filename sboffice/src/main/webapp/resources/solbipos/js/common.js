"use strict";
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
        try {
          var $form = $( urlOrSelector );
        } catch( e ) {
          var $form = {};
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
    , postJSON: function( url, data, func ){
      return $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function(data) {
          if(data.status === "OK") {
            return func(data);
          }
          else if(data.status === "FAIL") {
            return func(data);
          }
          else if(data.status === "SESSION_EXFIRE") {
            s_alert.popOk(data.message, function() {
              location.href = data.url;
             });
          }
          else if(data.status === "SERVER_ERROR") {
            s_alert.pop(data.message);
          }
          else {
            var msg = data.status + " : " + data.message;
            alert(msg);
          }
        },
        cache: false,
        async:true,
        dataType: "json",
        beforeSend: function() {
          $("#loadingback").show();
          $("#loadingfront").show();
        },
        complete: function() {
          $("#loadingback").hide();
          $("#loadingfront").hide();
        },
        error : function(){
          $("#loadingback").hide();
          $("#loadingfront").hide();
        }
      });
//      return $.post( url, data, func, "json" );
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
  
}( "undefined" != typeof window ? window : this, jQuery );




