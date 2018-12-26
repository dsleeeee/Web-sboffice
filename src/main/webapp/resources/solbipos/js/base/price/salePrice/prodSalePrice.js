/****************************************************************
 *
 * 파일명 : prodSalePrice.js
 * 설  명 : 상품별 판매가관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.20     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 판매가 선택 DropBoxDataMap
var saleAmtOptionFg = [
  {"name":"매장판매가","value":"S"},
  {"name":"본사판매가","value":"H"}
];

/**
 * 상품별 판매가관리 그리드 생성
 */
app.controller('prodSalePriceCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상품정보
  $scope.prodInfo;
  $scope.setProdInfo = function(data){
    $scope.prodInfo = data;
  };
  $scope.getProdInfo = function(){
    return $scope.prodInfo;
  };

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("saleAmtOption", saleAmtOptionFg);

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodSalePriceCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        // 판매가 변경시 다른 컬럼값도 변경
        if (col.binding === "saleUprc") {
          var item = s.rows[e.row].dataItem;
          $scope.calcAmt(item);
        }
      }
      s.collectionView.commitEdit();
    });
  };
  // 상품정보관리 그리드 조회
  $scope.$on("prodSalePriceCtrl", function(event, data) {

    $scope.searchSalePriceInfo();

    event.preventDefault();
  });

  // 상품별 판매가관리 목록 조회
  $scope.searchSalePriceInfo = function(){

    if( isEmptyObject( $("#prodCd").val()) ) {
      $scope._popMsg("상품을 선택해주세요.");
      return false;
    }

    var params = {};
    params.prodCd = $("#prodCd").val();
    params.storeCd = $("#storeCd").val();

    // console.log('params',params);

    // 상품 정보 조회
    $scope._postJSONQuery.withOutPopUp('/base/price/salePrice/prodSalePrice/getProdInfo.sb', params,
      function(response){
        // console.log('response.data.data', response.data.data);
        $scope.setProdInfo(response.data.data);

        if( isEmptyObject($scope.getProdInfo().prodCd) ) {
          $scope._popMsg(messages["cmm.error"]);
          return false;
        }
        $scope.searchSalePriceList(params);
      }
    );
  };

  // 가격 변경
  $scope.calcAmt = function(item){

    // console.log('calcAmt',item);
    var costUprc = item.costUprc;
    var saleUprc = item.saleUprc;
    var splyUprc = item.storeSplyUprc;
    var poUnitQty =  item.poUnitQty;

    item.hqMarginAmt = (splyUprc - costUprc); // 본사마진금액
    item.hqMarginRate = (splyUprc - costUprc) / costUprc * 100; // 본사마진율
    item.saleUprcAmt = (saleUprc - poUnitQty); // 현재판매금액
    item.storeMarginAmt = ((saleUprc - poUnitQty) - splyUprc); // 매장마진금액
    item.storeMarginRate = ((saleUprc - poUnitQty) - splyUprc) / (saleUprc - poUnitQty) * 100; // 매장마진율
  };

  // 판매가 그리드 조회
  $scope.searchSalePriceList = function(params){

    console.log(params);

    $scope._inquirySub('/base/price/salePrice/prodSalePrice/getProdSalePriceList.sb', params, function() {

      // 조회한 값으로 마진금액, 마진율 계산
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

        $scope.calcAmt($scope.flex.collectionView.items[i]);

        $scope.flex.collectionView.commitEdit();
      }
    }, false);
  };

  // 판매가 콤보박스 선택 이벤트
  $scope.inputSaleAmtReadOnly = false;
  $scope.setSelectedSaleAmtOption = function(s){
    if(s.selectedValue === 'S') {
      $scope.inputSaleAmtReadOnly = false;
    } else {
      $scope.inputSaleAmtReadOnly = true;
    }
  };

  // 일괄적용 버튼 클릭
  // 매장판매가 일괄적용시, 입력한 매장판매가를 적용시킴.
  // 본사판매가 일괄적용시, 조회된 본사판매가를 적용시킴.
  $scope.changeAmt = function() {

    var saleAmtOption = $scope.prodInfo.saleAmtOption;

    if( isEmptyObject( $("#prodCd").val()) ) {
      $scope._popMsg("상품을 선택해주세요.");
      return false;
    }

    if( $scope.prodInfo.saleUprc === undefined) {
      $scope._popMsg("판매가를 일괄적용할 상품을 조회해주세요.");
      return false;
    }

    if(saleAmtOption === 'S') {
      if($scope.prodInfo.inputSaleAmt === undefined || $scope.prodInfo.inputSaleAmt === '') {
        $scope._popMsg("매장판매가 일괄 적용시, 매장판매가를 입력해주세요.");
        return false;
      }
    }

    var newSaleAmt = 0;

    if(saleAmtOption === 'S') newSaleAmt = Number($scope.prodInfo.inputSaleAmt);
    else                      newSaleAmt = $scope.prodInfo.saleUprc;

    // console.log('saleAmtOption',saleAmtOption);
    // console.log('newSaleAmt',newSaleAmt);
    // console.log('$scope.prodInfo.inputSaleAmt',$scope.prodInfo.inputSaleAmt);

    var chkCount = 0;

    // 변경 판매가 적용
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].saleUprc = newSaleAmt;
        chkCount++;
      }
    }

    // 변경 판매가로 마진율, 마진금액 적용
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.calcAmt($scope.flex.collectionView.items[i]);
      }
    }

    if(chkCount == 0){
      $scope._popMsg("선택된 매장이 없습니다. 적용 매장을 선택해주세요.");
      return false;
    }

    $scope.flex.collectionView.commitEdit();
    $scope.flex.collectionView.refresh();
  };

  // 저장
  $scope.saveProdPrice = function(){

    if(priceEnvstVal === 'STORE'){
      $scope._popMsg("판매가 본사통제여부가 '본사'로 설정되었습니다.");
      return false;
    }

    // 파라미터 설정
    var params = new Array();
    var saleAmtOption = $scope.prodInfo.saleAmtOption;

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].saleUprcP !== $scope.flex.collectionView.items[i].saleUprc) {
        $scope.flex.collectionView.items.saleAmtOption = saleAmtOption;
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // console.log('params',params)

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save('/base/price/salePrice/prodSalePrice/saveProdSalePrice.sb', params);
  };

  // 상품선택 모듈 팝업 사용시 정의 (상품찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.prodShow = function () {
    $scope._broadcast('prodCtrl');
  };

  // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeShow = function () {
    $scope._broadcast('storeCtrl');
  };

}]);
