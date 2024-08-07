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
      function o() {
        this.constructor = t
      }
      e(t, i), t.prototype = null === i ? Object.create(i) : (o.prototype = i.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    e._addCultureInfo("FlexGrid", {
      groupHeaderFormat: "{name}: <b>{value}</b> ({count:n0} items)",
      ariaLabels: {
        toggleDropDown: "Toggle Dropdown",
        toggleGroup: "Toggle Group"
      }
    });
    var i;
    ! function(e) {
      e[e.None = 0] = "None", e[e.Column = 1] = "Column", e[e.Row = 2] = "Row", e[e.All = 3] = "All"
    }(i = t.HeadersVisibility || (t.HeadersVisibility = {}));
    var o = function(o) {
      function n(n, r) {
        var s = o.call(this, n, null, !0) || this;
        s._szClient = new e.Size(0, 0), s._ptScrl = new e.Point(0, 0), s._cellPadding = 3, s._clipToScreen = !1, s._autoGenCols = !0, s._autoClipboard = !0, s._autoScroll = !0, s._autoSearch = !1, s._readOnly = !1, s._indent = 14, s._autoSizeMode = t.AutoSizeMode.Both, s._hdrVis = i.All, s._alSorting = !0, s._alAddNew = !1, s._alDelete = !1, s._alResizing = t.AllowResizing.Columns, s._alDragging = t.AllowDragging.Columns, s._alMerging = t.AllowMerging.None, s._ssHdr = i.None, s._shSort = !0, s._shGroups = !0, s._shAlt = !0, s._shErr = !0, s._shDropDown = !0, s._valEdt = !0, s._deferResizing = !1, s._pSel = !0, s._pOutline = !0, s._vt = 0, s._vtRows = 0, s._vtCols = 0, s.itemsSourceChanging = new e.Event, s.itemsSourceChanged = new e.Event, s.scrollPositionChanged = new e.Event, s.selectionChanging = new e.Event, s.selectionChanged = new e.Event, s.loadingRows = new e.Event, s.loadedRows = new e.Event, s.updatingLayout = new e.Event, s.updatedLayout = new e.Event, s.resizingColumn = new e.Event, s.resizedColumn = new e.Event, s.autoSizingColumn = new e.Event, s.autoSizedColumn = new e.Event, s.draggingColumn = new e.Event, s.draggingColumnOver = new e.Event, s.draggedColumn = new e.Event, s.resizingRow = new e.Event, s.resizedRow = new e.Event, s.autoSizingRow = new e.Event, s.autoSizedRow = new e.Event, s.draggingRow = new e.Event, s.draggingRowOver = new e.Event, s.draggedRow = new e.Event, s.groupCollapsedChanging = new e.Event, s.groupCollapsedChanged = new e.Event, s.sortingColumn = new e.Event, s.sortedColumn = new e.Event, s.beginningEdit = new e.Event, s.prepareCellForEdit = new e.Event, s.cellEditEnding = new e.Event, s.cellEditEnded = new e.Event, s.rowEditStarting = new e.Event, s.rowEditStarted = new e.Event, s.rowEditEnding = new e.Event, s.rowEditEnded = new e.Event, s.rowAdded = new e.Event, s.deletingRow = new e.Event, s.deletedRow = new e.Event, s.copying = new e.Event, s.copied = new e.Event, s.pasting = new e.Event, s.pasted = new e.Event, s.pastingCell = new e.Event, s.pastedCell = new e.Event, s.formatItem = new e.Event, s.updatingView = new e.Event, s.updatedView = new e.Event, s._mappedColumns = null;
        var l = s.hostElement;
        e.isIE() && (l.style.borderRadius = "0");
        var a = s.getTemplate();
        s.applyTemplate("wj-control wj-content wj-flexgrid", a, {
          _root: "root",
          _eSz: "sz",
          _eCt: "cells",
          _fCt: "fcells",
          _eTL: "tl",
          _eBL: "bl",
          _eCHdr: "ch",
          _eRHdr: "rh",
          _eCFtr: "cf",
          _eTLCt: "tlcells",
          _eBLCt: "blcells",
          _eCHdrCt: "chcells",
          _eCFtrCt: "cfcells",
          _eRHdrCt: "rhcells",
          _eMarquee: "marquee",
          _eFocus: "focus"
        });
        return [s._eRHdr, s._eCFtr, s._eCHdr, s._eBL, s._eTL].forEach(function(t) {
          e.setAttribute(t, "aria-hidden", !0), e.setCss(t, {
            position: "absolute",
            overflow: "hidden",
            outline: "none"
          }), e.setCss(t.firstElementChild, {
            position: "relative"
          })
        }), [s._eFocus, s._eMarquee, s._fCt, s._eSz].forEach(function(t) {
          e.setAttribute(t, "aria-hidden", !0)
        }), l.tabIndex = -1, s.deferUpdate(function() {
          var i = s._getDefaultRowHeight();
          s._rows = new t.RowCollection(s, i), s._cols = new t.ColumnCollection(s, 4 * i), s._hdrRows = new t.RowCollection(s, i), s._hdrCols = new t.ColumnCollection(s, Math.round(1.25 * i)), s._ftrRows = new t.RowCollection(s, i), s._gpTL = new t.GridPanel(s, t.CellType.TopLeft, s._hdrRows, s._hdrCols, s._eTLCt), s._gpCHdr = new t.GridPanel(s, t.CellType.ColumnHeader, s._hdrRows, s._cols, s._eCHdrCt), s._gpRHdr = new t.GridPanel(s, t.CellType.RowHeader, s._rows, s._hdrCols, s._eRHdrCt), s._gpCells = new t.GridPanel(s, t.CellType.Cell, s._rows, s._cols, s._eCt), s._gpBL = new t.GridPanel(s, t.CellType.BottomLeft, s._ftrRows, s._hdrCols, s._eBLCt), s._gpCFtr = new t.GridPanel(s, t.CellType.ColumnFooter, s._ftrRows, s._cols, s._eCFtrCt), s._hdrRows.push(new t.Row), s._hdrCols.push(new t.Column({
            align: "center"
          })), s._cf = new t.CellFactory, s._keyHdl = new t._KeyboardHandler(s), s._mouseHdl = new t._MouseHandler(s), s._edtHdl = new t._EditHandler(s), s._selHdl = new t._SelectionHandler(s), s._addHdl = new t._AddNewHandler(s), s._mrgMgr = new t.MergeManager(s), s._bndSortConverter = s._sortConverter.bind(s), e.setAttribute(s.cells.hostElement, "role", "grid"), s.selectionMode = t.SelectionMode.CellRange, s.initialize(r)
        }), s.addEventListener(s._root, "scroll", function(e) {
          s._updateScrollPosition() && (s.finishEditing(), s._updateContent(!0))
        }), s.addEventListener(l, "focus", function(e) {
          if (l.tabIndex > -1) {
            var t = e.target;
            if (t instanceof HTMLElement && t.tabIndex < 0) return void s._setFocus(!0)
          }
        }, !0), s
      }
      return __extends(n, o), n.prototype._handleResize = function() {
        this._rcBounds = null, o.prototype._handleResize.call(this)
      }, Object.defineProperty(n.prototype, "headersVisibility", {
        get: function() {
          return this._hdrVis
        },
        set: function(t) {
          (t = e.asEnum(t, i)) != this._hdrVis && (this._hdrVis = t, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "stickyHeaders", {
        get: function() {
          return this._stickyHdr
        },
        set: function(t) {
          t != this._stickyHdr && (this._stickyHdr = e.asBoolean(t), this._updateStickyHeaders(), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "preserveSelectedState", {
        get: function() {
          return this._pSel
        },
        set: function(t) {
          this._pSel = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "preserveOutlineState", {
        get: function() {
          return this._pOutline
        },
        set: function(t) {
          this._pOutline = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "virtualizationThreshold", {
        get: function() {
          return this._vt
        },
        set: function(t) {
          this._vt = t, e.isNumber(t) ? this._vtRows = this._vtCols = e.asNumber(t) : t ? e.isArray(t) && 2 == t.length ? (this._vtRows = e.asNumber(t[0]), this._vtCols = e.asNumber(t[1])) : e.assert(!1, "virtualizationThreshold should be a number or an array with two numbers.") : this._vtRows = this._vtCols = 0
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "autoGenerateColumns", {
        get: function() {
          return this._autoGenCols
        },
        set: function(t) {
          this._autoGenCols = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "autoClipboard", {
        get: function() {
          return this._autoClipboard
        },
        set: function(t) {
          this._autoClipboard = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "autoScroll", {
        get: function() {
          return this._autoScroll
        },
        set: function(t) {
          this._autoScroll = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "autoSearch", {
        get: function() {
          return this._autoSearch
        },
        set: function(t) {
          this._autoSearch = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "columnLayout", {
        get: function() {
          for (var i = n._getSerializableProperties(t.Column), o = new t.Column, r = [], s = 0; s < this.columns.length; s++) {
            for (var l = this.columns[s], a = {}, c = 0; c < i.length; c++) {
              var h = i[c],
                u = l[h];
              u != o[h] && e.isPrimitive(u) && "size" != h && (a[h] = u)
            }
            r.push(a)
          }
          return JSON.stringify({
            columns: r
          })
        },
        set: function(t) {
          var i = JSON.parse(e.asString(t));
          if (!i || null == i.columns) throw "Invalid columnLayout data.";
          this.columns.clear(), this.initialize(i)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isReadOnly", {
        get: function() {
          return this._readOnly
        },
        set: function(t) {
          t != this._readOnly && (this._readOnly = e.asBoolean(t), this.finishEditing(), this.invalidate(!0), this._addHdl.updateNewRowTemplate(), e.toggleClass(this.hostElement, "wj-state-readonly", this.isReadOnly), this._setAria("readonly", this.isReadOnly ? "true" : null))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "imeEnabled", {
        get: function() {
          return null != this._imeHdl
        },
        set: function(i) {
          if (e.asBoolean(i) != this.imeEnabled && this.finishEditing()) {
            var o = this.containsFocus();
            this._imeHdl && (this._imeHdl.dispose(), this._imeHdl = null), i && (this._imeHdl = new t._ImeHandler(this)), o && this.focus()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "allowResizing", {
        get: function() {
          return this._alResizing
        },
        set: function(i) {
          this._alResizing = e.asEnum(i, t.AllowResizing)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "deferResizing", {
        get: function() {
          return this._deferResizing
        },
        set: function(t) {
          this._deferResizing = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "autoSizeMode", {
        get: function() {
          return this._autoSizeMode
        },
        set: function(i) {
          this._autoSizeMode = e.asEnum(i, t.AutoSizeMode)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "quickAutoSize", {
        get: function() {
          return this._quickSize
        },
        set: function(t) {
          this._quickSize = e.asBoolean(t, !0)
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._getQuickAutoSize = function() {
        return e.isBoolean(this._quickSize) ? this._quickSize : !this.formatItem.hasHandlers && null == this.itemFormatter
      }, Object.defineProperty(n.prototype, "allowSorting", {
        get: function() {
          return this._alSorting
        },
        set: function(t) {
          this._alSorting = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "allowAddNew", {
        get: function() {
          return this._alAddNew
        },
        set: function(t) {
          t != this._alAddNew && (this._alAddNew = e.asBoolean(t), this._addHdl.updateNewRowTemplate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "newRowAtTop", {
        get: function() {
          return this._addHdl.newRowAtTop
        },
        set: function(t) {
          this._addHdl.newRowAtTop = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "allowDelete", {
        get: function() {
          return this._alDelete
        },
        set: function(t) {
          t != this._alDelete && (this._alDelete = e.asBoolean(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "allowMerging", {
        get: function() {
          return this._alMerging
        },
        set: function(i) {
          (i = e.asEnum(i, t.AllowMerging)) != this._alMerging && (this._alMerging = i, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showSelectedHeaders", {
        get: function() {
          return this._ssHdr
        },
        set: function(t) {
          (t = e.asEnum(t, i)) != this._ssHdr && (this._ssHdr = t, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showMarquee", {
        get: function() {
          return !this._eMarquee.style.display
        },
        set: function(t) {
          if (t != this.showMarquee) {
            var i = this._eMarquee.style;
            i.visibility = "collapse", i.display = e.asBoolean(t) ? "" : "none", this.invalidate()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showSort", {
        get: function() {
          return this._shSort
        },
        set: function(t) {
          t != this._shSort && (this._shSort = e.asBoolean(t), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showGroups", {
        get: function() {
          return this._shGroups
        },
        set: function(t) {
          t != this._shGroups && (this._shGroups = e.asBoolean(t), this._bindGrid(!1))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showAlternatingRows", {
        get: function() {
          return this._shAlt
        },
        set: function(t) {
          t != this._shAlt && (this._shAlt = e.asBoolean(t), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showErrors", {
        get: function() {
          return this._shErr
        },
        set: function(t) {
          t != this._shErr && (this._clearCells(), this._shErr = e.asBoolean(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "itemValidator", {
        get: function() {
          return this._itemValidator
        },
        set: function(t) {
          t != this.itemValidator && (this._itemValidator = e.asFunction(t), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "validateEdits", {
        get: function() {
          return this._valEdt
        },
        set: function(t) {
          this._valEdt = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "groupHeaderFormat", {
        get: function() {
          return this._gHdrFmt
        },
        set: function(t) {
          t != this._gHdrFmt && (this._gHdrFmt = e.asString(t), this._bindGrid(!1))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "allowDragging", {
        get: function() {
          return this._alDragging
        },
        set: function(i) {
          (i = e.asEnum(i, t.AllowDragging)) != this._alDragging && (this._alDragging = i, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "itemsSource", {
        get: function() {
          return this._items
        },
        set: function(i) {
          if (i != this._items) {
            var o = new e.CancelEventArgs;
            if (this.onItemsSourceChanging(o)) {
              if (this._cv && ((n = e.tryCast(this._cv, e.collections.CollectionView)) && n.sortConverter == this._bndSortConverter && (n.sortConverter = null), this._cv.currentChanged.removeHandler(this._cvCurrentChanged, this), this._cv.collectionChanged.removeHandler(this._cvCollectionChanged, this), this._cv = null), this._items = i, this._cv = this._getCollectionView(i), this._lastCount = 0, this._cv) {
                this._cv.currentChanged.addHandler(this._cvCurrentChanged, this), this._cv.collectionChanged.addHandler(this._cvCollectionChanged, this);
                var n = e.tryCast(this._cv, e.collections.CollectionView);
                n && !n.sortConverter && (n.sortConverter = this._bndSortConverter)
              }
              this._bindGrid(!0);
              var r = t.SelectionMode;
              this.selectionMode == r.ListBox && (this.selectionMode = r.CellRange, this.selectionMode = r.ListBox), this.onItemsSourceChanged(o)
            }
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "collectionView", {
        get: function() {
          return this._cv
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "editableCollectionView", {
        get: function() {
          return e.tryCast(this._cv, "IEditableCollectionView")
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "childItemsPath", {
        get: function() {
          return this._childItemsPath
        },
        set: function(t) {
          t != this._childItemsPath && (e.assert(null == t || e.isArray(t) || e.isString(t), "childItemsPath should be an array or a string."), this._childItemsPath = t, this._bindGrid(!0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "rowHeaderPath", {
        get: function() {
          return this._rowHdrPath ? this._rowHdrPath.path : null
        },
        set: function(t) {
          t != this.rowHeaderPath && (t = e.asString(t), this._rowHdrPath = t ? new e.Binding(t) : null, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "cells", {
        get: function() {
          return this._gpCells
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "columnHeaders", {
        get: function() {
          return this._gpCHdr
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "columnFooters", {
        get: function() {
          return this._gpCFtr
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "rowHeaders", {
        get: function() {
          return this._gpRHdr
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "topLeftCells", {
        get: function() {
          return this._gpTL
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "bottomLeftCells", {
        get: function() {
          return this._gpBL
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "rows", {
        get: function() {
          return this._rows
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "columns", {
        get: function() {
          return this._cols
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.getColumn = function(e) {
        return this.columns.getColumn(e)
      }, Object.defineProperty(n.prototype, "frozenRows", {
        get: function() {
          return this.rows.frozen
        },
        set: function(e) {
          this.rows.frozen = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "frozenColumns", {
        get: function() {
          return this.columns.frozen
        },
        set: function(e) {
          this.columns.frozen = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "cloneFrozenCells", {
        get: function() {
          return this._fzClone
        },
        set: function(t) {
          t != this.cloneFrozenCells && (this._fzClone = e.asBoolean(t, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "sortRowIndex", {
        get: function() {
          return this._sortRowIndex
        },
        set: function(t) {
          t != this._sortRowIndex && (this._sortRowIndex = e.asNumber(t, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "scrollPosition", {
        get: function() {
          return this._ptScrl.clone()
        },
        set: function(e) {
          var t = this._root,
            i = -e.x;
          if (this.rightToLeft) switch (n._getRtlMode()) {
            case "rev":
              i = t.scrollWidth - t.clientWidth + e.x;
              break;
            case "neg":
              i = e.x;
              break;
            default:
              i = -e.x
          }
          t.scrollLeft = i, t.scrollTop = -e.y
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "clientSize", {
        get: function() {
          return this._szClient
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "controlRect", {
        get: function() {
          return this._rcBounds || (this._rcBounds = e.getElementRect(this._root)), this._rcBounds
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "scrollSize", {
        get: function() {
          return new e.Size(this._gpCells.width, this._heightBrowser)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "viewRange", {
        get: function() {
          return this._gpCells.viewRange
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "cellFactory", {
        get: function() {
          return this._cf
        },
        set: function(i) {
          i != this._cf && (this._clearCells(), this._cf = e.asType(i, t.CellFactory, !1))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "itemFormatter", {
        get: function() {
          return this._itemFormatter
        },
        set: function(t) {
          t != this._itemFormatter && (this._clearCells(), this._itemFormatter = e.asFunction(t))
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.canEditCell = function(e, t) {
        return this._edtHdl._allowEditing(e, t)
      }, n.prototype.getCellData = function(e, t, i) {
        return this.cells.getCellData(e, t, i)
      }, n.prototype.getCellBoundingRect = function(e, t, i) {
        return this.cells.getCellBoundingRect(e, t, i)
      }, n.prototype.setCellData = function(e, t, i, o, n) {
        return void 0 === o && (o = !0), void 0 === n && (n = !0), this.cells.setCellData(e, t, i, o, n)
      }, n.prototype.hitTest = function(i, o) {
        return e.isNumber(i) && e.isNumber(o) && (i = new e.Point(i, o)), e.isBoolean(o) && o && (this._rcBounds = null), new t.HitTestInfo(this, i)
      }, n.prototype.getClipString = function(e) {
        return this._edtHdl.getClipString(e)
      }, n.prototype.setClipString = function(e, t) {
        this._edtHdl.setClipString(e, t)
      }, n.prototype.focus = function() {
        this._setFocus(!1)
      }, n.prototype.dispose = function() {
        this.finishEditing(!0), this.itemsSource = null, o.prototype.dispose.call(this)
      }, n.prototype.refresh = function(e) {
        if (void 0 === e && (e = !0), o.prototype.refresh.call(this, e), this.finishEditing(), e) {
          this._updateColumnTypes(), this.scrollPosition = this._ptScrl;
          var t = this._getDefaultRowHeight();
          this._rows._setDefaultSize(t), this._cols._setDefaultSize(4 * t), this._hdrRows._setDefaultSize(t), this._hdrCols._setDefaultSize(Math.round(1.25 * t)), this._ftrRows._setDefaultSize(t)
        }
        this.refreshCells(e)
      }, n.prototype.refreshCells = function(e, t, i) {
        this.isUpdating || (e ? this._updateLayout() : this._updateContent(t, i))
      }, n.prototype.autoSizeColumn = function(e, t, i) {
        void 0 === t && (t = !1), void 0 === i && (i = 4), this.autoSizeColumns(e, e, t, i)
      }, n.prototype.autoSizeColumns = function(i, o, r, s) {
        var l = this;
        void 0 === r && (r = !1), void 0 === s && (s = 4);
        var a = 0,
          c = r ? this.topLeftCells : this.columnHeaders,
          h = r ? this.bottomLeftCells : this.columnFooters,
          u = r ? this.rowHeaders : this.cells,
          d = this.viewRange;
        i = null == i ? 0 : e.asInt(i), o = null == o ? u.columns.length - 1 : e.asInt(o), d.row = Math.max(0, d.row - 1e3), d.row2 = Math.min(d.row2 + 1e3, this.rows.length - 1), this.finishEditing() && this.columns.deferUpdate(function() {
          e.setCss(l._eCt, {
            width: l._gpCells.width
          });
          var r = document.createElement("div");
          r.setAttribute(n._WJS_MEASURE, "true"), r.style.visibility = "hidden", u.hostElement.parentElement.appendChild(r);
          for (var g = l._getCanvasContext(), p = i; p <= o && p > -1 && p < u.columns.length; p++) {
            var f = u.columns[p];
            if (f.isVisible) {
              if (a = 0, l.autoSizeMode & t.AutoSizeMode.Headers) {
                for (_ = 0; _ < c.rows.length; _++)
                  if (c.rows[_].isVisible) {
                    w = l._getDesiredWidth(c, _, p, r);
                    a = Math.max(a, w)
                  }
                for (_ = 0; _ < h.rows.length; _++)
                  if (h.rows[_].isVisible) {
                    w = l._getDesiredWidth(h, _, p, r);
                    a = Math.max(a, w)
                  }
              }
              if (l.autoSizeMode & t.AutoSizeMode.Cells && d.isValid)
                if (f._getQuickAutoSize()) {
                  var _ = l._getWidestRow(u, d, p, g),
                    w = l._getDesiredWidth(u, _, p, r);
                  a = Math.max(a, w)
                } else
                  for (_ = d.row; _ <= d.row2 && _ < u.rows.length; _++)
                    if (u.rows[_].isVisible) {
                      w = l._getDesiredWidth(u, _, p, r);
                      a = Math.max(a, w)
                    }
              a > 0 && (f.width = a + s + 2)
            }
          }
          l.cellFactory.disposeCell(r), e.removeChild(r)
        })
      }, n.prototype.autoSizeRow = function(e, t, i) {
        void 0 === t && (t = !1), void 0 === i && (i = 0), this.autoSizeRows(e, e, t, i)
      }, n.prototype.autoSizeRows = function(i, o, r, s) {
        var l = this;
        void 0 === r && (r = !1), void 0 === s && (s = 0);
        var a = 0,
          c = r ? this.topLeftCells : this.rowHeaders,
          h = r ? this.columnHeaders : this.cells;
        r = e.asBoolean(r), s = e.asNumber(s), i = null == i ? 0 : e.asInt(i), o = null == o ? h.rows.length - 1 : e.asInt(o), this.finishEditing() && this.rows.deferUpdate(function() {
          e.setCss(l._eCt, {
            width: l._gpCells.width
          });
          var r = document.createElement("div");
          r.setAttribute(n._WJS_MEASURE, "true"), r.style.visibility = "hidden", h.hostElement.appendChild(r);
          for (var u = {}, d = i; d <= o && d > -1 && d < h.rows.length; d++) h.rows[d].isVisible && (a = 0, l.autoSizeMode & t.AutoSizeMode.Headers && (a = l._getDesiredRowHeight(c, d, r, u)), l.autoSizeMode & t.AutoSizeMode.Cells && (a = Math.max(a, l._getDesiredRowHeight(h, d, r, u))), a += s, h.rows[d].height = a > h.rows.defaultSize ? a : null);
          l.cellFactory.disposeCell(r), e.removeChild(r)
        })
      }, Object.defineProperty(n.prototype, "treeIndent", {
        get: function() {
          return this._indent
        },
        set: function(t) {
          t != this._indent && (this._indent = e.asNumber(t, !1, !0), this.columns.onCollectionChanged())
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.collapseGroupsToLevel = function(e) {
        var i = this;
        this.finishEditing() && this.deferUpdate(function() {
          var o = i.rows;
          o.deferUpdate(function() {
            for (var i = 0; i < o.length; i++) {
              var n = o[i];
              n instanceof t.GroupRow && (n.isCollapsed = n.level >= e)
            }
          })
        })
      }, Object.defineProperty(n.prototype, "selectionMode", {
        get: function() {
          return this._selHdl.selectionMode
        },
        set: function(i) {
          (i = e.asEnum(i, t.SelectionMode)) != this.selectionMode && (this._clearCells(), this._selHdl.selectionMode = i)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "selection", {
        get: function() {
          return this._selHdl.selection.clone()
        },
        set: function(e) {
          this._selHdl.selection = e
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.select = function(e, t) {
        void 0 === t && (t = !0), this._selHdl.select(e, t)
      }, n.prototype.getSelectedState = function(e, t) {
        return this.cells.getSelectedState(e, t, null)
      }, Object.defineProperty(n.prototype, "selectedRows", {
        get: function() {
          var e = [];
          if (this.selectionMode == t.SelectionMode.ListBox)
            for (o = 0; o < this.rows.length; o++) this.rows[o].isSelected && e.push(this.rows[o]);
          else if (this.rows.length)
            for (var i = this.selection, o = i.topRow; o > -1 && o <= i.bottomRow; o++) e.push(this.rows[o]);
          return e
        },
        set: function(i) {
          var o = this;
          e.assert(this.selectionMode == t.SelectionMode.ListBox, "This property can be set only in ListBox mode."), i = e.asArray(i), this.deferUpdate(function() {
            for (var e = 0, t = !0; e < o.rows.length; e++) {
              var n = o.rows[e],
                r = i && i.indexOf(n) > -1;
              r && t && (t = !1, o.select(e, o.selection.col)), n.isSelected = r
            }
          })
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "selectedItems", {
        get: function() {
          for (var e = this.selectedRows, t = 0; t < e.length; t++) e[t] = e[t].dataItem;
          return e
        },
        set: function(i) {
          var o = this;
          e.assert(this.selectionMode == t.SelectionMode.ListBox, "This property can be set only in ListBox mode."), i = e.asArray(i), this.deferUpdate(function() {
            for (var e = 0, t = !0; e < o.rows.length; e++) {
              var n = o.rows[e],
                r = i && i.indexOf(n.dataItem) > -1;
              r && t && (t = !1, o.select(e, o.selection.col)), n.isSelected = r
            }
          })
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.scrollIntoView = function(t, i, o) {
        (null == this._maxOffsetY || this._rows._dirty || this._cols._dirty) && this._updateLayout();
        var n = this.scrollPosition,
          r = this._szClient.width,
          s = this._szClient.height - this._gpCFtr.rows.getTotalSize(),
          l = this.cells._getFrozenPos();
        if ((t = e.asInt(t)) > -1 && t < this._rows.length && t >= this._rows.frozen) {
          var a = this._rows[t],
            c = this.cells.height > s ? Math.round(a.pos / (this.cells.height - s) * 100) / 100 : 0,
            h = Math.round(this._maxOffsetY * c),
            u = a.pos - h,
            d = u + a.renderSize;
          d > s - n.y && (n.y = Math.max(-u, s - d)), u - l.y < -n.y && (n.y = -(u - l.y))
        }
        if ((i = e.asInt(i)) > -1 && i < this._cols.length && i >= this._cols.frozen) {
          var g = this._cols[i],
            p = g.pos + g.renderSize;
          p > -n.x + r && (n.x = Math.max(-g.pos, r - p)), g.pos - l.x < -n.x && (n.x = -(g.pos - l.x))
        }
        return !n.equals(this._ptScrl) && (this.scrollPosition = n, o && (this._updateScrollPosition(), this.refresh()), !0)
      }, n.prototype.isRangeValid = function(e) {
        return e.isValid && e.bottomRow < this.rows.length && e.rightCol < this.columns.length
      }, n.prototype.startEditing = function(e, t, i, o) {
        return void 0 === e && (e = !0), this._edtHdl.startEditing(e, t, i, o)
      }, n.prototype.finishEditing = function(e) {
        return this._edtHdl.finishEditing(e)
      }, Object.defineProperty(n.prototype, "activeEditor", {
        get: function() {
          return this._edtHdl.activeEditor
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "editRange", {
        get: function() {
          return this._edtHdl.editRange
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "mergeManager", {
        get: function() {
          return this._mrgMgr
        },
        set: function(i) {
          i != this._mrgMgr && (this._mrgMgr = e.asType(i, t.MergeManager, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.getMergedRange = function(e, t, i, o) {
        return void 0 === o && (o = !0), this._mrgMgr ? this._mrgMgr.getMergedRange(e, t, i, o) : null
      }, Object.defineProperty(n.prototype, "keyActionTab", {
        get: function() {
          return this._keyHdl._kaTab
        },
        set: function(i) {
          this._keyHdl._kaTab = e.asEnum(i, t.KeyAction)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "keyActionEnter", {
        get: function() {
          return this._keyHdl._kaEnter
        },
        set: function(i) {
          this._keyHdl._kaEnter = e.asEnum(i, t.KeyAction)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showDropDown", {
        get: function() {
          return this._shDropDown
        },
        set: function(t) {
          t != this._shDropDown && (this._shDropDown = e.asBoolean(t, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.toggleDropDownList = function() {
        this._tglDropDown || (this._tglDropDown = !0, this._edtHdl._toggleListBox(null), this._tglDropDown = !1)
      }, n.prototype.onItemsSourceChanging = function(e) {
        return this.itemsSourceChanging.raise(this, e), !e.cancel
      }, n.prototype.onItemsSourceChanged = function(e) {
        this.itemsSourceChanged.raise(this, e)
      }, n.prototype.onScrollPositionChanged = function(e) {
        this.scrollPositionChanged.raise(this, e)
      }, n.prototype.onSelectionChanging = function(e) {
        return this.selectionChanging.raise(this, e), !e.cancel
      }, n.prototype.onSelectionChanged = function(e) {
        this.selectionChanged.raise(this, e)
      }, n.prototype.onLoadingRows = function(e) {
        return this.loadingRows.raise(this, e), !e.cancel
      }, n.prototype.onLoadedRows = function(e) {
        this.loadedRows.raise(this, e)
      }, n.prototype.onUpdatingLayout = function(e) {
        return this.updatingLayout.raise(this, e), !e.cancel
      }, n.prototype.onUpdatedLayout = function(e) {
        this.updatedLayout.raise(this, e)
      }, n.prototype.onResizingColumn = function(e) {
        return this.resizingColumn.raise(this, e), !e.cancel
      }, n.prototype.onResizedColumn = function(e) {
        this.resizedColumn.raise(this, e)
      }, n.prototype.onAutoSizingColumn = function(e) {
        return this.autoSizingColumn.raise(this, e), !e.cancel
      }, n.prototype.onAutoSizedColumn = function(e) {
        this.autoSizedColumn.raise(this, e)
      }, n.prototype.onDraggingColumn = function(e) {
        return this.draggingColumn.raise(this, e), !e.cancel
      }, n.prototype.onDraggingColumnOver = function(e) {
        return this.draggingColumnOver.raise(this, e), !e.cancel
      }, n.prototype.onDraggedColumn = function(e) {
        this.draggedColumn.raise(this, e)
      }, n.prototype.onResizingRow = function(e) {
        return this.resizingRow.raise(this, e), !e.cancel
      }, n.prototype.onResizedRow = function(e) {
        this.resizedRow.raise(this, e)
      }, n.prototype.onAutoSizingRow = function(e) {
        return this.autoSizingRow.raise(this, e), !e.cancel
      }, n.prototype.onAutoSizedRow = function(e) {
        this.autoSizedRow.raise(this, e)
      }, n.prototype.onDraggingRow = function(e) {
        return this.draggingRow.raise(this, e), !e.cancel
      }, n.prototype.onDraggingRowOver = function(e) {
        return this.draggingRowOver.raise(this, e), !e.cancel
      }, n.prototype.onDraggedRow = function(e) {
        this.draggedRow.raise(this, e)
      }, n.prototype.onGroupCollapsedChanging = function(e) {
        return this.groupCollapsedChanging.raise(this, e), !e.cancel
      }, n.prototype.onGroupCollapsedChanged = function(e) {
        this.groupCollapsedChanged.raise(this, e)
      }, n.prototype.onSortingColumn = function(e) {
        return this.sortingColumn.raise(this, e), !e.cancel
      }, n.prototype.onSortedColumn = function(e) {
        this.sortedColumn.raise(this, e)
      }, n.prototype.onBeginningEdit = function(e) {
        return this.beginningEdit.raise(this, e), !e.cancel
      }, n.prototype.onPrepareCellForEdit = function(e) {
        this.prepareCellForEdit.raise(this, e)
      }, n.prototype.onCellEditEnding = function(e) {
        return this.cellEditEnding.raise(this, e), !e.cancel && !e.stayInEditMode
      }, n.prototype.onCellEditEnded = function(e) {
        this.cellEditEnded.raise(this, e)
      }, n.prototype.onRowEditStarting = function(e) {
        this.rowEditStarting.raise(this, e)
      }, n.prototype.onRowEditStarted = function(e) {
        this.rowEditStarted.raise(this, e)
      }, n.prototype.onRowEditEnding = function(e) {
        this.rowEditEnding.raise(this, e)
      }, n.prototype.onRowEditEnded = function(e) {
        this.rowEditEnded.raise(this, e)
      }, n.prototype.onRowAdded = function(e) {
        return this.rowAdded.raise(this, e), !e.cancel
      }, n.prototype.onDeletingRow = function(e) {
        return this.deletingRow.raise(this, e), !e.cancel
      }, n.prototype.onDeletedRow = function(e) {
        this.deletedRow.raise(this, e)
      }, n.prototype.onCopying = function(e) {
        return this.copying.raise(this, e), !e.cancel
      }, n.prototype.onCopied = function(e) {
        this.copied.raise(this, e)
      }, n.prototype.onPasting = function(e) {
        return this.pasting.raise(this, e), !e.cancel
      }, n.prototype.onPasted = function(e) {
        this.pasted.raise(this, e)
      }, n.prototype.onPastingCell = function(e) {
        return this.pastingCell.raise(this, e), !e.cancel
      }, n.prototype.onPastedCell = function(e) {
        this.pastedCell.raise(this, e)
      }, n.prototype.onFormatItem = function(e) {
        this.formatItem.raise(this, e)
      }, n.prototype.onUpdatingView = function(e) {
        return this.updatingView.raise(this, e), !e.cancel
      }, n.prototype.onUpdatedView = function(e) {
        this.updatedView.raise(this, e)
      }, n.prototype._getShowErrors = function() {
        return this.showErrors && this._hasValidation
      }, n.prototype._getHasValidation = function() {
        return this._hasValidation
      }, n.prototype._getError = function(t, i, o) {
        if (e.isFunction(this.itemValidator)) {
          if (t == this.cells) return this.itemValidator(i, o);
          if (t == this.rowHeaders)
            for (o = 0; o < this.columns.length; o++)
              if (h = this.itemValidator(i, o)) return h
        }
        var n = this._cv,
          r = n ? n.getError : null;
        if (e.isFunction(r)) {
          var s = t.rows,
            l = this.columns,
            a = s[i].dataItem;
          if (a)
            for (; i < s.length && s[i].dataItem == a; i++) {
              if (t == this.cells) return r(a, (c = this._getBindingColumn(this.cells, i, l[o])).binding);
              if (t == this.rowHeaders)
                for (o = 0; o < l.length; o++) {
                  var c = this._getBindingColumn(this.cells, i, l[o]),
                    h = r(a, c.binding);
                  if (h) return h
                }
            }
        }
        return null
      }, n.prototype._setAria = function(t, i) {
        e.setAttribute(this.cells.hostElement, "aria-" + t, i)
      }, n.prototype._setFocus = function(t) {
        if (this.hostElement && (t || !this.containsFocus())) {
          var i = e.getActiveElement(),
            o = this.activeEditor,
            n = this._activeCell,
            r = this._eFocus;
          o ? e.contains(o, i) || (o.focus(), r.tabIndex = -1) : n ? e.contains(n, i) || i != this._root && (n.tabIndex = this._orgTabIndex, n.focus(), r.tabIndex = -1) : e.contains(r, i) || i == this._root || (r.tabIndex = this._orgTabIndex, r.focus()), this.containsFocus() || (r.tabIndex = this._orgTabIndex, r.focus())
        }
      }, n.prototype._setFocusNoScroll = function(t) {
        if (t.tabIndex = this._orgTabIndex, e.supportsFocusOptions()) t.focus({
          preventScroll: !0
        });
        else {
          var i = this.scrollPosition,
            o = t.style,
            n = o.position,
            r = o.opacity;
          o.opacity = "0", o.position = "fixed", t.focus(), o.position = n, o.opacity = r, this.scrollPosition = i
        }
        this._fixScroll()
      }, n.prototype._getDefaultRowHeight = function() {
        var t = this._eFocus,
          i = null;
        t.offsetHeight || ((i = e.createElement('<div><div class="wj-cell">0</div></div>', document.body)).setAttribute("class", this.hostElement.getAttribute("class")), t = i.children[0]);
        var o = t.offsetHeight;
        return (isNaN(o) || o <= 6) && (o = 28), e.removeChild(i), o
      }, n.prototype._getDefaultCellPadding = function() {
        var e = getComputedStyle(this._eFocus);
        return parseInt(this.rightToLeft ? e.paddingRight : e.paddingLeft)
      }, n.prototype._getCollectionView = function(t) {
        return e.asCollectionView(t)
      }, n.prototype._getCanvasContext = function() {
        var e = document.createElement("canvas").getContext("2d"),
          t = getComputedStyle(this.hostElement);
        return e.font = t.fontSize + " " + t.fontFamily.split(",")[0], e
      }, n.prototype._getWidestRow = function(t, i, o, n) {
        for (var r = 0, s = 0, l = t.columns[o].dataType == e.DataType.Boolean, a = i.row; a <= i.row2; a++)
          if (t.rows[a].isVisible) {
            var c = t.getCellData(a, o, !0),
              h = n.measureText(c).width;
            if (h > s && (s = h, r = a), l) break
          }
        return r
      }, n.prototype._getDesiredWidth = function(e, t, i, o) {
        var n = this.getMergedRange(e, t, i, !1),
          r = o.style;
        return this.cellFactory.updateCell(e, t, i, o, n), r.width = r.top = r.left = "", o.offsetWidth / (n && n.columnSpan > 1 ? n.columnSpan : 1)
      }, n.prototype._getDesiredHeight = function(e, t, i, o) {
        var n = this.getMergedRange(e, t, i, !1),
          r = o.style;
        return this.cellFactory.updateCell(e, t, i, o, n), r.height = r.top = r.left = "", o.offsetHeight / (n && n.rowSpan > 1 ? n.rowSpan : 1)
      }, n.prototype._getDesiredRowHeight = function(t, i, o, n) {
        for (var r = 0, s = 0; s < t.columns.length; s++) {
          var l = t.columns[s];
          if (l.isVisible) {
            var a = this.getMergedRange(t, i, s, !1),
              c = void 0;
            if (this._getQuickAutoSize()) {
              var h = {
                  ct: t.cellType,
                  col: s,
                  rng: a && a.rowSpan > 1 ? a.rowSpan : 1,
                  content: l.dataType == e.DataType.Number ? "1" : t.getCellData(i, s, !0)
                },
                u = JSON.stringify(h);
              null == (c = n[u]) && (c = this._getDesiredHeight(t, i, s, o), n[u] = c)
            } else c = this._getDesiredHeight(t, i, s, o);
            r = Math.max(r, c)
          }
        }
        return r
      }, n.prototype._getSortRowIndex = function() {
        return null != this._sortRowIndex ? this._sortRowIndex : this.columnHeaders.rows.length - 1
      }, n.prototype._sortConverter = function(e, t, i, o) {
        var n;
        if (o) {
          if (this._mappedColumns = null, this._cv)
            for (var r = this._cv.sortDescriptions, s = 0; s < r.length; s++)(n = this.getColumn(r[s].property)) && n.dataMap && (this._mappedColumns || (this._mappedColumns = {}), this._mappedColumns[n.binding] = n.dataMap);
          this._mouseHdl._htDown && this._mouseHdl._htDown.col > -1 && (n = this.columns[this._mouseHdl._htDown.col], this._mappedColumns && n.dataMap && (this._mappedColumns[n.binding] = n.dataMap))
        }
        if (this._mappedColumns) {
          var l = this._mappedColumns[e.property];
          l && l.sortByDisplayValues && (i = l.getDisplayValue(i))
        }
        return i
      }, n.prototype._bindGrid = function(i) {
        var o = this;
        if (this.finishEditing(), this.deferUpdate(function() {
          0 == o._lastCount && e.hasItems(o._cv) && (i = !0);
          var n, r = o.selectionMode == t.SelectionMode.ListBox;
          o.preserveSelectedState && r && !o.childItemsPath && (n = o.selectedItems);
          var s;
          if (o.preserveOutlineState && e.isFunction(window.Map) && o.rows.maxGroupLevel > -1) {
            s = new Map;
            for (d = 0; d < o.rows.length; d++) {
              var l = o.rows[d];
              if (l instanceof t.GroupRow && l.isCollapsed && l.dataItem) {
                var a = l.dataItem;
                a instanceof e.collections.CollectionViewGroup && (a = a._path), s.set(a, !0)
              }
            }
          }
          i && o.columns.deferUpdate(function() {
            o._bindColumns()
          });
          var c = new e.CancelEventArgs;
          o.onLoadingRows(c) && (o.rows.deferUpdate(function() {
            o._bindRows()
          }), o.onLoadedRows(c));
          var h = 0;
          if (n && n.length)
            for (d = 0; d < o.rows.length && h < n.length; d++) n.indexOf(o.rows[d].dataItem) > -1 && (o.rows[d].isSelected = !0, h++);
          if (r && 0 == h && o._lastCount > 0)
            for (var u = o.selection, d = u.topRow; d <= u.bottomRow && d > -1 && d < o.rows.length; d++) o.rows[d].isSelected = !0;
          s && o.rows.deferUpdate(function() {
            for (var i = 0; i < o.rows.length; i++) {
              var n = o.rows[i];
              if (n instanceof t.GroupRow) {
                var r = n.dataItem;
                r instanceof e.collections.CollectionViewGroup && (r = r._path), s.get(r) && (n.isCollapsed = !0)
              }
            }
          }), !o._lastCount && o._cv && o._cv.items && (o._lastCount = o._cv.items.length)
        }), !this.rows.length) {
          var n = this._selHdl.selection;
          n.row = n.row2 = -1
        }
        this._cv && setTimeout(function() {
          o._syncSelection(i)
        })
      }, n.prototype._cvCollectionChanged = function(i, o) {
        if (this.autoGenerateColumns && 0 == this.columns.length) this._bindGrid(!0);
        else {
          var n = e.collections.NotifyCollectionChangedAction;
          if (this.childItemsPath && o.action != n.Change) this._bindGrid(!1);
          else {
            switch (o.action) {
              case n.Change:
                return void this.invalidate();
              case n.Add:
                if (o.index == this._cv.items.length - 1) {
                  var r = this.rows.length;
                  return this.rows[r - 1] instanceof t._NewRowTemplate && r--, void this.rows.insert(r, new t.Row(o.item))
                }
                e.assert(!1, "added item should be the last one.");
                break;
              case n.Remove:
                var s = this._findRow(o.item);
                if (s > -1) return this.rows.removeAt(s), void this._syncSelection(!1);
                e.assert(!1, "removed item not found on grid.")
            }
            this._bindGrid(!1)
          }
        }
      }, n.prototype._cvCurrentChanged = function(e, t) {
        this._syncSelection(!1)
      }, n.prototype._syncSelection = function(i) {
        if (this._cv && this.selectionMode != t.SelectionMode.None) {
          var o = this.selection,
            n = o.row > -1 && o.row < this.rows.length ? this.rows[o.row] : null,
            r = n ? n.dataItem : null;
          if (this.newRowAtTop && n instanceof t._NewRowTemplate && (r = null), r instanceof e.collections.CollectionViewGroup && (r = null), (r != this._cv.currentItem || i) && (!this.childItemsPath || !this.editableCollectionView || !this.editableCollectionView.currentAddItem)) {
            var s = this._getRowIndex(this._cv.currentPosition);
            s == o.row && this.childItemsPath || (o.row = o.row2 = s, this.select(o, !1), this.selectionMode && this.scrollIntoView(o.row, -1))
          }
        }
      }, n.prototype._getRowIndex = function(e) {
        if (this._cv) {
          if (e > -1) {
            for (var i = this._cv.items[e]; e < this.rows.length; e++)
              if (this.rows[e].dataItem === i) return e;
            return -1
          }
          if (1 == this.rows.length && this.rows[0] instanceof t._NewRowTemplate) return 0;
          var o = this.selection.row,
            n = o > -1 ? this.rows[o] : null;
          return n && (n instanceof t.GroupRow || null == n.dataItem) ? o : -1
        }
        return this.selection.row
      }, n.prototype._getCvIndex = function(e) {
        if (this._cv && e > -1 && e < this.rows.length) {
          var t = this._cv.items,
            i = this.rows[e].dataItem;
          for (e = Math.min(e, t.length - 1); e > -1; e--)
            if (t[e] === i) return e
        }
        return -1
      }, n.prototype._findRow = function(e) {
        for (var t = 0; t < this.rows.length; t++)
          if (this.rows[t].dataItem == e) return t;
        return -1
      }, n.prototype._updateLayout = function() {
        var t = new e.CancelEventArgs;
        if (this.onUpdatingLayout(t)) {
          var o = this._hdrVis & i.Row ? this._hdrCols.getTotalSize() : 0,
            r = this._hdrVis & i.Column ? this._hdrRows.getTotalSize() : 0,
            s = this._ftrRows.getTotalSize(),
            l = this._rows.getTotalSize() + s;
          l < 1 && (l = 1), this._heightBrowser = Math.min(l, n._getMaxSupportedCssHeight()), this._maxOffsetY = Math.max(0, l - this._heightBrowser), this._cellPadding = this._getDefaultCellPadding();
          var a = this._heightBrowser + r - s,
            c = this._gpCells.width,
            h = this._heightBrowser;
          !c && this.rows.length && (c = .1), !h && this.columns.length && (h = .1), this.rightToLeft ? (e.setCss(this._eTL, {
            right: 0,
            top: 0,
            width: o,
            height: r
          }), e.setCss(this._eCHdr, {
            right: o,
            top: 0,
            height: r
          }), e.setCss(this._eRHdr, {
            right: 0,
            top: r,
            width: o
          }), e.setCss(this._eCt, {
            right: o,
            top: r,
            width: c,
            height: h
          }), e.setCss(this._fCt, {
            right: o,
            top: r
          }), e.setCss(this._eBL, {
            right: 0,
            top: a,
            width: o,
            height: s
          }), e.setCss(this._eCFtr, {
            right: o,
            top: a,
            height: s
          })) : (e.setCss(this._eTL, {
            left: 0,
            top: 0,
            width: o,
            height: r
          }), e.setCss(this._eCHdr, {
            left: o,
            top: 0,
            height: r
          }), e.setCss(this._eRHdr, {
            left: 0,
            top: r,
            width: o
          }), e.setCss(this._eCt, {
            left: o,
            top: r,
            width: c,
            height: h
          }), e.setCss(this._fCt, {
            left: o,
            top: r
          }), e.setCss(this._eBL, {
            left: 0,
            top: a,
            width: o,
            height: s
          }), e.setCss(this._eCFtr, {
            left: o,
            top: a,
            height: s
          })), this._stickyHdr && this._updateStickyHeaders();
          var u = this.frozenRows || this.frozenColumns ? "3" : "";
          e.setCss([this._eTL, this._eBL, this._eCHdr, this._eCFtr, this._eRHdr, this._eMarquee], {
            zIndex: u
          });
          var d = this._root,
            g = d.offsetWidth - d.clientWidth,
            p = d.offsetHeight - d.clientHeight;
          e.setCss(this._eSz, {
            width: o + g + this._gpCells.width,
            height: r + p + this._heightBrowser
          });
          var f = null;
          this.columns._updateStarSizes(d.clientWidth - o) && (f = d.clientWidth, e.setCss(this._eCt, {
            width: this._gpCells.width
          })), this._szClient = new e.Size(d.clientWidth - o, d.clientHeight - r), this._rcBounds = null, this._updateScrollHandler(), this._updateContent(!1), g = d.offsetWidth - d.clientWidth, p = d.offsetHeight - d.clientHeight, e.setCss(this._eSz, {
            width: o + g + this._gpCells.width,
            height: r + p + this._heightBrowser
          }), this._szClient = new e.Size(d.clientWidth - o, d.clientHeight - r), f && f != d.clientWidth && this.columns._updateStarSizes(d.clientWidth - o) && (e.setCss(this._eCt, {
            width: this._gpCells.width
          }), this._updateContent(!1)), e.setCss([this._eCHdr, this._eCFtr, this._fCt], {
            width: this._szClient.width
          }), e.setCss([this._eRHdr, this._fCt], {
            height: this._szClient.height
          }), s && (a = Math.min(a, this._szClient.height + r - s), e.setCss([this._eBL, this._eCFtr], {
            top: a
          })), this.onUpdatedLayout(t)
        }
      }, n.prototype._updateStickyHeaders = function() {
        var t = !1,
          i = 0;
        if (this._stickyHdr) {
          for (var o = 0, r = null, s = this.hostElement; s; s = s.parentElement) {
            var l = s.getBoundingClientRect();
            null == r && (r = l.top), o = Math.max(o, l.top)
          }
          i = -(r = Math.max(0, o - r - 1)), t = r > 0, this._rcBounds = null
        }
        this._eTL.style.top = this._eCHdr.style.top = t ? -i + "px" : "", e.toggleClass(this._eTL, n._WJS_STICKY, t), e.toggleClass(this._eCHdr, n._WJS_STICKY, t)
      }, n.prototype._updateScrollHandler = function() {
        this._clipToScreen = this._getClipToScreen();
        var e = this._stickyHdr || this._clipToScreen;
        e != this._scrollHandlerAttached && (this._scrollHandlerAttached = e, e ? this.addEventListener(window, "scroll", this._scroll.bind(this), !0) : this.removeEventListener(window, "scroll"))
      }, n.prototype._getClipToScreen = function() {
        if (this.rows.length <= n._MIN_VIRT_ROWS) return !1;
        if (this._root.clientHeight != this._root.scrollHeight) return !1;
        for (var e = this.hostElement; e && e != document.documentElement; e = e.parentElement)
          if ("auto" == getComputedStyle(e).overflow) return !1;
        return !0
      }, n.prototype._scroll = function(t) {
        var i = this;
        e.contains(t.target, this.hostElement) && (this._clipToScreen && (this._afScrl && cancelAnimationFrame(this._afScrl), this._afScrl = requestAnimationFrame(function() {
          i._afScrl = null, i.finishEditing(), i._updateContent(!0)
        })), this._stickyHdr && (this._afSticky && cancelAnimationFrame(this._afSticky), this._afSticky = requestAnimationFrame(function() {
          i._afSticky = null;
          var t = new e.CancelEventArgs;
          i.onUpdatingLayout(t) && (i._updateStickyHeaders(), i.onUpdatedLayout(t))
        })))
      }, n.prototype._updateScrollPosition = function() {
        var t = this._root,
          i = t.scrollTop,
          o = t.scrollLeft;
        this.rightToLeft && "rev" == n._getRtlMode() && (o = t.scrollWidth - t.clientWidth - o);
        var r = new e.Point(-Math.abs(o), -i);
        return !this._ptScrl.equals(r) && (this._ptScrl = r, this.onScrollPositionChanged(), !0)
      }, n.prototype._updateContent = function(t, o) {
        var n = this._root,
          r = this.hostElement,
          s = this.cells.hostElement,
          l = e.getActiveElement(),
          a = e.contains(r, l) ? l : null,
          c = this._activeCell,
          h = new e.CancelEventArgs;
        if (this.onUpdatingView(h)) {
          if (e.setAttribute(s, "role", this.rows.maxGroupLevel < 0 ? "grid" : "treegrid"), this._hasValidation = e.isFunction(this._itemValidator) || this._cv && e.isFunction(this._cv.getError), this._offsetY = 0, this._heightBrowser > this._szClient.height) {
            var u = Math.round(-this._ptScrl.y / (this._heightBrowser - this._szClient.height) * 100) / 100;
            this._offsetY = Math.round(this._maxOffsetY * u)
          }
          this._updateScrollPosition();
          var d = this._gpCells._updateContent(t, o, this._offsetY);
          if (this._hdrVis & i.Column && (!o || this._ssHdr & i.Column) && this._gpCHdr._updateContent(t, o, 0), this._hdrVis & i.Row && (!o || this._ssHdr & i.Row) && this._gpRHdr._updateContent(t, o, this._offsetY), this._hdrVis && !o && this._gpTL._updateContent(t, o, 0), this._gpCFtr.rows.length && (this._gpBL._updateContent(t, o, 0), this._gpCFtr._updateContent(t, o, 0)), this.showMarquee) {
            var g = this._selHdl.selection,
              p = this._eMarquee;
            if (this.isRangeValid(g)) {
              var f = this._getMarqueeRect(g),
                _ = p.firstChild,
                w = p.offsetWidth - _.offsetWidth,
                m = p.offsetHeight - _.offsetHeight;
              e.setCss(p, {
                left: f.left + s.offsetLeft - w / 2,
                top: f.top + s.offsetTop - m / 2,
                width: f.width + w,
                height: f.height + m,
                visibility: f.width > 0 && f.height > 0 ? "" : "collapse"
              })
            } else e.setCss(p, {
              left: 0,
              top: 0,
              width: 0,
              height: 0,
              visibility: "collapse"
            })
          }
          if (this._useFrozenDiv() && (this._updateFrozenCells(o), d && e.hasClass(d, "wj-frozen") && (d = null)), this._fCt.style.display = this._fCt.childElementCount ? "" : "none", this._activeCell = d, a)
            if (a != n && a != this._eFocus && e.contains(r, a) && !e.contains(s, a)) {
              if (e.getActiveElement() != a && a.focus(), e.isIE() && a instanceof HTMLInputElement && !a.type.match(/checkbox|radio|range/i)) {
                var y = a.selectionStart,
                  v = a.selectionEnd;
                a.setSelectionRange(y, v)
              }
            } else {
              var C = d != c;
              this._setFocus(C)
            }!a && d && (d.tabIndex = this._orgTabIndex), c && c != d && (c.tabIndex = -1), this._fixScroll(), this._rcBounds = null, this.onUpdatedView(h)
        }
      }, n.prototype._fixScroll = function() {
        if (!this._updating) {
          var e = this.hostElement,
            t = this._root ? this._root.parentElement : null;
          e && e.scrollTop && (e.scrollTop = 0), t && t.scrollTop && (t.scrollTop = 0)
        }
      }, n.prototype._clearCells = function() {
        for (var e in this)
          if ("_" == e[0]) {
            var i = this[e];
            i instanceof t.GridPanel && i._clearCells()
          }
        this.invalidate()
      }, n.prototype._useFrozenDiv = function() {
        return e.isBoolean(this._fzClone) ? this._fzClone : e.isIE() || e.isFirefox() || e.isSafari() || e.isMobile()
      }, n.prototype._updateFrozenCells = function(t) {
        var i = this._fCt;
        if (this.frozenRows || this.frozenColumns) {
          var o = this._eCt.querySelectorAll(".wj-frozen");
          if (t && i.children.length == o.length) {
            for (n = 0; n < o.length; n++) i.children[n].className = o[n].className;
            return
          }
          if (e.setText(i, null), !this.activeEditor)
            for (var n = 0; n < o.length; n++) {
              var r = o[n];
              e.closest(r, ".wj-flexgrid") == this.hostElement && (r = o[n].cloneNode(!0), i.appendChild(r))
            }
        } else e.setText(i, null)
      }, n.prototype._getMarqueeRect = function(i) {
        var o = this.getMergedRange(this.cells, i.topRow, i.leftCol) || new t.CellRange(i.topRow, i.leftCol),
          n = this.getMergedRange(this.cells, i.bottomRow, i.rightCol) || new t.CellRange(i.bottomRow, i.rightCol),
          r = this.cells.getCellBoundingRect(o.topRow, o.leftCol, !0),
          s = this.cells.getCellBoundingRect(n.bottomRow, n.rightCol, !0);
        if (this.rows.frozen) {
          var l = Math.min(this.rows.length, this.rows.frozen),
            a = this.cells.getCellBoundingRect(l - 1, 0, !0);
          i.topRow >= l && r.top < a.bottom && (r.top = a.bottom), i.bottomRow >= l && s.bottom < a.bottom && (s.height = a.bottom - s.top)
        }
        if (this.columns.frozen) {
          var c = Math.min(this.columns.length, this.columns.frozen),
            a = this.cells.getCellBoundingRect(0, c - 1, !0);
          this.rightToLeft ? (i.leftCol >= c && r.right > a.left && (r.left = a.left - r.width), i.rightCol >= c && s.left > a.left && (s.left = a.left)) : (i.leftCol >= c && r.left < a.right && (r.left = a.right), i.rightCol >= c && s.right < a.right && (s.width = a.right - s.left))
        }
        return this.rightToLeft ? new e.Rect(s.left, r.top, r.right - s.left, s.bottom - r.top) : new e.Rect(r.left, r.top, s.right - r.left, s.bottom - r.top)
      }, n.prototype._bindColumns = function() {
        for (var i = 0; i < this.columns.length; i++)(c = this.columns[i])._getFlag(t.RowColFlags.AutoGenerated) && (this.columns.removeAt(i), i--);
        var o = this._cv,
          n = o ? o.sourceCollection : null,
          r = n && n.length ? n[0] : null;
        if (r && this.autoGenerateColumns)
          for (var s in r)
            for (var l = null, a = 0; a < n.length && a < 1e3 && null == l; a++)
              if (l = n[a][s], e.isPrimitive(l)) {
                var c = new t.Column;
                c._setFlag(t.RowColFlags.AutoGenerated, !0), c.binding = c.name = s, c.header = e.toHeaderCase(s), c.dataType = e.getType(l), c.dataType == e.DataType.Number && (c.width = 80);
                var h = Object.getOwnPropertyDescriptor(r, s);
                !h || h.writable || e.isFunction(h.set) || c._setFlag(t.RowColFlags.ReadOnly, !0), this.columns.push(c)
              }
        this._updateColumnTypes()
      }, n.prototype._updateColumnTypes = function() {
        var t = this._cv;
        if (e.hasItems(t))
          for (var i = t.items[0], o = this.columns, n = 0; n < o.length; n++) {
            var r = o[n];
            null == r.dataType && r._binding && (r.dataType = e.getType(r._binding.getValue(i)))
          }
      }, n.prototype._getBindingColumn = function(e, t, i) {
        return i
      }, n.prototype._getRowHeaderPath = function() {
        return this._rowHdrPath
      }, n.prototype._bindRows = function() {
        this.rows.clear();
        var e = this._cv;
        if (e && e.items) {
          var t = e.items,
            i = e.groups;
          if (this.childItemsPath)
            for (o = 0; o < t.length; o++) this._addNode(t, o, 0);
          else if (null != i && i.length > 0 && this.showGroups)
            for (o = 0; o < i.length; o++) this._addGroup(i[o]);
          else
            for (var o = 0; o < t.length; o++) this._addBoundRow(t, o)
        }
      }, n.prototype._addBoundRow = function(e, i) {
        this.rows.push(new t.Row(e[i]))
      }, n.prototype._addNode = function(i, o, n) {
        var r = new t.GroupRow,
          s = this.childItemsPath,
          l = e.isArray(s) ? s[n] : s,
          a = i[o],
          c = a[l];
        if (r.dataItem = a, r.level = n, this.rows.push(r), e.isArray(c))
          for (var h = 0; h < c.length; h++) this._addNode(c, h, n + 1)
      }, n.prototype._addGroup = function(e) {
        var i = new t.GroupRow;
        if (i.level = e.level, i.dataItem = e, this.rows.push(i), e.isBottomLevel)
          for (var o = e.items, n = 0; n < o.length; n++) this._addBoundRow(o, n);
        else
          for (n = 0; n < e.groups.length; n++) this._addGroup(e.groups[n])
      }, n._getSerializableProperties = function(e) {
        var t = [];
        for (e = e.prototype; e != Object.prototype; e = Object.getPrototypeOf(e))
          for (var i = Object.getOwnPropertyNames(e), o = 0; o < i.length; o++) {
            var n = i[o],
              r = Object.getOwnPropertyDescriptor(e, n);
            r && r.set && r.get && "_" != n[0] && !n.match(/disabled|required/) && t.push(n)
          }
        return t
      }, n.prototype._copy = function(i, o) {
        if ("columns" == i) {
          this.columns.clear();
          for (var n = e.asArray(o), r = 0; r < n.length; r++) {
            var s = new t.Column;
            e.copy(s, n[r]), this.columns.push(s)
          }
          return !0
        }
        return !1
      }, n.prototype._isInputElement = function(t) {
        if (t instanceof HTMLElement && !e.hasClass(t, "wj-btn-glyph")) {
          if ("true" == t.contentEditable) return !0;
          var i = t.tagName.match(/^(BUTTON|A|INPUT|TEXTAREA|SELECT|OPTION)$/i);
          return i && i.length > 0
        }
        return !1
      }, n.prototype._wantsInput = function(t) {
        return this._isInputElement(t) && !this.activeEditor && !this._edtHdl._isNativeCheckbox(t) && !e.hasClass(t, "wj-grid-ime") && e.contains(document.body, t)
      }, n._getMaxSupportedCssHeight = function() {
        if (!n._maxCssHeight) {
          var t = 335e5;
          e.isIE() ? t = 15e5 : e.isFirefox() && (t = 175e5), n._maxCssHeight = t
        }
        return n._maxCssHeight
      }, n._getRtlMode = function() {
        if (!n._rtlMode) {
          var t = e.createElement('<div dir="rtl" style="visibility:hidden;width:100px;height:100px;overflow:auto"><div style="width:2000px;height:2000px"></div></div>');
          document.body.appendChild(t);
          var i = t.scrollLeft;
          t.scrollLeft = -1e3;
          var o = t.scrollLeft;
          e.removeChild(t), n._rtlMode = o < 0 ? "neg" : i > 0 ? "rev" : "std"
        }
        return n._rtlMode
      }, n._WJS_STICKY = "wj-state-sticky", n._WJS_MEASURE = "wj-state-measuring", n._WJS_UPDATING = "wj-state-updating", n._MIN_VIRT_ROWS = 200, n.controlTemplate = '<div style="position:relative;width:100%;height:100%;overflow:hidden;max-width:inherit;max-height:inherit"><div wj-part="root" style="position:absolute;width:100%;height:100%;overflow:auto;max-width:inherit;max-height:inherit;-webkit-overflow-scrolling:touch;"><div wj-part="cells" class="wj-cells" style="position:absolute"></div><div wj-part="marquee" class="wj-marquee" style="display:none;pointer-events:none;"><div style="width:100%;height:100%"></div></div></div><div wj-part="fcells" aria-hidden="true" class="wj-cells wj-frozen-clone" style="position:absolute;pointer-events:none;overflow:hidden"></div><div wj-part="rh"><div wj-part="rhcells" class="wj-rowheaders"></div></div><div wj-part="cf"><div wj-part="cfcells" class="wj-colfooters"></div></div><div wj-part="ch"><div wj-part="chcells" class="wj-colheaders"></div></div><div wj-part="bl"><div wj-part="blcells" class="wj-bottomleft"></div></div><div wj-part="tl"><div wj-part="tlcells" class="wj-topleft"></div></div><div wj-part="focus" class="wj-cell" style="position:fixed;left:-32000px">0</div><div wj-part="sz" style="position:relative;visibility:hidden"></div></div>', n
    }(e.Control);
    t.FlexGrid = o
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
      function o() {
        this.constructor = t
      }
      e(t, i), t.prototype = null === i ? Object.create(i) : (o.prototype = i.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i = function(i) {
      function o(o, n, r) {
        var s = i.call(this) || this;
        return s._p = e.asType(o, t.GridPanel, !0), s._rng = e.asType(n, t.CellRange, !0), s._data = r, s
      }
      return __extends(o, i), Object.defineProperty(o.prototype, "panel", {
        get: function() {
          return this._p
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "range", {
        get: function() {
          return this._rng.clone()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "row", {
        get: function() {
          return this._rng.row
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "col", {
        get: function() {
          return this._rng.col
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "data", {
        get: function() {
          return this._data
        },
        set: function(e) {
          this._data = e
        },
        enumerable: !0,
        configurable: !0
      }), o
    }(e.CancelEventArgs);
    t.CellRangeEventArgs = i;
    var o = function(t) {
      function i(i, o, n) {
        var r = t.call(this, i, o) || this;
        return r._cell = e.asType(n, HTMLElement), r
      }
      return __extends(i, t), Object.defineProperty(i.prototype, "cell", {
        get: function() {
          return this._cell
        },
        enumerable: !0,
        configurable: !0
      }), i
    }(i);
    t.FormatItemEventArgs = o;
    var n = function(t) {
      function i() {
        var e = null !== t && t.apply(this, arguments) || this;
        return e._stayInEditMode = !1, e._refresh = !0, e
      }
      return __extends(i, t), Object.defineProperty(i.prototype, "stayInEditMode", {
        get: function() {
          return this._stayInEditMode
        },
        set: function(t) {
          this._stayInEditMode = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "refresh", {
        get: function() {
          return this._refresh
        },
        set: function(t) {
          this._refresh = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), i
    }(i);
    t.CellEditEndingEventArgs = n
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i;
    ! function(e) {
      e[e.None = 0] = "None", e[e.Cell = 1] = "Cell", e[e.ColumnHeader = 2] = "ColumnHeader", e[e.RowHeader = 3] = "RowHeader", e[e.TopLeft = 4] = "TopLeft", e[e.ColumnFooter = 5] = "ColumnFooter", e[e.BottomLeft = 6] = "BottomLeft"
    }(i = t.CellType || (t.CellType = {}));
    var o = function() {
      function o(i, o, n, r, s) {
        this._offsetY = 0, this._g = e.asType(i, t.FlexGrid), this._ct = e.asInt(o), this._rows = e.asType(n, t.RowCollection), this._cols = e.asType(r, t.ColumnCollection), this._e = e.asType(s, HTMLElement), this._vrb = new t.CellRange
      }
      return Object.defineProperty(o.prototype, "grid", {
        get: function() {
          return this._g
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "cellType", {
        get: function() {
          return this._ct
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "viewRange", {
        get: function() {
          return this._getViewRange()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "width", {
        get: function() {
          return this._cols.getTotalSize()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "height", {
        get: function() {
          return this._rows.getTotalSize()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "rows", {
        get: function() {
          return this._rows
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "columns", {
        get: function() {
          return this._cols
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.getCellData = function(o, n, r) {
        var s, l = this._rows[e.asNumber(o, !1, !0)],
          a = null;
        if (e.isString(n) && (n = this._cols.indexOf(n)) < 0) throw "Invalid column name or binding.";
        s = this._cols[e.asNumber(n, !1, !0)];
        var c = this._g ? this._g._getBindingColumn(this, o, s) : s;
        if (!c.binding || !l.dataItem || l.dataItem instanceof e.collections.CollectionViewGroup ? l._ubv && (a = l._ubv[s._hash]) : a = c._binding.getValue(l.dataItem), null == a) switch (this._ct) {
          case i.ColumnHeader:
            o != this._rows.length - 1 && c == s || (a = c.header);
            break;
          case i.ColumnFooter:
            if (c.aggregate != e.Aggregate.None && l instanceof t.GroupRow) {
              var h = this._g.collectionView;
              if (h) {
                var u = e.tryCast(h, e.collections.CollectionView);
                a = u ? u.getAggregate(c.aggregate, c.binding) : e.getAggregate(c.aggregate, h.items, c.binding)
              }
            }
            break;
          case i.Cell:
            if (c.aggregate != e.Aggregate.None && l instanceof t.GroupRow) {
              var d = e.tryCast(l.dataItem, e.collections.CollectionViewGroup);
              d && (a = d.getAggregate(c.aggregate, c.binding, this._g.collectionView))
            }
        }
        return r && (this.cellType == i.Cell && c.dataMap && (a = c.dataMap.getDisplayValue(a)), a = null != a ? e.Globalize.format(a, c.format) : ""), a
      }, o.prototype.setCellData = function(t, o, n, r, s) {
        void 0 === r && (r = !0), void 0 === s && (s = !0);
        var l, a = this._rows[e.asNumber(t, !1, !0)];
        if (e.isString(o) && (o = this._cols.indexOf(o)) < 0) throw "Invalid column name or binding.";
        l = this._cols[e.asNumber(o, !1, !0)];
        var c = this._g ? this._g._getBindingColumn(this, t, l) : l;
        if (this._ct == i.Cell) {
          if (c.dataMap && null != n && (c.isRequired || "" != n)) {
            var h = c.dataMap,
              u = h.getKeyValue(n);
            if (null == u && null == h.getDisplayValue(null)) {
              if (h.getDisplayValue(n) != n);
              else if (!h.isEditable || h.displayMemberPath != h.selectedValuePath) return !1
            } else n = u
          }
          var d = e.DataType.Object;
          if (c.dataType) d = c.dataType;
          else {
            var g = this.getCellData(t, o, !1);
            d = e.getType(g)
          }
          if (e.isBoolean(c.isRequired))
            if (c.isRequired || "" !== n && null !== n) {
              if (c.isRequired && ("" === n || null === n)) return !1
            } else n = null, r = !1;
          if (r && (n = e.changeType(n, d, c.format), d != e.DataType.Object && e.getType(n) != d)) return !1
        }
        if (a.dataItem && c.binding) {
          var p = c._binding,
            f = a.dataItem,
            _ = p.getValue(f);
          if (n !== _ && !e.DateTime.equals(n, _)) {
            p.setValue(f, n);
            var w = this._g.collectionView;
            if (w instanceof e.collections.CollectionView && f != w.currentEditItem) {
              var m = new e.collections.NotifyCollectionChangedEventArgs(e.collections.NotifyCollectionChangedAction.Change, f, w.items.indexOf(f));
              w.onCollectionChanged(m)
            }
          }
        } else a._ubv || (a._ubv = {}), a._ubv[l._hash] = n;
        return s && this._g && this._g.invalidate(), !0
      }, o.prototype.getCellBoundingRect = function(t, i, o) {
        var n = this.rows[t],
          r = this.columns[i],
          s = new e.Rect(r.pos, n.pos, r.renderSize, n.renderSize);
        if (this._g.rightToLeft && (s.left = this.hostElement.clientWidth - s.right, !e.isIE() && !e.isFirefox())) {
          var l = this.hostElement.parentElement;
          s.left -= l.offsetWidth - l.clientWidth
        }
        if (!o) {
          var a = this.hostElement.getBoundingClientRect();
          s.left += a.left, s.top += a.top - this._offsetY
        }
        return t < this.rows.frozen && (s.top -= this._g.scrollPosition.y), i < this.columns.frozen && (s.left -= this._g.scrollPosition.x * (this._g.rightToLeft ? -1 : 1)), s
      }, o.prototype.getCellElement = function(e, t) {
        for (var i = this.hostElement.children, n = Math.min(e + 2, i.length), r = 0; r < n; r++)
          for (var s = i[r].children, l = Math.min(t + 2, s.length), a = 0; a < l; a++) {
            var c = s[a],
              h = c[o._INDEX_KEY];
            if (h && (h.row == e && h.col == t || h.rng && h.rng.contains(e, t))) return c
          }
        return null
      }, o.prototype.getSelectedState = function(e, o, n) {
        var r = this._g,
          s = r.selectionMode,
          l = r._selHdl.selection,
          a = t.SelectionMode,
          c = t.SelectedState;
        if (s != a.None) switch (this._ct) {
          case i.Cell:
            if (n || (n = r.getMergedRange(this, e, o)), n) {
              if (n.contains(l.row, l.col)) return r.showMarquee ? c.None : c.Cursor;
              if (n.intersects(l)) return c.Selected
            }
            if (l.row == e && l.col == o) return r.showMarquee ? c.Active : c.Cursor;
            if (r.rows[e].isSelected || r.columns[o].isSelected) return c.Selected;
            if (n) switch (s) {
              case a.Row:
              case a.RowRange:
              case a.ListBox:
                if (n.containsRow(l.row)) return c.Selected
            }
            return l = r._selHdl._adjustSelection(l), s == t.SelectionMode.ListBox ? c.None : l.containsRow(e) && l.containsColumn(o) ? c.Selected : c.None;
          case i.ColumnHeader:
            if (r.showSelectedHeaders & t.HeadersVisibility.Column && (r.columns[o].isSelected || l.containsColumn(o) || l.intersectsColumn(n)) && (n && (e = n.bottomRow), e == this.rows.length - 1)) return c.Selected;
            break;
          case i.RowHeader:
            if (r.showSelectedHeaders & t.HeadersVisibility.Row && (r.rows[e].isSelected || l.containsRow(e) || l.intersectsRow(n)) && (n && (o = n.rightCol), o == this.columns.length - 1)) return c.Selected
        }
        return c.None
      }, Object.defineProperty(o.prototype, "hostElement", {
        get: function() {
          return this._e
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype._getOffsetY = function() {
        return this._offsetY
      }, o.prototype._updateContent = function(e, o, n) {
        var r = this._g,
          s = this._e,
          l = this._rows,
          a = this._cols,
          c = this._ct;
        if (c == i.ColumnHeader || c == i.ColumnFooter || c == i.RowHeader) {
          var h = r._ptScrl,
            u = s.style;
          c == i.RowHeader ? u.top = h.y + "px" : r.rightToLeft ? u.right = h.x + "px" : u.left = h.x + "px"
        }
        this._offsetY != n && (e = !1, this._offsetY = n);
        var d = this._getViewRange(),
          g = d;
        if (g.isValid) {
          var p = r.isTouching,
            f = l.length <= r._vtRows ? 1e7 : p ? 6 : 0,
            _ = a.length <= r._vtCols ? 1e7 : p ? 1 : 0;
          g = new t.CellRange(Math.max(d.row - f, l.frozen), Math.max(d.col - _, a.frozen), Math.min(d.row2 + f, l.length - 1), Math.min(d.col2 + _, a.length - 1))
        }
        if (e && !o && this._vrb.contains(d) && !l.frozen && !a.frozen) return this._activeCell;
        e && g.equals(this._vrb) || (o = !1), e && !o && this._ct != i.TopLeft && this._reorderCells(g, this._vrb), this._activeCell = null, this._vru = d, this._vrb = g, this._cf = r.cellFactory;
        var w = 0;
        this._ct == i.Cell && (w = this._renderColHdrRow(g, o));
        for (m = 0; m < l.frozen && m < l.length; m++) w = this._renderRow(m, g, o, w);
        for (var m = g.topRow; m <= g.bottomRow && m > -1; m++) w = this._renderRow(m, g, o, w);
        for (; s.childElementCount > w;) {
          var y = s.lastElementChild;
          s.removeChild(y), this._removeExtraCells(y, 0)
        }
        return this._activeCell
      }, o.prototype._updateScrollPosition = function() {
        var e = this._g,
          t = e._ptScrl,
          o = this.hostElement.style;
        this.cellType == i.RowHeader ? o.top = t.y + "px" : e.rightToLeft ? o.right = t.x + "px" : o.left = t.x + "px"
      }, o.prototype._clearCells = function() {
        for (var e = this.hostElement, t = e.childElementCount - 1; t >= 0; t--) {
          var i = e.children[t];
          e.removeChild(i);
          for (var o = i.childElementCount - 1; o >= 0; o--) this._cf.disposeCell(i.children[o])
        }
      }, o.prototype._reorderCells = function(e, t) {
        if (!(this._rows.frozen > 0 || this._cols.frozen > 0 || e.columnSpan != t.columnSpan || e.rowSpan != t.rowSpan) && t.isValid && e.isValid && e.intersects(t) && e.row != t.row) {
          var o = e.row - t.row,
            n = Math.max(1, e.rowSpan - 1),
            r = this._e;
          if (0 != o && Math.abs(o) < n) {
            var s = this._ct == i.Cell ? 1 : 0,
              l = r.childElementCount;
            if (o > 0) {
              var a = s,
                c = Math.min(s + o, l - 1);
              (h = this._createRange(a, c)) && c < l - 1 && r.appendChild(h.extractContents())
            }
            if (o < 0) {
              var c = l,
                a = Math.max(s, c + o),
                h = this._createRange(a, c);
              if (h && a > s) {
                var u = r.children[s];
                r.insertBefore(h.extractContents(), u)
              }
            }
          }
        }
      }, o.prototype._createRange = function(e, t) {
        var i, o = this._e.childElementCount;
        return t > e && t <= o && e > -1 && ((i = document.createRange()).setStart(this._e, e), i.setEnd(this._e, t)), i
      }, o.prototype._renderColHdrRow = function(t, i) {
        var o = this._e.children[0];
        o || (o = e.createElement('<div class="wj-row" role="row"></div>', this._e));
        var n = this._g,
          r = n ? n.columnHeaders.rows.ariaLabel : null;
        e.setAttribute(o, "aria-label", r), e.setAttribute(o, "aria-selected", null);
        var s = 0,
          l = this._g._getRowHeaderPath();
        l && (s = this._renderRowHdrCell(o, -1, l.path));
        for (a = 0; a < this.columns.frozen && a < this.columns.length; a++) s = this._renderColHdrCell(o, a, t, i, s);
        for (var a = t.leftCol; a <= t.rightCol && a > -1; a++) s = this._renderColHdrCell(o, a, t, i, s);
        return this._removeExtraCells(o, s), 1
      }, o.prototype._renderColHdrCell = function(t, i, n, r, s) {
        var l = this.columns[i];
        if (l.renderSize <= 0) return s;
        if (r) return s + 1;
        var a = t.children[s];
        a || (a = e.createElement('<div class="wj-cell" tabindex="-1"></div>', t)), e.setAttribute(a, "role", "columnheader"), a.textContent = this.columns[i].header, e.setCss(a, {
          position: "fixed",
          top: -32e3,
          height: .1,
          left: l.pos,
          width: l.renderWidth,
          overflow: "hidden",
          opacity: "0",
          pointerEvents: "none"
        });
        var c = [l.describedById, this.columns.describedById].join(" ").trim();
        e.setAttribute(a, "aria-describedby", c || null);
        var h = this.grid;
        if (h.allowSorting) {
          var u = "none";
          switch (h._getBindingColumn(this, 0, l).currentSort) {
            case "+":
              u = "ascending";
              break;
            case "-":
              u = "descending"
          }
          e.setAttribute(a, "aria-sort", u)
        }
        return h.isReadOnly || (e.setAttribute(a, "aria-readonly", l.isReadOnly), e.setAttribute(a, "aria-required", l.getIsRequired())), a[o._INDEX_KEY] = {
          row: -1,
          col: i,
          panel: this
        }, s + 1
      }, o.prototype._renderRowHdrCell = function(t, i, n) {
        var r = t.children[0];
        return r || (r = e.createElement('<div class="wj-cell" tabindex="-1"></div>', t)), r.setAttribute("role", i < 0 ? "columnheader" : "rowheader"), r.textContent = n ? n.toString() : "", e.setCss(r, {
          position: "fixed",
          left: -32e3,
          top: -32e3,
          width: .1,
          height: .1,
          overflow: "hidden",
          opacity: "0"
        }), r[o._INDEX_KEY] = {
          row: i,
          col: -1,
          panel: this
        }, 1
      }, o.prototype._renderRow = function(o, n, r, s) {
        var l = this._g,
          a = this.rows[o];
        if (a.renderSize <= 0) return s;
        var c = this._e.children[s];
        if (c || (c = e.createElement('<div class="wj-row"></div>', this._e)), this._ct == i.Cell) {
          c.setAttribute("role", "row");
          var h = t.SelectionMode;
          switch (l.selectionMode) {
            case h.Row:
            case h.RowRange:
            case h.ListBox:
              var u = a.isSelected || this._g._selHdl.selection.containsRow(o);
              e.setAttribute(c, "aria-selected", !!u)
          }
          e.setAttribute(c, "aria-level", a instanceof t.GroupRow ? a.level + 1 : null), e.setAttribute(c, "aria-expanded", a instanceof t.GroupRow ? !a.isCollapsed : null), this.rows.ariaLabel && e.setAttribute(c, "aria-label", this.rows.ariaLabel)
        }
        var d = 0;
        if (this._ct == i.Cell) {
          var g = this._g._getRowHeaderPath();
          g && (d = this._renderRowHdrCell(c, o, g.getValue(a.dataItem)))
        }
        for (p = 0; p < this.columns.frozen && p < this.columns.length; p++) d = this._renderCell(c, o, p, n, r, d);
        for (var p = n.leftCol; p <= n.rightCol && p > -1; p++) d = this._renderCell(c, o, p, n, r, d);
        return this._removeExtraCells(c, d), s + 1
      }, o.prototype._renderCell = function(n, r, s, l, a, c) {
        var h = this._g,
          u = h.getMergedRange(this, r, s);
        if (u) {
          for (d = Math.max(l.row, u.row); d < r; d++)
            if (this.rows[d].isVisible) return c;
          for (var d = Math.max(l.col, u.col); d < s; d++)
            if (this.columns[d].isVisible) return c
        }
        var g = this.columns[s];
        if (g.renderSize <= 0 && (!u || u.getRenderSize(this).width <= 0)) return c;
        var p = n.children[c],
          f = t.SelectedState,
          _ = this.getSelectedState(r, s, u),
          w = _ == f.Cursor || _ == f.Active;
        return p && a ? (e.toggleClass(p, "wj-state-active", w), e.toggleClass(p, "wj-state-selected", _ == f.Cursor), e.toggleClass(p, "wj-state-multi-selected", _ == f.Selected), e.setAttribute(p, "aria-selected", _ != f.None || w), w && (this._activeCell = p), c + 1) : (p || ((p = document.createElement("div")).tabIndex = -1, n.appendChild(p)), w && (this._activeCell = p), this._ct == i.Cell && (e.setAttribute(p, "role", "gridcell"), e.setAttribute(p, "aria-selected", _ != f.None || w), e.setAttribute(p, "aria-readonly", !h.canEditCell(r, s) || null), e.setAttribute(p, "aria-required", g.getIsRequired())), h.cellFactory.updateCell(this, r, s, p, u), p[o._INDEX_KEY] = {
          row: r,
          col: s,
          rng: u,
          panel: this
        }, c + 1)
      }, o.prototype._removeExtraCells = function(e, t) {
        for (; e.childElementCount > t;) {
          var i = e.lastElementChild;
          e.removeChild(i), this._cf.disposeCell(i)
        }
      }, o.prototype._getViewRange = function() {
        var e = this._g,
          o = e._ptScrl,
          n = this._rows,
          r = this._cols,
          s = new t.CellRange(0, 0, n.length - 1, r.length - 1);
        if (this._ct == i.Cell || this._ct == i.RowHeader) {
          var l = -o.y + this._offsetY,
            a = e._szClient.height;
          if ((d = Math.min(n.frozen, n.length - 1)) > 0 && (l += g = n[d - 1].pos, a -= g), s.row = Math.min(n.length - 1, Math.max(n.frozen, n.getItemAt(l + 1))), s.row2 = n.getItemAt(l + a), e._clipToScreen) {
            var c = e.hostElement.getBoundingClientRect();
            c.top < 0 && (s.row = Math.max(s.row, n.getItemAt(-c.top - e._ptScrl.y) - 1)), c.bottom > innerHeight && (s.row2 = Math.min(s.row2, n.getItemAt(-c.top - e._ptScrl.y + innerHeight) + 1))
          }
        }
        if (this._ct == i.Cell || this._ct == i.ColumnHeader) {
          var h = -o.x,
            u = e._szClient.width,
            d = Math.min(r.frozen, r.length - 1);
          if (d > 0) {
            var g = r[d - 1].pos;
            h += g, u -= g
          }
          s.col = Math.min(r.length - 1, Math.max(r.frozen, r.getItemAt(h + 1))), s.col2 = r.getItemAt(h + u)
        }
        return n.length <= n.frozen && (s.row = s.row2 = -1), r.length <= r.frozen && (s.col = s.col2 = -1), s
      }, o.prototype._getFrozenPos = function() {
        var t = this._rows.frozen,
          i = this._cols.frozen,
          o = t > 0 ? this._rows[t - 1] : null,
          n = i > 0 ? this._cols[i - 1] : null,
          r = o ? o.pos + o.renderSize : 0,
          s = n ? n.pos + n.renderSize : 0;
        return new e.Point(s, r)
      }, o._INDEX_KEY = "wj-cell-index", o
    }();
    t.GridPanel = o
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i = function() {
      function i() {}
      return i.prototype.updateCell = function(o, n, r, s, l, a) {
        var c = o.grid,
          h = c.rightToLeft,
          u = t.CellType,
          d = o.rows,
          g = o.columns,
          p = d[n],
          f = g[r],
          _ = n,
          w = r,
          m = p instanceof t.GroupRow ? p : null,
          y = p instanceof t._NewRowTemplate ? p : null,
          v = f.renderWidth,
          C = p.renderHeight,
          b = o.cellType,
          R = "wj-cell",
          S = "",
          E = {
            display: ""
          };
        if (0 != a && s.firstElementChild && (1 == s.childNodes.length && "checkbox" == s.firstElementChild.type || (s.textContent = "")), l && !l.isSingleCell) {
          n = l.row, r = l.col, _ = l.row2, w = l.col2, p = d[n], f = g[r], m = p instanceof t.GroupRow ? p : null;
          var z = l.getRenderSize(o);
          C = z.height, v = z.width
        }
        var x = c._getBindingColumn(o, n, f),
          T = x.dataType == e.DataType.Boolean && !x.dataMap,
          H = f.pos,
          P = p.pos;
        if (c._useFrozenDiv() && b == u.Cell && !c.editRange ? (n < d.frozen && r >= g.frozen && (H += c._ptScrl.x), r < g.frozen && n >= d.frozen && (P += c._ptScrl.y)) : (n < d.frozen && (P -= c._ptScrl.y), r < g.frozen && (H -= c._ptScrl.x)), h ? E.right = H + "px" : E.left = H + "px", E.top = P - o._getOffsetY() + "px", E.width = v + "px", E.height = C + "px", E.zIndex = "", (n < d.frozen || r < g.frozen) && (E.zIndex = n < d.frozen && r < g.frozen ? 2 : 1), b == u.Cell ? (m && (R += " wj-group"), c.showAlternatingRows && p.visibleIndex % 2 != 0 && (l && l.row != l.row2 || (R += " wj-alt")), (n < d.frozen || r < g.frozen) && (R += " wj-frozen"), y && (R += " wj-new"), p.cssClass && (R += " " + p.cssClass), x.cssClass && (R += " " + x.cssClass)) : (R += " wj-header", c.showAlternatingRows && n % 2 != 0 && (R += " wj-header-alt")), (b == u.Cell || b == u.RowHeader) && c._getShowErrors()) {
          var M = c._getError(o, n, r);
          e.setAttribute(s, "title", M), M && (R += " wj-state-invalid")
        }
        var j = t.SelectedState,
          A = o.getSelectedState(n, r, l);
        switch (A != j.None && b == u.Cell && !T && c.editRange && c.editRange.contains(n, r) && (A = j.None), A) {
          case j.Active:
            R += " wj-state-active";
            break;
          case j.Cursor:
            R += " wj-state-selected wj-state-active";
            break;
          case j.Selected:
            R += " wj-state-multi-selected"
        }
        switch (_ == d.frozen - 1 && (R += " wj-frozen-row"), w == g.frozen - 1 && (R += " wj-frozen-col"), (x.wordWrap || p.wordWrap) && (R += " wj-wrap"), (x.multiLine || p.multiLine) && (R += " wj-multiline"), x.getAlignment()) {
          case "right":
            S = " wj-align-right";
            break;
          case "center":
            S = " wj-align-center";
            break;
          case "justify":
            S = " wj-align-justify"
        }
        if (E.paddingLeft = E.paddingRight = E.paddingTop = E.paddingBottom = "", b == u.Cell && c.rows.maxGroupLevel > -1 && r == c.columns.firstVisibleIndex && c.treeIndent) {
          var D = m ? Math.max(0, m.level) : c.rows.maxGroupLevel + 1,
            O = c.treeIndent * D + c._cellPadding;
          h ? E.paddingRight = O + "px" : E.paddingLeft = O + "px"
        }
        if (0 != a) {
          var L = o.getCellData(n, r, !1),
            I = o.getCellData(n, r, !0);
          if (b == u.Cell && r == c.columns.firstVisibleIndex && m && m.hasChildren && !this._isEditingCell(c, n, r)) {
            Y = this._getTreeBtn(m);
            I = e.escapeHtml(I) || m.getGroupHeader(), s.innerHTML = Y.outerHTML + " " + I, S = ""
          } else if (b == u.ColumnHeader && x.currentSort && c.showSort && (_ == c._getSortRowIndex() || x != f)) R += " wj-sort-" + ("+" == x.currentSort ? "asc" : "desc"), s.innerHTML = e.escapeHtml(I) + "&nbsp;" + this._getSortIcon(x);
          else if (b != u.RowHeader || r != c.rowHeaders.columns.length - 1 || I)
            if (b != u.Cell || x.dataType != e.DataType.Boolean || x.dataMap || m && !e.isBoolean(L))
              if (b == u.Cell && this._isEditingCell(c, n, r)) {
                var N = x.inputType;
                if (x.inputType || (N = x.dataType != e.DataType.Number || x.dataMap ? "text" : "tel"), !x.dataMap && !x.mask) {
                  var B = o.getCellData(n, r, !1);
                  if (e.isNumber(B)) {
                    var k = B.toString(),
                      F = x.format;
                    if (F && B != Math.round(B)) {
                      var V = k.match(/\.(\d+)/)[1].length;
                      F = F.replace(/([a-z])(\d*)(.*)/gi, "$01" + V + "$3")
                    }
                    I = e.Globalize.formatNumber(B, F, !0)
                  }
                }
                s.innerHTML = (x.multiLine || p.multiLine) && "checkbox" != N ? '<textarea wrap="soft"></textarea>' : '<input type="' + N + '"/>';
                var G = s.children[0];
                e.addClass(G, "wj-grid-editor wj-form-control"), e.disableAutoComplete(G), G.value = I, G.required = x.getIsRequired(), e.setAttribute(G, "aria-required", G.required), x.maxLength && (G.maxLength = x.maxLength), G.style.textAlign = x.getAlignment(), x.mask && new e._MaskProvider(G, x.mask), c._edtHdl._edt = G
              } else b == u.Cell && (p.isContentHtml || x.isContentHtml) ? s.innerHTML = I : s.textContent = I || "";
            else {
              var K = s.firstChild;
              K instanceof HTMLInputElement && "checkbox" == K.type || (s.innerHTML = '<input type="checkbox" class="wj-cell-check" tabindex="-1"/>', K = s.firstChild), K.checked = 1 == L, K.indeterminate = null == L, K.disabled = !c.canEditCell(n, r), K.disabled && (K.style.cursor = "default"), c.editRange && c.editRange.contains(n, r) && (c._edtHdl._edt = K)
            } else {
            var W = c.editableCollectionView,
              q = W ? W.currentEditItem : null;
            q && p.dataItem == q ? s.innerHTML = '<span class="wj-glyph-pencil"></span>' : p instanceof t._NewRowTemplate && (s.innerHTML = '<span class="wj-glyph-asterisk"></span>')
          }
          if (b == u.Cell && e.input && x.dataMap && c.showDropDown && 0 != x.showDropDown && c.canEditCell(n, r)) {
            if (!i._ddBtn) {
              var U = i._WJC_DROPDOWN,
                Y = e.createElement('<button class="wj-btn wj-btn-glyph wj-right ' + U + '" type="button" tabindex="-1"><span class="wj-glyph-down"></span></button>');
              e.setAriaLabel(Y, e.culture.FlexGrid.ariaLabels.toggleDropDown), e.setAttribute(Y, "aria-expanded", !1), i._ddBtn = Y
            }
            var J = i._ddBtn.cloneNode(!0);
            s.appendChild(J)
          }
        }
        var X = !1;
        switch (b) {
          case u.RowHeader:
            X = !m && !y && p.allowDragging && 0 != (c.allowDragging & t.AllowDragging.Rows), e.setAttribute(s, "draggable", X ? "true" : null);
            break;
          case u.ColumnHeader:
            X = f.allowDragging && 0 != (c.allowDragging & t.AllowDragging.Columns), e.setAttribute(s, "draggable", X ? "true" : null)
        }
        R += S, s.className != R && (s.className = R);
        var Z = s.style;
        for (var Q in E) Z[Q] !== E[Q] && (Z[Q] = E[Q]);
        if (c._edtHdl._edt && c._edtHdl._edt.parentElement == s) {
          var $ = c._root,
            ee = $.getBoundingClientRect(),
            te = s.getBoundingClientRect(),
            ie = ee.top + $.clientHeight - te.top,
            oe = ee.left + $.clientWidth - te.left;
          te.height > ie && (s.style.height = ie + "px"), te.width > oe && (s.style.width = oe + "px")
        }
        if (c.itemFormatter && c.itemFormatter(o, n, r, s), c.formatItem.hasHandlers) {
          var ne = i._fmtRng;
          ne ? ne.setRange(n, r, _, w) : ne = i._fmtRng = new t.CellRange(n, r, _, w);
          var re = new t.FormatItemEventArgs(o, ne, s);
          c.onFormatItem(re)
        }
      }, i.prototype.disposeCell = function(e) {}, i.prototype.getEditorValue = function(t) {
        var i = t._edtHdl._edt;
        if (i instanceof HTMLInputElement && "checkbox" == i.type) return i.checked;
        if (i instanceof HTMLInputElement || i instanceof HTMLTextAreaElement) {
          var o = i.maxLength;
          return e.isNumber(o) && o > -1 && i.value.length > o ? i.value.substr(0, o) : i.value
        }
        return null
      }, i.prototype._isEditingCell = function(e, t, i) {
        return e.editRange && e.editRange.contains(t, i)
      }, i.prototype._getTreeBtn = function(t) {
        var o = i._WJC_COLLAPSE,
          n = (t.isCollapsed ? "" : "down-") + (t.grid.rightToLeft ? "left" : "right"),
          r = e.createElement('<button class="wj-btn wj-btn-glyph ' + o + '" type="button" tabindex="-1"><span class="wj-glyph-' + n + '"></span></button>');
        return e.setAriaLabel(r, e.culture.FlexGrid.ariaLabels.toggleGroup), e.setAttribute(r, "aria-expanded", !t.isCollapsed), r
      }, i.prototype._getSortIcon = function(e) {
        return '<span class="wj-glyph-' + ("+" == e.currentSort ? "up" : "down") + '"></span>'
      }, i._WJC_COLLAPSE = "wj-elem-collapse", i._WJC_DROPDOWN = "wj-elem-dropdown", i
    }();
    t.CellFactory = i
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i = function() {
      function t(e, t, i, o) {
        void 0 === e && (e = -1), void 0 === t && (t = -1), void 0 === i && (i = e), void 0 === o && (o = t), this.setRange(e, t, i, o)
      }
      return t.prototype.setRange = function(t, i, o, n) {
        void 0 === t && (t = -1), void 0 === i && (i = -1), void 0 === o && (o = t), void 0 === n && (n = i), this._row = e.asInt(t), this._col = e.asInt(i), this._row2 = e.asInt(o), this._col2 = e.asInt(n)
      }, Object.defineProperty(t.prototype, "row", {
        get: function() {
          return this._row
        },
        set: function(t) {
          this._row = e.asInt(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "col", {
        get: function() {
          return this._col
        },
        set: function(t) {
          this._col = e.asInt(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "row2", {
        get: function() {
          return this._row2
        },
        set: function(t) {
          this._row2 = e.asInt(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "col2", {
        get: function() {
          return this._col2
        },
        set: function(t) {
          this._col2 = e.asInt(t)
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.clone = function() {
        return new t(this._row, this._col, this._row2, this._col2)
      }, Object.defineProperty(t.prototype, "rowSpan", {
        get: function() {
          return Math.abs(this._row2 - this._row) + 1
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "columnSpan", {
        get: function() {
          return Math.abs(this._col2 - this._col) + 1
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
      }), Object.defineProperty(t.prototype, "isValid", {
        get: function() {
          return this._row > -1 && this._col > -1 && this._row2 > -1 && this._col2 > -1
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "isSingleCell", {
        get: function() {
          return this._row == this._row2 && this._col == this._col2
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.contains = function(i, o) {
        var n = e.tryCast(i, t);
        if (n) return n.topRow >= this.topRow && n.bottomRow <= this.bottomRow && n.leftCol >= this.leftCol && n.rightCol <= this.rightCol;
        if (e.isInt(i) && e.isInt(o)) return i >= this.topRow && i <= this.bottomRow && o >= this.leftCol && o <= this.rightCol;
        throw "contains expects a CellRange or row/column indices."
      }, t.prototype.containsRow = function(t) {
        return e.asInt(t) >= this.topRow && t <= this.bottomRow
      }, t.prototype.containsColumn = function(t) {
        return e.asInt(t) >= this.leftCol && t <= this.rightCol
      }, t.prototype.intersects = function(e) {
        return this.intersectsRow(e) && this.intersectsColumn(e)
      }, t.prototype.intersectsRow = function(e) {
        return e && !(this.bottomRow < e.topRow || this.topRow > e.bottomRow)
      }, t.prototype.intersectsColumn = function(e) {
        return e && !(this.rightCol < e.leftCol || this.leftCol > e.rightCol)
      }, t.prototype.getRenderSize = function(t) {
        var i = new e.Size(0, 0);
        if (this.isValid) {
          for (var o = this.topRow; o <= this.bottomRow; o++) i.height += t.rows[o].renderSize;
          for (var n = this.leftCol; n <= this.rightCol; n++) i.width += t.columns[n].renderSize
        }
        return i
      }, t.prototype.equals = function(e) {
        return e instanceof t && this._row == e._row && this._col == e._col && this._row2 == e._row2 && this._col2 == e._col2
      }, t
    }();
    t.CellRange = i
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
      function o() {
        this.constructor = t
      }
      e(t, i), t.prototype = null === i ? Object.create(i) : (o.prototype = i.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i;
    ! function(e) {
      e[e.Visible = 1] = "Visible", e[e.AllowResizing = 2] = "AllowResizing", e[e.AllowDragging = 4] = "AllowDragging", e[e.AllowMerging = 8] = "AllowMerging", e[e.AllowSorting = 16] = "AllowSorting", e[e.AutoGenerated = 32] = "AutoGenerated", e[e.Collapsed = 64] = "Collapsed", e[e.ParentCollapsed = 128] = "ParentCollapsed", e[e.Selected = 256] = "Selected", e[e.ReadOnly = 512] = "ReadOnly", e[e.HtmlContent = 1024] = "HtmlContent", e[e.WordWrap = 2048] = "WordWrap", e[e.MultiLine = 4096] = "MultiLine", e[e.HasTemplate = 8192] = "HasTemplate", e[e.RowDefault = 3] = "RowDefault", e[e.ColumnDefault = 23] = "ColumnDefault"
    }(i = t.RowColFlags || (t.RowColFlags = {}));
    var o = function() {
      function o() {
        this._list = null, this._pos = 0, this._idx = -1, this._vidx = -1
      }
      return Object.defineProperty(o.prototype, "visible", {
        get: function() {
          return this._getFlag(i.Visible)
        },
        set: function(e) {
          this._setFlag(i.Visible, e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "isVisible", {
        get: function() {
          return !!this._getFlag(i.Visible) && (!this._getFlag(i.ParentCollapsed) || this instanceof t._NewRowTemplate)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "pos", {
        get: function() {
          return this._list && this._list._dirty && this._list._update(), this._pos
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "index", {
        get: function() {
          return this._list && this._list._dirty && this._list._update(), this._idx
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "visibleIndex", {
        get: function() {
          return this._list && this._list._dirty && this._list._update(), this.isVisible ? this._vidx : -1
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "size", {
        get: function() {
          return this._sz
        },
        set: function(t) {
          t != this._sz && (this._sz = e.asNumber(t, !0), this.onPropertyChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "renderSize", {
        get: function() {
          if (!this.isVisible) return 0;
          var e = this._sz,
            t = this._list;
          return t && ((null == e || e < 0) && (e = Math.round(t.defaultSize)), null != t.minSize && e < t.minSize && (e = t.minSize), null != t.maxSize && e > t.maxSize && (e = t.maxSize)), null != this._szMin && e < this._szMin && (e = this._szMin), null != this._szMax && e > this._szMax && (e = this._szMax), Math.round(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "allowResizing", {
        get: function() {
          return this._getFlag(i.AllowResizing)
        },
        set: function(e) {
          this._setFlag(i.AllowResizing, e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "allowDragging", {
        get: function() {
          return this._getFlag(i.AllowDragging)
        },
        set: function(e) {
          this._setFlag(i.AllowDragging, e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "allowMerging", {
        get: function() {
          return this._getFlag(i.AllowMerging)
        },
        set: function(e) {
          this._setFlag(i.AllowMerging, e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "isSelected", {
        get: function() {
          return this._getFlag(i.Selected)
        },
        set: function(e) {
          if (this._setFlag(i.Selected, e, !0)) {
            var t = this.grid;
            t && t.refreshCells(!1, !0, !0)
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "isReadOnly", {
        get: function() {
          return this._getFlag(i.ReadOnly)
        },
        set: function(e) {
          this._setFlag(i.ReadOnly, e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "isContentHtml", {
        get: function() {
          return this._getFlag(i.HtmlContent)
        },
        set: function(e) {
          this.isContentHtml != e && (this._setFlag(i.HtmlContent, e), this.grid && this.grid.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "wordWrap", {
        get: function() {
          return this._getFlag(i.WordWrap)
        },
        set: function(e) {
          this._setFlag(i.WordWrap, e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "multiLine", {
        get: function() {
          return this._getFlag(i.MultiLine)
        },
        set: function(e) {
          this._setFlag(i.MultiLine, e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "cssClass", {
        get: function() {
          return this._cssClass
        },
        set: function(t) {
          t != this._cssClass && (this._cssClass = e.asString(t), this.grid && this.grid.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "grid", {
        get: function() {
          return this._list ? this._list._g : null
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "collectionView", {
        get: function() {
          return this.grid ? this.grid.collectionView : null
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.onPropertyChanged = function() {
        this._list && (this._list._dirty = !0, this.grid.invalidate())
      }, o.prototype._getFlag = function(e) {
        return 0 != (this._f & e)
      }, o.prototype._setFlag = function(e, t, i) {
        return t != this._getFlag(e) && (this._f = t ? this._f | e : this._f & ~e, i || this.onPropertyChanged(), !0)
      }, o
    }();
    t.RowCol = o;
    var n = function(o) {
      function n(t) {
        var r = o.call(this) || this;
        return r._f = i.ColumnDefault, r._hash = n._ctr.toString(36), n._ctr++, t && e.copy(r, t), r
      }
      return __extends(n, o), Object.defineProperty(n.prototype, "name", {
        get: function() {
          return this._name
        },
        set: function(e) {
          this._name = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "dataType", {
        get: function() {
          return this._type
        },
        set: function(t) {
          t = e.asEnum(t, e.DataType), this._type != t && (this._type = t, this.grid && this.grid.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isRequired", {
        get: function() {
          return this._required
        },
        set: function(t) {
          this._required = e.asBoolean(t, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showDropDown", {
        get: function() {
          return this._showDropDown
        },
        set: function(t) {
          t != this._showDropDown && (this._showDropDown = e.asBoolean(t, !0), this.grid && this.grid.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "dropDownCssClass", {
        get: function() {
          return this._ddCssClass
        },
        set: function(t) {
          this._ddCssClass = e.asString(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "inputType", {
        get: function() {
          return this._inpType
        },
        set: function(t) {
          this._inpType = e.asString(t, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "mask", {
        get: function() {
          return this._mask
        },
        set: function(t) {
          this._mask = e.asString(t, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "maxLength", {
        get: function() {
          return this._maxLen
        },
        set: function(t) {
          this._maxLen = e.asNumber(t, !0, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "binding", {
        get: function() {
          return this._binding ? this._binding.path : null
        },
        set: function(t) {
          if (t != this.binding) {
            var i = e.asString(t);
            if (this._binding = i ? new e.Binding(i) : null, !this._type && this.grid && this._binding) {
              var o = this.grid.collectionView;
              if (o && o.sourceCollection && o.sourceCollection.length) {
                var n = o.sourceCollection[0];
                this._type = e.getType(this._binding.getValue(n))
              }
            }
            this.onPropertyChanged()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "sortMemberPath", {
        get: function() {
          return this._bindingSort ? this._bindingSort.path : null
        },
        set: function(t) {
          if (t != this.sortMemberPath) {
            var i = e.asString(t);
            this._bindingSort = i ? new e.Binding(i) : null, this.onPropertyChanged()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "width", {
        get: function() {
          return null != this._szStar ? this._szStar : this.size
        },
        set: function(t) {
          null != n._parseStarSize(t) ? (this._szStar = t, this.onPropertyChanged()) : (this._szStar = null, this.size = e.asNumber(t, !0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "minWidth", {
        get: function() {
          return this._szMin
        },
        set: function(t) {
          t != this._szMin && (this._szMin = e.asNumber(t, !0, !0), this.onPropertyChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "maxWidth", {
        get: function() {
          return this._szMax
        },
        set: function(t) {
          t != this._szMax && (this._szMax = e.asNumber(t, !0, !0), this.onPropertyChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "quickAutoSize", {
        get: function() {
          return this._quickSize
        },
        set: function(t) {
          this._quickSize = e.asBoolean(t, !0)
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._getQuickAutoSize = function() {
        return !!this.grid._getQuickAutoSize() && (e.isBoolean(this._quickSize) ? this._quickSize : !(this.isContentHtml || this.wordWrap || this.multiLine || this._getFlag(i.HasTemplate)))
      }, Object.defineProperty(n.prototype, "renderWidth", {
        get: function() {
          return this.renderSize
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "align", {
        get: function() {
          return this._align
        },
        set: function(e) {
          this._align != e && (this._align = e, this.onPropertyChanged())
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.getAlignment = function() {
        var t = this._align;
        if (null == t && (t = "", !this._map)) switch (this._type) {
          case e.DataType.Boolean:
            t = "center";
            break;
          case e.DataType.Number:
            t = "right"
        }
        return t
      }, n.prototype.getIsRequired = function() {
        return null != this._required ? this._required : this.dataType != e.DataType.String || (null != this.dataMap || null != this._mask && this._mask.length > 0)
      }, Object.defineProperty(n.prototype, "header", {
        get: function() {
          return this._hdr ? this._hdr : this.binding
        },
        set: function(e) {
          this._hdr != e && (this._hdr = e, this.onPropertyChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "dataMap", {
        get: function() {
          return this._map
        },
        set: function(i) {
          this._map != i && (this._map && this._map.mapChanged.removeHandler(this.onPropertyChanged, this), e.isArray(i) && (i = new t.DataMap(i, null, null)), this._map = e.asType(i, t.DataMap, !0), this._map && this._map.mapChanged.addHandler(this.onPropertyChanged, this), this.onPropertyChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "format", {
        get: function() {
          return this._fmt
        },
        set: function(e) {
          this._fmt != e && (this._fmt = e, this.onPropertyChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "allowSorting", {
        get: function() {
          return this._getFlag(i.AllowSorting)
        },
        set: function(e) {
          this._setFlag(i.AllowSorting, e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "currentSort", {
        get: function() {
          var e = this.grid ? this.grid.collectionView : null,
            t = e ? e.sortDescriptions : null,
            i = t && t.length ? this._getBindingSort() : null;
          if (i)
            for (var o = 0; o < t.length; o++)
              if (t[o].property == i) return t[o].ascending ? "+" : "-";
          return null
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "aggregate", {
        get: function() {
          return null != this._agg ? this._agg : e.Aggregate.None
        },
        set: function(t) {
          (t = e.asEnum(t, e.Aggregate)) != this._agg && (this._agg = t, this.onPropertyChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "describedById", {
        get: function() {
          return this._descById
        },
        set: function(t) {
          t != this._descById && (this._descById = e.asString(t), this.grid && this.grid.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._getBindingSort = function() {
        return this.sortMemberPath ? this.sortMemberPath : this.binding ? this.binding : null
      }, n._parseStarSize = function(t) {
        if (e.isString(t) && t.length > 0 && "*" == t[t.length - 1]) {
          var i = 1 == t.length ? 1 : 1 * t.substr(0, t.length - 1);
          if (i > 0 && !isNaN(i)) return i
        }
        return null
      }, n._ctr = 0, n
    }(o);
    t.Column = n;
    var r = function(e) {
      function t(t) {
        var o = e.call(this) || this;
        return o._f = i.ColumnDefault, o._data = t, o
      }
      return __extends(t, e), Object.defineProperty(t.prototype, "dataItem", {
        get: function() {
          return this._data
        },
        set: function(e) {
          this._data = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "height", {
        get: function() {
          return this.size
        },
        set: function(e) {
          this.size = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "renderHeight", {
        get: function() {
          return this.renderSize
        },
        enumerable: !0,
        configurable: !0
      }), t
    }(o);
    t.Row = r;
    var s = function(o) {
      function n() {
        var e = o.call(this) || this;
        return e._level = -1, e.isReadOnly = !0, e
      }
      return __extends(n, o), Object.defineProperty(n.prototype, "level", {
        get: function() {
          return this._level
        },
        set: function(t) {
          e.asInt(t), t != this._level && (this._level = t, this.onPropertyChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "hasChildren", {
        get: function() {
          if (null != this.grid && null != this._list) {
            this._list._update();
            var i = this.index < this._list.length - 1 ? this._list[this.index + 1] : null,
              o = e.tryCast(i, n),
              r = e.tryCast(i, t._NewRowTemplate);
            return null != i && null == r && (null == o || o.level > this.level)
          }
          return !0
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isCollapsed", {
        get: function() {
          return this._getFlag(i.Collapsed)
        },
        set: function(t) {
          e.asBoolean(t), t != this.isCollapsed && null != this._list && this._setCollapsed(t)
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.getGroupHeader = function() {
        var t = this.grid,
          i = t.groupHeaderFormat || e.culture.FlexGrid.groupHeaderFormat,
          o = e.tryCast(this.dataItem, e.collections.CollectionViewGroup);
        if (o && i) {
          var n = o.groupDescription.propertyName,
            r = o.name,
            s = t.getColumn(n),
            l = this.isContentHtml;
          s && (l = l || s.isContentHtml, s.header && (n = s.header), s.dataMap ? r = s.dataMap.getDisplayValue(r) : s.format && (r = e.Globalize.format(r, s.format)));
          var a = o.getAggregate(e.Aggregate.CntAll, null, t.collectionView);
          return e.format(i, {
            name: e.escapeHtml(n),
            value: l ? r : e.escapeHtml(r),
            level: o.level,
            count: a
          })
        }
        return ""
      }, n.prototype._setCollapsed = function(e) {
        var o = this,
          r = this.grid,
          s = r.rows,
          l = this.getCellRange(),
          a = new t.CellRangeEventArgs(r.cells, new t.CellRange(this.index, -1));
        r.onGroupCollapsedChanging(a) && (r.deferUpdate(function() {
          s.deferUpdate(function() {
            o._setFlag(i.Collapsed, e, !0);
            for (var t = l.topRow + 1; t <= l.bottomRow && t > -1 && t < s.length; t++) {
              s[t]._setFlag(i.ParentCollapsed, e, !0);
              var r = s[t];
              r instanceof n && r.isCollapsed && (t = r.getCellRange().bottomRow)
            }
          })
        }), r.onGroupCollapsedChanged(a))
      }, n.prototype.getCellRange = function() {
        for (var i = this._list, o = this.index, r = i.length - 1, s = o + 1; s <= r; s++) {
          var l = e.tryCast(i[s], n);
          if (null != l && l.level <= this.level) {
            r = s - 1;
            break
          }
        }
        return new t.CellRange(o, 0, r, this.grid.columns.length - 1)
      }, n
    }(r);
    t.GroupRow = s;
    var l = function(i) {
      function o(o, n) {
        var r = i.call(this) || this;
        return r._frozen = 0, r._vlen = 0, r._szDef = 28, r._szTot = 0, r._szCustom = !1, r._dirty = !1, r._g = e.asType(o, t.FlexGrid), r._szDef = e.asNumber(n, !1, !0), r
      }
      return __extends(o, i), Object.defineProperty(o.prototype, "grid", {
        get: function() {
          return this._g
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "defaultSize", {
        get: function() {
          return this._szDef
        },
        set: function(t) {
          this._szCustom = !0, this._szDef != t && (this._szDef = e.asNumber(t, !1, !0), this._dirty = !0, this._g.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "frozen", {
        get: function() {
          return this._frozen
        },
        set: function(t) {
          t != this._frozen && (this._frozen = e.asNumber(t, !1, !0), this._dirty = !0, this._g.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.isFrozen = function(e) {
        return e < this.frozen
      }, Object.defineProperty(o.prototype, "minSize", {
        get: function() {
          return this._szMin
        },
        set: function(t) {
          t != this._szMin && (this._szMin = e.asNumber(t, !0, !0), this._dirty = !0, this._g.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "maxSize", {
        get: function() {
          return this._szMax
        },
        set: function(t) {
          t != this._szMax && (this._szMax = e.asNumber(t, !0, !0), this._dirty = !0, this._g.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.getTotalSize = function() {
        return this._dirty && this._update(), this._szTot
      }, Object.defineProperty(o.prototype, "visibleLength", {
        get: function() {
          return this._dirty && this._update(), this._vlen
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.getItemAt = function(e) {
        if (this._dirty && this._update(), e <= 0 && this.length > 0 && this[0].renderSize) return 0;
        e = Math.round(e);
        for (var t, i, o = this.length, n = 0, r = o - 1; n <= r;)
          if (t = n + r >>> 1, (i = this[t])._pos > e && t > 0) r = t - 1;
          else {
            if (!(i._pos + i.renderSize <= e && t < o - 1)) {
              for (; t > 0 && !this[t].renderSize;) t--;
              for (; t < o - 1 && !this[t].renderSize;) t++;
              return t
            }
            n = t + 1
          }
        return r
      }, o.prototype.getNextCell = function(e, i, o) {
        var n, r = t.SelMove;
        switch (i) {
          case r.Next:
            for (n = e + 1; n < this.length; n++)
              if (this[n].renderSize > 0) return n;
            break;
          case r.Prev:
            for (n = e - 1; n >= 0; n--)
              if (this[n].renderSize > 0) return n;
            break;
          case r.End:
            for (n = this.length - 1; n >= 0; n--)
              if (this[n].renderSize > 0) return n;
            break;
          case r.Home:
            for (n = 0; n < this.length; n++)
              if (this[n].renderSize > 0) return n;
            break;
          case r.NextPage:
            return (n = this.getItemAt(this[e].pos + o)) < 0 ? this.getNextCell(e, t.SelMove.End, o) : n == e && n < this.length - 1 && this[n + 1].renderSize ? n + 1 : n;
          case r.PrevPage:
            return (n = this.getItemAt(this[e].pos - o)) < 0 ? this.getNextCell(e, t.SelMove.Home, o) : n == e && n > 0 && this[n - 1].renderSize ? n - 1 : n
        }
        return e
      }, o.prototype.canMoveElement = function(e, i) {
        if (i == e) return !1;
        if (e < 0 || e >= this.length || i >= this.length) return !1;
        i < 0 && (i = this.length - 1);
        for (var o = Math.min(e, i), n = Math.max(e, i), r = o; r <= n; r++)
          if (!this[r].allowDragging) return !1;
        return !(this[i] instanceof t._NewRowTemplate)
      }, o.prototype.moveElement = function(e, t) {
        if (this.canMoveElement(e, t)) {
          var i = this[e];
          this.removeAt(e), t < 0 && (t = this.length), this.insert(t, i)
        }
      }, o.prototype.onCollectionChanged = function(t) {
        void 0 === t && (t = e.collections.NotifyCollectionChangedEventArgs.reset), this._dirty = !0, this._g.invalidate(), i.prototype.onCollectionChanged.call(this, t)
      }, o.prototype.push = function(e) {
        return e._list = this, i.prototype.push.call(this, e)
      }, o.prototype.splice = function(e, t, o) {
        return o && (o._list = this), i.prototype.splice.call(this, e, t, o)
      }, o.prototype.beginUpdate = function() {
        this._update(), i.prototype.beginUpdate.call(this)
      }, o.prototype._setDefaultSize = function(e) {
        this._szCustom || (this.defaultSize = e, this._szCustom = !1)
      }, o.prototype._update = function() {
        if (this._dirty && !this.isUpdating) {
          this._dirty = !1;
          for (var e = 0, t = 0, i = void 0, o = void 0, n = 0; n < this.length; n++)(o = this[n])._idx = n, o._vidx = e, o._list = this, o._pos = t, (i = o.renderSize) > 0 && (t += i, e++);
          return this._vlen = e, this._szTot = t, !0
        }
        return !1
      }, o
    }(e.collections.ObservableArray);
    t.RowColCollection = l;
    var a = function(t) {
      function i() {
        var e = null !== t && t.apply(this, arguments) || this;
        return e._firstVisible = -1, e
      }
      return __extends(i, t), i.prototype.getColumn = function(t) {
        var i = e.isNumber(t) ? t : this.indexOf(t);
        return i > -1 ? this[i] : null
      }, i.prototype.indexOf = function(e) {
        if (e instanceof n) return t.prototype.indexOf.call(this, e);
        for (i = 0; i < this.length; i++)
          if (this[i].name == e) return i;
        for (var i = 0; i < this.length; i++)
          if (this[i].binding == e) return i;
        return -1
      }, Object.defineProperty(i.prototype, "describedById", {
        get: function() {
          return this._descById
        },
        set: function(t) {
          t != this._descById && (this._descById = e.asString(t), this._g && this._g.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "firstVisibleIndex", {
        get: function() {
          return this._dirty && this._update(), this._firstVisible
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype._update = function() {
        if (t.prototype._update.call(this)) {
          this._firstVisible = -1;
          for (var e = 0; e < this.length; e++)
            if (this[e].visible) {
              this._firstVisible = e;
              break
            }
          return !0
        }
        return !1
      }, i.prototype._updateStarSizes = function(e) {
        for (var t, i = 0, o = 0; o < this.length; o++)(s = this[o]).isVisible && (s._szStar ? (i += n._parseStarSize(s._szStar), t = s) : e -= s.renderWidth);
        if (t) {
          for (var r = e, o = 0; o < this.length; o++) {
            var s = this[o];
            s.isVisible && s._szStar && (s == t && r > 0 ? s._sz = r : (s._sz = Math.max(0, Math.round(n._parseStarSize(s._szStar) / i * e)), r -= s.renderWidth))
          }
          return this._dirty = !0, this._update(), !0
        }
        return !1
      }, i
    }(l);
    t.ColumnCollection = a;
    var c = function(t) {
      function i() {
        var e = null !== t && t.apply(this, arguments) || this;
        return e._maxLevel = -1, e
      }
      return __extends(i, t), Object.defineProperty(i.prototype, "ariaLabel", {
        get: function() {
          return this._ariaLabel
        },
        set: function(t) {
          t != this.ariaLabel && (this._ariaLabel = e.asString(t), this._g && this._g.refresh())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "maxGroupLevel", {
        get: function() {
          return this._dirty && this._update(), this._maxLevel
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype._update = function() {
        if (t.prototype._update.call(this)) {
          this._maxLevel = -1;
          for (var i = 0; i < this.length; i++) {
            var o = e.tryCast(this[i], s);
            o && o.level > this._maxLevel && (this._maxLevel = o.level)
          }
          return !0
        }
        return !1
      }, i
    }(l);
    t.RowCollection = c
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  var t;
  ! function(i) {
    "use strict";
    var o = function() {
      function o(t, n) {
        this._row = -1, this._col = -1, this._edge = 0;
        var r;
        if (n instanceof Element) {
          if (e.closest(n, ".wj-flexgrid") != t.hostElement) return;
          t = n
        }
        if (!(t instanceof Element) || t instanceof i.FlexGrid) {
          if (t instanceof i.FlexGrid) r = this._g = t;
          else {
            if (!(t instanceof i.GridPanel)) throw "First parameter should be a FlexGrid or GridPanel.";
            this._p = t, r = this._g = this._p.grid
          }
          var s;
          n instanceof MouseEvent && (s = n, "mousedown" == n.type && (r._rcBounds = null)), n = e.mouseToPage(n), this._pt = n.clone();
          var l = r.controlRect,
            a = r._szClient,
            c = r.topLeftCells,
            h = r._eTL,
            u = r.headersVisibility,
            d = i.HeadersVisibility,
            g = u & d.Row ? c.columns.getTotalSize() : 0,
            p = u & d.Column ? c.rows.getTotalSize() : 0,
            f = u & d.Column ? p + h.offsetTop : 0,
            _ = r._eBL,
            w = _.offsetHeight;
          if (n.x -= l.left, n.y -= l.top, this._g.rightToLeft && (n.x = l.width - n.x), !this._p && n.x >= 0 && n.y >= h.offsetTop && a && n.x <= a.width + g && n.y <= a.height + f && (n.y < f ? this._p = n.x < g ? r.topLeftCells : r.columnHeaders : n.y < _.offsetTop ? this._p = n.x < g ? r.rowHeaders : r.cells : this._p = n.x < g ? r.bottomLeftCells : r.columnFooters), null != this._p) {
            var m = this._p.rows,
              y = this._p.columns,
              v = this._p.cellType,
              C = i.CellType,
              b = this._p._getFrozenPos(),
              R = v == C.TopLeft || v == C.ColumnHeader ? p : v == C.BottomLeft || v == C.ColumnFooter ? w : m.getTotalSize(),
              S = v == C.TopLeft || v == C.BottomLeft || v == C.RowHeader ? g : y.getTotalSize();
            v == C.RowHeader || v == C.Cell ? (n.y -= p, (n.y > b.y || b.y <= 0) && (n.y -= r._ptScrl.y, n.y += this._p._getOffsetY())) : v != C.BottomLeft && v != C.ColumnFooter || (n.y -= _.offsetTop), v != C.ColumnHeader && v != C.Cell && v != C.ColumnFooter || (n.x -= g, (n.x > b.x || b.x <= 0) && (n.x -= r._ptScrl.x)), v != C.ColumnHeader && v != C.TopLeft || (n.y -= f - p), this._edge = 0;
            var E = o._SZEDGE[this._g.isTouching ? 1 : 0];
            if (this._g.isTouching && (E = o._SZEDGE[1], n.x -= E / 2), this._row = n.y > R ? -1 : m.getItemAt(n.y), this._col = n.x > S ? -1 : y.getItemAt(n.x), this._row < 0 || this._col < 0) return void(this._p = null);
            if (this._col > -1) {
              var z = y[this._col];
              n.x - z.pos <= E && (this._edge |= 1), z.pos + z.renderSize - n.x <= E && (this._edge |= 4)
            }
            if (this._row > -1) {
              var x = m[this._row];
              n.y - x.pos <= E && (this._edge |= 2), x.pos + x.renderSize - n.y <= E && (this._edge |= 8)
            }
          }
          if (!(8 & this._edge) && s instanceof MouseEvent) {
            var T = e.closest(s.target, ".wj-cell"),
              H = T ? T[i.GridPanel._INDEX_KEY] : null;
            H && !H.rng && H.panel == this._p && this._row != H.row && (this._row = H.row)
          }
        } else(H = (T = e.closest(t, ".wj-cell")) ? T[i.GridPanel._INDEX_KEY] : null) && (this._row = H.row, this._col = H.col, this._rng = H.rng, this._p = H.panel, this._g = H.panel.grid)
      }
      return Object.defineProperty(o.prototype, "point", {
        get: function() {
          return this._pt
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "cellType", {
        get: function() {
          return this._p ? this._p.cellType : t.CellType.None
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "panel", {
        get: function() {
          return this._p
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "grid", {
        get: function() {
          return this._p ? this._p.grid : null
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "row", {
        get: function() {
          return this._row
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "col", {
        get: function() {
          return this._col
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "range", {
        get: function() {
          return this._rng || (this._rng = new i.CellRange(this._row, this._col)), this._rng
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "edgeLeft", {
        get: function() {
          return 0 != (1 & this._edge)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "edgeTop", {
        get: function() {
          return 0 != (2 & this._edge)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "edgeRight", {
        get: function() {
          return 0 != (4 & this._edge)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "edgeBottom", {
        get: function() {
          return 0 != (8 & this._edge)
        },
        enumerable: !0,
        configurable: !0
      }), o._SZEDGE = [5, 30], o
    }();
    i.HitTestInfo = o
  }(t = e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i;
    ! function(e) {
      e[e.None = 0] = "None", e[e.Cells = 1] = "Cells", e[e.ColumnHeaders = 2] = "ColumnHeaders", e[e.RowHeaders = 4] = "RowHeaders", e[e.AllHeaders = 6] = "AllHeaders", e[e.All = 7] = "All"
    }(i = t.AllowMerging || (t.AllowMerging = {}));
    var o = function() {
      function o(e) {
        this._g = e
      }
      return o.prototype.getMergedRange = function(o, n, r, s) {
        void 0 === s && (s = !0);
        var l = this._g,
          a = o.cellType,
          c = o.columns,
          h = o.rows,
          u = h[n],
          d = c[r];
        if (u instanceof t._NewRowTemplate) return null;
        if (u instanceof t.GroupRow && u.dataItem instanceof e.collections.CollectionViewGroup) {
          p = new t.CellRange(n, r);
          if (d.aggregate == e.Aggregate.None) {
            for (; p.col > 0 && c[p.col - 1].aggregate == e.Aggregate.None && p.col != c.frozen;) p.col--;
            for (; p.col2 < c.length - 1 && c[p.col2 + 1].aggregate == e.Aggregate.None && p.col2 + 1 != c.frozen;) p.col2++
          }
          for (; p.col < r && !c[p.col].visible;) p.col++;
          return p.isSingleCell ? null : p
        }
        var g = !1;
        switch (this._g.allowMerging) {
          case i.None:
            g = !0;
            break;
          case i.Cells:
            g = a != t.CellType.Cell;
            break;
          case i.ColumnHeaders:
            g = a != t.CellType.ColumnHeader && a != t.CellType.TopLeft;
            break;
          case i.RowHeaders:
            g = a != t.CellType.RowHeader && a != t.CellType.TopLeft;
            break;
          case i.AllHeaders:
            g = a == t.CellType.Cell
        }
        if (g) return null;
        if (c[r].allowMerging) {
          var p = new t.CellRange(n, r),
            f = 0,
            _ = h.length - 1;
          n >= h.frozen ? !s || a != t.CellType.Cell && a != t.CellType.RowHeader || l._vtRows < h.length && (f = (C = o._getViewRange()).topRow, _ = C.bottomRow) : _ = h.frozen - 1;
          for (var w = n - 1; w >= f && this._mergeCell(o, w, r, n, r); w--) p.row = w;
          for (var m = n + 1; m <= _ && this._mergeCell(o, n, r, m, r); m++) p.row2 = m;
          for (; p.row < n && !h[p.row].visible;) p.row++;
          if (!p.isSingleCell) return p
        }
        if (h[n].allowMerging) {
          var p = new t.CellRange(n, r),
            y = 0,
            v = c.length - 1;
          if (r >= c.frozen) {
            if (s && (a == t.CellType.Cell || a == t.CellType.ColumnHeader) && l._vtCols < c.length) {
              var C = o._getViewRange();
              y = C.leftCol, v = C.rightCol
            }
          } else v = c.frozen - 1;
          for (var b = r - 1; b >= y && this._mergeCell(o, n, b, n, r); b--) p.col = b;
          for (var R = r + 1; R <= v && this._mergeCell(o, n, r, n, R); R++) p.col2 = R;
          for (; p.col < r && !c[p.col].visible;) p.col++;
          if (!p.isSingleCell) return p
        }
        return null
      }, o.prototype._mergeCell = function(e, i, o, n, r) {
        var s = e.rows[i],
          l = e.rows[n];
        if (s instanceof t.GroupRow || s instanceof t._NewRowTemplate || l instanceof t.GroupRow || l instanceof t._NewRowTemplate) return !1;
        if (i != n && e.rows.isFrozen(i) != e.rows.isFrozen(n)) return !1;
        if (o != r && e.columns.isFrozen(o) != e.columns.isFrozen(r)) return !1;
        if (i != n) {
          if (o > 0 && (s.allowMerging && this._mergeCell(e, i, o - 1, i, o) || l.allowMerging && this._mergeCell(e, n, o - 1, n, o))) return !1;
          if (r < e.columns.length - 1 && (s.allowMerging && this._mergeCell(e, i, r, i, r + 1) || l.allowMerging && this._mergeCell(e, n, r, n, r + 1))) return !1
        }
        return e.getCellData(i, o, !0) == e.getCellData(n, r, !0)
      }, o
    }();
    t.MergeManager = o
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i = function() {
      function t(t, i, o) {
        if (this.mapChanged = new e.Event, e.isArray(t) && !i && !o) {
          for (var n = [], r = 0; r < t.length; r++) n.push({
            value: t[r]
          });
          t = n, i = o = "value"
        }
        this._cv = e.asCollectionView(t), this._keyPath = e.asString(i, !1), this._displayPath = e.asString(o, !1), this._cv.collectionChanged.addHandler(this.onMapChanged, this)
      }
      return Object.defineProperty(t.prototype, "sortByDisplayValues", {
        get: function() {
          return 1 != this._sortByKey
        },
        set: function(t) {
          this._sortByKey = !e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "collectionView", {
        get: function() {
          return this._cv
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "selectedValuePath", {
        get: function() {
          return this._keyPath
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "displayMemberPath", {
        get: function() {
          return this._displayPath
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.getKeyValue = function(e) {
        var t = this._displayPath,
          i = this._indexOf(e, t, !0);
        return i < 0 && (i = this._indexOf(e, t, !1)), i > -1 ? this._cv.sourceCollection[i][this._keyPath] : null
      }, t.prototype.getDisplayValue = function(e) {
        if (!this._hash) {
          this._hash = {};
          var t = this._cv.sourceCollection;
          if (t && this._keyPath && this._displayPath)
            for (var i = t.length - 1; i >= 0; i--) {
              var o = t[i],
                n = o[this._keyPath],
                r = o[this._displayPath];
              this._hash[n] = r
            }
        }
        var s = this._hash[e];
        return null != s ? s : e
      }, t.prototype.getDisplayValues = function(e) {
        var t = [];
        if (this._cv && this._displayPath)
          for (var i = this._cv.items, o = 0; o < i.length; o++) t.push(i[o][this._displayPath]);
        return t
      }, t.prototype.getKeyValues = function() {
        var e = [];
        if (this._cv && this._keyPath)
          for (var t = this._cv.items, i = 0; i < t.length; i++) e.push(t[i][this._keyPath]);
        return e
      }, Object.defineProperty(t.prototype, "isEditable", {
        get: function() {
          return this._editable
        },
        set: function(t) {
          this._editable = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.onMapChanged = function(e) {
        this._hash = null, this.mapChanged.raise(this, e)
      }, t.prototype._indexOf = function(e, t, i) {
        var o = -1,
          n = -1;
        if (this._cv && t)
          for (var r = null != e ? e.toString() : "", s = i ? r : r.toLowerCase(), l = this._cv.sourceCollection, a = 0; a < l.length; a++) {
            var c = l[a],
              h = c[t];
            if (h == e ? o = a : i || h.length != s.length || h.toLowerCase() != s ? null != h && h.toString() == r && (o = a) : o = a, o == a) {
              if (!this._cv.filter || this._cv.filter(c)) return o;
              n < 0 && (n = o)
            }
          }
        return n
      }, t
    }();
    t.DataMap = i
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i;
    ! function(e) {
      e[e.None = 0] = "None", e[e.Cell = 1] = "Cell", e[e.CellRange = 2] = "CellRange", e[e.Row = 3] = "Row", e[e.RowRange = 4] = "RowRange", e[e.ListBox = 5] = "ListBox"
    }(i = t.SelectionMode || (t.SelectionMode = {}));
    ! function(e) {
      e[e.None = 0] = "None", e[e.Selected = 1] = "Selected", e[e.Cursor = 2] = "Cursor", e[e.Active = 3] = "Active"
    }(t.SelectedState || (t.SelectedState = {}));
    var o;
    ! function(e) {
      e[e.None = 0] = "None", e[e.Next = 1] = "Next", e[e.Prev = 2] = "Prev", e[e.NextPage = 3] = "NextPage", e[e.PrevPage = 4] = "PrevPage", e[e.Home = 5] = "Home", e[e.End = 6] = "End", e[e.NextCell = 7] = "NextCell", e[e.PrevCell = 8] = "PrevCell"
    }(o = t.SelMove || (t.SelMove = {}));
    var n = function() {
      function n(e) {
        this._sel = new t.CellRange(0, 0), this._mode = i.CellRange, this._g = e
      }
      return Object.defineProperty(n.prototype, "selectionMode", {
        get: function() {
          return this._mode
        },
        set: function(e) {
          if (e != this._mode) {
            var o = i;
            if (e == o.ListBox || this._mode == o.ListBox)
              for (var n = this._g.rows, r = 0; r < n.length; r++) {
                var s = n[r],
                  l = e == i.ListBox && this._sel.containsRow(r);
                s._setFlag(t.RowColFlags.Selected, l, !0)
              }
            switch (e) {
              case o.None:
                this._sel.setRange(-1, -1);
                break;
              case o.Cell:
                this._sel.row2 = this._sel.row, this._sel.col2 = this._sel.col;
                break;
              case o.Row:
                this._sel.row2 = this._sel.row
            }
            this._mode = e, this._g.invalidate()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "selection", {
        get: function() {
          return this._sel
        },
        set: function(e) {
          this.select(e)
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.select = function(o, n) {
        void 0 === n && (n = !0), e.isNumber(o) && e.isNumber(n) && (o = new t.CellRange(o, n), n = !0), o = e.asType(o, t.CellRange);
        var r = this._g,
          s = this._sel,
          l = o,
          a = !1,
          c = i;
        switch (r.selectionMode) {
          case c.Cell:
            o.row2 = o.row, o.col2 = o.col;
            break;
          case c.Row:
            o.row2 = o.row;
            break;
          case c.ListBox:
            a = !0
        }
        var h = l.equals(s);
        if (a && l.row > -1 && !r.rows[l.row].isSelected && (h = !1), h) n && r.scrollIntoView(l.row, l.col);
        else {
          var u = new t.CellRangeEventArgs(r.cells, l);
          if (r.onSelectionChanging(u)) {
            if (a && (l.row != s.row || l.rowSpan > 1 || s.rowSpan > 1)) {
              for (var d = 0; d < r.rows.length; d++) r.rows[d]._setFlag(t.RowColFlags.Selected, l.containsRow(d), !0);
              r.refreshCells(!1, !0, !0)
            }
            if (l.row = Math.min(l.row, r.rows.length - 1), l.row2 = Math.min(l.row2, r.rows.length - 1), this._sel = l, r.refreshCells(!1, !0, !0), n && r.scrollIntoView(l.row, l.col), r.collectionView) {
              var g = r._getCvIndex(l.row);
              r.collectionView.moveCurrentToPosition(g)
            }
            r.onSelectionChanged(u)
          }
        }
      }, n.prototype.moveSelection = function(e, i, n) {
        var r, s, l = this._g,
          a = l.rows,
          c = l.columns,
          h = this._getReferenceCell(e, i, n),
          u = Math.max(0, l._szClient.height - l.columnHeaders.height);
        if (i == o.NextCell) s = c.getNextCell(h.col, o.Next, u), r = h.row, s == h.col && (r = a.getNextCell(r, o.Next, u)) > h.row && (s = c.getNextCell(0, o.Next, u), s = c.getNextCell(s, o.Prev, u)), l.select(r, s);
        else if (i == o.PrevCell) s = c.getNextCell(h.col, o.Prev, u), r = h.row, s == h.col && (r = a.getNextCell(r, o.Prev, u)) < h.row && (s = c.getNextCell(c.length - 1, o.Prev, u), s = c.getNextCell(s, o.Next, u)), l.select(r, s);
        else if (r = a.getNextCell(h.row, e, u), s = c.getNextCell(h.col, i, u), n) {
          var d = l._selHdl._sel;
          l.select(new t.CellRange(r, s, d.row2, d.col2))
        } else l.select(r, s)
      }, n.prototype._getReferenceCell = function(e, t, i) {
        var n = this._g,
          r = n._selHdl._sel,
          s = n.getMergedRange(n.cells, r.row, r.col);
        if (!s || s.isSingleCell) return r;
        switch (s = s.clone(), e) {
          case o.Next:
          case o.NextCell:
            s.row = s.bottomRow;
            break;
          case o.None:
            s.row = r.row
        }
        switch (t) {
          case o.Next:
          case o.NextCell:
            s.col = s.rightCol;
            break;
          case o.None:
            s.col = r.col
        }
        return s
      }, n.prototype._adjustSelection = function(e) {
        switch (this._mode) {
          case i.Cell:
            return new t.CellRange(e.row, e.col, e.row, e.col);
          case i.Row:
            return new t.CellRange(e.row, 0, e.row, this._g.columns.length - 1);
          case i.RowRange:
          case i.ListBox:
            return new t.CellRange(e.row, 0, e.row2, this._g.columns.length - 1)
        }
        return e
      }, n
    }();
    t._SelectionHandler = n
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i;
    ! function(e) {
      e[e.None = 0] = "None", e[e.MoveDown = 1] = "MoveDown", e[e.MoveAcross = 2] = "MoveAcross", e[e.Cycle = 3] = "Cycle", e[e.CycleOut = 4] = "CycleOut"
    }(i = t.KeyAction || (t.KeyAction = {}));
    var o = function() {
      function o(e) {
        this._kaTab = i.None, this._kaEnter = i.MoveDown, this._g = e;
        var t = e.hostElement;
        e.addEventListener(t, "keypress", this._keypress.bind(this)), e.addEventListener(t, "keydown", this._keydown.bind(this))
      }
      return o.prototype._keydown = function(i) {
        var o = this._g,
          n = o._edtHdl,
          r = o.selection,
          s = i.ctrlKey || i.metaKey,
          l = i.shiftKey,
          a = (i.target, !0);
        if (this._altDown = !1, !o._wantsInput(i.target)) {
          var c = i.defaultPrevented && !(i.target instanceof HTMLInputElement);
          if (o.isRangeValid(r) && !c && (!o.activeEditor || !n._keydown(i))) {
            var h = e.tryCast(o.rows[r.row], t.GroupRow),
              u = o.editableCollectionView,
              d = o._getKeyCode(i);
            if (o.autoClipboard) {
              if (s && (67 == d || 45 == d)) {
                var g = new t.CellRangeEventArgs(o.cells, r);
                if (o.onCopying(g)) {
                  var p = o.getClipString() + "\r\n";
                  e.Clipboard.copy(p), o.onCopied(g)
                }
                return void i.stopPropagation()
              }
              if (s && 86 == d || l && 45 == d) return o.isReadOnly || e.Clipboard.paste(function(e) {
                o.setClipString(e)
              }), void i.stopPropagation()
            }
            var f = t.SelMove,
              _ = t.SelectionMode;
            switch (d) {
              case e.Key.Space:
                if (l && r.isValid) switch (o.selectionMode) {
                  case _.CellRange:
                  case _.Row:
                  case _.RowRange:
                  case _.ListBox:
                    o.select(new t.CellRange(r.row, 0, r.row, o.columns.length - 1))
                } else if (s && r.isValid) switch (o.selectionMode) {
                  case _.CellRange:
                    o.select(new t.CellRange(0, r.col, o.rows.length - 1, r.col))
                } else(a = this._startEditing(!0, i)) && setTimeout(function() {
                  var t = o.activeEditor;
                  t && (t.disabled || t.readOnly ? o.finishEditing() : "checkbox" == t.type ? (t.checked = !t.checked, o.finishEditing()) : e.setSelectionRange(t, t.value.length))
                });
                break;
              case 65:
                if (s) switch (o.selectionMode) {
                  case _.CellRange:
                  case _.Row:
                  case _.RowRange:
                  case _.ListBox:
                    o.select(new t.CellRange(0, 0, o.rows.length - 1, o.columns.length - 1))
                } else a = !1;
                break;
              case e.Key.Left:
                s || i.altKey ? a = !1 : r.isValid && 0 == r.col && null != h && !h.isCollapsed && h.hasChildren ? h.isCollapsed = !0 : this._moveSel(f.None, s ? f.Home : f.Prev, l);
                break;
              case e.Key.Right:
                s || i.altKey ? a = !1 : r.isValid && 0 == r.col && null != h && h.isCollapsed ? h.isCollapsed = !1 : this._moveSel(f.None, s ? f.End : f.Next, l);
                break;
              case e.Key.Up:
                s ? a = !1 : (this._altDown = i.altKey, i.altKey ? a = n._toggleListBox(i) : this._moveSel(f.Prev, f.None, l));
                break;
              case e.Key.Down:
                s ? a = !1 : (this._altDown = i.altKey, i.altKey ? a = n._toggleListBox(i) : this._moveSel(f.Next, f.None, l));
                break;
              case e.Key.PageUp:
                if (this._altDown = i.altKey, this._moveSel(i.altKey ? f.Home : f.PrevPage, f.None, l), o.rows.frozen && o.selection.row < o.rows.frozen) {
                  var w = o.scrollPosition;
                  w.y && (o.scrollPosition = new e.Point(w.x, 0))
                }
                break;
              case e.Key.PageDown:
                this._altDown = i.altKey, this._moveSel(i.altKey ? f.End : f.NextPage, f.None, l);
                break;
              case e.Key.Home:
                this._moveSel(s ? f.Home : f.None, f.Home, l);
                break;
              case e.Key.End:
                this._moveSel(s ? f.End : f.None, f.End, l);
                break;
              case e.Key.Tab:
                a = this._performKeyAction(o.keyActionTab, l);
                break;
              case e.Key.Enter:
                a = this._performKeyAction(o.keyActionEnter, l), !l && u && null != u.currentEditItem && n._commitRowEdits();
                break;
              case e.Key.Escape:
                if (a = !1, u && (u.currentAddItem || u.currentEditItem)) {
                  var m = new t.CellRangeEventArgs(o.cells, o.selection);
                  m.cancel = !0, o.onRowEditEnding(m), u.currentAddItem && u.cancelNew(), u.currentEditItem && u.cancelEdit(), o.onRowEditEnded(m), a = !0
                }
                o._mouseHdl.resetMouseState();
                break;
              case e.Key.Delete:
              case e.Key.Back:
                a = this._deleteSel(i);
                break;
              case e.Key.F2:
                a = this._startEditing(!0, i);
                break;
              case e.Key.F4:
                a = n._toggleListBox(i);
                break;
              default:
                a = !1
            }
            a && (o.containsFocus() || o.focus(), i.preventDefault(), i.stopPropagation())
          }
        }
      }, o.prototype._performKeyAction = function(e, o) {
        var n = i,
          r = t.SelMove;
        switch (e) {
          case n.MoveDown:
            return this._moveSel(o ? r.Prev : r.Next, r.None, !1), !0;
          case n.MoveAcross:
            return this._moveSel(r.None, o ? r.Prev : r.Next, !1), !0;
          case n.Cycle:
            return this._moveSel(r.None, o ? r.PrevCell : r.NextCell, !1), !0;
          case n.CycleOut:
            var s = this._g.selection;
            return this._moveSel(r.None, o ? r.PrevCell : r.NextCell, !1), !s.equals(this._g.selection)
        }
        return !1
      }, o.prototype._keypress = function(t) {
        var i = this,
          o = this._g,
          n = o._edtHdl;
        if (!o._wantsInput(t.target) && !t.defaultPrevented)
          if (this._altDown) t.preventDefault();
          else if (o.activeEditor) n._keypress(t);
          else if (t.charCode > e.Key.Space)
            if (this._startEditing(!1, t) && o.activeEditor) {
              var r = e.getActiveElement();
              if (r instanceof HTMLInputElement && "checkbox" != r.type || r instanceof HTMLTextAreaElement) {
                var s = o._selHdl.selection,
                  l = o.getCellData(s.row, s.col, !0),
                  a = o.getCellData(s.row, s.col, !1);
                r.value = String.fromCharCode(t.charCode), e.isNumber(a) && l.indexOf("%") > -1 && (r.value += "%"), e.setSelectionRange(r, 1), r.dispatchEvent(n._evtInput), n._keypress(t), n._edtValue = r.value != l ? r.value : null, t.preventDefault()
              }
            } else if (o.autoSearch) {
              var c = !1,
                s = o._selHdl.selection;
              if (t.charCode > 32 || 32 == t.charCode && this._search) {
                t.preventDefault(), this._search += String.fromCharCode(t.charCode).toLowerCase(), this._toSearch && clearTimeout(this._toSearch), this._toSearch = setTimeout(function() {
                  i._toSearch = null, i._search = ""
                }, e.Control._SEARCH_DELAY);
                var h = this._findNext(s.row, s.col);
                h < 0 && this._search.length > 1 && (this._search = this._search[this._search.length - 1], h = this._findNext(s.row, s.col)), h > -1 && (c = !0, o.select(h, s.col))
              }
              c || (this._search = "")
            }
      }, o.prototype._findNext = function(e, t) {
        var i = this._g,
          o = i.rows.length;
        (e < 0 || 1 == this._search.length) && e++;
        for (var n = 0; n < o; n++) {
          var r = (e + n) % o;
          if (0 == i.getCellData(r, t, !0).trim().toLowerCase().indexOf(this._search)) return r
        }
        return -1
      }, o.prototype._moveSel = function(e, i, o) {
        this._g.selectionMode != t.SelectionMode.None && this._g._selHdl.moveSelection(e, i, o)
      }, o.prototype._deleteSel = function(e) {
        var i = this._g,
          o = i.editableCollectionView,
          n = i.selection,
          r = i.rows,
          s = [],
          l = new t.CellRange,
          a = new t.CellEditEndingEventArgs(i.cells, l, e),
          c = t.SelectionMode;
        if (i.allowDelete && !i.isReadOnly && (null == o || o.canRemove && !o.isAddingNew && !o.isEditingItem)) switch (i.selectionMode) {
          case c.CellRange:
            if (0 == n.leftCol && n.rightCol == i.columns.length - 1)
              for (h = n.topRow; h > -1 && h <= n.bottomRow; h++) s.push(r[h]);
            break;
          case c.ListBox:
            for (h = 0; h < r.length; h++) r[h].isSelected && s.push(r[h]);
            break;
          case c.Row:
            n.topRow > -1 && s.push(r[n.topRow]);
            break;
          case c.RowRange:
            for (var h = n.topRow; h > -1 && h <= n.bottomRow; h++) s.push(r[h])
        }
        if (s.length > 0) {
          if (i.deferUpdate(function() {
            o && o.beginUpdate();
            for (var e = s.length - 1; e >= 0; e--) {
              var t = s[e];
              l.setRange(t.index, -1), i.onDeletingRow(a) && (o && t.dataItem ? o.remove(t.dataItem) : i.rows.removeAt(t.index), i.onDeletedRow(a))
            }
            o && o.endUpdate()
          }), i.selectionMode == c.ListBox) {
            var u = i.selection.row;
            u > -1 && u < i.rows.length && (i.rows[u].isSelected = !0)
          }
          return i.childItemsPath && i.collectionView && i.collectionView.refresh(), !0
        }
        if (!i.isReadOnly && 0 == s.length) {
          var d = !1,
            g = i.scrollPosition;
          return i.deferUpdate(function() {
            for (var e = n.topRow; e <= n.bottomRow; e++) {
              var t = i.rows[e];
              if (!t.isReadOnly)
                for (var r = n.leftCol; r <= n.rightCol; r++) {
                  var s = i._getBindingColumn(i.cells, e, i.columns[r]);
                  if (!s.getIsRequired() && !s.isReadOnly && i.getCellData(e, r, !0) && (l.setRange(e, r), a.cancel = !1, i.onBeginningEdit(a))) {
                    if (o) {
                      var c = o.currentEditItem;
                      !d && c && c != t.dataItem && (d = !0, o.beginUpdate()), c = t.dataItem, o.editItem(c), i._edtHdl._edItem = c
                    }
                    i.setCellData(e, r, "", !0, !1), i.onCellEditEnding(a), i.onCellEditEnded(a)
                  }
                }
            }
            i.selection = n, i.scrollPosition = g, d && o.endUpdate()
          }), !0
        }
        return !1
      }, o.prototype._startEditing = function(e, t, i, o) {
        return this._g._edtHdl.startEditing(e, i, o, !0, t)
      }, o
    }();
    t._KeyboardHandler = o
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i;
    ! function(e) {
      e[e.None = 0] = "None", e[e.Columns = 1] = "Columns", e[e.Rows = 2] = "Rows", e[e.Both = 3] = "Both", e[e.ColumnsAllCells = 4 | e.Columns] = "ColumnsAllCells", e[e.RowsAllCells = 4 | e.Rows] = "RowsAllCells", e[e.BothAllCells = 4 | e.Both] = "BothAllCells"
    }(i = t.AllowResizing || (t.AllowResizing = {}));
    ! function(e) {
      e[e.None = 0] = "None", e[e.Headers = 1] = "Headers", e[e.Cells = 2] = "Cells", e[e.Both = 3] = "Both"
    }(t.AutoSizeMode || (t.AutoSizeMode = {}));
    var o;
    ! function(e) {
      e[e.None = 0] = "None", e[e.Columns = 1] = "Columns", e[e.Rows = 2] = "Rows", e[e.Both = 3] = "Both"
    }(o = t.AllowDragging || (t.AllowDragging = {}));
    var n = function() {
      function n(i) {
        var o = this;
        this._tsLast = 0;
        var n = i.hostElement,
          r = i.addEventListener.bind(i),
          s = i.removeEventListener.bind(i);
        this._g = i, this._dvMarker = e.createElement('<div class="wj-marker">&nbsp;</div>'), r(n, "mousedown", function(n) {
          if (i._rcBounds = null, !n.defaultPrevented && 0 == n.button) {
            var c = n.target;
            if (!i.containsFocus()) {
              var h = c instanceof HTMLElement && c.tabIndex > -1 ? c : i._eFocus;
              i._setFocusNoScroll(h)
            }
            if (setTimeout(function() {
              n.defaultPrevented || i.focus()
            }), e.closest(c, ".wj-flexgrid") != i.hostElement || !i.activeEditor && i._isInputElement(c)) {
              var u = i.hitTest(n);
              switch (u.cellType) {
                case t.CellType.Cell:
                  i.select(u.range, !1), c.focus();
                  break;
                case t.CellType.ColumnHeader:
                case t.CellType.ColumnFooter:
                  i.scrollIntoView(-1, u.col);
                  break;
                case t.CellType.RowHeader:
                  i.scrollIntoView(u.row, -1)
              }
              return
            }
            var d = document;
            s(d, "mousemove"), s(d, "mouseup"), r(d, "mousemove", l), r(d, "mouseup", a), o._isDown = !0, o._mousedown(n)
          }
        });
        var l = function(e) {
            o._mousemove(e)
          },
          a = function(e) {
            o._isDown = !1, s(document, "mousemove"), s(document, "mouseup"), o._mouseup(e)
          };
        r(n, "mouseup", function(e) {
          o._tsLast = Date.now()
        }), r(n, "mouseenter", function(e) {
          i._rcBounds = null
        }), r(n, "mousemove", this._hover.bind(this)), r(n, "dblclick", this._dblclick.bind(this)), r(n, "click", this._click.bind(this)), r(n, "selectstart", function(e) {
          i._isInputElement(e.target) || e.preventDefault()
        }), r(n, "wheel", function(t) {
          if (!t.defaultPrevented && t.deltaY && !t.ctrlKey && !t.metaKey) {
            var o = i.cells.hostElement.parentElement,
              n = t.deltaY,
              r = 0;
            if (o.scrollHeight > o.offsetHeight && e.closest(t.target, ".wj-flexgrid") == i.hostElement) {
              switch (t.deltaMode) {
                case 1:
                  r = i.rows.defaultSize * (n < 0 ? -1 : 1);
                  break;
                case 2:
                  r = o.clientHeight * (n < 0 ? -1 : 1);
                  break;
                case 0:
                default:
                  e.isSafari() && (n = e.clamp(n, -150, 150)), r = n
              }
              o.scrollTop += r, t.preventDefault()
            }
          }
        }), r(n, "dragstart", this._dragstart.bind(this)), r(n, "dragover", this._dragover.bind(this)), r(n, "dragleave", this._dragover.bind(this)), r(n, "drop", this._drop.bind(this)), r(n, "dragend", this._dragend.bind(this)), this._enableTouchResizing()
      }
      return n.prototype.resetMouseState = function() {
        this._updating && (this._updating = !1, this._g.endUpdate()), this._dragSrc && e.removeClass(this._dragSrc, "wj-state-dragsrc"), this._showDragMarker(null);
        var t = this._g.hostElement;
        t && (t.style.cursor = "");
        var i = this._g;
        i.removeEventListener(document, "mousemove"), i.removeEventListener(document, "mouseup"), this._tsLast = Date.now(), this._eMouse = null, this._isDown = null, this._htDown = null, this._lbSelRows = null, this._szRowCol = null, this._szArgs = null
      }, n.prototype._enableTouchResizing = function() {
        var t = this,
          i = this._g,
          o = i.hostElement,
          n = "ontouchstart" in window ? ["touchstart", "touchmove", "touchend"] : "onpointerdown" in window ? ["pointerdown", "pointermove", "pointerup"] : null;
        n && (i.addEventListener(o, n[0], function(n) {
          if ((null == n.pointerType || "touch" == n.pointerType) && !e.closest(n.target, ".wj-elem-filter")) {
            var r = e.closest(n.target, ".wj-cell");
            r instanceof HTMLElement && (i._rcBounds = null, t._htDown = null, t._hover(n), t._szRowCol && (r.removeAttribute("draggable"), o.style.touchAction = "none", t._mousedown(n), n.preventDefault()))
          }
        }), i.addEventListener(o, n[1], function(e) {
          null != e.pointerType && "touch" != e.pointerType || t._szRowCol && (t._mousemove(e), e.preventDefault())
        }), i.addEventListener(o, n[2], function(i) {
          if ((null == i.pointerType || "touch" == i.pointerType) && t._szRowCol) {
            if (t._szArgs) t._finishResizing(t._eMouse);
            else {
              var n = e.closest(i.target, ".wj-cell");
              n instanceof HTMLElement && n.click()
            }
            t.resetMouseState(), o.style.touchAction = ""
          }
        }))
      }, n.prototype._mousedown = function(i) {
        var n = this._g,
          r = n.hitTest(i),
          s = r.cellType,
          l = i.ctrlKey || i.metaKey,
          a = i.target;
        if (this._selDown = n.selection, r.panel == n.cells) {
          if (e.closestClass(a, t.CellFactory._WJC_DROPDOWN)) return n._edtHdl._toggleListBox(i, r.range), void i.preventDefault();
          if (n.editRange && n.editRange.contains(r.range)) return
        }
        var c = e.getActiveElement();
        if (a != c || !n._isInputElement(a)) {
          if (s == t.CellType.None) return n.finishEditing(), void(a != n._root && a != n._fCt && n._edtHdl._commitRowEdits());
          if (this._htDown = r, this._eMouse = i, null != this._szRowCol) return c != n._eFocus && (n._eFocus.tabIndex = 0, n._eFocus.focus()), void this._handleResizing(i);
          switch (s) {
            case t.CellType.Cell:
              l && n.selectionMode == t.SelectionMode.ListBox && this._startListBoxSelection(r.row), this._mouseSelect(i, i.shiftKey);
              break;
            case t.CellType.RowHeader:
              0 == (this._g.allowDragging & o.Rows) && (l && n.selectionMode == t.SelectionMode.ListBox && this._startListBoxSelection(r.row), this._mouseSelect(i, i.shiftKey), i.preventDefault(), n.focus())
          }
          if (s == t.CellType.Cell && n.rows.maxGroupLevel > -1 && e.closestClass(a, t.CellFactory._WJC_COLLAPSE)) {
            var h = e.tryCast(n.rows[r.row], t.GroupRow);
            if (h) return l ? n.collapseGroupsToLevel(h.isCollapsed ? h.level + 1 : h.level) : h.isCollapsed = !h.isCollapsed, void this.resetMouseState()
          }
        }
      }, n.prototype._mousemove = function(e) {
        if (null != this._htDown) {
          if (0 == e.buttons && this._eMouse && e.timeStamp - this._eMouse.timeStamp > 600) return void this.resetMouseState();
          if (this._eMouse = e, this._szRowCol) this._handleResizing(e);
          else switch (this._htDown.cellType) {
            case t.CellType.Cell:
              this._mouseSelect(e, !0);
              break;
            case t.CellType.RowHeader:
              0 == (this._g.allowDragging & o.Rows) && this._mouseSelect(e, !0)
          }
        }
      }, n.prototype._mouseup = function(e) {
        if (!this._g.isTouching || !(this._dragSrc || e.target instanceof HTMLHtmlElement)) {
          var i = this._g,
            o = i.hitTest(e),
            n = this._htDown,
            r = t.SelectionMode;
          if (n && !e.defaultPrevented)
            if (this._szArgs) this._finishResizing(e);
            else if (n.panel != i.topLeftCells || this._szArgs) n.panel != i.columnHeaders || e.dataTransfer ? n.panel == i.cells && (this._szRowCol || e.ctrlKey || e.metaKey || e.shiftKey || o.panel == n.panel && o.range.equals(n.range) && i.selection.equals(this._selDown) && i._edtHdl.startEditing(!0, o.row, o.col, !0, e)) : o.panel == n.panel && o.col == n.col && !o.edgeRight && o.col > -1 && this._clickSort(e, o);
            else if (o.panel == n.panel && o.row == n.row && o.col == n.col && i.rows.length && i.columns.length) switch (i.selectionMode) {
              case r.CellRange:
              case r.RowRange:
              case r.ListBox:
                i.select(new t.CellRange(0, 0, i.rows.length - 1, i.columns.length - 1))
            }
          this.resetMouseState()
        }
      }, n.prototype._click = function(e) {
        Date.now() - this._tsLast > 50 && !e.pointerType && this._handleClick(e)
      }, n.prototype._handleClick = function(i) {
        var o = this._g,
          n = i.target,
          r = new t.HitTestInfo(n, null),
          s = r.panel;
        if (!i.defaultPrevented && r.grid == o && !o._isInputElement(n))
          if (s == o.topLeftCells) o.select(new t.CellRange(0, 0, o.rows.length - 1, o.columns.length - 1));
          else if (s == o.columnHeaders) this._clickSort(i, r);
          else if (s == o.rowHeaders) o.select(new t.CellRange(r.row, 0, r.row, o.columns.length - 1));
          else if (s == o.cells)
            if (r.row < 0) this._clickSort(i, r);
            else if (e.closestClass(n, t.CellFactory._WJC_COLLAPSE)) {
              var l = o.rows[r.row];
              l instanceof t.GroupRow && (i.ctrlKey ? o.collapseGroupsToLevel(l.isCollapsed ? l.level + 1 : l.level) : l.isCollapsed = !l.isCollapsed)
            } else e.closestClass(n, t.CellFactory._WJC_DROPDOWN) ? o._edtHdl._toggleListBox(i, r.range) : o.select(r.range)
      }, n.prototype._clickSort = function(i, o) {
        var n = this._g,
          r = n.collectionView,
          s = i.ctrlKey || i.metaKey;
        if (r && r.canSort && n.allowSorting) {
          o.range.bottomRow;
          var l = o.panel.columns[o.col],
            a = n._getBindingColumn(o.panel, o.row, l),
            c = a ? a._getBindingSort() : null;
          if (a.allowSorting && c) {
            for (var h = o.grid.collectionView.sortDescriptions, u = null, d = 0; d < h.length; d++)
              if (h[d].property == c) {
                u = !h[d].ascending;
                break
              }
            if (null == u) {
              if (s) return;
              u = !0
            }
            var g = new t.CellRangeEventArgs(o.panel, o.range, i);
            n.onSortingColumn(g) && (n._edtHdl._commitRowEdits(), s ? h.clear() : h.splice(0, h.length, new e.collections.SortDescription(c, u)), n.onSortedColumn(g))
          }
        }
      }, n.prototype._dblclick = function(e) {
        var o, n = this._g,
          r = n.hitTest(e),
          s = r.cellType,
          l = n.selection,
          a = r.range;
        if (!e.defaultPrevented)
          if (r.edgeRight && n.allowResizing & i.Columns) {
            if (s == t.CellType.ColumnHeader || s == t.CellType.Cell && 4 & n.allowResizing) {
              e.preventDefault(), e.ctrlKey && l.containsColumn(r.col) && (a = l);
              for (var c = a.leftCol; c <= a.rightCol; c++) n.columns[c].allowResizing && (o = new t.CellRangeEventArgs(n.cells, new t.CellRange(-1, c)), n.onAutoSizingColumn(o) && n.onResizingColumn(o) && (n.autoSizeColumn(c), n.onResizedColumn(o), n.onAutoSizedColumn(o)))
            } else s == t.CellType.TopLeft && r.panel.columns[r.col].allowResizing && (e.preventDefault(), o = new t.CellRangeEventArgs(r.panel, new t.CellRange(-1, r.col)), n.onAutoSizingColumn(o) && n.onResizingColumn(o) && (n.autoSizeColumn(r.col, !0), n.onAutoSizedColumn(o), n.onResizedColumn(o)));
            this.resetMouseState()
          } else if (r.edgeBottom && n.allowResizing & i.Rows) {
            if (s == t.CellType.RowHeader || s == t.CellType.Cell && 4 & n.allowResizing) {
              e.ctrlKey && l.containsRow(r.row) && (a = l);
              for (var h = a.topRow; h <= a.bottomRow; h++) n.rows[h].allowResizing && (o = new t.CellRangeEventArgs(n.cells, new t.CellRange(h, -1)), n.onAutoSizingRow(o) && n.onResizingRow(o) && (n.autoSizeRow(h), n.onResizedRow(o), n.onAutoSizedRow(o)))
            } else s == t.CellType.TopLeft && r.panel.rows[r.row].allowResizing && (o = new t.CellRangeEventArgs(r.panel, new t.CellRange(r.row, -1)), n.onAutoSizingRow(o) && n.onResizingRow(o) && (n.autoSizeRow(r.row, !0), n.onResizedRow(o), n.onAutoSizedRow(o)));
            this.resetMouseState()
          } else;
      }, n.prototype._hover = function(e) {
        if (!this._isDown) {
          var o = this._g,
            n = o.hitTest(e),
            r = (n.panel, n.cellType),
            s = "";
          return this._szRowCol = null, (r == t.CellType.ColumnHeader || r == t.CellType.TopLeft || r == t.CellType.Cell && 4 & o.allowResizing) && n.edgeRight && 0 != (o.allowResizing & i.Columns) && (this._szRowCol = this._getResizeCol(n)), (r == t.CellType.RowHeader || r == t.CellType.TopLeft || r == t.CellType.Cell && 4 & o.allowResizing) && n.edgeBottom && 0 != (o.allowResizing & i.Rows) && (this._szRowCol = this._getResizeRow(n)), this._szRowCol instanceof t.Column ? s = "col-resize" : this._szRowCol instanceof t.Row && (s = "row-resize"), this._szStart = this._szRowCol ? this._szRowCol.renderSize : 0, o.hostElement.style.cursor = s, n
        }
        return null
      }, n.prototype._getResizeCol = function(e) {
        for (var i = e.panel.columns, o = i[e.col], n = e.col + 1; n < i.length; n++)
          if ((r = i[n]).visible) {
            r.renderSize < 1 && (o = r);
            break
          }
        if (e.col == i.length - 1 && (e.cellType == t.CellType.TopLeft || e.cellType == t.CellType.RowHeader)) {
          i = this._g.columns;
          for (n = 0; n < i.length; n++) {
            var r = i[n];
            if (r.visible) {
              r.renderSize < 1 && (o = r);
              break
            }
          }
        }
        return o.allowResizing ? o : null
      }, n.prototype._getResizeRow = function(e) {
        for (var i = e.panel.rows, o = i[e.row], n = e.row + 1; n < i.length; n++)
          if ((r = i[n]).visible) {
            r.renderSize < 1 && (o = r);
            break
          }
        if (e.row == i.length - 1 && (e.cellType == t.CellType.TopLeft || e.cellType == t.CellType.ColumnHeader)) {
          i = this._g.rows;
          for (n = 0; n < i.length; n++) {
            var r = i[n];
            if (r.visible) {
              r.renderSize < 1 && (o = r);
              break
            }
          }
        }
        return o.allowResizing ? o : null
      }, n.prototype._mouseSelect = function(i, o) {
        var n = this,
          r = this._g;
        if (i && this._htDown && this._htDown.panel && r.selectionMode != t.SelectionMode.None) {
          var s = new t.HitTestInfo(this._htDown.panel, i);
          this._handleSelection(s, o), !e.isIE9() && i.button >= 0 && i.target != r._root && (s = new t.HitTestInfo(r, i)).cellType != t.CellType.Cell && s.cellType != t.CellType.RowHeader && this._eMouse && setTimeout(function() {
            n._eMouse && n._mouseSelect(n._eMouse, o)
          }, 100)
        }
      }, n.prototype._handleResizing = function(i) {
        if (i.preventDefault(), this._szRowCol instanceof t.Column) {
          var o = this._g,
            r = e.mouseToPage(i).x,
            s = Math.round(Math.max(n._SZ_MIN, this._szStart + (r - this._htDown.point.x) * (o.rightToLeft ? -1 : 1)));
          if (this._szRowCol.renderSize != s) {
            if (null == this._szArgs) {
              a = o.rowHeaders.columns.indexOf(this._szRowCol) > -1 ? o.rowHeaders : o.cells;
              this._szArgs = new t.CellRangeEventArgs(a, new t.CellRange(-1, this._szRowCol.index))
            }
            this._szArgs.cancel = !1, o.onResizingColumn(this._szArgs) && (o.deferResizing ? this._showResizeMarker(s) : this._szRowCol.width = s)
          }
        }
        if (this._szRowCol instanceof t.Row) {
          var o = this._g,
            l = e.mouseToPage(i).y,
            s = Math.round(Math.max(n._SZ_MIN, this._szStart + (l - this._htDown.point.y)));
          if (this._szRowCol.renderSize != s) {
            if (null == this._szArgs) {
              var a = o.columnHeaders.rows.indexOf(this._szRowCol) > -1 ? o.columnHeaders : o.cells;
              this._szArgs = new t.CellRangeEventArgs(a, new t.CellRange(this._szRowCol.index, -1))
            }
            this._szArgs.cancel = !1, o.onResizingRow(this._szArgs) && (o.deferResizing ? this._showResizeMarker(s) : this._szRowCol.height = s)
          }
        }
      }, n.prototype._dragstart = function(i) {
        var n = this._g,
          r = this._htDown,
          s = o;
        if (r) {
          if (this._dragSrc = null, this._htDrag = null, !this._szRowCol) {
            var l = new t.CellRangeEventArgs(r.panel, r.range);
            if (r.cellType == t.CellType.ColumnHeader && n.allowDragging & s.Columns && r.col > -1 && n.columns[r.col].allowDragging) n.onDraggingColumn(l) ? this._dragSrc = i.target : i.preventDefault();
            else if (r.cellType == t.CellType.RowHeader && n.allowDragging & s.Rows && r.row > -1 && n.rows[r.row].allowDragging) {
              var a = n.rows[r.row];
              a instanceof t.GroupRow || a instanceof t._NewRowTemplate || (n.onDraggingRow(l) ? this._dragSrc = i.target : i.preventDefault())
            } else r.cellType == t.CellType.TopLeft && n.allowDragging & s.Columns && r.col > -1 && n.topLeftCells.columns[r.col].allowDragging && (n.onDraggingColumn(l) ? this._dragSrc = i.target : i.preventDefault())
          }
          this._dragSrc && i.dataTransfer && !i.defaultPrevented && (this._htDrag = r, e._startDrag(i.dataTransfer, "move"), i.stopPropagation(), e.addClass(this._dragSrc, "wj-state-dragsrc"), n.beginUpdate(), this._updating = !0)
        }
      }, n.prototype._dragend = function(e) {
        this._dragSrc = null, this._htDrag = null, this.resetMouseState()
      }, n.prototype._dragover = function(i) {
        var o = this._g,
          n = o.hitTest(i),
          r = this._dragSrc ? this._htDrag : null,
          s = t.CellType,
          l = !1;
        if (r && n.cellType == r.cellType) {
          var a = new t.CellRangeEventArgs(n.panel, n.range, r);
          n.cellType == s.ColumnHeader ? (a.cancel = !o.columns.canMoveElement(r.col, n.col), l = o.onDraggingColumnOver(a)) : n.cellType == s.RowHeader ? (a.cancel = !o.rows.canMoveElement(r.row, n.row), l = o.onDraggingRowOver(a)) : n.cellType == s.TopLeft && (a.cancel = !o.topLeftCells.columns.canMoveElement(r.col, n.col), l = o.onDraggingColumnOver(a))
        }
        if (l ? (i.dataTransfer.dropEffect = "move", this._showDragMarker(n), i.preventDefault(), i.stopPropagation()) : this._showDragMarker(null), r && o.autoScroll) {
          var c = o._rcBounds,
            h = o.scrollPosition,
            u = e.Control._DRAG_SCROLL_EDGE,
            d = e.Control._DRAG_SCROLL_STEP;
          r.panel == o.columnHeaders ? (i.pageX - c.left < u && (h.x += d), c.right - i.pageX < u && (h.x -= d)) : r.panel == o.rowHeaders && (i.pageY - c.top < u && (h.y += d), c.bottom - i.pageY < u && (h.y -= d)), h.equals(o._ptScrl) || (o.scrollPosition = h)
        }
      }, n.prototype._drop = function(e) {
        var i = this._g,
          o = i.hitTest(e),
          n = this._dragSrc ? this._htDrag : null,
          r = t.CellType;
        if (n && o.cellType == n.cellType) {
          var s = i.selection,
            l = new t.CellRangeEventArgs(o.panel, o.range, n);
          o.cellType == r.ColumnHeader ? (i.columns.moveElement(n.col, o.col), i.select(s.row, o.col), i.onDraggedColumn(l)) : o.cellType == r.RowHeader ? (i.rows.moveElement(n.row, o.row), i.select(o.row, s.col), i.onDraggedRow(l)) : o.cellType == r.TopLeft && (i.topLeftCells.columns.moveElement(n.col, o.col), i.onDraggedColumn(l))
        }
        this.resetMouseState()
      }, n.prototype._showResizeMarker = function(i) {
        var o = this._g,
          n = this._dvMarker,
          r = o.cells.hostElement;
        n.parentElement != r && r.appendChild(n);
        var s, l = this._szArgs.panel.cellType,
          a = t.CellType;
        this._szRowCol instanceof t.Column ? (s = {
          left: this._szRowCol.pos + i - 1,
          top: -1e3,
          right: "",
          bottom: 0,
          width: 3,
          height: ""
        }, o.rightToLeft && (s.left = r.clientWidth - s.left - s.width), l != a.TopLeft && l != a.RowHeader || (s.left -= o._eTL.offsetWidth)) : (s = {
          left: -1e3,
          top: this._szRowCol.pos + i - 1,
          right: 0,
          bottom: "",
          width: "",
          height: 3
        }, l != a.TopLeft && l != a.ColumnHeader || (s.top -= o._eTL.offsetHeight)), e.setCss(n, s)
      }, n.prototype._showDragMarker = function(i) {
        var o = this._g,
          n = this._dvMarker;
        if (!i || !i.panel) return e.removeChild(n), void(this._rngTarget = null);
        if (!i.range.equals(this._rngTarget)) {
          this._rngTarget = i.range;
          var r = i.panel.hostElement;
          n.parentElement != r && r.appendChild(n);
          var s = t.CellType,
            l = {
              left: 0,
              top: 0,
              width: 6,
              height: 6,
              right: "",
              bottom: ""
            };
          switch (i.cellType) {
            case s.TopLeft:
            case s.ColumnHeader:
              l.height = i.panel.height;
              var a = i.panel.columns[i.col];
              l.left = a.pos - l.width / 2, this._htDown && i.col > this._htDown.col && (l.left += a.renderWidth), o.rightToLeft && (l.left = n.parentElement.clientWidth - l.left - l.width);
              break;
            case s.RowHeader:
              l.width = i.panel.width;
              var c = i.panel.rows[i.row];
              l.top = c.pos - l.height / 2, i.row > this._htDown.row && (l.top += c.renderHeight)
          }
          e.setCss(n, l)
        }
      }, n.prototype._finishResizing = function(i) {
        var o = this._g,
          r = o.selection,
          s = this._szArgs,
          l = this._eMouse && this._eMouse.ctrlKey,
          a = t.CellType;
        if (s && !s.cancel) {
          if (s.col > -1) {
            var c = s.col,
              h = e.mouseToPage(i).x,
              u = Math.round(Math.max(n._SZ_MIN, this._szStart + (h - this._htDown.point.x) * (this._g.rightToLeft ? -1 : 1)));
            if (s.panel.columns[c].width = Math.round(u), o.onResizedColumn(s), l && this._htDown.cellType == a.ColumnHeader && r.containsColumn(c))
              for (var d = r.leftCol; d <= r.rightCol; d++) o.columns[d].allowResizing && d != c && (s = new t.CellRangeEventArgs(o.cells, new t.CellRange(-1, d)), o.onResizingColumn(s) && (o.columns[d].size = o.columns[c].size, o.onResizedColumn(s)))
          }
          if (s.row > -1) {
            var c = s.row,
              g = e.mouseToPage(i).y,
              u = Math.round(Math.max(n._SZ_MIN, this._szStart + (g - this._htDown.point.y)));
            if (s.panel.rows[c].height = Math.round(u), o.onResizedRow(s), l && this._htDown.cellType == a.RowHeader && r.containsRow(c))
              for (var p = r.topRow; p <= r.bottomRow; p++) o.rows[p].allowResizing && p != c && (s = new t.CellRangeEventArgs(o.cells, new t.CellRange(p, -1)), o.onResizingRow(s) && (o.rows[p].size = o.rows[c].size, o.onResizedRow(s)))
          }
        }
      }, n.prototype._startListBoxSelection = function(e) {
        var t = this._g.rows;
        this._lbSelState = !t[e].isSelected, this._lbSelRows = {};
        for (var i = 0; i < t.length; i++) t[i].isSelected && (this._lbSelRows[i] = !0)
      }, n.prototype._handleSelection = function(e, i) {
        var o = this._g,
          n = o.rows,
          r = o._selHdl.selection,
          s = new t.CellRange(e.row, e.col);
        if (e.row > -1 && e.col > -1)
          if (null != this._lbSelRows) {
            var l = !1;
            s = new t.CellRange(e.row, e.col, this._htDown.row, this._htDown.col);
            for (var a = 0; a < n.length; a++) {
              var c = s.containsRow(a) ? this._lbSelState : null != this._lbSelRows[a];
              if (c != n[a].isSelected) {
                var h = new t.CellRangeEventArgs(o.cells, new t.CellRange(a, r.col, a, r.col2));
                o.onSelectionChanging(h) && (n[a]._setFlag(t.RowColFlags.Selected, c, !0), l = !0, o.onSelectionChanged(h))
              }
            }
            l && o.refreshCells(!1, !0, !0), o.scrollIntoView(e.row, e.col)
          } else e.cellType == t.CellType.RowHeader && (s.col = 0, s.col2 = o.columns.length - 1), i && (s.row2 = r.row2, s.col2 = r.col2), o.select(s)
      }, n._SZ_MIN = 0, n
    }();
    t._MouseHandler = n
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i = function() {
      function i(i) {
        var o = this;
        this._fullEdit = !1, this._list = null, this._g = i, this._evtInput = document.createEvent("HTMLEvents"), this._evtInput.initEvent("input", !0, !1), this._evtChange = document.createEvent("HTMLEvents"), this._evtChange.initEvent("change", !0, !1), i.selectionChanging.addHandler(function(e, t) {
          if (o.finishEditing()) {
            var n = i._selHdl.selection.row;
            if (n != t.row) {
              var r = i.rows.length;
              (n > -1 && n < r ? i.rows[n].dataItem : null) != (t.row > -1 && t.row < r ? i.rows[t.row].dataItem : null) && o._commitRowEdits()
            }
          } else t.cancel = !0
        }), i.lostFocus.addHandler(function() {
          if (!i.containsFocus()) {
            var t = e.getActiveElement();
            t && "fixed" == getComputedStyle(t).position || o._commitRowEdits()
          }
        });
        var n = i.hostElement;
        i.addEventListener(n, "mousedown", function(n) {
          if (!n.defaultPrevented && 0 == n.button && !i._mouseHdl._szRowCol) {
            i.selection;
            var r = i.hitTest(n);
            if (r.cellType != t.CellType.Cell && r.cellType != t.CellType.None) o.finishEditing();
            else if (r.cellType != t.CellType.None) {
              var s = e.tryCast(n.target, HTMLInputElement);
              o._isNativeCheckbox(s) && (s != o.activeEditor && (n.preventDefault(), o.startEditing(!1, r.row, r.col, !0, n) && (s = o.activeEditor)), !s || "checkbox" != s.type || s.disabled || s.readOnly || (s.checked = !s.checked, s.focus(), o.finishEditing()))
            }
          }
        }, !0), i.addEventListener(n, "click", function(e) {
          o._isNativeCheckbox(e.target) && e.preventDefault()
        }), i.addEventListener(n, "compositionend", this._keypress.bind(this))
      }
      return i.prototype.startEditing = function(i, o, n, r, s) {
        void 0 === i && (i = !0);
        var l = this._g;
        if (o = e.asNumber(o, !0, !0), n = e.asNumber(n, !0, !0), null == o && (o = l.selection.row), null == n && (n = l.selection.col), null == r && (r = !0), !this._allowEditing(o, n)) return !1;
        var a = l.getMergedRange(l.cells, o, n, !1);
        a || (a = new t.CellRange(o, n));
        var c = l.rows[o].dataItem;
        if (l.scrollIntoView(a.row, a.col, !0), l.select(a, !0), !l.rows[o] || c != l.rows[o].dataItem) return !1;
        if (a.equals(this._rng)) return !0;
        if (this.activeEditor && !this.finishEditing()) return !1;
        var h = new t.CellRangeEventArgs(l.cells, a, s);
        if (!l.onBeginningEdit(h)) return !1;
        var u = l.editableCollectionView,
          d = !1;
        u && ((d = (c = l.rows[o].dataItem) != u.currentEditItem) && l.onRowEditStarting(h), u.editItem(c), d && (l.onRowEditStarted(h), this._edItem = c)), this._fullEdit = e.asBoolean(i), this._rng = a, this._list = null;
        var g = l.columns[n].dataMap;
        g && (this._list = g.getDisplayValues(c)), a.isSingleCell ? this._updateEditorCell(o, n, d) : l.refresh(!1);
        var p = this._edt;
        if (p) {
          if ("checkbox" == p.type) this._fullEdit = !1;
          else if (r) {
            l._setFocusNoScroll(p);
            var f = e.culture.Globalize.numberFormat["%"] || "%";
            if (e.isNumber(l.getCellData(o, n, !1)) && p.value.indexOf(f) > -1) {
              for (var _ = p.value, w = 0, m = _.length; m > 0 && _[m - 1] == f;) m--;
              for (; w < m && _[w] == f;) w++;
              e.setSelectionRange(p, w, m)
            } else e.setSelectionRange(p, 0, p.value.length)
          }
          if (l.onPrepareCellForEdit(h), p.disabled || p.readOnly) return !1
        }
        return null != p && !p.disabled && !p.readOnly
      }, i.prototype.finishEditing = function(i) {
        var o = this._edt;
        if (!o) return this._removeListBox(), !0;
        var n = this._g,
          r = this._rng,
          s = new t.CellEditEndingEventArgs(n.cells, r),
          l = n.containsFocus(),
          a = n.hostElement.querySelector(".wj-control.wj-state-focused");
        if (a) {
          var c = e.Control.getControl(a);
          c && c.onLostFocus(s)
        }
        if (s.cancel = i, !i && n.validateEdits) {
          var h = this._getValidationError();
          if (h) {
            s.cancel = !0;
            var u = o.parentElement;
            u && (e.toggleClass(u, "wj-state-invalid", !0), u.title = h, s.stayInEditMode = !0)
          }
        }
        if (!n.onCellEditEnding(s) && s.stayInEditMode) return l ? setTimeout(function() {
          o.select()
        }) : o.select(), !1;
        if (!s.cancel) {
          s.data = n.cells.getCellData(r.topRow, r.leftCol, !1);
          for (var d = n.cellFactory.getEditorValue(n), g = r.topRow; g <= r.bottomRow && g < n.rows.length; g++)
            for (var p = r.leftCol; p <= r.rightCol && p < n.columns.length; p++) n.cells.setCellData(g, p, d, !0, !1);
          o.value == this._edtValue && o.dispatchEvent(this._evtChange)
        }
        this._edt = null, this._rng = null, this._list = null, this._edtValue = null, this._removeListBox();
        var f = e.closest(o, ".wj-cell");
        return e.contains(f, e.getActiveElement()) && f.focus(), s.cancel || !s.refresh ? this._updateEditorCell(r.row, r.col, !1) : n.refresh(!1), l && n.focus(), n.onCellEditEnded(s), !0
      }, Object.defineProperty(i.prototype, "activeEditor", {
        get: function() {
          return this._edt
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "editRange", {
        get: function() {
          return this._rng
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.getClipString = function(i) {
        var o = this._g,
          n = "",
          r = !0,
          s = t.SelectionMode;
        if (!i) switch (i = o.selection, o.selectionMode) {
          case s.Row:
          case s.RowRange:
            i.col = 0, i.col2 = o.columns.length - 1;
            break;
          case s.ListBox:
            i.col = 0, i.col2 = o.columns.length - 1;
            for (var l = 0; l < o.rows.length; l++) o.rows[l].isSelected && o.rows[l].isVisible && (i.row = i.row2 = l, n && (n += "\n"), n += this.getClipString(i));
            return n
        }
        for (var a = (i = e.asType(i, t.CellRange)).topRow; a <= i.bottomRow; a++)
          if (o.rows[a].isVisible) {
            r || (n += "\n"), r = !1;
            for (var c = i.leftCol, h = !0; c <= i.rightCol; c++)
              if (o.columns[c].isVisible) {
                h || (n += "\t"), h = !1;
                var u = o.getCellData(a, c, !0);
                (u = u.replace(/\t/g, " ")).indexOf("\n") > -1 && (u = '"' + u.replace(/"/g, '""') + '"'), n += u
              }
          }
        return n
      }, i.prototype.setClipString = function(i, o) {
        var n = this,
          r = this._g,
          s = null == o,
          l = t.SelectionMode;
        if (!o) switch (o = r.selection, r.selectionMode) {
          case l.Row:
          case l.RowRange:
          case l.ListBox:
            o.col = 0, o.col2 = r.columns.length - 1
        }
        o = e.asType(o, t.CellRange);
        var a = this._parseClipString(e.asString(i));
        s && !o.isSingleCell && a.length && this._expandClipRows(a, o);
        var c = new t.CellRange(o.topRow, o.leftCol, o.topRow + a.length - 1, o.leftCol + a[0].length - 1),
          h = new t.CellRangeEventArgs(r.cells, c);
        if (!r.onPasting(h)) return !1;
        c = new t.CellRange(o.topRow, o.leftCol);
        var u = r.editableCollectionView,
          d = o.topRow,
          g = !1;
        r.deferUpdate(function() {
          u && a.length > 1 && u.beginUpdate();
          for (var i = 0; i < a.length && d < r.rows.length; i++, d++)
            if (r.rows[d].isVisible) {
              for (var s = a[i], l = o.leftCol, p = 0; p < s.length && l < r.columns.length; p++, l++) {
                var f = r.columns[l];
                if (f.isVisible) {
                  if (n._allowEditing(d, l)) {
                    var _ = s[p];
                    f.maxLength && (_ = _.substr(0, f.maxLength));
                    var w = new t.CellRangeEventArgs(r.cells, new t.CellRange(d, l), _);
                    r.onPastingCell(w) && (u && (u.editItem(r.rows[d].dataItem), n._edItem = u.currentEditItem), r.cells.setCellData(d, l, w.data) && (r.onPastedCell(w), g = !0)), c.row2 = Math.max(c.row2, d), c.col2 = Math.max(c.col2, l)
                  }
                } else p--
              }
              if (n._edItem && u instanceof e.collections.CollectionView) {
                var m = new e.collections.NotifyCollectionChangedEventArgs(e.collections.NotifyCollectionChangedAction.Change, n._edItem, d);
                u.onCollectionChanged(m)
              }
            } else i--;
          if (u && a.length > 1 && u.endUpdate(), r.select(c), r.onPasted(h), g && e.closest(r.hostElement, "form")) {
            var y = e.createElement("<input>", r.hostElement),
              v = document.createEvent("HTMLEvents");
            v.initEvent("change", !0, !1), y.dispatchEvent(v), e.removeChild(y)
          }
        })
      }, i.prototype._isNativeCheckbox = function(t) {
        return t instanceof HTMLInputElement && "checkbox" == t.type && !t.disabled && !t.readOnly && e.hasClass(t, "wj-cell-check") && e.closest(t, ".wj-flexgrid") == this._g.hostElement
      }, i.prototype._parseClipString = function(e) {
        var t = [];
        e = (e = e.replace(/\r\n/g, "\n").replace(/\r/g, "\n")).replace(/\n$/, "");
        var i = 0,
          o = 0;
        for (i = 0; i < e.length; i++) {
          var n = '"' == e[i] && e[i + 1] >= " ",
            r = !1,
            s = !1;
          for (o = i; o < e.length && !s; o++) {
            var l = e[o];
            switch (l) {
              case '"':
                n && (r = !r);
                break;
              case "\t":
              case "\n":
                r || (this._parseClipCell(t, e, i, o, "\n" == l), i = o, s = !0)
            }
          }
          if (o == e.length) {
            this._parseClipCell(t, e, i, o, !1);
            break
          }
        }
        return 0 == t.length && t.push([""]), t
      }, i.prototype._parseClipCell = function(e, t, i, o, n) {
        e.length || e.push([]);
        var r = t.substr(i, o - i),
          s = r.length;
        s > 1 && '"' == r[0] && '"' == r[s - 1] ? r = (r = r.substr(1, s - 2)).replace(/""/g, '"') : "\t" == r && (r = ""), e[e.length - 1].push(r), n && e.push([])
      }, i.prototype._expandClipRows = function(e, t) {
        for (var i = e.length, o = 0, n = 0; n < i; n++) o = Math.max(o, e[n].length);
        for (var r = this._g, s = 0, l = 0, a = t.topRow; a <= t.bottomRow; a++) r.rows[a].isVisible && s++;
        for (c = t.leftCol; c <= t.rightCol; c++) r.columns[c].isVisible && l++;
        if ((s > 1 || l > 1) && (1 == s && (s = i), 1 == l && (l = o), l % o == 0 && s % i == 0)) {
          for (var c = o; c < l; c++)
            for (a = 0; a < i; a++) e[a].push(e[a % i][c % o]);
          for (a = i; a < s; a++) e.push(e[a % i])
        }
      }, i.prototype._updateEditorCell = function(e, i, o) {
        var n = this._g,
          r = n.cells.getCellElement(e, i),
          s = n._useFrozenDiv() && (e < n.frozenRows || i < n.frozenColumns);
        if (!r || s || n._hasPendingUpdates()) n.refresh(!1);
        else if (this._updateCell(r), (o = o || n._getHasValidation()) && n.headersVisibility & t.HeadersVisibility.Row) {
          var l = n.rowHeaders,
            a = l.columns.length - 1,
            c = l.getCellElement(e, a);
          c && this._updateCell(c)
        }
      }, i.prototype._updateCell = function(i) {
        var o = new t.HitTestInfo(i, null),
          n = t.FlexGrid._WJS_UPDATING;
        o.panel && (e.addClass(i, n), o.grid.cellFactory.updateCell(o.panel, o.row, o.col, i, o.range), e.removeClass(i, n))
      }, i.prototype._getValidationError = function() {
        var e = this._g;
        if (e._getHasValidation()) {
          var t = this._rng.row,
            i = this._rng.col,
            o = e.cellFactory.getEditorValue(e),
            n = e.getCellData(t, i, !1);
          if (e.setCellData(t, i, o, !0, !1)) {
            var r = e._getError(e.cells, t, i);
            return e.setCellData(t, i, n, !0, !1), r
          }
        }
        return null
      }, i.prototype._allowEditing = function(e, i) {
        var o = this._g;
        if (o.isReadOnly || o.selectionMode == t.SelectionMode.None) return !1;
        if (e < 0 || e >= o.rows.length || o.rows[e].isReadOnly || !o.rows[e].isVisible) return !1;
        if (i < 0 || i >= o.columns.length) return !1;
        var n = o._getBindingColumn(o.cells, e, o.columns[i]);
        return !(!n || n.isReadOnly || !n.isVisible)
      }, i.prototype._commitRowEdits = function() {
        var e = this._g;
        if (this.finishEditing() && this._edItem) {
          var i = e.editableCollectionView;
          if (i && (i.currentEditItem || i.currentAddItem)) {
            var o = new t.CellRangeEventArgs(e.cells, e.selection);
            e.onRowEditEnding(o), i.commitEdit(), e.onRowEditEnded(o)
          }
          this._edItem = null
        }
      }, i.prototype._keydown = function(t) {
        var i = this._edt;
        switch (t.keyCode) {
          case e.Key.F2:
            return this._fullEdit = !this._fullEdit, t.preventDefault(), !0;
          case e.Key.F4:
            return this._toggleListBox(t), t.preventDefault(), !0;
          case e.Key.Space:
            return !i || "checkbox" != i.type || i.disabled || i.readOnly || (i.checked = !i.checked, this.finishEditing(), t.preventDefault()), !0;
          case e.Key.Enter:
            if (t.preventDefault(), i && t.altKey) {
              var o = t.target;
              if (o instanceof HTMLTextAreaElement && "soft" == o.wrap) {
                var n = o.value,
                  r = o.selectionStart,
                  s = o.selectionEnd;
                o.value = n.substr(0, r) + "\n" + n.substr(s), e.setSelectionRange(o, r + 1)
              }
              return !0
            }
            return !this.finishEditing();
          case e.Key.Tab:
            return t.preventDefault(), !this.finishEditing();
          case e.Key.Escape:
            return t.preventDefault(), this.finishEditing(!0), !0;
          case e.Key.Up:
          case e.Key.Down:
          case e.Key.Left:
          case e.Key.Right:
          case e.Key.PageUp:
          case e.Key.PageDown:
          case e.Key.Home:
          case e.Key.End:
            if (this._lbx) return this._keydownListBox(t);
            if (t.altKey) switch (t.keyCode) {
              case e.Key.Up:
              case e.Key.Down:
                return this._toggleListBox(t), t.preventDefault(), !0
            }
            if (!this._fullEdit && this.finishEditing()) return !1
        }
        return !0
      }, i.prototype._keydownListBox = function(t) {
        var i = !0;
        if (this._lbx) switch (t.keyCode) {
          case e.Key.Up:
            t.altKey ? this._toggleListBox(t) : this._lbx.selectedIndex > 0 && this._lbx.selectedIndex--;
            break;
          case e.Key.Down:
            t.altKey ? this._toggleListBox(t) : this._lbx.selectedIndex++;
            break;
          case e.Key.Home:
          case e.Key.PageUp:
            this._lbx.selectedIndex = 0;
            break;
          case e.Key.End:
          case e.Key.PageDown:
            this._lbx.selectedIndex = this._lbx.collectionView.items.length - 1;
            break;
          default:
            i = !1
        }
        return !!i && (t.preventDefault(), !0)
      }, i.prototype._keypress = function(t) {
        var i = this._edt,
          o = t.charCode || 32;
        if (i && "checkbox" != i.type && e.getActiveElement() == i && this._list && this._list.length > 0 && o >= 32) {
          var n = i.selectionStart,
            r = i.value.substr(0, n);
          t.target == i && t.charCode && (r += String.fromCharCode(t.charCode), n++);
          var s = this._findString(this._list, r, !0);
          s < 0 && (s = this._findString(this._list, r, !1)), s > -1 && (i.value = this._list[s], e.setSelectionRange(i, n, i.value.length), i.dispatchEvent(this._evtInput), t.preventDefault && t.preventDefault())
        }
      }, i.prototype._findString = function(e, t, i) {
        i || (t = t.toLowerCase());
        for (var o = 0; o < e.length; o++) {
          var n = e[o];
          if (i || (n = n.toLowerCase()), 0 == n.indexOf(t)) return o
        }
        return -1
      }, i.prototype._toggleListBox = function(t, i) {
        var o = this._g,
          n = o._selHdl.selection,
          r = o.isTouching;
        if (i || (i = n), this._lbx && (this._removeListBox(), n.contains(i))) return o.activeEditor ? o.activeEditor.focus() : o.containsFocus() || o.focus(), !0;
        var s = o._getBindingColumn(o.cells, i.row, o.columns[i.col]);
        return !(!e.input || !s.dataMap || !1 === s.showDropDown) && (!(!e.input || !this.startEditing(!0, i.row, i.col, !r, t)) && (this._lbx = this._createListBox(), this._lbx.showSelection(), r && this._lbx.focus(), !0))
      }, i.prototype._createListBox = function() {
        var i = this,
          o = this._g,
          n = o.activeEditor,
          r = this._rng,
          s = o.rows[r.row],
          l = o._getBindingColumn(o.cells, r.row, o.columns[r.col]),
          a = document.createElement("div");
        this._removeListBox(), e.addClass(a, "wj-dropdown-panel wj-grid-listbox"), e.addClass(a, l.dropDownCssClass);
        var c = new e.input.ListBox(a, {
          maxHeight: 4 * s.renderHeight,
          isContentHtml: l.isContentHtml,
          itemsSource: l.dataMap.getDisplayValues(s.dataItem),
          selectedValue: n ? n.value : o.getCellData(r.row, r.col, !0)
        });
        c.addEventListener(c.hostElement, "click", function() {
          i._removeListBox(), o.focus(), i.finishEditing()
        }), c.lostFocus.addHandler(function() {
          i._removeListBox()
        }), c.selectedIndexChanged.addHandler(function() {
          var t = o.activeEditor;
          t && (t.value = c.selectedValue, t.dispatchEvent(i._evtInput), e.setSelectionRange(t, 0, t.value.length))
        });
        var h = o.cells.getCellElement(r.row, r.col);
        if (h) {
          e.showPopup(a, h, !1, !1, !1);
          var u = h.querySelector("." + t.CellFactory._WJC_DROPDOWN);
          e.setAttribute(u, "aria-expanded", !0)
        } else e.showPopup(a, o.getCellBoundingRect(r.row, r.col)), a[e.Control._OWNR_KEY] = o.hostElement;
        return c
      }, i.prototype._removeListBox = function() {
        var t = this._lbx;
        t && (this._lbx = null, e.hidePopup(t.hostElement, function() {
          t.dispose()
        }))
      }, i
    }();
    t._EditHandler = i
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
      function o() {
        this.constructor = t
      }
      e(t, i), t.prototype = null === i ? Object.create(i) : (o.prototype = i.prototype, new o)
    }
  }(),
  wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i = function() {
      function i(e) {
        this._nrt = new o, this._g = e, this._keydownBnd = this._keydown.bind(this), this._attach()
      }
      return Object.defineProperty(i.prototype, "newRowAtTop", {
        get: function() {
          return this._top
        },
        set: function(t) {
          t != this.newRowAtTop && (this._top = e.asBoolean(t), this.updateNewRowTemplate())
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.updateNewRowTemplate = function() {
        var e = this._g,
          i = e.editableCollectionView,
          o = e.rows,
          n = i && i.canAddNew && e.allowAddNew && !e.isReadOnly,
          r = o.indexOf(this._nrt),
          s = this._top ? 0 : o.length - 1,
          l = !1;
        if (!n && r > -1) {
          var a = e.selection;
          a.row == r && e.select(a.row - 1, a.col), o.removeAt(r)
        } else n && (r < 0 ? l = !0 : r != s && (o.removeAt(r), l = !0), l && (this._top ? o.insert(0, this._nrt) : o.push(this._nrt)), this._nrt && this._nrt._setFlag(t.RowColFlags.ParentCollapsed, !1))
      }, i.prototype._attach = function() {
        var e = this._g;
        e && (e.beginningEdit.addHandler(this._beginningEdit, this), e.pastingCell.addHandler(this._beginningEdit, this), e.rowEditEnded.addHandler(this._rowEditEnded, this), e.loadedRows.addHandler(this.updateNewRowTemplate, this), e.hostElement.addEventListener("keydown", this._keydownBnd, !0))
      }, i.prototype._detach = function() {
        var e = this._g;
        e && (e.beginningEdit.removeHandler(this._beginningEdit), e.pastingCell.removeHandler(this._beginningEdit), e.rowEditEnded.removeHandler(this._rowEditEnded), e.loadedRows.removeHandler(this.updateNewRowTemplate), e.hostElement.removeEventListener("keydown", this._keydownBnd, !0))
      }, i.prototype._keydown = function(t) {
        t.defaultPrevented || t.keyCode != e.Key.Escape || null == this._g.activeEditor && this._top && this._nrt.dataItem && (this._nrt.dataItem = null, this._g.invalidate())
      }, i.prototype._beginningEdit = function(t, i) {
        if (!i.cancel) {
          var n = this._g.rows[i.row];
          if (e.tryCast(n, o)) {
            var r = this._g.editableCollectionView;
            if (r && r.canAddNew)
              if (this._top) {
                if (null == this._nrt.dataItem) {
                  var s = null,
                    l = r.sourceCollection,
                    a = r.newItemCreator;
                  s = e.isFunction(a) ? a() : l && l.length ? new l[0].constructor : {}, this._nrt.dataItem = s
                }
              } else {
                s = r.currentAddItem && r.currentAddItem == n.dataItem ? r.currentAddItem : r.addNew();
                r.moveCurrentTo(s), this.updateNewRowTemplate(), this._g.refresh(!0), this._g.onRowAdded(i), i.cancel && r.cancelNew()
              }
          }
        }
      }, i.prototype._rowEditEnded = function(e, t) {
        var i = this,
          o = this._g,
          n = o.editableCollectionView,
          r = this._nrt.dataItem;
        if (n && !this._committing)
          if (n.isAddingNew) n.commitNew();
          else if (r && !t.cancel) {
            this._committing = !0, this._nrt.dataItem = null;
            var s = n.addNew();
            for (var l in r) s[l] = r[l];
            o.onRowAdded(t), t.cancel ? n.cancelNew() : n.commitNew(), setTimeout(function() {
              o.select(0, o.columns.firstVisibleIndex), i.updateNewRowTemplate()
            }, 20), this._committing = !1
          }
      }, i
    }();
    t._AddNewHandler = i;
    var o = function(e) {
      function t() {
        return null !== e && e.apply(this, arguments) || this
      }
      return __extends(t, e), t
    }(t.Row);
    t._NewRowTemplate = o
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i = function() {
      function t(i) {
        this._updateImeFocusBnd = this._updateImeFocus.bind(this), this._cmpstartBnd = this._compositionstart.bind(this), this._mousedownBnd = this._mousedown.bind(this), this._mouseupBnd = this._mouseup.bind(this), this._keypressBnd = this._keypress.bind(this), this._g = i;
        var o = e.createElement('<textarea class="wj-grid-editor wj-form-control wj-grid-ime" aria-hidden="true"/>');
        e.disableAutoComplete(o), e.setCss(o, t._cssHidden), this._tbx = o, i.cells.hostElement.parentElement.appendChild(o), this._updateImeFocus();
        var n = i.hostElement,
          r = i.addEventListener.bind(i);
        r(o, "compositionstart", this._cmpstartBnd), r(n, "blur", this._updateImeFocusBnd), r(n, "focus", this._updateImeFocusBnd), r(n, "mousedown", this._mousedownBnd, !0), r(n, "mouseup", this._mouseupBnd, !0), r(n, "keypress", this._keypressBnd, !0), i.cellEditEnded.addHandler(this._cellEditEnded, this), i.selectionChanged.addHandler(this._updateImeFocus, this)
      }
      return t.prototype.dispose = function() {
        var t = this._g,
          i = t.hostElement,
          o = this._tbx,
          n = t.removeEventListener.bind(t);
        n(o, "compositionstart", this._cmpstartBnd), n(i, "blur", this._updateImeFocusBnd), n(i, "focus", this._updateImeFocusBnd), n(i, "mousedown", this._mousedownBnd), n(i, "mouseup", this._mouseupBnd), n(i, "keypress", this._keypressBnd), t.cellEditEnded.removeHandler(this._cellEditEnded), t.selectionChanged.removeHandler(this._updateImeFocus), e.removeChild(o)
      }, t.prototype._compositionstart = function() {
        var t = this._g;
        if (null == t.activeEditor) {
          var i = t._selHdl.selection;
          if (t.startEditing(!1, i.row, i.col, !1) && t.activeEditor) {
            i = t.editRange;
            var o = t.activeEditor,
              n = this._tbx,
              r = t.cells.hostElement,
              s = t.columns[i.col].pos + r.offsetLeft,
              l = t.rows[i.row].pos + r.offsetTop,
              a = t.getCellBoundingRect(i.row, i.col),
              c = o.parentElement,
              h = getComputedStyle(c),
              u = c.style.zIndex;
            i.isSingleCell || (a.width = c.offsetWidth, a.height = c.offsetHeight), i.row < t.frozenRows && (l += r.parentElement.scrollTop), i.col < t.frozenColumns && (s += r.parentElement.scrollLeft);
            var d = c.querySelector(".wj-btn.wj-right");
            d && (a.width -= d.offsetWidth), "minLength,maxLength,pattern".split(",").forEach(function(t) {
              e.setAttribute(n, t, o.getAttribute(t))
            }), e.setAttribute(n, "wrap", o instanceof HTMLTextAreaElement ? "soft" : "off"), e.setCss(n, {
              position: "absolute",
              left: s,
              top: l,
              width: a.width - 1,
              height: a.height - 1,
              paddingLeft: h.paddingLeft,
              paddingTop: h.paddingTop,
              zIndex: u
            }), t._edtHdl._edt = n, o.value = ""
          }
        }
      }, t.prototype._cellEditEnded = function() {
        var i = this;
        e.setCss(this._tbx, t._cssHidden), setTimeout(function() {
          i._tbx.value = ""
        })
      }, t.prototype._mousedown = function(e) {
        this._isMouseDown = !0, this._updateImeFocus()
      }, t.prototype._mouseup = function(e) {
        this._isMouseDown = !1, this._updateImeFocus()
      }, t.prototype._keypress = function(e) {
        null == this._g.activeEditor && (this._tbx.value = "", this._compositionstart(), e.stopPropagation())
      }, t.prototype._updateImeFocus = function() {
        var t = this._g,
          i = e.getActiveElement();
        if (!t.activeEditor && e.closest(i, ".wj-flexgrid") == t.hostElement) {
          var o = this._tbx;
          this._enableIme() ? i != o && (o.disabled = !1, o.select(), o.focus()) : o.disabled = !0
        }
      }, t.prototype._enableIme = function() {
        var t = this._g,
          i = t.selection,
          o = i.isValid ? t._getBindingColumn(t.cells, i.row, t.columns[i.col]) : null;
        return !!t.canEditCell(i.row, i.col) && !(!o || o.dataType == e.DataType.Boolean)
      }, t._cssHidden = {
        position: "fixed",
        left: -10,
        top: -10,
        width: "1px",
        overflow: "hidden"
      }, t
    }();
    t._ImeHandler = i
  }(e.grid || (e.grid = {}))
}(wijmo || (wijmo = {}));
