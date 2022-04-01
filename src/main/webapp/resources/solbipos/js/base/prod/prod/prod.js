/****************************************************************
 *
 * 파일명 : prod.js
 * 설  명 : 상품정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.24     노현수      1.0
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

/**
 * 상품정보관리 그리드 생성
 */
app.controller('prodCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodCtrl', $scope, $http, false));

  // 상품 본사통제구분 (H : 본사, S: 매장)
  // $scope.prodEnvstVal = prodEnvstVal;
  // $scope.userOrgnFg = gvOrgnFg;

  // 상품코드 채번방식
  $scope.prodNoEnvFg = prodNoEnvFg;

  // 본사에서 들어왔을때는 매장코드가 없다. (가상로그인 후, 세로고침 몇번 하면 gvOrgnFg가 바뀌는 것 예방)
  // $scope.userStoreCd = gvStoreCd;

  $scope.btnShowFg = false;
  // 단독매장
  if(hqOfficeCd == "00000") {
    $scope.btnShowFg = true;
    // 프랜 본사,매장
  } else {
    if((prodAuthEnvstVal== "ALL") || (orgnFg === 'HQ' && prodAuthEnvstVal== "HQ") || (orgnFg === 'STORE' && prodAuthEnvstVal== "STORE")) {
      $scope.btnShowFg = true;
    }
  }
  // $scope.btnShowFg = false; // 본사, 매장 모두 신규상품등록 가능하게 주석
  // if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
  //   || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
  //       $scope.btnShowFg = true;
  // }

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
  // 상품등록주체 (본사/매장구분)
  $scope._setComboData('regOrgnFgComboData', regOrgnFgComboData);
  // 사용여부를 쓰는 콤보박스의 데이터
  $scope._setComboData('useYnComboData', useYnComboData);
  // 브랜드명 콤보박스
  $scope._setComboData('hqBrandCd', brandList);
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
  // 품절여부 콤보박스
  $scope._getComboDataQuery('094', 'soldOutYnComboData');
  // 세트상품구분 콤보박스
  $scope._getComboDataQuery('095', 'setProdFgComboData');
  // 봉사료포함여부 콤보박스
  $scope._getComboDataQuery('058', 'prodTipYnComboData');
  // 가격관리구분 콤보박스
  $scope._getComboDataQuery('045', 'prcCtrlFgComboData');

  // 등록일자 셋팅
  $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드에서 사용하는 dataMap 초기화
    $scope.useYnComboDataMap = new wijmo.grid.DataMap(useYnComboData, 'value', 'name'); // 사용여부
    $scope.regFgDataMap = new wijmo.grid.DataMap(regOrgnFgComboData, 'value', 'name'); // 상품등록구분

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
          // $scope.searchProdDetail(selectedRow.prodCd);
          $scope.setProdInfo(selectedRow);
          // 수정권한이 있을때
          // if(($scope.prodEnvstVal === 'HQ' && isEmptyObject($scope.userStoreCd))
          //   || ($scope.prodEnvstVal === 'STORE' &&  !isEmptyObject($scope.userStoreCd))) {
          //     // 상품정보 수정 팝업
          //     $scope.prodModifyLayer.show();
          // } else {
          //     // 상품정보 상세 팝업
          //     $scope.prodDetailLayer.show();
          // }
          // 매장일땐 상품등록구분이 S인 것만 수정가능
          if(orgnFg == "HQ") {
            // 상품정보 수정 팝업
            $scope.prodModifyLayer.show();
          } else if(orgnFg == "STORE") {
            if(selectedRow["regFg"] === 'S') {
              // 상품정보 수정 팝업
              $scope.prodModifyLayer.show();
            } else {
              // 상품정보 상세 팝업
              $scope.prodDetailLayer.show();
            }
          }
          // 등록매장수
        } else if(col.binding === "storeCnt"){
          $scope.registProdStore(selectedRow);
        }
      }
    });

    // 전체기간 체크박스 선택에 따른 날짜선택 초기화
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;

    // 그리드 header 클릭시 정렬 이벤트 막기
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      s.allowSorting = false;
    });

  };

  // 전체기간 체크박스 클릭이벤트
  $scope.isChkDt = function() {
    $scope.srchStartDate.isReadOnly = $scope.isChecked;
    $scope.srchEndDate.isReadOnly = $scope.isChecked;
  };

  // 상품정보관리 그리드 조회
  $scope.$on("prodCtrl", function(event, data) {
    $scope.searchProdList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품 목록 조회
  $scope.searchProdList = function(){
    // 파라미터
    var params = {};
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/prod/prod/prod/list.sb", params, function(){

      var grid = wijmo.Control.getControl("#wjGridProd");
      var rows = grid.rows;

      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];

        // 주문앱 미등록상품, 배달비는 체크박스 선택 불가(삭제불가)
        if(item.prodCd === "0000000000000" || item.prodCd === "0A0000DLVFEE"){
          item.gChk = false;
          rows[i].isReadOnly = true;
        }

        // (프랜차이즈 매장만) 본사에서 등록한 상품은 체크박스 선택 불가(삭제불가)
        if (orgnFg === "STORE" && hqOfficeCd !== "00000") {
          if (item.regFg === "H") {
            item.gChk = false;
            rows[i].isReadOnly = true;
          }
        }
      }
    });
  };

  // 상세정보 팝업
  // $scope.searchProdDetail = function(prodCd) {
  //   var detailPopUp = $scope.prodDetailLayer;
  //   setTimeout(function() {
  //     detailPopUp.show(true, function (s) {
  //       // 수정 버튼 눌렀을때만
  //       if (s.dialogResult === "wj-hide-apply") {
  //         // 상품정보 수정 팝업
  //         var modifyPopUp = $scope.prodModifyLayer;
  //         modifyPopUp.show();
  //       }
  //     });
  //   }, 50);
  // };

  $scope.currPageNo;
  // 상품적용 매장 등록 팝업
  $scope.registProdStore = function(prodInfo) {
    $scope.setProdInfo(prodInfo);
    $scope.currPageNo = $scope._getPagingInfo('curr');
    $scope.prodStoreRegistLayer.show(true, function (s) {
      var regStoreGrid = agrid.getScope('regStoreCtrl');
      regStoreGrid.$apply(function(){
        regStoreGrid._gridDataInit();
      });
      var noRegStoreGrid = agrid.getScope('noRegStoreCtrl');
      noRegStoreGrid.$apply(function(){
        noRegStoreGrid._gridDataInit();
      });
      $scope._pageView('prodCtrl', $scope.currPageNo);
    });
  };

  // 신규상품 등록
  $scope.addProd = function() {
    $scope.setProdInfo({});
    $scope.prodModifyLayer.show();

    /*var modifyPopUp = $scope.prodModifyLayer;
    setTimeout(function() {
      modifyPopUp.show(true, function (s) {
        // 상품정보 등록 팝업 - 저장
        if (s.dialogResult === "wj-hide-apply") {
            // 팝업 속성에서 상품정보 get
            var params = s.data;
            // 저장수행
            //$scope._postJSONSave.withPopUp("/base/prod/prod/prod/save.sb", params, function () {
                $scope._popMsg(messages["cmm.saveSucc"]);
            //});
        }
      });
    }, 50);*/
  };
  
  // 상품 삭제
  $scope.delProd = function(){

    if($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["prod.delProdChk.msg"]); // 삭제할 상품이 없습니다.
      return false;
    }

    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if ($scope.flex.collectionView.items[i].gChk) {

        // 주문앱 미등록상품, 배달비는 체크박스 선택 불가(삭제불가)
        if($scope.flex.collectionView.items[i].prodCd === "0000000000000" || $scope.flex.collectionView.items[i].prodCd === "0A0000DLVFEE"){
          continue;
        }

        if (orgnFg === "STORE" && hqOfficeCd !== "00000") {
          if ($scope.flex.collectionView.items[i].regFg === "H") {
            continue;
          }
        }

        var obj = {};
        obj.prodCd = $scope.flex.collectionView.items[i].prodCd;
        params.push(obj);

      }
    }

    $scope._broadcast('prodDeleteCtrl', params);
    $scope.prodDeleteLayer.show(true);
    event.preventDefault();
    
  };

  // 매장 리스트 팝업(매장 상품 일괄적용을 위한)
  $scope.storeProdBatchList = function() {
    $scope.storeProdBatchListLayer.show(true);
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

    // 상품상세정보 수정 팝업 핸들러 추가
    $scope.prodModifyLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope.$apply(function() {
          $scope._broadcast('prodModifyCtrl', $scope.getProdInfo());
          // 팝업에 속성 추가 : 상품정보
          s.data = $scope.getProdInfo();
        });
      }, 50);
    });

    // 상품적용매장 등록 팝업 핸들러 추가
    $scope.prodStoreRegistLayer.shown.addHandler(function (s) {
      $("#prodTitle").text('['+$scope.getProdInfo().prodCd +'] '+$scope.getProdInfo().prodNm);
      $("#hdSideProdYn").val($scope.getProdInfo().sideProdYn);
      $("#hdSdselGrpCd").val($scope.getProdInfo().sdselGrpCd);
      $("#hdProdClassCd").val($scope.getProdInfo().prodClassCd);
    });
  });

  // 엑셀 다운로드
  $scope.excelDownload = function () {

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles: true,
        includeColumns: function (column) {
          return column.visible;
        }
      },
          messages["prod.prodInfo"] + '_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };

  // 조회조건내역 엑셀 다운로드
  $scope.excelDownloadCondition = function () {
    // 파라미터
    var params = {};
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }
    params.excelGubun = 'C';
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.prodClassCd = $scope.prodClassCd;
    params.barCd = $scope.barCd;
    params.useYn = $scope.useYn;
    params.hqBrandNm = $scope.hqBrandNm;

    $scope._popConfirm(messages["prod.totalExceDownload"], function() {
      $scope._broadcast('totalExcelCtrl', params);
    });
  };

  // 전체 엑셀 다운로드
  $scope.excelDownloadTotal = function () {
    // 파라미터
    var params = {};
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }
    params.excelGubun = 'T';

    $scope._popConfirm(messages["prod.totalExceDownload"], function() {
      $scope._broadcast('totalExcelCtrl', params);
    });
  };
}]);

app.controller('totalExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('totalExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) { };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("totalExcelCtrl", function (event, data) {
    $scope.searchProdExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품매출순위 리스트 조회
  $scope.searchProdExcelList = function (data) {
    // 파라미터
    var params       = {};
    params.startDate = data.startDate;
    params.endDate = data.endDate;
    params.excelGubun = data.excelGubun;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/prod/prod/getProdExcelList.sb", params, function() {
      if ($scope.excelFlex.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
        return false;
      }
      var excelGubun;
      if(data.excelGubun == 'C'){
        excelGubun = '(조회조건)_';
      } else if(data.excelGubun == 'T'){
        excelGubun = '(전체)_';
      }
      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
      $timeout(function () {
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
          includeColumnHeaders: true,
          includeCellStyles   : true,
          includeColumns      : function (column) {
            return column.visible;
          }
        }, messages["prod.prodInfo"] + excelGubun + getCurDateTime() +'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };
}]);