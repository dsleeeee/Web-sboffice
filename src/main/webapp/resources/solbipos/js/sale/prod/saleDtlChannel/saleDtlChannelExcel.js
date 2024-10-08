/****************************************************************
 *
 * 파일명 : saleDtlChannelExcel.js
 * 설  명 : 매출상세현황(채널별) 매출 다운로드 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.01.06     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 생성구분
var procFgData = [
    {"name":"생성요청","value":"0"},
    {"name":"생성중","value":"1"},
    {"name":"생성완료","value":"2"},
    {"name":"오류발생","value":"9"}
];

// 상품표시옵션
var prodOptionComboData = [
    {"name":"단품/세트","value":"1"},
    {"name":"단품/구성","value":"2"},
    {"name":"단품/세트/구성","value":"3"},
    {"name":"모두표시","value":"4"}
];

/**
 *  매출상세현황(채널별) 매출 다운로드 그리드 생성
 */
app.controller('saleDtlChannelExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('saleDtlChannelExcelCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startMonth = new wijmo.input.InputDate('#startMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });
    var endMonth = new wijmo.input.InputDate('#endMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 자료생성
    var dataCreateMonth = new wijmo.input.InputDate('#dataCreateMonth', {
        format       : "yyyy-MM",
        selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("srchProdOption", prodOptionComboData);                   // 상품표시옵션
    $scope._setComboData("srchStoreHqBrandCd", momsHqBrandCdComboList);            // 매장브랜드
    $scope._setComboData("srchProdHqBrand", momsHqBrandCdComboList);               // 상품브랜드
    $scope._setComboData("srchMomsTeam", momsTeamComboList);                       // 팀별
    $scope._setComboData("srchMomsAcShop", momsAcShopComboList);                   // AC점포별
    $scope._setComboData("srchMomsAreaFg", momsAreaFgComboList);                   // 지역구분
    $scope._setComboData("srchMomsCommercial", momsCommercialComboList);           // 상권
    $scope._setComboData("srchMomsShopType", momsShopTypeComboList);               // 점포유형
    $scope._setComboData("srchMomsStoreManageType", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("srchBranchCd", branchCdComboList);
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

    // // 팀별
    // if(momsTeamComboList.length <= 1) {
    //     $("#srchMomsTeam").css('background-color', '#F0F0F0');
    //     $("#srchMomsTeam").attr("disabled", true);
    // } else {
    //     $("#srchMomsTeam").css('background-color', '#FFFFFF');
    //     $("#srchMomsTeam").attr("disabled", false);
    // }
    // // AC점포별
    // if(momsAcShopComboList.length <= 1) {
    //     $("#srchMomsAcShop").css('background-color', '#F0F0F0');
    //     $("#srchMomsAcShop").attr("disabled", true);
    // } else {
    //     $("#srchMomsAcShop").css('background-color', '#FFFFFF');
    //     $("#srchMomsAcShop").attr("disabled", false);
    // }
    // // 지역구분
    // if(momsAreaFgComboList.length <= 1) {
    //     $("#srchMomsAreaFg").css('background-color', '#F0F0F0');
    //     $("#srchMomsAreaFg").attr("disabled", true);
    // } else {
    //     $("#srchMomsAreaFg").css('background-color', '#FFFFFF');
    //     $("#srchMomsAreaFg").attr("disabled", false);
    // }
    // // 상권
    // if(momsCommercialComboList.length <= 1) {
    //     $("#srchMomsCommercial").css('background-color', '#F0F0F0');
    //     $("#srchMomsCommercial").attr("disabled", true);
    // } else {
    //     $("#srchMomsCommercial").css('background-color', '#FFFFFF');
    //     $("#srchMomsCommercial").attr("disabled", false);
    // }
    // // 점포유형
    // if(momsShopTypeComboList.length <= 1) {
    //     $("#srchMomsShopType").css('background-color', '#F0F0F0');
    //     $("#srchMomsShopType").attr("disabled", true);
    // } else {
    //     $("#srchMomsShopType").css('background-color', '#FFFFFF');
    //     $("#srchMomsShopType").attr("disabled", false);
    // }
    // // 매장관리타입
    // if(momsStoreManageTypeComboList.length <= 1) {
    //     $("#srchMomsStoreManageType").css('background-color', '#F0F0F0');
    //     $("#srchMomsStoreManageType").attr("disabled", true);
    // } else {
    //     $("#srchMomsStoreManageType").css('background-color', '#FFFFFF');
    //     $("#srchMomsStoreManageType").attr("disabled", false);
    // }
    // // 그룹
    // if(branchCdComboList.length <= 1) {
    //     $("#srchBranchCd").css('background-color', '#F0F0F0');
    //     $("#srchBranchCd").attr("disabled", true);
    // } else {
    //     $("#srchBranchCd").css('background-color', '#FFFFFF');
    //     $("#srchBranchCd").attr("disabled", false);
    // }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.procFgDataMap = new wijmo.grid.DataMap(procFgData, 'value', 'name'); // 생성구분
        $scope.prodOptionComboDataMap = new wijmo.grid.DataMap(prodOptionComboData, 'value', 'name'); // 그룹
        $scope.branchCdDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name'); // 그룹
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name'); // 팀별
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name'); // AC점포별
        $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList, 'value', 'name'); // 지역구분
        $scope.momsCommercialDataMap = new wijmo.grid.DataMap(momsCommercialComboList, 'value', 'name'); // 상권
        $scope.momsShopTypeDataMap = new wijmo.grid.DataMap(momsShopTypeComboList, 'value', 'name'); // 점포유형
        $scope.momsStoreManageTypeDataMap = new wijmo.grid.DataMap(momsStoreManageTypeComboList, 'value', 'name'); // 매장관리타입
        $scope.momsStoreFg01DataMap = new wijmo.grid.DataMap(momsStoreFg01ComboList, 'value', 'name'); // 매장그룹
        $scope.momsStoreFg02DataMap = new wijmo.grid.DataMap(momsStoreFg02ComboList, 'value', 'name'); // 매장그룹2
        $scope.momsStoreFg03DataMap = new wijmo.grid.DataMap(momsStoreFg03ComboList, 'value', 'name'); // 매장그룹3
        $scope.momsStoreFg04DataMap = new wijmo.grid.DataMap(momsStoreFg04ComboList, 'value', 'name'); // 매장그룹4
        $scope.momsStoreFg05DataMap = new wijmo.grid.DataMap(momsStoreFg05ComboList, 'value', 'name'); // 매장그룹5

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }

                // 다운로드
                if (col.binding === "download") {
                    var item = s.rows[e.row].dataItem;
                    // 값이 있으면 링크 효과
                    if (nvl(item[("procFg")], '') == '2') {
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 다운로드 클릭시 상세정보 조회
                if ( col.binding === "download") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    // 값이 있으면 링크
                    if (nvl(selectedRow[("procFg")], '') == '2') {
                        // 다운로드
                        saleReport_download(selectedRow.fileName);
                        setTimeout(function() {
                            $("#deleteCookie_info").submit();
                        }, 500);
                    }
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("saleDtlChannelExcelCtrl", function(event, data) {
        $scope.searchsaleDtlChannelExcel();
        event.preventDefault();
    });

    $scope.searchsaleDtlChannelExcel = function(){

        // 조회기간
        var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
        var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
        var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }
        // 조회일자 최대 3개월 제한
        if (diffMonth > 3) {
            $scope._popMsg(messages['cmm.dateOver.3month.error']);
            return false;
        }
        
        var params = {};
        params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM');
        params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');
        params.prodClassCd = $scope.prodClassCd;
        params.prodOption = $scope.srchProdOptionCombo.selectedValue;
        params.prodCd = $("#srchProdCd").val();
        params.prodNm = $("#srchProdNm").val();
        params.prodCds = $("#saleDtlChannelExcelSelectCd").val();
        params.dlvrInFgCol = dlvrInFgCol;

        if(orgnFg === "HQ") {
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
            params.storeCds = $("#saleDtlChannelExcelStoreCd").val();
            params.prodHqBrandCd = $scope.srchProdHqBrandCombo.selectedValue;
            params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
            params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
            params.branchCd = $scope.srchBranchCdCombo.selectedValue;
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;

            // '전체' 일때
            if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) { // 수정완료 1992
                var momsHqBrandCd = "";
                for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
                    if (momsHqBrandCdComboList[i].value !== null) {
                        momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = momsHqBrandCd;
            }
        }

        console.log(params);

        $scope._inquiryMain("/sale/prod/saleDtlChannel/saleDtlChannelExcel/getSaleDtlChannelExcelList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // <-- 자료생성 -->
    $scope.dataCreate = function(){
        var createMonth = wijmo.Globalize.format(dataCreateMonth.value, 'yyyyMM');
        var createMonthLastDate = new Date(createMonth.substring(0, 4), createMonth.substring(4, 6), 0).getDate();

        // 자료생성 요청건 존재여부 확인
        var params = {};
        params.dataCreateMonth = createMonth;
        params.dataCreateMonthLastDate = createMonthLastDate;

        params.prodClassCd = $scope.prodClassCd;
        params.prodOption = $scope.srchProdOptionCombo.selectedValue;
        params.prodCd = $("#srchProdCd").val();
        params.prodNm = $("#srchProdNm").val();
        params.prodCds = $("#saleDtlChannelExcelSelectCd").val();
        params.dlvrInFgCol = dlvrInFgCol;

        if(orgnFg === "HQ") {
            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
            params.storeCds = $("#saleDtlChannelExcelStoreCd").val();
            params.prodHqBrandCd = $scope.srchProdHqBrandCombo.selectedValue;
            params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
            params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
            params.branchCd = $scope.srchBranchCdCombo.selectedValue;
            params.momsStoreFg01 = $scope.momsStoreFg01;
            params.momsStoreFg02 = $scope.momsStoreFg02;
            params.momsStoreFg03 = $scope.momsStoreFg03;
            params.momsStoreFg04 = $scope.momsStoreFg04;
            params.momsStoreFg05 = $scope.momsStoreFg05;

            // '전체' 일때
            // if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) { // 수정완료 1992
                var momsHqBrandCd = "";
                for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
                    if (momsHqBrandCdComboList[i].value !== null) {
                        momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = momsHqBrandCd;
            // }
        }

        console.log(params);

        // $scope._postJSONQuery.withOutPopUp( "/sale/prod/saleDtlChannel/saleDtlChannelExcel/getSaleDtlChannelChk.sb", params, function(response){
        //     var saleDtlChannelExcel = response.data.data.result;
        //     $scope.saleDtlChannelExcel = saleDtlChannelExcel;

            // if($scope.saleDtlChannelExcel.cnt > 0) {
            //     var msg = createMonth + " " + messages["saleDtlChannelExcel.saleMonthAlert"]; // 자료가 존재합니다. 삭제 후 진행해주세요.
            //     $scope._popMsg(msg);
            //     return;
            // } else {
                $scope.save(params);
            // }
        // });
    };

    $scope.save = function(params){
        // 자료생성을 하시겠습니까?
        $scope._popConfirm(messages["saleDtlChannelExcel.dataCreateSaveConfirm"], function() {
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/sale/prod/saleDtlChannel/saleDtlChannelExcel/getSaleDtlChannelSave.sb", params, function(){
                $scope.allSearch();
            });
        });
    };
    // <-- //자료생성 -->

    // <-- 삭제 -->
    $scope.del = function(){
        $scope._popConfirm(messages["cmm.choo.delete"], function() {
            // 파라미터 설정
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

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/sale/prod/saleDtlChannel/saleDtlChannelExcel/getSaleDtlChannelDel.sb", params, function(){
                $scope.allSearch();
            });
        });
    };
    // <-- //삭제 -->

    // 재조회
    $scope.allSearch = function () {
        $scope.searchsaleDtlChannelExcel();
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChangeExcel = function(){
        if( $("#tblSearchAddShowExcel").css("display") === 'none') {
            $("#tblSearchAddShowExcel").show();
        } else {
            $("#tblSearchAddShowExcel").hide();
        }
    };

    // 상품선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.saleDtlChannelExcelSelectShow = function () {
        $scope._broadcast('saleDtlChannelExcelSelectCtrl');
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
                        $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

    // 관리자생성 보여줌
    $scope.regist = function (){
        if( $("#regist").css("display") === 'none') {
            $("#regist").show();
        } else {
            $("#regist").hide();
        }
    }

    // 일괄다운로드
    $scope.allDownload = function (){

        var fileNames = new Array();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                // 값이 있으면 링크
                if (nvl($scope.flex.collectionView.items[i].procFg, '') == '2') {
                    // 다운로드
                    fileNames.push($scope.flex.collectionView.items[i].fileName);
                }
            }
        }
        download(fileNames);
    }

    async function download(fileNames) {
        const downloadChk = () =>
            new Promise((resolve) => {
                var chk = setInterval(function() {
                    var token = getCookie("fileDownloadToken");
                    // console.log(token);
                    if(token === "true") {
                        $("#deleteCookie_info").submit();
                        setTimeout(function() {
                            clearInterval(chk);
                            $scope.excelUploadingPopup(false);
                            resolve();
                        }, 50);
                    }
                }, 1000 );
            });

        for (let i = 0; i < fileNames.length; i++) {

            var frm = $('<form id="saleReport_info_all'+ i +'" method="post" action="/sale/prod/saleDtlChannel/saleDtlChannelExcel/getSaleDtlChannelDownload.sb" target="saleReportFrm' + i + '" async><iframe id="saleReportFrm' + i + '" name="saleReportFrm' + i + '" style="display: none;"></iframe><input type="hidden" name="fileName" value="'+fileNames[i] +'" /></form>');
            frm.appendTo("body");

            $scope.excelUploadingPopup(true);
            $("#totalRows").html(fileNames.length);
            var j = i;
            $("#progressCnt").html(j+1);

            $("#saleReport_info_all" + i).submit();

            const result = await downloadChk();
        }
    }

    function getCookie(name) {
        var parts = document.cookie.split(name + "=");
        if (parts.length == 2) return parts.pop().split(";").shift();
    }

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['touchKey.loading.msg'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 진행 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            // html 적용
            $scope._loadingPopup.content.innerHTML = innerHtml;
            // 팝업 show
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
    };
}]);