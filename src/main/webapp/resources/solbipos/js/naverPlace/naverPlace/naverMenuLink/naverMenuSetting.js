/****************************************************************
 *
 * 파일명 : naverMenuSetting.js
 * 설  명 : 네이버플레이스 > 네이버플레이스 > 네이버 메뉴연동 > 메뉴 연동 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.31     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

/**
 * 네이버메뉴 그리드
 */
app.controller('naverMenuSetting1Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('naverMenuSetting1Ctrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    $scope.$on("naverMenuSetting1Ctrl", function (event, data) {

        $scope.wjNaverMenuSettingLayer.show(true);

        // 조회
        $scope.getSubMenuOptionList(data);

        // 상품목록조회
        var vScope = agrid.getScope('naverMenuSetting3Ctrl');
        vScope.searchProdList();
    });

    // 조회
    $scope.getSubMenuOptionList = function (data) {
        var params = data;

        $.postJSON("/naverPlace/naverPlace/naverMenuLink/getSubMenuOptionList.sb", params, function(result) {

                var theGrid = new wijmo.Control.getControl('#wjGridNaverMenuSetting1');
                theGrid.itemsSource = new wijmo.collections.CollectionView(result.data.list);
                theGrid.collectionView.trackChanges = true;

                // 상품(첫번째 줄, 모상품)의 agencyKey가 없으면 theGrid2는 바인딩하지 않음
                var list = result.data.list;
                if (list && list.length > 0 && list[0].agencyKey) {
                    var theGrid2 = new wijmo.Control.getControl('#wjGridNaverMenuSetting2');
                    theGrid2.itemsSource = new wijmo.collections.CollectionView(list);
                    theGrid2.collectionView.trackChanges = true;
                }
            },
            function(result){
                s_alert.pop(result.message);
            }
        );
    };
    
    // 닫기
    $scope.close = function () {

        // 탭 닫을때 그리드 초기화
        var vScope1 = agrid.getScope("naverMenuSetting1Ctrl");
        vScope1._gridDataInit();
        var vScope2 = agrid.getScope("naverMenuSetting2Ctrl");
        vScope2._gridDataInit();
        var vScope3 = agrid.getScope("naverMenuSetting3Ctrl");
        vScope3._gridDataInit();

        // 상품목록 검색조건 초기화
        vScope3.prodCd = "";
        vScope3.prodNm = "";
        vScope3.srchStartDate.value = new Date();
        vScope3.srchEndDate.value = new Date();
        vScope3.isChecked = true;
        vScope3.srchStartDate.isReadOnly = true;
        vScope3.srchEndDate.isReadOnly = true;
    };

    // 저장
    $scope.save = function () {

        // 네이버메뉴와 링크포스메뉴의 그리드의 row 수가 다르면 저장 불가
        var theGrid1 = new wijmo.Control.getControl('#wjGridNaverMenuSetting1');
        var theGrid2 = new wijmo.Control.getControl('#wjGridNaverMenuSetting2');
        var grid1Cnt = (theGrid1 && theGrid1.collectionView) ? theGrid1.collectionView.items.length : 0;
        var grid2Cnt = (theGrid2 && theGrid2.collectionView) ? theGrid2.collectionView.items.length : 0;

        if (grid1Cnt !== grid2Cnt) {
            // 네이버메뉴와 링크포스메뉴의 갯수가 일치하지 않습니다.
            $scope._popMsg(messages["naverMenuLink.mapping.cnt.mismatch"]);
            return false;
        }

        // 모상품(첫번째 row) 정보 추출
        var naverMenu = theGrid1.collectionView.items[0];
        var lynkMenu  = theGrid2.collectionView.items[0];

        // 필요한 값 확인
        if (!naverMenu || !naverMenu.optionId) {
            $scope._popMsg(messages["naverMenuLink.mapping.optionId.empty"]);
            return false;
        }
        if (!lynkMenu || !lynkMenu.prodCd) {
            $scope._popMsg(messages["naverMenuLink.mapping.posNo.empty"]);
            return false;
        }

        // 메뉴(모상품) 연동
        var params = [{
            optionId: naverMenu.optionId,
            posNo: lynkMenu.prodCd,
            status: "MAPPED"
        }];

        $scope._postJSONSave.withOutPopUp("/naverPlace/naverPlace/naverMenuLink/mappingMenuOption.sb", params, function (response) {
            var result = response.data.data;
            var check = $scope._checkMappingResult(result);

            if (check.success) {

                // 모상품 연동 성공 시, 하위 사이드 상품이 있는 경우에만 연동 진행
                if (grid1Cnt > 1) {
                    $scope.mappingSubMenuOption(theGrid1, theGrid2);
                } else {
                    $scope._popMsg(messages["naverMenuLink.mapping.ok"]);
                    setTimeout(function () {
                        location.reload();
                    }, 1000);
                }
            } else {
                // 연동에 실패했습니다.
                $scope._popMsg(check.message);
                return false;
            }
        });
    };

    // 사이드 상품 연동
    $scope.mappingSubMenuOption = function (theGrid1, theGrid2) {

        var grid1Items = theGrid1.collectionView.items;
        var grid2Items = theGrid2.collectionView.items;

        var params = [];
        for (var i = 1; i < grid1Items.length; i++) {

            var naverSubMenu = grid1Items[i];
            var lynkSubMenu  = grid2Items[i];

            // 필요한 값 확인
            if (!naverSubMenu || !naverSubMenu.optionId
                || isEmpty(naverSubMenu.subOptionCategoryId) || isEmpty(naverSubMenu.subOptionItemSeq)) {
                $scope._popMsg(messages["naverMenuLink.mapping.subOption.empty"]);
                return false;
            }
            if (!lynkSubMenu || !lynkSubMenu.prodCd) {
                $scope._popMsg(messages["naverMenuLink.mapping.posNo.empty"]);
                return false;
            }

            params.push({
                optionId: naverSubMenu.optionId,
                posNo: lynkSubMenu.prodCd,
                status: "MAPPED",
                subOptionCategoryId: naverSubMenu.subOptionCategoryId,
                subOptionItemSeq: naverSubMenu.subOptionItemSeq
            });
        }

        $scope._postJSONSave.withOutPopUp("/naverPlace/naverPlace/naverMenuLink/mappingSubMenuOption.sb", params, function (response) {
            var result = response.data.data;
            var check = $scope._checkMappingResult(result);

            if (check.success) {
                // 연동에 성공했습니다.
                $scope._popMsg(messages["naverMenuLink.mapping.ok"]);

                setTimeout(function () {
                    // 재조회
                    location.reload();
                }, 1000);
            } else {
                // 연동에 실패했습니다.
                $scope._popMsg(check.message);
                return false;
            }
        });
    };

    // 매핑 API 응답(배열) 판정 : 원소 전부 status === 200 이어야 성공
    // 실패 건이 있으면 { success: false, message: "실패 메시지 모음" } 반환
    $scope._checkMappingResult = function (result) {

        if (!result || result.length === 0) {
            return { success: false, message: messages["naverMenuLink.mapping.error"] };
        }

        var failMessages = [];
        for (var i = 0; i < result.length; i++) {
            var item = result[i];
            if (!item || item.status !== 200) {
                var data = item ? item.data : null;
                var optionId = data ? data.optionId : "";
                var msg = item ? item.message : "";

                // 서브옵션(사이드 상품) 항목이면 구분값도 같이 표기
                var label = "[" + optionId;
                if (data && !isEmpty(data.subOptionCategoryId)) {
                    label += "-" + data.subOptionCategoryId + "-" + data.subOptionItemSeq;
                }
                label += "] ";

                failMessages.push(label + msg);
            }
        }

        if (failMessages.length > 0) {
            return { success: false, message: failMessages.join("<br/>") };
        }

        return { success: true };
    };

    // 취소
    $scope.cancel = function () {
        $scope.close();
    };

}]);

