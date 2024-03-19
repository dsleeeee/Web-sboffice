/****************************************************************
 *
 * 파일명 : storePosVersion.js
 * 설  명 : 매장포스버전현황 > 매장포스버전현황탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.03.30     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 적용매장구분
var regStoreFgAllData = [
  {"name":"전체","value":""},
  {"name":"등록","value":"Y"},
  {"name":"미등록","value":"N"}
];

// 최종매출일
var dateAllData = [
  {"name":"전체","value":""},
  {"name":"7일이내","value":"7"},
  {"name":"31일이내","value":"31"}
];

// 포스메인여부
var mainValAllData = [
  {"name":"전체","value":""},
  {"name":"메인포스","value":"1"},
  {"name":"서브포스","value":"2"}
];

// 버전체크
var verChkAllData = [
  {"name":"전체","value":""},
  {"name":"버전같음","value":"Y"},
  {"name":"버전다름","value":"N"}
];

// 패치여부
var patchFgData = [
  {"name":"전체","value":""},
  {"name":"정상","value":"Y"},
  {"name":"오류","value":"N"}
];

app.controller('storePosVersionCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storePosVersionCtrl', $scope, $http, true));

  // 브랜드 콤보박스 셋팅
  $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
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
  $scope._setComboData("registFgCombo", regStoreFgAllData); // 적용매장구분
  $scope._setComboData("selectVerCombo", selectVerComboList); // 버전체크
  $scope._setComboData("lastSaleCombo", dateAllData); // 최종매출일
  $scope._setComboData("mainValCombo", mainValAllData); // 포스메인여부
  $scope._setComboData("subValCombo", selectSubPos); // 포스용도
  $scope._setComboData("verChkCombo", verChkAllData); // 버전체크
  $scope._setComboData("posLogDtCombo", dateAllData); // 포스로그인일자
  $scope._setComboData("patchFgCombo", patchFgData); // 패치여부


  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("storePosVersionCtrl");

    // 그리드 DataMap 설정
    $scope.verTypeFgDataMap = new wijmo.grid.DataMap(verTypeFg, 'value', 'name');
    $scope.areaCdDataMap = new wijmo.grid.DataMap(areaCd, 'value', 'name'); // 지역
    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name'); // 용도
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name'); // 상태
    $scope.branchCdDataMap = new wijmo.grid.DataMap(branchCdComboList, 'value', 'name'); // 그룹
    $scope.lastSale = "7";

    if(hqOfficeCd === "DS034" || hqOfficeCd === "H0393" || hqOfficeCd === "DS021") {

      // ReadOnly 효과설정
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          var item = s.rows[e.row].dataItem;
          if (col.binding === "posVerNo1" || col.binding === "verSerAll") {
            if (item.posVerNo1 != $scope.selectVerCd) {
              wijmo.addClass(e.cell, 'red');
            }
          }
          if (col.binding === "patchFg") {
            if (item.patchFg != null && item.patchFg != '') {
              wijmo.addClass(e.cell, 'wijLink');
              wijmo.addClass(e.cell, 'blue');
            }
          }
          if (col.binding === "registFgStore") {
            if (item.registFgStore == '[등록]') {
              wijmo.addClass(e.cell, 'wijLink');
              wijmo.addClass(e.cell, 'blue');
            }
          }
          if (col.binding === "registFg") {
            if (item.registFg == '미등록') {
              wijmo.addClass(e.cell, 'red');
            }
          }
          if (col.binding === "verChk") {
            if (item.posVerNo1 != $scope.selectVerCd) {
              wijmo.addClass(e.cell, 'red');
            }
          }
        }
      });

      var grid = wijmo.Control.getControl("#wjGridPosVersionList");
      var columns = grid.columns;
      columns[7].visible  = true;
      columns[8].visible  = true;
      columns[10].visible = true;
      columns[11].visible = true;
      columns[15].visible = true;
      columns[16].visible = true;
      columns[17].visible = true;
      columns[18].visible = true;
      columns[19].visible = true;
      columns[20].visible = true;

      document.getElementById('patchFgTh').style.display = '';
      document.getElementById('patchFgTd').style.display = '';

      document.getElementById('selectVerTh').style.display = '';
      document.getElementById('selectVerTd').style.display = '';

    }

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "patchFg") { // 패치여부
          if(selectedRow.patchFg != null && selectedRow.patchFg != '') {
            var params = {};
            params.storeCd      = selectedRow.storeCd;
            params.posNo        = selectedRow.posNo;
            params.selectVerCd  = $scope.selectVerCd;
            $scope._broadcast('patchDtlCtrl', params);
          }
        }
        if(col.binding === "registFgStore"){
          if(selectedRow.registFgStore == '[등록]') {

            // 적용매장을 등록하시겠습니까?
            $scope._popConfirm(messages["storePosVersion.storeRegist.msg"], function () {

              var params = {};
              params.selectVerCd = $scope.selectVerCd;
              params.storeCd = selectedRow.storeCd;

              $scope._postJSONSave.withPopUp("/store/storeMoms/storePosVersion/storePosVersion/regist.sb", params, function (response) {
                var result = response.data.data;

                if(result === 0){
                  $scope._popMsg(messages["cmm.saveSucc"]);
                }else{
                  $scope._popMsg(messages["storePosVersion.dupStore.msg"]);
                }
                // 재조회
                $scope.searchStorePosVersionList();
              });
            });
          }
        }
      }
    });
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storePosVersionCtrl", function (event, data) {
    $scope.searchStorePosVersionList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 리스트 조회
  $scope.searchStorePosVersionList = function () {

    // 파라미터
    var params       = {};
    params.prodHqBrandCd        = $scope.prodHqBrandCd;
    params.momsTeam             = $scope.momsTeam;
    params.momsAcShop           = $scope.momsAcShop;
    params.momsAreaFg           = $scope.momsAreaFg;
    params.momsCommercial       = $scope.momsCommercial;
    params.momsShopType         = $scope.momsShopType;
    params.momsStoreManageType  = $scope.momsStoreManageType;
    params.branchCd             = $scope.branchCd;
    params.storeHqBrandCd       = $scope.storeHqBrandCd;
    params.registFg             = $scope.registFg;
    params.selectVer            = $scope.selectVer;
    params.lastSale             = $scope.lastSale;
    params.mainVal              = $scope.mainVal;
    params.subVal               = $scope.subVal;
    params.verChk               = $scope.verChk;
    params.posLogDt             = $scope.posLogDt;
    params.patchFg              = $scope.patchFg;

    var verCd =params.selectVer.indexOf("]");
    params.selectVerCd = params.selectVer.substring(1,verCd);
    $scope.selectVerCd = params.selectVerCd;
    // '전체' 일때
    if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
      var momsHqBrandCd = "";
      for(var i=0; i < momsHqBrandCdComboList.length; i++){
        if(momsHqBrandCdComboList[i].value !== null) {
          momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
        }
      }
      params.userBrands = momsHqBrandCd;
    }
    params.momsStoreFg01 = $scope.momsStoreFg01;
    params.momsStoreFg02 = $scope.momsStoreFg02;
    params.momsStoreFg03 = $scope.momsStoreFg03;
    params.momsStoreFg04 = $scope.momsStoreFg04;
    params.momsStoreFg05 = $scope.momsStoreFg05;
    params.listScale = 100;
    console.log(params);

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/store/storeMoms/storePosVersion/storePosVersion/getStorePosVersionList.sb", params, function (){});
  };

  // 확장조회 숨김/보임
  $scope.searchAddShowChange = function(){
    if( $("#tblSearchAddShow").css("display") === 'none') {
      $("#tblSearchAddShow").show();
    } else {
      $("#tblSearchAddShow").hide();
    }
  };

  // 엑셀 다운로드
  $scope.excelDownloadInfo = function () {

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
          messages["storePosVersion.storePosVersion"] + '_' + getCurDateTime() +'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };

}]);
