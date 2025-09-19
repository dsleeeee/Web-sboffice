/****************************************************************
 *
 * 파일명 : workHistory.js
 * 설  명 : 국민대 > 근로학생관리 > 근로학생별 근무내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.19     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 근로학생별 근무내역 그리드 controller */
app.controller('workHistoryByWorkStudentCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('workHistoryByWorkStudentCtrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("workHistoryByWorkStudentCtrl", function (event, data) {
        $scope.getWorkHistoryByWorkStudentList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 근로학생 배치 조회
    $scope.getWorkHistoryByWorkStudentList = function () {
        // 파라미터
        var params  = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

        $scope.startDate = params.startDate;
        $scope.endDate = params.endDate;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/workStudent/workHistoryByWorkStudent/workHistoryByWorkStudent/getWorkHistoryByWorkStudentList.sb", params, function () {
        });
    };

    // 리포트
    $scope.report = function () {

        if($scope.flex.rows.length <= 0){
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }
        var params       = {};
        params.startDate = $scope.startDate;
        params.endDate = $scope.endDate;
        params.storeCd = $scope.storeCd;
        params.storeNm = $scope.storeNm;
        params.studentNo = $scope.studentNo;
        params.studentNm = $scope.studentNm;
        $scope._broadcast('workHistoryByWorkStudentReportCtrl', params);

    };

}]);
