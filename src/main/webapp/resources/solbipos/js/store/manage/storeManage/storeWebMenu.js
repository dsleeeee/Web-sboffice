/****************************************************************
 *
 * 파일명 : storeWebMenu.js
 * 설  명 : 매장정보관리 > 매장 웹사이트 메뉴권한 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.05.12     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
var defaultComboData = [
    {"name": "-", "value": ""}
];

// 현재 클릭한 매장의 STORE_CD
var vStoreCd = "";

// 모바일 메뉴 탭 클릭
function changeMobileTab(){
    var scope = agrid.getScope('webMenuCtrl');
    scope.changeMobileTab();
}

// 웹사이트 메뉴 탭 클릭
function changeWebTab(){
    var scope = agrid.getScope('webMenuCtrl');
    scope.changeWebTab();
}

app.controller('webMenuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('webMenuCtrl', $scope, $http, false));

    // 본사 콤보박스 셋팅
    $scope._setComboData("hqOfficeCdCombo", authHqList);

    $scope.initGrid = function (s, e) {

    };

    // 팝업 오픈시 매장정보 조회
    $scope.$on("webMenuCtrl", function (event, data) {

        $scope._gridDataInit();
        var scope = agrid.getScope('notUseWebMenuCtrl');
        scope._gridDataInit();
        $("#webMenu").addClass("on");
        $("#mobileMenu").removeClass("on");
        $("#webArea").show();
        $("#webArea2").show();
        $("#mobileArea").hide();
        $("#mobileArea2").hide();

        // 제목
        var storeScope    = agrid.getScope('storeManageCtrl');
        var storeCd       = storeScope.getSelectedStore().storeCd;
        var storeNm       = storeScope.getSelectedStore().storeNm;
        vStoreCd = storeScope.getSelectedStore().storeCd;

        $("#storeAuthTitle").text("[" + storeCd + "] " + storeNm);

        // 메뉴권한복사 > 본사와 매장 기본 셋팅
        $scope.hqOfficeCdCombo.selectedIndex = 0;
        $scope.setStoreCdCombo($scope.hqOfficeCdCombo.selectedValue, vStoreCd);

        // 메뉴 리스트 조회
        $scope.menuList();
        event.preventDefault();
    });

    // 메뉴 리스트 조회
    $scope.menuList = function(){

        // 사용메뉴 조회
        var params = [];
        params.storeCd = vStoreCd;

        $scope._inquirySub("/store/manage/storeManage/storeManage/avlblMenu.sb", params, function() {

            // 미사용메뉴 조회
            var beUseMenuGrid = agrid.getScope("notUseWebMenuCtrl");
            beUseMenuGrid._pageView('notUseWebMenuCtrl', 1);

        });
    };

    // 매장 콤보박스 셋팅 전 값 체크(본사 콤보박스 선택에 따른)
    $scope.setStoreCd = function (s, e) {

        // 본사 선택 값이 없으면 매장에 빈값 셋팅
        if (s.selectedValue === "") {
            $scope._setComboData("storeCdCombo", defaultComboData);
            $("#storeCdCombo").css('background-color', '#F0F0F0');
            $("#btnCopyAuth").css("display", "none");
        }
        else {
            // 팝업 오픈시, 선택한 매장정보가 제대로 Load 된 경우에만 매장콤보박스 셋팅
            if(vStoreCd !== ""){
                $scope.setStoreCdCombo(s.selectedValue, vStoreCd);
            }
        }
    };

    // 매장 콤보박스 셋팅
    $scope.setStoreCdCombo = function(valHqCd, valStoreCd){
    var params = [];
        params.hqOfficeCd = valHqCd;
        params.storeCd = valStoreCd;

        $scope._postJSONQuery.withOutPopUp('/store/manage/storeManage/storeManage/getAuthStoreList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var authStoreList = response.data.data.list;
                $scope._setComboData("storeCdCombo", authStoreList);
                $("#storeCdCombo").css('background-color', '#FFFFFF');
                $("#btnCopyAuth").css("display", "");
            } else {
                $scope._setComboData("storeCdCombo", defaultComboData);
                $("#storeCdCombo").css('background-color', '#F0F0F0');
                $("#btnCopyAuth").css("display", "none");
            }
        });
    }

    // 권한복사 버튼 클릭
    $scope.copyAuth = function(){

        var param = {};
        param.storeCd      = vStoreCd;
        param.copyStoreCd  = $scope.storeCd;

        console.log(param);

        $.postJSONSave("/store/manage/storeManage/storeManage/copyAuth.sb", param, function(result) {
                var res = result.data;
                if(res > 0) {
                    $scope._popMsg(messages["cmm.copySucc"]);
                    $scope._broadcast('webMenuCtrl');
                }
            },
            function (result) {
                s_alert.pop(result.message);
            }
        );
    }

    // 미사용등록
    $scope.notUseReg = function(){

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].storeCd = vStoreCd;
                $scope.flex.collectionView.items[i].resrceCd = $scope.flex.collectionView.items[i].resrceCdSmall;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            $scope._popMsg(messages["storeManage.require.chkNotUseMenu"]);
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/store/manage/storeManage/storeManage/removeAuth.sb', params, function() {
            // 저장 후 재조회
            $scope._broadcast('webMenuCtrl');
            $scope._broadcast('notUseWebMenuCtrl');
        });
    };

    // 모바일 메뉴 탭 클릭
    $scope.changeMobileTab = function(){
        $("#mobileMenu").addClass("on");
        $("#webMenu").removeClass("on");
        $("#webArea").hide();
        $("#webArea2").hide();
        $("#mobileArea").show();
        $("#mobileArea2").show();
        $scope._broadcast('mobileMenuCtrl');
    }

    // 웹사이트 메뉴 탭 클릭
    $scope.changeWebTab = function(){
        $("#webMenu").addClass("on");
        $("#mobileMenu").removeClass("on");
        $("#webArea").show();
        $("#webArea2").show();
        $("#mobileArea").hide();
        $("#mobileArea2").hide();
        $scope._broadcast('webMenuCtrl');
    }

}]);

app.controller('notUseWebMenuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('notUseWebMenuCtrl', $scope, $http, false));

    //
    $scope.initGrid = function (s, e) {

    };

    // 팝업 오픈시 매장정보 조회
    $scope.$on("notUseWebMenuCtrl", function (event, data) {

        // 미사용메뉴 리스트 조회
        $scope.notUsemenuList();

    });

    // 미사용메뉴 리스트 조회
    $scope.notUsemenuList = function () {

        // 사용메뉴 조회
        var params = [];
        params.storeCd = vStoreCd;
        $scope._inquirySub("/store/manage/storeManage/storeManage/beUseMenu.sb", params, function() {

        });
    };

    // 사용등록
    $scope.useReg = function(){

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].storeCd = vStoreCd;
                $scope.flex.collectionView.items[i].resrceCd = $scope.flex.collectionView.items[i].resrceCdSmall;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            $scope._popMsg(messages["storeManage.require.chkUseMenu"]);
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/store/manage/storeManage/storeManage/addAuth.sb', params, function() {
            // 저장 후 재조회
            $scope._broadcast('notUseWebMenuCtrl');
            $scope._broadcast('webMenuCtrl');
        });
    };

}]);

// 모바일 사용메뉴
app.controller('mobileMenuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileMenuCtrl', $scope, $http, false));

    // 본사 콤보박스 셋팅
    $scope._setComboData("hqOfficeCdMobileCombo", authHqList);

    $scope.initGrid = function (s, e) {

    };

    // 팝업 오픈시 매장정보 조회
    $scope.$on("mobileMenuCtrl", function (event, data) {

        $("#webArea").hide();
        $("#webArea2").hide();
        $scope._gridDataInit();
        var scope = agrid.getScope('notUseMobileMenuCtrl')
        scope._gridDataInit();

        // 제목
        var storeScope    = agrid.getScope('storeManageCtrl');
        var storeCd       = storeScope.getSelectedStore().storeCd;
        var storeNm       = storeScope.getSelectedStore().storeNm;
        vStoreCd = storeScope.getSelectedStore().storeCd;

        $("#storeAuthTitle").text("[" + storeCd + "] " + storeNm);

        // 메뉴권한복사 > 본사와 매장 기본 셋팅
        $scope.hqOfficeCdMobileCombo.selectedIndex = 0;
        $scope.setStoreCdMobileCombo($scope.hqOfficeCdMobileCombo.selectedValue, vStoreCd);

        // 메뉴 리스트 조회
        $scope.menuListMobile();
        event.preventDefault();
    });

    // 메뉴 리스트 조회
    $scope.menuListMobile = function(){

        // 사용메뉴 조회
        var params = [];
        params.storeCd = vStoreCd;

        $scope._inquirySub("/store/manage/storeManage/storeManage/avlblMobileMenu.sb", params, function() {

            // 미사용메뉴 조회
            var beUseMenuGrid = agrid.getScope("notUseMobileMenuCtrl");
            beUseMenuGrid._pageView('notUseMobileMenuCtrl', 1);

        });
    };

    // 매장 콤보박스 셋팅 전 값 체크(본사 콤보박스 선택에 따른)
    $scope.setStoreCdMobile = function (s, e) {

        // 본사 선택 값이 없으면 매장에 빈값 셋팅
        if (s.selectedValue === "") {
            $scope._setComboData("storeCdCombo", defaultComboData);
            $("#storeCdMobileCombo").css('background-color', '#F0F0F0');
            $("#btnCopyMobileAuth").css("display", "none");
        }
        else {
            // 팝업 오픈시, 선택한 매장정보가 제대로 Load 된 경우에만 매장콤보박스 셋팅
            if(vStoreCd !== ""){
                $scope.setStoreCdMobileCombo(s.selectedValue, vStoreCd);
            }
        }
    };

    // 매장 콤보박스 셋팅
    $scope.setStoreCdMobileCombo = function(valHqCd, valStoreCd){
        var params = [];
        params.hqOfficeCd = valHqCd;
        params.storeCd = valStoreCd;

        $scope._postJSONQuery.withOutPopUp('/store/manage/storeManage/storeManage/getAuthStoreList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var authStoreList = response.data.data.list;
                $scope._setComboData("storeCdMobileCombo", authStoreList);
                $("#storeCdMobileCombo").css('background-color', '#FFFFFF');
                $("#btnCopyMobileAuth").css("display", "");
            } else {
                $scope._setComboData("storeCdMobileCombo", defaultComboData);
                $("#storeCdMobileCombo").css('background-color', '#F0F0F0');
                $("#btnCopyMobileAuth").css("display", "none");
            }
        });
    }

    // 권한복사 버튼 클릭
    $scope.copyMobileAuth = function(){

        var param = {};
        param.storeCd      = vStoreCd;
        param.copyStoreCd  = $scope.storeCdMobile;

        console.log(param);

        $.postJSONSave("/store/manage/storeManage/storeManage/copyMobileAuth.sb", param, function(result) {
                var res = result.data;
                if(res > 0) {
                    $scope._popMsg(messages["cmm.copySucc"]);
                    $scope._broadcast('mobileMenuCtrl');
                }
            },
            function (result) {
                s_alert.pop(result.message);
            }
        );
    }

    // 미사용등록
    $scope.notUseRegMobile = function(){

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].storeCd = vStoreCd;
                $scope.flex.collectionView.items[i].resrceCd = $scope.flex.collectionView.items[i].resrceCdMid;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            $scope._popMsg(messages["storeManage.require.chkNotUseMenu"]);
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/store/manage/storeManage/storeManage/removeMobileAuth.sb', params, function() {
            // 저장 후 재조회
            $scope._broadcast('mobileMenuCtrl');
        });
    };


}]);

app.controller('notUseMobileMenuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('notUseMobileMenuCtrl', $scope, $http, false));

    //
    $scope.initGrid = function (s, e) {

    };

    // 팝업 오픈시 매장정보 조회
    $scope.$on("notUseMobileMenuCtrl", function (event, data) {

        // 미사용메뉴 리스트 조회
        $scope.notUseMenuMobileList();

    });

    // 미사용메뉴 리스트 조회
    $scope.notUseMenuMobileList = function () {

        // 사용메뉴 조회
        var params = [];
        params.storeCd = vStoreCd;
        $scope._inquirySub("/store/manage/storeManage/storeManage/beUseMobileMenu.sb", params, function() {

        });
    };

    // 사용등록
    $scope.useRegMobile = function(){

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].storeCd = vStoreCd;
                $scope.flex.collectionView.items[i].resrceCd = $scope.flex.collectionView.items[i].resrceCdMid;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            $scope._popMsg(messages["storeManage.require.chkUseMenu"]);
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/store/manage/storeManage/storeManage/addMobileAuth.sb', params, function() {
            // 저장 후 재조회
            $scope._broadcast('mobileMenuCtrl');
        });
    };

}]);