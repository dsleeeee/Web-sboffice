/****************************************************************
 *
 * 파일명 : alimtalkTemplateRegister.js
 * 설  명 : 알림톡 템플릿등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.06.13     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 전송유형
var registerSendTypeCdComboData = [
    {"name":"선택","value":""}
];
// 계정/그룹
var registerTemplateGrpFgComboData = [
    {"name":"그룹","value":"1"}
];
// 메세지유형
var registerTemplateMsgTypeComboData = [
    {"name":"기본형","value":"BA"},
    {"name":"부가정보형","value":"EX"},
    {"name":"채널추가형","value":"AD"},
    {"name":"복합형","value":"MI"}
];
// 템플릿 강조 유형
var registerTemplateEmpsizeTypeComboData = [
    {"name":"선택안함","value":"NONE"},
    {"name":"TEXT","value":"TEXT"},
    {"name":"이미지형","value":"IMAGE"}
];
// 보안 템플릿 여부
var registerSecurityFgComboData = [
    {"name":"미설정","value":"false"},
    {"name":"설정","value":"true"}
];
// 템플릿 카테고리(대분류)
var registerTemplateClsCdLComboData = [
    {"name":"대분류","value":""}
];
// 템플릿 카테고리(중분류)
var registerTemplateClsCdMComboData = [
    {"name":"중분류","value":""}
];
// 버튼 타입
var registerButtonsTypeData = [
    {"name":"배송조회","value":"DS"},
    {"name":"웹링크","value":"WL"},
    {"name":"앱링크","value":"AL"},
    {"name":"봇키워드","value":"BK"},
    {"name":"메세지전달","value":"MD"},
    {"name":"상담톡전환","value":"BC"},
    {"name":"봇전환","value":"BT"},
    {"name":"채널 추가","value":"AC"}
];

/**
 *  템플릿등록 조회 그리드 생성
 */
app.controller('alimtalkTemplateRegisterCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkTemplateRegisterCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("registerSendTypeCdCombo", registerSendTypeCdComboData); // 전송유형
    $scope._setComboData("registerSendTypeDtlCdCombo", registerSendTypeCdComboData); // 전송유형상세
    $scope._setComboData("registerTemplateGrpFgCombo", registerTemplateGrpFgComboData); // 계정/그룹
    $scope._setComboData("registerGroupKeyCombo", registerSendTypeCdComboData); // 계정
    $scope._setComboData("registerTemplateMsgTypeCombo", registerTemplateMsgTypeComboData); // 메세지유형
    $scope._setComboData("registerTemplateEmpsizeTypeCombo", registerTemplateEmpsizeTypeComboData); // 템플릿 강조 유형
    $scope._setComboData("registerSecurityFgCombo", registerSecurityFgComboData); // 보안 템플릿 여부
    $scope._setComboData("registerTemplateClsCdLCombo", registerTemplateClsCdLComboData); // 템플릿 카테고리(대분류)
    $scope._setComboData("registerTemplateClsCdMCombo", registerTemplateClsCdMComboData); // 템플릿 카테고리(중분류)

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.buttonsTypeDataMap = new wijmo.grid.DataMap(registerButtonsTypeData, 'value', 'name'); // 버튼 타입

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;

                // 삭제
                if (col.binding === "del") {
                    if (item[("templateAdButtons")] === 'N') {
                        // 값이 있으면 링크 효과
                        wijmo.addClass(e.cell, 'wijLink');
                    }
                }

                // 값이 있으면 링크 효과
                // 메세지유형 - 채널추가형시 추가되는 버튼
                if(item[("templateAdButtons")] === 'Y') {
                    // 버튼 타입
                    if (col.binding === "buttonsType") {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);
                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                        s.rows[e.row].dataItem.buttonsType = "AC";
                    // 버튼 이름
                    } else if (col.binding === "buttonsName") {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);
                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                        s.rows[e.row].dataItem.buttonsName = "채널 추가";
                    // 버튼 링크 MOBILE
                    } else if (col.binding === "buttonsLinkMo") {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);
                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                        s.rows[e.row].dataItem.buttonsLinkMo = "";
                    // 버튼 링크 PC
                    } else if (col.binding === "buttonsLinkPc") {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);
                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                        s.rows[e.row].dataItem.buttonsLinkPc = "";
                    // 버튼 링크 IOS
                    } else if (col.binding === "buttonsLinkLos") {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);
                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                        s.rows[e.row].dataItem.buttonsLinkLos = "";
                    // 버튼 링크 ANDROID
                    } else if (col.binding === "buttonsLinkAndroid") {
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);
                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                        s.rows[e.row].dataItem.buttonsLinkAndroid = "";
                    }
                // 추가
                } else {
                    // 웹링크
                    if (item[("buttonsType")] === 'WL') {
                        // 버튼 링크 MOBILE
                        if (col.binding === "buttonsLinkMo") {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                            s.rows[e.row].dataItem.buttonsLinkMo = "";
                        // 버튼 링크 PC
                        } else if (col.binding === "buttonsLinkPc") {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                            s.rows[e.row].dataItem.buttonsLinkPc = "";
                        }
                    // 채널 추가
                    } else if (item[("buttonsType")] === 'AC') {
                        // 버튼 이름
                        if (col.binding === "buttonsName") {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                            s.rows[e.row].dataItem.buttonsName = "채널 추가";
                        // 버튼 링크 MOBILE
                        } else if (col.binding === "buttonsLinkMo") {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                            s.rows[e.row].dataItem.buttonsLinkMo = "";
                        // 버튼 링크 PC
                        } else if (col.binding === "buttonsLinkPc") {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                            s.rows[e.row].dataItem.buttonsLinkPc = "";
                        // 버튼 링크 IOS
                        } else if (col.binding === "buttonsLinkLos") {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                            s.rows[e.row].dataItem.buttonsLinkLos = "";
                        // 버튼 링크 ANDROID
                        } else if (col.binding === "buttonsLinkAndroid") {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                            s.rows[e.row].dataItem.buttonsLinkAndroid = "";
                        }
                    } else {
                        // 버튼 링크 MOBILE
                        if (col.binding === "buttonsLinkMo") {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                            s.rows[e.row].dataItem.buttonsLinkMo = "";
                        // 버튼 링크 PC
                        } else if (col.binding === "buttonsLinkPc") {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                            s.rows[e.row].dataItem.buttonsLinkPc = "";
                        // 버튼 링크 IOS
                        } else if (col.binding === "buttonsLinkLos") {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                            s.rows[e.row].dataItem.buttonsLinkLos = "";
                        // 버튼 링크 ANDROID
                        } else if (col.binding === "buttonsLinkAndroid") {
                            wijmo.addClass(e.cell, 'wijLink');
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);
                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                            s.rows[e.row].dataItem.buttonsLinkAndroid = "";
                        }
                    }
                }

                // 템플릿 양식 그리기
                // 버튼 입력시
                $scope.keyInButtons();
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                var selectedRow = s.rows[ht.row].dataItem;

                // 삭제 클릭시 상세정보 조회
                if ( col.binding === "del") {
                    if (selectedRow[("templateAdButtons")] === 'N') {
                        // 그리드 행 삭제
                        $scope.flex.collectionView.removeAt(ht.row);
                    }
                }
            }
        });

        // <-- 그리드 헤더2줄 -->
        // 헤더머지
        s.allowMerging = 2;
        s.columnHeaders.rows.push(new wijmo.grid.Row());

        // 첫째줄 헤더 생성
        var dataItem = {};
        dataItem.buttonsType = messages["alimtalkTemplate.buttonsType"];
        dataItem.buttonsName = messages["alimtalkTemplate.buttonsName"];
        dataItem.buttonsLinkMo = messages["alimtalkTemplate.buttonsLink"];
        dataItem.buttonsLinkPc = messages["alimtalkTemplate.buttonsLink"];
        dataItem.buttonsLinkLos = messages["alimtalkTemplate.buttonsLink"];
        dataItem.buttonsLinkAndroid = messages["alimtalkTemplate.buttonsLink"];
        dataItem.del = messages["cmm.del"];

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
        // <-- //그리드 헤더2줄 -->
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkTemplateRegisterCtrl", function(event, data) {
        $scope.searchAlimtalkTemplateRegister();
        event.preventDefault();
    });

    $scope.searchAlimtalkTemplateRegister = function() {
        // 전송유형 조회
        $scope.registerSendTypeCdComboList();

        // 템플릿 카테고리(대분류)
        $scope.registerTemplateClsCdLComboList();
    };
    // <-- //검색 호출 -->

    // <-- 전송유형 콤보박스 -->
    // 전송유형 조회
    $scope.registerSendTypeCdComboList = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp('/adi/alimtalk/alimtalkTemplate/alimtalkTemplate/getSendTypeCdComboList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var registerSendTypeCdComboList = response.data.data.list;
                $scope._setComboData("registerSendTypeCdCombo", registerSendTypeCdComboList); // 전송유형

            } else {
                $scope._setComboData("registerSendTypeCdCombo", registerSendTypeCdComboData); // 전송유형
            }
        });
    };

    // 전송유형 선택시 전송유형상세 조회
    $scope.registerSendTypeCdComboChange = function(s) {
        if(s.selectedValue != "") {
            var params = {};
            params.sendTypeCd = s.selectedValue;

            $scope._postJSONQuery.withOutPopUp('/adi/alimtalk/alimtalkTemplate/alimtalkTemplate/getSendTypeDtlCdComboList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var registerSendTypeDtlCdComboList = response.data.data.list;
                    $scope._setComboData("registerSendTypeDtlCdCombo", registerSendTypeDtlCdComboList); // 전송유형상세

                } else {
                    $scope._setComboData("registerSendTypeDtlCdCombo", registerSendTypeCdComboData); // 전송유형상세
                }
            });

        } else {
            $scope._setComboData("registerSendTypeDtlCdCombo", registerSendTypeCdComboData); // 전송유형상세
        }

        // #{변수} 조회
        $("#trTemplateParams").css("display", "none");
        var storeScope = agrid.getScope('alimtalkTemplateParamsCtrl');
        storeScope._gridDataInit();
        storeScope._broadcast('alimtalkTemplateParamsCtrl', null);
    };
    // <-- //전송유형 콤보박스 -->

    // <-- 계정/그룹 콤보박스 선택시 -->
    // 계정 조회
    $scope.registerTemplateGrpFgComboChange = function(s) {
        if(s.selectedValue != "") {
            var params = {};
            params.templateGrpFg = s.selectedValue;

            $scope._postJSONQuery.withOutPopUp('/adi/alimtalk/alimtalkTemplate/alimtalkTemplateRegister/getGroupKeyComboList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var registerGroupKeyComboList = response.data.data.list;
                    $scope._setComboData("registerGroupKeyCombo", registerGroupKeyComboList); // 계정

                } else {
                    $scope._setComboData("registerGroupKeyCombo", registerSendTypeCdComboData); // 계정
                }
            });

        } else {
            $scope._setComboData("registerGroupKeyCombo", registerSendTypeCdComboData); // 계정
        }
    };
    // <-- //계정/그룹 콤보박스 선택시 -->

    // <-- 메세지유형 콤보박스 선택시 -->
    $scope.registerTemplateMsgTypeComboChange = function(s) {
        // 기본형
        if(s.selectedValue == "BA") {
            $("#trTemplateExtra").css("display", "none");
            $("#trTemplateAd").css("display", "none");
            // 버튼 - 그리드 행 삭제
            $scope.delRowTemplateAdButtons();
            // 템플릿 양식 그리기
            $("#trTemplateContentForm2").css("display", "none");
            $("#trTemplateContentForm3").css("display", "none");

        // 부가정보형
        } else if(s.selectedValue == "EX") {
            $("#trTemplateExtra").css("display", "");
            $("#trTemplateAd").css("display", "none");
            // 버튼 - 그리드 행 삭제
            $scope.delRowTemplateAdButtons();
            // 템플릿 양식 그리기
            $("#trTemplateContentForm2").css("display", "");
            $("#trTemplateContentForm3").css("display", "none");

        // 채널추가형
        } else if(s.selectedValue == "AD") {
            $("#trTemplateExtra").css("display", "none");
            $("#trTemplateAd").css("display", "");
            // 버튼 - 그리드 행 추가
            $scope.addRowTemplateAdButtons();
            // 템플릿 양식 그리기
            $("#trTemplateContentForm2").css("display", "none");
            $("#trTemplateContentForm3").css("display", "");

        // 복합형
        } else if(s.selectedValue == "MI") {
            $("#trTemplateExtra").css("display", "");
            $("#trTemplateAd").css("display", "");
            // 버튼 - 그리드 행 추가
            $scope.addRowTemplateAdButtons();
            // 템플릿 양식 그리기
            $("#trTemplateContentForm2").css("display", "");
            $("#trTemplateContentForm3").css("display", "");
        }
    };
    // <-- //메세지유형 콤보박스 선택시 -->

    // <-- 템플릿 강조 유형 콤보박스 선택시 -->
    $scope.registerTemplateEmpsizeTypeComboChange = function(s) {
        // 선택안함
        if(s.selectedValue == "NONE") {
            $("#trTemplateTitle").css("display", "none");
            $("#trTemplateSubtitle").css("display", "none");
            $("#trTemplateImgNm").css("display", "none");
            // 템플릿 양식 그리기
            $("#trTemplateContentForm4").css("display", "none");
            $("#trTemplateContentForm5").css("display", "none");

        // TEXT
        } else if(s.selectedValue == "TEXT") {
            $("#trTemplateTitle").css("display", "");
            $("#trTemplateSubtitle").css("display", "");
            $("#trTemplateImgNm").css("display", "none");
            // 템플릿 양식 그리기
            $("#trTemplateContentForm4").css("display", "");
            $("#trTemplateContentForm5").css("display", "");

        // 이미지형
        } else if(s.selectedValue == "IMAGE") {
            $("#trTemplateTitle").css("display", "none");
            $("#trTemplateSubtitle").css("display", "none");
            $("#trTemplateImgNm").css("display", "");
            // 템플릿 양식 그리기
            $("#trTemplateContentForm4").css("display", "none");
            $("#trTemplateContentForm5").css("display", "none");
        }
    };
    // <-- //템플릿 강조 유형 콤보박스 선택시 -->

    // <-- 템플릿 카테고리 콤보박스 -->
    // 템플릿 카테고리(대분류) 조회
    $scope.registerTemplateClsCdLComboList = function() {
        var params = {};

        $scope._postJSONQuery.withOutPopUp('/adi/alimtalk/alimtalkTemplate/alimtalkTemplateRegister/getTemplateClsCdLComboList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var registerTemplateClsCdLComboList = response.data.data.list;
                $scope._setComboData("registerTemplateClsCdLCombo", registerTemplateClsCdLComboList); // 템플릿 카테고리(대분류)
                registerTemplateClsCdLComboList.unshift({name: "대분류", value: ""});

            } else {
                $scope._setComboData("registerTemplateClsCdLCombo", registerTemplateClsCdLComboData); // 템플릿 카테고리(대분류)
            }
        });
    };

    // 템플릿 카테고리(대분류) 선택시 템플릿 카테고리(중분류) 조회
    $scope.registerTemplateClsCdLComboChange = function(s) {
        if(s.selectedValue != "") {
            var params = {};
            params.templateClsCd = s.selectedValue;

            $scope._postJSONQuery.withOutPopUp('/adi/alimtalk/alimtalkTemplate/alimtalkTemplateRegister/getTemplateClsCdMComboList.sb', params, function (response) {
                if (response.data.data.list.length > 0) {
                    var registerTemplateClsCdMComboList = response.data.data.list;
                    $scope._setComboData("registerTemplateClsCdMCombo", registerTemplateClsCdMComboList); // 템플릿 카테고리(중분류)
                    registerTemplateClsCdMComboList.unshift({name: "중분류", value: ""});

                } else {
                    $scope._setComboData("registerTemplateClsCdMCombo", registerTemplateClsCdMComboData); // 템플릿 카테고리(중분류)
                }
            });

        } else {
            $scope._setComboData("registerTemplateClsCdMCombo", registerTemplateClsCdMComboData); // 템플릿 카테고리(중분류)
        }
    };
    // <-- //템플릿 카테고리 콤보박스 -->

    // #{변수} 조회
    $scope.templateParams = function(){
        if ($scope.registerSendTypeCdCombo !== "") {
            $("#trTemplateParams").css("display", "");
            var params = {};
            params.sendTypeCd = $scope.registerSendTypeCdCombo;
            var storeScope = agrid.getScope('alimtalkTemplateParamsCtrl');
            storeScope.searchAlimtalkTemplateParams(params);

            // #{변수} 조회
            storeScope.searchAlimtalkTemplateParamsList();

        } else {
            $("#trTemplateParams").css("display", "none");
            var storeScope = agrid.getScope('alimtalkTemplateParamsCtrl');
            storeScope._gridDataInit();
            storeScope._broadcast('alimtalkTemplateParamsCtrl', null);

            // #{변수} 조회
            storeScope.searchAlimtalkTemplateParamsList();
        }
    };

    // 템플릿내용에 #{변수} 삽입
    $scope.addTemplateParams = function(str) {
        var templateContent = $("#registerTemplateContent").val();
        $("#registerTemplateContent").val(templateContent + str);

        // 템플릿내용 입력시
        $scope.keyInTemplateContent();
    };

    // <-- 버튼 -->
    // <-- 그리드 행 추가 -->
    $scope.addRow = function() {
        if($scope.flex.rows.length > 0) {
            if($scope.flex.collectionView.items.length >= 5) {
                $scope._popMsg(messages["alimtalkTemplateRegister.buttonsAddAlert"]); // 버튼은 최대 5개까지 추가할 수 있습니다.
                return false;
            }
        }

        // 파라미터 설정
        var params = {};
        params.buttonsType = "DS";
        params.buttonsName = "배송조회";
        params.buttonsLinkMo = "";
        params.buttonsLinkPc = "";
        params.buttonsLinkLos = "";
        params.buttonsLinkAndroid = "";
        params.del = "삭제";
        params.templateAdButtons = 'N';

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };
    // <-- //그리드 행 추가 -->

    // 버튼 - 그리드 행 추가
    $scope.addRowTemplateAdButtons = function() {
        if($scope.flex.rows.length > 0) {
            if($scope.flex.collectionView.items.length >= 5) {
                // 그리드 행 삭제
                $scope.flex.collectionView.removeAt($scope.flex.collectionView.items.length-1);
                $scope._popMsg(messages["alimtalkTemplateRegister.buttonsAddAlert"]); // 버튼은 최대 5개까지 추가할 수 있습니다.
            } else {
                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    if($scope.flex.collectionView.items[i].templateAdButtons == "Y") {
                        return false;
                    }
                }
            }
        }

        // 파라미터 설정
        var params = {};
        params.buttonsType = "AC";
        params.buttonsName = "채널 추가";
        params.buttonsLinkMo = "";
        params.buttonsLinkPc = "";
        params.buttonsLinkLos = "";
        params.buttonsLinkAndroid = "";
        params.del = "";
        params.templateAdButtons = 'Y';

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };

    // 버튼 - 그리드 행 삭제
    $scope.delRowTemplateAdButtons = function() {
        if($scope.flex.rows.length > 0) {
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].templateAdButtons == "Y") {
                    // 그리드 행 삭제
                    $scope.flex.collectionView.removeAt(i);
                }
            }
        }
    };
    // <-- //버튼 -->

    // <-- 이미지 -->
    // 이미지 화면에 넣기
    $scope.changeImage = function (value) {
        if(value.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $("#imgAlimtalkImage").attr('src', e.target.result);
            };
            reader.readAsDataURL(value.files[0]);
        }

        $("#trTemplateContentForm11").css("display", "");
    };

    // 이미지 로드시
    $("#imgAlimtalkImage").load(function() {
        // 이미지 비율 2:1
        var fileWidth = this.width; // 이미지 크기
        var fileHeight = this.height; // 이미지 크기
        var fileNaturalWidth = this.naturalWidth; // 원본 이미지 크기
        var fileNaturalHeight = this.naturalHeight; // 원본 이미지 크기
        if(fileNaturalWidth/2 != fileNaturalHeight) {
            // 첨부파일 이미지 초기화
            $scope.clearImage();
            $scope._popMsg(messages["alimtalkTemplateRegister.fileWidthHeighAlert"]); // 가로:세로 비율은 2:1 이어야 합니다.
            return;
        }
    });

    // 첨부파일 이미지 초기화
    $scope.clearImage = function () {
        // 첨부파일 리셋
        var agent = navigator.userAgent.toLowerCase();
        if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
            // ie 일때
            $("#file").replaceWith( $("#file").clone(true) );
        } else {
            // other browser 일때
            $("#file").val("");
        }

        // 이미지 초기화
        $("#imgAlimtalkImage").attr("src", null);
        $("#trTemplateContentForm11").css("display", "none");
    };
    // <-- //이미지 -->

    // 저장
    $("#funcSave").click(function(e){
        alert('개발중 (버튼여러개, 이미지API 미완성)');
        return;




        if ($scope.registerSendTypeCdCombo === "") {
            $scope._popMsg(messages["alimtalkTemplateRegister.sendTypeCdBlank"]); // 전송유형을 선택해주세요.
            return;
        }

        if ($scope.registerSendTypeDtlCdCombo === "") {
            $scope._popMsg(messages["alimtalkTemplateRegister.sendTypeDtlCdBlank"]); // 전송유형상세를 선택해주세요.
            return;
        }

        if ($scope.registerTemplateGrpFgCombo === "") {
            $scope._popMsg(messages["alimtalkTemplateRegister.templateGrpFgBlank"]); // 계정/그룹 선택해주세요.
            return;
        }

        if ($scope.registerGroupKeyCombo === "") {
            $scope._popMsg(messages["alimtalkTemplateRegister.groupKeyBlank"]); // 계정을 선택해주세요.
            return;
        }

        // 내용
        var templateContent = $("#registerTemplateContent").val();
        if (templateContent === undefined) {
            templateContent = "";
        }
        if (templateContent === "") {
            $scope._popMsg(messages["alimtalkTemplateRegister.templateContentBlank"]); // 템플릿내용을 입력해주세요.
            return;
        } else {
            if(templateContent.length > 1000) {
                $scope._popMsg(messages["alimtalkTemplateRegister.templateContentLengthChk"]); // 템플릿내용이 너무 깁니다.(1,000자 이내)
                return false;
            }
        }

        // 부가정보
        var templateExtra = $("#registerTemplateExtra").val();
        if (templateExtra === undefined) {
            templateExtra = "";
        }
        if ($scope.registerTemplateMsgTypeCombo === "") {
            $scope._popMsg(messages["alimtalkTemplateRegister.templateMsgTypeBlank"]); // 메세지유형을 선택해주세요.
            return;
        } else {
            // 부가정보형
            if ($scope.registerTemplateMsgTypeCombo === "EX") {
                if (templateExtra === "") {
                    $scope._popMsg(messages["alimtalkTemplateRegister.templateExtraEXBlank"]); // 부가정보를 입력해주세요.
                    return;
                } else {
                    if(templateExtra.length > 500) {
                        $scope._popMsg(messages["alimtalkTemplateRegister.templateExtraLengthChk"]); // 부가정보가 너무 깁니다.(500자 이내)
                        return false;
                    }
                }
            // 복합형
            } else if ($scope.registerTemplateMsgTypeCombo === "MI") {
                if (templateExtra === "") {
                    $scope._popMsg(messages["alimtalkTemplateRegister.templateExtraEXBlank"]); // 부가정보를 입력해주세요.
                    return;
                } else {
                    if(templateExtra.length > 500) {
                        $scope._popMsg(messages["alimtalkTemplateRegister.templateExtraLengthChk"]); // 부가정보가 너무 깁니다.(500자 이내)
                        return false;
                    }
                }
            }
        }

        // 템플릿 강조 제목
        var templateTitle = $scope.templateTitle;
        if (templateTitle === undefined) {
            templateTitle = "";
        }
        // 템플릿 강조 부제목
        var templateSubtitle = $scope.templateSubtitle;
        if (templateSubtitle === undefined) {
            templateSubtitle = "";
        }
        // 템플릿 강조 유형
        if ($scope.registerTemplateEmpsizeTypeCombo === "") {
            $scope._popMsg(messages["alimtalkTemplateRegister.templateEmpsizeTypeBlank"]); // 템플릿 강조 유형을 선택해주세요.
            return;
        } else {
            // TEXT
            if($scope.registerTemplateEmpsizeTypeCombo == "TEXT") {
                if (templateTitle === "") {
                    $scope._popMsg(messages["alimtalkTemplateRegister.templateTitleBlank"]); // 템플릿 강조 제목를 입력해주세요.
                    return;
                } else {
                    if(templateTitle.getByteLengthForOracle() > 150) {
                        $scope._popMsg(messages["alimtalkTemplateRegister.templateTitleLengthChk"]); // 템플릿 강조 제목이 너무 깁니다.
                        return false;
                    }
                }
                if (templateSubtitle === "") {
                    $scope._popMsg(messages["alimtalkTemplateRegister.templateSubtitleBlank"]); // 템플릿 강조 부제목를 입력해주세요.
                    return;
                } else {
                    if(templateSubtitle.getByteLengthForOracle() > 150) {
                        $scope._popMsg(messages["alimtalkTemplateRegister.templateSubtitleLengthChk"]); // 템플릿 강조 부제목이 너무 깁니다.
                        return false;
                    }
                }
            // 이미지형
            } else if($scope.registerTemplateEmpsizeTypeCombo == "IMAGE") {
                // 이미지 파일이 있을 때
                if($("#file").val() !== null && $("#file").val() !== undefined && $("#file").val() !== "") {

                    // 이미지명 형식 체크
                    var imgFullNm = $("#file").val().substring($("#file").val().lastIndexOf('\\') + 1);
                    if (1 > imgFullNm.lastIndexOf('.')) {
                        $scope._popMsg(messages["alimtalkTemplateRegister.fileNmChk.msg"]); // 파일명 또는 확장자가 올바르지 않습니다. 다시 확인해주세요.
                        return false;
                    }

                    // 이미지(.png) 확장자 체크
                    var reg = /(.*?)\.(png|PNG)$/;
                    if(! $("#file").val().match(reg)) {
                        $scope._popMsg(messages["alimtalkTemplateRegister.fileExtensionChk.msg"]); // 확장자가 .png .PNG인 파일만 등록가능합니다.
                        return;
                    }

                    // 이미지 크기 체크
                    var maxSize = 500 * 1024; // 500KB
                    var fileSize = $("#file")[0].files[0].size;
                    if (fileSize > maxSize) {
                        $scope._popMsg(messages["alimtalkTemplateRegister.fileSizeChk.msg"]); // 이미지 첨부파일은 500KB 이내로 등록 가능합니다.
                        return false;
                    }
                // 이미지 파일이 없을 때
                } else {
                    $scope._popMsg(messages["alimtalkTemplateRegister.fileBlank"]); // 템플릿 강조 유형이 이미지형 입니다. 이미지를 선택해주세요.
                    return;
                }
            }
        }

        // 채널 추가 안내 메세지
        var templateAd = $scope.registerTemplateAd;
        if (templateAd === undefined) {
            templateAd = "";
        }

        if ($scope.registerSecurityFgCombo === "") {
            $scope._popMsg(messages["alimtalkTemplateRegister.securityFgBlank"]); // 보안 템플릿 여부를 선택해주세요.
            return;
        }

        if ($scope.registerTemplateClsCdLCombo === "") {
            $scope._popMsg(messages["alimtalkTemplateRegister.templateClsCdLBlank"]); // 카테고리(대분류)를 선택해주세요.
            return;
        }

        if ($scope.registerTemplateClsCdMCombo === "") {
            $scope._popMsg(messages["alimtalkTemplateRegister.templateClsCdMBlank"]); // 카테고리(중분류)를 선택해주세요.
            return;
        }

        // 이미지형
        if($scope.registerTemplateEmpsizeTypeCombo == "IMAGE") {
            // 이미지파일 저장
            $scope.templateImageFileSave(templateContent, templateExtra, templateAd, templateTitle, templateSubtitle);

        } else {
            // 템플릿 저장
            $scope.templateSave(templateContent, templateExtra, templateAd, templateTitle, templateSubtitle, '', '', '', '');
        }
    });

    // 이미지파일 저장
    $scope.templateImageFileSave = function(templateContent, templateExtra, templateAd, templateTitle, templateSubtitle){
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

        var formData = new FormData($("#regForm")[0]);
        formData.append("orgnCd", orgnCd);

        var url = '/adi/alimtalk/alimtalkTemplate/alimtalkTemplateRegister/getAlimtalkTemplateImageFileSave.sb';

        $.ajax({
            url: url,
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            // async:false,
            success: function(result) {
                // console.log('save result', result);
                if (result.status === "OK") {
                    // $scope._popMsg("저장되었습니다.");
                    $scope.$broadcast('loadingPopupInactive');

                    var fileNm = result.data;
                    // 템플릿 이미지 등록 API호출
                    $scope.templateImageRequestApi(templateContent, templateExtra, templateAd, templateTitle, templateSubtitle, fileNm);
                }
                else if (result.status === "FAIL") {
                    $scope._popMsg('Ajax Fail By HTTP Request');
                    $scope.$broadcast('loadingPopupInactive');
                }
                else if (result.status === "SERVER_ERROR") {
                    $scope._popMsg(result.message);
                    $scope.$broadcast('loadingPopupInactive');
                }
                /*else if(result.status === undefined) {
                    location.href = "/";
                }*/
                else {
                    var msg = result.status + " : " + result.message;
                    $scope._popMsg(msg);
                    $scope.$broadcast('loadingPopupInactive');
                }
            },
            error : function(result){
                $scope._popMsg("error");
                $scope.$broadcast('loadingPopupInactive');
            }
        },function() {
            $scope._popMsg("Ajax Fail By HTTP Request");
            $scope.$broadcast('loadingPopupInactive');
        });
    };

    // 템플릿 이미지 등록 API호출
    $scope.templateImageRequestApi = function(templateContent, templateExtra, templateAd, templateTitle, templateSubtitle, fileNm) {
        // 로딩바 show
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);

        var params = {};
        params.appKey = appKey;
        params.secretKey = secretKey;
        params.apiUrl = apiUrl;
        params.fileNm = fileNm;

        $.ajax({
            type: "POST",
            url: "/adi/alimtalk/alimtalkTemplate/alimtalkTemplateRegister/getAlimtalkTemplateImageApiSave.sb",
            data: JSON.stringify(params),
            cache: false,
            dataType: "json",
            contentType : 'application/json',
            success: function(result){
                // alert(result.status);
                // alert(result.data);
                // alert(result.data.resultCode);
                // alert(result.data.resultMessage);
                if (result.data.resultCode.toString() === "0") {
                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');

                    // 템플릿 저장
                    $scope.templateSave(templateContent, templateExtra, templateAd, templateTitle, templateSubtitle, result.data.fileNm, result.data.filePath, result.data.templateImageName, result.data.templateImageUrl);
                }
                else if (result.data.resultCode.toString() !== "0") {
                    $scope._popMsg(result.data.resultMessage.toString());
                    // 로딩바 hide
                    $scope.$broadcast('loadingPopupInactive');
                }
                // if (result.status === "OK") {
                //     $scope._popMsg("저장되었습니다.");
                //     $scope.close();
                // }
                else if (result.status === "FAIL") {
                    $scope._popMsg('Ajax Fail By HTTP Request');
                    $scope.$broadcast('loadingPopupInactive');
                }
                else if (result.status === "SERVER_ERROR") {
                    $scope._popMsg(result.message);
                    $scope.$broadcast('loadingPopupInactive');
                }
                /*else if(result.status === undefined) {
                    location.href = "/";
                }*/
                else {
                    var msg = result.status + " : " + result.message;
                    $scope._popMsg(msg);
                    $scope.$broadcast('loadingPopupInactive');
                }
            }
        });
    };

    // 템플릿 저장
    $scope.templateSave = function(templateContent, templateExtra, templateAd, templateTitle, templateSubtitle, fileNm, filePath, templateImageName, templateImageUrl){
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            var params = new Array();

            // 버튼 추가시
            if($scope.flex.rows.length > 0) {
                for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                    $scope.flex.collectionView.items[i].sendTypeCd = $scope.registerSendTypeCdCombo;
                    $scope.flex.collectionView.items[i].sendTypeDtlCd = $scope.registerSendTypeDtlCdCombo;
                    $scope.flex.collectionView.items[i].sendTypeDtlNm = $scope.srchRegisterSendTypeDtlCdCombo.text;
                    $scope.flex.collectionView.items[i].templateGrpFg = $scope.registerTemplateGrpFgCombo;
                    $scope.flex.collectionView.items[i].groupKey = $scope.registerGroupKeyCombo;
                    $scope.flex.collectionView.items[i].templateMsgType = $scope.registerTemplateMsgTypeCombo;
                    $scope.flex.collectionView.items[i].templateEmpsizeType = $scope.registerTemplateEmpsizeTypeCombo;
                    $scope.flex.collectionView.items[i].templateContent = templateContent;
                    $scope.flex.collectionView.items[i].templateExtra = templateExtra;
                    $scope.flex.collectionView.items[i].templateAd = templateAd;
                    $scope.flex.collectionView.items[i].templateTitle = templateTitle;
                    $scope.flex.collectionView.items[i].templateSubtitle = templateSubtitle;
                    $scope.flex.collectionView.items[i].filePath = filePath;
                    $scope.flex.collectionView.items[i].fileNm = fileNm;
                    $scope.flex.collectionView.items[i].templateImgNm = templateImageName;
                    $scope.flex.collectionView.items[i].templateImgUrl = templateImageUrl;
                    $scope.flex.collectionView.items[i].securityFg = $scope.registerSecurityFgCombo;
                    $scope.flex.collectionView.items[i].templateClsCd = $scope.registerTemplateClsCdLCombo + $scope.registerTemplateClsCdMCombo;
                    $scope.flex.collectionView.items[i].commonFg = "S";
                    $scope.flex.collectionView.items[i].buttonsOpdering = i+1;

                    // 메세지유형 - 채널추가형시 추가되는 버튼
                    if($scope.flex.collectionView.items[i].templateAdButtons == "Y") {
                        $scope.flex.collectionView.items[i].buttonsType = "AC"; // 버튼 타입
                        $scope.flex.collectionView.items[i].buttonsName = "채널 추가"; // 버튼 이름
                        $scope.flex.collectionView.items[i].buttonsLinkMo = ""; // 버튼 링크 MOBILE
                        $scope.flex.collectionView.items[i].buttonsLinkPc = ""; // 버튼 링크 PC
                        $scope.flex.collectionView.items[i].buttonsLinkLos = ""; // 버튼 링크 IOS
                        $scope.flex.collectionView.items[i].buttonsLinkAndroid = ""; // 버튼 링크 ANDROID
                        // 추가
                    } else {
                        // 웹링크
                        if($scope.flex.collectionView.items[i].buttonsType == "WL") {
                            $scope.flex.collectionView.items[i].buttonsName = $scope.flex.collectionView.items[i].buttonsName; // 버튼 이름
                            $scope.flex.collectionView.items[i].buttonsLinkMo = ""; // 버튼 링크 MOBILE
                            $scope.flex.collectionView.items[i].buttonsLinkPc = ""; // 버튼 링크 PC
                            $scope.flex.collectionView.items[i].buttonsLinkLos = $scope.flex.collectionView.items[i].buttonsLinkLos; // 버튼 링크 IOS
                            $scope.flex.collectionView.items[i].buttonsLinkAndroid = $scope.flex.collectionView.items[i].buttonsLinkAndroid; // 버튼 링크 ANDROID
                        // 앱링크
                        } else if($scope.flex.collectionView.items[i].buttonsType == "AL") {
                            $scope.flex.collectionView.items[i].buttonsName = $scope.flex.collectionView.items[i].buttonsName; // 버튼 이름
                            $scope.flex.collectionView.items[i].buttonsLinkMo = $scope.flex.collectionView.items[i].buttonsLinkMo; // 버튼 링크 MOBILE
                            $scope.flex.collectionView.items[i].buttonsLinkPc = $scope.flex.collectionView.items[i].buttonsLinkPc; // 버튼 링크 PC
                            $scope.flex.collectionView.items[i].buttonsLinkLos = $scope.flex.collectionView.items[i].buttonsLinkLos; // 버튼 링크 IOS
                            $scope.flex.collectionView.items[i].buttonsLinkAndroid = $scope.flex.collectionView.items[i].buttonsLinkAndroid; // 버튼 링크 ANDROID
                        // 채널 추가
                        } else if($scope.flex.collectionView.items[i].buttonsType == "AC") {
                            $scope.flex.collectionView.items[i].buttonsName = "채널 추가"; // 버튼 이름
                            $scope.flex.collectionView.items[i].buttonsLinkMo = ""; // 버튼 링크 MOBILE
                            $scope.flex.collectionView.items[i].buttonsLinkPc = ""; // 버튼 링크 PC
                            $scope.flex.collectionView.items[i].buttonsLinkLos = ""; // 버튼 링크 IOS
                            $scope.flex.collectionView.items[i].buttonsLinkAndroid = ""; // 버튼 링크 ANDROID
                        } else {
                            $scope.flex.collectionView.items[i].buttonsName = $scope.flex.collectionView.items[i].buttonsName; // 버튼 이름
                            $scope.flex.collectionView.items[i].buttonsLinkMo = ""; // 버튼 링크 MOBILE
                            $scope.flex.collectionView.items[i].buttonsLinkPc = ""; // 버튼 링크 PC
                            $scope.flex.collectionView.items[i].buttonsLinkLos = ""; // 버튼 링크 IOS
                            $scope.flex.collectionView.items[i].buttonsLinkAndroid = ""; // 버튼 링크 ANDROID
                        }
                    }
                    params.push($scope.flex.collectionView.items[i]);
                }
            // 버튼 추가X
            } else {
                var paramsX = {};
                paramsX.sendTypeCd = $scope.registerSendTypeCdCombo;
                paramsX.sendTypeDtlCd = $scope.registerSendTypeDtlCdCombo;
                paramsX.sendTypeDtlNm = $scope.srchRegisterSendTypeDtlCdCombo.text;
                paramsX.templateGrpFg = $scope.registerTemplateGrpFgCombo;
                paramsX.groupKey = $scope.registerGroupKeyCombo;
                paramsX.templateMsgType = $scope.registerTemplateMsgTypeCombo;
                paramsX.templateEmpsizeType = $scope.registerTemplateEmpsizeTypeCombo;
                paramsX.templateContent = templateContent;
                paramsX.templateExtra = templateExtra;
                paramsX.templateAd = templateAd;
                paramsX.templateTitle = templateTitle;
                paramsX.templateSubtitle = templateSubtitle;
                paramsX.filePath = filePath;
                paramsX.fileNm = fileNm;
                paramsX.templateImgNm = templateImageName;
                paramsX.templateImgUrl = templateImageUrl;
                paramsX.securityFg = $scope.registerSecurityFgCombo;
                paramsX.templateClsCd = $scope.registerTemplateClsCdLCombo + $scope.registerTemplateClsCdMCombo;
                paramsX.commonFg = "S";
                paramsX.buttonsOpdering = 0;

                paramsX.buttonsType = ""; // 버튼 타입
                paramsX.buttonsName = ""; // 버튼 이름
                paramsX.buttonsLinkMo = ""; // 버튼 링크 MOBILE
                paramsX.buttonsLinkPc = ""; // 버튼 링크 PC
                paramsX.buttonsLinkLos = ""; // 버튼 링크 IOS
                paramsX.buttonsLinkAndroid = ""; // 버튼 링크 ANDROID

                params.push(paramsX);
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            // $scope._postJSONSave.withPopUp("/adi/alimtalk/alimtalkTemplate/alimtalkTemplateRegister/getAlimtalkTemplateRegisterSave.sb", params, function(){ $scope.close() });

            // 저장수행
            $scope._postJSONSave.withPopUp("/adi/alimtalk/alimtalkTemplate/alimtalkTemplateRegister/getAlimtalkTemplateRegisterSave.sb", params, function (response) {
                var result = response.data.data;
                if(result < 1){
                    $scope._popMsg(messages["cmm.registFail"]);
                }else{
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    // 팝업 닫기
                    $scope.close();
                }
            });
        });
    };

    // <-- 템플릿 양식 그리기 -->
    // 템플릿내용 입력시
    $scope.keyInTemplateContent = function() {
        var templateContent = $("#registerTemplateContent").val();
        // #{변수} -> #{변수}예시로 치환
        for(var i=0; i < alimtalkTemplateParamsColList.length; i++) {
            templateContent = templateContent.replaceAll(alimtalkTemplateParamsColList[i].templateParamsCd, alimtalkTemplateParamsColList[i].templateParamsEx);
        }
        $("#templateContentForm1").val(templateContent);
    };

    // 부가정보 입력시
    $scope.keyInTemplateExtra = function() {
        var templateContent = $("#registerTemplateExtra").val();
        $("#templateContentForm2").val(templateContent);
    };

    // 템플릿 강조 제목 입력시
    $scope.keyInTemplateTitle = function() {
        var templateContent = $("#registerTemplateTitle").val();
        $("#templateContentForm4").val(templateContent);
    };

    // 템플릿 강조 부제목 입력시
    $scope.keyInTemplateSubtitle = function() {
        var templateContent = $("#registerTemplateSubtitle").val();
        $("#templateContentForm5").val(templateContent);
    };

    // 버튼 입력시
    $scope.keyInButtons = function() {
        $("#trTemplateContentForm6").css("display", "none");
        $("#trTemplateContentForm7").css("display", "none");
        $("#trTemplateContentForm8").css("display", "none");
        $("#trTemplateContentForm9").css("display", "none");
        $("#trTemplateContentForm10").css("display", "none");

        if($scope.flex.rows.length > 0) {
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var formNum = i + 6;
                $("#trTemplateContentForm" + formNum).css("display", "");
                $("#templateContentForm" + formNum).val($scope.flex.collectionView.items[i].buttonsName);
            }
        }
    };
    // <-- //템플릿 양식 그리기 -->

    // 팝업 닫기
    $scope.close = function(){
        $scope.srchRegisterSendTypeCdCombo.selectedIndex = 0;
        $scope.srchRegisterSendTypeDtlCdCombo.selectedIndex = 0;
        $scope.srchRegisterTemplateGrpFgCombo.selectedIndex = 0;
        $scope.srchRegisterGroupKeyCombo.selectedIndex = 0;
        $scope.srchRegisterTemplateMsgTypeCombo.selectedIndex = 0;
        $scope.srchRegisterTemplateEmpsizeTypeCombo.selectedIndex = 0;
        $("#registerTemplateContent").val("");
        $("#registerTemplateExtra").val("");
        $scope.registerTemplateAd = "";
        $scope.templateTitle = "";
        $scope.templateSubtitle = "";
        $scope.srchRegisterSecurityFgCombo.selectedIndex = 0;
        $scope.srchRegisterTemplateClsCdLCombo.selectedIndex = 0;
        $scope.srchRegisterTemplateClsCdMCombo.selectedIndex = 0;

        var storeScope = agrid.getScope('alimtalkTemplateRegisterCtrl');
        storeScope._gridDataInit();
        storeScope._broadcast('alimtalkTemplateRegisterCtrl', null);

        // 템플릿 양식 그리기
        $("#trTemplateContentForm1").css("display", "");
        $("#trTemplateContentForm2").css("display", "none");
        $("#trTemplateContentForm3").css("display", "none");
        $("#trTemplateContentForm4").css("display", "none");
        $("#trTemplateContentForm5").css("display", "none");
        $("#trTemplateContentForm6").css("display", "none");
        $("#trTemplateContentForm7").css("display", "none");
        $("#trTemplateContentForm8").css("display", "none");
        $("#trTemplateContentForm9").css("display", "none");
        $("#trTemplateContentForm10").css("display", "none");
        $("#templateContentForm1").val("");
        $("#templateContentForm2").val("");
        $("#templateContentForm3").val("");
        $("#templateContentForm4").val("");
        $("#templateContentForm5").val("");
        $("#templateContentForm6").val("");
        $("#templateContentForm7").val("");
        $("#templateContentForm8").val("");
        $("#templateContentForm9").val("");
        $("#templateContentForm10").val("");
        // 첨부파일 이미지 초기화
        $scope.clearImage();

        $scope.wjAlimtalkTemplateRegisterLayer.hide();
    };
}]);


