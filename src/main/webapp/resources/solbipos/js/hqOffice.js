!function(win, $) {

  var c_hq = {

    init : function(callback) {
      $("#_hqall").prop("checked", false);
      c_hq.search({});
      
      $("#_hqok").bind("click", function() {
        if (typeof callback == 'function') {
          callback(c_hq.check_value());
          c_hq.close();
          $(this).unbind();
        }
      });
    },
    
    close : function() {
      $("#_hqlayer").hide();
      $("#_hqtent").hide();
      $("#_hqbody").children().remove();
    },
    
    check_value : function() {
      var check = $('input:radio[name=_hqlist]:checked').val();
      
      var arr = [];
      var data = {};
      
      var checkVal = check.split("|");
      data.cd = checkVal[0];
      data.nm = checkVal[1];
      arr.push(data);
      
      return arr;
    },

    select : function(hqOfficeCd, hqOfficeNm) {
      var param = {};
      param.hqOfficeCd = hqOfficeCd;
      param.hqOfficeNm = hqOfficeNm;
      c_hq.search(param);
    },
    
    search : function(param, fun) {
      
      console.log(param);
      
      $.postJSONAsync("/menu/selectHq.sb", param, function(result) {
        console.log(result);
        
        $("#_hqbody").children().remove();
        
        var s = "<tr><td class=\"tc\">" +
            "<input name=\"_hqlist\" type=\"radio\" value=\"{cd}|{nm}\" style=\"width:17px; height:17px; position:relative; top:1px;\"/></td>" +
            "<td class=\"tc\">{cd}</td>" +
            "<td class=\"tc\">{nm}</td>" +
            "<td class=\"tc\">{owner}</td></tr>";
        
        var p = {};
        
        var d = result.data;
        for(var i=0; i<d.length; i++) {
          var k = d[i];
          p.cd = k.hqOfficeCd;
          p.nm = k.hqOfficeNm;
          p.owner = k.ownerNm;
          $("#_hqbody").append(wijmo.format(s, p));
        }
        
        $("#_hqtent").show();
        $("#_hqlayer").show();
      })
      .fail(function(){
        s_alert.pop("Ajax Fail");
      });
    }
    
  };

  win.c_hq = c_hq;
}("undefined" != typeof window ? window : this, jQuery);
