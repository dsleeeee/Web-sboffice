/****************************************************************
 *
 * 파일명 : couponInfo.js
 * 설  명 : 국민대 > 쿠폰관리 > 쿠폰관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.22     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 검색기간 콤보박스
var periodData = [
    {"name":"기간 미사용","value":"N"},
    {"name":"기간 사용","value":"Y"}
];

/** 쿠폰관리 그리드 controller */
app.controller('couponInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('couponInfoCtrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
    $scope._setComboData("periodType", periodData); // 검색기간 콤보박스

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "mergeCoupnCd") { // 상품코드
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                // 상품코드
                if (col.binding === "mergeCoupnCd") {
                    // 쿠폰적용관리 상세 팝업
                    var params = {};
                    params.coupnCd      = selectedRow.coupnCd;
                    params.payClassCd   = selectedRow.payClassCd;
                    params.edit         = '1';
                    $scope._broadcast('couponRegistCtrl', params);
                    $scope.couponRegistLayer.show(true, function(){
                        var scope = agrid.getScope('couponRegistCtrl');
                        scope.coupn = {};
                    });
                }
            }
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("couponInfoCtrl", function (event, data) {
        $scope.getCouponInfoList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 쿠폰정보 조회
    $scope.getCouponInfoList = function () {

        // 파라미터
        var params  = {};

        // 기간 '사용' 시에만 체크
        if($scope.periodType !== 'N' && $scope.periodType !== null && $scope.periodType !== undefined) {
            var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if (startDt.getTime() > endDt.getTime()) {
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }

            // 조회일자 최대 1년(365일) 제한
            if (diffDay > 365) {
                $scope._popMsg(messages['cmm.dateOver.1year.error']);
                return false;
            }

            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/coupon/couponInfo/couponInfo/getCouponInfoList.sb", params, function () {
        });
    };

    // 기간 설정 변경
    $scope.setPeriodType = function(s) {

        if (s.selectedValue === "N") {
            $scope.srchStartDate.isReadOnly = true;
            $scope.srchEndDate.isReadOnly = true;
        } else {
            $scope.srchStartDate.isReadOnly = false;
            $scope.srchEndDate.isReadOnly = false;
        }
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 쿠폰적용관리 등록 팝업
    $scope.couponInfoDtl = function(){
        var params = {};
        params.edit = '0';
        $scope._broadcast('couponRegistCtrl', params);
        $scope.couponRegistLayer.show(true, function(){
            var scope = agrid.getScope('couponRegistCtrl');
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
            }, '쿠폰관리' + '_' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    }

    // 양식 다운로드
    $scope.sampleDownload = function () {

        // 샘플데이터
        $scope.addRow();

        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex2, {
                includeColumnHeaders : true,
                includeCellStyles : true,
                includeColumns : function (column) {
                    return column.binding != 'gChk';
                }
            }, '쿠폰관리' + '_엑셀업로드_양식_' +  getCurDateTime() + '.xlsx');
        }, 10);
    };

    // 샘플데이터 추가
    $scope.addRow = function(){

        // 그리드 초기화
        var flex = $scope.flex2;
        flex.itemsSource = new wijmo.collections.CollectionView();
        flex.collectionView.trackChanges = true;

        // 샘플양식에 값 넣기
        var params = {};
        params.membrNo = "0000000001";
        params.membrNm = "김국민";
        params.membrTelNo = "01000000000";

        var newRow = flex.collectionView.addNew();
        for (var prop in params) {
            newRow[prop] = params[prop];
        }
        flex.collectionView.commitNew();
    };

    // 출력
    $scope.report = function () {

        if($scope.flex.rows.length <= 0){
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }

        var params       = {};
        if($scope.periodType !== 'N' && $scope.periodType !== null && $scope.periodType !== undefined) {
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }
        params.coupnCd  = $scope.coupnCd;
        params.coupnNm  = $scope.coupnNm;
        params.storeCd  = $scope.storeCd;
        params.storeNm  = $scope.storeNm;
        params.prodCd   = $scope.prodCd;
        params.prodNm   = $scope.prodNm;
        params.partCd   = $scope.partCd;
        params.partNm   = $scope.partNm;

        $scope._broadcast('couponInfoReportCtrl', params);
    };

}]);
