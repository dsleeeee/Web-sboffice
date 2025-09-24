/****************************************************************
 *
 * 파일명 : workStudentPayHistory.js
 * 설  명 : 국민대 > 근로학생관리 > 근로장학금 지급내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.18     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


/** 근로장학금 지급내역 그리드 controller */
app.controller('workStudentPayHistoryCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('workStudentPayHistoryCtrl', $scope, $http, false));

    // 근무일자 셋팅
    // 검색조건에 조회기간
    var srchYm = new wijmo.input.InputDate('#srchYm', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("workStudentPayHistoryCtrl", function (event, data) {
        $scope.getWorkStudentPayHistoryList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 근로장학금 지급내역 조회
    $scope.getWorkStudentPayHistoryList = function () {
        // 파라미터
        var params  = {};
        params.srchYm = wijmo.Globalize.format(srchYm.value, 'yyyyMM');
        $scope.srchYm = params.srchYm;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/workStudent/workStudentPayHistory/workStudentPayHistory/getWorkStudentPayHistoryList.sb", params, function () {
            // 사용자 행위 기록
            var actParams = {};
            actParams.resrceCd = menuCd;
            actParams.pathNm = "국민대-근로학생관리-근로장학금 지급내역";
            actParams.contents = "[조회] 버튼 클릭 시";

            $scope._postJSONSave.withOutPopUp("/common/method/saveUserAct.sb", actParams, function(response){});
        });
    };

    // 리포트
    $scope.report = function () {

        if($scope.flex.rows.length <= 0){
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }
        var params       = {};
        params.srchYm = $scope.srchYm;
        $scope._broadcast('workStudentPayHistoryReportCtrl', params);

        // 사용자 행위 기록
        var actParams = {};
        actParams.resrceCd = menuCd;
        actParams.pathNm = "국민대-근로학생관리-근로장학금 지급내역";
        actParams.contents = "[출력] 버튼 클릭 시";

        $scope._postJSONSave.withOutPopUp("/common/method/saveUserAct.sb", actParams, function(response){});

    };

}]);
