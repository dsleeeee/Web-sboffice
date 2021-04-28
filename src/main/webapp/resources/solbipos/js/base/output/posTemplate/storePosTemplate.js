/****************************************************************
 *
 * 파일명 : storePosTemplate.js
 * 설  명 : 상품적용 매장등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.15     권지현      1.0
 *
 * **************************************************************/

/**
 *  상품적용매장 그리드 생성
 */
app.controller('storePosTemplateCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storePosTemplateCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

    $scope.searchRegStore();
  };

  // 콤보박스 데이터 Set
  $scope._setComboData("srchSysStatFg", sysStatFg);

  // 등록 매장 그리드 조회
  $scope.$on("storePosTemplateCtrl", function(event, data) {
    $scope.searchRegStore();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 선택 매장 전송
  $scope.storeApply = function() {
    var templateScope = agrid.getScope("templateCtrl");
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      var item = $scope.flex.collectionView.items[i];
      if (item.gChk) {
        item.storeCd = item.storeCd;
        item.prtClassCd = $("#hdPrtClassCd").val();
        item.templtRegFg = $("#hdApplyTempltRegFg").val();
        item.templtCd = $("#hdTempltCd").val();
        params.push(item);
      }
    }
    $scope._postJSONSave.withPopUp("/base/output/posTemplate/template/applyToStoreReal.sb", params, function (response) {

      var result = response.data.data;

      if(result === ""){
        $scope._popMsg(messages["cmm.registFail"]);
      }else{
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.storePosTemplateLayer.hide();
      }

    });
  };

  // 매장 조회
  $scope.searchRegStore = function(){
    var params        = {};
    params.hqOfficeCd = hqOfficeCd;
    params.storeCd    = $scope.storeCd;
    params.storeNm    = $scope.storeNm;
    params.sysStatFg  = $scope.sysStatFg;

    $scope._inquirySub("/base/output/posTemplate/template/getRegStoreList.sb", params, function() {}, false);
  };

}]);
