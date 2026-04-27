/****************************************************************
 *
 * 파일명 : orgnInExRegist.js
 * 설  명 : 시스템관리 > 관리자기능2 > 메뉴권한임의등록 > 소속기준 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.04.21     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


/**
 *  소속 정보 그리드 생성
 */
app.controller('orgnInExRegistCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orgnInExRegistCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 본사코드, 매장코드
                if (col.binding === "hqOfficeCd" || col.binding === "storeCd") {
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

                // 본사코드, 매장코드
                if (col.binding === "hqOfficeCd" || col.binding === "storeCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var params = {};

                    if(col.binding === "hqOfficeCd"){
                        params.orgnCd = selectedRow.hqOfficeCd;
                        params.orgnNm = selectedRow.hqOfficeNm;
                        params.orgnFg = "H";
                    }else{
                        params.orgnCd = selectedRow.storeCd;
                        params.orgnNm = selectedRow.storeNm;
                        params.orgnFg = "S";
                    }

                    $scope._broadcast('regOrgnMenuCtrl', params);
                    $scope._broadcast('noRegOrgnMenuCtrl', params);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("orgnInExRegistCtrl", function(event, data) {
        // 소속정보 조회
        $scope.getSearchOrgnInfoDtlList();
        event.preventDefault();
    });

    // 소속정보 조회
    $scope.getSearchOrgnInfoDtlList = function(){
        var params = {};

        $scope._inquiryMain("/sys/admin2/authorInExRegist/orgnInExRegist/getSearchOrgnInfoDtlList.sb", params, function() {

            // 등록 메뉴 리스트 초기화
            $scope.$apply(function() {
                var storeScope = agrid.getScope('regOrgnMenuCtrl');
                storeScope._gridDataInit();
                $("#lblRegOrgn").text("");
                storeScope.setSelectedOrgn("");
            });

            // 미등록 메뉴 리스트 초기화
            $scope.$apply(function() {
                var regScope = agrid.getScope('noRegOrgnMenuCtrl');
                regScope._gridDataInit();
                regScope.setSelectedOrgn("");

                // 상품 grid paging 초기화(숨기기.. 아예 없애는거 모름..)
                var noRegOrgnMenuCtrlPager = document.getElementById('noRegOrgnMenuCtrlPager');
                noRegOrgnMenuCtrlPager.style.visibility='hidden'
            });

        }, false);
    };
}]);


/**
 *  등록 메뉴 그리드 생성
 */
