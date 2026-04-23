/****************************************************************
 *
 * 파일명 : userInExRegist.js
 * 설  명 : 시스템관리 > 관리자기능2 > 메뉴권한임의등록 > 사용자기준 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.04.17     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


/**
 *  사용자 정보 그리드 생성
 */
app.controller('userInExRegistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('userInExRegistCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 사용자ID
                if (col.binding === "userId") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 사용자ID
                if ( col.binding === "userId") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params = selectedRow;

                    // 메뉴 그리드 호출
                    $scope._broadcast('regUserMenuCtrl', params);
                    $scope._broadcast('noRegUserMenuCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("userInExRegistCtrl", function(event, data) {
        
        // 사용자 정보 조회
        $scope.getSearchUserInfoDtlList();
        event.preventDefault();
    });

    // 사용자 정보 조회
    $scope.getSearchUserInfoDtlList = function(){
        var params = {};

        $scope._inquiryMain("/sys/admin2/authorInExRegist/userInExRegist/getSearchUserInfoDtlList.sb", params, function() {

            // 등록 메뉴 리스트 초기화
            $scope.$apply(function() {
                var storeScope = agrid.getScope('regUserMenuCtrl');
                storeScope._gridDataInit();
                $("#lblRegUser").text("");
            });

            // 미등록 메뉴 리스트 초기화
                $scope.$apply(function() {
                    var regSope = agrid.getScope('noRegUserMenuCtrl');
                    regSope._gridDataInit();
                });

        }, false);
    };
}]);


/**
 *  등록 메뉴 그리드 생성
 */
app.controller('regUserMenuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('regUserMenuCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.authorProdcFgDataMap = new wijmo.grid.DataMap(authorProdcFgComboData, 'value', 'name'); // 권한처리구분
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYnComboData, 'value', 'name'); // 사용여부
        $scope.incldExcldDataMap = new wijmo.grid.DataMap(incldExcldFgComboData, 'value', 'name'); // 포함제외구분

    };

    // <-- 검색 호출 -->
    $scope.$on("regUserMenuCtrl", function(event, data) {
        $scope.setSelectedUser(data);

        if(!$.isEmptyObject($scope.selectedUser) ) {
            $("#lblRegUser").text("[" + $scope.selectedUser.userId + "] " + $scope.selectedUser.userNm);
            // 등록메뉴 리스트 조회
            $scope.getSearchUserRegMenuList();

        }else{
            $("#lblRegUser").text("");
        }
        event.preventDefault();
    });

    // 등록메뉴 리스트 조회
    $scope.getSearchUserRegMenuList = function(){
        var params = {};
        params.userId = $scope.selectedUser.userId;

        $scope._inquirySub("/sys/admin2/authorInExRegist/userInExRegist/getSearchUserRegMenuList.sb", params, function() {

        }, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedUser;
    $scope.setSelectedUser = function(user) {
        $scope.selectedUser = user;
    };
    $scope.getSelectedUser = function(){
        return $scope.selectedUser;
    };

    // <-- 그리드 행 삭제 -->
    $scope.deleteUserMenu = function(){

        if(!$.isEmptyObject($scope.selectedUser) ) {
            $scope._popConfirm(messages["cmm.choo.delete"], function() {

                // 파라미터 설정
                var params = new Array();
                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    var item = $scope.flex.collectionView.items[i];

                    if(item.gChk) {
                        item.status = "D";
                        params.push(item);
                    }
                }

                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._save("/sys/admin2/authorInExRegist/userInExRegist/deleteUserMenu.sb", params, function(){
                    // 등록 메뉴 조회
                    $scope.getSearchUserRegMenuList();

                    // 미등록 메뉴 조회
                    var scope = agrid.getScope("noRegUserMenuCtrl");
                    scope._broadcast('noRegUserMenuCtrl', $scope.selectedUser);
                });
            });

        }else{
            $scope._popMsg(messages["cmm.not.select"]); // 선택된 데이터가 없습니다.
            return false;
        }
    };
    // <-- 그리드 행 삭제 -->
}]);


/**
 * 미등록 메뉴 그리드 생성
 */
app.controller('noRegUserMenuCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('noRegUserMenuCtrl', $scope, $http, true));

    // 콤보박스 셋팅
    $scope._setComboData('userAuthGrpFgCombo',authGrpFgComboData); // 권한그룹여부
    $scope._setComboData('authorProdcFgCombo',authorProdcFgComboData); // 권한처리구분
    $scope._setComboData('incldExcldFgCombo',incldExcldFgComboData); // 포함제외구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 권한그룹여부 셋팅
        $scope.userAuthGrpFg = 'N';
    };

    $scope.$on("noRegUserMenuCtrl", function(event, data) {
        if(data !== null && data !== undefined) {
            // 상품 데이터 셋팅
            $scope.setSelectedUser(data);
        }

        // 미등록 메뉴 리스트 조회
        $scope.getSearchUserNoRegMenuList();
        event.preventDefault();
    });

    // 선택 매장
    $scope.selectedUser;
    $scope.setSelectedUser = function(user) {
        $scope.selectedUser = user;
    };
    $scope.getSelectedUser = function(){
        return $scope.selectedUser;
    };

    $scope.btnSearch = function (){
        if(!$scope.selectedUser){
            $scope._popMsg(messages["authorInExRegist.msg.requireUserId"]); // 사용자ID를 먼저 선택해주세요.
            return false;
        }
        $scope._pageView('noRegUserMenuCtrl', 1);
    }

    // 미등록 메뉴 리스트 조회
    $scope.getSearchUserNoRegMenuList = function(){
        var params = {};
        params.userId = $scope.selectedUser.userId;
        params.resrceCd = $scope.userResrceCd;
        params.resrceNm = $scope.userResrceNm;
        params.authGrpFg = $scope.userAuthGrpFg;

        $scope._inquirySub("/sys/admin2/authorInExRegist/userInExRegist/getSearchUserNoRegMenuList.sb", params, function() {
            var orgnFg = $scope.selectedUser.orgnFg;

            var filteredData;

            // 권한처리구분 셋팅
            if (orgnFg === 'H') {
                filteredData = authorProdcFgComboData.filter(item =>
                    item.value === 'H' || item.value === 'C'
                );
            } else if (orgnFg === 'S') {
                filteredData = authorProdcFgComboData.filter(item =>
                    item.value === 'S'
                );
            } else {
                filteredData = authorProdcFgComboData;
            }

            $timeout(function () {
                $scope._setComboData('authorProdcFgCombo', filteredData);
                if (orgnFg === 'S') {
                    $scope.authorProdcFg = 'S';
                } else {
                    $scope.authorProdcFg = 'H';
                }
            });

        }, false);
    };

    // 미등록 메뉴 추가
    $scope.addUserMenu = function () {

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].status = "I";
                $scope.flex.collectionView.items[i].userId = $scope.selectedUser.userId;
                $scope.flex.collectionView.items[i].authorProdcFg = $scope.authorProdcFg;
                $scope.flex.collectionView.items[i].incldExcldFg = $scope.incldExcldFg;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/admin2/authorInExRegist/userInExRegist/insertUserMenu.sb", params, function(){

            // 등록 메뉴 조회
            var scope = agrid.getScope("regUserMenuCtrl");
            scope._broadcast('regUserMenuCtrl', $scope.selectedUser);

            // 미등록 메뉴 조회
            $scope.getSearchUserNoRegMenuList();
        });
    };
}]);