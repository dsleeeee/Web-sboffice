/****************************************************************
 *
 * 파일명 : optionMenuDlvr.js
 * 설  명 : 사이드메뉴>옵션메뉴(배달) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.11.10     이다솜      1.0
 *
 * **************************************************************/

/**
 * 옵션메뉴(배달) 선택그룹 그리드 생성
 */
app.controller('optionMenuDlvrSelectGroupCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('optionMenuDlvrSelectGroupCtrl', $scope, $http, false));

    // 그리드 Refresh
    $scope.$on('optionMenuDlvrRefresh', function (event, data) {
        $scope.flex.refresh();
    });

    // 선택
    $scope.selectedSelGroup;
    $scope.setSelectedSelGroup = function (data) {
        $scope.selectedSelGroup = data;
    };
    $scope.getSelectedSelGroup = function () {
        return $scope.selectedSelGroup;
    };

    // 콤보박스 데이터 Set
    $scope._setComboData("srchTypeSelGroup3", vSrchTypeSelGroup);
    $scope._setComboData("srchTypeSelProd3", vSrchTypeSelProd);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.fixProdFgDataMap = fixProdFgDataMap;         // 구분
        $scope.sdselTypeFgDataMap = sdselTypeFgDataMap;     // 세트구분

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === 'sdselGrpCd') {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== 'I') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
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
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === 'sdselGrpCd') {
                    if (selectedRow.sdselGrpCd !== '' && selectedRow.sdselGrpCd !== undefined && selectedRow.sdselGrpCd !== '자동채번') {
                        $("#optionMenuDlvrSelectGroupTitle").html(" [" + selectedRow.sdselGrpCd + "]" + selectedRow.sdselGrpNm);
                        $("#optionMenuDlvrSelectClassTitle").html("");
                        
                        // 선택상품 저장 버튼
                        $("#btnSaveSelProd3").hide();
                        
                        $scope.setSelectedSelGroup(selectedRow);
                        $scope._broadcast('optionMenuDlvrSelectClassCtrl', selectedRow);
                        var prodGrid = agrid.getScope('optionMenuDlvrSelectProdCtrl');
                        prodGrid._gridDataInit();
                    }
                }
            }
        });
    };

    // 선택그룹 그리드 조회
    $scope.$on('optionMenuDlvrSelectGroupCtrl', function (event, data) {

        // 초기 버튼 셋팅
        // 선택상품버튼
        $("#btnSaveSelProd3").hide();

        var attrScope = agrid.getScope('optionMenuDlvrSelectClassCtrl');
        attrScope._gridDataInit();   // 선택분류 그리드 초기화

        $("#optionMenuDlvrClassTitle").html("");
        var prodScope = agrid.getScope('optionMenuDlvrSelectProdCtrl');
        prodScope._gridDataInit();   // 선택상품 그리드 초기화

        // 선택그룹 그리드 조회
        $scope.srchSelGroup();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 선택그룹 그리드 조회
    $scope.srchSelGroup = function () {

        // 파라미터
        var params = {};
        params.sdselTypeFg = 'C';
        params.sdselGrpCd = "";
        params.sdselGrpNm = "";
        params.sdselProdCd = "";
        params.sdselProdNm = "";
        params.orgProdFg = '30';  // 오더킷 상품 구분자 

        // 그룹명, 그룹코드 검색조건
        if ($("#txtSelGroup3").val() !== "") {
            if ($scope.srchTypeSelGroup3Combo.selectedValue === "grpNm") {
                params.sdselGrpCd = "";
                params.sdselGrpNm = $("#txtSelGroup3").val();
            } else if ($scope.srchTypeSelGroup3Combo.selectedValue === "grpCd") {
                params.sdselGrpCd = $("#txtSelGroup3").val();
                params.sdselGrpNm = "";
            }
        }

        // 상품명, 상품코드 검색조건
        if ($("#txtSelProd3").val() !== "") {
            if ($scope.srchTypeSelProd3Combo.selectedValue === "prodNm") {
                params.sdselProdCd = "";
                params.sdselProdNm = $("#txtSelProd3").val();
            } else if ($scope.srchTypeSelProd3Combo.selectedValue === "prodCd") {
                params.sdselProdCd = $("#txtSelProd3").val();
                params.sdselProdNm = "";
            }
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain('/base/prod/sideMenu/menuGrp/list.sb', params, function () {

        }, false);
    };

    // 오더킷 바로가기
    $scope.orderkitGoto = function () {

        var params = {};
        params.apiStoreYn = "N"; // 연동상태 저장여부

        var redirectUrl = "";
        var url = "https://test.orderkit.co.kr"; // 개발
        //var url = "https://orderkit.co.kr"; // 운영

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/getOmsUserStatus.sb", params, function (response) {

            var data = response.data.data.list;

            if (data.status === "success" && data.status_code === 200) {

                if (data.data.subscriptionStatus == "EXPIRED" || data.data.subscriptionStatus == "CANCELLED") { // 만료, 해지완료
                    redirectUrl = "/app/dashboard";
                } else if (data.data.subscriptionStatus == "UNPAID") { // 결제 이전
                    redirectUrl = "/app/payment/pay";
                } else if (data.data.subscriptionStatus == "ACTIVE" || data.data.subscriptionStatus == "GRACE" || data.data.subscriptionStatus == "REQ_CANCEL") { // 활성화, 유예, 해지요청
                    if (data.data.base_platform_info.platform === null) { // 배달앱 미연동
                        redirectUrl = "/app/setting/platform";
                    }
                    if (data.data.base_platform_info.platform !== null) { // 정상 연동
                        redirectUrl = "/app/dashboard";
                    }
                } else {
                    redirectUrl = ""
                }

            } else { // data.status === "error" && data.status_code === 500 인 상태
                redirectUrl = ""
            }

            params.redirectUrl = redirectUrl;
            $scope._postJSONQuery.withOutPopUp('/orderkit/orderkit/orderkitRecpOrigin/orderkitGoto.sb', params, function (response) {

                // jwtToken
                var jwtToken = response.data.data;

                if (redirectUrl !== "") {
                    console.log("url : " + url + "/auth/pos/url?token=" + jwtToken);
                    window.open(url + "/auth/pos/url?token=" + jwtToken, 'newWindow');
                } else {
                    console.log("url : " + url + "/auth/pos?token=" + jwtToken);
                    window.open(url + "/auth/pos?token=" + jwtToken, 'newWindow');
                }

            });

        });
    };

}]);

