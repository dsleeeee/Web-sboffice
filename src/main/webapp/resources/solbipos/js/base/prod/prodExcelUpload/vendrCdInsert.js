/****************************************************************
 *
 * 파일명 : vendrCdInsert.js
 * 설  명 : 상품엑셀업로드 > 기초마스터등록 > 거래처탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.23     권지현      1.0
 *
 * **************************************************************/

app.controller('vendrCdInsertCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('vendrCdInsertCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.vendorFgDataMap = new wijmo.grid.DataMap(vendorFgData, 'value', 'name'); // 거래처구분
    $scope.vatIncldYnDataMap = new wijmo.grid.DataMap(vatIncldYnData, 'value', 'name'); // 부가세포함여부
  };

  // <-- 검색 호출 -->
  $scope.$on("vendrCdInsertCtrl", function(event, data) {
    $scope.btnDis = data;
    $scope.searchVendrCdInsert();
    event.preventDefault();
  });

  // 상품분류탭
  $scope.changeprodClassCdTab = function(){
    $scope.vendrCdInsertLayer.hide();

    $scope.prodClassCdInsertLayer.show(true);
    $scope._broadcast('prodClassCdInsertCtrl', true);
  };

  $scope.searchVendrCdInsert = function (){
    // 파라미터
    var params       = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/base/prod/prodExcelUpload/prodExcelUpload/getVendrCdInsertList.sb", params, function() {}, false);
  }


  $scope.vendrCdSave = function () {

    if ($scope.vendrFlex.rows.length <= 0) {
      $scope._popMsg(messages["prodExcelUpload.masterInsert.None"]);
      return false;
    }

    var params = new Array();

    for(var i = 0; i < $scope.vendrFlex.collectionView.items.length; i++ ){
      var item = $scope.vendrFlex.collectionView.items[i];

      if(item.ownerNm == undefined || item.ownerNm == ""){
        $scope._popMsg(messages["prodExcelUpload.ownerNm.Chk"]);
        return false;
      } else {
        params.push(item);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._postJSONSave.withPopUp("/base/prod/prodExcelUpload/prodExcelUpload/vendrCdSave.sb", params, function () {
      if($("#vProdClassTab").is(":visible")){
        $scope.vendrCdInsertLayer.hide();

        $scope.prodClassCdInsertLayer.show(true);
        $scope._broadcast('prodClassCdInsertCtrl', false);
      } else {
        $scope.vendrCdInsertLayer.hide();
      }
    });

  }
}]);
