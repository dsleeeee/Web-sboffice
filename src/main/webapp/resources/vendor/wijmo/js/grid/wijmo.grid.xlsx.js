﻿/*
 *
 * Wijmo Library 5.20183.550
 * http://wijmo.com/
 *
 * Copyright(c) GrapeCity, Inc.  All rights reserved.
 *
 * Licensed under the GrapeCity Commercial License.
 * sales@wijmo.com
 * wijmo.com/products/wijmo-5/license/
 *
 */
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, l) {
        e.__proto__ = l
      } || function(e, l) {
        for (var o in l) l.hasOwnProperty(o) && (e[o] = l[o])
      };
    return function(l, o) {
      function t() {
        this.constructor = l
      }
      e(l, o), l.prototype = null === o ? Object.create(o) : (t.prototype = o.prototype, new t)
    }
  }(),
  wijmo;
! function(e) {
  ! function(l) {
    ! function(o) {
      "use strict";
      var t = function() {
        function o() {}
        return o.save = function(e, l, o) {
          var t = this._saveFlexGridToWorkbook(e, l);
          return o && t.save(o), t
        }, o.saveAsync = function(e, l, o, t, r) {
          var n = this._saveFlexGridToWorkbook(e, l);
          return o && n.saveAsync(o, t, r), n
        }, o.load = function(l, o, t) {
          var r = this;
          if (o instanceof Blob) {
            var n = new FileReader;
            n.onload = function() {
              var o = e.xlsx.Workbook._base64EncArr(new Uint8Array(n.result)),
                s = new e.xlsx.Workbook;
              s.load(o), o = null, l.deferUpdate(function() {
                r._loadToFlexGrid(l, s, t), s = null
              })
            }, n.readAsArrayBuffer(o)
          } else if (o instanceof e.xlsx.Workbook) l.deferUpdate(function() {
            r._loadToFlexGrid(l, o, t), o = null
          });
          else {
            if (o instanceof ArrayBuffer) o = e.xlsx.Workbook._base64EncArr(new Uint8Array(o));
            else if (!e.isString(o)) throw "Invalid workbook.";
            var s = new e.xlsx.Workbook;
            s.load(o), o = null, l.deferUpdate(function() {
              r._loadToFlexGrid(l, s, t), s = null
            })
          }
        }, o.loadAsync = function(l, o, t, r, n) {
          var s = this;
          if (o instanceof Blob) {
            var i = new FileReader;
            i.onload = function() {
              var o = e.xlsx.Workbook._base64EncArr(new Uint8Array(i.result)),
                a = new e.xlsx.Workbook;
              a.loadAsync(o, function(e) {
                o = null, a = null, l.deferUpdate(function() {
                  s._loadToFlexGrid(l, e, t), r && r(e), e = null
                })
              }, n)
            }, i.readAsArrayBuffer(o)
          } else if (o instanceof e.xlsx.Workbook) l.deferUpdate(function() {
            s._loadToFlexGrid(l, o, t), r && r(o), o = null
          });
          else {
            if (o instanceof ArrayBuffer) o = e.xlsx.Workbook._base64EncArr(new Uint8Array(o));
            else if (!e.isString(o)) throw "Invalid workbook.";
            (new e.xlsx.Workbook).loadAsync(o, function(e) {
              o = null, l.deferUpdate(function() {
                s._loadToFlexGrid(l, e, t), r && r(e), e = null
              })
            }, n)
          }
        }, o._saveFlexGridToWorkbook = function(o, t) {
          var r, n, s, i, a, d, u, c, f, h, p, x, g, m = new e.xlsx.Workbook,
            w = new e.xlsx.WorkSheet,
            y = (new e.xlsx.WorkbookStyle, new e.xlsx.WorkbookFrozenPane),
            b = !t || null == t.includeColumnHeaders || t.includeColumnHeaders,
            _ = !(!t || null == t.includeRowHeaders) && t.includeRowHeaders,
            C = !t || null == t.includeCellStyles || t.includeCellStyles,
            k = t ? t.activeWorksheet : null,
            v = t ? t.includeColumns : null,
            S = t ? t.formatItem : null,
            T = 0,
            W = 0,
            R = 0,
            A = 0,
            F = [];
          if (null == this.hasCssText && (this.hasCssText = "cssText" in document.body.style), h = o.wj_sheetInfo, w.name = t ? t.sheetName : "", w.visible = !t || !1 !== t.sheetVisible, h && h.tables && h.tables.length > 0)
            for (var B = 0; B < h.tables.length; B++) w.tables.push(h.tables[B]);
          if (d = [], h || !C && !S || ((p = document.createElement("div")).style.visibility = "hidden", p.setAttribute(l.FlexGrid._WJS_MEASURE, "true"), o.hostElement.appendChild(p)), _) {
            for (c = 0, u = 0; u < o.rowHeaders.rows.length; u++)
              if (!(o.rowHeaders.rows[u].renderSize <= 0)) {
                for (d[c] = [], f = 0; f < o.rowHeaders.columns.length; f++) s = o._getBindingColumn(o.rowHeaders, u, o.rowHeaders.columns[f]), i = this._getColumnSetting(s, f, o.columnHeaders.columns), d[c][f] = i, 0 === c && ((a = new e.xlsx.WorkbookColumn)._deserialize(i), w._addWorkbookColumn(a, f));
                c++
              }
            A = f
          }
          if (o.columnHeaders.rows.length > 0) {
            for (c = 0, u = 0; u < o.columnHeaders.rows.length; u++)
              if (!(o.columnHeaders.rows[u].renderSize <= 0)) {
                for (d[c] || (d[c] = []), f = 0; f < o.columnHeaders.columns.length; f++) s = o._getBindingColumn(o.columnHeaders, u, o.columnHeaders.columns[f]), i = this._getColumnSetting(s, f, o.columnHeaders.columns), d[c][A + f] = i, 0 === c && (v && !v(s) || ((a = new e.xlsx.WorkbookColumn)._deserialize(i), w._addWorkbookColumn(a)));
                b && (A = 0, r = {}, n = new e.xlsx.WorkbookRow, _ && (A = this._parseFlexGridRowToSheetRow(o.topLeftCells, r, u, 0, d, C, p, F, !1, 0, v, S)), this._parseFlexGridRowToSheetRow(o.columnHeaders, r, u, A, d, C, p, F, !1, 0, v, S), r.cells.length > 0 && (n._deserialize(r), w._addWorkbookRow(n, c))), c++
              }
            W = b ? c : 0
          }
          for (u = 0; u < o.cells.rows.length; u++) A = 0, r = {}, n = new e.xlsx.WorkbookRow, (x = o.rows[u]) instanceof l._NewRowTemplate || ((g = x instanceof l.GroupRow) ? T = e.tryCast(x, l.GroupRow).level : o.rows.maxGroupLevel > -1 && (T = o.rows.maxGroupLevel + 1), _ && (A = this._parseFlexGridRowToSheetRow(o.rowHeaders, r, u, 0, d, C, p, F, g, T, v, S)), this._parseFlexGridRowToSheetRow(o.cells, r, u, A, d, C, p, F, g, T, v, S), r.cells.length > 0 && (n._deserialize(r), w._addWorkbookRow(n, W + u)));
          for (R = o.cells.rows.length, u = 0; u < o.columnFooters.rows.length; u++) A = 0, r = {}, n = new e.xlsx.WorkbookRow, g = (x = o.columnFooters.rows[u]) instanceof l.GroupRow, _ && (A = this._parseFlexGridRowToSheetRow(o.rowHeaders, r, u, 0, d, C, p, F, g, 0, v, S)), this._parseFlexGridRowToSheetRow(o.columnFooters, r, u, A, d, C, p, F, g, 0, v, S), r.cells.length > 0 && (n._deserialize(r), w._addWorkbookRow(n, W + R + u));
          return y.rows = b ? o.frozenRows + W : o.frozenRows, y.columns = _ ? o.frozenColumns + A : o.frozenColumns, w.frozenPane = y, m._addWorkSheet(w), h || !C && !S || (o.hostElement.removeChild(p), F.forEach(function(e) {
            return e.forEach(function(e) {
              e && e.parentElement && e.parentElement.removeChild(e)
            })
          })), m.activeWorksheet = k, m
        }, o._loadToFlexGrid = function(o, t, r) {
          var n, s, i, a, d, u, c, f, h, p, x, g, m, w, y, b, _, C, k, v, S, T, W, R, A, F, B, H, N, D, E, I, z = !r || null == r.includeColumnHeaders || r.includeColumnHeaders,
            G = !r || null == r.includeColumnHeaders || r.includeColumnHeaders,
            L = r && null != r.sheetIndex && !isNaN(r.sheetIndex) ? r.sheetIndex : 0,
            O = r ? r.sheetName : null,
            M = !r || r.sheetVisible,
            j = null != o.wj_sheetInfo,
            P = 0,
            V = 0,
            U = !1,
            X = {};
          if (o.itemsSource = null, o.columns.clear(), o.rows.clear(), o.frozenColumns = 0, o.frozenRows = 0, F = {}, B = [], V = 0, i = [], H = [], L < 0 || L >= t.sheets.length) throw "The sheet index option is out of the sheet range of current workbook.";
          if (null != O)
            for (n = 0; n < t.sheets.length; n++)
              if (O === t.sheets[n].name) {
                x = t.sheets[n];
                break
              }
          if (null != (x = x || t.sheets[L]).rows) {
            for (z && (V = 1, x.rows.length <= 1 && (G = !1, V = 0), c = x.rows[0]), m = this._getColumnCount(x.rows), g = this._getRowCount(x.rows, m), C = x.summaryBelow, s = x.columns, P = 0; P < m; P++) o.columns.push(new l.Column), s[P] && (isNaN(+s[P].width) || (o.columns[P].width = +s[P].width), s[P].visible || null == s[P].visible || (o.columns[P].visible = !!s[P].visible), s[P].style && s[P].style.wordWrap && (o.columns[P].wordWrap = s[P].style.wordWrap));
            for (; V < g; V++) {
              if (w = !1, I = null, p = x.rows[V])
                for (b = V + 1; b < x.rows.length;) {
                  if (_ = x.rows[b]) {
                    (isNaN(p.groupLevel) && !isNaN(_.groupLevel) || !isNaN(p.groupLevel) && p.groupLevel < _.groupLevel) && (w = !0);
                    break
                  }
                  b++
                }
              for (w && !C ? (v && (v.isCollapsed = U), (v = new l.GroupRow).isReadOnly = !1, U = null != p.collapsed && p.collapsed, v.level = isNaN(p.groupLevel) ? 0 : p.groupLevel, X[v.level] = U, this._checkParentCollapsed(X, v.level) && v._setFlag(l.RowColFlags.ParentCollapsed, !0), o.rows.push(v)) : (k = new l.Row, p && this._checkParentCollapsed(X, p.groupLevel) && k._setFlag(l.RowColFlags.ParentCollapsed, !0), o.rows.push(k)), p && p.height && !isNaN(p.height) && (o.rows[G ? V - 1 : V].height = p.height), P = 0; P < m; P++)
                if (p) {
                  if (y = p.cells[P], (W = y ? y.formula : null) && "=" !== W[0] && (W = "=" + W), y && y.textRuns && y.textRuns.length > 0 ? (o.rows[G ? V - 1 : V].isContentHtml = !0, o.setCellData(G ? V - 1 : V, P, this._parseTextRunsToHTML(y.textRuns))) : o.setCellData(G ? V - 1 : V, P, W && j ? W : this._getItemValue(y)), w || this._setColumn(i, P, y), R = V * m + P, A = y ? y.style : null, N = e.xlsx.Workbook._parseExcelFormat(y), A) {
                    if (I = null == I ? !!A.wordWrap : I && !!A.wordWrap, D = this._getItemType(y), A.hAlign) E = e.xlsx.Workbook._parseHAlignToString(e.asEnum(A.hAlign, e.xlsx.HAlign));
                    else switch (D) {
                      case e.DataType.Number:
                        E = "right";
                        break;
                      case e.DataType.Boolean:
                        E = "center";
                        break;
                      default:
                        E = N && 0 === N.search(/[n,c,p]/i) ? "right" : "left"
                    }
                    F[R] = {
                      fontWeight: A.font && A.font.bold ? "bold" : "none",
                      fontStyle: A.font && A.font.italic ? "italic" : "none",
                      textDecoration: A.font && A.font.underline ? "underline" : "none",
                      textAlign: E,
                      fontFamily: A.font && A.font.family ? A.font.family : "",
                      fontSize: A.font && A.font.size ? A.font.size + "px" : "",
                      color: A.font && A.font.color ? A.font.color : "",
                      backgroundColor: A.fill && A.fill.color ? A.fill.color : "",
                      whiteSpace: A.wordWrap ? "pre-wrap" : "normal",
                      format: N
                    }, A.borders && (A.borders.left && (this._parseBorderStyle(A.borders.left.style, "Left", F[R]), F[R].borderLeftColor = A.borders.left.color), A.borders.right && (this._parseBorderStyle(A.borders.right.style, "Right", F[R]), F[R].borderRightColor = A.borders.right.color), A.borders.top && (this._parseBorderStyle(A.borders.top.style, "Top", F[R]), F[R].borderTopColor = A.borders.top.color), A.borders.bottom && (this._parseBorderStyle(A.borders.bottom.style, "Bottom", F[R]), F[R].borderBottomColor = A.borders.bottom.color)), A.font && -1 === H.indexOf(A.font.family) && H.push(A.font.family)
                  }
                  y && e.isNumber(y.rowSpan) && y.rowSpan > 0 && e.isNumber(y.colSpan) && y.colSpan > 0 && (y.rowSpan > 1 || y.colSpan > 1) && B.push(new l.CellRange(V, P, V + y.rowSpan - 1, P + y.colSpan - 1))
                }
              p && (this._checkParentCollapsed(X, p.groupLevel) || p.visible || null == p.visible || (o.rows[G ? V - 1 : V].visible = p.visible), o.rows[G ? V - 1 : V].wordWrap = !!p.style && !!p.style.wordWrap || !!I)
            }
            for (v && (v.isCollapsed = U), x.frozenPane && (S = x.frozenPane.columns, e.isNumber(S) && !isNaN(S) && (o.frozenColumns = S), T = x.frozenPane.rows, e.isNumber(T) && !isNaN(T) && (o.frozenRows = G && T > 0 ? T - 1 : T)), P = 0; P < o.columnHeaders.columns.length; P++) a = i[P], (d = o.columns[P]).isRequired = !1, G && ((f = c ? c.cells[P] : null) && f.value ? (h = e.xlsx.Workbook._parseExcelFormat(f), u = e.Globalize.format(f.value, h)) : u = ""), d.header = u, a && (a.dataType === e.DataType.Boolean && (d.dataType = a.dataType), d.format = a.format, d.align = a.hAlign, d.wordWrap = d.wordWrap || !!a.wordWrap);
            j && (o.wj_sheetInfo.name = x.name, o.wj_sheetInfo.visible = !0 === M || !1 !== x.visible, o.wj_sheetInfo.styledCells = F, o.wj_sheetInfo.mergedRanges = B, o.wj_sheetInfo.fonts = H, o.wj_sheetInfo.tables = x.tables)
          }
        }, o._parseFlexGridRowToSheetRow = function(o, t, n, s, i, a, d, u, c, f, h, p) {
          var x, g, m, w, y, b, _, C, k, v, S, T, W, R, A, F, B, H, N, D, E, I, z, G, L, O, M, j, P, V, U = !1,
            X = !1;
          for ((N = (x = o.grid).wj_sheetInfo) && (D = N.evaluateFormula), null == (g = o.rows[n]).recordIndex ? (G = 0, o.cellType === e.grid.CellType.ColumnHeader && o.rows.length > 1 && (G = n === i.length ? n - 1 : n)) : G = g.recordIndex, t.cells || (t.cells = []), t.visible = g.isVisible, t.height = g.renderHeight || o.rows.defaultSize, t.groupLevel = f, c && (t.collapsed = g.isCollapsed), g.wordWrap && (t.style = {
            wordWrap: g.wordWrap
          }), (g.constructor === e.grid.Row || g.constructor === e.grid._NewRowTemplate || e.grid.detail && g.constructor === e.grid.detail.DetailRow || e.grid.multirow && g.constructor === e.grid.multirow._MultiRow) && (U = !0), e.grid.detail && g.constructor === e.grid.detail.DetailRow && (X = !0), w = 0; w < o.columns.length; w++)
            if (y = null, H = 1, B = 1, P = 0, z = !1, A = null, M = null, O = null, C = null, I = x._getBindingColumn(o, n, o.columns[w]), F = null, N && o === x.cells ? (R = n * o.columns.length + w, N.mergedRanges && (F = this._getMergedRange(n, w, N.mergedRanges)), N.styledCells && (A = N.styledCells[R])) : a && (O = this._getMeasureCell(o, w, d, u), A = (F = x.getMergedRange(o, n, w, !1)) ? this._getCellStyle(o, O, F.bottomRow, F.rightCol) || {} : this._getCellStyle(o, O, n, w) || {}), F || (F = x.getMergedRange(o, n, w, !1)), F ? n === F.topRow && w === F.leftCol && (B = F.bottomRow - F.topRow + 1, H = this._getColSpan(o, F, h), z = !0) : z = !0, !h || h(I)) {
              if (m = i[G] ? i[G][w + s] : null, U || c ? (_ = z ? o.getCellData(n, w, !0) : null, k = z ? o.getCellData(n, w, !1) : null, S = !1, W = null, _ && e.isString(_) && _.length > 1 && "=" === _[0] && (S = !0), E = e.isDate(k), A && A.format ? (y = A.format, /[hsmyt\:]/i.test(y) && (E = !0), b = e.xlsx.Workbook._parseCellFormat(A.format, E)) : m && m.style && m.style.format ? (y = I.format, b = m.style.format) : b = null, S && null != D && e.isFunction(D) && (W = D(_)), b || (E ? b = "m/d/yyyy" : e.isNumber(k) && !I.dataMap ? b = e.isInt(k) ? "#,##0" : "#,##0.00" : S ? (T = _.toLowerCase()).indexOf("now()") > -1 ? (b = "m/d/yyyy h:mm", E = !0) : T.indexOf("today()") > -1 || T.indexOf("date(") > -1 ? (b = "m/d/yyyy", E = !0) : T.indexOf("time(") > -1 && (b = "h:mm AM/PM", E = !0) : b = "General")) : (_ = z ? x.columnHeaders.getCellData(0, w, !0) : null, b = "General"), e.isString(_) && -1 !== _.indexOf("<span") && (C = this._parseToTextRuns(_), _ = null), M = this._parseCellStyle(A) || {}, o === x.cells && c && g.hasChildren && w === x.columns.firstVisibleIndex) {
                if (S && null != W ? v = W : _ ? v = _ : z && (v = g.getGroupHeader().replace(/<\/?\w+>/g, "").replace(/&#39;/g, "'")), null == v && !A) continue;
                !(E = e.isDate(v)) && y && "d" === y.toLowerCase() && e.isInt(v) && (b = "0"), v = e.isString(v) ? e.xlsx.Workbook._unescapeXML(v) : v, w === x.columns.firstVisibleIndex && x.treeIndent && (P = f), L = {
                  value: v,
                  isDate: E,
                  formula: S ? this._parseToExcelFormula(_, E) : null,
                  colSpan: H,
                  rowSpan: B,
                  style: this._extend(M, {
                    format: b,
                    font: {
                      bold: !0
                    },
                    hAlign: e.xlsx.HAlign.Left,
                    indent: P
                  }),
                  textRuns: C
                }
              } else _ = e.isString(_) ? e.xlsx.Workbook._unescapeXML(_) : _, k = e.isString(k) ? e.xlsx.Workbook._unescapeXML(k) : k, !E && y && "d" === y.toLowerCase() && e.isInt(k) && (b = "0"), j = M && M.hAlign ? M.hAlign : m && m.style && null != m.style.hAlign ? e.asEnum(m.style.hAlign, e.xlsx.HAlign, !0) : e.isDate(k) ? e.xlsx.HAlign.Left : e.xlsx.HAlign.General, w !== x.columns.firstVisibleIndex || !x.treeIndent || j !== e.xlsx.HAlign.Left && j !== e.xlsx.HAlign.General || (P = f), L = {
                value: S ? W : "General" === b ? _ : k,
                isDate: E,
                formula: S ? this._parseToExcelFormula(_, E) : null,
                colSpan: w < x.columns.firstVisibleIndex ? 1 : H,
                rowSpan: B,
                style: this._extend(M, {
                  format: b,
                  hAlign: j,
                  vAlign: B > 1 ? o === x.cells || !1 === x.centerHeadersVertically ? e.xlsx.VAlign.Top : e.xlsx.VAlign.Center : null,
                  indent: P
                }),
                textRuns: C
              }, X && (V = o.getCellElement(n, w)) && V.appendChild(g.detail);
              p && p(new r(o, new l.CellRange(n, w), O, d, u, L)), t.cells.push(L)
            }
          return s + w
        }, o._parseCellStyle = function(l, o) {
          if (void 0 === o && (o = !1), null == l) return null;
          var t = l.fontSize;
          t = t ? +t.substring(0, t.indexOf("px")) : null, isNaN(t) && (t = null);
          var r = l.fontWeight;
          r = "bold" === r || +r >= 700;
          var n = "italic" === l.fontStyle,
            s = l.textDecorationStyle;
          null == s && (s = l.textDecoration), s = "underline" === s;
          var i = l.whiteSpace;
          return i = !!i && i.indexOf("pre") > -1, {
            font: {
              bold: r,
              italic: n,
              underline: s,
              family: this._parseToExcelFontFamily(l.fontFamily),
              size: t,
              color: l.color
            },
            fill: {
              color: l.backgroundColor
            },
            borders: this._parseBorder(l, o),
            hAlign: e.xlsx.Workbook._parseStringToHAlign(l.textAlign),
            wordWrap: i
          }
        }, o._parseBorder = function(e, l) {
          var o = {};
          return o.left = this._parseEgdeBorder(e, "Left"), o.right = this._parseEgdeBorder(e, "Right"), o.top = this._parseEgdeBorder(e, "Top"), o.bottom = this._parseEgdeBorder(e, "Bottom"), l && (o.vertical = this._parseEgdeBorder(e, "Vertical"), o.horizontal = this._parseEgdeBorder(e, "Horizontal")), o
        }, o._parseEgdeBorder = function(l, o) {
          var t, r = l["border" + o + "Style"],
            n = l["border" + o + "Width"];
          if (n && n.length > 2 && (n = +n.substring(0, n.length - 2)), r && "none" !== r && "hidden" !== r) {
            switch (t = {}, r = r.toLowerCase()) {
              case "dotted":
                t.style = n > 1 ? e.xlsx.BorderStyle.MediumDashDotted : e.xlsx.BorderStyle.Dotted;
                break;
              case "dashed":
                t.style = n > 1 ? e.xlsx.BorderStyle.MediumDashed : e.xlsx.BorderStyle.Dashed;
                break;
              case "double":
                t.style = e.xlsx.BorderStyle.Double;
                break;
              default:
                t.style = n > 2 ? e.xlsx.BorderStyle.Thick : n > 1 ? e.xlsx.BorderStyle.Medium : e.xlsx.BorderStyle.Thin
            }
            t.color = l["border" + o + "Color"]
          }
          return t
        }, o._parseBorderStyle = function(l, o, t) {
          var r = "border" + o + "Style",
            n = "border" + o + "Width";
          switch (l) {
            case e.xlsx.BorderStyle.Dotted:
            case e.xlsx.BorderStyle.Hair:
              t[r] = "dotted", t[n] = "1px";
              break;
            case e.xlsx.BorderStyle.Dashed:
            case e.xlsx.BorderStyle.ThinDashDotDotted:
            case e.xlsx.BorderStyle.ThinDashDotted:
              t[r] = "dashed", t[n] = "1px";
              break;
            case e.xlsx.BorderStyle.MediumDashed:
            case e.xlsx.BorderStyle.MediumDashDotDotted:
            case e.xlsx.BorderStyle.MediumDashDotted:
            case e.xlsx.BorderStyle.SlantedMediumDashDotted:
              t[r] = "dashed", t[n] = "2px";
              break;
            case e.xlsx.BorderStyle.Double:
              t[r] = "double", t[n] = "3px";
              break;
            case e.xlsx.BorderStyle.Medium:
              t[r] = "solid", t[n] = "2px";
              break;
            default:
              t[r] = "solid", t[n] = "1px"
          }
        }, o._parseToExcelFontFamily = function(e) {
          var l;
          return e && (l = e.split(",")) && l.length > 0 && (e = l[0].replace(/\"|\'/g, "")), e
        }, o._parseToExcelFormula = function(l, o) {
          var t, r, n, s, i, a, d, u = /(floor|ceiling)\([+-]?\d+\.?\d*\)/gi,
            c = /text\(\"?\w+\"?\s*\,\s*\"\w+\"\)/gi,
            f = /\"?\w+\"?\s*\,\s*\"(\w+)\"/i;
          if (t = l.match(u))
            for (i = 0; i < t.length; i++) d = (a = t[i]).substring(0, a.lastIndexOf(")")) + ", 1)", l = l.replace(a, d);
          if (t = null, t = l.match(c))
            for (i = 0; i < t.length; i++)(r = (a = t[i]).match(f)) && 2 === r.length && (n = r[1], /^d{1,4}?$/.test(n) || (s = e.xlsx.Workbook._parseCellFormat(n, o), d = a.replace(n, s), l = l.replace(a, d)));
          return l
        }, o._parseToTextRuns = function(e) {
          var l, o, t, r = e.split("<span "),
            n = [];
          for (l = 0; l < r.length; l++) t = -1 !== (o = r[l]).indexOf("</span>") ? {
            text: o.substring(o.indexOf(">") + 1, o.indexOf("</span>")),
            font: this._parseToTextRunFont(o.substring(o.indexOf('style="') + 7, o.indexOf(';"')))
          } : {
            text: o
          }, n.push(t);
          return n
        }, o._parseToTextRunFont = function(e) {
          var l, o, t, r, n, s, i, a, d, u = e.split(";");
          if (u.length > 0) {
            for (l = 0; l < u.length; l++)
              if (2 === (o = u[l].split(":")).length) switch (o[1] = o[1].trim(), o[0]) {
                case "font-size":
                  s = +o[1].substring(0, o[1].indexOf("px")), isNaN(s) && (s = null);
                  break;
                case "font-weight":
                  t = "bold" === o[1] || +o[1] >= 700;
                  break;
                case "font-style":
                  r = "italic" === o[1];
                  break;
                case "text-decoration-style":
                case "text-decoration":
                  n = "underline" === o[1];
                  break;
                case "font-family":
                  i = this._parseToExcelFontFamily(o[1]);
                  break;
                case "color":
                  a = o[1]
              }
            d = {
              bold: t,
              italic: r,
              underline: n,
              family: i,
              size: s,
              color: a
            }
          }
          return d
        }, o._getMeasureCell = function(e, l, o, t) {
          var r = t[e.cellType],
            n = r && r[l],
            s = null == n;
          if (n) {
            if (this.hasCssText) {
              n.style;
              n.style.cssText = "", n.style.visibility = "hidden"
            }
          } else r || (t[e.cellType] = r = []), r[l] = n = o.cloneNode();
          return !s && n.parentElement || (e.hostElement.children.length > 0 ? e.hostElement.children[0].appendChild(n) : e.hostElement.appendChild(n)), n
        }, o._getColumnSetting = function(l, o, t) {
          var r;
          return r = null != l.colspan ? this._getColumnRenderWidth(o, t) : l.renderWidth, r = r || t.defaultSize, {
            autoWidth: !0,
            width: r,
            visible: l.visible,
            style: {
              format: l.format ? e.xlsx.Workbook._parseCellFormat(l.format, l.dataType === e.DataType.Date) : "",
              hAlign: e.xlsx.Workbook._parseStringToHAlign(this._toExcelHAlign(l.getAlignment())),
              wordWrap: l.wordWrap
            }
          }
        }, o._toExcelHAlign = function(e) {
          return (e = e ? e.trim().toLowerCase() : e) ? e.indexOf("center") > -1 ? "center" : e.indexOf("right") > -1 || e.indexOf("end") > -1 ? "right" : e.indexOf("justify") > -1 ? "justify" : "left" : e
        }, o._getColumnCount = function(l) {
          for (var o, t = 0, r = 0, n = 0; n < l.length; n++)(o = l[n] && l[n].cells ? l[n].cells : []) && o.length > 0 && (r = o.length, e.isInt(o[r - 1].colSpan) && o[r - 1].colSpan > 1 && (r = r + o[r - 1].colSpan - 1), r > t && (t = r));
          return t
        }, o._getRowCount = function(l, o) {
          for (var t, r, n, s = l.length, i = s - 1, a = 0; a < o; a++) e: for (; i >= 0; i--)
            if (t = l[i], r = t && t.cells ? t.cells : [], (n = r[a]) && (null != n.value && "" !== n.value || e.isInt(n.rowSpan) && n.rowSpan > 1)) {
              e.isInt(n.rowSpan) && n.rowSpan > 1 && i + n.rowSpan > s && (s = i + n.rowSpan);
              break e
            }
          return s
        }, o._numAlpha = function(e) {
          var l = Math.floor(e / 26) - 1;
          return (l > -1 ? this._numAlpha(l) : "") + String.fromCharCode(65 + e % 26)
        }, o._getItemType = function(l) {
          return null == l || null == l.value || isNaN(l.value) ? null : e.getType(l.value)
        }, o._setColumn = function(l, o, t) {
          var r, n, s, i = l[o];
          i ? (r = this._getItemType(t), i.dataType !== r && i.dataType === e.DataType.Boolean && r !== e.DataType.Boolean && (i.dataType = r), !t || null == t.value || e.isString(t.value) && e.isNullOrWhiteSpace(t.value) || (n = e.xlsx.Workbook._parseExcelFormat(t)) && i.format !== n && "General" !== n && (i.format = n), t && t.style && (t.style.hAlign && (s = e.xlsx.Workbook._parseHAlignToString(e.asEnum(t.style.hAlign, e.xlsx.HAlign))), null == i.wordWrap ? i.wordWrap = !!t.style.wordWrap : i.wordWrap = i.wordWrap && !!t.style.wordWrap), s || r !== e.DataType.Number || (s = "right"), i.hAlign = s) : l[o] = {
            dataType: this._getItemType(t),
            format: e.xlsx.Workbook._parseExcelFormat(t),
            hAlign: "",
            wordWrap: null
          }
        }, o._getItemValue = function(l) {
          if (null == l || null == l.value) return null;
          var o = l.value;
          return e.isNumber(o) && isNaN(o) ? "" : o instanceof Date && isNaN(o.getTime()) ? "" : o
        }, o._getCellStyle = function(e, l, o, t) {
          try {
            e.grid.cellFactory.updateCell(e, o, t, l), l.className = l.className.replace("wj-state-selected", ""), l.className = l.className.replace("wj-state-multi-selected", "")
          } catch (e) {
            return null
          }
          return window.getComputedStyle(l)
        }, o._parseTextRunsToHTML = function(e) {
          var l, o, t, r;
          for (t = "", r = 0; r < e.length; r++) t += (o = (l = e[r]).font) ? '<span style="' + (o.bold ? "font-weight: bold;" : "") + (o.italic ? "font-style: italic;" : "") + (o.underline ? "text-decoration: underline;" : "") + (o.family ? "font-family: " + o.family + ";" : "") + (null != o.size ? "font-size: " + o.size + "px;" : "") + (o.color ? "color: " + o.color + ";" : "") + '">' + l.text + "</span>" : l.text;
          return t
        }, o._extend = function(l, o) {
          for (var t in o) {
            var r = o[t];
            e.isObject(r) && l[t] ? e.copy(l[t], r) : l[t] = r
          }
          return l
        }, o._checkParentCollapsed = function(e, l) {
          var o = !1;
          return Object.keys(e).forEach(function(t) {
            !0 === e[t] && !1 === o && !isNaN(l) && +t < l && (o = !0)
          }), o
        }, o._getColSpan = function(e, l, o) {
          for (var t = 0, r = l.leftCol; r <= l.rightCol; r++) o && !o(e.columns[r]) || t++;
          return t
        }, o._getColumnRenderWidth = function(e, l) {
          var o;
          return e >= l.length ? 0 : null != (o = l[e]).renderWidth ? o.renderWidth : l.defaultSize
        }, o._getMergedRange = function(e, l, o) {
          var t, r;
          for (t = 0; t < o.length; t++)
            if (r = o[t], e >= r.topRow && e <= r.bottomRow && l >= r.leftCol && l <= r.rightCol) return r;
          return null
        }, o
      }();
      o.FlexGridXlsxConverter = t;
      var r = function(e) {
        function l(l, o, t, r, n, s) {
          var i = e.call(this, l, o) || this;
          return i._cell = t, i._patternCell = r, i._xlsxCell = s, i._cellsCache = n, i
        }
        return __extends(l, e), Object.defineProperty(l.prototype, "cell", {
          get: function() {
            return this._cell
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(l.prototype, "xlsxCell", {
          get: function() {
            return this._xlsxCell
          },
          set: function(e) {
            this._xlsxCell = e
          },
          enumerable: !0,
          configurable: !0
        }), l.prototype.getFormattedCell = function() {
          return this._cell || (this._cell = t._getMeasureCell(this.panel, this.col, this._patternCell, this._cellsCache), t._getCellStyle(this.panel, this._cell, this.row, this.col)), this._cell
        }, l
      }(e.grid.CellRangeEventArgs);
      o.XlsxFormatItemEventArgs = r
    }(l.xlsx || (l.xlsx = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
