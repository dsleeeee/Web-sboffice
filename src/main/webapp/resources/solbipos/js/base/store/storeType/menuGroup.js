/****************************************************************
 *
 * 파일명 : menuGroup.js
 * 설  명 : 매장타입관리 - 메뉴그룹 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.07.01     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var regYn = [
    /*{"name": "전체", "value": ""},
    {"name": "등록", "value": "Y"},*/
    {"name": "미등록", "value": "N"}
];

var useYnAllFgData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

// 메뉴그룹관리-메뉴그룹등록
app.controller('menuGroupCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('menuGroupCtrl', $scope, $http, true));

    // 사용여부 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYnAll", useYnAllFgData);

    $scope.saleUprcApply = true;

    $("#btnAddMenuGroup").css("display", "none");
    $("#btnSaveMenuGroup").css("display", "none");
    $("#btnDelProdMapping").css("display", "none");
    $("#btnSaveProdMapping").css("display", "none");
    $("#btnSrchProd").css("display", "none");
    $("#btnRegProd").css("display", "none");

    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'storeGroupCd') {
                    if(item.storeGroupCd !== '자동채번') {
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
                if (col.binding === "storeGroupCd") {
                    if(selectedRow.storeGroupCd !== '자동채번') {

                        // 선택한 메뉴그룹코드 갖고있기
                        $("#hdStoreGroupCd").val(selectedRow.storeGroupCd);

                        // 선택한 메뉴그룹 명시
                        $("#lblMenuGroup").text("[" + selectedRow.storeGroupCd + "] " + selectedRow.storeGroupNm);

                        // 상품연결 grid 조회
                        $scope._broadcast('prodMappingCtrl');

                        // 버튼 visible 셋팅
                        $("#btnDelProdMapping").css("display", "");

                        if(storeTypeApplyEnvstVal === "1") { // 매장타입판매가설정(1107) 미사용시, 판매가 저장버튼 hidden
                            $("#btnSaveProdMapping").css("display", "");
                        }else{
                            $("#btnSaveProdMapping").css("display", "none");
                        }

                        $("#btnSrchProd").css("display", "");
                        $("#btnRegProd").css("display", "");

                        event.preventDefault();
                    }
                }
            }
        });

        // 메뉴그룹조회
        $scope.searchMenuGroup();

    };

    $scope.$on("menuGroupCtrl", function(event, data) {

        // 메뉴그룹조회
        $scope.searchMenuGroup();
        event.preventDefault();
    });

    // 메뉴그룹조회
    $scope.searchMenuGroup = function () {

        var params = [];
        params.storeGroupNm = $scope.storeGroupNm;
        params.useYn = $scope.useYn;

        $scope._inquirySub("/base/store/storeType/storeType/getMenuGroup.sb", params, function() {

            // 선택한 매장타입 초기화
            $("#hdStoreTypeCd").val("");
            $("#lblMenuGroup").text("");

            // 버튼 visible 셋팅 - 매장타입관리 grid 버튼은 보이고 나머지 grid 버튼은 숨길것.
            $("#btnAddMenuGroup").css("display", "");
            $("#btnSaveMenuGroup").css("display", "");
            $("#btnDelProdMapping").css("display", "none");
            $("#btnSaveProdMapping").css("display", "none");
            $("#btnSrchProd").css("display", "none");
            $("#btnRegProd").css("display", "none");

            // 상품연결 그리드 초기화
            var prodMappingScope = agrid.getScope('prodMappingCtrl');
            prodMappingScope.prodMappingGridDefault();

            // 상품 그리드 및 input 값 초기화
            var prodSelectScope = agrid.getScope('prodSelectCtrl');
            prodSelectScope.prodSelectGridDefault();

            prodSelectScope.srchStartDate.value = getCurDate('-');
            prodSelectScope.srchEndDate.value = getCurDate('-');
            prodSelectScope.srchStartDate.isReadOnly = true;
            prodSelectScope.srchEndDate.isReadOnly = true;
            prodSelectScope.isChecked = true;
            $("#srchProdCd").val("");
            $("#srchProdNm").val("");
            $("#srchProdClassCd").val("");
            $("#_prodClassCd").val("");
            $("#srchBarCd").val("");
            prodSelectScope.srchBrandCombo.selectedIndex = 0;
            prodSelectScope.srchUseYnCombo.selectedIndex = 0;
            prodSelectScope.srchProdTypeFgCombo.selectedIndex = 0;
            prodSelectScope.srchRegYnCombo.selectedIndex = 0;

        }, false);
    }

    // 메뉴그룹추가
    $scope.addMenuGroup = function () {

        var params = [];
        params.storeGroupCd = '자동채번';
        params.storeGroupNm = '';
        params.useYn = 'Y';
        params.remark = '';

        // 행추가
        $scope._addRow(params, 1);
    }
    
    // 메뉴그룹저장
    $scope.saveMenuGroup = function () {

        $scope.flex.collectionView.commitEdit();

        // 생성, 수정 Validation Check
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if ($scope.flex.collectionView.items[i].storeGroupNm === null || $scope.flex.collectionView.items[i].storeGroupNm === '') {
                $scope._popMsg(messages['storeType.require.storeGroupNm.msg']); // 메뉴그룹명을 입력해주세요.
                return;
            }
        }

        // 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = 'U';
            params.push($scope.flex.collectionView.itemsEdited[i]);
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            $scope.flex.collectionView.itemsAdded[i].status = 'I';
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/store/storeType/storeType/saveMenuGroup.sb', params, function () {

            // 메뉴그룹조회
            $scope.searchMenuGroup();

            // 메뉴그룹 dropdown 재조회
            $scope.setStoreGroupDropdownList();

        });

    };
    
    // 메뉴그룹 dropdown 재조회
    $scope.setStoreGroupDropdownList = function(){

        var url = '/base/store/storeType/storeType/getStoreGroupCombo.sb';
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
                    $scope._setComboData("srchPopStoreGroup", comboArray);
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

}]);

