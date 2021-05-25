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

// 가격관리구분
var prcCtrlFgData = [
  {"name":"본사","value":"H"},
  {"name":"매장","value":"S"}
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
    $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

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

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        // 체크박스
        if (col.binding === "gChk" || col.binding === "saleUprc") {
          var item = s.rows[e.row].dataItem;

          // 값이 있으면 링크 효과
          if (item[("prcCtrlFg")] === 'S') {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);
            item[("gChk")] = false; // 전체 체크시 오류

            // Attribute 의 변경사항을 적용.
            e.cell.outerHTML = e.cell.outerHTML;
          }
        }
      }
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

    // 상품 정보 조회
    $scope._postJSONQuery.withOutPopUp('/base/price/salePrice/prodSalePrice/getProdInfo.sb', params,
      function(response){
        // console.log('response.data.data', response.data.data);
        $scope.setProdInfo(response.data.data);

        if( isEmptyObject($scope.getProdInfo().prodCd) ) {
          $scope._popMsg(messages["cmm.error"]);
          return false;
        }
        $scope.searchSalePriceList();
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
  $scope.searchSalePriceList = function(){

    var params = {};
    params.prodCd = $("#prodCd").val();
    params.storeCd = $("#storeCd").val();

    // console.log(params);

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

    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
      if ($scope.flex.collectionView.items[i].gChk) {
        // PRC_CTRL_FG 가격관리구분 H인 상품만 수정가능
        if ($scope.flex.collectionView.items[i].prcCtrlFg === "S") {
          $scope._popMsg(messages["salePrice.prcCtrlFgStoreBlank"]); // 가격관리구분이 '매장'인 상품은 수정할 수 없습니다.
          return false;
        }
      }
    }

    // isInteger는 es6 임. ie 11 에서는 안되므로 함수 만듬.
    Number.isInteger = Number.isInteger || function(value) {
      return typeof value === "number" &&
          isFinite(value) &&
          Math.floor(value) === value;
    };

    // 파라미터 설정
    var params = new Array();
    var saleAmtOption = $scope.prodInfo.saleAmtOption;

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        if ($scope.flex.collectionView.items[i].saleUprcP !== $scope.flex.collectionView.items[i].saleUprc) {

          // 변경판매가 숫자만 입력가능하도록
          if($scope.flex.collectionView.items[i].saleUprc === "" || $scope.flex.collectionView.items[i].saleUprc === null) {
            $scope._popMsg(messages["salePrice.saleUprcBlank"]);
            return false;
          } else {
            if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].saleUprc)) == false){ // 소수점있으면 거름
              $scope.flex.collectionView.items[i].saleUprc = "";
              $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
              return false;
            } else {
              // 숫자만 입력
              var numchkexp = /[^0-9]/g;
              if (numchkexp.test($scope.flex.collectionView.items[i].saleUprc)) { // 음수
                var numchkexp2 = /^-[0-9]/g;
                if (numchkexp2.test($scope.flex.collectionView.items[i].saleUprc)) {
                } else if((numchkexp2.test($scope.flex.collectionView.items[i].saleUprc) == false)){
                  $scope.flex.collectionView.items[i].saleUprc = "";
                  $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                  return false;
                }
              } else if($scope.flex.collectionView.items[i].saleUprc >= 1000000000){ // 양수 max값
                $scope.flex.collectionView.items[i].saleUprc = "";
                $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                return false;
              }
            }
          }

          $scope.flex.collectionView.items.saleAmtOption = saleAmtOption;
          params.push($scope.flex.collectionView.items[i]);
        }
      }
    }

    // console.log('params',params)

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save('/base/price/salePrice/prodSalePrice/saveProdSalePrice.sb', params, function(){
      $scope.searchSalePriceList();
    });
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
