'use strict';

function RootController(ctrlName, $scope, $http, isPicker) {
    // set $scope Name
    $scope.name = ctrlName;
    // 그리드 초기화
    $scope._gridDataInit = function () {
        $scope.data = new wijmo.collections.CollectionView([]);
    };
    // 조회 : 마스터 그리드
    $scope._inquiryMain = function (url, params, callback) {
        _inquiry(url, params, callback, true, true);
    };
    // 조회 : 서브 그리드
    $scope._inquirySub = function (url, params, callback, isView) {
        if (isEmpty(isView) || typeof (isView) !== "boolean") {
            isView = false;
        }
        _inquiry(url, params, callback, isView, false);
    };

    // 그리드 조회 : private
    function _inquiry(url, params, callback, isView, isMaster) {
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive');
        // 마스터그리드 여부
        if (isMaster) {
            var el = angular.element('input');
            var name = '';
            for (var i = 0, l = el.length; i < l; i += 1) {
                name = angular.element(el[i]).attr('ng-model');
                if (name && $scope[name]) {
                    params[name] = $scope[name];
                }
            }
        }
        // 페이징 처리
        if ($scope._getPagingInfo('curr') > 0) {
            params['curr'] = $scope._getPagingInfo('curr');
        } else {
            params['curr'] = 1;
        }
        // 가상로그인 대응한 session id 설정
        if (document.getElementsByName('sessionId')[0]) {
            params['sid'] = document.getElementsByName('sessionId')[0].value;
        }
        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: url, /* 통신할 URL */
            params: params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            //$scope.pageData = response.data.data.page;
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (_httpStatusCheck(response, true)) {
                // this callback will be called asynchronously
                // when the response is available
                var list = response.data.data.list;
                if (list.length === undefined || list.length === 0) {
                    $scope.data = new wijmo.collections.CollectionView([]);
                    if (isView && response.data.message) {

                        // 페이징 처리
                        $scope._setPagingInfo('ctrlName', $scope.name);
                        $scope._setPagingInfo('pageScale', 10);
                        $scope._setPagingInfo('curr', 1);
                        $scope._setPagingInfo('totCnt', 1);
                        $scope._setPagingInfo('totalPage', 1);

                        $scope._broadcast('drawPager');

                        $scope._popMsg(response.data.message);
                    }
                    return false;
                }
                var data = new wijmo.collections.CollectionView(list);
                data.trackChanges = true;
                $scope.data = data;

                // 페이징 처리
                if (response.data.data.page && response.data.data.page.curr) {
                    var pagingInfo = response.data.data.page;
                    $scope._setPagingInfo('ctrlName', $scope.name);
                    $scope._setPagingInfo('pageScale', pagingInfo.pageScale);
                    $scope._setPagingInfo('curr', pagingInfo.curr);
                    $scope._setPagingInfo('totCnt', pagingInfo.totCnt);
                    $scope._setPagingInfo('totalPage', pagingInfo.totalPage);
                    $scope._broadcast('drawPager');
                }
            }
        }, function errorCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.error']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            if (typeof callback === 'function') {
                setTimeout(function () {
                    callback();
                }, 10);
            }
        });
    }

    // 행 추가
    $scope._addRow = function (params, pos) {
        _gridAddRow(params, pos);
    };

    // 행 추가 : private
    function _gridAddRow(params, pos) {
        var flex = $scope.flex;
        if (!flex.collectionView) {
            flex.itemsSource = new wijmo.collections.CollectionView();
        }
        var newRow = flex.collectionView.addNew();
        newRow.status = 'I';
        newRow.gChk = true;
        for (var prop in params) {
            newRow[prop] = params[prop];
        }
        flex.collectionView.trackChanges = true;
        flex.collectionView.commitNew();
        // 추가된 Row 선택
        setTimeout(function () {
            flex.scrollIntoView(flex.rows.length - 1, 0);
            flex.select(flex.rows.length - 1, 1);
            flex.focus();
            flex.startEditing(true, flex.rows.length - 1, (pos === null ? 0 : pos), true);
        }, 50);
    }

    // 그리드 저장
    $scope._save = function (url, params, callback) {
        return _gridSave(url, params, callback);
    };
    $scope._customSave = function (url, params, callback) {
        return _customGridSave(url, params, callback);
    };

    // 그리드 저장 : private
    function _gridSave(url, params, callback) {
        var sParam = {};
        // 길이체크
        if (params.length <= 0) {
            // 변경사항이 없습니다.
            $scope._popMsg(messages['cmm.not.modify']);
            return false;
        } else {
            // 로딩바 show
            $scope.$broadcast('loadingPopupActive', messages['cmm.saving']);
            // 가상로그인 대응한 session id 설정
            if (document.getElementsByName('sessionId')[0]) {
                sParam['sid'] = document.getElementsByName('sessionId')[0].value;
            }
        }
        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: url, /* 통신할 URL */
            data: params, /* 파라메터로 보낼 데이터 : @requestBody */
            params: sParam, /* 파라메터로 보낼 데이터 : request.getParameter */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (_httpStatusCheck(response, true)) {
                $scope._popMsg(messages['cmm.saveSucc']);
                $scope.flex.collectionView.clearChanges();
            }
        }, function errorCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            if (typeof callback === 'function') {
                setTimeout(function () {
                    callback();
                }, 10);
            }
        });
    }

    function _customGridSave(url, params, callback) {
        var sParam = {};
        // 길이체크
        if (params.length <= 0) {
            // 변경사항이 없습니다.
            $scope._popMsg(messages['cmm.not.modify']);
            return false;
        } else {
            // 로딩바 show
            $scope.$broadcast('loadingPopupActive', messages['cmm.saving']);
            // 가상로그인 대응한 session id 설정
            if (document.getElementsByName('sessionId')[0]) {
                sParam['sid'] = document.getElementsByName('sessionId')[0].value;
            }
        }
        // ajax 통신 설정
        $http({
            method: 'POST', //방식
            url: url, /* 통신할 URL */
            data: params, /* 파라메터로 보낼 데이터 : @requestBody */
            params: sParam, /* 파라메터로 보낼 데이터 : request.getParameter */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            if (response.status == "OK") {
                if (_httpStatusCheck(response, true)) {
                    $scope._popMsg(messages['cmm.saveSucc']);
                    $scope.flex.collectionView.clearChanges();
                } else {
                    $scope._popMsg(messages['cmm.saveFail']);
                }
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
        }, function errorCallback(response) {
            // 로딩바 hide
            $scope.$broadcast('loadingPopupInactive');
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            console.log("errer_response:: ", response)
            return true
        });
    }

    // http 조회 후 status 체크
    $scope._httpStatusCheck = function (res, isMsg) {
        return _httpStatusCheck(res, isMsg);
    };

    // private
    function _httpStatusCheck(res, isMsg) {
        if (res.data.status === 'OK') {
            return true;
        } else if (res.data.status === 'FAIL') {
            if (isMsg) {
                $scope._popMsg('Ajax Fail By HTTP Request');
            }
            return false;
        } else if (res.data.status === 'SESSION_EXFIRE') {
            if (isMsg) {
                $scope._popMsg(res.data.message, function () {
                    location.href = res.data.url;
                });
            }
            return false;
        } else if (res.data.status === 'SERVER_ERROR') {
            if (isMsg) {
                $scope._popMsg(res.data.message);
            }
            return false;
        } else if (res.data.status === undefined) {
            if (isMsg) {
                location.href = "/";
            }
            return false;
        } else {
            if (isMsg) {
                var msg = res.data.status + ' : ' + res.data.message;
                $scope._popMsg(msg);
            }
            return false;
        }
    }

    // itemFormatter 기본설정
    $scope._itemFormatter = function (panel, r, c, cell) {
        _itemFormatter(panel, r, c, cell);
    };

    // itemFormatter 기본설정 : private
    function _itemFormatter(panel, r, c, cell) {
        // 컬럼헤더 merged 의 헤더타이틀 중앙(vertical) 정렬
        if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
            var mRange = panel.grid.getMergedRange(panel, r, c);
            if (mRange) {
                cell.innerHTML = '<div class=\"wj-header merged-custom\">' + cell.innerHTML + '</div>';
            }
            // 헤더의 전체선택 클릭 로직
            var flex = panel.grid;
            var column = flex.columns[c];
            // check that this is a boolean column
            if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
                // prevent sorting on click
                column.allowSorting = false;
                // count true values to initialize checkbox
                var cnt = 0;
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
                var cb = cell.firstChild;
                cb.checked = cnt > 0;
                cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
                // apply checkbox value to cells
                cb.addEventListener('click', function (e) {
                    flex.beginUpdate();
                    for (var i = 0; i < flex.rows.length; i++) {
                        var cell = flex.cells.getCellElement(i, c);
                        // TODO : 활성화 및 readOnly 아닌 경우에만 체크되도록
                        // if (!cell.children[0].disabled) {
                        flex.setCellData(i, c, cb.checked);
                        // }
                    }
                    flex.endUpdate();
                });
            }
            // picker 를 위한 설정
        } else if (panel.cellType === wijmo.grid.CellType.TopLeft) {
            if (!isPicker) {
                $(cell).css({"background": "none", "background-color": "#e8e8e8"});
            }
            // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
        } else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
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
            // readOnly 배경색 표시
        } else if (panel.cellType === wijmo.grid.CellType.Cell) {
            var col = panel.columns[c];
            if (col.isReadOnly) {
                wijmo.addClass(cell, 'wj-custom-readonly');
            }
        }
    }

    // 에디팅 관련 기본설정
    $scope.$watch('flex', function () {
        var flex = $scope.flex;
        if (flex) {
            flex.beginningEdit.addHandler(function (s, e) {
                if (s.columns[e.col].binding !== 'gChk') {
                    // 선택(gChk) 되어있지 않으면 수정 불가 : 20181004 노현수 > 주석처리 일단 사용안함
                    // if (!s.rows[e.row].dataItem.gChk) {
                    //   e.cancel = true;
                    //   flex.endUpdate();
                    // } else {
                    if (s.columns[e.col].dataType !== wijmo.DataType.Boolean) {
                        setTimeout(function () {
                            var _cellData = s.getCellData(e.row, e.col, true);
                            if (!isEmpty(s.activeEditor) && s.activeEditor.value !== '') {
                                wijmo.setSelectionRange(s.activeEditor, _cellData.length); // caret position
                            }
                        }, 0);
                    }
                    // }
                }
            });
            flex.cellEditEnded.addHandler(function (s, e) {
                // console.log('Cell Edit End');
            });
            flex.rowEditEnded.addHandler(function (s, e) {
                // console.log('Row edit end');
            });
        }
    });
    // columnPicker 생성
    $scope._makePickColumns = function (ctrlName) {
        _makePickColumns(ctrlName);
    };

    // columnPicker 생성 : private
    function _makePickColumns(ctrlName) {
        var flex = $scope.flex;
        if (flex && isPicker) {
            flex.hostElement.addEventListener('mousedown', function (e) {
                var ht = flex.hitTest(e);
                if (ht.cellType === wijmo.grid.CellType.TopLeft) {
                    // create column picker (once)
                    if (!$scope.picker) {
                        $scope.picker = new wijmo.grid.ColumnPicker('#' + ctrlName);
                        $scope.picker.orgColumns = $scope.flex.columns;
                    }
                    // show column picker in a dialog
                    $scope.picker.grid = $scope.flex;
                    var pickerPopup = $scope.colPicker;
                    pickerPopup.show(true, function (s) {
                        var dr = s.dialogResult;
                        if (dr && dr.indexOf('apply') > -1) {
                            $scope.picker.save();
                        }
                    });
                    e.preventDefault();
                }
            });
        }
    }

    // 로딩 메시지 팝업 열기
    $scope.$on('loadingPopupActive', function (event, data) {
        // 팝업내용 동적 생성
        var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">';
        if (isEmpty(data)) {
            innerHtml += messages['cmm.loading'];
        } else {
            innerHtml += data;
        }
        innerHtml += '</p><p class=\"mt20\"><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
        // html 적용
        $scope._loadingPopup.content.innerHTML = innerHtml;
        // 팝업 show
        $scope._loadingPopup.show(true);
    });
    // 로딩 메시지 팝업 닫기
    $scope.$on('loadingPopupInactive', function () {
        $scope._loadingPopup.hide();
    });
    // 조회관련 공통로직
    $scope._postJSONQuery = {
        withPopUp: function () {
            return _postJSON(arguments, true, 'loading');
        },
        withOutPopUp: function () {
            return _postJSON(arguments, false, 'loading');
        }
    };
    // 저장관련 공통로직
    $scope._postJSONSave = {
        withPopUp: function () {
            return _postJSON(arguments, true, 'saving');
        },
        withOutPopUp: function () {
            return _postJSON(arguments, false, 'saving');
        }
    };

    // 조회/저장관련 공통로직 : private
    function _postJSON(args, isMsg, type) {
        var popMsg = type === 'loading' ? messages['cmm.loading'] : messages['cmm.saving'];
        var url, params, success, error, complete;
        switch (args.length) {
            case 3:
                url = args[0];
                params = args[1];
                success = args[2];
                break;
            case 4:
                url = args[0];
                params = args[1];
                success = args[2];
                complete = args[3];
                break;
            case 5:
                url = args[0];
                params = args[1];
                success = args[2];
                error = args[3];
                complete = args[4];
                break;
        }
        var data = {};
        var sParams = {};
        // data 존재시
        if (params.data && params.params) {
            data = params.data;
            sParams = params.params;
        } else {
            // 둘중하나만 있으면 오류
            if (params.data || params.params) {
                $scope._popMsg('파라미터가 올바르지 않습니다.');
                return false;
                // 둘다 없으면 기존대로 설정
            } else {
                data = params;
            }
        }
        // 가상로그인시 세션활용
        if (document.getElementsByName('sessionId')[0]) {
            if (type === 'loading') {
                params['sid'] = document.getElementsByName('sessionId')[0].value;
            } else if (type === 'saving') {
                // 저장시에는 sid를 request 에 실어보내기 위해 추가 생성
                sParams['sid'] = document.getElementsByName('sessionId')[0].value;
            }
        }
        // 로딩바 show
        if (isMsg) {
            $scope.$broadcast('loadingPopupActive', popMsg);
        }
        // http 속성
        var property = {
            method: 'POST', //방식
            url: url, /* 통신할 URL */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        };
        if (type === 'loading') {
            property.params = params;
        } else if (type === 'saving') {
            // 저장시에는 sid를 request 에 실어보내기 위해 params 추가 생성
            property.data = data;
            property.params = sParams; /* 파라메터로 보낼 데이터 : request.getParameter */
        }
        // ajax 통신 설정
        $http(property)
            .then(function successCallback(response) {
                if (isMsg) {
                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');
                }
                if (_httpStatusCheck(response, isMsg)) {
                    if (isMsg) {
                        if (response.data.message) {
                            $scope._popMsg(response.data.message);
                        } else {
                            if (type === 'saving') {
                                $scope._popMsg(messages['cmm.saveSucc']);
                            }
                        }
                    }
                    if (typeof success === 'function') {
                        success(response);
                    }
                }
            }, function errorCallback(response) {
                if (isMsg) {
                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    if (response.data.message) {
                        $scope._popMsg(response.data.message);
                    } else {
                        if (type === 'loading') {
                            $scope._popMsg(messages['cmm.error']);
                        } else if (type === 'saving') {
                            $scope._popMsg(messages['cmm.saveFail']);
                        }
                    }
                }
                if (typeof error === 'function') {
                    error(response);
                }
                return false;
            }).then(function () {
            // 'complete' code here
            if (typeof complete === 'function') {
                setTimeout(function () {
                    complete();
                }, 10);
            }
        });
    }

    // 콤보박스 조회 공통로직
    $scope._getComboDataQuery = function (code, comboNm, type) {
        /**
         * '' : 코드값만
         * 'S' : "선택" 포함
         * 'A' : "전체" 포함
         */
        var params = {};
        params.code = code;
        if (type) {
            params.type = type;
        } else {
            params.type = "";
        }

        $scope._postJSONQuery.withOutPopUp("/common/getComboData.sb", params,
            function (response) {
                $scope._setComboData(comboNm, JSON.parse(response.data.data));
            }
        );
    };
    // 콤보박스 커스텀 조회 공통로직
    $scope._getComboDataQueryCustom = function () {
        return _getComboDataQueryCustom(arguments);
    };

    // 조회/저장관련 공통로직 : private
    function _getComboDataQueryCustom(args) {
        var queryId, comboNm, type, params;

        /**
         * '' : 코드값만
         * 'S' : "선택" 포함
         * 'A' : "전체" 포함
         */

        switch (args.length) {
            case 2:
                queryId = args[0];
                comboNm = args[1];
            case 3:
                queryId = args[0];
                comboNm = args[1];
                type = args[2];
                break;
            case 4:
                queryId = args[0];
                comboNm = args[1];
                type = args[2];
                params = args[3];
                break;
        }

        if (!params) {
            params = {};
        }
        params.queryId = queryId;
        if (type) {
            params.type = type;
        } else {
            params.type = "";
        }

        // 콤보박스 조회 공통로직(권한에 따라 각각의 본사/매장별 공통코드 조회 시 사용)
        $scope._getComboDataQueryByAuth = function(code, comboNm, dataMapNm) {

            var params = {};
            params.nmcodeGrpCd = code;

            $scope._postJSONQuery.withOutPopUp("/iostock/cmm/iostockCmm/getOrgnCombo.sb", params, function(response) {

                var list = response.data.data.list;
                var comboArray = [];
                var comboData = {};

                for (var i = 0; i < list.length; i++) {
                    comboData = {};
                    comboData.name = list[i].nmcodeNm;
                    comboData.value = list[i].nmcodeCd;
                    comboArray.push(comboData);
                }

                // 콤보박스에 바인딩 하는 경우
                if(comboNm !== null && comboNm !== undefined && comboNm !== ""){
                    $scope._setComboData(comboNm, comboArray);
                }
                // 리스트 data-map에 바인딩 하는 경우
                if(dataMapNm !== null && dataMapNm !== undefined && dataMapNm !== ""){
                    $scope[dataMapNm] =  new wijmo.grid.DataMap(comboArray, 'value', 'name');
                }
            });
        };

        $scope._postJSONQuery.withOutPopUp("/common/getCustomCombo.sb", params,
            function (response) {
                $scope._setComboData(comboNm, JSON.parse(JSON.stringify(response.data.data)));
            }
        );

    }
}

