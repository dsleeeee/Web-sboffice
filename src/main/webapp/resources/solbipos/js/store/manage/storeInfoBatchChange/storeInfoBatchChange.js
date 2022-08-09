/****************************************************************
 *
 * 파일명 : storeInfoBatchChange.js
 * 설  명 : 매장정보일괄변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.08.03     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 직영구분
var directManageYnData = [
    {"name":"직영","value":"Y"},
    {"name":"가맹","value":"N"}
];

/**
 *  매장정보일괄변경 그리드 생성
 */
app.controller('storeInfoBatchChangeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeInfoBatchChangeCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("sysStatFgCombo", sysStatFgComboData); // 매장상태구분
    $scope._setComboData("areaCdChgCombo", areaCdComboData); // 날씨표시지역
    $scope._setComboData("directManageYnChgCombo", directManageYnData); // 직영구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 대리점코드 조회
        $scope.agencyCdComboList();
        // 관리벤사 조회
        $scope.vanCdComboList();

        // 그리드 DataMap 설정
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFgComboData, 'value', 'name'); // 매장상태구분
        $scope.agencyCdDataMap = new wijmo.grid.DataMap(agencyCdComboArray, 'value', 'name'); // 대리점코드
        $scope.clsFgDataMap = new wijmo.grid.DataMap(clsFgComboData, 'value', 'name'); // 용도구분
        $scope.areaCdDataMap = new wijmo.grid.DataMap(areaCdComboData, 'value', 'name'); // 날씨표시지역
        $scope.directManageYnDataMap = new wijmo.grid.DataMap(directManageYnData, 'value', 'name'); // 직영구분
        $scope.vanCdDataMap = new wijmo.grid.DataMap(vanCdComboArray, 'value', 'name'); // 관리벤사
    };

    // 대리점코드 조회
    var agencyCdComboArray = [];
    $scope.agencyCdComboList = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp('/store/manage/storeInfoBatchChange/storeInfoBatchChange/getAgencyCdComboList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var agencyCdList = response.data.data.list;

                var comboData = {};
                for (var i = 0; i < agencyCdList.length; i++) {
                    comboData = {};
                    comboData.value = agencyCdList[i].value;
                    comboData.name = agencyCdList[i].name;
                    agencyCdComboArray.push(comboData);
                }
            } else {
                var comboData = {};
                comboData.value = "";
                comboData.name = "선택";
                agencyCdComboArray.push(comboData);
            }
        });
    };

    // 관리벤사 조회
    var vanCdComboArray = [];
    $scope.vanCdComboList = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp('/store/manage/storeInfoBatchChange/storeInfoBatchChange/getVanCdComboList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var vanCdList = response.data.data.list;

                var comboData = {};
                for (var i = 0; i < vanCdList.length; i++) {
                    comboData = {};
                    comboData.value = vanCdList[i].value;
                    comboData.name = vanCdList[i].name;
                    vanCdComboArray.push(comboData);
                }
            } else {
                var comboData = {};
                comboData.value = "";
                comboData.name = "선택";
                vanCdComboArray.push(comboData);
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("storeInfoBatchChangeCtrl", function(event, data) {
        $scope.searchStoreInfoBatchChange();
        event.preventDefault();
    });

    $scope.searchStoreInfoBatchChange = function(){
        var params = {};

        $scope._inquiryMain("/store/manage/storeInfoBatchChange/storeInfoBatchChange/getStoreInfoBatchChangeList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 대리점 조회
    $scope.searchAgency = function(){
        if(orgnFg === "MASTER" || pAgencyCd === "00000" || orgnFg === "HQ") {
            var popup = $scope.agencyLayer;

            // 팝업 닫을때
            popup.show(true, function (s) {
                var agencyScope = agrid.getScope('searchAgencyCtrl');
                agencyScope.$apply(function () {
                    agencyScope._gridDataInit();

                    if (!$.isEmptyObject(agencyScope.getAgency())) {
                        $scope.agencyCdChg = agencyScope.getAgency().agencyCd;
                        $scope.agencyNmChg = agencyScope.getAgency().agencyNm;
                    }
                });
            });
        }
    };

    // 일괄적용
    $scope.batchChange = function(chgGubun) {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
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
                // 매장명
                if(chgGubun == "storeNmChg") {
                    $scope.flex.collectionView.items[i].storeNm = $scope.storeNmChg;
                }
                // 대리점
                else if(chgGubun == "agencyCdChg") {
                    $scope.flex.collectionView.items[i].agencyCd = $scope.agencyCdChg;
                }
                // 상호명
                else if(chgGubun == "bizStoreNmChg") {
                    $scope.flex.collectionView.items[i].bizStoreNm = $scope.bizStoreNmChg;
                }
                // 대표자명
                else if(chgGubun == "ownerNmChg") {
                    $scope.flex.collectionView.items[i].ownerNm = $scope.ownerNmChg;
                }
                // 날씨표시지역
                else if(chgGubun == "areaCdChg") {
                    $scope.flex.collectionView.items[i].areaCd = $scope.areaCdChg;
                }
                // 직영구분
                else if(chgGubun == "directManageYnChg") {
                    $scope.flex.collectionView.items[i].directManageYn = $scope.directManageYnChg;
                }
                // 전화번호
                else if(chgGubun == "telNoChg") {
                    $scope.flex.collectionView.items[i].telNo = $scope.telNoChg;
                }
                // 팩스번호
                else if(chgGubun == "faxNoChg") {
                    $scope.flex.collectionView.items[i].faxNo = $scope.faxNoChg;
                }
                // 시스템비고
                else if(chgGubun == "sysRemarkChg") {
                    $scope.flex.collectionView.items[i].sysRemark = $scope.sysRemarkChg;
                }
                // 본사비고
                else if(chgGubun == "hdRemarkChg") {
                    $scope.flex.collectionView.items[i].hdRemark = $scope.hdRemarkChg;
                }
                // 특이사항
                else if(chgGubun == "remarkChg") {
                    $scope.flex.collectionView.items[i].remark = $scope.remarkChg;
                }
                // 매핑매장코드
                else if(chgGubun == "mapStoreCdChg") {
                    $scope.flex.collectionView.items[i].mapStoreCd = $scope.mapStoreCdChg;
                }
            }
        }
        $scope.flex.refresh();
    };

    // <-- 그리드 저장 -->
    $scope.save = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        // 값 체크
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            // 매장명
            if($scope.flex.collectionView.itemsEdited[i].storeNm === "" || $scope.flex.collectionView.itemsEdited[i].storeNm === null) {
                $scope._popMsg(messages["storeInfoBatchChange.storeNm"]+messages["cmm.require.text"]); // 매장명을 입력해주세요.
                return false;
            }

            // 대리점
            if($scope.flex.collectionView.itemsEdited[i].agencyCd === "" || $scope.flex.collectionView.itemsEdited[i].agencyCd === null) {
                $scope._popMsg(messages["storeInfoBatchChange.agency"]+messages["cmm.require.select"]); // 대리점를 선택해주세요.
                return false;
            }

            // 상호명
            if($scope.flex.collectionView.itemsEdited[i].bizStoreNm === "" || $scope.flex.collectionView.itemsEdited[i].bizStoreNm === null) {
                $scope._popMsg(messages["storeInfoBatchChange.bizStoreNm"]+messages["cmm.require.text"]); // 상호명을 입력해주세요.
                return false;
            }

            // 대표자명
            if($scope.flex.collectionView.itemsEdited[i].ownerNm === "" || $scope.flex.collectionView.itemsEdited[i].ownerNm === null) {
                $scope._popMsg(messages["storeInfoBatchChange.ownerNm"]+messages["cmm.require.text"]); // 대표자명을 입력해주세요.
                return false;
            }

            // 날씨표시지역
            if($scope.flex.collectionView.itemsEdited[i].areaCd === "" || $scope.flex.collectionView.itemsEdited[i].areaCd === null) {
                $scope._popMsg(messages["storeInfoBatchChange.areaCd"]+messages["cmm.require.select"]); // 날씨표시지역을 선택해주세요.
                return false;
            }

            // 직영구분
            if($scope.flex.collectionView.itemsEdited[i].directManageYn === "" || $scope.flex.collectionView.itemsEdited[i].directManageYn === null) {
                $scope._popMsg(messages["storeInfoBatchChange.directManageYn"]+messages["cmm.require.select"]); // 직영구분을 선택해주세요.
                return false;
            }

            // 전화번호
            if($scope.flex.collectionView.itemsEdited[i].telNo === "" || $scope.flex.collectionView.itemsEdited[i].telNo === null) {
                $scope._popMsg(messages["storeInfoBatchChange.telNo"]+messages["cmm.require.text"]);  // 전화번호를 입력해주세요.
                return false;
            } else {
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.flex.collectionView.itemsEdited[i].telNo)) {
                    $scope._popMsg(messages["storeInfoBatchChange.telNo"]+messages["cmm.require.number"]); // 전화번호는 숫자만 입력할 수 있습니다.
                    return false;
                }
            }

            // 팩스번호
            if($scope.flex.collectionView.itemsEdited[i].faxNo === "" || $scope.flex.collectionView.itemsEdited[i].faxNo === null) {
            } else {
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.flex.collectionView.itemsEdited[i].faxNo)) {
                    $scope._popMsg(messages["storeInfoBatchChange.faxNo"]+messages["cmm.require.number"]); // 팩스번호는 숫자만 입력할 수 있습니다.
                    return false;
                }
            }
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/store/manage/storeInfoBatchChange/storeInfoBatchChange/getStoreInfoBatchChangeSave.sb", params, function(){ $scope.allSearch() });
        });
    };

    // 재조회
    $scope.allSearch = function () {
        $scope.searchStoreInfoBatchChange();
    };
    // <-- //그리드 저장 -->

}]);