/****************************************************************
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.25     이재영
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  회원정보 그리드
 **********************************************************************/
app.controller('memberChgBatchCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberChgBatchCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);
    $scope._setComboData("rEmailRecvYn", recvDataMapEx);
    $scope._setComboData("rSmsRecvYn", recvDataMapEx);
    $scope._setComboData("rGendrFg", genderDataMapEx);
    $scope._setComboData("rWeddingYn", weddingDataMap);
    $scope._setComboData("rUseYn", useDataMap);
    $scope._setComboData("rMemberClass", memberClassList);
    $scope._setComboData("rMembrcardYn", rMembrcardList);
    memberClassList.unshift({name: "전체", value: ""});

    $scope._getComboDataQuery('072', 'emailRecvYn', 'A');
    $scope._getComboDataQuery('072', 'smsRecvYn', 'A');
    $scope._getComboDataQuery('032', 'anvType', 'A');
    $scope._getComboDataQuery('077', 'periodType', 'A');
    $scope._getComboDataQuery('076', 'weddingYn', 'A');
    $scope._getComboDataQuery('055', 'gendrFg', 'A');
    $scope._getComboDataQuery('067', 'useYn', 'A');


    // 선택 회원
    $scope.selectedMember;
    $scope.setSelectedMember = function (member) {
        $scope.selectedMember = member;
    };
    $scope.getSelectedMember = function () {
        return $scope.selectedMember;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useDataMap, 'value', 'name');
        $scope.memberClassDataMap = new wijmo.grid.DataMap(memberClassList, 'value', 'name');
        $scope.emailRecvDataMap = new wijmo.grid.DataMap(recvDataMapEx, 'value', 'name');
        $scope.smsRecvDataMap = new wijmo.grid.DataMap(recvDataMapEx, 'value', 'name');

        // ReadOnly 효과설정
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                // 회원번호, 회원명 클릭시 상세정보 팝업
                if (col.binding === "membrNo") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
                // 후불적용매장등록 클릭시 매장선택 팝업
                if (col.binding === "postpaidStore") {
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 회원선택
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                // 회원번호, 회원명 클릭시 상세정보 팝업
                if (col.binding === "membrNo" || col.binding === "membrNm") {
                    var selectedData = s.rows[ht.row].dataItem;
                    $scope.setSelectedMember(selectedData);
                    // $scope.memberRegistLayer.show(true);
                    // // $scope.memberInfoDetailLayer.show(true);
                    event.preventDefault();
                }
            }
        });

        // <-- 그리드 헤더3줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.gChk = messages["cmm.chk"]
        dataItem.membrNo = messages["regist.membr.no"]
        dataItem.membrNm = messages["regist.membr.nm"]
        dataItem.membrClassCd = messages["regist.class.cd"]
        dataItem.membrClassNm = messages["regist.class.nm"]
        dataItem.membrCardNo = messages["regist.card.no"]
        dataItem.birthday = messages["regist.brthd"]
        dataItem.telNo = messages["regist.tel"]
        dataItem.phoneNo = messages["regist.phone.no"]
        dataItem.shortNo = messages["regist.membr.stortNo"]
        // dataItem.regStoreCd = messages["regist.membr.regStore"]
        // dataItem.regStoreNm = messages["regist.membr.regStore"]
        dataItem.emailRecvYn = messages["regist.email.recv"]
        dataItem.smsRecvYn = messages["regist.sms.recv"]
        dataItem.useYn = messages["regist.useYn"]
        dataItem.postpaidStore = messages["regist.membr.store"]

        dataItem.save = messages["regist.membr.total"]
        dataItem.saveCnt = messages["regist.membr.total"]
        dataItem.saveAmt = messages["regist.membr.total"]

        dataItem.visit = messages["regist.membr.total"]
        dataItem.firstSaleDate = messages["regist.membr.total"]
        dataItem.lastSaleDate = messages["regist.membr.total"]
        s.columnHeaders.rows[0].dataItem = dataItem;

        //둘째줄 헤더
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1 = {};
        dataItem1.gChk = messages["cmm.chk"]
        dataItem1.membrNo = messages["regist.membr.no"]
        dataItem1.membrNm = messages["regist.membr.nm"]
        dataItem1.membrClassCd = messages["regist.class.cd"]
        dataItem1.membrClassNm = messages["regist.class.nm"]
        dataItem1.membrCardNo = messages["regist.card.no"]
        dataItem1.birthday = messages["regist.brthd"]
        dataItem1.telNo = messages["regist.tel"]
        dataItem1.phoneNo = messages["regist.phone.no"]
        dataItem1.shortNo = messages["regist.membr.stortNo"]
        dataItem1.regStoreCd = messages["regist.membr.regStore"]
        dataItem1.regStoreNm = messages["regist.membr.regStore"]
        dataItem1.emailRecvYn = messages["regist.email.recv"]
        dataItem1.smsRecvYn = messages["regist.sms.recv"]
        dataItem1.useYn = messages["regist.useYn"]
        dataItem1.postpaidStore = messages["regist.membr.store"]

        dataItem1.save = messages["regist.membr.save"]
        dataItem1.saveCnt = messages["regist.membr.save"]
        dataItem1.saveAmt = messages["regist.membr.save"]

        dataItem1.visit = messages["regist.membr.visit"]
        dataItem1.firstSaleDate = messages["regist.membr.visit"]
        dataItem1.lastSaleDate = messages["regist.membr.visit"]

        s.columnHeaders.rows[1].dataItem = dataItem1;

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
        // <-- //그리드 헤더3줄 -->

    };

    // 조회 버튼 클릭
    $scope.$on("memberChgBatchCtrl", function (event, data) {
        // 이출, 이입매장 초기화
        /*$("#regStoreCd").val("");
        $("#regStoreNm").val(messages["cmm.select"]);*/
        $scope.getMemberChgBatchList();
        event.preventDefault();
    });

    // 회원 목록 조회
    $scope.getMemberChgBatchList = function () {
        var params = {};

        params.periodType = $scope.periodType;
        params.periodStartDate = dateToDaystring($scope.periodStartDate).replaceAll('-', '');
        params.periodEndDate = dateToDaystring($scope.periodEndDate).replaceAll('-', '');
        params.anvType = $scope.anvType;
        params.anvStartDate = dateToDaystring($scope.anvStartDate).replaceAll('-', '');
        params.anvEndDate = dateToDaystring($scope.anvEndDate).replaceAll('-', '');

        params.startSaveSale = $scope.startSaveSale;
        params.endSaveSale = $scope.endSaveSale;
        params.startAvablPoint = $scope.startAvablPoint;
        params.endAvablPoint = $scope.endAvablPoint
        params.stortNo = $scope.stortNo;
        params.weddingYn = $scope.weddingYn;
        params.memberClass = $scope.memberClass;
        params.phoneNo = $scope.phoneNo;

        params.listScale = $scope.listScale;

        params.membrNo = $("#memberNo").val();
        params.membrNm = $("#memberNm").val();
        params.regStoreCd = $("#regStoreCd").val();
        params.telNo = $("#telNo").val();
        params.membrCardNo = $("#membrCardNo").val();
        params.emailAddr = $("#emailAddr").val();
        params.emailRecvYn = $scope.emailRecvYn;
        params.smsRecvYn = $scope.smsRecvYn;
        params.gendrFg = $scope.gendrFg;
        params.useYn = $scope.useYn;

        // console.log('params ', params);


        $scope._inquiryMain("/membr/info/chgBatch/chgBatch/getMemberChgBatchList.sb", params, function () {
        });
    };

    // 저장
    $scope.gridSave = function () {
        // 파라미터 설정

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            $scope.flex.collectionView.itemsEdited[i].status = "U";
            $scope.flex.collectionView.itemsEdited[i].birthday = getFormatDateString($scope.flex.collectionView.itemsEdited[i].birthday);
            params.push($scope.flex.collectionView.itemsEdited[i]);

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $.postJSONArray("/membr/info/chgBatch/chgBatch/getMemberChgBatchSave.sb", params, function (result) {
                $scope.getMemberChgBatchList();
            });
        };
    };
}]);