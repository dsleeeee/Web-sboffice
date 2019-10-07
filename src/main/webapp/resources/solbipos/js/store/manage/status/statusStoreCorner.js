/****************************************************************
 *
 * 파일명 : statusStoreCorner.js
 * 설  명 : 코너 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.09.25     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('statusStoreCornerCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('statusStoreCornerCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name'); //사용여부
    };

    // 선택 매장
    $scope.selectedStore;
    $scope.setSelectedStore = function(store) {
        $scope.selectedStore = store;
    };
    $scope.getSelectedStore = function(){
        return $scope.selectedStore;
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("statusStoreCornerCtrl", function(event, data) {
        $scope.setSelectedStore(data);
        $scope.searchStatusStoreCorner();
        event.preventDefault();
    });

    // 코너 상세조회
    $scope.searchStatusStoreCorner = function(){

        $("#hqOfficeNmTitle").text( $scope.selectedStore.hqOfficeNm + "/");

        var params = {};
        params.storeCd = $scope.selectedStore.storeCd;

        $scope._inquiryMain("/store/manage/status/cornerdtl/getStatusStoreCornerList.sb", params, function() {}, false);
    };

}]);