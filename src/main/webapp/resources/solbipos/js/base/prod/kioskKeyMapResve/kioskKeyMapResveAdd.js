/****************************************************************
 *
 * 파일명 : kioskKeyMapResveAdd.js
 * 설  명 : 판매터치키설정(예약) 추가 팝업 JavaScript
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

/**
 *  상품삭제 팝업생성
 */
app.controller('kioskKeyMapResveAddCtrl', ['$scope', '$http', function ($scope, $http) {

    var envstCd;

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyMapResveAddCtrl', $scope, $http, true));

    // 콤보박스 데이터 Set
    if(orgnFg == "HQ") {
        $scope._setComboData("tuClsTypeAddCombo", tuClsTypeDataAll); // 예약키맵그룹
    } else {
        $scope._setComboData("posNoAddCombo", kioskPosList); // POS번호
        $scope._setComboData("tuClsTypeAddCombo", selectComboLData); // 예약키맵그룹
    }
    $scope._setComboData("tuClsTypeAddCombo2", tuClsTypeData); // 일괄변경 - 예약키맵그룹

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');
        $scope.tuClsTypeDataMap = new wijmo.grid.DataMap(tuClsTypeData, 'value', 'name');
        $scope.envstDataMap = new wijmo.grid.DataMap(envstData, 'value', 'name');

        // 예약일시 날짜셋팅
        $scope.startDateCombo.value = getTomorrow('-');

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "tuClsType") {
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
        dataItem.branchCd       = messages["kioskKeyMapResve.branchCd"];
        dataItem.branchNm       = messages["kioskKeyMapResve.branchNm"];
        dataItem.storeCd        = messages["kioskKeyMapResve.storeCd"];
        dataItem.storeNm        = messages["kioskKeyMapResve.storeNm"];
        dataItem.posNo          = messages["kioskKeyMapResve.posNo"];
        dataItem.envstCd        = messages["kioskKeyMapResve.envstCd"];
        dataItem.brand          = messages["kioskKeyMapResve.brand"];
        dataItem.momsTeam       = messages["kioskKeyMapResve.momsTeam"];
        dataItem.momsAcShop     = messages["kioskKeyMapResve.momsAcShop"];
        dataItem.startDate      = messages["kioskKeyMapResve.startDate"];
        dataItem.orgTuClsType   = messages["kioskKeyMapResve.tuClsType"];
        dataItem.tuClsType      = messages["kioskKeyMapResve.tuClsType"];
        dataItem.modDt          = messages["kioskKeyMapResve.tuClsType"];

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
    $scope.$on("kioskKeyMapResveAddCtrl", function(event, data) {
        console.log(data);
        envstCd = data;
        // 리스트 조회
        $scope.searchKioskKeyMapResveAddList();
        event.preventDefault();
    });

    // 리스트 조회
    $scope.searchKioskKeyMapResveAddList = function () {

        var params = {};
        params.envstCd = envstCd;
        params.storeCd   = $scope.storeCd;
        params.storeNm   = $scope.storeNm;
        params.tuClsType = $scope.tuClsTypeAdd;
        params.prodHqBrandCd = $scope.prodHqBrandCd;
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
        if(orgnFg === "STORE") {params.posNo = $scope.posNoAddCombo.selectedValue;}

        $scope._inquirySub('/base/prod/kioskKeyMapResve/kioskKeyMapResve/getKioskKeyMapResveAddList.sb', params, function() {}, false);
    };

    // 판매가 수정시 체크박스 체크
    $scope.checked = function (item){
        if(item.tuClsType != undefined && item.tuClsType != null && item.tuClsType != ""){
            item.gChk = true;
        }
    }

    // 저장
    $scope.saveProdPrice2 = function(){
        
        // 예약날짜 체크
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
        var vStartDate = wijmo.Globalize.format($scope.startDateCombo.value, 'yyyyMMdd');

        if(Number(now) >= Number(vStartDate)) {
            $scope._popMsg(messages["salePriceResve.startDate"] + "는 " + messages["salePriceResve.resveDate.chk.msg"]);
            return false;
        }


        // 파라미터 설정
        var params = new Array();

        for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flex.collectionView.items[i].gChk) {
                if($scope.flex.collectionView.items[i].tuClsType != undefined && $scope.flex.collectionView.items[i].tuClsType != null && $scope.flex.collectionView.items[i].tuClsType != ""){
                    $scope.flex.collectionView.items[i].startDate = wijmo.Globalize.format($scope.startDateCombo.value, 'yyyyMMdd');
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/base/prod/kioskKeyMapResve/kioskKeyMapResve/saveKioskKeyMapResve.sb', params, function(){

            // 부모창 재조회
            var vScope = agrid.getScope("kioskKeyMapResveCtrl");
            vScope.searchKioskKeyMapResveList();

            // 팝업 닫기
            $scope.kioskKeyMapResveAddLayer.hide(true);
            $scope.close();

        });

    };
    
    // 닫기
    $scope.close = function () {
        $scope.startDateCombo.value = getTomorrow('-');
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
                item.tuClsType = $scope.tuClsTypeAddChange;
            }
        }
        $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();
    };

    // 매장권한) POS번호 선택 시, 키맵그룹 dropdown 조회
    $scope.setAddTuClsType = function (s) {
        // 키맵그룹 dropdown 재조회
        $scope.setTuClsDropdownList();
    };

    // 키맵그룹 dropdownLIst 재조회
    $scope.setTuClsDropdownList = function (){
        // 키맵그룹 dropdown 재조회
        var url = '/base/prod/kioskKeyMap/kioskKeyMap/getKioskTuClsTypeList.sb';
        var params = {};
        if(orgnFg === "STORE") {params.posNo = $scope.posNoAddCombo.selectedValue;}

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

                    comboArray.unshift({name: "전체", value: ""});
                    $scope._setComboData("tuClsTypeAddCombo", comboArray);
                } else {
                    var comboArrayAll = [];

                    comboArrayAll.unshift({name: "전체", value: ""});
                    $scope._setComboData("tuClsTypeAddCombo", comboArrayAll);
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg(messages["cmm.error"]);
            return false;
        });
    };

}]);