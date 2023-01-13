/****************************************************************
 *
 * 파일명 : storeType.js
 * 설  명 : 매장타입관리 - 매장타입 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.28     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var useYnAllFgData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

// 매장타입관리-매장타입등록
app.controller('storeTypeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeTypeCtrl', $scope, $http, true));

    // 사용여부 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYnAll", useYnAllFgData);

    // 버튼 visible 셋팅
    $("#btnAddStoreType").css("display", "none");
    $("#btnSaveStoreType").css("display", "none");
    $("#btnDelStoreMapping").css("display", "none");
    $("#btnSearchStore").css("display", "none");
    $("#btnRegStore").css("display", "none");
    $("#btnDelMenuGroupMapping").css("display", "none");
    $("#btnSearchMenuGroup").css("display", "none");
    $("#btnRegMenuGroup").css("display", "none");

    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'storeTypeCd') {
                    if(item.storeTypeCd !== '자동채번') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 카테고리 코드 클릭 시, 키맵과 상품 조회
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "storeTypeCd") {
                    if(selectedRow.storeTypeCd !== '자동채번') {

                        // 선택한 매장타입코드 갖고있기
                        $("#hdStoreTypeCd").val(selectedRow.storeTypeCd);

                        // 선택한 매장타입 명시
                        $("#lblStoreType1").text("[" + selectedRow.storeTypeCd + "] " + selectedRow.storeTypeNm);
                        $("#lblStoreType2").text("[" + selectedRow.storeTypeCd + "] " + selectedRow.storeTypeNm);
                        
                        // 매장연결, 메뉴그룹연결 grid 조회
                        $scope._broadcast('storeMappingCtrl');
                        $scope._broadcast('menuGroupMappingCtrl');

                        // 버튼 visible 셋팅
                        $("#btnDelStoreMapping").css("display", "");
                        $("#btnSearchStore").css("display", "");
                        $("#btnRegStore").css("display", "");
                        $("#btnDelMenuGroupMapping").css("display", "");
                        $("#btnSearchMenuGroup").css("display", "");
                        $("#btnRegMenuGroup").css("display", "");
                        
                        event.preventDefault();
                    }
                }
            }
        });

        // 매장타입조회
        $scope.searchStoreType();
    };

    $scope.$on("storeTypeCtrl", function(event, data) {

        // 매장타입조회
        $scope.searchStoreType();
        event.preventDefault();

    });

    // 매장타입조회
    $scope.searchStoreType = function(){

        var params = {};
        params.storeTypeNm = $scope.storeTypeNm;
        params.useYn = $scope.useYn;
        
        $scope._inquirySub("/base/store/storeType/storeType/getStoreType.sb", params, function() {

            // 선택한 매장타입 초기화
            $("#hdStoreTypeCd").val("");
            $("#lblStoreType1").text("");
            $("#lblStoreType2").text("");

            // 버튼 visible 셋팅 - 매장타입관리 grid 버튼은 보이고 나머지 grid 버튼은 숨길것.
            $("#btnAddStoreType").css("display", "");
            $("#btnSaveStoreType").css("display", "");
            $("#btnDelStoreMapping").css("display", "none");
            $("#btnSearchStore").css("display", "none");
            $("#btnRegStore").css("display", "none");
            $("#btnDelMenuGroupMapping").css("display", "none");
            $("#btnSearchMenuGroup").css("display", "none");
            $("#btnRegMenuGroup").css("display", "none");

            // 매장연결 그리드 초기화
            var storeMappingScope = agrid.getScope('storeMappingCtrl');
            storeMappingScope.storeMappingGridDefault();

            // 매장 그리드 및 input 값 초기화
            var storeSelectScope = agrid.getScope('storeSelectCtrl');
            storeSelectScope.storeSelectGridDefault();

            $("#searchStoreCd").val("");
            $("#searchStoreNm").val("");
            storeSelectScope.searchSysStatFgCombo.selectedIndex = 0;

            // 메뉴그룹연결 그리드 초기화
            var menuGroupMappingScope = agrid.getScope('menuGroupMappingCtrl');
            menuGroupMappingScope.menuGroupMappingGridDefault();
            
            // 메뉴그룹선택 그리드 및 input 값 초기화
            var menuGroupSelectScope = agrid.getScope('menuGroupSelectCtrl');
            menuGroupSelectScope.menuGroupSelectGridDefault();

            $("#searchStoreGroupCd").val("");
            $("#searchStoreGroupNm").val("");
            menuGroupSelectScope.searchUseYnCombo.selectedIndex = 0;

        }, false);
    };
    
    // 매장타입추가
    $scope.addStoreType = function () {
        var params = {};

        params.storeTypeCd = '자동채번';
        params.storeTypeNm = '';
        params.useYn = 'Y';
        params.remark = '';

        // 행추가
        $scope._addRow(params, 1);
    }
    
    // 매장타입저장
    $scope.saveStoreType = function () {

        $scope.flex.collectionView.commitEdit();

        // 생성, 수정 Validation Check
        for (var m = 0; m < $scope.flex.collectionView.itemCount; m++) {
            if( $scope.flex.collectionView.items[m].storeTypeNm === null  || $scope.flex.collectionView.items[m].storeTypeNm === '') {
                $scope._popMsg(messages['storeType.require.storeTypeNm.msg']); // 타입명을 입력해주세요.
                return;
            }
        }

        // 파라미터 설정
        var params = [];

        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            $scope.flex.collectionView.itemsEdited[u].status = 'U';
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = 'I';
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/store/storeType/storeType/saveStoreType.sb', params, function() {

            // 매장타입조회
            $scope.searchStoreType();

            // 매장타입 dropdown 재조회
            $scope.setStoreTypeDropdownList();

        });

    };

    // 매장타입 dropdown 재조회
    $scope.setStoreTypeDropdownList = function(){

        var url = '/base/store/storeType/storeType/getStoreTypeCombo.sb';
        var params = {};

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : url, /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list = response.data.data.list;
                    var comboArray = [];
                    var comboData  = {};

                    for (var i = 0; i < list.length; i++) {
                        comboData = {};
                        comboData.name  = list[i].name;
                        comboData.value = list[i].value;
                        comboArray.push(comboData);
                    }

                    //
                    $scope._setComboData("srchPopStoreType", comboArray);
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {
            $timeout(function () {
            }, 10);
        });
    };
    
    // 매장타입 매장적용 팝업
    $scope.applyStore = function () {

        $scope.storeTypeApplyStoreLayer.show(true);
        $scope._broadcast('storeTypeApplyStoreCtrl');
    }

}]);

// 매장타입관리-매장연결
app.controller('storeMappingCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeMappingCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    };

    $scope.$on("storeMappingCtrl", function(event, data) {

        // 매장연결조회
        $scope.searchStoreMapping();
        event.preventDefault();
    });

    // 매장연결조회
    $scope.searchStoreMapping = function(){

        var params = {};
        params.storeTypeCd = $("#hdStoreTypeCd").val();

        $scope._inquirySub("/base/store/storeType/storeType/getStoreMapping.sb", params, function() {

            $scope._broadcast('storeSelectCtrl');

        }, false);
    };

    // 매장연결삭제
    $scope.delStoreMapping = function(){

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].storeTypeCd = $("#hdStoreTypeCd").val();
                $scope.flex.collectionView.items[i].storeTypeAutoEnvstVal = storeTypeAutoEnvstVal; // 매장타입자동적용(1106)
                $scope.flex.collectionView.items[i].applyFg = storeTypeApplyEnvstVal; // 매장타입판매가설정(1107)
                $scope.flex.collectionView.items[i].commentRemark = "TB_HQ_STORE_TYPE_STORE 삭제 후 자동 적용";

                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/storeType/storeType/deleteStoreMapping.sb", params, function(){

            // 매장연결 재조회
            $scope.searchStoreMapping();

            // 매장선택 재조회
            var storeSelectGrid = agrid.getScope("storeSelectCtrl");
            storeSelectGrid._pageView('storeSelectCtrl', 1);

        });
    };

    // 매장연결 그리드 초기화
    $scope.storeMappingGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);

// 매장타입관리-매장선택
app.controller('storeSelectCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSelectCtrl', $scope, $http, true));

    $scope._setComboData("searchSysStatFg", sysStatFg);
    $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList); // 매장브랜드

    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    };

    $scope.$on("storeSelectCtrl", function(event, data) {

        // 매장조회
        $scope.searchStore();
        event.preventDefault();
    });

    // 매장조회
    $scope.searchStore = function(){

        var params = {};
        params.storeCd = $("#searchStoreCd").val();
        params.storeNm = $("#searchStoreNm").val();
        params.sysStatFg = $scope.searchSysStatFgCombo.selectedValue;

        if(brandUseFg === "1" && orgnFg === "HQ"){

            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;

            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        $scope._inquirySub("/base/store/storeType/storeType/getStoreList.sb", params, function() {}, false);
    };

    // 매장등록
    $scope.regStore = function () {

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].storeTypeCd = $("#hdStoreTypeCd").val();
                $scope.flex.collectionView.items[i].storeTypeAutoEnvstVal = storeTypeAutoEnvstVal; // 매장타입자동적용(1106)
                $scope.flex.collectionView.items[i].applyFg = storeTypeApplyEnvstVal; // 매장타입판매가설정(1107)
                $scope.flex.collectionView.items[i].commentRemark = "TB_HQ_STORE_TYPE_STORE 등록 후 자동 적용";

                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/storeType/storeType/saveStoreMapping.sb", params, function(){

            // 매장 재조회
            $scope.searchStore();

            // 매장연결 재조회
            var storeMappingGrid = agrid.getScope("storeMappingCtrl");
            storeMappingGrid._pageView('storeMappingCtrl', 1);

        });
    };

    // 매장 그리드 초기화
    $scope.storeSelectGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);

// 매장타입관리-메뉴그룹연결
app.controller('menuGroupMappingCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('menuGroupMappingCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {

        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    };

    $scope.$on("menuGroupMappingCtrl", function(event, data) {

        // 메뉴그룹연결조회
        $scope.searchMenuGroupMapping();
    });

    // 메뉴그룹연결조회
    $scope.searchMenuGroupMapping = function(){

        var params = {};
        params.storeTypeCd = $("#hdStoreTypeCd").val();

        $scope._inquirySub("/base/store/storeType/storeType/getMenuGroupMapping.sb", params, function() {

            $scope._broadcast('menuGroupSelectCtrl');

        }, false);
    };

    // 메뉴그룹연결삭제
    $scope.delMenuGroupMapping = function(){

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].storeTypeCd = $("#hdStoreTypeCd").val();
                $scope.flex.collectionView.items[i].storeTypeAutoEnvstVal = storeTypeAutoEnvstVal; // 매장타입자동적용(1106)
                $scope.flex.collectionView.items[i].applyFg = storeTypeApplyEnvstVal; // 매장타입판매가설정(1107)
                $scope.flex.collectionView.items[i].commentRemark = "TB_HQ_STORE_TYPE_PROD_GROUP 삭제 후 자동 적용";

                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/storeType/storeType/deleteMenuGroupMapping.sb", params, function(){

            // 메뉴그룹연결 재조회
            $scope.searchMenuGroupMapping();

            // 메뉴그룹선택 재조회
            var menuGroupSelectGrid = agrid.getScope("menuGroupSelectCtrl");
            menuGroupSelectGrid._pageView('menuGroupSelectCtrl', 1);

        });
    }

    // 메뉴그룹연결 그리드 초기화
    $scope.menuGroupMappingGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);

// 매장타입관리-메뉴그룹선택
app.controller('menuGroupSelectCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('menuGroupSelectCtrl', $scope, $http, true));

    // 사용여부 조회조건 콤보박스 데이터 Set
    $scope._setComboData("searchUseYn", useYnAllFgData);

    $scope.initGrid = function (s, e) {

        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    };

    $scope.$on("menuGroupSelectCtrl", function(event, data) {

        // 메뉴그룹조회
        $scope.searchMenuGroup();
        event.preventDefault();
    });

    // 메뉴그룹조회
    $scope.searchMenuGroup = function(){

        var params = {};
        params.storeTypeCd = $("#hdStoreTypeCd").val();
        params.storeGroupCd = $("#searchStoreGroupCd").val();
        params.storeGroupNm = $("#searchStoreGroupNm").val();
        params.useYn = $scope.searchUseYnCombo.selectedValue;

        $scope._inquirySub("/base/store/storeType/storeType/getMenuGroupList.sb", params, function() {}, false);
    };

    // 메뉴그룹등록
    $scope.regMenuGroup = function () {

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].storeTypeCd = $("#hdStoreTypeCd").val();
                $scope.flex.collectionView.items[i].storeTypeAutoEnvstVal = storeTypeAutoEnvstVal; // 매장타입자동적용(1106)
                $scope.flex.collectionView.items[i].applyFg = storeTypeApplyEnvstVal; // 매장타입판매가설정(1107)
                $scope.flex.collectionView.items[i].commentRemark = "TB_HQ_STORE_TYPE_PROD_GROUP 등록 후 자동 적용";

                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/storeType/storeType/saveMenuGroupMapping.sb", params, function(){

            // 메뉴그룹 재조회
            $scope.searchMenuGroup();

            // 메뉴그룹연결 재조회
            var menuGroupMappingGrid = agrid.getScope("menuGroupMappingCtrl");
            menuGroupMappingGrid._pageView('menuGroupMappingCtrl', 1);

        });
    };

    // 메뉴그룹선택 그리드 초기화
    $scope.menuGroupSelectGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);