/**
 * 링크포스메뉴 그리드
 */
app.controller('naverMenuSetting2Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('naverMenuSetting2Ctrl', $scope, $http, false));

    $scope.$on("naverMenuSetting2Ctrl", function (event, data) {

    });

}]);

/**
 * 상품목록 그리드
 */
app.controller('naverMenuSetting3Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('naverMenuSetting3Ctrl', $scope, $http, false));

    // 등록일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 등록일자 기본 '전체기간'으로
    $scope.isChecked = true;

    $scope.initGrid = function (s, e) {

        // 등록일자 기본 '전체기간'으로
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;

        // rowType이 1(하위상품)인 행은 gChk 체크박스를 숨김
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "gChk") {
                    var item = s.rows[e.row].dataItem;
                    if (item.rowType === 1) {
                        e.cell.innerHTML = "";
                    }
                }
            }
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
        var dataItem          = {};
        dataItem.gChk         = messages["cmm.chk"];
        dataItem.prodClassNm  = messages["naverMenuLink.prodClassNm"];
        dataItem.dispProdCd   = messages["naverMenuLink.prodCd"];
        dataItem.dispProdNm   = messages["naverMenuLink.prodNm"];

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

    };

    //전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };

    $scope.$on("naverMenuSetting3Ctrl", function (event, data) {

        // 상품목록조회
        $scope.searchProdList();
        event.preventDefault();
    });

    // 상품목록조회
    $scope.searchProdList = function () {

        var params = {};
        params.chkDt = $scope.isChecked;
        params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;

        $scope._inquiryMain("/naverPlace/naverPlace/naverMenuLink/getProdList.sb", params, function() {

        }, false);
    };

    // 등록
    $scope.regProd = function () {

        var theGrid3 = new wijmo.Control.getControl('#wjGridNaverMenuSetting3');
        var allItems = theGrid3.collectionView.items;

        // 선택한 상품이 있는지 확인
        var checkedProdCdList = [];
        for (var i = 0; i < allItems.length; i++) {
            var item = allItems[i];
            if (item.rowType === 0 && item.gChk) {
                checkedProdCdList.push(item.prodCd);
            }
        }

        if (checkedProdCdList.length === 0) {
            // 선택 상품이 없습니다.
            $scope._popMsg(messages["naverMenuLink.selectProd.chk.msg"]);
            return false;
        }

        // 체크된 상품(모상품)과 같은 prodCd를 가진 하위(사이드) 상품 전체 수집
        var copyList = [];
        for (var j = 0; j < allItems.length; j++) {
            var row = allItems[j];
            if (checkedProdCdList.indexOf(row.prodCd) > -1) {
                var copyRow = angular.extend({}, row);
                copyRow.gChk = false;
                copyRow.prodCd = row.dispProdCd;
                copyRow.prodNm = row.dispProdNm;
                copyList.push(copyRow);
            }
        }

        // 링크포스메뉴 그리드에 복사
        var theGrid2 = new wijmo.Control.getControl('#wjGridNaverMenuSetting2');
        theGrid2.itemsSource = new wijmo.collections.CollectionView(copyList);
        theGrid2.collectionView.trackChanges = true;
    };

}]);