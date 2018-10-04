!function(win, $) {

  // 이미 선택된 매장이 있으면 매장 목록을 '매장코드1, 매장코드2' 문자열로 받음.
  // 예제) membr/info/view/view.jsp => c_store_init(..
  var chkedStore;

  var c_store = {

    init : function(chked, callback) {
      $("#_storeall").prop("checked", false);

      chkedStore = chked;  // 선택된 값

      c_store.search({});

      $("#_storeok").bind("click", function() {
        if (typeof callback == 'function') {
          callback(c_store.check_value());
          c_store.close();
          $(this).unbind();
        }
      });
    },

    close : function() {
      $("#_storelayer").hide();
      $("#_storetent").hide();
      $("#_storebody").children().remove();
    },

    // 저장된 내역 체크
    checkStores : function(){
      var stores = chkedStore.split(",");

      var checkbox = $('input:checkbox[name="_storelist"]');

      checkbox.each(function(i) {

        var data = {};
        var tr = checkbox.parent().parent().eq(i);
        var td = tr.children();
        var chkbox = this;

        stores.forEach(function(j){
          if(td.eq(1).text() === j.toString()) {
            chkbox.checked = true;
          }
        });
      });
    },

    check_value : function() {
      var checkbox = $("input[name=_storelist]:checked");

      var arr = [];

      if(checkbox.length == $("input[name=_storelist]").length) {
        var d = {};
        d.cd = "ALL";
        d.nm = "ALL";
        arr.push(d);
        return arr;
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

    search : function(param, fun, chked) {
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

        if(chkedStore !== null || chkedStore !== "") {
          c_store.checkStores();
        }
      },
      function (result) {
        s_alert.pop(result.message);
        return;
      });
    }

  };

  win.c_store = c_store;
}("undefined" != typeof window ? window : this, jQuery);