app.controller('regOrgnMenuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('regOrgnMenuCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.authorProdcFgDataMap = new wijmo.grid.DataMap(authorProdcFgComboData, 'value', 'name'); // 권한처리구분
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYnComboData, 'value', 'name'); // 사용여부
        $scope.incldExcldDataMap = new wijmo.grid.DataMap(incldExcldFgComboData, 'value', 'name'); // 포함제외구분

    };

    // <-- 검색 호출 -->
    $scope.$on("regOrgnMenuCtrl", function(event, data) {
        $scope.setSelectedOrgn(data);

        if(!$.isEmptyObject($scope.selectedOrgn) ) {
            $("#lblRegOrgn").text("[" + $scope.selectedOrgn.orgnCd + "] " + $scope.selectedOrgn.orgnNm);
            // 등록메뉴 리스트 조회
            $scope.getSearchOrgnRegMenuList();

        }else{
            $("#lblRegOrgn").text("");
        }
        event.preventDefault();
    });

    // 등록메뉴 리스트 조회
    $scope.getSearchOrgnRegMenuList = function(){
        var params = {};
        params.orgnCd = $scope.selectedOrgn.orgnCd;

        $scope._inquirySub("/sys/admin2/authorInExRegist/orgnInExRegist/getSearchOrgnRegMenuList.sb", params, function() {

        }, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedOrgn;
    $scope.setSelectedOrgn = function(user) {
        $scope.selectedOrgn = user;
    };
    $scope.getSelectedOrgn = function(){
        return $scope.selectedOrgn;
    };

    // <-- 그리드 행 삭제 -->
    $scope.deleteOrgnMenu = function(){

        if(!$.isEmptyObject($scope.selectedOrgn) ) {
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
                $scope._save("/sys/admin2/authorInExRegist/orgnInExRegist/deleteOrgnMenu.sb", params, function(){
                    // 등록 재료 조회
                    $scope.getSearchOrgnRegMenuList();

                    // 미등록 메뉴 조회
                    var scope = agrid.getScope("noRegOrgnMenuCtrl");
                    scope._broadcast('noRegOrgnMenuCtrl', $scope.selectedOrgn);
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
app.controller('noRegOrgnMenuCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('noRegOrgnMenuCtrl', $scope, $http, true));

    // 권한그룹여부 콤보박스 셋팅
    $scope._setComboData('orgnAuthGrpFgCombo',authGrpFgComboData);
    $scope._setComboData('orgnAuthorProdcFgCombo',authorProdcFgComboData);
    $scope._setComboData('incldExcldFgCombo',incldExcldFgComboData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.orgnAuthGrpFg = 'N';
        $scope.incldExcldDataMap = new wijmo.grid.DataMap(incldExcldFgComboData, 'value', 'name'); // 포함제외구분

    };

    $scope.$on("noRegOrgnMenuCtrl", function(event, data) {
        if(data !== null && data !== undefined) {
            // 상품 데이터 셋팅
            $scope.setSelectedOrgn(data);

            // 권한처리구분 셋팅
            var orgnFg = $scope.selectedOrgn.orgnFg;

            var filteredData;

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
                $scope._setComboData('orgnAuthorProdcFgCombo', filteredData);
                if (orgnFg === 'S') {
                    $scope.authorProdcFg = 'S';
                } else {
                    $scope.authorProdcFg = 'H';
                }
            });

        }

        // 미등록 메뉴 리스트 조회
        $scope.getSearchOrgnNoRegMenuList();
        event.preventDefault();
    });

    // 선택 매장
    $scope.selectedOrgn;
    $scope.setSelectedOrgn = function(user) {
        $scope.selectedOrgn = user;
    };
    $scope.getSelectedOrgn = function(){
        return $scope.selectedOrgn;
    };


    $scope.btnSearch = function (){
        if(!$scope.selectedOrgn){
            $scope._popMsg(messages["authorInExRegist.msg.requireOrgnCd"]); // 본사,매장을 먼저 선택해주세요.
            return false;
        }
        $scope._pageView('noRegOrgnMenuCtrl', 1);
    }

    // 미등록 메뉴 리스트 조회
    $scope.getSearchOrgnNoRegMenuList = function(){
        var params = {};
        params.orgnCd = $scope.selectedOrgn.orgnCd;
        params.resrceCd = $scope.orgnResrceCd;
        params.resrceNm = $scope.orgnResrceNm;
        params.authGrpFg = $scope.orgnAuthGrpFg;

        $scope._inquiryMain("/sys/admin2/authorInExRegist/orgnInExRegist/getSearchOrgnNoRegMenuList.sb", params, function() {

            // 상품 grid paging 초기화(숨기기.. 아예 없애는거 모름..)
            var noRegOrgnMenuCtrlPager = document.getElementById('noRegOrgnMenuCtrlPager');
            noRegOrgnMenuCtrlPager.style.visibility='visible'
        }, false);
    };

    // 미등록 메뉴 추가
    $scope.addOrgnMenu = function () {

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].status = "I";
                $scope.flex.collectionView.items[i].orgnCd = $scope.selectedOrgn.orgnCd;
                $scope.flex.collectionView.items[i].authorProdcFg = $scope.orgnAuthorProdcFg;
                $scope.flex.collectionView.items[i].incldExcldFg = $scope.incldExcldFg;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/sys/admin2/authorInExRegist/orgnInExRegist/insertOrgnMenu.sb", params, function(){

            // 등록 재료 조회
            var scope = agrid.getScope("regOrgnMenuCtrl");
            scope._broadcast('regOrgnMenuCtrl', $scope.selectedOrgn);

            // 미등록 메뉴 조회
            $scope.getSearchOrgnNoRegMenuList();
        });
    };
}]);