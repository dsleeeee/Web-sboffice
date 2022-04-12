/****************************************************************
 *
 * 파일명 : hqSalePriceResve.js
 * 설  명 : 가격예약(본사판매가) JavaScript
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

// 판매가 선택 DropBoxDataMap
var saleAmtOptionFg = [
    {"name":"매장판매가","value":"S"},
    {"name":"본사판매가","value":"H"}
];
var stinSaleAmtOptionFg = [
    {"name":"매장판매가","value":"S"},
    {"name":"본사판매가","value":"H"},
    {"name":"매장내점-판매가","value":"SS"},
    {"name":"본사내점-판매가","value":"HS"}
];
var dlvrSaleAmtOptionFg = [
    {"name":"매장판매가","value":"S"},
    {"name":"본사판매가","value":"H"},
    {"name":"매장배달-판매가","value":"SD"},
    {"name":"본사배달-판매가","value":"HD"}
];
var packSaleAmtOptionFg = [
    {"name":"매장판매가","value":"S"},
    {"name":"본사판매가","value":"H"},
    {"name":"매장포장-판매가","value":"SP"},
    {"name":"본사포장-판매가","value":"HP"}
];

// 단위 DropBoxDataMap
var unitFg = [
    {"name":"1원 단위","value":"1"},
    {"name":"100원 단위","value":"100"},
    {"name":"1,000원 단위","value":"1000"}
];
// 반올림 DropBoxDataMap
var modeFg = [
    {"name":"반올림","value":"0"},
    {"name":"절상","value":"1"},
    {"name":"절하","value":"2"}
];

/**
 * 가격예약(본사판매가) 그리드 생성
 */
