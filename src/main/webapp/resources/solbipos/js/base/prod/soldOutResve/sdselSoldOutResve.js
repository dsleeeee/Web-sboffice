/****************************************************************
 *
 * 파일명 : sdselSoldOutResve.js
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
app.controller('sdselSoldOutResveCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sdselSoldOutResveCtrl', $scope, $http, false));

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
  // 품절여부 콤보박스
  $scope._getComboDataQuery('094', 'soldOutYnComboData');
  // 비노출여부 콤보박스
  $scope._setComboData("sdselSoldOutResveYnCombo", soldOutYnAllData);
  $scope._setComboData("sdselSoldOutResveYnComboChg", soldOutYnData);
  $scope._setComboData("sdselSoldOutResveYnCombo2", soldOutYnData);
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
  $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
  $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
  $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
  $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

  // 등록일자 셋팅
  $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

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
  $scope.$on("sdselSoldOutResveCtrl", function(event, data) {
    $scope.searchsdselSoldOutResveList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.sdselSoldOutResveStoreShow = function () {
    $scope._broadcast('sdselSoldOutResveStoreCtrl');
  };

  // 상품선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.sdselSoldOutResveProdShow = function () {
    $scope._broadcast('sdselSoldOutResveProdCtrl');
  };

  // 예약 내역 조회
  $scope.searchsdselSoldOutResveList = function(){

    // 파라미터
    var params = {};
    // 등록일자 '전체기간' 선택에 따른 params
    if(!$scope.isChecked){
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    }
    params.storeCds = $("#sdselSoldOutResveStoreCd").val();
    params.prodCds = $("#sdselSoldOutResveProdCd").val();
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
      params.momsStoreFg02 = $scope.momsStoreFg02;
      params.momsStoreFg03 = $scope.momsStoreFg03;
      params.momsStoreFg04 = $scope.momsStoreFg04;
      params.momsStoreFg05 = $scope.momsStoreFg05;
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
    $scope._inquiryMain("/base/prod/soldOutResve/soldOutResve/getSdselSoldOutResve.sb", params, function(){

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
        $scope.flex.collectionView.items[i].sdselSoldOutResveYn = $scope.sdselSoldOutResveYnChg;
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
      $scope._save('/base/prod/soldOutResve/soldOutResve/deleteSdselSoldOutResve.sb', params, function () {
        $scope.searchsdselSoldOutResveList();
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
      $scope._save("/base/prod/soldOutResve/soldOutResve/modSdselSoldOutResve.sb", params, function(){
        $scope.searchsdselSoldOutResveList();
      });
    });

  };


  // 확장조회 숨김/보임
  $scope.searchAddShowChange2 = function(){
      if( $("#tblSearchAddShow2").css("display") === 'none') {
          $("#tblSearchAddShow2").show();
      } else {
          $("#tblSearchAddShow2").hide();
      }
  };

  // 추가 팝업
  $scope.add = function () {
    $scope.sdselSoldOutResveAddLayer.show(true);
  };

  // 그리드 선택불가 항목처리
  $scope.setGridReadOnly = function () {
    var grid = wijmo.Control.getControl("#wjGridSdselSoldOut");
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
      }, '품절관리(예약)_사이드메뉴(상품)_' + getCurDateTime() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);

  }

}]);
