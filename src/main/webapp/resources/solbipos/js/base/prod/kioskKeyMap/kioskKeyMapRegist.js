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

// 키오스크 카테고리(분류)
app.controller('kioskKeyMapRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyMapRegistCtrl', $scope, $http, true));

    $scope._setComboData("posNo", kioskPosList); // 키오스크용 포스 목록
    $scope._setComboData("tuClsType", kioskTuClsTypeList); // 키오스크용 키맵그룹 목록

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
                        $scope._broadcast('kioskKeyMapCtrl', selectedRow);
                        event.preventDefault();
                    }
                }
            }
        });
    };

    $scope.$on("kioskKeyMapRegistCtrl", function(event, data) {
        $scope.btnSearchCls();
        event.preventDefault();

    });

    // 키오스크 카테고리(분류) 조회
    $scope.btnSearchCls = function(){

        // 초기화
        $scope.reset();

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
            $("#lbTuClsType").text("POS번호 : " + $scope.posNoCombo.selectedValue + " / 키맵그룹 : " + $scope.tuClsTypeCombo.selectedValue);
        }else{
            $("#lbTuClsType").text("키맵그룹 : " + $scope.tuClsTypeCombo.selectedValue);
        }

        var params = {};
        if(orgnFg === "STORE") {params.posNo = $scope.posNoCombo.selectedValue;}
        params.tuClsType = $scope.tuClsTypeCombo.selectedValue;

        $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getKioskCategory.sb", params, function() {

            // 카테고리(분류)가 정상조회 되면 관련 버튼 보이도록
            var divBtnCls = document.getElementById('divBtnCls');
            divBtnCls.style.visibility='visible'

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
        var divBtnKeyMap = document.getElementById('divBtnKeyMap');
        var divBtnProd = document.getElementById('divBtnProd');
        divBtnCls.style.visibility='hidden';
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
            //if(params[m].status !== 'D') {
                if(  $scope.flex.collectionView.items[m].tuClsNm === null  || $scope.flex.collectionView.items[m].tuClsNm === '') {
                    $scope._popMsg(messages['kioskKeyMap.require.category.msg']); // 카테고리명을 입력해주세요.
                    return;
                }
            //}
        }

        // 파라미터 설정
        var params = [];

        for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
            if(orgnFg === "STORE") {$scope.flex.collectionView.itemsRemoved[d].posNo = $scope.posNoCombo.selectedValue;}
            $scope.flex.collectionView.itemsRemoved[d].tuClsType = $scope.tuClsTypeCombo.selectedValue;
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
            if(orgnFg === "STORE") {$scope.flex.collectionView.itemsEdited[u].posNo = $scope.posNoCombo.selectedValue;}
            $scope.flex.collectionView.itemsEdited[u].tuClsType = $scope.tuClsTypeCombo.selectedValue;
            $scope.flex.collectionView.itemsEdited[u].status = 'U';
            params.push($scope.flex.collectionView.itemsEdited[u]);
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            if(orgnFg === "STORE") {$scope.flex.collectionView.itemsAdded[i].posNo = $scope.posNoCombo.selectedValue;}
            $scope.flex.collectionView.itemsAdded[i].tuClsType = $scope.tuClsTypeCombo.selectedValue;
            $scope.flex.collectionView.itemsAdded[i].status = 'I';
            params.push($scope.flex.collectionView.itemsAdded[i]);
        }

        // 카테고리(분류)를 모두 삭제하는지 파악하기 위해
        var gridLength = $scope.flex.collectionView.items.length;

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/kioskKeyMap/kioskKeyMap/saveKioskCategory.sb', params, function() {

            if(gridLength > 0 ){
                // 카테고리분류 재조회
                $scope.btnSearchCls();
            }else{
                // 키맵그룹 dropdown 재조회
                $scope.setTuClsDropdownList("F");
            }
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

        // 신규그룹을 생성하시겠습니까?
        $scope._popConfirm(messages["kioskKeyMap.tuClsTypeAdd.msg"], function() {

            // 파라미터 설정
            var params = {};
            if(orgnFg === "STORE") {params.posNo = $scope.posNoCombo.selectedValue;}
            params.tuClsNm = "기본";
            params.indexNo = "1";

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

        // '01' 그룹을 복사하여 새 그룹을 생성하시겠습니까?
        $scope._popConfirm( "'" + $scope.tuClsTypeCombo.selectedValue + "' " + messages["kioskKeyMap.tuClsTypeCopy.msg"], function() {

            // 파라미터 설정
            var params = {};
            if(orgnFg === "STORE") {params.posNo = $scope.posNoCombo.selectedValue;}
            params.tuClsType = $scope.tuClsTypeCombo.selectedValue;

            $scope._postJSONSave.withPopUp("/base/prod/kioskKeyMap/kioskKeyMap/copyKioskTuClsType.sb", params, function (response) {

                // 키맵그룹 dropdown 재조회
                $scope.setTuClsDropdownList("L");
            });
        });
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
        if(orgnFg === "STORE") {params.posNo = $scope.posNoCombo.selectedValue;}

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

                    $scope._setComboData("tuClsType", comboArray);
                    if(orgnFg === "HQ") {
                        $scope._setComboData("applyTuClsType", comboArray);
                        $scope._setComboData("envTuClsType", comboArray);
                    }
                    if(orgnFg === "STORE"){
                        $scope._setComboData("envStoreTuClsType", comboArray);
                    }

                    if(type === "F"){
                        newGrp = 0;
                    }else if (type === "L"){
                        newGrp = list.length - 1;
                    }
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        }).then(function () {
            $timeout(function () {

                // 신규그룹추가 후, 새 그룹으로 dropdownLIst 와 grid 셋팅
                $scope.setNewTuClsType(newGrp);

            }, 10);
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
        params.tuClsType = $("#hdTuClsType").val();
        params.tuClsCd = $("#hdTuClsCd").val();

        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getKioskKeyMap.sb", params, function() {

            // 카테고리(분류)가 정상조회 되면 키맵관련 버튼 보이도록
            var divBtnKeyMap = document.getElementById('divBtnKeyMap');
            divBtnKeyMap.style.visibility='visible'

            // 카테고리(분류)가 정상조회 되면 상품관련 버튼 보이도록
            var divBtnProd = document.getElementById('divBtnProd');
            divBtnProd.style.visibility='visible'

            // 상품 조회
            var kioskProdGrid = agrid.getScope("kioskProdCtrl");
            kioskProdGrid._pageView('kioskProdCtrl', 1);

            // paging 영역 보이도록
            var kioskProdCtrlPager = document.getElementById('kioskProdCtrlPager');
            kioskProdCtrlPager.style.visibility='visible'
            
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
        $scope.flex.collectionView.commitEdit();

        // 파라미터 설정
        var params = [];

        for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
            if(orgnFg === "STORE") {$scope.flex.collectionView.itemsRemoved[d].posNo = $("#hdPosNo").val();}
            $scope.flex.collectionView.itemsRemoved[d].tuClsType = $("#hdTuClsType").val();
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
            if(orgnFg === "STORE") {$scope.flex.collectionView.itemsEdited[u].posNo = $("#hdPosNo").val();}
            $scope.flex.collectionView.itemsEdited[u].tuClsType = $("#hdTuClsType").val();
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

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                if(orgnFg === "STORE") {$scope.flex.collectionView.items[i].posNo = $("#hdPosNo").val();}
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
    }
}]);