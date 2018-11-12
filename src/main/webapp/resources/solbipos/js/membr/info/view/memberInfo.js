/****************************************************************
 *
 * 파일명 : memberInfo.js
 * 설  명 : 회원정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.08     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  회원정보 그리드
 **********************************************************************/
app.controller('memberCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberCtrl', $scope, $http, true));

  console.log(genderDataMap);

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("emailRecvYn", recvDataMap);
  $scope._setComboData("smsRecvYn", recvDataMap);
  $scope._setComboData("anvType", anvrsDataMap);
  $scope._setComboData("periodType", periodDataMap);
  $scope._setComboData("gendrFg", genderDataMap);

  // 선택 회원
  $scope.selectedMember;
  $scope.setSelectedMember = function(member) {
    $scope.selectedMember = member;
  };
  $scope.getSelectedMember = function(){
    return $scope.selectedMember;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.emailRecvDataMap = new wijmo.grid.DataMap(recvDataMap, 'value', 'name');
    $scope.smsRecvDataMap = new wijmo.grid.DataMap(recvDataMap, 'value', 'name');
    $scope.useYnDataMap = new wijmo.grid.DataMap(useDataMap, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "membrNo" || col.binding === "membrNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 회원선택
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        if ( col.binding === "membrNo" ||  col.binding === "membrNm") {
          var selectedData = s.rows[ht.row].dataItem;
          $scope.setSelectedMember(selectedData);
          $scope.memberRegistLayer.show(true);
          event.preventDefault();
        }
      }
    });
  };

  // 조회 버튼 클릭
  $scope.$on("memberCtrl", function(event, data) {
    $scope.getMemberList();
    event.preventDefault();
  });

  // 회원 목록 조회
  $scope.getMemberList = function(){
    var params = {};
    $scope._inquiryMain("/membr/info/view/view/getMemberlist.sb", params, function() {
    });
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 팝업 핸들러 추가
    $scope.memberRegistLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('memberRegistCtrl', $scope.getSelectedMember());
      }, 50)
    });
  });

  // 신규회원 등록
  $scope.registMember = function(){
    $scope.setSelectedMember(null);
    $scope.memberRegistLayer.show(true);
  };

}]);

