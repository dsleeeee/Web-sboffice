/****************************************************************
 *
 * 파일명 : storeProdSalePriceResve.js
 * 설  명 : 상품별 판매가관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.20     김지은      1.0
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

/**
 * 상품별 판매가관리 그리드 생성
 */
app.controller('storeProdSalePriceResveCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeProdSalePriceResveCtrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", getOneMonthLater());

    // 전체기간 체크박스
    $scope.isChecked = false;

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

    // 상품정보
    $scope.prodInfo;
    $scope.setProdInfo = function(data){
        $scope.prodInfo = data;
    };
    $scope.getProdInfo = function(){
        return $scope.prodInfo;
    };

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox1", gvListScaleBoxData2);
    $scope._setComboData("saleAmtOption", saleAmtOptionFg);
    $scope._setComboData("stinSaleUprcOption", stinSaleAmtOptionFg);
    $scope._setComboData("dlvrSaleUprcOption", dlvrSaleAmtOptionFg);
    $scope._setComboData("packSaleUprcOption", packSaleAmtOptionFg);

    $scope.prodSaleUprcApply = true;

    // 검색조건 일괄변경
    $("#tblProdChange").show();

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel == s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "storeCd") {
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
                if (col.binding === "storeCd") {
                    var params     = {};
                    params.prodCd = $("#prodCd").val();
                    params.prodNm = $("#prodNm").val();
                    params.storeCd = selectedRow.storeCd;
                    params.storeNm = selectedRow.storeNm;
                    $scope.storeProdSalePriceInfoLayer.show(true);
                    $scope._broadcast('storeProdSalePriceInfoCtrl', params);
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
                    if($scope.prodSaleUprcApply){
                        $scope.saleUprc(item);
                    }
                }
            }
            s.collectionView.commitEdit();
        });

        // 전체기간 체크박스 선택에 따른 날짜선택 초기화
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;

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
        dataItem.storeCd              = messages["salePriceResve.storeCd"];
        dataItem.storeNm              = messages["salePriceResve.storeNm"];
        dataItem.hqSaleUprc           = messages["salePriceResve.salePrice"];
        dataItem.saleUprcP            = messages["salePriceResve.salePrice"];
        dataItem.saleUprc             = messages["salePriceResve.salePrice"];
        dataItem.hqStinSaleUprc       = messages["salePriceResve.stinSaleUprc"];
        dataItem.stinSaleUprcP        = messages["salePriceResve.stinSaleUprc"];
        dataItem.stinSaleUprc         = messages["salePriceResve.stinSaleUprc"];
        dataItem.hqDlvrSaleUprc       = messages["salePriceResve.dlvrSaleUprc"];
        dataItem.dlvrSaleUprcP        = messages["salePriceResve.dlvrSaleUprc"];
        dataItem.dlvrSaleUprc         = messages["salePriceResve.dlvrSaleUprc"];
        dataItem.hqPackSaleUprc       = messages["salePriceResve.packSaleUprc"];
        dataItem.packSaleUprcP        = messages["salePriceResve.packSaleUprc"];
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

                if ((panel.grid.columnHeaders.rows.length - 1) === r) {
                    // 헤더의 전체선택 클릭 로직
                    var flex   = panel.grid;
                    var column = flex.columns[c];
                    // check that this is a boolean column
                    if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
                        // prevent sorting on click
                        column.allowSorting = false;
                        // count true values to initialize checkbox
                        var cnt             = 0;
                        for (var i = 0; i < flex.rows.length; i++) {
                            if (flex.getCellData(i, c) === true) {
                                cnt++;
                            }
                        }
                        // create and initialize checkbox
                        if (column.format === 'checkBoxText') {
                            cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />'
                                + '<label for=\"' + column.binding + '\" class=\"wj-header-label\">' + cell.innerHTML + '</label>';
                        } else {
                            cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
                        }
                        var cb           = cell.firstChild;
                        cb.checked       = cnt > 0;
                        cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
                        // apply checkbox value to cells
                        cb.addEventListener('click', function (e) {
                            flex.beginUpdate();
                            for (var i = 0; i < flex.rows.length; i++) {
                                if(!flex.rows[i].isReadOnly) {
                                    flex.setCellData(i, c, cb.checked);
                                }
                            }
                            flex.endUpdate();
                        });
                    }
                }
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

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    $scope.$on("storeProdSalePriceResveCtrl", function(event, data) {
        // 상품별 예약판매가 조회
        $scope.searchSalePriceInfo();
        event.preventDefault();
    });

    // 상품별 예약판매가 조회
    $scope.searchSalePriceInfo = function(){
        if( isEmptyObject( $("#prodCd").val()) ) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        // 상품 정보 조회
        var params = {};
        params.prodCd = $("#prodCd").val();
        params.storeCd = $("#storeCd").val();

        $scope._postJSONQuery.withOutPopUp('/base/price/salePrice/prodSalePrice/getProdInfo.sb', params,
            function(response){
                // console.log('response.data.data', response.data.data);
                $scope.setProdInfo(response.data.data);

                if( isEmptyObject($scope.getProdInfo().prodCd) ) {
                    $scope._popMsg(messages["cmm.error"]);
                    return false;
                }
                $scope.searchSalePriceList();
            }
        );
    };

    // 상품별 예약판매가 그리드 조회
    $scope.searchSalePriceList = function(){
        var params = {};
        // 조회일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }
        params.prodCd = $("#prodCd").val();
        params.storeCd = $("#storeCd").val();
        params.listScale = $scope.listScaleCombo1.text;
        // console.log(params);

        $scope._inquirySub('/base/price/salePriceResve/storeSalePriceResve/getStoreProdSalePriceResveList.sb', params, function() {

            // 조회한 값으로 마진금액, 마진율 계산
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                $scope.calcAmt($scope.flex.collectionView.items[i]);
                $scope.flex.collectionView.commitEdit();
            }

            // 그리드 선택불가 항목처리
            $scope.setGridReadOnly();

        }, false);
    };

    // 판매가 콤보박스 선택 이벤트
    $scope.inputSaleAmtReadOnly = false;
    $scope.setSelectedSaleAmtOption = function(s){
        if(s.selectedValue === 'S') {
            $scope.inputSaleAmtReadOnly = false;
        } else {
            $scope.inputSaleAmtReadOnly = true;
        }
    };

    // 내점-판매가 콤보박스 선택 이벤트
    $scope.inputStinSaleUprcReadOnly = false;
    $scope.setSelectedStinSaleUprcOption = function(s){
        if(s.selectedValue === 'S') {
            $scope.inputStinSaleUprcReadOnly = false;
        } else {
            $scope.inputStinSaleUprcReadOnly = true;
        }
    };

    // 배달-판매가 콤보박스 선택 이벤트
    $scope.inputDlvrSaleUprcReadOnly = false;
    $scope.setSelectedDlvrSaleUprcOption = function(s){
        if(s.selectedValue === 'S') {
            $scope.inputDlvrSaleUprcReadOnly = false;
        } else {
            $scope.inputDlvrSaleUprcReadOnly = true;
        }
    };

    // 포장-판매가 콤보박스 선택 이벤트
    $scope.inputPackSaleUprcReadOnly = false;
    $scope.setSelectedPackSaleUprcOption = function(s){
        if(s.selectedValue === 'S') {
            $scope.inputPackSaleUprcReadOnly = false;
        } else {
            $scope.inputPackSaleUprcReadOnly = true;
        }
    };

    // 일괄변경 테이블 숨김/보임
    $scope.changeShow = function(){
        if( $("#tblProdChange").css("display") === 'none'){
            $("#tblProdChange").show();
        } else {
            $("#tblProdChange").hide();
        }
    };

    // 가격 변경
    $scope.calcAmt = function(item){
        // console.log('calcAmt',item);
        var costUprc = item.costUprc;
        var saleUprc = item.saleUprc;
        var splyUprc = item.storeSplyUprc;
        var poUnitQty =  item.poUnitQty;

        item.hqMarginAmt = (splyUprc - costUprc); // 본사마진금액
        item.hqMarginRate = (splyUprc - costUprc) / costUprc * 100; // 본사마진율
        item.saleUprcAmt = (saleUprc - poUnitQty); // 현재판매금액
        item.storeMarginAmt = ((saleUprc - poUnitQty) - splyUprc); // 매장마진금액
        item.storeMarginRate = ((saleUprc - poUnitQty) - splyUprc) / (saleUprc - poUnitQty) * 100; // 매장마진율
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
    };

    // 판매가 일괄적용 버튼 클릭
    // 매장판매가 일괄적용시, 입력한 매장판매가를 적용시킴.
    // 본사판매가 일괄적용시, 조회된 본사판매가를 적용시킴.
    $scope.changeAmt = function() {

        if( isEmptyObject( $("#prodCd").val()) ) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if( $scope.flex.collectionView === undefined){
            $scope._popMsg("판매가를 일괄적용할 매장을 조회해주세요.");
            return false;
        }

        var saleAmtOption = $scope.prodInfo.saleAmtOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }

        if(saleAmtOption === 'S') {
            if($scope.prodInfo.inputSaleAmt === undefined || $scope.prodInfo.inputSaleAmt === '') {
                $scope._popMsg("판매가 일괄 적용을 위해, 매장판매가를 입력해주세요.");
                return false;
            }
        }

        var newSaleAmt = 0;

        if(saleAmtOption === 'S'){ // 매장
            newSaleAmt = Number($scope.prodInfo.inputSaleAmt);
        }else{ // 본사
            newSaleAmt = $scope.prodInfo.saleUprc; // 상품선택에서 선택한 상품의 본사가격
        }

        // 변경 판매가 적용
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].saleUprc = newSaleAmt;
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();

        // 그리드 선택불가 항목처리
        $scope.setGridReadOnly();
    };

    // 내점-판매가 일괄적용 버튼 클릭
    $scope.changeStinSaleUprc = function(){

        if( isEmptyObject( $("#prodCd").val()) ) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if( $scope.flex.collectionView === undefined){
            $scope._popMsg("내점-판매가를 일괄적용할 매장을 조회해주세요.");
            return false;
        }

        var stinSaleUprcOption = $scope.prodInfo.stinSaleUprcOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }

        if(stinSaleUprcOption === 'S') {
            if($scope.prodInfo.inputStinSaleUprc === undefined || $scope.prodInfo.inputStinSaleUprc === '') {
                $scope._popMsg("내점-판매가 일괄 적용을 위해, 매장판매가를 입력해주세요.");
                return false;
            }
        }

        var newStinSaleUprc = 0;

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {

                newStinSaleUprc = 0;

                if(stinSaleUprcOption === "S"){ // 매장
                    newStinSaleUprc = Number($scope.prodInfo.inputStinSaleUprc);
                }else if(stinSaleUprcOption === "H"){ // 본사
                    newStinSaleUprc = Number($scope.prodInfo.saleUprc);
                }else if(stinSaleUprcOption === "SS") { // 매장내점
                    newStinSaleUprc = Number($scope.flex.collectionView.items[i].stinSaleUprcP);
                }else if(stinSaleUprcOption === "HS") { // 본사내점
                    newStinSaleUprc = Number($scope.prodInfo.stinSaleUprc);
                }

                $scope.flex.collectionView.items[i].stinSaleUprc = newStinSaleUprc;
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();

        // 그리드 선택불가 항목처리
        $scope.setGridReadOnly();
    };

    // 배달-판매가 일괄적용 버튼 클릭
    $scope.changeDlvrSaleUprc = function(){

        if( isEmptyObject( $("#prodCd").val()) ) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if( $scope.flex.collectionView === undefined){
            $scope._popMsg("배달-판매가를 일괄적용할 매장을 조회해주세요.");
            return false;
        }

        var dlvrSaleUprcOption = $scope.prodInfo.dlvrSaleUprcOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }

        if(dlvrSaleUprcOption === 'S') {
            if($scope.prodInfo.inputDlvrSaleUprc === undefined || $scope.prodInfo.inputDlvrSaleUprc === '') {
                $scope._popMsg("배달-판매가 일괄 적용을 위해, 매장판매가를 입력해주세요.");
                return false;
            }
        }

        var newDlvrSaleUprc = 0;

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {

                newDlvrSaleUprc = 0;

                if(dlvrSaleUprcOption === "S"){ // 매장
                    newDlvrSaleUprc = Number($scope.prodInfo.inputDlvrSaleUprc);
                }else if(dlvrSaleUprcOption === "H"){ // 본사
                    newDlvrSaleUprc = Number($scope.prodInfo.saleUprc);
                }else if(dlvrSaleUprcOption === "SD") { // 매장배달
                    newDlvrSaleUprc = Number($scope.flex.collectionView.items[i].dlvrSaleUprcP);
                }else if(dlvrSaleUprcOption === "HD") { // 본사배달
                    newDlvrSaleUprc = Number($scope.prodInfo.dlvrSaleUprc);
                }

                $scope.flex.collectionView.items[i].dlvrSaleUprc = newDlvrSaleUprc;
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();

        // 그리드 선택불가 항목처리
        $scope.setGridReadOnly();
    };

    // 포장-판매가 일괄적용 버튼 클릭
    $scope.changePackSaleUprc = function(){

        if( isEmptyObject( $("#prodCd").val()) ) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        if( $scope.flex.collectionView === undefined){
            $scope._popMsg(" 포장-판매가를 일괄적용할 매장을 조회해주세요.");
            return false;
        }

        var packSaleUprcOption = $scope.prodInfo.packSaleUprcOption;

        var selectCnt = 0;
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){ selectCnt ++; }
        }

        if(selectCnt < 1) {
            $scope._popMsg("매장을 선택해주세요.");
            return false;
        }

        if(packSaleUprcOption === 'S') {
            if($scope.prodInfo.inputPackSaleUprc === undefined || $scope.prodInfo.inputPackSaleUprc === '') {
                $scope._popMsg("포장-판매가 일괄 적용을 위해, 매장판매가를 입력해주세요.");
                return false;
            }
        }

        var newPackSaleUprc = 0;

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            if($scope.flex.collectionView.items[i].gChk) {

                newPackSaleUprc = 0;

                if(packSaleUprcOption === "S"){ // 매장
                    newPackSaleUprc = Number($scope.prodInfo.inputPackSaleUprc);
                }else if(packSaleUprcOption === "H"){ // 본사
                    newPackSaleUprc = Number($scope.prodInfo.saleUprc);
                }else if(packSaleUprcOption === "SP") { // 매장포장
                    newPackSaleUprc = Number($scope.flex.collectionView.items[i].packSaleUprcP);
                }else if(packSaleUprcOption === "HP") { // 본사포장
                    newPackSaleUprc = Number($scope.prodInfo.packSaleUprc);
                }

                $scope.flex.collectionView.items[i].packSaleUprc = newPackSaleUprc;
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();

        // 그리드 선택불가 항목처리
        $scope.setGridReadOnly();
    };

    // 삭제
    $scope.delProdPrice = function (){
        $scope._popConfirm(messages["kioskKeyMapResve.delConfirm"], function() {
                // 파라미터 설정
            var params = new Array();

            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
                if ($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].orgStartDate = $scope.flex.collectionView.items[i].orgStartDate.replaceAll('-', ''); // 키값
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            $scope._save('/base/price/salePriceResve/storeSalePriceResve/delStoreProdSalePriceResve.sb', params, function(){
                $scope.searchSalePriceList();
            });
        });
    }

    // isValidDate, format: yyyyMMdd
    $scope.isValidDate = function (str_yyyyMMdd) {
        var pattern = /[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])/;
        return pattern.test(str_yyyyMMdd);
    };

    // 수정
    $scope.saveProdPrice = function(){

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if(coercionFg === "0") {
                    // PRC_CTRL_FG 가격관리구분 H인 상품만 수정가능
                    if ($scope.flex.collectionView.items[i].prcCtrlFg === "S") {
                        $scope._popMsg(messages["salePriceResve.prcCtrlFgStoreBlank"]); // 가격관리구분이 '매장'인 상품은 수정할 수 없습니다.
                        return false;
                    }
                }

                if (!$scope.isValidDate($scope.flex.collectionView.items[i].startDate.replaceAll('-', ''))) {
                    $scope._popMsg(messages["salePriceResve.startDate"] + " " + messages["member.excel.upload.invalid.date"]);
                    return false;
                }
                if (!$scope.isValidDate($scope.flex.collectionView.items[i].endDate.replaceAll('-', ''))) {
                    $scope._popMsg(messages["salePriceResve.endDate"] + " " + messages["member.excel.upload.invalid.date"]);
                    return false;
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

        // isInteger는 es6 임. ie 11 에서는 안되므로 함수 만듬.
        Number.isInteger = Number.isInteger || function(value) {
            return typeof value === "number" &&
                isFinite(value) &&
                Math.floor(value) === value;
        };

        var numchkexp = /[^0-9]/; // 숫자가 아닌 값 체크
        var numchkexp2 = /^-?[0-9]+$/;

        // 파라미터 설정
        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if (($scope.flex.collectionView.items[i].orgStartDate !== $scope.flex.collectionView.items[i].startDate) ||
                    ($scope.flex.collectionView.items[i].orgEndDate !== $scope.flex.collectionView.items[i].endDate) ||
                    ($scope.flex.collectionView.items[i].saleUprcP !== $scope.flex.collectionView.items[i].saleUprc) ||
                    ($scope.flex.collectionView.items[i].stinSaleUprcP !== $scope.flex.collectionView.items[i].stinSaleUprc) ||
                    ($scope.flex.collectionView.items[i].dlvrSaleUprcP !== $scope.flex.collectionView.items[i].dlvrSaleUprc) ||
                    ($scope.flex.collectionView.items[i].packSaleUprcP !== $scope.flex.collectionView.items[i].packSaleUprc)) {

                    // 변경판매가 - 필수 입력값 체크
                    if ($scope.flex.collectionView.items[i].saleUprc === "" || $scope.flex.collectionView.items[i].saleUprc === null) {
                        $scope._popMsg(messages["salePriceResve.saleUprcBlank"]);
                        return false;
                    }

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
                    // 변경판매가 - -1000000000 이하 입력 불가
                    if($scope.flex.collectionView.items[i].saleUprc <= -1000000000){
                        $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
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
                            // 변경내점-판매가 - -1000000000 이하 입력 불가
                            if ($scope.flex.collectionView.items[i].stinSaleUprc <= -1000000000) {
                                $scope._popMsg(messages["salePrice.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
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
                            // 변경배달-판매가 - -1000000000 이하 입력 불가
                            if ($scope.flex.collectionView.items[i].dlvrSaleUprc <= -1000000000) {
                                $scope._popMsg(messages["salePrice.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
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
                            // 변경포장-판매가 - -1000000000 이하 입력 불가
                            if ($scope.flex.collectionView.items[i].packSaleUprc <= -1000000000) {
                                $scope._popMsg(messages["salePrice.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }

                    }else{
                        $scope.flex.collectionView.items[i].stinSaleUprc = $scope.flex.collectionView.items[i].saleUprc;
                        $scope.flex.collectionView.items[i].dlvrSaleUprc = $scope.flex.collectionView.items[i].saleUprc;
                        $scope.flex.collectionView.items[i].packSaleUprc = $scope.flex.collectionView.items[i].saleUprc;
                    }

                    $scope.flex.collectionView.items[i].prodCd = $("#prodCd").val();
                    $scope.flex.collectionView.items[i].orgStartDate = $scope.flex.collectionView.items[i].orgStartDate.replaceAll('-', ''); // 키값
                    $scope.flex.collectionView.items[i].startDate = $scope.flex.collectionView.items[i].startDate.replaceAll('-', '');
                    $scope.flex.collectionView.items[i].endDate = $scope.flex.collectionView.items[i].endDate.replaceAll('-', '');
                    params.push($scope.flex.collectionView.items[i]);

                }
            }
        }

        // console.log('params',params)

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/price/salePriceResve/storeSalePriceResve/modStoreProdSalePriceResve.sb', params, function(){
            $scope.searchSalePriceList();
        });
    };

    // 추가 팝업
    $scope.addProdPrice = function () {
        $scope.storeProdSalePriceResveAddLayer.show(true);
    };

    // 그리드 선택불가 항목처리
    $scope.setGridReadOnly = function () {
        var grid = wijmo.Control.getControl("#wjGridProdSalePriceResveArea");
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
    
    // 상품선택 모듈 팝업 사용시 정의 (상품찾기)
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.prodShow = function () {
        $scope._broadcast('prodCtrl');
    };

    // 조회조건 엑셀다운로드
    $scope.excelDownloadProdPrice = function () {
        if( isEmptyObject( $("#prodCd").val()) ) {
            $scope._popMsg("상품을 선택해주세요.");
            return false;
        }

        var params = {};
        // 조회일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }
        params.prodCd = $("#prodCd").val();
        params.storeCd = $("#storeCd").val();

        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('storeProdSalePriceResveExcelCtrl', params);
        });
    };

}]);


/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('storeProdSalePriceResveExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeProdSalePriceResveExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.startDate            = messages["salePriceResve.startDate"];
        dataItem.endDate              = messages["salePriceResve.endDate"];
        dataItem.storeCd              = messages["salePriceResve.storeCd"];
        dataItem.storeNm              = messages["salePriceResve.storeNm"];
        dataItem.hqSaleUprc           = messages["salePriceResve.salePrice"];
        dataItem.saleUprcP            = messages["salePriceResve.salePrice"];
        dataItem.saleUprc             = messages["salePriceResve.salePrice"];
        dataItem.hqStinSaleUprc       = messages["salePriceResve.stinSaleUprc"];
        dataItem.stinSaleUprcP        = messages["salePriceResve.stinSaleUprc"];
        dataItem.stinSaleUprc         = messages["salePriceResve.stinSaleUprc"];
        dataItem.hqDlvrSaleUprc       = messages["salePriceResve.dlvrSaleUprc"];
        dataItem.dlvrSaleUprcP        = messages["salePriceResve.dlvrSaleUprc"];
        dataItem.dlvrSaleUprc         = messages["salePriceResve.dlvrSaleUprc"];
        dataItem.hqPackSaleUprc       = messages["salePriceResve.packSaleUprc"];
        dataItem.packSaleUprcP        = messages["salePriceResve.packSaleUprc"];
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
        // <-- //그리드 헤더2줄 -->
    };

    // <-- 검색 호출 -->
    $scope.$on("storeProdSalePriceResveExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/price/salePriceResve/storeSalePriceResve/getStoreProdSalePriceResveExcelList.sb", params, function() {
            if ($scope.excelFlexStoreProdSalePriceResve.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            // 조회한 값으로 마진금액, 마진율 계산
            for (var i = 0; i < $scope.excelFlexStoreProdSalePriceResve.collectionView.items.length; i++) {
                $scope.calcAmt($scope.excelFlexStoreProdSalePriceResve.collectionView.items[i]);
                $scope.excelFlexStoreProdSalePriceResve.collectionView.commitEdit();
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlexStoreProdSalePriceResve, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, "상품별 판매가관리_" + getCurDateTime()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };
    // <-- //검색 호출 -->

    // 가격 변경
    $scope.calcAmt = function(item){
        // console.log('calcAmt',item);
        var costUprc = item.costUprc;
        var saleUprc = item.saleUprc;
        var splyUprc = item.storeSplyUprc;
        var poUnitQty =  item.poUnitQty;

        item.hqMarginAmt = (splyUprc - costUprc); // 본사마진금액
        item.hqMarginRate = (splyUprc - costUprc) / costUprc * 100; // 본사마진율
        item.saleUprcAmt = (saleUprc - poUnitQty); // 현재판매금액
        item.storeMarginAmt = ((saleUprc - poUnitQty) - splyUprc); // 매장마진금액
        item.storeMarginRate = ((saleUprc - poUnitQty) - splyUprc) / (saleUprc - poUnitQty) * 100; // 매장마진율
    };

}]);