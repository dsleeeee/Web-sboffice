"use strict";
!function( win, $ ){
  
  // 그리드
  var wgrid = {
      genGrid: function( div, columns, resrceCd, gridIdx, columnLayout ) {
        var g = new wijmo.grid.FlexGrid(div, {
          itemsSource : new wijmo.collections.CollectionView(),
          columns : columns,
          isReadOnly : true,
          showSort : true,
          autoGenerateColumns: false,  // 이거 안하면 컬럼이 자동으로 막 생김
          showAlternatingRows: false,
          formatItem : function(s, e) {
            // 표시 화면 입력 생성
            if (e.panel == s.topLeftCells) {
              e.cell.innerHTML = "<div class=\"v-center\"></div>";
            }
          },
          draggedColumn : function(s, e){
            wgrid.setGridItem(resrceCd, gridIdx, s.columnLayout);
          }
        });
        
        // 저장된 레이아웃이 있을 경우 적용
        if( columnLayout != null ) {
          var userCols = columnLayout.columns;
          var isVisibleColumn = function(id) {
            var visible = true;
            for(var j = 0; j < userCols.length; j++) {
              if(id == userCols[j].binding) {
                visible = (userCols[j].visible == null ? true : userCols[j].visible);
                break;
              }
            }
            return visible;
          };
          for(var i = 0; i < g.columns.length; i++) {
            g.columns[i].visible = isVisibleColumn(g.columns[i].binding);
          }
        }
        
        genGridPicker(g, resrceCd, gridIdx);
        
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
        
        return g; 
      },
      
      // columnsLayout 서버에 저장
      setGridItem: function(resrceCd, idx, columnLayout) {
        var param = {};
        param.resrceCd = resrceCd;
        param.gridIdx = idx;
        param.columnItem = columnLayout;
        $.postJSONAsync("/setGridItem.sb", param, function(result) {
          console.log("resource : " + resrceCd + ", idx : " + idx + ", setGridItem success..");
        })
        .fail(function(){
          alert("Ajax Fail");
        });
      }
  };
  
  //그리드 표시 항목 생성
  var wgridPic = {
      
      genGridPicker: function( div, g, resrceCd, gridIdx) {
        // 표시 항목 생성
        var columnPicker = new wijmo.input.ListBox(div, {
          itemsSource: g.columns,
          checkedMemberPath: 'visible',
          displayMemberPath: 'header',
          lostFocus : function(s, e) {
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
      genCommonBoxFun: function( div, data, f) {
        return new wijmo.input.ComboBox(div, {
          displayMemberPath : "name",
          selectedValuePath : "value",
          isAnimated : true,
          itemsSource : data,
          selectedIndexChanged : function(s, e) {
            f(s, e);
          }
        });
      },
      genCommonBoxSimple: function( div ) {
        return new wijmo.input.ComboBox(div, {
          displayMemberPath : "name",
          selectedValuePath : "value",
          isAnimated : true
        });
      },
      genCommonBox: function( div, data ) {
        return new wijmo.input.ComboBox(div, {
          displayMemberPath : "name",
          selectedValuePath : "value",
          isAnimated : true,
          itemsSource : data
        });
      },
      genInput: function(div) {
        return new wijmo.input.ComboBox(div);
      },
      genTime: function(div, step){
        return new wijmo.input.InputTime(div, {
          format: "h:mm tt", // default format, with AM/PM
          min: "1:00", // list starts at 9am
          max: "23:59", // until 5pm
          step: step, // every 30 minutes
          isEditable: true // user can enter values not on the list
        });
      },
      genDate: function(div) {
        return new wijmo.input.InputDate(div, {
          format:"yyyy-MM-dd" 
        }); 
      },
      genDateVal: function(div, date) {
        if(date == "" || date.length != 8) {
          return wcombo.genDate(div);
        }
        var dt = new wijmo.input.InputDate(div, {
          format:"yyyy-MM-dd" 
        });
        date = date.substr(0,4) + "-" + date.substr(4,2) + "-" + date.substr(6,2);
        dt.value = new Date(date);
        return dt;
      }
  };
  
  // 엑셀 다운로드
  var wexcel = {
      down: function( grid, sheet, excel ) {
     // create book with current view
        var book = wijmo.grid.xlsx.FlexGridXlsxConverter.save(grid, {
          includeColumnHeaders : true,
          includeRowHeaders : true,
          includeColumns : function(column) {
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
}( "undefined" != typeof window ? window : this, jQuery );













