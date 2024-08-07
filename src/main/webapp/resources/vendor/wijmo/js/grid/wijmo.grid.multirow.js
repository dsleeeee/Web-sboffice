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
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(e) {
    ! function(t) {
      "use strict";
      var o = function(e) {
        function t(t, o, n) {
          var r = e.call(this, t) || this;
          return r._idxData = o, r._idxRecord = n, r
        }
        return __extends(t, e), Object.defineProperty(t.prototype, "recordIndex", {
          get: function() {
            return this._idxRecord
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(t.prototype, "dataIndex", {
          get: function() {
            return this._idxData
          },
          enumerable: !0,
          configurable: !0
        }), t
      }(e.Row);
      t._MultiRow = o
    }(e.multirow || (e.multirow = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function(t) {
        function o(o) {
          var n = t.call(this) || this;
          return n._row = n._col = 0, n._rowspan = n._colspan = 1, o && e.copy(n, o), n
        }
        return __extends(o, t), Object.defineProperty(o.prototype, "colspan", {
          get: function() {
            return this._colspan
          },
          set: function(t) {
            this._colspan = e.asInt(t, !1, !0)
          },
          enumerable: !0,
          configurable: !0
        }), o
      }(t.Column);
      o._Cell = n
    }(t.multirow || (t.multirow = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function(n) {
        function r(t, o) {
          var r = n.call(this) || this;
          if (r._colstart = 0, r._g = t, o && e.copy(r, o), !r._cells) throw "Cell group with no cells?";
          for (var l = 0, i = 0, s = 0, a = 0; a < r._cells.length; a++) i + (c = r._cells[a]).colspan > r._colspan && (l++, i = 0), c._row = l, c._col = i, i += c.colspan, s += c.colspan;
          r._rowspan = l + 1, r._colspan > s && (r._colspan = s);
          for (a = 0; a < r._cells.length; a++) {
            var c = r._cells[a];
            (a == r._cells.length - 1 || r._cells[a + 1]._row > c._row) && (i = c._col, c._colspan = r._colspan - i)
          }
          return r
        }
        return __extends(r, n), r.prototype._copy = function(t, n) {
          var r = this;
          return "cells" == t && (this._cells = [], e.isArray(n) && n.forEach(function(t) {
            var n = new o._Cell(t);
            !t.header && n.binding && (n.header = e.toHeaderCase(n.binding)), r._cells.push(n), r._colspan = Math.max(r._colspan, n.colspan)
          }), !0)
        }, Object.defineProperty(r.prototype, "cells", {
          get: function() {
            return this._cells
          },
          enumerable: !0,
          configurable: !0
        }), r.prototype.closeGroup = function(e) {
          if (e > this._rowspan) {
            for (o = 0; o < this._cells.length; o++)(n = this._cells[o])._row == this._rowspan - 1 && (n._rowspan = e - n._row);
            this._rowspan = e
          }
          this._cols = new t.ColumnCollection(this._g, this._g.columns.defaultSize), this._rng = new Array(e * this._colspan);
          for (var o = 0; o < this._cells.length; o++)
            for (var n = this._cells[o], r = 0; r < n._rowspan; r++)
              for (var l = 0; l < n._colspan; l++) {
                var i = (n._row + r) * this._colspan + n._col + l;
                this._cols.setAt(i, n);
                var s = new t.CellRange(0 - r, 0 - l, 0 - r + n._rowspan - 1, 0 - l + n._colspan - 1);
                s.isSingleCell || (this._rng[i] = s)
              }
          this._rng[-1] = new t.CellRange(0, this._colstart, 0, this._colstart + this._colspan - 1)
        }, r.prototype.getColumnWidth = function(e) {
          for (var t = 0; t < this._cells.length; t++) {
            var o = this._cells[t];
            if (o._col == e && 1 == o.colspan) return o.width
          }
          return null
        }, r.prototype.getMergedRange = function(e, o, n) {
          if (o < 0) return this._rng[-1];
          var r = e.rows[o],
            l = null != r.recordIndex ? r.recordIndex : o % this._rowspan,
            i = n - this._colstart,
            s = this._rng[l * this._colspan + i];
          return e.cellType == t.CellType.ColumnHeader && o++, s ? new t.CellRange(o + s.row, n + s.col, o + s.row2, n + s.col2) : null
        }, r.prototype.getBindingColumn = function(e, t, o) {
          if (t < 0) return this;
          var n = e.rows[t],
            r = n && null != n.recordIndex ? n.recordIndex : t % this._rowspan,
            l = o - this._colstart;
          return this._cols[r * this._colspan + l]
        }, r.prototype.getColumn = function(e) {
          return this._cols.getColumn(e)
        }, r
      }(o._Cell);
      o._CellGroup = n
    }(t.multirow || (t.multirow = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function(n) {
        function r() {
          return null !== n && n.apply(this, arguments) || this
        }
        return __extends(r, n), r.prototype.getMergedRange = function(r, l, i, s) {
          void 0 === s && (s = !0);
          var a = r.grid;
          switch (r.cellType) {
            case t.CellType.Cell:
            case t.CellType.RowHeader:
              if (r.rows[l] instanceof t.GroupRow) return n.prototype.getMergedRange.call(this, r, l, i, s)
          }
          switch (r.cellType) {
            case t.CellType.Cell:
            case t.CellType.ColumnHeader:
              var c = a._cellGroupsByColumn[i];
              e.assert(c instanceof o._CellGroup, "Failed to get the group!");
              var u = r.cellType == t.CellType.ColumnHeader ? c.getMergedRange(r, l - 1, i) : c.getMergedRange(r, l, i),
                p = r.columns.frozen;
              p && u && u.columnSpan > 1 && u.col < p && u.col2 >= p && (u = u.clone(), i < p ? u.col2 = p - 1 : u.col = p);
              var d = r.rows.frozen;
              return d && u && u.rowSpan > 1 && r.cellType == t.CellType.Cell && u.row < d && u.row2 >= d && (u = u.clone(), l < d ? u.row2 = d - 1 : u.row = d), u;
            case t.CellType.RowHeader:
              var _ = a._rowsPerItem,
                h = l - r.rows[l].recordIndex,
                f = Math.min(h + _ - 1, r.rows.length - 1);
              return new t.CellRange(h, 0, f, r.columns.length - 1);
            case t.CellType.TopLeft:
              return new t.CellRange(0, 0, r.rows.length - 1, r.columns.length - 1)
          }
          return null
        }, r
      }(t.MergeManager);
      o._MergeManager = n
    }(t.multirow || (t.multirow = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(t) {
      "use strict";
      var o = function(e) {
        function t(t) {
          return t._addHdl._detach(), e.call(this, t) || this
        }
        return __extends(t, e), t.prototype.updateNewRowTemplate = function() {
          for (var e = this._g.editableCollectionView, t = this._g, o = t.rows, r = e && e.canAddNew && t.allowAddNew && !t.isReadOnly, l = !0, i = o.length - t.rowsPerItem; i < o.length; i++)
            if (!(o[i] instanceof n)) {
              l = !1;
              break
            }
          if (r && !l)
            for (i = 0; i < t.rowsPerItem; i++) {
              var s = new n(i);
              o.push(s)
            }
          if (!r && l)
            for (i = 0; i < o.length; i++) o[i] instanceof n && (o.removeAt(i), i--)
        }, t
      }(e.grid._AddNewHandler);
      t._AddNewHandler = o;
      var n = function(e) {
        function t(t) {
          var o = e.call(this) || this;
          return o._idxRecord = t, o
        }
        return __extends(t, e), Object.defineProperty(t.prototype, "recordIndex", {
          get: function() {
            return this._idxRecord
          },
          enumerable: !0,
          configurable: !0
        }), t
      }(e.grid._NewRowTemplate)
    }(t.multirow || (t.multirow = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var o in t) t.hasOwnProperty(o) && (e[o] = t[o])
      };
    return function(t, o) {
      function n() {
        this.constructor = t
      }
      e(t, o), t.prototype = null === o ? Object.create(o) : (n.prototype = o.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(o) {
      "use strict";
      var n = function(n) {
        function r(r, l) {
          var i = n.call(this, r) || this;
          i._rowsPerItem = 1, i._cellBindingGroups = [], i._centerVert = !0, i._collapsedHeaders = !1, i.collapsedHeadersChanging = new e.Event, i.collapsedHeadersChanged = new e.Event, e.addClass(i.hostElement, "wj-multirow");
          var s = i.columnHeaders.hostElement.parentElement,
            a = e.createElement('<div class="wj-hdr-collapse"><span></span></div>');
          a.style.display = "none", s.appendChild(a), i._btnCollapse = a, i._updateButtonGlyph(), i.addEventListener(a, "mousedown", function(e) {
            switch (i.collapsedHeaders) {
              case null:
              case !1:
                i._collapsedHeadersWasNull = null == i.collapsedHeaders, i.collapsedHeaders = !0;
                break;
              case !0:
                i.collapsedHeaders = !!i._collapsedHeadersWasNull && null
            }
            e.preventDefault(), i.focus()
          }, !0), i.autoGenerateColumns = !1, i.mergeManager = new o._MergeManager(i);
          var c = i.hostElement;
          return i.removeEventListener(c, "dragover"), i.removeEventListener(c, "dragleave"), i.removeEventListener(c, "dragdrop"), i._addHdl = new o._AddNewHandler(i), i.formatItem.addHandler(i._formatItem, i), i.addEventListener(i.rowHeaders.hostElement, "click", function(e) {
            if (!e.defaultPrevented && i.selectionMode != t.SelectionMode.None) {
              var o = i.hitTest(e);
              if (o.panel == i.rowHeaders && o.row > -1) {
                var n = i.selection,
                  r = i.rows[n.topRow],
                  l = i.selectionMode != t.SelectionMode.Row ? i.rows[n.bottomRow] : r;
                if (r && null != r.recordIndex) {
                  var s = r.index - r.recordIndex,
                    a = l.index - l.recordIndex + i.rowsPerItem - 1,
                    c = i.columns.length - 1,
                    u = n.row != n.topRow ? new t.CellRange(a, 0, s, c) : new t.CellRange(s, 0, a, c);
                  i.select(u), e.preventDefault()
                }
              }
            }
          }, !0), i.initialize(l), i
        }
        return __extends(r, n), r.prototype._getProductInfo = function() {
          return "H87K,MultiRow"
        }, Object.defineProperty(r.prototype, "layoutDefinition", {
          get: function() {
            return this._layoutDef
          },
          set: function(t) {
            this._layoutDef = e.asArray(t), this._rowsPerItem = 1, this._cellBindingGroups = this._parseCellGroups(this._layoutDef);
            for (var o = 0; o < this._cellBindingGroups.length; o++) {
              var n = this._cellBindingGroups[o];
              this._rowsPerItem = Math.max(this._rowsPerItem, n._rowspan)
            }
            this._bindGrid(!0)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "rowsPerItem", {
          get: function() {
            return this._rowsPerItem
          },
          enumerable: !0,
          configurable: !0
        }), r.prototype.getBindingColumn = function(e, t, o) {
          return this._getBindingColumn(e, t, e.columns[o])
        }, r.prototype.getColumn = function(e) {
          for (var t, o = this._cellBindingGroups, n = 0; n < o.length && !t; n++) t = o[n].getColumn(e);
          return t
        }, Object.defineProperty(r.prototype, "centerHeadersVertically", {
          get: function() {
            return this._centerVert
          },
          set: function(t) {
            t != this._centerVert && (this._centerVert = e.asBoolean(t), this.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "collapsedHeaders", {
          get: function() {
            return this._collapsedHeaders
          },
          set: function(t) {
            if (t != this._collapsedHeaders) {
              var o = new e.CancelEventArgs;
              this.onCollapsedHeadersChanging(o) && (this._collapsedHeaders = e.asBoolean(t, !0), this._updateCollapsedHeaders(), this._updateButtonGlyph(), this.onCollapsedHeadersChanged(o))
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "showHeaderCollapseButton", {
          get: function() {
            return "" == this._btnCollapse.style.display
          },
          set: function(t) {
            t != this.showHeaderCollapseButton && (this._btnCollapse.style.display = e.asBoolean(t) ? "" : "none")
          },
          enumerable: !0,
          configurable: !0
        }), r.prototype.onCollapsedHeadersChanging = function(e) {
          return this.collapsedHeadersChanging.raise(this, e), !e.cancel
        }, r.prototype.onCollapsedHeadersChanged = function(e) {
          this.collapsedHeadersChanged.raise(this, e)
        }, r.prototype._addBoundRow = function(e, t) {
          for (var n = e[t], r = 0; r < this._rowsPerItem; r++) this.rows.push(new o._MultiRow(n, t, r))
        }, r.prototype._addNode = function(e, t, o) {
          this._addBoundRow(e, t)
        }, r.prototype._bindColumns = function() {
          for (var o = this.columnHeaders.rows, n = this._rowsPerItem + 1; o.length > n;) o.removeAt(o.length - 1);
          for (; o.length < n;) o.push(new t.Row);
          this._updateCollapsedHeaders(), this.columns.clear(), this._cellGroupsByColumn = {};
          var r = this.collectionView;
          if (r && r.sourceCollection && r.sourceCollection.length && r.sourceCollection[0], this._cellBindingGroups)
            for (var l = 0; l < this._cellBindingGroups.length; l++)
              for (var i = this._cellBindingGroups[l], s = 0; s < i._colspan; s++) {
                this._cellGroupsByColumn[this.columns.length] = i;
                for (var a = new t.Column, c = 0; c < i.cells.length; c++) {
                  var u = i.cells[c];
                  if (u._col == s) {
                    u.width && (a.width = u.width), u.binding && (a.binding = u.binding), u.format && (a.format = u.format), u.aggregate != e.Aggregate.None && (a.aggregate = u.aggregate);
                    break
                  }
                }
                this.columns.push(a)
              }
        }, r.prototype._updateCollapsedHeaders = function() {
          var e = this.columnHeaders.rows,
            t = this.collapsedHeaders;
          e[0].visible = 0 != t;
          for (var o = 1; o < e.length; o++) e[o].visible = 1 != t
        }, r.prototype._updateColumnTypes = function() {
          n.prototype._updateColumnTypes.call(this);
          var t = this.collectionView;
          if (e.hasItems(t))
            for (var o = t.items[0], r = 0; r < this._cellBindingGroups.length; r++)
              for (var l = this._cellBindingGroups[r], i = 0; i < l._cols.length; i++) {
                var s = l._cols[i];
                null == s.dataType && s._binding && (s.dataType = e.getType(s._binding.getValue(o)))
              }
        }, r.prototype._getBindingColumn = function(e, t, o) {
          if (e == this.cells || e == this.columnHeaders) {
            var n = this._cellGroupsByColumn[o.index];
            e == this.columnHeaders && t--, o = n.getBindingColumn(e, t, o.index)
          }
          return o
        }, r.prototype._cvCollectionChanged = function(n, r) {
          if (this.autoGenerateColumns && 0 == this.columns.length) this._bindGrid(!0);
          else {
            var l = e.collections.NotifyCollectionChangedAction;
            switch (r.action) {
              case l.Change:
                this.invalidate();
                break;
              case l.Add:
                if (r.index == this.collectionView.items.length - 1) {
                  for (var i = this.rows.length; i > 0 && this.rows[i - 1] instanceof t._NewRowTemplate;) i--;
                  for (var s = 0; s < this._rowsPerItem; s++) this.rows.insert(i + s, new o._MultiRow(r.item, r.index, s));
                  return
                }
                e.assert(!1, "added item should be the last one.");
                break;
              default:
                this._bindGrid(!1)
            }
          }
        }, r.prototype._parseCellGroups = function(e) {
          var t = [],
            n = 1;
          if (e) {
            for (var r = 0, l = 0; r < e.length; r++) {
              var i = new o._CellGroup(this, e[r]);
              i._colstart = l, l += i._colspan, n = Math.max(n, i._rowspan), t.push(i)
            }
            for (r = 0; r < t.length; r++) t[r].closeGroup(n)
          }
          return t
        }, r.prototype._formatItem = function(n, r) {
          var l = this._rowsPerItem,
            i = r.panel.cellType,
            s = r.panel.rows[r.range.row],
            a = r.panel.rows[r.range.row2],
            c = r.cell;
          if (i == t.CellType.ColumnHeader && e.toggleClass(c, "wj-group-header", 0 == r.range.row), i == t.CellType.Cell || i == t.CellType.ColumnHeader) {
            var u = this._cellGroupsByColumn[r.col];
            e.assert(u instanceof o._CellGroup, "Failed to get the group!"), e.toggleClass(c, "wj-group-start", u._colstart == r.range.col), e.toggleClass(c, "wj-group-end", u._colstart + u._colspan - 1 == r.range.col2)
          }
          if (l > 1 && (i != t.CellType.Cell && i != t.CellType.RowHeader || (e.toggleClass(c, "wj-record-start", s instanceof o._MultiRow && 0 == s.recordIndex), e.toggleClass(c, "wj-record-end", a instanceof o._MultiRow && a.recordIndex == l - 1))), this.showAlternatingRows && e.toggleClass(c, "wj-alt", s instanceof o._MultiRow && s.dataIndex % 2 != 0), this._centerVert)
            if (c.hasChildNodes && r.range.rowSpan > 1) {
              var p = document.createElement("div"),
                d = document.createRange();
              e.setCss(p, {
                display: "table-cell",
                verticalAlign: "middle",
                overflow: "inherit",
                textOverflow: "inherit"
              }), d.selectNodeContents(c), d.surroundContents(p), e.setCss(c, {
                display: "table",
                tableLayout: "fixed",
                paddingTop: 0,
                paddingBottom: 0
              })
            } else {
              var _ = c.querySelector("input") && !c.querySelector("input[type=checkbox]") ? "0" : "";
              e.setCss(c, {
                display: "",
                tableLayout: "",
                paddingTop: _,
                paddingBottom: _
              })
            }
        }, r.prototype._updateButtonGlyph = function() {
          var e = this._btnCollapse.querySelector("span");
          e instanceof HTMLElement && (e.className = this.collapsedHeaders ? "wj-glyph-left" : "wj-glyph-down-left")
        }, r.prototype._getError = function(t, o, r) {
          if (e.isFunction(this.itemValidator) && t == this.rowHeaders)
            for (var l = 0; l < this._rowsPerItem; l++)
              for (r = 0; r < this.columns.length; r++) {
                var i = this.itemValidator(o + l, r);
                if (i) return i
              }
          return n.prototype._getError.call(this, t, o, r)
        }, r
      }(t.FlexGrid);
      o.MultiRow = n
    }(t.multirow || (t.multirow = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
