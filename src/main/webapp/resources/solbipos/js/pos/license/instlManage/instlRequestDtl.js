/****************************************************************
 *
 * 파일명 : instlRequestDtl.js
 * 설  명 : POS관리 > 설치관리 > 설치요청 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.10.15     이다솜      1.0
 *
 * **************************************************************/

app.controller('instlRequestDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('instlRequestDtlCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.instFgDataMap = new wijmo.grid.DataMap(instFgData, 'value', 'name');
        $scope.reasonDatMap = new wijmo.grid.DataMap(reasonData, 'value', 'name');

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("instlRequestDtlCtrl", function (event, data) {

        $scope.instlRequestDtlLayer.show(true);

        // 조회
        $scope.instlDtl(data);

        $("#spanInstlRequestDtlTitle").html(" | " + data.storeCd + " | " + data.storeNm +  " | POS번호 : " + data.posNo);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();

    });

    // 일자 매장별 매출현황 리스트 조회
    $scope.instlDtl = function (data) {
        // 파라미터
        var params      = {};
        params.agencyCd = data.agencyCd;
        params.storeCd = data.storeCd;
        params.posNo = data.posNo;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/pos/license/instlManage/getInstlRequestDtl.sb", params);

    };

    // 닫기버튼 클릭
    $scope.close = function(){
        $scope.instlRequestDtlLayer.hide();
    };

}]);