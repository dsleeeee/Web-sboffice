/****************************************************************
 *
 * 파일명 : prodClassCdInsert.js
 * 설  명 : 상품엑셀업로드 > 기초마스터등록 > 상품분류 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.23     권지현      1.0
 *
 * **************************************************************/

app.controller('prodClassCdInsertCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodClassCdInsertCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // <-- 검색 호출 -->
  $scope.$on("prodClassCdInsertCtrl", function(event, data) {
    $scope.btnDis = data;
    $scope.searchProdClassCdInsert();
    event.preventDefault();
  });

  // 거래처탭
  $scope.changeVendrCdTab = function(){
    $scope.prodClassCdInsertLayer.hide();

    $scope.vendrCdInsertLayer.show(true);
    $scope._broadcast('vendrCdInsertCtrl', true);
  };

  $scope.searchProdClassCdInsert = function (){
    // 파라미터
    var params       = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/base/prod/prodExcelUpload/prodExcelUpload/getProdClassCdInsertList.sb", params, function() {}, false);
  };

  $scope.prodClassCdSave = function () {

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["prodExcelUpload.masterInsert.None"]);
      return false;
    }

    var params = new Array();

    for(var i = 0; i < $scope.prodClassFlex.collectionView.items.length; i++ ) {
      var item = $scope.prodClassFlex.collectionView.items[i];

      // 대분류
      if (params.find(function (data) {return data.level1 == item.level1}) == undefined) {
        item.prodClassNm = item.level1;
        item.pProdClassCd = '00000';
        item.clsLevelCd = '1';
        params.push(item);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._postJSONSave.withPopUp("/base/prod/prodExcelUpload/prodExcelUpload/prodClassCdSave.sb", params, function () {
      $scope.prodClassCdSaveLevel2();
    });

   };

  $scope.prodClassCdSaveLevel2 = function (){
    var params = new Array();
    for(var i = 0; i < $scope.prodClassFlex.collectionView.items.length; i++ ) {
      var item = $scope.prodClassFlex.collectionView.items[i];
      if (item.level2) {
        // 중분류
        if (params.find(function (data) {return data.level12 == item.level12}) == undefined) {
          item.prodClassNm = item.level2;
          item.pProdClassCd = item.level1;
          item.clsLevelCd = '2';
          params.push(item);
        }
      }
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._postJSONSave.withPopUp("/base/prod/prodExcelUpload/prodExcelUpload/prodClassCdSave.sb", params, function () {
      $scope.prodClassCdSaveLevel3();
    });
  }

  $scope.prodClassCdSaveLevel3 = function (){

    var params = new Array();
    for(var i = 0; i < $scope.prodClassFlex.collectionView.items.length; i++ ) {
      var item = $scope.prodClassFlex.collectionView.items[i];
      if (item.level3) {
        // 소분류
        if (params.find(function (data) {return data.level123 == item.level123}) == undefined) {
          item.prodClassNm = item.level3;
          item.pProdClassCd = item.level2;
          item.clsLevelCd = '3';
          params.push(item);
        }
      }
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._postJSONSave.withPopUp("/base/prod/prodExcelUpload/prodExcelUpload/prodClassCdSave.sb", params, function () {
      if($("#pVendrTab").is(":visible")){
        $scope.prodClassCdInsertLayer.hide();

        $scope.vendrCdInsertLayer.show(true);
        $scope._broadcast('vendrCdInsertCtrl', false);
      } else {
        $scope.prodClassCdInsertLayer.hide();
      }
    });

  };
}]);
