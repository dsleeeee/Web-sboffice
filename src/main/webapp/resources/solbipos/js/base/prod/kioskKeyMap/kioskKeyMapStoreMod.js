/****************************************************************
 *
 * 파일명 : kioskKeyMapStoreMod.js
 * 설  명 : 매장수정허용분류 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.10.21     권지현      1.0
 *
 * **************************************************************/
var useYnA = [
  {"name":"전체","value":""},
  {"name":"사용","value":"Y"},
  {"name":"미사용","value":"N"}
];

var useYn = [
  {"name":"사용","value":"Y"},
  {"name":"미사용","value":"N"}
];

// 팝업 그리드 생성
app.controller('storeModCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeModCtrl', $scope, $http, false));

  $scope._setComboData("storeModYnCombo", useYnA);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
  };

  // 팝업 그리드 조회
  $scope.$on("storeModCtrl", function(event, data) {
    $scope._setComboData("tuClsTypeCombo", kioskTuClsTypeListAll);
    $scope.getStoreModGrpList();
    event.preventDefault();
  });

  $scope.getStoreModGrpList = function (){
    // 파라미터
    var params = {};
    params.tukeyGrpCd = $scope.tukeyGrp;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getStoreModGrpList.sb", params, function() {});
  };

  $scope.saveStoreModGrp = function () {

    $scope._popConfirm(messages["cmm.choo.save"], function() {
      // 파라미터 설정
      var params = new Array();
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save("/base/prod/kioskKeyMap/kioskKeyMap/saveStoreModGrp.sb", params, function(){

        $scope.storeModLayer.hide();

        var scope = agrid.getScope("kioskKeyMapRegistCtrl");
        scope.btnSearchCls();
      });
    });
  }
}]);