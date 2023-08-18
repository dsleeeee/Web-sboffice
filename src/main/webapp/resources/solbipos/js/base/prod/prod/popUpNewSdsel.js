/****************************************************************
 *
 * 파일명 : popUpNewSdsel.js
 * 설  명 : 신규선택그룹생성 조회 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.27     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var sdselTypeFgData = [
    {"name": "세트", "value": "C"},
    {"name": "싱글세트", "value": "S"}
];

// 고정상품구분
var fixProdFgDataMap = new wijmo.grid.DataMap([
    {id: "0", name: "선택"},
    {id: "1", name: "고정"}
], 'id', 'name');

// 출력여부
var printYnData = [
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];
/**
 *  선택그룹 조회 그리드 생성
 */
app.controller('newSdselGrpCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('newSdselGrpCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.fixProdFgDataMap = fixProdFgDataMap;
        // 그리드 DataMap 설정
        $scope.sdselTypeFgDataMap = new wijmo.grid.DataMap(sdselTypeFgData, 'value', 'name'); // 세트구분

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "sdselGrpCd"){ // 선택그룹코드
                    if(item.sdselGrpCd !== '자동채번') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "sdselGrpCd") { // 선택그룹코드 클릭
                    if(selectedRow.sdselGrpCd !== '자동채번') {
                        $scope._broadcast('newSdselClassCtrl', selectedRow);
                        $("#sdselGrpTitle").text(' [' + selectedRow.sdselGrpCd + ']' + selectedRow.sdselGrpNm);
                        $("#sdselClassTitle").text('');
                    }
                }
            }
        });
    };

    $scope.$on("newSdselGrpCtrl", function(event, data) {
        // 선택그룹 리스트 조회
        $scope.searchSdselGrp();
        event.preventDefault();
    });

    // 선택그룹 리스트 조회
    $scope.searchSdselGrp = function () {
        var params = {};
        params.fixProdFg = '1';
        params.sdselTypeFg = 'C';
        params.useYn = 'Y';
        $scope._inquirySub("/base/prod/sideMenu/menuGrp/list.sb", params, function() {

            var scope = agrid.getScope('prodModifyCtrl');
            // 신규생성했으면
            if(scope.prodModifyInfo.sdselGrpCd === "" && (scope.getNewSdselGrpCd() === "" || scope.getNewSdselGrpCd() === undefined)){
                var newSdselGrpCd = $scope.flex.collectionView.items[$scope.flex.collectionView.items.length-1].sdselGrpCd;
                scope.setNewSdselGrpCd(newSdselGrpCd);
                $("#searchSdselGrpCd").val(scope.getNewSdselGrpCd(newSdselGrpCd));
            }
            // 선택상품 리스트 재조회
            var prodScope = agrid.getScope('newSdselProdCtrl');
            prodScope._gridDataInit();   // 그리드 초기화
            // 상품 리스트 재조회
            var prodScope2 = agrid.getScope('prodSelectCtrl');
            prodScope2._gridDataInit();   // 그리드 초기화
        }, false);
    }

    $scope.selectNewGrp = function (){
        var selectedRow = $scope.flex.rows[$scope.flex.rows.length-1].dataItem;

        $scope.flex.select($scope.flex.rows.length,1);
        $scope._broadcast('newSdselClassCtrl', selectedRow);
        $("#sdselGrpTitle").text(' [' + selectedRow.sdselGrpCd + ']' + selectedRow.sdselGrpNm);
        $("#sdselClassTitle").text('');
    }

    // 선택그룹 그리드 행 추가
    $scope.addRow = function() {
        // 파라미터 설정
        var params = {};
        params.status = 'I';
        params.gChk = true;
        params.sdselGrpCd = '자동채번';
        params.hqBrandCd = null;
        params.fixProdFg = 0;
        params.sdselTypeFg = "C";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params, 2);
    };

    // 선택그룹 그리드 행 삭제
    $scope.deleteRow = function() {
        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            // 파라미터 설정
            var params = [];
            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk) {
                    if(item.sdselGrpCd === "자동채번" || item.sdselGrpCd  === null || item.sdselGrpCd  === undefined || item.sdselGrpCd  === ""){
                        $scope.flex.collectionView.removeAt(i);
                    }else {
                        if ((orgnFg == "HQ") || (orgnFg == "STORE" && hqOfficeCd == '00000') || (orgnFg == "STORE" && hqOfficeCd != "00000" && item.sdselGrpCd > 799999)) {
                            if (item.cnt === 0 || item.cnt === null || item.cnt === undefined || item.cnt === "") {
                                $scope.flex.collectionView.removeAt(i);
                            } else {
                                $scope._popMsg(messages["sideMenu.selectMenu.sdselClass.notNull"]);
                                return false;
                            }
                        } else {
                            $scope._popMsg(messages["sideMenu.selectMenu.edited"]);
                            $scope._broadcast('sideMenuAttrClassCtrl');
                            return false;
                        }
                    }
                }
            }
            for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
                $scope.flex.collectionView.itemsRemoved[d].status = 'D';
                params.push($scope.flex.collectionView.itemsRemoved[d]);
            }
            // 삭제기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/sideMenu/menuGrp/save.sb', params, function() {
                $("#sdselGrpTitle").html("");
                var classScope = agrid.getScope('newSdselClassCtrl');
                classScope._gridDataInit();   // 그리드 초기화

                $("#sdselClassTitle").html("");
                var prodScope = agrid.getScope('newSdselProdCtrl');
                prodScope._gridDataInit();   // 그리드 초기화
                var prodScope2 = agrid.getScope('prodSelectCtrl');
                prodScope2._gridDataInit();   // 그리드 초기화
                // 저장 후 그리드 재조회
                $scope._broadcast("newSdselGrpCtrl");
            });
        });
    };

    // 저장
    $scope.save = function() {
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = [];
            var orgChk = 0;

            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                if ((orgnFg == "HQ") || (orgnFg == "STORE" && hqOfficeCd =="00000") || (orgnFg == "STORE" && hqOfficeCd !="00000" && $scope.flex.collectionView.itemsEdited[u].sdselGrpCd > 799999)) {
                    if($scope.flex.collectionView.itemsEdited[u].sdselGrpNm == ""){
                        $scope._popMsg(messages["sideMenu.selectMenu.sdselGrpNm"] + messages["sideMenu.selectMenu.inputEnv"]);
                        return false;
                    }
                    if($scope.maxChk($scope.flex.collectionView.itemsEdited[u].sdselGrpNm)) {
                        $scope.flex.collectionView.itemsEdited[u].status = 'U';
                        params.push($scope.flex.collectionView.itemsEdited[u]);
                    } else {
                        $scope._popMsg(messages["cmm.max50Chk"]);
                        return false;
                    }

                } else if(orgChk == 0) {
                    orgChk = 1;
                }
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if($scope.flex.collectionView.itemsAdded[i].sdselGrpNm == ""){
                    $scope._popMsg(messages["sideMenu.selectMenu.sdselGrpCd"] + messages["sideMenu.selectMenu.inputEnv"]);
                    return false;
                }
                if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].sdselGrpNm)) {

                    // addRow가 제대로 안된 데이터 다시 파악하여 필수값 채워주기
                    if($scope.flex.collectionView.itemsAdded[i].gChk === undefined){
                        $scope.flex.collectionView.itemsAdded[i].gChk = true;
                        $scope.flex.collectionView.itemsAdded[i].sdselGrpCd = '자동채번';
                    }

                    $scope.flex.collectionView.itemsAdded[i].status = 'I';
                    $scope.flex.collectionView.itemsAdded[i].sdselTypeFg = 'C';
                    params.push($scope.flex.collectionView.itemsAdded[i]);
                } else {
                    $scope._popMsg(messages["cmm.max50Chk"]);
                    return false;
                }
            }

            if (orgChk) {
                $scope._popMsg(messages["sideMenu.selectMenu.edited"]);
                orgChk = 0;
                return false;
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/sideMenu/menuGrp/save.sb', params, function() {
                $("#sdselGrpTitle").html("");
                var classScope = agrid.getScope('newSdselClassCtrl');
                classScope._gridDataInit();   // 그리드 초기화

                $("#sdselClassTitle").html("");
                var prodScope = agrid.getScope('newSdselProdCtrl');
                prodScope._gridDataInit();   // 그리드 초기화
                var prodScope2 = agrid.getScope('prodSelectCtrl');
                prodScope2._gridDataInit();   // 그리드 초기화
                // 저장 후 그리드 재조회
                $scope._broadcast("newSdselGrpCtrl");
            });
        });
    };

    $scope.maxChk = function (val){
        var str = val;
        var strLength = 0;
        var strTitle = "";
        var strPiece = "";
        for (i = 0; i < str.length; i++){
            var code = str.charCodeAt(i);
            var ch = str.substr(i,1).toUpperCase();
            //체크 하는 문자를 저장
            strPiece = str.substr(i,1)
            code = parseInt(code);
            if ((ch < "0" || ch > "9") && (ch < "A" || ch > "Z") && ((code > 255) || (code < 0))){
                strLength = strLength + 3; //UTF-8 3byte 로 계산
            }else{
                strLength = strLength + 1;
            }
            if(strLength > 50){ //제한 길이 확인
                return false;
            }else{
                strTitle = strTitle+strPiece; //제한길이 보다 작으면 자른 문자를 붙여준다.
            }
        }
        return true;
    };
}]);

