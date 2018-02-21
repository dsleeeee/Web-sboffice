"use strict";
!function(win, $) {

  // 엑셀 다운로드
  var s_alert = {
    test : function(title) {
      console.log(title);
    },
    test1 : function(test) {
      $("#lbk").text(test);
      $(".fullDimmed").toggle();
      $("#layerpop").toggle();
    }
  };

  win.s_alert = s_alert;
}("undefined" != typeof window ? window : this, jQuery);
