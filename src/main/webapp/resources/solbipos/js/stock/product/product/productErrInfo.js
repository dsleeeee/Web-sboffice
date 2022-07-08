/****************************************************************
 *
 * 파일명 : productErrInfo.js
 * 설  명 : 생산관리 - 생산등록 생산/폐기등록 업로드 실패내역 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.07.05     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 생산/폐기등록 업로드 실패내역 팝업 controller */
app.controller('productErrInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('productErrInfoCtrl', $scope, $http, true));

    // 그리드 상품상태
    $scope.prodStatusMap = new wijmo.grid.DataMap([
        {id: "0", name: messages["product.prodStatusNoReg"]},
        {id: "1", name: messages["product.prodStatusReg"]}
    ], 'id', 'name');


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // <-- 검색 호출 -->
   $scope.$on("productErrInfoCtrl", function(event, data) {
       // 그리드 초기화
       var cv          = new wijmo.collections.CollectionView([]);
       cv.trackChanges = true;
       $scope.data     = cv;

       // 업로드 에러내역 리스트 조회
       $scope.searchUploadErrInfoList();
   });

    // 업로드 에러내역 리스트 조회
    $scope.searchUploadErrInfoList = function () {

        // 파라미터
        var params = {};

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/stock/product/product/productRegist/getUploadErrInfoList.sb", params);
    };

}]);