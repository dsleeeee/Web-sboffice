/****************************************************************
 *
 * 파일명 : dclzManage.js
 * 설  명 : 근태관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.02.09     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 입력구분 VALUE
var inFg = [
    {"name":"전체","value":""},
    {"name":"POS","value":"010"},
    {"name":"WEB","value":"020"}
];

/**
 *  근태관리 생성
 */
app.controller('dclzManageCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dclzManageCtrl', $scope, $http, $timeout, true));

    // 조회일자 콤보박스
    var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 입력구분 콥보박스
    $scope._setComboData("inFg", inFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        
        // 입력구분
        $scope.inFgDataMap = new wijmo.grid.DataMap(inFg, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "empNm") {
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "empNm") { // 사원명 클릭
                    var params    = {};
                    params.storeCd = selectedRow.storeCd;
                    params.empInDate = selectedRow.empInDate;
                    params.empNo = selectedRow.empNo;
                    params.inFg = selectedRow.inFg;
                    $scope.wjDclzRegistLayer.show(true);
                    $scope._broadcast('dclzRegistCtrl', params);
                }
            }
        });
    };

    // 검색 호출
    $scope.$on("dclzManageCtrl", function (event, data) {
        
        // 조회
        $scope.dclzSearch();
        event.preventDefault();
    });

    // 조회
    $scope.dclzSearch = function () {
        var params = {};
        params.startDate = getDate(srchStartDate);
        params.endDate = getDate(srchEndDate);
        params.storeCd = $("#dclzManageStoreCd").val();

        $scope._inquiryMain("/adi/dclz/dclzmanage/dclzmanage/list.sb", params, function() {}, false);
    };

    // 근태 신규등록
    $scope.dclzRegist = function() {
        $scope.wjDclzRegistLayer.show(true);
        $scope._broadcast('dclzRegistCtrl');
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
                    return column.visible;
                }
            }, getToday() + '_' + $(menuNm).selector + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);

    }

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dclzManageStoreShow = function () {
        $scope._broadcast('dclzManageStoreCtrl');
    };

}]);