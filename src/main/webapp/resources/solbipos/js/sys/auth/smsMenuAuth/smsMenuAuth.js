/****************************************************************
 *
 * 파일명 : smsMenuAuth.js
 * 설  명 : 시스템관리 > 권한관리 > SMS화면관리(관리자) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.10     김유승      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// SMS 화면 권한 관리 API 기본 경로
var smsMenuAuthUrl = "/sys/auth/smsMenuAuth/smsMenuAuth/";

// 사용여부
var useYnComboData = [
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

// 재직여부
var serviceFgComboData = [
    {"name": "재직", "value": "1"},
    {"name": "퇴사", "value": "2"},
    {"name": "휴직", "value": "3"},
    {"name": "기타", "value": "4"}
];

/**
 * SMS 관리자 화면 그리드
 */
app.controller("smsMenuAuthCtrl", ["$scope", "$http", function ($scope, $http) {

    angular.extend(this, new RootController("smsMenuAuthCtrl", $scope, $http, true));

    // 중앙/우측 사용자 조회의 기준이 되는 SMS 메뉴
    $scope.selectedSmsMenu = null;

    $scope.initGrid = function (s, e) {
        // 메뉴코드는 하위 사용자 목록을 여는 링크로 표시한다.
        s.formatItem.addHandler(function (grid, event) {
            if (event.panel === grid.cells && grid.columns[event.col].binding === "resrceCd") {
                wijmo.addClass(event.cell, "wijLink");
            }
        });

        // 메뉴코드를 클릭한 경우에만 등록/미등록 사용자 목록을 조회한다.
        s.addEventListener(s.hostElement, "mousedown", function (event) {
            var hitTest = s.hitTest(event);

            if (hitTest.cellType !== wijmo.grid.CellType.Cell) {
                return;
            }

            if (hitTest.panel.columns[hitTest.col].binding !== "resrceCd") {
                return;
            }

            $scope.selectSmsMenu(s.rows[hitTest.row].dataItem);
            event.preventDefault();
        });
    };

    // 최상단 조회 버튼 호출
    $scope.$on("smsMenuAuthCtrl", function (event, data) {
        $scope.getSmsMenuList();
        event.preventDefault();
    });

    // 관리자용 SMS 화면 목록 조회
    $scope.getSmsMenuList = function () {
        var params = {};
        params.resrceCd = $scope.resrceCd;
        params.resrceNm = $scope.resrceNm;

        $scope._inquiryMain(smsMenuAuthUrl + "getSmsMenuList.sb", params, function () {
            $scope.selectedSmsMenu = null;
            $scope.resetUserGrids();
        });
    };

    // 선택한 SMS 화면을 기준으로 중앙/우측 사용자 목록 조회
    $scope.selectSmsMenu = function (smsMenu) {
        // 선택 메뉴 정보는 등록/미등록 사용자 조회와 권한 추가·삭제의 기준으로 사용한다.
        $scope.selectedSmsMenu = angular.copy(smsMenu);

        var regUserScope = agrid.getScope("smsMenuAuthRegUserCtrl");
        var noRegUserScope = agrid.getScope("smsMenuAuthNoRegUserCtrl");

        regUserScope._broadcast("smsMenuAuthRegUserCtrl", $scope.selectedSmsMenu);
        noRegUserScope._broadcast("smsMenuAuthNoRegUserCtrl", $scope.selectedSmsMenu);
    };

    // SMS 화면 재조회 시 이전 메뉴의 사용자 목록과 선택 상태 제거
    $scope.resetUserGrids = function () {
        var regUserScope = agrid.getScope("smsMenuAuthRegUserCtrl");
        var noRegUserScope = agrid.getScope("smsMenuAuthNoRegUserCtrl");

        regUserScope.resetSmsMenu();
        noRegUserScope.resetSmsMenu();
    };
}]);

/**
 * 등록된 사용자 정보 그리드
 */
