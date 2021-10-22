/****************************************************************
 *
 * 파일명 : dlvrEmp.js
 * 설  명 : 배달사원정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.10.20     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('dlvrEmpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrEmpCtrl', $scope, $http, false));

    //페이지 스케일 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 등록일자 전체기간 체크박스
    $scope.isChecked = true;

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // SMS 수신여부 셋팅
    $scope._getComboDataQuery('072', 'srchSmsRecvYn', 'A');

    // 사용여부 셋팅
    $scope._getComboDataQuery('067', 'srchUseYn', 'A');

    $scope.initGrid = function (s, e) {
        
        $scope.smsRecvDataMap = new wijmo.grid.DataMap(smsRecvYn, 'value', 'name');
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

        // // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 배달사원번호, 배달사원명
                if (col.binding === "dlvrEmpNo" ||  col.binding === "dlvrEmpNm") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 배달사원번호, 배달사원명 클릭시 상세정보 조회
                if (col.binding === "dlvrEmpNo" ||  col.binding === "dlvrEmpNm") {
                    var selectedRow = s.rows[ht.row].dataItem;

                    $scope.wjDlvrEmpDtlPopLayer.show(true);
                    $scope._broadcast('dlvrEmpDtlCtrl', selectedRow);
                }
            }
        });

    };

    $scope.$on("dlvrEmpCtrl", function (event, data) {
        $scope.searchDlvrEmp();
        event.preventDefault();
    });

    // 배달사원리스트 조회
    $scope.searchDlvrEmp = function(){

        var params = {};
        params.chkDt = $scope.isChecked;
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.dlvrEmpNo = $("#srchDlvrEmpNo").val();
        params.dlvrEmpNm = $("#srchDlvrEmpNm").val();
        params.hpNo = $("#srchHpNo").val();
        params.smsRecvYn = $scope.srchSmsRecvYnCombo.selectedValue;
        params.useYn = $scope.srchUseYnCombo.selectedValue;
        params.listScale = $scope.listScale;;

        $scope._inquiryMain("/dlvr/manage/info/dlvrEmp/getDlvrEmpList.sb", params, function() {}, false);
    };

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    // 신규사원등록
    $scope.btnDlvrEmpReg = function () {
        $scope.wjDlvrEmpRegPopLayer.show(true);
        $scope._broadcast('dlvrEmpRegCtrl');
    };

}]);
