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
        for (var i in t) t.hasOwnProperty(i) && (e[i] = t[i])
      };
    return function(t, i) {
      function r() {
        this.constructor = t
      }
      e(t, i), t.prototype = null === i ? Object.create(i) : (r.prototype = i.prototype, new r)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    ! function(i) {
      "use strict";
      var r = function(i) {
        function r(r, n) {
          var o = i.call(this, r) || this;
          o._hideGroupedCols = !0, o._maxGroups = 6, o._hiddenCols = [];
          e.assert(null != t, "Missing dependency: GroupPanel requires wijmo.grid.");
          var s = o.getTemplate();
          o.applyTemplate("wj-grouppanel wj-control", s, {
            _divMarkers: "div-markers",
            _divPH: "div-ph"
          });
          var l = o.hostElement;
          return o.addEventListener(l, "dragstart", o._dragStart.bind(o)), o.addEventListener(l, "dragover", o._dragOver.bind(o)), o.addEventListener(l, "drop", o._drop.bind(o)), o.addEventListener(l, "dragend", o._dragEnd.bind(o)), o.addEventListener(l, "click", o._click.bind(o)), o.initialize(n), o
        }
        return __extends(r, i), Object.defineProperty(r.prototype, "hideGroupedColumns", {
          get: function() {
            return this._hideGroupedCols
          },
          set: function(t) {
            t != this._hideGroupedCols && (this._hideGroupedCols = e.asBoolean(t))
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "maxGroups", {
          get: function() {
            return this._maxGroups
          },
          set: function(t) {
            if (t != this._maxGroups) {
              this._maxGroups = e.asNumber(t);
              var i = this._gds,
                r = this._maxGroups;
              i && r > -1 && r < i.length && i.splice(r, i.length - r)
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "placeholder", {
          get: function() {
            return this._divPH.textContent
          },
          set: function(e) {
            this._divPH.textContent = e
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "grid", {
          get: function() {
            return this._g
          },
          set: function(i) {
            if ((i = e.asType(i, t.FlexGrid, !0)) != this._g) {
              var r = this._g;
              r && (r.draggingColumn.removeHandler(this._draggingColumn), r.itemsSourceChanging.removeHandler(this._itemsSourceChanging), r.itemsSourceChanged.removeHandler(this._itemsSourceChanged), r.columns.collectionChanged.removeHandler(this._itemsSourceChanged)), r = this._g = i, this._hiddenCols = [], r && (r.draggingColumn.addHandler(this._draggingColumn, this), r.itemsSourceChanging.addHandler(this._itemsSourceChanging, this), r.itemsSourceChanged.addHandler(this._itemsSourceChanged, this), r.columns.collectionChanged.addHandler(this._itemsSourceChanged, this)), this._itemsSourceChanged(r, null)
            }
          },
          enumerable: !0,
          configurable: !0
        }), Object.defineProperty(r.prototype, "filter", {
          get: function() {
            return this._filter
          },
          set: function(t) {
            if ((t = e.asType(t, e.grid.filter.FlexGridFilter, !0)) != this._filter) {
              var i = this._filter;
              i && i.filterApplied.removeHandler(this.refresh, this), (i = this._filter = t) && i.filterApplied.addHandler(this.refresh, this), this.refresh()
            }
          },
          enumerable: !0,
          configurable: !0
        }), r.prototype.refresh = function() {
          if (i.prototype.refresh.call(this), this._divMarkers.innerHTML = "", this._dragMarker = this._dragCol = null, this._gds) {
            for (var t = this._g, r = t.columnHeaders, n = 0; n < this._gds.length; n++) {
              for (var o = this._gds[n], s = -1, l = -1, a = r.rows.length - 1; a >= 0 && l < 0; a--)
                for (var d = 0; d < r.columns.length && l < 0; d++) {
                  var h = t._getBindingColumn(r, a, r.columns[d]);
                  if (h && h.binding == o.propertyName) {
                    l = d, s = a;
                    break
                  }
                }
              if (l > -1 && s > -1) {
                var g = document.createElement("div");
                t.cellFactory.updateCell(this._g.columnHeaders, s, l, g), g.setAttribute("class", "wj-cell wj-header wj-groupmarker"), e.setCss(g, {
                  position: "static",
                  display: "inline-block",
                  verticalAlign: "top",
                  left: "",
                  top: "",
                  right: "",
                  height: "auto",
                  width: "auto"
                });
                var u = g.querySelector(".wj-elem-filter");
                u && e.removeChild(u);
                var p = this._getColumnFilter(r.columns[l]);
                p && (u = e.createElement('<span class="wj-filter wj-glyph-filter"></span>', g), e.toggleClass(u, "wj-filter-on", p.isActive), e.toggleClass(u, "wj-filter-off", !p.isActive)), e.createElement('<span class="wj-remove">&times;</span>', g), this._divMarkers.appendChild(g)
              }
            }
            var c = this._divMarkers.children.length > 0;
            this._divPH.style.display = c ? "none" : "", this._divMarkers.style.display = c ? "" : "none"
          }
        }, r.prototype._getColumnFilter = function(e) {
          var t = this._filter,
            i = null;
          return t && (i = t.filterColumns && t.filterColumns.indexOf(e.binding) < 0 ? null : t.getColumnFilter(e)), i
        }, r.prototype._editFilter = function(e) {
          var t = this._gds,
            i = this._getElementIndex(e),
            r = t && i > -1 ? t[i] : null,
            n = r ? r.propertyName : null,
            o = n ? this._g.columns.getColumn(n) : null;
          o && this._filter.editColumnFilter(o, null, e)
        }, r.prototype._addGroup = function(t, i) {
          for (var r = this._getIndex(i), n = this._gds, o = this._maxGroups, s = 0; s < n.length; s++)
            if (n[s].propertyName == t.binding) {
              n.removeAt(s), s < r && r--;
              break
            }
          if (o > -1)
            for (s = o - 1; s < n.length; s++) this._removeGroup(s, n), s < r && r--;
          (o < 0 || n.length < o) && (n.deferUpdate(function() {
            var i = new e.collections.PropertyGroupDescription(t.binding);
            n.insert(r, i)
          }), t && this.hideGroupedColumns && (t.visible = !1, this._hiddenCols.push(t)))
        }, r.prototype._moveGroup = function(e, t) {
          var i = this._gds,
            r = this._getElementIndex(this._dragMarker),
            n = this._getIndex(t);
          n > r && n--, n >= this._gds.length && (n = this._gds.length), r != n && i.deferUpdate(function() {
            var e = i[r];
            i.removeAt(r), i.insert(n, e)
          })
        }, r.prototype._removeGroup = function(e, t) {
          void 0 === t && (t = this._gds);
          var i = null;
          t && e > -1 && (i = t[e], t.removeAt(e));
          var r = i ? i.propertyName : null,
            n = r ? this._g.columns.getColumn(r) : null;
          if (n) {
            n.visible = !0;
            var o = this._hiddenCols.indexOf(n);
            o > -1 && this._hiddenCols.splice(o, 1)
          }
        }, r.prototype._getIndex = function(e) {
          for (var t = this._divMarkers.children, i = 0; i < t.length; i++) {
            var r = t[i].getBoundingClientRect();
            if (e.clientX < r.left + r.width / 2) return i
          }
          return t.length
        }, r.prototype._getElementIndex = function(e) {
          if (e && e.parentElement)
            for (var t = e.parentElement.children, i = 0; i < t.length; i++)
              if (t[i] == e) return i;
          return -1
        }, r.prototype._draggingColumn = function(e, t) {
          var i = this._g,
            r = i._getBindingColumn(t.panel, t.row, i.columns[t.col]);
          this._dragCol = r.binding ? r : null
        }, r.prototype._itemsSourceChanging = function(e, t) {
          this._hiddenCols.forEach(function(e) {
            e.visible = !0
          }), this._hiddenCols = []
        }, r.prototype._itemsSourceChanged = function(e, t) {
          this._view && this._view.collectionChanged.removeHandler(this._collectionChanged), this._view = this._g ? this._g.collectionView : null, this._gds = this._view ? this._view.groupDescriptions : null, this._view && this._view.collectionChanged.addHandler(this._collectionChanged, this), this.invalidate()
        }, r.prototype._collectionChanged = function(t, i) {
          i.action == e.collections.NotifyCollectionChangedAction.Reset && this.invalidate()
        }, r.prototype._dragStart = function(t) {
          e._startDrag(t.dataTransfer, "move"), this._dragMarker = t.target, this._dragCol = null
        }, r.prototype._dragOver = function(e) {
          (this._dragCol || this._dragMarker) && (e.dataTransfer.dropEffect = "move", e.preventDefault(), e.stopPropagation())
        }, r.prototype._drop = function(e) {
          this._dragMarker ? this._moveGroup(this._dragMarker, e) : this._dragCol && this._addGroup(this._dragCol, e)
        }, r.prototype._dragEnd = function(e) {
          this._dragMarker = this._dragCol = null
        }, r.prototype._click = function(t) {
          var i = t.target,
            r = e.hasClass(i, "wj-remove"),
            n = e.hasClass(i, "wj-filter"),
            o = e.closest(i, ".wj-cell");
          if (e.hasClass(o, "wj-cell")) {
            var s = this._getElementIndex(o),
              l = this._g.collectionView.sortDescriptions;
            if (n) this._editFilter(o);
            else if (r) this._removeGroup(s);
            else if (t.ctrlKey) l.clear();
            else {
              for (var a = this._gds[s], d = !0, h = 0; h < l.length; h++)
                if (l[h].property == a.propertyName) {
                  d = !l[h].ascending;
                  break
                }
              var g = new e.collections.SortDescription(a.propertyName, d);
              l.splice(0, l.length, g)
            }
          }
        }, r.controlTemplate = '<div style="cursor:default;overflow:hidden;height:100%;width:100%;min-height:1em"><div wj-part="div-ph"></div><div wj-part="div-markers"></div></div>', r
      }(e.Control);
      i.GroupPanel = r
    }(t.grouppanel || (t.grouppanel = {}))
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
