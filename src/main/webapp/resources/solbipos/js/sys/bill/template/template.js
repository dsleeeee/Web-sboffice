/****************************************************************
 *
 * 파일명 : template.js
 * 설  명 : 출력물샘플 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.07.30     노현수      1.0
 *
 * **************************************************************/
$(document).ready(function () {

    // 출력물종류 선택 콤보박스
    var srchPrtTypeList = wcombo.genCommonBoxFun("#srchPrtTypeList", cData, function (e) {
        searchPrintCodeList();
    });
    srchPrtTypeList.inputElement.disabled = true;

    // 템플릿 그리드 Data
    var dataTemplate =
        [
            { binding:"gChk", header:messages["template.chk"], dataType:wijmo.DataType.Boolean, width:45},
            { binding:"templtNm", header:messages["template.templtNm"], width:"*"},
        ];
    // 템플릿 그리드 생성
    var gridTemplate = wgrid.genGrid("#gridTemplate", dataTemplate, menuCd, 1, coulmnLayout1);
    gridTemplate.isReadOnly = false;

    // ReadOnly 효과설정
    gridTemplate.formatItem.addHandler(function(s, e) {
        if (e.panel == s.cells) {
            var col = s.columns[e.col];
            if (col.binding === "templtNm") {
                var item = s.rows[e.row].dataItem;
                if (item.status != "I") {
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                } else {
                    wijmo.removeClass(e.cell, 'wj-custom-readonly');
                }
            }
        }
    });

    // 대표명칭 그리드 선택변경 이벤트
    gridTemplate.selectionChanged.addHandler(function (s, e) {
        var col = s.columns[e.col];
        if (col.binding == "templtNm") {
            var selectedRow = gridTemplate.rows[e.row].dataItem;
            if (selectedRow.prtForm != null) {
                theTarget.value = selectedRow.prtForm;
                makePreview();
            } else {
                theTarget.value = "";
                thePreview.innerHTML = "";
            }
            $("#btnSaveTemplate").show();
        }
    });

    // 그리드 에디팅 방지
    gridTemplate.beginningEdit.addHandler(function (s, e) {
        var dataItem = gridTemplate.rows[e.row].dataItem;
        if ( nvl(dataItem.status, "") == "" && dataItem.status != "I" ) {
            e.cancel = true;
        }
    });

    // 조회버튼 클릭
    $("#btnSearch").click(function (e) {
        searchPrintCodeList();
    });

    // 그리드 조회
    function searchGrid(value) {

        var param = {};
        param.prtClassCd = value;

        $.postJSON("/sys/bill/template/item/list.sb", param,
            function (result) {
                if (result.status === "FAIL") {
                    s_alert.pop(result.message);
                    return;
                }

                var list = result.data.list;
                gridTemplate.itemsSource = new wijmo.collections.CollectionView(list);
                gridTemplate.itemsSource.trackChanges = true;

                // 버튼 Show
                $("#btnAddTemplate").show();
                $("#btnDelTemplate").show();
                $("#btnSaveTemplate").show();

                if (list.length === undefined || list.length == 0) {
                    // 그리드 초기화
                    gridTemplate.itemsSource = [];
                    // 편집/미리보기 폼 초기화
                    theTarget.value = "";
                    thePreview.innerHTML = "";
                } else {
                    gridTemplate.select(0, 1);
                }

            },
            function () {
                s_alert.pop("Ajax Fail");
            }
        );

    }

    // 템플릿 추가버튼 클릭
    $("#btnAddTemplate").click(function (e) {
        gridTemplate.collectionView.trackChanges = true;
        var newRow = gridTemplate.collectionView.addNew();
        newRow.status = "I";
        newRow.prtClassCd = srchPrtTypeList.selectedItem["value"];
        newRow.gChk = true;

        gridTemplate.collectionView.commitNew();
        // 추가된 Row 선택
        gridTemplate.select(gridTemplate.rows.length, 1);
    });

    // 템플릿 삭제버튼 클릭
    $("#btnDelTemplate").click(function (e) {

    });

    // 템플릿 저장버튼 클릭
    $("#btnSaveTemplate").click(function (e) {

        var paramArr = new Array();
        for ( var i = 0; i < gridTemplate.collectionView.itemsEdited.length; i++ ) {
            gridTemplate.collectionView.itemsEdited[i].status = "U";
            paramArr.push(gridTemplate.collectionView.itemsEdited[i]);
        }
        for ( var i = 0; i < gridTemplate.collectionView.itemsAdded.length; i++ ) {
            gridTemplate.collectionView.itemsAdded[i].status = "I";
            paramArr.push(gridTemplate.collectionView.itemsAdded[i]);
        }
        for ( var i = 0; i < gridTemplate.collectionView.itemsRemoved.length; i++ ) {
            gridTemplate.collectionView.itemsRemoved[i].status = "D";
            paramArr.push(gridTemplate.collectionView.itemsRemoved[i]);
        }

        if ( paramArr.length <= 0 ) {
            s_alert.pop(messages["cmm.not.modify"]);
            return;
        }

        $.postJSONArray("/sys/bill/kind/bill/save.sb", paramArr, function(result) {
                s_alert.pop(messages["cmm.saveSucc"]);
                gridTemplate.collectionView.clearChanges();
            },
            function(result) {
                s_alert.pop(result.data.msg);
            }
        );


    });

    // 리스트박스 생성
    var listBox = new wijmo.input.ListBox('#listBoxCode',
        {
            // 보여지는 데이터
            displayMemberPath: 'prtCd',
            // 선택시 데이터
            selectedValuePath: 'prtCd',
            // 드래그 사용하도록 설정
            formatItem: function (s, e) {
                e.item.setAttribute('draggable', 'true');
            }
        });

    // 출력물코드 목록 조회
    function searchPrintCodeList() {

        var param = {};
        param.prtClassCd = srchPrtTypeList.selectedItem["value"];

        $.postJSON("/sys/bill/template/code/list.sb", param,
            function (result) {
                if (result.status === "FAIL") {
                    s_alert.pop(result.message);
                    return;
                }

                var list = result.data.list;
                listBox.itemsSource = list;

                if (list.length === undefined || list.length == 0) {
                    listBox.itemsSource = [];
                } else {
                    searchGrid(srchPrtTypeList.selectedItem["value"]);
                }

            },
            function () {
                s_alert.pop("Ajax Fail");
            }
        );

    };

    // 클릭드래그시 선택이벤트 발생
    listBox.hostElement.addEventListener("mousedown", function (e) {
        listBox.selectedValue = e.target.innerText;
    });

    // 드래그 시작 이벤트
    listBox.hostElement.addEventListener('dragstart', function (e) {

        var mData = {
            prtCd: listBox.selectedValue,
            content: listBox.itemsSource[listBox.selectedIndex].content
        };

        var dragRow = JSON.stringify(mData);
        // 드래그 데이터 set
        e.dataTransfer.setData("text", dragRow);

    }, true);

    // 편집/미리보기 폼 element 할당
    var theTarget = document.getElementById('editTextArea');
    var thePreview = document.getElementById('preview');

    // 드래그시 이벤트 설정
    theTarget.addEventListener('dragover', function (e) {
        // prtCd 값으로 판단하여 copy 모드 설정
        var dragRow = e.dataTransfer.getData("text");
        if (dragRow != null) {
            e.dataTransfer.dropEffect = 'copy';
            e.preventDefault();
        }
    });

    // listBox 아이템 드랍이벤트
    theTarget.addEventListener('drop', function (e) {

        var dragRow = JSON.parse(e.dataTransfer.getData("text"));
        var prtCd = dragRow.prtCd;
        var content = dragRow.content;

        // 출력물코드가 있는 경우에만 작동
        if (prtCd != null) {
            var strOriginal = theTarget.value;
            var iStartPos = theTarget.selectionStart;
            var iEndPos = theTarget.selectionEnd;
            var strFront = "";
            var strEnd = "";
            // textarea 의 커서 위치 구해서 커서위치에 값 넣기
            if (iStartPos == iEndPos) {
                strFront = strOriginal.substring(0, iStartPos);
                strEnd = strOriginal.substring(iStartPos, strOriginal.length);
            } else {
                return;
            }

            theTarget.value = strFront + prtCd + strEnd;
            // 미리보기 적용
            makePreview();

        }
        e.preventDefault();
    });

    // 키이벤트 (키보드수정시 이벤트발생)
    theTarget.addEventListener('keyup', function (e) {
        makePreview();
    })

    // 미리보기 적용
    function makePreview() {

        var value = theTarget.value;
        var codeLen = 0;
        // 리스트박스 데이터 가져옴
        var listData = listBox.itemsSource;
        // {} 코드값 정규식 처리
        var matches = value.match(/\{([^}]+)\}/gm);
        if (matches != null) {
            // 정규식처리된 문자 처리
            for (var k = 0; k < matches.length; k++) {
                for (var l = 0; l < listData.length; l++) {
                    if (listData[l].prtCd == matches[k] && listData[l].content != null) {
                        // 코드값을 listBox 데이터에서 찾아서 예제 문구로 치환
                        value = value.replace(matches[k], listData[l].content);
                    }
                }
            }
        }
        value = value.replace(/\r\n|\n/g, "</P><P>").replace(new RegExp("<P></P>", "g"), "<P>&nbsp;</P>");
        // 특수태그용 정규식 처리
        var exceptMatches = value.match(/\{:([LRC])\d{2}\}.*?\{\/:\1\}?/g);
        if (exceptMatches != null) {
            for (var i = 0; i < exceptMatches.length; i++) {
                // 추출된 특수태그를 정규식에 의해 분리 ( 추출퇸태그/첫문자/바이트길이/내용 )
                var textSplit = exceptMatches[i].match(/\{:([LRC])(\d{2})\}(.*?)\{\/:\1\}/);
                codeLen = parseInt(textSplit[2]);
                if (codeLen <= 42) {
                    // 내용에 태그 길이 측정
                    var tagMatches = textSplit[3].match(/(<([^>]+)>)/gi);
                    var tagLen = 0;
                    if (!isEmpty(tagMatches)) {
                        for (var j = 0; j < tagMatches.length; j++) {
                            tagLen += tagMatches[j].length;
                        }
                    }
                    // 바이트길이 만큼 좌우 여백채우기
                    value = value.replace(textSplit[0], textSplit[3].setPadding(textSplit[1], " ", codeLen + tagLen)) ;
                }
            }
        }
        // 라인별로 글자수 체킹
        var lineArray = ("<P>" + value + "</P>").match(/<P>.*?<\/P>?/g);
        var newValues = new Array();
        var newLine = 0;
        var splitStr = "";
        if (lineArray != null) {
            for (var m = 0; m < lineArray.length; m++ ) {
                lineArray[m] = lineArray[m].replace(/<P>|<\/P>?/g, "");
                if ( lineArray[m].getByteLength() <= 42 || !isEmpty(lineArray[m].match(/<img src.*?>/g)) || !isEmpty(lineArray[m].match(/<font.*?>/g)) ) {
                    newValues[newLine++] = lineArray[m];
                } else {
                    splitStr = lineArray[m].splitByteLen(42);
                    for ( var n = 0; n < splitStr.length; n++ ) {
                        newValues[newLine++] = splitStr[n];
                    }
                }
            }
        }
        thePreview.innerHTML = "<PRE><P>" + newValues.join("</P><P>") + "</P></PRE>";
    }

});