app.controller('hqSalePriceResveCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqSalePriceResveCtrl', $scope, $http, false));

    // 오늘날짜
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

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "prodCd") {
                    var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col         = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "prodCd") {
                    var params     = {};
                    params.prodCd = selectedRow.prodCd;
                    params.prodNm = selectedRow.prodNm;
                    $scope.hqSalePriceInfoLayer.show(true);
                    $scope._broadcast('hqSalePriceInfoCtrl', params);
                }
            }
        });

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "startDate" || col.binding === "endDate" ||
                    col.binding === "saleUprc" || col.binding === "stinSaleUprc" || col.binding === "dlvrSaleUprc" || col.binding === "packSaleUprc") {
                    $scope.checked(item);
                }
                // 판매가 변경시 다른 컬럼값도 변경
                if (col.binding === "saleUprc") {
                    $scope.calcAmt(item);
                    if($scope.saleUprcApply){
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
        dataItem.startDate            = messages["salePriceResve.startDate"];
        dataItem.endDate              = messages["salePriceResve.endDate"];
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

    // 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("saleAmtOption", saleAmtOptionFg);
    $scope._setComboData("changeUnit", unitFg);
    $scope._setComboData("changeMode", modeFg);

    $scope._setComboData("storeStinSaleUprcOption", stinSaleAmtOptionFg);
    $scope._setComboData("stinSaleUprcChangeUnit", unitFg);
    $scope._setComboData("stinSaleUprcChangeMode", modeFg);

    $scope._setComboData("storeDlvrSaleUprcOption", dlvrSaleAmtOptionFg);
    $scope._setComboData("dlvrSaleUprcChangeUnit", unitFg);
    $scope._setComboData("dlvrSaleUprcChangeMode", modeFg);

    $scope._setComboData("storePackSaleUprcOption", packSaleAmtOptionFg);
    $scope._setComboData("packSaleUprcChangeUnit", unitFg);
    $scope._setComboData("packSaleUprcChangeMode", modeFg);

    $scope.applyFg = true;
    $scope.saleUprcApply = true;

    // 조회
    $scope.$on("hqSalePriceResveCtrl", function(event, data) {
        $scope.searchHqSalePriceResveList();
        event.preventDefault();
    });

    // 판매가 그리드 조회
    $scope.searchHqSalePriceResveList = function(){

        var params = {};
        params.prodClassCd = $scope.prodClassCd;
        params.listScale = $scope.listScaleCombo.text;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;

        $scope._inquirySub('/base/price/salePriceResve/hqSalePriceResve/getHqSalePriceResveList.sb', params, function() {

            // 조회한 값으로 마진금액, 마진율 계산
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                $scope.calcAmt($scope.flex.collectionView.items[i]);
                $scope.flex.collectionView.commitEdit();
            }

            // 그리드 선택불가 항목처리
            $scope.setGridReadOnly();

        }, false);
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
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

    // 일괄변경 테이블 숨김/보임
    $scope.changeShow = function(){
        if( $("#tblHqChange").css("display") === 'none'){
            $("#tblHqChange").show();
        } else {
            $("#tblHqChange").hide();
        }
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

    // 상품정보
    $scope.prodInfo;
    $scope.setProdInfo = function(data){
        $scope.prodInfo = data;
    };
    $scope.getProdInfo = function(){
        return $scope.prodInfo;
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {
        // 상품분류 팝업 핸들러 추가
        $scope.prodClassPopUpLayer.shown.addHandler(function (s) {
        });
    });

    // 판매가 일괄적용 버튼 클릭
    // 매장판매가 일괄적용시, 입력한 매장판매가를 적용시킴.
    // 본사판매가 일괄적용시, 조회된 본사판매가를 적용시킴.
    $scope.changeAmt = function() {
        /*if( isEmptyObject( $("#searchStoreCd").val()) ) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }*/

        if( $scope.flex.collectionView === undefined){
            $scope._popMsg("판매가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

        var saleAmtOption = $scope.prodInfo.saleAmtOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if($("#inputSaleRate").val() === ""){
            $scope._popMsg("판매가 변경비율을 입력해 주세요.");
            return false;
        }

        var newSaleAmt = 0;

        // 변경 판매가 적용
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {

                newSaleAmt = 0;
                if(saleAmtOption === "S"){ // 매장
                    newSaleAmt = Number($scope.flex.collectionView.items[i].storeSaleUprc) * (Number($("#inputSaleRate").val())/100);
                }else if(saleAmtOption === "H"){ // 본사
                    newSaleAmt = Number($scope.flex.collectionView.items[i].hqSaleUprc) * (Number($("#inputSaleRate").val())/100);
                }

                $scope.flex.collectionView.items[i].saleUprc = $scope.calChangeAmt(newSaleAmt, "1");
                $scope.calcAmt($scope.flex.collectionView.items[i]);
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();

        // 그리드 선택불가 항목처리
        $scope.setGridReadOnly();
    };

    // 내점-판매가 일괄적용 버튼 클릭
    $scope.changeStinSaleUprc = function(){

        /*if( isEmptyObject( $("#searchStoreCd").val()) ) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }*/

        if( $scope.flex.collectionView === undefined){
            $scope._popMsg("내점-판매가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

        var storeStinSaleUprcOption = $scope.prodInfo.storeStinSaleUprcOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if($("#inputStinSaleUprcRate").val() === ""){
            $scope._popMsg("내점-판매가 변경비율을 입력해 주세요.");
            return false;
        }

        var newStinSaleUprc = 0;

        // 변경내점-판매가 적용
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {

                newStinSaleUprc = 0;
                if(storeStinSaleUprcOption === "S"){ // 매장
                    newStinSaleUprc = Number($scope.flex.collectionView.items[i].storeSaleUprc) * (Number($("#inputStinSaleUprcRate").val())/100);
                }else if(storeStinSaleUprcOption === "H"){ // 본사
                    newStinSaleUprc = Number($scope.flex.collectionView.items[i].hqSaleUprc) * (Number($("#inputStinSaleUprcRate").val())/100);
                }else if(storeStinSaleUprcOption === "SS") { // 매장내점
                    newStinSaleUprc = Number($scope.flex.collectionView.items[i].storeStinSaleUprc) * (Number($("#inputStinSaleUprcRate").val())/100);
                }else if(storeStinSaleUprcOption === "HS") { // 본사내점
                    newStinSaleUprc = Number($scope.flex.collectionView.items[i].hqStinSaleUprc) * (Number($("#inputStinSaleUprcRate").val())/100);
                }

                $scope.flex.collectionView.items[i].stinSaleUprc = $scope.calChangeAmt(newStinSaleUprc, "2");
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();

        // 그리드 선택불가 항목처리
        $scope.setGridReadOnly();
    };

    // 배달-판매가 일괄적용 버튼 클릭
    $scope.changeDlvrSaleUprc = function(){

        /*if( isEmptyObject( $("#searchStoreCd").val()) ) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }*/

        if( $scope.flex.collectionView === undefined){
            $scope._popMsg("배달-판매가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

        var storeDlvrSaleUprcOption = $scope.prodInfo.storeDlvrSaleUprcOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if($("#inputDlvrSaleUprcRate").val() === ""){
            $scope._popMsg("배달-판매가 변경비율을 입력해 주세요.");
            return false;
        }

        var newDlvrSaleUprc = 0;

        // 변경배달-판매가 적용
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {

                newDlvrSaleUprc = 0;
                if(storeDlvrSaleUprcOption === "S"){ // 매장
                    newDlvrSaleUprc = Number($scope.flex.collectionView.items[i].storeSaleUprc) * (Number($("#inputDlvrSaleUprcRate").val())/100);
                }else if(storeDlvrSaleUprcOption === "H"){ // 본사
                    newDlvrSaleUprc = Number($scope.flex.collectionView.items[i].hqSaleUprc) * (Number($("#inputDlvrSaleUprcRate").val())/100);
                }else if(storeDlvrSaleUprcOption === "SD") { // 매장배달
                    newDlvrSaleUprc = Number($scope.flex.collectionView.items[i].storeDlvrSaleUprc) * (Number($("#inputDlvrSaleUprcRate").val())/100);
                }else if(storeDlvrSaleUprcOption === "HD") { // 본사배달
                    newDlvrSaleUprc = Number($scope.flex.collectionView.items[i].hqDlvrSaleUprc) * (Number($("#inputDlvrSaleUprcRate").val())/100);
                }

                $scope.flex.collectionView.items[i].dlvrSaleUprc = $scope.calChangeAmt(newDlvrSaleUprc, "3");
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();

        // 그리드 선택불가 항목처리
        $scope.setGridReadOnly();
    };

    // 포장-판매가 일괄적용 버튼 클릭
    $scope.changePackSaleUprc = function(){

        /*if( isEmptyObject( $("#searchStoreCd").val()) ) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }*/

        if( $scope.flex.collectionView === undefined){
            $scope._popMsg(" 포장-판매가를 일괄적용할 상품을 조회해주세요.");
            return false;
        }

        var storePackSaleUprcOption = $scope.prodInfo.storePackSaleUprcOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if($("#inputPackSaleUprcRate").val() === ""){
            $scope._popMsg("포장-판매가 변경비율을 입력해 주세요.");
            return false;
        }

        var newPackSaleUprc = 0;

        // 변경포장-판매가 적용
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {

                newPackSaleUprc = 0;
                if(storePackSaleUprcOption === "S"){ // 매장
                    newPackSaleUprc = Number($scope.flex.collectionView.items[i].storeSaleUprc) * (Number($("#inputPackSaleUprcRate").val())/100);
                }else if(storePackSaleUprcOption === "H"){ // 본사
                    newPackSaleUprc = Number($scope.flex.collectionView.items[i].hqSaleUprc) * (Number($("#inputPackSaleUprcRate").val())/100);
                }else if(storePackSaleUprcOption === "SP") { // 매장포장
                    newPackSaleUprc = Number($scope.flex.collectionView.items[i].storePackSaleUprc) * (Number($("#inputPackSaleUprcRate").val())/100);
                }else if(storePackSaleUprcOption === "HP") { // 본사포장
                    newPackSaleUprc = Number($scope.flex.collectionView.items[i].hqPackSaleUprc) * (Number($("#inputPackSaleUprcRate").val())/100);
                }

                $scope.flex.collectionView.items[i].packSaleUprc = $scope.calChangeAmt(newPackSaleUprc, "4");
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();

        // 그리드 선택불가 항목처리
        $scope.setGridReadOnly();
    };

    // 변경판매가 계산
    $scope.calChangeAmt = function(amt, type){

        var ChangeAmt = 0;
        var unit;
        var mode;

        if(type === "1"){ // 판매가
            unit = $scope.prodInfo.changeUnit;
            mode = $scope.prodInfo.changeMode;
        }else if(type === "2"){ // 내점-판매가
            unit = $scope.prodInfo.stinSaleUprcChangeUnit;
            mode = $scope.prodInfo.stinSaleUprcChangeMode;
        }else if(type === "3"){ // 배달-판매가
            unit = $scope.prodInfo.dlvrSaleUprcChangeUnit;
            mode = $scope.prodInfo.dlvrSaleUprcChangeMode;
        }else{ // 포장-판매가
            unit = $scope.prodInfo.packSaleUprcChangeUnit;
            mode = $scope.prodInfo.packSaleUprcChangeMode;
        }

        if(mode === "0"){ // 반올림
            ChangeAmt = Math.round(amt/(unit*10))*(unit*10);
        }else if(mode === "1"){ //절상
            ChangeAmt = Math.ceil(amt/(unit*10))*(unit*10);
        }else if(mode === "2"){ //절하
            ChangeAmt = Math.floor(amt/(unit*10))*(unit*10);
        }

        return ChangeAmt;
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

    //판매가 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    }

    // 수정
    $scope.saveProdPrice = function(){
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if(coercionFg === "0"){
                    if ($scope.flex.collectionView.items[i].prcCtrlFg === "S") {
                        $scope._popMsg(messages["salePriceResve.prcCtrlFgStoreBlank"]); // 가격관리구분이 '매장'인 상품은 수정할 수 없습니다.
                        return false;
                    }
                }

                if(Number(now) >= Number($scope.flex.collectionView.items[i].startDate.replaceAll('-', ''))) {
                    $scope._popMsg(messages["salePriceResve.startDate"] + "는 " + messages["salePriceResve.resveDate.chk.msg"]);
                    return false;
                }
                if(Number(now) >= Number($scope.flex.collectionView.items[i].endDate.replaceAll('-', ''))) {
                    $scope._popMsg(messages["salePriceResve.endDate"] + "는 " + messages["salePriceResve.resveDate.chk.msg"]);
                    return false;
                }
                if(Number($scope.flex.collectionView.items[i].startDate.replaceAll('-', '')) > Number($scope.flex.collectionView.items[i].endDate.replaceAll('-', ''))){
                    $scope._popMsg(messages["salePriceResve.resveDate"] + messages["salePriceResve.resveDate.chk.msg2"]);
                    return false;
                }
            }
        }

        var numchkexp = /[^0-9]/g; // 숫자가 아닌 값 체크
        var numchkexp2 = /^-[0-9]/g;

        // 파라미터 설정
        var params = new Array();

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if (($scope.flex.collectionView.items[i].orgStartDate !== $scope.flex.collectionView.items[i].startDate) ||
                    ($scope.flex.collectionView.items[i].hqSaleUprc !== $scope.flex.collectionView.items[i].saleUprc) ||
                    ($scope.flex.collectionView.items[i].hqStinSaleUprc !== $scope.flex.collectionView.items[i].stinSaleUprc) ||
                    ($scope.flex.collectionView.items[i].hqDlvrSaleUprc !== $scope.flex.collectionView.items[i].dlvrSaleUprc) ||
                    ($scope.flex.collectionView.items[i].hqPackSaleUprc !== $scope.flex.collectionView.items[i].packSaleUprc)) {

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
                    $scope.flex.collectionView.items[i].applyFg = $scope.applyFg;
                    $scope.flex.collectionView.items[i].orgStartDate = $scope.flex.collectionView.items[i].orgStartDate.replaceAll('-', ''); // 키값
                    $scope.flex.collectionView.items[i].startDate = $scope.flex.collectionView.items[i].startDate.replaceAll('-', '');
                    $scope.flex.collectionView.items[i].endDate = $scope.flex.collectionView.items[i].endDate.replaceAll('-', '');
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
        }

        if ($scope.applyFg) {
            $scope._popConfirm( "하위매장에 가격이 적용됩니다. 그래도 저장하시겠습니까?", function(){
                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._save('/base/price/salePriceResve/hqSalePriceResve/modHqSalePriceResve.sb', params, function(){
                    $scope.searchHqSalePriceResveList();
                });
            });
        } else {
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/price/salePriceResve/hqSalePriceResve/modHqSalePriceResve.sb', params, function(){
                $scope.searchHqSalePriceResveList();
            });
        }

    };

    // 추가 팝업
    $scope.addProdPrice = function () {
        $scope.hqSalePriceResveAddLayer.show(true);
        $scope._pageView('hqSalePriceResveAddCtrl',1);
    };

    // 그리드 선택불가 항목처리
    $scope.setGridReadOnly = function () {
        var grid = wijmo.Control.getControl("#wjGridHqSalePriceResve");
        var rows = grid.rows;

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];

            // 가격관리구분에 의해 본사는 매장의 상품가격 수정 불가(매장판매가관리본사강제수정 가능인 경우는 수정가능)
            if(coercionFg === "0") {
                if (item.prcCtrlFg === "S") {
                    item.gChk = false;
                    rows[i].isReadOnly = true;
                }
            }

            // 시작일자가 오늘날짜보다 작거나 같으면 수정불가
            if(Number(now) >= Number(item.orgStartDate.replaceAll('-', ''))) {
                item.gChk = false;
                rows[i].isReadOnly = true;
            }

            // 행간격 고정
            rows[i].height = 25
        }
    };

}]);
