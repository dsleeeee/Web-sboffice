/****************************************************************
 *
 * 파일명 : verDelInfo.js
 * 설  명 : 버전관리 삭제정보 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.06.17     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  버전관리 삭제정보 팝업 조회 그리드 생성
 */
app.controller('verDelInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('verDelInfoCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("verDelInfoCtrl", function(event, data) {
        $("#lblVerDelProgFg").text(data.progFg); // 프로그램구분
        $("#lblVerDelVerSerNo").text(data.verSerNo); // 버전일련번호
        $scope.searchVerDelInfo();
        event.preventDefault();
    });

    $scope.searchVerDelInfo = function(){
        var params = {};
        params.verSerNo = $("#lblVerDelVerSerNo").text(); // 버전일련번호

        $scope._postJSONQuery.withOutPopUp("/pos/confg/verManage/verDelInfo/getVerDelInfoList.sb", params, function (response) {
            var verDelInfoList = response.data.data.result;
            $scope.verDelInfoList = verDelInfoList;

            $("#lblVerDelApplyStoreCnt").text($scope.verDelInfoList.applyStoreCnt); // 적용매장-등록매장수
            $("#lblVerDelMonthStoreCnt").text($scope.verDelInfoList.monthStoreCnt); // 최근한달패치된매장수
            $("#lblVerDelUseStoreCnt").text($scope.verDelInfoList.useStoreCnt); // 현재버전사용매장수
        });
    };
    // <-- //검색 호출 -->

    // 확인
    $scope.delete = function() {
        if($scope.systemPw === "" || $scope.systemPw === undefined) {
            $scope._popMsg(messages["verDelInfo.systemPwBlank"]); // 시스템패스워드를 입력해주세요.
            return false;

        } else {
            if($scope.systemPw !== "2025") {
                $scope._popMsg(messages["verDelInfo.systemPwError"]); // 시스템패스워드를 틀렸습니다. 다시확인해주세요.
                return false;
            }
        }

        var params = {};
        params.verSerNo = $("#lblVerDelVerSerNo").text(); // 버전일련번호

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withPopUp("/pos/confg/verManage/verDelInfo/getVerDelInfoDelete.sb", params, function(){
            // 프로그램구분
            var prodFg = $("#lblVerDelProgFg").text();

            // 팝업 닫기
            $scope.close();

            // 상세 팝업 닫기
            $scope.versionInfoDetailLayer.hide();

            // 메인 화면 조회
            if(prodFg == "1") {
                var scope = agrid.getScope('verManageCtrl'); // NXPOS 버전관리
                scope.getVersionList();
            } else {
                var scope = agrid.getScope('verManageV2Ctrl'); // LYNKPOS 버전관리
                scope.getVersionList();
            }
        });
    };

    // 팝업 닫기
    $scope.close = function() {
        $scope.systemPw = "";
        $("#lblVerDelProgFg").text(""); // 프로그램구분
        $("#lblVerDelVerSerNo").text(""); // 버전일련번호
        $("#lblVerDelApplyStoreCnt").text(""); // 적용매장-등록매장수
        $("#lblVerDelMonthStoreCnt").text(""); // 최근한달패치된매장수
        $("#lblVerDelUseStoreCnt").text(""); // 현재버전사용매장수

        $scope.wjVerDelInfoLayer.hide();
        event.preventDefault();
    };

}]);