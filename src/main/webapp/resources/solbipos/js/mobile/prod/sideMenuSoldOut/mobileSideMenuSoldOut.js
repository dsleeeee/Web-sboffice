/****************************************************************
 *
 * 파일명 : mobileSideMenuSoldOut.js
 * 설  명 : (모바일) 상품관리 > 품절관리(선택메뉴)
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.03     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 품절여부
var soldOutYnData = [
    {"name": "품절", "value": "Y"},
    {"name": "정상", "value": "N"}
];

app.controller('mobileSideMenuSoldOutCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileSideMenuSoldOutCtrl', $scope, $http, false));

    $scope._setComboData("soldOutYnChg", soldOutYnData);

    // 일괄적용
    $scope.batchChange = function(chgGubun) {
        var scope = agrid.getScope("mobileSideMenuProdSoldOutGrid");
        if(scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["mobile.cmm.empty.data"]);
            return false;
        }

        var params = new Array();
        for (var i = 0; i < scope.flex.collectionView.items.length; i++) {
            if(scope.flex.collectionView.items[i].gChk) {
                params.push(scope.flex.collectionView.items[i]);
            }
        }
        if(params.length <= 0) {
            s_alert.pop(messages["mobile.cmm.not.select"]);
            return;
        }

        for (var i = 0; i < scope.flex.collectionView.items.length; i++) {
            if(scope.flex.collectionView.items[i].gChk) {
                scope.flex.collectionView.items[i].soldOutYn = $scope.soldOutYnChg;
            }
        }
        scope.flex.refresh();
    };


    // <-- 그리드 저장 -->
    $scope.save = function() {
        var scope = agrid.getScope("mobileSideMenuProdSoldOutGrid");
        scope.sideMenuSave();
    };

    // 다중매장 선택팝업
    $scope.mobileSideMenuSoldOutStoreShow = function () {
        $scope._broadcast('mobileSideMenuSoldOutStoreCtrl');
    };

}]);

/**
 *  선택그룹 그리드 생성
 */
app.controller('mobileSideMenuGrpSoldOutGrid', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileSideMenuGrpSoldOutGrid', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === 'sdselGrpCd') {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });
        // 선택그룹 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            if (col.binding === 'sdselGrpCd') {
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
                if ( col.binding === 'sdselGrpCd') {
                    if (selectedRow.sdselGrpCd !== '' && selectedRow.sdselGrpCd !== undefined && selectedRow.sdselGrpCd !== '자동채번') {
                        $("#sideSelectGroupTitle").html(messages["mobile.sideMenuSoldOut.sideMenuClass"]+ " [" + selectedRow.sdselGrpCd + "]" + selectedRow.sdselGrpNm);
                        $("#sideClassTitle").html(messages["mobile.sideMenuSoldOut.sideMenuProd"]);

                        $scope._broadcast('mobileSideMenuClassSoldOutGrid', selectedRow.sdselGrpCd);
                        var prodGrid = agrid.getScope('mobileSideMenuProdSoldOutGrid');
                        prodGrid._gridDataInit();
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileSideMenuGrpSoldOutGrid", function(event, data) {
        gridOpen("mobileSideMenuGrpSoldOut");

        var classGrid = agrid.getScope('mobileSideMenuClassSoldOutGrid');
        classGrid._gridDataInit();
        var prodGrid = agrid.getScope('mobileSideMenuProdSoldOutGrid');
        prodGrid._gridDataInit();

        $scope.searchMobileSideMenuGrp();
        event.preventDefault();
    });

    $scope.searchMobileSideMenuGrp = function(){
        if($("#mobileSideMenuSoldOutStoreCd").val() === null || $("#mobileSideMenuSoldOutStoreCd").val() === "" || $("#mobileSideMenuSoldOutStoreCd").val() === undefined){
            $scope._popMsg(messages["mobile.cmm.not.store"]);
            return false;
        }
        var params = {};
        params.storeCd = $("#mobileSideMenuSoldOutStoreCd").val();
        params.sdselGrpNm = $scope.sdselGrpNm;

        $scope._inquirySub("/mobile/prod/sideMenuSoldOut/mobileSideMenuSoldOut/getMobileSideMenuGrpList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileSideMenuGrpSoldOut", $scope.flexMobileSideMenuGrpSoldOut, "N");
            $("#sideSelectGroupTitle").html(messages["mobile.sideMenuSoldOut.sideMenuClass"]);
            $("#sideClassTitle").html(messages["mobile.sideMenuSoldOut.sideMenuProd"]);
        }, false);
    };
    // <-- //검색 호출 -->

}]);

/**
 *  선택분류 그리드 생성
 */
app.controller('mobileSideMenuClassSoldOutGrid', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileSideMenuClassSoldOutGrid', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === 'sdselClassCd') {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });
        // 선택분류 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            if (col.binding === 'sdselClassCd') {
                var dataItem = s.rows[e.row].dataItem;
                if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
                    e.cancel = true;
                }
            }
        });
        // 선택분류 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if(col.binding === 'sdselClassCd') {
                    $("#sideClassTitle").html(messages["mobile.sideMenuSoldOut.sideMenuProd"] + " [" + selectedRow.sdselClassCd + "]" + selectedRow.sdselClassNm);

                    var params = {};
                    params.sdselClassCd = selectedRow.sdselClassCd;
                    $scope._broadcast('mobileSideMenuProdSoldOutGrid', params);
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileSideMenuClassSoldOutGrid", function(event, data) {
        gridOpen("mobileSideMenuClassSoldOut");
        $scope.searchMobileSideMenuClass(data);
        event.preventDefault();
    });

    $scope.searchMobileSideMenuClass = function(data){
        var params = {};
        params.sdselGrpCd = data;
        params.storeCd = $("#mobileSideMenuSoldOutStoreCd").val();

        $scope._inquirySub("/mobile/prod/sideMenuSoldOut/mobileSideMenuSoldOut/getMobileSideMenuClassList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileSideMenuClassSoldOut", $scope.flexMobileSideMenuClassSoldOut, "N");
        }, false);
    };
    // <-- //검색 호출 -->
}]);

/**
 *  선택상품 그리드 생성
 */
app.controller('mobileSideMenuProdSoldOutGrid', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileSideMenuProdSoldOutGrid', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 품절여부
        $scope.soldOutYnDataMap = new wijmo.grid.DataMap(soldOutYnData, 'value', 'name');

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
    $scope.$on("mobileSideMenuProdSoldOutGrid", function(event, data) {
        gridOpen("mobileSideMenuProdSoldOut");
        $scope.searchMobileSideMenuProd(data);
        event.preventDefault();
    });

    $scope.searchMobileSideMenuProd = function(data){
        var params = {};
        params.sdselClassCd = data.sdselClassCd;
        params.storeCd = $("#mobileSideMenuSoldOutStoreCd").val();

        $scope._inquirySub("/mobile/prod/sideMenuSoldOut/mobileSideMenuSoldOut/getMobileSideMenuProdList.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileSideMenuProdSoldOut", $scope.flex, "N");
        }, false);
    };
    // <-- //검색 호출 -->

    // <-- 그리드 저장 -->
    $scope.sideMenuSave = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].status = "U";
                    $scope.flex.collectionView.items[i].storeCd = $("#mobileSideMenuSoldOutStoreCd").val();
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/mobile/prod/sideMenuSoldOut/mobileSideMenuSoldOut/getMobileSideMenuSoldOutSave.sb", params, function(){
                var param = {};
                param.sdselClassCd = $("#sideClassTitle").html().substr(6, 6);
                $scope.searchMobileSideMenuProd(param);
            });

        });
    };
}]);