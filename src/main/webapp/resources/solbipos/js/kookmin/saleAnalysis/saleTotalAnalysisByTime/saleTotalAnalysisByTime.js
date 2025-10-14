/****************************************************************
 *
 * 파일명 : saleAnalysisByTime.js
 * 설  명 : 국민대 > 매출분석 > 시간대별 매출분석(요일별) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.01     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 시간대별 매출분석(요일별) 현황 그리드 controller */
app.controller('saleTotalAnalysisByTimeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleTotalAnalysisByTimeCtrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("saleTotalAnalysisByTimeCtrl", function (event, data) {
        $scope.getSaleTotalAnalysisByTimeList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 근로학생 배치 조회
    $scope.getSaleTotalAnalysisByTimeList = function () {

        // if( isEmptyObject( $("#saleTotalAnalysisByTimeStoreCd").val()) ) {
        //     $scope._popMsg("매장을 선택해주세요.");
        //     return false;
        // }

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params  = {};
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd = $("#saleTotalAnalysisByTimeStoreCd").val();

        var yoil = '';
        yoil += $("#chkMon").is(":checked") ? '월,' : '';
        yoil += $("#chkTue").is(":checked") ? '화,' : '';
        yoil += $("#chkWed").is(":checked") ? '수,' : '';
        yoil += $("#chkThu").is(":checked") ? '목,' : '';
        yoil += $("#chkFri").is(":checked") ? '금,' : '';
        yoil += $("#chkSat").is(":checked") ? '토,' : '';
        yoil += $("#chkSun").is(":checked") ? '일,' : '';

        // 마지막 쉼표 제거
        yoil = yoil.replace(/,$/, '');
        params.yoil = yoil;

        $scope.paramData = params;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/saleAnalysis/saleTotalAnalysisByTime/saleTotalAnalysisByTime/getSaleTotalAnalysisByTimeList.sb", params, function () {
        });
    };

    // 엑셀다운로드
    $scope.excelDownload = function(){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    // return column.visible;
                    return column.binding != 'gChk';
                }
            }, '시간대 매출합계분석'+ '_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    }

    // 리포트
    $scope.report = function () {

        if($scope.flex.rows.length <= 0){
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }
        var params       = $scope.paramData;

        $scope._broadcast('saleTotalAnalysisByTimeReportCtrl', params);
    };

}]);
