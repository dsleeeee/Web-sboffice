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
    "use strict";
    var i = function() {
      function t() {
        this._cnt = 0, this._cntn = 0, this._sum = 0, this._sum2 = 0, this._min = null, this._max = null, this._first = null, this._last = null
      }
      return t.prototype.add = function(i, n) {
        i instanceof t ? (this._sum += i._sum, this._sum2 += i._sum2, this._max = this._max && i._max ? Math.max(this._max, i._max) : this._max || i._max, this._min = this._min && i._min ? Math.min(this._min, i._min) : this._min || i._min, this._cnt += i._cnt, this._cntn += i._cntn) : null != i && (this._cnt++, e.isBoolean(i) && (i = i ? 1 : 0), (null == this._min || i < this._min) && (this._min = i), (null == this._max || i > this._max) && (this._max = i), null == this._first && (this._first = i), this._last = i, e.isNumber(i) && !isNaN(i) && (e.isNumber(n) && (i *= n), this._cntn++, this._sum += i, this._sum2 += i * i))
      }, t.prototype.getAggregate = function(t) {
        if (0 == this._cnt) return null;
        var i = 0 == this._cntn ? 0 : this._sum / this._cntn;
        switch (t) {
          case e.Aggregate.Avg:
            return i;
          case e.Aggregate.Cnt:
            return this._cnt;
          case e.Aggregate.Max:
            return this._max;
          case e.Aggregate.Min:
            return this._min;
          case e.Aggregate.Rng:
            return this._max - this._min;
          case e.Aggregate.Sum:
            return this._sum;
          case e.Aggregate.VarPop:
            return this._cntn <= 1 ? 0 : this._sum2 / this._cntn - i * i;
          case e.Aggregate.StdPop:
            return this._cntn <= 1 ? 0 : Math.sqrt(this._sum2 / this._cntn - i * i);
          case e.Aggregate.Var:
            return this._cntn <= 1 ? 0 : (this._sum2 / this._cntn - i * i) * this._cntn / (this._cntn - 1);
          case e.Aggregate.Std:
            return this._cntn <= 1 ? 0 : Math.sqrt((this._sum2 / this._cntn - i * i) * this._cntn / (this._cntn - 1));
          case e.Aggregate.First:
            return this._first;
          case e.Aggregate.Last:
            return this._last
        }
        throw "Invalid aggregate type."
      }, t
    }();
    t._Tally = i
  }(e.olap || (e.olap = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i = function() {
      function t(e, t, i, n, r) {
        this._fields = e, this._fieldCount = t, this._valueFields = i, this._valueFieldIndex = n, this._item = r
      }
      return Object.defineProperty(t.prototype, "fields", {
        get: function() {
          return this._fields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "valueFields", {
        get: function() {
          return this._valueFields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "valueField", {
        get: function() {
          return this._valueFields[this._valueFieldIndex]
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "values", {
        get: function() {
          if (null == this._vals) {
            this._vals = new Array(this._fieldCount);
            for (var e = 0; e < this._fieldCount; e++) {
              var t = this._fields[e];
              this._vals[e] = t._getValue(this._item, !1)
            }
          }
          return this._vals
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "fieldNames", {
        get: function() {
          if (!this._names) {
            this._names = [];
            for (var e = 0; e < this.fields.length; e++) {
              var t = this._fields[e];
              this._names.push(t._getName())
            }
          }
          return this._names
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "aggregate", {
        get: function() {
          var t = this._valueFields,
            i = this._valueFieldIndex;
          return e.assert(t && i > -1 && i < t.length, "aggregate not available for this key"), t[i].aggregate
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.getValue = function(t, i) {
        if (0 == this.values.length) return e.culture.olap.PivotEngine.grandTotal;
        if (t > this.values.length - 1) return e.culture.olap.PivotEngine.subTotal;
        var n = this.values[t];
        if (i && !e.isString(n)) {
          var r = this.fields[t],
            o = r ? r.format : "";
          n = e.Globalize.format(this.values[t], o)
        }
        return n
      }, Object.defineProperty(t.prototype, "level", {
        get: function() {
          return this._fieldCount == this._fields.length ? -1 : this._fieldCount
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.compareTo = function(t) {
        var i = 0;
        if (null != t && t._fields == this._fields) {
          for (var n = this.values, r = t.values, o = Math.min(n.length, r.length), s = 0; s < o; s++) {
            var l = null != n[s] ? e.getType(n[s]) : null,
              a = n[s],
              u = r[s],
              d = this._fields[s];
            if (d.sortComparer && (i = d.sortComparer(a, u), e.isNumber(i))) {
              if (0 != i) return d.descending ? -i : i
            } else {
              if (l == e.DataType.Date) {
                var h = d.format;
                if (h && "d" != h && "D" != h) {
                  var c = d._getValue(this._item, !0),
                    p = d._getValue(t._item, !0),
                    _ = e.Globalize.parseDate(c, h),
                    f = e.Globalize.parseDate(p, h);
                  _ && f ? (a = _, u = f) : (a = c, u = p)
                }
              }
              if (!(a == u || e.DateTime.equals(a, u))) return null == a ? 1 : null == u ? -1 : (i = a < u ? -1 : 1, d.descending ? -i : i)
            }
          }
          if (n.length == r.length && 0 != (i = this._valueFieldIndex - t._valueFieldIndex)) return i;
          if (0 != (i = r.length - n.length)) return i * (this.fields.engine.totalsBeforeData ? -1 : 1)
        }
        return 0
      }, t.prototype.matchesItem = function(e) {
        for (var t = 0; t < this._vals.length; t++)
          if (this.getValue(t, !0) != this._fields[t]._getValue(e, !0)) return !1;
        return !0
      }, t.prototype.toString = function(e) {
        if (!this._key || e) {
          for (var t = "", i = 0; i < this._fieldCount; i++) {
            var n = this._fields[i];
            t += n._getName() + ":" + n._getValue(this._item, !0) + ";"
          }
          if (this._valueFields ? t += this._valueFields[this._valueFieldIndex]._getName() + ":0;" : t += "{total}", e) return t + e.toString();
          this._key = t
        }
        return this._key
      }, t._ROW_KEY_NAME = "$rowKey", t
    }();
    t._PivotKey = i
  }(e.olap || (e.olap = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(e) {
    "use strict";
    var t = function() {
      function t(t, i, n, r, o, s) {
        this._key = new e._PivotKey(t, i, n, r, o), this._nodes = {}, this._parent = s
      }
      return t.prototype.getNode = function(e, i, n, r, o) {
        for (var s = this, l = 0; l < i; l++) {
          a = e[l]._getValue(o, !0);
          (u = s._nodes[a]) || (u = new t(e, l + 1, n, r, o, s), s._nodes[a] = u), s = u
        }
        if (n && r > -1) {
          var a = n[r].header,
            u = s._nodes[a];
          u || (u = new t(e, i, n, r, o, s), s._nodes[a] = u), s = u
        }
        return s
      }, Object.defineProperty(t.prototype, "key", {
        get: function() {
          return this._key
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "parent", {
        get: function() {
          return this._parent
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "tree", {
        get: function() {
          return this._tree || (this._tree = new t(null, 0, null, -1, null)), this._tree
        },
        enumerable: !0,
        configurable: !0
      }), t
    }();
    e._PivotNode = t
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function(i) {
      function n(n) {
        var r = i.call(this) || this;
        return r._ng = e.asType(n, t.PivotEngine, !1), r
      }
      return __extends(n, i), Object.defineProperty(n.prototype, "engine", {
        get: function() {
          return this._ng
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._performSort = function(e) {
        var i = this._ng;
        if (i.sortableGroups && i._getShowRowTotals() == t.ShowTotals.Subtotals) {
          var n = 0,
            r = e.length - 1;
          0 == this._getItemLevel(e[n]) && n++, 0 == this._getItemLevel(e[r]) && r--, this._sortGroups(e, 1, n, r)
        } else this._sortData(e)
      }, n.prototype._performFilter = function(e) {
        return this._ng && 0 == this._ng.valueFields.length && 0 == this._ng.rowFields.length ? [] : this.canFilter && this._filter ? e.filter(this._filter, this) : e
      }, n.prototype._getGroupRange = function(e, t) {
        var i = this._ng,
          n = e.indexOf(t),
          r = n,
          o = this._getItemLevel(e[n]);
        if (i.totalsBeforeData)
          for (r = n; r < e.length - 1 && !((s = this._getItemLevel(e[r + 1])) > -1 && s <= o); r++);
        else
          for (n = r; n; n--) {
            var s = this._getItemLevel(e[n - 1]);
            if (s > -1 && s <= o) break
          }
        return [n, r]
      }, n.prototype._sortGroups = function(e, t, n, r) {
        for (var o = [], s = n; s <= r; s++) this._getItemLevel(e[s]) == t && o.push(e[s]);
        if (o.length) {
          i.prototype._performSort.call(this, o);
          for (var l = [], s = 0; s < o.length; s++) {
            for (var a = this._getGroupRange(e, o[s]), u = l.length, d = a[0]; d <= a[1]; d++) l.push(e[d]);
            t < this._ng.rowFields.length - 1 ? this._sortGroups(l, t + 1, n + u, l.length - 1) : this._sortSegment(l, n + u, l.length - 1)
          }
          for (s = 0; s < l.length; s++) e[n + s] = l[s]
        } else this._sortData(e)
      }, n.prototype._sortSegment = function(e, t, n) {
        var r = e.slice(t, n);
        i.prototype._performSort.call(this, r);
        for (var o = 0; o < r.length; o++) e[t + o] = r[o]
      }, n.prototype._sortData = function(e) {
        for (var t = 0; t < e.length; t++)
          if (!(this._getItemLevel(e[t]) > -1)) {
            for (var n = t; n < e.length - 1 && !(this._getItemLevel(e[n + 1]) > -1); n++);
            if (n > t) {
              var r = e.slice(t, n + 1);
              i.prototype._performSort.call(this, r);
              for (var o = 0; o < r.length; o++) e[t + o] = r[o]
            }
            t = n
          }
      }, n.prototype._getItemLevel = function(e) {
        return e[t._PivotKey._ROW_KEY_NAME].level
      }, n
    }(e.collections.CollectionView);
    t.PivotCollectionView = i
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function() {
      function i(i, n, r, o) {
        this.propertyChanged = new e.Event, this._ng = i, this._binding = new e.Binding(n), this._header = r || e.toHeaderCase(n), this._aggregate = e.Aggregate.Sum, this._showAs = t.ShowAs.NoCalculation, this._isContentHtml = !1, this._visible = !0, this._format = "", this._filter = new t.PivotFilter(this), o && e.copy(this, o)
      }
      return Object.defineProperty(i.prototype, "binding", {
        get: function() {
          return this._binding ? this._binding.path : null
        },
        set: function(t) {
          if (t != this.binding) {
            var i = this.binding,
              n = e.asString(t);
            if (this._binding = n ? new e.Binding(n) : null, !this._dataType && this._ng && this._binding) {
              var r = this._ng.collectionView;
              if (r && r.sourceCollection && r.sourceCollection.length) {
                var o = r.sourceCollection[0];
                this._dataType = e.getType(this._binding.getValue(o))
              }
            }
            var s = new e.PropertyChangedEventArgs("binding", i, t);
            this.onPropertyChanged(s)
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "getValue", {
        get: function() {
          return this._getValueFn
        },
        set: function(t) {
          this._getValueFn = e.asFunction(t, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "header", {
        get: function() {
          return this._header
        },
        set: function(t) {
          t = e.asString(t, !1);
          var i = this._ng.fields.getField(t);
          !t || i && i != this ? e.assert(!1, "field headers must be unique and non-empty.") : this._setProp("_header", e.asString(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "filter", {
        get: function() {
          return this._filter
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "aggregate", {
        get: function() {
          return this._aggregate
        },
        set: function(t) {
          this._setProp("_aggregate", e.asEnum(t, e.Aggregate))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "showAs", {
        get: function() {
          return this._showAs
        },
        set: function(i) {
          this._setProp("_showAs", e.asEnum(i, t.ShowAs))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "weightField", {
        get: function() {
          return this._weightField
        },
        set: function(t) {
          this._setProp("_weightField", e.asType(t, i, !0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "dataType", {
        get: function() {
          return this._dataType
        },
        set: function(t) {
          this._setProp("_dataType", e.asEnum(t, e.DataType))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isMeasure", {
        get: function() {
          return this._dataType == e.DataType.Number
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "subFields", {
        get: function() {
          return this._subFields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "format", {
        get: function() {
          return this._format
        },
        set: function(t) {
          this._setProp("_format", e.asString(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "width", {
        get: function() {
          return this._width
        },
        set: function(t) {
          this._setProp("_width", e.asNumber(t, !0, !0))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "wordWrap", {
        get: function() {
          return this._wordWrap
        },
        set: function(t) {
          this._setProp("_wordWrap", e.asBoolean(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "descending", {
        get: function() {
          return !!this._descending
        },
        set: function(t) {
          this._setProp("_descending", e.asBoolean(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isContentHtml", {
        get: function() {
          return this._isContentHtml
        },
        set: function(t) {
          this._setProp("_isContentHtml", e.asBoolean(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "visible", {
        get: function() {
          return this._visible
        },
        set: function(t) {
          this._setProp("_visible", e.asBoolean(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "sortComparer", {
        get: function() {
          return this._srtCmp
        },
        set: function(t) {
          t != this.sortComparer && (this._srtCmp = e.asFunction(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "engine", {
        get: function() {
          return this._ng
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "collectionView", {
        get: function() {
          return this.engine ? this.engine.collectionView : null
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isActive", {
        get: function() {
          return this._getIsActive()
        },
        set: function(e) {
          this._setIsActive(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "parentField", {
        get: function() {
          return this._parent
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "key", {
        get: function() {
          return this.header
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.onPropertyChanged = function(e) {
        this.propertyChanged.raise(this, e), this._ng._fieldPropertyChanged(this, e)
      }, i.prototype._copy = function(e, t) {
        var i = this;
        return "subFields" == e && (this._subFields ? this._subFields.splice(0, this._subFields.length) : this._subFields = [], t && t.length && t.forEach(function(e) {
          var t = i.engine._createField(e, i._autoGenerated);
          i._subFields.push(t)
        }), !0)
      }, i.prototype._getIsActive = function() {
        if (this._ng && !this._hasSubFields())
          for (var e = this._ng._viewLists, t = 0; t < e.length; t++)
            for (var i = e[t], n = 0; n < i.length; n++)
              if (i[n].binding == this.binding) return !0;
        return !1
      }, i.prototype._setIsActive = function(t) {
        if (this._ng && !this._hasSubFields()) {
          var i = this.isActive;
          if ((t = e.asBoolean(t)) != i)
            if (t) this.isMeasure ? this._ng.valueFields.push(this) : this._ng.rowFields.push(this);
            else {
              for (var n = this._ng._viewLists, r = 0; r < n.length; r++)
                for (var o = n[r], s = 0; s < o.length; s++)(a = o[s]) != this && a.parentField != this || (o.removeAt(s), s--);
              for (var l = this._ng.fields, s = l.length - 1; s >= 0; s--) {
                var a = l[s];
                a.parentField == this && (l.removeAt(s), s--)
              }
            }
        }
      }, i.prototype._hasSubFields = function() {
        return null != this._subFields && this._subFields.length > 0
      }, i.prototype._clone = function() {
        var e = new i(this._ng, this.binding);
        this._ng._copyProps(e, this, i._props), e._autoGenerated = !0, e._parent = this;
        for (var t = this.header.replace(/\d+$/, ""), n = 2;; n++) {
          var r = t + n.toString();
          if (null == this._ng.fields.getField(r)) {
            e._header = r;
            break
          }
        }
        return e
      }, i.prototype._setProp = function(t, i, n) {
        var r = this[t];
        if (i != r) {
          this[t] = i;
          var o = new e.PropertyChangedEventArgs(t.substr(1), r, i);
          this.onPropertyChanged(o)
        }
      }, i.prototype._getName = function() {
        return this.header || this.binding
      }, i.prototype._getValue = function(t, i) {
        var n, r = this._binding;
        return n = e.isFunction(this._getValueFn) ? this._getValueFn.call(this, t) : this._ng.isServer ? t[this.key] : r._key ? t[r._key] : r.getValue(t), i && "string" != typeof n ? e.Globalize.format(n, this._format) : n
      }, i.prototype._getWeight = function(t) {
        var i = this._weightField ? this._weightField._getValue(t, !1) : null;
        return e.isNumber(i) ? i : null
      }, i._props = ["dataType", "format", "width", "wordWrap", "aggregate", "showAs", "descending", "isContentHtml", "visible"], i
    }();
    t.PivotField = i;
    var n = function(t) {
      function i(i, n, r, o) {
        var s = t.call(this, i, n, r, o) || this;
        return e.assert(null != s.dimensionType, "CubePivotField objects must specify the field's dimensionType"), s
      }
      return __extends(i, t), Object.defineProperty(i.prototype, "header", {
        get: function() {
          return this._header
        },
        set: function(t) {
          this._setProp("_header", e.asString(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "dimensionType", {
        get: function() {
          return this._dimensionType
        },
        set: function(t) {
          this._setProp("_dimensionType", e.asEnum(t, r, !1))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "isMeasure", {
        get: function() {
          switch (this._dimensionType) {
            case 1:
            case 8:
              return !0
          }
          return !1
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "key", {
        get: function() {
          return this.binding
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype._clone = function() {
        throw "CubePivotField objects cannot be cloned"
      }, i
    }(i);
    t.CubePivotField = n;
    var r;
    ! function(e) {
      e[e.Dimension = 0] = "Dimension", e[e.Measure = 1] = "Measure", e[e.Kpi = 2] = "Kpi", e[e.NameSet = 3] = "NameSet", e[e.Attribute = 4] = "Attribute", e[e.Folder = 5] = "Folder", e[e.Hierarchy = 6] = "Hierarchy", e[e.Date = 7] = "Date", e[e.Currency = 8] = "Currency"
    }(r = t.DimensionType || (t.DimensionType = {}))
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function(i) {
      function n(e) {
        var t = i.call(this) || this;
        return t._ng = e, t
      }
      return __extends(n, i), Object.defineProperty(n.prototype, "maxItems", {
        get: function() {
          return this._maxItems
        },
        set: function(t) {
          this._maxItems = e.asInt(t, !0, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "engine", {
        get: function() {
          return this._ng
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.getField = function(e) {
        return this._getField(this, e)
      }, n.prototype._getField = function(e, t) {
        for (var i = 0; i < e.length; i++) {
          var n = e[i];
          if (n.key == t) return n;
          if (n._hasSubFields() && (n = this._getField(n.subFields, t))) return n
        }
        return null
      }, n.prototype.push = function() {
        for (var n = [], r = 0; r < arguments.length; r++) n[r] = arguments[r];
        for (var o = this._ng, s = 0; n && s < n.length; s++) {
          var l = n[s];
          if (e.isString(l) && (l = this == o.fields ? new t.PivotField(o, l) : o.fields.getField(l)), e.assert(l instanceof t.PivotField, "This collection must contain PivotField objects only."), l.key && this.getField(l.key)) return e.assert(!1, "PivotField keys must be unique."), -1;
          if (null != this._maxItems && this.length >= this._maxItems) break;
          i.prototype.push.call(this, l)
        }
        return this.length
      }, n
    }(e.collections.ObservableArray);
    t.PivotFieldCollection = i
  }(e.olap || (e.olap = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i = function() {
      function t(t) {
        this._fld = t;
        var i = t;
        this._valueFilter = new e.grid.filter.ValueFilter(i), this._conditionFilter = new e.grid.filter.ConditionFilter(i)
      }
      return Object.defineProperty(t.prototype, "filterType", {
        get: function() {
          return null != this._filterType ? this._filterType : this._fld.engine.defaultFilterType
        },
        set: function(t) {
          (t = e.asEnum(t, e.grid.filter.FilterType, !0)) != this._filterType && (this._filterType = t, this.clear())
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.apply = function(e) {
        return this._conditionFilter.apply(e) && this._valueFilter.apply(e)
      }, Object.defineProperty(t.prototype, "isActive", {
        get: function() {
          return this._conditionFilter.isActive || this._valueFilter.isActive
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.clear = function() {
        var t = !1;
        this._valueFilter.isActive && (this._valueFilter.clear(), t = !0), this._conditionFilter.isActive && (this._valueFilter.clear(), t = !0), t && this._fld.onPropertyChanged(new e.PropertyChangedEventArgs("filter", null, null))
      }, Object.defineProperty(t.prototype, "valueFilter", {
        get: function() {
          return this._valueFilter
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "conditionFilter", {
        get: function() {
          return this._conditionFilter
        },
        enumerable: !0,
        configurable: !0
      }), t
    }();
    t.PivotFilter = i
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function(i) {
      function n(t, n) {
        var r = i.call(this, t, null, !0) || this;
        e.assert(null != e.input, "Missing dependency: PivotFieldEditor requires wijmo.input."), r.hostElement.tabIndex = -1;
        var o = r.getTemplate();
        r.applyTemplate("wj-control wj-pivotfieldeditor", o, {
          _dBnd: "sp-bnd",
          _dHdr: "div-hdr",
          _dAgg: "div-agg",
          _dShw: "div-shw",
          _dWFl: "div-wfl",
          _dSrt: "div-srt",
          _btnFltEdt: "btn-flt-edt",
          _btnFltClr: "btn-flt-clr",
          _dFmt: "div-fmt",
          _dSmp: "div-smp",
          _btnApply: "btn-apply",
          _btnCancel: "btn-cancel",
          _gDlg: "g-dlg",
          _gHdr: "g-hdr",
          _gAgg: "g-agg",
          _gShw: "g-shw",
          _gWfl: "g-wfl",
          _gSrt: "g-srt",
          _gFlt: "g-flt",
          _gFmt: "g-fmt",
          _gSmp: "g-smp"
        }), r._pvDate = new Date;
        var s = e.culture.olap.PivotFieldEditor;
        return e.setText(r._gDlg, s.dialogHeader), e.setText(r._gHdr, s.header), e.setText(r._gAgg, s.summary), e.setText(r._gShw, s.showAs), e.setText(r._gWfl, s.weighBy), e.setText(r._gSrt, s.sort), e.setText(r._gFlt, s.filter), e.setText(r._gFmt, s.format), e.setText(r._gSmp, s.sample), e.setText(r._btnFltEdt, s.edit), e.setText(r._btnFltClr, s.clear), e.setText(r._btnApply, s.ok), e.setText(r._btnCancel, s.cancel), r._cmbHdr = new e.input.ComboBox(r._dHdr), r._cmbAgg = new e.input.ComboBox(r._dAgg), r._cmbShw = new e.input.ComboBox(r._dShw), r._cmbWFl = new e.input.ComboBox(r._dWFl), r._cmbSrt = new e.input.ComboBox(r._dSrt), r._cmbFmt = new e.input.ComboBox(r._dFmt), r._cmbSmp = new e.input.ComboBox(r._dSmp), r._initAggregateOptions(), r._initShowAsOptions(), r._initFormatOptions(), r._initSortOptions(), r._updatePreview(), r._cmbShw.textChanged.addHandler(r._updateFormat, r), r._cmbFmt.textChanged.addHandler(r._updatePreview, r), r.addEventListener(r._btnFltEdt, "click", function(e) {
          r._editFilter(), e.preventDefault()
        }), r.addEventListener(r._btnFltClr, "click", function(t) {
          r._createFilterEditor(), setTimeout(function() {
            r._eFlt.clearEditor(), r._btnFltEdt.focus(), e.enable(r._btnFltClr, !1)
          }), t.preventDefault()
        }), r.addEventListener(r._btnApply, "click", function(e) {
          r.updateField()
        }), r.initialize(n), r
      }
      return __extends(n, i), Object.defineProperty(n.prototype, "field", {
        get: function() {
          return this._fld
        },
        set: function(i) {
          i != this._fld && (this._fld = e.asType(i, t.PivotField), this.updateEditor())
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.updateEditor = function() {
        if (this._fld) {
          this._dBnd.textContent = this._fld.binding, this._cmbHdr.text = this._fld.header, this._cmbAgg.collectionView.refresh(), this._cmbAgg.selectedValue = this._fld.aggregate, this._cmbSrt.selectedValue = this._fld.descending, this._cmbShw.selectedValue = this._fld.showAs, this._initWeighByOptions(), e.enable(this._btnFltClr, this._fld.filter.isActive), this._cmbFmt.collectionView.refresh(), this._cmbFmt.selectedValue = this._fld.format, this._cmbFmt.selectedValue || (this._cmbFmt.text = this._fld.format);
          var i = this._fld instanceof t.CubePivotField;
          this._cmbAgg.isDisabled = i, this._cmbWFl.isDisabled = i
        }
      }, n.prototype.updateField = function() {
        if (this._fld) {
          var t = this._cmbHdr.text.trim();
          this._fld.header = t || e.toHeaderCase(this._fld.binding), this._fld.aggregate = this._cmbAgg.selectedValue, this._fld.showAs = this._cmbShw.selectedValue, this._fld.weightField = this._cmbWFl.selectedValue, this._fld.descending = this._cmbSrt.selectedValue, this._eFlt && this._eFlt.updateFilter(), this._fld.format = this._cmbFmt.selectedValue || this._cmbFmt.text
        }
      }, n.prototype._initAggregateOptions = function() {
        var t = this,
          i = e.culture.olap.PivotFieldEditor.aggs,
          n = e.Aggregate,
          r = [{
            key: i.sum,
            val: n.Sum,
            all: !1
          }, {
            key: i.cnt,
            val: n.Cnt,
            all: !0
          }, {
            key: i.avg,
            val: n.Avg,
            all: !1
          }, {
            key: i.max,
            val: n.Max,
            all: !0
          }, {
            key: i.min,
            val: n.Min,
            all: !0
          }, {
            key: i.rng,
            val: n.Rng,
            all: !1
          }, {
            key: i.std,
            val: n.Std,
            all: !1
          }, {
            key: i.var,
            val: n.Var,
            all: !1
          }, {
            key: i.stdp,
            val: n.StdPop,
            all: !1
          }, {
            key: i.varp,
            val: n.VarPop,
            all: !1
          }, {
            key: i.first,
            val: n.First,
            all: !0
          }, {
            key: i.last,
            val: n.Last,
            all: !0
          }];
        this._cmbAgg.itemsSource = r, this._cmbAgg.collectionView.filter = function(i) {
          if (i && i.all) return !0;
          if (t._fld) {
            var n = t._fld.dataType;
            return n == e.DataType.Number || n == e.DataType.Boolean
          }
          return !1
        }, this._cmbAgg.initialize({
          displayMemberPath: "key",
          selectedValuePath: "val"
        })
      }, n.prototype._initShowAsOptions = function() {
        var i = e.culture.olap.PivotFieldEditor.calcs,
          n = [{
            key: i.noCalc,
            val: t.ShowAs.NoCalculation
          }, {
            key: i.dRow,
            val: t.ShowAs.DiffRow
          }, {
            key: i.dRowPct,
            val: t.ShowAs.DiffRowPct
          }, {
            key: i.dCol,
            val: t.ShowAs.DiffCol
          }, {
            key: i.dColPct,
            val: t.ShowAs.DiffColPct
          }, {
            key: i.dPctGrand,
            val: t.ShowAs.PctGrand
          }, {
            key: i.dPctRow,
            val: t.ShowAs.PctRow
          }, {
            key: i.dPctCol,
            val: t.ShowAs.PctCol
          }, {
            key: i.dRunTot,
            val: t.ShowAs.RunTot
          }, {
            key: i.dRunTotPct,
            val: t.ShowAs.RunTotPct
          }];
        this._cmbShw.itemsSource = n, this._cmbShw.initialize({
          displayMemberPath: "key",
          selectedValuePath: "val"
        })
      }, n.prototype._initFormatOptions = function() {
        var t = this,
          i = e.culture.olap.PivotFieldEditor.formats,
          n = [{
            key: i.n0,
            val: "n0",
            all: !0
          }, {
            key: i.n2,
            val: "n2",
            all: !0
          }, {
            key: i.c,
            val: "c",
            all: !0
          }, {
            key: i.p0,
            val: "p0",
            all: !0
          }, {
            key: i.p2,
            val: "p2",
            all: !0
          }, {
            key: i.n2c,
            val: "n2,",
            all: !0
          }, {
            key: i.n2cc,
            val: "n2,,",
            all: !0
          }, {
            key: i.n2ccc,
            val: "n2,,,",
            all: !0
          }, {
            key: i.d,
            val: "d",
            all: !1
          }, {
            key: i.MMMMddyyyy,
            val: "MMMM dd, yyyy",
            all: !1
          }, {
            key: i.dMyy,
            val: "d/M/yy",
            all: !1
          }, {
            key: i.ddMyy,
            val: "dd/M/yy",
            all: !1
          }, {
            key: i.dMyyyy,
            val: "dd/M/yyyy",
            all: !1
          }, {
            key: i.MMMyyyy,
            val: "MMM yyyy",
            all: !1
          }, {
            key: i.MMMMyyyy,
            val: "MMMM yyyy",
            all: !1
          }, {
            key: i.yyyy,
            val: "yyyy",
            all: !1
          }, {
            key: i.yyyyQq,
            val: 'yyyy "Q"q',
            all: !1
          }, {
            key: i.FYEEEEQU,
            val: '"FY"EEEE "Q"U',
            all: !1
          }];
        this._cmbFmt.itemsSource = n, this._cmbFmt.isEditable = !0, this._cmbFmt.isRequired = !1, this._cmbFmt.collectionView.filter = function(i) {
          return !(!i || !i.all) || !!t._fld && t._fld.dataType == e.DataType.Date
        }, this._cmbFmt.initialize({
          displayMemberPath: "key",
          selectedValuePath: "val"
        })
      }, n.prototype._initWeighByOptions = function() {
        var t = [{
          key: e.culture.olap.PivotFieldEditor.none,
          val: null
        }];
        if (this._fld)
          for (var i = this._fld.engine, n = 0; n < i.fields.length; n++) {
            var r = i.fields[n];
            r != this._fld && r.dataType == e.DataType.Number && t.push({
              key: r.header,
              val: r
            })
          }
        this._cmbWFl.initialize({
          displayMemberPath: "key",
          selectedValuePath: "val",
          itemsSource: t,
          selectedValue: this._fld.weightField
        })
      }, n.prototype._initSortOptions = function() {
        var t = e.culture.olap.PivotFieldEditor.sorts,
          i = [{
            key: t.asc,
            val: !1
          }, {
            key: t.desc,
            val: !0
          }];
        this._cmbSrt.itemsSource = i, this._cmbSrt.initialize({
          displayMemberPath: "key",
          selectedValuePath: "val"
        })
      }, n.prototype._updateFormat = function() {
        switch (this._cmbShw.selectedValue) {
          case t.ShowAs.DiffRowPct:
          case t.ShowAs.DiffColPct:
            this._cmbFmt.selectedValue = "p0";
            break;
          default:
            this._cmbFmt.selectedValue = "n0"
        }
      }, n.prototype._updatePreview = function() {
        var t = this._cmbFmt.selectedValue || this._cmbFmt.text,
          i = e.Globalize.format,
          n = "";
        if (t) {
          var r = t[0].toLowerCase();
          n = "nfgxc".indexOf(r) > -1 ? i(1234.5678, t) : "p" == r ? i(.12345678, t) : i(this._pvDate, t)
        }
        this._cmbSmp.text = n
      }, n.prototype._editFilter = function() {
        this._createFilterEditor(), e.showPopup(this._dFlt, this._btnFltEdt, !1, !1, !1), e.moveFocus(this._dFlt, 0)
      }, n.prototype._createFilterEditor = function() {
        var i = this;
        this._dFlt || (this._dFlt = document.createElement("div"), this._eFlt = new t.PivotFilterEditor(this._dFlt, this._fld), e.addClass(this._dFlt, "wj-dropdown-panel"), this._eFlt.lostFocus.addHandler(function() {
          setTimeout(function() {
            var t = e.Control.getControl(i._dFlt);
            t && !t.containsFocus() && i._closeFilter()
          }, 10)
        }), this._eFlt.finishEditing.addHandler(function() {
          i._closeFilter(), e.enable(i._btnFltClr, !0)
        }))
      }, n.prototype._closeFilter = function() {
        this._dFlt && (e.hidePopup(this._dFlt, !0), this.focus())
      }, n.controlTemplate = '<div><div class="wj-dialog-header" tabindex="-1"><span wj-part="g-dlg"></span> <span wj-part="sp-bnd"></span></div><div class="wj-dialog-body"><table style="table-layout:fixed"><tr><td wj-part="g-hdr"></td><td><div wj-part="div-hdr"></div></td></tr><tr class="wj-separator"><td wj-part="g-agg"></td><td><div wj-part="div-agg"></div></td></tr><tr class="wj-separator"><td wj-part="g-shw"></td><td><div wj-part="div-shw"></div></td></tr><tr><td wj-part="g-wfl"></td><td><div wj-part="div-wfl"></div></td></tr><tr><td wj-part="g-srt"></td><td><div wj-part="div-srt"></div></td></tr><tr class="wj-separator"><td wj-part="g-flt"></td><td><div><button wj-part="btn-flt-edt" class="wj-btn"></button>&nbsp;&nbsp;<button wj-part="btn-flt-clr" class="wj-btn"></button></div></td></tr><tr class="wj-separator"><td wj-part="g-fmt"></td><td><div wj-part="div-fmt"></div></td></tr><tr><td wj-part="g-smp"></td><td><div wj-part="div-smp" readonly tabindex="-1"></div></td></tr></table></div><div class="wj-dialog-footer"><button wj-part="btn-apply" class="wj-btn wj-hide"></button>&nbsp;&nbsp;<button wj-part="btn-cancel" class="wj-btn wj-hide"></button></div></div>', n
    }(e.Control);
    t.PivotFieldEditor = i
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function(t) {
      function i(i, n, r) {
        var o = t.call(this, i) || this;
        o.finishEditing = new e.Event;
        e.assert(null != e.input, "Missing dependency: PivotFilterEditor requires wijmo.input."), o.hostElement.tabIndex = -1;
        var s = o.getTemplate();
        o.applyTemplate("wj-control wj-content wj-pivotfiltereditor", s, {
          _divType: "div-type",
          _aVal: "a-val",
          _aCnd: "a-cnd",
          _divEdtVal: "div-edt-val",
          _divEdtCnd: "div-edt-cnd",
          _btnOk: "btn-ok"
        }), e.setText(o._aVal, e.culture.FlexGridFilter.values), e.setText(o._aCnd, e.culture.FlexGridFilter.conditions), e.setText(o._btnOk, e.culture.olap.PivotFieldEditor.ok);
        var l = o._btnClicked.bind(o);
        return o.addEventListener(o._btnOk, "click", l), o.addEventListener(o._aVal, "click", l), o.addEventListener(o._aCnd, "click", l), o.addEventListener(o.hostElement, "keydown", function(t) {
          switch (t.keyCode) {
            case e.Key.Enter:
              switch (t.target.tagName) {
                case "A":
                case "BUTTON":
                  o._btnClicked(t);
                  break;
                default:
                  o.onFinishEditing(new e.CancelEventArgs)
              }
              t.preventDefault();
              break;
            case e.Key.Escape:
              o.onFinishEditing(new e.CancelEventArgs), t.preventDefault();
              break;
            case e.Key.Tab:
              e.moveFocus(o.hostElement, t.shiftKey ? -1 : 1), t.preventDefault()
          }
        }), o._fld = n, o.initialize(r), o.updateEditor(), o
      }
      return __extends(i, t), Object.defineProperty(i.prototype, "field", {
        get: function() {
          return this._fld
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(i.prototype, "filter", {
        get: function() {
          return this._fld ? this._fld.filter : null
        },
        enumerable: !0,
        configurable: !0
      }), i.prototype.updateEditor = function() {
        var t = e.grid.filter.FilterType.None;
        this.filter && (t = this.filter.conditionFilter.isActive || 0 == (this.filter.filterType & e.grid.filter.FilterType.Value) ? e.grid.filter.FilterType.Condition : e.grid.filter.FilterType.Value, this._showFilter(t)), this._edtVal && this._edtVal.updateEditor(), this._edtCnd && this._edtCnd.updateEditor()
      }, i.prototype.updateFilter = function() {
        switch (this._getFilterType()) {
          case e.grid.filter.FilterType.Value:
            this._edtVal.updateFilter(), this.filter.conditionFilter.clear();
            break;
          case e.grid.filter.FilterType.Condition:
            this._edtCnd.updateFilter(), this.filter.valueFilter.clear()
        }
        this.field.onPropertyChanged(new e.PropertyChangedEventArgs("filter", null, null))
      }, i.prototype.clearEditor = function() {
        this._edtVal && this._edtVal.clearEditor(), this._edtCnd && this._edtCnd.clearEditor()
      }, i.prototype.onFinishEditing = function(e) {
        return this.finishEditing.raise(this, e), !e.cancel
      }, i.prototype._showFilter = function(t) {
        switch (t == e.grid.filter.FilterType.Value && null == this._edtVal && (this._edtVal = new e.grid.filter.ValueFilterEditor(this._divEdtVal, this.filter.valueFilter)), t == e.grid.filter.FilterType.Condition && null == this._edtCnd && (this._edtCnd = new e.grid.filter.ConditionFilterEditor(this._divEdtCnd, this.filter.conditionFilter)), 0 != (t & this.filter.filterType) && (t == e.grid.filter.FilterType.Value ? (this._divEdtVal.style.display = "", this._divEdtCnd.style.display = "none", this._enableLink(this._aVal, !1), this._enableLink(this._aCnd, !0)) : (this._divEdtVal.style.display = "none", this._divEdtCnd.style.display = "", this._enableLink(this._aVal, !0), this._enableLink(this._aCnd, !1))), this.filter.filterType) {
          case e.grid.filter.FilterType.None:
          case e.grid.filter.FilterType.Condition:
          case e.grid.filter.FilterType.Value:
            this._divType.style.display = "none";
            break;
          default:
            this._divType.style.display = ""
        }
      }, i.prototype._enableLink = function(t, i) {
        t.style.textDecoration = i ? "" : "none", t.style.fontWeight = i ? "" : "bold", e.setAttribute(t, "href", i ? "" : null)
      }, i.prototype._getFilterType = function() {
        var t = e.grid.filter.FilterType;
        return "none" != this._divEdtVal.style.display ? t.Value : t.Condition
      }, i.prototype._btnClicked = function(t) {
        if (t.preventDefault(), t.stopPropagation(), !e.hasClass(t.target, "wj-state-disabled")) return t.target == this._aVal ? (this._showFilter(e.grid.filter.FilterType.Value), void e.moveFocus(this._edtVal.hostElement, 0)) : t.target == this._aCnd ? (this._showFilter(e.grid.filter.FilterType.Condition), void e.moveFocus(this._edtCnd.hostElement, 0)) : void this.onFinishEditing(new e.CancelEventArgs)
      }, i.controlTemplate = '<div><div wj-part="div-type" style="text-align:center;margin-bottom:12px;font-size:80%"><a wj-part="a-cnd" href="" tabindex="-1" draggable="false"></a>&nbsp;|&nbsp;<a wj-part="a-val" href="" tabindex="-1" draggable="false"></a></div><div wj-part="div-edt-val" tabindex="-1"></div><div wj-part="div-edt-cnd" tabindex="-1"></div><div style="text-align:right;margin-top:10px"><button wj-part="btn-ok" class="wj-btn"></button></div>', i
    }(e.Control);
    t.PivotFilterEditor = i
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    e._addCultureInfo("olap", {
      PivotEngine: {
        grandTotal: "Grand Total",
        subTotal: "Subtotal"
      },
      PivotPanel: {
        fields: "Choose fields to add to report:",
        drag: "Drag fields between areas below:",
        filters: "Filters",
        cols: "Columns",
        rows: "Rows",
        vals: "Values",
        defer: "Defer Updates",
        update: "Update"
      },
      PivotFieldEditor: {
        dialogHeader: "Field settings:",
        header: "Header:",
        summary: "Summary:",
        showAs: "Show As:",
        weighBy: "Weigh by:",
        sort: "Sort:",
        filter: "Filter:",
        format: "Format:",
        sample: "Sample:",
        edit: "Edit...",
        clear: "Clear",
        ok: "OK",
        cancel: "Cancel",
        none: "(none)",
        sorts: {
          asc: "Ascending",
          desc: "Descending"
        },
        aggs: {
          sum: "Sum",
          cnt: "Count",
          avg: "Average",
          max: "Max",
          min: "Min",
          rng: "Range",
          std: "StdDev",
          var: "Var",
          stdp: "StdDevPop",
          varp: "VarPop",
          first: "First",
          last: "Last"
        },
        calcs: {
          noCalc: "No calculation",
          dRow: "Difference from previous row",
          dRowPct: "% Difference from previous row",
          dCol: "Difference from previous column",
          dColPct: "% Difference from previous column",
          dPctGrand: "% of grand total",
          dPctRow: "% of row total",
          dPctCol: "% of column total",
          dRunTot: "Running total",
          dRunTotPct: "% running total"
        },
        formats: {
          n0: "Integer (n0)",
          n2: "Float (n2)",
          c: "Currency (c)",
          p0: "Percentage (p0)",
          p2: "Percentage (p2)",
          n2c: "Thousands (n2,)",
          n2cc: "Millions (n2,,)",
          n2ccc: "Billions (n2,,,)",
          d: "Date (d)",
          MMMMddyyyy: "Month Day Year (MMMM dd, yyyy)",
          dMyy: "Day Month Year (d/M/yy)",
          ddMyy: "Day Month Year (dd/M/yy)",
          dMyyyy: "Day Month Year (dd/M/yyyy)",
          MMMyyyy: "Month Year (MMM yyyy)",
          MMMMyyyy: "Month Year (MMMM yyyy)",
          yyyy: "Year (yyyy)",
          yyyyQq: 'Year Quarter (yyyy "Q"q)',
          FYEEEEQU: 'Fiscal Year Quarter ("FY"EEEE "Q"U)'
        }
      },
      _ListContextMenu: {
        up: "Move Up",
        down: "Move Down",
        first: "Move to Beginning",
        last: "Move to End",
        filter: "Move to Report Filter",
        rows: "Move to Row Labels",
        cols: "Move to Column Labels",
        vals: "Move to Values",
        remove: "Remove Field",
        edit: "Field Settings...",
        detail: "Show Detail..."
      },
      DetailDialog: {
        header: "Detail View:",
        ok: "OK",
        items: "{cnt:n0} items",
        item: "{cnt} item",
        row: "Row",
        col: "Column"
      },
      PivotChart: {
        by: "by",
        and: "and"
      },
      Slicer: {
        multiSelect: "Multi-Select",
        clearFilter: "Clear Filter"
      }
    });
    var i;
    ! function(e) {
      e[e.None = 0] = "None", e[e.GrandTotals = 1] = "GrandTotals", e[e.Subtotals = 2] = "Subtotals"
    }(i = t.ShowTotals || (t.ShowTotals = {}));
    var n;
    ! function(e) {
      e[e.NoCalculation = 0] = "NoCalculation", e[e.DiffRow = 1] = "DiffRow", e[e.DiffRowPct = 2] = "DiffRowPct", e[e.DiffCol = 3] = "DiffCol", e[e.DiffColPct = 4] = "DiffColPct", e[e.PctGrand = 5] = "PctGrand", e[e.PctRow = 6] = "PctRow", e[e.PctCol = 7] = "PctCol", e[e.RunTot = 8] = "RunTot", e[e.RunTotPct = 9] = "RunTotPct"
    }(n = t.ShowAs || (t.ShowAs = {}));
    var r = function() {
      function r(n) {
        this._autoGenFields = !0, this._allowFieldEditing = !0, this._showRowTotals = i.GrandTotals, this._showColTotals = i.GrandTotals, this._totalsBefore = !1, this._sortableGroups = !0, this._showZeros = !1, this._updating = 0, this._dirty = !1, this._cntTotal = 0, this._cntFiltered = 0, this._async = !0, this._serverParms = {
          timeout: t._ServerConnection._TIMEOUT,
          pollInterval: t._ServerConnection._POLL_INTERVAL,
          maxDetail: t._ServerConnection._MAXDETAIL
        }, this.itemsSourceChanged = new e.Event, this.viewDefinitionChanged = new e.Event, this.updatingView = new e.Event, this.updatedView = new e.Event, this.error = new e.Event, this._pivotView = new t.PivotCollectionView(this), this._fields = new t.PivotFieldCollection(this), this._rowFields = new t.PivotFieldCollection(this), this._columnFields = new t.PivotFieldCollection(this), this._valueFields = new t.PivotFieldCollection(this), this._filterFields = new t.PivotFieldCollection(this), this._viewLists = [this._rowFields, this._columnFields, this._valueFields, this._filterFields];
        var r = this._fieldListChanged.bind(this);
        this._fields.collectionChanged.addHandler(r), this._viewLists.forEach(function(e) {
          e.collectionChanged.addHandler(r)
        }), this._defaultFilterType = null, n && e.copy(this, n)
      }
      return Object.defineProperty(r.prototype, "itemsSource", {
        get: function() {
          return this._items
        },
        set: function(i) {
          var n = this;
          this._items != i && (this._cv && (this._cv.collectionChanged.removeHandler(this._cvCollectionChanged, this), this._cv = null), this._server && (this._server.clearPendingRequests(), this._server = null), this._items = i, e.isString(i) ? this._server = new t._ServerConnection(this, i) : e.isObject(i) && !e.tryCast(i, "ICollectionView") ? this._server = new t._SqlServerConnection(this, i) : this._cv = e.asCollectionView(i), null != this._cv && this._cv.collectionChanged.addHandler(this._cvCollectionChanged, this), this.deferUpdate(function() {
            n.autoGenerateFields && n._generateFields(), n._updateFieldTypes()
          }), this.onItemsSourceChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "isServer", {
        get: function() {
          return null != this._server
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "collectionView", {
        get: function() {
          return this._cv
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "pivotView", {
        get: function() {
          return this._pivotView
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "showRowTotals", {
        get: function() {
          return this._showRowTotals
        },
        set: function(t) {
          (t = e.asEnum(t, i)) != this.showRowTotals && (this._showRowTotals = t, this.onViewDefinitionChanged(), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "showColumnTotals", {
        get: function() {
          return this._showColTotals
        },
        set: function(t) {
          (t = e.asEnum(t, i)) != this.showColumnTotals && (this._showColTotals = t, this.onViewDefinitionChanged(), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "totalsBeforeData", {
        get: function() {
          return this._totalsBefore
        },
        set: function(t) {
          t != this._totalsBefore && (this._totalsBefore = e.asBoolean(t), this.onViewDefinitionChanged(), this._updatePivotView())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "sortableGroups", {
        get: function() {
          return this._sortableGroups
        },
        set: function(t) {
          t != this._sortableGroups && (this._sortableGroups = e.asBoolean(t), this.onViewDefinitionChanged())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "sortOnServer", {
        get: function() {
          return this._sortOnServer
        },
        set: function(t) {
          this._sortOnServer = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "showZeros", {
        get: function() {
          return this._showZeros
        },
        set: function(t) {
          t != this._showZeros && (this._showZeros = e.asBoolean(t), this.onViewDefinitionChanged(), this._updatePivotView())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "defaultFilterType", {
        get: function() {
          return null != this._defaultFilterType ? this._defaultFilterType : this._server ? e.grid.filter.FilterType.Condition : e.grid.filter.FilterType.Both
        },
        set: function(t) {
          this._defaultFilterType = e.asEnum(t, e.grid.filter.FilterType)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "autoGenerateFields", {
        get: function() {
          return this._autoGenFields
        },
        set: function(t) {
          this._autoGenFields = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "allowFieldEditing", {
        get: function() {
          return this._allowFieldEditing
        },
        set: function(t) {
          this._allowFieldEditing = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "fields", {
        get: function() {
          return this._fields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "rowFields", {
        get: function() {
          return this._rowFields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "columnFields", {
        get: function() {
          return this._columnFields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "filterFields", {
        get: function() {
          return this._filterFields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "valueFields", {
        get: function() {
          return this._valueFields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "viewDefinition", {
        get: function() {
          var e = this,
            t = {
              showZeros: this.showZeros,
              showColumnTotals: this.showColumnTotals,
              showRowTotals: this.showRowTotals,
              defaultFilterType: this.defaultFilterType,
              totalsBeforeData: this.totalsBeforeData,
              sortableGroups: this.sortableGroups,
              fields: [],
              rowFields: this._getFieldCollectionProxy(this.rowFields),
              columnFields: this._getFieldCollectionProxy(this.columnFields),
              filterFields: this._getFieldCollectionProxy(this.filterFields),
              valueFields: this._getFieldCollectionProxy(this.valueFields)
            };
          return this.fields.forEach(function(i) {
            var n = e._getFieldDefinition(i);
            t.fields.push(n)
          }), JSON.stringify(t)
        },
        set: function(t) {
          var i = this,
            n = JSON.parse(t);
          n && this.deferUpdate(function() {
            i._copyProps(i, n, r._props), i.fields.clear(), n.fields.forEach(function(e) {
              var t = i._getFieldFromDefinition(e);
              i.fields.push(t)
            }), n.fields.forEach(function(t, n) {
              e.isString(t.weightField) && (i.fields[n].weightField = i.fields.getField(t.weightField))
            }), i._setFieldCollectionProxy(i.rowFields, n.rowFields), i._setFieldCollectionProxy(i.columnFields, n.columnFields), i._setFieldCollectionProxy(i.filterFields, n.filterFields), i._setFieldCollectionProxy(i.valueFields, n.valueFields)
          })
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "isViewDefined", {
        get: function() {
          var e = this._valueFields.length,
            t = this._rowFields.length,
            i = this._columnFields.length;
          return this._server ? e > 0 && (t > 0 || i > 0) : e > 0 || t > 0 || i > 0
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.beginUpdate = function() {
        this.cancelPendingUpdates(), this._updating++
      }, r.prototype.endUpdate = function() {
        this._updating--, this._updating <= 0 && (this.onViewDefinitionChanged(), this.refresh())
      }, Object.defineProperty(r.prototype, "isUpdating", {
        get: function() {
          return this._updating > 0
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.deferUpdate = function(e) {
        try {
          this.beginUpdate(), e()
        } finally {
          this.endUpdate()
        }
      }, r.prototype.refresh = function(e) {
        void 0 === e && (e = !1), this.isUpdating && !e || this._updateView()
      }, r.prototype.invalidate = function() {
        var t = this;
        this._toInv && (this._toInv = clearTimeout(this._toInv)), this.isUpdating || (this._toInv = setTimeout(function() {
          t.refresh()
        }, e.Control._REFRESH_INTERVAL))
      }, Object.defineProperty(r.prototype, "async", {
        get: function() {
          return this._async
        },
        set: function(t) {
          t != this._async && (this.cancelPendingUpdates(), this._async = e.asBoolean(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "serverTimeout", {
        get: function() {
          return this._serverParms.timeout
        },
        set: function(t) {
          this._serverParms.timeout = e.asNumber(t, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "serverPollInterval", {
        get: function() {
          return this._serverParms.pollInterval
        },
        set: function(t) {
          this._serverParms.pollInterval = e.asNumber(t, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "serverMaxDetail", {
        get: function() {
          return this._serverParms.maxDetail
        },
        set: function(t) {
          this._serverParms.maxDetail = e.asNumber(t, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.cancelPendingUpdates = function() {
        this._toUpdateTallies && (clearTimeout(this._toUpdateTallies), this._toUpdateTallies = null)
      }, r.prototype.getDetail = function(e, i) {
        var n = this,
          r = e ? e[t._PivotKey._ROW_KEY_NAME] : null,
          o = this._getKey(i);
        if (this._server) return this._server.getDetail(r, o);
        var s = [];
        return this.collectionView.items.forEach(function(e) {
          !n._applyFilter(e) || null != r && !r.matchesItem(e) || null != o && !o.matchesItem(e) || s.push(e)
        }), s
      }, r.prototype.getDetailView = function(t, i) {
        var n = this.getDetail(t, i);
        return new e.collections.CollectionView(n)
      }, r.prototype.getKeys = function(e, i) {
        var n = e ? e[t._PivotKey._ROW_KEY_NAME] : null,
          r = this._getKey(i);
        return {
          rowKey: {
            fields: n ? n.fieldNames : [],
            values: n ? n.values : []
          },
          colKey: {
            fields: r ? r.fieldNames : [],
            values: r ? r.values : []
          }
        }
      }, r.prototype.editField = function(i) {
        if (this.allowFieldEditing) {
          var n = new t.PivotFieldEditor(document.createElement("div"), {
            field: i
          });
          new e.input.Popup(document.createElement("div"), {
            content: n.hostElement
          }).show(!0)
        }
      }, r.prototype.removeField = function(e) {
        for (var t = 0; t < this._viewLists.length; t++) {
          var i = this._viewLists[t],
            n = i.indexOf(e);
          if (n > -1) return void i.removeAt(n)
        }
      }, r.prototype.onItemsSourceChanged = function(e) {
        this.itemsSourceChanged.raise(this, e)
      }, r.prototype.onViewDefinitionChanged = function(e) {
        this._updating || this.viewDefinitionChanged.raise(this, e)
      }, r.prototype.onUpdatingView = function(e) {
        this.updatingView.raise(this, e)
      }, r.prototype.onUpdatedView = function(e) {
        this.updatedView.raise(this, e)
      }, r.prototype.onError = function(e) {
        return this.error.raise(this, e), !e.cancel
      }, r.prototype._copy = function(t, i) {
        var n = this;
        switch (t) {
          case "fields":
            return this.fields.clear(), this._viewLists.forEach(function(e) {
              e.clear()
            }), e.asArray(i).forEach(function(e) {
              var t = n._createField(e, !1);
              n.fields.push(t)
            }), this._updateFieldTypes(), !0;
          case "rowFields":
          case "columnFields":
          case "valueFields":
          case "filterFields":
            return this[t].clear(), e.isArray(i) || (this[t].maxItems = i.maxItems, i = i.items), e.asArray(i).forEach(function(e) {
              var i = n.fields.getField(e);
              n[t].push(i)
            }), !0
        }
        return !1
      }, r.prototype._getKey = function(e) {
        return this._keys[e]
      }, r.prototype._getRowLevel = function(i) {
        if (e.isNumber(i)) {
          var n = this._pivotView.items[i];
          i = n ? n[t._PivotKey._ROW_KEY_NAME] : null
        }
        return i ? i.level : -1
      }, r.prototype._getColLevel = function(i) {
        return e.isNumber(i) && (i = this._colBindings[i]), e.isString(i) && (i = this._getKey(i)), e.assert(null == i || i instanceof t._PivotKey, "invalid parameter in call to _getColLevel"), i ? i.level : -1
      }, r.prototype._applyFilter = function(e) {
        for (var t = this._activeFilterFields, i = 0; i < t.length; i++)
          if (!t[i].filter.apply(e)) return !1;
        return !0
      }, r.prototype._updateView = function() {
        var t = this;
        this.cancelPendingUpdates(), this._cntTotal = this._cntFiltered = 0, this._tallies = {}, this._keys = {}, this._activeFilterFields = [], this._server && this.isViewDefined ? this._server.getOutputView(function(e) {
          t.isViewDefined && (t._server.updateTallies(e), t._updatePivotView())
        }) : (this._viewLists.forEach(function(e) {
          e.forEach(function(e) {
            e.filter.isActive && t._activeFilterFields.push(e)
          })
        }), this.isViewDefined && e.hasItems(this._cv) ? (this._batchStart = Date.now(), this._updateTallies(this._cv.items, 0)) : this._updatePivotView())
      }, r.prototype._updateTallies = function(e, n) {
        var s = this,
          l = e.length,
          a = new t._PivotNode(this._rowFields, 0, null, -1, null),
          u = this.valueFields;
        0 == u.length && this.columnFields.length > 0 && (u = new t.PivotFieldCollection(this)).push(new t.PivotField(this, ""));
        for (var d = i, h = this._rowFields.length, c = this._getShowRowTotals(), p = c == d.None ? h : 0, _ = c == d.GrandTotals ? Math.max(1, h) : 1, f = this._columnFields.length, g = this._getShowColTotals(), v = g == d.None ? f : 0, y = g == d.GrandTotals ? Math.max(1, f) : 1, m = u.length, w = this, b = n; b < l; b++) {
          var F = function(i) {
            if (w._async && i - n >= r._BATCH_SIZE && Date.now() - w._batchStart > r._BATCH_DELAY) return w._toUpdateTallies = setTimeout(function() {
              s.onUpdatingView(new o(Math.round(i / e.length * 100))), s._batchStart = Date.now(), s._updateTallies(e, i)
            }, r._BATCH_TIMEOUT), {
              value: void 0
            };
            w._cntTotal++;
            var l = e[i];
            if (!w._activeFilterFields.length || w._applyFilter(l)) {
              w._cntFiltered++;
              for (var d = p; d <= h; d += _) {
                var c = a.getNode(w._rowFields, d, null, -1, l),
                  g = c.key,
                  b = g.toString(),
                  F = w._tallies[b];
                F || (w._keys[b] = g, w._tallies[b] = F = {});
                for (var C = v; C <= f; C += y)
                  for (var x = 0; x < m; x++) {
                    var P = c.tree.getNode(w._columnFields, C, u, x, l).key,
                      T = P.toString(),
                      S = F[T];
                    S || (w._keys[T] = P, S = F[T] = new t._Tally);
                    var E = u[x],
                      j = E._getValue(l, !1),
                      A = E._weightField ? E._getWeight(l) : null;
                    S.add(j, A)
                  }
              }
            }
          }(b);
          if ("object" == typeof F) return F.value
        }
        this._toUpdateTallies = null, this._updatePivotView()
      }, r.prototype._updatePivotView = function() {
        var e = this;
        this._pivotView.deferUpdate(function() {
          e.onUpdatingView(new o(100));
          var i = e._pivotView.sourceCollection;
          i.length = 0;
          var n = {};
          for (var r in e._tallies) n[r] = !0;
          var s = {};
          for (var r in e._tallies) {
            p = e._tallies[r];
            for (var l in p) s[l] = !0
          }
          for (var a = e._server && e.sortOnServer, u = e._getSortedKeys(n, a), d = e._getSortedKeys(s, a), h = 0; h < u.length; h++) {
            var c = u[h],
              p = e._tallies[c],
              _ = {};
            _[t._PivotKey._ROW_KEY_NAME] = e._getKey(c);
            for (var f = 0; f < d.length; f++) {
              var g = d[f],
                v = p[g],
                y = e._getKey(g),
                m = v ? v.getAggregate(y.aggregate) : null;
              0 != m || e._showZeros || (m = null), _[g] = m
            }
            i.push(_)
          }
          e._colBindings = d, e._updateFieldValues(i), e._pivotView.sortDescriptions.clear(), e.onUpdatedView()
        })
      }, r.prototype._getSortedKeys = function(e, t) {
        var i = this,
          n = Object.keys(e);
        return t || n.sort(function(e, t) {
          return i._keys[e].compareTo(i._keys[t])
        }), n
      }, r.prototype._updateFieldValues = function(t) {
        for (var i = this.valueFields.length, r = this, o = 0; o < i; o++) ! function(o) {
          var s = r.valueFields[o];
          switch (s.showAs) {
            case n.RunTot:
            case n.RunTotPct:
              for (h = o; h < r._colBindings.length; h += i) {
                for (f = 0; f < t.length; f++)(c = t[f])[g = r._colBindings[h]] = r._getRunningTotal(t, f, h, s.showAs);
                if (s.showAs == n.RunTotPct)
                  for (f = 0; f < t.length; f++) {
                    var l = (c = t[f])[g = r._colBindings[h]];
                    if (e.isNumber(l)) {
                      var a = r._getLastValueInRowGroup(t, f, h);
                      0 != a && (c[g] = l / a)
                    }
                  }
              }
              break;
            case n.PctGrand:
            case n.PctCol:
              var u = 0;
              if (s.showAs == n.PctGrand)
                for (h = o; h < r._colBindings.length; h += i) - 1 == r._getColLevel(h) && (u += r._getColTotal(t, h));
              for (var d = function(i) {
                s.showAs == n.PctCol && (u = r._getColTotal(t, i));
                var o = r._colBindings[i];
                t.forEach(function(t, i) {
                  var n = t[o];
                  e.isNumber(n) && (t[o] = 0 != u ? n / u : null)
                })
              }, h = o; h < r._colBindings.length; h += i) d(h);
              break;
            case n.PctRow:
              for (f = 0; f < t.length; f++) {
                for (var c = t[f], p = 0, h = o; h < r._colBindings.length; h += i) - 1 == r._getColLevel(h) && (_ = c[g = r._colBindings[h]], e.isNumber(_) && (p += _));
                for (h = o; h < r._colBindings.length; h += i) {
                  var _ = c[g = r._colBindings[h]];
                  e.isNumber(_) && (c[g] = 0 != p ? _ / p : null)
                }
              }
              break;
            case n.DiffRow:
            case n.DiffRowPct:
              for (h = o; h < r._colBindings.length; h += i)
                for (f = t.length - 1; f >= 0; f--)(c = t[f])[g = r._colBindings[h]] = r._getRowDifference(t, f, h, s.showAs);
              break;
            case n.DiffCol:
            case n.DiffColPct:
              for (var f = 0; f < t.length; f++)
                for (h = r._colBindings.length - i + o; h >= 0; h -= i) {
                  var c = t[f],
                    g = r._colBindings[h];
                  c[g] = r._getColDifference(t, f, h, s.showAs)
                }
          }
        }(o)
      }, r.prototype._getColTotal = function(t, i) {
        var n = this,
          r = this._colBindings[i],
          o = 0;
        return t.forEach(function(t, i) {
          if (-1 == n._getRowLevel(i)) {
            var s = t[r];
            e.isNumber(s) && (o += s)
          }
        }), o
      }, r.prototype._getRunningTotal = function(e, t, n, r) {
        var o = this._getRowLevel(t);
        if (0 == o) return null;
        for (var s = this._colBindings[n], l = e[t][s], a = this._getShowRowTotals(), u = this.rowFields.length - 2, d = t - 1; d >= 0; d--) {
          var h = this._getRowLevel(d);
          if (h == o) {
            if (u > -1 && o < 0 && a != i.Subtotals) {
              var c = e[t].$rowKey,
                p = e[d].$rowKey;
              if (c.values[u] != p.values[u]) return null
            }
            l += e[d][s];
            break
          }
          if (h > o) break
        }
        return l
      }, r.prototype._getLastValueInRowGroup = function(e, t, n) {
        for (var r = this._colBindings[n], o = e[t][r], s = this._getRowLevel(t), l = this.rowFields.length - 2, a = this._getShowRowTotals(), u = t + 1; u < e.length; u++) {
          var d = this._getRowLevel(u);
          if (d == s) {
            if (l > -1 && s < 0 && a != i.Subtotals) {
              var h = e[t].$rowKey,
                c = e[u].$rowKey;
              if (h.values[l] != c.values[l]) return o
            }
            o = e[u][r]
          }
          if (d > s) break
        }
        return o
      }, r.prototype._getRowDifference = function(e, t, r, o) {
        var s = this._getRowLevel(t);
        if (0 == s) return null;
        for (var l = this.rowFields.length - 2, a = this._getShowRowTotals(), u = t - 1; u >= 0; u--) {
          var d = this._getRowLevel(u);
          if (d == s) {
            if (l > -1 && s < 0 && a != i.Subtotals) {
              var h = e[t].$rowKey,
                c = e[u].$rowKey;
              if (h.values[l] != c.values[l]) return null
            }
            var p = this._colBindings[r],
              _ = e[t][p],
              f = e[u][p],
              g = _ - f;
            return o == n.DiffRowPct && (g /= f), g
          }
          if (d > s) break
        }
        return null
      }, r.prototype._getColDifference = function(e, t, r, o) {
        var s = this._getColLevel(r);
        if (0 == s) return null;
        for (var l = this.valueFields.length, a = this.columnFields.length - 2, u = this._getShowColTotals(), d = r - l; d >= 0; d -= l) {
          var h = this._getColLevel(d);
          if (h == s) {
            if (a > -1 && s < 0 && u != i.Subtotals) {
              var c = this._getKey(this._colBindings[r]),
                p = this._getKey(this._colBindings[d]);
              if (c.values[a] != p.values[a]) return null
            }
            var _ = e[t],
              f = _[this._colBindings[r]],
              g = _[this._colBindings[d]],
              v = f - g;
            return o == n.DiffColPct && (v /= g), v
          }
          if (h > s) break
        }
        return null
      }, r.prototype._getShowRowTotals = function() {
        return this._valueFields.length ? this._showRowTotals : i.None
      }, r.prototype._getShowColTotals = function() {
        return this._valueFields.length ? this._showColTotals : i.None
      }, r.prototype._generateFields = function() {
        var t = this;
        this._viewLists.forEach(function(e) {
          e.clear()
        });
        for (var i = 0; i < this.fields.length; i++)(u = this.fields[i])._autoGenerated && (this.fields.removeAt(i), i--);
        if (this._server) this._server.getFields().forEach(function(e) {
          var i = t._createField(e, !0);
          t.fields.getField(i.header) || t.fields.push(i)
        });
        else {
          var n = this.collectionView,
            r = n ? n.sourceCollection : null,
            o = r ? r[0] : null;
          if (o && this.autoGenerateFields)
            for (var s in o)
              for (var l = null, a = 0; a < r.length && a < 1e3 && null == l; a++)
                if (l = r[a][s], e.isPrimitive(l)) {
                  var u = this._createField({
                    binding: s,
                    header: e.toHeaderCase(s),
                    dataType: e.getType(l)
                  }, !0);
                  this.fields.getField(u.header) || this.fields.push(u)
                }
        }
      }, r.prototype._updateFieldTypes = function() {
        var e = this,
          t = this.collectionView,
          i = t ? t.sourceCollection : null;
        i && i.length && this.fields.forEach(function(t) {
          e._updateFieldType(t, i)
        })
      }, r.prototype._updateFieldType = function(t, i) {
        var n = this;
        if (t._hasSubFields()) t.subFields.forEach(function(e) {
          n._updateFieldType(e, i)
        });
        else if (null == t.dataType && t.binding)
          for (var r = 0; r < i.length && r < 1e3; r++) {
            var o = t._binding.getValue(i[r]);
            if (null != o) {
              t.dataType = e.isPrimitive(o) ? e.getType(o) : null;
              break
            }
          }
      }, r.prototype._createField = function(i, n) {
        var r;
        return e.isString(i) ? r = new t.PivotField(this, i) : i && (i.key && delete i.key, r = null != i.dimensionType ? new t.CubePivotField(this, i.binding, i.header, i) : new t.PivotField(this, i.binding, i.header, i), null != i.dataType && (r.dataType = i.dataType)), r._autoGenerated = n, (n || e.isString(i)) && (r.format = r.dataType == e.DataType.Date ? "d" : "n0", r.aggregate = r.dataType == e.DataType.Number ? e.Aggregate.Sum : e.Aggregate.Cnt), i && !e.isString(i) && e.copy(r, i), r
      }, r.prototype._cvCollectionChanged = function(e, t) {
        this.invalidate()
      }, r.prototype._fieldListChanged = function(t, i) {
        if (i.action == e.collections.NotifyCollectionChangedAction.Add) {
          for (var n = t, r = 0; r < n.length - 1; r++)
            if (n[r].key)
              for (var o = r + 1; o < n.length; o++) n[r].key == n[o].key && (n.removeAt(o), o--);
          if (n != this._fields)
            if (this._fields.getField(i.item.key)) {
              for (r = 0; r < this._viewLists.length; r++)
                if (this._viewLists[r] != n) {
                  var s = this._viewLists[r];
                  (l = s.indexOf(i.item)) > -1 && s.removeAt(l)
                }
            } else n.removeAt(i.index);
          if (e.isNumber(n.maxItems) && n.maxItems > -1)
            for (; n.length > n.maxItems;) {
              var l = n.length - 1;
              n[l] == i.item && l > 0 && l--, n.removeAt(l)
            }
        }
        this.onViewDefinitionChanged(), this.invalidate()
      }, r.prototype._fieldPropertyChanged = function(e, t) {
        if (this.onViewDefinitionChanged(), e.isActive) {
          var i = t.propertyName;
          "visible" != i && ("width" != i && "wordWrap" != i && "isContentHtml" != i ? "format" == i && this.valueFields.indexOf(e) > -1 ? this._pivotView.refresh() : "showAs" != i ? "descending" != i ? "aggregate" != i ? this.invalidate() : this.valueFields.indexOf(e) > -1 && !this.isUpdating && (this._server ? this._updateView() : this._updatePivotView()) : this._updatePivotView() : this.valueFields.indexOf(e) > -1 && !this.isUpdating && this._updatePivotView() : this._pivotView.refresh())
        }
      }, r.prototype._copyProps = function(e, t, i) {
        for (var n = 0; n < i.length; n++) {
          var r = i[n];
          null != t[r] && (e[r] = t[r])
        }
      }, r.prototype._getFieldFromDefinition = function(e) {
        var t = e.filter;
        e.filter && delete e.filter;
        var i = this._createField(e, !0);
        return t && (this._setFilterProxy(i, t), e.filter = t), i
      }, r.prototype._getFieldDefinition = function(e) {
        var i = this,
          n = {
            binding: e.binding,
            header: e.header,
            dataType: e.dataType,
            aggregate: e.aggregate,
            showAs: e.showAs,
            descending: e.descending,
            format: e.format,
            width: e.width,
            wordWrap: e.wordWrap,
            isContentHtml: e.isContentHtml
          };
        return e.weightField && (n.weightField = e.weightField._getName()), e.key && (n.key = e.key), e.filter.isActive && (n.filter = this._getFilterProxy(e)), e._hasSubFields() && (n.subFields = [], e.subFields.forEach(function(e) {
          n.subFields.push(i._getFieldDefinition(e))
        })), e instanceof t.CubePivotField && (n.dimensionType = e.dimensionType), n
      }, r.prototype._getFieldCollectionProxy = function(t) {
        var i = {
          items: []
        };
        e.isNumber(t.maxItems) && t.maxItems > -1 && (i.maxItems = t.maxItems);
        for (var n = 0; n < t.length; n++) {
          var r = t[n];
          i.items.push(r.key)
        }
        return i
      }, r.prototype._setFieldCollectionProxy = function(t, i) {
        t.clear(), t.maxItems = e.isNumber(i.maxItems) ? i.maxItems : null;
        for (var n = 0; n < i.items.length; n++) t.push(i.items[n])
      }, r.prototype._getFilterProxy = function(t) {
        var i = t.filter;
        if (i.conditionFilter.isActive) {
          var n = i.conditionFilter,
            r = {
              type: "condition",
              condition1: {
                operator: n.condition1.operator,
                value: n.condition1.value
              },
              and: n.and,
              condition2: {
                operator: n.condition2.operator,
                value: n.condition2.value
              }
            };
          return n.condition1.isActive || delete r.condition1, n.condition2.isActive || delete r.condition2, r
        }
        if (i.valueFilter.isActive) {
          var o = i.valueFilter;
          return {
            type: "value",
            filterText: o.filterText,
            showValues: o.showValues
          }
        }
        return e.assert(!1, "inactive filters shouldn't be persisted."), null
      }, r.prototype._setFilterProxy = function(t, i) {
        var n = t.filter;
        switch (n.clear(), i.type) {
          case "condition":
            var r = n.conditionFilter;
            if (i.condition1) {
              o = e.changeType(i.condition1.value, t.dataType, t.format);
              r.condition1.value = o || i.condition1.value, r.condition1.operator = i.condition1.operator
            }
            if (e.isBoolean(i.and) && (r.and = i.and), i.condition2) {
              var o = e.changeType(i.condition2.value, t.dataType, t.format);
              r.condition2.value = o || i.condition2.value, r.condition2.operator = i.condition2.operator
            }
            break;
          case "value":
            var s = n.valueFilter;
            s.filterText = i.filterText, s.showValues = i.showValues
        }
      }, r._BATCH_SIZE = 1e4, r._BATCH_TIMEOUT = 0, r._BATCH_DELAY = 100, r._props = ["showZeros", "showRowTotals", "showColumnTotals", "totalsBeforeData", "sortableGroups", "defaultFilterType"], r
    }();
    t.PivotEngine = r;
    var o = function(t) {
      function i(i) {
        var n = t.call(this) || this;
        return n._progress = e.asNumber(i), n
      }
      return __extends(i, t), Object.defineProperty(i.prototype, "progress", {
        get: function() {
          return this._progress
        },
        enumerable: !0,
        configurable: !0
      }), i
    }(e.EventArgs);
    t.ProgressEventArgs = o
  }(e.olap || (e.olap = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(t) {
    "use strict";
    var i = function() {
      function i(e, t) {
        this._ng = null, this._cubeName = null, this._ng = e, this._cubeName = t
      }
      return i.prototype.buildQuery = function() {
        var e = new t._TextBuilder,
          i = this.buildWhereSection(this._ng.valueFields);
        return e.append("SELECT ", this.buildAxes(), " FROM ", this.buildCubeName(), i), e.append(" CELL PROPERTIES VALUE, FORMAT_STRING"), e.toString()
      }, i.prototype.buildWhereSection = function(e) {
        var i = new t._TextBuilder;
        return 1 == e.length && (i.append(this.buildSetForMeasuresShelf(e)), i.wrap(" WHERE ", "")), i.toString()
      }, i.prototype.buildCubeName = function() {
        var e = new t._TextBuilder;
        e.append(this.buildSubcubeExpression());
        var i = e.length > 0;
        return i && e.wrap("SELECT ", " FROM "), e.append("[", this._cubeName, "]"), i && e.wrap("(", ")"), e.toString()
      }, i.prototype.buildSubcubeExpression = function() {
        var e = new t._TextBuilder,
          i = this.getMeasureFilterExpressions(this._ng.valueFields),
          n = this.buildFilterAttributeSet(this._ng.filterFields),
          r = this.buildFilterAttributeSet(this._ng.rowFields, i),
          o = this.buildFilterAttributeSet(this._ng.columnFields, 0 == r.length ? i : null);
        return e.joinItemToList(n), e.joinItemToList(r), e.joinItemToList(o), e.length > 0 && (e.wrap("(", ")"), e.append(" ON COLUMNS")), e.toString()
      }, i.prototype.buildFilterAttributeSet = function(e, i) {
        for (var n = new t._TextBuilder, r = 0; r < e.length; r++) {
          var o = e[r],
            s = this.buildFilterString(o, 0 == r ? i : null);
          n.joinItemToList(s)
        }
        return n.toString()
      }, i.prototype.buildFilterString = function(i, n) {
        var r = new t._TextBuilder;
        return i.filter.isActive ? i.filter.filterType == e.grid.filter.FilterType.Condition && r.append(this.getConditionFilterString(i, n)) : n && r.append(this.getConditionFilterString(i, n)), r.toString()
      }, i.prototype.getConditionFilterString = function(e, i) {
        var n = new t._TextBuilder,
          r = e.key + ".LEVELS(1).ALLMEMBERS",
          o = e.filter.conditionFilter.condition1,
          s = e.filter.conditionFilter.condition2;
        return o.isActive && (n.append(this.getConditionFilterExpression(e, o)), s.isActive && n.append(e.filter.conditionFilter.and ? " AND " : " OR ")), s.isActive && n.append(this.getConditionFilterExpression(e, s)), i && i.length > 0 && (n.length > 0 && n.append(" AND "), n.append(i.join(" AND "))), n.length > 0 && n.wrap("Filter(" + r + ",(", "))"), n.toString()
      }, i.prototype.getMeasureFilterExpressions = function(e) {
        for (var i = [], n = 0; n < e.length; n++) {
          var r = new t._TextBuilder,
            o = e[n],
            s = (o.dimensionType, t.DimensionType.Measure, o.filter.conditionFilter.condition1),
            l = o.filter.conditionFilter.condition2;
          s.isActive && (r.append(this.getConditionFilterExpression(o, s)), l.isActive && r.append(o.filter.conditionFilter.and ? " AND " : " OR ")), l.isActive && r.append(this.getConditionFilterExpression(o, l)), r.length > 0 && (r.wrap("(", ")"), i.push(r.toString()))
        }
        return i
      }, i.prototype.getConditionFilterExpression = function(i, n) {
        if (!n.isActive) return "";
        var r = i.dimensionType == t.DimensionType.Measure,
          o = i.dataType == e.DataType.Number,
          s = i.dataType == e.DataType.Date,
          l = o || s ? "member_value" : "member_caption",
          a = r ? i.key : i.key + ".CurrentMember." + l,
          u = s ? "CDate('" + e.Globalize.formatDate(n.value, "d") + "')" : n.value.toString(),
          d = new t._TextBuilder;
        switch (n.operator) {
          case e.grid.filter.Operator.BW:
            d.append("(Left(", a, ",", u.length.toString(), ')="', u, '")');
            break;
          case e.grid.filter.Operator.EW:
            d.append("(Right(", a, ",", u.length.toString(), ')="', u, '")');
            break;
          case e.grid.filter.Operator.EQ:
            r || o || s ? d.append("(", a, ")=", u) : d.append("(", a, ')="', u, '"');
            break;
          case e.grid.filter.Operator.NE:
            r || o || s ? d.append("(", a, ")<>", u) : d.append("(", a, ')<>"', u, '"');
            break;
          case e.grid.filter.Operator.CT:
            d.append("(InStr(1,", a, ',"', u, '")>0)');
            break;
          case e.grid.filter.Operator.NC:
            d.append("(InStr(1,", a, ',"', u, '")=0))');
            break;
          case e.grid.filter.Operator.GT:
            d.append("(", a, ")>", u);
            break;
          case e.grid.filter.Operator.LT:
            d.append("(", a, ")<", u);
            break;
          case e.grid.filter.Operator.GE:
            d.append("(", a, ")>=", u);
            break;
          case e.grid.filter.Operator.LE:
            d.append("(", a, ")<=", u)
        }
        return d.toString()
      }, i.prototype.buildAxes = function() {
        var e = new t._TextBuilder,
          i = this.buildSetForAttributesShelf(this._ng.rowFields);
        return e.joinItemToList(i), this._ng.rowFields.length > 0 && (this._ng.columnFields.length > 0 || this._ng.valueFields.length > 1 ? e.append(" ON ROWS") : e.append(" ON COLUMNS")), i = this.buildSetForAttributesColumnShelf(this._ng.columnFields), e.joinItemToList(i), this._ng.columnFields.length > 0 && e.append(" ON COLUMNS"), this._ng.valueFields.length > 1 && 0 == this._ng.columnFields.length && (i = this.buildSetForMeasuresShelf(this._ng.valueFields), e.joinItemToList(i), e.append(" ON COLUMNS")), e.toString()
      }, i.prototype.buildSetForAttributesShelf = function(e) {
        for (var i = new t._TextBuilder, n = 0; n < e.length; n++) {
          var r = e[n];
          r.dimensionType != t.DimensionType.Attribute && r.dimensionType != t.DimensionType.Hierarchy || i.joinItemToList(this.buildAttributeSetForAxis(r)), n > 0 && i.wrap("CrossJoin(", ")")
        }
        return i.wrap("NON EMPTY ", ""), i.toString()
      }, i.prototype.buildSetForAttributesColumnShelf = function(e) {
        for (var i = new t._TextBuilder, n = 0; n < e.length; n++) {
          var r = e[n];
          r.dimensionType != t.DimensionType.Attribute && r.dimensionType != t.DimensionType.Hierarchy || i.joinItemToList(this.buildAttributeSetForAxis(r))
        }
        return this._ng.valueFields.length > 1 && e.length > 0 && i.joinItemToList(this.buildSetForMeasuresShelf(this._ng.valueFields)), (this._ng.valueFields.length > 1 && e.length > 0 || e.length > 1) && i.wrap("CrossJoin(", ")"), i.wrap("NON EMPTY ", ""), i.toString()
      }, i.prototype.buildSetForMeasuresShelf = function(e) {
        for (var i = new t._TextBuilder, n = 0; n < e.length; n++) {
          var r = e[n];
          r.dimensionType != t.DimensionType.Measure && r.dimensionType != t.DimensionType.Kpi || i.joinItemToList(this.buildMeasureSetForAxis(r))
        }
        return i.wrap("{", "}"), i.toString()
      }, i.prototype.buildAttributeSetForAxis = function(e) {
        var i = new t._TextBuilder;
        return e.dimensionType == t.DimensionType.Hierarchy && (i.append(e.key), i.append(".ALLMEMBERS"), i.wrap("{", "}"), i.wrap("HIERARCHIZE(", ")")), i.toString()
      }, i.prototype.buildMeasureSetForAxis = function(e) {
        var i = new t._TextBuilder;
        return e.dimensionType != t.DimensionType.Measure && e.dimensionType != t.DimensionType.Kpi || i.append(e.key), i.toString()
      }, i
    }();
    t._MdxQueryBuilder = i
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function() {
      function i(i, n) {
        this._ng = e.asType(i, t.PivotEngine), e.assert(this._isValidUrl(n), "Invalid service Url: " + n + ")")
      }
      return i.prototype.getFields = function() {
        var t = this,
          i = null;
        return e.httpRequest(this._getUrl("Fields"), {
          async: !1,
          success: function(t) {
            i = JSON.parse(t.responseText), e.isArray(i) || console.error("Failed to get fields from server: " + t.responseText)
          },
          error: function(e) {
            t._handleError("Getting Fields", e)
          }
        }), i
      }, i.prototype.getOutputView = function(e) {
        var t = this;
        this.clearPendingRequests(), this._sendHttpRequest("Analyses", {
          method: "POST",
          data: {
            view: this._ng.viewDefinition
          },
          success: function(i) {
            var n = JSON.parse(i.responseText);
            t._token = n.token, t._start = Date.now(), t._handleResult(n.status, e)
          },
          error: function(e) {
            t._handleError("Analyses", e)
          }
        })
      }, i.prototype.getDetail = function(t, n) {
        for (var r, o = [], s = this._ng.rowFields.length, l = t ? t.values.length : 0, a = 0; a < s; a++) a < l ? o.push(i._getRequestedValue(t.values[a])) : o.push(null);
        s = this._ng.columnFields.length, l = n ? n.values.length : 0;
        for (a = 0; a < s; a++) a < l ? o.push(i._getRequestedValue(n.values[a])) : o.push(null);
        return r = new e.collections.ObservableArray, this._loadArray("Detail", r, {
          method: "POST",
          view: this._ng.viewDefinition,
          keys: o,
          max: this._ng.serverMaxDetail
        }), r
      }, i._getRequestedValue = function(t) {
        if (e.isDate(t)) {
          var i = t;
          return new Date(Date.UTC(i.getFullYear(), i.getMonth(), i.getDate(), i.getHours(), i.getMinutes(), i.getSeconds(), i.getMilliseconds()))
        }
        return t
      }, i.prototype.clearPendingRequests = function() {
        this._clearRequest(), this._clearTimeout(), this._clearToken()
      }, i.prototype.updateTallies = function(e) {
        var i = this,
          r = this._ng,
          o = r.rowFields.length,
          s = r.columnFields.length,
          l = r.valueFields.length,
          a = new t._PivotNode(r.rowFields, 0, null, -1, null),
          u = null,
          d = 0,
          h = function(e, t, h) {
            var c = i._getAggregatedFieldCount(e, r.rowFields),
              p = a.getNode(r.rowFields, o - c, null, -1, e),
              _ = p.key,
              f = _.toString(),
              g = _.toString(d > 0 ? d : null),
              v = r._tallies[g];
            v || (f !== u && (d = 0, g = f), u = f, r._keys[g] = _, r._tallies[g] = v = {}), c = i._getAggregatedFieldCount(e, r.columnFields);
            for (var y = 0; y < l; y++) {
              var m = p.tree.getNode(r.columnFields, s - c, r.valueFields, y, e).key,
                w = m.toString(),
                b = r.valueFields[y],
                F = v[w];
              if (F) return !1;
              r._keys[w] = m, (F = v[w] = new n).add(i._getFieldValue(b, e, !1))
            }
            return !0
          };
        e.forEach(function(e, t, i) {
          h(e) || (d++, h(e))
        })
      }, i.prototype._getFieldValue = function(t, i, n) {
        var r = i[t.key];
        return n && "string" != typeof r ? e.Globalize.format(r, t.format) : r
      }, i.prototype._getAggregatedFieldCount = function(e, t) {
        for (var i = t.length, n = 0, r = 0; r < i; r++) {
          var o = t[r];
          null == this._getFieldValue(o, e, !1) && n++
        }
        return n
      }, i.prototype._loadArray = function(t, i, n) {
        var r = this;
        n || (n = {}), null == n.skip && (n.skip = 0), null == n.top && (n.top = 100);
        var o = e.isNumber(n.max) ? n.max : 1e6;
        this._request = e.httpRequest(this._getUrl(t), {
          data: n,
          method: n.method || "GET",
          success: function(e) {
            var s = JSON.parse(e.responseText);
            i.deferUpdate(function() {
              s.value.forEach(function(e) {
                i.push(e)
              })
            }), s.value.length == n.top && i.length < o && (n.skip += n.top, r._loadArray(t, i, n))
          },
          error: function(e) {
            r._handleError(t, e)
          }
        })
      }, i.prototype._getUrl = function(t, i, n) {
        void 0 === i && (i = this._token);
        var r = this._ng.itemsSource.toString(),
          o = r.lastIndexOf("/");
        r.substr(0, o);
        switch (t = t.toLowerCase()) {
          case "rawdata":
          case "detail":
            return r;
          case "fields":
          case "analyses":
            return r + "/" + t;
          case "clear":
            return r + "/analyses/" + i + "/";
          case "result":
          case "status":
            return r + "/analyses/" + i + "/" + t;
          case "uniquevalues":
            return r + "/fields/" + n + "/" + t
        }
        e.assert(!1, "Unrecognized command")
      }, i.prototype._isValidUrl = function(t) {
        var i = document.createElement("a");
        return i.href = e.asString(t), i.href = i.href, i.protocol && i.hostname && i.pathname && "/" != t[t.length - 1]
      }, i.prototype._handleResult = function(e, i) {
        var n = this;
        switch (e.executingStatus.toLowerCase()) {
          case "executing":
          case "notset":
            if (Date.now() - this._start > this._ng.serverTimeout) return void this._handleError("Analyses", {
              status: 500,
              statusText: "Analysis timed out"
            });
            this._progress = e.progress, this._ng.onUpdatingView(new t.ProgressEventArgs(this._progress)), this._clearTimeout(), this._toGetStatus = setTimeout(function() {
              n._waitUntilComplete(i)
            }, this._ng.serverPollInterval);
            break;
          case "completed":
            this._progress = 100, this._ng.onUpdatingView(new t.ProgressEventArgs(this._progress)), this._getResults(i);
            break;
          case "exception":
            this._getResults(i);
            break;
          default:
            this._handleError("Analyses", {
              status: 500,
              statusText: "Unexpected result..."
            })
        }
      }, i.prototype._waitUntilComplete = function(e) {
        var t = this;
        this._sendHttpRequest("Status", {
          success: function(i) {
            var n = JSON.parse(i.responseText);
            t._handleResult(n, e)
          },
          error: function(e) {
            t._handleError("Status", e)
          }
        })
      }, i.prototype._getResults = function(t) {
        var i = this;
        this._sendHttpRequest("Result", {
          success: function(n) {
            i._clearToken();
            var r = JSON.parse(n.responseText);
            e.assert(e.isArray(r), "Result array Expected.");
            var o = [];
            i._ng._viewLists.forEach(function(t) {
              o = o.concat(t.filter(function(t) {
                return t.dataType == e.DataType.Date
              }))
            }), o.length > 0 && r.forEach(function(t) {
              o.forEach(function(i) {
                var n = i._binding,
                  r = n.getValue(t);
                e.isString(r) && n.setValue(t, new Date(r))
              })
            }), e.asFunction(t)(r)
          },
          error: function(e) {
            i._handleError("Result", e)
          }
        })
      }, i.prototype._handleError = function(t, i) {
        this.clearPendingRequests(), t = '** HttpRequest error on command "' + t + '"', this._ng.onError(new e.RequestErrorEventArgs(i, t)) && this._throwResponseError(t, i)
      }, i.prototype._throwResponseError = function(e, t) {
        e = e + "\r\n" + t.status + "\r\n";
        var i = t.responseText || "";
        throw 500 == t.status && t.responseText && (i = JSON.parse(t.responseText).ExceptionMessage), e += i || t.statusText
      }, i.prototype._sendHttpRequest = function(t, i) {
        var n = this._getUrl(t);
        this._request = e.httpRequest(n, i)
      }, i.prototype._clearToken = function() {
        this._token && (this._clearRequest(), this._clearTimeout(), this._sendHttpRequest("Clear", {
          method: "DELETE"
        }), this._token = null)
      }, i.prototype._clearRequest = function() {
        this._request && 4 != this._request.readyState && (this._request.abort(), this._request = null)
      }, i.prototype._clearTimeout = function() {
        this._toGetStatus && (clearTimeout(this._toGetStatus), this._toGetStatus = null)
      }, i._TIMEOUT = 6e4, i._POLL_INTERVAL = 500, i._MAXDETAIL = 1e3, i
    }();
    t._ServerConnection = i;
    var n = function(t) {
      function i() {
        return null !== t && t.apply(this, arguments) || this
      }
      return __extends(i, t), i.prototype.add = function(t, i) {
        e.assert(0 == this._cnt, "Server tallies have a single value."), this._aggregatedValue = t
      }, i.prototype.getAggregate = function(e) {
        return this._aggregatedValue
      }, i
    }(t._Tally)
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";

    function i(e, t) {
      var i = e.querySelector(t);
      return i ? i.innerHTML || i.textContent : null
    }

    function n(e) {
      return Number(i(e, "LNum"))
    }

    function r(e) {
      return n(e) > 0 ? i(e, "Caption") : null
    }

    function o(e, t) {
      var n = "Cell[CellOrdinal='" + t.toString() + "']",
        r = e.querySelector(n);
      if (r) {
        var o = i(r, "Value");
        if (o) return Number(o)
      }
      return null
    }

    function s(e, t) {
      if (e.subFields)
        for (var i = 0; i < e.subFields.length; i++) {
          var n = e.subFields[i];
          if (n.header == t) return n
        }
      return e
    }

    function l(e, t) {
      if (e.subFields)
        for (var i = 0; i < e.subFields.length; i++)
          if (e.subFields[i].header == t) return !0;
      return !1
    }
    var a = function(a) {
      function v(t, i) {
        var n = a.call(this, t, i.url) || this;
        return n._jsonResult = null, n._dataTypes = null, n._debug = !1, e.assert(e.isString(i.cube) && !e.isNullOrWhiteSpace(i.cube), "Cube name required."), n._cubeName = i.cube, n._url = i.url, n
      }
      return __extends(v, a), v.prototype.getFields = function() {
        this._getSession();
        return this._jsonResult = null, this._dataTypes = null, this._getProperties(this._token), this._getDimensions(this._token), this._endSession(), this._jsonResult
      }, v.prototype.getOutputView = function(i) {
        this._ng.onUpdatingView(new t.ProgressEventArgs(0));
        var n = this,
          r = new t._MdxQueryBuilder(this._ng, this._cubeName).buildQuery();
        this._jsonResult = [], this._getSession(), this._execQuery(this._token, r, function(r) {
          e.asFunction(i)(n._jsonResult), n._ng.onUpdatingView(new t.ProgressEventArgs(100))
        })
      }, v.prototype.getDetail = function(e, t) {
        throw "getDetail method not supported"
      }, v.prototype._getSession = function() {
        var t = this,
          i = this._url;
        return e.httpRequest(i, {
          async: !1,
          data: u,
          method: "POST",
          requestHeaders: {
            "Content-Type": "text/xml"
          },
          success: function(e) {
            var i = e.responseXML.getElementsByTagName("Session");
            t._token = i[0].getAttribute("SessionId")
          },
          error: function(e) {
            t._handleError("Begin Session", e)
          }
        })
      }, v.prototype._endSession = function() {
        var t = this,
          i = this._url;
        return e.httpRequest(i, {
          async: !1,
          data: d(t._token),
          method: "POST",
          requestHeaders: {
            "Content-Type": "text/xml"
          },
          error: function(e) {
            t._handleError("End Session", e)
          },
          complete: function(e) {
            t._token = void 0
          }
        })
      }, v.prototype._getProperties = function(t) {
        var n = this,
          r = this._url;
        return e.httpRequest(r, {
          async: !1,
          data: f(t, n._cubeName),
          method: "POST",
          requestHeaders: {
            "Content-Type": "text/xml"
          },
          success: function(t) {
            for (var r = {}, o = t.responseXML.getElementsByTagName("row"), s = 0; s < o.length; s++) {
              var l = i(o[s], "HIERARCHY_UNIQUE_NAME");
              switch (Number(i(o[s], "DATA_TYPE"))) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                  r[l] = e.DataType.Number;
                  break;
                case 7:
                  r[l] = e.DataType.Date
              }
            }
            n._dataTypes = r
          },
          error: function(e) {
            n._handleError("Get Properties", e)
          }
        })
      }, v.prototype._getDimensions = function(n) {
        var r = this,
          o = this._url;
        return e.httpRequest(o, {
          async: !1,
          data: h(n, r._cubeName),
          method: "POST",
          requestHeaders: {
            "Content-Type": "text/xml"
          },
          success: function(o) {
            for (var s = [], l = o.responseXML.getElementsByTagName("row"), a = 0; a < l.length; a++)
              if ("2" !== i(l[a], "DIMENSION_TYPE")) {
                var u = i(l[a], "DIMENSION_UNIQUE_NAME"),
                  d = {
                    header: i(l[a], "DIMENSION_CAPTION"),
                    dataType: e.DataType.Object,
                    dimensionType: t.DimensionType.Dimension,
                    subFields: []
                  };
                r._getSubFolders(n, u, d), s.push(d)
              } else r._getMeasures(n, s);
            var h = {
              header: "KPIs",
              dataType: e.DataType.Object,
              dimensionType: t.DimensionType.Kpi,
              subFields: []
            };
            r._getKPIs(n, h), s.push(h), r._jsonResult = s
          },
          error: function(e) {
            r._handleError("Get Dimensions", e)
          }
        })
      }, v.prototype._getSubFolders = function(n, r, o) {
        var a = this,
          u = this._url;
        e.httpRequest(u, {
          async: !1,
          data: c(n, a._cubeName, r),
          method: "POST",
          requestHeaders: {
            "Content-Type": "text/xml"
          },
          success: function(n) {
            for (var r = n.responseXML.getElementsByTagName("row"), u = 0; u < r.length; u++) {
              var d = {
                header: i(r[u], "HIERARCHY_NAME"),
                binding: i(r[u], "HIERARCHY_UNIQUE_NAME"),
                dataType: e.DataType.String,
                dimensionType: t.DimensionType.Hierarchy
              };
              a._dataTypes[d.binding] && (d.dataType = a._dataTypes[d.binding]);
              for (var h = i(r[u], "HIERARCHY_DISPLAY_FOLDER").split("\\"), c = o, p = 0; p < h.length; p++)
                if ("" != h[p]) {
                  var _ = {
                    header: h[p],
                    dataType: e.DataType.Object,
                    dimensionType: t.DimensionType.Folder,
                    subFields: []
                  };
                  l(c, h[p]) ? c = s(c, h[p]) : (c.subFields.push(_), c = _)
                }
              c.subFields.push(d)
            }
          },
          error: function(e) {
            a._handleError("Get Hierarchies", e)
          }
        })
      }, v.prototype._getMeasures = function(n, r) {
        var o = this,
          s = this._url;
        return e.httpRequest(s, {
          async: !1,
          data: p(n, o._cubeName),
          method: "POST",
          requestHeaders: {
            "Content-Type": "text/xml"
          },
          success: function(n) {
            for (var o = n.responseXML.getElementsByTagName("row"), s = {}, l = 0; l < o.length; l++) {
              var a = i(o[l], "MEASUREGROUP_NAME"),
                u = {
                  header: i(o[l], "MEASURE_CAPTION"),
                  binding: i(o[l], "MEASURE_UNIQUE_NAME"),
                  dataType: e.DataType.Number,
                  dimensionType: t.DimensionType.Measure
                };
              if (Object.keys(s).indexOf(a) < 0) {
                var d = {
                  header: a,
                  dataType: e.DataType.Number,
                  dimensionType: t.DimensionType.Measure,
                  subFields: []
                };
                d.subFields.push(u), r.push(d), s[a] = d
              } else s[a].subFields.push(u)
            }
          },
          error: function(e) {
            o._handleError("Get Measures", e)
          }
        })
      }, v.prototype._getKPIs = function(n, r) {
        var o = this,
          a = this._url;
        return e.httpRequest(a, {
          async: !1,
          data: _(n, o._cubeName),
          method: "POST",
          requestHeaders: {
            "Content-Type": "text/xml"
          },
          success: function(n) {
            for (var o = n.responseXML.getElementsByTagName("row"), a = 0; a < o.length; a++) {
              for (var u = i(o[a], "KPI_DISPLAY_FOLDER").split("\\"), d = r, h = 0; h < u.length; h++) {
                var c = {
                  header: u[h],
                  dataType: e.DataType.Object,
                  dimensionType: t.DimensionType.Folder,
                  subFields: []
                };
                l(d, u[h]) ? d = s(d, u[h]) : (d.subFields.push(c), d = c)
              }
              var p = {
                header: i(o[a], "KPI_CAPTION"),
                binding: i(o[a], "KPI_VALUE"),
                dataType: e.DataType.Number,
                dimensionType: t.DimensionType.Kpi
              };
              d.subFields.push(p)
            }
          },
          error: function(e) {
            o._handleError("Get KPIs", e)
          }
        })
      }, v.prototype._execQuery = function(t, i, n) {
        var r = this,
          o = this._url;
        return e.httpRequest(o, {
          async: !0,
          data: g(t, i),
          method: "POST",
          requestHeaders: {
            "Content-Type": "text/xml"
          },
          success: function(t) {
            var o = t.responseXML,
              s = o.querySelector("Error");
            if (s) throw "SSAS Error\r\n" + s.getAttribute("Description") + "\r\n" + i;
            r._createPivotKeys(o), e.asFunction(n)(t)
          },
          error: function(e) {
            r._handleError("Execute Query", e)
          },
          complete: function(e) {
            r._endSession()
          }
        })
      }, v.prototype._createPivotKeys = function(e) {
        var t = null,
          i = null,
          n = e.querySelector("CellData");
        this._ng.columnFields.length > 0 || this._ng.valueFields.length > 1 ? (i = e.querySelector("Axis[name='Axis0']"), this._ng.rowFields.length > 0 && (t = e.querySelector("Axis[name='Axis1']"))) : this._ng.rowFields.length > 0 && (t = e.querySelector("Axis[name='Axis0']"));
        var r = t && this._ng.rowFields.length > 0,
          o = i && this._ng.columnFields.length > 0;
        r && o ? this._createRowKeys(n, t, i) : r ? this._createRowOnlyKeys(n, t) : o && this._createColumnOnlyKeys(n, i)
      }, v.prototype._createRowKeys = function(e, t, i) {
        for (var n = 0, r = t.getElementsByTagName("Tuple"), o = i.getElementsByTagName("Tuple").length, s = 0; s < r.length; s++) {
          var l = r[s],
            a = this._validateTuple(l);
          a ? n = this._createColumnKeys(e, i, a, n) : n += o
        }
      }, v.prototype._createRowOnlyKeys = function(e, t) {
        for (var i = 0, n = t.getElementsByTagName("Tuple"), r = this._ng.valueFields.length, s = 0; s < n.length; s++) {
          var l = n[s],
            a = this._validateTuple(l);
          if (a) {
            for (var u = 0; u < this._ng.valueFields.length; u++) a[this._ng.valueFields[u].key] = o(e, i++);
            this._jsonResult.push(a)
          } else i += r
        }
      }, v.prototype._createColumnKeys = function(e, t, i, n) {
        for (var r = t.getElementsByTagName("Tuple"), s = this._ng.valueFields.length, l = 0; l < r.length; l += s) {
          var a = r[l],
            u = this._validateTuple(a);
          if (u) {
            for (var d in i) u[d] = i[d];
            for (var h = 0; h < this._ng.valueFields.length; h++) u[this._ng.valueFields[h].key] = o(e, n++);
            this._jsonResult.push(u)
          } else n += s
        }
        return n
      }, v.prototype._createColumnOnlyKeys = function(e, t) {
        for (var i = 0, n = t.getElementsByTagName("Tuple"), r = this._ng.valueFields.length, s = 0; s < n.length; s += r) {
          var l = n[s],
            a = this._validateTuple(l);
          if (a) {
            for (var u = 0; u < this._ng.valueFields.length; u++) a[this._ng.valueFields[u].key] = o(e, i++);
            this._jsonResult.push(a)
          }
        }
      }, v.prototype._validateTuple = function(e) {
        for (var t = e.getElementsByTagName("Member"), i = void 0, o = {}, s = 0; s < t.length; s++) {
          var l = t[s],
            a = n(l),
            u = l.getAttribute("Hierarchy"),
            d = r(l);
          if (0 == s) i = a;
          else if (a > i) return null;
          u.indexOf(".") >= 0 && (o[u] = d)
        }
        return o
      }, v
    }(t._ServerConnection);
    t._SqlServerConnection = a;
    var u = '\n    <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">\n        <Header>\n            <BeginSession soap:mustUnderstand="1" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns="urn:schemas-microsoft-com:xml-analysis" />\n        </Header>\n        <Body>\n            <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">\n                <Command>\n                    <Statement />\n                </Command>\n                <Properties>\n                </Properties>\n            </Execute>\n        </Body>\n    </Envelope>',
      d = function(e) {
        return '\n    <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">\n        <Header>\n            <XA:EndSession soap:mustUnderstand="1" SessionId="' + e + '" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:XA="urn:schemas-microsoft-com:xml-analysis" />\n        </Header>\n        <Body>\n            <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">\n                <Command>\n                    <Statement />\n                </Command>\n                <Properties>\n                </Properties>\n            </Execute>\n        </Body>\n    </Envelope>'
      },
      h = function(e, t) {
        return '\n    <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">\n        <Header>\n            <XA:Session soap:mustUnderstand="1" SessionId="' + e + '" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:XA="urn:schemas-microsoft-com:xml-analysis" />\n        </Header>\n        <Body>\n            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">\n                <RequestType>MDSCHEMA_DIMENSIONS</RequestType>\n                <Restrictions>\n                    <RestrictionList>\n                        <CUBE_NAME>' + t + "</CUBE_NAME>\n                    </RestrictionList>\n                </Restrictions>\n                <Properties>\n                </Properties>\n            </Discover>\n        </Body>\n    </Envelope>"
      },
      c = function(e, t, i) {
        return '\n    <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">\n        <Header>\n            <XA:Session soap:mustUnderstand="1" SessionId="' + e + '" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:XA="urn:schemas-microsoft-com:xml-analysis" />\n        </Header>\n        <Body>\n            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">\n                <RequestType>MDSCHEMA_HIERARCHIES</RequestType>\n                <Restrictions>\n                    <RestrictionList>\n                        <CUBE_NAME>' + t + "</CUBE_NAME>\n                        <DIMENSION_UNIQUE_NAME>" + i + "</DIMENSION_UNIQUE_NAME>\n                    </RestrictionList>\n                </Restrictions>\n                <Properties>\n                </Properties>\n            </Discover>\n        </Body>\n    </Envelope>"
      },
      p = function(e, t) {
        return '\n    <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">\n        <Header>\n            <XA:Session soap:mustUnderstand="1" SessionId="' + e + '" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:XA="urn:schemas-microsoft-com:xml-analysis" />\n        </Header>\n        <Body>\n            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">\n                <RequestType>MDSCHEMA_MEASURES</RequestType>\n                <Restrictions>\n                    <RestrictionList>\n                        <CUBE_NAME>' + t + "</CUBE_NAME>\n                    </RestrictionList>\n                </Restrictions>\n                <Properties>\n                </Properties>\n            </Discover>\n        </Body>\n    </Envelope>"
      },
      _ = function(e, t) {
        return '\n    <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">\n        <Header>\n            <XA:Session soap:mustUnderstand="1" SessionId="' + e + '" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:XA="urn:schemas-microsoft-com:xml-analysis" />\n        </Header>\n        <Body>\n            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">\n                <RequestType>MDSCHEMA_KPIS</RequestType>\n                <Restrictions>\n                    <RestrictionList>\n                        <CUBE_NAME>' + t + "</CUBE_NAME>\n                    </RestrictionList>\n                </Restrictions>\n                <Properties>\n                </Properties>\n            </Discover>\n        </Body>\n    </Envelope>"
      },
      f = function(e, t) {
        return '\n    <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">\n        <Header>\n            <XA:Session soap:mustUnderstand="1" SessionId="' + e + '" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:XA="urn:schemas-microsoft-com:xml-analysis" />\n        </Header>\n        <Body>\n            <Discover xmlns="urn:schemas-microsoft-com:xml-analysis">\n                <RequestType>MDSCHEMA_PROPERTIES</RequestType>\n                <Restrictions>\n                    <RestrictionList>\n                        <CUBE_NAME>' + t + "</CUBE_NAME>\n                        <PROPERTY_NAME>MEMBER_VALUE</PROPERTY_NAME>\n                    </RestrictionList>\n                </Restrictions>\n                <Properties>\n                </Properties>\n            </Discover>\n        </Body>\n    </Envelope>"
      },
      g = function(e, t) {
        return '\n    <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">\n        <Header>\n            <XA:Session soap:mustUnderstand="1" SessionId="' + e + '" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:XA="urn:schemas-microsoft-com:xml-analysis" />\n        </Header>\n        <Body>\n            <Execute xmlns="urn:schemas-microsoft-com:xml-analysis">\n                <Command>\n                    <Statement>\n                        <![CDATA[\n                        ' + t + "\n                        ]]>\n                    </Statement>\n                </Command>\n                <Properties>\n                    <PropertyList>\n                        <Content>Data</Content>\n                    </PropertyList>\n                </Properties>\n            </Execute>\n        </Body>\n    </Envelope>"
      }
  }(e.olap || (e.olap = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(e) {
  ! function(e) {
    "use strict";
    var t = function() {
      function e() {
        this._text = ""
      }
      return Object.defineProperty(e.prototype, "length", {
        get: function() {
          return this._text.length
        },
        enumerable: !0,
        configurable: !0
      }), e.prototype.toString = function() {
        return this._text
      }, e.prototype.append = function() {
        for (var e = [], t = 0; t < arguments.length; t++) e[t] = arguments[t];
        this._text = (i = this._text).concat.apply(i, e);
        var i
      }, e.prototype.joinToList = function(e) {
        for (var t = [], i = 1; i < arguments.length; i++) t[i - 1] = arguments[i];
        var n = t.join();
        this.length > 0 && n.length > 0 ? this._text = (r = this._text).concat.apply(r, [e].concat(t)) : n.length > 0 && (this._text = n);
        var r
      }, e.prototype.joinItemToList = function(e) {
        this.joinToList(",", e)
      }, e.prototype.wrap = function(e, t) {
        this.length > 0 && (this._text = e.concat(this._text, t))
      }, e.wrap = function(e, t, i) {
        return t.length > 0 ? e.concat(t, i) : ""
      }, e
    }();
    e._TextBuilder = t
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function(i) {
      function n(t) {
        var n = i.call(this, document.createElement("div"), {
          header: "Field Context Menu",
          displayMemberPath: "text",
          commandParameterPath: "parm",
          command: {
            executeCommand: function(e) {
              n._execute(e)
            },
            canExecuteCommand: function(e) {
              return n._canExecute(e)
            }
          }
        }) || this;
        return n._full = t, n.itemsSource = n._getMenuItems(t), e.addClass(n.dropDown, "context-menu wj-olap-context-menu"), n
      }
      return __extends(n, i), n.prototype.refresh = function(e) {
        void 0 === e && (e = !0), this.itemsSource = this._getMenuItems(this._full), i.prototype.refresh.call(this, e)
      }, n.prototype.attach = function(t) {
        var i = this;
        e.assert(t instanceof e.grid.FlexGrid, "Expecting a FlexGrid control...");
        var n = t.hostElement;
        n.addEventListener("contextmenu", function(e) {
          i._selectField(t, e) && (e.preventDefault(), i.owner = n, i.show(e))
        })
      }, n.prototype._selectField = function(e, i) {
        var n = e.hitTest(i);
        if (n.panel != e.cells || !n.range.isValid) return !1;
        var r = e.rows[n.row].dataItem;
        return !(r instanceof t.CubePivotField && r.subFields && r.subFields.length) && (e.select(n.range, !0), !0)
      }, n.prototype._getMenuItems = function(t) {
        var i;
        i = t ? [{
          text: '<div class="menu-icon"></div>*',
          parm: "up"
        }, {
          text: '<div class="menu-icon"></div>*',
          parm: "down"
        }, {
          text: '<div class="menu-icon"></div>*',
          parm: "first"
        }, {
          text: '<div class="menu-icon"></div>*',
          parm: "last"
        }, {
          text: '<div class="wj-separator"></div>'
        }, {
          text: '<div class="menu-icon"><span class="wj-glyph-filter"></span></div>*',
          parm: "filter"
        }, {
          text: '<div class="menu-icon">&#8801;</div>*',
          parm: "rows"
        }, {
          text: '<div class="menu-icon">&#10996;</div>*',
          parm: "cols"
        }, {
          text: '<div class="menu-icon">&#931;</div>*',
          parm: "vals"
        }, {
          text: '<div class="wj-separator"></div>'
        }, {
          text: '<div class="menu-icon menu-icon-remove">&#10006;</div>*',
          parm: "remove"
        }, {
          text: '<div class="wj-separator"></div>'
        }, {
          text: '<div class="menu-icon">&#9965;</div>*',
          parm: "edit"
        }] : [{
          text: '<div class="menu-icon"><span class="wj-glyph-filter"></span></div>*',
          parm: "filter"
        }, {
          text: '<div class="menu-icon">&#8801;</div>*',
          parm: "rows"
        }, {
          text: '<div class="menu-icon">&#10996;</div>*',
          parm: "cols"
        }, {
          text: '<div class="menu-icon">&#931;</div>*',
          parm: "vals"
        }, {
          text: '<div class="wj-separator"></div>'
        }, {
          text: '<div class="menu-icon">&#9965;</div>*',
          parm: "edit"
        }];
        for (var n = 0; n < i.length; n++) {
          var r = i[n];
          if (r.parm) {
            var o = e.culture.olap._ListContextMenu[r.parm];
            e.assert(o, "missing localized text for item " + r.parm), r.text = r.text.replace(/([^>]+$)/, o)
          }
        }
        return i
      }, n.prototype._execute = function(t) {
        var i = e.Control.getControl(this.owner),
          n = i.itemsSource,
          r = i.selection.row,
          o = i.rows[r].dataItem,
          s = o ? o.engine : null,
          l = this._getTargetList(s, t);
        switch (t) {
          case "up":
          case "first":
          case "down":
          case "last":
            if (s) {
              var a = n.indexOf(o),
                u = "up" == t ? a - 1 : "first" == t ? 0 : "down" == t ? a + 1 : "last" == t ? n.length : -1;
              s.deferUpdate(function() {
                n.removeAt(a), n.insert(u, o)
              })
            }
            break;
          case "filter":
          case "rows":
          case "cols":
          case "vals":
            l && o && l.push(o);
            break;
          case "remove":
            o && s.removeField(o);
            break;
          case "edit":
            o && s.editField(o)
        }
      }, n.prototype._canExecute = function(i) {
        var n = e.Control.getControl(this.owner);
        if (!n) return !1;
        var r = n.selection.row,
          o = r > -1 ? n.rows[r].dataItem : null,
          s = o ? o.engine : null,
          l = this._getTargetList(s, i),
          a = !!o && o instanceof t.CubePivotField,
          u = !!o && o.isMeasure;
        switch (i) {
          case "up":
          case "first":
            return r > 0;
          case "down":
          case "last":
            return r < n.rows.length - 1;
          case "filter":
          case "rows":
          case "cols":
            return !u && l && l.indexOf(o) < 0;
          case "vals":
            return (!a || u) && l && l.indexOf(o) < 0;
          case "edit":
            return s && s.allowFieldEditing;
          case "detail":
            return o && !a
        }
        return !0
      }, n.prototype._getTargetList = function(e, t) {
        if (e) switch (t) {
          case "filter":
            return e.filterFields;
          case "rows":
            return e.rowFields;
          case "cols":
            return e.columnFields;
          case "vals":
            return e.valueFields
        }
        return null
      }, n
    }(e.input.Menu);
    t._ListContextMenu = i
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function(i) {
      function n(n, r) {
        var o = i.call(this, n, null, !0) || this;
        o._showIcons = !0, o._restrictDrag = null, o.itemsSourceChanged = new e.Event, o.viewDefinitionChanged = new e.Event, o.updatingView = new e.Event, o.updatedView = new e.Event;
        var s = "Missing dependency: PivotPanel requires ";
        e.assert(null != e.input, s + "wijmo.input."), e.assert(null != e.grid && null != e.grid.filter, s + "wijmo.grid.filter.");
        var l = o.getTemplate();
        o.applyTemplate("wj-control wj-content wj-pivotpanel", l, {
          _dFields: "d-fields",
          _dFilters: "d-filters",
          _dRows: "d-rows",
          _dCols: "d-cols",
          _dVals: "d-vals",
          _dProgress: "d-prog",
          _btnUpdate: "btn-update",
          _chkDefer: "chk-defer",
          _gFlds: "g-flds",
          _gDrag: "g-drag",
          _gFlt: "g-flt",
          _gCols: "g-cols",
          _gRows: "g-rows",
          _gVals: "g-vals",
          _gDefer: "g-defer"
        }), o._globalize();
        var a = o.hostElement;
        o.addEventListener(a, "dragstart", o._dragstart.bind(o)), o.addEventListener(a, "dragover", o._dragover.bind(o)), o.addEventListener(a, "dragleave", o._dragover.bind(o)), o.addEventListener(a, "drop", o._drop.bind(o)), o.addEventListener(a, "dragend", o._dragend.bind(o)), o._lbFields = o._createFieldGrid(o._dFields), o._lbFilters = o._createFieldGrid(o._dFilters), o._lbRows = o._createFieldGrid(o._dRows), o._lbCols = o._createFieldGrid(o._dCols), o._lbVals = o._createFieldGrid(o._dVals);
        var u = o._ctxMenuShort = new t._ListContextMenu(!1);
        return u.attach(o._lbFields), (u = o._ctxMenuFull = new t._ListContextMenu(!0)).attach(o._lbFilters), u.attach(o._lbRows), u.attach(o._lbCols), u.attach(o._lbVals), o._dMarker = e.createElement('<div class="wj-marker" style="display:none">&nbsp;</div>'), o.hostElement.appendChild(o._dMarker), o.addEventListener(o._btnUpdate, "click", function(e) {
          o._ng.refresh(!0), e.preventDefault()
        }), o.addEventListener(o._chkDefer, "click", function(t) {
          e.enable(o._btnUpdate, o._chkDefer.checked), o._chkDefer.checked ? o._ng.beginUpdate() : o._ng.endUpdate()
        }), o.engine = new t.PivotEngine, o.initialize(r), o
      }
      return __extends(n, i), n.prototype._getProductInfo = function() {
        return "D6F4,PivotPanel"
      }, Object.defineProperty(n.prototype, "engine", {
        get: function() {
          return this._ng
        },
        set: function(i) {
          var n = this;
          this._ng && (this._ng.itemsSourceChanged.removeHandler(this._itemsSourceChanged), this._ng.viewDefinitionChanged.removeHandler(this._viewDefinitionChanged), this._ng.updatingView.removeHandler(this._updatingView), this._ng.updatedView.removeHandler(this._updatedView), this._ng.error.removeHandler(this._requestError)), i = e.asType(i, t.PivotEngine, !1), this._ng = i, this._ng.itemsSourceChanged.addHandler(this._itemsSourceChanged, this), this._ng.viewDefinitionChanged.addHandler(this._viewDefinitionChanged, this), this._ng.updatingView.addHandler(this._updatingView, this), this._ng.updatedView.addHandler(this._updatedView, this), this._ng.error.addHandler(this._requestError, this), this._lbFields.itemsSource = i.fields, this._lbFilters.itemsSource = i.filterFields, this._lbRows.itemsSource = i.rowFields, this._lbCols.itemsSource = i.columnFields, this._lbVals.itemsSource = i.valueFields, this._lbFields.collectionView.filter = function(e) {
            return e.visible && null == e.parentField
          }, "Filters,Rows,Cols,Vals".split(",").forEach(function(e) {
            n["_lb" + e].collectionView.filter = function(e) {
              return e.visible
            }
          })
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "itemsSource", {
        get: function() {
          return this._ng.itemsSource
        },
        set: function(e) {
          e instanceof t.PivotEngine ? this.engine = e : this._ng.itemsSource = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "collectionView", {
        get: function() {
          return this._ng.collectionView
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "pivotView", {
        get: function() {
          return this._ng.pivotView
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "autoGenerateFields", {
        get: function() {
          return this.engine.autoGenerateFields
        },
        set: function(e) {
          this._ng.autoGenerateFields = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "fields", {
        get: function() {
          return this._ng.fields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "rowFields", {
        get: function() {
          return this._ng.rowFields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "columnFields", {
        get: function() {
          return this._ng.columnFields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "valueFields", {
        get: function() {
          return this._ng.valueFields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "filterFields", {
        get: function() {
          return this._ng.filterFields
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "viewDefinition", {
        get: function() {
          return this._ng.viewDefinition
        },
        set: function(e) {
          this._ng.viewDefinition = e
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "isViewDefined", {
        get: function() {
          return this._ng.isViewDefined
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showFieldIcons", {
        get: function() {
          return this._showIcons
        },
        set: function(t) {
          t != this._showIcons && (this._showIcons = e.asBoolean(t), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "restrictDragging", {
        get: function() {
          return this._restrictDrag
        },
        set: function(t) {
          this._restrictDrag = e.asBoolean(t, !0)
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.onItemsSourceChanged = function(e) {
        this.itemsSourceChanged.raise(this, e)
      }, n.prototype.onViewDefinitionChanged = function(e) {
        this.viewDefinitionChanged.raise(this, e)
      }, n.prototype.onUpdatingView = function(e) {
        this.updatingView.raise(this, e)
      }, n.prototype.onUpdatedView = function(e) {
        this.updatedView.raise(this, e)
      }, n.prototype.refresh = function(e) {
        var t = this;
        void 0 === e && (e = !0), "Fields,Filters,Rows,Cols,Vals".split(",").forEach(function(e) {
          var i = t["_lb" + e];
          i.refresh(), i.collectionView.refresh()
        }), e && (this._globalize(), this._ctxMenuShort.refresh(), this._ctxMenuFull.refresh()), i.prototype.refresh.call(this, e)
      }, n.prototype._copy = function(e, t) {
        switch (e) {
          case "engine":
            return this.engine = t, !0
        }
        return !1
      }, n.prototype._globalize = function() {
        var t = e.culture.olap.PivotPanel;
        e.setText(this._gFlds, t.fields), e.setText(this._gDrag, t.drag), e.setText(this._gFlt, t.filters), e.setText(this._gCols, t.cols), e.setText(this._gRows, t.rows), e.setText(this._gVals, t.vals), e.setText(this._gDefer, t.defer), e.setText(this._btnUpdate, t.update)
      }, n.prototype._itemsSourceChanged = function(e, t) {
        this.onItemsSourceChanged(t)
      }, n.prototype._viewDefinitionChanged = function(e, t) {
        e.isUpdating || (this.invalidate(), this.onViewDefinitionChanged(t))
      }, n.prototype._updatingView = function(t, i) {
        var n = e.clamp(i.progress, 5, 100) % 100;
        this._dProgress.style.width = n + "%", this.onUpdatingView(i)
      }, n.prototype._updatedView = function(e, t) {
        this.onUpdatedView(t)
      }, n.prototype._requestError = function(e, t) {
        this._dProgress.style.width = "0"
      }, n.prototype._createFieldGrid = function(i) {
        var n = this,
          r = new e.grid.FlexGrid(i, {
            autoGenerateColumns: !1,
            childItemsPath: "subFields",
            columns: [{
              binding: "header",
              width: "*"
            }],
            headersVisibility: "None",
            selectionMode: "Cell",
            showAlternatingRows: !1
          });
        return r.cells.hostElement.parentElement.style.overflowX = "hidden", r.formatItem.addHandler(function(i, r) {
          var o = i.rows[r.row].dataItem;
          e.assert(o instanceof t.PivotField, "PivotField expected...");
          var s = o._hasSubFields();
          e.toggleClass(r.cell, "wj-header", s), r.cell.setAttribute("draggable", (!s).toString());
          var l = r.cell.innerHTML;
          o.filter.isActive && (l += '&nbsp;&nbsp;<span class="wj-glyph-filter"></span>'), i == n._lbVals && (l += ' <span class="wj-aggregate">(' + e.Aggregate[o.aggregate] + ")</span>"), i != n._lbFields || s || (n._showIcons && (l = '<span class="wj-glyph-' + (o.isMeasure ? "measure" : "dimension") + '"></span> ' + l), l = '<label><input type="checkbox"' + (o.isActive ? " checked" : "") + "> " + l + "</label>"), r.cell.innerHTML = l
        }), r.addEventListener(i, "click", function(e) {
          var i = e.target;
          if (i instanceof HTMLInputElement && "checkbox" == i.type) {
            var o = n._hitTestField(r, e);
            o instanceof t.PivotField && (o.isActive = i.checked)
          }
        }), r
      }, n.prototype._dragstart = function(i) {
        var n = this._getFlexGridTarget(i);
        n && (this._dragField = this._hitTestField(n, i), this._dragSource = this._dragField instanceof t.PivotField ? n.hostElement : null, this._dragSource && i.dataTransfer && (e._startDrag(i.dataTransfer, "copyMove"), i.stopPropagation()))
      }, n.prototype._dragover = function(e) {
        var i = !1,
          n = this._getFlexGridTarget(e);
        if (n && this._dragField) {
          if (this._dragSource == this._dFields && n != this._lbFields) {
            var r = n.itemsSource;
            if (null == r.maxItems || r.length < r.maxItems) {
              var o = this._dragField;
              n.itemsSource.indexOf(o) < 0 ? i = !0 : n == this._lbVals && (i = !(o instanceof t.CubePivotField))
            }
          }
          this._dragSource && this._dragSource != this._dFields && (i = !0)
        }
        if (i && this._getRestrictDrag() && this._dragSource != n.hostElement) {
          var s = this._dragField.isMeasure;
          n == this._lbVals ? i = s : n != this._lbRows && n != this._lbCols || (i = !s)
        }
        i ? (this._updateDropMarker(n, e), e.dataTransfer.dropEffect = this._dragSource == this._dFields ? "copy" : "move", e.preventDefault(), e.stopPropagation()) : this._updateDropMarker()
      }, n.prototype._drop = function(t) {
        var i = this,
          n = this._getFlexGridTarget(t);
        if (n && this._dragField) {
          var r = e.Control.getControl(this._dragSource),
            o = this._dragField;
          r == this._lbFields && n == this._lbVals && n.itemsSource.indexOf(o) > -1 && (o = o._clone(), this.engine.fields.push(o)), n == this._lbFields ? o.isActive = !1 : this._ng.deferUpdate(function() {
            var e = n.itemsSource,
              t = e.indexOf(o);
            t != i._dropIndex && (t > -1 && (e.removeAt(t), t < i._dropIndex && i._dropIndex--), e.insert(i._dropIndex, o))
          })
        }
        this._resetMouseState()
      }, n.prototype._dragend = function(e) {
        this._resetMouseState()
      }, n.prototype._hitTestField = function(e, t) {
        var i = e.hitTest(t.target);
        return i.panel == e.cells && i.range.isValid ? (e.select(i.range, !0), e.rows[i.row].dataItem) : null
      }, n.prototype._getRestrictDrag = function() {
        var e = this._restrictDrag;
        return null == e && this.fields.length && (e = this.fields[0] instanceof t.CubePivotField), e
      }, n.prototype._resetMouseState = function() {
        this._dragSource = null, this._updateDropMarker()
      }, n.prototype._getFlexGridTarget = function(t) {
        var i = e.Control.getControl(e.closest(t.target, ".wj-flexgrid"));
        return i instanceof e.grid.FlexGrid ? i : null
      }, n.prototype._updateDropMarker = function(t, i) {
        if (i) {
          var n;
          if (t.rows.length) {
            var r = t.hitTest(i),
              o = r.row;
            o > -1 ? (n = t.getCellBoundingRect(o, 0), r.point.y > n.top + n.height / 2 && (n.top += n.height, o++), this._dropIndex = o) : (o = t.viewRange.bottomRow, (n = t.getCellBoundingRect(o, 0)).top += n.height, this._dropIndex = o + 1)
          } else(n = e.Rect.fromBoundingRect(t.hostElement.getBoundingClientRect())).top += 4, this._dropIndex = 0;
          var s = this.hostElement,
            l = s.getBoundingClientRect();
          e.setCss(this._dMarker, {
            left: Math.round(n.left - l.left) + s.scrollLeft,
            top: Math.round(n.top - l.top - 2) + s.scrollTop,
            width: Math.round(n.width),
            height: 4,
            display: ""
          })
        } else this._dMarker.style.display = "none"
      }, n.controlTemplate = '<div><label wj-part="g-flds"></label><div wj-part="d-fields"></div><label wj-part="g-drag"></label><table><tr><td width="50%"><label><span class="wj-glyph wj-glyph-filter"></span> <span wj-part="g-flt"></span></label><div wj-part="d-filters"></div></td><td width= "50%" style= "border-left-style:solid"><label><span class="wj-glyph">&#10996;</span> <span wj-part="g-cols"></span></label><div wj-part="d-cols"></div></td></tr><tr style= "border-top-style:solid"><td width="50%"><label><span class="wj-glyph">&#8801;</span> <span wj-part="g-rows"></span></label><div wj-part="d-rows"></div></td><td width= "50%" style= "border-left-style:solid"><label><span class="wj-glyph">&#931;</span> <span wj-part="g-vals"></span></label><div wj-part="d-vals"></div></td></tr></table><div wj-part="d-prog" class="wj-state-selected" style="width:0px;height:3px"></div><div style="display:table"><label style="display:table-cell;vertical-align:middle"><input wj-part="chk-defer" type="checkbox"/> <span wj-part="g-defer"></span></label><button wj-part="btn-update" class="wj-btn wj-state-disabled" style="float:right;margin:6px" disabled></button></div></div>', n
    }(e.Control);
    t.PivotPanel = i
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function(i) {
      function n() {
        var t = i.call(this, document.createElement("div"), {
          header: "PivotGrid Context Menu",
          displayMemberPath: "text",
          commandParameterPath: "parm",
          command: {
            executeCommand: function(e) {
              t._execute(e)
            },
            canExecuteCommand: function(e) {
              return t._canExecute(e)
            }
          }
        }) || this;
        return t.itemsSource = t._getMenuItems(), e.addClass(t.dropDown, "context-menu wj-olap-context-menu"), t
      }
      return __extends(n, i), n.prototype.refresh = function(e) {
        void 0 === e && (e = !0), this.itemsSource = this._getMenuItems(), i.prototype.refresh.call(this, e)
      }, n.prototype.attach = function(i) {
        var n = this;
        e.assert(i instanceof t.PivotGrid, "Expecting a PivotGrid control...");
        var r = i.hostElement;
        r.addEventListener("contextmenu", function(t) {
          if (i.customContextMenu && (t.preventDefault(), n.owner = r, n._selectField(t))) {
            var o = n.dropDown;
            n.selectedIndex = -1, n.onIsDroppedDownChanging(new e.CancelEventArgs) && (e.showPopup(o, t), n.onIsDroppedDownChanged(), o.focus())
          }
        })
      }, n.prototype._selectField = function(t) {
        this._targetField = null, this._htDown = null;
        var i = e.Control.getControl(this.owner),
          n = i.engine,
          r = i.hitTest(t),
          o = e.grid.CellType;
        switch (r.cellType) {
          case o.Cell:
            i.select(r.range), this._targetField = n.valueFields[r.col % n.valueFields.length], this._htDown = r;
            break;
          case o.ColumnHeader:
            this._targetField = n.columnFields[r.row];
            break;
          case o.RowHeader:
            this._targetField = n.rowFields[r.col];
            break;
          case o.TopLeft:
            r.row == r.panel.rows.length - 1 && (this._targetField = n.rowFields[r.col])
        }
        return null != this._targetField
      }, n.prototype._getMenuItems = function() {
        for (var t = [{
          text: '<div class="menu-icon menu-icon-remove">&#10006;</div>Remove Field',
          parm: "remove"
        }, {
          text: '<div class="menu-icon">&#9965;</div>Field Settings...',
          parm: "edit"
        }, {
          text: '<div class="wj-separator"></div>'
        }, {
          text: '<div class="menu-icon">&#8981;</div>Show Detail...',
          parm: "detail"
        }], i = 0; i < t.length; i++) {
          var n = t[i];
          if (n.parm) {
            var r = e.culture.olap._ListContextMenu[n.parm];
            e.assert(r, "missing localized text for item " + n.parm), n.text = n.text.replace(/([^>]+$)/, r)
          }
        }
        return t
      }, n.prototype._execute = function(t) {
        var i = e.Control.getControl(this.owner),
          n = this._targetField,
          r = this._htDown;
        switch (t) {
          case "remove":
            i.engine.removeField(n);
            break;
          case "edit":
            i.engine.editField(n);
            break;
          case "detail":
            i.showDetail(r.row, r.col)
        }
      }, n.prototype._canExecute = function(i) {
        var n = this._targetField,
          r = e.Control.getControl(this.owner),
          o = r ? r.engine : null;
        switch (i) {
          case "remove":
            return null != n;
          case "edit":
            return null != n && o && o.allowFieldEditing;
          case "detail":
            return null != this._htDown && o.valueFields.length && null != n && !(n instanceof t.CubePivotField)
        }
        return !0
      }, n
    }(e.input.Menu);
    t._GridContextMenu = i
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function(i) {
      function n() {
        return null !== i && i.apply(this, arguments) || this
      }
      return __extends(n, i), n.prototype.getMergedRange = function(n, r, o, s) {
        void 0 === s && (s = !0);
        var l = n.grid.collectionView;
        if (this._ng = l instanceof t.PivotCollectionView ? l.engine : null, !this._ng) return i.prototype.getMergedRange.call(this, n, r, o, s);
        if (r < 0 || r >= n.rows.length || o < 0 || o >= n.columns.length) return null;
        switch (n.cellType) {
          case e.grid.CellType.TopLeft:
            return this._getMergedTopLeftRange(n, r, o);
          case e.grid.CellType.RowHeader:
            return this._getMergedRowHeaderRange(n, r, o, s ? n.viewRange : null);
          case e.grid.CellType.ColumnHeader:
            return this._getMergedColumnHeaderRange(n, r, o, s ? n.viewRange : null)
        }
        return null
      }, n.prototype._getMergedTopLeftRange = function(t, i, n) {
        for (var r = new e.grid.CellRange(i, n); r.col > 0 && !t.getCellData(i, r.col, !0);) r.col--;
        for (; r.col2 < t.columns.length - 1 && !t.getCellData(i, r.col2 + 1, !0);) r.col2++;
        return r
      }, n.prototype._getMergedRowHeaderRange = function(t, i, n, r) {
        var o = this._ng._getRowLevel(i);
        if (o > -1 && n >= o) {
          var s = t.getCellData(i, n, !1),
            l = void 0,
            a = void 0,
            u = r ? r.col : 0,
            d = r ? r.col2 : t.columns.length - 1;
          for (l = n; l > u && t.getCellData(i, l - 1, !1) == s; l--);
          for (a = n; a < d && t.getCellData(i, a + 1, !1) == s; a++);
          return l != a ? new e.grid.CellRange(i, l, i, a) : null
        }
        var h, c, p = r ? r.row : 0,
          _ = r ? r.row2 : t.rows.length - 1;
        for (h = i; h > p && this._sameColumnValues(t, i, h - 1, n); h--);
        for (c = i; c < _ && this._sameColumnValues(t, i, c + 1, n); c++);
        return h != c ? new e.grid.CellRange(h, n, c, n) : null
      }, n.prototype._sameColumnValues = function(e, t, i, n) {
        for (; n >= 0; n--)
          if (e.getCellData(t, n, !1) != e.getCellData(i, n, !1)) return !1;
        return !0
      }, n.prototype._getMergedColumnHeaderRange = function(t, i, n, r) {
        var o = this._ng._getKey(t.columns[n].binding),
          s = t.getCellData(i, n, !1),
          l = this._ng._getColLevel(o);
        if (l > -1 && i >= l) {
          var a = void 0,
            u = void 0,
            d = r ? r.row : 0,
            h = r ? r.row2 : t.rows.length - 1;
          for (a = i; a > d && t.getCellData(a - 1, n, !1) == s; a--);
          for (u = i; u < h && t.getCellData(u + 1, n, !1) == s; u++);
          if (a != u) return new e.grid.CellRange(a, n, u, n)
        }
        var c, p, _ = r ? r.col : 0,
          f = r ? r.col2 : t.columns.length - 1;
        for (c = n; c > _ && this._sameRowValues(t, i, n, c - 1); c--);
        for (p = n; p < f && this._sameRowValues(t, i, n, p + 1); p++);
        return c != p ? new e.grid.CellRange(i, c, i, p) : null
      }, n.prototype._sameRowValues = function(e, t, i, n) {
        for (; t >= 0; t--)
          if (e.getCellData(t, i, !1) != e.getCellData(t, n, !1)) return !1;
        return !0
      }, n
    }(e.grid.MergeManager);
    t._PivotMergeManager = i
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function(i) {
      function n(n, r) {
        var o = i.call(this, n) || this;
        return o._showDetailOnDoubleClick = !0, o._collapsibleSubtotals = !0, o._customCtxMenu = !0, o._showRowFldSort = !1, o._showRowFldHdrs = !0, o._showColFldHdrs = !0, o._centerVert = !0, o._collapsedKeys = {}, e.addClass(o.hostElement, "wj-pivotgrid"), o.isReadOnly = !0, o.deferResizing = !0, o.showAlternatingRows = !1, o.autoGenerateColumns = !1, o.allowDragging = e.grid.AllowDragging.None, o.mergeManager = new t._PivotMergeManager(o), o.customContextMenu = !0, o.initialize(r), o.formatItem.addHandler(o._formatItem, o), o.addEventListener(o.hostElement, "mousedown", o._mousedown.bind(o), !0), o.addEventListener(o.hostElement, "mouseup", o._mouseup.bind(o), !0), o.addEventListener(o.hostElement, "dblclick", o._dblclick.bind(o), !0), o._ctxMenu = new t._GridContextMenu, o._ctxMenu.attach(o), o
      }
      return __extends(n, i), n.prototype._getProductInfo = function() {
        return "D6F4,PivotGrid"
      }, Object.defineProperty(n.prototype, "engine", {
        get: function() {
          return this._ng
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showDetailOnDoubleClick", {
        get: function() {
          return this._showDetailOnDoubleClick
        },
        set: function(t) {
          this._showDetailOnDoubleClick = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "detailDialog", {
        get: function() {
          return this._dlgDetail || (this._dlgDetail = new t.DetailDialog(document.createElement("div"))), this._dlgDetail
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showRowFieldHeaders", {
        get: function() {
          return this._showRowFldHdrs
        },
        set: function(t) {
          t != this._showRowFldHdrs && (this._showRowFldHdrs = e.asBoolean(t), this._updateFixedContent())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showColumnFieldHeaders", {
        get: function() {
          return this._showColFldHdrs
        },
        set: function(t) {
          t != this._showColFldHdrs && (this._showColFldHdrs = e.asBoolean(t), this._updateFixedContent())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showRowFieldSort", {
        get: function() {
          return this._showRowFldSort
        },
        set: function(t) {
          t != this._showRowFldSort && (this._showRowFldSort = e.asBoolean(t), this._updateFixedContent())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "customContextMenu", {
        get: function() {
          return this._customCtxMenu
        },
        set: function(t) {
          this._customCtxMenu = e.asBoolean(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "collapsibleSubtotals", {
        get: function() {
          return this._collapsibleSubtotals
        },
        set: function(t) {
          t != this._collapsibleSubtotals && (this._collapsibleSubtotals = e.asBoolean(t), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "centerHeadersVertically", {
        get: function() {
          return this._centerVert
        },
        set: function(t) {
          t != this._centerVert && (this._centerVert = e.asBoolean(t), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.getDetail = function(t, i) {
        var n = this.rows[e.asInt(t)].dataItem,
          r = this.columns[e.asInt(i)].binding;
        return this._ng.getDetail(n, r)
      }, n.prototype.getKeys = function(t, i) {
        var n = this.rows[e.asInt(t)],
          r = this.columns[e.asInt(i)],
          o = n ? n.dataItem : null,
          s = r ? r.binding : null;
        return this._ng.getKeys(o, s)
      }, n.prototype.getDetailView = function(t, i) {
        var n = this.rows[e.asInt(t)].dataItem,
          r = this.columns[e.asInt(i)].binding;
        return this._ng.getDetailView(n, r)
      }, n.prototype.showDetail = function(t, i) {
        if (this._ng.valueFields.length) {
          var n = this.detailDialog;
          n.showDetail(this, new e.grid.CellRange(t, i)), n.show(!0)
        }
      }, n.prototype.collapseRowsToLevel = function(e) {
        this._collapseRowsToLevel(e)
      }, n.prototype.collapseColumnsToLevel = function(e) {
        this._collapseColsToLevel(e)
      }, n.prototype._getQuickAutoSize = function() {
        return e.isBoolean(this.quickAutoSize) ? this.quickAutoSize : this.formatItem.handlerCount <= 1 && null == this.itemFormatter
      }, n.prototype._bindGrid = function(n) {
        var r = this;
        this.deferUpdate(function() {
          var o = r.preserveOutlineState,
            s = r._collapsedKeys,
            l = r.rows,
            a = r.columns;
          if (o || (s = r._collapsedKeys = {}), i.prototype._bindGrid.call(r, n), r._ng && o && !e.isEmpty(s)) {
            for (var u = r._ng.totalsBeforeData, d = u ? l.length - 1 : 0, h = u ? -1 : l.length, c = u ? -1 : 1, p = d; p != h; p += c) {
              var _ = l[p].dataItem;
              (g = _ ? _[t._PivotKey._ROW_KEY_NAME] : null) && g.level > 0 && s[g.toString()] && r._setRowCollapsed(new e.grid.CellRange(p, g.level - 1), !0)
            }
            d = u ? a.length - 1 : 0, h = u ? -1 : a.length, c = u ? -1 : 1;
            for (p = d; p != h; p += c) {
              var f = a[p].binding,
                g = r._ng._getKey(f);
              g && g.level > 0 && s[g.toString()] && r._setColCollapsed(new e.grid.CellRange(g.level - 1, p), !0)
            }
          }
        })
      }, n.prototype._getCollectionView = function(i) {
        return i instanceof t.PivotPanel ? i = i.engine.pivotView : i instanceof t.PivotEngine && (i = i.pivotView), e.asCollectionView(i)
      }, n.prototype.refresh = function(e) {
        void 0 === e && (e = !0), this._ctxMenu.refresh(), i.prototype.refresh.call(this, e)
      }, n.prototype.onItemsSourceChanged = function(e) {
        this._ng && (this._ng.updatedView.removeHandler(this._updatedView, this), this._ng.viewDefinitionChanged.removeHandler(this._viewDefinitionChanged, this)), this._collapsedKeys = {};
        var n = this.collectionView;
        this._ng = n instanceof t.PivotCollectionView ? n.engine : null, this._ng && (this._ng.updatedView.addHandler(this._updatedView, this), this._ng.viewDefinitionChanged.addHandler(this._viewDefinitionChanged, this)), this._updatedView(), this._bindGrid(!0), i.prototype.onItemsSourceChanged.call(this, e)
      }, n.prototype.onResizedColumn = function(e) {
        var t = this._ng;
        if (t) {
          if (e.panel.columns == this.rowHeaders.columns) {
            var n = t.rowFields;
            e.col < n.length && ((r = n[e.col]).width = e.panel.columns[e.col].renderWidth)
          }
          if (e.panel.columns == this.columnHeaders.columns && (n = t.valueFields).length > 0) {
            var r = n[e.col % n.length];
            r.width = e.panel.columns[e.col].renderWidth
          }
        }
        i.prototype.onResizedColumn.call(this, e)
      }, n.prototype.onSortingColumn = function(e) {
        var t = this._ng;
        return (!t || !t.isUpdating) && i.prototype.onSortingColumn.call(this, e)
      }, n.prototype.onDraggingColumn = function(e) {
        var t = this._ng;
        return (!t || !t.isUpdating) && i.prototype.onDraggingColumn.call(this, e)
      }, n.prototype.onDraggedColumn = function(e) {
        var t = this._ng;
        if (t && e.panel.columns == this.topLeftCells.columns) {
          var n = t.rowFields,
            r = n[e.data.col];
          t.deferUpdate(function() {
            n.removeAt(e.data.col), n.insert(e.col, r)
          })
        }
        i.prototype.onDraggedColumn.call(this, e)
      }, n.prototype._updatedView = function() {
        this._updateFixedCounts(), this.columns.clear(), this.rows.clear()
      }, n.prototype._viewDefinitionChanged = function() {
        this._collapsedKeys = {}
      }, n.prototype.onLoadedRows = function(n) {
        if (0 == this.columns.length) {
          var r = this.collectionView,
            o = r ? r.sourceCollection : null;
          if (o && o.length) {
            var s = o[0];
            for (var l in s)
              if (l != t._PivotKey._ROW_KEY_NAME) {
                var a = new e.grid.Column({
                  binding: l,
                  dataType: null != s[l] ? e.getType(s[l]) : e.DataType.Number
                });
                this.columns.push(a)
              }
          }
        }
        this._updateFixedContent(), i.prototype.onLoadedRows.call(this, n)
      }, n.prototype._updateFixedCounts = function() {
        var e, t = this._ng,
          i = t && t.isViewDefined;
        e = Math.max(1, i ? t.rowFields.length : 1), this._setLength(this.topLeftCells.columns, e), e = Math.max(1, i ? t.columnFields.length : 1), t && t.columnFields.length && t.valueFields.length > 1 && e++, this._setLength(this.topLeftCells.rows, e)
      }, n.prototype._setLength = function(t, i) {
        for (; t.length < i;) t.push(t instanceof e.grid.ColumnCollection ? new e.grid.Column : new e.grid.Row);
        for (; t.length > i;) t.removeAt(t.length - 1)
      }, n.prototype._updateFixedContent = function() {
        var i = this._ng;
        if (i && i.isViewDefined) {
          for (var n = this.topLeftCells, r = 0; r < n.rows.length; r++)
            for (h = 0; h < n.columns.length; h++) {
              a = "";
              this.showRowFieldHeaders && h < i.rowFields.length && r == n.rows.length - 1 && (a = i.rowFields[h].header), this.showColumnFieldHeaders && !a && r < i.columnFields.length && 0 == h && (a = i.columnFields[r].header + ":"), n.setCellData(r, h, a, !1, !1)
            }
          n = this.rowHeaders;
          for (r = 0; r < n.rows.length; r++) {
            o = n.rows[r].dataItem[t._PivotKey._ROW_KEY_NAME];
            e.assert(o instanceof t._PivotKey, "missing PivotKey for row...");
            for (h = 0; h < n.columns.length; h++) {
              a = o.getValue(h, !0);
              n.setCellData(r, h, a, !1, !1)
            }
          }
          n = this.columnHeaders;
          for (h = 0; h < n.columns.length; h++) {
            var o = i._getKey(n.columns[h].binding),
              s = i.valueFields,
              l = s.length > 1 || 0 == s.length || o.level > -1;
            e.assert(o instanceof t._PivotKey, "missing PivotKey for column...");
            for (r = 0; r < n.rows.length; r++) {
              var a = l && r == n.rows.length - 1 && s.length ? s[h % s.length].header : o.getValue(r, !0);
              n.setCellData(r, h, a, !1, !1)
            }
          }
          n = this.topLeftCells;
          for (h = 0; h < n.columns.length; h++) {
            var u = n.columns[h],
              d = h < i.rowFields.length ? i.rowFields[h] : null;
            u.width = d && e.isNumber(d.width) ? d.width : this.columns.defaultSize, u.wordWrap = d ? d.wordWrap : null, u.align = null
          }
          n = this.cells;
          for (var h = 0; h < n.columns.length; h++) {
            var u = n.columns[h],
              d = i.valueFields.length ? i.valueFields[h % i.valueFields.length] : null;
            u.width = d && e.isNumber(d.width) ? d.width : this.columns.defaultSize, u.wordWrap = d ? d.wordWrap : null, u.format = d ? d.format : null
          }
        } else this.topLeftCells.setCellData(0, 0, null)
      }, n.prototype._formatItem = function(i, n) {
        var r = this._ng;
        if (r) {
          if (n.panel == this.topLeftCells) {
            n.cell.style.textAlign = "";
            var o = n.row < n.panel.rows.length - 1 || 0 == r.rowFields.length;
            e.toggleClass(n.cell, "wj-col-field-hdr", o), e.toggleClass(n.cell, "wj-row-field-hdr", !o)
          }
          n.panel == this.columnHeaders && (r.valueFields.length < 2 || n.row < n.panel.rows.length - 1) && (n.cell.style.textAlign = "");
          var s = null;
          n.panel == this.topLeftCells && n.row == n.panel.rows.length - 1 && this.allowDragging & e.grid.AllowDragging.Columns && (s = !0), e.setAttribute(n.cell, "draggable", s);
          var l;
          switch (n.panel) {
            case this.rowHeaders:
              l = r.rowFields[n.col % r.rowFields.length];
              break;
            case this.columnHeaders:
              l = r.columnFields[n.row];
              break;
            case this.cells:
              l = r.valueFields[n.col]
          }
          l && l.isContentHtml && (n.cell.innerHTML = n.cell.textContent);
          var a = r._getRowLevel(n.row),
            u = r._getColLevel(n.panel.columns[n.col].binding);
          if (e.toggleClass(n.cell, "wj-aggregate", a > -1 || u > -1), this._collapsibleSubtotals) {
            if (n.panel == this.rowHeaders && r._getShowRowTotals() == t.ShowTotals.Subtotals) {
              d = this.getMergedRange(n.panel, n.row, n.col, !1) || n.range;
              n.col < r.rowFields.length - 1 && d.rowSpan > 1 && (n.cell.innerHTML = this._getCollapsedGlyph(this._getRowCollapsed(d)) + n.cell.innerHTML)
            }
            if (n.panel == this.columnHeaders && r._getShowColTotals() == t.ShowTotals.Subtotals) {
              var d = this.getMergedRange(n.panel, n.row, n.col, !1) || n.range;
              n.row < r.columnFields.length - 1 && d.columnSpan > 1 && (n.cell.innerHTML = this._getCollapsedGlyph(this._getColCollapsed(d)) + n.cell.innerHTML)
            }
          }
          if (n.panel == this.topLeftCells && this.showRowFieldSort && n.col < r.rowFields.length && n.row == this._getSortRowIndex() && (l = r.rowFields[n.col], e.toggleClass(n.cell, "wj-sort-asc", !l.descending), e.toggleClass(n.cell, "wj-sort-desc", l.descending), n.cell.innerHTML += ' <span class="wj-glyph-' + (l.descending ? "down" : "up") + '"></span>'), this._centerVert && n.cell.hasChildNodes && (n.panel == this.rowHeaders || n.panel == this.columnHeaders)) {
            var h = e.createElement('<div style="display:table-cell;vertical-align:middle"></div>');
            this._docRange || (this._docRange = document.createRange()), this._docRange.selectNodeContents(n.cell), this._docRange.surroundContents(h), e.setCss(n.cell, {
              display: "table",
              tableLayout: "fixed",
              paddingTop: 0,
              paddingBottom: 0
            })
          }
        }
      }, n.prototype._getCollapsedGlyph = function(e) {
        return '<div style="display:inline-block;cursor:pointer" ' + n._WJA_COLLAPSE + '><span class="wj-glyph-' + (e ? "plus" : "minus") + '"></span></div>&nbsp'
      }, n.prototype._mousedown = function(t) {
        if (t.defaultPrevented || 0 != t.button) this._htDown = null;
        else if (this._htDown = this.hitTest(t), null != e.closest(t.target, "[" + n._WJA_COLLAPSE + "]") && null != this._htDown.panel) {
          var i = this._htDown.range,
            r = void 0;
          switch (this._htDown.panel.cellType) {
            case e.grid.CellType.RowHeader:
              r = this._getRowCollapsed(i), t.shiftKey || t.ctrlKey ? this._collapseRowsToLevel(i.col + (r ? 2 : 1)) : this._setRowCollapsed(i, !r);
              break;
            case e.grid.CellType.ColumnHeader:
              r = this._getColCollapsed(i), t.shiftKey || t.ctrlKey ? this._collapseColsToLevel(i.row + (r ? 2 : 1)) : this._setColCollapsed(i, !r)
          }
          this._htDown = null, t.preventDefault()
        }
      }, n.prototype._mouseup = function(t) {
        if (this._htDown && !t.defaultPrevented && "col-resize" != this.hostElement.style.cursor) {
          var i = this.hitTest(t);
          if (this._htDown.panel == i.panel && i.range.equals(this._htDown.range)) {
            var n = this._ng,
              r = this.topLeftCells;
            if (i.panel == r && i.row == r.rows.length - 1 && i.col > -1) {
              if (this.allowSorting && i.panel.columns[i.col].allowSorting && !n.isUpdating) {
                var o = new e.grid.CellRangeEventArgs(i.panel, i.range);
                if (this.onSortingColumn(o)) {
                  n.pivotView.sortDescriptions.clear();
                  var s = n.rowFields[i.col];
                  s.descending = !s.descending, this.onSortedColumn(o)
                }
              }
              t.preventDefault()
            }
          }
        }
      }, n.prototype._dblclick = function(e) {
        if (!e.defaultPrevented && this._showDetailOnDoubleClick) {
          var i = this._htDown;
          if (i && i.panel == this.cells) {
            var n = this._ng;
            n && n.fields.length > 0 && (n.fields[0] instanceof t.CubePivotField || this.showDetail(i.row, i.col))
          }
        }
      }, n.prototype._getRowLevel = function(e) {
        return this._ng._getRowLevel(e)
      }, n.prototype._getGroupedRows = function(t) {
        var i, n, r = this._getRowLevel.bind(this),
          o = t.col + 1;
        if (this._ng.totalsBeforeData) {
          for (n = t.row; n < this.rows.length - 1 && !((s = r(n + 1)) > -1 && s <= o); n++);
          for (i = n; i > 0 && r(i) != o; i--);
        } else {
          for (i = t.row; i > 0; i--) {
            var s = r(i - 1);
            if (s > -1 && s <= o) break
          }
          for (n = i; n < this.rows.length - 1 && r(n) != o; n++);
        }
        return r(i) == o && i++, r(n) == o && n--, e.assert(n >= i, "group end < start?"), n >= i ? new e.grid.CellRange(i, t.col, n, t.col2) : t
      }, n.prototype._toggleRowCollapsed = function(e) {
        this._setRowCollapsed(e, !this._getRowCollapsed(e))
      }, n.prototype._getRowCollapsed = function(e) {
        e = this._getGroupedRows(e);
        var i = this._ng.totalsBeforeData ? e.row - 1 : e.row2 + 1,
          n = this.rows[i] || this.rows[e.row],
          r = n ? n.dataItem[t._PivotKey._ROW_KEY_NAME] : null;
        return !!r && this._collapsedKeys[r.toString()]
      }, n.prototype._setRowCollapsed = function(i, n) {
        var r = this;
        i = this._getGroupedRows(i);
        var o = this._ng.totalsBeforeData ? i.row - 1 : i.row2 + 1,
          s = this.rows[o] || this.rows[i.row],
          l = s ? s.dataItem[t._PivotKey._ROW_KEY_NAME] : null;
        l && (this._collapsedKeys[l.toString()] = n), this.deferUpdate(function() {
          for (a = i.row; a <= i.row2; a++) r.rows[a].visible = !n;
          if (s && (s.visible = !0), !n) {
            for (var t = r._getRowLevel(o), l = [], a = i.row; a <= i.row2; a++)
              if (r._getRowLevel(a) > -1) {
                var u = r._getGroupedRows(new e.grid.CellRange(a, t));
                e.assert(u.row >= i.row && u.row2 <= i.row2, "child range overflow"), l.push(u), a++
              }
            l.forEach(function(e) {
              var t = r._getRowCollapsed(e);
              r._setRowCollapsed(e, t)
            })
          }
        })
      }, n.prototype._collapseRowsToLevel = function(e) {
        var i = this;
        e >= this._ng.rowFields.length && (e = -1), this.deferUpdate(function() {
          for (var n = 0; n < i.rows.length; n++) {
            var r = i._getRowLevel(n);
            if (r > 0) {
              var o = i.rows[n].dataItem[t._PivotKey._ROW_KEY_NAME];
              i._collapsedKeys[o.toString()] = e > 0 && r >= e
            }
            if (e < 0) i.rows[n].visible = !0;
            else {
              var s = r > -1 && r <= e;
              s || (i._ng.totalsBeforeData ? 0 == n && (s = !0) : n == i.rows.length - 1 && (s = !0)), i.rows[n].visible = s
            }
          }
        })
      }, n.prototype._getColLevel = function(e) {
        return this._ng._getColLevel(this.columns[e].binding)
      }, n.prototype._getGroupedCols = function(t) {
        var i, n = this._getColLevel.bind(this),
          r = t.row + 1,
          o = t.col;
        if (this._ng.totalsBeforeData)
          for (o = t.col2; o < this.columns.length && (s = n(o)) == r; o++);
        for (; o > 0 && !((s = n(o - 1)) > -1 && s <= r); o--);
        for (i = o; i < this.columns.length - 1; i++) {
          var s = n(i + 1);
          if (s > -1 && s <= r) break
        }
        return n(o) == r && o++, n(i) == r && i--, e.assert(i >= o, "group end < start?"), i >= o ? new e.grid.CellRange(t.row, o, t.row2, i) : t
      }, n.prototype._toggleColCollapsed = function(e) {
        this._setColCollapsed(e, !this._getColCollapsed(e))
      }, n.prototype._getColCollapsed = function(e) {
        e = this._getGroupedCols(e);
        var t = this._ng,
          i = t.totalsBeforeData ? e.col - t.valueFields.length : e.col2 + 1,
          n = this.columns[i],
          r = n ? n.binding : null;
        return !!r && this._collapsedKeys[r.toString()]
      }, n.prototype._setColCollapsed = function(t, i) {
        var n = this;
        t = this._getGroupedCols(t);
        var r = this._ng,
          o = r.totalsBeforeData ? t.col - r.valueFields.length : t.col2 + 1,
          s = this.columns[o],
          l = s ? s.binding : null;
        l && (this._collapsedKeys[l.toString()] = i), this.deferUpdate(function() {
          for (var s = 1; s <= r.valueFields.length; s++) n.columns[r.totalsBeforeData ? t.col - s : t.col2 + s].visible = !0;
          for (u = t.col; u <= t.col2; u++) n.columns[u].visible = !i;
          if (!i) {
            for (var l = n._getColLevel(o), a = [], u = t.col; u <= t.col2; u++)
              if (n._getColLevel(u) > -1) {
                var d = n._getGroupedCols(new e.grid.CellRange(l, u));
                e.assert(d.col >= t.col && d.col2 <= t.col2, "child range overflow"), a.push(d), u += r.valueFields.length - 1
              }
            a.forEach(function(e) {
              var t = n._getColCollapsed(e);
              n._setColCollapsed(e, t)
            })
          }
        })
      }, n.prototype._collapseColsToLevel = function(e) {
        var t = this;
        e >= this._ng.columnFields.length && (e = -1), this.deferUpdate(function() {
          for (var i = 0; i < t.columns.length; i++) {
            var n = t._getColLevel(i);
            if (n > 0) {
              var r = t._ng._getKey(t.columns[i].binding);
              t._collapsedKeys[r.toString()] = e > 0 && n >= e
            }
            if (e < 0) t.columns[i].visible = !0;
            else {
              var o = n > -1 && n <= e;
              t.columns[i].visible = o
            }
          }
        })
      }, n._WJA_COLLAPSE = "wj-pivot-collapse", n
    }(e.grid.FlexGrid);
    t.PivotGrid = i
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function(i) {
      function n(t, n) {
        var r = i.call(this, t, null) || this,
          o = r.getTemplate();
        r.applyTemplate("wj-control wj-detaildialog", o, {
          _sCnt: "sp-cnt",
          _dSummary: "div-summary",
          _dGrid: "div-grid",
          _btnOK: "btn-ok",
          _gHdr: "g-hdr"
        });
        var s = e.culture.olap.DetailDialog;
        return r._gHdr.textContent = s.header, r._btnOK.textContent = s.ok, r._g = new e.grid.FlexGrid(r._dGrid, {
          isReadOnly: !0
        }), r.initialize(n), r
      }
      return __extends(n, i), n.prototype.showDetail = function(i, n) {
        var r = this,
          o = i.getDetailView(n.row, n.col);
        this._g.itemsSource = o;
        var s = e.tryCast(o, "IPagedCollectionView");
        this._updateDetailCount(s ? s.totalItemCount : o.items.length), o.collectionChanged.addHandler(function() {
          r._updateDetailCount(o.items.length)
        });
        var l = i.engine,
          a = e.culture.olap.DetailDialog,
          u = "",
          d = i.rows[n.row].dataItem[t._PivotKey._ROW_KEY_NAME];
        this._rowHdr = e.escapeHtml(this._getHeader(d)), this._rowHdr && (u += a.row + ": <b>" + this._rowHdr + "</b><br>");
        var h = l._getKey(i.columns[n.col].binding);
        this._colHdr = e.escapeHtml(this._getHeader(h)), this._colHdr && (u += a.col + ": <b>" + this._colHdr + "</b><br>");
        var c = l.valueFields,
          p = c[n.col % c.length],
          _ = i.getCellData(n.row, n.col, !0);
        this._cellHdr = e.escapeHtml(p.header), this._cellValue = e.escapeHtml(_), u += this._cellHdr + ": <b>" + this._cellValue + "</b>", this._dSummary.innerHTML = u
      }, Object.defineProperty(n.prototype, "rowHeader", {
        get: function() {
          return this._rowHdr
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "columnHeader", {
        get: function() {
          return this._colHdr
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "cellHeader", {
        get: function() {
          return this._cellHdr
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "cellValue", {
        get: function() {
          return this._cellValue
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "detailCount", {
        get: function() {
          return this._detailCount
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype._updateDetailCount = function(t) {
        var i = e.culture.olap.DetailDialog;
        this._sCnt.textContent = e.format(1 == t ? i.item : i.items, {
          cnt: t
        }), this._detailCount = t
      }, n.prototype._getHeader = function(e) {
        if (e && e.values && e.values.length) {
          for (var t = [], i = 0; i < e.values.length; i++) t.push(e.getValue(i, !0));
          return t.join(" - ")
        }
        return null
      }, n.controlTemplate = '<div><div class="wj-dialog-header"><span wj-part="g-hdr">Detail View:</span> <span wj-part="sp-cnt"></span></div><div class="wj-dialog-body"><div wj-part="div-summary" class="wj-summary"></div><div wj-part="div-grid"></div></div><div class="wj-dialog-footer"><button wj-part="btn-ok"class="wj-hide wj-btn">OK</button>&nbsp;&nbsp;</div></div>', n
    }(e.input.Popup);
    t.DetailDialog = i
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i;
    ! function(e) {
      e[e.Column = 0] = "Column", e[e.Bar = 1] = "Bar", e[e.Scatter = 2] = "Scatter", e[e.Line = 3] = "Line", e[e.Area = 4] = "Area", e[e.Pie = 5] = "Pie"
    }(i = t.PivotChartType || (t.PivotChartType = {}));
    var n;
    ! function(e) {
      e[e.Always = 0] = "Always", e[e.Never = 1] = "Never", e[e.Auto = 2] = "Auto"
    }(n = t.LegendVisibility || (t.LegendVisibility = {}));
    var r = function(r) {
      function o(t, s) {
        var l = r.call(this, t) || this;
        return l._chartType = i.Column, l._showHierarchicalAxes = !0, l._showTotals = !1, l._showTitle = !0, l._showLegend = n.Always, l._legendPosition = e.chart.Position.Right, l._maxSeries = o.MAX_SERIES, l._maxPoints = o.MAX_POINTS, l._stacking = e.chart.Stacking.None, l._colItms = [], l._dataItms = [], l._lblsSrc = [], l._grpLblsSrc = [], e.addClass(l.hostElement, "wj-pivotchart"), l._isPieChart() ? l._createFlexPie() : l._createFlexChart(), r.prototype.initialize.call(l, s), l
      }
      return __extends(o, r), o.prototype._getProductInfo = function() {
        return "D6F4,PivotChart"
      }, Object.defineProperty(o.prototype, "engine", {
        get: function() {
          return this._ng
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "itemsSource", {
        get: function() {
          return this._itemsSource
        },
        set: function(i) {
          if (i && this._itemsSource !== i) {
            var n = this._itemsSource;
            i instanceof t.PivotPanel ? i = i.engine.pivotView : i instanceof t.PivotEngine && (i = i.pivotView), this._itemsSource = e.asCollectionView(i), this._onItemsSourceChanged(n)
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "chartType", {
        get: function() {
          return this._chartType
        },
        set: function(t) {
          if ((t = e.asEnum(t, i)) != this._chartType) {
            var n = this._chartType;
            this._chartType = t, this._changeChartType(), t !== i.Bar && n !== i.Bar || this._updatePivotChart()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "showHierarchicalAxes", {
        get: function() {
          return this._showHierarchicalAxes
        },
        set: function(t) {
          t != this._showHierarchicalAxes && (this._showHierarchicalAxes = e.asBoolean(t, !0), !this._isPieChart() && this._flexChart && this._updateFlexChart(this._dataItms, this._lblsSrc, this._grpLblsSrc))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "showTotals", {
        get: function() {
          return this._showTotals
        },
        set: function(t) {
          t != this._showTotals && (this._showTotals = e.asBoolean(t, !0), this._updatePivotChart())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "showTitle", {
        get: function() {
          return this._showTitle
        },
        set: function(t) {
          t != this._showTitle && (this._showTitle = e.asBoolean(t, !0), this._updatePivotChart())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "showLegend", {
        get: function() {
          return this._showLegend
        },
        set: function(t) {
          (t = e.asEnum(t, n)) != this.showLegend && (this._showLegend = t, this._updatePivotChart())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "legendPosition", {
        get: function() {
          return this._legendPosition
        },
        set: function(t) {
          (t = e.asEnum(t, e.chart.Position)) != this.legendPosition && (this._legendPosition = t, this._updatePivotChart())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "stacking", {
        get: function() {
          return this._stacking
        },
        set: function(t) {
          (t = e.asEnum(t, e.chart.Stacking)) != this._stacking && (this._stacking = t, this._flexChart && (this._flexChart.stacking = this._stacking, this._updatePivotChart()))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "maxSeries", {
        get: function() {
          return this._maxSeries
        },
        set: function(t) {
          t != this._maxSeries && (this._maxSeries = e.asNumber(t), this._updatePivotChart())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "maxPoints", {
        get: function() {
          return this._maxPoints
        },
        set: function(t) {
          t != this._maxPoints && (this._maxPoints = e.asNumber(t), this._updatePivotChart())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "flexChart", {
        get: function() {
          return this._flexChart
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "flexPie", {
        get: function() {
          return this._flexPie
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "header", {
        get: function() {
          return this._header
        },
        set: function(t) {
          t != this._header && (this._header = e.asString(t, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "footer", {
        get: function() {
          return this._footer
        },
        set: function(t) {
          t != this._footer && (this._footer = e.asString(t, !0), this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "headerStyle", {
        get: function() {
          return this._headerStyle
        },
        set: function(e) {
          e != this._headerStyle && (this._headerStyle = e, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "footerStyle", {
        get: function() {
          return this._footerStyle
        },
        set: function(e) {
          e != this._footerStyle && (this._footerStyle = e, this.invalidate())
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.refresh = function(e) {
        void 0 === e && (e = !0), r.prototype.refresh.call(this, e), this._isPieChart() ? this._flexPie && this._flexPie.refresh(e) : this._flexChart && this._flexChart.refresh(e), this._updatePivotChart()
      }, o.prototype.saveImageToDataUrl = function(e, t) {
        this._isPieChart() ? this._flexPie && this._flexPie.saveImageToDataUrl(e, t) : this._flexChart && this._flexChart.saveImageToDataUrl(e, t)
      }, o.prototype.saveImageToFile = function(e) {
        this._isPieChart() ? this._flexPie && this._flexPie.saveImageToFile(e) : this._flexChart && this._flexChart.saveImageToFile(e)
      }, o.prototype._onItemsSourceChanged = function(e) {
        this._ng && this._ng.updatedView.removeHandler(this._updatePivotChart, this), e && e.collectionChanged.removeHandler(this._updatePivotChart, this);
        var i = this._itemsSource;
        this._ng = i instanceof t.PivotCollectionView ? i.engine : null, this._ng && this._ng.updatedView.addHandler(this._updatePivotChart, this), this._itemsSource && this._itemsSource.collectionChanged.addHandler(this._updatePivotChart, this), this._updatePivotChart()
      }, o.prototype._createFlexChart = function() {
        var i = this,
          n = document.createElement("div");
        this.hostElement.appendChild(n), this._flexChart = new e.chart.FlexChart(n), this._flexChart._bindingSeparator = null, this._flexChart.legend.position = e.chart.Position.Right, this._flexChart.bindingX = t._PivotKey._ROW_KEY_NAME, this._flexChart.stacking = this._stacking, this._flexChart.tooltip.content = function(e) {
          var t = e.name ? "<b>" + e.name + "</b> <br/>" : "";
          return t += i._getLabel(e.x) + " " + i._getValue(e)
        }, this._flexChart.hostElement.style.visibility = "hidden"
      }, o.prototype._createFlexPie = function() {
        var i = this,
          n = document.createElement("div");
        this.hostElement.appendChild(n), this._colMenu = new e.input.Menu(n), this._colMenu.displayMemberPath = "text", this._colMenu.selectedValuePath = "prop", this._colMenu.hostElement.style.visibility = "hidden";
        var r = document.createElement("div");
        this.hostElement.appendChild(r), this._flexPie = new e.chart.FlexPie(r), this._flexPie.bindingName = t._PivotKey._ROW_KEY_NAME, this._flexPie.tooltip.content = function(e) {
          return "<b>" + i._getLabel(i._dataItms[e.pointIndex][t._PivotKey._ROW_KEY_NAME]) + "</b> <br/>" + i._getValue(e)
        }, this._flexPie.rendering.addHandler(this._updatePieInfo, this)
      }, o.prototype._updatePivotChart = function() {
        if (this._ng && this._ng.pivotView) {
          for (var e, i = [], n = [], r = [], o = 0, s = this._ng.pivotView, l = this._ng.rowFields, a = 0; a < s.items.length; a++) {
            var u = s.items[a],
              d = u.$rowKey;
            if (0 == a && this._getColumns(u), i.length >= this._maxPoints) break;
            if (!this._isTotalRow(u[t._PivotKey._ROW_KEY_NAME])) {
              i.push(u), n.push({
                value: i.length - 1,
                text: this._getLabel(u[t._PivotKey._ROW_KEY_NAME])
              });
              for (p = 0; p < l.length; p++)
                if (r.length <= p && r.push([]), this._getMergeIndex(d, e) < p) {
                  o = r[p].length - 1;
                  var h = r[p][o];
                  if (0 === o && p < l.length - 1 && (h.value = (h.width - 1) / 2), o > 0 && p < l.length - 1) {
                    var c = this._getOffsetWidth(r[p]);
                    h.value = c + (h.width - 1) / 2
                  }
                  r[p].push({
                    value: i.length - 1,
                    text: d.getValue(p, !0),
                    width: 1
                  })
                } else o = r[p].length - 1, r[p][o].width++;
              e = d
            }
          }
          for (var p = 0; p < l.length; p++) p < r.length && (o = r[p].length - 1, r[p][o].value = this._getOffsetWidth(r[p]) + (r[p][o].width - 1) / 2);
          this._dataItms = i, this._lblsSrc = n, this._grpLblsSrc = r, this._updateFlexChartOrPie()
        }
      }, o.prototype._updateFlexChartOrPie = function() {
        var e = this._isPieChart();
        !e && this._flexChart ? this._updateFlexChart(this._dataItms, this._lblsSrc, this._grpLblsSrc) : e && this._flexPie && this._updateFlexPie(this._dataItms, this._lblsSrc)
      }, o.prototype._updateFlexChart = function(t, i, n) {
        var r, o = this._flexChart,
          s = o ? o.hostElement : null;
        if (this._ng && o && s) {
          if (o.beginUpdate(), o.itemsSource = t, this._createSeries(), o.series && o.series.length > 0 && t.length > 0 ? s.style.visibility = "visible" : s.style.visibility = "hidden", o.header = this.header || this._getChartTitle(), this.headerStyle && (o.headerStyle = this.headerStyle), this.footer && (o.footer = this.footer), this.footerStyle && (o.footerStyle = this.footerStyle), this._isRotatedChart()) {
            if (this._showHierarchicalAxes && n.length > 0) {
              if (o.axisY.itemsSource = n[n.length - 1], o.axisX.labelAngle = void 0, n.length >= 2)
                for (l = n.length - 2; l >= 0; l--) this._createGroupAxes(n[l])
            } else o.axisY.labelAngle = void 0, o.axisY.itemsSource = i;
            o.axisX.itemsSource = void 0
          } else {
            if (this._showHierarchicalAxes && n.length > 0) {
              if (o.axisX.itemsSource = n[n.length - 1], n.length >= 2)
                for (var l = n.length - 2; l >= 0; l--) this._createGroupAxes(n[l])
            } else o.axisX.labelAngle = void 0, o.axisX.itemsSource = i;
            o.axisY.itemsSource = void 0
          }
          o.axisX.labelPadding = 6, o.axisY.labelPadding = 6, this._isRotatedChart() ? (r = o.axisX, o.axisY.reversed = !0) : (r = o.axisY, o.axisY.reversed = !1), o.stacking !== e.chart.Stacking.Stacked100pc && this._ng.valueFields.length > 0 && this._ng.valueFields[0].format ? r.format = this._ng.valueFields[0].format : r.format = "", o.legend.position = this._getLegendPosition(), o.endUpdate()
        }
      }, o.prototype._updateFlexPie = function(e, i) {
        var n = this._flexPie,
          r = n ? n.hostElement : null,
          o = this._colMenu;
        if (this._ng && n && r) {
          this._colItms.length > 0 && e.length > 0 ? r.style.visibility = "visible" : r.style.visibility = "hidden", n.beginUpdate(), n.itemsSource = e, n.bindingName = t._PivotKey._ROW_KEY_NAME, this._colItms && this._colItms.length > 0 && (n.binding = this._colItms[0].prop), n.header = this.header || this._getChartTitle(), this.headerStyle && (n.headerStyle = this.headerStyle), this.footer && (n.footer = this.footer), this.footerStyle && (n.footerStyle = this.footerStyle), n.legend.position = this._getLegendPosition(), n.endUpdate();
          var s = this._getTitle(this._ng.columnFields);
          "" !== s && (s = "<b>" + s + ": </b>"), this._colItms && this._colItms.length > 1 && e.length > 0 ? (o.hostElement.style.visibility = "visible", o.header = s + this._colItms[0].text, o.itemsSource = this._colItms, o.command = {
            executeCommand: function(e) {
              var t = o.selectedItem;
              o.header = s + t.text, n.binding = t.prop
            }
          }, o.selectedIndex = 0, o.invalidate(), o.listBox.invalidate()) : o.hostElement.style.visibility = "hidden"
        }
      }, o.prototype._getLegendPosition = function() {
        var t = this.legendPosition;
        if (this.showLegend == n.Never) t = e.chart.Position.None;
        else if (this.showLegend == n.Auto && this.flexChart && this.flexChart.series) {
          var i = 0;
          this.flexChart.series.forEach(function(t) {
            var n = t.visibility;
            t.name && n != e.chart.SeriesVisibility.Hidden && n != e.chart.SeriesVisibility.Plot && i++
          }), i < 2 && (t = e.chart.Position.None)
        }
        return t
      }, o.prototype._createSeries = function() {
        this._flexChart && (this._flexChart.series.length = 0);
        for (var t = 1 == this._ng.valueFields.length, i = 0; i < this._colItms.length; i++) {
          var n = new e.chart.Series,
            r = this._colItms[i].prop,
            o = this._colItms[i].text;
          if (t) {
            var s = o.lastIndexOf(";");
            s > -1 && (o = o.substr(0, s))
          }
          n.binding = r, n.name = o, this._flexChart.series.push(n)
        }
      }, o.prototype._getColumns = function(e) {
        var i, n = 0;
        if (e) {
          this._colItms = [];
          for (var r in e) e.hasOwnProperty(r) && r !== t._PivotKey._ROW_KEY_NAME && n < this._maxSeries && this._isValidColumn(r) && (i = this._ng._getKey(r), this._getLabel(i), this._colItms.push({
            prop: r,
            text: this._getLabel(i)
          }), n++)
        }
      }, o.prototype._createGroupAxes = function(t) {
        var i, n = this,
          r = this._flexChart,
          s = this._isRotatedChart() ? r.axisY : r.axisX;
        if (t) {
          (i = new e.chart.Axis).labelAngle = 0, i.labelPadding = 6, i.position = this._isRotatedChart() ? e.chart.Position.Left : e.chart.Position.Bottom, i.majorTickMarks = e.chart.TickMark.None, i.itemsSource = t, i.reversed = s.reversed, i.itemFormatter = function(e, r) {
            var s = .5 * t.filter(function(e) {
              return e.value == r.val
            })[0].width;
            if (n._isRotatedChart()) {
              var l = i.reversed ? -1 : 1,
                a = i.convert(r.val + s) + 5 * l,
                u = i.convert(r.val - s) - 5 * l,
                d = i._axrect.left + i._axrect.width - 5;
              e.drawLine(d, a, d, u, o.HRHAXISCSS), e.drawLine(d, a, d + 5, a, o.HRHAXISCSS), e.drawLine(d, u, d + 5, u, o.HRHAXISCSS), e.drawLine(d, r.pos.y, d - 5, r.pos.y, o.HRHAXISCSS)
            } else {
              var h = i.convert(r.val - s) + 5,
                c = i.convert(r.val + s) - 5,
                p = i._axrect.top;
              e.drawLine(h, p, c, p, o.HRHAXISCSS), e.drawLine(h, p, h, p - 5, o.HRHAXISCSS), e.drawLine(c, p, c, p - 5, o.HRHAXISCSS), e.drawLine(r.pos.x, p, r.pos.x, p + 5, o.HRHAXISCSS)
            }
            return r
          }, i.min = s.actualMin, i.max = s.actualMax, s.rangeChanged.addHandler(function() {
            isNaN(i.min) && isNaN(s.actualMin) || i.min == s.actualMin || (i.min = s.actualMin), isNaN(i.max) && isNaN(s.actualMax) || i.max == s.actualMax || (i.max = s.actualMax)
          });
          var l = new e.chart.Series;
          l.visibility = e.chart.SeriesVisibility.Hidden, this._isRotatedChart() ? l.axisY = i : l.axisX = i, r.series.push(l)
        }
      }, o.prototype._updateFlexPieBinding = function() {
        this._flexPie.binding = this._colMenu.selectedValue, this._flexPie.refresh()
      }, o.prototype._updatePieInfo = function() {
        var e = this;
        this._flexPie && (this._flexPie._labels = this._flexPie._labels.map(function(t, i) {
          return e._lblsSrc[i].text
        }))
      }, o.prototype._changeChartType = function() {
        var t = null;
        if (this.chartType === i.Pie) this._flexPie || this._createFlexPie(), this._updateFlexPie(this._dataItms, this._lblsSrc), this._swapChartAndPie(!1);
        else {
          switch (this.chartType) {
            case i.Column:
              t = e.chart.ChartType.Column;
              break;
            case i.Bar:
              t = e.chart.ChartType.Bar;
              break;
            case i.Scatter:
              t = e.chart.ChartType.Scatter;
              break;
            case i.Line:
              t = e.chart.ChartType.Line;
              break;
            case i.Area:
              t = e.chart.ChartType.Area
          }
          this._flexChart ? "none" !== this._flexChart.hostElement.style.display && t !== i.Bar && this._flexChart.chartType !== e.chart.ChartType.Bar || this._updateFlexChart(this._dataItms, this._lblsSrc, this._grpLblsSrc) : (this._createFlexChart(), this._updateFlexChart(this._dataItms, this._lblsSrc, this._grpLblsSrc)), this._flexChart.chartType = t, this._swapChartAndPie(!0)
        }
      }, o.prototype._swapChartAndPie = function(e) {
        var t = this;
        this._flexChart && (this._flexChart.hostElement.style.display = e ? "block" : "none"), this._flexPie && (this._flexPie.hostElement.style.display = e ? "none" : "block"), this._colMenu && this._colMenu.hostElement && (this._colMenu.hostElement.style.display = e ? "none" : "block", this._colMenu.hostElement.style.top = "0", setTimeout(function() {
          t._colMenu.hostElement.style.top = ""
        }, 0))
      }, o.prototype._getLabel = function(e) {
        var t = "";
        if (!e || !e.values) return t;
        var i = e.valueFields ? e.valueField : null;
        switch (e.values.length) {
          case 0:
            i && (t += i.header);
            break;
          case 1:
            t += e.getValue(0, !0), i && (t += "; " + i.header);
            break;
          default:
            for (var n = 0; n < e.values.length; n++) n > 0 && (t += "; "), t += e.getValue(n, !0);
            i && (t += "; " + i.header)
        }
        return t
      }, o.prototype._getValue = function(t) {
        var i = this._ng.valueFields,
          n = t.series ? t.series.chart.series.indexOf(t.series) : 0,
          r = n < i.length ? i[n].format : i.length ? i[0].format : "";
        return r ? e.Globalize.format(t.y, r) : t._yfmt
      }, o.prototype._getChartTitle = function() {
        if (!this.showTitle || !this._ng.valueFields.length) return null;
        var t = this._ng,
          i = this._getTitle(t.valueFields),
          n = this._getTitle(t.rowFields),
          r = this._getTitle(t.columnFields),
          o = i,
          s = e.culture.olap.PivotChart;
        return i && this._dataItms.length > 0 && (n && (o += e.format(" {by} {rows}", {
          by: s.by,
          rows: n
        })), r && (o += e.format(" {and} {cols}", {
          and: n ? s.and : s.by,
          cols: r
        }))), o
      }, o.prototype._getTitle = function(e) {
        for (var t = "", i = 0; i < e.length; i++) t.length > 0 && (t += "; "), t += e[i].header;
        return t
      }, o.prototype._isValidColumn = function(e) {
        var t = e.split(";"),
          i = this._showTotals;
        return 0 === this._ng.columnFields.length || (t && 2 === t.length ? i : !(!t || t.length - 2 !== this._ng.columnFields.length) && !i)
      }, o.prototype._isTotalRow = function(e) {
        return e.values.length < this._ng.rowFields.length
      }, o.prototype._isPieChart = function() {
        return this._chartType == i.Pie
      }, o.prototype._isRotatedChart = function() {
        return !this._isPieChart() && this._chartType == i.Bar !== this._flexChart.rotated
      }, o.prototype._getMergeIndex = function(e, t) {
        var i = -1;
        if (null != e && null != t && e.values.length == t.values.length && e.values.length == e.fields.length && t.values.length == t.fields.length)
          for (var n = 0; n < e.values.length; n++) {
            if (e.getValue(n, !0) != t.getValue(n, !0)) return i;
            i = n
          }
        return i
      }, o.prototype._getOffsetWidth = function(e) {
        var t = 0;
        if (e.length <= 1) return t;
        for (var i = 0; i < e.length - 1; i++) t += e[i].width;
        return t
      }, o.MAX_SERIES = 100, o.MAX_POINTS = 100, o.HRHAXISCSS = "wj-hierarchicalaxes-line", o
    }(e.Control);
    t.PivotChart = r
  }(e.olap || (e.olap = {}))
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
  ! function(t) {
    "use strict";
    var i = function(i) {
      function n(t, n) {
        var r = i.call(this, t, null, e.isIE() && !e.isEdge()) || this;
        r._mSel = !1, r._fldPropChangeBnd = r._fldPropChange.bind(r);
        var o = r.getTemplate();
        r.applyTemplate("wj-slicer wj-nocheck wj-control wj-content", o, {
          _root: "root",
          _divHdr: "div-hdr",
          _divHdrText: "div-hdr-text",
          _btnMSel: "btn-msel",
          _btnClear: "btn-clear"
        });
        var s = e.culture.olap.Slicer,
          l = new e.Tooltip;
        return e.setAttribute(r._btnMSel, "aria-label", s.multiSelect), e.setAttribute(r._btnClear, "aria-label", s.clearFilter), l.setTooltip(r._btnMSel, s.multiSelect), l.setTooltip(r._btnClear, s.clearFilter), r.addEventListener(r._btnClear, "click", function(e) {
          r._clear()
        }), r.addEventListener(r._btnMSel, "click", function(e) {
          r.multiSelect = !r.multiSelect
        }), r.initialize(n), r
      }
      return __extends(n, i), Object.defineProperty(n.prototype, "field", {
        get: function() {
          return this._fld
        },
        set: function(i) {
          var n = this;
          if (i != this._fld) {
            this._divListBox && (this.removeEventListener(this._divListBox, "click"), e.removeChild(this._divListBox), this._divListBox = null, this._lbx.dispose(), this._lbx = null), this._edt && (this._clear(), this._edt.dispose());
            var r = this._fld;
            if (r && r.propertyChanged.removeHandler(this._fldPropChangeBnd), (r = this._fld = e.asType(i, t.PivotField, !0)).propertyChanged.addHandler(this._fldPropChangeBnd), r && !r.isActive && r.engine.filterFields.push(r), r) {
              var o = document.createElement("div");
              this._edt = new e.grid.filter.ValueFilterEditor(o, r.filter.valueFilter)
            }
            this._edt && (this._divListBox = this._edt.hostElement.querySelector(".wj-listbox"), this._root.appendChild(this._divListBox), this._lbx = e.Control.getControl(this._divListBox), this._lbx.checkedItemsChanged.addHandler(function(e, t) {
              n.multiSelect || (n._mSel = !0, n._lbx.checkedItems = [n._lbx.selectedItem], n._mSel = !1), n._edt && r && (n._edt.updateFilter(), r.engine.invalidate())
            })), this._updateHeader(), e.isIE() && !e.isEdge() && this.refresh()
          }
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "header", {
        get: function() {
          return this._hdr
        },
        set: function(t) {
          t != this._hdr && (this._hdr = e.asString(t), this._updateHeader())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showHeader", {
        get: function() {
          return "none" != this._divHdr.style.display
        },
        set: function(t) {
          this._divHdr.style.display = e.asBoolean(t) ? "" : "none"
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "showCheckboxes", {
        get: function() {
          return !e.hasClass(this.hostElement, "wj-nocheck")
        },
        set: function(t) {
          e.toggleClass(this.hostElement, "wj-nocheck", !e.asBoolean(t))
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "multiSelect", {
        get: function() {
          return this._mSel
        },
        set: function(t) {
          this._mSel = e.asBoolean(t), e.toggleClass(this._btnMSel, "wj-state-active", this._mSel)
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.refresh = function(t) {
        void 0 === t && (t = !0), e.isIE() && !e.isEdge() && this.hostElement && e.setCss(this._lbx.hostElement, {
          height: this.hostElement.clientHeight - this._divHdr.offsetHeight
        }), i.prototype.refresh.call(this, t)
      }, n.prototype._fldPropChange = function(e, t) {
        switch (t.propertyName) {
          case "header":
            this._updateHeader();
            break;
          case "format":
          case "binding":
            e.filter.clear(), this._edt.updateEditor()
        }
      }, n.prototype._updateHeader = function() {
        var e = this._hdr;
        e || null == this._fld || (e = this._fld.header), this._divHdrText.textContent = e
      }, n.prototype._clear = function() {
        this._edt && (this._edt.clearEditor(), this._edt.updateFilter(), this._fld.engine.invalidate())
      }, n.controlTemplate = '<div wj-part="root"><div wj-part="div-hdr" class="wj-header"><div wj-part="div-hdr-text"></div><button wj-part="btn-msel" class="wj-btn btn-msel" tabindex="-1"><span class="wj-glyph">&#8801;</span></button><button wj-part="btn-clear" class="wj-btn btn-clear" tabindex="-1"><span class="wj-glyph">&times;</span></button></div></div>', n
    }(e.Control);
    t.Slicer = i
  }(e.olap || (e.olap = {}))
}(wijmo || (wijmo = {}));
