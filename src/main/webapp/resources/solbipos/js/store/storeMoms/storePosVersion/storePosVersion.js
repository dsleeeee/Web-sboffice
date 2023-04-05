/**
 * get application
 */
var app = agrid.getApp();


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
  $scope._setComboData("branchCdCombo", branchCdComboList); // 지사

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("storePosVersionCtrl");

    // 그리드 DataMap 설정
    $scope.verTypeFgDataMap = new wijmo.grid.DataMap(verTypeFg, 'value', 'name');
    $scope.areaCdDataMap = new wijmo.grid.DataMap(areaCd, 'value', 'name'); // 지역
    $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFg, 'value', 'name'); // 용도
    $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name'); // 상태
    $scope.branchCdDataMap = new wijmo.grid.DataMap(branchCdComboList, 'value', 'name'); // 지사

    $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name'); // 팀별
    $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name'); // AC점포별
    $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList, 'value', 'name'); // 지역구분
    $scope.momsCommercialDataMap = new wijmo.grid.DataMap(momsCommercialComboList, 'value', 'name'); // 상권
    $scope.momsShopTypeDataMap = new wijmo.grid.DataMap(momsShopTypeComboList, 'value', 'name'); // 점포유형
    $scope.momsStoreManageTypeDataMap = new wijmo.grid.DataMap(momsStoreManageTypeComboList, 'value', 'name'); // 매장관리타입


    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if(col.binding === "posVerNo" || col.binding === "maxVerSerNo"){
          if(item.posVerNo != item.maxVerSerNo){
            wijmo.addClass(e.cell, 'red');
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
    params.prodHqBrandCd = $scope.prodHqBrandCd;
    params.momsTeam = $scope.momsTeam;
    params.momsAcShop = $scope.momsAcShop;
    params.momsAreaFg = $scope.momsAreaFg;
    params.momsCommercial = $scope.momsCommercial;
    params.momsShopType = $scope.momsShopType;
    params.momsStoreManageType = $scope.momsStoreManageType;
    params.branchCd = $scope.branchCd;
    params.storeHqBrandCd = $scope.storeHqBrandCd;
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
    params.listScale = 100;
    console.log(params);

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/store/storeMoms/storePosVersion/storePosVersion/getStorePosVersionList.sb", params, function (){

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
