/****************************************************************
 *
 * 파일명 : soldOutResveAdd.js
 * 설  명 : 품절관리(예약) 추가 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.05.31     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  예약추가 팝업
 */
app.controller('soldOutResveAddCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('soldOutResveAddCtrl', $scope, $http, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("soldOutYnAddCombo", soldOutYnAllData);
    $scope._setComboData("soldOutYnAddCombo2", soldOutYnData);
    $scope._setComboData("startTimeCombo", startTimeData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.soldOutYnDataMap = new wijmo.grid.DataMap(soldOutYnData, 'value', 'name');
        $scope.allSoldOutYnDataMap = new wijmo.grid.DataMap(allSoldOutYnComboData, 'value', 'name');

        // 예약일시 날짜셋팅
        // $scope.startDateCombo.value = getTomorrow('-');

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "soldOutYn" || col.binding === "allSoldOutYn") {
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
        var dataItem            = {};
        dataItem.gChk           = messages["cmm.chk"];
        dataItem.branchCd       = messages["soldOutResve.branchCd"];
        dataItem.branchNm       = messages["soldOutResve.branchNm"];
        dataItem.storeCd        = messages["soldOutResve.storeCd"];
        dataItem.storeNm        = messages["soldOutResve.storeNm"];
        dataItem.prodCd         = messages["soldOutResve.prodCd"];
        dataItem.prodNm         = messages["soldOutResve.prodNm"];
        dataItem.brand          = messages["soldOutResve.brand"];
        dataItem.startDate      = messages["soldOutResve.startDate"];
        dataItem.orgSoldOutYn   = messages["soldOutResve.soldOutYn"];
        dataItem.soldOutYn      = messages["soldOutResve.soldOutYn"];
        dataItem.allSoldOutYn   = messages["soldOutResve.soldOutYn"];

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
    $scope.$on("soldOutResveAddCtrl", function(event, data) {
        event.preventDefault();
    });

    // 리스트 조회
    $scope.searchSoldOutResveAddList = function () {

        if(orgnFg == "HQ"){
            if(($("#soldOutResveAddStoreCd").val() === "" || $("#soldOutResveAddStoreCd").val() === undefined) && ($("#soldOutResveAddProdCd").val() === "" || $("#soldOutResveAddProdCd").val() === undefined)){
                $scope._popMsg(messages["soldOutResve.require.select.msg"]);
                return false;
            }
        }

        var params = {};
        params.storeCds = $("#soldOutResveAddStoreCd").val();
        params.prodCds = $("#soldOutResveAddProdCd").val();
        params.soldOutResveYn = $scope.soldOutResveYn;
        params.useYn = $scope.useYn;
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
            params.momsStoreFg01 = $scope.momsStoreFg01;
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

        $scope._inquirySub('/base/prod/soldOutResve/soldOutResve/getProdList.sb', params, function() {}, false);
    };

    // 체크박스 체크
    $scope.checked = function (item){
        if((item.soldOutYn != undefined && item.soldOutYn != null && item.soldOutYn != "") || (item.allSoldOutYn != undefined && item.allSoldOutYn != null && item.allSoldOutYn != "")){
            item.gChk = true;
        }
    }

    // 저장
    $scope.save = function(gubun){

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
            $scope._popMsg(messages["soldOutResve.startDate"] + "는 " + messages["soldOutResve.resveDate.chk.msg"]);
            return false;
        } else if(Number(now) === Number(vStartDate) && Number(time) >= $scope.startTime){
            $scope._popMsg(messages["soldOutResve.startTime"] + "는 " + messages["soldOutResve.resveTime.chk.msg"]);
            return false;
        }

        // 파라미터 설정
        var params = new Array();

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if($scope.flex.collectionView.items[i].soldOutYn != undefined && $scope.flex.collectionView.items[i].soldOutYn != null && $scope.flex.collectionView.items[i].soldOutYn != ""){

                    $scope.flex.collectionView.items[i].startDate = wijmo.Globalize.format($scope.startDateCombo.value, 'yyyyMMdd');
                    $scope.flex.collectionView.items[i].startTime = $scope.startTimeCombo.text;
                    $scope.flex.collectionView.items[i].allSoldOutYn = gubun;
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/soldOutResve/soldOutResve/saveSoldOutResve.sb', params, function(){

            // 부모창 재조회
            var vScope = agrid.getScope("soldOutResveCtrl");
            vScope.searchSoldOutResveList();

            // 팝업 닫기
            $scope.soldOutResveAddLayer.hide(true);

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
                item.soldOutYn = $scope.soldOutYnAddChange;
            }
        }
        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();
    };
    
    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.soldOutResveAddStoreShow = function () {
        $scope._broadcast('soldOutResveAddStoreCtrl');
    };

    // 상품선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.soldOutResveAddProdShow = function () {
        $scope._broadcast('soldOutResveAddProdCtrl');
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
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };
}]);