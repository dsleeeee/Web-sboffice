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
var wijmo;
! function(e) {
  ! function(t) {
    ! function(i) {
      "use strict";
      e._addCultureInfo("FlexGridDetailProvider", {
        ariaLabels: {
          toggleDetail: "Toggle Row Detail"
        }
      });
      var n;
      ! function(e) {
        e[e.None = 0] = "None", e[e.ToggleDetail = 1] = "ToggleDetail"
      }(n = i.KeyAction || (i.KeyAction = {}));
      var o;
      ! function(e) {
        e[e.Code = 0] = "Code", e[e.Selection = 1] = "Selection", e[e.ExpandSingle = 2] = "ExpandSingle", e[e.ExpandMulti = 3] = "ExpandMulti"
      }(o = i.DetailVisibilityMode || (i.DetailVisibilityMode = {}));
      var r = function() {
        function r(t, r) {
          var l = this;
          this._mode = o.ExpandSingle, this._animated = !1, this._keyActionEnter = n.None, this._g = t, t.mergeManager = new i.DetailMergeManager(t), t.rowHeaders.hostElement.addEventListener("click", this._hdrClick.bind(this)), t.rowHeaders.hostElement.addEventListener("mousedown", function(e) {
            var i = t.editableCollectionView;
            (t.activeEditor || i && i.currentEditItem) && (l._hdrClick(e), e.preventDefault())
          }), t.formatItem.addHandler(this._formatItem, this), t.selectionChanged.addHandler(this._selectionChanged, this), t.resizedRow.addHandler(this._resizedRow, this), t.loadingRows.addHandler(function() {
            l.hideDetail()
          }), t.updatedView.addHandler(function() {
            l._handleFixedCells()
          }), t.draggingRow.addHandler(function(e, t) {
            t.row < e.rows.length - 1 && e.rows[t.row + 1] instanceof i.DetailRow && (t.cancel = !0, l.hideDetail(t.row))
          }), t.hostElement.addEventListener("keydown", function(t) {
            if (t.keyCode == e.Key.Enter && l._keyActionEnter == n.ToggleDetail) {
              var i = l._g.selection.row;
              l._toggleRowDetail(i) && t.preventDefault()
            }
          }, !0), r && e.copy(this, r)
        }
        return Object.defineProperty(r.prototype, "grid", {
          get: function() {
            return this._g
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "detailVisibilityMode", {
          get: function() {
            return this._mode
          },
          set: function(t) {
            (t = e.asEnum(t, o)) != this._mode && (this._mode = t, this.hideDetail(), this._g.invalidate())
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "maxHeight", {
          get: function() {
            return this._maxHeight
          },
          set: function(t) {
            this._maxHeight = e.asNumber(t, !0)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "isAnimated", {
          get: function() {
            return this._animated
          },
          set: function(t) {
            t != this._animated && (this._animated = e.asBoolean(t))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "keyActionEnter", {
          get: function() {
            return this._keyActionEnter
          },
          set: function(t) {
            this._keyActionEnter = e.asEnum(t, n)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "createDetailCell", {
          get: function() {
            return this._createDetailCellFn
          },
          set: function(t) {
            this._createDetailCellFn = e.asFunction(t, !0)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "disposeDetailCell", {
          get: function() {
            return this._disposeDetailCellFn
          },
          set: function(t) {
            this._disposeDetailCellFn = e.asFunction(t, !0)
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "rowHasDetail", {
          get: function() {
            return this._rowHasDetailFn
          },
          set: function(t) {
            this._rowHasDetailFn = e.asFunction(t, !0)
          },
          enumerable: !0,
          configurable: !0
        }), r.prototype.getDetailRow = function(e) {
          e = this._toIndex(e);
          var t = this._g.rows;
          return t[e] instanceof i.DetailRow ? t[e] : e < t.length - 1 && t[e + 1] instanceof i.DetailRow ? t[e + 1] : null
        }, r.prototype.isDetailVisible = function(e) {
          return null != this.getDetailRow(e)
        }, r.prototype.isDetailAvailable = function(e) {
          return e = this._toIndex(e), this._hasDetail(e)
        }, r.prototype.hideDetail = function(t) {
          var n = this._g.rows;
          if (null != t) {
            !(n[t = this._toIndex(t)] instanceof i.DetailRow) && t < n.length - 1 && n[t + 1] instanceof i.DetailRow && t++;
            var o = n[t];
            o instanceof i.DetailRow && (this.disposeDetailCell && this.disposeDetailCell(o), e.Control.disposeAll(o.detail), n.removeAt(t))
          } else
            for (var r = 0; r < n.length; r++) n[r] instanceof i.DetailRow && this.hideDetail(r)
        }, r.prototype.showDetail = function(t, n) {
          void 0 === n && (n = !1);
          var o = this._g,
            r = o.rows;
          if ((t = this._toIndex(t)) > 0 && r[t] instanceof i.DetailRow && t--, n) {
            for (var l = o.selection, a = !1, s = 0; s < r.length - 1; s++) s != t && r[s + 1] instanceof i.DetailRow && (this.hideDetail(s), s < t && t--, s < l.row && (l.row--, l.row2--, a = !0));
            a && o.select(l, !1)
          }
          if (!this.isDetailVisible(t) && this._hasDetail(t)) {
            var c = new i.DetailRow(r[t]);
            if (c.detail = this._createDetailCell(r[t]), c.detail) {
              r.insert(t + 1, c), o.autoSizeRow(t + 1);
              var d = this._maxHeight;
              if (e.isNumber(d) && c.renderHeight > d && (c.height = d), this._animated) {
                var u = c.detail.style;
                u.transform = "translateY(-100%)", u.opacity = "0", e.animate(function(i) {
                  i < 1 ? (u.transform = "translateY(" + (100 * -(1 - i)).toFixed(0) + "%)", u.opacity = (i * i).toString()) : (u.transform = "", u.opacity = "", e.Control.invalidateAll(c.detail), o.scrollIntoView(t + 1, -1))
                })
              } else o.scrollIntoView(t + 1, -1, !0)
            }
          }
        }, r.prototype._handleFixedCells = function() {
          var i = this._g,
            n = i.hostElement;
          if (i.frozenRows || i.frozenColumns) {
            var o = e.Control.getControl(n.querySelector(".wj-flexgrid"));
            if (o instanceof t.FlexGrid && (o.frozenRows || o.frozenColumns)) {
              e.setCss([i._eTL, i._eBL, i._eCHdr, i._eCFtr, i._eRHdr, i._eMarquee], {
                zIndex: "6"
              });
              for (var r = i.hostElement.querySelectorAll(".wj-frozen"), l = 0; l < r.length; l++) {
                var a = r[l];
                if (e.closest(a, ".wj-flexgrid") == i.hostElement) {
                  var s = parseInt(a.style.zIndex);
                  a.style.zIndex = (s % 3 + 3).toString()
                }
              }
            }
          }
        }, r.prototype._toIndex = function(i) {
          return i instanceof t.Row && (i = i.index), e.asNumber(i, !1, !0)
        }, r.prototype._hdrClick = function(t) {
          if (!t.defaultPrevented && 0 == t.button && e.closestClass(t.target, r._WJC_DETAIL)) switch (this._mode) {
            case o.ExpandMulti:
            case o.ExpandSingle:
              var i = this._g,
                n = i.hitTest(t.target);
              n.panel || (n = i.hitTest(t)), n.panel && this._toggleRowDetail(n.row) && t.preventDefault()
          }
        }, r.prototype._toggleRowDetail = function(e) {
          if (e > -1) {
            if (this.isDetailVisible(e)) return this.hideDetail(e), !0;
            if (this._hasDetail(e)) {
              var i = this._g;
              return i.select(new t.CellRange(e, 0, e, i.columns.length - 1)), this.showDetail(e, this._mode == o.ExpandSingle), !0
            }
          }
          return !1
        }, r.prototype._selectionChanged = function(e, t) {
          var i = this;
          this._mode == o.Selection && (this._toSel && clearTimeout(this._toSel), this._toSel = setTimeout(function() {
            e.selection.row > -1 ? i.showDetail(e.selection.row, !0) : i.hideDetail()
          }, 300))
        }, r.prototype._formatItem = function(t, n) {
          var l = this._g,
            a = n.panel.rows[n.row];
          if (n.panel == l.cells && a instanceof i.DetailRow && null != a.detail && n.col == l.cells.columns.frozen)
            if (e.addClass(n.cell, "wj-detail"), n.cell.textContent = "", n.cell.style.textAlign = "", n.cell.appendChild(a.detail), null == a.height) {
              e.Control.refreshAll(n.cell);
              var s = getComputedStyle(n.cell),
                c = parseInt(s.paddingTop) + parseInt(s.paddingBottom),
                d = a.detail.scrollHeight + c;
              this._maxHeight > 0 && d > this._maxHeight && (d = this._maxHeight), a.height = d, a.detail.style.height || (a.detail.style.height = "100%");
              var u = a.detail.querySelector(".wj-flexgrid");
              u && !u.style.height && (u.style.height = "100%")
            } else setTimeout(function() {
              e.Control.refreshAll(a.detail)
            });
          if (n.panel == l.rowHeaders && 0 == n.col && this._hasDetail(n.row)) switch (n.cell.style.cursor = "", this._mode) {
            case o.ExpandMulti:
            case o.ExpandSingle:
              var h = n.cell,
                f = l.rows[n.row + 1] instanceof i.DetailRow,
                g = f ? "minus" : "plus",
                p = r._WJC_DETAIL;
              h.innerHTML = '<div class="wj-btn wj-btn-glyph ' + p + '" role="button" tabindex="-1"><span class="wj-glyph-' + g + '"></span></div>';
              var _ = h.children[0],
                w = e.culture.FlexGridDetailProvider.ariaLabels.toggleDetail;
              e.setAriaLabel(_, w), e.setAttribute(_, "aria-expanded", f)
          }
        }, r.prototype._resizedRow = function(t, n) {
          var o = n.panel.rows[n.row];
          o instanceof i.DetailRow && o.detail && e.Control.refreshAll(o.detail)
        }, r.prototype._hasDetail = function(t) {
          var i = this._g.rows[t];
          return e.isFunction(this._rowHasDetailFn) ? this._rowHasDetailFn(i) : this._isRegularRow(i)
        }, r.prototype._isRegularRow = function(e) {
          return !(e instanceof t.GroupRow || e instanceof t._NewRowTemplate)
        }, r.prototype._createDetailCell = function(e, t) {
          return this.createDetailCell ? this.createDetailCell(e, t) : null
        }, r._WJC_DETAIL = "wj-elem-detail", r
      }();
      i.FlexGridDetailProvider = r
    }(t.detail || (t.detail = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var i in t) t.hasOwnProperty(i) && (e[i] = t[i])
      };
    return function(t, i) {
      function n() {
        this.constructor = t
      }
      e(t, i), t.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(e) {
    ! function(t) {
      "use strict";
      var i = function(i) {
        function n(e) {
          return i.call(this, e) || this
        }
        return __extends(n, i), n.prototype.getMergedRange = function(n, o, r, l) {
          switch (void 0 === l && (l = !0), n.cellType) {
            case e.CellType.Cell:
              if (n.rows[o] instanceof t.DetailRow) {
                var a = n.columns,
                  s = Math.min(a.length, a.frozen);
                return r < s ? new e.CellRange(o, 0, o, s - 1) : new e.CellRange(o, s, o, a.length - 1)
              }
              break;
            case e.CellType.RowHeader:
              if (n.rows[o] instanceof t.DetailRow) return new e.CellRange(o - 1, r, o, r);
              if (o < n.rows.length - 1 && n.rows[o + 1] instanceof t.DetailRow) return new e.CellRange(o, r, o + 1, r)
          }
          return i.prototype.getMergedRange.call(this, n, o, r, l)
        }, n
      }(e.MergeManager);
      t.DetailMergeManager = i
    }(e.detail || (e.detail = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var e = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(e, t) {
        e.__proto__ = t
      } || function(e, t) {
        for (var i in t) t.hasOwnProperty(i) && (e[i] = t[i])
      };
    return function(t, i) {
      function n() {
        this.constructor = t
      }
      e(t, i), t.prototype = null === i ? Object.create(i) : (n.prototype = i.prototype, new n)
    }
  }(),
  wijmo;
! function(e) {
  ! function(e) {
    ! function(t) {
      "use strict";
      var i = function(e) {
        function t(t) {
          var i = e.call(this) || this;
          return i.isReadOnly = !0, i
        }
        return __extends(t, e), Object.defineProperty(t.prototype, "detail", {
          get: function() {
            return this._detail
          },
          set: function(e) {
            this._detail = e
          },
          enumerable: !0,
          configurable: !0
        }), t
      }(e.Row);
      t.DetailRow = i
    }(e.detail || (e.detail = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
