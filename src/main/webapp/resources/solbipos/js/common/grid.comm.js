//"use strict";
!function (win, $) {
    // 그리드
    var wgrid = {
        /**
         * 파라미터 구분에 따른 Grid 생성 기능
         *    2개 :
         *       파라미터 : div, columns
         *       기능설명 : columnPicker 표시 안함, coulmnLayout 저장 안함.
         *    3개 :
         *       파라미터 : div, columns, picker Y/N
         *       기능설명 : columnPicker 표시 Y/N 처리, columnLayout 저장 안함.
         *    5개 :
         *       파라미터 : div, columns, resrceCd, gridIdx, columnLayout
         *       기능설명 : columnPicker 표시 함, columnLayout 저장 함.
         *
         * @returns {*|FlexGrid}
         */
        genGrid: function () {
            var div, columns, resrceCd, gridIdx, columnLayout = "";
            var isSave, isView = false;
            var arg = arguments;
            switch (arg.length) {
                case 2:
                    div = arg[0];
                    columns = arg[1];
                    break;
                case 3:
                    div = arg[0];
                    columns = arg[1];
                    isView = arg[2];
                    break;
                case 5:
                    div = arg[0];
                    columns = arg[1];
                    resrceCd = arg[2];
                    gridIdx = arg[3];
                    columnLayout = arg[4];
                    isSave = true;
                    break;
            };
            var g = new wijmo.grid.FlexGrid(div, {
                itemsSource: new wijmo.collections.CollectionView(),
                columns: columns,
                isReadOnly: true,
                showSort: true,
                autoGenerateColumns: false,  // 이거 안하면 컬럼이 자동으로 막 생김
                showAlternatingRows: false,
                stickyHeaders: true,
                selectionMode: "Row",
                formatItem: function (s, e) {
                    // 그리드 Column헤더(첫번째)에 ColumnPicker 표시여부
                    if (e.panel == s.topLeftCells) {
                        if (isSave || isView) {
                            e.cell.innerHTML = "<div class=\"v-center\"></div>";
                        } else {
                            $(e.cell).css({"background":"none", "background-color":"#e8e8e8"});
                        }
                    }
                    // 컬럼헤더 merged 의 헤더타이틀 중앙(vertical) 정렬
                    if (e.panel.cellType == wijmo.grid.CellType.ColumnHeader) {
                        var mRange = g.getMergedRange(e.panel, e.row, e.col);
                        if (mRange) {
                            e.cell.innerHTML = "<div class='wj-header merged-custom'>" + e.cell.innerHTML + "</div>";
                        }
                        // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
                    } else if (e.panel.cellType == wijmo.grid.CellType.RowHeader) {
                        if (!isEmpty(e.panel._rows[e.row]._data.rnum)) {
                            e.cell.textContent = (e.panel._rows[e.row]._data.rnum).toString();
                        } else {
                            e.cell.textContent = (e.row + 1).toString();
                        }
                        // cell 속성 readonly 일때 backgrond 컬러 지정
                    } else if (e.panel == s.cells) {
                        var col = s.columns[e.col];
                        if (col.isReadOnly) {
                            e.cell.classList.add("wj-custom-readonly");
                        }
                    }
                },
                draggedColumn: function (s, e) {
                    if (isSave && !isEmpty(resrceCd)) {
                        wgrid.setGridItem(resrceCd, gridIdx, s.columnLayout);
                    }
                }
            });
            // 저장된 레이아웃이 있을 경우 적용
            if ( !isEmpty(columnLayout) ) {
                if (columnLayout.constructor === Object) {
                    var userCols = columnLayout.columns;
                    var isVisibleColumn = function (id) {
                        var visible = true;
                        for (var j = 0; j < userCols.length; j++) {
                            if (id == userCols[j].binding) {
                                visible = (userCols[j].visible == null ? true : userCols[j].visible);
                                break;
                            }
                        }
                        return visible;
                    };
                    for (var i = 0; i < g.columns.length; i++) {
                        g.columns[i].visible = isVisibleColumn(g.columns[i].binding);
                    }
                }
            }
            // gridPicker 사용여부
            if (isView) {
                genGridPicker(g, resrceCd, gridIdx);
            }
            function genGridPicker(grid, resrceCd, gridIdx) {
                // column picker element add
                var item = {};
                item.style = "display:none";
                item.id = grid._e.id + "picker";
                item.cl = "column-picker";
                var html = wijmo.format("<div style=\"{style}\"><div id=\"{id}\" class=\"{cl}\"></div></div>", item);
                $(document.body).append(html); // main div class
                // column picker gen
                wgridPic.genGridPicker("#" + item.id, grid, resrceCd, gridIdx);
            }
            genGridEditingEvent(g);
            // 그리드 에디팅 이벤트시 커서위치 조정 (값의 맨뒤로)
            function genGridEditingEvent(grid) {
                grid.beginningEdit.addHandler(function (s, e) {
                    setTimeout(function () {
                        if (s.columns[e.col].dataType !== wijmo.DataType.Boolean) {
                            var _cellData = g.getCellData(e.row, e.col, true);
                            if (s.activeEditor != null && s.activeEditor.value != "") {
                                wijmo.setSelectionRange(s.activeEditor, _cellData.length); // caret position
                            }
                        }
                    });
                });
            }
            genGridCheckBoxClickEvent(g);
            // 체크박스 클릭시 체크박스 value Set
            function genGridCheckBoxClickEvent(grid) {
                grid.addEventListener(grid.hostElement, 'click', function (e) {
                    if (e.target.type=="checkbox") {
                        var ht = grid.hitTest(e);
                        var colName = ht.panel.columns[ht.col].binding;
                        var selectedRow = grid.rows[ht.row].dataItem;
                        selectedRow[colName] = !selectedRow[colName];
                    }
                });
            }
            return g;
        },

        // columnsLayout 서버에 저장
        setGridItem: function (resrceCd, idx, columnLayout) {
            var param = {};
            param.resrceCd = resrceCd;
            param.gridIdx = idx;
            param.columnItem = columnLayout;
            if ( !isEmpty(resrceCd) && !isEmpty(idx) ) {
                $.postJSONAsync("/setGridItem.sb", param, function (result) {
                    console.log("resource : " + resrceCd + ", idx : " + idx + ", setGridItem success..");
                })
                    .fail(function () {
                        alert("Ajax Fail");
                    });
            }
        },

        getGridData: function (url, param, target, success, fail) {
            return $.postJSON(url, param, function (result) {
                    var list = result.data.list;
                    if (list.length === undefined || list.length == 0) {
                        s_alert.pop(result.message);
                        return;
                    }
                    target.itemsSource = new wijmo.collections.CollectionView(list, {
                        trackChanges: true
                    });
                    if (success != null) {
                        success(result);
                    }
                },
                function (result) {
                    s_alert.pop(result.message);
                    if (fail != null) {
                        fail(result);
                    }
                });
        }

    };

    //그리드 표시 항목 생성
    var wgridPic = {

        genGridPicker: function (div, g, resrceCd, gridIdx) {
            // 표시 항목 생성
            var columnPicker = new wijmo.input.ListBox(div, {
                itemsSource: g.columns,
                checkedMemberPath: 'visible',
                displayMemberPath: 'header',
                lostFocus: function (s, e) {
                    wgrid.setGridItem(resrceCd, gridIdx, g.columnLayout);
                    wijmo.hidePopup(s.hostElement);
                }
            });

            genGridPickerView(columnPicker, g);

            // 표시 항목 그리드에 적용
            function genGridPickerView(columnPicker, g) {

                var tlClass = "v-center";
                var ref = g.hostElement.querySelector('.wj-topleft');

                ref.addEventListener('mousedown', function (e) {
                    if (wijmo.hasClass(e.target.children[0], tlClass)) {
                        wijmo.showPopup(columnPicker.hostElement, ref, false, true, false);
                        columnPicker.focus();
                        e.preventDefault();
                    }
                });
            }

            return columnPicker;
        }
    };


    // 공통 콤보박스
    var wcombo = {
        // combo : 콤보박스 ID '#combo' data : json 형태 데이터 f : 콤보박스 선택 이벤트
        genCommonBoxFun: function (div, data, f) {
            return new wijmo.input.ComboBox(div, {
                displayMemberPath: "name",
                selectedValuePath: "value",
                isAnimated: true,
                itemsSource: data,
                selectedIndexChanged: function (s, e) {
                    f(s, e);
                }
            });
        },
        genCommonBoxSimple: function (div) {
            return new wijmo.input.ComboBox(div, {
                displayMemberPath: "name",
                selectedValuePath: "value",
                isAnimated: true
            });
        },
        genCommonBox: function (div, data) {
            return new wijmo.input.ComboBox(div, {
                displayMemberPath: "name",
                selectedValuePath: "value",
                isAnimated: true,
                itemsSource: data
            });
        },
        genInput: function (div) {
            return new wijmo.input.ComboBox(div);
        },
        genTime: function (div, step) {
            return new wijmo.input.InputTime(div, {
                format: "h:mm tt", // default format, with AM/PM
                min: "1:00", // list starts at 9am
                max: "23:59", // until 5pm
                step: step, // every 30 minutes
                isEditable: true // user can enter values not on the list
            });
        },
        genDate: function (div) {
            return new wijmo.input.InputDate(div, {
                format: "yyyy-MM-dd"
            });
        },
        genDateVal: function (div, date) {
            if (date == "" || date.length != 8) {
                return wcombo.genDate(div);
            }
            var dt = new wijmo.input.InputDate(div, {
                format: "yyyy-MM-dd"
            });
            date = date.substr(0, 4) + "-" + date.substr(4, 2) + "-" + date.substr(6, 2);
            dt.value = new Date(date);
            return dt;
        }
    };

    // 엑셀 다운로드
    var wexcel = {
        down: function (grid, sheet, excel) {
            // create book with current view
            var book = wijmo.grid.xlsx.FlexGridXlsxConverter.save(grid, {
                includeColumnHeaders: true,
                includeRowHeaders: true,
                includeColumns: function (column) {
//            console.log(column.binding + " : " + column.visible);
                    return true;
                }
            });
            book.sheets[0].name = sheet;
            // save the book
            book.save(excel);
        }
    };

    win.wgrid = wgrid;
    win.wcombo = wcombo;
    win.wgridPic = wgridPic;
    win.wexcel = wexcel;
}("undefined" != typeof window ? window : this, jQuery);













