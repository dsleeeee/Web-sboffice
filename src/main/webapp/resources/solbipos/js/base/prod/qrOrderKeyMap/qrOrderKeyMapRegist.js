/****************************************************************
 *
 * 파일명 : qrOrderKeyMapRegist.js
 * 설  명 : QR주문키맵등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.11.28     김유승      1.0
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
app.controller('qrOrderKeyMapRegistCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('qrOrderKeyMapRegistCtrl', $scope, $http, false));

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
                        $scope._broadcast('qrOrderKeyMapCtrl', selectedRow);
                        event.preventDefault();
                    }
                }
            }
        });
    };

    $scope.$on("qrOrderKeyMapRegistCtrl", function(event, data) {
        $scope.btnSearchCls();
        event.preventDefault();
    });

    // 키오스크 카테고리(분류) 조회
    $scope.btnSearchCls = function(){
        // 초기화
        $scope.reset();

        var params = {};
        if(orgnFg === "STORE") {params.posNo = '97'}
        params.tuClsType = '01';

        $scope._inquiryMain("/base/prod/qrOrderKeyMap/qrOrderKeyMap/getQrOrderCategory.sb", params, function() {
            if(orgnFg === "HQ" || (orgnFg === "STORE")){

                // 카테고리(분류)가 정상조회 되면 관련 버튼 보이도록
                var divBtnCls = document.getElementById('divBtnCls');
                divBtnCls.style.visibility='visible';

            }

            // 본사일때 매장수정가능한 키맵인 경우 수정X
            // var grid = wijmo.Control.getControl("#wjGridCategoryCls");
            // var rows = grid.rows;
            //
            // for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            //     var item = $scope.flex.collectionView.items[i];
            //
            //     if((orgnFg === "HQ" && item.storeModYn === "Y") || (orgnFg === "STORE" && kioskKeyEnvstVal === "2" && item.storeModYn === "N")){
            //         item.gChk = false;
            //         rows[i].isReadOnly = true;
            //     }
            // }
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
        var scope = agrid.getScope('qrOrderProdCtrl');
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
        scope.regYnAllCombo.selectedIndex = 2;

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
        var qrOrderProdCtrlPager = document.getElementById('qrOrderProdCtrlPager');
        qrOrderProdCtrlPager.style.visibility='hidden';
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

        var msg = messages["cmm.choo.delete"];

        $scope._popConfirm(msg, function () {

            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];
                if(item.gChk){
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 파라미터 설정
            var params = [];

            for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
                if (orgnFg === "STORE") {
                    $scope.flex.collectionView.itemsRemoved[d].posNo = '97';
                }
                $scope.flex.collectionView.itemsRemoved[d].tuClsType = '01';
                $scope.flex.collectionView.itemsRemoved[d].status = 'D';
                params.push($scope.flex.collectionView.itemsRemoved[d]);
            }

            // indexNo
            for(var s = 0, num = 1; s < $scope.flex.rows.length; s++, num++) {
                if ($scope.flex.collectionView.items[s].indexNo !== num) {
                    $scope.flex.collectionView.items[s].indexNo = num;
                    $scope.flex.collectionView.editItem($scope.flex.collectionView.items[s]);
                    $scope.flex.collectionView.items[s].status = "U";
                    $scope.flex.collectionView.commitEdit();
                    params.push($scope.flex.collectionView.items[s]);
                }
            }

            // 카테고리(분류)를 모두 삭제하는지 파악하기 위해
            var gridLength = $scope.flex.collectionView.items.length;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/qrOrderKeyMap/qrOrderKeyMap/saveQrOrderCategory.sb', params, function () {
                if (gridLength >= 0) {
                    // 카테고리분류 재조회
                    $scope.btnSearchCls();
                }
            });
        });
    };

    // 카테고리(분류) 저장
    $scope.saveCls = function () {

        var msg = messages["cmm.choo.save"];
        $scope._popConfirm(msg, function () {
            $scope.flex.collectionView.commitEdit();

            // 생성, 수정 Validation Check
            for (var m = 0; m < $scope.flex.collectionView.itemCount; m++) {
                if ($scope.flex.collectionView.items[m].tuClsNm === null || $scope.flex.collectionView.items[m].tuClsNm === '') {
                    $scope._popMsg(messages['qrOrderKeyMap.require.category.msg']); // 카테고리명을 입력해주세요.
                    return;
                }
            }

            // 파라미터 설정
            var params = [];

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

            // indexNo 재설정
            for(var s = 0, num = 1; s < $scope.flex.rows.length; s++, num++) {
                if ($scope.flex.collectionView.items[s].indexNo !== num) {
                    $scope.flex.collectionView.items[s].indexNo = num;
                    $scope.flex.collectionView.editItem($scope.flex.collectionView.items[s]);
                    $scope.flex.collectionView.items[s].status = "U";
                    $scope.flex.collectionView.commitEdit();
                }
            }

            // 수정
            for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
                if (orgnFg === "STORE") {
                    $scope.flex.collectionView.itemsEdited[u].posNo = '97';
                }
                $scope.flex.collectionView.itemsEdited[u].tuClsType = '01';
                $scope.flex.collectionView.itemsEdited[u].status = 'U';
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }

            // 추가
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if (orgnFg === "STORE") {
                    $scope.flex.collectionView.itemsAdded[i].posNo = '97';
                }
                $scope.flex.collectionView.itemsAdded[i].tuClsType = '01';
                $scope.flex.collectionView.itemsAdded[i].status = 'I';
                params.push($scope.flex.collectionView.itemsAdded[i]);
            }

            // 카테고리(분류)를 모두 삭제하는지 파악하기 위해
            var gridLength = $scope.flex.collectionView.items.length;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/qrOrderKeyMap/qrOrderKeyMap/saveQrOrderCategory.sb', params, function () {
                if (gridLength >= 0) {
                    // 카테고리분류 재조회
                    $scope.btnSearchCls();
                }
            });
        });
    };

    // QR동기화
    $scope.QRSynchronize = function () {

        var params = {};

        $scope._postJSONQuery.withPopUp('/base/prod/qrOrderKeyMap/qrOrderKeyMap/getQRSynchronize.sb', params, function (response) {

            var data = response.data.data.list;

            if (data.status === 200) {

                $scope._popMsg(data.message);
                $scope._popMsg(data.data);

            }else{
                $scope._popMsg(data.message);
            }
        });
    };

}]);


// 키오스크 키맵
app.controller('qrOrderKeyMapCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('qrOrderKeyMapCtrl', $scope, $http, false));

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

    $scope.$on("qrOrderKeyMapCtrl", function(event, data) {
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

        console.log(0);
        var params = {};
        if(orgnFg === "STORE") {params.posNo = '97'}
        params.tuClsType = '01';
        params.tuClsCd = $("#hdTuClsCd").val();

        $scope._inquirySub("/base/prod/qrOrderKeyMap/qrOrderKeyMap/getQrOrderKeyMap.sb", params, function() {
            // 본사 : storeMod = N
            // 프차매장 : 1249가 1 or 2인데 storeMod = Y
            // 단독매장
            // 카테고리(분류)가 정상조회 되면 키맵관련 버튼 보이도록
            var divBtnKeyMap = document.getElementById('divBtnKeyMap');
            divBtnKeyMap.style.visibility='visible';

            // 카테고리(분류)가 정상조회 되면 상품관련 버튼 보이도록
            var divBtnProd = document.getElementById('divBtnProd');
            divBtnProd.style.visibility='visible';

            // paging 영역 보이도록
            var qrOrderProdCtrlPager = document.getElementById('qrOrderProdCtrlPager');
            qrOrderProdCtrlPager.style.visibility='visible';

            // 상품 조회
            var tableOrderProdGrid = agrid.getScope("qrOrderProdCtrl");
            tableOrderProdGrid._pageView('qrOrderProdCtrl', 1);
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
        var msg = messages["cmm.choo.delete"] + '<br/>('+ $("#spanTuKeyCls").text() + ")";

        $scope._popConfirm(msg, function () {

            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];
                if (item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 파라미터 설정
            var params = [];

            for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
                if (orgnFg === "STORE") {
                    $scope.flex.collectionView.itemsRemoved[d].posNo = '97'
                }
                $scope.flex.collectionView.itemsRemoved[d].tuClsType = '01';
                $scope.flex.collectionView.itemsRemoved[d].tuClsCd = $("#hdTuClsCd").val();
                $scope.flex.collectionView.itemsRemoved[d].status = 'D';
                params.push($scope.flex.collectionView.itemsRemoved[d]);
            }

            for (var s = 0, num = 1; s < $scope.flex.rows.length; s++, num++) {
                if ($scope.flex.collectionView.items[s].indexNo !== num) {
                    $scope.flex.collectionView.items[s].indexNo = num;
                    $scope.flex.collectionView.editItem($scope.flex.collectionView.items[s]);
                    $scope.flex.collectionView.items[s].status = "U";
                    $scope.flex.collectionView.items[s].tuClsCd = $("#hdTuClsCd").val();
                    $scope.flex.collectionView.commitEdit();
                    params.push($scope.flex.collectionView.items[s]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/qrOrderKeyMap/qrOrderKeyMap/updateQrOrderKeyMap.sb', params, function() {

                // 키맵 재조회
                $scope.searchKeyMap();

            });
        });
    };

    // 키맵 저장
    $scope.saveKeyMap = function () {

        var msg = messages["cmm.choo.save"] + '<br/>('+ $("#spanTuKeyCls").text() + ")";

        $scope._popConfirm(msg, function () {
            $scope.flex.collectionView.commitEdit();

            // 파라미터 설정
            var params = [];


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

            // indexNo 재설정
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
                    $scope.flex.collectionView.itemsEdited[u].posNo = '97';
                }
                $scope.flex.collectionView.itemsEdited[u].tuClsType = '01';
                $scope.flex.collectionView.itemsEdited[u].tuClsCd = $("#hdTuClsCd").val();
                $scope.flex.collectionView.itemsEdited[u].status = 'U';
                params.push($scope.flex.collectionView.itemsEdited[u]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/qrOrderKeyMap/qrOrderKeyMap/updateQrOrderKeyMap.sb', params, function () {

                // 키맵 재조회
                $scope.searchKeyMap();

            });
        });
    }

    // 엑셀다운로드
    $scope.excelDownload = function(){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    // return column.visible;
                    return column.binding != 'gChk';
                }
            }, 'QR주문키맵등록(터치키목록)' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    }

}]);


// 키오스크 상품
app.controller('qrOrderProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('qrOrderProdCtrl', $scope, $http, false));

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

        $scope.regYnAllCombo.selectedIndex = 2;
        $scope.useYnAllCombo.selectedIndex = 1;

    };

    $scope.$on("qrOrderProdCtrl", function(event, data) {
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
        if(orgnFg === "STORE") { params.posNo = '97'}
        params.tuClsType = '01';
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

        $scope._inquiryMain("/base/prod/qrOrderKeyMap/qrOrderKeyMap/getQrOrderProdList.sb", params, function() {}, false);
    };

    // 키맵 등록
    $scope.regProd = function () {

        var msg = messages["cmm.choo.save"];
        $scope._popConfirm(msg, function () {
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if ($scope.flex.collectionView.items[i].gChk) {
                    if (orgnFg === "STORE") {
                        $scope.flex.collectionView.items[i].posNo = '97'
                    }
                    $scope.flex.collectionView.items[i].tuClsType = '01';
                    $scope.flex.collectionView.items[i].tuClsCd = $("#hdTuClsCd").val();
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/qrOrderKeyMap/qrOrderKeyMap/saveQrOrderKeyMap.sb", params, function () {
                // 상품 재조회
                $scope.searchProd();

                // 키맵 재조회
                var qrOrderKeyMapGrid = agrid.getScope("qrOrderKeyMapCtrl");
                qrOrderKeyMapGrid._pageView('qrOrderKeyMapCtrl', 1);
            });
        });
    };

    // 엑셀다운로드
    $scope.excelDownload = function(){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    // return column.visible;
                    return column.binding != 'gChk';
                }
            }, 'QR주문키맵등록(상품목록)' + getCurDateTime() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    }

}]);