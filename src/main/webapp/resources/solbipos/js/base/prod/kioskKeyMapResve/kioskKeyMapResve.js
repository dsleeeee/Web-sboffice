/****************************************************************
 *
 * 파일명 : kioskKeyMapResve.js
 * 설  명 : 키오스크키맵설정(예약) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.03.27     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

var envstData = [
    {"name":"전체","value":""},
    {"name":"매장","value":"4068"},
    {"name":"포장","value":"4069"}
];

// 예약키맵그룹
var selectComboLData = [
    {"name":"선택","value":""}
];

/**
 * 키오스크키맵설정(예약) 그리드 생성
 */
app.controller('kioskKeyMapResveCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyMapResveCtrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchTimeStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchTimeEndDate", gvEndDate);

    // 전체기간 체크박스
    $scope.isChecked = true;

    // 콤보박스 셋팅
    $scope._setComboData("envstCdCombo", envstData);
    if(orgnFg == "HQ") {
        $scope._setComboData("tuClsTypeCombo", tuClsTypeDataAll); // 예약키맵그룹
    } else {
        if(kioskPosList.length < 1) {
            $scope._setComboData("posNoCombo", selectComboLData); // POS번호
        } else {
            $scope._setComboData("posNoCombo", kioskPosList); // POS번호
        }
        $scope._setComboData("tuClsTypeCombo", selectComboLData); // 예약키맵그룹
    }
    $scope._setComboData("tuClsTypeCombo2", tuClsTypeData); // 일괄변경 - 예약키맵그룹
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹

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

        $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');
        $scope.tuClsTypeDataMap = new wijmo.grid.DataMap(tuClsTypeData, 'value', 'name');
        $scope.envstDataMap = new wijmo.grid.DataMap(envstData, 'value', 'name');

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "startDate" || col.binding === "tuClsType") {
                    $scope.checked(item);
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

    };

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    // 조회
    $scope.$on("kioskKeyMapResveCtrl", function(event, data) {
        if(orgnFg === "STORE") {
            if(kioskPosList.length < 1) {
                $scope._popMsg(messages["kioskKeyMapResve.kioskPosCntAlert"]); // 키오스크용으로 등록된 POS가 없습니다.
                return;
            }
        }

        $scope.searchKioskKeyMapResveList();
        event.preventDefault();
    });

    // 그리드 조회
    $scope.searchKioskKeyMapResveList = function(){
        var params = {};

        // 조회일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }
        params.envstCd   = $scope.envstCd;
        params.storeCd   = $scope.storeCd;
        params.storeNm   = $scope.storeNm;
        params.tuClsType = $scope.tuClsType;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        if(orgnFg === "STORE") {params.posNo = $scope.posNoCombo.selectedValue;}
        params.listScale = 500;

        console.log(params);

        $scope._inquirySub('/base/prod/kioskKeyMapResve/kioskKeyMapResve/getKioskKeyMapResveList.sb', params, function() {

            // 그리드 선택불가 항목처리
            $scope.setGridReadOnly();

        }, false);
    };

    // 일괄변경 테이블 숨김/보임
    $scope.changeShow = function(){
        if( $("#tblHqChange").css("display") === 'none'){
            $("#tblHqChange").show();
        } else {
            $("#tblHqChange").hide();
        }
    };

    $scope.change = function() {

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
                item.tuClsType = $scope.tuClsTypeChange;
            }
        }

        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();

        // 그리드 선택불가 항목처리
        $scope.setGridReadOnly();
    };

    // 수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    }

    // 삭제
    $scope.del = function(){
        $scope._popConfirm(messages["kioskKeyMapResve.delConfirm"], function() {
            // 파라미터 설정
            var params = new Array();

            for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
                if ($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].orgStartDate = $scope.flex.collectionView.items[i].orgStartDate.replaceAll('-', ''); // 키값
                    $scope.flex.collectionView.items[i].startDate = $scope.flex.collectionView.items[i].startDate.replaceAll('-', ''); // 키값
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/kioskKeyMapResve/kioskKeyMapResve/deleteKioskKeyMapResve.sb', params, function () {
                $scope.searchKioskKeyMapResveList();
            });
        });

    }

    // 수정
    $scope.save = function(){
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {

                if(Number(now) >= Number($scope.flex.collectionView.items[i].startDate.replaceAll('-', ''))) {
                    $scope._popMsg(messages["kioskKeyMapResve.startDate"] + "는 " + messages["kioskKeyMapResve.resveDate.chk.msg"]);
                    return false;
                }
            }
        }

        // 파라미터 설정
        var params = new Array();

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if (($scope.flex.collectionView.items[i].orgStartDate !== $scope.flex.collectionView.items[i].startDate) ||
                    ($scope.flex.collectionView.items[i].orgTouchKeyGrp !== $scope.flex.collectionView.items[i].tuClsType)) {

                    $scope.flex.collectionView.items[i].orgStartDate = $scope.flex.collectionView.items[i].orgStartDate.replaceAll('-', ''); // 키값
                    $scope.flex.collectionView.items[i].startDate = $scope.flex.collectionView.items[i].startDate.replaceAll('-', '');
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/kioskKeyMapResve/kioskKeyMapResve/modKioskKeyMapResve.sb', params, function(){
            $scope.searchKioskKeyMapResveList();
        });

    };

    // 추가 팝업
    $scope.add = function (envstCd) {
        $scope.kioskKeyMapResveAddLayer.show(true);
        $scope._broadcast('kioskKeyMapResveAddCtrl',envstCd);
    };

    // 그리드 선택불가 항목처리
    $scope.setGridReadOnly = function () {
        var grid = wijmo.Control.getControl("#wjGridKioskKeyMapResve");
        var rows = grid.rows;

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];

            // 시작일자가 오늘날짜보다 작거나 같으면 수정불가
            if(Number(now) >= Number(item.orgStartDate.replaceAll('-', ''))) {
                item.gChk = false;
                rows[i].isReadOnly = true;
            }

            // 행간격 고정
            rows[i].height = 25
        }
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 매장권한) POS번호 선택 시, 키맵그룹 dropdown 조회
    $scope.setTuClsType = function (s) {
        // 키맵그룹 dropdown 재조회
        $scope.setTuClsDropdownList();

        // 그리드에 콤보박스 데이터를 변경함으로 재조회
        $scope.searchKioskKeyMapResveList();
    };

    // 키맵그룹 dropdownLIst 재조회
    $scope.setTuClsDropdownList = function (){
        // 키맵그룹 dropdown 재조회
        var url = '/base/prod/kioskKeyMap/kioskKeyMap/getKioskTuClsTypeList.sb';
        var params = {};
        if(orgnFg === "STORE") {params.posNo = $scope.posNoCombo.selectedValue;}

        //가상로그인 session 설정
        if(document.getElementsByName('sessionId')[0]){
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : url, /* 통신할 URL */
            params : params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (!$.isEmptyObject(response.data.data.list)) {
                    var list = response.data.data.list;
                    var comboArray = [];
                    var comboData  = {};

                    for (var i = 0; i < list.length; i++) {
                        comboData = {};
                        comboData.name  = list[i].name;
                        comboData.value = list[i].value;
                        comboArray.push(comboData);
                    }

                    $scope._setComboData("tuClsTypeCombo", [{name: "선택", value: ""}].concat(comboArray));
                    $scope.tuClsTypeDataMap = new wijmo.grid.DataMap(comboArray, 'value', 'name');

                } else {
                    var comboArrayAll = [];
                    comboArrayAll.unshift({name: "선택", value: ""});
                    $scope._setComboData("tuClsTypeCombo", comboArrayAll);

                    $scope.tuClsTypeDataMap = new wijmo.grid.DataMap(comboArrayAll, 'value', 'name');
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        });
    };

}]);