/**
 *  선택분류 조회 그리드 생성
 */
app.controller('newSdselClassCtrl', ['$scope', '$http', 'sdselGrpCd', function ($scope, $http, sdselGrpCd) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('newSdselClassCtrl', $scope, $http, true));

    // sdselGrpCd Data Setter
    $scope.setSdselGrpCd = function (value) {
        sdselGrpCd.set(value);
    };
    // sdselGrpCd Data Getter
    $scope.getSdselGrpCd = function () {
        return sdselGrpCd.get();
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "sdselClassCd"){ // 선택분류코드
                    if(item.sdselClassCd !== '자동채번') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "sdselClassCd") { // 선택분류코드 클릭
                    if(selectedRow.sdselClassCd !== '자동채번') {
                        $scope._broadcast('newSdselProdCtrl', selectedRow);
                        $scope._broadcast('prodSelectCtrl', selectedRow);
                        $("#sdselClassTitle").text(' [' + selectedRow.sdselClassCd + ']' + selectedRow.sdselClassNm);
                    }
                }
            }
        });
    };

    $scope.$on("newSdselClassCtrl", function(event, data) {
        $scope.setSdselGrpCd(data.sdselGrpCd);
        // 선택분류 리스트 조회
        $scope.searchSdselClass();
        event.preventDefault();
    });

    // 선택분류 리스트 조회
    $scope.searchSdselClass = function () {

        // 선택상품 리스트 재조회
        var prodScope = agrid.getScope('newSdselProdCtrl');
        prodScope._gridDataInit();   // 그리드 초기화
        // 상품 리스트 재조회
        var prodScope2 = agrid.getScope('prodSelectCtrl');
        prodScope2._gridDataInit();   // 그리드 초기화

        var params = {};
        params.sdselGrpCd = $scope.getSdselGrpCd();
        $scope._inquirySub("/base/prod/sideMenu/menuClass/list.sb", params, function() {}, false);
    }

    // 위로 옮기기 버튼
    $scope.rowMoveUp = function() {
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

    // 아래로 옮기기 버튼
    $scope.rowMoveDown = function() {
        var movedRows = 0;
        for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
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

    // 선택그룹 추가
    $scope.addRow = function() {
        // 파라미터 설정
        var params = {};
        params.status = 'I';
        params.sdselGrpCd = $scope.getSdselGrpCd();
        params.gChk = true;
        params.sdselClassCd = '자동채번';
        params.sdselClassNm = '';
        params.sdselQty = 0;
        params.requireYn = "N";
        params.regStoreFg = "0";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };

    // 선택분류 삭제
    $scope.deleteRow = function() {
        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            // 파라미터 설정
            var params = [];
            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk) {
                    if(item.sdselClassCd === "자동채번" || item.sdselClassCd  === null || item.sdselClassCd  === undefined || item.sdselClassCd  === ""){
                        $scope.flex.collectionView.removeAt(i);
                    }else {
                        if (item.cnt === 0 || item.cnt === null || item.cnt === undefined || item.cnt === "") {
                            $scope.flex.collectionView.removeAt(i);
                        } else {
                            $scope._popMsg(messages["sideMenu.selectMenu.sdselClass.notNull"]);
                            return false;
                        }
                    }
                }
            }
            for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
                $scope.flex.collectionView.itemsRemoved[d].status = 'D';
                params.push($scope.flex.collectionView.itemsRemoved[d]);
            }
            $scope._save('/base/prod/sideMenu/menuClass/save.sb', params, function() {

                // 선택분류 리스트 재조회
                $scope.searchSdselClass();

                // 선택그룹 리스트 재조회
                var grpScope = agrid.getScope('newSdselGrpCtrl');
                prodScope.searchSdselGrp();   // 재조회

                // 선택상품 리스트 재조회
                var prodScope = agrid.getScope('newSdselProdCtrl');
                prodScope._gridDataInit();   // 그리드 초기화
                // 상품 리스트 재조회
                var prodScope2 = agrid.getScope('prodSelectCtrl');
                prodScope2._gridDataInit();   // 그리드 초기화

            });
        });
    };

    // 선택분류 저장
    $scope.saveClass = function() {
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = [];

            // dispSeq 재설정
            var editItems = [];
            for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
                if($scope.flex.collectionView.items[s].dispSeq !== (s+1)) {
                    if (isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
                        editItems.push($scope.flex.collectionView.items[s]);
                    }
                }
            }

            for (var s = 0; s < editItems.length; s++) {
                editItems[s].dispSeq = (s + 1);
                $scope.flex.collectionView.editItem(editItems[s]);
                editItems[s].status = "U";
                $scope.flex.collectionView.commitEdit();
            }

            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                if($scope.flex.collectionView.itemsEdited[u].sdselClassNm === ""){
                    $scope._popMsg(messages["sideMenu.selectMenu.sdselClassNm"] + messages["sideMenu.selectMenu.inputEnv"]);
                    return false;
                }

                if(nvl($scope.flex.collectionView.itemsEdited[u].fixProdCnt, 0) > nvl($scope.flex.collectionView.itemsEdited[u].sdselQty, 0)){
                    $scope._popMsg( "'" + $scope.flex.collectionView.itemsEdited[u].sdselClassNm + "[" + $scope.flex.collectionView.itemsEdited[u].sdselClassCd + "]'"
                        + messages["sideMenu.selectMenu.sdselQtyChk.msg"]
                        + "<br/> (선택분류수량 : " + $scope.flex.collectionView.itemsEdited[u].sdselQty + " / 고정상품수량합계 : "  + $scope.flex.collectionView.itemsEdited[u].fixProdCnt+ ")");
                    return false;
                }

                // 필수선택여부가 'Y'이면 수량은 1 이상
                if($scope.flex.collectionView.itemsEdited[u].requireYn === "Y") {
                    if(parseInt(nvl($scope.flex.collectionView.itemsEdited[u].sdselQty, 0)) >= 1) {
                    } else {
                        $scope._popMsg(messages["sideMenu.selectMenu.requireYnQtyChk.msg"]); // 필수선택시 수량은 1 이상 입력해주세요.
                        return false;
                    }
                }

                if($scope.maxChk($scope.flex.collectionView.itemsEdited[u].sdselClassNm)){
                    $scope.flex.collectionView.itemsEdited[u].status = 'U';
                    params.push($scope.flex.collectionView.itemsEdited[u]);
                } else {
                    $scope._popMsg(messages["cmm.max50Chk"]);
                    return false;
                }
            }


            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if($scope.flex.collectionView.itemsAdded[i].sdselClassNm == ""){
                    $scope._popMsg(messages["sideMenu.selectMenu.sdselClassNm"] + messages["sideMenu.selectMenu.inputEnv"]);
                    return false;
                }

                // 필수선택여부가 'Y'이면 수량은 1 이상
                if($scope.flex.collectionView.itemsAdded[i].requireYn === "Y") {
                    if(parseInt(nvl($scope.flex.collectionView.itemsAdded[i].sdselQty, 0)) >= 1) {
                    } else {
                        $scope._popMsg(messages["sideMenu.selectMenu.requireYnQtyChk.msg"]); // 필수선택시 수량은 1 이상 입력해주세요.
                        return false;
                    }
                }

                if($scope.maxChk($scope.flex.collectionView.itemsAdded[i].sdselClassNm)) {

                    // addRow가 제대로 안된 데이터 다시 파악하여 필수값 채워주기
                    if($scope.flex.collectionView.itemsAdded[i].gChk === undefined){
                        $scope.flex.collectionView.itemsAdded[i].sdselGrpCd =  $scope.getSdselGrpCd();
                        $scope.flex.collectionView.itemsAdded[i].gChk = true;
                        $scope.flex.collectionView.itemsAdded[i].sdselClassCd = '자동채번';
                        if($scope.flex.collectionView.itemsAdded[i].sdselQty === null ||
                            $scope.flex.collectionView.itemsAdded[i].sdselQty === undefined ||
                            $scope.flex.collectionView.itemsAdded[i].sdselQty === ""){
                            $scope.flex.collectionView.itemsAdded[i].sdselQty = 0;
                        }
                    }

                    $scope.flex.collectionView.itemsAdded[i].status = "I";
                    params.push($scope.flex.collectionView.itemsAdded[i]);
                } else {
                    $scope._popMsg(messages["cmm.max50Chk"]);
                    return false;
                }
            }
            // 적용매장구분관련 BBQ에선 안 써서 주석처리
            // if(orgnFg == 'HQ') {
            //     // 적용매장 전체 삭제
            //     $scope._postJSONSave.withOutPopUp("/base/prod/sideMenu/menuClass/getSdselClassRegStoreDeleteAll.sb", params, function(){
            //         // 저장
            //         $scope.save(params);
            //     });
            // } else {
                // 저장
                $scope.save(params);
            // }

        });
    };

    // 저장
    $scope.save = function(params) {
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/sideMenu/menuClass/save.sb', params, function() {

            // 선택분류 리스트 재조회
            $scope.searchSdselClass();

            // 선택그룹 리스트 재조회
            var grpScope = agrid.getScope('newSdselGrpCtrl');
            grpScope.searchSdselGrp();   // 재조회

            // 선택상품 리스트 재조회
            var prodScope = agrid.getScope('newSdselProdCtrl');
            prodScope._gridDataInit();   // 그리드 초기화
            // 상품 리스트 재조회
            var prodScope2 = agrid.getScope('prodSelectCtrl');
            prodScope2._gridDataInit();   // 그리드 초기화

        });
    };

    $scope.maxChk = function (val){
        var str = val;
        var strLength = 0;
        var strTitle = "";
        var strPiece = "";
        for (i = 0; i < str.length; i++){
            var code = str.charCodeAt(i);
            var ch = str.substr(i,1).toUpperCase();
            //체크 하는 문자를 저장
            strPiece = str.substr(i,1)
            code = parseInt(code);
            if ((ch < "0" || ch > "9") && (ch < "A" || ch > "Z") && ((code > 255) || (code < 0))){
                strLength = strLength + 3; //UTF-8 3byte 로 계산
            }else{
                strLength = strLength + 1;
            }
            if(strLength > 50){ //제한 길이 확인
                return false;
            }else{
                strTitle = strTitle+strPiece; //제한길이 보다 작으면 자른 문자를 붙여준다.
            }
        }
        return true;
    };
}]).factory('sdselGrpCd', function () {
    // 사이드메뉴 선택분류 그리드 의 변수 값 영역
    var sdselGrpCd = {};
    sdselGrpCd.set = function (value) {
        sdselGrpCd.value = value;
    };
    sdselGrpCd.get = function () {
        return sdselGrpCd.value;
    };
    return sdselGrpCd;
});

