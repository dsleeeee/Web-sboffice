/****************************************************************
 *
 * 파일명 : dlvrAgencyReg.js
 * 설  명 : 배달관리 - 배달정보 - 배달대행사 연동 - 배달대행사 추가 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.14     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('dlvrAgencyRegCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dlvrAgencyRegCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 데이터 형태에 따른 표기 변환
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "deposit") {
                    e.cell.innerHTML = addComma(e.cell.innerText);
                }
            }
        });

        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.gChk = messages["cmm.chk"];
        dataItem.storeCode = messages["dlvrAgencyLink.storeCd"];
        dataItem.storeName = messages["dlvrAgencyLink.storeNm"];
        dataItem.address = messages["dlvrAgencyLink.addr"];
        dataItem.deposit = messages["dlvrAgencyLink.deposit"];

        s.columnHeaders.rows[0].dataItem = dataItem;

        s.itemFormatter = function (panel, r, c, cell) {
            if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                //align in center horizontally and vertically
                panel.rows[r].allowMerging = true;
                panel.columns[c].allowMerging = true;
                wijmo.setCss(cell, {
                    display: 'table',
                    tableLayout: 'fixed'
                });
                cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
                wijmo.setCss(cell.children[0], {
                    display: 'table-cell',
                    verticalAlign: 'middle',
                    textAlign: 'center'
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

    $scope.$on("dlvrAgencyRegCtrl", function (event, data) {

        // 배달대행사 코드 조회
        $scope.getDlvrAgency();

    });

    // 배달대행사 코드 조회
    $scope.getDlvrAgency = function () {

        var params = {};
        params.linkType = "003";

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/getDlvrAgency.sb", params, function (response) {
            var data = response.data.data.list;

            if (data.code === "0000") {

                var list = data.data;
                var comboArray = [];
                var comboData = {};

                // 배달대행사 이름순 정렬
                list.sort((a, b) => a.agencyName.localeCompare(b.agencyName));

                // 콤보 기본값 '선택' 추가
                comboData.name = messages["cmm.select"];
                comboData.value = "";
                comboArray.push(comboData);

                for (var i = 0; i < list.length; i++) {

                    // 직접배달(DR01), 부릉(DR02) 제외
                    if (list[i].agencyCode !== "DR01" && list[i].agencyCode !== "DR02") {
                        comboData = {};
                        comboData.name = list[i].agencyName;
                        comboData.value = list[i].agencyCode;
                        comboArray.push(comboData);
                    }
                }

                $scope._setComboData("srchDlvrAgency", comboArray);

            } else {
                $scope._popMsg(data.message);
                return;
            }

        });

    };

    // 하위 배달대행사 코드 조회
    $scope.getSubAgency = function (s) {

        var comboArray = [];
        var comboData = {};

        // 콤보 기본값 '선택' 추가
        comboData.name = messages["cmm.select"];
        comboData.value = "";
        comboArray.push(comboData);

        // 배달대행사 코드 선택값이 없는경우, 하위 배달대행사 콤보박스 disabled 처리
        if (s.selectedValue === "") {
            $scope._setComboData("srchSubAgency", comboArray);
            $scope.srchSubAgencyCombo.isDisabled = true;
            return;
        }

        var params = {};
        params.linkType = "003";

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/getDlvrAgency.sb", params, function (response) {
            var data = response.data.data.list;

            if (data.code === "0000") {

                var list = data.data;

                for (var i = 0; i < list.length; i++) {

                    // 선택한 배달대행사 코드에 따른 하위 배달대행사 코드 셋팅
                    if (list[i].agencyCode === $scope.srchDlvrAgencyCombo.selectedValue) {

                        var list2 = list[i].subAgencies;

                        // 배달대행사 이름순 정렬
                        list2.sort((a, b) => a.subAgencyName.localeCompare(b.subAgencyName));

                        for (var j = 0; j < list2.length; j++) {

                            comboData = {};
                            comboData.name = list2[j].subAgencyName;
                            comboData.value = list2[j].subAgencyCode;
                            comboArray.push(comboData);

                        }
                    }
                }

                $scope._setComboData("srchSubAgency", comboArray);

                // 하위 배달대행사 유무에 따라 콤보박스 disabled 처리
                if (comboArray.length > 1) {
                    $scope.srchSubAgencyCombo.isDisabled = false;
                } else {
                    $scope.srchSubAgencyCombo.isDisabled = true;
                }

            } else {
                $scope._popMsg(data.message);
                $scope.srchSubAgencyCombo.isDisabled = true;
                return;
            }

        });
    };

    // 가맹점 조회
    $scope.seachStoreList = function () {

        // 그리드 초기화
        var cv = new wijmo.collections.CollectionView([]);
        cv.trackChanges = true;
        $scope.data = cv;

        // 배달대행사를 선택하세요.
        if ($scope.srchDlvrAgencyCombo.selectedValue === "") {
            $scope._popMsg(messages['dlvrAgencyLink.store.srch.chk.msg1']);
            return;
        }

        // 하위 배달대행사를 선택하세요.
        if (!$scope.srchSubAgencyCombo.isDisabled) {
            if ($scope.srchSubAgencyCombo.selectedValue === "") {
                $scope._popMsg(messages['dlvrAgencyLink.store.srch.chk.msg2']);
                return;
            }
        }

        var params = {};
        params.linkType = "004";
        params.agencyCode = $scope.srchDlvrAgencyCombo.selectedValue;
        params.subAgencyCode = $scope.srchSubAgencyCombo.selectedValue;

        if ($scope.srchDlvrAgencyCombo.selectedValue === "DR05") {
            params.isB2BContract = true;
        } else {
            params.isB2BContract = false;
        }

        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/getDlvrAgencyStore.sb", params, function (response) {

            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');

            var data = response.data.data.list;

            if (data.code === "0000") {

                var list = data.data;

                list.forEach(item => {
                    item.gChk = false; // 체크박스 선택을 위해 추가
                });

                console.log(list);

                var grid = wijmo.Control.getControl("#wjGrid");
                grid.itemsSource = new wijmo.collections.CollectionView(list);
                grid.collectionView.trackChanges = true;

            } else if (data.code === "2525") {
                $scope._popMsg(data.message);
                return;
            } else if (data.code === "8888") {
                $scope._popMsg(data.message);
                return;
            } else if (data.code === "9999") {
                $scope._popMsg(data.message);
                return;
            }
        });

    };

    // 선택
    $scope.btnSelect = function () {

        var chkCnt = 0;

        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                chkCnt++;
            }
        }

        if (1 > chkCnt) {
            $scope._popMsg(messages["dlvrAgencyLink.store.mapping.chk.msg1"]); // 선택한 가맹점이 없습니다.
            return false;
        }


        if (chkCnt > 1) {
            $scope._popMsg(messages["dlvrAgencyLink.store.mapping.chk.msg2"]); // 가맹점은 1개만 선택하세요.
            return false;
        }

        // 파라미터 설정
        var params = {};
        for (var i = $scope.flex.collectionView.items.length - 1; i >= 0; i--) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk) {
                params.linkType = "005";
                params.agencyCode = $scope.srchDlvrAgencyCombo.selectedValue;

                if ($scope.srchDlvrAgencyCombo.selectedValue === "DR06" || $scope.srchDlvrAgencyCombo.selectedValue === "DR07" || $scope.srchDlvrAgencyCombo.selectedValue === "DR22" ||
                    $scope.srchDlvrAgencyCombo.selectedValue === "DR24" || $scope.srchDlvrAgencyCombo.selectedValue === "DR29") {
                    params.subAgencyCode = $scope.srchSubAgencyCombo.selectedValue;
                }

                if ($scope.srchDlvrAgencyCombo.selectedValue === "DR01" || $scope.srchDlvrAgencyCombo.selectedValue === "DR02" || $scope.srchDlvrAgencyCombo.selectedValue === "DR03") {
                    params.storeCode = "";
                } else if ($scope.srchDlvrAgencyCombo.selectedValue === "DR05") {
                    params.storeCode = "";
                } else {
                    params.storeCode = item.storeCode;
                }

                if ($scope.srchDlvrAgencyCombo.selectedValue === "DR05") {
                    params.isB2BContract = true;
                } else {
                    params.isB2BContract = false;
                }
            }
        }

        $scope._postJSONQuery.withOutPopUp("/dlvr/manage/info/dlvrAgencyLink/regAgencyLink.sb", params, function (response) {

            var data = response.data.data.list;

            if (data.code === "0000") {
                $scope._popMsg(data.message);
                
                // 팝업 닫기
                $scope.btnClose();
                
                // 배달대행사 연동 현황 재조회
                var vScope = agrid.getScope('dlvrAgencyLinkCtrl');
                vScope.searchStatus();

                console.log(data.data);

            } else if (data.code === "2525") {
                $scope._popMsg(data.message);
                return;
            } else if (data.code === "8888") {
                $scope._popMsg(data.message);
                return;
            } else if (data.code === "9999") {
                $scope._popMsg(data.message);
                return;
            }
        });
    };

    // 닫기
    $scope.btnClose = function () {

        // 그리드 초기화
        var cv = new wijmo.collections.CollectionView([]);
        cv.trackChanges = true;
        $scope.data = cv;

        $scope.wjDlvrAgencyRegLayer.hide();
    };

}]);