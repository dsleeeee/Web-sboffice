"use strict";
!function(win, $) {

  var c_store = {

    init : function() {
      $("#_storeall").prop("checked", false);
      c_store.search({});
    },
    
    close : function() {
      $("#_storelayer").hide();
      $("#_storetent").hide();
      $("#_storebody").children().remove();
    },
    
    check_value : function() {
      var checkbox = $("input[name=_storelist]:checked");
      
      var arr = [];
      
      if(checkbox.length == $("input[name=_storelist]").length) {
        var d = {};
        d.cd = "ALL";
        d.nm = "ALL";
        arr.push(d);
      }
      
      checkbox.each(function(i) {
        var data = {};
        var tr = checkbox.parent().parent().eq(i);
        var td = tr.children();
        data.cd = td.eq(1).text();
        data.nm = td.eq(2).text();
        arr.push(data);
      });
      return arr;
    },

    select : function(storeNm) {
      var param = {};
      param.storeNm = storeNm;
      c_store.search(param);
    },
    
    search : function(param, fun) {
      $.postJSONAsync("/menu/selectStore.sb", param, function(result) {
        $("#_storebody").children().remove();
        
        var s = "<tr><td class=\"tc\">" +
        		"<input name=\"_storelist\" type=\"checkbox\"/></td>" +
        		"<td class=\"tc\">{cd}</td>" +
        		"<td class=\"tc\">{nm}</td>" +
        		"<td class=\"tc\">{owner}</td></tr>";
        
        var p = {};
        
        var d = result.data;
        for(var i=0; i<d.length; i++) {
          var k = d[i];
          p.cd = k.storeCd;
          p.nm = k.storeNm;
          p.owner = k.ownerNm;
          $("#_storebody").append(wijmo.format(s, p));
        }
        
        $("#_storetent").show();
        $("#_storelayer").show();
      })
      .fail(function(){
        s_alert.pop("Ajax Fail");
      });
    }
    
  };

  win.c_store = c_store;
}("undefined" != typeof window ? window : this, jQuery);
