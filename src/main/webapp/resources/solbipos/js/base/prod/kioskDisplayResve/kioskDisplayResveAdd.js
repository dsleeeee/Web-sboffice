/****************************************************************
 *
 * 파일명 : kioskDisplayResveAdd.js
 * 설  명 : 비노출관리(예약) 추가 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.05.25     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
var kioskDisplayYnAllComboData = [
    {"name": "전체", "value": ""},
    {"name": "노출", "value": "Y"},
    {"name": "비노출", "value": "N"}
];
var kioskDisplayYnComboData = [
    {"name": "노출", "value": "Y"},
    {"name": "비노출", "value": "N"}
];


var startTimeData = [
    {"name":"00", "value":"0"},
    {"name":"01", "value":"1"},
    {"name":"02", "value":"2"},
    {"name":"03", "value":"3"},
    {"name":"04", "value":"4"},
    {"name":"05", "value":"5"},
    {"name":"06", "value":"6"},
    {"name":"07", "value":"7"},
    {"name":"08", "value":"8"},
    {"name":"09", "value":"9"},
    {"name":"10", "value":"10"},
    {"name":"11", "value":"11"},
    {"name":"12", "value":"12"},
    {"name":"13", "value":"13"},
    {"name":"14", "value":"14"},
    {"name":"15", "value":"15"},
    {"name":"16", "value":"16"},
    {"name":"17", "value":"17"},
    {"name":"18", "value":"18"},
    {"name":"19", "value":"19"},
    {"name":"20", "value":"20"},
    {"name":"21", "value":"21"},
    {"name":"22", "value":"22"},
    {"name":"23", "value":"23"}
];

/**
 *  예약추가 팝업
 */
