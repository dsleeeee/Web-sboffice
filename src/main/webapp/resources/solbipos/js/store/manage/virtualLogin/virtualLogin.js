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
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 가상로그인 그리드 생성
 */
app.controller('gridCtrl',  ['$scope', '$http', function ($scope, $http, $rootScope) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('gridCtrl', $scope, $http, true));
  // 가상로그인 개수
  $scope.popupCnt = 0;
  // 콤보박스 데이터 Set
  $scope.setComboData("listScaleBox", gvListScaleBoxData);
  $scope.setComboData("srchClsFg", clsFg);
  $scope.setComboData("srchStatFg", sysStatFg);
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("gridCtrl");
    // 그리드 포맷
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        // 본사
        if (col.binding === "hqOfficeCd" && item.hqOfficeCd !== "00000") {
          if ( item.orgnfg === "M" ) {
            wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
          }
        }
        // 매장
        if (col.binding === "storeCd" && item.storeCd !== "00000") {
          if ( item.orgnfg !== "S" ) {
            wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
          }
        }
        // 대리점
        if (col.binding === "agencyNm") {
          if ( item.orgnfg === "M" ) {
            wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
          }
        }
      }
    });
    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "hqOfficeCd" && selectedRow.hqOfficeCd !== "00000") {
          if ( selectedRow.orgnfg === "M" ) {
            $scope.vLoginProcess(selectedRow.hqUserId);
          }
        } else if (col.binding === "storeCd" && selectedRow.storeCd !== "00000") {
          if ( selectedRow.orgnfg !== "S" ) {
            $scope.vLoginProcess(selectedRow.msUserId);
          }
        } else if (col.binding === "agencyNm") {
          if ( selectedRow.orgnfg === "M" ) {
            $scope.vLoginProcess(selectedRow.cmUserId);
          }
        }
      }
    });
  };
  // 가상로그인 그리드 조회
  $scope.$on("gridCtrl", function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/store/manage/virtualLogin/virtualLogin/list.sb", params, function() {

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 가상로그인 수행
  // 최초 가상로그인으로 로그인시에는 vLoginId 가 아닌 vUserId 파라미터로 로그인 후 vLoginId로 사용한다.
  $scope.vLoginProcess = function(value) {

    if (isEmpty(value)) {
      alert(messages["virtualLogin.vLogin.fail"]);
      return false;
    } else {

      /* post */
      $scope.popupCnt = $scope.popupCnt + 1;

      var form = document.createElement("form");
      form.setAttribute("method", "POST");
      form.setAttribute("action", "/store/manage/virtualLogin/virtualLogin/vLogin.sb");
      form.setAttribute("target", "vLogin" + $scope.popupCnt);

      var formField = document.createElement("input");
      formField.setAttribute("type", "hidden");
      formField.setAttribute("name", "vUserId");
      formField.setAttribute("value", value);
      form.appendChild(formField);
      document.body.appendChild(form);

      var popup = window.open("", "vLogin" + $scope.popupCnt, "width=1024, height=768");
      var crono = window.setInterval(function () {
        if (popup.closed !== false) { // !== opera compatibility reasons
          window.clearInterval(crono);
          var param = {};
          param.vUserId = value;
          $.postJSON("/store/manage/virtualLogin/virtualLogin/vLogout.sb", param,
            function (result) {

            },
            function (result) {
              s_alert.pop(result.message);
              return;
            }
          );
        }
      }, 250);
      form.submit();
      document.body.removeChild(form);
    }
  };
}]);
