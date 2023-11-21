/****************************************************************
 *
 * 파일명 : soldOutResve.js
 * 설  명 : 상품 품절관리(예약) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.05.30     권지현      1.0
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
var useYnComboData = [
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

// 전체품절여부
var allSoldOutYnComboData = [
  {"name": "단품", "value": "N"},
  {"name": "전체", "value": "Y"}
];

var startTimeData = [
  {"name":"00", "value":"00"},
  {"name":"01", "value":"01"},
  {"name":"02", "value":"02"},
  {"name":"03", "value":"03"},
  {"name":"04", "value":"04"},
  {"name":"05", "value":"05"},
  {"name":"06", "value":"06"},
  {"name":"07", "value":"07"},
  {"name":"08", "value":"08"},
  {"name":"09", "value":"09"},
  {"name":"10", "value":"10"},
  {"name":"11", "value":"11"},
  {"name":"12", "value":"12"},
  {"name":"13", "value":"13"},
  {"name":"14", "value":"14"},
  {"name":"15", "value":"15"},
  {"name":"16", "value":"16"},
  {"name":"17", "value":"17"},
  {"name":"18", "value":"18"},
  {"name":"19", "value":"19"},
  {"name":"20", "value":"20"},
  {"name":"21", "value":"21"},
  {"name":"22", "value":"22"},
  {"name":"23", "value":"23"}
];
/**
 * 상품정보관리 그리드 생성
 */
