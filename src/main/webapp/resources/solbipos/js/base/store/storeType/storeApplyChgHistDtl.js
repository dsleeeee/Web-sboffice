/****************************************************************
 *
 * 파일명 : storeApplyChgHistDtl.js
 * 설  명 : 매장적용이력 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.11.23     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매장적용이력 상세 팝업 조회 그리드 생성
 */
app.controller('storeApplyChgHistDtlCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeApplyChgHistDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.applyProcFgDataMap = new wijmo.grid.DataMap(applyProcFgComboData, 'value', 'name'); // 처리구분
    };

    // <-- 검색 호출 -->
    $scope.$on("storeApplyChgHistDtlCtrl", function(event, data) {
        if(data != undefined) {
            $("#storeApplyChgHistDtlTitle").text("( [" + data.storeTypeCd + "] " + data.storeTypeNm + " / [" + data.storeCd + "] " + data.storeNm + " )");
        } else {
            $("#storeApplyChgHistDtlTitle").text("");
        }
        $scope.searchStoreApplyChgHistDtl(data);
        event.preventDefault();
    });

    $scope.searchStoreApplyChgHistDtl = function(data){
        var params = {};
        params.storeCd = data.storeCd;
        params.procDt = data.procDt;
        params.storeTypeCd = data.storeTypeCd;

        $scope._inquiryMain("/base/store/storeType/storeApplyChgHist/getStoreApplyChgHistDtlList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 팝업 닫기
    $scope.close = function(){
        $("#storeApplyChgHistDtlTitle").text("");

        $scope.wjStoreApplyChgHistDtlLayer.hide();
    };
}]);