"use strict";
!function(win, $) {

  var s_alert = {
    pop : function(msg) {
      s_alert.popOk(msg, null);
    },

    popOk : function(msg, fun1) {
      var id = s_alert.randomString(5);
      var pop = $("#layerOk").clone(true).attr("id", id).appendTo(document.body);
      pop.find("p").text(msg);
      pop.find("a").bind("click", function() {
        $("#fullDimmedId").hide();
        pop.remove();
        if (fun1 != null) {
          return fun1();
        }
      });
      $("#fullDimmedId").show();
      pop.show();
    },

    popConf : function(msg, fun1, fun2) {
      var id = s_alert.randomString(5);
      var pop = $("#layerConf").clone(true).attr("id", id).appendTo(document.body);
      pop.find("p").text(msg);
      
      pop.find(".btn_blue").bind("click", function() {
        $("#fullDimmedId").hide();
        pop.remove();
        if (fun1 != null) {
          return fun1();
        }
      });
      
      pop.find(".btn_gray").bind("click", function() {
        $("#fullDimmedId").hide();
        pop.remove();
        if (fun2 != null) {
          return fun2();
        }
      });
      
      $("#fullDimmedId").show();
      pop.show();
    },
    randomString : function(length) {
      var chars = "abcdefghiklmnopqrstuvwxyz_";
      var randomstring = '';
      for (var i = 0; i < length; i++) {
        var rnum = Math.floor(Math.random() * chars.length);
        randomstring += chars.substring(rnum, rnum + 1);
      }
      return randomstring;
    }
  };

  win.s_alert = s_alert;
}("undefined" != typeof window ? window : this, jQuery);
