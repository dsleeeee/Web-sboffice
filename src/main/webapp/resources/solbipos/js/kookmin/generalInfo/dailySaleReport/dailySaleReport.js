/****************************************************************
 *
 * 파일명 : dailySaleReport.js
 * 설  명 : 국민대 > 총괄정보 > 일일매출보고서 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.23     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

/** 일일매출보고서 그리드 controller */
app.controller('dailySaleReportCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dailySaleReportCtrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dailySaleReportCtrl", function (event, data) {

        // 일일매출보고서 리스트 조회
        $scope.getDailySaleReportList();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 일일매출보고서 리스트 조회
    $scope.getDailySaleReportList = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 7일 제한
        if (diffDay >= 7) {
            $scope._popMsg(messages['cmm.dateOver.7day.error']);
            return false;
        }

        startDt = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        endDt = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        var lastStartDt = (Number(startDt.substr(0, 4)) - 1).toString() + startDt.substr(4);
        var lastEndDt = (Number(endDt.substr(0, 4)) - 1).toString() + endDt.substr(4);

        // 파라미터
        var params = {};
        params.startDate = startDt;
        params.endDate = endDt;
        params.lastStartDate = lastStartDt;
        params.lastEndDate = lastEndDt;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/kookmin/generalInfo/dailySaleReport/dailySaleReport/getDailySaleReportList.sb", params, function() {

            var grid = wijmo.Control.getControl("#wjGrid");
            var tot_contrast = 0;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];

                tot_contrast += item.contrast;

                if (Number(item.contrast) > 0) {
                    item.contrast = "▲" + addComma(item.contrast);
                } else if (0 > Number(item.contrast)) {
                    item.contrast = "▽" + addComma(item.contrast);
                } else {
                    item.contrast = addComma(item.contrast);
                }
            }

            // 전년도 매출 대비 증감액 합계 표시
            if (Math.sign(tot_contrast) === 1) {
                grid.columnFooters.setCellData(0, 3, "▲" + addComma(tot_contrast));
            } else if (Math.sign(tot_contrast) === -1) {
                grid.columnFooters.setCellData(0, 3, "▽" + addComma(tot_contrast));
            } else {
                grid.columnFooters.setCellData(0, 3, addComma(tot_contrast));
            }

        }, false);
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
            }, '일일매출보고서'+ '_' + getCurDateTime() + '.xlsx', function () {
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

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 7일 제한
        if (diffDay >= 7) {
            $scope._popMsg(messages['cmm.dateOver.7day.error']);
            return false;
        }

        startDt = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        endDt = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        var lastStartDt = (Number(startDt.substr(0, 4)) - 1).toString() + startDt.substr(4);
        var lastEndDt = (Number(endDt.substr(0, 4)) - 1).toString() + endDt.substr(4);

        // 파라미터
        var params = {};
        params.startDate = startDt;
        params.endDate = endDt;
        params.lastStartDate = lastStartDt;
        params.lastEndDate = lastEndDt;

        $scope._broadcast('dailySaleReportReportCtrl', params);
    };

}]);