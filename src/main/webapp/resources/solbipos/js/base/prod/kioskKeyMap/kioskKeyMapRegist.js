/****************************************************************
 *
 * 파일명 : kioskKeyMapRegist.js
 * 설  명 : 키오스크키맵등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.09.03     이다솜      1.0
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

// KIOSK중분류사용
var tuMClsFgComboData = [
    {"name":"미사용","value":"0"},
    {"name":"중분류사용","value":"2"}
];

if(pageFg === '1'){
    orgnFg = 'STORE';
};


// 키오스크 카테고리(분류)
app.controller('kioskKeyMapRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyMapRegistCtrl', $scope, $http, true));

    if(pageFg === '0') {
        $scope._setComboData("posNo", kioskPosList); // 키오스크용 포스 목록
        $scope._setComboData("tuClsType", kioskTuClsTypeList); // 키오스크용 키맵그룹 목록
    }
    $scope._setComboData("tuMClsFg", tuMClsFgComboData); // KIOSK중분류사용

    $scope.initGrid = function (s, e) {

        if(pageFg === '1'){
            $("#kioskKeyMapSelectStore").css("display",'block');
            if(sessionStorage.getItem('reloaded')){
                $("#kioskKeyMapSelectStoreCd").val(sessionStorage.getItem('selectStoreCd'));
                $("#kioskKeyMapSelectStoreNm").val(sessionStorage.getItem('selectStoreNm'));
                $("#kioskKeyMapSelectStoreNm").text(sessionStorage.getItem('selectStoreNm'));
                $scope._broadcast('kioskKeyMapRegistCtrl');
            };
        }

        if(hqOfficeCd === 'A0001' || hqOfficeCd === 'DS021' || hqOfficeCd === 'DS034' || hqOfficeCd === 'DS062'){
            $("#posNoTr").css("display",'none');
        }
        // 그리드 DataMap 설정
        $scope.tuMClsFgDataMap = new wijmo.grid.DataMap(tuMClsFgComboData, 'value', 'name'); // KIOSK중분류사용

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
                        // KIOSK중분류사용
                        // 미사용
                        if(selectedRow.tuMClsFg === "0") {
                            $("#divGridCategoryClsM").css("display", "none");

                            $scope._broadcast('kioskKeyMapCtrl', selectedRow);
                            event.preventDefault();
                            // 중분류사용
                        } else if(selectedRow.tuMClsFg === "2") {
                            //selectedRow.tuClsType = $("#hdTuClsType").val();

                            $("#divGridCategoryClsM").css("display", "");

                            $scope._broadcast('categoryClsMCtrl', selectedRow);
                            event.preventDefault();

                            // 초기화
                            $scope.resetM();
                        }
                    }
                }
            }
        });
    };

    $scope.$on("kioskKeyMapRegistCtrl", function(event, data) {

        if(pageFg === '1'){
            if( $("#kioskKeyMapSelectStoreCd").val() !== null && $("#kioskKeyMapSelectStoreCd").val() !== '') {
            }else{
                $scope._popMsg("매장을 선택해주세요.");
                return false;
            }
        }

        if(sessionStorage.getItem('reloaded')){
            sessionStorage.removeItem('reloaded');
            sessionStorage.removeItem('selectStoreCd');
            sessionStorage.removeItem('selectStoreNm');
            $scope.kioskKeyMapSelectStore();
        }else{
            $scope.btnSearchCls();
        }
        event.preventDefault();
    });

    // 키오스크 카테고리(분류) 조회
    $scope.btnSearchCls = function(){

        // 초기화
        $scope.reset();

        // 키맵그룹에 KIOSK중분류사용 조회
        $scope.kioskKeyMapGroupTuMClsFg();

        if(orgnFg === "STORE") {
            // 키오스크용으로 등록된 POS가 없습니다.
            if ($scope.posNoCombo.selectedValue === "" || $scope.posNoCombo.selectedValue === null) {
                s_alert.pop(messages["kioskKeyMap.require.pos.msg"]);
                return;
            }
        }

        // 키맵그룹이 없습니다. 신규그룹을 추가하세요.
        if ($scope.tuClsTypeCombo.selectedValue === "" || $scope.tuClsTypeCombo.selectedValue === null) {
            s_alert.pop(messages["kioskKeyMap.require.tuClsType.msg"]);
            return;
        }

        // POS 번호, 키맵그룹 hidden에 갖고있기(조회 시 사용)
        if(orgnFg === "STORE") {$("#hdPosNo").val($scope.posNoCombo.selectedValue);}
        $("#hdTuClsType").val($scope.tuClsTypeCombo.selectedValue);

        // 키맵그룹 관련 데이터 셋팅
        if(orgnFg === "STORE") {
            if(hqOfficeCd !== 'A0001' && hqOfficeCd !== 'DS021' && hqOfficeCd !== 'DS034' && hqOfficeCd !== 'DS062'){
                $("#lbTuClsType").text("POS번호 : " + $scope.posNoCombo.selectedValue + " / 키맵그룹 : " + $scope.tuClsTypeCombo.selectedValue);
            }else{
                $("#lbTuClsType").text("키맵그룹 : " + $scope.tuClsTypeCombo.selectedValue);
            }
            $("#tuClsTypeInfo").val($scope.tuClsTypeCombo._oldText);
        }else{
            $("#lbTuClsType").text("키맵그룹 : " + $scope.tuClsTypeCombo.selectedValue);
            $("#tuClsTypeInfo").val($scope.tuClsTypeCombo._oldText);
        }

        var params = {};
        if(orgnFg === "STORE") {params.posNo = $scope.posNoCombo.selectedValue;}
        if(pageFg === '1'){
            params.pageFg = '1';
            params.storeCd = $("#kioskKeyMapSelectStoreCd").val();
        }
        params.tuClsType = $scope.tuClsTypeCombo.selectedValue;

        $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getKioskCategory.sb", params, function() {
            if(orgnFg === "HQ" || (orgnFg === "STORE" && kioskKeyEnvstVal !== "0")){
                // 카테고리(분류)가 정상조회 되면 관련 버튼 보이도록
                var divBtnCls = document.getElementById('divBtnCls');
                divBtnCls.style.visibility='visible'

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

    // 키맵그룹에 KIOSK중분류사용 조회
    $scope.kioskKeyMapGroupTuMClsFg = function(){
        var params = {};
        if(orgnFg === "STORE") {params.posNo = $scope.posNoCombo.selectedValue;}
        if(pageFg === '1'){
            params.pageFg = '1';
            params.storeCd = $("#kioskKeyMapSelectStoreCd").val();
        }
        params.tuClsType = $scope.tuClsTypeCombo.selectedValue;

        $scope._postJSONQuery.withOutPopUp("/base/prod/kioskKeyMap/kioskKeyMap/getKioskKeyMapGroupTuMClsFg.sb", params, function(response) {
            var list = response.data.data.list;
            if(list.length > 0) {
                $("#hdTuMClsFg").val(list[0].tuMClsFg);
            }
        }, false);
    };

    // 초기화
    $scope.reset = function(){
        // 기존 POS번호, 키맵그룹, 카테고리(분류) 값 초기화
        $("#hdPosNo").val("");
        $("#hdTuClsType").val("");
        $("#hdTuClsCd").val("");
        $("#lbTuClsType").text("");
        $("#spanTuKeyCls").text("");

        // 카테고리(분류), 키맵, 상품 관련 버튼 hidden 처리
        var divBtnCls = document.getElementById('divBtnCls');
        var divBtnClsM = document.getElementById('divBtnClsM');
        var divBtnKeyMap = document.getElementById('divBtnKeyMap');
        var divBtnProd = document.getElementById('divBtnProd');
        divBtnCls.style.visibility='hidden';
        divBtnClsM.style.visibility='hidden';
        divBtnKeyMap.style.visibility='hidden';
        divBtnProd.style.visibility='hidden';

        // 카테고리(중분류) hidden
        $("#divGridCategoryClsM").css("display", "none");

        // 상품 검색조건 초기화
        var scope = agrid.getScope('kioskProdCtrl');
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
        scope.useYnAllCombo.selectedIndex = 1;
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
        var kioskProdCtrlPager = document.getElementById('kioskProdCtrlPager');
        kioskProdCtrlPager.style.visibility='hidden'
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
        var scope = agrid.getScope('kioskProdCtrl');
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
        scope.useYnAllCombo.selectedIndex = 1;
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
        var kioskProdCtrlPager = document.getElementById('kioskProdCtrlPager');
        kioskProdCtrlPager.style.visibility='hidden'
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

    // 카테고리(분류) 빈칸 추가
    $scope.blankRowCls = function () {
        var params = {};
        params.gChk = false;
        params.tuClsCd = '자동채번';
        params.tuClsNm = 'KIOSK_BLANK_CLS_NM_X';

        // 행추가
        $scope._addRow(params, 2);
    };

    // 카테고리(분류) 추가
    $scope.addRowCls = function () {
        var params = {};
        params.gChk = false;
        params.tuClsCd = '자동채번';
        params.tuClsNm = '';
        params.clsMemo = '';
        params.tuMClsFg = $("#hdTuMClsFg").val();

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
        var msg = messages["cmm.choo.save"] + '<br/>('+ $("#tuClsTypeInfo").val() + ")";

        if(orgnFg === "STORE" && (hqOfficeCd === 'A0001' || hqOfficeCd === 'DS021' || hqOfficeCd === 'DS034' || hqOfficeCd === 'DS062')){
            msg += "<br>" + messages["kioskKeyMap.keyMapStoreRegAll.msg"] // 저장 시 전체 키오스크의 정보가 변경됩니다.
        }

        $scope._popConfirm(msg, function () {
            $scope.flex.collectionView.commitEdit();

            // 생성, 수정 Validation Check
            for (var m = 0; m < $scope.flex.collectionView.itemCount; m++) {
                //if(params[m].status !== 'D') {
                if ($scope.flex.collectionView.items[m].tuClsNm === null || $scope.flex.collectionView.items[m].tuClsNm === '') {
                    $scope._popMsg(messages['kioskKeyMap.require.category.msg']); // 카테고리명을 입력해주세요.
                    return;
                }
                //}
            }

            // 파라미터 설정
            var params = [];

            for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
                if (orgnFg === "STORE") {
                    $scope.flex.collectionView.itemsRemoved[d].posNo = $scope.posNoCombo.selectedValue;
                }
                if(pageFg === '1'){
                    $scope.flex.collectionView.itemsRemoved[d].pageFg = '1';
                    $scope.flex.collectionView.itemsRemoved[d].storeCd = $("#kioskKeyMapSelectStoreCd").val();
                }
                $scope.flex.collectionView.itemsRemoved[d].tuClsType = $("#hdTuClsType").val();
                $scope.flex.collectionView.itemsRemoved[d].status = 'D';
                params.push($scope.flex.collectionView.itemsRemoved[d]);
            }

            // indexNo 재설정
            // var editItems = [];
            // for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
            //     if (isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
            //         editItems.push($scope.flex.collectionView.items[s]);
            //     }
            // }
            //
            // for (var s = 0; s < editItems.length; s++) {
            //     editItems[s].indexNo = (s + 1);
            //     $scope.flex.collectionView.editItem(editItems[s]);
            //     editItems[s].status = "U";
            //     $scope.flex.collectionView.commitEdit();
            // }
            for(var s = 0, num = 1; s < $scope.flex.rows.length; s++, num++) {
                if ($scope.flex.collectionView.items[s].indexNo !== num) {
                    $scope.flex.collectionView.items[s].indexNo = num;
                    $scope.flex.collectionView.editItem($scope.flex.collectionView.items[s]);
                    $scope.flex.collectionView.items[s].status = "U";
                    $scope.flex.collectionView.commitEdit();
                }
            }

            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                if (orgnFg === "STORE") {
                    $scope.flex.collectionView.itemsEdited[u].posNo = $scope.posNoCombo.selectedValue;
                }
                if(pageFg === '1'){
                    $scope.flex.collectionView.itemsEdited[u].pageFg = '1';
                    $scope.flex.collectionView.itemsEdited[u].storeCd = $("#kioskKeyMapSelectStoreCd").val();
                }
                $scope.flex.collectionView.itemsEdited[u].tuClsType = $("#hdTuClsType").val();
                $scope.flex.collectionView.itemsEdited[u].status = 'U';
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }

            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if (orgnFg === "STORE") {
                    $scope.flex.collectionView.itemsAdded[i].posNo = $scope.posNoCombo.selectedValue;
                }
                if(pageFg === '1'){
                    $scope.flex.collectionView.itemsAdded[i].pageFg = '1';
                    $scope.flex.collectionView.itemsAdded[i].storeCd = $("#kioskKeyMapSelectStoreCd").val();
                }
                $scope.flex.collectionView.itemsAdded[i].tuClsType = $("#hdTuClsType").val();
                $scope.flex.collectionView.itemsAdded[i].status = 'I';
                params.push($scope.flex.collectionView.itemsAdded[i]);
            }

            // 카테고리(분류)를 모두 삭제하는지 파악하기 위해
            var gridLength = $scope.flex.collectionView.items.length;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/kioskKeyMap/kioskKeyMap/saveKioskCategory.sb', params, function () {

                if (gridLength > 0) {
                    // 카테고리분류 재조회
                    $scope.btnSearchCls();
                } else {
                    // 키맵그룹 dropdown 재조회
                    $scope.setTuClsDropdownList("F");
                }
            });
        });
    };

    // 신규그룹추가
    $scope.tuClsTypeAdd = function(){

        if(orgnFg === "STORE") {
            // 키오스크용으로 등록된 POS가 없습니다.
            if ($scope.posNoCombo.selectedValue === "" || $scope.posNoCombo.selectedValue === null) {
                s_alert.pop(messages["kioskKeyMap.require.pos.msg"]);
                return;
            }
        }

        // 키오스크 키맵그룹 '0 :미사용' 인 경우 01번 그룹 제외하고 추가 키맵그룹 생성 불가
        if(kioskKeyMapGrpFg === "0"){
            // 키맵그룹 사용설정이 지정되지 않아 그룹을 추가할수 없습니다.
            if($scope.tuClsTypeCombo.selectedValue !== null){
                s_alert.pop(messages["kioskKeyMap.grp.limit.msg"]);
                return;
            }
        }

        if(orgnFg === "HQ") {
            if($scope.tuMClsFgCombo.selectedValue === null){
                $scope._popMsg(messages["kioskKeyMap.tuMClsFgComboBlank"]); // KIOSK중분류사용을 선택해주세요.
                return false;
            }
        }

        // 신규그룹을 생성하시겠습니까?
        $scope._popConfirm(messages["kioskKeyMap.tuClsTypeAdd.msg"], function() {

            var tuClsTypeNm = prompt('그룹명을 입력하세요','그룹명');
            // 파라미터 설정
            var params = {};
            if(orgnFg === "STORE") {
                params.posNo = $scope.posNoCombo.selectedValue;
                params.pageFg = '1';
                if($("#kioskKeyMapSelectStoreCd").val() !== null && $("#kioskKeyMapSelectStoreCd").val() !== ""){
                    params.storeCd = $("#kioskKeyMapSelectStoreCd").val();
                }
            }
            params.tuClsNm = "기본";
            params.indexNo = "1";
            params.tuClsTypeNm = tuClsTypeNm;
            if(orgnFg === "HQ") {params.tuMClsFg = $scope.tuMClsFgCombo.selectedValue;}

            $scope._postJSONSave.withPopUp("/base/prod/kioskKeyMap/kioskKeyMap/createKioskTuClsType.sb", params, function (response) {

                // 키맵그룹 dropdown 재조회
                $scope.setTuClsDropdownList("L");
            });

        });
    };

    // 그룹복제
    $scope.tuClsTypeCopy = function(){

        if(orgnFg === "STORE") {
            // 키오스크용으로 등록된 POS가 없습니다.
            if ($scope.posNoCombo.selectedValue === "" || $scope.posNoCombo.selectedValue === null) {
                s_alert.pop(messages["kioskKeyMap.require.pos.msg"]);
                return;
            }
        }

        // 키맵그룹이 없습니다. 신규그룹을 추가하세요.
        if ($scope.tuClsTypeCombo.selectedValue === "" || $scope.tuClsTypeCombo.selectedValue === null) {
            s_alert.pop(messages["kioskKeyMap.require.tuClsType.msg"]);
            return;
        }

        if(orgnFg === "HQ"){

            // '01' 그룹을 복사하여 새 그룹을 생성하시겠습니까?
            $scope._popConfirm( "'" + $scope.tuClsTypeCombo.selectedItem.name + "' " + messages["kioskKeyMap.tuClsTypeCopy.msg"], function() {

                // 파라미터 설정
                var params = {};
                if(orgnFg === "STORE") {params.posNo = $scope.posNoCombo.selectedValue;}
                params.tuClsType = $scope.tuClsTypeCombo.selectedValue;

                $scope._postJSONSave.withPopUp("/base/prod/kioskKeyMap/kioskKeyMap/copyKioskTuClsType.sb", params, function (response) {

                    // 키맵그룹 dropdown 재조회
                    $scope.setTuClsDropdownList("L");
                });
            });
        } else if (orgnFg === "STORE"){
            $scope.kioskKeyMapCopyStoreLayer.show(true);
            $scope._broadcast('kioskKeyMapCopyStoreCtrl');
        }

    };

    // 키맵매장적용
    $scope.tuClsTypeStore = function(){

        // 키맵그룹이 없습니다. 신규그룹을 추가하세요.
        if ($scope.tuClsTypeCombo.selectedValue === "" || $scope.tuClsTypeCombo.selectedValue === null) {
            s_alert.pop(messages["kioskKeyMap.require.tuClsType.msg"]);
            return;
        }else{
            $scope.kioskKeyMapStoreRegLayer.show(true);
            $scope._broadcast('kioskKeyMapStoreRegCtrl');
        }
    };

    // 키맵매장복사
    $scope.storeCopy = function(){
        $scope.kioskKeyMapStoreCopyLayer.show(true);
        $scope._broadcast('kioskKeyMapStoreCopyCtrl');
    };

    // 키오스크키맵삭제
    $scope.kioskKeyDel = function(){
        $scope.kioskKeyDelLayer.show(true);
        if(orgnFg === "HQ"){
            $scope._broadcast('kioskKeyDelCtrl', null);
        } else {
            $scope._broadcast('kioskKeyDelCtrl', $scope.posNoCombo.selectedValue);
        }
    };

    // 추천메뉴
    $scope.tuRecmd = function(){

        $scope.kioskRecmdLayer.show(true);
        var recmdScope = agrid.getScope('kioskRecmdCtrl');
        recmdScope.btnSearchRecmd();
    };

    // 매장수정허용카테고리
    $scope.storeMod = function(){
        $scope.storeModLayer.show(true);
        $scope._broadcast('storeModCtrl');
    };

    // 매장수정허용카테고리
    $scope.clsTypeNm = function(){
        $scope.clsTypeNmLayer.show(true);
        if(orgnFg === "HQ"){
            $scope._broadcast('clsTypeNmCtrl', null);
        } else {
            $scope._broadcast('clsTypeNmCtrl', $scope.posNoCombo.selectedValue);
        }
    };

    // 키맵미리보기
    $scope.kioskKeyMapView = function(){
        $scope.kioskKeyMapViewLayer.show(true);
        $scope._broadcast('kioskKeyMapViewCtrl');
    };


    // 본사판매가관리 팝업
    $scope.hqSalePriceView = function(){
        $scope.popUpHqSalePriceLayer.show(true);
        $scope._broadcast('popUpHqSalePriceCtrl');
        event.preventDefault();
    };

    // 매장판매가관리 팝업
    $scope.storeSalePriceView = function(){
        $scope.popUpStoreSalePriceLayer.show(true);
        $scope._broadcast('popUpStoreSalePriceCtrl');
        event.preventDefault();
    };

    // 판매가관리 팝업
    $scope.salePriceView = function(){
        $scope.popUpSalePriceLayer.show(true);
        $scope._broadcast('popUpSalePriceCtrl');
        event.preventDefault();
    };

    // 매장권한) POS번호 선택 시, 키맵그룹 dropdown 조회
    $scope.setTuClsType = function (s) {

        // 키맵그룹 dropdown 재조회
        $scope.setTuClsDropdownList("F");
    }

    // 키맵그룹 dropdownLIst 재조회
    $scope.setTuClsDropdownList = function (type){

        // 키맵그룹 dropdown 재조회
        var newGrp = 0; // 새로 생성된 그룹의 index 번호(dropdown 셋팅을 위해)
        var url = '/base/prod/kioskKeyMap/kioskKeyMap/getKioskTuClsTypeList.sb';
        var params = {};
        if(orgnFg === "STORE") {
            params.posNo = $scope.posNoCombo.selectedValue;
            params.pageFg = '1';
            if($("#kioskKeyMapSelectStoreCd").val() !== null && $("#kioskKeyMapSelectStoreCd").val() !== ""){
                params.storeCd = $("#kioskKeyMapSelectStoreCd").val();
            }
        }

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
                    var comboArrayAll = [];
                    var comboArray00 = [];
                    var comboData  = {};

                    comboArrayAll.unshift({name: "전체", value: ""});
                    comboArray00.unshift({name: "[00]사용중인키맵매장적용", value: "00"});

                    for (var i = 0; i < list.length; i++) {
                        comboData = {};
                        comboData.name  = list[i].name;
                        comboData.value = list[i].value;
                        comboArray.push(comboData);
                        comboArrayAll.push(comboData);
                        comboArray00.push(comboData);
                        // }
                        //
                        // if(type === "L"){
                        //     kioskTuClsTypeListAll = comboArrayAll;
                    }
                    if(pageFg === '1'){
                        kioskTuClsTypeListAll = comboArrayAll;
                    }

                    $scope._setComboData("tuClsType", comboArray);
                    $scope._setComboData("tuClsTypeView", comboArray);
                    if(orgnFg === "HQ") {
                        $scope._setComboData("applyTuClsType", comboArray00);   // 키오스크키맵 매장적용 - 키맵그룹 콤보
                        $scope._setComboData("tuClsTypeCombo2", comboArrayAll); // [매장/포장]키맵적용 - 검색조건 '키맵그룹' 콤보
                        $scope._setComboData("envTuClsType", comboArray);       // [매장/포장]키맵적용 - 키맵그룹 콤보 (적용 버튼 옆)
                    }
                    if(orgnFg === "STORE"){
                        $scope._setComboData("envStoreTuClsType", comboArray);
                    }

                    if(type === "F"){

                        // 현재 선택한 POS에서 사용중인 키맵그룹 셋팅을 위한 키오스크 환경설정 값((매장)키맵) 가져오기
                        var params = {};
                        params.posNo = $scope.posNoCombo.selectedValue;
                        params.envstCd = '4068';

                        if(pageFg === '1'){
                            params.pageFg = '1';
                            params.storeCd = $("#kioskKeyMapSelectStoreCd").val();
                        }

                        // 조회 수행 : 조회URL, 파라미터, 콜백함수
                        $scope._postJSONQuery.withPopUp("/base/prod/kioskKeyMap/kioskKeyMap/getKioskEnv.sb", params, function(response){

                            if(response.data.data !== null && response.data.data !== ""){
                                for(var i=0; i< comboArray.length; i++){
                                    if(comboArray[i].value === response.data.data){
                                        newGrp = i;
                                    }
                                }
                            }

                            $timeout(function () {
                                // 신규그룹추가 후, 새 그룹으로 dropdownLIst 와 grid 셋팅
                                $scope.setNewTuClsType(newGrp);
                            }, 10);
                        });

                    }else if (type === "L"){
                        newGrp = list.length - 1;

                        $timeout(function () {
                            // 신규그룹추가 후, 새 그룹으로 dropdownLIst 와 grid 셋팅
                            $scope.setNewTuClsType(newGrp);
                        }, 10);
                    }
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        });
    };

    // 신규그룹추가 후, 새 그룹으로 dropdownLIst 와 grid 셋팅
    $scope.setNewTuClsType = function (newGrp) {

        // 새로 생성된 신규그룹 setting
        $scope.tuClsTypeCombo.selectedIndex = newGrp;

        // 새로 생성된 신규그룹으로 카테고리분류 조회
        $scope.btnSearchCls();
    };

    // 키오스크 키맵그룹코드(매장/포장) 적용 팝업
    $scope.envConfg = function (key) {

        // 키맵그룹이 없습니다. 신규그룹을 추가하세요.
        if ($scope.tuClsTypeCombo.selectedValue === "" || $scope.tuClsTypeCombo.selectedValue === null) {
            s_alert.pop(messages["kioskKeyMap.require.tuClsType.msg"]);
            return;
        }else {
            if (orgnFg === "HQ") {
                $scope.kioskKeyMapEnvLayer.show(true);
                $scope._broadcast('kioskKeyMapEnvCtrl', key);
            } else{

                var params = {};
                params.envstCd = key;
                params.posNo = $scope.posNoCombo.selectedValue;

                $scope.kioskKeyMapEnvStoreLayer.show(true);
                $scope._broadcast('kioskKeyMapEnvStoreCtrl', params);
            }
        }
    }

    // 매장 선택
    $scope.kioskKeyMapSelectStore = function(selectStoreCd) {

        var params = {};
        if(selectStoreCd !== null && selectStoreCd !== "" && selectStoreCd !== undefined){
            params.storeCd = selectStoreCd;
            storeCd = selectStoreCd;
        }else {
            params.storeCd = $("#kioskKeyMapSelectStoreCd").val();
            storeCd = $("#kioskKeyMapSelectStoreCd").val();
        }

        if( isEmptyObject( params.storeCd) ) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.loading"]); // 데이터 처리중 메시지 팝업 열기

        $scope._postJSONQuery.withOutPopUp( "/base/prod/kioskKeyMap/kioskKeyMap/kioskKeyMapSelectStore.sb", params, function(response){
            var data = response.data.data;

            var flex = $scope.flex;
            flex.itemsSource = new wijmo.collections.CollectionView();
            flex.collectionView.trackChanges = true;

            var comboArray = [];
            var comboData  = {};

            var buttons = [
                'btnCol', 'btnTuClsTypeAdd', 'btnTuClsTypeCopyHq', 'btnTuClsTypeStore',
                'btnRecmd', 'btnDelKioskKey', 'btnStoreMod', 'btnClsTypeNm',
                'btnKioskKeyMapView', 'btnHqSalePrice', 'btnStoreSalePrice', 'btnSalePrice'
            ];

            buttons.forEach(function(id) {
                if(!isEmptyObject($("#"+id))){
                    $("#"+id).css("display", 'none');
                }
            });

            if(data.kioskKeyEnvstVal === '1'){
                $("#btnCol").css("display", '');
                $("#btnRecmd").css("display", '');
                $("#btnTuClsTypeAdd").css("display", '');
                $("#btnDelKioskKey").css("display", '');
                $("#btnClsTypeNm").css("display", '');

                if(kioskKeyMapGrpFg === '1'){
                    $("#btnTuClsTypeCopyHq").css("display", '');
                }
            }else if(data.kioskKeyEnvstVal === '2'){
                $("#btnCol").css("display", '');
                $("#btnRecmd").css("display", '');
                if(hqOfficeCd !== '00000'){
                    $("#btnStoreMod").css("display", '');
                }
            }
            if(data.momsEnvstVal === '1'){
                $("#btnKioskKeyMapView").css("display", '');
            }
            if(userId === 'ds021' || userId === 'ds024' || userId === 'h0360'){
                $("#btnSalePrice").css("display", '');
            }

            kioskPosList = data.kioskPosList;
            kioskKeyEnvstVal = data.kioskKeyEnvstVal;
            kioskKeyMapGrpFg = data.kioskKeyMapGrpFg;
            userHqBrandCdComboList = data.userHqBrandCdComboList;
            momsEnvstVal = data.momsEnvstVal;

            $scope._setComboData("posNo", kioskPosList);
            $scope._setComboData("orgStorePosNo", kioskPosList); // 키오스크용 포스 목록
            $scope._setComboData("storePosNo", kioskPosList); // 키오스크용 포스 목록
            $scope._setComboData("popUpPosNo", kioskPosList); // 키오스크용 포스 목록
            $scope._broadcast('prodClassPopUpCtrl','1');

            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기

        });
    }

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.kioskKeyMapSelectStoreShow = function () {
        $scope._broadcast('kioskKeyMapSelectStoreCtrl');
    };

}]);


// 키오스크 카테고리(중분류)
app.controller('categoryClsMCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('categoryClsMCtrl', $scope, $http, false));

    $scope.initGrid = function (s, e) {
        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'tuMClsCd') {
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
                if (col.binding === "tuMClsCd") {
                    if(selectedRow.tuMClsCd !== '자동채번') {
                        selectedRow.tuClsCd = selectedRow.tuMClsCd;
                        selectedRow.tuClsNm = selectedRow.tuMClsNm;
                        $scope._broadcast('kioskKeyMapCtrl', selectedRow);
                        event.preventDefault();
                    }
                }
            }
        });
    };

    $scope.$on("categoryClsMCtrl", function(event, data) {

        if(data !== undefined && !isEmptyObject(data)) {

            $("#lbTuClsCdM").text("중분류 : [" + data.tuClsCd + "] " + data.tuClsNm);

            // 카테고리 관련 데이터 셋팅
            $("#hdTuClsCd").val(data.tuClsCd);

            // 선택한 대분류 키값 갖고 있기
            $scope.selTuClsCd = data.tuClsCd;
        }

        // 중분류그리드 조회
        $scope.searchCategoryClsM();
    });

    // 중분류그리드 조회
    $scope.searchCategoryClsM = function () {
        var params = {};
        if(orgnFg === "STORE") {params.posNo = $("#hdPosNo").val();}
        if(pageFg === '1'){
            params.pageFg = '1';
            params.storeCd = $("#kioskKeyMapSelectStoreCd").val();
        }
        params.tuClsType = $("#hdTuClsType").val();
        params.tuClsCd = $scope.selTuClsCd;

        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getKioskCategoryM.sb", params, function() {
            // 본사 : storeMod = N
            // 프차매장 : 1249가 1 or 2인데 storeMod = Y
            // 단독매장
            if((orgnFg === "HQ" && $("#storeMod").val() === "N")
                || (orgnFg === "STORE" && hqOfficeCd != "00000" && kioskKeyEnvstVal ==="1")
                || (orgnFg === "STORE" && hqOfficeCd != "00000" && kioskKeyEnvstVal ==="2" && $("#storeMod").val() === "Y")
                || (orgnFg === "STORE" && hqOfficeCd == "00000")){
                // 카테고리(중분류)가 정상조회 되면 관련 버튼 보이도록
                var divBtnClsM = document.getElementById('divBtnClsM');
                divBtnClsM.style.visibility='visible'
            } else {
                // 카테고리(중분류)가 정상조회 되면 관련 버튼 보이도록
                var divBtnClsM = document.getElementById('divBtnClsM');
                divBtnClsM.style.visibility='hidden'
            }
        }, false);
    };

    // 카테고리(중분류) 상위 순서 이동
    $scope.rowMoveUpClsM = function () {
        var movedRows = 0;
        for (var i = 0; i < $scope.flexM.collectionView.itemCount; i++) {
            var item = $scope.flexM.collectionView.items[i];
            if (i > 0 && item.gChk) {
                if (!$scope.flexM.collectionView.items[i - 1].gChk) {
                    movedRows = i - 1;
                    var tmpItem = $scope.flexM.collectionView.items[movedRows];
                    $scope.flexM.collectionView.items[movedRows] = $scope.flexM.collectionView.items[i];
                    $scope.flexM.collectionView.items[i] = tmpItem;
                    $scope.flexM.collectionView.commitEdit();
                    $scope.flexM.collectionView.refresh();
                }
            }
        }
        $scope.flexM.select(movedRows, 1);
    };

    // 카테고리(중분류) 하위 순서 이동
    $scope.rowMoveDownClsM = function () {
        var movedRows = $scope.flexM.itemsSource.itemCount - 1;
        for (var i = $scope.flexM.itemsSource.itemCount - 1; i >= 0; i--) {
            var item = $scope.flexM.collectionView.items[i];
            if (i < ($scope.flexM.itemsSource.itemCount - 1) && item.gChk) {
                if (!$scope.flexM.collectionView.items[i + 1].gChk) {
                    movedRows = i + 1;
                    var tmpItem = $scope.flexM.collectionView.items[movedRows];
                    $scope.flexM.collectionView.items[movedRows] = $scope.flexM.collectionView.items[i];
                    $scope.flexM.collectionView.items[i] = tmpItem;
                    $scope.flexM.collectionView.commitEdit();
                    $scope.flexM.collectionView.refresh();
                }
            }
        }
        $scope.flexM.select(movedRows, 1);
    };

    // 카테고리(중분류) 추가
    $scope.addRowClsM = function () {
        var params = {};

        params.gChk = false;
        params.tuMClsCd = '자동채번';
        params.tuMClsNm = '';
        params.mmClsMemo = '';

        // 행추가
        // $scope._addRow(params, 2);
        var flex = $scope.flexM;
        var pos = 2;
        if (!flex.collectionView) {
            flex.itemsSource = new wijmo.collections.CollectionView();
        }
        var newRow = flex.collectionView.addNew();
        newRow.status = 'I';
        newRow.gChk = true;
        for (var prop in params) {
            newRow[prop] = params[prop];
        }
        flex.collectionView.trackChanges = true;
        flex.collectionView.commitNew();
        // 추가된 Row 선택
        setTimeout(function () {
            flex.scrollIntoView(flex.rows.length - 1, 0);
            flex.select(flex.rows.length - 1, 1);
            flex.focus();
            flex.startEditing(true, flex.rows.length - 1, (pos === null ? 0 : pos), true);
        }, 50);
    };

    // 카테고리(중분류) 삭제
    $scope.delRowClsM = function () {
        for(var i = $scope.flexM.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flexM.collectionView.items[i];
            if(item.gChk){
                $scope.flexM.collectionView.removeAt(i);
            }
        }
    };

    // 카테고리(중분류) 저장
    $scope.saveClsM = function () {
        var msg = messages["cmm.choo.save"];

        if(orgnFg === "STORE" && (hqOfficeCd === 'A0001' || hqOfficeCd === 'DS021' || hqOfficeCd === 'DS034' || hqOfficeCd === 'DS062')){
            msg += "<br>" + messages["kioskKeyMap.keyMapStoreRegAll.msg"] // 저장 시 전체 키오스크의 정보가 변경됩니다.
        }

        $scope._popConfirm(msg, function () {

            $scope.flexM.collectionView.commitEdit();

            // 생성, 수정 Validation Check
            for (var m = 0; m < $scope.flexM.collectionView.itemCount; m++) {
                //if(params[m].status !== 'D') {
                if(  $scope.flexM.collectionView.items[m].tuMClsNm === null  || $scope.flexM.collectionView.items[m].tuMClsNm === '') {
                    $scope._popMsg(messages['kioskKeyMap.require.category.msg']); // 카테고리명을 입력해주세요.
                    return;
                }
                //}
            }

            // 파라미터 설정
            var params = [];

            for (var d = 0; d < $scope.flexM.collectionView.itemsRemoved.length; d++) {
                if(orgnFg === "STORE") {$scope.flexM.collectionView.itemsRemoved[d].posNo = $scope.posNoCombo.selectedValue;}
                if(pageFg === '1'){
                    $scope.flexM.collectionView.itemsRemoved[d].pageFg = '1';
                    $scope.flexM.collectionView.itemsRemoved[d].storeCd = $("#kioskKeyMapSelectStoreCd").val();
                }
                $scope.flexM.collectionView.itemsRemoved[d].tuClsType =  $("#hdTuClsType").val();
                $scope.flexM.collectionView.itemsRemoved[d].tuClsCd = $scope.selTuClsCd;
                $scope.flexM.collectionView.itemsRemoved[d].status = 'D';
                params.push($scope.flexM.collectionView.itemsRemoved[d]);
            }

            // indexNo 재설정
            // var editItems = [];
            // for (var s = 0; s < $scope.flexM.collectionView.itemCount; s++) {IndexNo
            //     if($scope.flex.collectionView.items[s].indexNo !== (s+1)) {
            //         if (isEmptyObject($scope.flexM.collectionView.items[s].status) || $scope.flexM.collectionView.items[s].status === 'I') {
            //             editItems.push($scope.flexM.collectionView.items[s]);
            //         }
            //     }
            // }
            //
            // for (var s = 0; s < editItems.length; s++) {
            //     editItems[s].indexNo = (s + 1);
            //     $scope.flexM.collectionView.editItem(editItems[s]);
            //     editItems[s].status = "U";
            //     $scope.flexM.collectionView.commitEdit();
            // // }

            // for(var s = 0; s < $scope.flexM.rows.length; s++) {
            //     if ($scope.flexM.collectionView.items[s].indexNo !== $scope.flexM.rowNum) {
            //         $scope.flexM.collectionView.items[s].indexNo = $scope.flexM.rowNum;
            //     }
            // }

            for(var s = 0, num = 1; s < $scope.flexM.rows.length; s++, num++) {
                if ($scope.flexM.collectionView.items[s].indexNo !== num) {
                    $scope.flexM.collectionView.items[s].indexNo = num;
                    $scope.flexM.collectionView.editItem($scope.flexM.collectionView.items[s]);
                    $scope.flexM.collectionView.items[s].status = "U";
                    $scope.flexM.collectionView.commitEdit();
                }
                if($scope.flexM.rowNum == "" || $scope.flexM.rowNum == null || $scope.flexM.rowNum == undefined) {
                    $scope.flexM.rowNum = 0;
                }
            }

            for (var u = 0; u < $scope.flexM.collectionView.itemsEdited.length; u++) {
                if(orgnFg === "STORE") {$scope.flexM.collectionView.itemsEdited[u].posNo = $scope.posNoCombo.selectedValue;}
                $scope.flexM.collectionView.itemsEdited[u].tuClsType = $("#hdTuClsType").val();
                $scope.flexM.collectionView.itemsEdited[u].tuClsCd = $scope.selTuClsCd;
                $scope.flexM.collectionView.itemsEdited[u].status = 'U';
                if(pageFg === '1'){
                    $scope.flexM.collectionView.itemsEdited[u].pageFg = '1';
                    $scope.flexM.collectionView.itemsEdited[u].storeCd = $("#kioskKeyMapSelectStoreCd").val();
                }
                params.push($scope.flexM.collectionView.itemsEdited[u]);
            }

            for (var i = 0; i < $scope.flexM.collectionView.itemsAdded.length; i++) {
                if(orgnFg === "STORE") {$scope.flexM.collectionView.itemsAdded[i].posNo = $scope.posNoCombo.selectedValue;}
                $scope.flexM.collectionView.itemsAdded[i].tuClsType = $("#hdTuClsType").val();
                $scope.flexM.collectionView.itemsAdded[i].tuClsCd = $scope.selTuClsCd;
                $scope.flexM.collectionView.itemsAdded[i].status = 'I';
                if(pageFg === '1'){
                    $scope.flexM.collectionView.itemsAdded[i].pageFg = '1';
                    $scope.flexM.collectionView.itemsAdded[i].storeCd = $("#kioskKeyMapSelectStoreCd").val();
                }
                params.push($scope.flexM.collectionView.itemsAdded[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/kioskKeyMap/kioskKeyMap/saveKioskCategoryM.sb', params, function() {

                // 키맵 grid 초기화
                var wjGridKeyMap = wijmo.Control.getControl("#wjGridKeyMap");
                while(wjGridKeyMap.rows.length > 0){
                    wjGridKeyMap.rows.removeAt(wjGridKeyMap.rows.length-1);
                }
                $("#spanTuKeyCls").text('');

                // 상품 grid 초기화
                var wjGridProd = wijmo.Control.getControl("#wjGridProd");
                while(wjGridProd.rows.length > 0){
                    wjGridProd.rows.removeAt(wjGridProd.rows.length-1);
                }

                // 상품 grid paging 초기화(숨기기.. 아예 없애는거 모름..)
                var kioskProdCtrlPager = document.getElementById('kioskProdCtrlPager');
                kioskProdCtrlPager.style.visibility='hidden'

                // 카테고리분류 재조회
                $scope.searchCategoryClsM();
            });
        });
    };

}]);


// 키오스크 키맵
app.controller('kioskKeyMapCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyMapCtrl', $scope, $http, true));

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

    $scope.$on("kioskKeyMapCtrl", function(event, data) {

        if(data !== undefined && !isEmptyObject(data)) {

            // 카테고리 관련 데이터 셋팅
            $("#spanTuKeyCls").text("[" + data.tuClsCd + "] " + data.tuClsNm)
            $("#hdTuClsCd").val(data.tuClsCd);
        }

        // 키맵 조회
        $scope.searchKeyMap();

    });

    // 키맵 조회
    $scope.searchKeyMap = function () {

        var params = {};
        if(orgnFg === "STORE") {params.posNo = $("#hdPosNo").val();}
        if(pageFg === '1'){
            params.pageFg = '1';
            params.storeCd = $("#kioskKeyMapSelectStoreCd").val();
        }
        params.tuClsType = $("#hdTuClsType").val();
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
                divBtnKeyMap.style.visibility='visible'

                // 카테고리(분류)가 정상조회 되면 상품관련 버튼 보이도록
                var divBtnProd = document.getElementById('divBtnProd');
                divBtnProd.style.visibility='visible'

                // paging 영역 보이도록
                var kioskProdCtrlPager = document.getElementById('kioskProdCtrlPager');
                kioskProdCtrlPager.style.visibility='visible'

                // 상품 조회
                var kioskProdGrid = agrid.getScope("kioskProdCtrl");
                kioskProdGrid._pageView('kioskProdCtrl', 1);
            } else {
                // 카테고리(분류)가 정상조회 되면 키맵관련 버튼 보이도록
                var divBtnKeyMap = document.getElementById('divBtnKeyMap');
                divBtnKeyMap.style.visibility='hidden'

                // 카테고리(분류)가 정상조회 되면 상품관련 버튼 보이도록
                var divBtnProd = document.getElementById('divBtnProd');
                divBtnProd.style.visibility='hidden'

                // paging 영역 보이도록
                var kioskProdCtrlPager = document.getElementById('kioskProdCtrlPager');
                kioskProdCtrlPager.style.visibility='hidden'
            }
        }, false);
    }

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
    }

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
    }

    // 키맵 삭제
    $scope.delRowKeyMap = function () {
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
                $scope.flex.collectionView.removeAt(i);
            }
        }
    }

    // 키맵 저장
    $scope.saveKeyMap = function () {
        var msg = messages["cmm.choo.save"] + '<br/>(' + $("#spanTuKeyCls").text() + ")";

        if(orgnFg === "STORE" && (hqOfficeCd === 'A0001' || hqOfficeCd === 'DS021' || hqOfficeCd === 'DS034' || hqOfficeCd === 'DS062')){
            msg += "<br>" + messages["kioskKeyMap.keyMapStoreRegAll.msg"] // 저장 시 전체 키오스크의 정보가 변경됩니다.
        }

        $scope._popConfirm(msg, function () {
            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = [];

            for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
                if (orgnFg === "STORE") {
                    $scope.flex.collectionView.itemsRemoved[d].posNo = $("#hdPosNo").val();
                }
                $scope.flex.collectionView.itemsRemoved[d].tuClsType = $("#hdTuClsType").val();
                $scope.flex.collectionView.itemsRemoved[d].tuClsCd = $("#hdTuClsCd").val();
                $scope.flex.collectionView.itemsRemoved[d].status = 'D';
                if(pageFg === '1'){
                    $scope.flex.collectionView.itemsRemoved[d].pageFg = '1';
                    $scope.flex.collectionView.itemsRemoved[d].storeCd = $("#kioskKeyMapSelectStoreCd").val();
                }
                params.push($scope.flex.collectionView.itemsRemoved[d]);
            }

            // indexNo 재설정
            // var editItems = [];
            // for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
            //     if($scope.flex.collectionView.items[s].indexNo !== (s+1)) {
            //         if (isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
            //             editItems.push($scope.flex.collectionView.items[s]);
            //         }
            //     }
            // }
            //
            // for (var s = 0; s < editItems.length; s++) {
            //     editItems[s].indexNo = (s + 1);
            //     $scope.flex.collectionView.editItem(editItems[s]);
            //     editItems[s].status = "U";
            //     $scope.flex.collectionView.commitEdit();
            // }
            for(var s = 0, num = 1; s < $scope.flex.rows.length; s++, num++) {
                if ($scope.flex.collectionView.items[s].indexNo !== num) {
                    $scope.flex.collectionView.items[s].indexNo = num;
                    $scope.flex.collectionView.editItem($scope.flex.collectionView.items[s]);
                    $scope.flex.collectionView.items[s].status = "U";
                    $scope.flex.collectionView.commitEdit();
                }
            }

            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                if (orgnFg === "STORE") {
                    $scope.flex.collectionView.itemsEdited[u].posNo = $("#hdPosNo").val();
                }
                $scope.flex.collectionView.itemsEdited[u].tuClsType = $("#hdTuClsType").val();
                $scope.flex.collectionView.itemsEdited[u].tuClsCd = $("#hdTuClsCd").val();
                $scope.flex.collectionView.itemsEdited[u].status = 'U';
                if(pageFg === '1'){
                    $scope.flex.collectionView.itemsEdited[u].pageFg = '1';
                    $scope.flex.collectionView.itemsEdited[u].storeCd = $("#kioskKeyMapSelectStoreCd").val();
                }
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/kioskKeyMap/kioskKeyMap/updateKioskKeyMap.sb', params, function () {

                // 키맵 재조회
                $scope.searchKeyMap();

            });
        });
    }

}]);

// 키오스크 상품
app.controller('kioskProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskProdCtrl', $scope, $http, true));

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

    $scope.$on("kioskProdCtrl", function(event, data) {
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
                if(pageFg === '1'){
                    params.pageFg = '1';
                    params.storeCd = $("#kioskKeyMapSelectStoreCd").val();
                }
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
    }

    // 상품 조회
    $scope.searchProd = function () {

        var params = {};
        if(orgnFg === "STORE") {params.posNo = $("#hdPosNo").val();}
        if(pageFg === '1'){
            params.pageFg = '1';
            params.storeCd = $("#kioskKeyMapSelectStoreCd").val();
        }
        params.tuClsType = $("#hdTuClsType").val();
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

        $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getKioskProdList.sb", params, function() {

            // 공백 상품 일단 주석 처리_(2020.09.14)
            /*
            // 공백 상품 추가
            var params = {};

            params.gChk = false;
            params.prodCd = 'BLK_PROD_CD_X';
            params.prodNm = '이 상품은 화면에 표시되지 않습니다.';
            params.saleUprc = '-';
            params.regYn = '-';

            $scope._addRow(params, 2);

            // 공백 상품은 항상 맨 위로 표기
            var blankIndex = $scope.flex.collectionView.items.length - 1;
            var tmpItem = $scope.flex.collectionView.items[blankIndex];

            for (var i = blankIndex - 1; i >=  0; i--) {
                $scope.flex.collectionView.items[i+1] = $scope.flex.collectionView.items[i];
                $scope.flex.collectionView.commitEdit();
                $scope.flex.collectionView.refresh();
            }

            $scope.flex.collectionView.items[0] = tmpItem;
            $scope.flex.collectionView.commitEdit();
            $scope.flex.collectionView.refresh();
            */

        }, false);

    }

    // 키맵 등록
    $scope.regProd = function () {
        var msg = messages["cmm.choo.save"];

        if(orgnFg === "STORE" && (hqOfficeCd === 'A0001' || hqOfficeCd === 'DS021' || hqOfficeCd === 'DS034' || hqOfficeCd === 'DS062')){
            msg += "<br>" + messages["kioskKeyMap.keyMapStoreRegAll.msg"] // 저장 시 전체 키오스크의 정보가 변경됩니다.
        }

        $scope._popConfirm(msg, function () {

            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    if(orgnFg === "STORE") {$scope.flex.collectionView.items[i].posNo = $("#hdPosNo").val();}
                    if(pageFg === '1'){
                        $scope.flex.collectionView.items[i].pageFg = '1';
                        $scope.flex.collectionView.items[i].storeCd = $("#kioskKeyMapSelectStoreCd").val();
                    }
                    $scope.flex.collectionView.items[i].tuClsType = $("#hdTuClsType").val();
                    $scope.flex.collectionView.items[i].tuClsCd = $("#hdTuClsCd").val();
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/kioskKeyMap/kioskKeyMap/saveKioskKeyMap.sb", params, function(){

                // 상품 재조회
                $scope.searchProd()

                // 키맵 재조회
                var kioskKeyMapGrid = agrid.getScope("kioskKeyMapCtrl");
                kioskKeyMapGrid._pageView('kioskKeyMapCtrl', 1);

            });
        });
    }
}]);