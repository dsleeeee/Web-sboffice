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
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var r in t) t.hasOwnProperty(r) && (e[r] = t[r])
      };
    return function(t, r) {
      function o() {
        this.constructor = t
      }
      e(t, r), t.prototype = null === r ? Object.create(r) : (o.prototype = r.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(t) {
      "use strict";

      function r(t, o, n) {
        if (void 0 === n && (n = !1), !t && o && (t = {}), o && t)
          for (var i in o) {
            var l = o[i],
              s = t[i];
            if (e.isObject(l)) {
              if (void 0 === s || !e.isObject(s) && n) {
                if (e.isFunction(l.clone)) {
                  t[i] = s = l.clone();
                  continue
                }
                t[i] = s = {}
              }
              e.isObject(s) && r(t[i], l, n)
            } else(void 0 === s || n && void 0 !== l) && (t[i] = l)
          }
        return t
      }
      var o;
      ! function(e) {
        e[e.ActualSize = 0] = "ActualSize", e[e.PageWidth = 1] = "PageWidth", e[e.SinglePage = 2] = "SinglePage"
      }(o = t.ScaleMode || (t.ScaleMode = {}));
      var n;
      ! function(e) {
        e[e.All = 0] = "All", e[e.Selection = 1] = "Selection"
      }(n = t.ExportMode || (t.ExportMode = {}));
      var i;
      ! function(e) {
        e[e.None = 0] = "None", e[e.Cell = 1] = "Cell", e[e.ColumnHeader = 2] = "ColumnHeader", e[e.RowHeader = 3] = "RowHeader", e[e.TopLeft = 4] = "TopLeft", e[e.ColumnFooter = 5] = "ColumnFooter", e[e.BottomLeft = 6] = "BottomLeft"
      }(i = t._CellType || (t._CellType = {}));
      var l = function(t) {
        function r(r, o, n, i, l, s, a, u, c) {
          var p = t.call(this) || this;
          return p.cancelBorders = !1, p._p = r, p._rng = o, p._cell = e.asType(n, HTMLElement, !0), p._canvas = i, p._clientRect = l, p._contentRect = s, p._textTop = a, p._style = u, p._getFormattedCell = c, p
        }
        return __extends(r, t), Object.defineProperty(r.prototype, "panel", {
          get: function() {
            return this._p
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "range", {
          get: function() {
            return this._rng.clone()
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "row", {
          get: function() {
            return this._rng.row
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "col", {
          get: function() {
            return this._rng.col
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "data", {
          get: function() {
            return this._data
          },
          set: function(e) {
            this._data = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "canvas", {
          get: function() {
            return this._canvas
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "cell", {
          get: function() {
            return this._cell
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "clientRect", {
          get: function() {
            return this._clientRect
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "contentRect", {
          get: function() {
            return this._contentRect
          },
          enumerable: !0,
          configurable: !0
        }), r.prototype.getFormattedCell = function() {
          return e.asFunction(this._getFormattedCell)()
        }, Object.defineProperty(r.prototype, "style", {
          get: function() {
            return this._style
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "textTop", {
          get: function() {
            return this._textTop
          },
          enumerable: !0,
          configurable: !0
        }), r
      }(e.CancelEventArgs);
      t.PdfFormatItemEventArgs = l, t._merge = r;
      var s = function() {
        function t() {}
        return t.draw = function(t, r, n, i, l, s) {
          e.assert(!!t, "The flex argument cannot be null."), e.assert(!!r, "The doc argument cannot be null."), null == (s = this._applyDefaultDrawSettings(s)).scaleMode && (s.scaleMode = null == i ? o.ActualSize : null == l ? o.PageWidth : o.SinglePage), this._drawInternal(t, r, n, i, l, s)
        }, t._applyDefaultDrawSettings = function(e) {
          return r(r({}, e), t.DefaultDrawSettings)
        }, t._drawInternal = function(t, r, o, n, i, l) {
          var s = null != o,
            u = new e.Size(r.width, r.height);
          o || (o = new e.Point(0, r.y)), e.isArray(l.embeddedFonts) && l.embeddedFonts.forEach(function(e) {
            r.registerFont(e)
          });
          var c = this._getRowsToRender(t, l),
            p = new a(t, l, c, this.BorderWidth, !0),
            h = new e.Rect(o.x || 0, o.y || 0, n || u.width, i || u.height),
            f = this._getScaleFactor(p, l.scaleMode, h),
            d = this._getPages(p, c, h, l, s, f),
            g = (l.progress ? this._getCellsCount(t, l, d) : 0) / l._progressMax,
            b = 0,
            _ = 0,
            w = l.progress ? function() {
              ++b - _ >= 50 && (_ = b, l.progress(b / g))
            } : null;
          l.progress && l.progress(0);
          for (var y = 0; y < d.length; y++) {
            y > 0 && r.addPage();
            var m = d[y],
              C = 0 === m.pageCol ? h.left : 0,
              v = 0 === m.pageRow ? h.top : 0;
            r.saveState(), r.paths.rect(0, 0, u.width, u.height).clip(), r.scale(f, f, new e.Point(C, v)), r.translate(C, v);
            var x = new a(t, l, m.range, this.BorderWidth, y === d.length - 1);
            x.render(r, w), r.restoreState(), r.x = C, r.y = v + x.renderSize.height * f
          }
          l.progress && 0 < l._progressMax && l.progress(l._progressMax)
        }, t._getCellsCount = function(e, t, r) {
          for (var o = 0, n = 0; n < r.length; n++) o += new a(e, t, r[n].range, 0, !1).getCellsCount();
          return o
        }, t._getRowsToRender = function(e, t) {
          var r = [];
          t.exportMode === n.All ? r.push(new h(null, 0, 0, e.rows.length - 1, e.columns.length - 1)) : r = e.getSelection();
          var o = new f(r);
          if (!t.drawDetailRows) {
            var i = [];
            o.forEach(e.cells, function(t, r, o) {
              if (!e.isDetailRow(t)) {
                var n = i.length;
                n && i[n - 1].bottomRow + 1 === o ? i[n - 1].row2++ : i.push(new h(null, o, r.col, o, r.col2))
              }
            }), o = new f(i)
          }
          return o
        }, t._getScaleFactor = function(e, t, r) {
          var n = 1;
          if (t === o.ActualSize) return n;
          var i = e.renderSize;
          if (t === o.SinglePage)(l = Math.min(r.width / i.width, r.height / i.height)) < 1 && (n = l);
          else {
            var l = r.width / i.width;
            l < 1 && (n = l)
          }
          return n
        }, t._getPages = function(t, r, n, i, l, s) {
          var a = this,
            u = [],
            c = [],
            p = e.pdf.pxToPt,
            h = t.flex,
            f = t.showColumnHeader,
            g = t.showColumnFooter,
            b = t.showRowHeader,
            _ = f ? p(h.columnHeaders.height) : 0,
            w = g ? p(h.columnFooters.height) : 0,
            y = b ? p(h.rowHeaders.width) : 0,
            m = i.scaleMode === o.ActualSize || i.scaleMode === o.PageWidth,
            C = i.scaleMode === o.ActualSize,
            v = (n.width - n.left) * (1 / s),
            x = (n.height - n.top) * (1 / s),
            R = n.width * (1 / s),
            P = n.height * (1 / s),
            S = _ + w,
            T = y,
            O = l && i.scaleMode == o.ActualSize;
          if (m) {
            var j = 0;
            r.forEach(h.cells, function(e, r, o, n) {
              var i = u.length ? P : x;
              if (t.isRenderableRow(e)) {
                var l = p(e.renderHeight);
                j++, S += l, (f || j > 1) && (S -= a.BorderWidth), S > i && (_ + l > i || O ? (u.push(n), S = _) : (u.push(n - 1), S = _ + l), f && (S -= a.BorderWidth))
              }
            })
          }
          var H = r.length() - 1;
          if (H < 0 && (H = 0), u.length && u[u.length - 1] === H || u.push(H), C)
            for (var z = 0, M = r.leftCol; M <= r.rightCol; M++) {
              var F = h.columns[M];
              if (F.isVisible) {
                var L = p(F.renderWidth),
                  G = c.length ? R : v;
                z++, T += L, (b || z > 1) && (T -= this.BorderWidth), T > G && (y + L > G || O ? (c.push(M), T = y) : (c.push(M - 1), T = y + L), b && (T -= this.BorderWidth))
              }
            }
          c.length && c[c.length - 1] === r.rightCol || c.push(r.rightCol);
          for (var W = [], A = !1, D = 1, V = l && i.maxPages > 0 ? 1 : i.maxPages, M = 0; M < u.length && !A; M++)
            for (var E = 0; E < c.length && !A; E++, D++)
              if (!(A = D > V)) {
                var B = 0 == M ? 0 : u[M - 1] + 1,
                  N = 0 == E ? r.leftCol : c[E - 1] + 1;
                W.push(new d(r.subrange(B, u[M] - B + 1, N, c[E]), E, M))
              }
          return W
        }, t.BorderWidth = 1, t.DefFont = new e.pdf.PdfFont, t.DefaultDrawSettings = {
          customCellContent: !1,
          drawDetailRows: !1,
          exportMode: n.All,
          maxPages: Number.MAX_VALUE,
          repeatMergedValuesAcrossPages: !0,
          recalculateStarWidths: !0,
          styles: {
            cellStyle: {
              font: {
                family: t.DefFont.family,
                size: t.DefFont.size,
                style: t.DefFont.style,
                weight: t.DefFont.weight
              },
              padding: 1.5,
              verticalAlign: "middle"
            },
            headerCellStyle: {
              font: {
                weight: "bold"
              }
            }
          },
          _progressMax: 1
        }, t
      }();
      t._FlexGridPdfConverterCore = s;
      var a = function() {
          function t(e, t, r, o, n) {
            this._flex = e, this._borderWidth = o, this._lastPage = n, this._settings = t || {}, this._topLeft = new u(this, e.topLeftCells, new f(this.showRowHeader && this.showColumnHeader ? [new h(null, 0, 0, e.topLeftCells.rows.length - 1, e.topLeftCells.columns.length - 1)] : []), o), this._rowHeader = new u(this, e.rowHeaders, this.showRowHeader ? r.clone(0, e.rowHeaders.columns.length - 1) : new f([]), o), this._columnHeader = new u(this, e.columnHeaders, new f(this.showColumnHeader ? [new h(null, 0, r.leftCol, e.columnHeaders.rows.length - 1, r.rightCol)] : []), o), this._cells = new u(this, e.cells, r, o), this._bottomLeft = new u(this, e.bottomLeftCells, new f(this.showRowHeader && this.showColumnFooter ? [new h(null, 0, 0, e.bottomLeftCells.rows.length - 1, e.bottomLeftCells.columns.length - 1)] : []), o), this._columnFooter = new u(this, e.columnFooters, new f(this.showColumnFooter ? [new h(null, 0, r.leftCol, e.columnFooters.rows.length - 1, r.rightCol)] : []), o)
          }
          return Object.defineProperty(t.prototype, "settings", {
            get: function() {
              return this._settings
            },
            enumerable: !0,
            configurable: !0
          }), t.prototype.isRenderableRow = function(e) {
            return e.isVisible && !this._flex.isNewRow(e)
          }, t.prototype.getCellsCount = function() {
            return this._topLeft.getCellsCount() + this._rowHeader.getCellsCount() + this._columnHeader.getCellsCount() + this._cells.getCellsCount() + this._bottomLeft.getCellsCount() + this._columnFooter.getCellsCount()
          }, t.prototype.render = function(e, t) {
            var r = Math.max(0, Math.max(this._topLeft.renderSize.width, this._rowHeader.renderSize.width) - this._borderWidth),
              o = Math.max(0, Math.max(this._topLeft.renderSize.height, this._columnHeader.renderSize.height) - this._borderWidth);
            this._topLeft.render(e, 0, 0, t), this._rowHeader.render(e, 0, o, t), this._columnHeader.render(e, r, 0, t), this._cells.render(e, r, o, t), o = Math.max(0, o + this._cells.renderSize.height - this._borderWidth), this._bottomLeft.render(e, 0, o, t), this._columnFooter.render(e, r, o, t)
          }, Object.defineProperty(t.prototype, "flex", {
            get: function() {
              return this._flex
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "renderSize", {
            get: function() {
              var t = Math.max(this._topLeft.renderSize.height, this._columnHeader.renderSize.height) + Math.max(this._rowHeader.renderSize.height, this._cells.renderSize.height) + Math.max(this._bottomLeft.renderSize.height, this._columnFooter.renderSize.height),
                r = Math.max(this._topLeft.renderSize.width, this._rowHeader.renderSize.width) + Math.max(this._columnHeader.renderSize.width, this._cells.renderSize.width);
              return this._columnHeader.visibleRows > 0 && (t -= this._borderWidth), this._columnFooter.visibleRows > 0 && (t -= this._borderWidth), this._rowHeader.visibleColumns > 0 && (r -= this._borderWidth), new e.Size(r, t)
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "showColumnHeader", {
            get: function() {
              return this._flex.showColumnHeader
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "showRowHeader", {
            get: function() {
              return this._flex.showRowHeader
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "showColumnFooter", {
            get: function() {
              return this._lastPage && this._flex.showColumnFooter
            },
            enumerable: !0,
            configurable: !0
          }), t.prototype.alignMergedTextToTheTopRow = function(e) {
            return this._flex.alignMergedTextToTheTopRow(e)
          }, t.prototype.getColumn = function(e, t, r) {
            return this._flex.getColumn(e, t, r)
          }, t.prototype.isAlternatingRow = function(e) {
            return this._flex.isAlternatingRow(e)
          }, t.prototype.isGroupRow = function(e) {
            return this._flex.isGroupRow(e)
          }, t.prototype.isNewRow = function(e) {
            return this._flex.isNewRow(e)
          }, t.prototype.isExpandableGroupRow = function(e) {
            return this._flex.isExpandableGroupRow(e)
          }, t.prototype.isBooleanCell = function(e, t, r) {
            return this._flex.isBooleanCell(e, t, r)
          }, t.prototype.getCellStyle = function(e, t, r) {
            return this._flex.getCellStyle(e, t, r)
          }, t
        }(),
        u = function(t) {
          function r(e, r, o, n) {
            var i = t.call(this, r, o) || this;
            return i._gr = e, i._borderWidth = n, i
          }
          return __extends(r, t), Object.defineProperty(r.prototype, "gr", {
            get: function() {
              return this._gr
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(r.prototype, "renderSize", {
            get: function() {
              return null == this._renderSize && (this._renderSize = this.size.clone(), this.visibleColumns > 1 && (this._renderSize.width -= this._borderWidth * (this.visibleColumns - 1)), this.visibleRows > 1 && (this._renderSize.height -= this._borderWidth * (this.visibleRows - 1))), this._renderSize
            },
            enumerable: !0,
            configurable: !0
          }), r.prototype.getRangeWidth = function(t, r) {
            for (var o = 0, n = 0, i = this.panel, l = t; l <= r; l++) {
              var s = i.columns[l];
              s.isVisible && (n++, o += s.renderWidth)
            }
            return o = e.pdf.pxToPt(o), n > 1 && (o -= this._borderWidth * (n - 1)), o
          }, r.prototype.getRangeHeight = function(t, r) {
            for (var o = 0, n = 0, i = this.panel, l = t; l <= r; l++) {
              var s = i.rows[l];
              this.isRenderableRow(s) && (n++, o += s.renderHeight)
            }
            return o = e.pdf.pxToPt(o), n > 1 && (o -= this._borderWidth * (n - 1)), o
          }, r.prototype.getCellsCount = function() {
            var e = this,
              t = 0,
              r = this.range,
              o = this.panel,
              n = new p(this._gr.flex);
            return r.isValid ? (r.forEach(o, function(i, l, s) {
              if (e.isRenderableRow(i))
                for (var a = r.leftCol; a <= r.rightCol; a++) {
                  var u = !1,
                    c = void 0;
                  if (e.gr.getColumn(o, s, a).isVisible) {
                    var p = null;
                    (p = n.getMergedRange(o, s, a)) ? p.topRow !== p.bottomRow ? p.firstVisibleRow !== s && s !== l.topRow || (u = !0) : (u = !0, c = Math.min(r.rightCol, p.rightCol)): u = !0, u && t++, c && (a = c)
                  }
                }
            }), t) : t
          }, r.prototype.render = function(t, r, o, n) {
            var i = this,
              l = this.range,
              s = this.panel,
              a = new p(this._gr.flex),
              u = new h(s, 0, 0, 0, 0),
              f = new c(this, t, this._borderWidth);
            if (l.isValid) {
              for (var d = {}, g = l.leftCol; g <= l.rightCol; g++) d[g] = o;
              l.forEach(s, function(t, o, c) {
                if (i.isRenderableRow(t))
                  for (var p = r, h = l.leftCol; h <= l.rightCol; h++) {
                    var g, b = i.gr.getColumn(s, c, h),
                      _ = void 0,
                      w = void 0,
                      y = !1,
                      m = void 0;
                    if (b.isVisible) {
                      var C = i._gr.flex.getCellContent(i.panel, t, b, h),
                        v = null;
                      if (v = a.getMergedRange(s, c, h))
                        if (u.copyFrom(v), v.topRow !== v.bottomRow) v.firstVisibleRow === c || c === o.topRow ? (y = !0, g = i.gr.settings.repeatMergedValuesAcrossPages ? C : v.firstVisibleRow === c ? C : "", _ = i.getRangeHeight(c, Math.min(v.bottomRow, o.bottomRow)), w = i.getRangeWidth(h, h)) : w = i.getRangeWidth(h, h);
                        else {
                          y = !0, g = i.gr.settings.repeatMergedValuesAcrossPages ? C : h === v.leftCol ? C : "", _ = i.getRangeHeight(c, c), w = i.getRangeWidth(Math.max(l.leftCol, v.leftCol), Math.min(l.rightCol, v.rightCol)), m = Math.min(l.rightCol, v.rightCol);
                          for (var x = h + 1; x <= m; x++) d[x] += _ - i._borderWidth
                        } else u.setRange(c, h, c, h), y = !0, g = C, _ = i.getRangeHeight(c, c), w = i.getRangeWidth(h, h);
                      y && (f.renderCell(g, t, b, u, new e.Rect(p, d[h], w, _)), n && n()), _ && (d[h] += _ - i._borderWidth), w && (p += w - i._borderWidth), m && (h = m)
                    }
                  }
              })
            }
          }, r.prototype.isRenderableRow = function(e) {
            return this._gr.isRenderableRow(e)
          }, r
        }(function() {
          function t(e, t) {
            this._panel = e, this._range = t.clone()
          }
          return Object.defineProperty(t.prototype, "visibleRows", {
            get: function() {
              var e = this;
              return null == this._visibleRows && (this._visibleRows = 0, this._range.forEach(this._panel, function(t) {
                e.isRenderableRow(t) && e._visibleRows++
              })), this._visibleRows
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "visibleColumns", {
            get: function() {
              if (null == this._visibleColumns && (this._visibleColumns = 0, this._range.isValid))
                for (var e = this._range.leftCol; e <= this._range.rightCol; e++) this._panel.columns[e].isVisible && this._visibleColumns++;
              return this._visibleColumns
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "size", {
            get: function() {
              if (null == this._size) {
                var t = this._range.getRenderSize(this._panel);
                this._size = new e.Size(e.pdf.pxToPt(t.width), e.pdf.pxToPt(t.height))
              }
              return this._size
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "range", {
            get: function() {
              return this._range
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "panel", {
            get: function() {
              return this._panel
            },
            enumerable: !0,
            configurable: !0
          }), t.prototype.isRenderableRow = function(e) {
            return e.isVisible
          }, t
        }()),
        c = function() {
          function t(e, t, r) {
            this._pr = e, this._area = t, this._borderWidth = r
          }
          return t.prototype.renderCell = function(t, r, o, n, s) {
            var a, u = this._pr.gr.flex,
              c = this._pr.panel,
              p = function() {
                return u.getCell(c, n.topRow, n.leftCol)
              },
              h = null,
              f = this._pr.gr.getCellStyle(c, r, o);
            if (this._pr.gr.settings.customCellContent) {
              var d = u.getComputedStyle(h = p());
              f.color = d.color, f.backgroundColor = d.backgroundColor, f.borderColor = d.borderColor || d.borderRightColor || d.borderBottomColor || d.borderLeftColor || d.borderTopColor, f.font = new e.pdf.PdfFont(d.fontFamily, e.pdf._asPt(d.fontSize, !0, void 0), d.fontStyle, d.fontWeight), f.textAlign = d.textAlign
            }
            if (f.font && !(f.font instanceof e.pdf.PdfFont)) {
              var g = f.font;
              f.font = new e.pdf.PdfFont(g.family, g.size, g.style, g.weight)
            }
            if (f.boxSizing = "border-box", f.borderWidth = this._borderWidth, f.borderStyle = "solid", f.textAlign || u.isGroupRow(r) && !o.aggregate || (f.textAlign = o.getAlignment()), c.cellType === i.Cell && u.rows.maxGroupLevel >= 0 && n.leftCol === u.columns.firstVisibleIndex) {
              var b = u.isGroupRow(r) ? Math.max(r.level, 0) : u.rows.maxGroupLevel + 1,
                _ = e.pdf._asPt(f.paddingLeft || f.padding),
                w = e.pdf.pxToPt(b * u.treeIndent);
              console.log(b), f.paddingLeft = _ + w
            }
            var y, m = this._measureCell(t, c, r, o, f, s),
              C = n.rowSpan > 1 && n.visibleRowsCount > 1 && this._pr.gr.alignMergedTextToTheTopRow(c);
            C && (y = new e.Rect(m.contentRect.left, m.contentRect.top, m.contentRect.width, m.contentRect.height / (n.visibleRowsCount || 1)), m.textRect = this._calculateTextRect(t, c, r, o, y, f)), e.isFunction(this._pr.gr.settings.formatItem) && ((a = new l(c, n, h, this._area, m.rect, m.contentRect, m.textRect.top, f, function() {
              return h || p()
            })).data = t, this._pr.gr.settings.formatItem(a), a.data !== t && (t = e.asString(a.data), m.textRect = this._calculateTextRect(t, c, r, o, C ? y : m.contentRect, f))), this._renderCell(t, r, o, n, m, f, !a || !a.cancel, !a || !a.cancelBorders)
          }, t.prototype._renderCell = function(e, t, r, o, n, i, l, s) {
            (l || s) && (this._isBooleanCellAndValue(e, this._pr.panel, t, r) ? this._renderBooleanCell(e, n, i, l, s) : this._renderTextCell(e, n, i, l, s))
          }, t.prototype._isBooleanCellAndValue = function(e, t, r, o) {
            return this._pr.gr.isBooleanCell(t, r, o) && this._isBoolean(e)
          }, t.prototype._isBoolean = function(t) {
            var r = e.isString(t) && t.toLowerCase();
            return "true" === r || "false" === r || !0 === t || !1 === t
          }, t.prototype._measureCell = function(t, r, o, n, i, l) {
            this._decompositeStyle(i);
            var s = l.left,
              a = l.top,
              u = l.height,
              c = l.width,
              p = this._parseBorder(i),
              h = p.left.width,
              f = p.top.width,
              d = p.bottom.width,
              g = p.right.width,
              b = this._parsePadding(i),
              _ = 0,
              w = 0,
              y = 0,
              m = 0;
            if ("content-box" === i.boxSizing || void 0 === i.boxSizing) _ = b.top + u + b.bottom, w = b.left + c + b.right, y = u, m = c;
            else {
              if ("border-box" !== i.boxSizing) throw "Invalid value: " + i.boxSizing;
              e.pdf._IE && i instanceof CSSStyleDeclaration ? (_ = b.top + b.bottom + u, w = b.left + b.right + c) : (_ = u - f - d, w = c - h - g), y = _ - b.top - b.bottom, m = w - b.left - b.right
            }
            var l = new e.Rect(s, a, c, u),
              C = new e.Rect(s + h, a + f, w, _),
              v = new e.Rect(s + h + b.left, a + f + b.top, m, y);
            return {
              rect: l,
              clientRect: C,
              contentRect: v,
              textRect: this._calculateTextRect(t, r, o, n, v, i)
            }
          }, t.prototype._decompositeStyle = function(e) {
            if (e) {
              var t;
              (t = e.borderColor) && (e.borderLeftColor || (e.borderLeftColor = t), e.borderRightColor || (e.borderRightColor = t), e.borderTopColor || (e.borderTopColor = t), e.borderBottomColor || (e.borderBottomColor = t)), (t = e.borderWidth) && (e.borderLeftWidth || (e.borderLeftWidth = t), e.borderRightWidth || (e.borderRightWidth = t), e.borderTopWidth || (e.borderTopWidth = t), e.borderBottomWidth || (e.borderBottomWidth = t)), (t = e.borderStyle) && (e.borderLeftStyle || (e.borderLeftStyle = t), e.borderRightStyle || (e.borderRightStyle = t), e.borderTopStyle || (e.borderTopStyle = t), e.borderBottomStyle || (e.borderBottomStyle = t)), (t = e.padding) && (e.paddingLeft || (e.paddingLeft = t), e.paddingRight || (e.paddingRight = t), e.paddingTop || (e.paddingTop = t), e.paddingBottom || (e.paddingBottom = t))
            }
          }, t.prototype._parseBorder = function(t) {
            var r = {
              left: {
                width: 0
              },
              top: {
                width: 0
              },
              bottom: {
                width: 0
              },
              right: {
                width: 0
              }
            };
            return "none" !== t.borderLeftStyle && (r.left = {
              width: e.pdf._asPt(t.borderLeftWidth),
              style: t.borderLeftStyle,
              color: t.borderLeftColor
            }), "none" !== t.borderTopStyle && (r.top = {
              width: e.pdf._asPt(t.borderTopWidth),
              style: t.borderTopStyle,
              color: t.borderTopColor
            }), "none" !== t.borderBottomStyle && (r.bottom = {
              width: e.pdf._asPt(t.borderBottomWidth),
              style: t.borderBottomStyle,
              color: t.borderBottomColor
            }), "none" !== t.borderRightStyle && (r.right = {
              width: e.pdf._asPt(t.borderRightWidth),
              style: t.borderRightStyle,
              color: t.borderRightColor
            }), r
          }, t.prototype._parsePadding = function(t) {
            return {
              left: e.pdf._asPt(t.paddingLeft),
              top: e.pdf._asPt(t.paddingTop),
              bottom: e.pdf._asPt(t.paddingBottom),
              right: e.pdf._asPt(t.paddingRight)
            }
          }, t.prototype._renderEmptyCell = function(t, r, o, n) {
            var i = t.rect.left,
              l = t.rect.top,
              s = t.clientRect.width,
              a = t.clientRect.height,
              u = t.clientRect.left - t.rect.left,
              c = t.clientRect.top - t.rect.top,
              p = t.rect.top + t.rect.height - (t.clientRect.top + t.clientRect.height),
              h = t.rect.left + t.rect.width - (t.clientRect.left + t.clientRect.width);
            if (n && (u || h || p || c)) {
              var f = r.borderLeftColor || r.borderColor,
                d = r.borderRightColor || r.borderColor,
                g = r.borderTopColor || r.borderColor,
                b = r.borderBottomColor || r.borderColor;
              if (u && c && p && h && u === h && u === p && u === c && f === d && f === b && f === g) {
                var _ = u,
                  w = _ / 2;
                this._area.paths.rect(i + w, l + w, s + _, a + _).stroke(new e.pdf.PdfPen(f, _))
              } else u && this._area.paths.polygon([
                [i, l],
                [i + u, l + c],
                [i + u, l + c + a],
                [i, l + c + a + p]
              ]).fill(f), c && this._area.paths.polygon([
                [i, l],
                [i + u, l + c],
                [i + u + s, l + c],
                [i + u + s + h, l]
              ]).fill(g), h && this._area.paths.polygon([
                [i + u + s + h, l],
                [i + u + s, l + c],
                [i + u + s, l + c + a],
                [i + u + s + h, l + c + a + p]
              ]).fill(d), p && this._area.paths.polygon([
                [i, l + c + a + p],
                [i + u, l + c + a],
                [i + u + s, l + c + a],
                [i + u + s + h, l + c + a + p]
              ]).fill(b)
            }
            o && r.backgroundColor && s > 0 && a > 0 && this._area.paths.rect(i + u, l + c, s, a).fill(r.backgroundColor)
          }, t.prototype._renderBooleanCell = function(t, r, o, n, i) {
            if (this._renderEmptyCell(r, o, n, i), n) {
              var l = r.textRect.left,
                s = r.textRect.top,
                a = r.textRect.height;
              if (this._area.paths.rect(l, s, a, a).fillAndStroke(e.Color.fromRgba(255, 255, 255), new e.pdf.PdfPen(void 0, .5)), !0 === e.changeType(t, e.DataType.Boolean, "")) {
                var u = a / 20,
                  c = a - .5 - 2 * u,
                  p = a / 8;
                this._area.document.saveState(), this._area.translate(l + .25 + u, s + .25 + u).paths.moveTo(p / 2, .6 * c).lineTo(c - .6 * c, c - p).lineTo(c - p / 2, p / 2).stroke(new e.pdf.PdfPen(void 0, p)), this._area.document.restoreState()
              }
            }
          }, t.prototype._renderTextCell = function(t, r, o, n, i) {
            this._renderEmptyCell(r, o, n, i), n && t && this._area.drawText(t, r.textRect.left, r.textRect.top, {
              brush: o.color,
              font: o.font,
              height: r.textRect.height,
              width: r.textRect.width,
              align: "center" === o.textAlign ? e.pdf.PdfTextHorizontalAlign.Center : "right" === o.textAlign ? e.pdf.PdfTextHorizontalAlign.Right : "justify" === o.textAlign ? e.pdf.PdfTextHorizontalAlign.Justify : e.pdf.PdfTextHorizontalAlign.Left
            })
          }, t.prototype._calculateTextRect = function(e, t, r, o, n, i) {
            var l = n.clone();
            if (this._isBooleanCellAndValue(e, t, r, o)) {
              var s = this._getTextLineHeight(i.font);
              switch (i.verticalAlign) {
                case "middle":
                  l.top = n.top + n.height / 2 - s / 2;
                  break;
                case "bottom":
                  l.top = n.top + n.height - s
              }
              switch (i.textAlign) {
                case "justify":
                case "center":
                  l.left = n.left + n.width / 2 - s / 2;
                  break;
                case "right":
                  l.left = n.left + n.width - s
              }
              l.height = l.width = s
            } else if (l.height > 0 && l.width > 0) {
              switch (i.verticalAlign) {
                case "bottom":
                  (a = this._area.measureText(e, i.font, {
                    height: l.height,
                    width: l.width
                  })).size.height < l.height && (l.top += l.height - a.size.height, l.height = a.size.height);
                  break;
                case "middle":
                  var a = this._area.measureText(e, i.font, {
                    height: l.height,
                    width: l.width
                  });
                  a.size.height < l.height && (l.top += l.height / 2 - a.size.height / 2, l.height = a.size.height)
              }
              o.wordWrap || (l.height = this._getTextLineHeight(i.font))
            }
            return l
          }, t.prototype._extractCheckboxValue = function(e) {
            var t = e.querySelector("input.wj-cell-check[type=checkbox]");
            if (t) {
              var r = window.getComputedStyle(t);
              if ("none" !== r.display && "hidden" !== r.visibility) return t.checked
            }
          }, t.prototype._getTextLineHeight = function(e) {
            return this._area.lineHeight(e)
          }, t
        }(),
        p = function() {
          function e(e) {
            this._columns = {}, this._flex = e
          }
          return e.prototype.getMergedRange = function(e, t, r) {
            var o = this._columns[r];
            if (o && t >= o.topRow && t <= o.bottomRow) return o;
            var n = this._flex.getMergedRange(e, t, r);
            return this._columns[r] = n ? new h(e, n) : null
          }, e
        }(),
        h = function() {
          function t(e, t, r, o, n) {
            if ("number" == typeof t ? (this._row = t, this._col = r, this._row2 = o, this._col2 = n) : (this._row = t.row, this._col = t.col, this._row2 = t.row2, this._col2 = t.col2), this.firstVisibleRow = -1, this.visibleRowsCount = 0, e) {
              var i = this.topRow,
                l = this.bottomRow,
                s = e.rows.length;
              if (s > 0)
                for (var a = i; a <= l && a < s; a++) e.rows[a].isVisible && (this.firstVisibleRow < 0 && (this.firstVisibleRow = a), this.visibleRowsCount++)
            }
          }
          return Object.defineProperty(t.prototype, "row", {
            get: function() {
              return this._row
            },
            set: function(e) {
              this._row = e
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "col", {
            get: function() {
              return this._col
            },
            set: function(e) {
              this._col = e
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "row2", {
            get: function() {
              return this._row2
            },
            set: function(e) {
              this._row2 = e
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "col2", {
            get: function() {
              return this._col2
            },
            set: function(e) {
              this._col2 = e
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "topRow", {
            get: function() {
              return Math.min(this._row, this._row2)
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "bottomRow", {
            get: function() {
              return Math.max(this._row, this._row2)
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "leftCol", {
            get: function() {
              return Math.min(this._col, this._col2)
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "rightCol", {
            get: function() {
              return Math.max(this._col, this._col2)
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "rowSpan", {
            get: function() {
              return Math.abs(this._row2 - this._row) + 1
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "isValid", {
            get: function() {
              return this._row > -1 && this._col > -1 && this._row2 > -1 && this._col2 > -1
            },
            enumerable: !0,
            configurable: !0
          }), t.prototype.copyFrom = function(e) {
            this.setRange(e.row, e.col, e.row2, e.col2), this.firstVisibleRow = e.firstVisibleRow, this.visibleRowsCount = e.visibleRowsCount
          }, t.prototype.clone = function() {
            return new t(null, this._row, this._col, this._row2, this._col2)
          }, t.prototype.getRenderSize = function(t) {
            var r = new e.Size(0, 0);
            if (this.isValid) {
              for (var o = this.topRow; o <= this.bottomRow; o++) r.height += t.rows[o].renderHeight;
              for (var n = this.leftCol; n <= this.rightCol; n++) r.width += t.columns[n].renderWidth
            }
            return r
          }, t.prototype.setRange = function(t, r, o, n) {
            void 0 === t && (t = -1), void 0 === r && (r = -1), void 0 === o && (o = t), void 0 === n && (n = r), this._row = e.asInt(t), this._col = e.asInt(r), this._row2 = e.asInt(o), this._col2 = e.asInt(n)
          }, t
        }();
      t._CellRange = h;
      var f = function() {
          function t(e) {
            this._ranges = e || []
          }
          return t.prototype.length = function() {
            for (var e = 0, t = 0; t < this._ranges.length; t++) {
              var r = this._ranges[t];
              r.isValid && (e += r.bottomRow - r.topRow + 1)
            }
            return e
          }, Object.defineProperty(t.prototype, "isValid", {
            get: function() {
              return this._ranges.length && this._ranges[0].isValid
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "leftCol", {
            get: function() {
              return this._ranges.length ? this._ranges[0].leftCol : -1
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "rightCol", {
            get: function() {
              return this._ranges.length ? this._ranges[0].rightCol : -1
            },
            enumerable: !0,
            configurable: !0
          }), t.prototype.clone = function(e, r) {
            for (var o = [], n = 0; n < this._ranges.length; n++) {
              var i = this._ranges[n].clone();
              arguments.length > 0 && (i.col = e), arguments.length > 1 && (i.col2 = r), o.push(i)
            }
            return new t(o)
          }, t.prototype.getRenderSize = function(t) {
            for (var r = new e.Size(0, 0), o = 0; o < this._ranges.length; o++) {
              var n = this._ranges[o].getRenderSize(t);
              r.width = Math.max(r.width, n.width), r.height += n.height
            }
            return r
          }, t.prototype.forEach = function(e, t) {
            for (var r = 0, o = 0; o < this._ranges.length; o++) {
              var n = this._ranges[o];
              if (n.isValid)
                for (var i = n.topRow; i <= n.bottomRow; i++) t(e.rows[i], n, i, r++)
            }
          }, t.prototype.subrange = function(e, r, o, n) {
            var i = [];
            if (e >= 0 && r > 0)
              for (var l = 0, s = 0, a = 0; a < this._ranges.length && r > 0; a++, l = s + 1) {
                var u = this._ranges[a];
                if (s = l + (u.bottomRow - u.topRow), !(e > s)) {
                  var c = e > l ? u.topRow + (e - l) : u.topRow,
                    p = Math.min(u.bottomRow, c + r - 1),
                    f = arguments.length > 2 ? o : u.leftCol,
                    d = arguments.length > 2 ? n : u.rightCol;
                  i.push(new h(null, c, f, p, d)), r -= p - c + 1
                }
              }
            return new t(i)
          }, t
        }(),
        d = function() {
          function e(e, t, r) {
            this._col = t, this._row = r, this._range = e
          }
          return Object.defineProperty(e.prototype, "range", {
            get: function() {
              return this._range
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(e.prototype, "pageCol", {
            get: function() {
              return this._col
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(e.prototype, "pageRow", {
            get: function() {
              return this._row
            },
            enumerable: !0,
            configurable: !0
          }), e
        }()
    }(t.pdf || (t.pdf = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {})),
  function(e) {
    ! function(t) {
      ! function(r) {
        function o() {
          return i || ((i = document.createElement("div")).setAttribute(t.FlexGrid._WJS_MEASURE, "true"), document && document.body && document.body.appendChild(i)), i.className = "", i.innerHTML = "", i.style.cssText = "", i.style.visibility = "hidden", i
        }

        function n() {
          i && (e.removeChild(i), i = null)
        }
        var i;
        r._removeFakeCell = n;
        var l = function() {
          function o() {}
          return o.draw = function(e, t, r, o, n) {
            this.drawToPosition(e, t, null, r, o, n)
          }, o.drawToPosition = function(o, i, l, s, a, u) {
            e.assert(!!o, "The flex argument cannot be null."), e.assert(!!i, "The doc argument cannot be null.");
            var c = void 0 !== e.grid.FlexGrid && o instanceof t.FlexGrid;
            try {
              u = r._FlexGridPdfConverterCore._applyDefaultDrawSettings(u);
              var p = o;
              c && (u && u.recalculateStarWidths && o.columns._updateStarSizes(e.pdf.ptToPx(i.width)), p = this._getFlexGridAdapter(o, u)), r._FlexGridPdfConverterCore.draw(p, i, l, s, a, u)
            } finally {
              n(), c && u && u.recalculateStarWidths && o.invalidate(!0)
            }
          }, o.export = function(t, r, o) {
            e.assert(!!t, "The flex argument cannot be null."), e.assert(!!r, "The fileName argument cannot be empty.");
            var n = (o = this._applyDefaultExportSettings(o)).documentOptions.ended;
            o.documentOptions.ended = function(t, l) {
              o.progress && o.progress(1), n && !1 === n.apply(i, [t, l]) || e.pdf.saveBlob(l.blob, r), t.dispose()
            };
            var i = new e.pdf.PdfDocument(o.documentOptions);
            this.drawToPosition(t, i, null, null, null, o), i.end()
          }, o._getFlexGridAdapter = function(r, o) {
            return new(t.multirow && r instanceof t.multirow.MultiRow && u || t.sheet && r instanceof t.sheet.FlexSheet && a || e.olap && r instanceof e.olap.PivotGrid && c || s)(r, o)
          }, o._applyDefaultExportSettings = function(e) {
            return r._merge(r._merge({}, e), this._DefaultExportSettings)
          }, o._DefaultExportSettings = r._merge({
            scaleMode: r.ScaleMode.PageWidth,
            documentOptions: {
              compress: !1,
              pageSettings: {
                margins: {
                  left: 36,
                  right: 36,
                  top: 18,
                  bottom: 18
                }
              }
            },
            _progressMax: .9
          }, r._FlexGridPdfConverterCore.DefaultDrawSettings), o
        }();
        r.FlexGridPdfConverter = l;
        var s = function() {
            function n(e, t) {
              this._flex = e, this._settings = t
            }
            return Object.defineProperty(n.prototype, "flex", {
              get: function() {
                return this._flex
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "settings", {
              get: function() {
                return this._settings
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "columns", {
              get: function() {
                return this._flex.columns
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "rows", {
              get: function() {
                return this._flex.rows
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "bottomLeftCells", {
              get: function() {
                return this._flex.bottomLeftCells
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "cells", {
              get: function() {
                return this._flex.cells
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "columnFooters", {
              get: function() {
                return this._flex.columnFooters
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "columnHeaders", {
              get: function() {
                return this._flex.columnHeaders
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "rowHeaders", {
              get: function() {
                return this._flex.rowHeaders
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "topLeftCells", {
              get: function() {
                return this._flex.topLeftCells
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "treeIndent", {
              get: function() {
                return this._flex.treeIndent
              },
              enumerable: !0,
              configurable: !0
            }), n.prototype.getSelection = function() {
              var e = [],
                r = this._flex.selection;
              switch (this._flex.selectionMode) {
                case t.SelectionMode.None:
                  break;
                case t.SelectionMode.Cell:
                case t.SelectionMode.CellRange:
                  e.push(r);
                  break;
                case t.SelectionMode.Row:
                  e.push(new t.CellRange(r.topRow, 0, r.topRow, this._flex.cells.columns.length - 1));
                  break;
                case t.SelectionMode.RowRange:
                  e.push(new t.CellRange(r.topRow, 0, r.bottomRow, this._flex.cells.columns.length - 1));
                  break;
                case t.SelectionMode.ListBox:
                  for (var o = -1, n = 0; n < this._flex.rows.length; n++) this._flex.rows[n].isSelected ? (o < 0 && (o = n), n === this._flex.rows.length - 1 && e.push(new t.CellRange(o, 0, n, this._flex.cells.columns.length - 1))) : (o >= 0 && e.push(new t.CellRange(o, 0, n - 1, this._flex.cells.columns.length - 1)), o = -1)
              }
              return e
            }, n.prototype.getCell = function(e, t, r) {
              var n = e.getCellElement(t, r);
              return n || (n = o(), this._flex.cellFactory.updateCell(e, t, r, n)), n
            }, n.prototype.getComputedStyle = function(e) {
              return e.className = e.className.replace("wj-state-selected", ""), e.className = e.className.replace("wj-state-multi-selected", ""), window.getComputedStyle(e)
            }, n.prototype.getMergedRange = function(e, t, r) {
              return this._flex.getMergedRange(e, t, r, !1)
            }, Object.defineProperty(n.prototype, "showColumnHeader", {
              get: function() {
                return !!(this._flex.headersVisibility & t.HeadersVisibility.Column)
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "showRowHeader", {
              get: function() {
                return !!(this._flex.headersVisibility & t.HeadersVisibility.Row)
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "showColumnFooter", {
              get: function() {
                return this._flex.columnFooters.rows.length > 0
              },
              enumerable: !0,
              configurable: !0
            }), n.prototype.alignMergedTextToTheTopRow = function(e) {
              return !1
            }, n.prototype.getCellData = function(e, t, r) {
              return e.getCellData(r, t, !0)
            }, n.prototype.getCellContent = function(e, r, n, i) {
              var l = this.getCellData(e, i, r.index),
                s = n.isContentHtml;
              if (!l && e.cellType === t.CellType.Cell) {
                var a = r;
                a instanceof t.GroupRow && a.dataItem && a.dataItem.groupDescription && n.index === e.columns.firstVisibleIndex && (s = !0, l = a.getGroupHeader())
              }
              if (s)(u = o()).innerHTML = l, l = u.textContent.trim();
              else if (this.settings.customCellContent) {
                var u = this.getCell(e, r.index, i);
                !(l = u.textContent.trim()) && this.isBooleanCell(e, r, n) && (l = this._extractCheckboxValue(u) + "")
              }
              return l
            }, n.prototype.isBooleanCell = function(r, o, n) {
              return n.dataType === e.DataType.Boolean && r.cellType === t.CellType.Cell && !this.isExpandableGroupRow(o)
            }, n.prototype.getColumn = function(e, t, r) {
              return e.columns[r]
            }, n.prototype.isAlternatingRow = function(e) {
              return e.index % 2 != 0
            }, n.prototype.isGroupRow = function(e) {
              return e instanceof t.GroupRow
            }, n.prototype.isNewRow = function(e) {
              return e instanceof t._NewRowTemplate
            }, n.prototype.isDetailRow = function(e) {
              return t.detail && e instanceof t.detail.DetailRow
            }, n.prototype.isExpandableGroupRow = function(e) {
              return e instanceof t.GroupRow && e.hasChildren
            }, n.prototype.getCellStyle = function(e, o, n) {
              var i = this.settings.styles,
                l = r._merge({}, i.cellStyle),
                s = this._flex;
              switch (e.cellType) {
                case t.CellType.Cell:
                  this.isExpandableGroupRow(o) ? r._merge(l, i.groupCellStyle, !0) : this.isAlternatingRow(o) && r._merge(l, i.altCellStyle, !0);
                  break;
                case t.CellType.ColumnHeader:
                case t.CellType.RowHeader:
                case t.CellType.TopLeft:
                case t.CellType.BottomLeft:
                  r._merge(l, i.headerCellStyle, !0);
                  break;
                case t.CellType.ColumnFooter:
                  r._merge(l, i.headerCellStyle, !0), r._merge(l, i.footerCellStyle, !0)
              }
              return !this.settings.customCellContent && s._getShowErrors() && s._getError(e, o.index, n.index) && r._merge(l, i.errorCellStyle, !0), l
            }, n.prototype._extractCheckboxValue = function(e) {
              var t = e.querySelector("input.wj-cell-check[type=checkbox]");
              if (t) {
                var r = window.getComputedStyle(t);
                if ("none" !== r.display && "hidden" !== r.visibility) return t.checked
              }
            }, n
          }(),
          a = function(o) {
            function n() {
              return null !== o && o.apply(this, arguments) || this
            }
            return __extends(n, o), n.prototype.getCellData = function(e, r, n) {
              return e.cellType === t.CellType.Cell ? e.rows[n] instanceof t.sheet.HeaderRow ? this.flex.columnHeaders.getCellData(n, r, !0) : this.flex.getCellValue(n, r, !0) : o.prototype.getCellData.call(this, e, r, n)
            }, n.prototype.getCellStyle = function(t, n, i) {
              var l = o.prototype.getCellStyle.call(this, t, n, i),
                s = this.flex.selectedSheet.findTable(n.index, i.index);
              if (s) {
                var a = s.getRange(),
                  u = n.index - a.topRow,
                  c = i.index - a.leftCol,
                  p = s._getTableCellAppliedStyles(u, c);
                p && (p.font = new e.pdf.PdfFont(p.fontFamily, e.pdf._asPt(p.fontSize, !0, void 0), p.fontStyle, p.fontWeight)), r._merge(l, p, !0)
              }
              return l
            }, Object.defineProperty(n.prototype, "showColumnHeader", {
              get: function() {
                return !1
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "showRowHeader", {
              get: function() {
                return !1
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(n.prototype, "showColumnFooter", {
              get: function() {
                return !1
              },
              enumerable: !0,
              configurable: !0
            }), n
          }(s),
          u = function(e) {
            function r() {
              return null !== e && e.apply(this, arguments) || this
            }
            return __extends(r, e), r.prototype.getColumn = function(e, t, r) {
              return this.flex.getBindingColumn(e, t, r)
            }, r.prototype.isAlternatingRow = function(r) {
              return r instanceof t.multirow._MultiRow ? r.dataIndex % 2 != 0 : e.prototype.isAlternatingRow.call(this, r)
            }, r
          }(s),
          c = function(e) {
            function r() {
              return null !== e && e.apply(this, arguments) || this
            }
            return __extends(r, e), r.prototype.alignMergedTextToTheTopRow = function(e) {
              return !this.flex.centerHeadersVertically && (e.cellType === t.CellType.ColumnHeader || e.cellType === t.CellType.RowHeader)
            }, r
          }(s)
      }(t.pdf || (t.pdf = {}))
    }(e.grid || (e.grid = {}))
  }(wijmo || (wijmo = {})),
  function(e) {
    ! function(t) {
      ! function(r) {
        function o(e) {
          for (var t = new Uint16Array(e), r = "", o = 0, n = t.length; o < n; o++) r += String.fromCharCode(t[o]);
          return r
        }

        function n(e) {
          for (var t = new ArrayBuffer(2 * e.length), r = new Uint16Array(t), o = 0, n = e.length; o < n; o++) r[o] = e.charCodeAt(o);
          return t
        }

        function i(t, r) {
          e.asFunction(r);
          var o = new FileReader;
          o.onload = function(e) {
            r(e.target.result)
          }, o.readAsArrayBuffer(t)
        }
        var l;
        ! function(e) {
          e[e.Done = 1] = "Done", e[e.Progress = 2] = "Progress"
        }(l || (l = {}));
        var s = function(e) {
          function t(t) {
            var r = e.call(this) || this;
            return r._buf = t, r
          }
          return __extends(t, e), Object.defineProperty(t.prototype, "blob", {
            get: function() {
              return this._blob || (this._blob = new Blob([new Uint8Array(this._buf)], {
                type: "application/pdf"
              })), this._blob
            },
            enumerable: !0,
            configurable: !0
          }), Object.defineProperty(t.prototype, "buffer", {
            get: function() {
              return this._buf
            },
            enumerable: !0,
            configurable: !0
          }), t
        }(e.EventArgs);
        r.PdfWebWorkerExportDoneEventArgs = s;
        var a = function() {
          function o() {}
          return o.exportGrid = function(t, o, n, i, l, s) {
            (i = r._merge({}, i)).progress = i.progress || s, this.addGrid(t, o, "", i), this.export(t, i.documentOptions, function(t) {
              e.isFunction(l) && !1 === l(t) || e.pdf.saveBlob(t.blob, n)
            }, s)
          }, o.export = function(t, r, o, n) {
            t.addEventListener("message", function r(i) {
              var a = i.data;
              switch (a.status) {
                case l.Done:
                  t.removeEventListener("message", r), e.isFunction(o) && o(new s(a.data));
                  break;
                case l.Progress:
                  n && n(a.data);
                  break;
                default:
                  throw "Unknown status: " + a.status
              }
            });
            var i = this._clientDataToArrayBuffer(t);
            t.postMessage({
              content: i,
              settings: r
            }, [i]), this._clearClientData(t)
          }, o.addGrid = function(e, t, r, o) {
            var n = this._gridToJson(t, o);
            this._addClientData(e, JSON.stringify(n), r, o, !0)
          }, o.addImage = function(t, r, o, n) {
            var i = e.isString(r) && r.indexOf("data:image/svg") >= 0 ? r : e.pdf._PdfImageHelper.getDataUri(r);
            this._addClientData(t, i, o, n)
          }, o.addString = function(e, t, r) {
            this._addClientData(e, t, r, null)
          }, o.serializeGrid = function(e, t) {
            var r = this._gridToJson(e, t);
            return n(JSON.stringify(r))
          }, o._addClientData = function(t, r, o, n, i) {
            void 0 === i && (i = !1), e.asType(t, Worker), e.asString(o), e.asString(r, !0);
            var l = t;
            l.clientData = l.clientData || {}, l.clientData[o] = {
              content: r,
              settings: n ? JSON.stringify(n) : null
            };
            var s = l.clientData[o];
            i && (s.isGrid = !0, s.progressMessaging = e.isFunction(n.progress))
          }, o._clearClientData = function(e) {
            delete e.clientData
          }, o._clientDataToArrayBuffer = function(e) {
            var t = e;
            return n(JSON.stringify(t.clientData || {}))
          }, o._gridToJson = function(t, o) {
            o = r.FlexGridPdfConverter._applyDefaultExportSettings(o);
            var i;
            try {
              i = new e.pdf.PdfDocument(o.documentOptions), o && o.recalculateStarWidths && t.columns._updateStarSizes(e.pdf.ptToPx(i.width));
              var l = this._getJsonConverter(t, o).convert();
              n(JSON.stringify(l));
              return l
            } finally {
              r._removeFakeCell(), o && o.recalculateStarWidths && t.invalidate(!0), null != i && (i.dispose(), i = null)
            }
          }, o._getJsonConverter = function(o, n) {
            return new(t.multirow && o instanceof t.multirow.MultiRow && f || e.olap && o instanceof e.olap.PivotGrid && d || h)(o, r.FlexGridPdfConverter._getFlexGridAdapter(o, n))
          }, o
        }();
        r.PdfWebWorkerClient = a;
        var u = function() {
          function t() {}
          return t.initExportGrid = function() {
            var e = this;
            this.initExport(function(t, o) {
              var n = o[""];
              n.progressMessaging && (n.settings._progressMax = r.FlexGridPdfConverter._DefaultExportSettings._progressMax, n.settings.progress = function(t) {
                e.sendExportProgress(t)
              }, t.ended.addHandler(function() {
                e.sendExportProgress(1)
              })), r.FlexGridPdfConverter.draw(n.content, t, null, null, n.settings), t.end()
            })
          }, t.initExport = function(r) {
            self.addEventListener("message", function n(s) {
              self.removeEventListener("message", n);
              var a = s.data,
                u = JSON.parse(o(a.content)),
                c = a.settings || {};
              c.ended = function(e, t) {
                i(t.blob, function(e) {
                  self.postMessage({
                    data: e,
                    status: l.Done
                  }, [e])
                })
              };
              var p = new e.pdf.PdfDocument(c);
              Object.keys(u).forEach(function(e) {
                var r = u[e];
                r.settings && (r.settings = JSON.parse(r.settings)), r.isGrid && (r.settings && t._disableUnsupportedFeatures(r.settings), r.content = t._deserializeGridFromString(r.content, r.settings))
              }), r(p, u)
            })
          }, t.sendExportProgress = function(t) {
            t = e.clamp(t, 0, 1), self.postMessage({
              data: t,
              status: l.Progress
            })
          }, t.deserializeGrid = function(e, t) {
            return this._deserializeGridFromString(o(e), t)
          }, t._deserializeGridFromString = function(e, t) {
            return this._getJsonAdapter(JSON.parse(e), r.FlexGridPdfConverter._applyDefaultExportSettings(t))
          }, t._disableUnsupportedFeatures = function(e) {
            e.customCellContent = !1
          }, t._getJsonAdapter = function(e, t) {
            switch (e.typeName) {
              case "MultiRow":
                return new x(e, t);
              case "PivotGrid":
                return new C(e, t);
              default:
                return new m(e, t)
            }
          }, t
        }();
        r.PdfWebWorker = u;
        var c;
        ! function(e) {
          e[e.None = 0] = "None", e[e.Alternate = 1] = "Alternate", e[e.Group = 2] = "Group", e[e.ExpandableGroup = 4] = "ExpandableGroup", e[e.New = 8] = "New", e[e.Visible = 16] = "Visible", e[e.Detail = 32] = "Detail"
        }(c || (c = {}));
        var p;
        ! function(e) {
          e[e.None = 0] = "None", e[e.Visible = 1] = "Visible"
        }(p || (p = {}));
        var h = function() {
            function e(e, t) {
              this._flex = e, this._adapter = t
            }
            return e.prototype.convert = function() {
              var e = {};
              return e.typeName = this._getTypeName(this._flex), e.selection = this._serializeSelection(), e.showColumnFooter = this.adapter.showColumnFooter, e.showColumnHeader = this.adapter.showColumnHeader, e.showRowHeader = this.adapter.showRowHeader, e.treeIndent = this._flex.treeIndent, e.bottomLeftCells = this._serializePanel(this._flex.bottomLeftCells), e.cells = this._serializePanel(this._flex.cells), e.columnFooters = this._serializePanel(this._flex.columnFooters), e.columnHeaders = this._serializePanel(this._flex.columnHeaders), e.rowHeaders = this._serializePanel(this._flex.rowHeaders), e.topLeftCells = this._serializePanel(this._flex.topLeftCells), e
            }, Object.defineProperty(e.prototype, "adapter", {
              get: function() {
                return this._adapter
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "flex", {
              get: function() {
                return this._flex
              },
              enumerable: !0,
              configurable: !0
            }), e.prototype._getTypeName = function(e) {
              return e.constructor.toString().match(/function\s*(\w+)/)[1]
            }, e.prototype._getRowState = function(e) {
              var r = c.None;
              return this.adapter.isAlternatingRow(e) && (r |= c.Alternate), e instanceof t.GroupRow && (r |= c.Group), this.adapter.isDetailRow(e) && (r |= c.Detail), this.adapter.isExpandableGroupRow(e) && (r |= c.ExpandableGroup), e instanceof t._NewRowTemplate && (r |= c.New), e.isVisible && (r |= c.Visible), r
            }, e.prototype._getColumnState = function(e) {
              var t = p.None;
              return e.isVisible && (t |= p.Visible), t
            }, e.prototype._serializeSelection = function() {
              for (var e = this.adapter.getSelection(), t = [], r = 0; r < e.length; r++) {
                var o = e[r];
                t.push([o.row, o.col, o.row2, o.col2])
              }
              return t
            }, e.prototype._serializeColumns = function(e) {
              for (var t = [], r = 0, o = (e || []).length; r < o; r++) {
                var n = e[r],
                  i = {
                    aggregate: n.aggregate,
                    alignment: n.getAlignment(),
                    dataType: n.dataType,
                    renderWidth: n.renderWidth,
                    wordWrap: n.wordWrap
                  },
                  l = this._getColumnState(n);
                l != p.None && (i.state = l), t.push(i)
              }
              return t
            }, e.prototype._serializePanel = function(e) {
              var r = {
                cellType: e.cellType,
                height: e.height,
                width: e.width
              };
              r.mergedRanges = this._serializeMergedRanges(e), r.columns = this._serializeColumns(e.columns), r.columnsFirstVisibleIndex = e.columns.firstVisibleIndex, r.rows = [], r.rowsMaxGroupLevel = e.rows.maxGroupLevel;
              for (var o = 0, n = e.rows.length, i = e.columns.length; o < n; o++) {
                var l = e.rows[o],
                  s = {
                    renderHeight: l.renderHeight
                  };
                r.rows.push(s), l instanceof t.GroupRow && (s.level = l.level);
                var a = this._getRowState(l);
                a != c.None && (s.state = a), s.cells = [];
                for (var u = 0; u < i; u++) s.cells[u] = this._adapter.getCellContent(e, e.rows[o], e.columns[u], u)
              }
              return r
            }, e.prototype._serializeMergedRanges = function(e) {
              for (var t = [], r = [], o = 0; o < e.columns.length; o++) r[o] = 0;
              for (var n = 0; n < e.rows.length; n++)
                for (var i = 0; i < e.columns.length; i++)
                  if (!(n < r[i])) {
                    var l = e.grid.getMergedRange(e, n, i);
                    if (null != l && !l.isSingleCell) {
                      t.push([l.row, l.col, l.row2, l.col2]);
                      for (var s = l.col; s <= l.col2; s++) r[s] = l.row2 + 1;
                      l.col == l.col2 || (i = l.col2)
                    }
                  }
              return t
            }, e
          }(),
          f = function(e) {
            function r() {
              return null !== e && e.apply(this, arguments) || this
            }
            return __extends(r, e), r.prototype.convert = function() {
              var t = e.prototype.convert.call(this);
              return t.rowsPerItem = this.flex.rowsPerItem, t
            }, r.prototype._serializePanel = function(t) {
              var r = e.prototype._serializePanel.call(this, t);
              return r.cellGroups = this._serializeCellGroup(t), r
            }, r.prototype._serializeCellGroup = function(e) {
              var r = [],
                o = [],
                n = this.flex.rowsPerItem;
              e.cellType != t.CellType.TopLeft && e.cellType != t.CellType.ColumnHeader || (n = e.rows.length);
              for (var i = 0; i < n; i++) {
                o[i] = [];
                for (var l = 0; l < e.columns.length; l++) {
                  var s = this.flex.getBindingColumn(e, i, l),
                    a = r.indexOf(s);
                  o[i][l] = a < 0 ? r.push(s) - 1 : a
                }
              }
              return {
                bindingColumns: this._serializeColumns(r),
                mappings: o
              }
            }, r
          }(h),
          d = function(e) {
            function t() {
              return null !== e && e.apply(this, arguments) || this
            }
            return __extends(t, e), t.prototype.convert = function() {
              var t = e.prototype.convert.call(this);
              return t.centerHeadersVertically = this.flex.centerHeadersVertically, t
            }, t
          }(h),
          g = function() {
            function e(e, t) {
              this._index = t, this._level = e.level, this._renderHeight = e.renderHeight, this._state = e.state, this._cells = e.cells
            }
            return Object.defineProperty(e.prototype, "cells", {
              get: function() {
                return this._cells
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "index", {
              get: function() {
                return this._index
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "isAlternatingRow", {
              get: function() {
                return (this._state & c.Alternate) != c.None
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "isGroupRow", {
              get: function() {
                return (this._state & c.Group) != c.None
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "isDetailRow", {
              get: function() {
                return (this._state & c.Detail) != c.None
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "isExpandableGroupRow", {
              get: function() {
                return (this._state & c.ExpandableGroup) != c.None
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "isNewRow", {
              get: function() {
                return (this._state & c.New) != c.None
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "isVisible", {
              get: function() {
                return (this._state & c.Visible) != c.None
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "level", {
              get: function() {
                return this._level
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "renderHeight", {
              get: function() {
                return this._renderHeight
              },
              enumerable: !0,
              configurable: !0
            }), e
          }(),
          b = function() {
            function e(e, t) {
              this._aggregate = e.aggregate, this._alignment = e.alignment, this._dataType = e.dataType, this._index = t, this._renderWidth = e.renderWidth, this._state = e.state, this._wordWrap = e.wordWrap
            }
            return Object.defineProperty(e.prototype, "aggregate", {
              get: function() {
                return this._aggregate
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "dataType", {
              get: function() {
                return this._dataType
              },
              enumerable: !0,
              configurable: !0
            }), e.prototype.getAlignment = function() {
              return this._alignment
            }, Object.defineProperty(e.prototype, "index", {
              get: function() {
                return this._index
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "isVisible", {
              get: function() {
                return (this._state & p.Visible) != p.None
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "renderWidth", {
              get: function() {
                return this._renderWidth
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "wordWrap", {
              get: function() {
                return this._wordWrap
              },
              enumerable: !0,
              configurable: !0
            }), e
          }(),
          _ = function() {
            function e(e, t) {
              void 0 === t && (t = -1), this._maxGroupLevel = t, this._length = e.length;
              for (var r = 0; r < e.length; r++) this[r] = new g(e[r], r)
            }
            return Object.defineProperty(e.prototype, "length", {
              get: function() {
                return this._length
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "maxGroupLevel", {
              get: function() {
                return this._maxGroupLevel
              },
              enumerable: !0,
              configurable: !0
            }), e
          }(),
          w = function() {
            function e(e, t) {
              void 0 === t && (t = -1), this._firstVisibleIndex = t, this._length = e.length;
              for (var r = 0; r < e.length; r++) this[r] = new b(e[r], r)
            }
            return Object.defineProperty(e.prototype, "length", {
              get: function() {
                return this._length
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "firstVisibleIndex", {
              get: function() {
                return this._firstVisibleIndex
              },
              enumerable: !0,
              configurable: !0
            }), e
          }(),
          y = function() {
            function e(e) {
              this._height = e.height, this._width = e.width, this._cellType = e.cellType, this._columns = new w(e.columns, e.columnsFirstVisibleIndex), this._rows = new _(e.rows, e.rowsMaxGroupLevel), this._mergedRanges = this._deserializeMergedRanges(e.mergedRanges)
            }
            return Object.defineProperty(e.prototype, "cellType", {
              get: function() {
                return this._cellType
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "height", {
              get: function() {
                return this._height
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "width", {
              get: function() {
                return this._width
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "columns", {
              get: function() {
                return this._columns
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(e.prototype, "rows", {
              get: function() {
                return this._rows
              },
              enumerable: !0,
              configurable: !0
            }), e.prototype.getMergedRange = function(e, t) {
              var r = this._mergedRanges[e];
              if (r)
                for (var o = 0, n = r.length; o < n; o++) {
                  var i = r[o];
                  if (t >= i.col && t <= i.col2) return i
                }
              return null
            }, e.prototype._deserializeMergedRanges = function(e) {
              var t = [];
              e = e || [];
              for (var o = 0, n = this._rows.length; o < n; o++) t[o] = [];
              for (var o = 0, n = e.length; o < n; o++)
                for (var i = e[o], l = new r._CellRange(null, i[0], i[1], i[2], i[3]), s = l.row; s <= l.row2; s++) t[s].push(l);
              return t
            }, e
          }(),
          m = function() {
            function t(e, t) {
              this._settings = t, this._bottomLeftCells = this.deserializePanel(e.bottomLeftCells), this._cells = this.deserializePanel(e.cells), this._columnFooters = this.deserializePanel(e.columnFooters), this._columnHeaders = this.deserializePanel(e.columnHeaders), this._rowHeaders = this.deserializePanel(e.rowHeaders), this._topLeftCells = this.deserializePanel(e.topLeftCells), this._treeIndent = e.treeIndent, this._showColumnFooter = e.showColumnFooter, this._showColumnHeader = e.showColumnHeader, this._showRowHeader = e.showRowHeader, this._selection = [];
              for (var o = 0; o < e.selection.length; o++) {
                var n = e.selection[o];
                this._selection.push(new r._CellRange(null, n[0], n[1], n[2], n[3]))
              }
            }
            return Object.defineProperty(t.prototype, "settings", {
              get: function() {
                return this._settings
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(t.prototype, "columns", {
              get: function() {
                return this._cells.columns
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(t.prototype, "rows", {
              get: function() {
                return this._cells.rows
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(t.prototype, "bottomLeftCells", {
              get: function() {
                return this._bottomLeftCells
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(t.prototype, "cells", {
              get: function() {
                return this._cells
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(t.prototype, "columnFooters", {
              get: function() {
                return this._columnFooters
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(t.prototype, "columnHeaders", {
              get: function() {
                return this._columnHeaders
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(t.prototype, "rowHeaders", {
              get: function() {
                return this._rowHeaders
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(t.prototype, "topLeftCells", {
              get: function() {
                return this._topLeftCells
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(t.prototype, "treeIndent", {
              get: function() {
                return this._treeIndent
              },
              enumerable: !0,
              configurable: !0
            }), t.prototype.getSelection = function() {
              return this._selection
            }, t.prototype.getCell = function(e, t, r) {
              throw "Not implemented"
            }, t.prototype.getComputedStyle = function(e) {
              throw "Not implemented"
            }, t.prototype.getMergedRange = function(e, t, r) {
              return e.getMergedRange(t, r)
            }, Object.defineProperty(t.prototype, "showColumnFooter", {
              get: function() {
                return this._showColumnFooter
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(t.prototype, "showColumnHeader", {
              get: function() {
                return this._showColumnHeader
              },
              enumerable: !0,
              configurable: !0
            }), Object.defineProperty(t.prototype, "showRowHeader", {
              get: function() {
                return this._showRowHeader
              },
              enumerable: !0,
              configurable: !0
            }), t.prototype.alignMergedTextToTheTopRow = function(e) {
              return !1
            }, t.prototype.getCellData = function(e, t, r) {
              return e.rows[r].cells[t]
            }, t.prototype.getCellContent = function(e, t, r, o) {
              return this.getCellData(e, o, t.index)
            }, t.prototype.isBooleanCell = function(t, o, n) {
              return n.dataType === e.DataType.Boolean && t.cellType === r._CellType.Cell && !this.isExpandableGroupRow(o)
            }, t.prototype.getColumn = function(e, t, r) {
              return e.columns[r]
            }, t.prototype.isAlternatingRow = function(e) {
              return e.isAlternatingRow
            }, t.prototype.isGroupRow = function(e) {
              return e.isGroupRow
            }, t.prototype.isNewRow = function(e) {
              return e.isNewRow
            }, t.prototype.isDetailRow = function(e) {
              return e.isDetailRow
            }, t.prototype.isExpandableGroupRow = function(e) {
              return e.isExpandableGroupRow
            }, t.prototype.getCellStyle = function(e, t, o) {
              var n = this.settings.styles,
                i = r._merge({}, n.cellStyle);
              switch (e.cellType) {
                case r._CellType.Cell:
                  this.isExpandableGroupRow(t) ? r._merge(i, n.groupCellStyle, !0) : this.isAlternatingRow(t) && r._merge(i, n.altCellStyle, !0);
                  break;
                case r._CellType.ColumnHeader:
                case r._CellType.RowHeader:
                case r._CellType.TopLeft:
                case r._CellType.BottomLeft:
                  r._merge(i, n.headerCellStyle, !0);
                  break;
                case r._CellType.ColumnFooter:
                  r._merge(i, n.headerCellStyle, !0), r._merge(i, n.footerCellStyle, !0)
              }
              return i
            }, t.prototype.deserializePanel = function(e) {
              return new y(e)
            }, t
          }(),
          C = function(e) {
            function t(t, r) {
              var o = e.call(this, t, r) || this;
              return o._centerHeadersVertically = t.centerHeadersVertically, o
            }
            return __extends(t, e), t.prototype.alignMergedTextToTheTopRow = function(e) {
              return !this._centerHeadersVertically && (e.cellType === r._CellType.ColumnHeader || e.cellType === r._CellType.RowHeader)
            }, t
          }(m),
          v = function(e) {
            function t(t) {
              var r = e.call(this, t) || this;
              return t.cellGroups && (r._cellGroups = {
                bindingColumns: new w(t.cellGroups.bindingColumns),
                mappings: t.cellGroups.mappings
              }), r
            }
            return __extends(t, e), t.prototype.getColumn = function(e, t, o) {
              return this._cellGroups ? (this.cellType !== r._CellType.ColumnHeader && this.cellType !== r._CellType.BottomLeft && (e %= o), this._cellGroups.bindingColumns[this._cellGroups.mappings[e][t]]) : this.columns[t]
            }, t
          }(y),
          x = function(e) {
            function t(t, r) {
              var o = e.call(this, t, r) || this;
              return o._rowsPerItem = t.rowsPerItem, o
            }
            return __extends(t, e), t.prototype.deserializePanel = function(e) {
              return new v(e)
            }, t.prototype.getColumn = function(e, t, r) {
              return e.getColumn(t, r, this._rowsPerItem)
            }, t
          }(m)
      }(t.pdf || (t.pdf = {}))
    }(e.grid || (e.grid = {}))
  }(wijmo || (wijmo = {}));
