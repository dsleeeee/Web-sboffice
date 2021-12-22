"use strict";
!function (win, $) {

  var s_alert = {
    pop: function (msg) {
      s_alert.popOk(msg, null);
    },

    popOk: function (msg, callback) {
      var id = s_alert.randomString(5);
      var pop = $("#_layerOk").clone(true).attr("id", id).appendTo(document.body);
      pop.find("p").text(msg);
      pop.find("a").bind("click", function () {
        $("#_alertTent").hide();
        pop.remove();
        if (typeof callback === 'function') {
          setTimeout(function () {
            callback();
          }, 50);
        }
        return false;
      });
      $("#_alertTent").show();
      $("#_alertTent").attr("tabindex", -1).focus();
      pop.show();
    },

    popConf: function (msg, callback) {
      var id = s_alert.randomString(5);
      var pop = $("#_layerConf").clone(true).attr("id", id).appendTo(document.body);
      pop.find("p").text(msg);

      pop.find("a.btn_blue.conf").bind("click", function () {
        $("#_alertTent").hide();
        pop.remove();
        if (typeof callback === 'function') {
          setTimeout(function () {
            callback();
          }, 50);
        }
        return false;
      });

      pop.find("a.btn_gray.conf").bind("click", function () {
        $("#_alertTent").hide();
        pop.remove();
      });

      $("#_alertTent").show();
      pop.show();
    },
    randomString: function (length) {
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
}("undefined" !== typeof window ? window : this, jQuery);
