/****************************************************************
 *
 * 파일명 : mobileKioskKeyMap.js
 * 설  명 : (모바일) 상품관리 > 키오스크키맵
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.08.22     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// KIOSK중분류사용
var tuMClsFgStoreRegistComboData = [
    {"name":"전체","value":""},
    {"name":"미사용","value":"0"},
    {"name":"중분류사용","value":"2"}
];
/**
 *  조회조건
 */
app.controller('mobileKioskKeyMapCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileKioskKeyMapCtrl', $scope, $http, false));

    // 입력구분 콤보박스
    $scope._setComboData("posNo", kioskPosList); // 키오스크용 포스 목록

}]);

/**
 *  키맵
 */
app.controller('mobileKioskKeyMapGrpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileKioskKeyMapGrpCtrl', $scope, $http, false));

    // 입력구분 콤보박스
    $scope._setComboData("posNo", kioskPosList); // 키오스크용 포스 목록

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.tuMClsFgDataMap = new wijmo.grid.DataMap(tuMClsFgStoreRegistComboData, 'value', 'name'); // KIOSK중분류사용
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === 'tuClsCd') {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });
        // 선택그룹 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            if (col.binding === 'tuClsCd') {
                var dataItem = s.rows[e.row].dataItem;
                if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
                    e.cancel = true;
                }
            }
        });
        // 선택그룹 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if ( col.binding === 'tuClsCd') {
                    var params = {};
                    params.tuClsType    = selectedRow.tuClsType;
                    params.posNo        = selectedRow.posNo;
                    params.tuClsCd      = selectedRow.tuClsCd;
                    params.envstCd      = selectedRow.envstCd;

                    if(selectedRow.tuMClsFg === "2"){
                        $scope._broadcast('mobileKioskKeyMapMGrpCtrl', params);
                        $("#kioskKeyMapMGrpTitle").html(  messages["mobile.kioskKeyMap.kioskKeyMapMGrp"] + "[" + selectedRow.tuClsCd + "]" + selectedRow.tuClsNm);
                    } else {
                        $("#kioskKeyMapProdTitle").html("[" + selectedRow.tuClsCd + "]" + selectedRow.tuClsNm);

                        $scope._broadcast('mobileKioskKeyMapProdCtrl', params);
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileKioskKeyMapGrpCtrl", function(event, data) {
        $("#kioskKeyMapMGrpTitle").html(messages["mobile.kioskKeyMap.kioskKeyMapMGrp"]);
        $("#kioskKeyMapProdTitle").html("");
        var scope = agrid.getScope('mobileKioskKeyMapMGrpCtrl');
        scope._gridDataInit();
        var scope2 = agrid.getScope('mobileKioskKeyMapProdCtrl');
        scope2._gridDataInit();

        $scope.searchMobileKioskKeyMap(data);
        event.preventDefault();
    });

    $scope.searchMobileKioskKeyMap = function(data){
        var params = {};
        params.posNo       = $scope.posNo;
        $("#searchPosNo").html($scope.posNo);

        var url = "";
        if(data === "4068"){
            url = "/mobile/prod/kioskKeyMap/mobileKioskKeyMap/getMobileKioskKeyMapStoreList.sb";
        } else if(data === "4069"){
            url = "/mobile/prod/kioskKeyMap/mobileKioskKeyMap/getMobileKioskKeyMapPackList.sb";
        }

        $scope._inquirySub(url, params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileKioskKeyMapGrp", $scope.flex, "N");
            if($scope.flex.collectionView.items[0].tuMClsFg === "2"){
                $("#mobileKioskKeyMapMGrpDiv").css("display", "");
                var scope = agrid.getScope('mobileKioskKeyMapMGrpCtrl');
                scope.flex.refresh();
            } else {
                $("#mobileKioskKeyMapMGrpDiv").css("display", "none");
            }
        }, true);
    };

    // <-- 그리드 저장 -->
    $scope.save = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["mobile.cmm.empty.data"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                $scope.flex.collectionView.items[i].indexNo = i + 1;
                params.push($scope.flex.collectionView.items[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/mobile/prod/kioskKeyMap/mobileKioskKeyMap/getMobileKioskKeyMapGrpSave.sb", params, function(){
                $scope.searchMobileKioskKeyMap($scope.flex.collectionView.items[0].envstCd);
            });
        });
    };

    // 키맵 상위 순서 이동
    $scope.rowMoveUpKeyMap = function () {
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

    // 키맵 하위 순서 이동
    $scope.rowMoveDownKeyMap = function () {
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
}]);

