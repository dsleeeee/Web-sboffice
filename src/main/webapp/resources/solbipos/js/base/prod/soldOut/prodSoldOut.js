/****************************************************************
 *
 * 파일명 : prodSoldOut.js
 * 설  명 : 상품 품절관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.02.25     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
var useYnAllComboData = [
  {"name": "전체", "value": ""},
  {"name": "사용", "value": "Y"},
  {"name": "미사용", "value": "N"}
];

// 상품등록주체 (본사/매장)
var regOrgnFgComboData = [
  {"name": "전체", "value": ""},
  {"name": "본사", "value": "H"},
  {"name": "매장", "value": "S"}
];

// 품절여부
var soldOutYnData = [
  {"name": "품절", "value": "Y"},
  {"name": "정상", "value": "N"}
];

// 품절여부
var soldOutYnAllData = [
  {"name": "전체", "value": ""},
  {"name": "품절", "value": "Y"},
  {"name": "정상", "value": "N"}
];


/* 상품상세 필수 START */
// 사용여부
var useYnComboData = [
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

// 보증금상품유형
var depositCupFgComboData2 = [
  {"name": "", "value": ""},
  // {"name": "일반", "value": "0"},
  {"name": "종이", "value": "1"},
  {"name": "플라스틱", "value": "2"},
  {"name": "다회용", "value": "3"},
  {"name": "보증컵기타", "value": "4"}
];

// KIOSK 엣지
var momsKioskEdgeComboData = [
  {"name": "미사용", "value": "0"},
  {"name": "NEW", "value": "1"},
  {"name": "BEST", "value": "2"},
  {"name": "EVENT", "value": "3"}
];

// 부가세포함여부
var vatIncldYnComboData = [
  {"name": "포함", "value": "Y"},
  {"name": "별도", "value": "N"}
];
/* 상품상세 필수 END */


/**
 * 상품정보관리 그리드 생성
 */
app.controller('prodSoldOutCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodSoldOutCtrl', $scope, $http, false));

  // 상품 상세 정보
  $scope.prodInfo = {};
  $scope.setProdInfo = function(data){
    $scope.prodInfo = data;
  };
  $scope.getProdInfo = function(){
    return $scope.prodInfo;
  };

  // 전체기간 체크박스
  $scope.isChecked = true;

  // 사용여부를 쓰는 콤보박스의 데이터 (조회용)
  $scope._setComboData('useYnAllComboData', useYnAllComboData);
  // 상품등록주체 (본사/매장구분)
  $scope._setComboData('regOrgnFgComboData', regOrgnFgComboData);
  // 브랜드명 콤보박스
  //$scope._setComboData('hqBrandCd', brandList);
  // 품절여부 콤보박스
  $scope._getComboDataQuery('094', 'soldOutYnComboData');
  // 품절여부 콤보박스
  $scope._setComboData("soldOutYnCombo", soldOutYnAllData); // 판매상품여부
  $scope._setComboData("soldOutYnComboChg", soldOutYnData); // 판매상품여부
  // 매장브랜드 콤보박스
  $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList);
  // 상품브랜드 콤보박스
  $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList);

  /* 상품상세 필수 START */
  // 커스텀콤보 : 사이드메뉴-속성
  $scope._getComboDataQueryCustom('getSideMenuAttrClassCombo', 'sdattrClassCdComboData', 'S');
  // 커스텀콤보 : 사이드메뉴-선택메뉴
  $scope._getComboDataQueryCustom('getSideMenuSdselGrpCdCombo', 'sdselGrpCdComboData', 'S');
  // 콤보박스 데이터 Set
  $scope._setComboData('listScaleBox', gvListScaleBoxData);
  // 사용여부를 쓰는 콤보박스의 데이터
  $scope._setComboData('useYnComboData', useYnComboData);
  // 상품유형 콤보박스
  $scope._getComboDataQuery('008', 'prodTypeFgComboData');
  // 판매상품여부 콤보박스
  $scope._getComboDataQuery('091', 'saleProdYnComboData');
  // 주문상품구분 콤보박스
  $scope._getComboDataQuery('092', 'poProdFgComboData');
  // 주문단위 콤보박스와 data-map
  $scope._getComboDataQueryByAuth('093', 'poUnitFgComboData', 'poUnitFgComboDataMap');
  // 세트상품구분 콤보박스
  $scope._getComboDataQuery('095', 'setProdFgComboData');
  // 봉사료포함여부 콤보박스
  $scope._getComboDataQuery('058', 'prodTipYnComboData');
  // 가격관리구분 콤보박스
  $scope._getComboDataQuery('045', 'prcCtrlFgComboData');
  // 과세여부 콤보박스
  $scope._getComboDataQuery('039', 'vatFgComboData');
  // 부가세포함여부 콤보박스
  $scope._setComboData("vatIncldYnComboData", vatIncldYnComboData);
  // 보증금상품유형 콤보박스
  $scope._setComboData('depositCupFgComboData2', depositCupFgComboData2);
  // 코너 콤보박스
  $scope._setComboData("cornrCdComboData", cornerList);
  // KIOSK 엣지 콤보박스
  $scope._setComboData('momsKioskEdgeComboData', momsKioskEdgeComboData);

  $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
  $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
  $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
  $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
  $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
  $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
  $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
  $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
  $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
  $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
  $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
  $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5
  /* 상품상세 필수 END */

  // 등록일자 셋팅
  $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드에서 사용하는 dataMap 초기화
    $scope.useYnComboDataMap = new wijmo.grid.DataMap(useYnComboData, 'value', 'name'); // 사용여부
    $scope.soldOutYnDataMap = new wijmo.grid.DataMap(soldOutYnData, 'value', 'name'); // 품절여부

    // 품절여부 변경 시 체크박스 체크
    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "soldOutYn") {
          item.gChk = true;
        }
      }
      s.collectionView.commitEdit();
    });

    // 그리드 포맷
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if( col.binding === "prodCd" || col.binding === "storeCnt") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        // 상품코드
        if( col.binding === "prodCd") {
          $scope.setProdInfo(selectedRow);
          // 수정권한이 있을때
          // 매장일땐 상품등록구분이 S인 것만 수정가능
          // 상품정보 상세 팝업
          $scope.prodDetailLayer.show();
          // 등록매장수
        }
      }
    });

    // 전체기간 체크박스 선택에 따른 날짜선택 초기화
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };

  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };

  // 상품정보관리 그리드 조회
  $scope.$on("prodSoldOutCtrl", function(event, data) {
    $scope.searchProdList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.prodSoldOutProdSelectShow = function () {
    $scope._broadcast('prodSoldOutProdSelectCtrl');
  };

  // 상품 목록 조회
  $scope.searchProdList = function(){

    if(orgnFg == "HQ"){
      if(($("#prodSoldOutStoreCd").val() === "" || $("#prodSoldOutStoreCd").val() === undefined) && ($("#prodSoldOutProdSelectCd").val() === "" || $("#prodSoldOutProdSelectCd").val() === undefined)){
        $scope._popMsg(messages["soldOut.require.select.msg"]);
        return false;
      }
    }

    // 파라미터
    var params = {};
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }
    params.storeCds = $("#prodSoldOutStoreCd").val();
    params.prodCds = $("#prodSoldOutProdSelectCd").val();
    params.soldOutYn = $scope.soldOutYn;
    params.useYn = $scope.useYn;
    params.prodClassCd = $scope.prodClassCd;
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;

    if(momsEnvstVal === "1" && orgnFg === "HQ"){ // 확장조회는 본사권한이면서 맘스터치만 사용
      params.momsTeam = $scope.momsTeam;
      params.momsAcShop = $scope.momsAcShop;
      params.momsAreaFg = $scope.momsAreaFg;
      params.momsCommercial = $scope.momsCommercial;
      params.momsShopType = $scope.momsShopType;
      params.momsStoreManageType = $scope.momsStoreManageType;
      params.branchCd = $scope.branchCd;
      params.momsStoreFg01 = $scope.momsStoreFg01;
      params.momsStoreFg02 = $scope.momsStoreFg02;
      params.momsStoreFg03 = $scope.momsStoreFg03;
      params.momsStoreFg04 = $scope.momsStoreFg04;
      params.momsStoreFg05 = $scope.momsStoreFg05;
    }

    if(brandUseFg === "1" && orgnFg === "HQ"){ // 본사이면서 브랜드사용시만 검색가능

      params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
      params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

      // '전체' 일때
      if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) { // 확인완료 1992
        var momsHqBrandCd = "";
        for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
          if (momsHqBrandCdComboList[i].value !== null) {
            momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
          }
        }
        params.userBrands = momsHqBrandCd;
      }
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/prod/soldOut/soldOut/list.sb", params, function(){});
  };

  // 상품분류정보 팝업
  $scope.popUpProdClass = function() {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd = scope.getSelectedClass();
        console.log('상품');
        console.log(prodClassCd);
        var params = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
            function(response){
              $scope.prodClassCd = prodClassCd;
              $scope.prodClassCdNm = response.data.data;
            }
        );
      }
    });
  };

  // 상품분류정보 선택취소
  $scope.delProdClass = function(){
    $scope.prodClassCd = "";
    $scope.prodClassCdNm = "";
  };

  // 일괄적용
  $scope.batchChange = function(chgGubun) {
    if($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["cmm.empty.data"]);
      return false;
    }

    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        params.push($scope.flex.collectionView.items[i]);
      }
    }
    if(params.length <= 0) {
      s_alert.pop(messages["cmm.not.select"]);
      return;
    }

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        $scope.flex.collectionView.items[i].soldOutYn = $scope.soldOutYnChg;
      }
    }
    $scope.flex.refresh();
  };

  // <-- 그리드 저장 -->
  $scope.save = function() {

    if($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["cmm.empty.data"]);
      return false;
    }

    $scope._popConfirm(messages["cmm.choo.save"], function() {
      // 파라미터 설정
      var params = new Array();
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        if($scope.flex.collectionView.items[i].gChk) {
          $scope.flex.collectionView.items[i].status = "U";
          params.push($scope.flex.collectionView.items[i]);
        }
      }
      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save("/base/prod/soldOut/soldOut/getProdSoldOutSave.sb", params, function(){
        $scope.searchProdList();
      });
    });
  };
  // <-- //그리드 저장 -->

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 상품상세정보 팝업 핸들러 추가
    $scope.prodDetailLayer.shown.addHandler(function (s) {
      var selectedRow = $scope.flex.selectedRows[0]._data;
      setTimeout(function() {
        var params = {};
          params.prodCd = selectedRow.prodCd;
          params.storeCd = selectedRow.storeCd;
        $scope._broadcast('prodDetailCtrl', params);
      }, 50);
    });

  });

  // 확장조회 숨김/보임
  $scope.searchAddShowChange = function(){
      if( $("#tblSearchAddShow").css("display") === 'none') {
          $("#tblSearchAddShow").show();
      } else {
          $("#tblSearchAddShow").hide();
      }
  };

  // 품절여부전체저장
  $scope.soldOutAllSave = function() {
    if($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["cmm.empty.data"]);
      return false;
    }

    $scope._popConfirm(messages["cmm.choo.save"], function() {
      // 파라미터 설정
      var params = new Array();
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        if($scope.flex.collectionView.items[i].gChk) {
          params.push($scope.flex.collectionView.items[i]);
        }
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save("/base/prod/soldOut/soldOut/getProdSoldOutAllSave.sb", params, function(){
        $scope.searchProdList();
      });
    });
  };

  // 엑셀다운로드
  $scope.excelDownload = function(){

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : true,
        includeColumns      : function (column) {
          // return column.visible;
          return column.binding != 'gChk';
        }
      }, '품절관리_상품_' + getCurDateTime() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  }

}]);