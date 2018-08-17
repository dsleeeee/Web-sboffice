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
$(document).ready(function () {

  var srchHqOfficeCd = wcombo.genInputText("#srchHqOfficeCd", 5, "");
  var srchHqOfficeNm = wcombo.genInputText("#srchHqOfficeNm", 50, "");
  var srchStoreCd = wcombo.genInputText("#srchStoreCd", 7, "");
  var srchStoreNm = wcombo.genInputText("#srchStoreNm", 50, "");
  var srchClsFg = wcombo.genCommonBox("#srchClsFg", clsFg);
  var srchSysStatFg = wcombo.genCommonBox("#srchSysStatFg", sysStatFg);

  var gridData =
    [
      {binding: "hqOfficeCd", header: messages["virtualLogin.hqOfficeCd"], width: "*"},
      {binding: "hqOfficeNm", header: messages["virtualLogin.hqOfficeNm"], width: "*"},
      {binding: "hqUserId", header: messages["virtualLogin.hqUserId"], visible: false, width: "*"},
      {binding: "storeCd", header: messages["virtualLogin.storeCd"], width: "*"},
      {binding: "storeNm", header: messages["virtualLogin.storeNm"], width: "*"},
      {binding: "msUserId", header: messages["virtualLogin.msUserId"], visible: false, width: "*"},
      {binding: "clsFgNm", header: messages["virtualLogin.clsFgNm"], width: "*"},
      {binding: "sysStatFgNm", header: messages["virtualLogin.sysStatFgNm"], width: "*"},
      {binding: "ownerNm", header: messages["virtualLogin.ownerNm"], width: "*"},
      {binding: "telNo", header: messages["virtualLogin.telNo"], width: "*"},
      {binding: "mpNo", header: messages["virtualLogin.mpNo"], width: "*"},
      {binding: "agencyNm", header: messages["virtualLogin.agencyNm"], width: "*"},
      {binding: "cmUserId", header: messages["virtualLogin.cmUserId"], visible: false, width: "*"},
      {binding: "sysOpenDate", header: messages["virtualLogin.sysOpenDate"], width: "*"},
      {binding: "sysClosureDate", header: messages["virtualLogin.sysClosureDate"], width: "*"}
    ];
  var grid = wgrid.genGrid("#theGrid", gridData);
  var listScaleBox = wcombo.genCommonBox("#listScaleBox", listScaleBoxData);

  // 그리드 포맷
  grid.formatItem.addHandler(function (s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if (col.binding == "hqOfficeCd" && item.hqOfficeCd != "00000") {
        wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
      }
      if (col.binding == "storeCd" && item.storeCd != "00000") {
        wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
      }
      if (col.binding == "agencyNm") {
        wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
      }
    }
  });

  // 그리드 선택 이벤트
  grid.addEventListener(grid.hostElement, 'click', function(e) {
    var ht = grid.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      var selectedRow = grid.rows[ht.row].dataItem;
      if (col.binding === "hqOfficeCd" && selectedRow.hqOfficeCd != "00000") {
        vLoginProcess(selectedRow.hqUserId);
      } else if (col.binding == "storeCd" && selectedRow.storeCd != "00000") {
        vLoginProcess(selectedRow.msUserId);
      } else if (col.binding == "agencyNm") {
        vLoginProcess(selectedRow.cmUserId);
      }
    }
  });

  // 조회버튼 클릭 --%>
  $("#btnSearch").click(function (e) {
    search(1);
  });

  // 페이징
  $(document).on("click", ".page", function () {
    search($(this).data("value"));
  });

  // 가상로그인 대상 목록 조회
  function search(index) {
    // validation 추가
    var param = {};
    param.hqOfficeCd = srchHqOfficeCd.text;
    param.hqOfficeNm = srchHqOfficeNm.text;
    param.hqStoreCd = srchStoreCd.text;
    param.hqStoreNm = srchStoreNm.text;
    param.clsFg = srchClsFg.selectedValue;
    param.sysStatFg = srchSysStatFg.selectedValue;
    param.listScale = listScaleBox.selectedValue;
    param.curr = index;

    $.postJSON("/store/manage/virtualLogin/virtualLogin/list.sb", param,
      function (result) {
        if (result.status === "FAIL") {
          s_alert.pop(result.message);
          return;
        }
        var list = result.data.list;
        grid.itemsSource = new wijmo.collections.CollectionView(list);

        page.make("#page", result.data.page.curr, result.data.page.totalPage);
        if (list.length === undefined || list.length == 0) {
          grid.itemsSource = new wijmo.collections.CollectionView([]);
          s_alert.pop(result.message);
          return;
        }
      },
      function () {
        s_alert.pop("Ajax Fail");
      }
    )
  };

  // 가상로그인 수행
  // 최초 가상로그인으로 로그인시에는 vLoginId 가 아닌 vUserId 파라미터로 로그인 후 vLoginId로 사용한다.
  function vLoginProcess(value) {

    if (isEmpty(value)) {
      alert(messages["virtualLogin.vLogin.fail"]);
      return false;
    } else {

      /* post */
      popupCnt = popupCnt + 1

      var form = document.createElement("form");
      form.setAttribute("method", "POST");
      form.setAttribute("action", "/store/manage/virtualLogin/virtualLogin/vLogin.sb");
      form.setAttribute("target", "vLogin" + popupCnt);

      var formField = document.createElement("input");
      formField.setAttribute("type", "hidden");
      formField.setAttribute("name", "vUserId");
      formField.setAttribute("value", value);
      form.appendChild(formField);
      document.body.appendChild(form);

      var popup = window.open("", "vLogin" + popupCnt, "width=1024, height=768");
      var crono = window.setInterval(function () {
        if (popup.closed !== false) { // !== opera compatibility reasons
          window.clearInterval(crono);
          var param = {};
          param.vUserId = value;
          $.postJSON("/store/manage/virtualLogin/virtualLogin/vLogout.sb", param,
            function (result) {
              if (result.status === "FAIL") {
                s_alert.pop(result.message);
                return;
              }
            },
            function () {
              s_alert.pop("Ajax Fail");
            }
          )
        }
      }, 250);
      form.submit();
      document.body.removeChild(form);
    }
  };
});
