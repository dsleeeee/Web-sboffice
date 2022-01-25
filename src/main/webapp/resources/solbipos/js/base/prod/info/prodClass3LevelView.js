/****************************************************************
 *
 * 파일명 : prodClass3LevelView.js
 * 설  명 : 상품분류정보관리(3단계) 화면 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.24     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// 대분류
app.controller('prodClassLevel1Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('prodClassLevel1Ctrl', $scope, $http, false));

    // 분류 생성 여부에 따른 버튼 설정
    $("#spLevel1").css("display", "none");
    $("#spLevel2").css("display", "none");
    $("#spLevel3").css("display", "none");
    $("#divBtnLevel1").css("display", "none"); // 대분류 추가/삭제/저장 버튼
    $("#divBtnLevel2").css("display", "none"); // 중분류 추가/삭제/저장 버튼
    $("#divBtnLevel3").css("display", "none"); // 소분류 추가/삭제/저장 버튼

    if(hqOfficeCd == "00000") { // 단독매장
        $("#divBtnLevel1").css("display", "block");
        $("#divBtnLevel2").css("display", "block");
        $("#divBtnLevel3").css("display", "block");

    } else { // 프랜차이즈 본사,매장
        if((prodAuthEnvstVal== "ALL") || (orgnFg === 'HQ' && prodAuthEnvstVal== "HQ") || (orgnFg === 'STORE' && prodAuthEnvstVal== "STORE")) {
            $("#divBtnLevel1").css("display", "block");
            $("#divBtnLevel2").css("display", "block");
            $("#divBtnLevel3").css("display", "block");
        }else{
            $("#spLevel1").css("display", "block");
            $("#spLevel2").css("display", "block");
            $("#spLevel3").css("display", "block");
        }
    }

    $scope.initGrid = function (s, e) {

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'prodClassCd') {
                    
                    // 기존 등록 분류는 분류코드 수정불가
                    if (item.newRowYn !== 'Y' && item.prodClassCd !== '' && item.prodClassCd !== '자동채번') {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }

                    // 새로 등록하는 분류중 '자동채번' 분류는 분류코드 입력불가
                    if(item.newRowYn === 'Y' && item.prodClassCd === '자동채번'){
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }
            }
        });

        // 카테고리 코드 클릭 시, 키맵과 상품 조회
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "prodClassCd") {

                    // 기존 등록 분류는 분류코드 클릭시 하위 분류 조회
                    if(selectedRow.newRowYn !== 'Y' && selectedRow.prodClassCd !== '' && selectedRow.prodClassCd !== '자동채번') {

                        // 현재 메뉴 선택 경로
                        $("#lblLevel1").text("[" + selectedRow.prodClassCd + "]" + selectedRow.prodClassNm);
                        $("#hdLevel1").val(selectedRow.prodClassCd);

                        // 중분류 조회
                        $scope._broadcast('prodClassLevel2Ctrl', selectedRow);

                        // 소분류 그리드 초기화
                        var gridScope3 = agrid.getScope('prodClassLevel3Ctrl');
                        gridScope3.dtlGridDefault();

                        // 선택한 중분류 정보 라벨 초기화
                        $("#lblLevel2").text("");
                        $("#hdLevel2").val("");

                        event.preventDefault();
                    }
                }
            }
        });

        // 대분류 조회
        $scope.searchLevel1();
    };

    $scope.$on("prodClassLevel1Ctrl", function(event, data) {

        // 대분류 조회
        $scope.searchLevel1();
        event.preventDefault();
    });

    // 대분류 조회
    $scope.searchLevel1 = function(){

        var params = {};
        params.clsLevelCd = "1";

        $scope._inquirySub("/base/prod/info/class/getProdClass.sb", params, function() {

            // 프랜차이즈 매장은 본사에서 등록한 분류 수정불가
            if(orgnFg == "STORE" && hqOfficeCd != "00000") {

                var grid = wijmo.Control.getControl("#wjGridProdClassLevel1");
                var rows = grid.rows;

                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    var item = $scope.flex.collectionView.items[i];
                    if(item.prodClassCd < 80001){
                        item.gChk = false;
                        rows[i].isReadOnly = true;
                    }
                }
            }
        }, false);
    };

    // 대분류 추가
    $scope.addLevel1 = function () {

        var params = {};
        var focusLine = 0;

        // 본사권한이면서, 상품분류코드직접입력여부 값이 있는 경우만 상품분류코드 수동입력 가능
        if(orgnFg === "HQ" && prodClassCdInputType !== ''){
            params.prodClassCd = '';
            focusLine = 1;
        }else{
            params.prodClassCd = '자동채번';
            focusLine = 2;
        }

        params.gChk = true;
        params.prodClassNm = '';
        params.newRowYn = 'Y'; // 새로 입력한 분류인지 확인을 위해 추가

        // 행추가
        $scope._addRow(params, focusLine);
    };

    // 대분류 삭제
    $scope.delLevel1 = function () {

        // 1. 선택 분류가 있는지 체크
        var chkCount = 0;
        for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
            var item =  $scope.flex.collectionView.items[i];
            if(item.gChk) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["info.delProdClassChk.msg"]); // 삭제할 분류의 체크박스를 선택하세요.
            return false;
        }else{
            // 선택 분류 중 신규추가분류를 체크한 경우, grid에서 삭제
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];
                if(item.gChk){
                    if(item.newRowYn === 'Y'){
                        $scope.flex.collectionView.removeAt(i);
                    }
                }
            }
        }

        // 2. 다시 선택 분류 체크(신규추가분류를 삭제하고 남은 분류가 있는지 확인)
        chkCount = 0;
        for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
            var item =  $scope.flex.collectionView.items[i];
            if(item.gChk) chkCount++;
        }

        if(chkCount === 0){
            return false;
        }else{
            // 3. 분류가 있는경우, 분류에 매핑된 상품 또는 자식분류가 있는지 체크
            for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
                var item = $scope.flex.collectionView.items[i];
                if(item.gChk){

                    // 프랜차이즈 매장권한 일때 코드값이 80001 이하면 본사이므로 삭제불가
                    if(orgnFg == "STORE") {
                        if (hqOfficeCd != "00000") {
                            if (item.prodClassCd < 80001) {
                                $scope._popMsg(messages["info.prodClassCdHqDel"]); // 본사에서 등록한 상품분류는 삭제 불가능합니다.
                                return;
                            }
                        }
                    }

                    if(parseInt(nvl(item.hqChildClassCnt, 0)) !== 0 || parseInt(nvl(item.hqProdCnt, 0)) !== 0 ||
                        parseInt(nvl(item.msChildClassCnt, 0)) !== 0 || parseInt(nvl(item.msProdCnt, 0)) !== 0){
                        $scope._popMsg(messages["info.delProdClassDenied.msg"]);// 상품 또는 하위분류가 등록된 분류는 삭제할 수 없습니다.
                        return;
                    }
                }
            }
        }

        var params = new Array();

        // 대분류를 삭제하시겠습니까?
        $scope._popConfirm(messages["info.prodClassLevel1"] + "를 " + messages["cmm.choo.delete"], function() {

            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk === true) {
                    var obj = {};
                    obj.status = "D";
                    obj.prodClassCd = item.prodClassCd;
                    params.push(obj);
                }
            }
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/info/class/saveProdClass.sb", params, function () {

                // 대분류 조회
                $scope.searchLevel1();

                // 중분류 그리드 초기화
                var gridScope2 = agrid.getScope('prodClassLevel2Ctrl');
                gridScope2.dtlGridDefault();

                // 소분류 그리드 초기화
                var gridScope3 = agrid.getScope('prodClassLevel3Ctrl');
                gridScope3.dtlGridDefault();

                // 선택한 대/중분류 정보 라벨 초기화
                $("#lblLevel1").text("");
                $("#lblLevel2").text("");
                $("#hdLevel1").val("");
                $("#hdLevel2").val("");

            });
        });
    };

    // 대분류 저장
    $scope.saveLevel1 = function () {

        $scope.flex.collectionView.commitEdit();

        // 분류코드, 분류명 앞뒤 공백 및 엔터값 제거
        if(orgnFg === "HQ" && prodClassCdInputType !== ''){
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if($scope.flex.collectionView.itemsAdded[i].prodClassCd !== null && $scope.flex.collectionView.itemsAdded[i].prodClassCd !== '') {
                    $scope.flex.collectionView.itemsAdded[i].prodClassCd = $scope.flex.collectionView.itemsAdded[i].prodClassCd.trim().removeEnter();
                }
            }
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            if($scope.flex.collectionView.itemsAdded[i].prodClassNm !== null && $scope.flex.collectionView.itemsAdded[i].prodClassNm !== '') {
                $scope.flex.collectionView.itemsAdded[i].prodClassNm = $scope.flex.collectionView.itemsAdded[i].prodClassNm.trim().removeEnter();
            }
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].prodClassNm !== null && $scope.flex.collectionView.itemsEdited[i].prodClassNm !== '') {
                $scope.flex.collectionView.itemsEdited[i].prodClassNm = $scope.flex.collectionView.itemsEdited[i].prodClassNm.trim().removeEnter();
            }
        }

        var strProdClassCd = ""; // 상품분류코드 중복체크를 위함.

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {

            if(orgnFg === "HQ" && prodClassCdInputType !== ''){
                if($scope.flex.collectionView.items[i].prodClassCd === null || $scope.flex.collectionView.items[i].prodClassCd === ''){
                    $scope._popMsg(messages['info.saveProdClassCdChk.msg']); // 분류코드를 입력하세요.
                    return;
                }

                if($scope.flex.collectionView.items[i].prodClassCd.length > 5){
                    $scope._popMsg(messages['info.prodClassCdLength.msg']); // 분류코드는 최대 5자리까지 입력할 수 있습니다.
                    return;
                }
            }

            if($scope.flex.collectionView.items[i].prodClassNm === null || $scope.flex.collectionView.items[i].prodClassNm === '') {
                $scope._popMsg(messages['info.saveProdClassChk.msg']); // 분류명을 입력하세요.
                return;
            }

            /*if($scope.flex.collectionView.items[i].prodClassNm.length > 15){
                $scope._popMsg(messages['info.prodClassNmLength.msg']); // 분류명은 최대 15자리까지 입력할 수 있습니다.
                return;
            }*/
        }

        // 분류명 길이체크 따로 함(기존에 등록된 분류명중 이미 15자리 넘는 분류가 있어, 새로 추가하거나 수정한 분류만 체크하는걸로 변경)
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            if($scope.flex.collectionView.itemsAdded[i].prodClassNm.length > 15){
                $scope._popMsg(messages['info.prodClassNmLength.msg']); // 분류명은 최대 15자리까지 입력할 수 있습니다.
                return;
            }
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].prodClassNm.length > 15){
                $scope._popMsg(messages['info.prodClassNmLength.msg']); // 분류명은 최대 15자리까지 입력할 수 있습니다.
                return;
            }
        }
        
        // 상품코드 수동입력인 경우, 중복체크 필요
        if(orgnFg === "HQ" && prodClassCdInputType !== '') {

            var arr = [];
            var arr2 = [];
            var inputProdClassCd = "";

            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if ($scope.flex.collectionView.itemsAdded[i].newRowYn === 'Y') {
                    arr[i] = $scope.flex.collectionView.itemsAdded[i].prodClassCd;
                    strProdClassCd += $scope.flex.collectionView.itemsAdded[i].prodClassCd + ",";
                }
            }
            
            // 1. 중복체크 : 새로 입력한 분류코드 중 중복코드가 있는지 체크
            arr.forEach(function(element){
                if(!arr2.includes(element)){
                    arr2.push(element);
                }else{
                    inputProdClassCd += element + ",";
                }
            });

            if(inputProdClassCd !== ""){
                $scope._popMsg(messages["info.prodClassCdDuplicate.msg"] + "<br>(" + inputProdClassCd.substr(0, inputProdClassCd.length - 1) + ")"); // 중복된 분류코드 입니다.
                return;
            }
        }

        // 상품분류명수정만 했거나, 분류코드 채번방식이 자동인경우
        if(strProdClassCd === "") {

            // 저장
            $scope.saveProdClassLevel1();

        }else{

            // 2. 중복체크 : 이미 등록한 분류코드 중 중복코드가 있는지 체크
            var params = {};
            params.prodClassCd = strProdClassCd.substr(0, strProdClassCd.length - 1);

            $scope._postJSONQuery.withOutPopUp( "/base/prod/info/getChkProdClassCd.sb", params, function(response){
                var list = response.data.data.list;

                if(list.length > 0) { //  중복

                    var duplicateProdClassCd = "";
                    for(var i=0; i< list.length; i++){
                        duplicateProdClassCd += list[i].prodClassCd + ",";
                    }

                    $scope._popMsg(messages["info.prodClassCdDuplicate.msg"] + "<br>(" + duplicateProdClassCd.substr(0, duplicateProdClassCd.length - 1) + ")"); // 중복된 분류코드 입니다.
                    return;

                }else{

                    // 저장
                    $scope.saveProdClassLevel1();
                }
            });
        }

        // 프랜차이즈 매장권한 일때 코드값이 80001 이하면 본사이므로 수정불가
        /*if (orgnFg == "STORE") {
            if (hqOfficeCd != "00000") {
                for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                    var item = $scope.flex.collectionView.itemsEdited[i];
                    if(item.prodClassCd !== "자동채번") {
                        if (item.prodClassCd < 80001) {
                            $scope._popMsg(messages["info.prodClassCdHqSave"]); // 본사에서 등록한 상품분류는 수정 불가능합니다.
                            return;
                        }
                    }
                }
            }
        }*/

    };

    // 대분류 실제 저장
    $scope.saveProdClassLevel1 = function(){

        var params = new Array();

        // 대분류를 저장하시겠습니까?
        $scope._popConfirm(messages["info.prodClassLevel1"] + "를 " + messages["cmm.choo.save"], function() {

            // 그룹추가
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                var item = $scope.flex.collectionView.itemsAdded[i];
                var obj = {};

                if(item.newRowYn === "Y") {
                    if(orgnFg === "HQ" && prodClassCdInputType !== '' && item.prodClassCd !== "자동채번"){
                     obj.prodClassCd = item.prodClassCd.trim().removeEnter();
                    }
                    obj.status = "I";
                    obj.prodClassNm = item.prodClassNm.trim().removeEnter();
                    obj.pProdClassCd = "00000";
                    params.push(obj);
                }
            }

            // 그룹수정
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                var item = $scope.flex.collectionView.itemsEdited[i];
                var obj = {};

                // 프랜차이즈매장은 80001번 이상인 분류만 저장되도록 처리
                if (orgnFg === "STORE" && hqOfficeCd !== "00000") {
                    if(item.prodClassCd !== "자동채번" && item.prodClassCd !== ""){
                        if(item.prodClassCd > 80000) {
                            obj.status = "U";
                            obj.prodClassCd = item.prodClassCd;
                            obj.prodClassNm = item.prodClassNm.trim().removeEnter();
                            obj.pProdClassCd = "00000";
                            params.push(obj);
                        }
                    }
                }

                if (orgnFg === "HQ" || (orgnFg === "STORE" && hqOfficeCd === "00000")) {
                    if (item.prodClassCd !== "자동채번" && item.prodClassCd !== ""){
                        obj.status = "U";
                        obj.prodClassCd = item.prodClassCd;
                        obj.prodClassNm = item.prodClassNm.trim().removeEnter();
                        obj.pProdClassCd = "00000";
                        params.push(obj);
                    }
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/info/class/saveProdClass.sb", params, function () {

                // 대분류 조회
                $scope.searchLevel1();

                // 중분류 그리드 초기화
                var gridScope2 = agrid.getScope('prodClassLevel2Ctrl');
                gridScope2.dtlGridDefault();

                // 소분류 그리드 초기화
                var gridScope3 = agrid.getScope('prodClassLevel3Ctrl');
                gridScope3.dtlGridDefault();

                // 선택한 대/중분류 정보 라벨 초기화
                $("#lblLevel1").text("");
                $("#lblLevel2").text("");
                $("#hdLevel1").val("");
                $("#hdLevel2").val("");

            });
        });

    };

    // 조회 버튼
    $scope.btnSearch = function () {

        // 대분류 조회
        $scope.searchLevel1();

        // 중분류 그리드 초기화
        var gridScope2 = agrid.getScope('prodClassLevel2Ctrl');
        gridScope2.dtlGridDefault();

        // 소분류 그리드 초기화
        var gridScope3 = agrid.getScope('prodClassLevel3Ctrl');
        gridScope3.dtlGridDefault();

        // 선택한 대/중분류 정보 라벨 초기화
        $("#lblLevel1").text("");
        $("#lblLevel2").text("");
        $("#hdLevel1").val("");
        $("#hdLevel2").val("");

    };

}]);

