/****************************************************************
 *
 * 파일명 : multiBizManage.js
 * 설  명 : 매장정보관리 > 매장환경설정 > 매장환경 탭 > 다중사업자관리 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.02.28     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 대표코너 여부
var baseYnData = [
    {"name":"대표","value":"Y"},
    {"name":"미대표","value":"N"}
];

// vandorList 에서 name 만을 dataMap으로 사용. (name과 value 동시 사용시 오류) // todo 추후 수정 필요
var allVanList = new Array();
var vanList = new Array();
var paycoList = new Array();
var mpayList = new Array();
var mcoupnList = new Array();
var paperVoucherList = new Array();
var taxRefundList = new Array();
var sktList = new Array();
var cornerFgList = [];
var cornerFgListDataMap = [];

for (var i in vandorList) {
    if (vandorList[i].vanFg === '01') {
        vanList.push(vandorList[i].name);
    } else if (vandorList[i].vanFg === '02') {
        paycoList.push(vandorList[i].name);
    } else if (vandorList[i].vanFg === '03') {
        mpayList.push(vandorList[i].name);
    } else if (vandorList[i].vanFg === '04') {
        mcoupnList.push(vandorList[i].name);
    } else if (vandorList[i].vanFg === '06') {
        paperVoucherList.push(vandorList[i].name);
    } else if (vandorList[i].vanFg === '07') {
        taxRefundList.push(vandorList[i].name);
    } else if (vandorList[i].vanFg === '08') {
        sktList.push(vandorList[i].name);
    }
}

/**
 *  다중사업자관리 팝업 그리드 생성
 */
