/****************************************************************
 *
 * 파일명 : kioskKeyMapClsTypeNm.js
 * 설  명 : 키맵그룹명 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.11.08     권지현      1.0
 *
 * **************************************************************/

// 팝업 그리드 생성
app.controller('clsTypeNmCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('clsTypeNmCtrl', $scope, $http, false));

  $scope._setComboData("clsTypeNmYnCombo", useYnA);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
  };

  // 팝업 그리드 조회
  $scope.$on("clsTypeNmCtrl", function(event, data) {
    $scope._setComboData("tuClsTypeCombo", kioskTuClsTypeListAll);
    $scope.getClsTypeNmList(data);
    event.preventDefault();
  });

  $scope.getClsTypeNmList = function (data){

    // 파라미터
    var params = {};
    params.posNo = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getClsTypeList.sb", params, function() {});
  };

  $scope.saveClsTypeNm = function () {

    $scope._popConfirm(messages["cmm.choo.save"], function() {

      // 파라미터 설정
      var params = new Array();
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save("/base/prod/kioskKeyMap/kioskKeyMap/saveClsType.sb", params, function(){

        $scope.clsTypeNmLayer.hide();

        var scope = agrid.getScope("kioskKeyMapRegistCtrl");
        scope.setTuClsDropdownList();
      });
    });
  }
}]);