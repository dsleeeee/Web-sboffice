/****************************************************************
 *
 * 파일명 : sdselProdCopy.js
 * 설  명 : 선택분류복사 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.14     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  선택분류복사 팝업 조회 그리드 생성
 */
app.controller('sdselProdCopyCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sdselProdCopyCtrl', $scope, $http, false));

    // 콤보박스 데이터 Set
    $scope._setComboData("srchCopyTypeSelGroup", vSrchTypeSelGroup);
    $scope._setComboData("srchCopyTypeSelProd", vSrchTypeSelProd);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on('sdselProdCopyCtrl', function(event, data) {

        // 적용할 그룹
        $("#srchApplyClassProd").val(data.sdselClassCdNm);
        $("#srchApplyClassProdCd").val(data.sdselClassCd);
        $("#srchApplyGroupProd").val(data.sdselGrpCdNm);
        $("#srchApplyGroupProdCd").val(data.sdselGrpCd);

        // 검색조건 초기화
        $scope.srchCopyTypeSelGroupCombo.selectedIndex = 0;
        $('#txtCopySelGroupProd').val("");
        $scope.srchCopyTypeSelProdCombo.selectedIndex = 0;
        $('#txtCopySelProdProd').val("");

        $scope.searchSdselProdCopy();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    $scope.searchSdselProdCopy = function(){
        // 선택그룹 조회
        $scope._broadcast('sdselProdCopyGroupCtrl');
        var classdGrid = agrid.getScope('sdselProdCopyClassCtrl');
        classdGrid._gridDataInit();
        var prodGrid = agrid.getScope('sdselProdCopyProdCtrl');
        prodGrid._gridDataInit();
    };
    // <-- //검색 호출 -->

    // 선택분류복사
    $scope.prodCopySave = function(){
        //  [000012]관리자테스트 에 체크한 선택분류를 복사하시겠습니까?
        var msg = $("#srchApplyClassProd").val() + " " + messages["sideMenu.sdselProdCopy.prodCopySaveConfirm"];
        $scope._popConfirm(msg, function() {

            var scopeGroup = agrid.getScope("sdselProdCopyGroupCtrl");
            var scopeClass = agrid.getScope("sdselProdCopyClassCtrl");
            var scopeProd = agrid.getScope("sdselProdCopyProdCtrl");

            // 파라미터 설정
            var params = [];
            for (var i = 0; i < scopeProd.flex.collectionView.items.length; i++) {
                if(scopeProd.flex.collectionView.items[i].gChk) {
                    scopeProd.flex.collectionView.items[i].applySdselGrpCd = $("#srchApplyGroupProdCd").val(); // 적용할 그룹
                    scopeProd.flex.collectionView.items[i].applySdselClassCd = $("#srchApplyClassProdCd").val(); // 적용할 분류
                    scopeProd.flex.collectionView.items[i].copySdselGrpCd = scopeGroup.selectedSelGroup.sdselGrpCd; // 복사할 그룹
                    scopeProd.flex.collectionView.items[i].copySdselClassCd = scopeClass.selectedSelClass.sdselClassCd; // 복사할 분류
                    scopeProd.flex.collectionView.items[i].copySdselProdCd = scopeProd.flex.collectionView.items[i].prodCd; // 복사할 상품
                    params.push(scopeProd.flex.collectionView.items[i]);
                }
            }

            if(params.length <= 0) {
                s_alert.pop(messages["cmm.not.select"]);
                return;
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp( "/base/prod/sideMenu/menuClass/getSdselProdCopySave.sb", params, function(response){
                // 하단 화면에 선택상품 리스트 재조회
                var prodGrid = agrid.getScope('sideMenuSelectProdCtrl');
                var selectedSelProd = prodGrid.getSelectedSdselProd();
                $scope._broadcast('sideMenuSelectProdCtrl', selectedSelProd);

                // 하단 화면에 선택분류 리스트 재조회
                var grpGrid = agrid.getScope('sideMenuSelectGroupCtrl');
                var selectedSelGroup = grpGrid.getSelectedSelGroup();
                $scope._broadcast('sideMenuSelectClassCtrl', selectedSelGroup);

                // 팝업 닫기
                $scope.close();
            });
        });
    };

    // 팝업 닫기
    $scope.close = function(){
        // 적용할 그룹
        $("#srchApplyGroupProd").val("");
        $("#srchApplyGroupProdCd").val("");
        $("#srchApplyClassProd").val("");
        $("#srchApplyClassProdCd").val("");

        $scope.wjSdselProdCopyLayer.hide();
    };
}]);


/**
 *  사이드메뉴 선택그룹 조회 그리드 생성
 */
