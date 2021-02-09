/**
 * get application
 */
var app = agrid.getApp();
app.controller('dlvrReceiptCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrReceiptCtrl', $scope, $http, false));
    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    $scope.selectedMember;
    $scope.setSelectedMember = function (data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

    $scope.initGrid = function (s, e) {
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');

        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "dlvrAddr") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                // 회원코드 클릭시 해당 기능 목록 조회
                if (col.binding === "dlvrAddr") {
                    var selectedData = s.rows[ht.row].dataItem
                    selectedData.periodStartDate = dateToDaystring($scope.periodStartDate).replaceAll('-', '');
                    selectedData.periodEndDate = dateToDaystring($scope.periodEndDate).replaceAll('-', '');
                    $scope.setSelectedMember(selectedData);
                    var detailScope = agrid.getScope('dlvrReceiptDetailCtrl');
                    detailScope._broadcast('dlvrReceiptDetailCtrl', selectedRow);
                    event.preventDefault();
                }
            }
        });
    };

    // 조회
    $scope.$on("dlvrReceiptCtrl", function (event, data) {
        $scope.dlvrReceiptSearch();
        event.preventDefault();
    });

    // 조회
    $scope.dlvrReceiptSearch = function () {

        // 배달지별 내역 조회 후 상세 그리드 초기화
        var dlvrReceiptDetailScope = agrid.getScope('dlvrReceiptDetailCtrl');
        dlvrReceiptDetailScope.dtlGridDefault();

        var params = {};
        params.periodStartDate = dateToDaystring($scope.periodStartDate).replaceAll('-', '');
        params.periodEndDate = dateToDaystring($scope.periodEndDate).replaceAll('-', '');
        params.listScale = $scope.listScale;
        // params.listScale = 1;
        $scope._inquirySub("/dlvr/manage/anals/dlvrZone/getDlvrReceiptList.sb", params, function () {
        }, false);
    };
}]);

app.controller('dlvrReceiptDetailCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrReceiptDetailCtrl', $scope, $http, true));
    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    $scope.selectedMember;
    $scope.setSelectedMember = function (data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };
    $scope.data = new wijmo.collections.CollectionView([]);

    $scope.initGrid = function (s, e) {
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // 그리드 포맷
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "billNo") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
                if(col.binding === "saleDate"){
                    e.cell.innerHTML = getFormatDate(e.cell.innerText.substring(0, 8));
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "billNo") {
                    $scope.setSelectedMember(selectedRow);
                    $scope._broadcast('popBillInfo', selectedRow);
                    $scope.wjDlvrInfoLayer.show(true);
                }
            }
        });
    };

    $scope.$on("dlvrReceiptDetailCtrl", function (event, data) {
        $scope.setSelectedMember(data);
        $scope.getReceiptDetailList(data);
        event.preventDefault();
    });

    // 상세 조회
    $scope.getReceiptDetailList = function (data) {
        var params = {};
        params.hqOfficeCd = data.hqOfficeCd;
        params.hqBrandCd = data.hqBrandCd;
        params.periodStartDate = data.periodStartDate;
        params.periodEndDate = data.periodEndDate;
        params.dlvrAddr = data.dlvrAddr;
        params.listScale  = $scope.listScale;

        $scope._inquirySub("/dlvr/manage/anals/dlvrZone/getDlvrReceiptDetailList.sb", params, function () {
        }, false);
    };

    // 상세 그리드 초기화
    $scope.dtlGridDefault = function () {
        var cv          = new wijmo.collections.CollectionView([]);
        cv.trackChanges = true;
        $scope.data     = cv;
        $scope.flex.refresh();
    };

}]);