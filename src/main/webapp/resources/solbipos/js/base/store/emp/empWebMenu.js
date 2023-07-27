/****************************************************************
 *
 * 파일명 : empWebMenu.js
 * 설  명 : 메뉴별 권한복사 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.05.10     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 그룹관리-그룹등록
app.controller('empWebMenuCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empWebMenuCtrl', $scope, $http, true));

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("userHqBrandCdCombo", userHqBrandCdComboList);// 상품브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹

    $scope.initGrid = function (s, e) {

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'sMenuNm') {
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
                if (col.binding === "sMenuNm") {
                    $("#sMenuCd").val(selectedRow.sMenuCd);
                    $("#lblMenu").text('[' + selectedRow.sMenuCd + ']' + selectedRow.sMenuNm);
                    var scope = agrid.getScope('useEmpCtrl');
                    scope.searchUseEmp();
                }
            }
        });
    };

    $scope.$on("empWebMenuCtrl", function(event, data) {
        // 웹메뉴조회
        $scope.searchWebMenu();
        event.preventDefault();
    });

    // 웹메뉴조회
    $scope.searchWebMenu = function () {
        var params = [];
        params.sMenuNm = $scope.sMenuNm;

        $scope._inquirySub("/base/store/emp/empWebMenu/getMenuList.sb", params, function (){

            var useScope = agrid.getScope('useEmpCtrl');
            useScope.gridDefault();
            var unusedScope = agrid.getScope('unusedEmpCtrl');
            unusedScope.gridDefault();

            $("#sMenuNm").val("");
            $("#lblMenu").text("");

        });
    }
}]);

// 사용사원
app.controller('useEmpCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('useEmpCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {
    };

    $scope.$on("useEmpCtrl", function(event, data) {
        // 사용사원조회
        $scope.searchUseEmp();
        event.preventDefault();
    });

    // 사용사원조회
    $scope.searchUseEmp = function () {

        var params = {};
        params.sMenuCd = $("#sMenuCd").val();
        var scope = agrid.getScope('empWebMenuCtrl');
        params.empNo = scope.srchEmpNo;
        params.empNm = scope.srchEmpNm;
        params.userId = scope.srchUserId;
        params.mpNo = scope.srchMpNo;
        params.userHqBrand = scope.userHqBrandCd;
        params.momsTeam = scope.momsTeam;
        params.momsAcShop = scope.momsAcShop;
        params.momsAreaFg = scope.momsAreaFg;
        params.momsCommercial = scope.momsCommercial;
        params.momsShopType = scope.momsShopType;
        params.momsStoreManageType = scope.momsStoreManageType;
        params.branchCd = scope.branchCd;
        $scope._inquirySub("/base/store/emp/empWebMenu/getUseEmp.sb", params, function() {

            // 미사용사원조회
            var scope = agrid.getScope("unusedEmpCtrl");
            scope.searchUnusedEmp();

        }, false);
    }

    // 사용사원삭제(미사용사원으로 등록)
    $scope.delEmp = function(){

        $scope._popConfirm(messages["cmm.choo.save"], function () {
            // 파라미터 설정
            var params = new Array();

            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                if ($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].sMenuCd = $("#sMenuCd").val();
                    $scope.flex.collectionView.items[i].status = 'I';

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/store/emp/empWebMenu/getEmpWebMenuSave.sb", params, function () {

                // 사용사원 재조회
                $scope.searchUseEmp();

                // 미사용사원 재조회
                var scope = agrid.getScope("unusedEmpCtrl");
                scope.searchUnusedEmp();

            });
        });
    };

    // 그리드 초기화
    $scope.gridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data     = cv;
            $scope.flex.refresh();
        }, 10);
    };
}]);

// 미사용사원
app.controller('unusedEmpCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('unusedEmpCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {
    };

    $scope.$on("unusedEmpCtrl", function(event, data) {
        // 미사용사원조회
        $scope.searchUnusedEmp();
        event.preventDefault();
    });

    // 미사용사원조회
    $scope.searchUnusedEmp = function(){
        var params = {};
        params.sMenuCd = $("#sMenuCd").val();
        var scope = agrid.getScope('empWebMenuCtrl');
        params.empNo = scope.srchEmpNo;
        params.empNm = scope.srchEmpNm;
        params.userId = scope.srchUserId;
        params.mpNo = scope.srchMpNo;
        params.userHqBrand = scope.userHqBrandCd;
        params.momsTeam = scope.momsTeam;
        params.momsAcShop = scope.momsAcShop;
        params.momsAreaFg = scope.momsAreaFg;
        params.momsCommercial = scope.momsCommercial;
        params.momsShopType = scope.momsShopType;
        params.momsStoreManageType = scope.momsStoreManageType;
        params.branchCd = scope.branchCd;
        $scope._inquirySub("/base/store/emp/empWebMenu/getUnuesdEmp.sb", params);
    };

    // 사용사원으로 등록(미사용 사원에서 삭제)
    $scope.saveEmp = function () {

        $scope._popConfirm(messages["cmm.choo.save"], function () {
            $scope.flex.collectionView.commitEdit();

            var params = new Array();

            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                if ($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].sMenuCd = $("#sMenuCd").val();
                    $scope.flex.collectionView.items[i].status = 'D';

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/store/emp/empWebMenu/getEmpWebMenuSave.sb", params, function () {

                // 사용사원 재조회
                var scope = agrid.getScope("useEmpCtrl");
                scope.searchUseEmp();
            });
        });
    };

    // 그리드 초기화
    $scope.gridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data     = cv;
            $scope.flex.refresh();
        }, 10);
    };
}]);
