"use strict";
!function( win, $ ){
  $(document).on("click", ".btn_previous", function() {
    var curr = $(".btn_next").data("curr");
    var page_scale = $(this).parent().data("size");
    if((curr - page_scale) < 0) {
      return false;
    }
    page.makePage($(this), curr - page_scale, $(".btn_previous").data("tot"));
  });
  
  $(document).on("click", ".btn_next", function() {
    var curr = $(".btn_next").data("curr");
    var tot = $(".btn_previous").data("tot");
    var page_scale = $(this).parent().data("size");
    var r = curr.toString().length > 1 ? (parseInt(curr.toString()[0])+1) * page_scale : curr;
    if(r > tot) {
      return false;
    }
    page.makePage($(this), curr + page_scale, tot);
  });
  
  // 페이징
  var page = {
      
      makePage: function(nav, curr, tot) {
        var realCurr = $(".btn_next").data("curr");
        page.make("#"+nav.parent()[0].id, curr, tot);
      },
      
      make: function(div, curr, tot) {
        var page_scale = $(div).data("size");
        var page_end = page_scale == 10 ? 9 : 4;
        // HTML
        var pre = "<li class=\"btn_previous\" data-tot={tot}><a href=\"javascript:;\"></a></li>";
        var nav = "<li><a href=\"javascript:;\" class=\"{cnm}\" data-value={i}>{i}</a></li>";
        var nex = "<li class=\"btn_next\" data-curr={curr}><a href=\"javascript:;\"></a></li>";
        
        $(div).children().remove();
        var item = {};
        item.curr = curr;
        item.tot = tot;
        item.start = 0;
        item.end = 0;
        // 페이징 계산
        var t = curr / page_scale;
        if ( t.toString().indexOf(".") === -1 ) {
          item.end = curr;
          item.start = item.end - page_end;
        } else {
          item.start = (parseInt(t) * page_scale) + 1;
          item.end = item.start + page_end;
        }
        if(item.end > tot) {
          item.end = tot;
        }
        // 페이징 제작
        if ( tot > page_scale ) {
          $(div).append(wijmo.format(pre, item));
        }
        for(var i=item.start; i<=item.end; i++) {
          var cl = div.substr(1, div.length);
          item.i = i, item.cnm = i==curr ? "on pagenav " + cl : "pagenav " + cl;
          $(div).append(wijmo.format(nav, item));
        }
        if(tot > page_scale) {
          $(div).append(wijmo.format(nex, item));
        }
      }
  };
  win.page = page;
}( "undefined" !== typeof window ? window : this, jQuery );
