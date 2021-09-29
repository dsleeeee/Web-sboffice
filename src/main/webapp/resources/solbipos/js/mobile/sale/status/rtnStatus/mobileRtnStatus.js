/****************************************************************
 *
 * 파일명 : mobileRtnStatus.js
 * 설  명 : (모바일) 매출현황 > 반품현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.28     권지현      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

/**
 *  반품현황 그리드 생성
 */
app.controller('mobileRtnStatusCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileRtnStatusCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }

                if (col.binding === "rtnRealSaleAmt") {
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
                if (col.binding === "rtnRealSaleAmt") { // 반품금액 클릭
                    var params    = {};
                    params.srchStoreCd = $("#mobileRtnStatusStoreCd").val();
                    params.startDate = selectedRow.saleDate;
                    $scope.wjRtnStatusDtlLayer.show(true);
                    $scope._broadcast('rtnStatusDtlCtrl', params);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileRtnStatusCtrl", function(event, data) {
        gridOpen("mobileRtnStatus");

        $scope.searchMobileRtnStatus(data);
        event.preventDefault();
    });

    $scope.searchMobileRtnStatus = function(data){
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
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.srchStoreCd = $("#mobileRtnStatusStoreCd").val();

        $scope._inquirySub("/mobile/sale/status/rtnStatus/rtnStatus/getMobileRtnStatusList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileRtnStatus", $scope.flexMobileRtnStatus, "Y");
        }, false);
    };
    // <-- //검색 호출 -->

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileRtnStatusStoreShow = function () {
        $scope._broadcast('mobileRtnStatusStoreCtrl');
    };
}]);