app.controller('kioskDisplayResveAddCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskDisplayResveAddCtrl', $scope, $http, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("kioskDisplayYnAddCombo", kioskDisplayYnAllComboData);
    $scope._setComboData("kioskDisplayYnAddCombo2", kioskDisplayYnComboData);
    $scope._setComboData("startTimeCombo", startTimeData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.kioskDisplayYnComboDataMap = new wijmo.grid.DataMap(kioskDisplayYnComboData, 'value', 'name');

        // 예약일시 날짜셋팅
        // $scope.startDateCombo.value = getTomorrow('-');

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "kioskDisplayYn") {
                    $scope.checked(item);
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
        var dataItem                = {};
        dataItem.gChk               = messages["cmm.chk"];
        dataItem.branchCd           = messages["kioskDisplayResve.branchCd"];
        dataItem.branchNm           = messages["kioskDisplayResve.branchNm"];
        dataItem.storeCd            = messages["kioskDisplayResve.storeCd"];
        dataItem.storeNm            = messages["kioskDisplayResve.storeNm"];
        dataItem.prodCd             = messages["kioskDisplayResve.prodCd"];
        dataItem.prodNm             = messages["kioskDisplayResve.prodNm"];
        dataItem.brand              = messages["kioskDisplayResve.brand"];
        dataItem.startDate          = messages["kioskDisplayResve.startDate"];
        dataItem.orgKioskDisplayYn  = messages["kioskDisplayResve.kioskDisplayYn"];
        dataItem.kioskDisplayYn     = messages["kioskDisplayResve.kioskDisplayYn"];

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

    // 조회
    $scope.$on("kioskDisplayResveAddCtrl", function(event, data) {
        event.preventDefault();
    });

    // 리스트 조회
    $scope.searchKioskDisplayResveAddList = function () {

        if(orgnFg == "HQ"){
            if(($("#kioskDisplayResveAddStoreCd").val() === "" || $("#kioskDisplayResveAddStoreCd").val() === undefined) && ($("#kioskDisplayResveAddProdCd").val() === "" || $("#kioskDisplayResveAddProdCd").val() === undefined)){
                $scope._popMsg(messages["kioskDisplayResve.require.select.msg"]);
                return false;
            }
        }

        var params = {};
        params.storeCds = $("#kioskDisplayResveAddStoreCd").val();
        params.prodCds = $("#kioskDisplayResveAddProdCd").val();
        params.kioskDisplayResveYn = $scope.kioskDisplayResveYn;
        params.useYn = $scope.useYn;
        params.kioskUseYn = $scope.kioskUseYn;
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;

        if(momsEnvstVal === "1" && orgnFg === "HQ"){ // 확장조회는 본사권한이면서 맘스터치만 사용
            params.momsTeam = $scope.momsTeam;
            params.momsAcShop = $scope.momsAcShop;
            params.momsAreaFg = $scope.momsAreaFg;
            params.momsCommercial = $scope.momsCommercial;
            params.momsShopType = $scope.momsShopType;
            params.momsStoreManageType = $scope.momsStoreManageType;
            params.branchCd = $scope.branchCd;
        }

        if(brandUseFg === "1" && orgnFg === "HQ"){ // 본사이면서 브랜드사용시만 검색가능

            params.storeHqBrandCd = $scope.srchStoreHqBrandCdCombo.selectedValue;
            params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

            // '전체' 일때
            if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null || params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var momsHqBrandCd = "";
                for (var i = 0; i < momsHqBrandCdComboList.length; i++) {
                    if (momsHqBrandCdComboList[i].value !== null) {
                        momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = momsHqBrandCd;
            }
        }

        $scope._inquirySub('/base/prod/kioskDisplayResve/kioskDisplayResve/getProdList.sb', params, function() {}, false);
    };

    // 체크박스 체크
    $scope.checked = function (item){
        if(item.kioskDisplayYn != undefined && item.kioskDisplayYn != null && item.kioskDisplayYn != ""){
            item.gChk = true;
        }
    }

    // 저장
    $scope.saveProdPrice2 = function(){

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        // 예약날짜 체크
        var date = new Date();
        var year = new String(date.getFullYear());
        var month = new String(date.getMonth()+1);
        var day = new String(date.getDate());
        var time = new String(date.getHours());

        // 한자리수일 경우 0을 채워준다.
        if(month.length == 1){
            month = "0" + month;
        }
        if(day.length == 1){
            day = "0" + day;
        }
        var now = year + "" + month + "" + day;
        var vStartDate = wijmo.Globalize.format($scope.startDateCombo.value, 'yyyyMMdd');

        if(Number(now) > Number(vStartDate)) {
            $scope._popMsg(messages["kioskDisplayResve.startDate"] + "는 " + messages["kioskDisplayResve.resveDate.chk.msg"]);
            return false;
        } else if(Number(now) === Number(vStartDate) && Number(time) >= $scope.startTime){
            $scope._popMsg(messages["kioskDisplayResve.startTime"] + "는 " + messages["kioskDisplayResve.resveTime.chk.msg"]);
            return false;
        }

        // 파라미터 설정
        var params = new Array();

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if($scope.flex.collectionView.items[i].kioskDisplayYn != undefined && $scope.flex.collectionView.items[i].kioskDisplayYn != null && $scope.flex.collectionView.items[i].kioskDisplayYn != ""){

                    $scope.flex.collectionView.items[i].startDate = wijmo.Globalize.format($scope.startDateCombo.value, 'yyyyMMdd');
                    $scope.flex.collectionView.items[i].startTime = $scope.startTime;
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/kioskDisplayResve/kioskDisplayResve/saveKioskDisplayResve.sb', params, function(){

            // 부모창 재조회
            var vScope = agrid.getScope("kioskDisplayResveCtrl");
            vScope.searchKioskDisplayResveList();

            // 팝업 닫기
            $scope.kioskDisplayResveAddLayer.hide(true);

        });

    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChangeEnv = function(){
        if( $("#tblSearchAddShowAdd").css("display") === 'none') {
            $("#tblSearchAddShowAdd").show();
        } else {
            $("#tblSearchAddShowAdd").hide();
        }
    };

    // 일괄변경 테이블 숨김/보임
    $scope.changeAddShow = function(){
        if( $("#tblAddChange").css("display") === 'none'){
            $("#tblAddChange").show();
        } else {
            $("#tblAddChange").hide();
        }
    };

    // 일괄변경
    $scope.changeAdd = function() {
        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk){
                item.kioskDisplayYn = $scope.kioskDisplayYnAddChange;
            }
        }
        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();
    };
    
    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.kioskDisplayResveAddStoreShow = function () {
        $scope._broadcast('kioskDisplayResveAddStoreCtrl');
    };

    // 상품선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.kioskDisplayResveAddProdShow = function () {
        $scope._broadcast('kioskDisplayResveAddProdCtrl');
    };


    // 상품분류정보 팝업
    $scope.popUpProdClass2 = function() {
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
    $scope.delProdClass2 = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };
}]);