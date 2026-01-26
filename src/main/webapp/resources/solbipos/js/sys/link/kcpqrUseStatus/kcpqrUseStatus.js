/****************************************************************
 *
 * 파일명 : onlineOrder.js
 * 설  명 : 시스템관리 > 연동 > KCPQR현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.01.21     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매출 구분
var srchFgComboData = [
    {"name":"사용자현황","value":"0"},
    {"name":"접속현황","value":"1"}
];

/**
 *  온라인주문확인 그리드 조회
 */
app.controller('kcpqrStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kcpqrStatusCtrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 콤보박스 데이터
    $scope._setComboData("srchFgCombo", srchFgComboData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 검색 호출
    $scope.$on("kcpqrStatusCtrl", function(event, data) {
        $scope.getSearchKcpqrStatus();
        event.preventDefault();
    });

    // 온라인주문확인 조회
    $scope.getSearchKcpqrStatus = function(){

        var params = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.srchFg = $scope.srchFgCombo.selectedValue;

        $scope._inquiryMain("/sys/link/kcpqrUseStatus/kcpqrUseStatus/getSearchKcpqrStatus.sb", params, function() {

            // 구분에 따른 그리드 셋팅
            var grid = wijmo.Control.getControl("#wjGridList");
            var columns = grid.columns;
            // 컬럼 총갯수
            var columnsCnt = columns.length;
            if(params.srchFg === "0"){
                for(var i=0; i<columnsCnt; i++) {
                    if(i < 8) {
                        columns[i].visible = true;
                    }else{
                        columns[i].visible = false;
                    }
                }
            }else{
                for(var i=0; i<columnsCnt; i++) {
                    if(i < 8) {
                        columns[i].visible = false;
                    }else{
                        columns[i].visible = true;
                    }
                }
            }
        }, false);
    };

    // 조회기간 히든처리
    $scope.setSrchDate = function (s) {
        if (s.selectedValue === "0") {
            $("#thSrchDate").css("display", "none");
            $("#tdSrchDate").css("display", "none");
        } else {
            $("#thSrchDate").css("display", "");
            $("#tdSrchDate").css("display", "");
        }
    };

}]);