/**
 *  선택메뉴 조회 그리드 생성
 */
app.controller('newSdselProdCtrl', ['$scope', '$http', 'sdselClassCd', function ($scope, $http, sdselClassCd) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('newSdselProdCtrl', $scope, $http, true));

    // sdselClassCd Data Setter
    $scope.setSdselClassCd = function (value) {
        sdselClassCd.set(value);
    };
    // sdselClassCd Data Getter
    $scope.getSdselClassCd = function () {
        return sdselClassCd.get();
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.brandDataMap = new wijmo.grid.DataMap(brandList, 'value', 'name'); // 브랜드
        $scope.printYnDataMap = new wijmo.grid.DataMap(printYnData, 'value', 'name'); // 출력여부
    };

    $scope.$on("newSdselProdCtrl", function(event, data) {
        $scope.setSdselClassCd(data.sdselClassCd);
        $scope.searchSdselProd();
        event.preventDefault();
    });

    // 선택메뉴 리스트 조회
    $scope.searchSdselProd = function () {
        var params = {};
        params.sdselClassCd = $scope.getSdselClassCd();
        $scope._inquirySub("/base/prod/sideMenu/menuProd/list.sb", params, function() {}, false);
    };


    // 위로 옮기기 버튼
    $scope.rowMoveUp = function() {
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

    // 아래로 옮기기 버튼
    $scope.rowMoveDown = function() {
        var movedRows = 0;
        for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if ((i < $scope.flex.itemsSource.itemCount - 1) && item.gChk) {
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

    // 선택상품 그리드 행 삭제
    $scope.deleteRow = function() {
        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            // 파라미터 설정
            var params = [];
            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }
            for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
                $scope.flex.collectionView.itemsRemoved[d].status = 'D';
                params.push($scope.flex.collectionView.itemsRemoved[d]);
            }

            // 삭제기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/sideMenu/menuProd/save.sb', params, function() {

                var classScope = agrid.getScope('newSdselClassCtrl');
                classScope.searchSdselClass();   // 그리드 초기화
                var prodScope2 = agrid.getScope('prodSelectCtrl');
                prodScope2.searchProd();   // 그리드 초기화

                $scope.searchSdselProd(); // 재조회
            });
        });
    };

    // 선택상품 그리드 저장
    $scope.saveProd = function() {
        var msg = messages["cmm.choo.save"];

        if(orgnFg == 'HQ' && hqOfficeCd == 'DS021') {
            msg = messages["sideMenu.selectMenu.sdselClassRegStoreAlert"] + " " +  messages["cmm.choo.save"]; // 적용매장구분 변경된 경우 등록된 적용매장은 모두 삭제됩니다. 저장하시겠습니까?
        }

        $scope._popConfirm(msg, function() {
            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = [];

            // dispSeq 재설정
            var editItems = [];
            for (var s = 0; s < $scope.flex.collectionView.itemCount; s++) {
                if($scope.flex.collectionView.items[s].dispSeq !== (s+1)) {
                    if (isEmptyObject($scope.flex.collectionView.items[s].status) || $scope.flex.collectionView.items[s].status === 'I') {
                        editItems.push($scope.flex.collectionView.items[s]);
                    }
                }
            }

            for (var s = 0; s < editItems.length; s++) {
                editItems[s].dispSeq = (s + 1);
                $scope.flex.collectionView.editItem(editItems[s]);
                editItems[s].status = "U";
                $scope.flex.collectionView.commitEdit();
            }

            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                $scope.flex.collectionView.itemsEdited[u].status = 'U';
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                $scope.flex.collectionView.itemsAdded[i].status = 'I';
                params.push($scope.flex.collectionView.itemsAdded[i]);
            }

            for (var m = 0; m < params.length; m++) {
                if(params[m].status !== 'D') {
                    //if(  params[m].addProdQty === null  || params[m].addProdQty === '' || params[m].addProdQty === 0 ) {
                    if(  params[m].addProdQty === null  || params[m].addProdQty === '') {
                        $scope._popMsg("상품 수량을 한 개 이상 입력해주세요.");
                        return false;
                    }
                }
            }

            // 구분이 '고정'인 상품의 수량합 체크(선택분류의 수량보다 크면 안됨)
            var chkFixProdCnt = 0;
            for (var m = 0; m < params.length; m++) {
                if(params[m].status !== 'D') {
                    if(params[m].fixProdFg === "1") {
                        chkFixProdCnt += parseInt(params[m].addProdQty);
                    }
                }
            }

            if(chkFixProdCnt > parseInt($scope.sdselQty)){
                $scope._popMsg(messages["sideMenu.selectMenu.fixProdCntChk.msg"] + "<br/> (고정상품수량합계 : " + chkFixProdCnt + " / 선택분류수량 : "  + $scope.sdselQty + ")"); // 구분이 '고정'인 상품의 수량합이 선택분류의 수량보다 클 수 없습니다.
                return false;
            }

            if(orgnFg == 'HQ') {
                // 적용매장 전체 삭제
                $scope._postJSONSave.withOutPopUp("/base/prod/sideMenu/menuProd/getSdselProdRegStoreDeleteAll.sb", params, function(){
                    // 저장
                    $scope.save(params);
                });
            } else {
                // 저장
                $scope.save(params);
            }

        });
    };

    // 저장
    $scope.save = function(params) {
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/sideMenu/menuProd/save.sb', params, function() {

            var classScope = agrid.getScope('newSdselClassCtrl');
            classScope.searchSdselClass();   // 그리드 초기화
            var prodScope2 = agrid.getScope('prodSelectCtrl');
            prodScope2.searchProd();   // 그리드 초기화

            $scope.searchSdselProd(); // 재조회
        });
    };
}]).factory('sdselClassCd', function () {
    // 사이드메뉴 선택분류 그리드 의 변수 값 영역
    var sdselClassCd = {};
    sdselClassCd.set = function (value) {
        sdselClassCd.value = value;
    };
    sdselClassCd.get = function () {
        return sdselClassCd.value;
    };
    return sdselClassCd;
});

