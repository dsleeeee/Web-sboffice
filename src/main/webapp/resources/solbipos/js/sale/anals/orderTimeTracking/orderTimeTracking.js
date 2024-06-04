/****************************************************************
 *
 * 파일명 : orderTimeTracking.js
 * 설  명 : 주문시간트레킹 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.08.28     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 수락구분
var acceptedFg = [
    {"name": "포스", "value": "1"},
    {"name": "사장님앱", "value": "2"},
    {"name": "채널사", "value": "3"}
];

// 완료구분
var completedFg = [
    {"name": "포스", "value": "1"},
    {"name": "사장님앱", "value": "2"},
    {"name": "채널사", "value": "3"}
];

// 취소구분
var canceledFg = [
    {"name": "포스", "value": "1"},
    {"name": "사장님앱", "value": "2"},
    {"name": "채널사", "value": "3"}
];

/**
 *  주문시간트레킹 생성
 */
app.controller('orderTimeTrackingCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orderTimeTrackingCtrl', $scope, $http, false));

    $scope.srchStartDate  = wcombo.genDateVal("#srchStartDate", gvStartDate);

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

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.acceptedFgDataMap = new wijmo.grid.DataMap(acceptedFg, 'value', 'name');   // 수락구분
        $scope.completedFgDataMap = new wijmo.grid.DataMap(completedFg, 'value', 'name'); // 완료구분
        $scope.canceledFgDataMap = new wijmo.grid.DataMap(canceledFg, 'value', 'name');   // 취소구분
        $scope.momsStoreFg01DataMap = new wijmo.grid.DataMap(momsStoreFg01ComboList, 'value', 'name'); // 매장그룹
        $scope.momsStoreFg02DataMap = new wijmo.grid.DataMap(momsStoreFg02ComboList, 'value', 'name'); // 매장그룹2
        $scope.momsStoreFg03DataMap = new wijmo.grid.DataMap(momsStoreFg03ComboList, 'value', 'name'); // 매장그룹3
        $scope.momsStoreFg04DataMap = new wijmo.grid.DataMap(momsStoreFg04ComboList, 'value', 'name'); // 매장그룹4
        $scope.momsStoreFg05DataMap = new wijmo.grid.DataMap(momsStoreFg05ComboList, 'value', 'name'); // 매장그룹5
    };
    
    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("orderTimeTrackingCtrl", function (event, data) {
      $scope.srchOrderTimeTrackingList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });
    
    //
    $scope.srchOrderTimeTrackingList = function () {

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.storeCds   = $("#orderTimeTrackingStoreCd").val();
        params.listScale = 500;

        if(orgnFg === "HQ"){
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
            params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
            params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
            params.branchCd = $scope.srchBranchCdCombo.selectedValue;
            // 선택한 매장브랜드가 없을 때('전체' 일때)
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
        }

        console.log(params);

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/anals/orderTimeTracking/orderTimeTracking/getOrderTimeTrackingList.sb", params, function (){});
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
    $scope.excelDownload = function () {

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.storeCds   = $("#orderTimeTrackingStoreCd").val();
        params.listScale = 500;

        if(orgnFg === "HQ"){
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
            params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
            params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
            params.branchCd = $scope.srchBranchCdCombo.selectedValue;
            // 선택한 매장브랜드가 없을 때('전체' 일때)
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
        }

        console.log(params);

        $scope._broadcast('orderTimeTrackingExcelCtrl',params);
    };

}]);


/**
 *  주문시간트레킹 엑셀다운로드 생성
 */

app.controller('orderTimeTrackingExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orderTimeTrackingExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.acceptedFgDataMap = new wijmo.grid.DataMap(acceptedFg, 'value', 'name');   // 수락구분
        $scope.completedFgDataMap = new wijmo.grid.DataMap(completedFg, 'value', 'name'); // 완료구분
        $scope.canceledFgDataMap = new wijmo.grid.DataMap(canceledFg, 'value', 'name');   // 취소구분
        $scope.momsStoreFg01DataMap = new wijmo.grid.DataMap(momsStoreFg01ComboList, 'value', 'name'); // 매장그룹
        $scope.momsStoreFg02DataMap = new wijmo.grid.DataMap(momsStoreFg02ComboList, 'value', 'name'); // 매장그룹2
        $scope.momsStoreFg03DataMap = new wijmo.grid.DataMap(momsStoreFg03ComboList, 'value', 'name'); // 매장그룹3
        $scope.momsStoreFg04DataMap = new wijmo.grid.DataMap(momsStoreFg04ComboList, 'value', 'name'); // 매장그룹4
        $scope.momsStoreFg05DataMap = new wijmo.grid.DataMap(momsStoreFg05ComboList, 'value', 'name'); // 매장그룹5
    };
    
    // 다른 컨트롤러의 orderTimeTrackingExcelCtrl 받기
    $scope.$on("orderTimeTrackingExcelCtrl", function (event, data) {
        $scope.srchOrderTimeTrackingExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });
    
    // 엑셀 다운로드
    $scope.srchOrderTimeTrackingExcelList = function (params) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/anals/orderTimeTracking/orderTimeTracking/getOrderTimeTrackingExcelList.sb", params, function() {
          if ($scope.excelFlex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
          }

          $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
          $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
              includeColumnHeaders: true,
              includeCellStyles   : false,
              includeColumns      : function (column) {
                return column.visible;
              }
            }, messages["orderTimeTracking.orderTimeTracking"] + '_' + params.startDate + '_' + getCurDateTime()+'.xlsx', function () {
              $timeout(function () {
                $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
              }, 10);
            });
          }, 10);
        });
    };

}]);