app.controller("smsMenuAuthRegUserCtrl", ["$scope", "$http", function ($scope, $http) {

    angular.extend(this, new RootController("smsMenuAuthRegUserCtrl", $scope, $http, true));

    // 삭제 및 재조회 시 사용할 현재 선택 SMS 메뉴
    $scope.selectedSmsMenu = null;

    $scope.initGrid = function (s, e) {
        // 등록 사용자 그리드의 사용여부 및 재직여부 코드 표시용 DataMap
        $scope.useYnComboDataMap = new wijmo.grid.DataMap(useYnComboData, "value", "name");
        $scope.serviceFgComboDataMap = new wijmo.grid.DataMap(serviceFgComboData, "value", "name");

        // 사용자 그리드는 체크 선택 외 편집을 허용하지 않는다.
    };

    $scope.$on("smsMenuAuthRegUserCtrl", function (event, data) {
        if (data && data.resrceCd) {
            $scope.selectedSmsMenu = data;
            $("#lblSmsMenuAuthRegUser").text(messages["smsMenuAuth.regUserInfo"] + " [" + data.resrceCd + "] " + data.resrceNm);
            $scope.getRegUserList();
        }

        event.preventDefault();
    });

    // 선택 메뉴에 등록된 사용자 조회
    $scope.getRegUserList = function () {
        var params = {};
        params.resrceCd = $scope.selectedSmsMenu.resrceCd;

        $scope._inquirySub(smsMenuAuthUrl + "getRegUserList.sb", params, function () {
        }, false);
    };

    // 조회 기준 메뉴와 사용자 목록 초기화
    $scope.resetSmsMenu = function () {
        $scope.selectedSmsMenu = null;
        $scope._gridDataInit();
        $("#lblSmsMenuAuthRegUser").text(messages["smsMenuAuth.regUserInfo"]);
    };

    // 선택 사용자의 SMS 화면 권한 삭제
    $scope.deleteSmsMenuAuth = function () {
        if (!$scope.selectedSmsMenu) {
            $scope._popMsg(messages["smsMenuAuth.msg.selectSmsMenu"]);
            return false;
        }

        // 체크된 사용자 삭제 파라미터 설정
        var params = [];
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];

            if (item.gChk) {
                params.push({
                    status: "D",
                    resrceCd: $scope.selectedSmsMenu.resrceCd,
                    userId: item.userId
                });
            }
        }

        if (params.length === 0) {
            $scope._popMsg(messages["cmm.not.select"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.delete"], function () {
            $scope._save(smsMenuAuthUrl + "deleteSmsMenuAuth.sb", params, function () {
                $scope.getRegUserList();

                var noRegUserScope = agrid.getScope("smsMenuAuthNoRegUserCtrl");
                noRegUserScope._broadcast("smsMenuAuthNoRegUserCtrl", $scope.selectedSmsMenu);
            });
        });
    };
}]);

/**
 * 미등록된 관리자 사용자 정보 그리드
 */
app.controller("smsMenuAuthNoRegUserCtrl", ["$scope", "$http", function ($scope, $http) {

    angular.extend(this, new RootController("smsMenuAuthNoRegUserCtrl", $scope, $http, true));

    // 추가 및 재조회 시 사용할 현재 선택 SMS 메뉴
    $scope.selectedSmsMenu = null;

    $scope.initGrid = function (s, e) {
        // 미등록 사용자 그리드의 사용여부 및 재직여부 코드 표시용 DataMap
        $scope.useYnComboDataMap = new wijmo.grid.DataMap(useYnComboData, "value", "name");
        $scope.serviceFgComboDataMap = new wijmo.grid.DataMap(serviceFgComboData, "value", "name");

        // 관리자 사용자 제한은 화면 파라미터가 아닌 서버 조회 조건으로 강제한다.
    };

    $scope.$on("smsMenuAuthNoRegUserCtrl", function (event, data) {
        if (data && data.resrceCd) {
            $scope.selectedSmsMenu = data;
            $("#lblSmsMenuAuthNoRegUser").text(messages["smsMenuAuth.noRegUserInfo"] + " [" + data.resrceCd + "] " + data.resrceNm);
            $scope.getNoRegUserList();
        }

        event.preventDefault();
    });

    // 선택 메뉴에 미등록된 관리자 사용자만 조회
    $scope.getNoRegUserList = function () {
        var params = {};
        params.resrceCd = $scope.selectedSmsMenu.resrceCd;

        $scope._inquirySub(smsMenuAuthUrl + "getNoRegUserList.sb", params, function () {
        }, false);
    };

    // 조회 기준 메뉴와 사용자 목록 초기화
    $scope.resetSmsMenu = function () {
        $scope.selectedSmsMenu = null;
        $scope._gridDataInit();
        $("#lblSmsMenuAuthNoRegUser").text(messages["smsMenuAuth.noRegUserInfo"]);
    };

    // 선택 사용자에게 SMS 화면 권한 추가
    $scope.addSmsMenuAuth = function () {
        if (!$scope.selectedSmsMenu) {
            $scope._popMsg(messages["smsMenuAuth.msg.selectSmsMenu"]);
            return false;
        }

        // 체크된 사용자 추가 파라미터 설정
        var params = [];
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];

            if (item.gChk) {
                params.push({
                    status: "I",
                    resrceCd: $scope.selectedSmsMenu.resrceCd,
                    userId: item.userId
                });
            }
        }

        if (params.length === 0) {
            $scope._popMsg(messages["cmm.not.select"]);
            return false;
        }

        $scope._save(smsMenuAuthUrl + "insertSmsMenuAuth.sb", params, function () {
            $scope.getNoRegUserList();

            var regUserScope = agrid.getScope("smsMenuAuthRegUserCtrl");
            regUserScope._broadcast("smsMenuAuthRegUserCtrl", $scope.selectedSmsMenu);
        });
    };
}]);
