/****************************************************************
 *
 * 파일명 : membrPoint.js
 * 설  명 : 회원 포인트실적 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.11.11     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 정렬 DropBoxDataMap
var arrayData = [
    {"name":"매장-일자-회원","value":"1"},
    {"name":"매장-회원-일자","value":"2"},
    {"name":"회원-일자-매장","value":"3"},
    {"name":"회원-매장-일자","value":"4"},
    {"name":"일자-매장-회원","value":"5"},
    {"name":"일자-회원-매장","value":"6"}
];

/**
 *  회원 포인트실적 그리드 생성
 */
app.controller('membrPointCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('membrPointCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("array", arrayData); //정렬

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("membrPointCtrl", function(event, data) {
        $scope.searchMembrPoint();
        event.preventDefault();
    });

    // 회원 포인트실적 그리드 조회
    $scope.searchMembrPoint = function(){
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.storeCds = $("#storeCd").val();

        $scope._inquiryMain("/membr/anals/membrPoint/membrPoint/getMembrPointList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.storeShow = function () {
        $scope._broadcast('storeCtrl');
    };

}]);