/****************************************************************
 *
 * 파일명 : saleDtl.js
 * 설  명 : 매출 공통팝업 > 매장 상세내역 (매출) 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.01.10     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 승인구분
var vApprFg = [
    {"name":"승인","value":"1"},
    {"name":"취소","value":"2"}
];

// 승인처리
var vApprProcFg = [
    {"name":"POS","value":"1"},
    {"name":"CAT","value":"2"},
    {"name":"일반","value":"3"}
];

/**
 *  팝업 그리드 생성
 */
app.controller('saleDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("saleDtlCtrl", function(event, data) {
        $scope.wjSaleDtlLayer.show(true);

        // 매장정보,매출종합내역,결제내역,회원정보 조회
        $scope.searchSaleDtlList(data);

        // 신용카드 결제내역 조회
        $scope._broadcast('saleCardDtlCtrl', data);

        event.preventDefault();
    });

    $scope.searchSaleDtlList = function(data){
        var params = {};
        params.hqOfficeCd  = data.hqOfficeCd;
        params.hqBrandCd  = data.hqBrandCd;
        params.storeCd  = data.storeCd;
        params.saleDate  = data.saleDate;
        params.posNo = data.posNo;
        params.billNo = data.billNo;
        params.payCol = payCol;

        $scope._postJSONQuery.withOutPopUp( "/sale/cmmSalePopup/saleInfo/saleInfo/getSaleDtlList.sb", params, function(response){
            var saleDtl = response.data.data;
            $scope.saleDtl = saleDtl;
        });
    };

}]);

// 신용카드 결제내역
app.controller('saleCardDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleCardDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.apprFgDataMap = new wijmo.grid.DataMap(vApprFg, 'value', 'name'); //승인구분
        $scope.apprProcFgDataMap = new wijmo.grid.DataMap(vApprProcFg, 'value', 'name'); //승인처리
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("saleCardDtlCtrl", function(event, data) {
        $scope.searchSaleCardDtlList(data);

        // 현금영수증 결제내역 조회
        $scope._broadcast('saleCashDtlCtrl', data);
    });

    $scope.searchSaleCardDtlList = function(data){
        var params = {};
        params.hqOfficeCd  = data.hqOfficeCd;
        params.hqBrandCd  = data.hqBrandCd;
        params.storeCd  = data.storeCd;
        params.saleDate  = data.saleDate;
        params.posNo = data.posNo;
        params.billNo = data.billNo;

        $scope._inquirySub("/sale/cmmSalePopup/saleInfo/saleInfo/getSaleCardDtlList.sb", params, function() {}, false);
    };

}]);

// 현금영수증 결제내역
app.controller('saleCashDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleCashDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.apprProcFgDataMap = new wijmo.grid.DataMap(vApprProcFg, 'value', 'name'); //승인처리
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("saleCashDtlCtrl", function(event, data) {
        $scope.searchSaleCashDtlList(data);

        // 상품내역 조회
        $scope._broadcast('saleProdDtlCtrl', data);
    });

    $scope.searchSaleCashDtlList = function(data){
        var params = {};
        params.hqOfficeCd  = data.hqOfficeCd;
        params.hqBrandCd  = data.hqBrandCd;
        params.storeCd  = data.storeCd;
        params.saleDate  = data.saleDate;
        params.posNo = data.posNo;
        params.billNo = data.billNo;

        $scope._inquirySub("/sale/cmmSalePopup/saleInfo/saleInfo/getSaleCashDtlList.sb", params, function() {}, false);
    };

}]);

// 상품내역
app.controller('saleProdDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleProdDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("saleProdDtlCtrl", function(event, data) {
        $scope.searchSaleProdDtlList(data);
    });

    $scope.searchSaleProdDtlList = function(data){
        var params = {};
        params.hqOfficeCd  = data.hqOfficeCd;
        params.hqBrandCd  = data.hqBrandCd;
        params.storeCd  = data.storeCd;
        params.saleDate  = data.saleDate;
        params.posNo = data.posNo;
        params.billNo = data.billNo;

        $scope._inquirySub("/sale/cmmSalePopup/saleInfo/saleInfo/getSaleProdDtlList.sb", params, function() {}, false);
    };

    // 닫기버튼 클릭
    $scope.close = function(){
        $scope.wjSaleDtlLayer.hide();
    };

}]);