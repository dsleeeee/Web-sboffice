/**
 * get application
 */
var app = agrid.getApp();
app.controller('dlvrManageCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrManageCtrl', $scope, $http, false));

    $scope._getComboDataQuery('067', 'useYn', '');

    $scope.selectedMember;
    $scope.setSelectedMember = function (data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

    $scope.initGrid = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
        $scope.$apply(function () {
            $scope.data = new wijmo.collections.CollectionView(dlvrFirstList);
        });

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "dlvrLzoneCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });
    };

    // row 추가
    $scope.dlvrAreaAdd = function () {
        // 파라미터 설정

        var params = {};
        params.dlvrLzoneCd = '';
        params.dlvrLzoneNm = '';
        params.pageNo = '1';
        params.useYn = 'Y';
        params.inFg = 'W';
        // 추가 row
        $scope._addRow(params);
    };
    // 삭제
    $scope.dlvrAreaDel = function () {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }
    };

    // up
    $scope.dlvrAreaUp = function () {
        console.log($scope.flex.collectionView);
        $scope.flex.collectionView.items[1] = $scope.flex.collectionView.items[0];
    };
    // dn
    $scope.dlvrAreaDn = function () {

    };

    /*********************************************************
     * 저장
     * *******************************************************/
    $scope.dlvrAreaSave = function () {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = "I";
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $.postJSONArray("/dlvr/manage/info/dlvrZone/regist.sb", params, function (result) {
            $scope.data = new wijmo.collections.CollectionView(dlvrFirstList);
        });
    };
}])
;

app.controller('dlvrManageDetailCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrManageDetailCtrl', $scope, $http, true));
    $scope.selectedMember;
    $scope.setSelectedMember = function (data) {
        $scope.selectedMember = data;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };
    $scope.data = new wijmo.collections.CollectionView([]);

    $scope.initGrid = function (s, e) {
        if ($scope.userUseYn === false) {
            // ReadOnly 효과설정
            s.formatItem.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "payCd" || col.binding === "membrOrgnCd" || col.binding === "accRate") {
                        col.isReadOnly = true;
                    }
                }
            });
        }
    };
}]);