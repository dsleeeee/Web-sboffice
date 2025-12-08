/****************************************************************
 *
 * 파일명 : inStockReportDtl.js
 * 설  명 : 국민대 > 매입처관리 > 매입처별 상세매입내역(상품별) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.12.02     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 입고내역서 그리드 controller */
app.controller('inStockReportDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('inStockReportDtlCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("inStockReportDtlCtrl");

        s.mergeManager = new wijmo.grid.MergeManager(s);

        s.mergeManager.getMergedRange = function(panel, r, c) {

            // 데이터셀 병합 (prodClassNm 세로 병합)
            if (panel.cellType === wijmo.grid.CellType.Cell) {

                var col = panel.columns[c];
                if (col.binding !== 'vendrCd' && col.binding !== 'vendrNm'
                    && col.binding !== 'storeCd' && col.binding !== 'storeNm') return null;

                var val = panel.getCellData(r, c, true);
                if (val == null) return null;

                var rng = new wijmo.grid.CellRange(r, c);

                // 위로 확장
                for (var i = r - 1; i >= 0; i--) {
                    if (panel.getCellData(i, c, true) === val) {
                        rng.row = i;
                    } else break;
                }

                // 아래로 확장
                for (var i = r + 1; i < panel.rows.length; i++) {
                    if (panel.getCellData(i, c, true) === val) {
                        rng.row2 = i;
                    } else break;
                }

                return rng.isSingleCell ? null : rng;
            }

            return null; // 나머지 셀은 병합 안함
        };

    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("inStockReportDtlCtrl", function (event, data) {
        $scope.getInStockReportDtlList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });


    // 입고/반출 리스트 조회
    $scope.getInStockReportDtlList = function () {

        var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 6달(186일) 제한
        if (diffDay > 186) {
            $scope._popMsg(messages['cmm.dateOver.6month.error']);
            return false;
        }

        // 매장(을)를 선택해주세요.
        if($("#inStockReportDtlStoreCd").val() === "") {
            $scope._popMsg(messages["cmm.require.selectStore"]);
            return false;
        }

        // 파라미터
        var params       = {};
        params.startDate    = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate      = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.storeCd      = $("#inStockReportDtlStoreCd").val();
        params.vendrCd      = $("#inStockReportDtlVendrCd").val();
        params.prodClassCd  = $scope.prodClassCd;

        $scope.startDate    = params.startDate;
        $scope.endDate      = params.endDate;
        $scope.storeCd      = params.storeCd;
        $scope.vendrCd      = params.vendrCd;
        $scope.selectProdClassCd = params.prodClassCd;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/acquire/inStockReportDtl/inStockReportDtl/getInStockReportDtlList.sb", params);
    };

    // 거래처선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.inStockReportDtlVendrShow = function () {
        $scope._broadcast('inStockReportDtlVendrCtrl');
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {
        var popUp = $scope.prodClassPopUpLayer.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function (response) {
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    //상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd		= "";
        $scope.prodClassNm 	= "";
    }

    // 리포트
    $scope.print = function () {

        if($scope.flex.rows.length <= 0){
            $scope._popMsg(messages["cmm.empty.data"]); // 조회 데이터가 없습니다.
            return false;
        }
        var params       = {};
        params.startDate    = $scope.startDate;
        params.endDate      = $scope.endDate;
        params.storeCd      = $scope.storeCd;
        params.vendrCd      = $scope.vendrCd;
        params.prodClassCd  = $scope.selectProdClassCd;
        $scope._broadcast('inStockReportDtlReportCtrl', params);

    };
}]);
