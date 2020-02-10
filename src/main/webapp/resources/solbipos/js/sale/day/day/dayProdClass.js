/**
 * get application
 */
var app = agrid.getApp();

// 분류표시 DropBoxDataMap
var vLevel = [
    {"name":"1레벨","value":"1"},
    {"name":"2레벨","value":"2"},
    {"name":"3레벨","value":"3"}
];

/** 상품분류별 controller */
app.controller('dayProdClassCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayProdClassCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchLevelCombo", vLevel);

    $scope.srchStartDate = wcombo.genDateVal("#srchProdClassStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchProdClassEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("dayProdClassCtrl");

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');



    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dayProdClassCtrl", function (event, data) {
        $scope.searchDayProdClassList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 상품분류별 리스트 조회
    $scope.searchDayProdClassList = function () {

        $scope.searchedStoreCd = $("#dayTimeSelectStoreCd").val();
        // 파라미터
        var params= {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $scope.searchedStoreCd;
        params.saleTime = $scope.saleTime;
    };

}]);