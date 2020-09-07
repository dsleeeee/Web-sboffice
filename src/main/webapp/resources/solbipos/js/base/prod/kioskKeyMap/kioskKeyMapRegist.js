/****************************************************************
 *
 * 파일명 : kioskKeyMapRegist.js
 * 설  명 : 키오스크키맵등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.09.03     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('kioskKeyMapRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyMapRegistCtrl', $scope, $http, true));

    // 키오스크용 포스 목록
    $scope._setComboData("posNoCombo", kioskPosList);

    $scope.initGrid = function (s, e) {

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'tuClsCd') {
                    if(item.tuClsCd !== '자동채번') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        //
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "tuClsCd") {
                    if(item.tuClsCd !== '자동채번') {
                        var selectedData = s.rows[ht.row].dataItem;
                        $scope._broadcast('kioskKeyMapKeyCtrl', selectedData);
                        event.preventDefault();
                    }
                }
            }
        });
    };

    $scope.$on("kioskKeyMapRegistCtrl", function(event, data) {

    });

    // 키오스크 카테고리(분류) 조회
    $scope.btnSearchCls = function(){

        var params = {};
        params.posNo = $scope.posNo;

        $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getKioskCategory.sb", params, function() {}, false);
    }

    // 카테고리(분류) 상위 순서 이동
    $scope.rowMoveUpCls = function () {
        var movedRows = 0;
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (i > 0 && item.gChk) {
                if (!$scope.flex.collectionView.items[i - 1].gChk) {
                    movedRows = i - 1;
                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                    $scope.flex.collectionView.items[i] = tmpItem;
                    $scope.flex.collectionView.commitEdit();
                    $scope.flex.collectionView.refresh();
                }
            }
        }
        $scope.flex.select(movedRows, 1);
    }

    // 카테고리(분류) 하위 순서 이동
    $scope.rowMoveDownCls = function () {
        var movedRows = $scope.flex.itemsSource.itemCount - 1;
        for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (i < ($scope.flex.itemsSource.itemCount - 1) && item.gChk) {
                if (!$scope.flex.collectionView.items[i + 1].gChk) {
                    movedRows = i + 1;
                    var tmpItem = $scope.flex.collectionView.items[movedRows];
                    $scope.flex.collectionView.items[movedRows] = $scope.flex.collectionView.items[i];
                    $scope.flex.collectionView.items[i] = tmpItem;
                    $scope.flex.collectionView.commitEdit();
                    $scope.flex.collectionView.refresh();
                }
            }
        }
        $scope.flex.select(movedRows, 1);
    }

    // 카테고리(분류) 빈칸 추가
    $scope.blankRowCls = function () {

        var params = {};

        params.gChk = false;
        params.tuClsCd = '자동채번';
        params.tuClsNm = 'KIOSK_BLANK_CLS_NM_X';

        // 행추가
        $scope._addRow(params, 2);
    }

    // 카테고리(분류) 추가
    $scope.addRowCls = function () {

        var params = {};

        params.gChk = false;
        params.tuClsCd = '자동채번';
        params.tuClsNm = '';
        
        // 행추가
        $scope._addRow(params, 2);
    }

    // 카테고리(분류) 삭제
    $scope.delRowCls = function () {
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
                $scope.flex.collectionView.removeAt(i);
            }
        }
    }

    // 카테고리(분류) 저장
    $scope.saveCls = function () {

        $scope.flex.collectionView.commitEdit();

        // 파라미터 설정
        var params = [];

        for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
            $scope.flex.collectionView.itemsRemoved[d].posNo = $scope.posNo;
            $scope.flex.collectionView.itemsRemoved[d].status = 'D';
            params.push($scope.flex.collectionView.itemsRemoved[d]);
        }

        // indexNo 재설정
        var editItems = [];
        for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
            if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
                editItems.push($scope.flex.collectionView.items[s]);
            }
        }

        for (var s = 0; s < editItems.length; s++) {
            editItems[s].indexNo = (s + 1);
            $scope.flex.collectionView.editItem(editItems[s]);
            editItems[s].status = "U";
            $scope.flex.collectionView.commitEdit();
        }

        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            $scope.flex.collectionView.itemsEdited[u].posNo = $scope.posNo;
            $scope.flex.collectionView.itemsEdited[u].status = 'U';
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].posNo = $scope.posNo;
            $scope.flex.collectionView.itemsAdded[i].status = 'I';
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        // 생성, 수정 Validation Check
        for (var m = 0; m < params.length; m++) {
            if(params[m].status !== 'D') {
                if(  params[m].tuClsNm === null  || params[m].tuClsNm === '') {
                    $scope._popMsg(messages['kioskKeyMap.require.category.msg']); // 카테고리명을 입력해주세요.
                    return false;
                }
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/kioskKeyMap/kioskKeyMap/saveKioskCategory.sb', params, function() {

            // 재조회
            $scope.btnSearchCls();
        });
    }

}]);


//
app.controller('kioskKeyMapKeyCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyMapKeyCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {

    };

    $scope.$on("kioskKeyMapKeyCtrl", function(event, data) {

        alert(data.tuClsCd);

    });

}]);


//
app.controller('kioskKeyProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyProdCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {

    };

    $scope.$on("kioskKeyProdCtrl", function(event, data) {

    });

}]);