app.controller('sdselProdCopyGroupCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sdselProdCopyGroupCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 내 콤보박스 설정
        $scope.fixProdFgDataMap = fixProdFgDataMap;
        $scope.sdselTypeFgDataMap = sdselTypeFgDataMap;
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name'); //하프앤하프(진행구분)

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === 'sdselGrpCd') {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== 'I') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 선택그룹 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            if (col.binding === 'sdselGrpCd') {
                var dataItem = s.rows[e.row].dataItem;
                if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
                    e.cancel = true;
                }
            }
        });

        // 선택그룹 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if ( col.binding === 'sdselGrpCd') {
                    if (selectedRow.sdselGrpCd !== '' && selectedRow.sdselGrpCd !== undefined && selectedRow.sdselGrpCd !== '자동채번') {
                        $("#sideSelectGroupCopyTitleProd").html(" [" + selectedRow.sdselGrpCd + "]" + selectedRow.sdselGrpNm);
                        $("#sideClassCopyTitleProd").html("");

                        $scope.setSelectedSelGroup(selectedRow);
                        $scope._broadcast('sdselProdCopyClassCtrl', selectedRow);
                        var prodGrid = agrid.getScope('sdselProdCopyProdCtrl');
                        prodGrid._gridDataInit();
                    }
                }
            }
        });
    };

    // 그리드 Refresh
    $scope.$on('selectMenuRefresh', function (event, data) {
        $scope.flex.refresh();
    });

    // 선택
    $scope.selectedSelGroup;
    $scope.setSelectedSelGroup = function(data) {
        $scope.selectedSelGroup = data;
    };
    $scope.getSelectedSelGroup = function(){
        return $scope.selectedSelGroup;
    };

    // <-- 검색 호출 -->
    $scope.$on('sdselProdCopyGroupCtrl', function(event, data) {
        $scope.srchSelGroup();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 선택그룹 그리드 조회
    $scope.srchSelGroup = function(){
        // 파라미터
        var params = {};
        params.sdselTypeFg = 'C';
        params.srchType = "";
        params.sdselGrpCd = "";
        params.sdselGrpNm = "";

        // 그룹명, 그룹코드 검색조건
        var vScope = agrid.getScope("sdselProdCopyCtrl");
        var srchTypeGroup = vScope.srchCopyTypeSelGroupCombo.selectedValue;
        if($("#txtCopySelGroupProd").val() !== ""){
            if(srchTypeGroup === "grpNm"){
                params.sdselGrpCd = "";
                params.sdselGrpNm = $("#txtCopySelGroupProd").val();
            }
            else if(srchTypeGroup === "grpCd"){
                params.sdselGrpCd = $("#txtCopySelGroupProd").val();
                params.sdselGrpNm = "";
            }
        }
        // 상품명, 상품코드 검색조건
        var srchTypeProd = vScope.srchCopyTypeSelProdCombo.selectedValue;
        if($("#txtCopySelProdProd").val() !== ""){
            if(srchTypeProd === "prodNm"){
                params.sdselProdCd = "";
                params.sdselProdNm = $("#txtCopySelProdProd").val();
            }
            else if(srchTypeProd === "prodCd"){
                params.sdselProdCd = $("#txtCopySelProdProd").val();
                params.sdselProdNm = "";
            }
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain('/base/prod/sideMenu/menuGrp/list.sb', params,function() {}, false);
    };
    // <-- //검색 호출 -->
}]);


/**
 *  사이드메뉴 선택분류 조회 그리드 생성
 */