// 메뉴그룹관리-상품연결
app.controller('prodMappingCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodMappingCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 판매가 변경시 다른 컬럼값도 변경
                if (col.binding === "saleUprc") {
                    var scope = agrid.getScope('menuGroupCtrl');
                    if(scope.saleUprcApply){
                        item.stinSaleUprc = item.saleUprc;
                        item.dlvrSaleUprc = item.saleUprc;
                        item.packSaleUprc = item.saleUprc;
                    }
                }
            }
            s.collectionView.commitEdit();
        });
    };

    $scope.$on("prodMappingCtrl", function(event, data) {
        // 상품연결조회
        $scope.searchProdMapping();
        event.preventDefault();
    });

    // 상품연결조회
    $scope.searchProdMapping = function () {

        var params = [];
        params.storeGroupCd = $("#hdStoreGroupCd").val();

        $scope._inquirySub("/base/store/storeType/storeType/getProdMapping.sb", params, function() {

            // 상품선택 조회
            var prodSelectGrid = agrid.getScope("prodSelectCtrl");
            prodSelectGrid._pageView('prodSelectCtrl', 1);

        }, false);
    }

    // 상품연결삭제
    $scope.delProdMapping = function(){

        // 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].storeGroupCd = $("#hdStoreGroupCd").val();
                $scope.flex.collectionView.items[i].storeTypeAutoEnvstVal = storeTypeAutoEnvstVal; // 매장타입자동적용(1106)
                $scope.flex.collectionView.items[i].applyFg = storeTypeApplyEnvstVal; // 매장타입판매가설정(1107)
                $scope.flex.collectionView.items[i].commentRemark = "TB_HQ_STORE_PROD_GROUP_DTL 삭제 후 자동 적용";
                $scope.flex.collectionView.items[i].status = 'D';

                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/storeType/storeType/saveProdMapping.sb", params, function(){

            // 상품연결 재조회
            $scope.searchProdMapping();

            // 상품선택 재조회
            var prodSelectGrid = agrid.getScope("prodSelectCtrl");
            prodSelectGrid._pageView('prodSelectCtrl', 1);

        });
    };

    // 상품연결수정
    $scope.saveProdMapping = function(){

        $scope.flex.collectionView.commitEdit();

        // 생성, 수정 Validation Check
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if ($scope.flex.collectionView.items[i].saleUprc === null || $scope.flex.collectionView.items[i].saleUprc === '') {
                $scope._popMsg(messages['storeType.require.saleUprc.msg']); // 판매가를 입력해주세요.
                return;
            }
        }

        // 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].storeGroupCd = $("#hdStoreGroupCd").val();
            $scope.flex.collectionView.itemsEdited[i].storeTypeAutoEnvstVal = storeTypeAutoEnvstVal; // 매장타입자동적용(1106)
            $scope.flex.collectionView.itemsEdited[i].applyFg = storeTypeApplyEnvstVal; // 매장타입판매가설정(1107)
            $scope.flex.collectionView.itemsEdited[i].commentRemark = "TB_HQ_STORE_PROD_GROUP_DTL 수정 후 자동 적용";
            $scope.flex.collectionView.itemsEdited[i].status = 'U';

            params.push($scope.flex.collectionView.itemsEdited[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/store/storeType/storeType/saveProdMapping.sb', params, function () {

            // 상품연결 재조회
            $scope.searchProdMapping();

            // 상품선택 재조회
            var prodSelectGrid = agrid.getScope("prodSelectCtrl");
            prodSelectGrid._pageView('prodSelectCtrl', 1);

        });

    };

    // 상품연결 그리드 초기화
    $scope.prodMappingGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);

