/****************************************************************
 *
 * 파일명 : posDtl.js
 * 설  명 : 패치정보 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.03.13    김유승       1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('patchDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('patchDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("patchDtlCtrl", function(event, data) {
        $scope.patchDtlLayer.show(true);
        $scope.patchDtlList(data);
        event.preventDefault();
    });

    $scope.patchDtlList = function(data){
        var params = {};

        params.storeCd      = data.storeCd;
        params.posNo        = data.posNo;
        params.selectVerCd  = data.selectVerCd;

        $scope._inquiryMain("/store/storeMoms/storePosVersion/patchDtl/patchDtlList.sb", params, function() {}, false);
    };


}]);