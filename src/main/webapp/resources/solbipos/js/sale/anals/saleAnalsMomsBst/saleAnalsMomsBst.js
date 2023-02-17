/****************************************************************
 *
 * 파일명 : saleAnalsMomsBst.js
 * 설  명 : 매출분석(사업전략팀) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.01.27     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

// 화면출력
var viewTypeComboData = [
    {"name": "매장별", "value": "store"},
    {"name": "상품별", "value": "prod"}
];

// 상품표시옵션
var prodOptionComboData = [
    {"name": "단품/세트", "value": "1"},
    {"name": "단품/구성", "value": "2"},
    {"name": "단품/세트/구성", "value": "3"}/*,
    {"name":"모두표시","value":"4"}*/
];
// 일자표시옵션
/*var dayOptionComboData = [
    {"name":"일자별","value":"1"},
    {"name":"기간합","value":"2"}
];*/
// 일/월/년 구분
var dayGubunComboData = [
    {"name": "일", "value": "day"},
    {"name": "월", "value": "month"}
    /*, {"name": "년", "value": "year"}*/
];

/** controller */
app.controller('saleAnalsMomsBstCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleAnalsMomsBstCtrl', $scope, $http, true));

    // 조회일자(일)
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 조회일자(월)
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format: "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format: "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 조회일자(년)
    var arrYear = [];
    var today = new Date();
    var yyyy = today.getFullYear();

    for (var i = 2004; i <= yyyy; i++) {
        arrYear[i - 2004] = i;
    }

    var startYear = new wijmo.input.ComboBox('#startYear', {
        itemsSource: arrYear,
        selectedValue: yyyy,
    });
    var endYear = new wijmo.input.ComboBox('#endYear', {
        itemsSource: arrYear,
        selectedValue: yyyy,
    });

    // 콤보박스 셋팅
    $scope._setComboData("srchViewType", viewTypeComboData);                       // 화면출력
    $scope._setComboData("srchDayGubun", dayGubunComboData);                       // 일/월 구분
    $scope._setComboData("srchProdOption", prodOptionComboData);                   // 상품표시옵션
    //$scope._setComboData("srchDayOption", dayOptionComboData);                      // 일자표시옵션
    $scope._setComboData("srchStoreHqBrandCd", momsHqBrandCdComboList);            // 매장브랜드
    $scope._setComboData("srchProdHqBrand", momsHqBrandCdComboList);               // 상품브랜드
    $scope._setComboData("srchMomsTeam", momsTeamComboList);                       // 팀별
    $scope._setComboData("srchMomsAcShop", momsAcShopComboList);                   // AC점포별
    $scope._setComboData("srchMomsAreaFg", momsAreaFgComboList);                   // 지역구분
    $scope._setComboData("srchMomsCommercial", momsCommercialComboList);           // 상권
    $scope._setComboData("srchMomsShopType", momsShopTypeComboList);               // 점포유형
    $scope._setComboData("srchMomsStoreManageType", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("srchBranchCd", branchCdComboList);                       // 지사

    // // 팀별
    // if (momsTeamComboList.length <= 1) {
    //     $("#srchMomsTeam").css('background-color', '#F0F0F0');
    //     $("#srchMomsTeam").attr("disabled", true);
    // } else {
    //     $("#srchMomsTeam").css('background-color', '#FFFFFF');
    //     $("#srchMomsTeam").attr("disabled", false);
    // }
    // // AC점포별
    // if (momsAcShopComboList.length <= 1) {
    //     $("#srchMomsAcShop").css('background-color', '#F0F0F0');
    //     $("#srchMomsAcShop").attr("disabled", true);
    // } else {
    //     $("#srchMomsAcShop").css('background-color', '#FFFFFF');
    //     $("#srchMomsAcShop").attr("disabled", false);
    // }
    // // 지역구분
    // if (momsAreaFgComboList.length <= 1) {
    //     $("#srchMomsAreaFg").css('background-color', '#F0F0F0');
    //     $("#srchMomsAreaFg").attr("disabled", true);
    // } else {
    //     $("#srchMomsAreaFg").css('background-color', '#FFFFFF');
    //     $("#srchMomsAreaFg").attr("disabled", false);
    // }
    // // 상권
    // if (momsCommercialComboList.length <= 1) {
    //     $("#srchMomsCommercial").css('background-color', '#F0F0F0');
    //     $("#srchMomsCommercial").attr("disabled", true);
    // } else {
    //     $("#srchMomsCommercial").css('background-color', '#FFFFFF');
    //     $("#srchMomsCommercial").attr("disabled", false);
    // }
    // // 점포유형
    // if (momsShopTypeComboList.length <= 1) {
    //     $("#srchMomsShopType").css('background-color', '#F0F0F0');
    //     $("#srchMomsShopType").attr("disabled", true);
    // } else {
    //     $("#srchMomsShopType").css('background-color', '#FFFFFF');
    //     $("#srchMomsShopType").attr("disabled", false);
    // }
    // // 매장관리타입
    // if (momsStoreManageTypeComboList.length <= 1) {
    //     $("#srchMomsStoreManageType").css('background-color', '#F0F0F0');
    //     $("#srchMomsStoreManageType").attr("disabled", true);
    // } else {
    //     $("#srchMomsStoreManageType").css('background-color', '#FFFFFF');
    //     $("#srchMomsStoreManageType").attr("disabled", false);
    // }
    // // 지사
    // if (branchCdComboList.length <= 1) {
    //     $("#srchBranchCd").css('background-color', '#F0F0F0');
    //     $("#srchBranchCd").attr("disabled", true);
    // } else {
    //     $("#srchBranchCd").css('background-color', '#FFFFFF');
    //     $("#srchBranchCd").attr("disabled", false);
    // }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
          var ht = s.hitTest(e);
          s.allowSorting = false;
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("saleAnalsMomsBstCtrl", function (event, data) {
        // 조회
        $scope.searchList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 조회
    $scope.searchList = function () {

        // 기간제한 체크
        if($scope.srchDayGubunCombo.selectedValue === "day"){

            // 조회기간
            var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }

            // 조회일자 최대 7일 제한
            if (diffDay > 6) {
                s_alert.pop(messages['cmm.dateOver.7day.error']);
                return false;
            }

        }else if($scope.srchDayGubunCombo.selectedValue === "month"){

            var startDt = new Date(wijmo.Globalize.format(startMonth.value + "-01", 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format(endMonth.value + "-01", 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }

            // 조회일자 최대 6개월 제한
            if (diffDay > 180) {
                s_alert.pop(messages['cmm.dateOver.6month.error']);
                return false;
            }

        }else if($scope.srchDayGubunCombo.selectedValue === "year"){

            var startDt = new Date(wijmo.Globalize.format(startYear.selectedValue + "-01-01", 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format(endYear.selectedValue + "-12-31", 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if(startDt.getTime() > endDt.getTime()){
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }

            // 조회일자 최대 1년 제한
            if (diffDay > 365) {
                s_alert.pop(messages['cmm.dateOver.1year.error']);
                return false;
            }
        }

        // 파라미터
        var params = {};
        params.viewType = $scope.srchViewTypeCombo.selectedValue;

        var opt1 = ""; // 조회옵션1
        if($("#chkOpt1Store").is(":checked")){opt1 += "1,"}
        if($("#chkOpt1App").is(":checked")){opt1 += "9,"}
        if($("#chkOpt1DlvrAppBaemin").is(":checked")){opt1 += "3,"}
        if($("#chkOpt1DlvrAppYogiyo").is(":checked")){opt1 += "4,"}
        if($("#chkOpt1DlvrAppEtc").is(":checked")){opt1 += "2,5,6,7,8,10,11,12,13"}
        params.dlvrInFg = opt1;

        var opt2 = ""; // 조회옵션2
        if($("#chkOpt2StoreIn").is(":checked")){opt2 += "1,"}
        if($("#chkOpt2Pack").is(":checked")){opt2 += "3,"}
        if($("#chkOpt2Dlvr").is(":checked")){opt2 += "2,"}
        params.dlvrOrderFg = opt2;

        params.dayGubun = $scope.srchDayGubunCombo.selectedValue;
        if (params.dayGubun === "day") {
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        } else if (params.dayGubun === "month") {
            params.startDate = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
            params.endDate = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        } else if (params.dayGubun === "year") {
            params.startDate = startYear.selectedValue;
            params.endDate = endYear.selectedValue;
        }

        params.prodClassCd = $scope.prodClassCd;
        //params.dayOption = $scope.srchDayOptionCombo.selectedValue;
        params.prodOption = $scope.srchProdOptionCombo.selectedValue;
        params.prodCd = $("#srchProdCd").val();
        params.prodNm = $("#srchProdNm").val();
        params.storeCds = $("#saleAnalsMomsBstStoreCd").val();
        params.prodCds = $("#saleAnalsMomsBstSelectCd").val();
        //params.listScale = 500; //-페이지 스케일 갯수

        if (orgnFg === "HQ") {
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
            params.prodHqBrandCd = $scope.srchProdHqBrandCombo.selectedValue;
            params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
            params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
            params.branchCd = $scope.srchBranchCdCombo.selectedValue;

            // '전체' 일때
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var momsHqBrandCd = "";
                for(var i=0; i < momsHqBrandCdComboList.length; i++){
                    if(momsHqBrandCdComboList[i].value !== null) {
                        momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = momsHqBrandCd;
            }
        }

        // 화면출력과 상품,매장 조회조건 입력에 따른 리스트 타입 셋팅
        params.listType = "all";
        if(params.viewType === "store"){ //화면출력이 매장별 이면서
            // 분류, 상품코드, 상품명, 상품브랜드, 상품멀티선택 조회조건이 있는경우 
            if(!(params.prodClassCd === undefined   || params.prodClassCd === null   || params.prodClassCd === ""       )){params.listType = "part"}
            if(!(params.prodCd === undefined        || params.prodCd === null        || params.prodCd === ""            )){params.listType = "part"}
            if(!(params.prodNm === undefined        || params.prodNm === null        || params.prodNm === ""            )){params.listType = "part"}
            if(!(params.prodHqBrandCd === undefined || params.prodHqBrandCd === null || params.prodHqBrandCd === ""     )){params.listType = "part"}
            if(!(params.prodCds === undefined       || params.prodCds === null       || params.prodCds === ""           )){params.listType = "part"}
        }else if(params.viewType === "prod"){ // 화면출력이 상품별 이면서
            // 매장브랜드, 매장멀티선택 조회조건이 있는경우
            if(!(params.storeHqBrandCd === undefined || params.storeHqBrandCd === null || params.storeHqBrandCd === ""  )){params.listType = "part"}
            if(!(params.storeCds === undefined       || params.storeCds === null       || params.storeCds === ""        )){params.listType = "part"}
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/sale/anals/saleAnalsMomsBst/saleAnalsMomsBst/getSaleAnalsMomsBstList.sb", params, function (response) {

            var list = response.data.data.list;
            var length = response.data.data.list.length;
            var grid = wijmo.Control.getControl("#wjGridList");
            var columns = grid.columns;

            // 화면출력 방식에 따른 사용컬럼 show/hidden(매장정보, 분류, 상품정보)
            if(params.viewType === "store"){
                columns[0].visible = false;
                columns[1].visible = false;
                columns[2].visible = false;
                columns[11].visible = true;
                columns[12].visible = true;
                columns[13].visible = true;

                if(params.listType === "all"){
                    columns[3].visible = false;
                    columns[4].visible = false;
                    columns[5].visible = false;
                    columns[6].visible = false;
                    columns[7].visible = false;
                    columns[8].visible = false;
                    columns[9].visible = false;
                    columns[10].visible = false;
                }else if(params.listType === "part"){
                    columns[3].visible = true;
                    columns[4].visible = true;
                    columns[5].visible = true;
                    columns[6].visible = true;
                    columns[7].visible = true;
                    columns[8].visible = true;
                    columns[9].visible = true;
                    columns[10].visible = true;
                }

            }else if(params.viewType === "prod"){
                columns[3].visible = true;
                columns[4].visible = true;
                columns[5].visible = true;
                columns[6].visible = true;
                columns[7].visible = true;
                columns[8].visible = true;
                columns[9].visible = true;
                columns[10].visible = true;

                if(params.listType === "all"){
                    columns[0].visible = false;
                    columns[1].visible = false;
                    columns[2].visible = false;
                    columns[11].visible = false;
                    columns[12].visible = false;
                    columns[13].visible = false;
                }else if(params.listType === "part"){
                    columns[0].visible = true;
                    columns[1].visible = true;
                    columns[2].visible = true;
                    columns[11].visible = false;
                    columns[12].visible = false;
                    columns[13].visible = false;
                }
            }

            // rows, footer 초기화
            grid.rows.clear();
            grid.columnFooters.rows.clear();

            if(length > 0){

                // 동적 컬럼 초기화
                if (length != "" || length != null) {
                    while (grid.columns.length > 16) {
                        grid.columns.removeAt(grid.columns.length - 1);
                    }
                }

                // 기간선택 두 날짜 사이 모든날짜 구하기
                // ajax 통신 설정
                $http({
                    method : 'POST', //방식
                    url    : "/sale/anals/saleAnalsMomsBst/saleAnalsMomsBst/getDateDiff.sb", /* 통신할 URL */
                    params : params, /* 파라메터로 보낼 데이터 */
                    headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
                }).then(function successCallback(response) {
                    if ($scope._httpStatusCheck(response, true)) {

                        var dateArr = response.data.data.list;
                        
                        // 날짜 형태 정규식
                        var regex = {};
                        if(params.dayGubun === "day"){
                            regex[0] = /(\d{4})(\d{2})(\d{2})/g;
                            regex[1] = '$1-$2-$3';
                        }else if(params.dayGubun === "month"){
                            regex[0] = /(\d{4})(\d{2})/g;
                            regex[1] = '$1-$2';
                        }else if(params.dayGubun === "year"){
                            regex[0] = /(\d{4})/g;
                            regex[1] = '$1';
                        }

                        // 컬럼 셋팅
                        if(params.viewType === "store") {
                            // 고객수
                            for (var i = 0; i < dateArr.length; i++) {
                                grid.columns.push(new wijmo.grid.Column({
                                    header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                    binding: "guestCnt" + params.prodOption + dateArr[i].sOrgDate,
                                    width: 90,
                                    align: "center",
                                    isReadOnly: "true"
                                }));
                            }
                            grid.columns.push(new wijmo.grid.Column({
                                header: messages["saleAnalsMomsBst.total"],
                                binding: "totGuestCnt",
                                width: 90,
                                align: "center",
                                isReadOnly: "true"
                            }));
                        }

                        // 판매수량
                        for (var i = 0; i < dateArr.length; i++) {
                            grid.columns.push(new wijmo.grid.Column({
                                header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                binding: "saleQty" + params.prodOption + dateArr[i].sOrgDate,
                                width: 90,
                                align: "center",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["saleAnalsMomsBst.total"],
                            binding: "totSaleQty",
                            width: 90,
                            align: "center",
                            isReadOnly: "true"
                        }));

                        // 총매출
                        for (var i = 0; i < dateArr.length; i++) {
                            grid.columns.push(new wijmo.grid.Column({
                                header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                binding: "realSaleAmt" + params.prodOption + dateArr[i].sOrgDate,
                                width: 90,
                                align: "center",
                                isReadOnly: "true"
                            }));
                        }
                        grid.columns.push(new wijmo.grid.Column({
                            header: messages["saleAnalsMomsBst.total"],
                            binding: "totRealSaleAmt",
                            width: 90,
                            align: "center",
                            isReadOnly: "true"
                        }));

                        if(params.viewType === "prod") {
                            // P.MIX
                            for (var i = 0; i < dateArr.length; i++) {
                                grid.columns.push(new wijmo.grid.Column({
                                    header: dateArr[i].sOrgDate.replace(regex[0], regex[1]),
                                    binding: "pMix" + params.prodOption + dateArr[i].sOrgDate,
                                    width: 90,
                                    align: "center",
                                    isReadOnly: "true"
                                }));
                            }
                            grid.columns.push(new wijmo.grid.Column({
                                header: messages["saleAnalsMomsBst.total"],
                                binding: "totPMix",
                                width: 90,
                                align: "center",
                                isReadOnly: "true"
                            }));
                        }

                        // 데이터 뿌리기
                        var data = new wijmo.collections.CollectionView(list);
                        data.trackChanges = true;
                        $scope.data = data;

                        /*// header, footer 만들기
                        // 기존 footer 초기화
                        grid.columnFooters.rows.clear();

                        // add the new GroupRow to the grid's 'columnFooters' panel
                        grid.columnFooters.rows.push(new wijmo.grid.GroupRow());
                        // add a sigma to the header to show that this is a summary row
                        grid.bottomLeftCells.setCellData(0, 0, '합계');*/

                        // grid merge 가능 영역
                        // All:= 7 (Merge all areas), AllHeaders:= 6(Merge column and row headers), Cells:= 1(Merge scrollable cells),
                        // ColumnHeaders:= 2(Merge column headers), None:= 0(No merging), RowHeaders:= 4(Merge row headers)
                        grid.allowMerging = 'ColumnHeaders';
                        // grid header 라인 기본 2줄 유지
                        if(2 > grid.columnHeaders.rows.length){
                            grid.columnHeaders.rows.push(new wijmo.grid.Row());
                        }

                        // 첫째줄 헤더 생성
                        var dataItem = {};
                        dataItem.storeCd = messages["saleAnalsMomsBst.storeCd"];
                        dataItem.storeNm = messages["saleAnalsMomsBst.storeNm"];
                        dataItem.areaNm = messages["saleAnalsMomsBst.area"];
                        dataItem.lClassCd = messages["saleAnalsMomsBst.lClassCd"];
                        dataItem.lClassNm = messages["saleAnalsMomsBst.lClassNm"];
                        dataItem.mClassCd = messages["saleAnalsMomsBst.mClassCd"];
                        dataItem.mClassNm = messages["saleAnalsMomsBst.mClassNm"];
                        dataItem.sClassCd = messages["saleAnalsMomsBst.sClassCd"];
                        dataItem.sClassNm = messages["saleAnalsMomsBst.sClassNm"];
                        dataItem.prodCd = messages["saleAnalsMomsBst.prodCd"];
                        dataItem.prodNm = messages["saleAnalsMomsBst.prodNm"];
                        dataItem.storeCd = messages["saleAnalsMomsBst.storeCd"];
                        dataItem.storeNm = messages["saleAnalsMomsBst.storeNm"];
                        dataItem.areaNm = messages["saleAnalsMomsBst.area"];
                        dataItem.orderSeq = "";
                        dataItem.rowType = "";

                        if(params.viewType === "store") {
                            for (var i = 0; i < dateArr.length; i++) {
                                eval('dataItem.guestCnt' + params.prodOption + dateArr[i].sOrgDate + '= "고객수"');
                            }
                            dataItem.totGuestCnt = "고객수";
                        }

                        for (var i = 0; i < dateArr.length; i++) {
                            eval('dataItem.saleQty' + params.prodOption + dateArr[i].sOrgDate + '= "판매수량"');
                        }
                        dataItem.totSaleQty = "판매수량";

                        for (var i = 0; i < dateArr.length; i++) {
                            eval('dataItem.realSaleAmt' + params.prodOption + dateArr[i].sOrgDate + '= "총매출"');
                        }
                        dataItem.totRealSaleAmt = "총매출";

                        if(params.viewType === "prod") {
                            for (var i = 0; i < dateArr.length; i++) {
                                eval('dataItem.pMix' + params.prodOption + dateArr[i].sOrgDate + '= "P.MIX"');
                            }
                            dataItem.totPMix = "P.MIX";
                        }

                        grid.columnHeaders.rows[0].dataItem = dataItem;

                        grid.itemFormatter = function (panel, r, c, cell) {

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
                                var rows = panel.rows[r];

                                if (col.isReadOnly) {
                                    wijmo.addClass(cell, 'wj-custom-readonly');
                                }
                            }
                        };

                        grid.formatItem.addHandler(function (s, e) {
                            if (e.panel === s.cells) {
                                var col = s.columns[e.col];
                                var item = s.rows[e.row].dataItem;

                                // 집계 row에 색상 넣기
                                if (item[("rowType")] === 'R2') { // 소계
                                    wijmo.addClass(e.cell, 'wij_gridBackground-pink-bold');
                                }
                                if (item[("rowType")] === 'R3') { // 매장별 & 상품별 총계
                                    wijmo.addClass(e.cell, 'wij_gridBackground-yellow-bold');
                                }
                                if (item[("rowType")] === 'R4') { // 총계
                                    wijmo.addClass(e.cell, 'wij_gridBackground-gary-bold');
                                }

                                // 소계, 총계 텍스트 표시
                                if(params.viewType === "store"){
                                    if(params.listType === "all"){
                                        if(item[("rowType")] === 'R4'){
                                            if(col.binding === "areaNm"){
                                                e.cell.innerHTML = "총계";
                                            }
                                            if(col.binding === "prodNm"){
                                                e.cell.innerHTML = "";
                                            }
                                        }
                                    }else if(params.listType === "part"){

                                        if(item[("rowType")] === 'R2'){
                                            if(col.binding === "prodNm"){
                                                e.cell.innerHTML = "소계";
                                            }
                                            if(col.binding === "areaNm"){
                                                e.cell.innerHTML = "";
                                            }
                                        }

                                        if(item[("rowType")] === 'R3'){
                                            if(col.binding === "prodNm") {
                                                if(e.cell.innerHTML === ""){
                                                    e.cell.innerHTML = "총계";
                                                }
                                            }
                                        }

                                        if(item[("rowType")] === 'R4'){
                                            if(col.binding === "prodNm"){
                                                e.cell.innerHTML = "총계";
                                            }
                                            if(col.binding === "areaNm"){
                                                e.cell.innerHTML = "";
                                            }
                                        }
                                    }
                                }else if(params.viewType === "prod"){
                                    if(params.listType === "all"){
                                        if(item[("rowType")] === 'R4'){
                                            if(col.binding === "prodNm"){
                                                e.cell.innerHTML = "총계";
                                            }
                                            if(col.binding === "areaNm"){
                                                e.cell.innerHTML = "";
                                            }
                                        }
                                    }else if(params.listType === "part"){

                                        if(item[("rowType")] === 'R2'){
                                            if(col.binding === "areaNm"){
                                                e.cell.innerHTML = "소계";
                                            }
                                            if(col.binding === "prodNm"){
                                                e.cell.innerHTML = "";
                                            }
                                        }

                                        if(item[("rowType")] === 'R3'){
                                            if(col.binding === "areaNm"){
                                                if(e.cell.innerHTML === ""){
                                                    e.cell.innerHTML = "총계";
                                                }
                                            }
                                        }

                                        if(item[("rowType")] === 'R4'){
                                            if(col.binding === "areaNm"){
                                                e.cell.innerHTML = "총계";
                                            }
                                            if(col.binding === "prodNm"){
                                                e.cell.innerHTML = "";
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                }, function errorCallback(response) {
                    $scope._popMsg(messages["cmm.error"]);
                    return false;
                }).then(function () {
                    if (typeof callback === 'function') {
                        $timeout(function () {
                            callback();
                        }, 10);
                    }
                });
            }
        });
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.saleAnalsMomsBstStoreShow = function () {
        $scope._broadcast('saleAnalsMomsBstStoreCtrl');
    };

    // 상품선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.saleAnalsMomsBstSelectShow = function () {
        $scope._broadcast('saleAnalsMomsBstSelectCtrl');
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {
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
                    function (response) {
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function () {
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function () {
        if ($("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 일/월/년 구분 콤보박스 선택 이벤트
    $scope.setDayGubunCombo = function (s) {
        if (s.selectedValue === "day") {
            $("#spanDay").css("display", "");
            $("#spanMonth").css("display", "none");
            $("#spanYear").css("display", "none");
        } else if (s.selectedValue === "month") {
            $("#spanDay").css("display", "none");
            $("#spanMonth").css("display", "");
            $("#spanYear").css("display", "none");
        } else if (s.selectedValue === "year") {
            $("#spanDay").css("display", "none");
            $("#spanMonth").css("display", "none");
            $("#spanYear").css("display", "");
        }
    };

    // 엑셀다운로드
    $scope.excelDownload = function () {
        if ($scope.flex.rows.length <= 0) {
          $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
          return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    return column.visible;
                }
            },  messages["saleAnalsMomsBst.saleAnalsMomsBst"] + '_'+ getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
            });
        }, 10);
    }

}]);