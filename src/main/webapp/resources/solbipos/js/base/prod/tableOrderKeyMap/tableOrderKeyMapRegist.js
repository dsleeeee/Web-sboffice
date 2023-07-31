/****************************************************************
 *
 * 파일명 : tableOrderKeyMapRegist.js
 * 설  명 : 테이블오더키맵등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.26     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var regYn = [
    {"name": "전체", "value": ""},
    {"name": "등록", "value": "Y"},
    {"name": "미등록", "value": "N"}
];

var useYnAllComboData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

// 키오스크 카테고리(분류)
app.controller('tableOrderKeyMapRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('tableOrderKeyMapRegistCtrl', $scope, $http, false));

    $scope.initGrid = function (s, e) {
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'tuClsCd') {
                    if(item.tuClsCd !== '자동채번') {
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
                if (col.binding === "tuClsCd") {
                    if(selectedRow.tuClsCd !== '자동채번') {
                        $("#storeMod").val(selectedRow.storeModYn);
                        $scope._broadcast('tableOrderKeyMapCtrl', selectedRow);
                        event.preventDefault();
                    }
                }
            }
        });
    };

    $scope.$on("tableOrderKeyMapRegistCtrl", function(event, data) {
        $scope.btnSearchCls();
        event.preventDefault();
    });

    // 키오스크 카테고리(분류) 조회
    $scope.btnSearchCls = function(){
        // 초기화
        $scope.reset();

        var params = {};
        if(orgnFg === "STORE") {params.posNo = '00'}
        params.tuClsType = '00';

        $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getKioskCategory.sb", params, function() {
            if(orgnFg === "HQ" || (orgnFg === "STORE" && kioskKeyEnvstVal !== "0")){

                // 카테고리(분류)가 정상조회 되면 관련 버튼 보이도록
                var divBtnCls = document.getElementById('divBtnCls');
                divBtnCls.style.visibility='visible';

                if(orgnFg === "STORE" && kioskKeyEnvstVal === "2"){
                    $("#btnUpCls").css("display", "none");
                    $("#btnDownCls").css("display", "none");
                    $("#btnAddCls").css("display", "none");
                    $("#btnDelCls").css("display", "none");
                }
            }

            // 본사일때 매장수정가능한 키맵인 경우 수정X
            var grid = wijmo.Control.getControl("#wjGridCategoryCls");
            var rows = grid.rows;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];

                if((orgnFg === "HQ" && item.storeModYn === "Y") || (orgnFg === "STORE" && kioskKeyEnvstVal === "2" && item.storeModYn === "N")){
                    item.gChk = false;
                    rows[i].isReadOnly = true;
                }
            }
        }, false);
    };

    // 초기화
    $scope.reset = function(){
        // 기존 POS번호, 키맵그룹, 카테고리(분류) 값 초기화
        $("#hdTuClsCd").val("");
        $("#spanTuKeyCls").text("");

        // 카테고리(분류), 키맵, 상품 관련 버튼 hidden 처리
        var divBtnCls = document.getElementById('divBtnCls');
        var divBtnKeyMap = document.getElementById('divBtnKeyMap');
        var divBtnProd = document.getElementById('divBtnProd');
        divBtnCls.style.visibility='hidden';
        divBtnKeyMap.style.visibility='hidden';
        divBtnProd.style.visibility='hidden';

        // 카테고리(중분류) hidden
        $("#divGridCategoryClsM").css("display", "none");

        // 상품 검색조건 초기화
        var scope = agrid.getScope('tableOrderProdCtrl');
        scope.srchStartDate.selectedValue = new Date();
        scope.srchEndDate.selectedValue = new Date();
        scope.srchStartDate.isReadOnly = true;
        scope.srchEndDate.isReadOnly = true;
        scope.isChecked = true;
        scope.prodCd = "";
        scope.prodNm = "";
        scope.prodClassCd = "";
        scope.prodClassCdNm = "";
        scope.barCd = "";
        scope.useYnAllCombo.selectedIndex = 0;
        scope.prodTypeFgAllCombo.selectedIndex = 0;
        scope.regYnAllCombo.selectedIndex = 0;

        // 키맵 grid 초기화
        var wjGridKeyMap = wijmo.Control.getControl("#wjGridKeyMap");
        while(wjGridKeyMap.rows.length > 0){
            wjGridKeyMap.rows.removeAt(wjGridKeyMap.rows.length-1);
        }

        // 상품 grid 초기화
        var wjGridProd = wijmo.Control.getControl("#wjGridProd");
        while(wjGridProd.rows.length > 0){
            wjGridProd.rows.removeAt(wjGridProd.rows.length-1);
        }

        // 상품 grid paging 초기화(숨기기.. 아예 없애는거 모름..)
        var tableOrderProdCtrlPager = document.getElementById('tableOrderProdCtrlPager');
        tableOrderProdCtrlPager.style.visibility='hidden';
    };

    // 초기화
    $scope.resetM = function(){
        // 기존 POS번호, 키맵그룹, 카테고리(분류) 값 초기화
        $("#spanTuKeyCls").text("");

        // 카테고리(분류), 키맵, 상품 관련 버튼 hidden 처리
        var divBtnKeyMap = document.getElementById('divBtnKeyMap');
        var divBtnProd = document.getElementById('divBtnProd');
        divBtnKeyMap.style.visibility='hidden';
        divBtnProd.style.visibility='hidden';

        // 상품 검색조건 초기화
        var scope = agrid.getScope('tableOrderProdCtrl');
        scope.srchStartDate.selectedValue = new Date();
        scope.srchEndDate.selectedValue = new Date();
        scope.srchStartDate.isReadOnly = true;
        scope.srchEndDate.isReadOnly = true;
        scope.isChecked = true;
        scope.prodCd = "";
        scope.prodNm = "";
        scope.prodClassCd = "";
        scope.prodClassCdNm = "";
        scope.barCd = "";
        scope.useYnAllCombo.selectedIndex = 0;
        scope.prodTypeFgAllCombo.selectedIndex = 0;
        scope.regYnAllCombo.selectedIndex = 0;

        // 키맵 grid 초기화
        var wjGridKeyMap = wijmo.Control.getControl("#wjGridKeyMap");
        while(wjGridKeyMap.rows.length > 0){
            wjGridKeyMap.rows.removeAt(wjGridKeyMap.rows.length-1);
        }

        // 상품 grid 초기화
        var wjGridProd = wijmo.Control.getControl("#wjGridProd");
        while(wjGridProd.rows.length > 0){
            wjGridProd.rows.removeAt(wjGridProd.rows.length-1);
        }

        // 상품 grid paging 초기화(숨기기.. 아예 없애는거 모름..)
        var tableOrderProdCtrlPager = document.getElementById('tableOrderProdCtrlPager');
        tableOrderProdCtrlPager.style.visibility='hidden'
    };

    // 카테고리(분류) 상위 순서 이동
    $scope.rowMoveUpCls = function () {
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
    };

    // 카테고리(분류) 하위 순서 이동
    $scope.rowMoveDownCls = function () {
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
    };

    // 카테고리(분류) 추가
    $scope.addRowCls = function () {
        var params = {};
        params.gChk = false;
        params.tuClsCd = '자동채번';
        params.tuClsNm = '';
        params.clsMemo = '';
        params.tuMClsFg = "0";

        // 행추가
        $scope._addRow(params, 2);
    };

    // 카테고리(분류) 삭제
    $scope.delRowCls = function () {
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
                $scope.flex.collectionView.removeAt(i);
            }
        }
    };

    // 카테고리(분류) 저장
    $scope.saveCls = function () {
        $scope.flex.collectionView.commitEdit();

        // 생성, 수정 Validation Check
        for (var m = 0; m < $scope.flex.collectionView.itemCount; m++) {
            if(  $scope.flex.collectionView.items[m].tuClsNm === null  || $scope.flex.collectionView.items[m].tuClsNm === '') {
                $scope._popMsg(messages['tableOrderKeyMap.require.category.msg']); // 카테고리명을 입력해주세요.
                return;
            }
        }

        // 파라미터 설정
        var params = [];

        for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
            if(orgnFg === "STORE") {$scope.flex.collectionView.itemsRemoved[d].posNo = '00';}
            $scope.flex.collectionView.itemsRemoved[d].tuClsType = '00';
            $scope.flex.collectionView.itemsRemoved[d].status = 'D';
            params.push($scope.flex.collectionView.itemsRemoved[d]);
        }

        // indexNo 재설정
        var editItems = [];
        for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
            if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
                editItems.push($scope.flex.collectionView.items[s]);
            }
        }

        for (var s = 0; s < editItems.length; s++) {
            editItems[s].indexNo = (s + 1);
            $scope.flex.collectionView.editItem(editItems[s]);
            editItems[s].status = "U";
            $scope.flex.collectionView.commitEdit();
        }

        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            if(orgnFg === "STORE") {$scope.flex.collectionView.itemsEdited[u].posNo = '00';}
            $scope.flex.collectionView.itemsEdited[u].tuClsType = '00';
            $scope.flex.collectionView.itemsEdited[u].status = 'U';
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            if(orgnFg === "STORE") {$scope.flex.collectionView.itemsAdded[i].posNo = '00';}
            $scope.flex.collectionView.itemsAdded[i].tuClsType = '00';
            $scope.flex.collectionView.itemsAdded[i].status = 'I';
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        // 카테고리(분류)를 모두 삭제하는지 파악하기 위해
        var gridLength = $scope.flex.collectionView.items.length;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/kioskKeyMap/kioskKeyMap/saveKioskCategory.sb', params, function() {
            if(gridLength > 0) {
                // 카테고리분류 재조회
                $scope.btnSearchCls();
            }
        });
    };

    // 키맵매장적용
    $scope.tuClsTypeStore = function(){
        $scope.tableOrderKeyMapStoreRegLayer.show(true);
        $scope._broadcast('tableOrderKeyMapStoreRegCtrl');
    };

}]);


// 키오스크 키맵
app.controller('tableOrderKeyMapCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('tableOrderKeyMapCtrl', $scope, $http, false));

    $scope.initGrid = function (s, e) {
        // 그리드 포맷 핸들러
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col  = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "prodNm") {
                    if(item.prodNm === null) {
                        e.cell.textContent = "이 상품은 화면에 표시되지 않습니다.";
                    }
                }
            }
        });
    };

    $scope.$on("tableOrderKeyMapCtrl", function(event, data) {
        if(data !== undefined && !isEmptyObject(data)) {
            // 카테고리 관련 데이터 셋팅
            $("#spanTuKeyCls").text("[" + data.tuClsCd + "] " + data.tuClsNm);
            $("#hdTuClsCd").val(data.tuClsCd);
        }

        // 키맵 조회
        $scope.searchKeyMap();
    });

    // 키맵 조회
    $scope.searchKeyMap = function () {
        var params = {};
        if(orgnFg === "STORE") {params.posNo = '00'}
        params.tuClsType = '00';
        params.tuClsCd = $("#hdTuClsCd").val();

        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getKioskKeyMap.sb", params, function() {
            // 본사 : storeMod = N
            // 프차매장 : 1249가 1 or 2인데 storeMod = Y
            // 단독매장
            if((orgnFg === "HQ" && $("#storeMod").val() === "N")
                || (orgnFg === "STORE" && hqOfficeCd != "00000" && kioskKeyEnvstVal ==="1")
                || (orgnFg === "STORE" && hqOfficeCd != "00000" && kioskKeyEnvstVal ==="2" && $("#storeMod").val() === "Y")
                || (orgnFg === "STORE" && hqOfficeCd == "00000")){

                // 카테고리(분류)가 정상조회 되면 키맵관련 버튼 보이도록
                var divBtnKeyMap = document.getElementById('divBtnKeyMap');
                divBtnKeyMap.style.visibility='visible';

                // 카테고리(분류)가 정상조회 되면 상품관련 버튼 보이도록
                var divBtnProd = document.getElementById('divBtnProd');
                divBtnProd.style.visibility='visible';

                // paging 영역 보이도록
                var tableOrderProdCtrlPager = document.getElementById('tableOrderProdCtrlPager');
                tableOrderProdCtrlPager.style.visibility='visible';

                // 상품 조회
                var tableOrderProdGrid = agrid.getScope("tableOrderProdCtrl");
                tableOrderProdGrid._pageView('tableOrderProdCtrl', 1);
            } else {
                // 카테고리(분류)가 정상조회 되면 키맵관련 버튼 보이도록
                var divBtnKeyMap = document.getElementById('divBtnKeyMap');
                divBtnKeyMap.style.visibility='hidden';

                // 카테고리(분류)가 정상조회 되면 상품관련 버튼 보이도록
                var divBtnProd = document.getElementById('divBtnProd');
                divBtnProd.style.visibility='hidden';

                // paging 영역 보이도록
                var tableOrderProdCtrlPager = document.getElementById('tableOrderProdCtrlPager');
                tableOrderProdCtrlPager.style.visibility='hidden'
            }
        }, false);
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
    };

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
    };

    // 키맵 삭제
    $scope.delRowKeyMap = function () {
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
                $scope.flex.collectionView.removeAt(i);
            }
        }
    };

    // 키맵 저장
    $scope.saveKeyMap = function () {
        $scope.flex.collectionView.commitEdit();

        // 파라미터 설정
        var params = [];

        for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
            if(orgnFg === "STORE") {$scope.flex.collectionView.itemsRemoved[d].posNo = '00'}
            $scope.flex.collectionView.itemsRemoved[d].tuClsType = '00';
            $scope.flex.collectionView.itemsRemoved[d].tuClsCd = $("#hdTuClsCd").val();
            $scope.flex.collectionView.itemsRemoved[d].status = 'D';
            params.push($scope.flex.collectionView.itemsRemoved[d]);
        }

        // indexNo 재설정
        var editItems = [];
        for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
            if( isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
                editItems.push($scope.flex.collectionView.items[s]);
            }
        }

        for (var s = 0; s < editItems.length; s++) {
            editItems[s].indexNo = (s + 1);
            $scope.flex.collectionView.editItem(editItems[s]);
            editItems[s].status = "U";
            $scope.flex.collectionView.commitEdit();
        }

        for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
            if(orgnFg === "STORE") {$scope.flex.collectionView.itemsEdited[u].posNo = '00';}
            $scope.flex.collectionView.itemsEdited[u].tuClsType = '00';
            $scope.flex.collectionView.itemsEdited[u].tuClsCd = $("#hdTuClsCd").val();
            $scope.flex.collectionView.itemsEdited[u].status = 'U';
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/kioskKeyMap/kioskKeyMap/updateKioskKeyMap.sb', params, function() {

            // 키맵 재조회
            $scope.searchKeyMap();

        });
    }

}]);


// 키오스크 상품
app.controller('tableOrderProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('tableOrderProdCtrl', $scope, $http, false));

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

    // ComboBox 셋팅
    $scope._setComboData('useYnAllComboData', useYnAllComboData);
    $scope._setComboData('prodTypeFg', prodTypeFg);
    $scope._setComboData('regYn', regYn);
    $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드

    // 등록일자 기본 '전체기간'으로
    $scope.isChecked = true;

    $scope.initGrid = function (s, e) {
        // 등록일자 기본 '전체기간'으로
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;

        $scope.regYnDataMap = new wijmo.grid.DataMap(regYn, 'value', 'name');
    };

    $scope.$on("tableOrderProdCtrl", function(event, data) {
        // 상품 조회
        $scope.searchProd();
        event.preventDefault();
    });

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

    // 상품 조회
    $scope.searchProd = function () {
        var params = {};
        if(orgnFg === "STORE") { params.posNo = '00'}
        params.tuClsType = '00';
        params.tuClsCd = $("#hdTuClsCd").val();
        params.chkDt = $scope.isChecked;
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.prodClassCd = $scope.prodClassCd;
        params.barCd = $scope.barCd;
        params.useYn = $scope.useYn;
        params.prodTypeFg = $scope.prodTypeFg;
        params.regYn = $scope.regYn;

        if(brandUseFg === "1" && orgnFg === "HQ"){
            // 선택한 상품브랜드가 있을 때
            params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

            // 선택한 상품브랜드가 없을 때('전체' 일때)
            if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

        $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getKioskProdList.sb", params, function() {}, false);
    };

    // 키맵 등록
    $scope.regProd = function () {
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                if(orgnFg === "STORE") {$scope.flex.collectionView.items[i].posNo = '00'}
                $scope.flex.collectionView.items[i].tuClsType = '00';
                $scope.flex.collectionView.items[i].tuClsCd = $("#hdTuClsCd").val();
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/kioskKeyMap/kioskKeyMap/saveKioskKeyMap.sb", params, function(){
            // 상품 재조회
            $scope.searchProd();

            // 키맵 재조회
            var tableOrderKeyMapGrid = agrid.getScope("tableOrderKeyMapCtrl");
            tableOrderKeyMapGrid._pageView('tableOrderKeyMapCtrl', 1);
        });
    };

}]);