// 중분류
app.controller('prodClassLevel2Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('prodClassLevel2Ctrl', $scope, $http, false));

    $scope.initGrid = function (s, e) {

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'prodClassCd') {

                    // 기존 등록 분류는 분류코드 수정불가
                    if (item.newRowYn !== 'Y' && item.prodClassCd !== '' && item.prodClassCd !== '자동채번') {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }

                    // 새로 등록하는 분류중 '자동채번' 분류는 분류코드 입력불가
                    if(item.newRowYn === 'Y' && item.prodClassCd === '자동채번'){
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }
            }
        });

        // 카테고리 코드 클릭 시, 키맵과 상품 조회
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "prodClassCd") {

                    // 기존 등록 분류는 분류코드 클릭시 하위 분류 조회
                    if(selectedRow.newRowYn !== 'Y' && selectedRow.prodClassCd !== '' && selectedRow.prodClassCd !== '자동채번') {

                        // 현재 메뉴 선택 경로
                        $("#lblLevel2").text(" ▶ [" + selectedRow.prodClassCd + "]" + selectedRow.prodClassNm);
                        $("#hdLevel2").val(selectedRow.prodClassCd);

                        // 소분류 조회
                        $scope._broadcast('prodClassLevel3Ctrl', selectedRow);
                        event.preventDefault();
                    }
                }
            }
        });
    };

    $scope.$on("prodClassLevel2Ctrl", function(event, data) {

        // 중분류 조회
        $scope.searchLevel2(data);
        event.preventDefault();
    });

    // 중분류 조회
    $scope.searchLevel2 = function(data){

        var params = {};
        params.clsLevelCd = "2";
        params.pProdClassCd = $("#hdLevel1").val();

        $scope._inquirySub("/base/prod/info/class/getProdClass.sb", params, function() {

            // 프랜차이즈 매장은 본사에서 등록한 분류 수정불가
            if(orgnFg == "STORE" && hqOfficeCd != "00000") {

                var grid = wijmo.Control.getControl("#wjGridProdClassLevel2");
                var rows = grid.rows;

                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    var item = $scope.flex.collectionView.items[i];
                    if(item.prodClassCd < 80001){
                        item.gChk = false;
                        rows[i].isReadOnly = true;
                    }
                }
            }
        }, false);
    };

    // 중분류 추가
    $scope.addLevel2 = function () {

        if($("#hdLevel1").val() === "null" || $("#hdLevel1").val() === ""){
            $scope._popMsg(messages["info.selectLevel1.msg"]); // 대분류를 선택하세요.
            return;
        }

        var params = {};
        var focusLine = 0;

        // 본사권한이면서, 상품분류코드직접입력여부 값이 있는 경우만 상품분류코드 수동입력 가능
        if(orgnFg === "HQ" && prodClassCdInputType !== ''){
            params.prodClassCd = '';
            focusLine = 1;
        }else{
            params.prodClassCd = '자동채번';
            focusLine = 2;
        }

        params.gChk = true;
        params.prodClassNm = '';
        params.newRowYn = 'Y'; // 새로 입력한 분류인지 확인을 위해 추가

        // 행추가
        $scope._addRow(params, focusLine);
    };

    // 중분류 삭제
    $scope.delLevel2 = function () {

        if($("#hdLevel1").val() === "null" || $("#hdLevel1").val() === ""){
            $scope._popMsg(messages["info.selectLevel1.msg"]); // 대분류를 선택하세요.
            return;
        }

        // 1. 선택 분류가 있는지 체크
        var chkCount = 0;
        for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
            var item =  $scope.flex.collectionView.items[i];
            if(item.gChk) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["info.delProdClassChk.msg"]); // 삭제할 분류의 체크박스를 선택하세요.
            return false;
        }else{
            // 선택 분류 중 신규추가분류를 체크한 경우, grid에서 삭제
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];
                if(item.gChk){
                    if(item.newRowYn === 'Y'){
                        $scope.flex.collectionView.removeAt(i);
                    }
                }
            }
        }

        // 2. 다시 선택 분류 체크(신규추가분류를 삭제하고 남은 분류가 있는지 확인)
        chkCount = 0;
        for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
            var item =  $scope.flex.collectionView.items[i];
            if(item.gChk) chkCount++;
        }

        if(chkCount === 0){
            return false;
        }else{
            // 3. 분류가 있는경우, 분류에 매핑된 상품 또는 자식분류가 있는지 체크
            for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
                var item = $scope.flex.collectionView.items[i];
                if(item.gChk){

                    // 프랜차이즈 매장권한 일때 코드값이 80001 이하면 본사이므로 삭제불가
                    if(orgnFg == "STORE") {
                        if (hqOfficeCd != "00000") {
                            if (item.prodClassCd < 80001) {
                                $scope._popMsg(messages["info.prodClassCdHqDel"]); // 본사에서 등록한 상품분류는 삭제 불가능합니다.
                                return;
                            }
                        }
                    }

                    if(parseInt(nvl(item.hqChildClassCnt, 0)) !== 0 || parseInt(nvl(item.hqProdCnt, 0)) !== 0 ||
                        parseInt(nvl(item.msChildClassCnt, 0)) !== 0 || parseInt(nvl(item.msProdCnt, 0)) !== 0){
                        $scope._popMsg(messages["info.delProdClassDenied.msg"]);// 상품 또는 하위분류가 등록된 분류는 삭제할 수 없습니다.
                        return;
                    }
                }
            }
        }

        var params = new Array();

        // 중분류를 삭제하시겠습니까?
        $scope._popConfirm(messages["info.prodClassLevel2"] + "를 " + messages["cmm.choo.delete"], function() {

            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk === true) {
                    var obj = {};
                    obj.status = "D";
                    obj.prodClassCd = item.prodClassCd;
                    params.push(obj);
                }
            }
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/info/class/saveProdClass.sb", params, function () {

                // 대분류 조회
                var gridScope1 = agrid.getScope('prodClassLevel1Ctrl');
                gridScope1.searchLevel1();

                // 중분류 조회
                $scope.searchLevel2();

                // 소분류 그리드 초기화
                var gridScope3 = agrid.getScope('prodClassLevel3Ctrl');
                gridScope3.dtlGridDefault();

                // 선택한 중분류 정보 라벨 초기화
                $("#lblLevel2").text("");
                $("#hdLevel2").val("");

            });
        });
    };

    // 중분류 저장
    $scope.saveLevel2 = function () {

        if($("#hdLevel1").val() === "null" || $("#hdLevel1").val() === ""){
            $scope._popMsg(messages["info.selectLevel1.msg"]); // 대분류를 선택하세요.
            return;
        }

        $scope.flex.collectionView.commitEdit();

        // 분류코드, 분류명 앞뒤 공백 및 엔터값 제거
        if(orgnFg === "HQ" && prodClassCdInputType !== ''){
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if($scope.flex.collectionView.itemsAdded[i].prodClassCd !== null && $scope.flex.collectionView.itemsAdded[i].prodClassCd !== '') {
                    $scope.flex.collectionView.itemsAdded[i].prodClassCd = $scope.flex.collectionView.itemsAdded[i].prodClassCd.trim().removeEnter();
                }
            }
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            if($scope.flex.collectionView.itemsAdded[i].prodClassNm !== null && $scope.flex.collectionView.itemsAdded[i].prodClassNm !== '') {
                $scope.flex.collectionView.itemsAdded[i].prodClassNm = $scope.flex.collectionView.itemsAdded[i].prodClassNm.trim().removeEnter();
            }
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].prodClassNm !== null && $scope.flex.collectionView.itemsEdited[i].prodClassNm !== '') {
                $scope.flex.collectionView.itemsEdited[i].prodClassNm = $scope.flex.collectionView.itemsEdited[i].prodClassNm.trim().removeEnter();
            }
        }

        var strProdClassCd = ""; // 상품분류코드 중복체크를 위함.

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {

            if(orgnFg === "HQ" && prodClassCdInputType !== ''){
                if($scope.flex.collectionView.items[i].prodClassCd === null || $scope.flex.collectionView.items[i].prodClassCd === ''){
                    $scope._popMsg(messages['info.saveProdClassCdChk.msg']); // 분류코드를 입력하세요.
                    return;
                }

                if($scope.flex.collectionView.items[i].prodClassCd.length > 5){
                    $scope._popMsg(messages['info.prodClassCdLength.msg']); // 분류코드는 최대 5자리까지 입력할 수 있습니다.
                    return;
                }
            }

            if($scope.flex.collectionView.items[i].prodClassNm === null || $scope.flex.collectionView.items[i].prodClassNm === '') {
                $scope._popMsg(messages['info.saveProdClassChk.msg']); // 분류명을 입력하세요.
                return;
            }

            /*if($scope.flex.collectionView.items[i].prodClassNm.length > 15){
                $scope._popMsg(messages['info.prodClassNmLength.msg']); // 분류명은 최대 15자리까지 입력할 수 있습니다.
                return;
            }*/
        }

        // 분류명 길이체크 따로 함(기존에 등록된 분류명중 이미 15자리 넘는 분류가 있어, 새로 추가하거나 수정한 분류만 체크하는걸로 변경)
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            if($scope.flex.collectionView.itemsAdded[i].prodClassNm.length > 15){
                $scope._popMsg(messages['info.prodClassNmLength.msg']); // 분류명은 최대 15자리까지 입력할 수 있습니다.
                return;
            }
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].prodClassNm.length > 15){
                $scope._popMsg(messages['info.prodClassNmLength.msg']); // 분류명은 최대 15자리까지 입력할 수 있습니다.
                return;
            }
        }

        // 상품코드 수동입력인 경우, 중복체크 필요
        if(orgnFg === "HQ" && prodClassCdInputType !== '') {

            var arr = [];
            var arr2 = [];
            var inputProdClassCd = "";

            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if ($scope.flex.collectionView.itemsAdded[i].newRowYn === 'Y') {
                    arr[i] = $scope.flex.collectionView.itemsAdded[i].prodClassCd;
                    strProdClassCd += $scope.flex.collectionView.itemsAdded[i].prodClassCd + ",";
                }
            }

            // 1. 중복체크 : 새로 입력한 분류코드 중 중복코드가 있는지 체크
            arr.forEach(function(element){
                if(!arr2.includes(element)){
                    arr2.push(element);
                }else{
                    inputProdClassCd += element + ",";
                }
            });

            if(inputProdClassCd !== ""){
                $scope._popMsg(messages["info.prodClassCdDuplicate.msg"] + "<br>(" + inputProdClassCd.substr(0, inputProdClassCd.length - 1) + ")"); // 중복된 분류코드 입니다.
                return;
            }
        }

        // 상품분류명수정만 했거나, 분류코드 채번방식이 자동인경우
        if(strProdClassCd === "") {

            // 저장
            $scope.saveProdClassLevel2();

        }else{

            // 2. 중복체크 : 이미 등록한 분류코드 중 중복코드가 있는지 체크
            var params = {};
            params.prodClassCd = strProdClassCd.substr(0, strProdClassCd.length - 1);

            $scope._postJSONQuery.withOutPopUp( "/base/prod/info/getChkProdClassCd.sb", params, function(response){
                var list = response.data.data.list;

                if(list.length > 0) { //  중복

                    var duplicateProdClassCd = "";
                    for(var i=0; i< list.length; i++){
                        duplicateProdClassCd += list[i].prodClassCd + ",";
                    }

                    $scope._popMsg(messages["info.prodClassCdDuplicate.msg"] + "<br>(" + duplicateProdClassCd.substr(0, duplicateProdClassCd.length - 1) + ")"); // 중복된 분류코드 입니다.
                    return;

                }else{

                    // 저장
                    $scope.saveProdClassLevel2();
                }
            });
        }

        // 프랜차이즈 매장권한 일때 코드값이 80001 이하면 본사이므로 수정불가
        /*if (orgnFg == "STORE") {
            if (hqOfficeCd != "00000") {
                for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                    var item = $scope.flex.collectionView.itemsEdited[i];
                    if(item.prodClassCd !== "자동채번") {
                        if (item.prodClassCd < 80001) {
                            $scope._popMsg(messages["info.prodClassCdHqSave"]); // 본사에서 등록한 상품분류는 수정 불가능합니다.
                            return;
                        }
                    }
                }
            }
        }*/

    };

    // 중분류 실제 저장
    $scope.saveProdClassLevel2 = function(){

        var params = new Array();

        // 중분류를 저장하시겠습니까?
        $scope._popConfirm(messages["info.prodClassLevel2"] + "를 " + messages["cmm.choo.save"], function() {

            // 그룹추가
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                var item = $scope.flex.collectionView.itemsAdded[i];
                var obj = {};

                if(item.newRowYn === "Y") {
                    if(orgnFg === "HQ" && prodClassCdInputType !== '' && item.prodClassCd !== "자동채번"){
                        obj.prodClassCd = item.prodClassCd.trim().removeEnter();
                    }
                    obj.status = "I";
                    obj.prodClassNm = item.prodClassNm.trim().removeEnter();
                    obj.pProdClassCd = $("#hdLevel1").val();
                    params.push(obj);
                }
            }

            // 그룹수정
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                var item = $scope.flex.collectionView.itemsEdited[i];
                var obj = {};

                // 프랜차이즈매장은 80001번 이상인 분류만 저장되도록 처리
                if (orgnFg === "STORE" && hqOfficeCd !== "00000") {
                    if(item.prodClassCd !== "자동채번" && item.prodClassCd !== ""){
                        if(item.prodClassCd > 80000) {
                            obj.status = "U";
                            obj.prodClassCd = item.prodClassCd;
                            obj.prodClassNm = item.prodClassNm.trim().removeEnter();
                            obj.pProdClassCd = "00000";
                            params.push(obj);
                        }
                    }
                }

                if (orgnFg === "HQ" || (orgnFg === "STORE" && hqOfficeCd === "00000")) {
                    if (item.prodClassCd !== "자동채번" && item.prodClassCd !== ""){
                        obj.status = "U";
                        obj.prodClassCd = item.prodClassCd;
                        obj.prodClassNm = item.prodClassNm.trim().removeEnter();
                        obj.pProdClassCd = "00000";
                        params.push(obj);
                    }
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/info/class/saveProdClass.sb", params, function () {

                // 대분류 조회
                var gridScope1 = agrid.getScope('prodClassLevel1Ctrl');
                gridScope1.searchLevel1();

                // 중분류 조회
                $scope.searchLevel2();

                // 소분류 그리드 초기화
                var gridScope3 = agrid.getScope('prodClassLevel3Ctrl');
                gridScope3.dtlGridDefault();

                // 선택한 중분류 정보 라벨 초기화
                $("#lblLevel2").text("");
                $("#hdLevel2").val("");

            });
        });
    };

    // 중분류 그리드 초기화
    $scope.dtlGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);

