!function(win, $) {

  var c_ca = {

    init : function(callback) {
      
      c_ca.search({});
      
      $("#_caok").bind("click", function() {
        if($('input:radio[name=_calist]:checked').val() != undefined)
        {
          if (typeof callback == 'function') {
            callback(c_ca.check_value());
            c_ca.close();
            $(this).unbind();
          }
        }
      });
    },
    
    close : function() {
      $("#_calayer").hide();
      $("#_catent").hide();
      $("#_cabody").children().remove();
    },
    
    check_value : function() {
      var check = $('input:radio[name=_calist]:checked').val();
      var arr = [];
      var data = {};
      
      var checkVal = check.split("|");
      data.cd = checkVal[0];
      data.nm = checkVal[1];
      arr.push(data);
      
      return arr;
    },

    select : function(cacode) {
      var param = {};
      param.agencyCd = cacode;
      c_ca.search(param);
    },
    
    search : function(param, fun) {
      
      console.log(param);
      
      $.postJSONAsync("/menu/selectCmAgency.sb", param, function(result) {
        console.log(result);
        
        $("#_cabody").children().remove();
        
        var s = "<tr><td class=\"tc\">" +
            "<input name=\"_calist\" type=\"radio\" value=\"{cd}|{nm}\" style=\"width:17px; height:17px; position:relative; top:1px;\"/></td>" +
            "<td class=\"tc\">{cd}</td>" +
            "<td class=\"tc\">{nm}</td>" +
            "</tr>";
        
        var p = {};
        
        var d = result.data;
        for(var i=0; i<d.length; i++) {
          var k = d[i];
          p.cd = k.agencyCd;
          p.nm = k.agencyNm;
          $("#_cabody").append(wijmo.format(s, p));
        }
        
        $("#_catent").show();
        $("#_calayer").show();
      })
      .fail(function(){
        s_alert.pop("Ajax Fail");
      });
    }
    
  };

  win.c_ca = c_ca;
}("undefined" != typeof window ? window : this, jQuery);
