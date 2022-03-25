/****************************************************************
 *
 * 파일명 : pwdManage.js
 * 설  명 : 비밀번호 임의변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     노현수      1.0
 * 2018.12.22     김지은      1.0           Angular 로 변경
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 조회조건 DropBoxDataMap
var empOrgnFgData = [
  {"name":"전체","value":""},
  {"name":"총판/대리점","value":"A"},
  {"name":"본사","value":"H"},
  {"name":"매장","value":"S"}
];




/**********************************************************************
 *  사원목록 그리드
 **********************************************************************/
app.controller('pwdManageCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('pwdManageCtrl', $scope, $http, true));

  // 사용자의 권한구분
  $scope.userOrgnFg = gvOrgnFg;
  
  // 본사, 매장은 총판/대리점 검색 코드값 제거
  if($scope.userOrgnFg == "H" || $scope.userOrgnFg == "S"){
    empOrgnFgData.splice(1,1);
  }

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("empOrgnFg", empOrgnFgData);
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 선택 사원
  $scope.emp;
  $scope.setEmp = function(emp) {
    $scope.emp = emp;
  };
  $scope.getEmp = function(){
    return $scope.emp;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 내 콤보박스 데이터 set
    $scope.empOrgnFgDataMap = new wijmo.grid.DataMap(empOrgnFgData, 'value', 'name');
    $scope.serviceFgDataMap = new wijmo.grid.DataMap(serviceFg, 'value', 'name');
    $scope.webUseYnDataMap = new wijmo.grid.DataMap(webUseYn, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 사원 선택 가능
        if (col.binding === "userId") {
          wijmo.addClass(e.cell, 'wijLink');
        }

        // 관리자만 로그인 잠금해제 가능
        // 관리자, 총판/대리점 권한은 웹 비밀번호 잠김해제 가능하도록 변경_2022.03.23
        if($scope.userOrgnFg === "M" || $scope.userOrgnFg === "A"){
          if(col.binding === "userNm"){
            wijmo.addClass(e.cell, 'wijLink');
          }
        }
      }
    });

    // 사원선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        // 선택 사원 비밀번호 변경 팝업
        if ( col.binding === "userId" ) {
          var selectedData = s.rows[ht.row].dataItem;
          $scope.setEmp(selectedData);
          $scope.pwdChangePopupLayer.show(true, function(){
            var changeScope = agrid.getScope('pwdChangeCtrl');
            $scope.$apply(function() {
              changeScope.pwdChange = null;
            });
          });
          event.preventDefault();
        }

        // 관리자만 선택 사원 웹 비밀번호잠김해제 팝업
        // 관리자, 총판/대리점 권한은 웹 비밀번호 잠김해제 가능하도록 변경_2022.03.23
        if($scope.userOrgnFg === "M" || $scope.userOrgnFg === "A"){
          if ( col.binding === "userNm" ) {
            var selectedData = s.rows[ht.row].dataItem;
            $scope.pwdUnlockPopupLayer.show(true);
            $scope._broadcast('pwdUnlockCtrl', selectedData);
            event.preventDefault();
          }
        }

      }
    });
  };

  // 조회 버튼 클릭
  $scope.$on("pwdManageCtrl", function(event, data) {
    $scope.getEmpList();
    event.preventDefault();
  });

  // 회원 목록 조회
  $scope.getEmpList = function(){
    var params = {};
    $scope._inquiryMain("/store/manage/pwdManage/pwdManage/list.sb", params, function() {
    });
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 비밀번호 변경 팝업 핸들러 추가
    $scope.pwdChangePopupLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('pwdChangeCtrl', $scope.getEmp());
      }, 50)
    });
  });
}]);
