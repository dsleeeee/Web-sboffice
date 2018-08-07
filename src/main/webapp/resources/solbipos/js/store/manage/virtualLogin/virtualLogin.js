/****************************************************************
 * 
 * 파일명 : virtualLogin.js
 * 설  명 : 가상로그인 JavaScript
 * 
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.06.15     노현수      1.0            
 * 
 * **************************************************************/
var popupCnt = 0;

$(document).ready(function() {
    
    var srchHqOfficeCd = wcombo.genInput("#srchHqOfficeCd");
    var srchHqOfficeNm = wcombo.genInput("#srchHqOfficeNm");
    var srchStoreCd    = wcombo.genInput("#srchStoreCd");
    var srchStoreNm    = wcombo.genInput("#srchStoreNm");
    var srchClsFg      = wcombo.genCommonBox("#srchClsFg", clsFg);
    var srchSysStatFg  = wcombo.genCommonBox("#srchSysStatFg", sysStatFg);
    var gridData =
        [
            {"binding":"hqOfficeCd","header":messages["virtualLogin.hqOfficeCd"], "width":"*"},
            {"binding":"hqOfficeNm","header":messages["virtualLogin.hqOfficeNm"], "width":"*"},
            {"binding":"storeCd","header":messages["virtualLogin.storeCd"], "width":"*"},
            {"binding":"storeNm","header":messages["virtualLogin.storeNm"], "width":"*"},
            {"binding":"clsFgNm","header":messages["virtualLogin.clsFgNm"], "width":"*"},
            {"binding":"sysStatFgNm","header":messages["virtualLogin.sysStatFgNm"], "width":"*"},
            {"binding":"ownerNm","header":messages["virtualLogin.ownerNm"], "width":"*"},
            {"binding":"telNo","header":messages["virtualLogin.telNo"], "width":"*"},
            {"binding":"mpNo","header":messages["virtualLogin.mpNo"], "width":"*"},
            {"binding":"agencyNm","header":messages["virtualLogin.agencyNm"], "width":"*"},
            {"binding":"sysOpenDate","header":messages["virtualLogin.sysOpenDate"], "width":"*"},
            {"binding":"sysClosureDate","header":messages["virtualLogin.sysClosureDate"], "width":"*"}
        ];
    var grid = wgrid.genGrid("#theGrid", gridData);
    var listScaleBox = wcombo.genCommonBox("#listScaleBox", listScaleBoxData);

    // 그리드 포맷
    grid.formatItem.addHandler(function(s, e) {
      if ( e.panel == s.cells ) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "hqOfficeCd" && s.rows[e.row].dataItem.hqOfficeCd != "00000") {
          wijmo.addClass(e.cell, 'wijLink');
        }
        if( col.binding == "storeCd" && s.rows[e.row].dataItem.storeCd != "00000") {
          wijmo.addClass(e.cell, 'wijLink');
        }
        if( col.binding == "agencyNm" ) {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    
    // 그리드 선택 이벤트
    grid.addEventListener(grid.hostElement, 'click', function(e) {
      var ht = grid.hitTest(e);
      if( ht.cellType == wijmo.grid.CellType.Cell) {
          var col = ht.panel.columns[ht.col];
          // 단독매장본사의 경우에는 선택 불가능
          if( col.binding == "hqOfficeCd" && grid.rows[ht.row].dataItem.hqOfficeCd != "00000") {
            selectedRow = grid.rows[ht.row].dataItem;
            vLoginProcess(selectedRow.hqUserId);
          }
          if( col.binding == "storeCd" && grid.rows[ht.row].dataItem.storeCd != "00000") {
            selectedRow = grid.rows[ht.row].dataItem;
            vLoginProcess(selectedRow.msUserId);
          }
          if( col.binding == "agencyNm" ) {
            selectedRow = grid.rows[ht.row].dataItem;
            vLoginProcess(selectedRow.cmUserId);
          }
        }
    });
    
    // 조회버튼 클릭 --%>
    $("#btnSearch").click(function(e){
      search(1);
    });
    
    // 페이징
    $(document).on("click", ".page", function() {
      search($(this).data("value"));
    });
    
    // 가상로그인 대상 목록 조회
    function search(index) {
      // validation 추가
      var param = {};
      param.hqOfficeCd = srchHqOfficeCd.text;
      param.hqOfficeNm = srchHqOfficeNm.text;
      param.hqStoreCd  = srchStoreCd.text;
      param.hqStoreNm  = srchStoreNm.text;
      param.clsFg      = srchClsFg.selectedValue;
      param.sysStatFg  = srchSysStatFg.selectedValue;
      param.listScale  = listScaleBox.selectedValue;
      param.curr       = index;
      
      $.postJSON("/store/manage/virtualLogin/virtualLogin/list.sb", param, function(result) {
        if(result.status === "FAIL") {
          s_alert.pop(result.message);
          return;
        }
        var list = result.data.list;
        
        if(list.length === undefined || list.length == 0) {
          s_alert.pop(result.message);
          return;
        }
        grid.itemsSource = list;
        
        page.make("#page", result.data.page.curr, result.data.page.totalPage);
      })
      .fail(function(){
          s_alert.pop("Ajax Fail");
      });
    };
    
    function vLoginProcess(value) {
      
      if ( isEmpty(value) ) {
        
        alert("해당ID가 존재하지 않습니다.");
        return false;
        
      } else {
        
        /* post */
        popupCnt = popupCnt + 1
        
        var form = document.createElement("form");
        form.setAttribute("method", "POST");
        form.setAttribute("action", "/store/manage/virtualLogin/virtualLogin/vLogin.sb");
        form.setAttribute("target", "vLogin"+ popupCnt);
        
        var formField = document.createElement("input");
        formField.setAttribute("type", "hidden");
        formField.setAttribute("name", "vUserId");
        formField.setAttribute("value", value);
        form.appendChild(formField);
        document.body.appendChild(form);
        
        var popup = window.open("", "vLogin"+ popupCnt, "width=1024, height=768");
        var crono = window.setInterval(function() {
          if (popup.closed !== false) { // !== opera compatibility reasons
            window.clearInterval(crono);
            var param = {};
            param.vUserId  = value;
            $.postJSON("/store/manage/virtualLogin/virtualLogin/vLogout.sb", param, function(result) {
              if(result.status === "FAIL") {
                s_alert.pop(result.message);
                return;
              }
            })
            .fail(function(){
              s_alert.pop("Ajax Fail");
            });
          }
        }, 250); 
        form.submit();
        document.body.removeChild(form);
      }
    };
  });
