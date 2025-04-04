/****************************************************************
 *
 * 파일명 : barcd.js
 * 설  명 : 상품바코드등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.07.01     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();
app.config(['$qProvider', function ($qProvider) {
  $qProvider.errorOnUnhandledRejections(false);
}]);

// 사용여부 (전체)
var useYnAllComboData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
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
 *  그리드 생성
 */
app.controller('barcdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('barcdCtrl', $scope, $http, false));

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

    // 그리드 포맷
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding === "prodCd") {
          wijmo.addClass(e.cell, 'wijLink');
        }

        // 검증결과
        if (col.binding === "result") {

          // 값이 있으면 링크 효과
          if (item[("result")] !== '검증성공') {
            wijmo.addClass(e.cell, 'wij_gridText-red');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          }
        }
        // 프차매장 본사애서 등록한 상품은 수정 못 하도록
        if(col.binding === "barCd"){
          if(item.regFg === "H" && orgnFg === "STORE" && hqOfficeCd !== '00000'){

            wijmo.addClass(e.cell, 'wj-custom-readonly');
            wijmo.setAttribute(e.cell, 'aria-readonly', true);
          }
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
          // 상품정보 상세 팝업
          $scope.prodDetailLayer.show();
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

  // 자동생성
  $scope.barCdAutoSet = function() {

    if($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["cmm.empty.data"]);
      return false;
    }

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      var item = $scope.flex.collectionView.items[i];
      if(item.gChk){
        item.barCd = item.prodCd + numberPad(Math.floor(Math.random() * 100), 2);
      }
    }

    $scope.flex.refresh();
  }

  function numberPad(n, width) {
    n = n + '';
    return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
  }

  // 상품정보관리 그리드 조회
  $scope.$on("barcdCtrl", function(event, data) {
    $scope.searchProdList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품 목록 조회
  $scope.searchProdList = function(){
    if($scope.flex.columns[1].binding == "result"){
      $scope.flex.columns[1].visible = false;
    }
    // 파라미터
    var params = {};
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/prod/prodBarcd/list.sb", params, function (){

      var grid = wijmo.Control.getControl("#wjGridProdBarcd");
      var rows = grid.rows;

      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];

        if(item.regFg === "H" && orgnFg === "STORE" && hqOfficeCd !== '00000'){
          item.gChk = false;
          rows[i].isReadOnly = true;
        }
      }
    });
  };

  // 엑셀업로드 조회
  $scope.searchExcelList = function(){
    $scope._gridDataInit();
    if($scope.flex.columns[1].binding == "result"){
      $scope.flex.columns[1].visible = true;
    }
    var params = {};
    $scope._inquiryMain("/base/prod/prodBarcd/getExcelList.sb", params);
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

  // <-- 엑셀업로드 -->
  $scope.excelUpload = function(){
    // 상품엑셀업로드 팝업
    $("#excelUpFile").val('');
    $("#excelUpFile").trigger('click');
  };
  // <-- //엑셀업로드 -->

  // 바코드 삭제
  $scope.delete = function() {
    var params = [];
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      if ($scope.flex.collectionView.items[i].gChk === true) {
        $scope.flex.itemsSource.removeAt(i);
      }
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }
    console.log(params);
    if (params.length > 0) {
      $.postJSONArray("/base/prod/prodBarcd/chkBarCds.sb", params, function(result) {
            $scope._save("/base/prod/prodBarcd/saveBarcd.sb", params,
                function(){
                  $scope._popMsg(messages["cmm.saveSucc"]);
                  $scope.searchProdList();
                }
            );
          },
          function (result) {
            $scope._popMsg(result.data[0]);
            return false;
          });
    } else {
      $scope._popMsg(messages["cmm.not.modify"]);
    }
  };

  // 변경내역 저장
  $scope.save = function(){
    var params      = [];

    if($scope.flex.columns[1].visible){ // 검증결과 컬럼이 보일때(엑셀업로드일때)

      $scope.stepCnt = 100;   // 한번에 DB에 저장할 숫자 세팅
      $scope.progressCnt = 0; // 처리된 숫자

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        if($scope.flex.collectionView.itemsEdited[i].barCd.getByteLengthForOracle() > 40){
          $scope._popMsg("[" + $scope.flex.collectionView.itemsEdited[i].prodCd + "]" + messages["barcd.maxBarCd.msg"]);
          return false;
        }

        var numberAlphabet = /[^A-za-z0-9]/g;
        if(numberAlphabet.test($scope.flex.collectionView.itemsEdited[i].barCd)){
          $scope._popMsg("[" + $scope.flex.collectionView.itemsEdited[i].prodCd + "]" + messages["barcd.inChkBarCd.msg"]);
          return false;
        }
      }

      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        if($scope.flex.collectionView.items[i].result === "검증성공"){
          params.push($scope.flex.collectionView.items[i]);
        }
      }

      $scope._popConfirm(messages["barcd.saveConfirm"], function() {  // 검증을 통과한 상품을 저장하시겠습니까?
          // 저장
          $scope.saveSave(params);
      });
    } else {  // 일반 저장일때
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        if($scope.flex.collectionView.itemsEdited[i].barCd.getByteLengthForOracle() > 40){
          $scope._popMsg("[" + $scope.flex.collectionView.itemsEdited[i].prodCd + "]" + $scope.flex.collectionView.itemsEdited[i].prodNm + messages["barcd.maxBarCd.msg"]);
          return false;
        }

        var numberAlphabet = /[^A-za-z0-9]/g;
        if(numberAlphabet.test($scope.flex.collectionView.itemsEdited[i].barCd)){
          $scope._popMsg("[" + $scope.flex.collectionView.itemsEdited[i].prodCd + "]" + $scope.flex.collectionView.itemsEdited[i].prodNm + messages["barcd.inChkBarCd.msg"]);
          return false;
        } else {
          for(var j = 0; j < $scope.flex.collectionView.itemsEdited.length; j++){
            if(i != j){
              if($scope.flex.collectionView.itemsEdited[i].barCd === $scope.flex.collectionView.itemsEdited[j].barCd && $scope.flex.collectionView.itemsEdited[j].barCd.length !== 0){
                $scope._popMsg(messages["barcd.chkBarCd.msg2"] + "["+ $scope.flex.collectionView.itemsEdited[i].barCd + "]");
                return false;
              }
            }
          }
        }
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        $scope.flex.collectionView.itemsEdited[i].barCd = $scope.flex.collectionView.itemsEdited[i].barCd.trim().removeEnter();

        if($scope.flex.collectionView.itemsEdited[i].barCd !== null && $scope.flex.collectionView.itemsEdited[i].barCd !== ""){
          $scope.flex.collectionView.itemsEdited[i].status = "U";
          params.push($scope.flex.collectionView.itemsEdited[i]);
        }
      }

      if (params.length > 0) {
        $.postJSONArray("/base/prod/prodBarcd/chkBarCds.sb", params, function(result) {
              $scope._save("/base/prod/prodBarcd/saveBarcd.sb", params,
                  function(){
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    $scope.searchProdList();
                  }
              );
            },
            function (result) {
              $scope._popMsg(result.data[0]);
              return false;
            });
      } else {
        $scope._popMsg(messages["cmm.not.modify"]);
      }
    }
  };

    // 저장
    $scope.saveSave = function(jsonData) {

        $scope.totalRows = jsonData.length;
        var params = [];
        var msg = '';

        // 저장 시작이면 업로드 중 팝업 오픈
        if ($scope.progressCnt === 0) {
            $timeout(function () {
                $scope.excelUploadingPopup(true);
                $("#progressCnt").html($scope.progressCnt);
                $("#totalRows").html($scope.totalRows);
            }, 10);
        }

        // stepCnt 만큼 데이터 DB에 저장
        var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
        for (var i = $scope.progressCnt; i < loopCnt; i++) {
            var item = jsonData[i];

            item.progressCnt = $scope.progressCnt;

            params.push(item);
        }

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/base/prod/prodBarcd/saveBarcdExcel.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
                    // 조회
                    $scope.searchProdList();
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
            if (parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
                // 처리된 숫자 변경
                $scope.progressCnt = loopCnt;
                // 팝업의 progressCnt 값 변경
                $("#progressCnt").html($scope.progressCnt);
                $scope.saveSave(jsonData);
            }
        });
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
    })
  });

  // 바코드자동생성
  $scope.autoBarcd = function (){
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["barcd.rows.null"]); // 그리드가 비어있습니다.<br>조회 후 바코드자동생성을 시도해주세요.
      return false;
    }
  };

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
          messages["barcd.prodBarcd"] + '_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };

  // 조회조건내역 엑셀다운로드
  $scope.excelDownloadCondition = function(){
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
    params.barcdYn = $scope.barcdYn;

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

  // 양식 다운로드
  $scope.sampleDownload = function () {
    // 파라미터
    var params = {};
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }
    params.excelGubun = 'S';

    $scope._popConfirm(messages["prod.totalExceDownload"], function() {
      $scope._broadcast('totalExcelCtrl', params);
    });
  };

    // 업로딩 팝업 열기
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['empCardInfo.excelUploading'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 업로드 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            // html 적용
            $scope._loadingPopup.content.innerHTML = innerHtml;
            // 팝업 show
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
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

    var columns = $scope.excelFlex.columns;

    if(data.excelGubun === 'S'){
      for(var i=0; i<columns.length; i++){
        if(columns[i].binding === 'prodNm' || columns[i].binding === 'barcdOld'){
          columns[i].visible = false;
        }
      }
    } else {
      for(var i=0; i<columns.length; i++){
        if(columns[i].binding === 'prodNm' || columns[i].binding === 'barcdOld'){
          columns[i].visible = true;
        }
      }
    }

    var excelGubun;

    if(data.excelGubun === 'C'){
      excelGubun = '(조회조건)_';
      params.prodCd = data.prodCd;
      params.prodNm = data.prodNm;
      params.prodClassCd = data.prodClassCd;
      params.barCd = data.barCd;
      params.useYn = data.useYn;
      params.barcdYn = data.barcdYn;
    } else if(data.excelGubun === 'T'){
      excelGubun = '(전체)_';
    } else if(data.excelGubun === 'S'){
      excelGubun = '(업로드양식)_';
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/prodBarcd/getProdExcelList.sb", params, function() {

      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
      $timeout(function () {
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
          includeColumnHeaders: true,
          includeCellStyles   : true,
          includeColumns      : function (column) {
            return column.visible;
          }
        }, messages["barcd.prodBarcd"] + excelGubun + getCurDateTime() +'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };
}]);