app.controller('sdselProdCopyClassCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sdselProdCopyClassCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 내 콤보박스 설정
        $scope.requireYnDataMap = requireYnDataMap;
        $scope.regStoreFgDataMap = new wijmo.grid.DataMap(regStoreFgData, 'value', 'name'); // 적용매장구분
        $scope.oldRegStoreFgDataMap = new wijmo.grid.DataMap(regStoreFgData, 'value', 'name'); // 적용매장구분
        $scope.topYnDataMap = new wijmo.grid.DataMap(printYnData, 'value', 'name'); // 상단표기여부
        $scope.expandYnDataMap = new wijmo.grid.DataMap(printYnData, 'value', 'name'); // 펼치기여부
        $scope.mappingYnDataMap = new wijmo.grid.DataMap(printYnData, 'value', 'name'); // ERP상품맵핑여부

        if(hqOfficeCd == 'A0001') { // 보나비(A0001) 분류구분 별도 셋팅
            $scope.popUpClassYnDataMap = new wijmo.grid.DataMap(bonaviePopUpClassYnData, 'value', 'name'); // 분류구분
        } else {
            $scope.popUpClassYnDataMap = new wijmo.grid.DataMap(popUpClassYnData, 'value', 'name'); // 분류구분
        }

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === 'sdselClassCd') {
                    var item = s.rows[e.row].dataItem;
                    if (item.status !== 'I') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 선택분류 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            if (col.binding === 'sdselClassCd') {
                var dataItem = s.rows[e.row].dataItem;
                if (nvl(dataItem.status, '') === '' && dataItem.status !== 'I') {
                    e.cancel = true;
                }
            }
        });

        // 선택분류 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if(col.binding === 'sdselClassCd') {
                    if(selectedRow.sdselClassCd !== '' && selectedRow.sdselClassCd !== undefined && selectedRow.sdselClassCd !== '자동채번') {
                        $("#sideClassCopyTitleProd").html(" [" + selectedRow.sdselClassCd + "]" + selectedRow.sdselClassNm);

                        var params = {};
                        params.sdselClassCd = selectedRow.sdselClassCd;
                        params.sdselQty = selectedRow.sdselQty;
                        params.selGroupFixProdFg = $scope.getSelectedSelClassFixProdFg();
                        $scope.setSelectedSelClass(selectedRow);
                        $scope._broadcast('sdselProdCopyProdCtrl', params);
                    }
                }
            }
        });
    };

    // 그리드 Refresh
    $scope.$on('selectMenuRefresh', function (event, data) {
        $scope.flex.refresh();
    });

    // 선택
    $scope.selectedSelClass;
    $scope.setSelectedSelClass = function(data) {
        $scope.selectedSelClass = data;
    };
    $scope.getSelectedSelClass = function(){
        return $scope.selectedSelClass;
    };

    // 선택
    $scope.selectedSelClassFixProdFg;
    $scope.setSelectedSelClassFixProdFg = function(fixProdFg) {
        $scope.selectedSelClassFixProdFg = fixProdFg;
    };
    $scope.getSelectedSelClassFixProdFg = function(){
        return $scope.selectedSelClassFixProdFg;
    };

    // <-- 검색 호출 -->
    $scope.$on('sdselProdCopyClassCtrl', function(event, data) {
        // 변수 set - 고정여부
        $scope.setSelectedSelClassFixProdFg(data.fixProdFg);

        // 파라미터
        var params = {};
        params.sdselGrpCd = data.sdselGrpCd;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain('/base/prod/sideMenu/menuClass/list.sb', params,function() {
            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridSelClassCopyListProd");
            var columns = grid.columns;

            // 컬럼 총갯수
            var columnsCnt = 7;

            // 합계가 0이면 해당 컬럼 숨기기
            for (var j = 0; j < columnsCnt; j++) {
                // [1014 포스프로그램구분]
                if(posVerEnvstVal === "1") {
                    if(columns[j].binding == "requireYn") {
                        columns[j].visible = false;
                    }
                } else if(posVerEnvstVal === "2") {
                    // [1261 필수선택사용여부]
                    if(requireYnEnvstVal === "1") {
                        // 선택그룹 그리드에 고정여부
                        if($scope.getSelectedSelClassFixProdFg() === "1") {
                            if(columns[j].binding == "requireYn" || columns[j].binding == "sdselQty") {
                                columns[j].visible = false;
                            }
                        } else {
                            if(columns[j].binding == "requireYn" || columns[j].binding == "sdselQty") {
                                columns[j].visible = true;
                            }
                        }
                    } else if(requireYnEnvstVal === "0") {
                        if(columns[j].binding == "requireYn") {
                            columns[j].visible = false;
                        }
                    }
                }
            }
        }, false);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });
    // <-- //검색 호출 -->
}]);


/**
 *  사이드메뉴 선택상품 조회 그리드 생성
 */
app.controller('sdselProdCopyProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sdselProdCopyProdCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 내 콤보박스 설정
        $scope.brandDataMap = new wijmo.grid.DataMap(brandList, 'value', 'name'); // 브랜드
        $scope.fixProdFgDataMap = fixProdFgDataMap;
        $scope.regStoreFgDataMap = new wijmo.grid.DataMap(regStoreFgData, 'value', 'name'); // 적용매장구분
        $scope.oldRegStoreFgDataMap = new wijmo.grid.DataMap(regStoreFgData, 'value', 'name'); // 적용매장구분
        $scope.printYnDataMap = new wijmo.grid.DataMap(printYnData, 'value', 'name'); // 출력여부

        // 선택상품 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (s, e) {
            var col = s.columns[e.col];
            if (col.binding === 'prodCd' || col.binding === 'prodNm') {
                e.cancel = true;
            }
        });
    };

    // 그리드 Refresh
    $scope.$on('selectMenuRefresh', function (event, data) {
        $scope.flex.refresh();
    });

    // 선택분류의 수량 set
    $scope.sdselQty = 0;

    // <-- 검색 호출 -->
    $scope.$on('sdselProdCopyProdCtrl', function(event, data) {
        $scope.sdselQty = parseInt(data.sdselQty); // 선택분류의 수량
        var selGroupFixProdFg = data.selGroupFixProdFg; // 선택그룹의 고정여부

        // 파라미터
        var params = {};
        params.sdselClassCd = data.sdselClassCd;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
        $scope._inquiryMain('/base/prod/sideMenu/menuProd/list.sb', params,function() {
            // <-- 그리드 visible -->
            // 선택한 테이블에 따른 리스트 항목 visible
            var grid = wijmo.Control.getControl("#wjGridSelProdCopyListProd");
            var columns = grid.columns;

            // 컬럼 총갯수
            var columnsCnt = 6;

            // 합계가 0이면 해당 컬럼 숨기기
            for (var j = 0; j < columnsCnt; j++) {
                // 선택그룹 그리드에 고정여부
                if(selGroupFixProdFg === "1") {
                    if(columns[j].binding == "fixProdFg") {
                        columns[j].visible = false;
                    }
                } else {
                    if(columns[j].binding == "fixProdFg") {
                        columns[j].visible = true;
                    }
                }
            }
            // <-- //그리드 visible -->
        }, false);

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });
    // <-- //검색 호출 -->
}]);