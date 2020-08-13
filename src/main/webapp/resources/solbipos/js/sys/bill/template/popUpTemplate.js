/****************************************************************
 *
 * 파일명 : popUpTemplate.js
 * 설  명 : 출력물샘플 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.14     노현수      1.0
 *
 * **************************************************************/
// 팝업 그리드 생성
app.controller('popUpApplyTemplateCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('popUpApplyTemplateCtrl', $scope, $http, false));
  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("srchSysStatFgCombo", sysStatFgComboData);
  $scope._setComboData("srchClsFgCombo", clsFgComboData);
  // 팝업의 템플릿 값을 리턴받기 위한 설정
  $scope.srchTempltCdCombo = "";
  $scope.setSelectedCombo = function(s) {
    $scope.srchTempltCdCombo = s.selectedItem;
  };
  // 그리드 DataMap 설정
  $scope.storeFgDataMap = new wijmo.grid.DataMap([
    {id: "HQ", name: "본사"},
    {id: "MS",name: "매장"}
  ], 'id', 'name');
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

  };
  // 팝업 그리드 조회
  $scope.$on("popUpApplyTemplateCtrl", function(event, data) {

    // 선택한 출력물 종류가 무엇인지 보이도록 추가
    $("#prtClassNm").text(data);
    
    // 파라미터
    var params = {};

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sys/bill/template/unUsed/list.sb", params, function() {

    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
}]);

