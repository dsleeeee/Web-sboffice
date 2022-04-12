/****************************************************************
 *
 * 파일명 : hqSalePriceResveAdd.js
 * 설  명 : 가격예약(본사판매가) 추가 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.04.05     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품삭제 팝업생성
 */
app.controller('hqSalePriceResveAddCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqSalePriceResveAddCtrl', $scope, $http, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox2", gvListScaleBoxData);

    $scope.popApplyFg = true;
    $scope.popSaleUprcApply = true;

    // 상품정보
    $scope.prodInfo;
    $scope.setProdInfo = function(data){
        $scope.prodInfo = data;
    };
    $scope.getProdInfo = function(){
        return $scope.prodInfo;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        // 예약일시 날짜셋팅
        $scope.startDateCombo.value = getTomorrow('-');
        $scope.endDateCombo.value = "9999-12-31";

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "saleUprc" || col.binding === "stinSaleUprc" || col.binding === "dlvrSaleUprc" || col.binding === "packSaleUprc") {
                    $scope.checked(item);
                }
                // 판매가 변경시 다른 컬럼값도 변경
                if (col.binding === "saleUprc") {
                    $scope.calcAmt(item);
                    if($scope.popSaleUprcApply){
                        $scope.saleUprc(item);
                    }
                }
            }
            s.collectionView.commitEdit();
        });

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());
        // 첫째줄 헤더 생성
        var dataItem                  = {};
        dataItem.gChk                 = messages["cmm.chk"];
        dataItem.prodCd               = messages["salePriceResve.prodCd"];
        dataItem.prodNm               = messages["salePriceResve.prodNm"];
        dataItem.hqSaleUprc           = messages["salePriceResve.salePrice"];
        dataItem.saleUprc             = messages["salePriceResve.salePrice"];
        dataItem.hqStinSaleUprc       = messages["salePriceResve.stinSaleUprc"];
        dataItem.stinSaleUprc         = messages["salePriceResve.stinSaleUprc"];
        dataItem.hqDlvrSaleUprc       = messages["salePriceResve.dlvrSaleUprc"];
        dataItem.dlvrSaleUprc         = messages["salePriceResve.dlvrSaleUprc"];
        dataItem.hqPackSaleUprc       = messages["salePriceResve.packSaleUprc"];
        dataItem.packSaleUprc         = messages["salePriceResve.packSaleUprc"];
        dataItem.prcCtrlFg            = messages["salePriceResve.prcCtrlFg"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging    = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display    : 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display      : 'table-cell',
                    verticalAlign: 'middle',
                    textAlign    : 'center'
                });
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
            else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
                // GroupRow 인 경우에는 표시하지 않는다.
                if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                    cell.textContent = '';
                } else {
                    if (!isEmpty(panel._rows[r]._data.rnum)) {
                        cell.textContent = (panel._rows[r]._data.rnum).toString();
                    } else {
                        cell.textContent = (r + 1).toString();
                    }
                }
            }
            // readOnly 배경색 표시
            else if (panel.cellType === wijmo.grid.CellType.Cell) {
                var col = panel.columns[c];
                if (col.isReadOnly) {
                    wijmo.addClass(cell, 'wj-custom-readonly');
                }
            }
        }
    };

    // 조회
    $scope.$on("hqSalePriceResveAddCtrl", function(event, data) {

        // 상품리스트 조회
        $scope.searchHqSalePriceList();
        event.preventDefault();
    });

    // 상품리스트 조회
    $scope.searchHqSalePriceList = function () {

        var params = {};
        params.prodClassCd = $scope.prodClassCd;
        params.listScale = $scope.listScaleCombo2.text;
        params.prodCd = $("#srchPopProdCd").val();
        params.prodNm = $("#srchPopProdNm").val();

        $scope._inquirySub('/base/price/salePrice/hqSalePrice/getHqSalePriceList.sb', params, function() {

            // 조회한 값으로 마진금액, 마진율 계산
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                $scope.calcAmt($scope.flex.collectionView.items[i]);
                $scope.flex.collectionView.commitEdit();
            }

            // 가격관리구분에 의해 본사는 매장의 상품가격 수정 불가(매장판매가관리본사강제수정 가능인 경우는 수정가능)
            if(coercionFg === "0") {
                var grid = wijmo.Control.getControl("#wjGridHqSalePricePop");
                var rows = grid.rows;

                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    var item = $scope.flex.collectionView.items[i];
                    if (item.prcCtrlFg === "S") {
                        item.gChk = false;
                        rows[i].isReadOnly = true;
                    }
                }
            }

        }, false);
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass2 = function() {
        var popUp = $scope.prodClassPopUpLayer.show(true, function (s) {
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
                        $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass2 = function(){
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

    // 가격 변경
    $scope.calcAmt = function(item){
        var hqCostUprc = item.hqCostUprc;
        var hqSplyUprc = item.hqSplyUprc;
        //var storeSplyUprc = item.storeSplyUprc;
        // var hqSaleUprc = item.hqSaleUprc;
        var saleUprc = item.saleUprc;
        var poUnitQty =  item.poUnitQty;

        item.hqMarginAmt = (hqSplyUprc - hqCostUprc); // 본사마진금액
        item.hqMarginRate = (hqSplyUprc - hqCostUprc) / hqCostUprc * 100; // 본사마진율
        // item.saleUprcAmt = (saleUprc - poUnitQty); // 현재판매금액
        // item.storeMarginAmt = ((saleUprc - poUnitQty) - storeSplyUprc); // 매장마진금액
        // item.storeMarginRate = ((saleUprc - poUnitQty) - storeSplyUprc) / (saleUprc - poUnitQty) * 100; // 매장마진율
    };

    // 일괄변경
    $scope.saleUprc = function (item){
        item.stinSaleUprc = item.saleUprc;
        item.dlvrSaleUprc = item.saleUprc;
        item.packSaleUprc = item.saleUprc;
    };

    // 판매가 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    }

    // 저장
    $scope.saveProdPrice2 = function(){
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if(coercionFg === "0"){
                    if ($scope.flex.collectionView.items[i].prcCtrlFg === "S") {
                        $scope._popMsg(messages["salePriceResve.prcCtrlFgStoreBlank"]); // 가격관리구분이 '매장'인 상품은 수정할 수 없습니다.
                        return false;
                    }
                }
            }
        }
        
        // 예약날짜 체크
        var date = new Date();
        var year = new String(date.getFullYear());
        var month = new String(date.getMonth()+1);
        var day = new String(date.getDate());

        // 한자리수일 경우 0을 채워준다.
        if(month.length == 1){
            month = "0" + month;
        }
        if(day.length == 1){
            day = "0" + day;
        }
        var now = year + "" + month + "" + day;
        var vStartDate = wijmo.Globalize.format($scope.startDateCombo.value, 'yyyyMMdd');
        var vEndDate = wijmo.Globalize.format($scope.endDateCombo.value, 'yyyyMMdd');

        if(Number(now) >= Number(vStartDate)) {
            $scope._popMsg(messages["salePriceResve.startDate"] + "는 " + messages["salePriceResve.resveDate.chk.msg"]);
            return false;
        }
        if(Number(now) >= Number(vEndDate)){
            $scope._popMsg(messages["salePriceResve.endDate"] + "는 " + messages["salePriceResve.resveDate.chk.msg"]);
            return false;
        }

        if(Number(vStartDate) > Number(vEndDate)){
            $scope._popMsg(messages["salePriceResve.resveDate"] + messages["salePriceResve.resveDate.chk.msg2"]);
            return false;
        }

        var numchkexp = /[^0-9]/g; // 숫자가 아닌 값 체크
        var numchkexp2 = /^-[0-9]/g;

        // 파라미터 설정
        var params = new Array();

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {

                // 변경판매가 - 필수 입력값 체크
                if ($scope.flex.collectionView.items[i].saleUprc === "" || $scope.flex.collectionView.items[i].saleUprc === null) {
                    $scope._popMsg(messages["salePriceResve.saleUprcBlank"]);
                    return false;
                }

                // isInteger는 es6 임. ie 11 에서는 안되므로 함수 만듬.
                Number.isInteger = Number.isInteger || function(value) {
                    return typeof value === "number" &&
                        isFinite(value) &&
                        Math.floor(value) === value;
                };

                // 변경판매가 - 소수점 입력 불가
                if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].saleUprc)) == false){
                    $scope._popMsg(messages["salePriceResve.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                    return false;
                }

                // 변경판매가 - 마이너스(-)외에 다른 문자 입력 불가
                if (numchkexp.test($scope.flex.collectionView.items[i].saleUprc)) {
                    if((numchkexp2.test($scope.flex.collectionView.items[i].saleUprc) == false)){
                        $scope._popMsg(messages["salePriceResve.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }
                }

                // 변경판매가 - 1000000000 이상 입력 불가
                if($scope.flex.collectionView.items[i].saleUprc >= 1000000000){
                    $scope._popMsg(messages["salePriceResve.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                    return false;
                }

                // 내점/배달/포장 판매가 사용 시
                if(subPriceFg === "1") {

                    // 변경내점-판매가 입력했을 경우 체크
                    if ($scope.flex.collectionView.items[i].stinSaleUprc !== "" && $scope.flex.collectionView.items[i].stinSaleUprc !== null) {

                        // 변경내점-판매가 - 소수점 입력 불가
                        if (Number.isInteger(parseFloat($scope.flex.collectionView.items[i].stinSaleUprc)) == false) {
                            $scope._popMsg(messages["salePriceResve.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        }

                        // 변경내점-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                        if (numchkexp.test($scope.flex.collectionView.items[i].stinSaleUprc)) {
                            if ((numchkexp2.test($scope.flex.collectionView.items[i].stinSaleUprc) == false)) {
                                $scope._popMsg(messages["salePriceResve.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }

                        // 변경내점-판매가 - 1000000000 이상 입력 불가
                        if ($scope.flex.collectionView.items[i].stinSaleUprc >= 1000000000) {
                            $scope._popMsg(messages["salePriceResve.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        }
                    }

                    // 변경배달-판매가 입력했을 경우 체크
                    if ($scope.flex.collectionView.items[i].dlvrSaleUprc !== "" && $scope.flex.collectionView.items[i].dlvrSaleUprc !== null) {

                        // 변경배달-판매가 - 소수점 입력 불가
                        if (Number.isInteger(parseFloat($scope.flex.collectionView.items[i].dlvrSaleUprc)) == false) {
                            $scope._popMsg(messages["salePriceResve.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        }

                        // 변경배달-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                        if (numchkexp.test($scope.flex.collectionView.items[i].dlvrSaleUprc)) {
                            if ((numchkexp2.test($scope.flex.collectionView.items[i].dlvrSaleUprc) == false)) {
                                $scope._popMsg(messages["salePriceResve.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }

                        // 변경배달-판매가 - 1000000000 이상 입력 불가
                        if ($scope.flex.collectionView.items[i].dlvrSaleUprc >= 1000000000) {
                            $scope._popMsg(messages["salePriceResve.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        }
                    }

                    // 변경포장-판매가 입력했을 경우 체크
                    if ($scope.flex.collectionView.items[i].packSaleUprc !== "" && $scope.flex.collectionView.items[i].packSaleUprc !== null) {

                        // 변경포장-판매가 - 소수점 입력 불가
                        if (Number.isInteger(parseFloat($scope.flex.collectionView.items[i].packSaleUprc)) == false) {
                            $scope._popMsg(messages["salePriceResve.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        }

                        // 변경포장-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                        if (numchkexp.test($scope.flex.collectionView.items[i].packSaleUprc)) {
                            if ((numchkexp2.test($scope.flex.collectionView.items[i].packSaleUprc) == false)) {
                                $scope._popMsg(messages["salePriceResve.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }

                        // 변경포장-판매가 - 1000000000 이상 입력 불가
                        if ($scope.flex.collectionView.items[i].packSaleUprc >= 1000000000) {
                            $scope._popMsg(messages["salePriceResve.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        }
                    }
                }
                $scope.flex.collectionView.items[i].applyFg = $scope.popApplyFg;
                $scope.flex.collectionView.items[i].startDate = wijmo.Globalize.format($scope.startDateCombo.value, 'yyyyMMdd');
                $scope.flex.collectionView.items[i].endDate = wijmo.Globalize.format($scope.endDateCombo.value, 'yyyyMMdd');
                params.push($scope.flex.collectionView.items[i]);

            }
        }

        if ($scope.popApplyFg) {
            $scope._popConfirm( "하위매장에 가격이 적용됩니다. 그래도 저장하시겠습니까?", function(){
                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._save('/base/price/salePriceResve/hqSalePriceResve/saveHqSalePriceResve.sb', params, function(){

                    // 부모창 재조회
                    var vScope = agrid.getScope("hqSalePriceResveCtrl");
                    vScope.searchHqSalePriceResveList();

                    // 팝업 닫기
                    $scope.hqSalePriceResveAddLayer.hide(true);
                    $scope.close();

                });
            });
        } else {
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/price/salePriceResve/hqSalePriceResve/saveHqSalePriceResve.sb', params, function(){

                // 부모창 재조회
                var vScope = agrid.getScope("hqSalePriceResveCtrl");
                vScope.searchHqSalePriceResveList();

                // 팝업 닫기
                $scope.hqSalePriceResveAddLayer.hide(true);
                $scope.close();

            });
        }

    };
    
    // 닫기
    $scope.close = function () {
        
        // 입력값 초기화
        $scope.delProdClass2();
        $("#srchPopProdCd").val("");
        $("#srchPopProdNm").val("");
        $scope.startDateCombo.value = getTomorrow('-');
        $scope.endDateCombo.value = "9999-12-31";
        $("input:checkbox[id='popSaleUprcApply']").prop("checked", true);
        $("input:checkbox[id='popApplyFg']").prop("checked", true);
        $scope.popApplyFg = true;
        $scope.popSaleUprcApply = true;

    };

}]);