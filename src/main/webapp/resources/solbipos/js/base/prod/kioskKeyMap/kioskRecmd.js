/****************************************************************
 *
 * 파일명 : kioskRecmd.js
 * 설  명 : 키오스크키 추천메뉴 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.08     권지현      1.0
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

var recmdType = [
    {"name": "베스트메뉴추천", "value": "0"},
    {"name": "단일상품", "value": "1"}
];

var dispType = [
    {"name": "3*2", "value": "1"},
    {"name": "3*3", "value": "2"}
];

var addType = [
    {"name": "메뉴교체", "value": "0"},
    {"name": "메뉴추가", "value": "1"}
];

// 추천메뉴코드(위 그리드)
app.controller('kioskRecmdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskRecmdCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {
        $("#recmdProdPopupNm").hide(); // 상품단일선택 팝업 사용시 밖 화면에 선택한 상품명이 떠서 그거 hide처리

        // 그리드에서 사용하는 dataMap 초기화
        $scope.recmdTypeDataMap  = new wijmo.grid.DataMap(recmdType, 'value', 'name');
        $scope.dispTypeDataMap  = new wijmo.grid.DataMap(dispType, 'value', 'name');;
        $scope.addTypeDataMap  = new wijmo.grid.DataMap(addType, 'value', 'name');
        $scope.useYnDataMap  = new wijmo.grid.DataMap(useYn, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                if (col.binding === 'recmdCd') {
                    if(item.recmdCd !== '자동채번') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }

                if (col.binding === "recmdType") {
                    if(item.recmdType === "0") {
                        item.addType = "1";
                        item.recmdProdCd = "선택불가";
                        item.prodNm = "";
                    } else if(item.recmdType === "1" && item.recmdProdCd === "선택불가") {
                        item.recmdProdCd = "선택";
                        item.prodNm = "";
                    }
                }

                if (col.binding === 'recmdProdCd') {
                    if(item.recmdType !== "0"){
                        wijmo.addClass(e.cell, 'wijLink');
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
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                var rowNum = s.selection.row;
                if (col.binding === "recmdCd") {
                    if(selectedRow.recmdCd !== '자동채번') {
                        $scope._broadcast('recmdProdCtrl', selectedRow);
                        event.preventDefault();
                    }
                }

                if(col.binding === "recmdProdCd") {

                    if(selectedRow.recmdType === '1') {
                        if(selectedRow.recmdProdCd !== "선택"){
                            $("#recmdProdPopupCd").val(selectedRow.recmdProdCd);
                        }
                        var popup = $scope.wjrecmdProdPopupLayerS;
                        popup.shown.addHandler(function (s) {
                            // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
                            setTimeout(function() {
                                $scope._broadcast('recmdProdPopupCtrl');
                            }, 50)
                        });
                        // 팝업 닫을때
                        popup.show(true, function (s) {
                            $scope.setRecmdProdCd(rowNum);
                        });
                        event.preventDefault();
                    }
                }
            }
        });
    };

    $scope.$on("kioskRecmdCtrl", function(event, data) {
        $scope.btnSearchRecmd();
        event.preventDefault();

    });

    // 선택한 상품정보 그리드에 넣음
    $scope.setRecmdProdCd = function (data) {
        // 기능사용자 데이터 set
        if($("#recmdProdPopupCd").val() === null || $("#recmdProdPopupCd").val() === ""){
            $scope.flex.collectionView.items[data].recmdProdCd = "선택";
            $scope.flex.collectionView.items[data].prodNm = "";
        }else{
            $scope.flex.collectionView.items[data].recmdProdCd = $("#recmdProdPopupCd").val();
            $scope.flex.collectionView.items[data].prodNm = "";
        }

        // 저장시, 수정되어야 하기 때문에 해당 그룹은 edit 처리
        var item = $scope.flex.collectionView.items[data];
        if (item === null) return false;

        $scope.flex.collectionView.editItem(item);
        item.status = "U";

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();

        // 선택한 상품 초기화
        $("#recmdProdPopupCd").val("");
    }

    // 키오스크 추천메뉴 조회
    $scope.btnSearchRecmd = function(){
        var params = {};
        // 초기화
        $scope.reset();

        $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getRecmd.sb", params, function() {}, false);
    };

    // 위 그리드 행 추가
    $scope.addRow = function() {
        // 파라미터 설정
        var params = {};
        params.recmdCd = '자동채번';
        params.recmdType = '1';
        params.addType = '0';
        params.recmdProdCd = '선택';
        params.dispType = '1';
        params.useYn = 'Y';
        // 추가기능 수행 : 파라미터
        $scope._addRow(params, 2);
    };

    // <-- 그리드 행 삭제 -->
    $scope.delRow = function(){
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];

            if(item.gChk) {
                $scope.flex.collectionView.removeAt(i);
            }
        }
    };
    // <-- //그리드 행 삭제 -->

    // 저장
    $scope.save = function() {
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            var typeChk = 0;
            $scope.flex.collectionView.commitEdit();

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if ($scope.flex.collectionView.items[i].recmdType === "0"){
                    if(typeChk > 0){
                        $scope._popMsg(messages['kioskKeyMap.recmdTypeChk']);
                        return false;
                    }
                    typeChk ++;
                }
            }

            // 파라미터 설정
            var params = [];

            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                $scope.flex.collectionView.itemsAdded[i].status = "I";
                params.push($scope.flex.collectionView.itemsAdded[i]);
            }
            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                $scope.flex.collectionView.itemsEdited[u].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }
            for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
                $scope.flex.collectionView.itemsRemoved[d].status = "D";
                params.push($scope.flex.collectionView.itemsRemoved[d]);
            }

            $scope._save('/base/prod/kioskKeyMap/kioskKeyMap/saveRecmd.sb', params, function() {
                // 저장 후 재조회
                $scope.btnSearchRecmd();
            });
        });
    };

    // 초기화
    $scope.reset = function(){

        // 기존 POS번호, 키맵그룹, 카테고리(분류) 값 초기화
        $("#recmdCd").val("");
        $("#spanRecmd").text("");

        // 카테고리(분류), 키맵, 상품 관련 버튼 hidden 처리
        var divBtnRecmdProd = document.getElementById('divBtnRecmdProd');
        var divBtnProdSearch = document.getElementById('divBtnProdSearch');
        divBtnRecmdProd.style.visibility='hidden';
        divBtnProdSearch.style.visibility='hidden';

        // 상품 검색조건 초기화
        var scope = agrid.getScope('recmdProdListCtrl');

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
        // scope.useYnAllCombo.selectedIndex = 0;
        // scope.prodTypeFgAllCombo.selectedIndex = 0;
        // scope.regYnAllCombo.selectedIndex = 0;

        var storeScope = agrid.getScope('recmdProdCtrl');
        storeScope._gridDataInit();   // 그리드 초기화
        var storeScope = agrid.getScope('recmdProdListCtrl');
        storeScope._gridDataInit();   // 그리드 초기화

        // 상품 grid paging 초기화(숨기기.. 아예 없애는거 모름..)
        var recmdProdListCtrlPager = document.getElementById('recmdProdListCtrlPager');
        recmdProdListCtrlPager.style.visibility='hidden'
    };
    
    // 추천메뉴매장적용 팝업
    $scope.recmdStoreReg = function () {
        $scope.kioskRecmdStoreRegLayer.show(true);
        $scope._broadcast('kioskRecmdStoreRegCtrl');
        event.preventDefault();
    }

}]);

// 추천메뉴
app.controller('recmdProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('recmdProdCtrl', $scope, $http, true));

    $scope.initGrid = function (s, e) {};

    $scope.$on("recmdProdCtrl", function(event, data) {

        if(data !== undefined && !isEmptyObject(data)) {

            // 카테고리 관련 데이터 셋팅
            $("#spanRecmd").text("[" + data.recmdCd + "] ")
            $("#recmdCd").val(data.recmdCd);
        }
        
        // 추천상품 조회
        $scope.searchRecmdProdCd();

    });

    // 추천상품 조회
    $scope.searchRecmdProdCd = function () {

        var params = {};
        params.recmdCd = $("#recmdCd").val();

        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getRecmdProd.sb", params, function() {

            // 카테고리(분류)가 정상조회 되면 키맵관련 버튼 보이도록
            var divBtnRecmdProd = document.getElementById('divBtnRecmdProd');
            divBtnRecmdProd.style.visibility='visible'

            // 카테고리(분류)가 정상조회 되면 상품관련 버튼 보이도록
            var divBtnProdSearch = document.getElementById('divBtnProdSearch');
            divBtnProdSearch.style.visibility='visible'

            // 상품 조회
            var kioskProdGrid = agrid.getScope("recmdProdListCtrl");
            kioskProdGrid._pageView('recmdProdListCtrl', 1);

            // paging 영역 보이도록
            var recmdProdListCtrlPager = document.getElementById('recmdProdListCtrlPager');
            recmdProdListCtrlPager.style.visibility='visible'

        }, false);
    }

    // 추천상품 상위 순서 이동
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

    // 추천상품 하위 순서 이동
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

    // 추천상품 삭제
    $scope.delRowKeyMap = function () {
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
                $scope.flex.collectionView.removeAt(i);
            }
        }
    }

    // 추천상품 저장
    $scope.saveRecmdProd = function () {
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            $scope.flex.collectionView.items[i].recmdCd = $("#recmdCd").val();
            params.push($scope.flex.collectionView.items[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/kioskKeyMap/kioskKeyMap/saveRecmdProd.sb', params, function() {

            // 추천상품 재조회
            $scope.searchRecmdProdCd();

        });
    }

}]);

// 상품조회
app.controller('recmdProdListCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('recmdProdListCtrl', $scope, $http, true));

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // ComboBox 셋팅
    // $scope._setComboData('useYnAllComboData', useYnAllComboData);
    // $scope._setComboData('prodTypeFg', prodTypeFg);
    // $scope._setComboData('regYn', regYn);
    $scope._setComboData("srchRmProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드
    
    // 등록일자 기본 '전체기간'으로
    $scope.isChecked = true;

    $scope.initGrid = function (s, e) {

        // 등록일자 기본 '전체기간'으로
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;

        $scope.regYnDataMap = new wijmo.grid.DataMap(regYn, 'value', 'name');
    };

    $scope.$on("recmdProdListCtrl", function(event, data) {
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
        params.recmdCd = $("#recmdCd").val();
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
          params.prodHqBrandCd = $scope.srchRmProdHqBrandCdCombo.selectedValue;

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

        $scope._inquiryMain("/base/prod/kioskKeyMap/kioskKeyMap/getRecmdProdList.sb", params);

    }
    
    // 추천상품 등록
    $scope.regRecmdProd = function () {

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].recmdCd = $("#recmdCd").val();
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/kioskKeyMap/kioskKeyMap/addRecmdProd.sb", params, function(){

            // 상품 재조회
            $scope.searchProd()

            // 추천메뉴 재조회
            var kioskKeyMapGrid = agrid.getScope("recmdProdCtrl");
            kioskKeyMapGrid._pageView('recmdProdCtrl', 1);
            
        });
    }
}]);