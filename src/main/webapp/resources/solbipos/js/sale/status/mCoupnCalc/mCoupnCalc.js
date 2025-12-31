/****************************************************************
 *
 * 파일명 : mCoupnCalc.js
 * 설  명 : 맘스터치 > 매출분석2 > 모바일쿠폰 정산 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.07.19     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/** 모바일쿠폰 정산 controller */
app.controller('mCoupnCalcCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mCoupnCalcCtrl', $scope, $http, $timeout, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 콤보박스 셋팅
    $scope._setComboData("srchMCoupn", mCoupnFg); // 모바일쿠폰

    // 조회조건 승인구분 데이터 Set
    $scope._setComboData("srchSaleFg", [
        {"name": messages["cmm.all"], "value": ""},
        {"name": messages["mCoupnCalc.appr"], "value": "1"},
        {"name": messages["cmm.cancel"], "value": "-1"}
    ]);

    //조회조건 승인처리 데이터 Set
    $scope._setComboData("srchApprProcFg", [
        {"name": messages["cmm.all"], "value": ""},
        {"name": messages["mCoupnCalc.apprProcFg1"], "value": "1"},
        {"name": messages["mCoupnCalc.apprProcFg2"], "value": "2"},
        {"name": messages["mCoupnCalc.comm"], "value": "3"}
    ]);

    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // picker 사용시 호출 : 미사용시 호출안함
        $scope._makePickColumns("mCoupnCalcCtrl");

        // 그리드 DataMap 설정
        $scope.brandCdDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
          if (e.panel === s.cells) {
            var col = s.columns[e.col];

            if (col.binding === "storeNm") {
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

              var params = {};
              params.startDate = $scope.startDate;
              params.endDate = $scope.endDate;
              params.storeHqBrandCd = selectedRow.brandCd;
              params.storeCd = selectedRow.storeCd;
              params.posNo = $scope.posNo;
              params.posNos = $scope.posNos;
              params.mcoupnCd = selectedRow.mcoupnCd;
              params.saleFg = $scope.srchSaleFg;
              params.cashBillApprProcFg = $scope.srchCashBillApprProcFg;
              //params.aprProcFg = $scope.srchApprProcFgCombo.selectedValue;

              if(momsEnvstVal === "1" && orgnFg === "HQ"){ // 확장조회는 본사권한이면서 맘스터치만 사용
                  params.momsTeam = selectedRow.momsTeam;
                  params.momsAcShop = selectedRow.momsAcShop;
                  params.momsAreaFg = $scope.momsAreaFg;
                  params.momsCommercial = $scope.momsCommercial;
                  params.momsShopType = $scope.momsShopType;
                  params.momsStoreManageType = $scope.momsStoreManageType;
                  params.branchCd = selectedRow.branchCd;
                  params.momsStoreFg01 = $scope.momsStoreFg01;
                  params.momsStoreFg02 = $scope.momsStoreFg02;
                  params.momsStoreFg03 = $scope.momsStoreFg03;
                  params.momsStoreFg04 = $scope.momsStoreFg04;
                  params.momsStoreFg05 = $scope.momsStoreFg05;
              }

              // '전체' 일때
              if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
                  var momsHqBrandCd = "";
                  for(var i=0; i < momsHqBrandCdComboList.length; i++){
                      if(momsHqBrandCdComboList[i].value !== null) {
                          momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                      }
                  }
                  params.userBrands = momsHqBrandCd;
              }

            if (col.binding === "storeNm") {
                $scope._broadcast('mCoupnCalcDtlCtrl', params);
                $scope.mCoupnCalcDtlLayer.show(true);
            }
          }
        });

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.branchCd = messages["mCoupnCalc.branchCd"];
        dataItem.branchNm = messages["mCoupnCalc.branchNm"];
        dataItem.storeCd = messages["mCoupnCalc.storeCd"];
        dataItem.storeNm = messages["mCoupnCalc.storeNm"];
        dataItem.brandCd = messages["mCoupnCalc.brandCd"];
        dataItem.momsTeam = messages["cmm.moms.momsTeam"];
        dataItem.momsAcShop = messages["cmm.moms.momsAcShop"];
        dataItem.mcoupnCd = messages["mCoupnCalc.coupnCd"];
        dataItem.mcoupnNm = messages["mCoupnCalc.coupnNm"];
        dataItem.cnt = messages["cmm.all"];
        dataItem.apprAmt = messages["cmm.all"];
        dataItem.cntOk = messages["mCoupnCalc.appr"];
        dataItem.apprAmtOk = messages["mCoupnCalc.appr"];
        dataItem.cntCancel = messages["mCoupnCalc.cancel"];
        dataItem.apprAmtCancel = messages["mCoupnCalc.cancel"];

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
    $scope.$on("mCoupnCalcCtrl", function(event, data) {

        // 모바일쿠폰 정산 리스트 조회
        $scope.searchMCoupnCalc();
        event.preventDefault();
    });

    // 모바일쿠폰 정산 리스트 조회
    $scope.searchMCoupnCalc = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#mCoupnCalcStoreCd").val();
        params.posNo = $("#mCoupnCalcSelectPosCd").val();
        params.posNos = $("#mCoupnCalcSelectPosCd").val();
        params.mcoupnCd = $scope.srchMCoupnCombo.selectedValue;
        params.saleFg = $scope.srchSaleFgCombo.selectedValue;
        params.cashBillApprProcFg = $scope.srchApprProcFgCombo.selectedValue;
        //params.aprProcFg = $scope.srchApprProcFgCombo.selectedValue;

        if(momsEnvstVal === "1" && orgnFg === "HQ"){ // 확장조회는 본사권한이면서 맘스터치만 사용
            params.momsTeam = $scope.momsTeam;
            params.momsAcShop = $scope.momsAcShop;
            params.momsAreaFg = $scope.momsAreaFg;
            params.momsCommercial = $scope.momsCommercial;
            params.momsShopType = $scope.momsShopType;
            params.momsStoreManageType = $scope.momsStoreManageType;
            params.branchCd = $scope.branchCd;
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;
        }

        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.listScale = 500;

        $scope.startDate = params.startDate;
        $scope.endDate  = params.endDate;
        $scope.posNo    = params.posNo;
        $scope.posNos    = params.posNos;
        $scope.srchSaleFg = params.saleFg;
        $scope.srchCashBillApprProcFg = params.cashBillApprProcFg;

        // 페이징 처리
        if ($scope._getPagingInfo('curr') > 0) {
            params['curr'] = $scope._getPagingInfo('curr');
        } else {
            params['curr'] = 1;
        }
        // 가상로그인 대응한 session id 설정
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        $.postJSON("/sale/status/mCoupnCalc/mCoupnCalc/getMCoupnCalcList.sb", params, function(response) {
            var grid = $scope.flex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;

            var list = response.data.list;
            if (list.length === undefined || list.length === 0) {
                $scope.data = new wijmo.collections.CollectionView([]);
                if (true && response.message) {

                    // 페이징 처리
                    $scope._setPagingInfo('ctrlName', $scope.name);
                    $scope._setPagingInfo('pageScale', 10);
                    $scope._setPagingInfo('curr', 1);
                    $scope._setPagingInfo('totCnt', 1);
                    $scope._setPagingInfo('totalPage', 1);

                    $scope._broadcast('drawPager');

                    $scope._popMsg(response.message);
                }
                return false;
            }
            var data = new wijmo.collections.CollectionView(list);
            data.trackChanges = true;
            $scope.data = data;

            // 페이징 처리
            if (response.data.page && response.data.page.curr) {
                var pagingInfo = response.data.page;
                $scope._setPagingInfo('ctrlName', $scope.name);
                $scope._setPagingInfo('pageScale', pagingInfo.pageScale);
                $scope._setPagingInfo('curr', pagingInfo.curr);
                $scope._setPagingInfo('totCnt', pagingInfo.totCnt);
                $scope._setPagingInfo('totalPage', pagingInfo.totalPage);
                $scope._broadcast('drawPager');
            }

            var columns = grid.columns;

            // 조회조건 '승인구분'에 따른 컴럼 show/hidden 처리
            if(orgnFg === "HQ"){
                if($scope.srchSaleFgCombo.selectedValue === '1'){
                    columns[11].visible = true;
                    columns[12].visible = true;
                    columns[13].visible = false;
                    columns[14].visible = false;
                }else if($scope.srchSaleFgCombo.selectedValue === '-1'){
                    columns[11].visible = false;
                    columns[12].visible = false;
                    columns[13].visible = true;
                    columns[14].visible = true;
                }else{
                    columns[11].visible = true;
                    columns[12].visible = true;
                    columns[13].visible = true;
                    columns[14].visible = true;
                }
            }else{
                if($scope.srchSaleFgCombo.selectedValue === '1'){
                    columns[6].visible = true;
                    columns[7].visible = true;
                    columns[8].visible = false;
                    columns[9].visible = false;
                }else if($scope.srchSaleFgCombo.selectedValue === '-1'){
                    columns[6].visible = false;
                    columns[7].visible = false;
                    columns[8].visible = true;
                    columns[9].visible = true;
                }else{
                    columns[6].visible = true;
                    columns[7].visible = true;
                    columns[8].visible = true;
                    columns[9].visible = true;
                }
            }

        }, function(response) {
            s_alert.pop(response.message);
            var grid = $scope.flex;
            grid.itemsSource = new wijmo.collections.CollectionView([]);
        });
    };
    // <-- //검색 호출 -->

    // 조회조건/분할 엑셀다운로드
    $scope.excelDownload = function(excelType){

        // 조회기간
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        // 조회일자 최대 1달(31일) 제한
        if (diffDay > 31) {
            $scope._popMsg(messages['cmm.dateOver.1month.error']);
            return false;
        }

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        params.storeCds = $("#mCoupnCalcStoreCd").val();
        params.posNo = $("#mCoupnCalcSelectPosCd").val();
        params.posNos = $("#mCoupnCalcSelectPosCd").val();
        params.mcoupnCd = $scope.srchMCoupnCombo.selectedValue;
        params.saleFg = $scope.srchSaleFgCombo.selectedValue;
        params.cashBillApprProcFg = $scope.srchApprProcFgCombo.selectedValue;
        //params.aprProcFg = $scope.srchApprProcFgCombo.selectedValue;

        if(momsEnvstVal === "1" && orgnFg === "HQ"){ // 확장조회는 본사권한이면서 맘스터치만 사용
            params.momsTeam = $scope.momsTeam;
            params.momsAcShop = $scope.momsAcShop;
            params.momsAreaFg = $scope.momsAreaFg;
            params.momsCommercial = $scope.momsCommercial;
            params.momsShopType = $scope.momsShopType;
            params.momsStoreManageType = $scope.momsStoreManageType;
            params.branchCd = $scope.branchCd;
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;
        }

        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }

        params.excelType = excelType;

        // 데이터양에 따라 2-3초에서 수분이 걸릴 수도 있습니다.
        $scope._popConfirm(messages["cmm.excel.totalExceDownload"], function() {
            $scope._broadcast('mCoupnCalcExcelCtrl', params);
        });
    };

    //포스선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mCoupnCalcSelectPosShow = function () {
        $scope._broadcast('mCoupnCalcSelectPosCtrl');
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 선택
    /*$scope.selectedSaleMcoupon;
    $scope.setSelectedSaleMcoupon = function(store) {
        $scope.selectedSaleMcoupon = store;
    };
    $scope.getSelectedSaleMcoupon = function() {
        return $scope.selectedSaleMcoupon;
    };*/



}]);