/**
 * 옵션메뉴(배달) 선택분류 그리드 생성
 */
app.controller('optionMenuDlvrSelectClassCtrl', ['$scope', '$http', 'sdselGrpCd', function ($scope, $http, sdselGrpCd) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('optionMenuDlvrSelectClassCtrl', $scope, $http, false));

    // 그리드 Refresh
    $scope.$on('optionMenuDlvrRefresh', function (event, data) {
        $scope.flex.refresh();
    });

    // sdselGrpCd Data Setter
    $scope.setSdselGrpCd = function (value) {
        sdselGrpCd.set(value);
    };
    // sdselGrpCd Data Getter
    $scope.getSdselGrpCd = function () {
        return sdselGrpCd.get();
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 내 콤보박스 설정
        $scope.requireYnDataMap = requireYnDataMap; // 필수선택여부

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === 'sdselClassCd') {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== 'I') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
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
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === 'sdselClassCd') {
                    if (selectedRow.sdselClassCd !== '' && selectedRow.sdselClassCd !== undefined && selectedRow.sdselClassCd !== '자동채번') {
                        $("#optionMenuDlvrSelectClassTitle").html(" [" + selectedRow.sdselClassCd + "]" + selectedRow.sdselClassNm);

                        // 선택상품 저장 버튼
                        $("#btnSaveSelProd3").show();

                        var params = {};
                        params.sdselClassCd = selectedRow.sdselClassCd;
                        params.sdselGrpCd = $scope.getSdselGrpCd();
                        $scope._broadcast('optionMenuDlvrSelectProdCtrl', params);
                    }
                }
            }
        });
    };

    // 선택분류 그리드 조회
    $scope.$on('optionMenuDlvrSelectClassCtrl', function (event, data) {

        // scope 영역에 변수 Set
        $scope.setSdselGrpCd(data.sdselGrpCd);

        // 파라미터
        var params = {};
        params.sdselGrpCd = data.sdselGrpCd;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain('/base/prod/sideMenu/menuClass/list.sb', params, function () {

        }, false);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });
}]);

