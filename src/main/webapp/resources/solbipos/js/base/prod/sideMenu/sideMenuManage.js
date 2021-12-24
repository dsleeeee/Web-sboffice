/****************************************************************
 *
 * 파일명 : sideMenuManage.js
 * 설  명 : 사이드메뉴>사이드메뉴관리 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.21     이다솜      1.0
 *
 * **************************************************************/

/**
 * 사이드메뉴관리 그리드 생성
 */

// 상품등록구분
var regFgData = [
    {"name":"본사","value":"H"},
    {"name":"매장","value":"S"}
];

// 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
var useYnComboData = [
    {"name": "전체", "value": ""},
    {"name": "사용", "value": "Y"},
    {"name": "미사용", "value": "N"}
];

app.controller('sideMenuManageCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sideMenuManageCtrl', $scope, $http, false));

    // 페이지 스케일 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    // 브랜드 콤보박스 셋팅
    $scope._setComboData("srchHqBrand", brandList);
    // 사이드메뉴여부 콤보박스 셋팅
    $scope._setComboData("sideProdYnComboData", sideProdYnData);
    $scope._setComboData("chgSideProdYn", useYnData);
    //사이드메뉴-속성 콤보박스 셋팅
    $scope._setComboData("sdattrClassCdComboData", sdattrClassList);
    $scope._setComboData("chgSdattrClassCd", sdattrClassList.slice(1, sdattrClassList.length));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.regFgDataMap = new wijmo.grid.DataMap(regFgData, 'value', 'name'); // 상품등록구분
        $scope.saleProdYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name');         // 판매상품여부
        $scope.stockProdYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name');        // 재고상품여부
        $scope.vatFgDataMap = new wijmo.grid.DataMap(vatFgData, 'value', 'name');               // 부과세구분
        $scope.sideProdYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name');         // 사이드메뉴여부
        $scope.sdattrClassDataMap = new wijmo.grid.DataMap(sdattrClassList.slice(1, sdattrClassList.length), 'value', 'name'); // 속성
        $scope.sdselGrpDataMap = new wijmo.grid.DataMap(sdselGrpList.slice(1, sdselGrpList.length), 'value', 'name');        // 선택메뉴
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name');               // 사용여부
        $scope.poProdFgDataMap = new wijmo.grid.DataMap(poProdFgData, 'value', 'name');        // 발주상품구분
        $scope.prodTipYnDataMap = new wijmo.grid.DataMap(useYnData, 'value', 'name');          // 상품봉사료여부

        // 프랜 매장일때만
        if(orgnFg == "STORE" && hqOfficeCd != "00000") {
            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];

                    // 체크박스
                    if (col.binding === "gChk" || col.binding === "sideProdYn" || col.binding === "sdattrClassCd" || col.binding === "sdselGrpCd") {
                        var item = s.rows[e.row].dataItem;

                        // 값이 있으면 링크 효과
                        if (item[("regFg")] === 'H') {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            item[("gChk")] = false; // 전체 체크시 오류

                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }
                }
            });
        }
    };

    // 사이드메뉴관리 그리드 조회
    $scope.$on('sideMenuManageCtrl', function(event, data) {
        
        // 조회
        $scope.searchSideMenuManage();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.searchSideMenuManage = function(){
        var params = {};

        params.prodClassCd = $scope.prodClassCd;
        params.hqBrandCd = $scope.hqBrandCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.sideProdYn = $scope.sideProdYn;
        params.sdattrClassCd = $scope.sdattrClassCd;
        params.sdselGrpCd = $("#sdselGrpCd").val();

        $scope._inquiryMain("/base/prod/sideMenu/menuProd/getSideMenuManageProdList.sb", params, function() {}, false);
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope          = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd    = scope.getSelectedClass();
                var params         = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function (response) {
                        $scope.prodClassCd   = prodClassCd;
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

    // 사이드상품여부 선택에 따른 속성, 선택메뉴 display 여부
    $scope.setSdattrClassCd = function (s) {
        if(s.selectedValue === "Y"){
            $("#trAttrSelectMenu").css("display", "");
        }else{
            // 속성 선택값 초기화
            $scope.srchSdattrClassCdCombo.selectedValue = "";

            // 선텍메뉴 선택값 초기화
            $("#sdselGrpCd").val("");
            $("#sdselGrpNm").val(messages["cmm.select"]);

            $("#trAttrSelectMenu").css("display", "none");
        }
    };

    // 검색용, 일괄적용용 선택메뉴 팝업
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.sdselGrpShow = function () {
        $scope._broadcast('sdselGrpCtrl');
    };

    $scope.chgSdselGrpShow = function () {
        $scope._broadcast('chgSdselGrpCtrl');
    };

    // 일괄적용
    $scope.prodBatchChange = function(chgGubun) {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        // 일괄적용할 선택메뉴를 선택하세요.
        if(chgGubun === "chgSdselGrp"){
            if($("#chgSdselGrpCd").val() === null || $("#chgSdselGrpCd").val() === undefined ||  $("#chgSdselGrpCd").val() === ""){
                s_alert.pop(messages["sideMenu.manage.selectMenuChk"]);
                return;
            }
        }

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                // 사이드메뉴여부
                if(chgGubun === "chgSideProdYn"){
                    $scope.flex.collectionView.items[i].sideProdYn = $scope.chgSideProdYnCombo.selectedValue;
                }
                // 속성
                else if(chgGubun === "chgSdattrClass") {
                    $scope.flex.collectionView.items[i].sdattrClassCd = $scope.chgSdattrClassCdCombo.selectedValue;
                }
                // 선택메뉴
                else if(chgGubun === "chgSdselGrp") {
                    $scope.flex.collectionView.items[i].sdselGrpCd = $("#chgSdselGrpCd").val();
                }
            }
        }
        $scope.flex.refresh();
    };

    // 저장
    $scope.prodBatchSave = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 프랜 매장일때만
            if(orgnFg == "STORE" && hqOfficeCd != "00000") {
                for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                    if($scope.flex.collectionView.items[i].gChk) {
                        // REG_FG 상품등록구분 S인 상품만 수정가능
                        if ($scope.flex.collectionView.items[i].regFg === "H") {
                            $scope._popMsg(messages["prodBatchChange.regFgHqBlank"]); // 상품등록구분이 '본사'인 상품은 수정할 수 없습니다.
                            return false;
                        }
                    }

                    // REG_FG 상품등록구분 S인 상품만 수정가능
                    if ($scope.flex.collectionView.items[i].regFg === "H") {
                        $scope._popMsg(messages["prodBatchChange.regFgHqBlank"]); // 상품등록구분이 '본사'인 상품은 수정할 수 없습니다.
                        return false;
                    }
                }
            }

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/sideMenu/menuProd/saveSideMenuManageProdBatch.sb", params, function(){
                
                // 재조회
                $scope.searchSideMenuManage();
            });
        });
    };


}]);