/**
 *  #{변수} 조회 그리드 생성
 */
app.controller('alimtalkTemplateParamsCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('alimtalkTemplateParamsCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // #{변수}명
                if (col.binding === "templateParamsNm") {
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

                // #{변수}명 클릭시 상세정보 조회
                if ( col.binding === "templateParamsNm") {
                    var selectedRow = s.rows[ht.row].dataItem;

                    // 템플릿내용에 #{변수} 삽입
                    var storeScope = agrid.getScope('alimtalkTemplateRegisterCtrl');
                    storeScope.addTemplateParams(selectedRow.templateParamsCd);
                    event.preventDefault();
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("alimtalkTemplateParamsCtrl", function(event, data) {
        $scope.searchAlimtalkTemplateParams(data);
        event.preventDefault();
    });

    $scope.searchAlimtalkTemplateParams = function(data) {
        $scope.setSelectedTemplateParams(data);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedTemplateParams;
    $scope.setSelectedTemplateParams = function(store) {
        $scope.selectedTemplateParams = store;
    };
    $scope.getSelectedTemplateParams = function(){
        return $scope.selectedTemplateParams;
    };

    // #{변수} 조회
    $scope.searchAlimtalkTemplateParamsList = function() {
        var params = {};
        params.sendTypeCd = $scope.selectedTemplateParams.sendTypeCd;

        $scope._inquiryMain("/adi/alimtalk/alimtalkTemplate/alimtalkTemplateRegister/getAlimtalkTemplateParamsList.sb", params, function() {}, false);
    };
}]);