/**
 * 옵션메뉴(배달) 선택상품 그리드 생성
 */
app.controller('optionMenuDlvrSelectProdCtrl', ['$scope', '$http', 'sdselClassCd', function ($scope, $http, sdselClassCd) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('optionMenuDlvrSelectProdCtrl', $scope, $http, false));

    // 그리드 Refresh
    $scope.$on('selectMenuRefresh', function (event, data) {
        $scope.flex.refresh();
    });

    // sdselGrpCd Data Setter
    $scope.setSdselGrpCd = function (value) {
        $scope.sdselGrpCd = value;
    };
    // sdselGrpCd Data Getter
    $scope.getSdselGrpCd = function () {
        return $scope.sdselGrpCd;
    };

    // sdselClassCd Data Setter
    $scope.setSdselClassCd = function (value) {
        sdselClassCd.set(value);
    };
    // sdselClassCd Data Getter
    $scope.getSdselClassCd = function () {
        return sdselClassCd.get();
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 내 콤보박스 설정
        $scope.fixProdFgDataMap = fixProdFgDataMap;                                   // 구분
        $scope.printYnDataMap = new wijmo.grid.DataMap(printYnData, 'value', 'name'); // 출력여부

        // 선택상품 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            if (col.binding === 'prodCd' || col.binding === 'prodNm') {
                e.cancel = true;
            }
        });
    };

    // 선택상품 그리드 조회
    $scope.$on('optionMenuDlvrSelectProdCtrl', function (event, data) {

        // scope 영역에 변수 Set
        $scope.setSdselClassCd(data.sdselClassCd);         // 선택분류코드
        if (data.sdselGrpCd !== null && data.sdselGrpCd !== '' && data.sdselGrpCd !== undefined) {
            $scope.setSdselGrpCd(data.sdselGrpCd);
        }

        // 파라미터
        var params = {};
        params.sdselClassCd = data.sdselClassCd;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain('/base/prod/sideMenu/menuProd/list.sb', params, function () {

        }, false);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 선택상품 그리드 저장
    $scope.saveProd = function () {
        var msg = messages["cmm.choo.save"];

        $scope._popConfirm(msg, function () {

            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = [];

            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                $scope.flex.collectionView.itemsEdited[u].status = 'U';
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }

            if (orgnFg == 'HQ') {
                /*
                // 적용매장 전체 삭제
                $scope._postJSONSave.withOutPopUp("/base/prod/sideMenu/menuProd/getSdselProdRegStoreDeleteAll.sb", params, function () {
                    // 저장
                    $scope.save(params);
                });*/
            } else {
                // 저장
                $scope.save(params);
            }
        });
    };

    // 저장
    $scope.save = function (params) {

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/sideMenu/menuProd/save.sb', params, function () {

            // 선택상품 리스트 재조회
            var params = {};
            params.sdselClassCd = $scope.getSdselClassCd();
            $scope._broadcast('optionMenuDlvrSelectProdCtrl', params);

            // 선택분류 리스트 재조회
            var grpGrid = agrid.getScope('optionMenuDlvrSelectGroupCtrl');
            var selectedSelGroup = grpGrid.getSelectedSelGroup();
            $scope._broadcast('optionMenuDlvrSelectClassCtrl', selectedSelGroup);

            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });
    };

}]);