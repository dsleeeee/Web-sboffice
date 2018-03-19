"use strict";
!function( win, $ ){
  
  $(document).on("click", ".btn_previous", function() {
    var curr = $(".btn_next").data("value");
    if((curr - 10) < 0) {
      return;
    }
    page.makePage(curr - 10, $(".btn_previous").data("value"));
  });
  
  $(document).on("click", ".btn_next", function() {
    var curr = $(".btn_next").data("value");
    var tot = $(".btn_previous").data("value");
    var r = curr.toString().length > 1 ? (parseInt(curr.toString()[0])+1) * 10 : curr;
    if(r > tot) {
      return;
    }
    page.makePage(curr + 10, tot);
  });
  
  // 페이징
  var page = {
      
      makePage: function(curr, tot) {
        var realCurr = $(".btn_next").data("value");
        page.make(curr, tot);
      },
      
      make: function(curr, tot) {
        $("#pagenav").children().remove();
        var item = {};
        if(tot > 10) {
          item.curr=curr, item.tot=tot, item.start=0, item.end=0;
          var t = curr / 10;
          if(t.toString().indexOf(".") == -1) {
            item.end = curr, item.start = item.end - 9;
          }
          else {
            item.start = (parseInt(t) * 10) + 1, item.end = item.start + 9;
          }
          if(item.end > tot) {
            item.end = tot;
          }
          
          $("#pagenav").append(wijmo.format("<li class=\"btn_previous\" data-value={tot}><a href=\"javascript:;\"></a></li>", item));
          for(var i=item.start; i<=item.end; i++) {
            item.i = i, item.cnm = i==curr ? "on pagenav" : "pagenav";
            $("#pagenav").append(wijmo.format("<li><a href=\"javascript:;\" class=\"{cnm}\" data-value={i}>{i}</a></li>", item));
          }
          $("#pagenav").append(wijmo.format("<li class=\"btn_next\" data-value={curr}><a href=\"javascript:;\"></a></li>", item));
        }
        else {
          for(var i=1; i<=tot; i++) {
            item.i = i;
            item.classnm = i==curr ? "on pagenav" : "\"pagenav\"";
            $("#pagenav").append(wijmo.format("<li><a href=\"javascript:;\" class=\"{classnm}\" data-value={i}>{i}</a></li>", item));
          }
        }
      }
  };
  win.page = page;
}( "undefined" != typeof window ? window : this, jQuery );