// 메뉴그룹관리-상품선택
app.controller('prodSelectCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodSelectCtrl', $scope, $http, true));

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchBrand", brandList);
    $scope._setComboData("srchUseYn", useYnAllFgData);
    $scope._setComboData('srchProdTypeFg', prodTypeFg);
    $scope._setComboData('srchRegYn', regYn);

    // 등록일자 기본 '전체기간'으로
    $scope.isChecked = true;

    $scope.initGrid = function (s, e) {

        // 등록일자 기본 '전체기간'으로
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;

        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
        $scope.regYnDataMap = new wijmo.grid.DataMap(regYn, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                // 체크박스
                if (col.binding === "gChk" || col.binding === "saleUprc" || col.binding === "stinSaleUprc" || col.binding === "dlvrSaleUprc" || col.binding === "packSaleUprc") {
                    // 이미 등록된 상품이면 체크박스와 가격입력 막기
                    if (item[("regYn")] === 'Y') {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);
                        item[("gChk")] = false; // 전체 체크시 오류

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }

                // 판매가 변경시 다른 컬럼값도 변경
                if (col.binding === "saleUprc") {
                    var scope = agrid.getScope('menuGroupCtrl');
                    if(scope.saleUprcApply){
                        item.stinSaleUprc = item.saleUprc;
                        item.dlvrSaleUprc = item.saleUprc;
                        item.packSaleUprc = item.saleUprc;
                    }
                }
            }
            s.collectionView.commitEdit();
        });
    };

    $scope.$on("prodSelectCtrl", function(event, data) {

        // 상품조회
        $scope.searchProd();
        event.preventDefault();
    });

    // 상품조회
    $scope.searchProd = function(){

        var params = [];
        params.chkDt = $scope.isChecked;
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.prodCd = $("#srchProdCd").val();
        params.prodNm = $("#srchProdNm").val();
        params.prodClassCd = $scope.prodClassCd;
        params.barCd = $("#srchBarCd").val();
        params.hqBrandCd = $scope.srchBrandCombo.selectedValue;
        params.useYn = $scope.srchUseYnCombo.selectedValue;
        params.prodTypeFg = $scope.srchProdTypeFgCombo.selectedValue;
        params.regYn = $scope.srchRegYnCombo.selectedValue;
        params.storeGroupCd = $("#hdStoreGroupCd").val();

        $scope._inquirySub("/base/store/storeType/storeType/getProdList.sb", params, function() {}, false);
    };

    // 상품등록
    $scope.regProd = function () {

        $scope.flex.collectionView.commitEdit();

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].storeGroupCd = $("#hdStoreGroupCd").val();
                $scope.flex.collectionView.items[i].storeTypeAutoEnvstVal = storeTypeAutoEnvstVal; // 매장타입자동적용(1106)
                $scope.flex.collectionView.items[i].applyFg = storeTypeApplyEnvstVal; // 매장타입판매가설정(1107)
                $scope.flex.collectionView.items[i].commentRemark = "TB_HQ_STORE_PROD_GROUP_DTL 등록 후 자동 적용";
                $scope.flex.collectionView.items[i].status = 'I';

                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/store/storeType/storeType/saveProdMapping.sb", params, function(){

            // 상품 재조회
            $scope.searchProd();

            // 상품연결 재조회
            var prodMappingGrid = agrid.getScope("prodMappingCtrl");
            prodMappingGrid._pageView('prodMappingCtrl', 1);

        });
    };

    //전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    // 상품 그리드 초기화
    $scope.prodSelectGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);