app.controller('multiBizManageCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('multiBizManageCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.vendorFgDataMap = new wijmo.grid.DataMap(vendorFg, 'value', 'name');
        // $scope.vanCdDataMap = new wijmo.grid.DataMap(vandorList, 'value', 'name');
        $scope.vanCdDataMap = allVanList;
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFg, 'value', 'name');
        $scope.baseYnDataMap = new wijmo.grid.DataMap(baseYnData, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "cornrCd") {
                    if (item.status !== "I") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }
                if (col.binding === "vendorFg") {
                    if (item.cornrCd === "") {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }
                if ($("#lblVanFixFg").text() == "Y") {
                    if (col.binding === "vendorNm") {
                        if (item.vendorFg === "01") {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);

                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }
                }
                if (col.binding === "cornrNm" || col.binding === "ownerNm" || col.binding === "bizNo" || col.binding === "baseYn" || col.binding === "telNo") {
                    if (item.status !== "I") {
                        if (item.baseVanYn !== "Y" && item.cornrRnum != "1") {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);

                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }

                    if(item.status === "I"){
                        if(item.cornrCd !== ""){
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);

                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }
                }
            }
        });

        // 구분, 상세 그리드 에디팅 방지
        s.beginningEdit.addHandler(function (sender, elements) {
            var col = sender.columns[elements.col];
            if (col.binding === "cornrCd") {
                var dataItem = s.rows[elements.row].dataItem;
                if (dataItem.status !== "I") {
                    elements.cancel = true;
                }
                /*if (dataItem.status === "U") {
                    elements.cancel = true;
                } else if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
                    elements.cancel = true;
                } else if (dataItem.status === "I") {
                    if (col.binding === "cornrCd" && !isNull(terminalScope.getCornerFgVal())) {
                        elements.cancel = true;
                    }
                }*/
            }
            if (col.binding === "vendorFg") {
                var dataItem = s.rows[elements.row].dataItem;
                if(dataItem.cornrCd === "") {
                    elements.cancel = true;
                }
            }
            if ($("#lblVanFixFg").text() == "Y") {
                if (col.binding === "vendorNm") {
                    var dataItem = s.rows[elements.row].dataItem;
                    if(dataItem.vendorFg === "01") {
                        elements.cancel = true;
                    }
                }
            }
            if (col.binding === "cornrNm" || col.binding === "ownerNm" || col.binding === "bizNo" || col.binding === "baseYn" || col.binding === "telNo") {
                var dataItem = s.rows[elements.row].dataItem;
                if (dataItem.status !== "I") {
                    if (dataItem.baseVanYn !== "Y" && dataItem.cornrRnum != "1") {
                        elements.cancel = true;
                    }
                }
                if(dataItem.status === "I"){
                    if(dataItem.cornrCd !== ""){
                        elements.cancel = true;
                    }
                }
            }
        });

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 변경시 체크박스 체크
                if (col.binding === "cornrCd" || col.binding === "vendorFg" ||
                    col.binding === "vendorNm" || col.binding === "vendorCd" || col.binding === "vendorTermnlNo" ||
                    col.binding === "vendorSerNo" || col.binding === "cornrNm" || col.binding === "ownerNm" || col.binding ==="bizNo" || col.binding === "baseYn" || col.binding === "telNo") {
                    $scope.checked(item);
                    if($("#lblVanFixFg").text() == "Y") {
                        if(col.binding === "vendorFg"){
                            if(item.vendorFg === "01") {
                                item.vendorNm = "KOCES";
                            }
                        }
                    }

                    if(col.binding === "cornrCd"){
                        if(item.cornrCd === "") {
                            item.vendorFg = "01";
                            item.vendorNm = "KCP";
                        }
                    }

                    if(item.cornrCd !== "") {
                        item.cornrNm = "";
                        item.ownerNm = "";
                        item.bizNo = "";
                        item.baseYn = "N";
                        item.telNo = "";
                    }
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // 구분 변경시 상세 dataMap 변경
    $scope.changeVendorFg = function (s, e) {
        if (e.panel === s.cells) {
            var col = s.columns[e.col];
            var item = s.rows[e.row].dataItem;
            if(col.binding === "vendorFg"){
                if (item.status === "I") {
                    item.vendorNm = '';
                }
            } else if (col.binding === "vendorNm") {
                var changeVendorFg = s.rows[e.row].dataItem.vendorFg;
                switch (changeVendorFg) {
                    case '01':
                        col.dataMap = vanList;
                        break;
                    case '02':
                        col.dataMap = paycoList;
                        break;
                    case '03':
                        col.dataMap = mpayList;
                        break;
                    case '04':
                        col.dataMap = mcoupnList;
                        break;
                    case '06':
                        col.dataMap = paperVoucherList;
                        break;
                    case '07':
                        col.dataMap = taxRefundList;
                        break;
                    case '08':
                        col.dataMap = sktList;
                        break;
                }
            }
        }
    };

    // 조회
    $scope.$on("multiBizManageCtrl", function (event, data) {

        // 매장 정보 셋팅
        var storeScope = agrid.getScope('storeManageCtrl');
        $("#lblStoreCd").text(storeScope.getSelectedStore().storeCd);
        $("#lblHqOfficeCd").text(storeScope.getSelectedStore().hqOfficeCd);

        var params = {};
        params.storeCd = $("#lblStoreCd").text();
        params.envstCd = "2028"; //코너사용설정[2028]

        $scope._postJSONQuery.withOutPopUp(baseUrl + "terminalManage/getTerminalEnv.sb", params, function (result) {

            var cornerList = result.data.data.cornerList;

            //
            cornerFgList = cornerList;
            cornerFgListDataMap = cornerList;

            // 다시 초기화해주고
            $scope.resetCombobox();

            // 코너선택 콤보박스 셋팅
            if(cornerList.length > 0) {
                var allData = {};
                allData.gChk = false;
                allData.name = "-전체-";
                allData.value = "";

                $scope.cornerFgArr.push(allData);
            }

            for (var j = 0; j < cornerList.length; j++) {
                $scope.cornerFgArr.push(cornerList[j]);
            }

            $scope.cornerCombo.itemsSource = new wijmo.collections.CollectionView($scope.cornerFgArr);

            // 코너 터미널 리스트 '코너' 컬럼 사용
            var allData = {};
            allData.name = "[XX] 코너추가";
            allData.value = "";
            cornerFgListDataMap.push(allData);

            // 코너 터미널 그리드에서 사용하는 코너콤보 선택값 재셋팅
            $scope.cornerFgDataMap = new wijmo.grid.DataMap(cornerFgListDataMap, 'value', 'name');

            // VAN사 변경 허용 체크
            $scope.chkVanFix(params);
        });

        event.preventDefault();
    });

    // VAN사 변경 허용 체크
    $scope.chkVanFix = function(data){
        var params = data;
        $scope._postJSONQuery.withOutPopUp( "/store/manage/terminalManage/terminalManage/chkVanFix.sb", params, function(response){
            var chkVanFix = response.data.data;
            if(chkVanFix === "Y" || chkVanFix === "N") {
                $("#lblVanFixFg").text(chkVanFix);
            }else{
                $("#lblVanFixFg").text("N");
            }
        });
    };

    // 코너 콤보박스 선택값 (코너 콤보박스 선택시, 해당 코너의 터미널 정보 조회)
    $scope.cornerFgVal = "01";
    $scope.setCornerFgVal = function (s, e) {

        $scope.cornerFgVal = s.selectedValue;

        if (!isNull($("#lblStoreCd").text())) {
            setTimeout(function () {
                $scope.getCornerSetting();
            }, 10);
        }
    };
    $scope.getCornerFgVal = function () {
        return $scope.cornerFgVal;
    };

    // 코너설정 데이터 조회
    $scope.getCornerSetting = function () {

        if (isNull($("#lblStoreCd").text())) {
            $scope._popMsg(messages["terminalManage.request.select.store"]);
            return false;
        }

        var params = {};
        params.storeCd = $("#lblStoreCd").text();
        params.cornrCd = $scope.getCornerFgVal();

        $scope._inquirySub(baseUrl + "corner/getCornerTerminalList.sb", params, function () {
            $scope.cornerFgDataMap = new wijmo.grid.DataMap(cornerFgListDataMap, 'value', 'name');

        }, false);
    };

    // 행 추가
    $scope.addRow = function () {

        var params = {};
        //var terminalScope = agrid.getScope('terminalCtrl');

        params.status = "I";
        params.gChk = true;
        if(isNull($scope.getCornerFgVal())) {
            if(cornerFgList !== null && cornerFgList !== '') {
                params.cornrCd = cornerFgList[0].value;
            }
        }else{
            params.cornrCd = $scope.getCornerFgVal();
        }
        params.vendorFg = "01";
        if($("#lblVanFixFg").text() == "Y") {
            params.vendorNm = "KOCES";
        }else{
            params.vendorNm = "KCP";
        }
        params.vendorCd = "";
        params.vendorTermnlNo = "";
        params.vendorSerNo = "";
        params.cornrNm = "";
        params.ownerNm = "";
        params.bizNo = "";
        params.baseYn = "N";
        params.telNo = "";
        params.cornrRnum = "";
        params.baseVanYn = "";

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };

    // 코너 정보 저장
    $scope.save = function () {
        $scope._popConfirm(messages["cmm.choo.save"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                if($scope.flex.collectionView.itemsEdited[i].gChk) {
                    $scope.flex.collectionView.itemsEdited[i].status = "U";
                    $scope.flex.collectionView.itemsEdited[i].storeCd = $("#lblStoreCd").text();
                    params.push($scope.flex.collectionView.itemsEdited[i]);
                }
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
                if($scope.flex.collectionView.itemsAdded[i].gChk) {
                    $scope.flex.collectionView.itemsAdded[i].status = "I";
                    $scope.flex.collectionView.itemsAdded[i].storeCd = $("#lblStoreCd").text();
                    params.push($scope.flex.collectionView.itemsAdded[i]);
                }
            }
            for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
                if($scope.flex.collectionView.itemsRemoved[i].gChk) {
                    if($scope.flex.collectionView.itemsRemoved[i].baseVanYn !== "Y") { // 대표VAN 터미널 삭제 금지
                        $scope.flex.collectionView.itemsRemoved[i].status = "D";
                        $scope.flex.collectionView.itemsRemoved[i].storeCd = $("#lblStoreCd").text();
                        params.push($scope.flex.collectionView.itemsRemoved[i]);
                    }
                }
            }

            // 필수값 체크
            for (var i = 0; i < params.length; i++) {

                if (params[i].status === "I" || params[i].status === "U") {

                    if (params[i].vendorFg == "") {
                        $scope._popMsg(messages["terminalManage.vendorFg"] + messages["terminalManage.require.select"]);
                        return false;
                    }

                    if (params[i].vendorNm == "" || params[i].vendorNm == undefined) {
                        $scope._popMsg(messages["terminalManage.vendorCd"] + messages["terminalManage.require.select"]);
                        return false;
                    }

                    if (params[i].vendorTermnlNo == "" || params[i].vendorTermnlNo == undefined) {
                        $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + messages["terminalManage.require.input"]);
                        return false;
                    } else {
                        if (params[i].vendorFg === "01" && params[i].vendorNm === "KCP") {
                            if (params[i].vendorTermnlNo.length != 10) {
                                $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + "는 10자리로 입력하세요.");
                                return false;
                            }
                        } else {
                            if (nvl(params[i].vendorTermnlNo.getByteLengthForOracle(),'') > 20) {
                                $scope._popMsg(messages["terminalManage.vendorTermnlNo"] + messages["terminalManage.require.exact.data"]);
                                return false;
                            }
                        }
                    }

                    if (params[i].vendorSerNo == "" || params[i].vendorSerNo == undefined) {
                        $scope._popMsg(messages["terminalManage.vendorSerNo"] + messages["terminalManage.require.input"]);
                        return false;
                    } else {
                        if (nvl(params[i].vendorSerNo.getByteLengthForOracle(),'') > 20) {
                            $scope._popMsg(messages["terminalManage.vendorSerNo"] + messages["terminalManage.require.exact.data"]);
                            return false;
                        }
                    }

                    // BBQ는 VAN - KCP 만 저장 가능
                    // 개발 DS011
                    // 운영 DS024 H0360
                    if ($("#lblHqOfficeCd").text() == "DS011" || $("#lblHqOfficeCd").text() == "DS024" || $("#lblHqOfficeCd").text() == "H0360") {
                        if (params[i].vendorFg == "01") {
                            if (params[i].vendorNm == "KCP") {
                            } else {
                                // BBQ 매장은 VAN - KCP 선택하여 주십시오.
                                $scope._popMsg(messages["terminalManage.bbqSave.msg"]);
                                return false;
                            }
                        }
                    }

                    // KOCES 총판은 벤더코드 KOCES만 저장가능
                    if($("#lblVanFixFg").text() == "Y") {
                        if (params[i].vendorFg === '01') {
                            if (params[i].vendorNm !== 'KOCES') {
                                $scope._popMsg(messages["terminalManage.vendorFg"]+"이 [01]VAN인 경우 " + messages["terminalManage.vendorCd"] + "는 [008] KOCES" + messages["terminalManage.require.select"]);
                                return false;
                            }
                        }
                    }

                }

                // 수정시
                if(params[i].status === "U"){

                    // 코너을(를) 선택해주세요.
                    if (params[i].cornrCd === "" || params[i].cornrCd === null || params[i].cornrCd === undefined) {
                        $scope._popMsg(messages["terminalManage.corner"] + messages["terminalManage.require.select"]);
                        return false;
                    }

                    // 코너정보를 변경할 수 있는 경우
                    if(params[i].cornrRnum === 1){

                        // 코너명을(를) 입력해주세요.
                        if (params[i].cornrNm == "") {
                            $scope._popMsg(messages["terminalManage.cornrNm"] + messages["terminalManage.require.input"]);
                            return false;
                        }

                        // 사업자번호을(를) 입력해주세요.
                        if (params[i].bizNo == "") {
                            $scope._popMsg(messages["terminalManage.bizNo"] + messages["terminalManage.require.input"]);
                            return false;
                        }

                        // 숫자만 입력
                        var numChkexp = /[^-\.0-9]/g;
                        if (numChkexp.test(nvl(params[i].bizNo, 0)) || String(params[i].bizNo).split('.').length - 1 > 1) {
                            // 사업자번호는 숫자만 입력해주세요.
                            $scope._popMsg(messages["terminalManage.bizNo"] + messages["cmm.require.number"]);
                            return false;
                        }

                        if (numChkexp.test(nvl(params[i].telNo, 0)) || String(params[i].telNo).split('.').length - 1 > 1) {
                            // 전화번호는 숫자만 입력해주세요.
                            $scope._popMsg(messages["terminalManage.telNo"] + messages["cmm.require.number"]);
                            return false;
                        }

                        // 대표코너 확인
                        // 미대표 --> 대표
                        if(params[i].baseYn === "Y"){
                            for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                                var item = $scope.flex.collectionView.items[j];
                                if(item.cornrRnum === 1) {
                                    if (params[i].cornrCd !== item.cornrCd) {
                                        if (item.baseYn === 'Y') {
                                            // 대표코너는 반드시 1개 존재해야 합니다.
                                            $scope._popMsg(messages["terminalManage.baseYn.chg.msg"]);
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        // 대표 --> 미대표
                        if(params[i].baseYn === "N"){
                            var chkBaseYnCnt = 0;
                            for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                                var item = $scope.flex.collectionView.items[j];
                                if(item.cornrRnum === 1) {
                                    if (params[i].cornrCd !== item.cornrCd) {
                                        if (item.baseYn === 'Y') {
                                            chkBaseYnCnt++;
                                        }
                                    }
                                }
                            }

                            if (chkBaseYnCnt === 0) {
                                // 대표코너는 반드시 1개 존재해야 합니다.
                                $scope._popMsg(messages["terminalManage.baseYn.chg.msg"]);
                                return false;
                            }
                        }
                    }
                }

                // 추가시
                if(params[i].status === "I"){

                    // 새 코너를 추가하는 경우
                    if (params[i].cornrCd === "" || params[i].cornrCd === null || params[i].cornrCd === undefined) {

                        // 코너추가시, 구분은 'VAN'만 선택할 수 있습니다.
                        if (params[i].vendorFg !== "01") {
                            $scope._popMsg(messages["terminalManage.cornerAdd.chk.msg"]);
                            return false;
                        }

                        // 코너명을(를) 입력해주세요.
                        if (params[i].cornrNm == "") {
                            $scope._popMsg(messages["terminalManage.cornrNm"] + messages["terminalManage.require.input"]);
                            return false;
                        }

                        // 사업자번호을(를) 입력해주세요.
                        if (params[i].bizNo == "") {
                            $scope._popMsg(messages["terminalManage.bizNo"] + messages["terminalManage.require.input"]);
                            return false;
                        }

                        // 숫자만 입력
                        var numChkexp = /[^-\.0-9]/g;
                        if (numChkexp.test(nvl(params[i].bizNo, 0)) || String(params[i].bizNo).split('.').length - 1 > 1) {
                            // 사업자번호는 숫자만 입력해주세요.
                            $scope._popMsg(messages["terminalManage.bizNo"] + messages["cmm.require.number"]);
                            return false;
                        }

                        if (numChkexp.test(nvl(params[i].telNo, 0)) || String(params[i].telNo).split('.').length - 1 > 1) {
                            // 전화번호는 숫자만 입력해주세요.
                            $scope._popMsg(messages["terminalManage.telNo"] + messages["cmm.require.number"]);
                            return false;
                        }

                        // 대표코너 확인
                        // 미대표 --> 대표
                        if(params[i].baseYn === "Y"){
                            for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                                var item = $scope.flex.collectionView.items[j];
                                if(item.cornrRnum === 1) {
                                    if (params[i].cornrCd !== item.cornrCd) {
                                        if (item.baseYn === 'Y') {
                                            // 대표코너는 반드시 1개 존재해야 합니다.
                                            $scope._popMsg(messages["terminalManage.baseYn.chg.msg"]);
                                            return false;
                                        }
                                    }
                                }
                            }
                        }

                        // 대표 --> 미대표
                        if(params[i].baseYn === "N"){
                            var chkBaseYnCnt = 0;
                            for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                                var item = $scope.flex.collectionView.items[j];
                                if(item.cornrRnum === 1) {
                                    if (params[i].cornrCd !== item.cornrCd) {
                                        if (item.baseYn === 'Y') {
                                            chkBaseYnCnt++;
                                        }
                                    }
                                }
                            }

                            if (chkBaseYnCnt === 0) {
                                // 대표코너는 반드시 1개 존재해야 합니다.
                                $scope._popMsg(messages["terminalManage.baseYn.chg.msg"]);
                                return false;
                            }
                        }
                    }
                }
            }

            // 코너-구분-상세 중복체크
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                    var item2 = $scope.flex.collectionView.items[j];
                    if(i !== j){
                        if(item.cornrCd !== "" && item2.cornrCd !== "") {
                            if (item.vendorNm.toString !== null && item.vendorNm.toString !== '' && item.vendorNm.toString !== undefined
                                && item2.vendorNm.toString !== null && item2.vendorNm.toString !== '' && item2.vendorNm.toString !== undefined) {
                                if (item.cornrCd.toString() + item.vendorFg.toString() + item.vendorNm.toString() ===
                                    item2.cornrCd.toString() + item2.vendorFg.toString() + item2.vendorNm.toString()) {
                                    $scope._popMsg("코너-구분-상세 : [" + item.cornrCd.toString() + "]-" + getVendorFgNm(item.vendorFg.toString()) + "-" + item.vendorNm.toString() + messages["terminalManage.input.duplicate"]);
                                    return false;
                                }
                            }
                        }
                    }
                }
            }

            // 리스트 변경사항이 있는지 확인
            if (params.length <= 0) {
                $scope._popMsg(messages["cmm.not.modify"]);
                return false;
            } else {
                params = JSON.stringify(params);
                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._save(baseUrl + "corner/saveCornerTerminalInfo.sb", params, function () {
                    // 재조회
                    $scope._broadcast('multiBizManageCtrl');
                });
            }
        });
    };

    // 코너 정보 삭제
    $scope.del = function () {

        var chkCnt = 0;

        // 대표밴 'Y' 인 코너 터미널 체크
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];

            if (item.gChk) {
                if (item.baseVanYn === "Y") {
                    chkCnt++;
                }
            }
        }

        if(chkCnt > 0){
            // 대표VAN 터미널 정보는 제외하고 삭제됩니다. 삭제하시겠습니까?
            $scope._popConfirm(messages["terminalManage.baseVanYn.delete.chk.msg"], function() {

                // 그리드에서 제거
                for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                    var item = $scope.flex.collectionView.items[i];

                    if (item.gChk) {
                        if (item.baseVanYn !== "Y") { // 대표VAN 터미널 삭제 금지
                            $scope.flex.collectionView.removeAt(i);
                        }
                    }
                }

                // 코너 정보 저장
                $scope.save();
            });
        }else{

            // 그리드에서 제거
            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                var item = $scope.flex.collectionView.items[i];

                if (item.gChk) {
                    if (item.baseVanYn !== "Y") { // 대표VAN 터미널 삭제 금지
                        $scope.flex.collectionView.removeAt(i);
                    }
                }
            }

            // 코너 정보 저장
            $scope.save();
        }
    };

    // 정보 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    };
    
    // 콤보박스 데이터 초기화
    $scope.resetCombobox = function () {
        $scope.cornerFgArr = [];
    };

}]);

// 구분 코드값으로 명칭 가져오기
function getVendorFgNm (cd){
    var list = vendorFg;
    for (var i = 0; i < list.length; i++) {
        if(list[i].value === cd){
            return list[i].name;
        }
    }
}