// 소분류
app.controller('prodClassLevel3Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('prodClassLevel3Ctrl', $scope, $http, false));

    $scope.initGrid = function (s, e) {

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === 'prodClassCd') {

                    // 기존 등록 분류는 분류코드 수정불가
                    if (item.newRowYn !== 'Y' && item.prodClassCd !== '' && item.prodClassCd !== '자동채번') {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }

                    // 새로 등록하는 분류중 '자동채번' 분류는 분류코드 입력불가
                    if(item.newRowYn === 'Y' && item.prodClassCd === '자동채번'){
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }
            }
        });

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    $scope.$on("prodClassLevel3Ctrl", function(event, data) {

        // 소분류 조회
        $scope.searchLevel3(data);
        event.preventDefault();
    });

    // 소분류 조회
    $scope.searchLevel3 = function(data){

        var params = {};
        params.clsLevelCd = "3";
        params.pProdClassCd = $("#hdLevel2").val();

        $scope._inquirySub("/base/prod/info/class/getProdClass.sb", params, function() {

            // 프랜차이즈 매장은 본사에서 등록한 분류 수정불가
            if(orgnFg == "STORE" && hqOfficeCd != "00000") {

                var grid = wijmo.Control.getControl("#wjGridProdClassLevel3");
                var rows = grid.rows;

                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    var item = $scope.flex.collectionView.items[i];
                    if(item.prodClassCd < 80001){
                        item.gChk = false;
                        rows[i].isReadOnly = true;
                    }
                }
            }
        }, false);
    };

    // 소분류 추가
    $scope.addLevel3 = function () {

        if($("#hdLevel1").val() === "null" || $("#hdLevel1").val() === ""){
            $scope._popMsg(messages["info.selectLevel1.msg"]); // 대분류를 선택하세요.
            return;
        }

        if($("#hdLevel2").val() === "null" || $("#hdLevel2").val() === ""){
            $scope._popMsg(messages["info.selectLevel2.msg"]); // 중분류를 선택하세요.
            return;
        }

        var params = {};
        var focusLine = 0;

        // 본사권한이면서, 상품분류코드직접입력여부 값이 있는 경우만 상품분류코드 수동입력 가능
        if(orgnFg === "HQ" && prodClassCdInputType !== ''){
            params.prodClassCd = '';
            focusLine = 1;
        }else{
            params.prodClassCd = '자동채번';
            focusLine = 2;
        }

        params.gChk = true;
        params.prodClassNm = '';
        params.newRowYn = 'Y'; // 새로 입력한 분류인지 확인을 위해 추가

        // 행추가
        $scope._addRow(params, focusLine);
    };

    // 소분류 삭제
    $scope.delLevel3 = function () {

        if($("#hdLevel1").val() === "null" || $("#hdLevel1").val() === ""){
            $scope._popMsg(messages["info.selectLevel1.msg"]); // 대분류를 선택하세요.
            return;
        }

        if($("#hdLevel2").val() === "null" || $("#hdLevel2").val() === ""){
            $scope._popMsg(messages["info.selectLevel2.msg"]); // 중분류를 선택하세요.
            return;
        }

        // 1. 선택 분류가 있는지 체크
        var chkCount = 0;
        for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
            var item =  $scope.flex.collectionView.items[i];
            if(item.gChk) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["info.delProdClassChk.msg"]); // 삭제할 분류의 체크박스를 선택하세요.
            return false;
        }else{
            // 선택 분류 중 신규추가분류를 체크한 경우, grid에서 삭제
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];
                if(item.gChk){
                    if(item.newRowYn === 'Y'){
                        $scope.flex.collectionView.removeAt(i);
                    }
                }
            }
        }

        // 2. 다시 선택 분류 체크(신규추가분류를 삭제하고 남은 분류가 있는지 확인)
        chkCount = 0;
        for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
            var item =  $scope.flex.collectionView.items[i];
            if(item.gChk) chkCount++;
        }

        if(chkCount === 0){
            return false;
        }else{
            // 3. 분류가 있는경우, 분류에 매핑된 상품 또는 자식분류가 있는지 체크
            for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
                var item = $scope.flex.collectionView.items[i];
                if(item.gChk){

                    // 프랜차이즈 매장권한 일때 코드값이 80001 이하면 본사이므로 삭제불가
                    if(orgnFg == "STORE") {
                        if (hqOfficeCd != "00000") {
                            if (item.prodClassCd < 80001) {
                                $scope._popMsg(messages["info.prodClassCdHqDel"]); // 본사에서 등록한 상품분류는 삭제 불가능합니다.
                                return;
                            }
                        }
                    }

                    if(parseInt(nvl(item.hqChildClassCnt, 0)) !== 0 || parseInt(nvl(item.hqProdCnt, 0)) !== 0 ||
                        parseInt(nvl(item.msChildClassCnt, 0)) !== 0 || parseInt(nvl(item.msProdCnt, 0)) !== 0){
                        $scope._popMsg(messages["info.delProdClassDenied.msg"]);// 상품 또는 하위분류가 등록된 분류는 삭제할 수 없습니다.
                        return;
                    }
                }
            }
        }

        var params = new Array();

        // 소분류를 삭제하시겠습니까?
        $scope._popConfirm(messages["info.prodClassLevel3"] + "를 " + messages["cmm.choo.delete"], function() {

            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk === true) {
                    var obj = {};
                    obj.status = "D";
                    obj.prodClassCd = item.prodClassCd;
                    params.push(obj);
                }
            }
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/info/class/saveProdClass.sb", params, function () {

                // 중분류 조회
                var gridScope2 = agrid.getScope('prodClassLevel2Ctrl');
                gridScope2.searchLevel2();

                // 소분류 조회
                $scope.searchLevel3();

            });
        });
    };

    // 소분류 저장
    $scope.saveLevel3 = function () {

        if($("#hdLevel1").val() === "null" || $("#hdLevel1").val() === ""){
            $scope._popMsg(messages["info.selectLevel1.msg"]); // 대분류를 선택하세요.
            return;
        }

        if($("#hdLevel2").val() === "null" || $("#hdLevel2").val() === ""){
            $scope._popMsg(messages["info.selectLevel2.msg"]); // 중분류를 선택하세요.
            return;
        }

        $scope.flex.collectionView.commitEdit();

        // 분류코드, 분류명 앞뒤 공백 및 엔터값 제거
        if(orgnFg === "HQ" && prodClassCdInputType !== ''){
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if($scope.flex.collectionView.itemsAdded[i].prodClassCd !== null && $scope.flex.collectionView.itemsAdded[i].prodClassCd !== '') {
                    $scope.flex.collectionView.itemsAdded[i].prodClassCd = $scope.flex.collectionView.itemsAdded[i].prodClassCd.trim().removeEnter();
                }
            }
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            if($scope.flex.collectionView.itemsAdded[i].prodClassNm !== null && $scope.flex.collectionView.itemsAdded[i].prodClassNm !== '') {
                $scope.flex.collectionView.itemsAdded[i].prodClassNm = $scope.flex.collectionView.itemsAdded[i].prodClassNm.trim().removeEnter();
            }
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].prodClassNm !== null && $scope.flex.collectionView.itemsEdited[i].prodClassNm !== '') {
                $scope.flex.collectionView.itemsEdited[i].prodClassNm = $scope.flex.collectionView.itemsEdited[i].prodClassNm.trim().removeEnter();
            }
        }

        var strProdClassCd = ""; // 상품분류코드 중복체크를 위함.

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {

            if(orgnFg === "HQ" && prodClassCdInputType !== ''){
                if($scope.flex.collectionView.items[i].prodClassCd === null || $scope.flex.collectionView.items[i].prodClassCd === ''){
                    $scope._popMsg(messages['info.saveProdClassCdChk.msg']); // 분류코드를 입력하세요.
                    return;
                }

                if($scope.flex.collectionView.items[i].prodClassCd.length > 5){
                    $scope._popMsg(messages['info.prodClassCdLength.msg']); // 분류코드는 최대 5자리까지 입력할 수 있습니다.
                    return;
                }
            }

            if($scope.flex.collectionView.items[i].prodClassNm === null || $scope.flex.collectionView.items[i].prodClassNm === '') {
                $scope._popMsg(messages['info.saveProdClassChk.msg']); // 분류명을 입력하세요.
                return;
            }

            /*if($scope.flex.collectionView.items[i].prodClassNm.length > 15){
                $scope._popMsg(messages['info.prodClassNmLength.msg']); // 분류명은 최대 15자리까지 입력할 수 있습니다.
                return;
            }*/
        }

        // 분류명 길이체크 따로 함(기존에 등록된 분류명중 이미 15자리 넘는 분류가 있어, 새로 추가하거나 수정한 분류만 체크하는걸로 변경)
        for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
            if($scope.flex.collectionView.itemsAdded[i].prodClassNm.length > 15){
                $scope._popMsg(messages['info.prodClassNmLength.msg']); // 분류명은 최대 15자리까지 입력할 수 있습니다.
                return;
            }
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            if($scope.flex.collectionView.itemsEdited[i].prodClassNm.length > 15){
                $scope._popMsg(messages['info.prodClassNmLength.msg']); // 분류명은 최대 15자리까지 입력할 수 있습니다.
                return;
            }
        }

        // 상품코드 수동입력인 경우, 중복체크 필요
        if(orgnFg === "HQ" && prodClassCdInputType !== '') {

            var arr = [];
            var arr2 = [];
            var inputProdClassCd = "";

            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if ($scope.flex.collectionView.itemsAdded[i].newRowYn === 'Y') {
                    arr[i] = $scope.flex.collectionView.itemsAdded[i].prodClassCd;
                    strProdClassCd += $scope.flex.collectionView.itemsAdded[i].prodClassCd + ",";
                }
            }

            // 1. 중복체크 : 새로 입력한 분류코드 중 중복코드가 있는지 체크
            arr.forEach(function(element){
                if(!arr2.includes(element)){
                    arr2.push(element);
                }else{
                    inputProdClassCd += element + ",";
                }
            });

            if(inputProdClassCd !== ""){
                $scope._popMsg(messages["info.prodClassCdDuplicate.msg"] + "<br>(" + inputProdClassCd.substr(0, inputProdClassCd.length - 1) + ")"); // 중복된 분류코드 입니다.
                return;
            }
        }

        // 상품분류명수정만 했거나, 분류코드 채번방식이 자동인경우
        if(strProdClassCd === "") {

            // 저장
            $scope.saveProdClassLevel3();

        }else{

            // 2. 중복체크 : 이미 등록한 분류코드 중 중복코드가 있는지 체크
            var params = {};
            params.prodClassCd = strProdClassCd.substr(0, strProdClassCd.length - 1);

            $scope._postJSONQuery.withOutPopUp( "/base/prod/info/getChkProdClassCd.sb", params, function(response){
                var list = response.data.data.list;

                if(list.length > 0) { //  중복

                    var duplicateProdClassCd = "";
                    for(var i=0; i< list.length; i++){
                        duplicateProdClassCd += list[i].prodClassCd + ",";
                    }

                    $scope._popMsg(messages["info.prodClassCdDuplicate.msg"] + "<br>(" + duplicateProdClassCd.substr(0, duplicateProdClassCd.length - 1) + ")"); // 중복된 분류코드 입니다.
                    return;

                }else{

                    // 저장
                    $scope.saveProdClassLevel3();
                }
            });
        }

        // 프랜차이즈 매장권한 일때 코드값이 80001 이하면 본사이므로 수정불가
        /*if (orgnFg == "STORE") {
            if (hqOfficeCd != "00000") {
                for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                    var item = $scope.flex.collectionView.itemsEdited[i];
                    if(item.prodClassCd !== "자동채번") {
                        if (item.prodClassCd < 80001) {
                            $scope._popMsg(messages["info.prodClassCdHqSave"]); // 본사에서 등록한 상품분류는 수정 불가능합니다.
                            return;
                        }
                    }
                }
            }
        }*/

    };

    // 소분류 실제 저장
    $scope.saveProdClassLevel3 = function(){

        var params = new Array();

        // 소분류를 저장하시겠습니까?
        $scope._popConfirm(messages["info.prodClassLevel3"] + "를 " + messages["cmm.choo.save"], function() {

            // 그룹추가
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                var item = $scope.flex.collectionView.itemsAdded[i];
                var obj = {};

                if(item.newRowYn === "Y") {
                    if(orgnFg === "HQ" && prodClassCdInputType !== '' && item.prodClassCd !== "자동채번"){
                        obj.prodClassCd = item.prodClassCd.trim().removeEnter();
                    }
                    obj.status = "I";
                    obj.prodClassNm = item.prodClassNm.trim().removeEnter();
                    obj.pProdClassCd = $("#hdLevel2").val();
                    params.push(obj);
                }
            }

            // 그룹수정
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                var item = $scope.flex.collectionView.itemsEdited[i];
                var obj = {};

                // 프랜차이즈매장은 80001번 이상인 분류만 저장되도록 처리
                if (orgnFg === "STORE" && hqOfficeCd !== "00000") {
                    if(item.prodClassCd !== "자동채번" && item.prodClassCd !== ""){
                        if(item.prodClassCd > 80000) {
                            obj.status = "U";
                            obj.prodClassCd = item.prodClassCd;
                            obj.prodClassNm = item.prodClassNm.trim().removeEnter();
                            obj.pProdClassCd = "00000";
                            params.push(obj);
                        }
                    }
                }

                if (orgnFg === "HQ" || (orgnFg === "STORE" && hqOfficeCd === "00000")) {
                    if (item.prodClassCd !== "자동채번" && item.prodClassCd !== ""){
                        obj.status = "U";
                        obj.prodClassCd = item.prodClassCd;
                        obj.prodClassNm = item.prodClassNm.trim().removeEnter();
                        obj.pProdClassCd = "00000";
                        params.push(obj);
                    }
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/info/class/saveProdClass.sb", params, function () {

                // 중분류 조회
                var gridScope2 = agrid.getScope('prodClassLevel2Ctrl');
                gridScope2.searchLevel2();

                // 소분류 조회
                $scope.searchLevel3();

            });
        });
    };

    // 소분류 그리드 초기화
    $scope.dtlGridDefault = function () {
        $timeout(function () {
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;
            $scope.flex.refresh();
        }, 10);
    };

}]);