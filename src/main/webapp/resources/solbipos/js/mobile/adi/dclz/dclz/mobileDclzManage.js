/****************************************************************
 *
 * 파일명 : mobileDclzManage.js
 * 설  명 : (모바일) 부가서비스 > 근태현황
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.07.09     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 입력구분 VALUE
var inFg = [
    {"name":"전체","value":""},
    {"name":"POS","value":"POS"},
    {"name":"WEB","value":"WEB"}
];

/**
 *  일자-시간대별 그리드 생성
 */
app.controller('mobileDclzManageCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileDclzManageCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);
    // 입력구분 콤보박스
    $scope._setComboData("inFg", inFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

    // <-- 검색 호출 -->
    $scope.$on("mobileDclzManageCtrl", function(event, data) {
        $scope.searchMobileDclzManage(data);
        event.preventDefault();
    });



    $scope.searchMobileDclzManage = function(data){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['mobile.cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 한달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['mobile.cmm.dateOver.1month.error']);
            return false;
        }

        var params = {};
        params.startDate = getDate(startDate);
        params.endDate = getDate(endDate);
        params.srchStoreCd = $("#mobileDclzManageStoreCd").val();
        params.inFg = $scope.inFg;

        $scope._inquirySub("/mobile/adi/dclz/dclz/mobileDclzManage/getDclzManage.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileDclzManage", $scope.flexMobileDclzManage, "N");

        }, false);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileDclzManageStoreShow = function () {
        $scope._broadcast('mobileDclzManageStoreCtrl');
    };

}]);