// 상품조회
app.controller('prodSelectCtrl', ['$scope', '$http', '$timeout', 'sdselClassCd', function ($scope, $http, $timeout, sdselClassCd) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodSelectCtrl', $scope, $http, true));

    // sdselClassCd Data Setter
    $scope.setSdselClassCd = function (value) {
        sdselClassCd.set(value);
    };
    // sdselClassCd Data Getter
    $scope.getSdselClassCd = function () {
        return sdselClassCd.get();
    };

    // 등록일자 셋팅
    $scope.srchGroupStartDate = wcombo.genDateVal("#srchGroupStartDate", gvStartDate);
    $scope.srchGroupEndDate   = wcombo.genDateVal("#srchGroupEndDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchBrand", userHqBrandCdComboList);
    $scope._setComboData("srchUseYn", useYn);
    $scope._setComboData('srchProdTypeFg', prodTypeFg);

    // 등록일자 기본 '전체기간'으로
    $scope.isGroupChecked = true;

    $scope.initGrid = function (s, e) {

        // 등록일자 기본 '전체기간'으로
        $scope.srchGroupStartDate.isReadOnly = $scope.isGroupChecked;
        $scope.srchGroupEndDate.isReadOnly = $scope.isGroupChecked;

        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
            }
        });
    };

    $scope.$on("prodSelectCtrl", function(event, data) {
        $scope.setSdselClassCd(data.sdselClassCd);
        // 상품조회
        $scope.searchProd();
        event.preventDefault();
    });

    // 상품조회
    $scope.searchProd = function(){
        if($scope.getSdselClassCd() === "" || $scope.getSdselClassCd() === undefined){
            $scope._popMsg("선택분류를 선택 후 조회해주세요");
            return false;
        }

        var params = [];
        params.chkDt = $scope.isGroupChecked;
        params.startDate = wijmo.Globalize.format($scope.srchGroupStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchGroupEndDate.value, 'yyyyMMdd');
        params.hqBrandCd = $scope.srchBrandCombo.selectedValue;
        params.prodCd = $scope.srchProdCd;
        params.prodNm = $scope.srchProdNm;
        params.prodClassCd = $scope.prodClassCd;
        params.barCd = $("#srchBarCd").val();
        params.useYn = $scope.srchUseYnCombo.selectedValue;
        params.prodTypeFg = $scope.srchProdTypeFgCombo.selectedValue;
        params.sdselClassCd = $scope.getSdselClassCd();

        $scope._inquirySub("/base/prod/prod/prod/getNewSdselProdList.sb", params, function() {}, false);
    };

    // 상품등록
    $scope.regProd = function () {

        if($scope.getSdselClassCd() === "" || $scope.getSdselClassCd() === undefined){
            $scope._popMsg("선택분류를 선택 후 등록해주세요");
            return false;
        }
        
        $scope._popConfirm(messages["printerGroup.addChk.msg"], function () {
            $scope.flex.collectionView.commitEdit();

            var params = new Array();

            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                if ($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].status = 'I';
                    $scope.flex.collectionView.items[i].sdselClassCd = $scope.getSdselClassCd();
                    $scope.flex.collectionView.items[i].addProdUprc = 0;
                    $scope.flex.collectionView.items[i].addProdQty = 1;
                    $scope.flex.collectionView.items[i].fixProdFg = '0';
                    $scope.flex.collectionView.items[i].regStoreFg = '0';
                    $scope.flex.collectionView.items[i].printYn = 'Y';

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/prod/prod/insertSdselProdList.sb", params, function () {

                // 상품 재조회
                $scope.searchProd();

                var classScope = agrid.getScope('newSdselClassCtrl');
                classScope.searchSdselClass();
                var prodScope = agrid.getScope('newSdselProdCtrl');
                prodScope.searchSdselProd();

            });
        });
    };

    //전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchGroupStartDate.isReadOnly = $scope.isGroupChecked;
        $scope.srchGroupEndDate.isReadOnly = $scope.isGroupChecked;
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

}]).factory('sdselClassCd', function () {
    // 사이드메뉴 선택분류 그리드 의 변수 값 영역
    var sdselClassCd = {};
    sdselClassCd.set = function (value) {
        sdselClassCd.value = value;
    };
    sdselClassCd.get = function () {
        return sdselClassCd.value;
    };
    return sdselClassCd;
});
