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
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var r in e) e.hasOwnProperty(r) && (t[r] = e[r])
      };
    return function(e, r) {
      function n() {
        this.constructor = e
      }
      t(e, r), e.prototype = null === r ? Object.create(r) : (n.prototype = r.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var r = function(e) {
      function r(r, n, i) {
        var o = e.call(this) || this;
        return o._count = 0, o._sortOnServer = !0, o._pageOnServer = !0, o._filterOnServer = !0, o._showDatesAsGmt = !1, o._inferDataTypes = !0, o._reviverBnd = o._reviver.bind(o), o.loading = new t.Event, o.loaded = new t.Event, o.error = new t.Event, o._url = t.asString(r, !1), o._tbl = t.asString(n), i && t.copy(o, i), o.sortDescriptions.collectionChanged.addHandler(function() {
          o.sortOnServer && o._getData()
        }), o._getData(), o
      }
      return __extends(r, e), Object.defineProperty(r.prototype, "tableName", {
        get: function() {
          return this._tbl
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "fields", {
        get: function() {
          return this._fields
        },
        set: function(e) {
          this._fields != e && (this._fields = t.asArray(e), this._getData())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "requestHeaders", {
        get: function() {
          return this._requestHeaders
        },
        set: function(t) {
          this._requestHeaders = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "keys", {
        get: function() {
          return this._keys
        },
        set: function(e) {
          this._keys = t.asArray(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "expand", {
        get: function() {
          return this._expand
        },
        set: function(e) {
          this._expand = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "jsonReviver", {
        get: function() {
          return this._jsonReviver
        },
        set: function(e) {
          this._jsonReviver = t.asFunction(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "dataTypes", {
        get: function() {
          return this._dataTypes
        },
        set: function(t) {
          this._dataTypes = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "inferDataTypes", {
        get: function() {
          return this._inferDataTypes
        },
        set: function(e) {
          e != this.inferDataTypes && (this._inferDataTypes = t.asBoolean(e), this._getData())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "showDatesAsGmt", {
        get: function() {
          return this._showDatesAsGmt
        },
        set: function(e) {
          e != this.showDatesAsGmt && (this._showDatesAsGmt = t.asBoolean(e), this._getData())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "sortOnServer", {
        get: function() {
          return this._sortOnServer
        },
        set: function(e) {
          e != this._sortOnServer && (this._sortOnServer = t.asBoolean(e), this._getData())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "pageOnServer", {
        get: function() {
          return this._pageOnServer
        },
        set: function(e) {
          e != this._pageOnServer && (this._pageOnServer = t.asBoolean(e), this.pageSize && this._getData())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "filterOnServer", {
        get: function() {
          return this._filterOnServer
        },
        set: function(e) {
          e != this._filterOnServer && (this._filterOnServer = t.asBoolean(e), this._getData())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "filterDefinition", {
        get: function() {
          return this._filterDef
        },
        set: function(e) {
          e != this._filterDef && (this._filterDef = t.asString(e), this._getData())
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.updateFilterDefinition = function(e) {
        this.filterOnServer && t.grid && t.grid.filter && e instanceof t.grid.filter.FlexGridFilter && (this.filterDefinition = this._asODataFilter(e))
      }, Object.defineProperty(r.prototype, "oDataVersion", {
        get: function() {
          return this._odv
        },
        set: function(e) {
          this._odv = t.asNumber(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "isLoading", {
        get: function() {
          return this._loading
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.onLoading = function(t) {
        this.loading.raise(this, t)
      }, r.prototype.onLoaded = function(t) {
        this.loaded.raise(this, t)
      }, r.prototype.load = function() {
        this._getData()
      }, r.prototype.onError = function(t) {
        return this.error.raise(this, t), !t.cancel
      }, r.prototype.commitNew = function() {
        var r = this,
          n = {
            Accept: "application/json"
          };
        if (this.requestHeaders)
          for (var i in this.requestHeaders) n[i] = this.requestHeaders[i];
        var o = this.currentAddItem;
        if (o) {
          var a = this._getWriteUrl();
          t.httpRequest(a, {
            method: "POST",
            requestHeaders: n,
            data: this._convertToDbFormat(o),
            success: function(t) {
              var e = JSON.parse(t.responseText, r._reviverBnd);
              r.keys.forEach(function(t) {
                o[t] = e[t]
              }), r.refresh()
            },
            error: this._error.bind(this)
          })
        }
        e.prototype.commitNew.call(this)
      }, r.prototype.commitEdit = function() {
        var r = this.currentEditItem;
        if (r && !this.currentAddItem && !this._sameContent(r, this._edtClone)) {
          var n = this._getWriteUrl(this._edtClone);
          t.httpRequest(n, {
            method: "PUT",
            requestHeaders: this.requestHeaders,
            data: this._convertToDbFormat(r),
            error: this._error.bind(this)
          })
        }
        e.prototype.commitEdit.call(this)
      }, r.prototype.remove = function(r) {
        if (r && r != this.currentAddItem && this.items.indexOf(r) > -1) {
          var n = this._getWriteUrl(r);
          t.httpRequest(n, {
            method: "DELETE",
            requestHeaders: this.requestHeaders,
            error: this._error.bind(this)
          })
        }
        e.prototype.remove.call(this, r)
      }, Object.defineProperty(r.prototype, "totalItemCount", {
        get: function() {
          return this._count
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "pageCount", {
        get: function() {
          return this.pageSize ? Math.ceil(this.totalItemCount / this.pageSize) : 1
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "pageSize", {
        get: function() {
          return this._pgSz
        },
        set: function(e) {
          e != this._pgSz && (this._pgSz = t.asInt(e), this.pageOnServer ? (this._pgIdx = t.clamp(this._pgIdx, 0, this.pageCount - 1), this._getData()) : this.refresh())
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.onPageChanging = function(t) {
        return e.prototype.onPageChanging.call(this, t), !t.cancel && this.pageOnServer && this._getData(), !t.cancel
      }, r.prototype._getPageView = function() {
        return this.pageOnServer ? this._view : e.prototype._getPageView.call(this)
      }, r.prototype._performRefresh = function() {
        var t = this._canFilter,
          r = this._canSort;
        this._canFilter = !this._filterOnServer, this._canSort = !this._sortOnServer, e.prototype._performRefresh.call(this), this._canFilter = t, this._canSort = r
      }, r.prototype._storeItems = function(t, e) {
        e ? Array.prototype.push.apply(this.sourceCollection, t) : this.sourceCollection = t
      }, r.prototype._getReadUrl = function(t) {
        var e = this._url;
        return "/" != e[e.length - 1] && (e += "/"), t ? e = 0 == t.indexOf("http") ? t : e + t : this._tbl && (e += this._tbl), e
      }, r.prototype._getReadParams = function(t) {
        var e = {
          $format: "json"
        };
        if (this._tbl && !t) {
          if (this._odv < 4 ? e.$inlinecount = "allpages" : e.$count = !0, this.fields && (e.$select = this.fields.join(",")), this.expand && (e.$expand = this.expand), this.sortOnServer && this.sortDescriptions.length) {
            for (var r = "", n = 0; n < this.sortDescriptions.length; n++) {
              var i = this.sortDescriptions[n];
              r && (r += ","), r += i.property, i.ascending || (r += " desc")
            }
            e.$orderby = r
          }
          this.pageOnServer && this.pageSize > 0 && (e.$skip = this.pageIndex * this.pageSize, e.$top = this.pageSize), this.filterDefinition && (e.$filter = this.filterDefinition)
        }
        return e
      }, r.prototype._getData = function(e, r) {
        var n = this;
        this._toGetData && clearTimeout(this._toGetData), this._toGetData = setTimeout(function() {
          if (null != n._odv) {
            n._loading = !0, n.onLoading();
            var i = t.httpRequest(n._getReadUrl(e), {
              requestHeaders: n.requestHeaders,
              data: n._getReadParams(e),
              success: function(t) {
                var r = JSON.parse(t.responseText, n._reviverBnd),
                  i = r.d ? r.d.results : r.value,
                  o = r.d ? r.d.__count : r["odata.count"] || r["@odata.count"];
                if (null != o && (n._count = parseInt(o)), n.pageIndex > 0 && n.pageIndex >= n.pageCount) {
                  var a = n.pageIndex;
                  if (n.moveToLastPage(), n.pageIndex != a) return
                }
                e || n.inferDataTypes && !n._dataTypesInferred && (n._dataTypesInferred = n._getInferredDataTypes(i));
                var s = n.dataTypes ? n.dataTypes : n._dataTypesInferred;
                if (s)
                  for (var u = 0; u < i.length; u++) n._convertItem(s, i[u]);
                n._storeItems(i, null != e), n.refresh(), (e = r.d ? r.d.__next : r["odata.nextLink"] || r["@odata.nextLink"]) ? n._getData(e) : (n._loading = !1, n.onLoaded())
              },
              error: function(e) {
                if (n._loading = !1, n.onLoaded(), n.onError(new t.RequestErrorEventArgs(e))) throw "HttpRequest Error: " + e.status + " " + e.statusText
              }
            });
            t.isFunction(r) && r(i)
          } else n._getSchema()
        }, 100)
      }, r.prototype._convertToDbFormat = function(e) {
        var r = {};
        for (var n in e) {
          var i = e[n];
          t.isDate(i) && this._showDatesAsGmt ? i = new Date(i.getTime() - 6e4 * i.getTimezoneOffset()) : t.isNumber(i) && this._odv < 4 && (i = i.toString()), r[n] = i
        }
        return r
      }, r.prototype._reviver = function(e, n) {
        return "string" == typeof n && r._rxDate.test(n) && (n = 0 == n.indexOf("/Date(") ? new Date(parseInt(n.substr(6))) : new Date(n), t.isDate(n) && this._showDatesAsGmt && (n = new Date(n.getTime() + 6e4 * n.getTimezoneOffset()))), this._jsonReviver ? this._jsonReviver(e, n) : n
      }, r.prototype._convertItem = function(e, r) {
        for (var n in e) {
          var i = e[n],
            o = r[n];
          null != o && (o = i == t.DataType.Date && t.isString(o) && 0 == o.indexOf("/Date(") ? new Date(parseInt(o.substr(6))) : t.changeType(o, i, null), t.isDate(o) && this._showDatesAsGmt && (o = new Date(o.getTime() + 6e4 * o.getTimezoneOffset())), r[n] = o)
        }
      }, r.prototype._getInferredDataTypes = function(e) {
        var n = null;
        if (e.length > 0) {
          for (var i = {}, o = 0; o < e.length && o < 10; o++) this._extend(i, e[o]);
          for (var a in i) {
            var s = i[a];
            t.isString(s) && r._rxDate.test(s) && (n || (n = {}), n[a] = t.DataType.Date)
          }
        }
        return n
      }, r.prototype._getServiceUrl = function() {
        var t = this._url;
        return "/" != t[t.length - 1] && (t += "/"), t
      }, r.prototype._getSchema = function() {
        var e = this,
          n = this._getServiceUrl() + "$metadata",
          i = r._odvCache;
        this._odv = i[n], this._odv ? this._getData() : t.httpRequest(n, {
          requestHeaders: this.requestHeaders,
          success: function(t) {
            var r = t.responseText.match(/<.*Version\s*=\s*"([0-9.]+)"/),
              o = r ? parseFloat(r[1]) : 4;
            i[n] = e._odv = o
          },
          error: function(t) {
            i[n] = e._odv = 4
          },
          complete: function(t) {
            e._getData()
          }
        })
      }, r.prototype._getWriteUrl = function(e) {
        var r = this._getServiceUrl();
        if (r += this._tbl, e) {
          t.assert(this.keys && this.keys.length > 0, "write operations require keys.");
          var n = [];
          this.keys.forEach(function(r) {
            t.assert(null != e[r], "key values cannot be null."), n.push(r + "=" + e[r])
          }), r += "(" + n.join(",") + ")"
        }
        return r
      }, r.prototype._asODataFilter = function(t) {
        for (var e = "", r = 0; r < t.grid.columns.length; r++) {
          var n = t.grid.columns[r],
            i = t.getColumnFilter(n, !1);
          i && i.isActive && (e && (e += " and "), i.conditionFilter && i.conditionFilter.isActive ? e += this._asODataConditionFilter(i.conditionFilter) : i.valueFilter && i.valueFilter.isActive && (e += this._asODataValueFilter(i.valueFilter)))
        }
        return e
      }, r.prototype._asODataValueFilter = function(e) {
        var r = e.column,
          n = r.binding,
          i = r.dataMap,
          o = (e._getUniqueValues(r, !1), "");
        for (var a in e.showValues) {
          var s = t.changeType(a, r.dataType, r.format);
          i && t.isString(s) && (s = i.getKeyValue(s)), o && (o += " or "), o += this._asEquals(n, s, r.dataType)
        }
        return o.length && (o = "(" + o + ")"), o
      }, r.prototype._asEquals = function(e, r, n) {
        return n == t.DataType.Date ? "(" + e + " ge " + this._asODataValue(r, n) + " and " + e + " lt " + this._asODataValue(t.DateTime.addDays(r, 1), n) + ")" : "(" + e + " eq " + this._asODataValue(r, n) + ")"
      }, r.prototype._asODataConditionFilter = function(t) {
        var e = this._asODataCondition(t, t.condition1),
          r = this._asODataCondition(t, t.condition2);
        return e && r ? "(" + e + (t.and ? " and " : " or ") + r + ")" : e ? "(" + e + ")" : r ? "(" + r + ")" : null
      }, r.prototype._asODataCondition = function(t, e) {
        var r = t.column.binding,
          n = this._asODataValue(e.value, t.column.dataType);
        switch (e.operator) {
          case 0:
            return this._asEquals(r, e.value, t.column.dataType);
          case 1:
            return r + " ne " + n;
          case 2:
            return r + " gt " + n;
          case 3:
            return r + " ge " + n;
          case 4:
            return r + " lt " + n;
          case 5:
            return r + " le " + n;
          case 6:
            return "startswith(" + r + "," + n + ")";
          case 7:
            return "endswith(" + r + "," + n + ")";
          case 8:
            return this._odv >= 4 ? "contains(" + r + "," + n + ")" : "substringof(" + n.toLowerCase() + ", tolower(" + r + "))";
          case 9:
            return this._odv >= 4 ? "not contains(" + r + "," + n + ")" : "not substringof(" + n.toLowerCase() + ", tolower(" + r + "))"
        }
      }, r.prototype._asODataValue = function(e, r) {
        return t.isDate(e) ? (this._showDatesAsGmt && (e = new Date(e.getTime() - 6e4 * e.getTimezoneOffset())), e = e.toJSON(), this._odv < 4 && (e = "datetime'" + e.substr(0, 10) + "'"), e) : t.isString(e) ? "'" + e.replace(/'/g, "''") + "'" : null != e ? e.toString() : r == t.DataType.String ? "''" : null
      }, r.prototype._error = function(e) {
        if (this.onError(new t.RequestErrorEventArgs(e))) throw this._getData(), "HttpRequest Error: " + e.status + " " + e.statusText
      }, r._odvCache = {}, r._rxDate = /^\d{4}\-\d{2}\-\d{2}T\d{2}\:\d{2}\:\d{2}|\/Date\([\d\-]*?\)/, r
    }(t.collections.CollectionView);
    e.ODataCollectionView = r
  }(t.odata || (t.odata = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var r in e) e.hasOwnProperty(r) && (t[r] = e[r])
      };
    return function(e, r) {
      function n() {
        this.constructor = e
      }
      t(e, r), e.prototype = null === r ? Object.create(r) : (n.prototype = r.prototype, new n)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var r = function(e) {
      function r(t, r, n) {
        var i = this;
        return null == n && (n = {}), n.pageOnServer = !0, n.sortOnServer = !0, n.canGroup = !1, n.pageSize || (n.pageSize = 100), i = e.call(this, t, r, n) || this, i._data = [], i.sourceCollection = i._data, i._skip = 0, i.setWindow(0, i.pageSize), i
      }
      return __extends(r, e), r.prototype.setWindow = function(t, e) {
        var r = this;
        this._toSetWindow && clearTimeout(this._toSetWindow), this._toSetWindow = setTimeout(function() {
          r._toSetWindow = null, r._performSetWindow(t, e)
        }, 50)
      }, Object.defineProperty(r.prototype, "pageOnServer", {
        get: function() {
          return !0
        },
        set: function(t) {
          if (!t) throw "ODataVirtualCollectionView requires pageOnServer = true."
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "sortOnServer", {
        get: function() {
          return !0
        },
        set: function(e) {
          if (!t.asBoolean(e)) throw "ODataVirtualCollectionView requires sortOnServer = true."
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "filterOnServer", {
        get: function() {
          return !0
        },
        set: function(e) {
          if (!t.asBoolean(e)) throw "ODataVirtualCollectionView requires filterOnServer = true."
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "canGroup", {
        get: function() {
          return this._canGroup
        },
        set: function(e) {
          if (t.asBoolean(e)) throw "ODataVirtualCollectionView does not support grouping."
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype._performRefresh = function() {
        this.isLoading || (this._refresh = !0), e.prototype._performRefresh.call(this)
      }, r.prototype._getReadParams = function(t) {
        var r = e.prototype._getReadParams.call(this, t);
        return t || (r.$skip = this._skip || 0, r.$top = this.pageSize), r
      }, r.prototype._storeItems = function(t, e) {
        if (this._refresh || this._data.length != this.totalItemCount) {
          this._data.length = this.totalItemCount;
          for (n = 0; n < this._data.length; n++) this._data[n] = null;
          this._refresh = !1
        }
        e || (this.currentPosition < 0 && this.moveCurrentToFirst(), this._loadOffset = 0);
        for (var r = this._loadOffset + (this._skip || 0), n = 0; n < t.length; n++) this._data[n + r] = t[n];
        this._loadOffset += t.length
      }, r.prototype._performSetWindow = function(e, r) {
        var n = this;
        this._pendingRequest && (this._pendingRequest.abort(), this._pendingRequest = null), e = t.asInt(e), r = t.asInt(r), t.assert(r >= e, "Start must be smaller than end.");
        var i = t.isNumber(this._start) && e > this._start;
        this._start = e, this._end = r;
        for (var o = !1, a = e; a < r && a < this._data.length && !o; a++) o = null == this._data[a];
        if (o) {
          for (var s = Math.max(0, i ? e : r - this.pageSize), a = s; a < this._data.length && null != this._data[a]; a++) s++;
          this._skip = s, this._getData(null, function(t) {
            n._pendingRequest = t
          })
        }
      }, r
    }(e.ODataCollectionView);
    e.ODataVirtualCollectionView = r
  }(t.odata || (t.odata = {}))
}(wijmo || (wijmo = {}));