/**
 *  중분류
 */
app.controller('mobileKioskKeyMapMGrpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileKioskKeyMapMGrpCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === 'tuMClsCd') {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });
        // 선택그룹 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            if (col.binding === 'tuMClsCd') {
                var dataItem = s.rows[e.row].dataItem;
                if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
                    e.cancel = true;
                }
            }
        });
        // 선택그룹 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if ( col.binding === 'tuMClsCd') {
                    var params = {};
                    params.tuClsType    = selectedRow.tuClsType;
                    params.posNo        = selectedRow.posNo;
                    params.tuClsCd      = selectedRow.tuMClsCd;
                    params.envstCd      = selectedRow.envstCd;
                    $("#kioskKeyMapProdTitle").html("[" + selectedRow.tuMClsCd + "]" + selectedRow.tuMClsNm);

                    $scope._broadcast('mobileKioskKeyMapProdCtrl', params);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileKioskKeyMapMGrpCtrl", function(event, data) {
        $("#kioskKeyMapProdTitle").html("");
        var scope = agrid.getScope('mobileKioskKeyMapProdCtrl');
        scope._gridDataInit();

        $scope.searchMobileKioskKeyMapM(data);
        event.preventDefault();
    });

    $scope.searchMobileKioskKeyMapM = function(data){
        var params = {};
        params.tuClsType    = data.tuClsType;
        params.posNo        = data.posNo;
        params.tuClsCd      = data.tuClsCd;
        params.envstCd      = data.envstCd;
        $scope._inquirySub("/mobile/prod/kioskKeyMap/mobileKioskKeyMap/getMobileKioskKeyMapMList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileKioskKeyMapMGrp", $scope.flex, "N");

        }, true);
    };

    // <-- 그리드 저장 -->
    $scope.save = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["mobile.cmm.empty.data"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                $scope.flex.collectionView.items[i].indexNo = i + 1;
                params.push($scope.flex.collectionView.items[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/mobile/prod/kioskKeyMap/mobileKioskKeyMap/getMobileKioskKeyMapMGrpSave.sb", params, function(){
                $scope.searchMobileKioskKeyMapM($scope.flex.collectionView.items[0]);
            });
        });
    };

    // 키맵 상위 순서 이동
    $scope.rowMoveUpKeyMap = function () {
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

    // 키맵 하위 순서 이동
    $scope.rowMoveDownKeyMap = function () {
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
}]);


/**
 *  키맵 상품
 */
app.controller('mobileKioskKeyMapProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileKioskKeyMapProdCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 품절여부 변경 시 체크박스 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "soldOutYn") {
                    item.gChk = true;
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileKioskKeyMapProdCtrl", function(event, data) {
        gridOpen("mobileKioskKeyMapProd");
        $scope.searchMobileKioskKeyMapProd(data);
        event.preventDefault();
    });

    $scope.searchMobileKioskKeyMapProd = function(data){
        var params = {};
        params.tuClsType    = data.tuClsType;
        params.posNo        = data.posNo;
        params.tuClsCd      = data.tuClsCd;

        $scope._inquirySub("/mobile/prod/kioskKeyMap/mobileKioskKeyMap/getMobileKioskKeyMapProdList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileKioskKeyMapProd", $scope.flex, "N");
        }, false);
    };
    // <-- //검색 호출 -->

    // <-- 그리드 저장 -->
    $scope.saveProd = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["mobile.cmm.empty.data"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                $scope.flex.collectionView.items[i].indexNo = i + 1;
                params.push($scope.flex.collectionView.items[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/mobile/prod/kioskKeyMap/mobileKioskKeyMap/getMobileKioskKeyMapProdSave.sb", params, function(){
                var params = {};
                params.tuClsType    = $scope.flex.collectionView.items[0].tuClsType;
                params.posNo        = $scope.flex.collectionView.items[0].posNo;
                params.tuClsCd      = $scope.flex.collectionView.items[0].tuClsCd;
                $scope.searchMobileKioskKeyMapProd(params);
            });
        });
    };

    // 키맵 상위 순서 이동
    $scope.rowMoveUpKeyMapProd = function () {
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

    // 키맵 하위 순서 이동
    $scope.rowMoveDownKeyMapProd = function () {
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
}]);