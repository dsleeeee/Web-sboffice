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
                // 회원코드 클릭시 해당 기능 목록 조회
                if (col.binding === "dlvrAddr") {
                    var selectedData = s.rows[ht.row].dataItem
                    selectedData.periodStartDate = dateToDaystring($scope.periodStartDate).replaceAll('-', '');
                    selectedData.periodEndDate = dateToDaystring($scope.periodEndDate).replaceAll('-', '');
                    $scope.setSelectedMember(selectedData);
                    var detailScope = agrid.getScope('dlvrReceiptDetailCtrl');
                    detailScope._broadcast('dlvrReceiptDetailCtrl', $scope.getSelectedMember());
                    event.preventDefault();
                }
            }
        });
    };

    // 조회
    $scope.dlvrReceiptSearch = function(){
        var params = {};
        params.periodStartDate = dateToDaystring($scope.periodStartDate).replaceAll('-', '');
        params.periodEndDate = dateToDaystring($scope.periodEndDate).replaceAll('-', '');
        params.listScale = $scope.listScale;
        $scope._inquiryMain("/dlvr/manage/anals/dlvrZone/getDlvrReceiptList.sb", params, function() {});
    };
}])
;

app.controller('dlvrReceiptDetailCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrReceiptDetailCtrl', $scope, $http, true));
    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleDatail", gvListScaleBoxData);

    $scope.selectedMember;
    $scope.setSelectedMember = function (data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };
    $scope.data = new wijmo.collections.CollectionView([]);

    $scope.initGrid = function (s, e) {
    };
    $scope.$on("dlvrReceiptDetailCtrl", function (event, data) {
        $scope.setSelectedMember(data);
        $scope.getReceiptDetailList();
        event.preventDefault();
    });
    // 상세 조회
    $scope.getReceiptDetailList = function () {
        var params = {};
        params.hqOfficeCd = $scope.getSelectedMember().hqOfficeCd;
        params.hqBrandCd = $scope.getSelectedMember().hqBrandCd;
        params.periodStartDate = $scope.getSelectedMember().periodStartDate;
        params.periodEndDate = $scope.getSelectedMember().periodEndDate;
        params.posNo = $scope.getSelectedMember().posNo;
        params.listScale = $scope.listScaleDatail;

        $scope.$broadcast('loadingPopupActive');
        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: '/dlvr/manage/anals/dlvrZone/getDlvrReceiptDetailList.sb', /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (response.data.data.list.length === undefined || response.data.data.list.length === 0) {
                $scope.data = new wijmo.collections.CollectionView([]);
            } else {
                var data = new wijmo.collections.CollectionView(response.data.data.list);
                data.trackChanges = true;
                $scope.data = data;
            }
        }, function errorCallback(response) {
            $scope.$broadcast('loadingPopupInactive');
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            if (typeof callback === 'function') {
                setTimeout(function () {
                    callback();
                }, 10);
            }
        });
    };
}]);