// 메뉴 트리뷰 생성
function MenuController(ctrlName, menuUrl, $scope, $http) {
    // 파라미터
    $scope.params = {};
    // 가상로그인시 파라미터인 sid 설정
    if (document.getElementsByName('sessionId').length > 0) {
        $scope.params.sid = document.getElementsByName('sessionId')[0].value;
    }
    // 트리 변환
    $scope._convertTreeModel = function (arrayList, rootId) {
        return _convertTreeModel(arrayList, rootId);
    };

    // 트리 변환 : private
    function _convertTreeModel(arrayList, rootId) {
        var rootNodes = [];
        var traverse = function (nodes, item, index) {
            if (nodes instanceof Array) {
                return nodes.some(function (node) {
                    if (node.resrceCd === item.pResrce) {
                        node.children = node.children || [];
                        return node.children.push(arrayList.splice(index, 1)[0]);
                    }

                    return traverse(node.children, item, index);
                });
            }
        };
        while (arrayList.length > 0) {
            arrayList.some(function (item, index) {
                if (item.pResrce === rootId) {
                    return rootNodes.push(arrayList.splice(index, 1)[0]);
                }
                return traverse(rootNodes, item, index);
            });
        }
        return rootNodes;
    }

    // 메뉴목록 조회 및 트리Data Set
    $scope._searchTree = function (menuUrl, callback) {
        _searchTree(menuUrl, callback);
    };

    // 메뉴목록 조회 및 트리Data Set : private
    function _searchTree(menuUrl, callback) {
        $http({
            method: 'POST', //방식
            url: menuUrl, /* 통신할 URL */
            params: $scope.params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if (response.data.status === 'OK') {
                if (response.data.data.length > 0) {
                    var data = JSON.stringify($scope._convertTreeModel(response.data.data, '000000'), null, '');
                    $scope.items = JSON.parse(data);
                } else {
                    $scope.items = [];
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg('메뉴를 불러오는데 실패하였습니다.');
            return false;
        }).then(function () {
            // 'complete' code here
            if (typeof callback === 'function') {
                setTimeout(function () {
                    callback();
                }, 10);
            }
        });
    }

    // 현재선택한 메뉴 가져오기
    $scope._getCurrentMenu = function () {
        return _getCurrentMenu();
    };

    // 현재선택한 메뉴 가져오기 : private
    function _getCurrentMenu() {
        $http({
            method: 'POST', //방식
            url: '/menu/currentMenu.sb', /* 통신할 URL */
            params: $scope.params, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if (response.data.status === 'OK') {
                var data = response.data.data;
                if (data) {
                    $scope._setInitMenu(data.resrceCd);
                } else {
                    $scope._setInitMenu('');
                }
            }
        }, function errorCallback(response) {
            $scope._popMsg('선택된 메뉴를 불러오는데 실패하였습니다.');
            return false;
        });
    }

    // 메뉴목록 조회
    $scope._searchTree(menuUrl);
    // 현재선택한 메뉴 가져오기
    $scope._getCurrentMenu();

    // 트리의 아이템이 load 완료 되었을 때 이벤트
    $scope.loadedItems = function (s, e) {
        _loadedItems(s, e);
    };

    // 트리의 아이템이 load 완료 되었을 때 이벤트 : private
    function _loadedItems(s, e) {
        var node;
        // 아이콘 Class 추가
        for (node = s.getFirstNode(); node; node = node.nextSibling()) {
            if (!isEmpty(node)) {
                wijmo.addClass(node.element, node.dataItem.iconNm);
            }
        }
        s.collapseToLevel(0);

        var initMenu = $scope._getInitMenu();
        // 초기 메뉴(현재 메뉴) 설정
        if (initMenu) {
            for (node = s.getFirstNode(); node; node = node.next()) {
                if (isEmpty(node.nodes)) {
                    if (!isEmpty(node.dataItem) && node.dataItem.resrceCd === initMenu) {
                        s.selectedItem = node.dataItem;
                    }
                }
            }
        }
    }

    // 선택된 메뉴가 변경 되었을 때 이벤트
    $scope.selectedItemChanged = function (s, e) {
        _selectedItemChaged(s, e);
    };

    // 선택된 메뉴가 변경 되었을 때 이벤트 : private
    function _selectedItemChaged(s, e) {
        var node, pNode = $scope._getPNode();
        // 이전 메뉴의 클래스 제거
        if (pNode) {
            for (node = pNode; node; node = node.parentNode) {
                wijmo.removeClass(node.element, "on");
            }
        }
        // 선택된 메뉴에 클래스 추가
        for (node = s.selectedNode; node; node = node.parentNode) {
            wijmo.addClass(node.element, "on");
        }
        $scope._setPNode(s.selectedNode);
    }

    // 아이템 클릭 시 이벤트
    $scope.itemClicked = function (s, e) {
        _itemClicked(s, e);
    };

    // 아이템 클릭 시 이벤트 : private
    function _itemClicked(s, e) {
        // URL 이 있을 경우 페이지 이동
        if (!isEmpty(s.selectedNode.dataItem.url)) {
            // 가상로그인시 파라미터인 SessionID 설정
            if (document.getElementsByName('sessionId').length > 0) {
                var vSessionId = document.getElementsByName('sessionId')[0].value;
                location.href = s.selectedNode.dataItem.url + '?sid=' + vSessionId;
            } else {
                location.href = s.selectedNode.dataItem.url;
            }
        }
        // 같은 메뉴를 다시 선택 했을 때 메뉴 닫기 기능
        if ($scope._getPNode() === s.selectedNode) {
            s.selectedNode.isCollapsed = !s.selectedNode.isCollapsed;
        } else {
            s.selectedNode.isCollapsed = false;
        }
    }
}

!function (win, $) {
    var app = angular.module('rootApp', ['wj', 'ngSanitize', 'ui.bootstrap']);
    // main-controller
    app.controller('rootCtrl', ['$scope', '$http', '$compile', '$sce', 'comboData', 'pagingInfo', 'pNode', 'initMenu',
        function ($scope, $http, $compile, $sce, comboData, pagingInfo, pNode, initMenu) {
            // 페이징바 동적 생성
            $scope.$on('drawPager', function () {
                // 페이징바 갯수
                var page_scale = $scope._getPagingInfo('pageScale');
                var page_end = page_scale === 10 ? 9 : 4;
                // 버튼 태그 동적 생성
                var firstBtnTag = '<li class=\"btn_previous first\"><a href=\"javascript:void(0);\" onclick="return false;" ng-click=\"_pageFirst(\'{ctrlName}\', 0);\"></a></li>';
                var prevBtnTag = '<li class=\"btn_previous\"><a href=\"javascript:void(0);\" onclick="return false;" ng-click=\"_pagePrev($event, \'{ctrlName}\', \'{prev}\');\"></a></li>';
                var pageBtnTag = '<li><a href=\"javascript:void(0);\" onclick="return false;" class=\"{cnm}\" ng-click=\"_pageView(\'{ctrlName}\', \'{i}\');\">{i}</a></li>';
                var nextBtnTag = '<li class=\"btn_next\"><a href=\"javascript:void(0);\" onclick="return false;" ng-click=\"_pageNext($event, \'{ctrlName}\', \'{next}\');\"></a></li>';
                var lastBtnTag = '<li class=\"btn_next last\"><a href=\"javascript:void(0);\" onclick="return false;" ng-click=\"_pageLast(\'{ctrlName}\', \'{totalPage}\');\"></a></li>';
                var pagerTag = '';

                var item = {};
                item.ctrlName = $scope._getPagingInfo('ctrlName');
                item.curr = $scope._getPagingInfo('curr');
                item.totCnt = $scope._getPagingInfo('totCnt');
                item.totalPage = $scope._getPagingInfo('totalPage');
                item.prev = 0;
                item.next = 0;
                item.start = 0;
                item.end = 0;

                // 페이징 계산
                var t = $scope._getPagingInfo('curr') / page_scale;
                if (t.toString().indexOf('.') === -1) {
                    item.end = $scope._getPagingInfo('curr');
                    item.start = item.end - page_end;
                } else {
                    item.start = (parseInt(t) * page_scale) + 1;
                    item.end = item.start + page_end;
                }
                if (item.end > item.totalPage) {
                    item.end = item.totalPage;
                }

                // 페이징 제작

                pagerTag += wijmo.format(firstBtnTag, item);

                if (item.curr > page_scale) {
                    item.prev = item.start - 1;
                    pagerTag += wijmo.format(prevBtnTag, item);
                }
                for (var i = item.start; i <= item.end; i++) {
                    item.i = i;
                    item.cnm = i === item.curr ? 'on pagenav' : 'pagenav';
                    pagerTag += wijmo.format(pageBtnTag, item);
                }
                if (item.end < item.totalPage) {
                    item.next = item.end + 1;
                    pagerTag += wijmo.format(nextBtnTag, item);
                }

                pagerTag += wijmo.format(lastBtnTag, item);

                var pager = $compile(pagerTag)($scope);
                var pagerName = item.ctrlName + 'Pager';
                angular.element(document.getElementById(pagerName)).children().remove();
                angular.element(document.getElementById(pagerName)).append(pager);
            });
            // 조회
            $scope._broadcast = function (ctrlName, params) {
                $scope.$broadcast('init');
                $scope.$broadcast(ctrlName, params);
            };
            // 페이지 선택
            $scope._pageView = function (ctrlName, curr) {
                _pageView(ctrlName, curr);
            };
            // 이전 페이지
            $scope._pagePrev = function (event, ctrlName, curr) {
                event.stopPropagation();
                _pageView(ctrlName, curr);
            };
            // 다음 페이지
            $scope._pageNext = function (event, ctrlName, curr) {
                event.stopPropagation();
                _pageView(ctrlName, curr);
            };
            // 처음 페이지
            $scope._pageFirst = function (ctrlName, curr) {
                _pageView(ctrlName, curr);
            };
            // 끝 페이지
            $scope._pageLast = function (ctrlName, curr) {
                _pageView(ctrlName, curr);
            };

            // 페이지 이동
            function _pageView(ctrlName, curr) {
                $scope._setPagingInfo('curr', curr);
                $scope.$broadcast(ctrlName);
            }

            // 메시지 팝업
            $scope._popMsg = function (msg, callback) {
                var popUpMsg = function () {
                    $scope.s_alert_msg = $sce.trustAsHtml(msg);
                    setTimeout(function () {
                        $scope._alertPopup.show(true, function () {
                            if (typeof callback === 'function') {
                                setTimeout(function () {
                                    callback();
                                }, 50);
                            }
                        });
                    }, 100);
                };
                if ($scope.$$phase === '$apply' || $scope.$$phase === '$digest') {
                    popUpMsg();
                } else {
                    $scope.$apply(function () {
                        popUpMsg();
                    });
                }
            };
            // 메시지 컨펌
            $scope._popConfirm = function (msg, callback) {
                var popUpConfirm = function () {
                    $scope.s_confirm_msg = $sce.trustAsHtml(msg);
                    setTimeout(function () {
                        $scope._alertConfirm.show(true, function (e) {
                            if (e.dialogResult === "wj-hide-apply") {
                                if (typeof callback === 'function') {
                                    setTimeout(function () {
                                        callback();
                                    }, 50);
                                }
                            }
                        });
                    }, 100);
                };
                if ($scope.$$phase === '$apply' || $scope.$$phase === '$digest') {
                    popUpConfirm();
                } else {
                    $scope.$apply(function () {
                        popUpConfirm();
                    });
                }
            };
            // 콤보박스 초기화 > ng-model 사용하기 위한 설정 : 20180831 노현수
            $scope._initComboBox = function (s) {
                s._tbx.id = s._orgAtts.id.value;
                s._tbx.setAttribute("ng-model", s._orgAtts['ng-model']);
                s._tbx.attributes['ng-model'].value = s._orgAtts['ng-model'].value;
            };
            // 날짜입력박스 초기화
            $scope._initDateBox = function (s) {
                s.itemFormatter = function (date, element) {
                    var day = date.getDay();
                    if (day === 0) {
                        element.style.color = 'red';
                    } else if (day === 6) {
                        element.style.color = '#1e88e5';
                    }
                };
            };
            // 페이징바 Data Setter
            $scope._setPagingInfo = function (id, data) {
                pagingInfo.set(id, data);
            };
            // 페이징바 Data Getter
            $scope._getPagingInfo = function (id) {
                return pagingInfo.get(id);
            };
            // 콤보박스 Data Setter
            $scope._setComboData = function (id, data) {
                comboData.set(id, data);
            };
            // 콤보박스 Data Getter
            $scope._getComboData = function (id) {
                return comboData.get(id);
            };
            // 콤보박스 listScale 수기 입력시 숫자만 입력 받도록 함
            $scope._checkValidation = function (s) {

                s.text = s.text.replace(/[^0-9]/g, "");

                var str = s.text.split("");
                if (str[0] == "0") {
                    s.text = s.text.replace(/(^0+)/, "");
                }

                if (parseInt(s.text) > 999) {
                    s.text = 999;
                }
            }
            // initMenu Data Setter
            $scope._setInitMenu = function (data) {
                initMenu.set(data);
            };
            // initMenu Data Getter
            $scope._getInitMenu = function () {
                return initMenu.get();
            };
            // pNode Data Setter
            $scope._setPNode = function (data) {
                pNode.set(data);
            };
            // pNode Data Getter
            $scope._getPNode = function () {
                return pNode.get();
            };
        }]);
    app.factory('comboData', function () {
        var comboDataMap = [];
        comboDataMap.set = function (id, data) {
            comboDataMap[id] = data;
        };
        comboDataMap.get = function (id) {
            return comboDataMap[id];
        };
        return comboDataMap;
    }).factory('pagingData', function () {
        var currentPage = {};
        currentPage.set = function (idx) {
            currentPage.value = idx;
        };
        currentPage.get = function () {
            return currentPage.value;
        };
        return currentPage;
    }).factory('pagingInfo', function () {
        var pagingInfo = [];
        pagingInfo.set = function (id, data) {
            pagingInfo[id] = data;
        };
        pagingInfo.get = function (id) {
            return pagingInfo[id];
        };
        return pagingInfo;
    }).factory('pNode', function () {
        // 상위node Get/Set
        var pNode = {};
        pNode.set = function (data) {
            pNode.value = data;
        };
        pNode.get = function () {
            return pNode.value;
        };
        return pNode;
    }).factory('initMenu', function () {
        // 현재메뉴 Get/Set
        var initMenu = {};
        initMenu.set = function (data) {
            initMenu.value = data;
        };
        initMenu.get = function () {
            return initMenu.value;
        };
        return initMenu;
    });
    app.config(function ($httpProvider) {
        // $httpProvider.interceptors.push('myHttpInterceptor');
    });
    // angular Grid 생성
    var agrid = {
        getApp: function () {
            return app;
        },
        getScope: function (ctrlName) {
            var sel = 'div[ng-controller="' + ctrlName + '"]';
            return angular.element(sel).scope();
        }
    };

    win.agrid = agrid;

}('undefined' !== typeof window ? window : this, angular);
