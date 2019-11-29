/****************************************************************
 *
 * 파일명 : saleStoreList.js
 * 설  명 : 운영현황 매출매장현황탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.10.28     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매출매장현황 그리드 생성
 */
app.controller('saleStoreListCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleStoreListCtrl', $scope, $http, true));

    // comboBox 초기화
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태구분
    };

    // <-- 검색 호출 -->
    $scope.$on("saleStoreListCtrl", function(event, data) {
        $scope.searchSaleStoreList();
        event.preventDefault();
    });

    // 매출매장현황 그리드 조회
    $scope.searchSaleStoreList = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
        params.srchAgencyCd = $("#ssl_srchAgencyCd").val();
        params.hqOfficeCd = $("#hqOfficeCd").val();
        params.hqOfficeNm = $("#hqOfficeNm").val();
        params.storeCd = $("#storeCd").val();
        params.storeNm = $("#storeNm").val();
        params.chkDt = $scope.isChecked;
        params.orgnFg = orgnFg;
        params.pAgencyCd = pAgencyCd;
        params.agencyCd = orgnCd;
        params.listScale = $scope.listScaleSale;

        $scope._inquiryMain("/pos/license/oper/oper/getSaleStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    /*********************************************************
     * 관리업체 조회
     * *******************************************************/
    $scope.searchAgency = function(){
        if(orgnFg === "MASTER") {
            var popup = $scope.agencyLayer;

            // 팝업 닫을때
            popup.show(true, function (s) {
                var agencyScope = agrid.getScope('searchAgencyCtrl');
                agencyScope.$apply(function () {
                    agencyScope._gridDataInit();
                    if (!$.isEmptyObject(agencyScope.getAgency())) {
                        $("#ssl_srchAgencyCd").val(agencyScope.getAgency().agencyCd);
                        $("#agencyNm").val(agencyScope.getAgency().agencyNm);
                    }
                });
            });
        }
    };

}]);