app.controller('soldOutResveCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('soldOutResveCtrl', $scope, $http, false));

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
  // 커스텀콤보 : 사이드메뉴-속성
  $scope._getComboDataQueryCustom('getSideMenuAttrClassCombo', 'sdattrClassCdComboData', 'S');
  // 커스텀콤보 : 사이드메뉴-선택메뉴
  $scope._getComboDataQueryCustom('getSideMenuSdselGrpCdCombo', 'sdselGrpCdComboData', 'S');
  // 콤보박스 데이터 Set
  $scope._setComboData('listScaleBox', gvListScaleBoxData);
  // 사용여부를 쓰는 콤보박스의 데이터 (조회용)
  $scope._setComboData('useYnAllComboData', useYnAllComboData);
  $scope._setComboData('kioskUseYnAllComboData', useYnAllComboData);
  // 상품등록주체 (본사/매장구분)
  $scope._setComboData('regOrgnFgComboData', regOrgnFgComboData);
  // 사용여부를 쓰는 콤보박스의 데이터
  $scope._setComboData('useYnComboData', useYnComboData);
  // 브랜드명 콤보박스
  //$scope._setComboData('hqBrandCd', brandList);
  // 상품유형 콤보박스
  $scope._getComboDataQuery('008', 'prodTypeFgComboData');
  // 판매상품여부 콤보박스
  $scope._getComboDataQuery('091', 'saleProdYnComboData');
  // 주문상품구분 콤보박스
  $scope._getComboDataQuery('092', 'poProdFgComboData');
  // 주문단위 콤보박스와 data-map
  $scope._getComboDataQueryByAuth('093', 'poUnitFgComboData', 'poUnitFgComboDataMap');
  // 과세여부 콤보박스
  $scope._getComboDataQuery('039', 'vatFgComboData');
  // 비노출여부 콤보박스
  $scope._getComboDataQuery('094', 'soldOutResveYnComboData');
  // 세트상품구분 콤보박스
  $scope._getComboDataQuery('095', 'setProdFgComboData');
  // 봉사료포함여부 콤보박스
  $scope._getComboDataQuery('058', 'prodTipYnComboData');
  // 가격관리구분 콤보박스
  $scope._getComboDataQuery('045', 'prcCtrlFgComboData');
  // 품절여부 콤보박스
  $scope._getComboDataQuery('094', 'soldOutYnComboData');
  // 비노출여부 콤보박스
  $scope._setComboData("soldOutResveYnCombo", soldOutYnAllData);
  $scope._setComboData("soldOutResveYnComboChg", soldOutYnData);
  $scope._setComboData("soldOutResveYnCombo2", soldOutYnData);
  // 매장브랜드 콤보박스
  $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList);
  // 상품브랜드 콤보박스
  $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList);

  $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
  $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
  $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
  $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
  $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
  $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
  $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
  $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹

  // 등록일자 셋팅
  $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

  // 오늘날짜
  var date = new Date();
  var year = new String(date.getFullYear());
  var month = new String(date.getMonth()+1);
  var day = new String(date.getDate());
  var time = new String(date.getHours());

  // 한자리수일 경우 0을 채워준다.
  if(month.length == 1){
    month = "0" + month;
  }
  if(day.length == 1){
    day = "0" + day;
  }
  var now = year + "" + month + "" + day;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드에서 사용하는 dataMap 초기화
    $scope.soldOutYnDataMap = new wijmo.grid.DataMap(soldOutYnData, 'value', 'name'); // 품절여부
    $scope.startTimeDataMap = new wijmo.grid.DataMap(startTimeData, 'value', 'name'); // 비노출여부
    $scope.allSoldOutYnDataMap = new wijmo.grid.DataMap(allSoldOutYnComboData, 'value', 'name');
    $scope.useYnComboDataMap = new wijmo.grid.DataMap(useYnComboData, 'value', 'name');

    // 품절여부 변경 시 체크박스 체크
    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if((item.soldOutYn != undefined && item.soldOutYn != null && item.soldOutYn != "") || (item.allSoldOutYn != undefined && item.allSoldOutYn != null && item.allSoldOutYn != "")){
          item.gChk = true;
        }
      }
      s.collectionView.commitEdit();
    });

    // 그리드 포맷
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if( col.binding === "prodCd") {
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

  // 일괄변경
  $scope.change = function() {
    for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
      var item = $scope.flex.collectionView.items[i];
      if(item.gChk){
        item.soldOutYn = $scope.soldOutYnChange;
      }
    }
    $scope.flex.collectionView.commitEdit();
    $scope.flex.collectionView.refresh();
    // 그리드 선택불가 항목처리
    $scope.setGridReadOnly();
  };

  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };

  // 상품정보관리 그리드 조회
  $scope.$on("soldOutResveCtrl", function(event, data) {
    $scope.searchSoldOutResveList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.soldOutResveStoreShow = function () {
    $scope._broadcast('soldOutResveStoreCtrl');
  };

  // 상품선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.soldOutResveProdShow = function () {
    $scope._broadcast('soldOutResveProdCtrl');
  };

  // 예약 내역 조회
  $scope.searchSoldOutResveList = function(){

    // 파라미터
    var params = {};
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }
    params.storeCds = $("#soldOutResveStoreCd").val();
    params.prodCds = $("#soldOutResveProdCd").val();
    params.soldOutYn = $scope.soldOutYn;
    params.useYn = $scope.useYn;
    params.prodClassCd = $scope.prodClassCd;
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.listScale=500;

    if(momsEnvstVal === "1" && orgnFg === "HQ"){ // 확장조회는 본사권한이면서 맘스터치만 사용
      params.momsTeam = $scope.momsTeam;
      params.momsAcShop = $scope.momsAcShop;
      params.momsAreaFg = $scope.momsAreaFg;
      params.momsCommercial = $scope.momsCommercial;
      params.momsShopType = $scope.momsShopType;
      params.momsStoreManageType = $scope.momsStoreManageType;
      params.branchCd = $scope.branchCd;
      params.momsStoreFg01 = $scope.momsStoreFg01;
    }

    if(brandUseFg === "1" && orgnFg === "HQ"){ // 본사이면서 브랜드사용시만 검색가능

      params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
      params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

      // '전체' 일때
      if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
        var momsHqBrandCd = "";
        for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
          if (momsHqBrandCdComboList[i].value !== null) {
            momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
          }
        }
        params.userBrands = momsHqBrandCd;
      }
    }

    // 조회 수행 : 조회URL, 파라미터,startTime 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/prod/soldOutResve/soldOutResve/getSoldOutResve.sb", params, function(){

      // 그리드 선택불가 항목처리
      $scope.setGridReadOnly();
    });
  };

  // 상품분류정보 팝업
  $scope.popUpProdClass = function() {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd = scope.getSelectedClass();
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
        $scope.flex.collectionView.items[i].soldOutResveYn = $scope.soldOutResveYnChg;
      }
    }
    $scope.flex.refresh();
  };

  // 삭제
  $scope.del = function (){
    $scope._popConfirm(messages["cmm.choo.delete"], function() {
      // 파라미터 설정
      var params = new Array();

      for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
        if ($scope.flex.collectionView.items[i].gChk) {

          $scope.flex.collectionView.items[i].orgStartDate = $scope.flex.collectionView.items[i].orgStartDate.replaceAll('-', ''); // 키값
          $scope.flex.collectionView.items[i].startDate = $scope.flex.collectionView.items[i].startDate.replaceAll('-', ''); // 키값
          params.push($scope.flex.collectionView.items[i]);
        }
      }

      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save('/base/prod/soldOutResve/soldOutResve/deleteSoldOutResve.sb', params, function () {
        $scope.searchSoldOutResveList();
      });
    });
  }

  // 수정
  $scope.save = function(){
    if($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["cmm.empty.data"]);
      return false;
    }

    $scope._popConfirm(messages["cmm.choo.save"], function() {
      // 파라미터 설정
      var params = new Array();
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        if($scope.flex.collectionView.items[i].gChk) {
          if(Number(now) > Number($scope.flex.collectionView.items[i].startDate.replaceAll('-', ''))) {
            $scope._popMsg(messages["soldOutResve.startDate"] + "는 " + messages["soldOutResve.resveDate.chk.msg"]);
            return false;
          } else if(Number(now) === Number($scope.flex.collectionView.items[i].startDate.replaceAll('-', ''))&& Number(time) >= Number($scope.flex.collectionView.items[i].startTime)){
            $scope._popMsg(messages["soldOutResve.startTime"] + "는 " + messages["soldOutResve.resveTime.chk.msg"]);
            return false;
          }

          $scope.flex.collectionView.items[i].orgStartDate = $scope.flex.collectionView.items[i].orgStartDate.replaceAll('-', ''); // 키값
          $scope.flex.collectionView.items[i].startDate = $scope.flex.collectionView.items[i].startDate.replaceAll('-', ''); // 키값

          params.push($scope.flex.collectionView.items[i]);
        }
      }
      // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
      $scope._save("/base/prod/soldOutResve/soldOutResve/modSoldOutResve.sb", params, function(){
        $scope.searchSoldOutResveList();
      });
    });

  };


  // 확장조회 숨김/보임
  $scope.searchAddShowChange = function(){
      if( $("#tblSearchAddShow").css("display") === 'none') {
          $("#tblSearchAddShow").show();
      } else {
          $("#tblSearchAddShow").hide();
      }
  };

  // 추가 팝업
  $scope.add = function () {
    $scope.soldOutResveAddLayer.show(true);
  };

  // 그리드 선택불가 항목처리
  $scope.setGridReadOnly = function () {
    var grid = wijmo.Control.getControl("#wjGridSoldOut");
    var rows = grid.rows;

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      var item = $scope.flex.collectionView.items[i];

      // 시작일자가 오늘날짜보다 작거나 같으면 수정불가
      if(Number(now) > Number(item.orgStartDate.replaceAll('-', '')) || item.resveFg !== '0') {
        item.gChk = false;
        rows[i].isReadOnly = true;
      } else if(Number(now) === Number(item.orgStartDate.replaceAll('-', '')) && Number(time) >= Number($scope.flex.collectionView.items[i].startTime)){
        item.gChk = false;
        rows[i].isReadOnly = true;
      }

      // 행간격 고정
      rows[i].height = 25;
    }
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {

    // 상품상세정보 팝업 핸들러 추가
    $scope.prodDetailLayer.shown.addHandler(function (s) {
      var selectedRow = $scope.flex.selectedRows[0]._data;
      setTimeout(function() {
        var params = {};
        params.prodCd = selectedRow.prodCd;
        $scope._broadcast('prodDetailCtrl', params);
      }, 50);
    });

  });
}]);
