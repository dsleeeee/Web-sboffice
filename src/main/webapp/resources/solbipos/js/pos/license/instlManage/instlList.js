/****************************************************************
 *
 * 파일명 : instlList.js
 * 설  명 : 설치관리 설치현황탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.10.21     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 설치구분 DropBoxDataMap
var instFgData = [
    {"name": "설치의뢰", "value": "0"},
    {"name": "신규설치", "value": "1"},
    {"name": "재설치", "value": "2"}
];

/**
 *  설치현황 조회 그리드 생성
 */
app.controller('instlListCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('instlListCtrl', $scope, $http, true));

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.startDateCombo.isReadOnly = $scope.isChecked;
        $scope.endDateCombo.isReadOnly = $scope.isChecked;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "agencyNm") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 업체명 클릭시 상세정보 조회
                if ( col.binding === "agencyNm") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.agencyCd = selectedRow.agencyCd;
                    params.agencyNm  = selectedRow.agencyNm;
                    params.posCnt  = selectedRow.posCnt;
                    params.restCnt  = selectedRow.restCnt;
                    params.chkDt = $scope.isChecked;
                    params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
                    params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');

                    var storeScope = agrid.getScope('instlListDetailCtrl');
                    storeScope._broadcast('instlListDetailCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("instlListCtrl", function(event, data) {
        $scope.searchInstlList();
        event.preventDefault();
    });

    // 설치현황 그리드 조회
    $scope.searchInstlList = function() {
        var params = {};
        params.chkDt = $scope.isChecked;
        params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
        params.srchAgencyCd = $("#il_srchAgencyCd").val();
        params.srchAgencyNm = $("#il_srchAgencyNm").val();
        params.orgnFg = orgnFg;
        params.agencyCd = orgnCd;

        $scope._inquiryMain("/pos/license/instlManage/instlManage/getInstlList.sb", params, function() {
            $scope.$apply(function() {
                var storeScope = agrid.getScope('instlListDetailCtrl');
                storeScope._gridDataInit();
            });
        }, false);
    };
    // <-- //검색 호출 -->

}]);


/**
 *  설치현황 상세조회 그리드 생성
 */
app.controller('instlListDetailCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('instlListDetailCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgData, 'value', 'name'); //상태구분
        $scope.instFgDataMap = new wijmo.grid.DataMap(instFgData, 'value', 'name'); //설치구분

        //그리드 링크설정
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "posNo") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 포스번호 클릭시 posNo 조회
                if ( col.binding === "posNo") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params      = {};
                    params.storeCd = selectedRow.storeCd;
                    params.storeNm  = selectedRow.storeNm;
                    params.posNo  = selectedRow.posNo;

                    $scope._broadcast('instlDtlCtrl', params);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("instlListDetailCtrl", function(event, data) {
        if( !isEmptyObject(data) ) {
            $scope.setSelectedStoreDetail(data);
        }
        $scope.searchInstlDetailList();

        $("#lblAgencyCdInstl").text("[" + data.agencyCd + "]");
        $("#lblAgencyNmInstl").text(data.agencyNm);
        $("#lblPosCntInstl").text("( "+ messages["instl.managePosCnt"] + " : " + data.posCnt);
        $("#lblRestCntInstl").text(" | " + messages["instl.instlStayCnt"] + " : " + data.restCnt + " )");

        event.preventDefault();
    });

    // 설치현황 상세 그리드 조회
    $scope.searchInstlDetailList = function() {
        var params = {};
        params.agencyCd = $scope.selectedStoreDetail.agencyCd;
        params.chkDt = $scope.selectedStoreDetail.chkDt;
        params.startDate = $scope.selectedStoreDetail.startDate;
        params.endDate = $scope.selectedStoreDetail.endDate;
        params.listScale = 30;

        $scope._inquiryMain("/pos/license/instlManage/instlManage/getInstlDetailList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedStoreDetail;
    $scope.setSelectedStoreDetail = function(store) {
        $scope.selectedStoreDetail = store;
    };
    $scope.getSelectedStoreDetail = function(){
        return $scope.selectedStoreDetail;
    };

}]);