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

// 검색기간 콤보박스
var periodData = [
    {"name":"기간 미사용","value":"all"},
    {"name":"가입일","value":"reg"},
    {"name":"최종방문일","value":"last"}
];

/**********************************************************************
 *  회원정보 그리드
 **********************************************************************/
app.controller('memberChgBatchCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('memberChgBatchCtrl', $scope, $http, true));

    var rAnvTypeList = [{value: "", name: messages["chgBatch.membr.anv.none"]}, {
        value: "1",
        name: messages["chgBatch.membr.anv.birth"]
    }, {value: "2", name: messages["chgBatch.membr.anv.weding"]}];

    // var rShortNoYnList = [{value: "", name: "전체"}, {value: "1", name: messages["chgBatch.membr.short.telNo"]}];
    var rShortNoYnList = [{value: "1", name: messages["chgBatch.membr.short.telNo"]}];

    // var rMonthList = [];
    // for (var i = 1; i <= 12; i++) {
    //     var month = new Object();
    //     if (i < 10) {
    //         month.value = '0' + i;
    //         month.name = '0' + i;
    //     } else {
    //         month.value = '' + i;
    //         month.name = '' + i;
    //     }
    //     rMonthList.push(month);
    // }
    //
    // var rDayList = [];
    // for (var i = 1; i <= 31; i++) {
    //     var day = new Object();
    //     if (i < 10) {
    //         day.value = '0' + i;
    //         day.name = '0' + i;
    //     } else {
    //         day.value = '' + i;
    //         day.name = '' + i;
    //     }
    //     rDayList.push(day);
    // }

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    /*$scope._setComboData("rEmailRecvYn", recvDataMapEx);
    $scope._setComboData("rSmsRecvYn", recvDataMapEx);*/
    /*$scope._setComboData("rGendrFg", genderDataMapEx);*/
    /*$scope._setComboData("rWeddingYn", weddingDataMap);*/
    $scope._setComboData("rMemberClassList", memberClass); // 회원등급 적용
    // memberClass.unshift({name: "전체", value: ""});
    $scope._setComboData("rMemberClassSelect", memberClassSelect);
    memberClassSelect.unshift({name: "선택", value: ""});
    // $scope._setComboData("rMembrcardYn", rMembrcardList);
    $scope._getComboDataQuery('299', 'membrCardFg', 'A');
    $scope._getComboDataQuery('032', 'anvType', 'A');
    // $scope._setComboData("anvType", rAnvTypeList);
    $scope._setComboData("shortNoYn", rShortNoYnList); // 단축번호 적용
    // $scope._setComboData("startMonth", rMonthList);
    // $scope._setComboData("startDay", rDayList);
    // $scope._setComboData("endMonth", rMonthList);
    // $scope._setComboData("endDay", rDayList);
    // $scope.memberClassSelect = memberClassList;
    // $scope.memberClassSelect.splice(0,1);
    // $scope._setComboData("rMemberClassSelect", $scope.memberClassSelect);
    $scope._getComboDataQuery('072', 'rEmailRecvYn', 'A'); // 이메일수신
    $scope._getComboDataQuery('072', 'rSmsRecvYn', 'A'); // SMS수신
    $scope._getComboDataQuery('072', 'emailRecvYn', ''); // 이메일수신 적용
    $scope._getComboDataQuery('072', 'smsRecvYn', ''); // SMS수신 적용
    // $scope._getComboDataQuery('077', 'periodType', ''); // 검색기간 콤보박스
    $scope._setComboData("periodType", periodData); // 검색기간 콤보박스
    $scope._getComboDataQuery('076', 'weddingYn', 'A');
    $scope._getComboDataQuery('055', 'gendrFg', 'A');
    $scope._getComboDataQuery('067', 'useYnAll', 'A');
    $scope._getComboDataQuery('067', 'useYn', '');

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
        $scope.memberClassDataMap = new wijmo.grid.DataMap(memberClassSelect, 'value', 'name');
        $scope.emailRecvDataMap = new wijmo.grid.DataMap(recvDataMapEx, 'value', 'name');
        $scope.smsRecvDataMap = new wijmo.grid.DataMap(recvDataMapEx, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 회원번호, 회원명
                if (col.binding === "membrNo") {
                    // var item = s.rows[e.row].dataItem;
                    wijmo.addClass(e.cell, 'wijLink');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 회원번호, 회원명 클릭시 상세정보 팝업
                if (col.binding === "membrNo") {
                    var selectedData = s.rows[ht.row].dataItem;
                    $scope.setSelectedMember(selectedData);
                    $scope.wjMemberInfoDtlLayer.show(true);
                    event.preventDefault();
                }
            }
        });


        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 브랜드 변경시 체크박스 체크
                if (col.binding === "membrClassCd" || col.binding === "shortNo" || col.binding === "emailRecvYn" || col.binding === "smsRecvYn" || col.binding === "useYn") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });

        // <-- 그리드 헤더3줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.gChk = messages["cmm.chk"];
        dataItem.membrNo = messages["regist.membr.no"];
        dataItem.membrNm = messages["regist.membr.nm"];
        dataItem.membrClassCd = messages["regist.class.cd"];
        dataItem.membrClassNm = messages["regist.class.nm"];
        dataItem.membrCardNo = messages["regist.card.no"];
        dataItem.birthday = messages["regist.brthd"];
        dataItem.telNo = messages["regist.tel"];
        dataItem.phoneNo = messages["regist.phone.no"];
        dataItem.shortNo = messages["regist.membr.shortNo"];
        // dataItem.regStoreCd = messages["regist.membr.regStore"]
        // dataItem.regStoreNm = messages["regist.membr.regStore"]
        dataItem.emailRecvYn = messages["regist.email.recv"];
        dataItem.smsRecvYn = messages["regist.sms.recv"];
        dataItem.useYn = messages["regist.useYn"];
        dataItem.postpaidStore = messages["regist.membr.store"];

        dataItem.save = messages["regist.membr.total"];
        dataItem.saveCnt = messages["regist.membr.total"];
        dataItem.saveAmt = messages["regist.membr.total"];

        dataItem.visit = messages["regist.membr.total"];
        dataItem.firstSaleDate = messages["regist.membr.total"];
        dataItem.lastSaleDate = messages["regist.membr.total"];
        s.columnHeaders.rows[0].dataItem = dataItem;

        //둘째줄 헤더
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        var dataItem1 = {};
        dataItem1.gChk = messages["cmm.chk"];
        dataItem1.membrNo = messages["regist.membr.no"];
        dataItem1.membrNm = messages["regist.membr.nm"];
        dataItem1.membrClassCd = messages["regist.class.cd"];
        dataItem1.membrClassNm = messages["regist.class.nm"];
        dataItem1.membrCardNo = messages["regist.card.no"];
        dataItem1.birthday = messages["regist.brthd"];
        dataItem1.telNo = messages["regist.tel"];
        dataItem1.phoneNo = messages["regist.phone.no"];
        dataItem1.shortNo = messages["regist.membr.shortNo"];
        dataItem1.regStoreCd = messages["regist.membr.regStore"];
        dataItem1.regStoreNm = messages["regist.membr.regStore"];
        dataItem1.emailRecvYn = messages["regist.email.recv"];
        dataItem1.smsRecvYn = messages["regist.sms.recv"];
        dataItem1.useYn = messages["regist.useYn"];
        dataItem1.postpaidStore = messages["regist.membr.store"];

        dataItem1.save = messages["regist.membr.save"];
        dataItem1.saveCnt = messages["regist.membr.save"];
        dataItem1.saveAmt = messages["regist.membr.save"];

        dataItem1.visit = messages["regist.membr.visit"];
        dataItem1.firstSaleDate = messages["regist.membr.visit"];
        dataItem1.lastSaleDate = messages["regist.membr.visit"];

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
                })


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
        // <-- //그리드 헤더3줄 -->
    };

    // 화면 ready 된 후 설정
    angular.element(document).ready(function () {

        // 회원조회 팝업 핸들러 추가
        $scope.wjMemberInfoDtlLayer.shown.addHandler(function (s) {
            setTimeout(function () {
                $scope._broadcast('memberInfoDtlCtrl', $scope.getSelectedMember());
            }, 50)
        });
    });

    // 브랜드 변경시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    };

    // 등록매장
    $scope.regStoreShow = function () {
        $scope._broadcast('regStoreCtrl');
    };
    $scope.regUseStoreShow = function () {
        $scope._broadcast('regUseStoreCtrl');
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
        // if ($scope.anvType !== 0) {
        //     params.anvStartDate = $scope.startMonth + $scope.startDay;
        //     params.anvEndDate = $scope.endMonth + $scope.endDay;
        // }
        params.startSaveSale = $scope.startSaveSale;
        params.endSaveSale = $scope.endSaveSale;
        params.startAvablPoint = $scope.startAvablPoint;
        params.endAvablPoint = $scope.endAvablPoint;
        /*params.stortNo = $scope.stortNo;*/
        params.weddingYn = $scope.weddingYn;
        params.memberClass = $scope.memberClass;
        // params.phoneNo = $scope.phoneNo;
        params.shortNo = $scope.shortNo;
        params.listScale = $scope.listScale;

        params.membrNo = $("#memberNo").val();
        params.membrNm = $("#memberNm").val();

        params.storeMembr = $scope.storeMembr;
        params.visitStoreMembr = $scope.visitStoreMembr;

        params.regStoreCd = $("#regStoreCd").val();
        params.telNo = $("#telNo").val();
        params.regUseStoreCd = $("#regUseStoreCd").val();
        params.membrCardNo = $("#membrCardNo").val();
        params.cstCardUseFg = $scope.cstCardUseFg;
        params.emailAddr = $("#emailAddr").val();
        params.emailRecvYn = $scope.emailRecvYn;
        params.smsRecvYn = $scope.smsRecvYn;
        params.gendrFg = $scope.gendrFg;
        params.useYn = $scope.useYn;

        if (orgnFg === "HQ") {
            if (params.regUseStoreCd !== null && params.regUseStoreCd !== '') {
                $scope.regStoreChk = true
            } else {
                $scope.regStoreChk = false
            }
        }else {
            if(hqOfficeCd !== "00000") {
                if ($scope.visitStoreMembr) {
                    $scope.regStoreChk = true
                } else if (!$scope.visitStoreMembr) {
                    $scope.regStoreChk = false
                }
            }
        }

        // 자기매장 회원만 보이게
        // if(orgnFg == "STORE") {
        //     params.storeMembr = true;
        // }

        // console.log('params ', params);
        $scope._inquiryMain("/membr/info/chgBatch/chgBatch/getMemberChgBatchList.sb", params, function () {});
    };

    $scope.chgSave = function (param) {
        console.log("$scope.chgMembrClassCd:: ", $scope.chgMembrClassCd);
        console.log("$scope.chgSmsRecvYn:: ", $scope.chgSmsRecvYn);
        console.log("$scope.chgEmailRecvYn:: ", $scope.chgEmailRecvYn);
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk === true) {
                if (param === "class") {
                    $scope.flex.collectionView.items[i].membrClassCd = $scope.chgMembrClassCd;
                } else if (param === "short") {
                    var tel = $scope.flex.collectionView.items[i].telNo;
                    $scope.flex.collectionView.items[i].shortNo = tel.substr(tel.length - 4, 4);
                } else if (param === "sms") {
                    $scope.flex.collectionView.items[i].smsRecvYn = $scope.chgSmsRecvYn;
                } else if (param === "email") {
                    $scope.flex.collectionView.items[i].emailRecvYn = $scope.chgEmailRecvYn;
                } else if(param === "use") {
                    $scope.flex.collectionView.items[i].useYn = $scope.chgUseYn;
                }
            }
        }
        // $scope.flex.collectionView.commitEdit();
        $scope.flex.collectionView.refresh();
    };

    // 삭제
    $scope.gridDel = function () {
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if ($scope.flex.collectionView.items[i].gChk) {
                var obj = {};
                obj.membrNo = $scope.flex.collectionView.items[i].membrNo;
                params.push(obj);
            }
        }

        if(params.length !== 0){
            $scope._popConfirm(messages["regist.membr.delYn.msg"], function() {
                // 회원 사용여부 '미사용'으로 변경 수행 : 저장URL, 파라미터, 콜백함수
                $scope._save("/membr/info/view/base/remove.sb", params, function () {
                    $scope.getMemberChgBatchList()
                });
            });
        }
    };

    // 저장
    $scope.gridSave = function () {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

            if($scope.flex.collectionView.items[i].gChk) {
                if($scope.flex.collectionView.items[i].membrClassCd === null || $scope.flex.collectionView.items[i].membrClassCd === ""){
                    $scope._popMsg(messages['regist.class.cd.msg']);
                    return false;
                }
                // 단축번호 앞뒤 공백 및 엔터값 제거
                if ($scope.flex.collectionView.items[i].shortNo !== "" && $scope.flex.collectionView.items[i].shortNo !== null) {
                    $scope.flex.collectionView.items[i].shortNo = $scope.flex.collectionView.items[i].shortNo.trim().removeEnter();
                    if ($scope.flex.collectionView.items[i].shortNo.length <= 0) {
                        $scope.flex.collectionView.items[i].shortNo = null;
                    }

                    var numChkexp = /[^0-9]/g;
                    if($scope.flex.collectionView.items[i].shortNo.length > 4 || numChkexp.test($scope.flex.collectionView.items[i].shortNo)){
                        $scope._popMsg(messages['regist.shortNo.msg']);
                        return false;
                    }
                }else{
                        $scope.flex.collectionView.items[i].shortNo = null;
                }

                if (nvl($scope.flex.collectionView.items[i].membrClassCd,'') !== nvl($scope.flex.collectionView.items[i].oldMembrClassCd,'')
                    || nvl($scope.flex.collectionView.items[i].shortNo,'') !== nvl($scope.flex.collectionView.items[i].oldShortNo,'')
                    || nvl($scope.flex.collectionView.items[i].emailRecvYn,'') !== nvl($scope.flex.collectionView.items[i].oldEmailRecvYn,'')
                    || nvl($scope.flex.collectionView.items[i].smsRecvYn,'') !== nvl($scope.flex.collectionView.items[i].oldSmsRecvYn,'')
                    || nvl($scope.flex.collectionView.items[i].useYn,'') !== nvl($scope.flex.collectionView.items[i].oldUseYn,'')) {

                    $scope.flex.collectionView.items[i].status = "U";
                    // $scope.flex.collectionView.itemsEdited[i].birthday = getFormatDateString($scope.flex.collectionView.itemsEdited[i].birthday);
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        }

        if (params.length <= 0) {
            // 변경사항이 없습니다.
            $scope._popMsg(messages['cmm.not.modify']);
            return false;
        }

        $.postJSONArray("/membr/info/chgBatch/chgBatch/getMemberChgBatchSave.sb", params, function (result) {
            if (result.status === "OK") {
                $scope._popMsg(messages["cmm.saveSucc"]); // 저장 되었습니다.
                $scope.getMemberChgBatchList();
                $scope.$broadcast('loadingPopupInactive');

            } else {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(result.status);
                return false;
            }
        }, function (err) {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(err.message);
        });
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

}]);