/** 모바일쿠폰 정산 엑셀다운로드 controller */
app.controller('mCoupnCalcExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mCoupnCalcExcelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.brandCdDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');

        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.branchCd = messages["mCoupnCalc.branchCd"];
        dataItem.branchNm = messages["mCoupnCalc.branchNm"];
        dataItem.storeCd = messages["mCoupnCalc.storeCd"];
        dataItem.storeNm = messages["mCoupnCalc.storeNm"];
        dataItem.brandCd = messages["mCoupnCalc.brandCd"];
        dataItem.momsTeam = messages["cmm.moms.momsTeam"];
        dataItem.momsAcShop = messages["cmm.moms.momsAcShop"];
        dataItem.mcoupnCd = messages["mCoupnCalc.coupnCd"];
        dataItem.mcoupnNm = messages["mCoupnCalc.coupnNm"];
        dataItem.cnt = messages["cmm.all"];
        dataItem.apprAmt = messages["cmm.all"];
        dataItem.cntOk = messages["mCoupnCalc.appr"];
        dataItem.apprAmtOk = messages["mCoupnCalc.appr"];
        dataItem.cntCancel = messages["mCoupnCalc.cancel"];
        dataItem.apprAmtCancel = messages["mCoupnCalc.cancel"];

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

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("mCoupnCalcExcelCtrl", function (event, data) {

        if(data.excelType === '1') {
            $scope.searchExcelList(data);
        }else{
            //$scope.searchExcelDivisionList(data);
        }

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $.postJSON("/sale/status/mCoupnCalc/mCoupnCalc/getMCoupnCalcExcelList.sb", params, function (response){
            var grid = $scope.excelFlex;
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;

            if (grid.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            var columns = grid.columns;

            // 조회조건 '승인구분'에 따른 컴럼 show/hidden 처리
            if(orgnFg === "HQ") {
                if (params.saleFg === '1') {
                    columns[11].visible = true;
                    columns[12].visible = true;
                    columns[13].visible = false;
                    columns[14].visible = false;
                } else if (params.saleFg === '-1') {
                    columns[11].visible = false;
                    columns[12].visible = false;
                    columns[13].visible = true;
                    columns[14].visible = true;
                } else {
                    columns[11].visible = true;
                    columns[12].visible = true;
                    columns[13].visible = true;
                    columns[14].visible = true;
                }
            }else{
                if (params.saleFg === '1') {
                    columns[6].visible = true;
                    columns[7].visible = true;
                    columns[8].visible = false;
                    columns[9].visible = false;
                } else if (params.saleFg === '-1') {
                    columns[6].visible = false;
                    columns[7].visible = false;
                    columns[8].visible = true;
                    columns[9].visible = true;
                } else {
                    columns[6].visible = true;
                    columns[7].visible = true;
                    columns[8].visible = true;
                    columns[9].visible = true;
                }
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : false,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, '모바일쿠폰 정산_' + params.startDate + '_' + params.endDate + '_' + getCurDateTime() +'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        }, function(response) {
            s_alert.pop(response.message);
            var grid = $scope.excelFlex;
            grid.itemsSource = new wijmo.collections.CollectionView([]);
        });
    };

}]);


