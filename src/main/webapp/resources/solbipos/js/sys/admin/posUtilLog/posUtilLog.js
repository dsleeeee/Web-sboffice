/****************************************************************
 *
 * 파일명 : posUtilLog.js
 * 설  명 : 매출점검 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.12.21     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('posUtilLogCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('posUtilLogCtrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.endDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 사용로그 그리드 조회
    $scope.$on("posUtilLogCtrl", function(event, data) {
        $scope.getPosUtilLogList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    $scope.getPosUtilLogList= function() {
        var startDt = new Date(wijmo.Globalize.format($scope.startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.endDate.value, 'yyyy-MM-dd'));

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format($scope.startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.endDate.value, 'yyyyMMdd');

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sys/admin/posUtilLog/posUtilLog/getPosUtilLogList.sb", params, function() {});
    }

    // 엑셀다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	false,
                    includeColumns      :	function (column) {
                        // return column.visible;
                        return column.binding != 'gChk';
                    }
                },messages["posUtilLog.posUtilLog"]+ '_'+ messages["cmm.search.date"] + wijmo.Globalize.format($scope.startDate.value, 'yyyy-MM-dd') +'_' + wijmo.Globalize.format($scope.endDate.value, 'yyyy-MM-dd') + ')'+'_'+ getCurDateTime() +'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
}])