/****************************************************************
 *
 * 파일명 : workHistory.js
 * 설  명 : 국민대 > 근로학생관리 > 근로학생 근무배치 현황 JavaScript
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

var currentYear = new Date().getFullYear() + 2;
var yearsData = [];
for (let y = 2000; y <= currentYear; y++) {
    yearsData.push(y.toString());
}

/** 근로학생 근무배치 현황 그리드 controller */
app.controller('workStudentScheduleStatusCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('workStudentScheduleStatusCtrl', $scope, $http, false));

    // 조회일자 설정
    $scope.srchDate = new wijmo.input.ComboBox('#srchDate', {
        itemsSource: yearsData,
        selectedValue: new Date().getFullYear().toString(), // 기본 선택값: 올해
        isEditable: false // 사용자가 직접 입력하지 못하게 설정 (선택 전용)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("workStudentScheduleStatusCtrl", function (event, data) {
        $scope.getWorkStudentScheduleStatusList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 근로학생 배치 조회
    $scope.getWorkStudentScheduleStatusList = function () {
        // 파라미터
        var params  = {};
        params.srchYear = $scope.srchDate.selectedValue;
        params.termFg   = $("input[name=termFg]:checked").val();


        $scope.srchYear = params.srchYear;
        $scope.termFg   = $("input[name=termFg]:checked").val();
        $scope.termFgTxt = $("label[for='" + $("input[name=termFg]:checked").attr("id") + "']").text();

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/workStudent/workStudentScheduleStatus/workStudentScheduleStatus/getWorkStudentScheduleStatusList.sb", params, function () {
        });
    };

    // 리포트
    $scope.report = function () {

        if($scope.flex.rows.length <= 0){
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }
        var params       = {};
        params.srchYear = $scope.srchYear;
        params.termFg   = $scope.termFg;
        params.storeCd  = $scope.storeCd;
        params.storeNm  = $scope.storeNm;

        params.termFgTxt = $scope.termFgTxt;
        $scope._broadcast('workStudentScheduleStatusReportCtrl', params);

    };

}]);
