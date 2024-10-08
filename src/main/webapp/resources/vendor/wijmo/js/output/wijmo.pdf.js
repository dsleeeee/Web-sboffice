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
! function(t) {
  t.pdf || (t.pdf = {}),
    function(t) {
      if ("object" == typeof localExports) localModule.localExports = t();
      else if ("function" == typeof localDefine && localDefine.amd) localDefine(t);
      else {
        var e;
        "undefined" != typeof window ? e = window : "undefined" != typeof global ? e = global : "undefined" != typeof self && (e = self), e.PDFDocument = t()
      }
    }(function() {
      return function t(e, n, r) {
        function i(a, s) {
          if (!n[a]) {
            if (!e[a]) {
              var u = "function" == typeof localRequire && localRequire;
              if (!s && u) return u(a, !0);
              if (o) return o(a, !0);
              throw new Error("Cannot find module '" + a + "'")
            }
            var c = n[a] = {
              localExports: {}
            };
            e[a][0].call(c.localExports, function(t) {
              var n = e[a][1][t];
              return i(n || t)
            }, c, c.localExports, t, e, n, r)
          }
          return n[a].localExports
        }
        for (var o = "function" == typeof localRequire && localRequire, a = 0; a < r.length; a++) i(r[a]);
        return i
      }({
        1: [function(t, e, n) {
          var r;
          r = function() {
            function t(t) {
              this.data = null != t ? t : [], this.pos = 0, this.length = this.data.length
            }
            return t.prototype.readByte = function() {
              return this.data[this.pos++]
            }, t.prototype.writeByte = function(t) {
              return this.data[this.pos++] = t
            }, t.prototype.byteAt = function(t) {
              return this.data[t]
            }, t.prototype.readBool = function() {
              return !!this.readByte()
            }, t.prototype.writeBool = function(t) {
              return this.writeByte(t ? 1 : 0)
            }, t.prototype.readUInt32 = function() {
              var t, e, n, r;
              return t = 16777216 * this.readByte(), e = this.readByte() << 16, n = this.readByte() << 8, r = this.readByte(), t + e + n + r
            }, t.prototype.writeUInt32 = function(t) {
              return this.writeByte(t >>> 24 & 255), this.writeByte(t >> 16 & 255), this.writeByte(t >> 8 & 255), this.writeByte(255 & t)
            }, t.prototype.readInt32 = function() {
              var t;
              return (t = this.readUInt32()) >= 2147483648 ? t - 4294967296 : t
            }, t.prototype.writeInt32 = function(t) {
              return t < 0 && (t += 4294967296), this.writeUInt32(t)
            }, t.prototype.readUInt16 = function() {
              var t, e;
              return t = this.readByte() << 8, e = this.readByte(), t | e
            }, t.prototype.writeUInt16 = function(t) {
              return this.writeByte(t >> 8 & 255), this.writeByte(255 & t)
            }, t.prototype.readInt16 = function() {
              var t;
              return (t = this.readUInt16()) >= 32768 ? t - 65536 : t
            }, t.prototype.writeInt16 = function(t) {
              return t < 0 && (t += 65536), this.writeUInt16(t)
            }, t.prototype.readString = function(t) {
              var e, n, r;
              for (n = [], e = r = 0; 0 <= t ? r < t : r > t; e = 0 <= t ? ++r : --r) n[e] = String.fromCharCode(this.readByte());
              return n.join("")
            }, t.prototype.writeString = function(t) {
              var e, n, r, i;
              for (i = [], e = n = 0, r = t.length; 0 <= r ? n < r : n > r; e = 0 <= r ? ++n : --n) i.push(this.writeByte(t.charCodeAt(e)));
              return i
            }, t.prototype.stringAt = function(t, e) {
              return this.pos = t, this.readString(e)
            }, t.prototype.readShort = function() {
              return this.readInt16()
            }, t.prototype.writeShort = function(t) {
              return this.writeInt16(t)
            }, t.prototype.readLongLong = function() {
              var t, e, n, r, i, o, a, s;
              return t = this.readByte(), e = this.readByte(), n = this.readByte(), r = this.readByte(), i = this.readByte(), o = this.readByte(), a = this.readByte(), s = this.readByte(), 128 & t ? -1 * (72057594037927940 * (255 ^ t) + 281474976710656 * (255 ^ e) + 1099511627776 * (255 ^ n) + 4294967296 * (255 ^ r) + 16777216 * (255 ^ i) + 65536 * (255 ^ o) + 256 * (255 ^ a) + (255 ^ s) + 1) : 72057594037927940 * t + 281474976710656 * e + 1099511627776 * n + 4294967296 * r + 16777216 * i + 65536 * o + 256 * a + s
            }, t.prototype.writeLongLong = function(t) {
              var e, n;
              return e = Math.floor(t / 4294967296), n = 4294967295 & t, this.writeByte(e >> 24 & 255), this.writeByte(e >> 16 & 255), this.writeByte(e >> 8 & 255), this.writeByte(255 & e), this.writeByte(n >> 24 & 255), this.writeByte(n >> 16 & 255), this.writeByte(n >> 8 & 255), this.writeByte(255 & n)
            }, t.prototype.readInt = function() {
              return this.readInt32()
            }, t.prototype.writeInt = function(t) {
              return this.writeInt32(t)
            }, t.prototype.slice = function(t, e) {
              return this.data.slice(t, e)
            }, t.prototype.read = function(t) {
              var e, n;
              for (e = [], n = 0; 0 <= t ? n < t : n > t; 0 <= t ? ++n : --n) e.push(this.readByte());
              return e
            }, t.prototype.write = function(t) {
              var e, n, r, i;
              for (i = [], n = 0, r = t.length; n < r; n++) e = t[n], i.push(this.writeByte(e));
              return i
            }, t
          }(), e.localExports = r
        }, {}],
        2: [function(t, e, n) {
          (function(n) {
            var r, i, o, a, s, u, c = {}.hasOwnProperty,
              l = function(t, e) {
                function n() {
                  this.constructor = t
                }
                for (var r in e) c.call(e, r) && (t[r] = e[r]);
                return n.prototype = e.prototype, t.prototype = new n, t.__super__ = e.prototype, t
              };
            u = t("stream"), s = t("fs"), i = t("./object"), a = t("./reference"), o = t("./page"), r = function(e) {
              function r(t) {
                var e, n, i, o;
                if (this.options = null != t ? t : {}, r.__super__.constructor.apply(this, arguments), this.version = 1.3, this.compress = null == (i = this.options.compress) || i, this._pageBuffer = [], this._pageBufferStart = 0, this._offsets = [], this._waiting = 0, this._ended = !1, this._offset = 0, this._root = this.ref({
                  Type: "Catalog",
                  Pages: this.ref({
                    Type: "Pages",
                    Count: 0,
                    Kids: []
                  })
                }), this.page = null, this.initColor(), this.initVector(), this.initFonts(), this.initText(), this.initImages(), this.info = {
                  Producer: "PDFKit",
                  Creator: "PDFKit",
                  CreationDate: new Date
                }, this.options.info) {
                  o = this.options.info;
                  for (e in o) n = o[e], this.info[e] = n
                }
                this._write("%PDF-" + this.version), this._write("%ÿÿÿÿ"), this.options.pageAdding && this.on("pageAdding", this.options.pageAdding), this.options.pageAdded && this.on("pageAdded", this.options.pageAdded), !1 !== this.options.autoFirstPage && this.addPage()
              }
              var u;
              return l(r, e), (u = function(t) {
                var e, n, i;
                i = [];
                for (n in t) e = t[n], i.push(r.prototype[n] = e);
                return i
              })(t("./mixins/color")), u(t("./mixins/vector")), u(t("./mixins/fonts")), u(t("./mixins/text")), u(t("./mixins/images")), u(t("./mixins/annotations")), r.prototype.addPage = function(t) {
                var e;
                return null == t && (t = this.options), this.emit("pageAdding", this, t), this.options.bufferPages || this.flushPages(), this.page = new o(this, t), this._pageBuffer.push(this.page), (e = this._root.data.Pages.data).Kids.push(this.page.dictionary), e.Count++, this.x = this.page.margins.left, this.y = this.page.margins.top, this._ctm = [1, 0, 0, 1, 0, 0], this.transform(1, 0, 0, -1, 0, this.page.height), this.emit("pageAdded", this), this
              }, r.prototype.bufferedPageRange = function() {
                return {
                  start: this._pageBufferStart,
                  count: this._pageBuffer.length
                }
              }, r.prototype.switchToPage = function(t) {
                var e;
                if (!(e = this._pageBuffer[t - this._pageBufferStart])) throw new Error("switchToPage(" + t + ") out of bounds, current buffer covers pages " + this._pageBufferStart + " to " + (this._pageBufferStart + this._pageBuffer.length - 1));
                return this.page = e
              }, r.prototype.flushPages = function() {
                var t, e, n;
                for (t = this._pageBuffer, this._pageBuffer = [], this._pageBufferStart += t.length, e = 0, n = t.length; e < n; e++) t[e].end()
              }, r.prototype.ref = function(t) {
                var e;
                return e = new a(this, this._offsets.length + 1, t), this._offsets.push(null), this._waiting++, e
              }, r.prototype._read = function() {}, r.prototype._write = function(t) {
                return n.isBuffer(t) || (t = new n(t + "\n", "binary")), this.push(t), this._offset += t.length
              }, r.prototype.addContent = function(t) {
                return this.page.write(t), this
              }, r.prototype._refEnd = function(t) {
                if (this._offsets[t.id - 1] = t.offset, 0 == --this._waiting && this._ended) return this._finalize(), this._ended = !1
              }, r.prototype.write = function(t, e) {
                var n;
                return n = new Error("PDFDocument#write is deprecated, and will be removed in a future version of PDFKit. Please pipe the document into a Node stream."), console.warn(n.stack), this.pipe(s.createWriteStream(t)), this.end(), this.once("end", e)
              }, r.prototype.output = function(t) {
                throw new Error("PDFDocument#output is deprecated, and has been removed from PDFKit. Please pipe the document into a Node stream.")
              }, r.prototype.end = function() {
                var t, e, n, r, i;
                this.emit("ending"), this.flushPages(), this._info = this.ref(), r = this.info;
                for (t in r) "string" == typeof(n = r[t]) && (n = new String(n)), this._info.data[t] = n;
                this._info.end(), i = this._fontFamilies;
                for (e in i) i[e].finalize();
                return this._root.end(), this._root.data.Pages.end(), 0 === this._waiting ? this._finalize() : this._ended = !0
              }, r.prototype._finalize = function(t) {
                var e, n, r, o, a;
                for (n = this._offset, this._write("xref"), this._write("0 " + (this._offsets.length + 1)), this._write("0000000000 65535 f "), r = 0, o = (a = this._offsets).length; r < o; r++) e = ("0000000000" + (e = a[r])).slice(-10), this._write(e + " 00000 n ");
                return this._write("trailer"), this._write(i.convert({
                  Size: this._offsets.length + 1,
                  Root: this._root,
                  Info: this._info
                })), this._write("startxref"), this._write("" + n), this._write("%%EOF"), this.push(null)
              }, r.prototype.toString = function() {
                return "[object PDFDocument]"
              }, r
            }(u.Readable), e.localExports = r
          }).call(this, t("buffer").Buffer)
        }, {
          "./mixins/annotations": 12,
          "./mixins/color": 13,
          "./mixins/fonts": 14,
          "./mixins/images": 15,
          "./mixins/text": 16,
          "./mixins/vector": 17,
          "./object": 18,
          "./page": 19,
          "./reference": 21,
          buffer: 31,
          fs: 29,
          stream: 207
        }],
        3: [function(t, e, n) {
          (function(n) {
            var r, i, o, a;
            a = t("fontkit"), i = function() {
              function t() {
                throw new Error("Cannot construct a PDFFont directly.")
              }
              return t.open = function(t, e, i, s) {
                var u;
                if ("string" == typeof e) {
                  if (o.isStandardFont(e)) return new o(t, e, s);
                  u = a.openSync(e, i)
                } else n.isBuffer(e) ? u = a.create(e, i) : e instanceof Uint8Array ? u = a.create(new n(e), i) : e instanceof ArrayBuffer && (u = a.create(new n(new Uint8Array(e)), i));
                if (null == u) throw new Error("Not a supported font format or standard PDF font.");
                return new r(t, u, s)
              }, t.prototype.encode = function(t) {
                throw new Error("Must be implemented by subclasses")
              }, t.prototype.widthOfString = function(t) {
                throw new Error("Must be implemented by subclasses")
              }, t.prototype.ref = function() {
                return null != this.dictionary ? this.dictionary : this.dictionary = this.document.ref()
              }, t.prototype.finalize = function() {
                if (!this.embedded && null != this.dictionary) return this.embed(), this.embedded = !0
              }, t.prototype.embed = function() {
                throw new Error("Must be implemented by subclasses")
              }, t.prototype.getAscender = function(t) {
                return this.ascender / 1e3 * t
              }, t.prototype.getBBox = function(t) {
                return {
                  llx: this.bbox[0] / 1e3 * t,
                  lly: this.bbox[1] / 1e3 * t,
                  urx: this.bbox[2] / 1e3 * t,
                  ury: this.bbox[3] / 1e3 * t
                }
              }, t.prototype.lineHeight = function(t, e) {
                var n;
                return null == e && (e = !1), n = e ? this.lineGap : 0, (this.ascender + n - this.descender) / 1e3 * t
              }, t
            }(), e.localExports = i, o = t("./font/standard"), r = t("./font/embedded")
          }).call(this, t("buffer").Buffer)
        }, {
          "./font/embedded": 5,
          "./font/standard": 6,
          buffer: 31,
          fontkit: 33
        }],
        4: [function(t, e, n) {
          var r;
          r = function() {
            function t(t) {
              this.contents = t, this.bbox = this._parseFontBBox(t[1]), this.ascender = t[4], this.descender = t[5], this.glyphWidths = this._parseGlyphWidths(t[6]), this.charWidths = this._getCharWidths(), this.kernPairs = this._parseKerningPairs(t[7]), this.lineGap = this.bbox[3] - this.bbox[1] - (this.ascender - this.descender)
            }
            var e, n, r;
            return t.prototype.encodeText = function(t) {
              var e, n, i, o, a;
              for (i = [], n = o = 0, a = t.length; 0 <= a ? o < a : o > a; n = 0 <= a ? ++o : --o) e = t.charCodeAt(n), e = r[e] || e, i.push(e.toString(16));
              return i
            }, t.prototype.glyphsForString = function(t) {
              var e, n, r, i, o;
              for (r = [], n = i = 0, o = t.length; 0 <= o ? i < o : i > o; n = 0 <= o ? ++i : --i) e = t.charCodeAt(n), r.push(this.characterToGlyph(e));
              return r
            }, t.prototype.characterToGlyph = function(t) {
              return e[r[t] || t] || ".notdef"
            }, t.prototype.widthOfGlyph = function(t) {
              return this.glyphWidths[t] || 0
            }, t.prototype.getKernPair = function(t, e) {
              return this.kernPairs[t + "\0" + e] || 0
            }, t.prototype.advancesForGlyphs = function(t) {
              var e, n, r, i, o, a;
              for (e = [], n = o = 0, a = t.length; o < a; n = ++o) r = t[n], i = t[n + 1], e.push(this.widthOfGlyph(r) + this.getKernPair(r, i));
              return e
            }, t.prototype._parseFontBBox = function(t) {
              var e, n, r, i, o;
              for (n = [], e = i = 0, o = (r = t.split(":")).length; 0 <= o ? i < o : i > o; e = 0 <= o ? ++i : --i) n.push(parseInt(r[e]));
              return n
            }, t.prototype._getCharWidths = function() {
              var t, n, r;
              for (n = [], t = r = 0; r <= 255; t = ++r) n.push(this.glyphWidths[e[t]]);
              return n
            }, t.prototype._parseGlyphWidths = function(t) {
              var e, r, i, o, a, s, u, c, l, h, f, d;
              if (l = t.split(","), s = {}, c = 0, 1 === l.length)
                for (o = l[0].match(/(^\d+)-(\d+):(\d+)$/), u = parseInt(o[1]), r = parseInt(o[2]), c = parseInt(o[3]), i = h = u; u <= r ? h <= r : h >= r; i = u <= r ? ++h : --h) s[n[i]] = c;
              else
                for (e = 0, i = f = 0, d = l.length; 0 <= d ? f < d : f > d; i = 0 <= d ? ++f : --f) 1 === (a = l[i].split(":")).length ? c = parseInt(a[0]) : (e = parseInt(a[0]), c = parseInt(a[1])), s[n[e++]] = c;
              return s
            }, t.prototype._parseKerningPairs = function(t) {
              return {}
            }, r = {
              402: 131,
              8211: 150,
              8212: 151,
              8216: 145,
              8217: 146,
              8218: 130,
              8220: 147,
              8221: 148,
              8222: 132,
              8224: 134,
              8225: 135,
              8226: 149,
              8230: 133,
              8364: 128,
              8240: 137,
              8249: 139,
              8250: 155,
              710: 136,
              8482: 153,
              338: 140,
              339: 156,
              732: 152,
              352: 138,
              353: 154,
              376: 159,
              381: 142,
              382: 158
            }, n = "space:exclam:quotedbl:numbersign:dollar:percent:ampersand:quoteright:parenleft:parenright:asterisk:plus:comma:hyphen:period:slash:zero:one:two:three:four:five:six:seven:eight:nine:colon:semicolon:less:equal:greater:question:at:A:B:C:D:E:F:G:H:I:J:K:L:M:N:O:P:Q:R:S:T:U:V:W:X:Y:Z:bracketleft:backslash:bracketright:asciicircum:underscore:quoteleft:a:b:c:d:e:f:g:h:i:j:k:l:m:n:o:p:q:r:s:t:u:v:w:x:y:z:braceleft:bar:braceright:asciitilde:exclamdown:cent:sterling:fraction:yen:florin:section:currency:quotesingle:quotedblleft:guillemotleft:guilsinglleft:guilsinglright:fi:fl:endash:dagger:daggerdbl:periodcentered:paragraph:bullet:quotesinglbase:quotedblbase:quotedblright:guillemotright:ellipsis:perthousand:questiondown:grave:acute:circumflex:tilde:macron:breve:dotaccent:dieresis:ring:cedilla:hungarumlaut:ogonek:caron:emdash:AE:ordfeminine:Lslash:Oslash:OE:ordmasculine:ae:dotlessi:lslash:oslash:oe:germandbls:Idieresis:eacute:abreve:uhungarumlaut:ecaron:Ydieresis:divide:Yacute:Acircumflex:aacute:Ucircumflex:yacute:scommaaccent:ecircumflex:Uring:Udieresis:aogonek:Uacute:uogonek:Edieresis:Dcroat:commaaccent:copyright:Emacron:ccaron:aring:Ncommaaccent:lacute:agrave:Tcommaaccent:Cacute:atilde:Edotaccent:scaron:scedilla:iacute:lozenge:Rcaron:Gcommaaccent:ucircumflex:acircumflex:Amacron:rcaron:ccedilla:Zdotaccent:Thorn:Omacron:Racute:Sacute:dcaron:Umacron:uring:threesuperior:Ograve:Agrave:Abreve:multiply:uacute:Tcaron:partialdiff:ydieresis:Nacute:icircumflex:Ecircumflex:adieresis:edieresis:cacute:nacute:umacron:Ncaron:Iacute:plusminus:brokenbar:registered:Gbreve:Idotaccent:summation:Egrave:racute:omacron:Zacute:Zcaron:greaterequal:Eth:Ccedilla:lcommaaccent:tcaron:eogonek:Uogonek:Aacute:Adieresis:egrave:zacute:iogonek:Oacute:oacute:amacron:sacute:idieresis:Ocircumflex:Ugrave:Delta:thorn:twosuperior:Odieresis:mu:igrave:ohungarumlaut:Eogonek:dcroat:threequarters:Scedilla:lcaron:Kcommaaccent:Lacute:trademark:edotaccent:Igrave:Imacron:Lcaron:onehalf:lessequal:ocircumflex:ntilde:Uhungarumlaut:Eacute:emacron:gbreve:onequarter:Scaron:Scommaaccent:Ohungarumlaut:degree:ograve:Ccaron:ugrave:radical:Dcaron:rcommaaccent:Ntilde:otilde:Rcommaaccent:Lcommaaccent:Atilde:Aogonek:Aring:Otilde:zdotaccent:Ecaron:Iogonek:kcommaaccent:minus:Icircumflex:ncaron:tcommaaccent:logicalnot:odieresis:udieresis:notequal:gcommaaccent:eth:zcaron:ncommaaccent:onesuperior:imacron:Euro:universal:existential:suchthat:asteriskmath:congruent:Alpha:Beta:Chi:Epsilon:Phi:Gamma:Eta:Iota:theta1:Kappa:Lambda:Mu:Nu:Omicron:Pi:Theta:Rho:Sigma:Tau:Upsilon:sigma1:Omega:Xi:Psi:Zeta:therefore:perpendicular:radicalex:alpha:beta:chi:delta:epsilon:phi:gamma:eta:iota:phi1:kappa:lambda:nu:omicron:pi:theta:rho:sigma:tau:upsilon:omega1:omega:xi:psi:zeta:similar:Upsilon1:minute:infinity:club:diamond:heart:spade:arrowboth:arrowleft:arrowup:arrowright:arrowdown:second:proportional:equivalence:approxequal:arrowvertex:arrowhorizex:carriagereturn:aleph:Ifraktur:Rfraktur:weierstrass:circlemultiply:circleplus:emptyset:intersection:union:propersuperset:reflexsuperset:notsubset:propersubset:reflexsubset:element:notelement:angle:gradient:registerserif:copyrightserif:trademarkserif:product:dotmath:logicaland:logicalor:arrowdblboth:arrowdblleft:arrowdblup:arrowdblright:arrowdbldown:angleleft:registersans:copyrightsans:trademarksans:parenlefttp:parenleftex:parenleftbt:bracketlefttp:bracketleftex:bracketleftbt:bracelefttp:braceleftmid:braceleftbt:braceex:angleright:integral:integraltp:integralex:integralbt:parenrighttp:parenrightex:parenrightbt:bracketrighttp:bracketrightex:bracketrightbt:bracerighttp:bracerightmid:bracerightbt:apple:a1:a2:a202:a3:a4:a5:a119:a118:a117:a11:a12:a13:a14:a15:a16:a105:a17:a18:a19:a20:a21:a22:a23:a24:a25:a26:a27:a28:a6:a7:a8:a9:a10:a29:a30:a31:a32:a33:a34:a35:a36:a37:a38:a39:a40:a41:a42:a43:a44:a45:a46:a47:a48:a49:a50:a51:a52:a53:a54:a55:a56:a57:a58:a59:a60:a61:a62:a63:a64:a65:a66:a67:a68:a69:a70:a71:a72:a73:a74:a203:a75:a204:a76:a77:a78:a79:a81:a82:a83:a84:a97:a98:a99:a100:a89:a90:a93:a94:a91:a92:a205:a85:a206:a86:a87:a88:a95:a96:a101:a102:a103:a104:a106:a107:a108:a112:a111:a110:a109:a120:a121:a122:a123:a124:a125:a126:a127:a128:a129:a130:a131:a132:a133:a134:a135:a136:a137:a138:a139:a140:a141:a142:a143:a144:a145:a146:a147:a148:a149:a150:a151:a152:a153:a154:a155:a156:a157:a158:a159:a160:a161:a163:a164:a196:a165:a192:a166:a167:a168:a169:a170:a171:a172:a173:a162:a174:a175:a176:a177:a178:a179:a193:a180:a199:a181:a200:a182:a201:a183:a184:a197:a185:a194:a198:a186:a195:a187:a188:a189:a190:a191".split(":"), e = ".notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef .notdef space exclam quotedbl numbersign dollar percent ampersand quotesingle parenleft parenright asterisk plus comma hyphen period slash zero one two three four five six seven eight nine colon semicolon less equal greater question at A B C D E F G H I J K L M N O P Q R S T U V W X Y Z bracketleft backslash bracketright asciicircum underscore grave a b c d e f g h i j k l m n o p q r s t u v w x y z braceleft bar braceright asciitilde .notdef Euro .notdef quotesinglbase florin quotedblbase ellipsis dagger daggerdbl circumflex perthousand Scaron guilsinglleft OE .notdef Zcaron .notdef .notdef quoteleft quoteright quotedblleft quotedblright bullet endash emdash tilde trademark scaron guilsinglright oe .notdef zcaron ydieresis space exclamdown cent sterling currency yen brokenbar section dieresis copyright ordfeminine guillemotleft logicalnot hyphen registered macron degree plusminus twosuperior threesuperior acute mu paragraph periodcentered cedilla onesuperior ordmasculine guillemotright onequarter onehalf threequarters questiondown Agrave Aacute Acircumflex Atilde Adieresis Aring AE Ccedilla Egrave Eacute Ecircumflex Edieresis Igrave Iacute Icircumflex Idieresis Eth Ntilde Ograve Oacute Ocircumflex Otilde Odieresis multiply Oslash Ugrave Uacute Ucircumflex Udieresis Yacute Thorn germandbls agrave aacute acircumflex atilde adieresis aring ae ccedilla egrave eacute ecircumflex edieresis igrave iacute icircumflex idieresis eth ntilde ograve oacute ocircumflex otilde odieresis divide oslash ugrave uacute ucircumflex udieresis yacute thorn ydieresis".split(" "), t
          }(), e.localExports = r
        }, {}],
        5: [function(t, e, n) {
          var r, i, o = {}.hasOwnProperty,
            a = function(t, e) {
              function n() {
                this.constructor = t
              }
              for (var r in e) o.call(e, r) && (t[r] = e[r]);
              return n.prototype = e.prototype, t.prototype = new n, t.__super__ = e.prototype, t
            },
            s = [].slice;
          i = t("../font"), t("../object"), r = function(t) {
            function e(t, e, n) {
              this.document = t, this.font = e, this.id = n, this.subset = this.font.createSubset(), this.unicode = [
                [0]
              ], this.widths = [this.font.getGlyph(0).advanceWidth], this.name = this.font.postscriptName, this.scale = 1e3 / this.font.unitsPerEm, this.ascender = this.font.ascent * this.scale, this.descender = this.font.descent * this.scale, this.lineGap = this.font.lineGap * this.scale, this.bbox = this.font.bbox, this.layoutCache = Object.create(null)
            }
            var n;
            return a(e, i), e.prototype.layoutRun = function(t, e) {
              var n, r, i, o, a, s, u;
              for (n = a = 0, s = (u = (o = this.font.layout(t, e)).positions).length; a < s; n = ++a) {
                i = u[n];
                for (r in i) i[r] *= this.scale;
                i.advanceWidth = o.glyphs[n].advanceWidth * this.scale
              }
              return o
            }, e.prototype.layoutCached = function(t) {
              var e, n;
              return (e = this.layoutCache[t]) ? e : (n = this.layoutRun(t), this.layoutCache[t] = n, n)
            }, e.prototype.layout = function(t, e, n) {
              var r, i, o, a, s, u, c;
              if (null == n && (n = !1), e) return this.layoutRun(t, e);
              for (i = n ? null : [], s = n ? null : [], r = 0, a = 0, o = 0; o <= t.length;) o === t.length && a < o || " " === (c = t.charAt(o)) || "\t" === c ? (u = this.layoutCached(t.slice(a, ++o)), n || (i.push.apply(i, u.glyphs), s.push.apply(s, u.positions)), r += u.advanceWidth, a = o) : o++;
              return {
                glyphs: i,
                positions: s,
                advanceWidth: r
              }
            }, e.prototype.encode = function(t, e) {
              var n, r, i, o, a, s, u, c, l, h, f;
              for (i = (f = this.layout(t, e)).glyphs, a = f.positions, s = [], o = l = 0, h = i.length; l < h; o = ++l) r = i[o], n = this.subset.includeGlyph(r.id), s.push(("0000" + n.toString(16)).slice(-4)), null == (u = this.widths)[n] && (u[n] = r.advanceWidth * this.scale), null == (c = this.unicode)[n] && (c[n] = r.codePoints);
              return [s, a]
            }, e.prototype.widthOfString = function(t, e, n) {
              var r, i;
              return i = this.layout(t, n, !0).advanceWidth, r = e / 1e3, i * r
            }, e.prototype.embed = function() {
              var t, e, n, r, i, o, a, s, u, c, l;
              return s = null != this.subset.cff, o = this.document.ref(), s && (o.data.Subtype = "CIDFontType0C"), this.subset.encodeStream().on("data", function(t) {
                return o.write(t)
              }).on("end", function() {
                return o.end()
              }), r = ((null != (l = this.font["OS/2"]) ? l.sFamilyClass : void 0) || 0) >> 8, i = 0, this.font.post.isFixedPitch && (i |= 1), 1 <= r && r <= 7 && (i |= 2), i |= 4, 10 === r && (i |= 8), this.font.head.macStyle.italic && (i |= 64), c = function() {
                var t, e;
                for (e = [], a = t = 0; t < 6; a = ++t) e.push(String.fromCharCode(26 * Math.random() + 65));
                return e
              }().join(""), u = c + "+" + this.font.postscriptName, t = this.font.bbox, n = this.document.ref({
                Type: "FontDescriptor",
                FontName: u,
                Flags: i,
                FontBBox: [t.minX * this.scale, t.minY * this.scale, t.maxX * this.scale, t.maxY * this.scale],
                ItalicAngle: this.font.italicAngle,
                Ascent: this.ascender,
                Descent: this.descender,
                CapHeight: (this.font.capHeight || this.font.ascent) * this.scale,
                XHeight: (this.font.xHeight || 0) * this.scale,
                StemV: 0
              }), s ? n.data.FontFile3 = o : n.data.FontFile2 = o, n.end(), (e = this.document.ref({
                Type: "Font",
                Subtype: s ? "CIDFontType0" : "CIDFontType2",
                BaseFont: u,
                CIDSystemInfo: {
                  Registry: new String("Adobe"),
                  Ordering: new String("Identity"),
                  Supplement: 0
                },
                FontDescriptor: n,
                W: [0, this.widths]
              })).end(), this.dictionary.data = {
                Type: "Font",
                Subtype: "Type0",
                BaseFont: u,
                Encoding: "Identity-H",
                DescendantFonts: [e],
                ToUnicode: this.toUnicodeCmap()
              }, this.dictionary.end()
            }, n = function() {
              var t, e;
              return e = 1 <= arguments.length ? s.call(arguments, 0) : [],
                function() {
                  var n, r, i;
                  for (i = [], n = 0, r = e.length; n < r; n++) t = e[n], i.push(("0000" + t.toString(16)).slice(-4));
                  return i
                }().join("")
            }, e.prototype.toUnicodeCmap = function() {
              var t, e, r, i, o, a, s, u, c, l;
              for (t = this.document.ref(), i = [], a = 0, u = (l = this.unicode).length; a < u; a++) {
                for (r = [], s = 0, c = (e = l[a]).length; s < c; s++)(o = e[s]) > 65535 && (o -= 65536, r.push(n(o >>> 10 & 1023 | 55296)), o = 56320 | 1023 & o), r.push(n(o));
                i.push("<" + r.join(" ") + ">")
              }
              return t.end("/CIDInit /ProcSet findresource begin\n12 dict begin\nbegincmap\n/CIDSystemInfo <<\n  /Registry (Adobe)\n  /Ordering (UCS)\n  /Supplement 0\n>> def\n/CMapName /Adobe-Identity-UCS def\n/CMapType 2 def\n1 begincodespacerange\n<0000><ffff>\nendcodespacerange\n1 beginbfrange\n<0000> <" + n(i.length - 1) + "> [" + i.join(" ") + "]\nendbfrange\nendcmap\nCMapName currentdict /CMap defineresource pop\nend\nend"), t
            }, e
          }(), e.localExports = r
        }, {
          "../font": 3,
          "../object": 18
        }],
        6: [function(t, e, n) {
          var r, i, o, a = {}.hasOwnProperty,
            s = function(t, e) {
              function n() {
                this.constructor = t
              }
              for (var r in e) a.call(e, r) && (t[r] = e[r]);
              return n.prototype = e.prototype, t.prototype = new n, t.__super__ = e.prototype, t
            };
          r = t("./afm"), i = t("../font"), t("fs"), o = function(t) {
            function e(t, e, i) {
              var o;
              this.document = t, this.name = e, this.id = i, this.font = new r(n[this.name]()), o = this.font, this.ascender = o.ascender, this.descender = o.descender, this.bbox = o.bbox, this.lineGap = o.lineGap
            }
            var n;
            return s(e, i), e.prototype.embed = function() {
              return this.dictionary.data = {
                Type: "Font",
                BaseFont: this.name,
                Subtype: "Type1",
                Encoding: "WinAnsiEncoding"
              }, this.dictionary.end()
            }, e.prototype.encode = function(t) {
              var e, n, r, i, o, a, s, u;
              for (n = this.font.encodeText(t), i = this.font.glyphsForString("" + t), e = this.font.advancesForGlyphs(i), a = [], o = s = 0, u = i.length; s < u; o = ++s) r = i[o], a.push({
                xAdvance: e[o],
                yAdvance: 0,
                xOffset: 0,
                yOffset: 0,
                advanceWidth: this.font.widthOfGlyph(r)
              });
              return [n, a]
            }, e.prototype.widthOfString = function(t, e) {
              var n, r, i, o, a, s;
              for (r = this.font.glyphsForString("" + t), o = 0, a = 0, s = (n = this.font.advancesForGlyphs(r)).length; a < s; a++) o += n[a];
              return i = e / 1e3, o * i
            }, e.isStandardFont = function(t) {
              return t in n
            }, n = {
              Courier: function() {
                return [0, "-23:-250:715:805", 562, 426, 629, -157, "0-314:600"]
              },
              "Courier-Bold": function() {
                return [0, "-113:-250:749:801", 562, 439, 629, -157, "0-314:600"]
              },
              "Courier-Oblique": function() {
                return [-12, "-27:-250:849:805", 562, 426, 629, -157, "0-314:600"]
              },
              "Courier-BoldOblique": function() {
                return [-12, "-57:-250:869:801", 562, 439, 629, -157, "0-314:600"]
              },
              Helvetica: function() {
                return [0, "-166:-225:1000:931", 718, 523, 718, -207, "278,278,355,556,556,889,667,222,333,333,389,584,278,333,278,278,556,556,556,556,556,556,556,556,556,556,278,278,584,584,584,556,1015,667,667,722,722,667,611,778,722,278,500,667,556,833,722,778,667,778,722,667,611,722,667,944,667,667,611,278,278,278,469,556,222,556,556,500,556,556,278,556,556,222,222,500,222,833,556,556,556,556,333,500,278,556,500,722,500,500,500,334,260,334,584,333,556,556,167,556,556,556,556,191,333,556,333,333,500,500,556,556,556,278,537,350,222,333,333,556,1000,1000,611,333,333,333,333,333,333,333,333,333,333,333,333,333,1000,1000,370,556,778,1000,365,889,278,222,611,944,611,278,556,556,556,556,667,584,667,667,556,722,500,500,556,722,722,556,722,556,667,722,250,737,667,500,556,722,222,556,611,722,556,667,500,500,278,471,722,778,556,556,667,333,500,611,667,778,722,667,643,722,556,333,778,667,667,584,556,611,476,500,722,278,667,556,556,500,556,556,722,278,584,260,737,778,278,600,667,333,556,611,611,549,722,722,222,317,556,722,667,667,556,500,222,778,556,556,500,278,778,722,612,556,333,778,556,278,556,667,556,834,667,299,667,556,1000,556,278,278,556,834,549,556,556,722,667,556,556,834,667,667,778,400,556,722,556,453,722,333,722,556,722,556,667,667,667,778,500,667,278,500,584,278,556,278,584,556,556,549,556,556,500,556,333,278,556"]
              },
              "Helvetica-Bold": function() {
                return [0, "-170:-228:1003:962", 718, 532, 718, -207, "278,333,474,556,556,889,722,278,333,333,389,584,278,333,278,278,556,556,556,556,556,556,556,556,556,556,333,333,584,584,584,611,975,722,722,722,722,667,611,778,722,278,556,722,611,833,722,778,667,778,722,667,611,722,667,944,667,667,611,333,278,333,584,556,278,556,611,556,611,556,333,611,611,278,278,556,278,889,611,611,611,611,389,556,333,611,556,778,556,556,500,389,280,389,584,333,556,556,167,556,556,556,556,238,500,556,333,333,611,611,556,556,556,278,556,350,278,500,500,556,1000,1000,611,333,333,333,333,333,333,333,333,333,333,333,333,333,1000,1000,370,611,778,1000,365,889,278,278,611,944,611,278,556,556,611,556,667,584,667,722,556,722,556,556,556,722,722,556,722,611,667,722,250,737,667,556,556,722,278,556,611,722,556,667,556,556,278,494,722,778,611,556,722,389,556,611,667,778,722,667,743,722,611,333,778,722,722,584,611,611,494,556,722,278,667,556,556,556,611,611,722,278,584,280,737,778,278,600,667,389,611,611,611,549,722,722,278,389,556,722,722,722,556,500,278,778,611,556,556,278,778,722,612,611,333,778,611,278,611,667,611,834,667,400,722,611,1000,556,278,278,611,834,549,611,611,722,667,556,611,834,667,667,778,400,611,722,611,549,722,389,722,611,722,611,722,722,722,778,500,667,278,556,584,278,611,333,584,611,611,549,611,611,500,611,333,278,556"]
              },
              "Helvetica-Oblique": function() {
                return [-12, "-170:-225:1116:931", 718, 523, 718, -207, "278,278,355,556,556,889,667,222,333,333,389,584,278,333,278,278,556,556,556,556,556,556,556,556,556,556,278,278,584,584,584,556,1015,667,667,722,722,667,611,778,722,278,500,667,556,833,722,778,667,778,722,667,611,722,667,944,667,667,611,278,278,278,469,556,222,556,556,500,556,556,278,556,556,222,222,500,222,833,556,556,556,556,333,500,278,556,500,722,500,500,500,334,260,334,584,333,556,556,167,556,556,556,556,191,333,556,333,333,500,500,556,556,556,278,537,350,222,333,333,556,1000,1000,611,333,333,333,333,333,333,333,333,333,333,333,333,333,1000,1000,370,556,778,1000,365,889,278,222,611,944,611,278,556,556,556,556,667,584,667,667,556,722,500,500,556,722,722,556,722,556,667,722,250,737,667,500,556,722,222,556,611,722,556,667,500,500,278,471,722,778,556,556,667,333,500,611,667,778,722,667,643,722,556,333,778,667,667,584,556,611,476,500,722,278,667,556,556,500,556,556,722,278,584,260,737,778,278,600,667,333,556,611,611,549,722,722,222,317,556,722,667,667,556,500,222,778,556,556,500,278,778,722,612,556,333,778,556,278,556,667,556,834,667,299,667,556,1000,556,278,278,556,834,549,556,556,722,667,556,556,834,667,667,778,400,556,722,556,453,722,333,722,556,722,556,667,667,667,778,500,667,278,500,584,278,556,278,584,556,556,549,556,556,500,556,333,278,556"]
              },
              "Helvetica-BoldOblique": function() {
                return [-12, "-174:-228:1114:962", 718, 532, 718, -207, "278,333,474,556,556,889,722,278,333,333,389,584,278,333,278,278,556,556,556,556,556,556,556,556,556,556,333,333,584,584,584,611,975,722,722,722,722,667,611,778,722,278,556,722,611,833,722,778,667,778,722,667,611,722,667,944,667,667,611,333,278,333,584,556,278,556,611,556,611,556,333,611,611,278,278,556,278,889,611,611,611,611,389,556,333,611,556,778,556,556,500,389,280,389,584,333,556,556,167,556,556,556,556,238,500,556,333,333,611,611,556,556,556,278,556,350,278,500,500,556,1000,1000,611,333,333,333,333,333,333,333,333,333,333,333,333,333,1000,1000,370,611,778,1000,365,889,278,278,611,944,611,278,556,556,611,556,667,584,667,722,556,722,556,556,556,722,722,556,722,611,667,722,250,737,667,556,556,722,278,556,611,722,556,667,556,556,278,494,722,778,611,556,722,389,556,611,667,778,722,667,743,722,611,333,778,722,722,584,611,611,494,556,722,278,667,556,556,556,611,611,722,278,584,280,737,778,278,600,667,389,611,611,611,549,722,722,278,389,556,722,722,722,556,500,278,778,611,556,556,278,778,722,612,611,333,778,611,278,611,667,611,834,667,400,722,611,1000,556,278,278,611,834,549,611,611,722,667,556,611,834,667,667,778,400,611,722,611,549,722,389,722,611,722,611,722,722,722,778,500,667,278,556,584,278,611,333,584,611,611,549,611,611,500,611,333,278,556"]
              },
              "Times-Roman": function() {
                return [0, "-168:-218:1000:898", 662, 450, 683, -217, "250,333,408,500,500,833,778,333,333,333,500,564,250,333,250,278,500,500,500,500,500,500,500,500,500,500,278,278,564,564,564,444,921,722,667,667,722,611,556,722,722,333,389,722,611,889,722,722,556,722,667,556,611,722,722,944,722,722,611,333,278,333,469,500,333,444,500,444,500,444,333,500,500,278,278,500,278,778,500,500,500,500,333,389,278,500,500,722,500,500,444,480,200,480,541,333,500,500,167,500,500,500,500,180,444,500,333,333,556,556,500,500,500,250,453,350,333,444,444,500,1000,1000,444,333,333,333,333,333,333,333,333,333,333,333,333,333,1000,889,276,611,722,889,310,667,278,278,500,722,500,333,444,444,500,444,722,564,722,722,444,722,500,389,444,722,722,444,722,500,611,722,250,760,611,444,444,722,278,444,611,667,444,611,389,389,278,471,667,722,500,444,722,333,444,611,556,722,667,556,588,722,500,300,722,722,722,564,500,611,476,500,722,278,611,444,444,444,500,500,722,333,564,200,760,722,333,600,611,333,500,611,611,549,722,667,278,326,444,722,722,722,444,444,278,722,500,444,389,278,722,722,612,500,300,722,500,278,500,611,500,750,556,344,722,611,980,444,333,333,611,750,549,500,500,722,611,444,500,750,556,556,722,400,500,667,500,453,722,333,722,500,667,611,722,722,722,722,444,611,333,500,564,333,500,278,564,500,500,549,500,500,444,500,300,278,500"]
              },
              "Times-Bold": function() {
                return [0, "-168:-218:1000:935", 676, 461, 683, -217, "250,333,555,500,500,1000,833,333,333,333,500,570,250,333,250,278,500,500,500,500,500,500,500,500,500,500,333,333,570,570,570,500,930,722,667,722,722,667,611,778,778,389,500,778,667,944,722,778,611,778,722,556,667,722,722,1000,722,722,667,333,278,333,581,500,333,500,556,444,556,444,333,500,556,278,333,556,278,833,556,500,556,556,444,389,333,556,500,722,500,500,444,394,220,394,520,333,500,500,167,500,500,500,500,278,500,500,333,333,556,556,500,500,500,250,540,350,333,500,500,500,1000,1000,500,333,333,333,333,333,333,333,333,333,333,333,333,333,1000,1000,300,667,778,1000,330,722,278,278,500,722,556,389,444,500,556,444,722,570,722,722,500,722,500,389,444,722,722,500,722,556,667,722,250,747,667,444,500,722,278,500,667,722,500,667,389,389,278,494,722,778,556,500,722,444,444,667,611,778,722,556,672,722,556,300,778,722,722,570,556,667,494,500,722,278,667,500,444,444,556,556,722,389,570,220,747,778,389,600,667,444,500,667,667,549,722,722,278,416,444,722,722,722,444,444,278,778,500,500,389,278,778,722,612,556,300,778,556,278,500,667,556,750,556,394,778,667,1000,444,389,389,667,750,549,500,556,722,667,444,500,750,556,556,778,400,500,722,556,549,722,444,722,500,722,667,722,722,722,778,444,667,389,556,570,389,556,333,570,500,556,549,500,500,444,556,300,278,500"]
              },
              "Times-Italic": function() {
                return [-15.5, "-169:-217:1010:883", 653, 441, 683, -217, "250,333,420,500,500,833,778,333,333,333,500,675,250,333,250,278,500,500,500,500,500,500,500,500,500,500,333,333,675,675,675,500,920,611,611,667,722,611,611,722,722,333,444,667,556,833,667,722,611,722,611,500,556,722,611,833,611,556,556,389,278,389,422,500,333,500,500,444,500,444,278,500,500,278,278,444,278,722,500,500,500,500,389,389,278,500,444,667,444,444,389,400,275,400,541,389,500,500,167,500,500,500,500,214,556,500,333,333,500,500,500,500,500,250,523,350,333,556,556,500,889,1000,500,333,333,333,333,333,333,333,333,333,333,333,333,333,889,889,276,556,722,944,310,667,278,278,500,667,500,333,444,500,500,444,556,675,556,611,500,722,444,389,444,722,722,500,722,500,611,722,250,760,611,444,500,667,278,500,556,667,500,611,389,389,278,471,611,722,500,500,611,389,444,556,611,722,611,500,544,722,500,300,722,611,611,675,500,556,476,444,667,278,611,500,444,444,500,500,667,333,675,275,760,722,333,600,611,389,500,556,556,549,722,667,278,300,444,722,611,611,444,389,278,722,500,500,389,278,722,722,612,500,300,722,500,278,500,611,500,750,500,300,667,556,980,444,333,333,611,750,549,500,500,722,611,444,500,750,500,500,722,400,500,667,500,453,722,389,667,500,611,556,611,611,611,722,389,611,333,444,675,333,500,278,675,500,500,549,500,500,389,500,300,278,500"]
              },
              "Times-BoldItalic": function() {
                return [-15, "-200:-218:996:921", 669, 462, 683, -217, "250,389,555,500,500,833,778,333,333,333,500,570,250,333,250,278,500,500,500,500,500,500,500,500,500,500,333,333,570,570,570,500,832,667,667,667,722,667,667,722,778,389,500,667,611,889,722,722,611,722,667,556,611,722,667,889,667,611,611,333,278,333,570,500,333,500,500,444,500,444,333,500,556,278,278,500,278,778,556,500,500,500,389,389,278,556,444,667,500,444,389,348,220,348,570,389,500,500,167,500,500,500,500,278,500,500,333,333,556,556,500,500,500,250,500,350,333,500,500,500,1000,1000,500,333,333,333,333,333,333,333,333,333,333,333,333,333,1000,944,266,611,722,944,300,722,278,278,500,722,500,389,444,500,556,444,611,570,611,667,500,722,444,389,444,722,722,500,722,556,667,722,250,747,667,444,500,722,278,500,611,667,500,667,389,389,278,494,667,722,556,500,667,389,444,611,611,722,667,556,608,722,556,300,722,667,667,570,556,611,494,444,722,278,667,500,444,444,556,556,722,389,570,220,747,722,389,600,667,389,500,611,611,549,722,667,278,366,444,722,667,667,444,389,278,722,500,500,389,278,722,722,612,500,300,722,576,278,500,667,500,750,556,382,667,611,1000,444,389,389,611,750,549,500,556,722,667,444,500,750,556,556,722,400,500,667,556,549,722,389,722,500,667,611,667,667,667,722,389,667,389,500,606,389,556,278,606,500,556,549,500,500,389,556,300,278,500"]
              },
              Symbol: function() {
                return [0, "-180:-293:1090:1010", 0, 0, 0, 0, "250,333,315:713,3:500,316:549,5:833,778,317:439,8:333,333,318:500,11:549,250,300:549,14:250,278,500,500,500,500,500,500,500,500,500,500,278,278,549,549,549,444,319:549,722,667,722,250:612,323:611,763,603,722,333,631,722,686,889,722,722,768,741,556,592,611,690,439,768,645,795,611,59:333,345:863,61:333,346:658,63:500,347:500,631,549,549,494,439,521,411,603,329,603,549,549,254:576,360:521,549,549,521,549,603,439,576,713,686,493,686,494,91:480,200,480,373:549,314:750,374:620,247,270:549,98:167,376:713,100:500,377:753,753,753,753,1042,987,603,987,603,281:400,220:549,386:411,231:549,205:549,387:713,208:494,115:460,155:549,307:549,388:549,549,120:1000,390:603,1000,658,823,686,795,987,768,768,823,768,768,713,713,713,713,713,713,713,768,713,790,790,890,823,285:549,415:250,304:713,416:603,603,1042,987,603,987,603,185:494,423:329,790,790,786,225:713,427:384,384,384,384,384,384,494,494,494,494,329,274,686,686,686,384,384,384,384,384,384,494,494,494,790"]
              },
              ZapfDingbats: function() {
                return [0, "-1:-143:981:820", 0, 0, 0, 0, "278,452:974,961,974,980,719,789,790,791,690,960,939,549,855,911,933,911,945,974,755,846,762,761,571,677,763,760,759,754,494,552,537,577,692,786,788,788,790,793,794,816,823,789,841,823,833,816,831,923,744,723,749,790,792,695,776,768,792,759,707,708,682,701,826,815,789,789,707,687,696,689,786,787,713,791,785,791,873,761,762,762,759,759,892,892,788,784,438,138,277,415,392,392,668,668,390,390,317,317,276,276,509,509,410,410,234,234,334,334,732,544,544,910,667,760,760,776,595,694,626,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,788,894,838,1016,458,748,924,748,918,927,928,928,834,873,828,924,924,917,930,931,463,883,836,836,867,867,696,696,874,874,760,946,771,865,771,888,967,888,831,873,927,970,918"]
              }
            }, e
          }(), e.localExports = o
        }, {
          "../font": 3,
          "./afm": 4,
          fs: 29
        }],
        7: [function(t, e, n) {
          var r, i, o, a = {}.hasOwnProperty,
            s = function(t, e) {
              function n() {
                this.constructor = t
              }
              for (var r in e) a.call(e, r) && (t[r] = e[r]);
              return n.prototype = e.prototype, t.prototype = new n, t.__super__ = e.prototype, t
            };
          r = function() {
            function t(t) {
              this.doc = t, this.stops = [], this.embedded = !1, this.transform = [1, 0, 0, 1, 0, 0], this._colorSpace = "DeviceRGB"
            }
            return t.prototype.stop = function(t, e, n) {
              return null == n && (n = 1), n = Math.max(0, Math.min(1, n)), this.stops.push([t, this.doc._normalizeColor(e), n]), this
            }, t.prototype.setTransform = function(t, e, n, r, i, o) {
              return this.transform = [t, e, n, r, i, o], this
            }, t.prototype.embed = function(t) {
              var e, n, r, i, o, a, s, u, c, l, h, f, d, p, g, _, m, v, y, b;
              if (0 !== this.stops.length) {
                for (this.embedded = !0, this.matrix = t, (u = this.stops[this.stops.length - 1])[0] < 1 && this.stops.push([1, u[1], u[2]]), e = [], n = [], p = [], s = _ = 0, y = this.stops.length - 1; 0 <= y ? _ < y : _ > y; s = 0 <= y ? ++_ : --_) n.push(0, 1), s + 2 !== this.stops.length && e.push(this.stops[s + 1][0]), r = this.doc.ref({
                  FunctionType: 2,
                  Domain: [0, 1],
                  C0: this.stops[s + 0][1],
                  C1: this.stops[s + 1][1],
                  N: 1
                }), p.push(r), r.end();
                if (1 === p.length ? r = p[0] : (r = this.doc.ref({
                  FunctionType: 3,
                  Domain: [0, 1],
                  Functions: p,
                  Bounds: e,
                  Encode: n
                })).end(), this.id = "Sh" + ++this.doc._gradCount, (f = this.shader(r)).end(), (h = this.doc.ref({
                  Type: "Pattern",
                  PatternType: 2,
                  Shading: f,
                  Matrix: function() {
                    var t, e, n, r;
                    for (r = [], t = 0, e = (n = this.matrix).length; t < e; t++) g = n[t], r.push(+g.toFixed(5));
                    return r
                  }.call(this)
                })).end(), this.stops.some(function(t) {
                  return t[2] < 1
                })) {
                  for ((o = this.opacityGradient())._colorSpace = "DeviceGray", m = 0, v = (b = this.stops).length; m < v; m++) d = b[m], o.stop(d[0], [d[2]]);
                  o = o.embed(this.matrix), l = [0, 0, this.doc.page.width, this.doc.page.height], (i = this.doc.ref({
                    Type: "XObject",
                    Subtype: "Form",
                    FormType: 1,
                    BBox: l,
                    Group: {
                      Type: "Group",
                      S: "Transparency",
                      CS: "DeviceGray"
                    },
                    Resources: {
                      ProcSet: ["PDF", "Text", "ImageB", "ImageC", "ImageI"],
                      Pattern: {
                        Sh1: o
                      }
                    }
                  })).write("/Pattern cs /Sh1 scn"), i.end(l.join(" ") + " re f"), (a = this.doc.ref({
                    Type: "ExtGState",
                    SMask: {
                      Type: "Mask",
                      S: "Luminosity",
                      G: i
                    }
                  })).end(), (c = this.doc.ref({
                    Type: "Pattern",
                    PatternType: 1,
                    PaintType: 1,
                    TilingType: 2,
                    BBox: l,
                    XStep: l[2],
                    YStep: l[3],
                    Resources: {
                      ProcSet: ["PDF", "Text", "ImageB", "ImageC", "ImageI"],
                      Pattern: {
                        Sh1: h
                      },
                      ExtGState: {
                        Gs1: a
                      }
                    }
                  })).write("/Gs1 gs /Pattern cs /Sh1 scn"), c.end(l.join(" ") + " re f"), this.doc.page.patterns[this.id] = c
                } else this.doc.page.patterns[this.id] = h;
                return h
              }
            }, t.prototype.apply = function(t) {
              var e, n, r, i, o, a, s, u, c, l, h, f, d, p, g;
              return p = this.doc._ctm.slice(), i = p[0], o = p[1], u = p[2], h = p[3], f = p[4], d = p[5], g = this.transform, a = g[0], s = g[1], c = g[2], l = g[3], e = g[4], n = g[5], r = [i * a + u * s, o * a + h * s, i * c + u * l, o * c + h * l, i * e + u * n + f, o * e + h * n + d], this.embedded && r.join(" ") === this.matrix.join(" ") || this.embed(r), this.doc.addContent("/" + this.id + " " + t)
            }, t
          }(), i = function(t) {
            function e(t, n, r, i, o) {
              this.doc = t, this.x1 = n, this.y1 = r, this.x2 = i, this.y2 = o, e.__super__.constructor.apply(this, arguments)
            }
            return s(e, r), e.prototype.shader = function(t) {
              return this.doc.ref({
                ShadingType: 2,
                ColorSpace: this._colorSpace,
                Coords: [this.x1, this.y1, this.x2, this.y2],
                Function: t,
                Extend: [!0, !0]
              })
            }, e.prototype.opacityGradient = function() {
              return new e(this.doc, this.x1, this.y1, this.x2, this.y2)
            }, e
          }(), o = function(t) {
            function e(t, n, r, i, o, a, s) {
              this.doc = t, this.x1 = n, this.y1 = r, this.r1 = i, this.x2 = o, this.y2 = a, this.r2 = s, e.__super__.constructor.apply(this, arguments)
            }
            return s(e, r), e.prototype.shader = function(t) {
              return this.doc.ref({
                ShadingType: 3,
                ColorSpace: this._colorSpace,
                Coords: [this.x1, this.y1, this.r1, this.x2, this.y2, this.r2],
                Function: t,
                Extend: [!0, !0]
              })
            }, e.prototype.opacityGradient = function() {
              return new e(this.doc, this.x1, this.y1, this.r1, this.x2, this.y2, this.r2)
            }, e
          }(), e.localExports = {
            PDFGradient: r,
            PDFLinearGradient: i,
            PDFRadialGradient: o
          }
        }, {}],
        8: [function(t, e, n) {
          (function(n) {
            var r, i, o, a;
            a = t("fs"), t("./data"), r = t("./image/jpeg"), o = t("./image/png"), i = function() {
              function t() {}
              return t.open = function(t, e) {
                var i, s;
                if (n.isBuffer(t)) i = t;
                else if (t instanceof ArrayBuffer) i = new n(new Uint8Array(t));
                else if (s = /^data:.+;base64,(.*)$/.exec(t)) i = new n(s[1], "base64");
                else if (!(i = a.readFileSync(t))) return;
                if (255 === i[0] && 216 === i[1]) return new r(i, e);
                if (137 === i[0] && "PNG" === i.toString("ascii", 1, 4)) return new o(i, e);
                throw new Error("Unknown image format.")
              }, t
            }(), e.localExports = i
          }).call(this, t("buffer").Buffer)
        }, {
          "./data": 1,
          "./image/jpeg": 9,
          "./image/png": 10,
          buffer: 31,
          fs: 29
        }],
        9: [function(t, e, n) {
          var r, i = [].indexOf || function(t) {
            for (var e = 0, n = this.length; e < n; e++)
              if (e in this && this[e] === t) return e;
            return -1
          };
          t("fs"), r = function() {
            function t(t, n) {
              var r, o, a;
              if (this.data = t, this.label = n, 65496 !== this.data.readUInt16BE(0)) throw "SOI not found in JPEG";
              for (a = 2; a < this.data.length && (o = this.data.readUInt16BE(a), a += 2, !(i.call(e, o) >= 0));) a += this.data.readUInt16BE(a);
              if (i.call(e, o) < 0) throw "Invalid JPEG.";
              a += 2, this.bits = this.data[a++], this.height = this.data.readUInt16BE(a), a += 2, this.width = this.data.readUInt16BE(a), a += 2, r = this.data[a++], this.colorSpace = function() {
                switch (r) {
                  case 1:
                    return "DeviceGray";
                  case 3:
                    return "DeviceRGB";
                  case 4:
                    return "DeviceCMYK"
                }
              }(), this.obj = null
            }
            var e;
            return e = [65472, 65473, 65474, 65475, 65477, 65478, 65479, 65480, 65481, 65482, 65483, 65484, 65485, 65486, 65487], t.prototype.embed = function(t) {
              if (!this.obj) return this.obj = t.ref({
                Type: "XObject",
                Subtype: "Image",
                BitsPerComponent: this.bits,
                Width: this.width,
                Height: this.height,
                ColorSpace: this.colorSpace,
                Filter: "DCTDecode"
              }), "DeviceCMYK" === this.colorSpace && (this.obj.data.Decode = [1, 0, 1, 0, 1, 0, 1, 0]), this.obj.end(this.data), this.data = null
            }, t
          }(), e.localExports = r
        }, {
          fs: 29
        }],
        10: [function(t, e, n) {
          (function(n) {
            var r, i, o;
            o = t("zlib"), r = t("png-js"), i = function() {
              function t(t, e) {
                this.label = e, this.image = new r(t), this.width = this.image.width, this.height = this.image.height, this.imgData = this.image.imgData, this.obj = null
              }
              return t.prototype.embed = function(t) {
                var e, r, i, o, a, s, u, c;
                if (this.document = t, !this.obj) {
                  if (this.obj = this.document.ref({
                    Type: "XObject",
                    Subtype: "Image",
                    BitsPerComponent: this.image.bits,
                    Width: this.width,
                    Height: this.height,
                    Filter: "FlateDecode"
                  }), this.image.hasAlphaChannel || (i = this.document.ref({
                    Predictor: 15,
                    Colors: this.image.colors,
                    BitsPerComponent: this.image.bits,
                    Columns: this.width
                  }), this.obj.data.DecodeParms = i, i.end()), 0 === this.image.palette.length ? this.obj.data.ColorSpace = this.image.colorSpace : ((r = this.document.ref()).end(new n(this.image.palette)), this.obj.data.ColorSpace = ["Indexed", "DeviceRGB", this.image.palette.length / 3 - 1, r]), this.image.transparency.grayscale) return a = this.image.transparency.greyscale, this.obj.data.Mask = [a, a];
                  if (this.image.transparency.rgb) {
                    for (e = [], u = 0, c = (o = this.image.transparency.rgb).length; u < c; u++) s = o[u], e.push(s, s);
                    return this.obj.data.Mask = e
                  }
                  return this.image.transparency.indexed ? this.loadIndexedAlphaChannel() : this.image.hasAlphaChannel ? this.splitAlphaChannel() : this.finalize()
                }
              }, t.prototype.finalize = function() {
                var t;
                return this.alphaChannel && ((t = this.document.ref({
                  Type: "XObject",
                  Subtype: "Image",
                  Height: this.height,
                  Width: this.width,
                  BitsPerComponent: 8,
                  Filter: "FlateDecode",
                  ColorSpace: "DeviceGray",
                  Decode: [0, 1]
                })).end(this.alphaChannel), this.obj.data.SMask = t), this.obj.end(this.imgData), this.image = null, this.imgData = null
              }, t.prototype.splitAlphaChannel = function() {
                return this.image.decodePixels(function(t) {
                  return function(e) {
                    var r, i, a, s, u, c, l, h, f;
                    for (a = t.image.colors * t.image.bits / 8, f = t.width * t.height, c = new n(f * a), i = new n(f), u = h = r = 0, l = e.length; u < l;) c[h++] = e[u++], c[h++] = e[u++], c[h++] = e[u++], i[r++] = e[u++];
                    return s = 0, o.deflate(c, function(e, n) {
                      if (t.imgData = n, e) throw e;
                      if (2 == ++s) return t.finalize()
                    }), o.deflate(i, function(e, n) {
                      if (t.alphaChannel = n, e) throw e;
                      if (2 == ++s) return t.finalize()
                    })
                  }
                }(this))
              }, t.prototype.loadIndexedAlphaChannel = function(t) {
                var e;
                return e = this.image.transparency.indexed, this.image.decodePixels(function(t) {
                  return function(r) {
                    var i, a, s, u, c;
                    for (i = new n(t.width * t.height), a = 0, s = u = 0, c = r.length; u < c; s = u += 1) i[a++] = e[r[s]];
                    return o.deflate(i, function(e, n) {
                      if (t.alphaChannel = n, e) throw e;
                      return t.finalize()
                    })
                  }
                }(this))
              }, t
            }(), e.localExports = i
          }).call(this, t("buffer").Buffer)
        }, {
          buffer: 31,
          "png-js": 204,
          zlib: 28
        }],
        11: [function(t, e, n) {
          var r, i, o, a = {}.hasOwnProperty,
            s = function(t, e) {
              function n() {
                this.constructor = t
              }
              for (var r in e) a.call(e, r) && (t[r] = e[r]);
              return n.prototype = e.prototype, t.prototype = new n, t.__super__ = e.prototype, t
            };
          r = t("events").EventEmitter, i = t("linebreak"), o = function(t) {
            function e(t, e) {
              var n;
              this.document = t, this.indent = e.indent || 0, this.characterSpacing = e.characterSpacing || 0, this.wordSpacing = 0 === e.wordSpacing, this.columns = e.columns || 1, this.columnGap = null != (n = e.columnGap) ? n : 18, this.lineWidth = (e.width - this.columnGap * (this.columns - 1)) / this.columns, this.spaceLeft = this.lineWidth, this.startX = this.document.x, this.startY = this.document.y, this.column = 1, this.ellipsis = e.ellipsis, this.continuedX = 0, this.features = e.features, null != e.height ? (this.height = e.height, this.maxY = this.startY + e.height) : this.maxY = this.document.page.maxY(), this.on("firstLine", function(t) {
                return function(e) {
                  var n;
                  return n = t.continuedX || t.indent, t.document.x += n, t.lineWidth -= n, t.once("line", function() {
                    if (t.document.x -= n, t.lineWidth += n, e.continued && !t.continuedX && (t.continuedX = t.indent), !e.continued) return t.continuedX = 0
                  })
                }
              }(this)), this.on("lastLine", function(t) {
                return function(e) {
                  var n;
                  return "justify" === (n = e.align) && (e.align = "left"), t.lastLine = !0, t.once("line", function() {
                    return t.document.y += e.paragraphGap || 0, e.align = n, t.lastLine = !1
                  })
                }
              }(this))
            }
            return s(e, r), e.prototype.wordWidth = function(t) {
              return this.document.widthOfString(t, this) + this.characterSpacing + this.wordSpacing
            }, e.prototype.eachWord = function(t, e) {
              var n, r, o, a, s, u, c, l, h, f;
              for (r = new i(t), s = null, f = Object.create(null); n = r.nextBreak();) {
                if (h = t.slice((null != s ? s.position : void 0) || 0, n.position), (l = null != f[h] ? f[h] : f[h] = this.wordWidth(h)) > this.lineWidth + this.continuedX)
                  for (u = s, o = {}; h.length;) {
                    for (a = h.length; l > this.spaceLeft;) l = this.wordWidth(h.slice(0, --a));
                    if (o.required = a < h.length, c = e(h.slice(0, a), l, o, u), u = {
                      required: !1
                    }, h = h.slice(a), l = this.wordWidth(h), !1 === c) break
                  } else c = e(h, l, n, s);
                if (!1 === c) break;
                s = n
              }
            }, e.prototype.wrap = function(t, e) {
              var n, r, i, o, a, s, u;
              return null != e.indent && (this.indent = e.indent), null != e.characterSpacing && (this.characterSpacing = e.characterSpacing), null != e.wordSpacing && (this.wordSpacing = e.wordSpacing), null != e.ellipsis && (this.ellipsis = e.ellipsis), o = this.document.y + this.document.currentLineHeight(!0), (this.document.y > this.maxY || o > this.maxY) && this.nextSection(), n = "", a = 0, s = 0, i = 0, u = this.document.y, r = function(t) {
                return function() {
                  return e.textWidth = a + t.wordSpacing * (s - 1), e.wordCount = s, e.lineWidth = t.lineWidth, u = t.document.y, t.emit("line", n, e, t), i++
                }
              }(this), this.emit("sectionStart", e, this), this.eachWord(t, function(t) {
                return function(i, o, u, c) {
                  var l;
                  if ((null == c || c.required) && (t.emit("firstLine", e, t), t.spaceLeft = t.lineWidth), o <= t.spaceLeft && (n += i, a += o, s++), u.required || o > t.spaceLeft) {
                    if (u.required && t.emit("lastLine", e, t), l = t.document.currentLineHeight(!0), null != t.height && t.ellipsis && t.document.y + 2 * l > t.maxY && t.column >= t.columns) {
                      for (!0 === t.ellipsis && (t.ellipsis = "…"), n = n.replace(/\s+$/, ""), a = t.wordWidth(n + t.ellipsis); a > t.lineWidth;) n = n.slice(0, -1).replace(/\s+$/, ""), a = t.wordWidth(n + t.ellipsis);
                      n += t.ellipsis
                    }
                    return u.required && o > t.spaceLeft && (n = i, a = o, s = 1), r(), t.maxY - (t.document.y + l) < -1e-6 && !t.nextSection() ? (s = 0, n = "", !1) : u.required ? (t.spaceLeft = t.lineWidth, n = "", a = 0, s = 0) : (t.spaceLeft = t.lineWidth - o, n = i, a = o, s = 1)
                  }
                  return t.spaceLeft -= o
                }
              }(this)), s > 0 && (this.emit("lastLine", e, this), r()), this.emit("sectionEnd", e, this), !0 === e.continued ? (i > 1 && (this.continuedX = 0), this.continuedX += e.textWidth, this.document.y = u) : this.document.x = this.startX
            }, e.prototype.nextSection = function(t) {
              var e;
              if (this.emit("sectionEnd", t, this), ++this.column > this.columns) {
                if (null != this.height) return !1;
                this.document.addPage(), this.column = 1, this.startY = this.document.page.margins.top, this.maxY = this.document.page.maxY(), this.document.x = this.startX, this.document._fillColor && (e = this.document).fillColor.apply(e, this.document._fillColor), this.emit("pageBreak", t, this)
              } else this.document.x += this.lineWidth + this.columnGap, this.document.y = this.startY, this.emit("columnBreak", t, this);
              return this.emit("sectionStart", t, this), !0
            }, e
          }(), e.localExports = o
        }, {
          events: 32,
          linebreak: 191
        }],
        12: [function(t, e, n) {
          e.localExports = {
            annotate: function(t, e, n, r, i) {
              var o, a, s;
              i.Type = "Annot", i.Rect = this._convertRect(t, e, n, r), i.Border = [0, 0, 0], "Link" !== i.Subtype && null == i.C && (i.C = this._normalizeColor(i.color || [0, 0, 0])), delete i.color, "string" == typeof i.Dest && (i.Dest = new String(i.Dest));
              for (o in i) s = i[o], i[o[0].toUpperCase() + o.slice(1)] = s;
              return a = this.ref(i), this.page.annotations.push(a), a.end(), this
            },
            note: function(t, e, n, r, i, o) {
              return null == o && (o = {}), o.Subtype = "Text", o.Contents = new String(i), o.Name = "Comment", null == o.color && (o.color = [243, 223, 92]), this.annotate(t, e, n, r, o)
            },
            link: function(t, e, n, r, i, o) {
              return null == o && (o = {}), o.Subtype = "Link", o.A = this.ref({
                S: "URI",
                URI: new String(i)
              }), o.A.end(), this.annotate(t, e, n, r, o)
            },
            _markup: function(t, e, n, r, i) {
              var o, a, s, u, c;
              return null == i && (i = {}), c = this._convertRect(t, e, n, r), o = c[0], s = c[1], a = c[2], u = c[3], i.QuadPoints = [o, u, a, u, o, s, a, s], i.Contents = new String, this.annotate(t, e, n, r, i)
            },
            highlight: function(t, e, n, r, i) {
              return null == i && (i = {}), i.Subtype = "Highlight", null == i.color && (i.color = [241, 238, 148]), this._markup(t, e, n, r, i)
            },
            underline: function(t, e, n, r, i) {
              return null == i && (i = {}), i.Subtype = "Underline", this._markup(t, e, n, r, i)
            },
            strike: function(t, e, n, r, i) {
              return null == i && (i = {}), i.Subtype = "StrikeOut", this._markup(t, e, n, r, i)
            },
            lineAnnotation: function(t, e, n, r, i) {
              return null == i && (i = {}), i.Subtype = "Line", i.Contents = new String, i.L = [t, this.page.height - e, n, this.page.height - r], this.annotate(t, e, n, r, i)
            },
            rectAnnotation: function(t, e, n, r, i) {
              return null == i && (i = {}), i.Subtype = "Square", i.Contents = new String, this.annotate(t, e, n, r, i)
            },
            ellipseAnnotation: function(t, e, n, r, i) {
              return null == i && (i = {}), i.Subtype = "Circle", i.Contents = new String, this.annotate(t, e, n, r, i)
            },
            textAnnotation: function(t, e, n, r, i, o) {
              return null == o && (o = {}), o.Subtype = "FreeText", o.Contents = new String(i), o.DA = new String, this.annotate(t, e, n, r, o)
            },
            _convertRect: function(t, e, n, r) {
              var i, o, a, s, u, c, l, h, f;
              return h = e, e += r, l = t + n, f = this._ctm, i = f[0], o = f[1], a = f[2], s = f[3], u = f[4], c = f[5], t = i * t + a * e + u, e = o * t + s * e + c, l = i * l + a * h + u, h = o * l + s * h + c, [t, e, l, h]
            }
          }
        }, {}],
        13: [function(t, e, n) {
          var r, i, o, a, s;
          s = t("../gradient"), r = s.PDFGradient, i = s.PDFLinearGradient, o = s.PDFRadialGradient, e.localExports = {
            initColor: function() {
              return this._opacityRegistry = {}, this._opacityCount = 0, this._gradCount = 0
            },
            _normalizeColor: function(t) {
              var e, n;
              return t instanceof r ? t : ("string" == typeof t && ("#" === t.charAt(0) ? (4 === t.length && (t = t.replace(/#([0-9A-F])([0-9A-F])([0-9A-F])/i, "#$1$1$2$2$3$3")), e = parseInt(t.slice(1), 16), t = [e >> 16, e >> 8 & 255, 255 & e]) : a[t] && (t = a[t])), Array.isArray(t) ? (3 === t.length ? t = function() {
                var e, r, i;
                for (i = [], e = 0, r = t.length; e < r; e++) n = t[e], i.push(n / 255);
                return i
              }() : 4 === t.length && (t = function() {
                var e, r, i;
                for (i = [], e = 0, r = t.length; e < r; e++) n = t[e], i.push(n / 100);
                return i
              }()), t) : null)
            },
            _setColor: function(t, e) {
              var n, i;
              return !!(t = this._normalizeColor(t)) && (n = e ? "SCN" : "scn", t instanceof r ? (this._setColorSpace("Pattern", e), t.apply(n)) : (i = 4 === t.length ? "DeviceCMYK" : "DeviceRGB", this._setColorSpace(i, e), t = t.join(" "), this.addContent(t + " " + n)), !0)
            },
            _setColorSpace: function(t, e) {
              var n;
              return n = e ? "CS" : "cs", this.addContent("/" + t + " " + n)
            },
            fillColor: function(t, e) {
              return this._setColor(t, !1) && this.fillOpacity(e), this._fillColor = [t, e], this
            },
            strokeColor: function(t, e) {
              return this._setColor(t, !0) && this.strokeOpacity(e), this
            },
            opacity: function(t) {
              return this._doOpacity(t, t), this
            },
            fillOpacity: function(t) {
              return this._doOpacity(t, null), this
            },
            strokeOpacity: function(t) {
              return this._doOpacity(null, t), this
            },
            _doOpacity: function(t, e) {
              var n, r, i, o;
              if (null != t || null != e) return null != t && (t = Math.max(0, Math.min(1, t))), null != e && (e = Math.max(0, Math.min(1, e))), r = t + "_" + e, this._opacityRegistry[r] ? (n = (o = this._opacityRegistry[r])[0], i = o[1]) : (n = {
                Type: "ExtGState"
              }, null != t && (n.ca = t), null != e && (n.CA = e), (n = this.ref(n)).end(), i = "Gs" + ++this._opacityCount, this._opacityRegistry[r] = [n, i]), this.page.ext_gstates[i] = n, this.addContent("/" + i + " gs")
            },
            linearGradient: function(t, e, n, r) {
              return new i(this, t, e, n, r)
            },
            radialGradient: function(t, e, n, r, i, a) {
              return new o(this, t, e, n, r, i, a)
            }
          }, a = {
            aliceblue: [240, 248, 255],
            antiquewhite: [250, 235, 215],
            aqua: [0, 255, 255],
            aquamarine: [127, 255, 212],
            azure: [240, 255, 255],
            beige: [245, 245, 220],
            bisque: [255, 228, 196],
            black: [0, 0, 0],
            blanchedalmond: [255, 235, 205],
            blue: [0, 0, 255],
            blueviolet: [138, 43, 226],
            brown: [165, 42, 42],
            burlywood: [222, 184, 135],
            cadetblue: [95, 158, 160],
            chartreuse: [127, 255, 0],
            chocolate: [210, 105, 30],
            coral: [255, 127, 80],
            cornflowerblue: [100, 149, 237],
            cornsilk: [255, 248, 220],
            crimson: [220, 20, 60],
            cyan: [0, 255, 255],
            darkblue: [0, 0, 139],
            darkcyan: [0, 139, 139],
            darkgoldenrod: [184, 134, 11],
            darkgray: [169, 169, 169],
            darkgreen: [0, 100, 0],
            darkgrey: [169, 169, 169],
            darkkhaki: [189, 183, 107],
            darkmagenta: [139, 0, 139],
            darkolivegreen: [85, 107, 47],
            darkorange: [255, 140, 0],
            darkorchid: [153, 50, 204],
            darkred: [139, 0, 0],
            darksalmon: [233, 150, 122],
            darkseagreen: [143, 188, 143],
            darkslateblue: [72, 61, 139],
            darkslategray: [47, 79, 79],
            darkslategrey: [47, 79, 79],
            darkturquoise: [0, 206, 209],
            darkviolet: [148, 0, 211],
            deeppink: [255, 20, 147],
            deepskyblue: [0, 191, 255],
            dimgray: [105, 105, 105],
            dimgrey: [105, 105, 105],
            dodgerblue: [30, 144, 255],
            firebrick: [178, 34, 34],
            floralwhite: [255, 250, 240],
            forestgreen: [34, 139, 34],
            fuchsia: [255, 0, 255],
            gainsboro: [220, 220, 220],
            ghostwhite: [248, 248, 255],
            gold: [255, 215, 0],
            goldenrod: [218, 165, 32],
            gray: [128, 128, 128],
            grey: [128, 128, 128],
            green: [0, 128, 0],
            greenyellow: [173, 255, 47],
            honeydew: [240, 255, 240],
            hotpink: [255, 105, 180],
            indianred: [205, 92, 92],
            indigo: [75, 0, 130],
            ivory: [255, 255, 240],
            khaki: [240, 230, 140],
            lavender: [230, 230, 250],
            lavenderblush: [255, 240, 245],
            lawngreen: [124, 252, 0],
            lemonchiffon: [255, 250, 205],
            lightblue: [173, 216, 230],
            lightcoral: [240, 128, 128],
            lightcyan: [224, 255, 255],
            lightgoldenrodyellow: [250, 250, 210],
            lightgray: [211, 211, 211],
            lightgreen: [144, 238, 144],
            lightgrey: [211, 211, 211],
            lightpink: [255, 182, 193],
            lightsalmon: [255, 160, 122],
            lightseagreen: [32, 178, 170],
            lightskyblue: [135, 206, 250],
            lightslategray: [119, 136, 153],
            lightslategrey: [119, 136, 153],
            lightsteelblue: [176, 196, 222],
            lightyellow: [255, 255, 224],
            lime: [0, 255, 0],
            limegreen: [50, 205, 50],
            linen: [250, 240, 230],
            magenta: [255, 0, 255],
            maroon: [128, 0, 0],
            mediumaquamarine: [102, 205, 170],
            mediumblue: [0, 0, 205],
            mediumorchid: [186, 85, 211],
            mediumpurple: [147, 112, 219],
            mediumseagreen: [60, 179, 113],
            mediumslateblue: [123, 104, 238],
            mediumspringgreen: [0, 250, 154],
            mediumturquoise: [72, 209, 204],
            mediumvioletred: [199, 21, 133],
            midnightblue: [25, 25, 112],
            mintcream: [245, 255, 250],
            mistyrose: [255, 228, 225],
            moccasin: [255, 228, 181],
            navajowhite: [255, 222, 173],
            navy: [0, 0, 128],
            oldlace: [253, 245, 230],
            olive: [128, 128, 0],
            olivedrab: [107, 142, 35],
            orange: [255, 165, 0],
            orangered: [255, 69, 0],
            orchid: [218, 112, 214],
            palegoldenrod: [238, 232, 170],
            palegreen: [152, 251, 152],
            paleturquoise: [175, 238, 238],
            palevioletred: [219, 112, 147],
            papayawhip: [255, 239, 213],
            peachpuff: [255, 218, 185],
            peru: [205, 133, 63],
            pink: [255, 192, 203],
            plum: [221, 160, 221],
            powderblue: [176, 224, 230],
            purple: [128, 0, 128],
            red: [255, 0, 0],
            rosybrown: [188, 143, 143],
            royalblue: [65, 105, 225],
            saddlebrown: [139, 69, 19],
            salmon: [250, 128, 114],
            sandybrown: [244, 164, 96],
            seagreen: [46, 139, 87],
            seashell: [255, 245, 238],
            sienna: [160, 82, 45],
            silver: [192, 192, 192],
            skyblue: [135, 206, 235],
            slateblue: [106, 90, 205],
            slategray: [112, 128, 144],
            slategrey: [112, 128, 144],
            snow: [255, 250, 250],
            springgreen: [0, 255, 127],
            steelblue: [70, 130, 180],
            tan: [210, 180, 140],
            teal: [0, 128, 128],
            thistle: [216, 191, 216],
            tomato: [255, 99, 71],
            turquoise: [64, 224, 208],
            violet: [238, 130, 238],
            wheat: [245, 222, 179],
            white: [255, 255, 255],
            whitesmoke: [245, 245, 245],
            yellow: [255, 255, 0],
            yellowgreen: [154, 205, 50]
          }
        }, {
          "../gradient": 7
        }],
        14: [function(t, e, n) {
          var r;
          r = t("../font"), e.localExports = {
            initFonts: function() {
              return this._fontFamilies = {}, this._fontCount = 0, this._fontSize = 12, this._font = null, this._registeredFonts = {}, this.font("Helvetica")
            },
            font: function(t, e, n) {
              var i, o, a, s;
              return "number" == typeof e && (n = e, e = null), "string" == typeof t && this._registeredFonts[t] ? (i = t, t = (s = this._registeredFonts[t]).src, e = s.family) : "string" != typeof(i = e || t) && (i = null), null != n && this.fontSize(n), (o = this._fontFamilies[i]) ? (this._font = o, this) : (a = "F" + ++this._fontCount, this._font = r.open(this, t, e, a), (o = this._fontFamilies[this._font.name]) ? (this._font = o, this) : (i && (this._fontFamilies[i] = this._font), this._font.name && (this._fontFamilies[this._font.name] = this._font), this))
            },
            fontSize: function(t) {
              return this._fontSize = t, this
            },
            currentLineHeight: function(t) {
              return null == t && (t = !1), this._font.lineHeight(this._fontSize, t)
            },
            currentFontAscender: function() {
              return this._font.getAscender(this._fontSize)
            },
            currentFontBBox: function() {
              return this._font.getBBox(this._fontSize)
            },
            currentFontSize: function() {
              return this._fontSize
            },
            registerFont: function(t, e, n) {
              return this._registeredFonts[t] = {
                src: e,
                family: n
              }, this
            }
          }
        }, {
          "../font": 3
        }],
        15: [function(t, e, n) {
          var r;
          r = t("../image"), e.localExports = {
            initImages: function() {
              return this._imageRegistry = {}, this._imageCount = 0
            },
            image: function(t, e, n, r) {
              var i, o, a, s, u, c, l, h, f, d, p, g, _, m, v, y;
              return null == r && (r = {}), "object" == typeof e && (r = e, e = null), e = null != (_ = null != e ? e : r.x) ? _ : this.x, n = null != (m = null != n ? n : r.y) ? m : this.y, "string" == typeof t && (u = this._imageRegistry[t]), u || (u = t.width && t.height ? t : this.openImage(t)), u.obj || u.embed(this), null == (p = this.page.xobjects)[g = u.label] && (p[g] = u.obj), l = .75 * u.width, c = .75 * u.height, f = r.width || l, a = r.height || c, r.width && !r.height ? (f = l * (d = f / l), a = c * d) : r.height && !r.width ? (f = l * (s = a / c), a = c * s) : r.scale ? (f = l * r.scale, a = c * r.scale) : r.fit ? (h = l / c) > (o = (v = r.fit)[0]) / (i = v[1]) ? (f = o, a = o / h) : (a = i, f = i * h) : r.cover && ((h = l / c) > (o = (y = r.cover)[0]) / (i = y[1]) ? (a = i, f = i * h) : (f = o, a = o / h)), (r.fit || r.cover) && ("center" === r.align ? e = e + o / 2 - f / 2 : "right" === r.align && (e = e + o - f), "center" === r.valign ? n = n + i / 2 - a / 2 : "bottom" === r.valign && (n = n + i - a)), this.y === n && (this.y += a), this.save(), this.transform(f, 0, 0, -a, e, n + a), this.addContent("/" + u.label + " Do"), this.restore(), this
            },
            openImage: function(t) {
              var e;
              return "string" == typeof t && (e = this._imageRegistry[t]), e || (e = r.open(t, "I" + ++this._imageCount), "string" == typeof t && (this._imageRegistry[t] = e)), e
            }
          }
        }, {
          "../image": 8
        }],
        16: [function(t, e, n) {
          var r, i;
          r = t("../line_wrapper"), i = t("../object").number, e.localExports = {
            initText: function() {
              return this.x = 0, this.y = 0, this._lineGap = 0
            },
            currentLineGap: function() {
              return this._lineGap
            },
            lineGap: function(t) {
              return this._lineGap = t, this
            },
            moveDown: function(t) {
              return null == t && (t = 1), this.y += this.currentLineHeight(!0) * t + this._lineGap, this
            },
            moveUp: function(t) {
              return null == t && (t = 1), this.y -= this.currentLineHeight(!0) * t + this._lineGap, this
            },
            _text: function(t, e, n, i, o) {
              var a, s, u, c;
              if (i = this._initOptions(e, n, i), t = null == t ? "" : "" + t, i.wordSpacing && (t = t.replace(/\s{2,}/g, " ")), i.width)(a = this._wrapper) || (a = new r(this, i)).on("line", o), this._wrapper = i.continued ? a : null, this._textOptions = i.continued ? i : null, a.wrap(t, i);
              else
                for (s = 0, u = (c = t.split("\n")).length; s < u; s++) o(c[s], i);
              return this
            },
            text: function(t, e, n, r) {
              return this._text(t, e, n, r, this._line.bind(this))
            },
            widthOfString: function(t, e) {
              return null == e && (e = {}), this._font.widthOfString(t, this._fontSize, e.features) + (e.characterSpacing || 0) * (t.length - 1)
            },
            heightOfString: function(t, e) {
              var n, r, i, o;
              return null == e && (e = {}), i = this.x, o = this.y, e = this._initOptions(e), e.height = 1 / 0, r = e.lineGap || this._lineGap || 0, this._text(t, this.x, this.y, e, function(t) {
                return function(e, n) {
                  return t.y += t.currentLineHeight(!0) + r
                }
              }(this)), n = this.y - o, this.x = i, this.y = o, n
            },
            textAndMeasure: function(t, e, n, r, i) {
              var o, a, s, u, c, l, h, f, d;
              return r = r || {}, null === e && (e = this.x, n = this.y), l = this, f = this.x, d = this.y, h = {
                width: 0,
                height: 0,
                charCount: 0
              }, c = r.lineGap || this._lineGap || 0, s = r.columnGap || 18, u = null === e ? this.x : e, o = [], a = 0, i && null === r.height && (r.height = 1 / 0), this._text(t, e, n, r, function(n, r, s) {
                return h.charCount += n.length, l.x === e && (h.height += l.currentLineHeight(!0) + c), r.textWidth && (l.x > u && (u = l.x, o[a++] = r.lineWidth), o[a] = Math.max(o[a] || 0, r.textWidth)), i ? s ? l.y += l.currentLineHeight(!0) + c : l.x += l.widthOfString(t) : l._line.apply(l, arguments)
              }), o.length ? (o.forEach(function(t) {
                return h.width += t + s
              }), h.width -= s) : h.width = this.x - f, !1 === r.includeLastLineExternalLeading && (h.height -= this.currentLineHeight(!0) - this.currentLineHeight(!1)), h.height = Math.max(0, h.height), i && (this.x = f, this.y = d), h
            },
            list: function(t, e, n, i, o) {
              var a, s, u, c, l, h, f, d, p;
              return i = this._initOptions(e, n, i), d = Math.round(this._font.ascender / 1e3 * this._fontSize / 2), p = i.bulletRadius || Math.round(this._font.ascender / 1e3 * this._fontSize / 3), u = i.textIndent || 5 * p, c = i.bulletIndent || 8 * p, h = 1, l = [], f = [], (a = function(t) {
                var e, n, r, i, o;
                for (o = [], e = r = 0, i = t.length; r < i; e = ++r) n = t[e], Array.isArray(n) ? (h++, a(n), o.push(h--)) : (l.push(n), o.push(f.push(h)));
                return o
              })(t), (o = new r(this, i)).on("line", this._line.bind(this)), h = 1, s = 0, o.on("firstLine", function(t) {
                return function() {
                  var e, n;
                  return (n = f[s++]) !== h && (e = c * (n - h), t.x += e, o.lineWidth -= e, h = n), t.circle(t.x - u + p, t.y + d, p), t.fill()
                }
              }(this)), o.on("sectionStart", function(t) {
                return function() {
                  var e;
                  return e = u + c * (h - 1), t.x += e, o.lineWidth -= e
                }
              }(this)), o.on("sectionEnd", function(t) {
                return function() {
                  var e;
                  return e = u + c * (h - 1), t.x -= e, o.lineWidth += e
                }
              }(this)), o.wrap(l.join("\n"), i), this
            },
            _initOptions: function(t, e, n) {
              var r, i, o;
              if (null == t && (t = {}), null == n && (n = {}), "object" == typeof t && (n = t, t = null), n = function() {
                var t, e, r;
                e = {};
                for (t in n) r = n[t], e[t] = r;
                return e
              }(), this._textOptions) {
                o = this._textOptions;
                for (r in o) i = o[r], "continued" !== r && null == n[r] && (n[r] = i)
              }
              return null != t && (this.x = t), null != e && (this.y = e), !1 !== n.lineBreak && null == n.width && (n.width = Math.max(this.page.width - this.x - this.page.margins.right, 0)), n.columns || (n.columns = 0), null == n.columnGap && (n.columnGap = 18), n
            },
            _line: function(t, e, n) {
              var r;
              return null == e && (e = {}), this._fragment(t, this.x, this.y, e), r = e.lineGap || this._lineGap || 0, n ? this.y += this.currentLineHeight(!0) + r : this.x += this.widthOfString(t)
            },
            _fragment: function(t, e, n, r) {
              var o, a, s, u, c, l, h, f, d, p, g, _, m, v, y, b, w, x, S, C, k, E, A, P, j, I, L, O, T, B, N, z, F, D, M, R;
              if (0 !== (t = ("" + t).replace(/\n/g, "")).length) {
                if (a = r.align || "left", I = r.wordSpacing || 0, s = r.characterSpacing || 0, r.width) switch (a) {
                  case "right":
                    A = this.widthOfString(t.replace(/\s+$/, ""), r), e += r.lineWidth - A;
                    break;
                  case "center":
                    e += r.lineWidth / 2 - r.textWidth / 2;
                    break;
                  case "justify":
                    L = t.trim().split(/\s+/), A = this.widthOfString(t.replace(/\s+/g, ""), r), E = this.widthOfString(" ") + s, I = Math.max(0, (r.lineWidth - A) / Math.max(1, L.length - 1) - E)
                }
                if (S = r.textWidth + I * (r.wordCount - 1) + s * (t.length - 1), r.link && this.link(e, n, S, this.currentLineHeight(), r.link), (r.underline || r.strike) && (this.save(), r.stroke || this.strokeColor.apply(this, this._fillColor), m = this._fontSize < 10 ? .5 : Math.floor(this._fontSize / 10), this.lineWidth(m), c = r.underline ? 1 : 2, v = n + this.currentLineHeight() / c, r.underline && (v -= m), this.moveTo(e, v), this.lineTo(e + S, v), this.stroke(), this.restore()), this.save(), this.transform(1, 0, 0, -1, 0, this.page.height), n = this.page.height - n - this._font.ascender / 1e3 * this._fontSize, null == (O = this.page.fonts)[F = this._font.id] && (O[F] = this._font.ref()), this.addContent("BT"), this.addContent("1 0 0 1 " + i(e) + " " + i(n) + " Tm"), this.addContent("/" + this._font.id + " " + i(this._fontSize) + " Tf"), (y = r.fill && r.stroke ? 2 : r.stroke ? 1 : 0) && this.addContent(y + " Tr"), s && this.addContent(i(s) + " Tc"), I)
                  for (L = t.trim().split(/\s+/), I += this.widthOfString(" ") + s, I *= 1e3 / this._fontSize, l = [], w = [], T = 0, N = L.length; T < N; T++) {
                    j = L[T], h = (D = this._font.encode(j, r.features))[0], x = D[1], l.push.apply(l, h), w.push.apply(w, x), k = {}, M = w[w.length - 1];
                    for (g in M) P = M[g], k[g] = P;
                    k.xAdvance += I, w[w.length - 1] = k
                  } else R = this._font.encode(t, r.features), l = R[0], w = R[1];
                for (C = this._fontSize / 1e3, u = [], _ = 0, d = !1, o = function(t) {
                  var e, n;
                  return _ < t && (n = l.slice(_, t).join(""), e = w[t - 1].xAdvance - w[t - 1].advanceWidth, u.push("<" + n + "> " + i(-e))), _ = t
                }, f = function(t) {
                  return function(e) {
                    if (o(e), u.length > 0) return t.addContent("[" + u.join(" ") + "] TJ"), u.length = 0
                  }
                }(this), p = B = 0, z = w.length; B < z; p = ++B)(b = w[p]).xOffset || b.yOffset ? (f(p), this.addContent("1 0 0 1 " + i(e + b.xOffset * C) + " " + i(n + b.yOffset * C) + " Tm"), f(p + 1), d = !0) : (d && (this.addContent("1 0 0 1 " + i(e) + " " + i(n) + " Tm"), d = !1), b.xAdvance - b.advanceWidth != 0 && o(p + 1)), e += b.xAdvance * C;
                return f(p), this.addContent("ET"), this.restore()
              }
            }
          }
        }, {
          "../line_wrapper": 11,
          "../object": 18
        }],
        17: [function(t, e, n) {
          var r, i, o, a = [].slice;
          i = t("../path"), o = t("../object").number, r = (Math.sqrt(2) - 1) / 3 * 4, e.localExports = {
            initVector: function() {
              return this._ctm = [1, 0, 0, 1, 0, 0], this._ctmStack = []
            },
            save: function() {
              return this._ctmStack.push(this._ctm.slice()), this.addContent("q")
            },
            restore: function() {
              return this._ctm = this._ctmStack.pop() || [1, 0, 0, 1, 0, 0], this.addContent("Q")
            },
            closePath: function() {
              return this.addContent("h")
            },
            lineWidth: function(t) {
              return this.addContent(o(t) + " w")
            },
            _CAP_STYLES: {
              BUTT: 0,
              ROUND: 1,
              SQUARE: 2
            },
            lineCap: function(t) {
              return "string" == typeof t && (t = this._CAP_STYLES[t.toUpperCase()]), this.addContent(t + " J")
            },
            _JOIN_STYLES: {
              MITER: 0,
              ROUND: 1,
              BEVEL: 2
            },
            lineJoin: function(t) {
              return "string" == typeof t && (t = this._JOIN_STYLES[t.toUpperCase()]), this.addContent(t + " j")
            },
            miterLimit: function(t) {
              return this.addContent(o(t) + " M")
            },
            dash: function(t, e) {
              var n, r, i, a;
              return null == e && (e = {}), null == t ? this : Array.isArray(t) ? (t = function() {
                var e, n, r;
                for (r = [], e = 0, n = t.length; e < n; e++) i = t[e], r.push(o(i));
                return r
              }().join(" "), n = e.phase || 0, this.addContent("[" + t + "] " + o(n) + " d")) : (r = null != (a = e.space) ? a : t, n = e.phase || 0, this.addContent("[" + o(t) + " " + o(r) + "] " + o(n) + " d"))
            },
            undash: function() {
              return this.addContent("[] 0 d")
            },
            moveTo: function(t, e) {
              return this.addContent(o(t) + " " + o(e) + " m")
            },
            lineTo: function(t, e) {
              return this.addContent(o(t) + " " + o(e) + " l")
            },
            bezierCurveTo: function(t, e, n, r, i, a) {
              return this.addContent(o(t) + " " + o(e) + " " + o(n) + " " + o(r) + " " + o(i) + " " + o(a) + " c")
            },
            quadraticCurveTo: function(t, e, n, r) {
              return this.addContent(o(t) + " " + o(e) + " " + o(n) + " " + o(r) + " v")
            },
            rect: function(t, e, n, r) {
              return this.addContent(o(t) + " " + o(e) + " " + o(n) + " " + o(r) + " re")
            },
            roundedRect: function(t, e, n, i, o) {
              var a;
              return null == o && (o = 0), o = Math.min(o, .5 * n, .5 * i), a = o * (1 - r), this.moveTo(t + o, e), this.lineTo(t + n - o, e), this.bezierCurveTo(t + n - a, e, t + n, e + a, t + n, e + o), this.lineTo(t + n, e + i - o), this.bezierCurveTo(t + n, e + i - a, t + n - a, e + i, t + n - o, e + i), this.lineTo(t + o, e + i), this.bezierCurveTo(t + a, e + i, t, e + i - a, t, e + i - o), this.lineTo(t, e + o), this.bezierCurveTo(t, e + a, t + a, e, t + o, e), this.closePath()
            },
            ellipse: function(t, e, n, i) {
              var o, a, s, u, c, l;
              return null == i && (i = n), t -= n, e -= i, o = n * r, a = i * r, s = t + 2 * n, c = e + 2 * i, u = t + n, l = e + i, this.moveTo(t, l), this.bezierCurveTo(t, l - a, u - o, e, u, e), this.bezierCurveTo(u + o, e, s, l - a, s, l), this.bezierCurveTo(s, l + a, u + o, c, u, c), this.bezierCurveTo(u - o, c, t, l + a, t, l), this.closePath()
            },
            circle: function(t, e, n) {
              return this.ellipse(t, e, n)
            },
            arc: function(t, e, n, i, o, a) {
              var s, u, c, l, h, f, d, p, g, _, m, v, y, b, w, x;
              for (null == a && (a = !1), u = 2 * Math.PI, s = .5 * Math.PI, _ = o - i, Math.abs(_) > u ? _ = u : 0 !== _ && a !== _ < 0 && (_ = (a ? -1 : 1) * u + _), y = (w = _ / (b = Math.ceil(Math.abs(_) / s))) / s * r * n, g = i, m = -Math.sin(g) * y, v = Math.cos(g) * y, c = t + Math.cos(g) * n, l = e + Math.sin(g) * n, this.moveTo(c, l), x = 0; 0 <= b ? x < b : x > b; 0 <= b ? ++x : --x) h = c + m, f = l + v, g += w, c = t + Math.cos(g) * n, l = e + Math.sin(g) * n, d = c - (m = -Math.sin(g) * y), p = l - (v = Math.cos(g) * y), this.bezierCurveTo(h, f, d, p, c, l);
              return this
            },
            polygon: function() {
              var t, e, n, r;
              for (e = 1 <= arguments.length ? a.call(arguments, 0) : [], this.moveTo.apply(this, e.shift()), n = 0, r = e.length; n < r; n++) t = e[n], this.lineTo.apply(this, t);
              return this.closePath()
            },
            path: function(t) {
              return i.apply(this, t), this
            },
            _windingRule: function(t) {
              return /even-?odd/.test(t) ? "*" : ""
            },
            fill: function(t, e) {
              return /(even-?odd)|(non-?zero)/.test(t) && (e = t, t = null), t && this.fillColor(t), this.addContent("f" + this._windingRule(e))
            },
            stroke: function(t) {
              return t && this.strokeColor(t), this.addContent("S")
            },
            fillAndStroke: function(t, e, n) {
              var r;
              return null == e && (e = t), (r = /(even-?odd)|(non-?zero)/).test(t) && (n = t, t = null), r.test(e) && (n = e, e = t), t && (this.fillColor(t), this.strokeColor(e)), this.addContent("B" + this._windingRule(n))
            },
            clip: function(t) {
              return this.addContent("W" + this._windingRule(t) + " n")
            },
            transform: function(t, e, n, r, i, a) {
              var s, u, c, l, h, f, d, p, g;
              return s = this._ctm, u = s[0], c = s[1], l = s[2], h = s[3], f = s[4], d = s[5], s[0] = u * t + l * e, s[1] = c * t + h * e, s[2] = u * n + l * r, s[3] = c * n + h * r, s[4] = u * i + l * a + f, s[5] = c * i + h * a + d, g = function() {
                var s, u, c, l;
                for (l = [], s = 0, u = (c = [t, e, n, r, i, a]).length; s < u; s++) p = c[s], l.push(o(p));
                return l
              }().join(" "), this.addContent(g + " cm")
            },
            translate: function(t, e) {
              return this.transform(1, 0, 0, 1, t, e)
            },
            rotate: function(t, e) {
              var n, r, i, o, a, s, u;
              return null == e && (e = {}), r = t * Math.PI / 180, n = Math.cos(r), i = Math.sin(r), o = a = 0, null != e.origin && (s = (o = (u = e.origin)[0]) * i + (a = u[1]) * n, o -= o * n - a * i, a -= s), this.transform(n, i, -i, n, o, a)
            },
            scale: function(t, e, n) {
              var r, i, o;
              return null == e && (e = t), null == n && (n = {}), "object" == typeof e && (n = e, e = t), r = i = 0, null != n.origin && (r = (o = n.origin)[0], i = o[1], r -= t * r, i -= e * i), this.transform(t, 0, 0, e, r, i)
            }
          }
        }, {
          "../object": 18,
          "../path": 20
        }],
        18: [function(t, e, n) {
          (function(n) {
            var r, i;
            r = function() {
              function t() {}
              var e, r, o, a;
              return o = function(t, e) {
                return (Array(e + 1).join("0") + t).slice(-e)
              }, r = /[\n\r\t\b\f\(\)\\]/g, e = {
                "\n": "\\n",
                "\r": "\\r",
                "\t": "\\t",
                "\b": "\\b",
                "\f": "\\f",
                "\\": "\\\\",
                "(": "\\(",
                ")": "\\)"
              }, a = function(t) {
                var e, n, r, i, o;
                if (1 & (r = t.length)) throw new Error("Buffer length must be even");
                for (n = i = 0, o = r - 1; i < o; n = i += 2) e = t[n], t[n] = t[n + 1], t[n + 1] = e;
                return t
              }, t.convert = function(s) {
                var u, c, l, h, f, d, p, g, _;
                if ("string" == typeof s) return "/" + s;
                if (s instanceof String) {
                  for (l = !1, c = g = 0, _ = (d = s).length; g < _; c = g += 1)
                    if (d.charCodeAt(c) > 127) {
                      l = !0;
                      break
                    }
                  return l && (d = a(new n("\ufeff" + d, "utf16le")).toString("binary")), "(" + (d = d.replace(r, function(t) {
                    return e[t]
                  })) + ")"
                }
                if (n.isBuffer(s)) return "<" + s.toString("hex") + ">";
                if (s instanceof i) return s.toString();
                if (s instanceof Date) return "(D:" + o(s.getUTCFullYear(), 4) + o(s.getUTCMonth() + 1, 2) + o(s.getUTCDate(), 2) + o(s.getUTCHours(), 2) + o(s.getUTCMinutes(), 2) + o(s.getUTCSeconds(), 2) + "Z)";
                if (Array.isArray(s)) return "[" + function() {
                  var e, n, r;
                  for (r = [], e = 0, n = s.length; e < n; e++) u = s[e], r.push(t.convert(u));
                  return r
                }().join(" ") + "]";
                if ("[object Object]" === {}.toString.call(s)) {
                  f = ["<<"];
                  for (h in s) p = s[h], f.push("/" + h + " " + t.convert(p));
                  return f.push(">>"), f.join("\n")
                }
                return "number" == typeof s ? t.number(s) : "" + s
              }, t.number = function(t) {
                if (t > -1e21 && t < 1e21) return Math.round(1e6 * t) / 1e6;
                throw new Error("unsupported number: " + t)
              }, t
            }(), e.localExports = r, i = t("./reference")
          }).call(this, t("buffer").Buffer)
        }, {
          "./reference": 21,
          buffer: 31
        }],
        19: [function(t, e, n) {
          var r;
          r = function() {
            function t(t, r) {
              var i;
              this.document = t, null == r && (r = {}), this.size = r.size || "letter", this.layout = r.layout || "portrait", "number" == typeof r.margin ? this.margins = {
                top: r.margin,
                left: r.margin,
                bottom: r.margin,
                right: r.margin
              } : (this.margins = r.margins || e, this.margins = {
                top: this.margins.top,
                left: this.margins.left,
                bottom: this.margins.bottom,
                right: this.margins.right
              }), i = Array.isArray(this.size) ? this.size : n[this.size.toUpperCase()], this.width = i["portrait" === this.layout ? 0 : 1], this.height = i["portrait" === this.layout ? 1 : 0], this.content = this.document.ref(), this.resources = this.document.ref({
                ProcSet: ["PDF", "Text", "ImageB", "ImageC", "ImageI"]
              }), Object.defineProperties(this, {
                fonts: {
                  get: function(t) {
                    return function() {
                      var e;
                      return null != (e = t.resources.data).Font ? e.Font : e.Font = {}
                    }
                  }(this)
                },
                xobjects: {
                  get: function(t) {
                    return function() {
                      var e;
                      return null != (e = t.resources.data).XObject ? e.XObject : e.XObject = {}
                    }
                  }(this)
                },
                ext_gstates: {
                  get: function(t) {
                    return function() {
                      var e;
                      return null != (e = t.resources.data).ExtGState ? e.ExtGState : e.ExtGState = {}
                    }
                  }(this)
                },
                patterns: {
                  get: function(t) {
                    return function() {
                      var e;
                      return null != (e = t.resources.data).Pattern ? e.Pattern : e.Pattern = {}
                    }
                  }(this)
                },
                annotations: {
                  get: function(t) {
                    return function() {
                      var e;
                      return null != (e = t.dictionary.data).Annots ? e.Annots : e.Annots = []
                    }
                  }(this)
                }
              }), this.dictionary = this.document.ref({
                Type: "Page",
                Parent: this.document._root.data.Pages,
                MediaBox: [0, 0, this.width, this.height],
                Contents: this.content,
                Resources: this.resources
              })
            }
            var e, n;
            return t.prototype.maxY = function() {
              return this.height - this.margins.bottom
            }, t.prototype.write = function(t) {
              return this.content.write(t)
            }, t.prototype.end = function() {
              return this.dictionary.end(), this.resources.end(), this.content.end()
            }, e = {
              top: 72,
              left: 72,
              bottom: 72,
              right: 72
            }, n = {
              "4A0": [4767.87, 6740.79],
              "2A0": [3370.39, 4767.87],
              A0: [2383.94, 3370.39],
              A1: [1683.78, 2383.94],
              A2: [1190.55, 1683.78],
              A3: [841.89, 1190.55],
              A4: [595.28, 841.89],
              A5: [419.53, 595.28],
              A6: [297.64, 419.53],
              A7: [209.76, 297.64],
              A8: [147.4, 209.76],
              A9: [104.88, 147.4],
              A10: [73.7, 104.88],
              B0: [2834.65, 4008.19],
              B1: [2004.09, 2834.65],
              B2: [1417.32, 2004.09],
              B3: [1000.63, 1417.32],
              B4: [708.66, 1000.63],
              B5: [498.9, 708.66],
              B6: [354.33, 498.9],
              B7: [249.45, 354.33],
              B8: [175.75, 249.45],
              B9: [124.72, 175.75],
              B10: [87.87, 124.72],
              C0: [2599.37, 3676.54],
              C1: [1836.85, 2599.37],
              C2: [1298.27, 1836.85],
              C3: [918.43, 1298.27],
              C4: [649.13, 918.43],
              C5: [459.21, 649.13],
              C6: [323.15, 459.21],
              C7: [229.61, 323.15],
              C8: [161.57, 229.61],
              C9: [113.39, 161.57],
              C10: [79.37, 113.39],
              RA0: [2437.8, 3458.27],
              RA1: [1729.13, 2437.8],
              RA2: [1218.9, 1729.13],
              RA3: [864.57, 1218.9],
              RA4: [609.45, 864.57],
              SRA0: [2551.18, 3628.35],
              SRA1: [1814.17, 2551.18],
              SRA2: [1275.59, 1814.17],
              SRA3: [907.09, 1275.59],
              SRA4: [637.8, 907.09],
              EXECUTIVE: [521.86, 756],
              FOLIO: [612, 936],
              LEGAL: [612, 1008],
              LETTER: [612, 792],
              TABLOID: [792, 1224]
            }, t
          }(), e.localExports = r
        }, {}],
        20: [function(t, e, n) {
          var r;
          r = function() {
            function t() {}
            var e, n, r, i, o, a, s, u, c, l, h, f, d;
            return t.apply = function(t, n) {
              var r;
              return r = a(n), e(r, t)
            }, o = {
              A: 7,
              a: 7,
              C: 6,
              c: 6,
              H: 1,
              h: 1,
              L: 2,
              l: 2,
              M: 2,
              m: 2,
              Q: 4,
              q: 4,
              S: 4,
              s: 4,
              T: 2,
              t: 2,
              V: 1,
              v: 1,
              Z: 0,
              z: 0
            }, a = function(t) {
              var e, n, r, i, a, s, u, c, l;
              for (u = [], e = [], i = "", a = !1, s = 0, c = 0, l = t.length; c < l; c++)
                if (n = t[c], null != o[n]) s = o[n], r && (i.length > 0 && (e[e.length] = +i), u[u.length] = {
                  cmd: r,
                  args: e
                }, e = [], i = "", a = !1), r = n;
                else if (" " === n || "," === n || "-" === n && i.length > 0 && "e" !== i[i.length - 1] || "." === n && a) {
                  if (0 === i.length) continue;
                  e.length === s ? (u[u.length] = {
                    cmd: r,
                    args: e
                  }, e = [+i], "M" === r && (r = "L"), "m" === r && (r = "l")) : e[e.length] = +i, a = "." === n, i = "-" === n || "." === n ? n : ""
                } else i += n, "." === n && (a = !0);
              return i.length > 0 && (e.length === s ? (u[u.length] = {
                cmd: r,
                args: e
              }, e = [+i], "M" === r && (r = "L"), "m" === r && (r = "l")) : e[e.length] = +i), u[u.length] = {
                cmd: r,
                args: e
              }, u
            }, r = i = s = u = f = d = 0, e = function(t, e) {
              var n, o, a, l, h;
              for (r = i = s = u = f = d = 0, o = a = 0, l = t.length; a < l; o = ++a) n = t[o], "function" == typeof c[h = n.cmd] && c[h](e, n.args);
              return r = i = s = u = 0
            }, c = {
              M: function(t, e) {
                return r = e[0], i = e[1], s = u = null, f = r, d = i, t.moveTo(r, i)
              },
              m: function(t, e) {
                return r += e[0], i += e[1], s = u = null, f = r, d = i, t.moveTo(r, i)
              },
              C: function(t, e) {
                return r = e[4], i = e[5], s = e[2], u = e[3], t.bezierCurveTo.apply(t, e)
              },
              c: function(t, e) {
                return t.bezierCurveTo(e[0] + r, e[1] + i, e[2] + r, e[3] + i, e[4] + r, e[5] + i), s = r + e[2], u = i + e[3], r += e[4], i += e[5]
              },
              S: function(t, e) {
                return null === s && (s = r, u = i), t.bezierCurveTo(r - (s - r), i - (u - i), e[0], e[1], e[2], e[3]), s = e[0], u = e[1], r = e[2], i = e[3]
              },
              s: function(t, e) {
                return null === s && (s = r, u = i), t.bezierCurveTo(r - (s - r), i - (u - i), r + e[0], i + e[1], r + e[2], i + e[3]), s = r + e[0], u = i + e[1], r += e[2], i += e[3]
              },
              Q: function(t, e) {
                return s = e[0], u = e[1], r = e[2], i = e[3], t.quadraticCurveTo(e[0], e[1], r, i)
              },
              q: function(t, e) {
                return t.quadraticCurveTo(e[0] + r, e[1] + i, e[2] + r, e[3] + i), s = r + e[0], u = i + e[1], r += e[2], i += e[3]
              },
              T: function(t, e) {
                return null === s ? (s = r, u = i) : (s = r - (s - r), u = i - (u - i)), t.quadraticCurveTo(s, u, e[0], e[1]), s = r - (s - r), u = i - (u - i), r = e[0], i = e[1]
              },
              t: function(t, e) {
                return null === s ? (s = r, u = i) : (s = r - (s - r), u = i - (u - i)), t.quadraticCurveTo(s, u, r + e[0], i + e[1]), r += e[0], i += e[1]
              },
              A: function(t, e) {
                return h(t, r, i, e), r = e[5], i = e[6]
              },
              a: function(t, e) {
                return e[5] += r, e[6] += i, h(t, r, i, e), r = e[5], i = e[6]
              },
              L: function(t, e) {
                return r = e[0], i = e[1], s = u = null, t.lineTo(r, i)
              },
              l: function(t, e) {
                return r += e[0], i += e[1], s = u = null, t.lineTo(r, i)
              },
              H: function(t, e) {
                return r = e[0], s = u = null, t.lineTo(r, i)
              },
              h: function(t, e) {
                return r += e[0], s = u = null, t.lineTo(r, i)
              },
              V: function(t, e) {
                return i = e[0], s = u = null, t.lineTo(r, i)
              },
              v: function(t, e) {
                return i += e[0], s = u = null, t.lineTo(r, i)
              },
              Z: function(t) {
                return t.closePath(), r = f, i = d
              },
              z: function(t) {
                return t.closePath(), r = f, i = d
              }
            }, h = function(t, e, r, i) {
              var o, a, s, u, c, h, f, d, p, g, _, m, v;
              for (h = i[0], f = i[1], c = i[2], u = i[3], g = i[4], a = i[5], s = i[6], v = [], _ = 0, m = (p = n(a, s, h, f, u, g, c, e, r)).length; _ < m; _++) d = p[_], o = l.apply(null, d), v.push(t.bezierCurveTo.apply(t, o));
              return v
            }, n = function(t, e, n, r, i, o, a, c, l) {
              var h, f, d, p, g, _, m, v, y, b, w, x, S, C, k, E, A, P, j, I, L, O, T, B;
              for (S = a * (Math.PI / 180), x = Math.sin(S), g = Math.cos(S), n = Math.abs(n), r = Math.abs(r), (m = (s = g * (c - t) * .5 + x * (l - e) * .5) * s / (n * n) + (u = g * (l - e) * .5 - x * (c - t) * .5) * u / (r * r)) > 1 && (n *= m = Math.sqrt(m), r *= m), (w = 1 / (((j = (h = g / n) * t + (f = x / n) * e) - (P = h * c + f * l)) * (j - P) + ((O = (d = -x / r) * t + (p = g / r) * e) - (L = d * c + p * l)) * (O - L)) - .25) < 0 && (w = 0), b = Math.sqrt(w), o === i && (b = -b), I = .5 * (P + j) - b * (O - L), T = .5 * (L + O) + b * (j - P), C = Math.atan2(L - T, P - I), (A = Math.atan2(O - T, j - I) - C) < 0 && 1 === o ? A += 2 * Math.PI : A > 0 && 0 === o && (A -= 2 * Math.PI), y = Math.ceil(Math.abs(A / (.5 * Math.PI + .001))), v = [], _ = B = 0; 0 <= y ? B < y : B > y; _ = 0 <= y ? ++B : --B) k = C + _ * A / y, E = C + (_ + 1) * A / y, v[_] = [I, T, k, E, n, r, x, g];
              return v
            }, l = function(t, e, n, r, i, o, a, s) {
              var u, c, l, h, f, d, p, g, _, m, v, y;
              return u = s * i, c = -a * o, l = a * i, h = s * o, d = .5 * (r - n), f = 8 / 3 * Math.sin(.5 * d) * Math.sin(.5 * d) / Math.sin(d), p = t + Math.cos(n) - f * Math.sin(n), m = e + Math.sin(n) + f * Math.cos(n), _ = t + Math.cos(r), y = e + Math.sin(r), g = _ + f * Math.sin(r), v = y - f * Math.cos(r), [u * p + c * m, l * p + h * m, u * g + c * v, l * g + h * v, u * _ + c * y, l * _ + h * y]
            }, t
          }(), e.localExports = r
        }, {}],
        21: [function(t, e, n) {
          (function(n) {
            var r, i, o, a = function(t, e) {
              return function() {
                return t.apply(e, arguments)
              }
            };
            o = t("zlib"), i = function() {
              function t(t, e, n) {
                this.document = t, this.id = e, this.data = null != n ? n : {}, this.finalize = a(this.finalize, this), this.gen = 0, this.compress = this.document.compress && !this.data.Filter, this.chunks = []
              }
              return t.prototype.write = function(t, e) {
                var r;
                return n.isBuffer(t) || (t = new n(t + "\n", "binary")), null == (r = this.data).Length && (r.Length = 0), this.compress && (this.data.Filter = "FlateDecode"), this.chunks.push(t), this.data.Length += t.length
              }, t.prototype.end = function(t) {
                return t && this.write(t), this.compress && this.chunks.length ? o.deflate(n.concat(this.chunks), function(t) {
                  return function(e, n) {
                    return t.chunks = n, t.data.Length = t.chunks.length, t.finalize()
                  }
                }(this)) : this.finalize()
              }, t.prototype.finalize = function() {
                var t;
                return this.offset = this.document._offset, this.document._write(this.id + " " + this.gen + " obj"), this.document._write(r.convert(this.data)), this.chunks.length && (this.document._write("stream"), t = n.isBuffer(this.chunks) ? this.chunks : n.concat(this.chunks), this.document._write(t), t = null, this.chunks = [], this.document._write("\nendstream")), this.document._write("endobj"), this.document._refEnd(this)
              }, t.prototype.toString = function() {
                return this.id + " " + this.gen + " R"
              }, t
            }(), e.localExports = i, r = t("./object")
          }).call(this, t("buffer").Buffer)
        }, {
          "./object": 18,
          buffer: 31,
          zlib: 28
        }],
        22: [function(t, e, n) {
          function r(t, e) {
            return d.isUndefined(e) ? "" + e : !d.isNumber(e) || !isNaN(e) && isFinite(e) ? d.isFunction(e) || d.isRegExp(e) ? e.toString() : e : e.toString()
          }

          function i(t, e) {
            return d.isString(t) ? t.length < e ? t : t.slice(0, e) : t
          }

          function o(t) {
            return i(JSON.stringify(t.actual, r), 128) + " " + t.operator + " " + i(JSON.stringify(t.expected, r), 128)
          }

          function a(t, e, n, r, i) {
            throw new _.AssertionError({
              message: n,
              actual: t,
              expected: e,
              operator: r,
              stackStartFunction: i
            })
          }

          function s(t, e) {
            t || a(t, !0, e, "==", _.ok)
          }

          function u(t, e) {
            if (t === e) return !0;
            if (d.isBuffer(t) && d.isBuffer(e)) {
              if (t.length != e.length) return !1;
              for (var n = 0; n < t.length; n++)
                if (t[n] !== e[n]) return !1;
              return !0
            }
            return d.isDate(t) && d.isDate(e) ? t.getTime() === e.getTime() : d.isRegExp(t) && d.isRegExp(e) ? t.source === e.source && t.global === e.global && t.multiline === e.multiline && t.lastIndex === e.lastIndex && t.ignoreCase === e.ignoreCase : d.isObject(t) || d.isObject(e) ? l(t, e) : t == e
          }

          function c(t) {
            return "[object Arguments]" == Object.prototype.toString.call(t)
          }

          function l(t, e) {
            if (d.isNullOrUndefined(t) || d.isNullOrUndefined(e)) return !1;
            if (t.prototype !== e.prototype) return !1;
            if (c(t)) return !!c(e) && (t = p.call(t), e = p.call(e), u(t, e));
            try {
              var n, r, i = m(t),
                o = m(e)
            } catch (t) {
              return !1
            }
            if (i.length != o.length) return !1;
            for (i.sort(), o.sort(), r = i.length - 1; r >= 0; r--)
              if (i[r] != o[r]) return !1;
            for (r = i.length - 1; r >= 0; r--)
              if (n = i[r], !u(t[n], e[n])) return !1;
            return !0
          }

          function h(t, e) {
            return !(!t || !e) && ("[object RegExp]" == Object.prototype.toString.call(e) ? e.test(t) : t instanceof e || !0 === e.call({}, t))
          }

          function f(t, e, n, r) {
            var i;
            d.isString(n) && (r = n, n = null);
            try {
              e()
            } catch (t) {
              i = t
            }
            if (r = (n && n.name ? " (" + n.name + ")." : ".") + (r ? " " + r : "."), t && !i && a(i, n, "Missing expected exception" + r), !t && h(i, n) && a(i, n, "Got unwanted exception" + r), t && i && n && !h(i, n) || !t && i) throw i
          }
          var d = t("util/"),
            p = Array.prototype.slice,
            g = Object.prototype.hasOwnProperty,
            _ = e.localExports = s;
          _.AssertionError = function(t) {
            this.name = "AssertionError", this.actual = t.actual, this.expected = t.expected, this.operator = t.operator, t.message ? (this.message = t.message, this.generatedMessage = !1) : (this.message = o(this), this.generatedMessage = !0);
            var e = t.stackStartFunction || a;
            if (Error.captureStackTrace) Error.captureStackTrace(this, e);
            else {
              var n = new Error;
              if (n.stack) {
                var r = n.stack,
                  i = e.name,
                  s = r.indexOf("\n" + i);
                if (s >= 0) {
                  var u = r.indexOf("\n", s + 1);
                  r = r.substring(u + 1)
                }
                this.stack = r
              }
            }
          }, d.inherits(_.AssertionError, Error), _.fail = a, _.ok = s, _.equal = function(t, e, n) {
            t != e && a(t, e, n, "==", _.equal)
          }, _.notEqual = function(t, e, n) {
            t == e && a(t, e, n, "!=", _.notEqual)
          }, _.deepEqual = function(t, e, n) {
            u(t, e) || a(t, e, n, "deepEqual", _.deepEqual)
          }, _.notDeepEqual = function(t, e, n) {
            u(t, e) && a(t, e, n, "notDeepEqual", _.notDeepEqual)
          }, _.strictEqual = function(t, e, n) {
            t !== e && a(t, e, n, "===", _.strictEqual)
          }, _.notStrictEqual = function(t, e, n) {
            t === e && a(t, e, n, "!==", _.notStrictEqual)
          }, _.throws = function(t, e, n) {
            f.apply(this, [!0].concat(p.call(arguments)))
          }, _.doesNotThrow = function(t, e) {
            f.apply(this, [!1].concat(p.call(arguments)))
          }, _.ifError = function(t) {
            if (t) throw t
          };
          var m = Object.keys || function(t) {
            var e = [];
            for (var n in t) g.call(t, n) && e.push(n);
            return e
          }
        }, {
          "util/": 25
        }],
        23: [function(t, e, n) {
          "function" == typeof Object.create ? e.localExports = function(t, e) {
            t.super_ = e, t.prototype = Object.create(e.prototype, {
              constructor: {
                value: t,
                enumerable: !1,
                writable: !0,
                configurable: !0
              }
            })
          } : e.localExports = function(t, e) {
            t.super_ = e;
            var n = function() {};
            n.prototype = e.prototype, t.prototype = new n, t.prototype.constructor = t
          }
        }, {}],
        24: [function(t, e, n) {
          e.localExports = function(t) {
            return t && "object" == typeof t && "function" == typeof t.copy && "function" == typeof t.fill && "function" == typeof t.readUInt8
          }
        }, {}],
        25: [function(t, e, n) {
          (function(e, r) {
            function i(t, e) {
              var r = {
                seen: [],
                stylize: a
              };
              return arguments.length >= 3 && (r.depth = arguments[2]), arguments.length >= 4 && (r.colors = arguments[3]), g(e) ? r.showHidden = e : e && n._extend(r, e), y(r.showHidden) && (r.showHidden = !1), y(r.depth) && (r.depth = 2), y(r.colors) && (r.colors = !1), y(r.customInspect) && (r.customInspect = !0), r.colors && (r.stylize = o), u(r, t, r.depth)
            }

            function o(t, e) {
              var n = i.styles[e];
              return n ? "[" + i.colors[n][0] + "m" + t + "[" + i.colors[n][1] + "m" : t
            }

            function a(t, e) {
              return t
            }

            function s(t) {
              var e = {};
              return t.forEach(function(t, n) {
                e[t] = !0
              }), e
            }

            function u(t, e, r) {
              if (t.customInspect && e && C(e.inspect) && e.inspect !== n.inspect && (!e.constructor || e.constructor.prototype !== e)) {
                var i = e.inspect(r, t);
                return v(i) || (i = u(t, i, r)), i
              }
              var o = c(t, e);
              if (o) return o;
              var a = Object.keys(e),
                g = s(a);
              if (t.showHidden && (a = Object.getOwnPropertyNames(e)), S(e) && (a.indexOf("message") >= 0 || a.indexOf("description") >= 0)) return l(e);
              if (0 === a.length) {
                if (C(e)) {
                  var _ = e.name ? ": " + e.name : "";
                  return t.stylize("[Function" + _ + "]", "special")
                }
                if (b(e)) return t.stylize(RegExp.prototype.toString.call(e), "regexp");
                if (x(e)) return t.stylize(Date.prototype.toString.call(e), "date");
                if (S(e)) return l(e)
              }
              var m = "",
                y = !1,
                w = ["{", "}"];
              if (p(e) && (y = !0, w = ["[", "]"]), C(e) && (m = " [Function" + (e.name ? ": " + e.name : "") + "]"), b(e) && (m = " " + RegExp.prototype.toString.call(e)), x(e) && (m = " " + Date.prototype.toUTCString.call(e)), S(e) && (m = " " + l(e)), 0 === a.length && (!y || 0 == e.length)) return w[0] + m + w[1];
              if (r < 0) return b(e) ? t.stylize(RegExp.prototype.toString.call(e), "regexp") : t.stylize("[Object]", "special");
              t.seen.push(e);
              var k;
              return k = y ? h(t, e, r, g, a) : a.map(function(n) {
                return f(t, e, r, g, n, y)
              }), t.seen.pop(), d(k, m, w)
            }

            function c(t, e) {
              if (y(e)) return t.stylize("undefined", "undefined");
              if (v(e)) {
                var n = "'" + JSON.stringify(e).replace(/^"|"$/g, "").replace(/'/g, "\\'").replace(/\\"/g, '"') + "'";
                return t.stylize(n, "string")
              }
              return m(e) ? t.stylize("" + e, "number") : g(e) ? t.stylize("" + e, "boolean") : _(e) ? t.stylize("null", "null") : void 0
            }

            function l(t) {
              return "[" + Error.prototype.toString.call(t) + "]"
            }

            function h(t, e, n, r, i) {
              for (var o = [], a = 0, s = e.length; a < s; ++a) P(e, String(a)) ? o.push(f(t, e, n, r, String(a), !0)) : o.push("");
              return i.forEach(function(i) {
                i.match(/^\d+$/) || o.push(f(t, e, n, r, i, !0))
              }), o
            }

            function f(t, e, n, r, i, o) {
              var a, s, c;
              if ((c = Object.getOwnPropertyDescriptor(e, i) || {
                value: e[i]
              }).get ? s = c.set ? t.stylize("[Getter/Setter]", "special") : t.stylize("[Getter]", "special") : c.set && (s = t.stylize("[Setter]", "special")), P(r, i) || (a = "[" + i + "]"), s || (t.seen.indexOf(c.value) < 0 ? (s = _(n) ? u(t, c.value, null) : u(t, c.value, n - 1)).indexOf("\n") > -1 && (s = o ? s.split("\n").map(function(t) {
                return "  " + t
              }).join("\n").substr(2) : "\n" + s.split("\n").map(function(t) {
                return "   " + t
              }).join("\n")) : s = t.stylize("[Circular]", "special")), y(a)) {
                if (o && i.match(/^\d+$/)) return s;
                (a = JSON.stringify("" + i)).match(/^"([a-zA-Z_][a-zA-Z_0-9]*)"$/) ? (a = a.substr(1, a.length - 2), a = t.stylize(a, "name")) : (a = a.replace(/'/g, "\\'").replace(/\\"/g, '"').replace(/(^"|"$)/g, "'"), a = t.stylize(a, "string"))
              }
              return a + ": " + s
            }

            function d(t, e, n) {
              var r = 0;
              return t.reduce(function(t, e) {
                return r++, e.indexOf("\n") >= 0 && r++, t + e.replace(/\u001b\[\d\d?m/g, "").length + 1
              }, 0) > 60 ? n[0] + ("" === e ? "" : e + "\n ") + " " + t.join(",\n  ") + " " + n[1] : n[0] + e + " " + t.join(", ") + " " + n[1]
            }

            function p(t) {
              return Array.isArray(t)
            }

            function g(t) {
              return "boolean" == typeof t
            }

            function _(t) {
              return null === t
            }

            function m(t) {
              return "number" == typeof t
            }

            function v(t) {
              return "string" == typeof t
            }

            function y(t) {
              return void 0 === t
            }

            function b(t) {
              return w(t) && "[object RegExp]" === k(t)
            }

            function w(t) {
              return "object" == typeof t && null !== t
            }

            function x(t) {
              return w(t) && "[object Date]" === k(t)
            }

            function S(t) {
              return w(t) && ("[object Error]" === k(t) || t instanceof Error)
            }

            function C(t) {
              return "function" == typeof t
            }

            function k(t) {
              return Object.prototype.toString.call(t)
            }

            function E(t) {
              return t < 10 ? "0" + t.toString(10) : t.toString(10)
            }

            function A() {
              var t = new Date,
                e = [E(t.getHours()), E(t.getMinutes()), E(t.getSeconds())].join(":");
              return [t.getDate(), O[t.getMonth()], e].join(" ")
            }

            function P(t, e) {
              return Object.prototype.hasOwnProperty.call(t, e)
            }
            var j = /%[sdj%]/g;
            n.format = function(t) {
              if (!v(t)) {
                for (var e = [], n = 0; n < arguments.length; n++) e.push(i(arguments[n]));
                return e.join(" ")
              }
              for (var n = 1, r = arguments, o = r.length, a = String(t).replace(j, function(t) {
                if ("%%" === t) return "%";
                if (n >= o) return t;
                switch (t) {
                  case "%s":
                    return String(r[n++]);
                  case "%d":
                    return Number(r[n++]);
                  case "%j":
                    try {
                      return JSON.stringify(r[n++])
                    } catch (t) {
                      return "[Circular]"
                    }
                  default:
                    return t
                }
              }), s = r[n]; n < o; s = r[++n]) _(s) || !w(s) ? a += " " + s : a += " " + i(s);
              return a
            }, n.deprecate = function(t, i) {
              if (y(r.process)) return function() {
                return n.deprecate(t, i).apply(this, arguments)
              };
              if (!0 === e.noDeprecation) return t;
              var o = !1;
              return function() {
                if (!o) {
                  if (e.throwDeprecation) throw new Error(i);
                  e.traceDeprecation ? console.trace(i) : console.error(i), o = !0
                }
                return t.apply(this, arguments)
              }
            };
            var I, L = {};
            n.debuglog = function(t) {
              if (y(I) && (I = e.env.NODE_DEBUG || ""), t = t.toUpperCase(), !L[t])
                if (new RegExp("\\b" + t + "\\b", "i").test(I)) {
                  var r = e.pid;
                  L[t] = function() {
                    var e = n.format.apply(n, arguments);
                    console.error("%s %d: %s", t, r, e)
                  }
                } else L[t] = function() {};
              return L[t]
            }, n.inspect = i, i.colors = {
              bold: [1, 22],
              italic: [3, 23],
              underline: [4, 24],
              inverse: [7, 27],
              white: [37, 39],
              grey: [90, 39],
              black: [30, 39],
              blue: [34, 39],
              cyan: [36, 39],
              green: [32, 39],
              magenta: [35, 39],
              red: [31, 39],
              yellow: [33, 39]
            }, i.styles = {
              special: "cyan",
              number: "yellow",
              boolean: "yellow",
              undefined: "grey",
              null: "bold",
              string: "green",
              date: "magenta",
              regexp: "red"
            }, n.isArray = p, n.isBoolean = g, n.isNull = _, n.isNullOrUndefined = function(t) {
              return null == t
            }, n.isNumber = m, n.isString = v, n.isSymbol = function(t) {
              return "symbol" == typeof t
            }, n.isUndefined = y, n.isRegExp = b, n.isObject = w, n.isDate = x, n.isError = S, n.isFunction = C, n.isPrimitive = function(t) {
              return null === t || "boolean" == typeof t || "number" == typeof t || "string" == typeof t || "symbol" == typeof t || void 0 === t
            }, n.isBuffer = t("./support/isBuffer");
            var O = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
            n.log = function() {
              console.log("%s - %s", A(), n.format.apply(n, arguments))
            }, n.inherits = t("inherits"), n._extend = function(t, e) {
              if (!e || !w(e)) return t;
              for (var n = Object.keys(e), r = n.length; r--;) t[n[r]] = e[n[r]];
              return t
            }
          }).call(this, t("v3go1D"), "undefined" != typeof self ? self : "undefined" != typeof window ? window : {})
        }, {
          "./support/isBuffer": 24,
          inherits: 23,
          v3go1D: 205
        }],
        26: [function(t, e, n) {
          var r = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
          ! function(t) {
            "use strict";

            function e(t) {
              var e = t.charCodeAt(0);
              return e === i || e === c ? 62 : e === o || e === l ? 63 : e < a ? -1 : e < a + 10 ? e - a + 26 + 26 : e < u + 26 ? e - u : e < s + 26 ? e - s + 26 : void 0
            }
            var n = "undefined" != typeof Uint8Array ? Uint8Array : Array,
              i = "+".charCodeAt(0),
              o = "/".charCodeAt(0),
              a = "0".charCodeAt(0),
              s = "a".charCodeAt(0),
              u = "A".charCodeAt(0),
              c = "-".charCodeAt(0),
              l = "_".charCodeAt(0);
            t.toByteArray = function(t) {
              function r(t) {
                c[h++] = t
              }
              var i, o, a, s, u, c;
              if (t.length % 4 > 0) throw new Error("Invalid string. Length must be a multiple of 4");
              var l = t.length;
              u = "=" === t.charAt(l - 2) ? 2 : "=" === t.charAt(l - 1) ? 1 : 0, c = new n(3 * t.length / 4 - u), a = u > 0 ? t.length - 4 : t.length;
              var h = 0;
              for (i = 0, o = 0; i < a; i += 4, o += 3) r((16711680 & (s = e(t.charAt(i)) << 18 | e(t.charAt(i + 1)) << 12 | e(t.charAt(i + 2)) << 6 | e(t.charAt(i + 3)))) >> 16), r((65280 & s) >> 8), r(255 & s);
              return 2 === u ? r(255 & (s = e(t.charAt(i)) << 2 | e(t.charAt(i + 1)) >> 4)) : 1 === u && (r((s = e(t.charAt(i)) << 10 | e(t.charAt(i + 1)) << 4 | e(t.charAt(i + 2)) >> 2) >> 8 & 255), r(255 & s)), c
            }, t.fromByteArray = function(t) {
              function e(t) {
                return r.charAt(t)
              }
              var n, i, o, a = t.length % 3,
                s = "";
              for (n = 0, o = t.length - a; n < o; n += 3) s += function(t) {
                return e(t >> 18 & 63) + e(t >> 12 & 63) + e(t >> 6 & 63) + e(63 & t)
              }(i = (t[n] << 16) + (t[n + 1] << 8) + t[n + 2]);
              switch (a) {
                case 1:
                  s += e((i = t[t.length - 1]) >> 2), s += e(i << 4 & 63), s += "==";
                  break;
                case 2:
                  s += e((i = (t[t.length - 2] << 8) + t[t.length - 1]) >> 10), s += e(i >> 4 & 63), s += e(i << 2 & 63), s += "="
              }
              return s
            }
          }(void 0 === n ? this.base64js = {} : n)
        }, {}],
        27: [function(t, e, n) {
          (function(e, r) {
            function i(t) {
              if (t < n.DEFLATE || t > n.UNZIP) throw new TypeError("Bad argument");
              this.mode = t, this.init_done = !1, this.write_in_progress = !1, this.pending_close = !1, this.windowBits = 0, this.level = 0, this.memLevel = 0, this.strategy = 0, this.dictionary = null
            }

            function o(t, e) {
              for (var n = 0; n < t.length; n++) this[e + n] = t[n]
            }
            var a = t("pako/lib/zlib/messages"),
              s = t("pako/lib/zlib/zstream"),
              u = t("pako/lib/zlib/deflate.js"),
              c = t("pako/lib/zlib/inflate.js"),
              l = t("pako/lib/zlib/constants");
            for (var h in l) n[h] = l[h];
            n.NONE = 0, n.DEFLATE = 1, n.INFLATE = 2, n.GZIP = 3, n.GUNZIP = 4, n.DEFLATERAW = 5, n.INFLATERAW = 6, n.UNZIP = 7, i.prototype.init = function(t, e, r, i, o) {
              switch (this.windowBits = t, this.level = e, this.memLevel = r, this.strategy = i, this.mode !== n.GZIP && this.mode !== n.GUNZIP || (this.windowBits += 16), this.mode === n.UNZIP && (this.windowBits += 32), this.mode !== n.DEFLATERAW && this.mode !== n.INFLATERAW || (this.windowBits = -this.windowBits), this.strm = new s, this.mode) {
                case n.DEFLATE:
                case n.GZIP:
                case n.DEFLATERAW:
                  a = u.deflateInit2(this.strm, this.level, n.Z_DEFLATED, this.windowBits, this.memLevel, this.strategy);
                  break;
                case n.INFLATE:
                case n.GUNZIP:
                case n.INFLATERAW:
                case n.UNZIP:
                  var a = c.inflateInit2(this.strm, this.windowBits);
                  break;
                default:
                  throw new Error("Unknown mode " + this.mode)
              }
              a === n.Z_OK ? (this.write_in_progress = !1, this.init_done = !0) : this._error(a)
            }, i.prototype.params = function() {
              throw new Error("deflateParams Not supported")
            }, i.prototype._writeCheck = function() {
              if (!this.init_done) throw new Error("write before init");
              if (this.mode === n.NONE) throw new Error("already finalized");
              if (this.write_in_progress) throw new Error("write already in progress");
              if (this.pending_close) throw new Error("close is pending")
            }, i.prototype.write = function(t, n, r, i, o, a, s) {
              this._writeCheck(), this.write_in_progress = !0;
              var u = this;
              return e.nextTick(function() {
                u.write_in_progress = !1;
                var e = u._write(t, n, r, i, o, a, s);
                u.callback(e[0], e[1]), u.pending_close && u.close()
              }), this
            }, i.prototype.writeSync = function(t, e, n, r, i, o, a) {
              return this._writeCheck(), this._write(t, e, n, r, i, o, a)
            }, i.prototype._write = function(t, e, i, a, s, l, h) {
              if (this.write_in_progress = !0, t !== n.Z_NO_FLUSH && t !== n.Z_PARTIAL_FLUSH && t !== n.Z_SYNC_FLUSH && t !== n.Z_FULL_FLUSH && t !== n.Z_FINISH && t !== n.Z_BLOCK) throw new Error("Invalid flush value");
              null == e && (e = new r(0), a = 0, i = 0), s._set ? s.set = s._set : s.set = o;
              var f = this.strm;
              switch (f.avail_in = a, f.input = e, f.next_in = i, f.avail_out = h, f.output = s, f.next_out = l, this.mode) {
                case n.DEFLATE:
                case n.GZIP:
                case n.DEFLATERAW:
                  d = u.deflate(f, t);
                  break;
                case n.UNZIP:
                case n.INFLATE:
                case n.GUNZIP:
                case n.INFLATERAW:
                  var d = c.inflate(f, t);
                  break;
                default:
                  throw new Error("Unknown mode " + this.mode)
              }
              return d !== n.Z_STREAM_END && d !== n.Z_OK && this._error(d), this.write_in_progress = !1, [f.avail_in, f.avail_out]
            }, i.prototype.close = function() {
              this.write_in_progress ? this.pending_close = !0 : (this.pending_close = !1, this.mode === n.DEFLATE || this.mode === n.GZIP || this.mode === n.DEFLATERAW ? u.deflateEnd(this.strm) : c.inflateEnd(this.strm), this.mode = n.NONE)
            }, i.prototype.reset = function() {
              switch (this.mode) {
                case n.DEFLATE:
                case n.DEFLATERAW:
                  t = u.deflateReset(this.strm);
                  break;
                case n.INFLATE:
                case n.INFLATERAW:
                  var t = c.inflateReset(this.strm)
              }
              t !== n.Z_OK && this._error(t)
            }, i.prototype._error = function(t) {
              this.onerror(a[t] + ": " + this.strm.msg, t), this.write_in_progress = !1, this.pending_close && this.close()
            }, n.Zlib = i
          }).call(this, t("v3go1D"), t("buffer").Buffer)
        }, {
          buffer: 31,
          "pako/lib/zlib/constants": 195,
          "pako/lib/zlib/deflate.js": 197,
          "pako/lib/zlib/inflate.js": 199,
          "pako/lib/zlib/messages": 201,
          "pako/lib/zlib/zstream": 203,
          v3go1D: 205
        }],
        28: [function(t, e, n) {
          (function(e, r) {
            function i(t, e, n) {
              function i() {
                for (var e; null !== (e = t.read());) a.push(e), s += e.length;
                t.once("readable", i)
              }

              function o() {
                var e = r.concat(a, s);
                a = [], n(null, e), t.close()
              }
              var a = [],
                s = 0;
              t.on("error", function(e) {
                t.removeListener("end", o), t.removeListener("readable", i), n(e)
              }), t.on("end", o), t.end(e), i()
            }

            function o(t, e) {
              if ("string" == typeof e && (e = new r(e)), !r.isBuffer(e)) throw new TypeError("Not a string or buffer");
              var n = g.Z_FINISH;
              return t._processChunk(e, n)
            }

            function a(t) {
              if (!(this instanceof a)) return new a(t);
              d.call(this, t, g.DEFLATE)
            }

            function s(t) {
              if (!(this instanceof s)) return new s(t);
              d.call(this, t, g.INFLATE)
            }

            function u(t) {
              if (!(this instanceof u)) return new u(t);
              d.call(this, t, g.GZIP)
            }

            function c(t) {
              if (!(this instanceof c)) return new c(t);
              d.call(this, t, g.GUNZIP)
            }

            function l(t) {
              if (!(this instanceof l)) return new l(t);
              d.call(this, t, g.DEFLATERAW)
            }

            function h(t) {
              if (!(this instanceof h)) return new h(t);
              d.call(this, t, g.INFLATERAW)
            }

            function f(t) {
              if (!(this instanceof f)) return new f(t);
              d.call(this, t, g.UNZIP)
            }

            function d(t, e) {
              if (this._opts = t = t || {}, this._chunkSize = t.chunkSize || n.Z_DEFAULT_CHUNK, p.call(this, t), t.flush && t.flush !== g.Z_NO_FLUSH && t.flush !== g.Z_PARTIAL_FLUSH && t.flush !== g.Z_SYNC_FLUSH && t.flush !== g.Z_FULL_FLUSH && t.flush !== g.Z_FINISH && t.flush !== g.Z_BLOCK) throw new Error("Invalid flush flag: " + t.flush);
              if (this._flushFlag = t.flush || g.Z_NO_FLUSH, t.chunkSize && (t.chunkSize < n.Z_MIN_CHUNK || t.chunkSize > n.Z_MAX_CHUNK)) throw new Error("Invalid chunk size: " + t.chunkSize);
              if (t.windowBits && (t.windowBits < n.Z_MIN_WINDOWBITS || t.windowBits > n.Z_MAX_WINDOWBITS)) throw new Error("Invalid windowBits: " + t.windowBits);
              if (t.level && (t.level < n.Z_MIN_LEVEL || t.level > n.Z_MAX_LEVEL)) throw new Error("Invalid compression level: " + t.level);
              if (t.memLevel && (t.memLevel < n.Z_MIN_MEMLEVEL || t.memLevel > n.Z_MAX_MEMLEVEL)) throw new Error("Invalid memLevel: " + t.memLevel);
              if (t.strategy && t.strategy != n.Z_FILTERED && t.strategy != n.Z_HUFFMAN_ONLY && t.strategy != n.Z_RLE && t.strategy != n.Z_FIXED && t.strategy != n.Z_DEFAULT_STRATEGY) throw new Error("Invalid strategy: " + t.strategy);
              if (t.dictionary && !r.isBuffer(t.dictionary)) throw new Error("Invalid dictionary: it should be a Buffer instance");
              this._binding = new g.Zlib(e);
              var i = this;
              this._hadError = !1, this._binding.onerror = function(t, e) {
                i._binding = null, i._hadError = !0;
                var r = new Error(t);
                r.errno = e, r.code = n.codes[e], i.emit("error", r)
              };
              var o = n.Z_DEFAULT_COMPRESSION;
              "number" == typeof t.level && (o = t.level);
              var a = n.Z_DEFAULT_STRATEGY;
              "number" == typeof t.strategy && (a = t.strategy), this._binding.init(t.windowBits || n.Z_DEFAULT_WINDOWBITS, o, t.memLevel || n.Z_DEFAULT_MEMLEVEL, a, t.dictionary), this._buffer = new r(this._chunkSize), this._offset = 0, this._closed = !1, this._level = o, this._strategy = a, this.once("end", this.close)
            }
            var p = t("_stream_transform"),
              g = t("./binding"),
              _ = t("util"),
              m = t("assert").ok;
            g.Z_MIN_WINDOWBITS = 8, g.Z_MAX_WINDOWBITS = 15, g.Z_DEFAULT_WINDOWBITS = 15, g.Z_MIN_CHUNK = 64, g.Z_MAX_CHUNK = 1 / 0, g.Z_DEFAULT_CHUNK = 16384, g.Z_MIN_MEMLEVEL = 1, g.Z_MAX_MEMLEVEL = 9, g.Z_DEFAULT_MEMLEVEL = 8, g.Z_MIN_LEVEL = -1, g.Z_MAX_LEVEL = 9, g.Z_DEFAULT_LEVEL = g.Z_DEFAULT_COMPRESSION, Object.keys(g).forEach(function(t) {
              t.match(/^Z/) && (n[t] = g[t])
            }), n.codes = {
              Z_OK: g.Z_OK,
              Z_STREAM_END: g.Z_STREAM_END,
              Z_NEED_DICT: g.Z_NEED_DICT,
              Z_ERRNO: g.Z_ERRNO,
              Z_STREAM_ERROR: g.Z_STREAM_ERROR,
              Z_DATA_ERROR: g.Z_DATA_ERROR,
              Z_MEM_ERROR: g.Z_MEM_ERROR,
              Z_BUF_ERROR: g.Z_BUF_ERROR,
              Z_VERSION_ERROR: g.Z_VERSION_ERROR
            }, Object.keys(n.codes).forEach(function(t) {
              n.codes[n.codes[t]] = t
            }), n.Deflate = a, n.Inflate = s, n.Gzip = u, n.Gunzip = c, n.DeflateRaw = l, n.InflateRaw = h, n.Unzip = f, n.createDeflate = function(t) {
              return new a(t)
            }, n.createInflate = function(t) {
              return new s(t)
            }, n.createDeflateRaw = function(t) {
              return new l(t)
            }, n.createInflateRaw = function(t) {
              return new h(t)
            }, n.createGzip = function(t) {
              return new u(t)
            }, n.createGunzip = function(t) {
              return new c(t)
            }, n.createUnzip = function(t) {
              return new f(t)
            }, n.deflate = function(t, e, n) {
              return "function" == typeof e && (n = e, e = {}), i(new a(e), t, n)
            }, n.deflateSync = function(t, e) {
              return o(new a(e), t)
            }, n.gzip = function(t, e, n) {
              return "function" == typeof e && (n = e, e = {}), i(new u(e), t, n)
            }, n.gzipSync = function(t, e) {
              return o(new u(e), t)
            }, n.deflateRaw = function(t, e, n) {
              return "function" == typeof e && (n = e, e = {}), i(new l(e), t, n)
            }, n.deflateRawSync = function(t, e) {
              return o(new l(e), t)
            }, n.unzip = function(t, e, n) {
              return "function" == typeof e && (n = e, e = {}), i(new f(e), t, n)
            }, n.unzipSync = function(t, e) {
              return o(new f(e), t)
            }, n.inflate = function(t, e, n) {
              return "function" == typeof e && (n = e, e = {}), i(new s(e), t, n)
            }, n.inflateSync = function(t, e) {
              return o(new s(e), t)
            }, n.gunzip = function(t, e, n) {
              return "function" == typeof e && (n = e, e = {}), i(new c(e), t, n)
            }, n.gunzipSync = function(t, e) {
              return o(new c(e), t)
            }, n.inflateRaw = function(t, e, n) {
              return "function" == typeof e && (n = e, e = {}), i(new h(e), t, n)
            }, n.inflateRawSync = function(t, e) {
              return o(new h(e), t)
            }, _.inherits(d, p), d.prototype.params = function(t, r, i) {
              if (t < n.Z_MIN_LEVEL || t > n.Z_MAX_LEVEL) throw new RangeError("Invalid compression level: " + t);
              if (r != n.Z_FILTERED && r != n.Z_HUFFMAN_ONLY && r != n.Z_RLE && r != n.Z_FIXED && r != n.Z_DEFAULT_STRATEGY) throw new TypeError("Invalid strategy: " + r);
              if (this._level !== t || this._strategy !== r) {
                var o = this;
                this.flush(g.Z_SYNC_FLUSH, function() {
                  o._binding.params(t, r), o._hadError || (o._level = t, o._strategy = r, i && i())
                })
              } else e.nextTick(i)
            }, d.prototype.reset = function() {
              return this._binding.reset()
            }, d.prototype._flush = function(t) {
              this._transform(new r(0), "", t)
            }, d.prototype.flush = function(t, n) {
              var i = this._writableState;
              if (("function" == typeof t || void 0 === t && !n) && (n = t, t = g.Z_FULL_FLUSH), i.ended) n && e.nextTick(n);
              else if (i.ending) n && this.once("end", n);
              else if (i.needDrain) {
                var o = this;
                this.once("drain", function() {
                  o.flush(n)
                })
              } else this._flushFlag = t, this.write(new r(0), "", n)
            }, d.prototype.close = function(t) {
              if (t && e.nextTick(t), !this._closed) {
                this._closed = !0, this._binding.close();
                var n = this;
                e.nextTick(function() {
                  n.emit("close")
                })
              }
            }, d.prototype._transform = function(t, e, n) {
              var i, o = this._writableState,
                a = (o.ending || o.ended) && (!t || o.length === t.length);
              if (null === !t && !r.isBuffer(t)) return n(new Error("invalid input"));
              a ? i = g.Z_FINISH : (i = this._flushFlag, t.length >= o.length && (this._flushFlag = this._opts.flush || g.Z_NO_FLUSH));
              this._processChunk(t, i, n)
            }, d.prototype._processChunk = function(t, e, n) {
              function i(l, d) {
                if (!u._hadError) {
                  var p = a - d;
                  if (m(p >= 0, "have should not go down"), p > 0) {
                    var g = u._buffer.slice(u._offset, u._offset + p);
                    u._offset += p, c ? u.push(g) : (h.push(g), f += g.length)
                  }
                  if ((0 === d || u._offset >= u._chunkSize) && (a = u._chunkSize, u._offset = 0, u._buffer = new r(u._chunkSize)), 0 === d) {
                    if (s += o - l, o = l, !c) return !0;
                    var _ = u._binding.write(e, t, s, o, u._buffer, u._offset, u._chunkSize);
                    return _.callback = i, void(_.buffer = t)
                  }
                  if (!c) return !1;
                  n()
                }
              }
              var o = t && t.length,
                a = this._chunkSize - this._offset,
                s = 0,
                u = this,
                c = "function" == typeof n;
              if (!c) {
                var l, h = [],
                  f = 0;
                this.on("error", function(t) {
                  l = t
                });
                do {
                  var d = this._binding.writeSync(e, t, s, o, this._buffer, this._offset, a)
                } while (!this._hadError && i(d[0], d[1]));
                if (this._hadError) throw l;
                var p = r.concat(h, f);
                return this.close(), p
              }
              var g = this._binding.write(e, t, s, o, this._buffer, this._offset, a);
              g.buffer = t, g.callback = i
            }, _.inherits(a, d), _.inherits(s, d), _.inherits(u, d), _.inherits(c, d), _.inherits(l, d), _.inherits(h, d), _.inherits(f, d)
          }).call(this, t("v3go1D"), t("buffer").Buffer)
        }, {
          "./binding": 27,
          _stream_transform: 211,
          assert: 22,
          buffer: 31,
          util: 216,
          v3go1D: 205
        }],
        29: [function(t, e, n) {}, {}],
        30: [function(t, e, n) {
          function r(t) {
            if (t && !s.isEncoding(t)) throw new Error("Unknown encoding: " + t)
          }

          function i(t) {
            return t.toString(this.encoding)
          }

          function o(t) {
            var e = this.charReceived = t.length % 2;
            return this.charLength = e ? 2 : 0, e
          }

          function a(t) {
            var e = this.charReceived = t.length % 3;
            return this.charLength = e ? 3 : 0, e
          }
          var s = t("buffer").Buffer,
            u = n.StringDecoder = function(t) {
              switch (this.encoding = (t || "utf8").toLowerCase().replace(/[-_]/, ""), r(t), this.encoding) {
                case "utf8":
                  this.surrogateSize = 3;
                  break;
                case "ucs2":
                case "utf16le":
                  this.surrogateSize = 2, this.detectIncompleteChar = o;
                  break;
                case "base64":
                  this.surrogateSize = 3, this.detectIncompleteChar = a;
                  break;
                default:
                  return void(this.write = i)
              }
              this.charBuffer = new s(6), this.charReceived = 0, this.charLength = 0
            };
          u.prototype.write = function(t) {
            for (var e = "", n = 0; this.charLength;) {
              var r = t.length >= this.charLength - this.charReceived ? this.charLength - this.charReceived : t.length;
              if (t.copy(this.charBuffer, this.charReceived, n, r), this.charReceived += r - n, n = r, this.charReceived < this.charLength) return "";
              if (!((a = (e = this.charBuffer.slice(0, this.charLength).toString(this.encoding)).charCodeAt(e.length - 1)) >= 55296 && a <= 56319)) {
                if (this.charReceived = this.charLength = 0, r == t.length) return e;
                t = t.slice(r, t.length);
                break
              }
              this.charLength += this.surrogateSize, e = ""
            }
            var i = this.detectIncompleteChar(t),
              o = t.length;
            this.charLength && (t.copy(this.charBuffer, 0, t.length - i, o), this.charReceived = i, o -= i);
            var o = (e += t.toString(this.encoding, 0, o)).length - 1,
              a = e.charCodeAt(o);
            if (a >= 55296 && a <= 56319) {
              var s = this.surrogateSize;
              return this.charLength += s, this.charReceived += s, this.charBuffer.copy(this.charBuffer, s, 0, s), this.charBuffer.write(e.charAt(e.length - 1), this.encoding), e.substring(0, o)
            }
            return e
          }, u.prototype.detectIncompleteChar = function(t) {
            for (var e = t.length >= 3 ? 3 : t.length; e > 0; e--) {
              var n = t[t.length - e];
              if (1 == e && n >> 5 == 6) {
                this.charLength = 2;
                break
              }
              if (e <= 2 && n >> 4 == 14) {
                this.charLength = 3;
                break
              }
              if (e <= 3 && n >> 3 == 30) {
                this.charLength = 4;
                break
              }
            }
            return e
          }, u.prototype.end = function(t) {
            var e = "";
            if (t && t.length && (e = this.write(t)), this.charReceived) {
              var n = this.charReceived,
                r = this.charBuffer,
                i = this.encoding;
              e += r.slice(0, n).toString(i)
            }
            return e
          }
        }, {
          buffer: 31
        }],
        31: [function(t, e, n) {
          function r(t, e, n) {
            if (!(this instanceof r)) return new r(t, e, n);
            var i = typeof t;
            if ("base64" === e && "string" === i)
              for (t = P(t); t.length % 4 != 0;) t += "=";
            var o;
            if ("number" === i) o = I(t);
            else if ("string" === i) o = r.byteLength(t, e);
            else {
              if ("object" !== i) throw new Error("First argument needs to be a number, array or string.");
              o = I(t.length)
            }
            var a;
            r._useTypedArrays ? a = r._augment(new Uint8Array(o)) : ((a = this).length = o, a._isBuffer = !0);
            var s;
            if (r._useTypedArrays && "number" == typeof t.byteLength) a._set(t);
            else if (O(t))
              for (s = 0; s < o; s++) r.isBuffer(t) ? a[s] = t.readUInt8(s) : a[s] = t[s];
            else if ("string" === i) a.write(t, 0, e);
            else if ("number" === i && !r._useTypedArrays && !n)
              for (s = 0; s < o; s++) a[s] = 0;
            return a
          }

          function i(t, e, n, i) {
            n = Number(n) || 0;
            var o = t.length - n;
            i ? (i = Number(i)) > o && (i = o) : i = o;
            var a = e.length;
            G(a % 2 == 0, "Invalid hex string"), i > a / 2 && (i = a / 2);
            for (var s = 0; s < i; s++) {
              var u = parseInt(e.substr(2 * s, 2), 16);
              G(!isNaN(u), "Invalid hex string"), t[n + s] = u
            }
            return r._charsWritten = 2 * s, s
          }

          function o(t, e, n, i) {
            return r._charsWritten = D(B(e), t, n, i)
          }

          function a(t, e, n, i) {
            return r._charsWritten = D(N(e), t, n, i)
          }

          function s(t, e, n, r) {
            return a(t, e, n, r)
          }

          function u(t, e, n, i) {
            return r._charsWritten = D(F(e), t, n, i)
          }

          function c(t, e, n, i) {
            return r._charsWritten = D(z(e), t, n, i)
          }

          function l(t, e, n) {
            return 0 === e && n === t.length ? q.fromByteArray(t) : q.fromByteArray(t.slice(e, n))
          }

          function h(t, e, n) {
            var r = "",
              i = "";
            n = Math.min(t.length, n);
            for (var o = e; o < n; o++) t[o] <= 127 ? (r += M(i) + String.fromCharCode(t[o]), i = "") : i += "%" + t[o].toString(16);
            return r + M(i)
          }

          function f(t, e, n) {
            var r = "";
            n = Math.min(t.length, n);
            for (var i = e; i < n; i++) r += String.fromCharCode(t[i]);
            return r
          }

          function d(t, e, n) {
            return f(t, e, n)
          }

          function p(t, e, n) {
            var r = t.length;
            (!e || e < 0) && (e = 0), (!n || n < 0 || n > r) && (n = r);
            for (var i = "", o = e; o < n; o++) i += T(t[o]);
            return i
          }

          function g(t, e, n) {
            for (var r = t.slice(e, n), i = "", o = 0; o < r.length; o += 2) i += String.fromCharCode(r[o] + 256 * r[o + 1]);
            return i
          }

          function _(t, e, n, r) {
            r || (G("boolean" == typeof n, "missing or invalid endian"), G(void 0 !== e && null !== e, "missing offset"), G(e + 1 < t.length, "Trying to read beyond buffer length"));
            var i = t.length;
            if (!(e >= i)) {
              var o;
              return n ? (o = t[e], e + 1 < i && (o |= t[e + 1] << 8)) : (o = t[e] << 8, e + 1 < i && (o |= t[e + 1])), o
            }
          }

          function m(t, e, n, r) {
            r || (G("boolean" == typeof n, "missing or invalid endian"), G(void 0 !== e && null !== e, "missing offset"), G(e + 3 < t.length, "Trying to read beyond buffer length"));
            var i = t.length;
            if (!(e >= i)) {
              var o;
              return n ? (e + 2 < i && (o = t[e + 2] << 16), e + 1 < i && (o |= t[e + 1] << 8), o |= t[e], e + 3 < i && (o += t[e + 3] << 24 >>> 0)) : (e + 1 < i && (o = t[e + 1] << 16), e + 2 < i && (o |= t[e + 2] << 8), e + 3 < i && (o |= t[e + 3]), o += t[e] << 24 >>> 0), o
            }
          }

          function v(t, e, n, r) {
            if (r || (G("boolean" == typeof n, "missing or invalid endian"), G(void 0 !== e && null !== e, "missing offset"), G(e + 1 < t.length, "Trying to read beyond buffer length")), !(e >= t.length)) {
              var i = _(t, e, n, !0);
              return 32768 & i ? -1 * (65535 - i + 1) : i
            }
          }

          function y(t, e, n, r) {
            if (r || (G("boolean" == typeof n, "missing or invalid endian"), G(void 0 !== e && null !== e, "missing offset"), G(e + 3 < t.length, "Trying to read beyond buffer length")), !(e >= t.length)) {
              var i = m(t, e, n, !0);
              return 2147483648 & i ? -1 * (4294967295 - i + 1) : i
            }
          }

          function b(t, e, n, r) {
            return r || (G("boolean" == typeof n, "missing or invalid endian"), G(e + 3 < t.length, "Trying to read beyond buffer length")), Z.read(t, e, n, 23, 4)
          }

          function w(t, e, n, r) {
            return r || (G("boolean" == typeof n, "missing or invalid endian"), G(e + 7 < t.length, "Trying to read beyond buffer length")), Z.read(t, e, n, 52, 8)
          }

          function x(t, e, n, r, i) {
            i || (G(void 0 !== e && null !== e, "missing value"), G("boolean" == typeof r, "missing or invalid endian"), G(void 0 !== n && null !== n, "missing offset"), G(n + 1 < t.length, "trying to write beyond buffer length"), R(e, 65535));
            var o = t.length;
            if (!(n >= o))
              for (var a = 0, s = Math.min(o - n, 2); a < s; a++) t[n + a] = (e & 255 << 8 * (r ? a : 1 - a)) >>> 8 * (r ? a : 1 - a)
          }

          function S(t, e, n, r, i) {
            i || (G(void 0 !== e && null !== e, "missing value"), G("boolean" == typeof r, "missing or invalid endian"), G(void 0 !== n && null !== n, "missing offset"), G(n + 3 < t.length, "trying to write beyond buffer length"), R(e, 4294967295));
            var o = t.length;
            if (!(n >= o))
              for (var a = 0, s = Math.min(o - n, 4); a < s; a++) t[n + a] = e >>> 8 * (r ? a : 3 - a) & 255
          }

          function C(t, e, n, r, i) {
            i || (G(void 0 !== e && null !== e, "missing value"), G("boolean" == typeof r, "missing or invalid endian"), G(void 0 !== n && null !== n, "missing offset"), G(n + 1 < t.length, "Trying to write beyond buffer length"), U(e, 32767, -32768)), n >= t.length || (e >= 0 ? x(t, e, n, r, i) : x(t, 65535 + e + 1, n, r, i))
          }

          function k(t, e, n, r, i) {
            i || (G(void 0 !== e && null !== e, "missing value"), G("boolean" == typeof r, "missing or invalid endian"), G(void 0 !== n && null !== n, "missing offset"), G(n + 3 < t.length, "Trying to write beyond buffer length"), U(e, 2147483647, -2147483648)), n >= t.length || (e >= 0 ? S(t, e, n, r, i) : S(t, 4294967295 + e + 1, n, r, i))
          }

          function E(t, e, n, r, i) {
            i || (G(void 0 !== e && null !== e, "missing value"), G("boolean" == typeof r, "missing or invalid endian"), G(void 0 !== n && null !== n, "missing offset"), G(n + 3 < t.length, "Trying to write beyond buffer length"), H(e, 3.4028234663852886e38, -3.4028234663852886e38)), n >= t.length || Z.write(t, e, n, r, 23, 4)
          }

          function A(t, e, n, r, i) {
            i || (G(void 0 !== e && null !== e, "missing value"), G("boolean" == typeof r, "missing or invalid endian"), G(void 0 !== n && null !== n, "missing offset"), G(n + 7 < t.length, "Trying to write beyond buffer length"), H(e, 1.7976931348623157e308, -1.7976931348623157e308)), n >= t.length || Z.write(t, e, n, r, 52, 8)
          }

          function P(t) {
            return t.trim ? t.trim() : t.replace(/^\s+|\s+$/g, "")
          }

          function j(t, e, n) {
            return "number" != typeof t ? n : (t = ~~t) >= e ? e : t >= 0 ? t : (t += e) >= 0 ? t : 0
          }

          function I(t) {
            return (t = ~~Math.ceil(+t)) < 0 ? 0 : t
          }

          function L(t) {
            return (Array.isArray || function(t) {
              return "[object Array]" === Object.prototype.toString.call(t)
            })(t)
          }

          function O(t) {
            return L(t) || r.isBuffer(t) || t && "object" == typeof t && "number" == typeof t.length
          }

          function T(t) {
            return t < 16 ? "0" + t.toString(16) : t.toString(16)
          }

          function B(t) {
            for (var e = [], n = 0; n < t.length; n++) {
              var r = t.charCodeAt(n);
              if (r <= 127) e.push(t.charCodeAt(n));
              else {
                var i = n;
                r >= 55296 && r <= 57343 && n++;
                for (var o = encodeURIComponent(t.slice(i, n + 1)).substr(1).split("%"), a = 0; a < o.length; a++) e.push(parseInt(o[a], 16))
              }
            }
            return e
          }

          function N(t) {
            for (var e = [], n = 0; n < t.length; n++) e.push(255 & t.charCodeAt(n));
            return e
          }

          function z(t) {
            for (var e, n, r, i = [], o = 0; o < t.length; o++) n = (e = t.charCodeAt(o)) >> 8, r = e % 256, i.push(r), i.push(n);
            return i
          }

          function F(t) {
            return q.toByteArray(t)
          }

          function D(t, e, n, r) {
            for (var i = 0; i < r && !(i + n >= e.length || i >= t.length); i++) e[i + n] = t[i];
            return i
          }

          function M(t) {
            try {
              return decodeURIComponent(t)
            } catch (t) {
              return String.fromCharCode(65533)
            }
          }

          function R(t, e) {
            G("number" == typeof t, "cannot write a non-number as a number"), G(t >= 0, "specified a negative value for writing an unsigned value"), G(t <= e, "value is larger than maximum value for type"), G(Math.floor(t) === t, "value has a fractional component")
          }

          function U(t, e, n) {
            G("number" == typeof t, "cannot write a non-number as a number"), G(t <= e, "value larger than maximum allowed value"), G(t >= n, "value smaller than minimum allowed value"), G(Math.floor(t) === t, "value has a fractional component")
          }

          function H(t, e, n) {
            G("number" == typeof t, "cannot write a non-number as a number"), G(t <= e, "value larger than maximum allowed value"), G(t >= n, "value smaller than minimum allowed value")
          }

          function G(t, e) {
            if (!t) throw new Error(e || "Failed assertion")
          }
          var q = t("base64-js"),
            Z = t("ieee754");
          n.Buffer = r, n.SlowBuffer = r, n.INSPECT_MAX_BYTES = 50, r.poolSize = 8192, r._useTypedArrays = function() {
            try {
              var t = new ArrayBuffer(0),
                e = new Uint8Array(t);
              return e.foo = function() {
                return 42
              }, 42 === e.foo() && "function" == typeof e.subarray
            } catch (t) {
              return !1
            }
          }(), r.isEncoding = function(t) {
            switch (String(t).toLowerCase()) {
              case "hex":
              case "utf8":
              case "utf-8":
              case "ascii":
              case "binary":
              case "base64":
              case "raw":
              case "ucs2":
              case "ucs-2":
              case "utf16le":
              case "utf-16le":
                return !0;
              default:
                return !1
            }
          }, r.isBuffer = function(t) {
            return !(null === t || void 0 === t || !t._isBuffer)
          }, r.byteLength = function(t, e) {
            var n;
            switch (t += "", e || "utf8") {
              case "hex":
                n = t.length / 2;
                break;
              case "utf8":
              case "utf-8":
                n = B(t).length;
                break;
              case "ascii":
              case "binary":
              case "raw":
                n = t.length;
                break;
              case "base64":
                n = F(t).length;
                break;
              case "ucs2":
              case "ucs-2":
              case "utf16le":
              case "utf-16le":
                n = 2 * t.length;
                break;
              default:
                throw new Error("Unknown encoding")
            }
            return n
          }, r.concat = function(t, e) {
            if (G(L(t), "Usage: Buffer.concat(list, [totalLength])\nlist should be an Array."), 0 === t.length) return new r(0);
            if (1 === t.length) return t[0];
            var n;
            if ("number" != typeof e)
              for (e = 0, n = 0; n < t.length; n++) e += t[n].length;
            var i = new r(e),
              o = 0;
            for (n = 0; n < t.length; n++) {
              var a = t[n];
              a.copy(i, o), o += a.length
            }
            return i
          }, r.prototype.write = function(t, e, n, r) {
            if (isFinite(e)) isFinite(n) || (r = n, n = void 0);
            else {
              var l = r;
              r = e, e = n, n = l
            }
            e = Number(e) || 0;
            var h = this.length - e;
            n ? (n = Number(n)) > h && (n = h) : n = h;
            var f;
            switch (r = String(r || "utf8").toLowerCase()) {
              case "hex":
                f = i(this, t, e, n);
                break;
              case "utf8":
              case "utf-8":
                f = o(this, t, e, n);
                break;
              case "ascii":
                f = a(this, t, e, n);
                break;
              case "binary":
                f = s(this, t, e, n);
                break;
              case "base64":
                f = u(this, t, e, n);
                break;
              case "ucs2":
              case "ucs-2":
              case "utf16le":
              case "utf-16le":
                f = c(this, t, e, n);
                break;
              default:
                throw new Error("Unknown encoding")
            }
            return f
          }, r.prototype.toString = function(t, e, n) {
            var r = this;
            if (t = String(t || "utf8").toLowerCase(), e = Number(e) || 0, (n = void 0 !== n ? Number(n) : n = r.length) === e) return "";
            var i;
            switch (t) {
              case "hex":
                i = p(r, e, n);
                break;
              case "utf8":
              case "utf-8":
                i = h(r, e, n);
                break;
              case "ascii":
                i = f(r, e, n);
                break;
              case "binary":
                i = d(r, e, n);
                break;
              case "base64":
                i = l(r, e, n);
                break;
              case "ucs2":
              case "ucs-2":
              case "utf16le":
              case "utf-16le":
                i = g(r, e, n);
                break;
              default:
                throw new Error("Unknown encoding")
            }
            return i
          }, r.prototype.toJSON = function() {
            return {
              type: "Buffer",
              data: Array.prototype.slice.call(this._arr || this, 0)
            }
          }, r.prototype.copy = function(t, e, n, i) {
            var o = this;
            if (n || (n = 0), i || 0 === i || (i = this.length), e || (e = 0), i !== n && 0 !== t.length && 0 !== o.length) {
              G(i >= n, "sourceEnd < sourceStart"), G(e >= 0 && e < t.length, "targetStart out of bounds"), G(n >= 0 && n < o.length, "sourceStart out of bounds"), G(i >= 0 && i <= o.length, "sourceEnd out of bounds"), i > this.length && (i = this.length), t.length - e < i - n && (i = t.length - e + n);
              var a = i - n;
              if (a < 100 || !r._useTypedArrays)
                for (var s = 0; s < a; s++) t[s + e] = this[s + n];
              else t._set(this.subarray(n, n + a), e)
            }
          }, r.prototype.slice = function(t, e) {
            var n = this.length;
            if (t = j(t, n, 0), e = j(e, n, n), r._useTypedArrays) return r._augment(this.subarray(t, e));
            for (var i = e - t, o = new r(i, void 0, !0), a = 0; a < i; a++) o[a] = this[a + t];
            return o
          }, r.prototype.get = function(t) {
            return console.log(".get() is deprecated. Access using array indexes instead."), this.readUInt8(t)
          }, r.prototype.set = function(t, e) {
            return console.log(".set() is deprecated. Access using array indexes instead."), this.writeUInt8(t, e)
          }, r.prototype.readUInt8 = function(t, e) {
            if (e || (G(void 0 !== t && null !== t, "missing offset"), G(t < this.length, "Trying to read beyond buffer length")), !(t >= this.length)) return this[t]
          }, r.prototype.readUInt16LE = function(t, e) {
            return _(this, t, !0, e)
          }, r.prototype.readUInt16BE = function(t, e) {
            return _(this, t, !1, e)
          }, r.prototype.readUInt32LE = function(t, e) {
            return m(this, t, !0, e)
          }, r.prototype.readUInt32BE = function(t, e) {
            return m(this, t, !1, e)
          }, r.prototype.readInt8 = function(t, e) {
            if (e || (G(void 0 !== t && null !== t, "missing offset"), G(t < this.length, "Trying to read beyond buffer length")), !(t >= this.length)) return 128 & this[t] ? -1 * (255 - this[t] + 1) : this[t]
          }, r.prototype.readInt16LE = function(t, e) {
            return v(this, t, !0, e)
          }, r.prototype.readInt16BE = function(t, e) {
            return v(this, t, !1, e)
          }, r.prototype.readInt32LE = function(t, e) {
            return y(this, t, !0, e)
          }, r.prototype.readInt32BE = function(t, e) {
            return y(this, t, !1, e)
          }, r.prototype.readFloatLE = function(t, e) {
            return b(this, t, !0, e)
          }, r.prototype.readFloatBE = function(t, e) {
            return b(this, t, !1, e)
          }, r.prototype.readDoubleLE = function(t, e) {
            return w(this, t, !0, e)
          }, r.prototype.readDoubleBE = function(t, e) {
            return w(this, t, !1, e)
          }, r.prototype.writeUInt8 = function(t, e, n) {
            n || (G(void 0 !== t && null !== t, "missing value"), G(void 0 !== e && null !== e, "missing offset"), G(e < this.length, "trying to write beyond buffer length"), R(t, 255)), e >= this.length || (this[e] = t)
          }, r.prototype.writeUInt16LE = function(t, e, n) {
            x(this, t, e, !0, n)
          }, r.prototype.writeUInt16BE = function(t, e, n) {
            x(this, t, e, !1, n)
          }, r.prototype.writeUInt32LE = function(t, e, n) {
            S(this, t, e, !0, n)
          }, r.prototype.writeUInt32BE = function(t, e, n) {
            S(this, t, e, !1, n)
          }, r.prototype.writeInt8 = function(t, e, n) {
            n || (G(void 0 !== t && null !== t, "missing value"), G(void 0 !== e && null !== e, "missing offset"), G(e < this.length, "Trying to write beyond buffer length"), U(t, 127, -128)), e >= this.length || (t >= 0 ? this.writeUInt8(t, e, n) : this.writeUInt8(255 + t + 1, e, n))
          }, r.prototype.writeInt16LE = function(t, e, n) {
            C(this, t, e, !0, n)
          }, r.prototype.writeInt16BE = function(t, e, n) {
            C(this, t, e, !1, n)
          }, r.prototype.writeInt32LE = function(t, e, n) {
            k(this, t, e, !0, n)
          }, r.prototype.writeInt32BE = function(t, e, n) {
            k(this, t, e, !1, n)
          }, r.prototype.writeFloatLE = function(t, e, n) {
            E(this, t, e, !0, n)
          }, r.prototype.writeFloatBE = function(t, e, n) {
            E(this, t, e, !1, n)
          }, r.prototype.writeDoubleLE = function(t, e, n) {
            A(this, t, e, !0, n)
          }, r.prototype.writeDoubleBE = function(t, e, n) {
            A(this, t, e, !1, n)
          }, r.prototype.fill = function(t, e, n) {
            if (t || (t = 0), e || (e = 0), n || (n = this.length), "string" == typeof t && (t = t.charCodeAt(0)), G("number" == typeof t && !isNaN(t), "value is not a number"), G(n >= e, "end < start"), n !== e && 0 !== this.length) {
              G(e >= 0 && e < this.length, "start out of bounds"), G(n >= 0 && n <= this.length, "end out of bounds");
              for (var r = e; r < n; r++) this[r] = t
            }
          }, r.prototype.inspect = function() {
            for (var t = [], e = this.length, r = 0; r < e; r++)
              if (t[r] = T(this[r]), r === n.INSPECT_MAX_BYTES) {
                t[r + 1] = "...";
                break
              }
            return "<Buffer " + t.join(" ") + ">"
          }, r.prototype.toArrayBuffer = function() {
            if ("undefined" != typeof Uint8Array) {
              if (r._useTypedArrays) return new r(this).buffer;
              for (var t = new Uint8Array(this.length), e = 0, n = t.length; e < n; e += 1) t[e] = this[e];
              return t.buffer
            }
            throw new Error("Buffer.toArrayBuffer not supported in this browser")
          };
          var W = r.prototype;
          r._augment = function(t) {
            return t._isBuffer = !0, t._get = t.get, t._set = t.set, t.get = W.get, t.set = W.set, t.write = W.write, t.toString = W.toString, t.toLocaleString = W.toString, t.toJSON = W.toJSON, t.copy = W.copy, t.slice = W.slice, t.readUInt8 = W.readUInt8, t.readUInt16LE = W.readUInt16LE, t.readUInt16BE = W.readUInt16BE, t.readUInt32LE = W.readUInt32LE, t.readUInt32BE = W.readUInt32BE, t.readInt8 = W.readInt8, t.readInt16LE = W.readInt16LE, t.readInt16BE = W.readInt16BE, t.readInt32LE = W.readInt32LE, t.readInt32BE = W.readInt32BE, t.readFloatLE = W.readFloatLE, t.readFloatBE = W.readFloatBE, t.readDoubleLE = W.readDoubleLE, t.readDoubleBE = W.readDoubleBE, t.writeUInt8 = W.writeUInt8, t.writeUInt16LE = W.writeUInt16LE, t.writeUInt16BE = W.writeUInt16BE, t.writeUInt32LE = W.writeUInt32LE, t.writeUInt32BE = W.writeUInt32BE, t.writeInt8 = W.writeInt8, t.writeInt16LE = W.writeInt16LE, t.writeInt16BE = W.writeInt16BE, t.writeInt32LE = W.writeInt32LE, t.writeInt32BE = W.writeInt32BE, t.writeFloatLE = W.writeFloatLE, t.writeFloatBE = W.writeFloatBE, t.writeDoubleLE = W.writeDoubleLE, t.writeDoubleBE = W.writeDoubleBE, t.fill = W.fill, t.inspect = W.inspect, t.toArrayBuffer = W.toArrayBuffer, t
          }
        }, {
          "base64-js": 26,
          ieee754: 188
        }],
        32: [function(t, e, n) {
          function r() {
            this._events = this._events || {}, this._maxListeners = this._maxListeners || void 0
          }

          function i(t) {
            return "function" == typeof t
          }

          function o(t) {
            return "number" == typeof t
          }

          function a(t) {
            return "object" == typeof t && null !== t
          }

          function s(t) {
            return void 0 === t
          }
          e.localExports = r, r.EventEmitter = r, r.prototype._events = void 0, r.prototype._maxListeners = void 0, r.defaultMaxListeners = 10, r.prototype.setMaxListeners = function(t) {
            if (!o(t) || t < 0 || isNaN(t)) throw TypeError("n must be a positive number");
            return this._maxListeners = t, this
          }, r.prototype.emit = function(t) {
            var e, n, r, o, u, c;
            if (this._events || (this._events = {}), "error" === t && (!this._events.error || a(this._events.error) && !this._events.error.length)) {
              if ((e = arguments[1]) instanceof Error) throw e;
              throw TypeError('Uncaught, unspecified "error" event.')
            }
            if (n = this._events[t], s(n)) return !1;
            if (i(n)) switch (arguments.length) {
              case 1:
                n.call(this);
                break;
              case 2:
                n.call(this, arguments[1]);
                break;
              case 3:
                n.call(this, arguments[1], arguments[2]);
                break;
              default:
                for (r = arguments.length, o = new Array(r - 1), u = 1; u < r; u++) o[u - 1] = arguments[u];
                n.apply(this, o)
            } else if (a(n)) {
              for (r = arguments.length, o = new Array(r - 1), u = 1; u < r; u++) o[u - 1] = arguments[u];
              for (r = (c = n.slice()).length, u = 0; u < r; u++) c[u].apply(this, o)
            }
            return !0
          }, r.prototype.addListener = function(t, e) {
            if (!i(e)) throw TypeError("listener must be a function");
            if (this._events || (this._events = {}), this._events.newListener && this.emit("newListener", t, i(e.listener) ? e.listener : e), this._events[t] ? a(this._events[t]) ? this._events[t].push(e) : this._events[t] = [this._events[t], e] : this._events[t] = e, a(this._events[t]) && !this._events[t].warned) {
              var n;
              (n = s(this._maxListeners) ? r.defaultMaxListeners : this._maxListeners) && n > 0 && this._events[t].length > n && (this._events[t].warned = !0, console.error("(node) warning: possible EventEmitter memory leak detected. %d listeners added. Use emitter.setMaxListeners() to increase limit.", this._events[t].length), "function" == typeof console.trace && console.trace())
            }
            return this
          }, r.prototype.on = r.prototype.addListener, r.prototype.once = function(t, e) {
            function n() {
              this.removeListener(t, n), r || (r = !0, e.apply(this, arguments))
            }
            if (!i(e)) throw TypeError("listener must be a function");
            var r = !1;
            return n.listener = e, this.on(t, n), this
          }, r.prototype.removeListener = function(t, e) {
            var n, r, o, s;
            if (!i(e)) throw TypeError("listener must be a function");
            if (!this._events || !this._events[t]) return this;
            if (n = this._events[t], o = n.length, r = -1, n === e || i(n.listener) && n.listener === e) delete this._events[t], this._events.removeListener && this.emit("removeListener", t, e);
            else if (a(n)) {
              for (s = o; s-- > 0;)
                if (n[s] === e || n[s].listener && n[s].listener === e) {
                  r = s;
                  break
                }
              if (r < 0) return this;
              1 === n.length ? (n.length = 0, delete this._events[t]) : n.splice(r, 1), this._events.removeListener && this.emit("removeListener", t, e)
            }
            return this
          }, r.prototype.removeAllListeners = function(t) {
            var e, n;
            if (!this._events) return this;
            if (!this._events.removeListener) return 0 === arguments.length ? this._events = {} : this._events[t] && delete this._events[t], this;
            if (0 === arguments.length) {
              for (e in this._events) "removeListener" !== e && this.removeAllListeners(e);
              return this.removeAllListeners("removeListener"), this._events = {}, this
            }
            if (n = this._events[t], i(n)) this.removeListener(t, n);
            else
              for (; n.length;) this.removeListener(t, n[n.length - 1]);
            return delete this._events[t], this
          }, r.prototype.listeners = function(t) {
            return this._events && this._events[t] ? i(this._events[t]) ? [this._events[t]] : this._events[t].slice() : []
          }, r.listenerCount = function(t, e) {
            return t._events && t._events[e] ? i(t._events[e]) ? 1 : t._events[e].length : 0
          }
        }, {}],
        33: [function(t, e, n) {
          (function(n, r) {
            "use strict";

            function i(t) {
              return t && "object" == typeof t && "default" in t ? t.default : t
            }

            function o(t, e, n) {
              if (n.get) {
                var r = n.get;
                n.get = function() {
                  var t = r.call(this);
                  return w(this, e, {
                    value: t
                  }), t
                }
              } else if ("function" == typeof n.value) {
                var i = n.value;
                return {
                  get: function() {
                    function t() {
                      for (var t = arguments.length, e = Array(t), r = 0; r < t; r++) e[r] = arguments[r];
                      var o = e.length > 0 ? e[0] : "value";
                      if (n.has(o)) return n.get(o);
                      var a = i.apply(this, e);
                      return n.set(o, a), a
                    }
                    var n = new C;
                    return w(this, e, {
                      value: t
                    }), t
                  }
                }
              }
            }

            function a(t, e, n) {
              var r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 0;
              return 1 === t && X[r] ? X[r] : K[t][e]
            }

            function s(t, e) {
              for (var n = 0, r = t.length - 1; n <= r;) {
                var i = n + r >> 1,
                  o = e(t[i]);
                if (o < 0) r = i - 1;
                else {
                  if (!(o > 0)) return i;
                  n = i + 1
                }
              }
              return -1
            }

            function u(t, e) {
              for (var n = []; t < e;) n.push(t++);
              return n
            }

            function c(t, e, n, r, i) {
              var o = {};
              return Object.keys(r).forEach(function(t) {
                o[t] = r[t]
              }), o.enumerable = !!o.enumerable, o.configurable = !!o.configurable, ("value" in o || o.initializer) && (o.writable = !0), o = n.slice().reverse().reduce(function(n, r) {
                return r(t, e, n) || n
              }, o), i && void 0 !== o.initializer && (o.value = o.initializer ? o.initializer.call(i) : void 0, o.initializer = void 0), void 0 === o.initializer && (Object.defineProperty(t, e, o), o = null), o
            }

            function l(t) {
              for (var e = t.length, n = 0; n < e;) {
                var r = t.charCodeAt(n++);
                if (55296 <= r && r <= 56319 && n < e) {
                  var i = t.charCodeAt(n);
                  56320 <= i && i <= 57343 && (n++, r = ((1023 & r) << 10) + (1023 & i) + 65536)
                }
                var o = P.getScript(r);
                if ("Common" !== o && "Inherited" !== o && "Unknown" !== o) return gt[o]
              }
              return gt.Unknown
            }

            function h(t) {
              for (var e = 0; e < t.length; e++) {
                var n = t[e],
                  r = P.getScript(n);
                if ("Common" !== r && "Inherited" !== r && "Unknown" !== r) return gt[r]
              }
              return gt.Unknown
            }

            function f(t) {
              return St[t] ? "rtl" : "ltr"
            }

            function d(t, e, n, r, i) {
              var o = {};
              return Object.keys(r).forEach(function(t) {
                o[t] = r[t]
              }), o.enumerable = !!o.enumerable, o.configurable = !!o.configurable, ("value" in o || o.initializer) && (o.writable = !0), o = n.slice().reverse().reduce(function(n, r) {
                return r(t, e, n) || n
              }, o), i && void 0 !== o.initializer && (o.value = o.initializer ? o.initializer.call(i) : void 0, o.initializer = void 0), void 0 === o.initializer && (Object.defineProperty(t, e, o), o = null), o
            }

            function p(t, e, n, r, i) {
              var o = {};
              return Object.keys(r).forEach(function(t) {
                o[t] = r[t]
              }), o.enumerable = !!o.enumerable, o.configurable = !!o.configurable, ("value" in o || o.initializer) && (o.writable = !0), o = n.slice().reverse().reduce(function(n, r) {
                return r(t, e, n) || n
              }, o), i && void 0 !== o.initializer && (o.value = o.initializer ? o.initializer.call(i) : void 0, o.initializer = void 0), void 0 === o.initializer && (Object.defineProperty(t, e, o), o = null), o
            }
            var g = i(t("restructure")),
              _ = i(t("babel-runtime/core-js/object/get-own-property-descriptor")),
              m = i(t("babel-runtime/core-js/get-iterator")),
              v = i(t("babel-runtime/core-js/object/freeze")),
              y = i(t("babel-runtime/core-js/object/keys")),
              b = i(t("babel-runtime/helpers/typeof")),
              w = i(t("babel-runtime/core-js/object/define-property")),
              x = i(t("babel-runtime/helpers/classCallCheck")),
              S = i(t("babel-runtime/helpers/createClass")),
              C = i(t("babel-runtime/core-js/map")),
              k = i(t("babel-runtime/core-js/string/from-code-point")),
              E = i(t("babel-runtime/core-js/array/from")),
              A = i(t("babel-runtime/core-js/set")),
              P = i(t("unicode-properties")),
              j = i(t("babel-runtime/helpers/possibleConstructorReturn")),
              I = i(t("babel-runtime/helpers/inherits")),
              L = i(t("clone")),
              O = {};
            O.logErrors = !1;
            var T = [];
            O.registerFormat = function(t) {
              T.push(t)
            }, O.create = function(t, e) {
              for (var n = 0; n < T.length; n++) {
                var r = T[n];
                if (r.probe(t)) {
                  var i = new r(new g.DecodeStream(t));
                  return e ? i.getFont(e) : i
                }
              }
              throw new Error("Unknown font format")
            };
            var B = new g.Struct({
                firstCode: g.uint16,
                entryCount: g.uint16,
                idDelta: g.int16,
                idRangeOffset: g.uint16
              }),
              N = new g.Struct({
                startCharCode: g.uint32,
                endCharCode: g.uint32,
                glyphID: g.uint32
              }),
              z = new g.Struct({
                startUnicodeValue: g.uint24,
                additionalCount: g.uint8
              }),
              F = new g.Struct({
                unicodeValue: g.uint24,
                glyphID: g.uint16
              }),
              D = new g.Array(z, g.uint32),
              M = new g.Array(F, g.uint32),
              R = new g.Struct({
                varSelector: g.uint24,
                defaultUVS: new g.Pointer(g.uint32, D, {
                  type: "parent"
                }),
                nonDefaultUVS: new g.Pointer(g.uint32, M, {
                  type: "parent"
                })
              }),
              U = new g.VersionedStruct(g.uint16, {
                0: {
                  length: g.uint16,
                  language: g.uint16,
                  codeMap: new g.LazyArray(g.uint8, 256)
                },
                2: {
                  length: g.uint16,
                  language: g.uint16,
                  subHeaderKeys: new g.Array(g.uint16, 256),
                  subHeaderCount: function(t) {
                    return Math.max.apply(Math, t.subHeaderKeys)
                  },
                  subHeaders: new g.LazyArray(B, "subHeaderCount"),
                  glyphIndexArray: new g.LazyArray(g.uint16, "subHeaderCount")
                },
                4: {
                  length: g.uint16,
                  language: g.uint16,
                  segCountX2: g.uint16,
                  segCount: function(t) {
                    return t.segCountX2 >> 1
                  },
                  searchRange: g.uint16,
                  entrySelector: g.uint16,
                  rangeShift: g.uint16,
                  endCode: new g.LazyArray(g.uint16, "segCount"),
                  reservedPad: new g.Reserved(g.uint16),
                  startCode: new g.LazyArray(g.uint16, "segCount"),
                  idDelta: new g.LazyArray(g.int16, "segCount"),
                  idRangeOffset: new g.LazyArray(g.uint16, "segCount"),
                  glyphIndexArray: new g.LazyArray(g.uint16, function(t) {
                    return (t.length - t._currentOffset) / 2
                  })
                },
                6: {
                  length: g.uint16,
                  language: g.uint16,
                  firstCode: g.uint16,
                  entryCount: g.uint16,
                  glyphIndices: new g.LazyArray(g.uint16, "entryCount")
                },
                8: {
                  reserved: new g.Reserved(g.uint16),
                  length: g.uint32,
                  language: g.uint16,
                  is32: new g.LazyArray(g.uint8, 8192),
                  nGroups: g.uint32,
                  groups: new g.LazyArray(N, "nGroups")
                },
                10: {
                  reserved: new g.Reserved(g.uint16),
                  length: g.uint32,
                  language: g.uint32,
                  firstCode: g.uint32,
                  entryCount: g.uint32,
                  glyphIndices: new g.LazyArray(g.uint16, "numChars")
                },
                12: {
                  reserved: new g.Reserved(g.uint16),
                  length: g.uint32,
                  language: g.uint32,
                  nGroups: g.uint32,
                  groups: new g.LazyArray(N, "nGroups")
                },
                13: {
                  reserved: new g.Reserved(g.uint16),
                  length: g.uint32,
                  language: g.uint32,
                  nGroups: g.uint32,
                  groups: new g.LazyArray(N, "nGroups")
                },
                14: {
                  length: g.uint32,
                  numRecords: g.uint32,
                  varSelectors: new g.LazyArray(R, "numRecords")
                }
              }),
              H = new g.Struct({
                platformID: g.uint16,
                encodingID: g.uint16,
                table: new g.Pointer(g.uint32, U, {
                  type: "parent",
                  lazy: !0
                })
              }),
              G = new g.Struct({
                version: g.uint16,
                numSubtables: g.uint16,
                tables: new g.Array(H, "numSubtables")
              }),
              q = new g.Struct({
                version: g.int32,
                revision: g.int32,
                checkSumAdjustment: g.uint32,
                magicNumber: g.uint32,
                flags: g.uint16,
                unitsPerEm: g.uint16,
                created: new g.Array(g.int32, 2),
                modified: new g.Array(g.int32, 2),
                xMin: g.int16,
                yMin: g.int16,
                xMax: g.int16,
                yMax: g.int16,
                macStyle: new g.Bitfield(g.uint16, ["bold", "italic", "underline", "outline", "shadow", "condensed", "extended"]),
                lowestRecPPEM: g.uint16,
                fontDirectionHint: g.int16,
                indexToLocFormat: g.int16,
                glyphDataFormat: g.int16
              }),
              Z = new g.Struct({
                version: g.int32,
                ascent: g.int16,
                descent: g.int16,
                lineGap: g.int16,
                advanceWidthMax: g.uint16,
                minLeftSideBearing: g.int16,
                minRightSideBearing: g.int16,
                xMaxExtent: g.int16,
                caretSlopeRise: g.int16,
                caretSlopeRun: g.int16,
                caretOffset: g.int16,
                reserved: new g.Reserved(g.int16, 4),
                metricDataFormat: g.int16,
                numberOfMetrics: g.uint16
              }),
              W = new g.Struct({
                advance: g.uint16,
                bearing: g.int16
              }),
              V = new g.Struct({
                metrics: new g.LazyArray(W, function(t) {
                  return t.parent.hhea.numberOfMetrics
                }),
                bearings: new g.LazyArray(g.int16, function(t) {
                  return t.parent.maxp.numGlyphs - t.parent.hhea.numberOfMetrics
                })
              }),
              Y = new g.Struct({
                version: g.int32,
                numGlyphs: g.uint16,
                maxPoints: g.uint16,
                maxContours: g.uint16,
                maxComponentPoints: g.uint16,
                maxComponentContours: g.uint16,
                maxZones: g.uint16,
                maxTwilightPoints: g.uint16,
                maxStorage: g.uint16,
                maxFunctionDefs: g.uint16,
                maxInstructionDefs: g.uint16,
                maxStackElements: g.uint16,
                maxSizeOfInstructions: g.uint16,
                maxComponentElements: g.uint16,
                maxComponentDepth: g.uint16
              }),
              K = [
                ["utf16be", "utf16be", "utf16be", "utf16be", "utf16be", "utf16be"],
                ["macroman", "shift-jis", "big5", "euc-kr", "iso-8859-6", "iso-8859-8", "macgreek", "maccyrillic", "symbol", "Devanagari", "Gurmukhi", "Gujarati", "Oriya", "Bengali", "Tamil", "Telugu", "Kannada", "Malayalam", "Sinhalese", "Burmese", "Khmer", "macthai", "Laotian", "Georgian", "Armenian", "gb-2312-80", "Tibetan", "Mongolian", "Geez", "maccenteuro", "Vietnamese", "Sindhi"],
                ["ascii"],
                ["symbol", "utf16be", "shift-jis", "gb18030", "big5", "wansung", "johab", null, null, null, "utf16be"]
              ],
              X = {
                15: "maciceland",
                17: "macturkish",
                18: "maccroatian",
                24: "maccenteuro",
                25: "maccenteuro",
                26: "maccenteuro",
                27: "maccenteuro",
                28: "maccenteuro",
                30: "maciceland",
                37: "macromania",
                38: "maccenteuro",
                39: "maccenteuro",
                40: "maccenteuro",
                143: "macinuit",
                146: "macgaelic"
              },
              J = [
                [], {
                  0: "en",
                  30: "fo",
                  60: "ks",
                  90: "rw",
                  1: "fr",
                  31: "fa",
                  61: "ku",
                  91: "rn",
                  2: "de",
                  32: "ru",
                  62: "sd",
                  92: "ny",
                  3: "it",
                  33: "zh",
                  63: "bo",
                  93: "mg",
                  4: "nl",
                  34: "nl-BE",
                  64: "ne",
                  94: "eo",
                  5: "sv",
                  35: "ga",
                  65: "sa",
                  128: "cy",
                  6: "es",
                  36: "sq",
                  66: "mr",
                  129: "eu",
                  7: "da",
                  37: "ro",
                  67: "bn",
                  130: "ca",
                  8: "pt",
                  38: "cz",
                  68: "as",
                  131: "la",
                  9: "no",
                  39: "sk",
                  69: "gu",
                  132: "qu",
                  10: "he",
                  40: "si",
                  70: "pa",
                  133: "gn",
                  11: "ja",
                  41: "yi",
                  71: "or",
                  134: "ay",
                  12: "ar",
                  42: "sr",
                  72: "ml",
                  135: "tt",
                  13: "fi",
                  43: "mk",
                  73: "kn",
                  136: "ug",
                  14: "el",
                  44: "bg",
                  74: "ta",
                  137: "dz",
                  15: "is",
                  45: "uk",
                  75: "te",
                  138: "jv",
                  16: "mt",
                  46: "be",
                  76: "si",
                  139: "su",
                  17: "tr",
                  47: "uz",
                  77: "my",
                  140: "gl",
                  18: "hr",
                  48: "kk",
                  78: "km",
                  141: "af",
                  19: "zh-Hant",
                  49: "az-Cyrl",
                  79: "lo",
                  142: "br",
                  20: "ur",
                  50: "az-Arab",
                  80: "vi",
                  143: "iu",
                  21: "hi",
                  51: "hy",
                  81: "id",
                  144: "gd",
                  22: "th",
                  52: "ka",
                  82: "tl",
                  145: "gv",
                  23: "ko",
                  53: "mo",
                  83: "ms",
                  146: "ga",
                  24: "lt",
                  54: "ky",
                  84: "ms-Arab",
                  147: "to",
                  25: "pl",
                  55: "tg",
                  85: "am",
                  148: "el-polyton",
                  26: "hu",
                  56: "tk",
                  86: "ti",
                  149: "kl",
                  27: "es",
                  57: "mn-CN",
                  87: "om",
                  150: "az",
                  28: "lv",
                  58: "mn",
                  88: "so",
                  151: "nn",
                  29: "se",
                  59: "ps",
                  89: "sw"
                },
                [], {
                  1078: "af",
                  16393: "en-IN",
                  1159: "rw",
                  1074: "tn",
                  1052: "sq",
                  6153: "en-IE",
                  1089: "sw",
                  1115: "si",
                  1156: "gsw",
                  8201: "en-JM",
                  1111: "kok",
                  1051: "sk",
                  1118: "am",
                  17417: "en-MY",
                  1042: "ko",
                  1060: "sl",
                  5121: "ar-DZ",
                  5129: "en-NZ",
                  1088: "ky",
                  11274: "es-AR",
                  15361: "ar-BH",
                  13321: "en-PH",
                  1108: "lo",
                  16394: "es-BO",
                  3073: "ar",
                  18441: "en-SG",
                  1062: "lv",
                  13322: "es-CL",
                  2049: "ar-IQ",
                  7177: "en-ZA",
                  1063: "lt",
                  9226: "es-CO",
                  11265: "ar-JO",
                  11273: "en-TT",
                  2094: "dsb",
                  5130: "es-CR",
                  13313: "ar-KW",
                  2057: "en-GB",
                  1134: "lb",
                  7178: "es-DO",
                  12289: "ar-LB",
                  1033: "en",
                  1071: "mk",
                  12298: "es-EC",
                  4097: "ar-LY",
                  12297: "en-ZW",
                  2110: "ms-BN",
                  17418: "es-SV",
                  6145: "ary",
                  1061: "et",
                  1086: "ms",
                  4106: "es-GT",
                  8193: "ar-OM",
                  1080: "fo",
                  1100: "ml",
                  18442: "es-HN",
                  16385: "ar-QA",
                  1124: "fil",
                  1082: "mt",
                  2058: "es-MX",
                  1025: "ar-SA",
                  1035: "fi",
                  1153: "mi",
                  19466: "es-NI",
                  10241: "ar-SY",
                  2060: "fr-BE",
                  1146: "arn",
                  6154: "es-PA",
                  7169: "aeb",
                  3084: "fr-CA",
                  1102: "mr",
                  15370: "es-PY",
                  14337: "ar-AE",
                  1036: "fr",
                  1148: "moh",
                  10250: "es-PE",
                  9217: "ar-YE",
                  5132: "fr-LU",
                  1104: "mn",
                  20490: "es-PR",
                  1067: "hy",
                  6156: "fr-MC",
                  2128: "mn-CN",
                  3082: "es",
                  1101: "as",
                  4108: "fr-CH",
                  1121: "ne",
                  1034: "es",
                  2092: "az-Cyrl",
                  1122: "fy",
                  1044: "nb",
                  21514: "es-US",
                  1068: "az",
                  1110: "gl",
                  2068: "nn",
                  14346: "es-UY",
                  1133: "ba",
                  1079: "ka",
                  1154: "oc",
                  8202: "es-VE",
                  1069: "eu",
                  3079: "de-AT",
                  1096: "or",
                  2077: "sv-FI",
                  1059: "be",
                  1031: "de",
                  1123: "ps",
                  1053: "sv",
                  2117: "bn",
                  5127: "de-LI",
                  1045: "pl",
                  1114: "syr",
                  1093: "bn-IN",
                  4103: "de-LU",
                  1046: "pt",
                  1064: "tg",
                  8218: "bs-Cyrl",
                  2055: "de-CH",
                  2070: "pt-PT",
                  2143: "tzm",
                  5146: "bs",
                  1032: "el",
                  1094: "pa",
                  1097: "ta",
                  1150: "br",
                  1135: "kl",
                  1131: "qu-BO",
                  1092: "tt",
                  1026: "bg",
                  1095: "gu",
                  2155: "qu-EC",
                  1098: "te",
                  1027: "ca",
                  1128: "ha",
                  3179: "qu",
                  1054: "th",
                  3076: "zh-HK",
                  1037: "he",
                  1048: "ro",
                  1105: "bo",
                  5124: "zh-MO",
                  1081: "hi",
                  1047: "rm",
                  1055: "tr",
                  2052: "zh",
                  1038: "hu",
                  1049: "ru",
                  1090: "tk",
                  4100: "zh-SG",
                  1039: "is",
                  9275: "smn",
                  1152: "ug",
                  1028: "zh-TW",
                  1136: "ig",
                  4155: "smj-NO",
                  1058: "uk",
                  1155: "co",
                  1057: "id",
                  5179: "smj",
                  1070: "hsb",
                  1050: "hr",
                  1117: "iu",
                  3131: "se-FI",
                  1056: "ur",
                  4122: "hr-BA",
                  2141: "iu-Latn",
                  1083: "se",
                  2115: "uz-Cyrl",
                  1029: "cs",
                  2108: "ga",
                  2107: "se-SE",
                  1091: "uz",
                  1030: "da",
                  1076: "xh",
                  8251: "sms",
                  1066: "vi",
                  1164: "prs",
                  1077: "zu",
                  6203: "sma-NO",
                  1106: "cy",
                  1125: "dv",
                  1040: "it",
                  7227: "sms",
                  1160: "wo",
                  2067: "nl-BE",
                  2064: "it-CH",
                  1103: "sa",
                  1157: "sah",
                  1043: "nl",
                  1041: "ja",
                  7194: "sr-Cyrl-BA",
                  1144: "ii",
                  3081: "en-AU",
                  1099: "kn",
                  3098: "sr",
                  1130: "yo",
                  10249: "en-BZ",
                  1087: "kk",
                  6170: "sr-Latn-BA",
                  4105: "en-CA",
                  1107: "km",
                  2074: "sr-Latn",
                  9225: "en-029",
                  1158: "quc",
                  1132: "nso"
                }
              ],
              Q = new g.Struct({
                platformID: g.uint16,
                encodingID: g.uint16,
                languageID: g.uint16,
                nameID: g.uint16,
                length: g.uint16,
                string: new g.Pointer(g.uint16, new g.String("length", function(t) {
                  return a(t.platformID, t.encodingID, t.languageID)
                }), {
                  type: "parent",
                  relativeTo: "parent.stringOffset",
                  allowNull: !1
                })
              }),
              $ = new g.Struct({
                length: g.uint16,
                tag: new g.Pointer(g.uint16, new g.String("length", "utf16be"), {
                  type: "parent",
                  relativeTo: "stringOffset"
                })
              }),
              tt = new g.VersionedStruct(g.uint16, {
                0: {
                  count: g.uint16,
                  stringOffset: g.uint16,
                  records: new g.Array(Q, "count")
                },
                1: {
                  count: g.uint16,
                  stringOffset: g.uint16,
                  records: new g.Array(Q, "count"),
                  langTagCount: g.uint16,
                  langTags: new g.Array($, "langTagCount")
                }
              }),
              et = ["copyright", "fontFamily", "fontSubfamily", "uniqueSubfamily", "fullName", "version", "postscriptName", "trademark", "manufacturer", "designer", "description", "vendorURL", "designerURL", "license", "licenseURL", null, "preferredFamily", "preferredSubfamily", "compatibleFull", "sampleText", "postscriptCIDFontName", "wwsFamilyName", "wwsSubfamilyName"];
            tt.process = function(t) {
              for (var e = {}, n = this.records, r = Array.isArray(n), i = 0, n = r ? n : m(n);;) {
                var o;
                if (r) {
                  if (i >= n.length) break;
                  o = n[i++]
                } else {
                  if ((i = n.next()).done) break;
                  o = i.value
                }
                var a = o,
                  s = J[a.platformID][a.languageID];
                null == s && null != this.langTags && a.languageID >= 32768 && (s = this.langTags[a.languageID - 32768].tag), null == s && (s = a.platformID + "-" + a.languageID);
                var u = a.nameID >= 256 ? "fontFeatures" : et[a.nameID] || a.nameID;
                null == e[u] && (e[u] = {});
                var c = e[u];
                a.nameID >= 256 && (c = c[a.nameID] || (c[a.nameID] = {})), "string" != typeof a.string && "string" == typeof c[s] || (c[s] = a.string)
              }
              this.records = e
            }, tt.preEncode = function() {
              if (!Array.isArray(this.records)) {
                this.version = 0;
                var t = [];
                for (var e in this.records) {
                  var n = this.records[e];
                  "fontFeatures" !== e && (t.push({
                    platformID: 3,
                    encodingID: 1,
                    languageID: 1033,
                    nameID: et.indexOf(e),
                    length: r.byteLength(n.en, "utf16le"),
                    string: n.en
                  }), "postscriptName" === e && t.push({
                    platformID: 1,
                    encodingID: 0,
                    languageID: 0,
                    nameID: et.indexOf(e),
                    length: n.en.length,
                    string: n.en
                  }))
                }
                this.records = t, this.count = t.length, this.stringOffset = tt.size(this, null, !1)
              }
            };
            var nt = new g.VersionedStruct(g.uint16, {
                header: {
                  xAvgCharWidth: g.int16,
                  usWeightClass: g.uint16,
                  usWidthClass: g.uint16,
                  fsType: new g.Bitfield(g.uint16, [null, "noEmbedding", "viewOnly", "editable", null, null, null, null, "noSubsetting", "bitmapOnly"]),
                  ySubscriptXSize: g.int16,
                  ySubscriptYSize: g.int16,
                  ySubscriptXOffset: g.int16,
                  ySubscriptYOffset: g.int16,
                  ySuperscriptXSize: g.int16,
                  ySuperscriptYSize: g.int16,
                  ySuperscriptXOffset: g.int16,
                  ySuperscriptYOffset: g.int16,
                  yStrikeoutSize: g.int16,
                  yStrikeoutPosition: g.int16,
                  sFamilyClass: g.int16,
                  panose: new g.Array(g.uint8, 10),
                  ulCharRange: new g.Array(g.uint32, 4),
                  vendorID: new g.String(4),
                  fsSelection: new g.Bitfield(g.uint16, ["italic", "underscore", "negative", "outlined", "strikeout", "bold", "regular", "useTypoMetrics", "wws", "oblique"]),
                  usFirstCharIndex: g.uint16,
                  usLastCharIndex: g.uint16
                },
                0: {},
                1: {
                  typoAscender: g.int16,
                  typoDescender: g.int16,
                  typoLineGap: g.int16,
                  winAscent: g.uint16,
                  winDescent: g.uint16,
                  codePageRange: new g.Array(g.uint32, 2)
                },
                2: {
                  typoAscender: g.int16,
                  typoDescender: g.int16,
                  typoLineGap: g.int16,
                  winAscent: g.uint16,
                  winDescent: g.uint16,
                  codePageRange: new g.Array(g.uint32, 2),
                  xHeight: g.int16,
                  capHeight: g.int16,
                  defaultChar: g.uint16,
                  breakChar: g.uint16,
                  maxContent: g.uint16
                },
                5: {
                  typoAscender: g.int16,
                  typoDescender: g.int16,
                  typoLineGap: g.int16,
                  winAscent: g.uint16,
                  winDescent: g.uint16,
                  codePageRange: new g.Array(g.uint32, 2),
                  xHeight: g.int16,
                  capHeight: g.int16,
                  defaultChar: g.uint16,
                  breakChar: g.uint16,
                  maxContent: g.uint16,
                  usLowerOpticalPointSize: g.uint16,
                  usUpperOpticalPointSize: g.uint16
                }
              }),
              rt = nt.versions;
            rt[3] = rt[4] = rt[2];
            var it = new g.VersionedStruct(g.fixed32, {
                header: {
                  italicAngle: g.fixed32,
                  underlinePosition: g.int16,
                  underlineThickness: g.int16,
                  isFixedPitch: g.uint32,
                  minMemType42: g.uint32,
                  maxMemType42: g.uint32,
                  minMemType1: g.uint32,
                  maxMemType1: g.uint32
                },
                1: {},
                2: {
                  numberOfGlyphs: g.uint16,
                  glyphNameIndex: new g.Array(g.uint16, "numberOfGlyphs"),
                  names: new g.Array(new g.String(g.uint8))
                },
                2.5: {
                  numberOfGlyphs: g.uint16,
                  offsets: new g.Array(g.uint8, "numberOfGlyphs")
                },
                3: {},
                4: {
                  map: new g.Array(g.uint32, function(t) {
                    return t.parent.maxp.numGlyphs
                  })
                }
              }),
              ot = new g.VersionedStruct("head.indexToLocFormat", {
                0: {
                  offsets: new g.Array(g.uint16)
                },
                1: {
                  offsets: new g.Array(g.uint32)
                }
              });
            ot.process = function() {
              if (0 === this.version)
                for (var t = 0; t < this.offsets.length; t++) this.offsets[t] <<= 1
            }, ot.preEncode = function() {
              if (null == this.version && (this.version = this.offsets[this.offsets.length - 1] > 65535 ? 1 : 0, 0 === this.version))
                for (var t = 0; t < this.offsets.length; t++) this.offsets[t] >>>= 1
            };
            var at = new g.Array(new g.Buffer),
              st = {};
            st.cmap = G, st.head = q, st.hhea = Z, st.hmtx = V, st.maxp = Y, st.name = tt, st["OS/2"] = nt, st.post = it, st.loca = ot, st.glyf = at;
            var ut = new g.Struct({
                tag: new g.String(4),
                checkSum: g.uint32,
                offset: new g.Pointer(g.uint32, "void", {
                  type: "global"
                }),
                length: g.uint32
              }),
              ct = new g.Struct({
                tag: new g.String(4),
                numTables: g.uint16,
                searchRange: g.uint16,
                entrySelector: g.uint16,
                rangeShift: g.uint16,
                tables: new g.Array(ut, "numTables")
              });
            ct.process = function() {
              for (var t = {}, e = this.tables, n = Array.isArray(e), r = 0, e = n ? e : m(e);;) {
                var i;
                if (n) {
                  if (r >= e.length) break;
                  i = e[r++]
                } else {
                  if ((r = e.next()).done) break;
                  i = r.value
                }
                var o = i;
                t[o.tag] = o
              }
              this.tables = t
            }, ct.preEncode = function(t) {
              var e = [];
              for (var n in this.tables) {
                var r = this.tables[n];
                r && e.push({
                  tag: n,
                  checkSum: 0,
                  offset: new g.VoidPointer(st[n], r),
                  length: st[n].size(r)
                })
              }
              this.tag = "true", this.numTables = e.length, this.tables = e, this.searchRange = 16 * Math.floor(Math.log(this.numTables) / Math.LN2), this.entrySelector = Math.floor(this.searchRange / Math.LN2), this.rangeShift = 16 * this.numTables - this.searchRange
            };
            var lt;
            try {
              var ht = window.iconv
            } catch (t) {}
            var ft = (lt = function() {
                function t(e) {
                  if (x(this, t), this.encoding = null, this.cmap = this.findSubtable(e, [
                    [3, 10],
                    [0, 6],
                    [0, 4],
                    [3, 1],
                    [0, 3],
                    [0, 2],
                    [0, 1],
                    [0, 0]
                  ]), !this.cmap && ht)
                    for (var n = e.tables, r = Array.isArray(n), i = 0, n = r ? n : m(n);;) {
                      var o;
                      if (r) {
                        if (i >= n.length) break;
                        o = n[i++]
                      } else {
                        if ((i = n.next()).done) break;
                        o = i.value
                      }
                      var s = o,
                        u = a(s.platformID, s.encodingID, s.table.language - 1);
                      ht.encodingExists(u) && (this.cmap = s.table, this.encoding = u)
                    }
                  if (!this.cmap) throw new Error("Could not find a supported cmap table");
                  this.uvs = this.findSubtable(e, [
                    [0, 5]
                  ]), this.uvs && 14 !== this.uvs.version && (this.uvs = null)
                }
                return t.prototype.findSubtable = function(t, e) {
                  for (var n = e, r = Array.isArray(n), i = 0, n = r ? n : m(n);;) {
                    var o;
                    if (r) {
                      if (i >= n.length) break;
                      o = n[i++]
                    } else {
                      if ((i = n.next()).done) break;
                      o = i.value
                    }
                    for (var a = o, s = a[0], u = a[1], c = t.tables, l = Array.isArray(c), h = 0, c = l ? c : m(c);;) {
                      var f;
                      if (l) {
                        if (h >= c.length) break;
                        f = c[h++]
                      } else {
                        if ((h = c.next()).done) break;
                        f = h.value
                      }
                      var d = f;
                      if (d.platformID === s && d.encodingID === u) return d.table
                    }
                  }
                  return null
                }, t.prototype.lookup = function(t, e) {
                  if (this.encoding) {
                    var n = ht.encode(k(t), this.encoding);
                    t = 0;
                    for (var r = 0; r < n.length; r++) t = t << 8 | n[r]
                  } else if (e) {
                    var i = this.getVariationSelector(t, e);
                    if (i) return i
                  }
                  var o = this.cmap;
                  switch (o.version) {
                    case 0:
                      return o.codeMap.get(t) || 0;
                    case 4:
                      for (var a = 0, s = o.segCount - 1; a <= s;) {
                        var u = a + s >> 1;
                        if (t < o.startCode.get(u)) s = u - 1;
                        else {
                          if (!(t > o.endCode.get(u))) {
                            var c = o.idRangeOffset.get(u),
                              l = void 0;
                            if (0 === c) l = t + o.idDelta.get(u);
                            else {
                              var h = c / 2 + (t - o.startCode.get(u)) - (o.segCount - u);
                              0 !== (l = o.glyphIndexArray.get(h) || 0) && (l += o.idDelta.get(u))
                            }
                            return 65535 & l
                          }
                          a = u + 1
                        }
                      }
                      return 0;
                    case 8:
                      throw new Error("TODO: cmap format 8");
                    case 6:
                    case 10:
                      return o.glyphIndices.get(t - o.firstCode) || 0;
                    case 12:
                    case 13:
                      for (var f = 0, d = o.nGroups - 1; f <= d;) {
                        var p = f + d >> 1,
                          g = o.groups.get(p);
                        if (t < g.startCharCode) d = p - 1;
                        else {
                          if (!(t > g.endCharCode)) return 12 === o.version ? g.glyphID + (t - g.startCharCode) : g.glyphID;
                          f = p + 1
                        }
                      }
                      return 0;
                    case 14:
                      throw new Error("TODO: cmap format 14");
                    default:
                      throw new Error("Unknown cmap format " + o.version)
                  }
                }, t.prototype.getVariationSelector = function(t, e) {
                  if (!this.uvs) return 0;
                  var n = this.uvs.varSelectors.toArray(),
                    r = s(n, function(t) {
                      return e - t.varSelector
                    }),
                    i = n[r];
                  return -1 !== r && i.defaultUVS && (r = s(i.defaultUVS, function(e) {
                    return t < e.startUnicodeValue ? -1 : t > e.startUnicodeValue + e.additionalCount ? 1 : 0
                  })), -1 !== r && i.nonDefaultUVS && -1 !== (r = s(i.nonDefaultUVS, function(e) {
                    return t - e.unicodeValue
                  })) ? i.nonDefaultUVS[r].glyphID : 0
                }, t.prototype.getCharacterSet = function() {
                  var t = this.cmap;
                  switch (t.version) {
                    case 0:
                      return u(0, t.codeMap.length);
                    case 4:
                      for (var e = [], n = t.endCode.toArray(), r = 0; r < n.length; r++) {
                        var i = n[r] + 1,
                          o = t.startCode.get(r);
                        e.push.apply(e, u(o, i))
                      }
                      return e;
                    case 8:
                      throw new Error("TODO: cmap format 8");
                    case 6:
                    case 10:
                      return u(t.firstCode, t.firstCode + t.glyphIndices.length);
                    case 12:
                    case 13:
                      for (var a = [], s = t.groups.toArray(), c = Array.isArray(s), l = 0, s = c ? s : m(s);;) {
                        var h;
                        if (c) {
                          if (l >= s.length) break;
                          h = s[l++]
                        } else {
                          if ((l = s.next()).done) break;
                          h = l.value
                        }
                        var f = h;
                        a.push.apply(a, u(f.startCharCode, f.endCharCode + 1))
                      }
                      return a;
                    case 14:
                      throw new Error("TODO: cmap format 14");
                    default:
                      throw new Error("Unknown cmap format " + t.version)
                  }
                }, t.prototype.codePointsForGlyph = function(t) {
                  var e = this.cmap;
                  switch (e.version) {
                    case 0:
                      for (var n = [], r = 0; r < 256; r++) e.codeMap.get(r) === t && n.push(r);
                      return n;
                    case 4:
                      for (var i = [], o = 0; o < e.segCount; o++)
                        for (var a = e.endCode.get(o), s = e.startCode.get(o), c = e.idRangeOffset.get(o), l = e.idDelta.get(o), h = s; h <= a; h++) {
                          var f = 0;
                          if (0 === c) f = h + l;
                          else {
                            var d = c / 2 + (h - s) - (e.segCount - o);
                            0 !== (f = e.glyphIndexArray.get(d) || 0) && (f += l)
                          }
                          f === t && i.push(h)
                        }
                      return i;
                    case 12:
                      for (var p = [], g = e.groups.toArray(), _ = Array.isArray(g), v = 0, g = _ ? g : m(g);;) {
                        var y;
                        if (_) {
                          if (v >= g.length) break;
                          y = g[v++]
                        } else {
                          if ((v = g.next()).done) break;
                          y = v.value
                        }
                        var b = y;
                        t >= b.glyphID && t <= b.glyphID + (b.endCharCode - b.startCharCode) && p.push(b.startCharCode + (t - b.glyphID))
                      }
                      return p;
                    case 13:
                      for (var w = [], x = e.groups.toArray(), S = Array.isArray(x), C = 0, x = S ? x : m(x);;) {
                        var k;
                        if (S) {
                          if (C >= x.length) break;
                          k = x[C++]
                        } else {
                          if ((C = x.next()).done) break;
                          k = C.value
                        }
                        var E = k;
                        t === E.glyphID && w.push.apply(w, u(E.startCharCode, E.endCharCode + 1))
                      }
                      return w;
                    default:
                      throw new Error("Unknown cmap format " + e.version)
                  }
                }, t
              }(), c(lt.prototype, "getCharacterSet", [o], _(lt.prototype, "getCharacterSet"), lt.prototype), c(lt.prototype, "codePointsForGlyph", [o], _(lt.prototype, "codePointsForGlyph"), lt.prototype), lt),
              dt = function() {
                function t(e) {
                  x(this, t), this.font = e
                }
                return t.prototype.positionGlyphs = function(t, e) {
                  for (var n = 0, r = 0, i = 0; i < t.length; i++) t[i].isMark ? r = i : (n !== r && this.positionCluster(t, e, n, r), n = r = i);
                  return n !== r && this.positionCluster(t, e, n, r), e
                }, t.prototype.positionCluster = function(t, e, n, r) {
                  var i = t[n],
                    o = i.cbox.copy();
                  i.codePoints.length > 1 && (o.minX += (i.codePoints.length - 1) * o.width / i.codePoints.length);
                  for (var a = -e[n].xAdvance, s = 0, u = this.font.unitsPerEm / 16, c = n + 1; c <= r; c++) {
                    var l = t[c],
                      h = l.cbox,
                      f = e[c],
                      d = this.getCombiningClass(l.codePoints[0]);
                    if ("Not_Reordered" !== d) {
                      switch (f.xOffset = f.yOffset = 0, d) {
                        case "Double_Above":
                        case "Double_Below":
                          f.xOffset += o.minX - h.width / 2 - h.minX;
                          break;
                        case "Attached_Below_Left":
                        case "Below_Left":
                        case "Above_Left":
                          f.xOffset += o.minX - h.minX;
                          break;
                        case "Attached_Above_Right":
                        case "Below_Right":
                        case "Above_Right":
                          f.xOffset += o.maxX - h.width - h.minX;
                          break;
                        default:
                          f.xOffset += o.minX + (o.width - h.width) / 2 - h.minX
                      }
                      switch (d) {
                        case "Double_Below":
                        case "Below_Left":
                        case "Below":
                        case "Below_Right":
                        case "Attached_Below_Left":
                        case "Attached_Below":
                          "Attached_Below_Left" !== d && "Attached_Below" !== d || (o.minY += u), f.yOffset = -o.minY - h.maxY, o.minY += h.height;
                          break;
                        case "Double_Above":
                        case "Above_Left":
                        case "Above":
                        case "Above_Right":
                        case "Attached_Above":
                        case "Attached_Above_Right":
                          "Attached_Above" !== d && "Attached_Above_Right" !== d || (o.maxY += u), f.yOffset = o.maxY - h.minY, o.maxY += h.height
                      }
                      f.xAdvance = f.yAdvance = 0, f.xOffset += a, f.yOffset += s
                    } else a -= f.xAdvance, s -= f.yAdvance
                  }
                }, t.prototype.getCombiningClass = function(t) {
                  var e = P.getCombiningClass(t);
                  if (3584 == (-256 & t))
                    if ("Not_Reordered" === e) switch (t) {
                      case 3633:
                      case 3636:
                      case 3637:
                      case 3638:
                      case 3639:
                      case 3655:
                      case 3660:
                      case 3645:
                      case 3662:
                        return "Above_Right";
                      case 3761:
                      case 3764:
                      case 3765:
                      case 3766:
                      case 3767:
                      case 3771:
                      case 3788:
                      case 3789:
                        return "Above";
                      case 3772:
                        return "Below"
                    } else if (3642 === t) return "Below_Right";
                  switch (e) {
                    case "CCC10":
                    case "CCC11":
                    case "CCC12":
                    case "CCC13":
                    case "CCC14":
                    case "CCC15":
                    case "CCC16":
                    case "CCC17":
                    case "CCC18":
                    case "CCC20":
                    case "CCC22":
                      return "Below";
                    case "CCC23":
                      return "Attached_Above";
                    case "CCC24":
                      return "Above_Right";
                    case "CCC25":
                    case "CCC19":
                      return "Above_Left";
                    case "CCC26":
                      return "Above";
                    case "CCC21":
                      break;
                    case "CCC27":
                    case "CCC28":
                    case "CCC30":
                    case "CCC31":
                    case "CCC33":
                    case "CCC34":
                    case "CCC35":
                    case "CCC36":
                      return "Above";
                    case "CCC29":
                    case "CCC32":
                      return "Below";
                    case "CCC103":
                      return "Below_Right";
                    case "CCC107":
                      return "Above_Right";
                    case "CCC118":
                      return "Below";
                    case "CCC122":
                      return "Above";
                    case "CCC129":
                    case "CCC132":
                      return "Below";
                    case "CCC130":
                      return "Above"
                  }
                  return e
                }, t
              }(),
              pt = function() {
                function t(e, n, r, i) {
                  var o = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 1 / 0,
                    a = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 1 / 0,
                    s = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : -1 / 0,
                    u = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : -1 / 0;
                  x(this, t), this.minX = o, this.minY = a, this.maxX = s, this.maxY = u
                }
                return t.prototype.addPoint = function(t, e) {
                  Math.abs(t) !== 1 / 0 && (t < this.minX && (this.minX = t), t > this.maxX && (this.maxX = t)), Math.abs(e) !== 1 / 0 && (e < this.minY && (this.minY = e), e > this.maxY && (this.maxY = e))
                }, t.prototype.copy = function() {
                  return new t(this.minX, this.minY, this.maxX, this.maxY)
                }, S(t, [{
                  key: "width",
                  get: function() {
                    return this.maxX - this.minX
                  }
                }, {
                  key: "height",
                  get: function() {
                    return this.maxY - this.minY
                  }
                }]), t
              }(),
              gt = {
                Caucasian_Albanian: "aghb",
                Arabic: "arab",
                Imperial_Aramaic: "armi",
                Armenian: "armn",
                Avestan: "avst",
                Balinese: "bali",
                Bamum: "bamu",
                Bassa_Vah: "bass",
                Batak: "batk",
                Bengali: ["bng2", "beng"],
                Bopomofo: "bopo",
                Brahmi: "brah",
                Braille: "brai",
                Buginese: "bugi",
                Buhid: "buhd",
                Chakma: "cakm",
                Canadian_Aboriginal: "cans",
                Carian: "cari",
                Cham: "cham",
                Cherokee: "cher",
                Coptic: "copt",
                Cypriot: "cprt",
                Cyrillic: "cyrl",
                Devanagari: ["dev2", "deva"],
                Deseret: "dsrt",
                Duployan: "dupl",
                Egyptian_Hieroglyphs: "egyp",
                Elbasan: "elba",
                Ethiopic: "ethi",
                Georgian: "geor",
                Glagolitic: "glag",
                Gothic: "goth",
                Grantha: "gran",
                Greek: "grek",
                Gujarati: ["gjr2", "gujr"],
                Gurmukhi: ["gur2", "guru"],
                Hangul: "hang",
                Han: "hani",
                Hanunoo: "hano",
                Hebrew: "hebr",
                Hiragana: "hira",
                Pahawh_Hmong: "hmng",
                Katakana_Or_Hiragana: "hrkt",
                Old_Italic: "ital",
                Javanese: "java",
                Kayah_Li: "kali",
                Katakana: "kana",
                Kharoshthi: "khar",
                Khmer: "khmr",
                Khojki: "khoj",
                Kannada: ["knd2", "knda"],
                Kaithi: "kthi",
                Tai_Tham: "lana",
                Lao: "lao ",
                Latin: "latn",
                Lepcha: "lepc",
                Limbu: "limb",
                Linear_A: "lina",
                Linear_B: "linb",
                Lisu: "lisu",
                Lycian: "lyci",
                Lydian: "lydi",
                Mahajani: "mahj",
                Mandaic: "mand",
                Manichaean: "mani",
                Mende_Kikakui: "mend",
                Meroitic_Cursive: "merc",
                Meroitic_Hieroglyphs: "mero",
                Malayalam: ["mlm2", "mlym"],
                Modi: "modi",
                Mongolian: "mong",
                Mro: "mroo",
                Meetei_Mayek: "mtei",
                Myanmar: ["mym2", "mymr"],
                Old_North_Arabian: "narb",
                Nabataean: "nbat",
                Nko: "nko ",
                Ogham: "ogam",
                Ol_Chiki: "olck",
                Old_Turkic: "orkh",
                Oriya: ["ory2", "orya"],
                Osmanya: "osma",
                Palmyrene: "palm",
                Pau_Cin_Hau: "pauc",
                Old_Permic: "perm",
                Phags_Pa: "phag",
                Inscriptional_Pahlavi: "phli",
                Psalter_Pahlavi: "phlp",
                Phoenician: "phnx",
                Miao: "plrd",
                Inscriptional_Parthian: "prti",
                Rejang: "rjng",
                Runic: "runr",
                Samaritan: "samr",
                Old_South_Arabian: "sarb",
                Saurashtra: "saur",
                Shavian: "shaw",
                Sharada: "shrd",
                Siddham: "sidd",
                Khudawadi: "sind",
                Sinhala: "sinh",
                Sora_Sompeng: "sora",
                Sundanese: "sund",
                Syloti_Nagri: "sylo",
                Syriac: "syrc",
                Tagbanwa: "tagb",
                Takri: "takr",
                Tai_Le: "tale",
                New_Tai_Lue: "talu",
                Tamil: ["tml2", "taml"],
                Tai_Viet: "tavt",
                Telugu: ["tel2", "telu"],
                Tifinagh: "tfng",
                Tagalog: "tglg",
                Thaana: "thaa",
                Thai: "thai",
                Tibetan: "tibt",
                Tirhuta: "tirh",
                Ugaritic: "ugar",
                Vai: "vai ",
                Warang_Citi: "wara",
                Old_Persian: "xpeo",
                Cuneiform: "xsux",
                Yi: "yi  ",
                Inherited: "zinh",
                Common: "zyyy",
                Unknown: "zzzz"
              },
              _t = {};
            for (var mt in gt) {
              var vt = gt[mt];
              if (Array.isArray(vt))
                for (var yt = vt, bt = Array.isArray(yt), wt = 0, yt = bt ? yt : m(yt);;) {
                  var xt;
                  if (bt) {
                    if (wt >= yt.length) break;
                    xt = yt[wt++]
                  } else {
                    if ((wt = yt.next()).done) break;
                    xt = wt.value
                  }
                  _t[xt] = mt
                } else _t[vt] = mt
            }
            for (var St = {
              arab: !0,
              hebr: !0,
              syrc: !0,
              thaa: !0,
              cprt: !0,
              khar: !0,
              phnx: !0,
              "nko ": !0,
              lydi: !0,
              avst: !0,
              armi: !0,
              phli: !0,
              prti: !0,
              sarb: !0,
              orkh: !0,
              samr: !0,
              mand: !0,
              merc: !0,
              mero: !0,
              mani: !0,
              mend: !0,
              nbat: !0,
              narb: !0,
              palm: !0,
              phlp: !0
            }, Ct = function() {
              function t(e, n, r, i, o) {
                if (x(this, t), this.glyphs = e, this.positions = null, this.script = r, this.language = i || null, this.direction = o || f(r), this.features = {}, Array.isArray(n))
                  for (var a = n, s = Array.isArray(a), u = 0, a = s ? a : m(a);;) {
                    var c;
                    if (s) {
                      if (u >= a.length) break;
                      c = a[u++]
                    } else {
                      if ((u = a.next()).done) break;
                      c = u.value
                    }
                    var l = c;
                    this.features[l] = !0
                  } else "object" === (void 0 === n ? "undefined" : b(n)) && (this.features = n)
              }
              return S(t, [{
                key: "advanceWidth",
                get: function() {
                  for (var t = 0, e = this.positions, n = Array.isArray(e), r = 0, e = n ? e : m(e);;) {
                    var i;
                    if (n) {
                      if (r >= e.length) break;
                      i = e[r++]
                    } else {
                      if ((r = e.next()).done) break;
                      i = r.value
                    }
                    t += i.xAdvance
                  }
                  return t
                }
              }, {
                key: "advanceHeight",
                get: function() {
                  for (var t = 0, e = this.positions, n = Array.isArray(e), r = 0, e = n ? e : m(e);;) {
                    var i;
                    if (n) {
                      if (r >= e.length) break;
                      i = e[r++]
                    } else {
                      if ((r = e.next()).done) break;
                      i = r.value
                    }
                    t += i.yAdvance
                  }
                  return t
                }
              }, {
                key: "bbox",
                get: function() {
                  for (var t = new pt, e = 0, n = 0, r = 0; r < this.glyphs.length; r++) {
                    var i = this.glyphs[r],
                      o = this.positions[r],
                      a = i.bbox;
                    t.addPoint(a.minX + e + o.xOffset, a.minY + n + o.yOffset), t.addPoint(a.maxX + e + o.xOffset, a.maxY + n + o.yOffset), e += o.xAdvance, n += o.yAdvance
                  }
                  return t
                }
              }]), t
            }(), kt = function t(e) {
              var n = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : 0,
                r = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : 0,
                i = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 0,
                o = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : 0;
              x(this, t), this.xAdvance = n, this.yAdvance = r, this.xOffset = i, this.yOffset = o
            }, Et = function() {
              function t(e) {
                x(this, t), this.font = e, this.unicodeLayoutEngine = null, this.kernProcessor = null
              }
              return t.prototype.layout = function(t, e, n, r, i) {
                if ("string" == typeof e && (i = r, r = n, n = e, e = []), "string" == typeof t) {
                  null == n && (n = l(t));
                  d = this.font.glyphsForString(t)
                } else {
                  if (null == n) {
                    for (var o = [], a = t, s = Array.isArray(a), u = 0, a = s ? a : m(a);;) {
                      var c;
                      if (s) {
                        if (u >= a.length) break;
                        c = a[u++]
                      } else {
                        if ((u = a.next()).done) break;
                        c = u.value
                      }
                      var f = c;
                      o.push.apply(o, f.codePoints)
                    }
                    n = h(o)
                  }
                  var d = t
                }
                var p = new Ct(d, e, n, r, i);
                return 0 === d.length ? (p.positions = [], p) : (this.engine && this.engine.setup && this.engine.setup(p), this.substitute(p), this.position(p), this.hideDefaultIgnorables(p.glyphs, p.positions), this.engine && this.engine.cleanup && this.engine.cleanup(), p)
              }, t.prototype.substitute = function(t) {
                this.engine && this.engine.substitute && this.engine.substitute(t)
              }, t.prototype.position = function(t) {
                t.positions = t.glyphs.map(function(t) {
                  return new kt(t.advanceWidth)
                });
                var e = null;
                this.engine && this.engine.position && (e = this.engine.position(t)), e || this.engine && !this.engine.fallbackPosition || (this.unicodeLayoutEngine || (this.unicodeLayoutEngine = new dt(this.font)), this.unicodeLayoutEngine.positionGlyphs(t.glyphs, t.positions))
              }, t.prototype.hideDefaultIgnorables = function(t, e) {
                for (var n = this.font.glyphForCodePoint(32), r = 0; r < t.length; r++) this.isDefaultIgnorable(t[r].codePoints[0]) && (t[r] = n, e[r].xAdvance = 0, e[r].yAdvance = 0)
              }, t.prototype.isDefaultIgnorable = function(t) {
                var e = t >> 16;
                if (0 === e) switch (t >> 8) {
                  case 0:
                    return 173 === t;
                  case 3:
                    return 847 === t;
                  case 6:
                    return 1564 === t;
                  case 23:
                    return 6068 <= t && t <= 6069;
                  case 24:
                    return 6155 <= t && t <= 6158;
                  case 32:
                    return 8203 <= t && t <= 8207 || 8234 <= t && t <= 8238 || 8288 <= t && t <= 8303;
                  case 254:
                    return 65024 <= t && t <= 65039 || 65279 === t;
                  case 255:
                    return 65520 <= t && t <= 65528;
                  default:
                    return !1
                } else switch (e) {
                  case 1:
                    return 113824 <= t && t <= 113827 || 119155 <= t && t <= 119162;
                  case 14:
                    return 917504 <= t && t <= 921599;
                  default:
                    return !1
                }
              }, t.prototype.getAvailableFeatures = function(t, e) {
                var n = [];
                return this.engine && n.push.apply(n, this.engine.getAvailableFeatures(t, e)), this.font.kern && -1 === n.indexOf("kern") && n.push("kern"), n
              }, t.prototype.stringsForGlyph = function(t) {
                for (var e = new A, n = this.font._cmapProcessor.codePointsForGlyph(t), r = Array.isArray(n), i = 0, n = r ? n : m(n);;) {
                  var o;
                  if (r) {
                    if (i >= n.length) break;
                    o = n[i++]
                  } else {
                    if ((i = n.next()).done) break;
                    o = i.value
                  }
                  var a = o;
                  e.add(k(a))
                }
                if (this.engine && this.engine.stringsForGlyph)
                  for (var s = this.engine.stringsForGlyph(t), u = Array.isArray(s), c = 0, s = u ? s : m(s);;) {
                    var l;
                    if (u) {
                      if (c >= s.length) break;
                      l = s[c++]
                    } else {
                      if ((c = s.next()).done) break;
                      l = c.value
                    }
                    var h = l;
                    e.add(h)
                  }
                return E(e)
              }, t
            }(), At = {
              moveTo: "M",
              lineTo: "L",
              quadraticCurveTo: "Q",
              bezierCurveTo: "C",
              closePath: "Z"
            }, Pt = function() {
              function t() {
                x(this, t), this.commands = [], this._bbox = null, this._cbox = null
              }
              return t.prototype.toFunction = function() {
                var t = this.commands.map(function(t) {
                  return "  ctx." + t.command + "(" + t.args.join(", ") + ");"
                });
                return new Function("ctx", t.join("\n"))
              }, t.prototype.toSVG = function() {
                return this.commands.map(function(t) {
                  var e = t.args.map(function(t) {
                    return Math.round(100 * t) / 100
                  });
                  return "" + At[t.command] + e.join(" ")
                }).join("")
              }, t.prototype.mapPoints = function(e) {
                for (var n = new t, r = this.commands, i = Array.isArray(r), o = 0, r = i ? r : m(r);;) {
                  var a;
                  if (i) {
                    if (o >= r.length) break;
                    a = r[o++]
                  } else {
                    if ((o = r.next()).done) break;
                    a = o.value
                  }
                  for (var s = a, u = [], c = 0; c < s.args.length; c += 2) {
                    var l = e(s.args[c], s.args[c + 1]),
                      h = l[0],
                      f = l[1];
                    u.push(h, f)
                  }
                  n[s.command].apply(n, u)
                }
                return n
              }, t.prototype.transform = function(t, e, n, r, i, o) {
                return this.mapPoints(function(a, s) {
                  return a = t * a + n * s + i, s = e * a + r * s + o, [a, s]
                })
              }, t.prototype.translate = function(t, e) {
                return this.transform(1, 0, 0, 1, t, e)
              }, t.prototype.rotate = function(t) {
                var e = Math.cos(t),
                  n = Math.sin(t);
                return this.transform(e, n, -n, e, 0, 0)
              }, t.prototype.scale = function(t) {
                var e = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : t;
                return this.transform(t, 0, 0, e, 0, 0)
              }, S(t, [{
                key: "cbox",
                get: function() {
                  if (!this._cbox) {
                    for (var t = new pt, e = this.commands, n = Array.isArray(e), r = 0, e = n ? e : m(e);;) {
                      var i;
                      if (n) {
                        if (r >= e.length) break;
                        i = e[r++]
                      } else {
                        if ((r = e.next()).done) break;
                        i = r.value
                      }
                      for (var o = i, a = 0; a < o.args.length; a += 2) t.addPoint(o.args[a], o.args[a + 1])
                    }
                    this._cbox = v(t)
                  }
                  return this._cbox
                }
              }, {
                key: "bbox",
                get: function() {
                  if (this._bbox) return this._bbox;
                  for (var t = new pt, e = 0, n = 0, r = function(t) {
                    return Math.pow(1 - t, 3) * C[P] + 3 * Math.pow(1 - t, 2) * t * k[P] + 3 * (1 - t) * Math.pow(t, 2) * E[P] + Math.pow(t, 3) * A[P]
                  }, i = this.commands, o = Array.isArray(i), a = 0, i = o ? i : m(i);;) {
                    var s;
                    if (o) {
                      if (a >= i.length) break;
                      s = i[a++]
                    } else {
                      if ((a = i.next()).done) break;
                      s = a.value
                    }
                    var u = s;
                    switch (u.command) {
                      case "moveTo":
                      case "lineTo":
                        var c = u.args,
                          l = c[0],
                          h = c[1];
                        t.addPoint(l, h), e = l, n = h;
                        break;
                      case "quadraticCurveTo":
                      case "bezierCurveTo":
                        if ("quadraticCurveTo" === u.command) var f = u.args,
                          d = f[0],
                          p = f[1],
                          g = e + 2 / 3 * (d - e),
                          _ = n + 2 / 3 * (p - n),
                          y = (x = f[2]) + 2 / 3 * (d - x),
                          b = (S = f[3]) + 2 / 3 * (p - S);
                        else var w = u.args,
                          g = w[0],
                          _ = w[1],
                          y = w[2],
                          b = w[3],
                          x = w[4],
                          S = w[5];
                        t.addPoint(x, S);
                        for (var C = [e, n], k = [g, _], E = [y, b], A = [x, S], P = 0; P <= 1; P++) {
                          var j = 6 * C[P] - 12 * k[P] + 6 * E[P],
                            I = -3 * C[P] + 9 * k[P] - 9 * E[P] + 3 * A[P];
                          if (u = 3 * k[P] - 3 * C[P], 0 !== I) {
                            var L = Math.pow(j, 2) - 4 * u * I;
                            if (!(L < 0)) {
                              var O = (-j + Math.sqrt(L)) / (2 * I);
                              0 < O && O < 1 && (0 === P ? t.addPoint(r(O), t.maxY) : 1 === P && t.addPoint(t.maxX, r(O)));
                              var T = (-j - Math.sqrt(L)) / (2 * I);
                              0 < T && T < 1 && (0 === P ? t.addPoint(r(T), t.maxY) : 1 === P && t.addPoint(t.maxX, r(T)))
                            }
                          } else {
                            if (0 === j) continue;
                            var B = -u / j;
                            0 < B && B < 1 && (0 === P ? t.addPoint(r(B), t.maxY) : 1 === P && t.addPoint(t.maxX, r(B)))
                          }
                        }
                        e = x, n = S
                    }
                  }
                  return this._bbox = v(t)
                }
              }]), t
            }(), jt = ["moveTo", "lineTo", "quadraticCurveTo", "bezierCurveTo", "closePath"], It = 0; It < jt.length; It++) ! function() {
              var t = jt[It];
              Pt.prototype[t] = function() {
                for (var e = arguments.length, n = Array(e), r = 0; r < e; r++) n[r] = arguments[r];
                return this._bbox = this._cbox = null, this.commands.push({
                  command: t,
                  args: n
                }), this
              }
            }();
            var Lt, Ot, Tt = [".notdef", ".null", "nonmarkingreturn", "space", "exclam", "quotedbl", "numbersign", "dollar", "percent", "ampersand", "quotesingle", "parenleft", "parenright", "asterisk", "plus", "comma", "hyphen", "period", "slash", "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "colon", "semicolon", "less", "equal", "greater", "question", "at", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "bracketleft", "backslash", "bracketright", "asciicircum", "underscore", "grave", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "braceleft", "bar", "braceright", "asciitilde", "Adieresis", "Aring", "Ccedilla", "Eacute", "Ntilde", "Odieresis", "Udieresis", "aacute", "agrave", "acircumflex", "adieresis", "atilde", "aring", "ccedilla", "eacute", "egrave", "ecircumflex", "edieresis", "iacute", "igrave", "icircumflex", "idieresis", "ntilde", "oacute", "ograve", "ocircumflex", "odieresis", "otilde", "uacute", "ugrave", "ucircumflex", "udieresis", "dagger", "degree", "cent", "sterling", "section", "bullet", "paragraph", "germandbls", "registered", "copyright", "trademark", "acute", "dieresis", "notequal", "AE", "Oslash", "infinity", "plusminus", "lessequal", "greaterequal", "yen", "mu", "partialdiff", "summation", "product", "pi", "integral", "ordfeminine", "ordmasculine", "Omega", "ae", "oslash", "questiondown", "exclamdown", "logicalnot", "radical", "florin", "approxequal", "Delta", "guillemotleft", "guillemotright", "ellipsis", "nonbreakingspace", "Agrave", "Atilde", "Otilde", "OE", "oe", "endash", "emdash", "quotedblleft", "quotedblright", "quoteleft", "quoteright", "divide", "lozenge", "ydieresis", "Ydieresis", "fraction", "currency", "guilsinglleft", "guilsinglright", "fi", "fl", "daggerdbl", "periodcentered", "quotesinglbase", "quotedblbase", "perthousand", "Acircumflex", "Ecircumflex", "Aacute", "Edieresis", "Egrave", "Iacute", "Icircumflex", "Idieresis", "Igrave", "Oacute", "Ocircumflex", "apple", "Ograve", "Uacute", "Ucircumflex", "Ugrave", "dotlessi", "circumflex", "tilde", "macron", "breve", "dotaccent", "ring", "cedilla", "hungarumlaut", "ogonek", "caron", "Lslash", "lslash", "Scaron", "scaron", "Zcaron", "zcaron", "brokenbar", "Eth", "eth", "Yacute", "yacute", "Thorn", "thorn", "minus", "multiply", "onesuperior", "twosuperior", "threesuperior", "onehalf", "onequarter", "threequarters", "franc", "Gbreve", "gbreve", "Idotaccent", "Scedilla", "scedilla", "Cacute", "cacute", "Ccaron", "ccaron", "dcroat"],
              Bt = (Lt = function() {
                function t(e, n, r) {
                  x(this, t), this.id = e, this.codePoints = n, this._font = r, this.isMark = this.codePoints.length > 0 && this.codePoints.every(P.isMark), this.isLigature = this.codePoints.length > 1
                }
                return t.prototype._getPath = function() {
                  return new Pt
                }, t.prototype._getCBox = function() {
                  return this.path.cbox
                }, t.prototype._getBBox = function() {
                  return this.path.bbox
                }, t.prototype._getTableMetrics = function(t) {
                  if (this.id < t.metrics.length) return t.metrics.get(this.id);
                  var e = t.metrics.get(t.metrics.length - 1);
                  return {
                    advance: e ? e.advance : 0,
                    bearing: t.bearings.get(this.id - t.metrics.length) || 0
                  }
                }, t.prototype._getMetrics = function(t) {
                  if (this._metrics) return this._metrics;
                  var e = this._getTableMetrics(this._font.hmtx),
                    n = e.advance,
                    r = e.bearing;
                  if (this._font.vmtx) var i = this._getTableMetrics(this._font.vmtx),
                    o = i.advance,
                    a = i.bearing;
                  else {
                    var s = void 0;
                    if (void 0 !== t && null !== t || (t = this.cbox), (s = this._font["OS/2"]) && s.version > 0) var o = Math.abs(s.typoAscender - s.typoDescender),
                      a = s.typoAscender - t.maxY;
                    else var u = this._font.hhea,
                      o = Math.abs(u.ascent - u.descent),
                      a = u.ascent - t.maxY
                  }
                  return this._font._variationProcessor && this._font.HVAR && (n += this._font._variationProcessor.getAdvanceAdjustment(this.id, this._font.HVAR)), this._metrics = {
                    advanceWidth: n,
                    advanceHeight: o,
                    leftBearing: r,
                    topBearing: a
                  }
                }, t.prototype.getScaledPath = function(t) {
                  var e = 1 / this._font.unitsPerEm * t;
                  return this.path.scale(e)
                }, t.prototype._getName = function() {
                  var t = this._font.post;
                  if (!t) return null;
                  switch (t.version) {
                    case 1:
                      return Tt[this.id];
                    case 2:
                      var e = t.glyphNameIndex[this.id];
                      return e < Tt.length ? Tt[e] : t.names[e - Tt.length];
                    case 2.5:
                      return Tt[this.id + t.offsets[this.id]];
                    case 4:
                      return String.fromCharCode(t.map[this.id])
                  }
                }, t.prototype.render = function(t, e) {
                  t.save();
                  var n = 1 / this._font.head.unitsPerEm * e;
                  t.scale(n, n), this.path.toFunction()(t), t.fill(), t.restore()
                }, S(t, [{
                  key: "cbox",
                  get: function() {
                    return this._getCBox()
                  }
                }, {
                  key: "bbox",
                  get: function() {
                    return this._getBBox()
                  }
                }, {
                  key: "path",
                  get: function() {
                    return this._getPath()
                  }
                }, {
                  key: "advanceWidth",
                  get: function() {
                    return this._getMetrics().advanceWidth
                  }
                }, {
                  key: "advanceHeight",
                  get: function() {
                    return this._getMetrics().advanceHeight
                  }
                }, {
                  key: "ligatureCaretPositions",
                  get: function() {}
                }, {
                  key: "name",
                  get: function() {
                    return this._getName()
                  }
                }]), t
              }(), d(Lt.prototype, "cbox", [o], _(Lt.prototype, "cbox"), Lt.prototype), d(Lt.prototype, "bbox", [o], _(Lt.prototype, "bbox"), Lt.prototype), d(Lt.prototype, "path", [o], _(Lt.prototype, "path"), Lt.prototype), d(Lt.prototype, "advanceWidth", [o], _(Lt.prototype, "advanceWidth"), Lt.prototype), d(Lt.prototype, "advanceHeight", [o], _(Lt.prototype, "advanceHeight"), Lt.prototype), d(Lt.prototype, "name", [o], _(Lt.prototype, "name"), Lt.prototype), Lt),
              Nt = new g.Struct({
                numberOfContours: g.int16,
                xMin: g.int16,
                yMin: g.int16,
                xMax: g.int16,
                yMax: g.int16
              }),
              zt = function() {
                function t(e, n, r, i) {
                  var o = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 0,
                    a = arguments.length > 3 && void 0 !== arguments[3] ? arguments[3] : 0;
                  x(this, t), this.onCurve = e, this.endContour = n, this.x = o, this.y = a
                }
                return t.prototype.copy = function() {
                  return new t(this.onCurve, this.endContour, this.x, this.y)
                }, t
              }(),
              Ft = function t(e, n, r) {
                x(this, t), this.glyphID = e, this.dx = n, this.dy = r, this.pos = 0, this.scaleX = this.scaleY = 1, this.scale01 = this.scale10 = 0
              },
              Dt = function(t) {
                function e() {
                  return x(this, e), j(this, t.apply(this, arguments))
                }
                return I(e, t), e.prototype._getCBox = function(t) {
                  if (this._font._variationProcessor && !t) return this.path.cbox;
                  var e = this._font._getTableStream("glyf");
                  e.pos += this._font.loca.offsets[this.id];
                  var n = Nt.decode(e),
                    r = new pt(n.xMin, n.yMin, n.xMax, n.yMax);
                  return v(r)
                }, e.prototype._parseGlyphCoord = function(t, e, n, r) {
                  if (n) {
                    i = t.readUInt8();
                    r || (i = -i), i += e
                  } else if (r) i = e;
                  else var i = e + t.readInt16BE();
                  return i
                }, e.prototype._decode = function() {
                  var t = this._font.loca.offsets[this.id];
                  if (t === this._font.loca.offsets[this.id + 1]) return null;
                  var e = this._font._getTableStream("glyf");
                  e.pos += t;
                  var n = e.pos,
                    r = Nt.decode(e);
                  return r.numberOfContours > 0 ? this._decodeSimple(r, e) : r.numberOfContours < 0 && this._decodeComposite(r, e, n), r
                }, e.prototype._decodeSimple = function(t, e) {
                  t.points = [];
                  var n = new g.Array(g.uint16, t.numberOfContours).decode(e);
                  t.instructions = new g.Array(g.uint8, g.uint16).decode(e);
                  for (var r = [], i = n[n.length - 1] + 1; r.length < i;) {
                    s = e.readUInt8();
                    if (r.push(s), 8 & s)
                      for (var o = e.readUInt8(), a = 0; a < o; a++) r.push(s)
                  }
                  for (l = 0; l < r.length; l++) {
                    var s = r[l],
                      u = new zt(!!(1 & s), n.indexOf(l) >= 0, 0, 0);
                    t.points.push(u)
                  }
                  for (var c = 0, l = 0; l < r.length; l++) {
                    s = r[l];
                    t.points[l].x = c = this._parseGlyphCoord(e, c, 2 & s, 16 & s)
                  }
                  for (var h = 0, l = 0; l < r.length; l++) {
                    s = r[l];
                    t.points[l].y = h = this._parseGlyphCoord(e, h, 4 & s, 32 & s)
                  }
                  if (this._font._variationProcessor) {
                    var f = t.points.slice();
                    f.push.apply(f, this._getPhantomPoints(t)), this._font._variationProcessor.transformPoints(this.id, f), t.phantomPoints = f.slice(-4)
                  }
                }, e.prototype._decodeComposite = function(t, e) {
                  var n = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : 0;
                  t.components = [];
                  for (var r = !1, i = 32; 32 & i;) {
                    i = e.readUInt16BE();
                    var o = e.pos - n,
                      a = e.readUInt16BE();
                    if (r || (r = 0 != (256 & i)), 1 & i) var s = e.readInt16BE(),
                      u = e.readInt16BE();
                    else var s = e.readInt8(),
                      u = e.readInt8();
                    (h = new Ft(a, s, u)).pos = o, 8 & i ? h.scaleX = h.scaleY = (e.readUInt8() << 24 | e.readUInt8() << 16) / 1073741824 : 64 & i ? (h.scaleX = (e.readUInt8() << 24 | e.readUInt8() << 16) / 1073741824, h.scaleY = (e.readUInt8() << 24 | e.readUInt8() << 16) / 1073741824) : 128 & i && (h.scaleX = (e.readUInt8() << 24 | e.readUInt8() << 16) / 1073741824, h.scale01 = (e.readUInt8() << 24 | e.readUInt8() << 16) / 1073741824, h.scale10 = (e.readUInt8() << 24 | e.readUInt8() << 16) / 1073741824, h.scaleY = (e.readUInt8() << 24 | e.readUInt8() << 16) / 1073741824), t.components.push(h)
                  }
                  if (this._font._variationProcessor) {
                    for (var c = [], l = 0; l < t.components.length; l++) {
                      var h = t.components[l];
                      c.push(new zt(!0, !0, h.dx, h.dy))
                    }
                    c.push.apply(c, this._getPhantomPoints(t)), this._font._variationProcessor.transformPoints(this.id, c), t.phantomPoints = c.splice(-4, 4);
                    for (var f = 0; f < c.length; f++) {
                      var d = c[f];
                      t.components[f].dx = d.x, t.components[f].dy = d.y
                    }
                  }
                  return r
                }, e.prototype._getPhantomPoints = function(t) {
                  var e = this._getCBox(!0);
                  null == this._metrics && (this._metrics = Bt.prototype._getMetrics.call(this, e));
                  var n = this._metrics,
                    r = n.advanceWidth,
                    i = n.advanceHeight,
                    o = n.leftBearing,
                    a = n.topBearing;
                  return [new zt(!1, !0, t.xMin - o, 0), new zt(!1, !0, t.xMin - o + r, 0), new zt(!1, !0, 0, t.yMax + a), new zt(!1, !0, 0, t.yMax + a + i)]
                }, e.prototype._getContours = function() {
                  var t = this._decode();
                  if (!t) return [];
                  var e = [];
                  if (t.numberOfContours < 0)
                    for (var n = t.components, r = Array.isArray(n), i = 0, n = r ? n : m(n);;) {
                      var o;
                      if (r) {
                        if (i >= n.length) break;
                        o = n[i++]
                      } else {
                        if ((i = n.next()).done) break;
                        o = i.value
                      }
                      for (var a = o, s = this._font.getGlyph(a.glyphID)._getContours(), u = 0; u < s.length; u++)
                        for (var c = s[u], l = 0; l < c.length; l++) {
                          var h = c[l],
                            f = h.x * a.scaleX + h.y * a.scale01 + a.dx,
                            d = h.y * a.scaleY + h.x * a.scale10 + a.dy;
                          e.push(new zt(h.onCurve, h.endContour, f, d))
                        }
                    } else e = t.points || [];
                  t.phantomPoints && !this._font.directory.tables.HVAR && (this._metrics.advanceWidth = t.phantomPoints[1].x - t.phantomPoints[0].x, this._metrics.advanceHeight = t.phantomPoints[3].y - t.phantomPoints[2].y, this._metrics.leftBearing = t.xMin - t.phantomPoints[0].x, this._metrics.topBearing = t.phantomPoints[2].y - t.yMax);
                  for (var p = [], g = [], _ = 0; _ < e.length; _++) {
                    var v = e[_];
                    g.push(v), v.endContour && (p.push(g), g = [])
                  }
                  return p
                }, e.prototype._getMetrics = function() {
                  if (this._metrics) return this._metrics;
                  var e = this._getCBox(!0);
                  return t.prototype._getMetrics.call(this, e), this._font._variationProcessor && !this._font.HVAR && this.path, this._metrics
                }, e.prototype._getPath = function() {
                  for (var t = this._getContours(), e = new Pt, n = 0; n < t.length; n++) {
                    var r = t[n],
                      i = r[0],
                      o = r[r.length - 1],
                      a = 0;
                    if (i.onCurve) {
                      f = null;
                      a = 1
                    } else f = i = o.onCurve ? o : new zt(!1, !1, (i.x + o.x) / 2, (i.y + o.y) / 2);
                    e.moveTo(i.x, i.y);
                    for (var s = a; s < r.length; s++) {
                      var u = r[s],
                        c = 0 === s ? i : r[s - 1];
                      if (c.onCurve && u.onCurve) e.lineTo(u.x, u.y);
                      else if (c.onCurve && !u.onCurve) f = u;
                      else if (c.onCurve || u.onCurve) {
                        if (c.onCurve || !u.onCurve) throw new Error("Unknown TTF path state");
                        e.quadraticCurveTo(f.x, f.y, u.x, u.y);
                        f = null
                      } else {
                        var l = (c.x + u.x) / 2,
                          h = (c.y + u.y) / 2;
                        e.quadraticCurveTo(c.x, c.y, l, h);
                        var f = u
                      }
                    }
                    f && e.quadraticCurveTo(f.x, f.y, i.x, i.y), e.closePath()
                  }
                  return e
                }, e
              }(Bt),
              Mt = function() {
                function t(e) {
                  x(this, t), this.font = e, this.glyphs = [], this.mapping = {}, this.includeGlyph(0)
                }
                return t.prototype.includeGlyph = function(t) {
                  return "object" === (void 0 === t ? "undefined" : b(t)) && (t = t.id), null == this.mapping[t] && (this.glyphs.push(t), this.mapping[t] = this.glyphs.length - 1), this.mapping[t]
                }, t.prototype.encodeStream = function() {
                  var t = this,
                    e = new g.EncodeStream;
                  return n.nextTick(function() {
                    return t.encode(e), e.end()
                  }), e
                }, t
              }(),
              Rt = function() {
                function t() {
                  x(this, t)
                }
                return t.size = function(t) {
                  return t >= 0 && t <= 255 ? 1 : 2
                }, t.encode = function(t, e) {
                  e >= 0 && e <= 255 ? t.writeUInt8(e) : t.writeInt16BE(e)
                }, t
              }(),
              Ut = new g.Struct({
                numberOfContours: g.int16,
                xMin: g.int16,
                yMin: g.int16,
                xMax: g.int16,
                yMax: g.int16,
                endPtsOfContours: new g.Array(g.uint16, "numberOfContours"),
                instructions: new g.Array(g.uint8, g.uint16),
                flags: new g.Array(g.uint8, 0),
                xPoints: new g.Array(Rt, 0),
                yPoints: new g.Array(Rt, 0)
              }),
              Ht = function() {
                function t() {
                  x(this, t)
                }
                return t.prototype.encodeSimple = function(t) {
                  for (var e = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : [], n = [], r = [], i = [], o = [], a = 0, s = 0, u = 0, c = 0, l = 0, h = 0; h < t.commands.length; h++) {
                    for (var f = t.commands[h], d = 0; d < f.args.length; d += 2) {
                      var p = f.args[d],
                        _ = f.args[d + 1],
                        m = 0;
                      if ("quadraticCurveTo" === f.command && 2 === d) {
                        var v = t.commands[h + 1];
                        if (v && "quadraticCurveTo" === v.command) {
                          var y = (s + v.args[0]) / 2,
                            b = (u + v.args[1]) / 2;
                          if (p === y && _ === b) continue
                        }
                      }
                      "quadraticCurveTo" === f.command && 0 === d || (m |= 1), m = this._encodePoint(p, s, r, m, 2, 16), (m = this._encodePoint(_, u, i, m, 4, 32)) === c && a < 255 ? (o[o.length - 1] |= 8, a++) : (a > 0 && (o.push(a), a = 0), o.push(m), c = m), s = p, u = _, l++
                    }
                    "closePath" === f.command && n.push(l - 1)
                  }
                  t.commands.length > 1 && "closePath" !== t.commands[t.commands.length - 1].command && n.push(l - 1);
                  var w = t.bbox,
                    x = {
                      numberOfContours: n.length,
                      xMin: w.minX,
                      yMin: w.minY,
                      xMax: w.maxX,
                      yMax: w.maxY,
                      endPtsOfContours: n,
                      instructions: e,
                      flags: o,
                      xPoints: r,
                      yPoints: i
                    },
                    S = Ut.size(x),
                    C = 4 - S % 4,
                    k = new g.EncodeStream(S + C);
                  return Ut.encode(k, x), 0 !== C && k.fill(0, C), k.buffer
                }, t.prototype._encodePoint = function(t, e, n, r, i, o) {
                  var a = t - e;
                  return t === e ? r |= o : (-255 <= a && a <= 255 && (r |= i, a < 0 ? a = -a : r |= o), n.push(a)), r
                }, t
              }(),
              Gt = function(t) {
                function e(n) {
                  x(this, e);
                  var r = j(this, t.call(this, n));
                  return r.glyphEncoder = new Ht, r
                }
                return I(e, t), e.prototype._addGlyph = function(t) {
                  var e = this.font.getGlyph(t),
                    n = e._decode(),
                    i = this.font.loca.offsets[t],
                    o = this.font.loca.offsets[t + 1],
                    a = this.font._getTableStream("glyf");
                  a.pos += i;
                  var s = a.readBuffer(o - i);
                  if (n && n.numberOfContours < 0) {
                    s = new r(s);
                    for (var u = n.components, c = Array.isArray(u), l = 0, u = c ? u : m(u);;) {
                      var h;
                      if (c) {
                        if (l >= u.length) break;
                        h = u[l++]
                      } else {
                        if ((l = u.next()).done) break;
                        h = l.value
                      }
                      var f = h;
                      t = this.includeGlyph(f.glyphID), s.writeUInt16BE(t, f.pos)
                    }
                  } else n && this.font._variationProcessor && (s = this.glyphEncoder.encodeSimple(e.path, n.instructions));
                  return this.glyf.push(s), this.loca.offsets.push(this.offset), this.hmtx.metrics.push({
                    advance: e.advanceWidth,
                    bearing: e._getMetrics().leftBearing
                  }), this.offset += s.length, this.glyf.length - 1
                }, e.prototype.encode = function(t) {
                  this.glyf = [], this.offset = 0, this.loca = {
                    offsets: []
                  }, this.hmtx = {
                    metrics: [],
                    bearings: []
                  };
                  for (var e = 0; e < this.glyphs.length;) this._addGlyph(this.glyphs[e++]);
                  var n = L(this.font.maxp);
                  n.numGlyphs = this.glyf.length, this.loca.offsets.push(this.offset), st.loca.preEncode.call(this.loca);
                  var r = L(this.font.head);
                  r.indexToLocFormat = this.loca.version;
                  var i = L(this.font.hhea);
                  i.numberOfMetrics = this.hmtx.metrics.length, ct.encode(t, {
                    tables: {
                      head: r,
                      hhea: i,
                      loca: this.loca,
                      maxp: n,
                      "cvt ": this.font["cvt "],
                      prep: this.font.prep,
                      glyf: this.glyf,
                      hmtx: this.hmtx,
                      fpgm: this.font.fpgm
                    }
                  })
                }, e
              }(Mt),
              qt = (Ot = function() {
                function t(e, n) {
                  var r = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : null;
                  x(this, t), this.stream = e, this.variationCoords = r, this._directoryPos = this.stream.pos, this._tables = {}, this._glyphs = {}, this._decodeDirectory();
                  for (var i in this.directory.tables) {
                    var o = this.directory.tables[i];
                    st[i] && o.length > 0 && w(this, i, {
                      get: this._getTable.bind(this, o)
                    })
                  }
                }
                return t.probe = function(t) {
                  var e = t.toString("ascii", 0, 4);
                  return "true" === e || "OTTO" === e || e === String.fromCharCode(0, 1, 0, 0)
                }, t.prototype._getTable = function(t) {
                  if (!(t.tag in this._tables)) try {
                    this._tables[t.tag] = this._decodeTable(t)
                  } catch (e) {
                    O.logErrors && (console.error("Error decoding table " + t.tag), console.error(e.stack))
                  }
                  return this._tables[t.tag]
                }, t.prototype._getTableStream = function(t) {
                  var e = this.directory.tables[t];
                  return e ? (this.stream.pos = e.offset, this.stream) : null
                }, t.prototype._decodeDirectory = function() {
                  return this.directory = ct.decode(this.stream, {
                    _startOffset: 0
                  })
                }, t.prototype._decodeTable = function(t) {
                  var e = this.stream.pos,
                    n = this._getTableStream(t.tag),
                    r = st[t.tag].decode(n, this, t.length);
                  return this.stream.pos = e, r
                }, t.prototype.getName = function(t) {
                  var e = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "en",
                    n = this.name.records[t];
                  return n ? n[e] : null
                }, t.prototype.hasGlyphForCodePoint = function(t) {
                  return !!this._cmapProcessor.lookup(t)
                }, t.prototype.glyphForCodePoint = function(t) {
                  return this.getGlyph(this._cmapProcessor.lookup(t), [t])
                }, t.prototype.glyphsForString = function(t) {
                  for (var e = [], n = t.length, r = 0, i = -1, o = -1; r <= n;) {
                    var a = 0,
                      s = 0;
                    if (r < n) {
                      if (55296 <= (a = t.charCodeAt(r++)) && a <= 56319 && r < n) {
                        var u = t.charCodeAt(r);
                        56320 <= u && u <= 57343 && (r++, a = ((1023 & a) << 10) + (1023 & u) + 65536)
                      }
                      s = 65024 <= a && a <= 65039 || 917760 <= a && a <= 917999 ? 1 : 0
                    } else r++;
                    0 === o && 1 === s ? e.push(this.getGlyph(this._cmapProcessor.lookup(i, a), [i, a])) : 0 === o && 0 === s && e.push(this.glyphForCodePoint(i)), i = a, o = s
                  }
                  return e
                }, t.prototype.layout = function(t, e, n, r, i) {
                  return this._layoutEngine.layout(t, e, n, r, i)
                }, t.prototype.stringsForGlyph = function(t) {
                  return this._layoutEngine.stringsForGlyph(t)
                }, t.prototype.getAvailableFeatures = function(t, e) {
                  return this._layoutEngine.getAvailableFeatures(t, e)
                }, t.prototype._getBaseGlyph = function(t) {
                  var e = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : [];
                  return this._glyphs[t] || (this.directory.tables.glyf ? this._glyphs[t] = new Dt(t, e, this) : this.directory.tables["CFF "] || this.directory.tables.CFF2), this._glyphs[t] || null
                }, t.prototype.getGlyph = function(t) {
                  var e = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : [];
                  return this._glyphs[t] || this._getBaseGlyph(t, e), this._glyphs[t] || null
                }, t.prototype.createSubset = function() {
                  return new Gt(this)
                }, t.prototype.getVariation = function(e) {
                  if (!this.directory.tables.fvar || !(this.directory.tables.gvar && this.directory.tables.glyf || this.directory.tables.CFF2)) throw new Error("Variations require a font with the fvar, gvar and glyf, or CFF2 tables.");
                  if ("string" == typeof e && (e = this.namedVariations[e]), "object" !== (void 0 === e ? "undefined" : b(e))) throw new Error("Variation settings must be either a variation name or settings object.");
                  var n = this.fvar.axis.map(function(t, n) {
                      var r = t.axisTag.trim();
                      return r in e ? Math.max(t.minValue, Math.min(t.maxValue, e[r])) : t.defaultValue
                    }),
                    r = new g.DecodeStream(this.stream.buffer);
                  r.pos = this._directoryPos;
                  var i = new t(r, n);
                  return i._tables = this._tables, i
                }, t.prototype.getFont = function(t) {
                  return this.getVariation(t)
                }, S(t, [{
                  key: "postscriptName",
                  get: function() {
                    var t = this.name.records.postscriptName;
                    return t ? t[y(t)[0]] : null
                  }
                }, {
                  key: "fullName",
                  get: function() {
                    return this.getName("fullName")
                  }
                }, {
                  key: "familyName",
                  get: function() {
                    return this.getName("fontFamily")
                  }
                }, {
                  key: "subfamilyName",
                  get: function() {
                    return this.getName("fontSubfamily")
                  }
                }, {
                  key: "copyright",
                  get: function() {
                    return this.getName("copyright")
                  }
                }, {
                  key: "version",
                  get: function() {
                    return this.getName("version")
                  }
                }, {
                  key: "ascent",
                  get: function() {
                    return this.hhea.ascent
                  }
                }, {
                  key: "descent",
                  get: function() {
                    return this.hhea.descent
                  }
                }, {
                  key: "lineGap",
                  get: function() {
                    return this.hhea.lineGap
                  }
                }, {
                  key: "underlinePosition",
                  get: function() {
                    return this.post.underlinePosition
                  }
                }, {
                  key: "underlineThickness",
                  get: function() {
                    return this.post.underlineThickness
                  }
                }, {
                  key: "italicAngle",
                  get: function() {
                    return this.post.italicAngle
                  }
                }, {
                  key: "capHeight",
                  get: function() {
                    var t = this["OS/2"];
                    return t ? t.capHeight : this.ascent
                  }
                }, {
                  key: "xHeight",
                  get: function() {
                    var t = this["OS/2"];
                    return t ? t.xHeight : 0
                  }
                }, {
                  key: "numGlyphs",
                  get: function() {
                    return this.maxp.numGlyphs
                  }
                }, {
                  key: "unitsPerEm",
                  get: function() {
                    return this.head.unitsPerEm
                  }
                }, {
                  key: "bbox",
                  get: function() {
                    return v(new pt(this.head.xMin, this.head.yMin, this.head.xMax, this.head.yMax))
                  }
                }, {
                  key: "_cmapProcessor",
                  get: function() {
                    return new ft(this.cmap)
                  }
                }, {
                  key: "characterSet",
                  get: function() {
                    return this._cmapProcessor.getCharacterSet()
                  }
                }, {
                  key: "_layoutEngine",
                  get: function() {
                    return new Et(this)
                  }
                }, {
                  key: "availableFeatures",
                  get: function() {
                    return this._layoutEngine.getAvailableFeatures()
                  }
                }, {
                  key: "variationAxes",
                  get: function() {
                    var t = {};
                    if (!this.fvar) return t;
                    for (var e = this.fvar.axis, n = Array.isArray(e), r = 0, e = n ? e : m(e);;) {
                      var i;
                      if (n) {
                        if (r >= e.length) break;
                        i = e[r++]
                      } else {
                        if ((r = e.next()).done) break;
                        i = r.value
                      }
                      var o = i;
                      t[o.axisTag.trim()] = {
                        name: o.name.en,
                        min: o.minValue,
                        default: o.defaultValue,
                        max: o.maxValue
                      }
                    }
                    return t
                  }
                }, {
                  key: "namedVariations",
                  get: function() {
                    var t = {};
                    if (!this.fvar) return t;
                    for (var e = this.fvar.instance, n = Array.isArray(e), r = 0, e = n ? e : m(e);;) {
                      var i;
                      if (n) {
                        if (r >= e.length) break;
                        i = e[r++]
                      } else {
                        if ((r = e.next()).done) break;
                        i = r.value
                      }
                      for (var o = i, a = {}, s = 0; s < this.fvar.axis.length; s++) a[this.fvar.axis[s].axisTag.trim()] = o.coord[s];
                      t[o.name.en] = a
                    }
                    return t
                  }
                }, {
                  key: "_variationProcessor",
                  get: function() {
                    if (!this.fvar) return null;
                    var t = this.variationCoords;
                    if (!t && !this.CFF2) return null;
                    t || (t = this.fvar.axis.map(function(t) {
                      return t.defaultValue
                    }))
                  }
                }]), t
              }(), p(Ot.prototype, "bbox", [o], _(Ot.prototype, "bbox"), Ot.prototype), p(Ot.prototype, "_cmapProcessor", [o], _(Ot.prototype, "_cmapProcessor"), Ot.prototype), p(Ot.prototype, "characterSet", [o], _(Ot.prototype, "characterSet"), Ot.prototype), p(Ot.prototype, "_layoutEngine", [o], _(Ot.prototype, "_layoutEngine"), Ot.prototype), p(Ot.prototype, "variationAxes", [o], _(Ot.prototype, "variationAxes"), Ot.prototype), p(Ot.prototype, "namedVariations", [o], _(Ot.prototype, "namedVariations"), Ot.prototype), p(Ot.prototype, "_variationProcessor", [o], _(Ot.prototype, "_variationProcessor"), Ot.prototype), Ot),
              Zt = new g.VersionedStruct(g.uint32, {
                65536: {
                  numFonts: g.uint32,
                  offsets: new g.Array(g.uint32, "numFonts")
                },
                131072: {
                  numFonts: g.uint32,
                  offsets: new g.Array(g.uint32, "numFonts"),
                  dsigTag: g.uint32,
                  dsigLength: g.uint32,
                  dsigOffset: g.uint32
                }
              }),
              Wt = function() {
                function t(e) {
                  if (x(this, t), this.stream = e, "ttcf" !== e.readString(4)) throw new Error("Not a TrueType collection");
                  this.header = Zt.decode(e)
                }
                return t.probe = function(t) {
                  return "ttcf" === t.toString("ascii", 0, 4)
                }, t.prototype.getFont = function(t) {
                  for (var e = this.header.offsets, n = Array.isArray(e), r = 0, e = n ? e : m(e);;) {
                    var i;
                    if (n) {
                      if (r >= e.length) break;
                      i = e[r++]
                    } else {
                      if ((r = e.next()).done) break;
                      i = r.value
                    }
                    var o = i,
                      a = new g.DecodeStream(this.stream.buffer);
                    a.pos = o;
                    var s = new qt(a);
                    if (s.postscriptName === t) return s
                  }
                  return null
                }, S(t, [{
                  key: "fonts",
                  get: function() {
                    for (var t = [], e = this.header.offsets, n = Array.isArray(e), r = 0, e = n ? e : m(e);;) {
                      var i;
                      if (n) {
                        if (r >= e.length) break;
                        i = e[r++]
                      } else {
                        if ((r = e.next()).done) break;
                        i = r.value
                      }
                      var o = i,
                        a = new g.DecodeStream(this.stream.buffer);
                      a.pos = o, t.push(new qt(a))
                    }
                    return t
                  }
                }]), t
              }(),
              Vt = new g.String(g.uint8),
              Yt = (new g.Struct({
                len: g.uint32,
                buf: new g.Buffer("len")
              }), new g.Struct({
                id: g.uint16,
                nameOffset: g.int16,
                attr: g.uint8,
                dataOffset: g.uint24,
                handle: g.uint32
              })),
              Kt = new g.Struct({
                name: new g.String(4),
                maxTypeIndex: g.uint16,
                refList: new g.Pointer(g.uint16, new g.Array(Yt, function(t) {
                  return t.maxTypeIndex + 1
                }), {
                  type: "parent"
                })
              }),
              Xt = new g.Struct({
                length: g.uint16,
                types: new g.Array(Kt, function(t) {
                  return t.length + 1
                })
              }),
              Jt = new g.Struct({
                reserved: new g.Reserved(g.uint8, 24),
                typeList: new g.Pointer(g.uint16, Xt),
                nameListOffset: new g.Pointer(g.uint16, "void")
              }),
              Qt = new g.Struct({
                dataOffset: g.uint32,
                map: new g.Pointer(g.uint32, Jt),
                dataLength: g.uint32,
                mapLength: g.uint32
              }),
              $t = function() {
                function t(e) {
                  x(this, t), this.stream = e, this.header = Qt.decode(this.stream);
                  for (var n = this.header.map.typeList.types, r = Array.isArray(n), i = 0, n = r ? n : m(n);;) {
                    var o;
                    if (r) {
                      if (i >= n.length) break;
                      o = n[i++]
                    } else {
                      if ((i = n.next()).done) break;
                      o = i.value
                    }
                    for (var a = o, s = a.refList, u = Array.isArray(s), c = 0, s = u ? s : m(s);;) {
                      var l;
                      if (u) {
                        if (c >= s.length) break;
                        l = s[c++]
                      } else {
                        if ((c = s.next()).done) break;
                        l = c.value
                      }
                      var h = l;
                      h.nameOffset >= 0 ? (this.stream.pos = h.nameOffset + this.header.map.nameListOffset, h.name = Vt.decode(this.stream)) : h.name = null
                    }
                    "sfnt" === a.name && (this.sfnt = a)
                  }
                }
                return t.probe = function(t) {
                  var e = new g.DecodeStream(t);
                  try {
                    var n = Qt.decode(e)
                  } catch (t) {
                    return !1
                  }
                  for (var r = n.map.typeList.types, i = Array.isArray(r), o = 0, r = i ? r : m(r);;) {
                    var a;
                    if (i) {
                      if (o >= r.length) break;
                      a = r[o++]
                    } else {
                      if ((o = r.next()).done) break;
                      a = o.value
                    }
                    if ("sfnt" === a.name) return !0
                  }
                  return !1
                }, t.prototype.getFont = function(t) {
                  if (!this.sfnt) return null;
                  for (var e = this.sfnt.refList, n = Array.isArray(e), r = 0, e = n ? e : m(e);;) {
                    var i;
                    if (n) {
                      if (r >= e.length) break;
                      i = e[r++]
                    } else {
                      if ((r = e.next()).done) break;
                      i = r.value
                    }
                    var o = i,
                      a = this.header.dataOffset + o.dataOffset + 4,
                      s = new g.DecodeStream(this.stream.buffer.slice(a)),
                      u = new qt(s);
                    if (u.postscriptName === t) return u
                  }
                  return null
                }, S(t, [{
                  key: "fonts",
                  get: function() {
                    for (var t = [], e = this.sfnt.refList, n = Array.isArray(e), r = 0, e = n ? e : m(e);;) {
                      var i;
                      if (n) {
                        if (r >= e.length) break;
                        i = e[r++]
                      } else {
                        if ((r = e.next()).done) break;
                        i = r.value
                      }
                      var o = i,
                        a = this.header.dataOffset + o.dataOffset + 4,
                        s = new g.DecodeStream(this.stream.buffer.slice(a));
                      t.push(new qt(s))
                    }
                    return t
                  }
                }]), t
              }();
            O.registerFormat(qt), O.registerFormat(Wt), O.registerFormat($t), e.localExports = O
          }).call(this, t("v3go1D"), t("buffer").Buffer)
        }, {
          "babel-runtime/core-js/array/from": 34,
          "babel-runtime/core-js/get-iterator": 35,
          "babel-runtime/core-js/map": 36,
          "babel-runtime/core-js/object/define-property": 38,
          "babel-runtime/core-js/object/freeze": 39,
          "babel-runtime/core-js/object/get-own-property-descriptor": 40,
          "babel-runtime/core-js/object/keys": 41,
          "babel-runtime/core-js/set": 43,
          "babel-runtime/core-js/string/from-code-point": 44,
          "babel-runtime/helpers/classCallCheck": 47,
          "babel-runtime/helpers/createClass": 48,
          "babel-runtime/helpers/inherits": 49,
          "babel-runtime/helpers/possibleConstructorReturn": 50,
          "babel-runtime/helpers/typeof": 51,
          buffer: 31,
          clone: 52,
          restructure: 167,
          "unicode-properties": 186,
          v3go1D: 205
        }],
        34: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/array/from"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/array/from": 53
        }],
        35: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/get-iterator"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/get-iterator": 54
        }],
        36: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/map"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/map": 55
        }],
        37: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/object/create"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/object/create": 56
        }],
        38: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/object/define-property"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/object/define-property": 57
        }],
        39: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/object/freeze"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/object/freeze": 58
        }],
        40: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/object/get-own-property-descriptor"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/object/get-own-property-descriptor": 59
        }],
        41: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/object/keys"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/object/keys": 60
        }],
        42: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/object/set-prototype-of"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/object/set-prototype-of": 61
        }],
        43: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/set"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/set": 62
        }],
        44: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/string/from-code-point"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/string/from-code-point": 63
        }],
        45: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/symbol"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/symbol": 64
        }],
        46: [function(t, e, n) {
          e.localExports = {
            default: t("core-js/library/fn/symbol/iterator"),
            __esModule: !0
          }
        }, {
          "core-js/library/fn/symbol/iterator": 65
        }],
        47: [function(t, e, n) {
          "use strict";
          n.__esModule = !0, n.default = function(t, e) {
            if (!(t instanceof e)) throw new TypeError("Cannot call a class as a function")
          }
        }, {}],
        48: [function(t, e, n) {
          "use strict";
          n.__esModule = !0;
          var r = function(t) {
            return t && t.__esModule ? t : {
              default: t
            }
          }(t("../core-js/object/define-property"));
          n.default = function() {
            function t(t, e) {
              for (var n = 0; n < e.length; n++) {
                var i = e[n];
                i.enumerable = i.enumerable || !1, i.configurable = !0, "value" in i && (i.writable = !0), r.default(t, i.key, i)
              }
            }
            return function(e, n, r) {
              return n && t(e.prototype, n), r && t(e, r), e
            }
          }()
        }, {
          "../core-js/object/define-property": 38
        }],
        49: [function(t, e, n) {
          "use strict";

          function r(t) {
            return t && t.__esModule ? t : {
              default: t
            }
          }
          n.__esModule = !0;
          var i = r(t("../core-js/object/set-prototype-of")),
            o = r(t("../core-js/object/create")),
            a = r(t("../helpers/typeof"));
          n.default = function(t, e) {
            if ("function" != typeof e && null !== e) throw new TypeError("Super expression must either be null or a function, not " + (void 0 === e ? "undefined" : a.default(e)));
            t.prototype = o.default(e && e.prototype, {
              constructor: {
                value: t,
                enumerable: !1,
                writable: !0,
                configurable: !0
              }
            }), e && (i.default ? i.default(t, e) : t.__proto__ = e)
          }
        }, {
          "../core-js/object/create": 37,
          "../core-js/object/set-prototype-of": 42,
          "../helpers/typeof": 51
        }],
        50: [function(t, e, n) {
          "use strict";
          n.__esModule = !0;
          var r = function(t) {
            return t && t.__esModule ? t : {
              default: t
            }
          }(t("../helpers/typeof"));
          n.default = function(t, e) {
            if (!t) throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
            return !e || "object" !== (void 0 === e ? "undefined" : r.default(e)) && "function" != typeof e ? t : e
          }
        }, {
          "../helpers/typeof": 51
        }],
        51: [function(t, e, n) {
          "use strict";

          function r(t) {
            return t && t.__esModule ? t : {
              default: t
            }
          }
          n.__esModule = !0;
          var i = r(t("../core-js/symbol/iterator")),
            o = r(t("../core-js/symbol")),
            a = "function" == typeof o.default && "symbol" == typeof i.default ? function(t) {
              return typeof t
            } : function(t) {
              return t && "function" == typeof o.default && t.constructor === o.default && t !== o.default.prototype ? "symbol" : typeof t
            };
          n.default = "function" == typeof o.default && "symbol" === a(i.default) ? function(t) {
            return void 0 === t ? "undefined" : a(t)
          } : function(t) {
            return t && "function" == typeof o.default && t.constructor === o.default && t !== o.default.prototype ? "symbol" : void 0 === t ? "undefined" : a(t)
          }
        }, {
          "../core-js/symbol": 45,
          "../core-js/symbol/iterator": 46
        }],
        52: [function(t, e, n) {
          (function(t) {
            var n = function() {
              "use strict";

              function e(n, i, o, a) {
                function s(n, o) {
                  if (null === n) return null;
                  if (0 == o) return n;
                  var h, f;
                  if ("object" != typeof n) return n;
                  if (e.__isArray(n)) h = [];
                  else if (e.__isRegExp(n)) h = new RegExp(n.source, r(n)), n.lastIndex && (h.lastIndex = n.lastIndex);
                  else if (e.__isDate(n)) h = new Date(n.getTime());
                  else {
                    if (l && t.isBuffer(n)) return h = t.allocUnsafe ? t.allocUnsafe(n.length) : new t(n.length), n.copy(h), h;
                    void 0 === a ? (f = Object.getPrototypeOf(n), h = Object.create(f)) : (h = Object.create(a), f = a)
                  }
                  if (i) {
                    var d = u.indexOf(n);
                    if (-1 != d) return c[d];
                    u.push(n), c.push(h)
                  }
                  for (var p in n) {
                    var g;
                    f && (g = Object.getOwnPropertyDescriptor(f, p)), g && null == g.set || (h[p] = s(n[p], o - 1))
                  }
                  return h
                }
                "object" == typeof i && (o = i.depth, a = i.prototype, i.filter, i = i.circular);
                var u = [],
                  c = [],
                  l = void 0 !== t;
                return void 0 === i && (i = !0), void 0 === o && (o = 1 / 0), s(n, o)
              }

              function n(t) {
                return Object.prototype.toString.call(t)
              }

              function r(t) {
                var e = "";
                return t.global && (e += "g"), t.ignoreCase && (e += "i"), t.multiline && (e += "m"), e
              }
              return e.clonePrototype = function(t) {
                if (null === t) return null;
                var e = function() {};
                return e.prototype = t, new e
              }, e.__objToStr = n, e.__isDate = function(t) {
                return "object" == typeof t && "[object Date]" === n(t)
              }, e.__isArray = function(t) {
                return "object" == typeof t && "[object Array]" === n(t)
              }, e.__isRegExp = function(t) {
                return "object" == typeof t && "[object RegExp]" === n(t)
              }, e.__getRegExpFlags = r, e
            }();
            "object" == typeof e && e.localExports && (e.localExports = n)
          }).call(this, t("buffer").Buffer)
        }, {
          buffer: 31
        }],
        53: [function(t, e, n) {
          t("../../modules/es6.string.iterator"), t("../../modules/es6.array.from"), e.localExports = t("../../modules/_core").Array.from
        }, {
          "../../modules/_core": 80,
          "../../modules/es6.array.from": 144,
          "../../modules/es6.string.iterator": 156
        }],
        54: [function(t, e, n) {
          t("../modules/web.dom.iterable"), t("../modules/es6.string.iterator"), e.localExports = t("../modules/core.get-iterator")
        }, {
          "../modules/core.get-iterator": 143,
          "../modules/es6.string.iterator": 156,
          "../modules/web.dom.iterable": 166
        }],
        55: [function(t, e, n) {
          t("../modules/es6.object.to-string"), t("../modules/es6.string.iterator"), t("../modules/web.dom.iterable"), t("../modules/es6.map"), t("../modules/es7.map.to-json"), t("../modules/es7.map.of"), t("../modules/es7.map.from"), e.localExports = t("../modules/_core").Map
        }, {
          "../modules/_core": 80,
          "../modules/es6.map": 146,
          "../modules/es6.object.to-string": 153,
          "../modules/es6.string.iterator": 156,
          "../modules/es7.map.from": 158,
          "../modules/es7.map.of": 159,
          "../modules/es7.map.to-json": 160,
          "../modules/web.dom.iterable": 166
        }],
        56: [function(t, e, n) {
          t("../../modules/es6.object.create");
          var r = t("../../modules/_core").Object;
          e.localExports = function(t, e) {
            return r.create(t, e)
          }
        }, {
          "../../modules/_core": 80,
          "../../modules/es6.object.create": 147
        }],
        57: [function(t, e, n) {
          t("../../modules/es6.object.define-property");
          var r = t("../../modules/_core").Object;
          e.localExports = function(t, e, n) {
            return r.defineProperty(t, e, n)
          }
        }, {
          "../../modules/_core": 80,
          "../../modules/es6.object.define-property": 148
        }],
        58: [function(t, e, n) {
          t("../../modules/es6.object.freeze"), e.localExports = t("../../modules/_core").Object.freeze
        }, {
          "../../modules/_core": 80,
          "../../modules/es6.object.freeze": 149
        }],
        59: [function(t, e, n) {
          t("../../modules/es6.object.get-own-property-descriptor");
          var r = t("../../modules/_core").Object;
          e.localExports = function(t, e) {
            return r.getOwnPropertyDescriptor(t, e)
          }
        }, {
          "../../modules/_core": 80,
          "../../modules/es6.object.get-own-property-descriptor": 150
        }],
        60: [function(t, e, n) {
          t("../../modules/es6.object.keys"), e.localExports = t("../../modules/_core").Object.keys
        }, {
          "../../modules/_core": 80,
          "../../modules/es6.object.keys": 151
        }],
        61: [function(t, e, n) {
          t("../../modules/es6.object.set-prototype-of"), e.localExports = t("../../modules/_core").Object.setPrototypeOf
        }, {
          "../../modules/_core": 80,
          "../../modules/es6.object.set-prototype-of": 152
        }],
        62: [function(t, e, n) {
          t("../modules/es6.object.to-string"), t("../modules/es6.string.iterator"), t("../modules/web.dom.iterable"), t("../modules/es6.set"), t("../modules/es7.set.to-json"), t("../modules/es7.set.of"), t("../modules/es7.set.from"), e.localExports = t("../modules/_core").Set
        }, {
          "../modules/_core": 80,
          "../modules/es6.object.to-string": 153,
          "../modules/es6.set": 154,
          "../modules/es6.string.iterator": 156,
          "../modules/es7.set.from": 161,
          "../modules/es7.set.of": 162,
          "../modules/es7.set.to-json": 163,
          "../modules/web.dom.iterable": 166
        }],
        63: [function(t, e, n) {
          t("../../modules/es6.string.from-code-point"), e.localExports = t("../../modules/_core").String.fromCodePoint
        }, {
          "../../modules/_core": 80,
          "../../modules/es6.string.from-code-point": 155
        }],
        64: [function(t, e, n) {
          t("../../modules/es6.symbol"), t("../../modules/es6.object.to-string"), t("../../modules/es7.symbol.async-iterator"), t("../../modules/es7.symbol.observable"), e.localExports = t("../../modules/_core").Symbol
        }, {
          "../../modules/_core": 80,
          "../../modules/es6.object.to-string": 153,
          "../../modules/es6.symbol": 157,
          "../../modules/es7.symbol.async-iterator": 164,
          "../../modules/es7.symbol.observable": 165
        }],
        65: [function(t, e, n) {
          t("../../modules/es6.string.iterator"), t("../../modules/web.dom.iterable"), e.localExports = t("../../modules/_wks-ext").f("iterator")
        }, {
          "../../modules/_wks-ext": 140,
          "../../modules/es6.string.iterator": 156,
          "../../modules/web.dom.iterable": 166
        }],
        66: [function(t, e, n) {
          e.localExports = function(t) {
            if ("function" != typeof t) throw TypeError(t + " is not a function!");
            return t
          }
        }, {}],
        67: [function(t, e, n) {
          e.localExports = function() {}
        }, {}],
        68: [function(t, e, n) {
          e.localExports = function(t, e, n, r) {
            if (!(t instanceof e) || void 0 !== r && r in t) throw TypeError(n + ": incorrect invocation!");
            return t
          }
        }, {}],
        69: [function(t, e, n) {
          var r = t("./_is-object");
          e.localExports = function(t) {
            if (!r(t)) throw TypeError(t + " is not an object!");
            return t
          }
        }, {
          "./_is-object": 99
        }],
        70: [function(t, e, n) {
          var r = t("./_for-of");
          e.localExports = function(t, e) {
            var n = [];
            return r(t, !1, n.push, n, e), n
          }
        }, {
          "./_for-of": 90
        }],
        71: [function(t, e, n) {
          var r = t("./_to-iobject"),
            i = t("./_to-length"),
            o = t("./_to-absolute-index");
          e.localExports = function(t) {
            return function(e, n, a) {
              var s, u = r(e),
                c = i(u.length),
                l = o(a, c);
              if (t && n != n) {
                for (; c > l;)
                  if ((s = u[l++]) != s) return !0
              } else
                for (; c > l; l++)
                  if ((t || l in u) && u[l] === n) return t || l || 0; return !t && -1
            }
          }
        }, {
          "./_to-absolute-index": 131,
          "./_to-iobject": 133,
          "./_to-length": 134
        }],
        72: [function(t, e, n) {
          var r = t("./_ctx"),
            i = t("./_iobject"),
            o = t("./_to-object"),
            a = t("./_to-length"),
            s = t("./_array-species-create");
          e.localExports = function(t, e) {
            var n = 1 == t,
              u = 2 == t,
              c = 3 == t,
              l = 4 == t,
              h = 6 == t,
              f = 5 == t || h,
              d = e || s;
            return function(e, s, p) {
              for (var g, _, m = o(e), v = i(m), y = r(s, p, 3), b = a(v.length), w = 0, x = n ? d(e, b) : u ? d(e, 0) : void 0; b > w; w++)
                if ((f || w in v) && (g = v[w], _ = y(g, w, m), t))
                  if (n) x[w] = _;
                  else if (_) switch (t) {
                    case 3:
                      return !0;
                    case 5:
                      return g;
                    case 6:
                      return w;
                    case 2:
                      x.push(g)
                  } else if (l) return !1;
              return h ? -1 : c || l ? l : x
            }
          }
        }, {
          "./_array-species-create": 74,
          "./_ctx": 82,
          "./_iobject": 96,
          "./_to-length": 134,
          "./_to-object": 135
        }],
        73: [function(t, e, n) {
          var r = t("./_is-object"),
            i = t("./_is-array"),
            o = t("./_wks")("species");
          e.localExports = function(t) {
            var e;
            return i(t) && ("function" != typeof(e = t.constructor) || e !== Array && !i(e.prototype) || (e = void 0), r(e) && null === (e = e[o]) && (e = void 0)), void 0 === e ? Array : e
          }
        }, {
          "./_is-array": 98,
          "./_is-object": 99,
          "./_wks": 141
        }],
        74: [function(t, e, n) {
          var r = t("./_array-species-constructor");
          e.localExports = function(t, e) {
            return new(r(t))(e)
          }
        }, {
          "./_array-species-constructor": 73
        }],
        75: [function(t, e, n) {
          var r = t("./_cof"),
            i = t("./_wks")("toStringTag"),
            o = "Arguments" == r(function() {
              return arguments
            }()),
            a = function(t, e) {
              try {
                return t[e]
              } catch (t) {}
            };
          e.localExports = function(t) {
            var e, n, s;
            return void 0 === t ? "Undefined" : null === t ? "Null" : "string" == typeof(n = a(e = Object(t), i)) ? n : o ? r(e) : "Object" == (s = r(e)) && "function" == typeof e.callee ? "Arguments" : s
          }
        }, {
          "./_cof": 76,
          "./_wks": 141
        }],
        76: [function(t, e, n) {
          var r = {}.toString;
          e.localExports = function(t) {
            return r.call(t).slice(8, -1)
          }
        }, {}],
        77: [function(t, e, n) {
          "use strict";
          var r = t("./_object-dp").f,
            i = t("./_object-create"),
            o = t("./_redefine-all"),
            a = t("./_ctx"),
            s = t("./_an-instance"),
            u = t("./_for-of"),
            c = t("./_iter-define"),
            l = t("./_iter-step"),
            h = t("./_set-species"),
            f = t("./_descriptors"),
            d = t("./_meta").fastKey,
            p = t("./_validate-collection"),
            g = f ? "_s" : "size",
            _ = function(t, e) {
              var n, r = d(e);
              if ("F" !== r) return t._i[r];
              for (n = t._f; n; n = n.n)
                if (n.k == e) return n
            };
          e.localExports = {
            getConstructor: function(t, e, n, c) {
              var l = t(function(t, r) {
                s(t, l, e, "_i"), t._t = e, t._i = i(null), t._f = void 0, t._l = void 0, t[g] = 0, void 0 != r && u(r, n, t[c], t)
              });
              return o(l.prototype, {
                clear: function() {
                  for (var t = p(this, e), n = t._i, r = t._f; r; r = r.n) r.r = !0, r.p && (r.p = r.p.n = void 0), delete n[r.i];
                  t._f = t._l = void 0, t[g] = 0
                },
                delete: function(t) {
                  var n = p(this, e),
                    r = _(n, t);
                  if (r) {
                    var i = r.n,
                      o = r.p;
                    delete n._i[r.i], r.r = !0, o && (o.n = i), i && (i.p = o), n._f == r && (n._f = i), n._l == r && (n._l = o), n[g]--
                  }
                  return !!r
                },
                forEach: function(t) {
                  p(this, e);
                  for (var n, r = a(t, arguments.length > 1 ? arguments[1] : void 0, 3); n = n ? n.n : this._f;)
                    for (r(n.v, n.k, this); n && n.r;) n = n.p
                },
                has: function(t) {
                  return !!_(p(this, e), t)
                }
              }), f && r(l.prototype, "size", {
                get: function() {
                  return p(this, e)[g]
                }
              }), l
            },
            def: function(t, e, n) {
              var r, i, o = _(t, e);
              return o ? o.v = n : (t._l = o = {
                i: i = d(e, !0),
                k: e,
                v: n,
                p: r = t._l,
                n: void 0,
                r: !1
              }, t._f || (t._f = o), r && (r.n = o), t[g]++, "F" !== i && (t._i[i] = o)), t
            },
            getEntry: _,
            setStrong: function(t, e, n) {
              c(t, e, function(t, n) {
                this._t = p(t, e), this._k = n, this._l = void 0
              }, function() {
                for (var t = this, e = t._k, n = t._l; n && n.r;) n = n.p;
                return t._t && (t._l = n = n ? n.n : t._t._f) ? "keys" == e ? l(0, n.k) : "values" == e ? l(0, n.v) : l(0, [n.k, n.v]) : (t._t = void 0, l(1))
              }, n ? "entries" : "values", !n, !0), h(e)
            }
          }
        }, {
          "./_an-instance": 68,
          "./_ctx": 82,
          "./_descriptors": 84,
          "./_for-of": 90,
          "./_iter-define": 102,
          "./_iter-step": 104,
          "./_meta": 107,
          "./_object-create": 108,
          "./_object-dp": 109,
          "./_redefine-all": 121,
          "./_set-species": 126,
          "./_validate-collection": 138
        }],
        78: [function(t, e, n) {
          var r = t("./_classof"),
            i = t("./_array-from-iterable");
          e.localExports = function(t) {
            return function() {
              if (r(this) != t) throw TypeError(t + "#toJSON isn't generic");
              return i(this)
            }
          }
        }, {
          "./_array-from-iterable": 70,
          "./_classof": 75
        }],
        79: [function(t, e, n) {
          "use strict";
          var r = t("./_global"),
            i = t("./_export"),
            o = t("./_meta"),
            a = t("./_fails"),
            s = t("./_hide"),
            u = t("./_redefine-all"),
            c = t("./_for-of"),
            l = t("./_an-instance"),
            h = t("./_is-object"),
            f = t("./_set-to-string-tag"),
            d = t("./_object-dp").f,
            p = t("./_array-methods")(0),
            g = t("./_descriptors");
          e.localExports = function(t, e, n, _, m, v) {
            var y = r[t],
              b = y,
              w = m ? "set" : "add",
              x = b && b.prototype,
              S = {};
            return g && "function" == typeof b && (v || x.forEach && !a(function() {
              (new b).entries().next()
            })) ? (b = e(function(e, n) {
              l(e, b, t, "_c"), e._c = new y, void 0 != n && c(n, m, e[w], e)
            }), p("add,clear,delete,forEach,get,has,set,keys,values,entries,toJSON".split(","), function(t) {
              var e = "add" == t || "set" == t;
              t in x && (!v || "clear" != t) && s(b.prototype, t, function(n, r) {
                if (l(this, b, t), !e && v && !h(n)) return "get" == t && void 0;
                var i = this._c[t](0 === n ? 0 : n, r);
                return e ? this : i
              })
            }), v || d(b.prototype, "size", {
              get: function() {
                return this._c.size
              }
            })) : (b = _.getConstructor(e, t, m, w), u(b.prototype, n), o.NEED = !0), f(b, t), S[t] = b, i(i.G + i.W + i.F, S), v || _.setStrong(b, t, m), b
          }
        }, {
          "./_an-instance": 68,
          "./_array-methods": 72,
          "./_descriptors": 84,
          "./_export": 88,
          "./_fails": 89,
          "./_for-of": 90,
          "./_global": 91,
          "./_hide": 93,
          "./_is-object": 99,
          "./_meta": 107,
          "./_object-dp": 109,
          "./_redefine-all": 121,
          "./_set-to-string-tag": 127
        }],
        80: [function(t, e, n) {
          var r, i = e.localExports = {
            version: "2.5.7"
          };
          "number" == typeof r && (r = i)
        }, {}],
        81: [function(t, e, n) {
          "use strict";
          var r = t("./_object-dp"),
            i = t("./_property-desc");
          e.localExports = function(t, e, n) {
            e in t ? r.f(t, e, i(0, n)) : t[e] = n
          }
        }, {
          "./_object-dp": 109,
          "./_property-desc": 120
        }],
        82: [function(t, e, n) {
          var r = t("./_a-function");
          e.localExports = function(t, e, n) {
            if (r(t), void 0 === e) return t;
            switch (n) {
              case 1:
                return function(n) {
                  return t.call(e, n)
                };
              case 2:
                return function(n, r) {
                  return t.call(e, n, r)
                };
              case 3:
                return function(n, r, i) {
                  return t.call(e, n, r, i)
                }
            }
            return function() {
              return t.apply(e, arguments)
            }
          }
        }, {
          "./_a-function": 66
        }],
        83: [function(t, e, n) {
          e.localExports = function(t) {
            if (void 0 == t) throw TypeError("Can't call method on  " + t);
            return t
          }
        }, {}],
        84: [function(t, e, n) {
          e.localExports = !t("./_fails")(function() {
            return 7 != Object.defineProperty({}, "a", {
              get: function() {
                return 7
              }
            }).a
          })
        }, {
          "./_fails": 89
        }],
        85: [function(t, e, n) {
          var r = t("./_is-object"),
            i = t("./_global").document,
            o = r(i) && r(i.createElement);
          e.localExports = function(t) {
            return o ? i.createElement(t) : {}
          }
        }, {
          "./_global": 91,
          "./_is-object": 99
        }],
        86: [function(t, e, n) {
          e.localExports = "constructor,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString,toString,valueOf".split(",")
        }, {}],
        87: [function(t, e, n) {
          var r = t("./_object-keys"),
            i = t("./_object-gops"),
            o = t("./_object-pie");
          e.localExports = function(t) {
            var e = r(t),
              n = i.f;
            if (n)
              for (var a, s = n(t), u = o.f, c = 0; s.length > c;) u.call(t, a = s[c++]) && e.push(a);
            return e
          }
        }, {
          "./_object-gops": 114,
          "./_object-keys": 117,
          "./_object-pie": 118
        }],
        88: [function(t, e, n) {
          var r = t("./_global"),
            i = t("./_core"),
            o = t("./_ctx"),
            a = t("./_hide"),
            s = t("./_has"),
            u = function(t, e, n) {
              var c, l, h, f = t & u.F,
                d = t & u.G,
                p = t & u.S,
                g = t & u.P,
                _ = t & u.B,
                m = t & u.W,
                v = d ? i : i[e] || (i[e] = {}),
                y = v.prototype,
                b = d ? r : p ? r[e] : (r[e] || {}).prototype;
              d && (n = e);
              for (c in n)(l = !f && b && void 0 !== b[c]) && s(v, c) || (h = l ? b[c] : n[c], v[c] = d && "function" != typeof b[c] ? n[c] : _ && l ? o(h, r) : m && b[c] == h ? function(t) {
                var e = function(e, n, r) {
                  if (this instanceof t) {
                    switch (arguments.length) {
                      case 0:
                        return new t;
                      case 1:
                        return new t(e);
                      case 2:
                        return new t(e, n)
                    }
                    return new t(e, n, r)
                  }
                  return t.apply(this, arguments)
                };
                return e.prototype = t.prototype, e
              }(h) : g && "function" == typeof h ? o(Function.call, h) : h, g && ((v.virtual || (v.virtual = {}))[c] = h, t & u.R && y && !y[c] && a(y, c, h)))
            };
          u.F = 1, u.G = 2, u.S = 4, u.P = 8, u.B = 16, u.W = 32, u.U = 64, u.R = 128, e.localExports = u
        }, {
          "./_core": 80,
          "./_ctx": 82,
          "./_global": 91,
          "./_has": 92,
          "./_hide": 93
        }],
        89: [function(t, e, n) {
          e.localExports = function(t) {
            try {
              return !!t()
            } catch (t) {
              return !0
            }
          }
        }, {}],
        90: [function(t, e, n) {
          var r = t("./_ctx"),
            i = t("./_iter-call"),
            o = t("./_is-array-iter"),
            a = t("./_an-object"),
            s = t("./_to-length"),
            u = t("./core.get-iterator-method"),
            c = {},
            l = {};
          (n = e.localExports = function(t, e, n, h, f) {
            var d, p, g, _, m = f ? function() {
                return t
              } : u(t),
              v = r(n, h, e ? 2 : 1),
              y = 0;
            if ("function" != typeof m) throw TypeError(t + " is not iterable!");
            if (o(m)) {
              for (d = s(t.length); d > y; y++)
                if ((_ = e ? v(a(p = t[y])[0], p[1]) : v(t[y])) === c || _ === l) return _
            } else
              for (g = m.call(t); !(p = g.next()).done;)
                if ((_ = i(g, v, p.value, e)) === c || _ === l) return _
          }).BREAK = c, n.RETURN = l
        }, {
          "./_an-object": 69,
          "./_ctx": 82,
          "./_is-array-iter": 97,
          "./_iter-call": 100,
          "./_to-length": 134,
          "./core.get-iterator-method": 142
        }],
        91: [function(t, e, n) {
          var r, i = e.localExports = "undefined" != typeof window && window.Math == Math ? window : "undefined" != typeof self && self.Math == Math ? self : Function("return this")();
          "number" == typeof r && (r = i)
        }, {}],
        92: [function(t, e, n) {
          var r = {}.hasOwnProperty;
          e.localExports = function(t, e) {
            return r.call(t, e)
          }
        }, {}],
        93: [function(t, e, n) {
          var r = t("./_object-dp"),
            i = t("./_property-desc");
          e.localExports = t("./_descriptors") ? function(t, e, n) {
            return r.f(t, e, i(1, n))
          } : function(t, e, n) {
            return t[e] = n, t
          }
        }, {
          "./_descriptors": 84,
          "./_object-dp": 109,
          "./_property-desc": 120
        }],
        94: [function(t, e, n) {
          var r = t("./_global").document;
          e.localExports = r && r.documentElement
        }, {
          "./_global": 91
        }],
        95: [function(t, e, n) {
          e.localExports = !t("./_descriptors") && !t("./_fails")(function() {
            return 7 != Object.defineProperty(t("./_dom-create")("div"), "a", {
              get: function() {
                return 7
              }
            }).a
          })
        }, {
          "./_descriptors": 84,
          "./_dom-create": 85,
          "./_fails": 89
        }],
        96: [function(t, e, n) {
          var r = t("./_cof");
          e.localExports = Object("z").propertyIsEnumerable(0) ? Object : function(t) {
            return "String" == r(t) ? t.split("") : Object(t)
          }
        }, {
          "./_cof": 76
        }],
        97: [function(t, e, n) {
          var r = t("./_iterators"),
            i = t("./_wks")("iterator"),
            o = Array.prototype;
          e.localExports = function(t) {
            return void 0 !== t && (r.Array === t || o[i] === t)
          }
        }, {
          "./_iterators": 105,
          "./_wks": 141
        }],
        98: [function(t, e, n) {
          var r = t("./_cof");
          e.localExports = Array.isArray || function(t) {
            return "Array" == r(t)
          }
        }, {
          "./_cof": 76
        }],
        99: [function(t, e, n) {
          e.localExports = function(t) {
            return "object" == typeof t ? null !== t : "function" == typeof t
          }
        }, {}],
        100: [function(t, e, n) {
          var r = t("./_an-object");
          e.localExports = function(t, e, n, i) {
            try {
              return i ? e(r(n)[0], n[1]) : e(n)
            } catch (e) {
              var o = t.return;
              throw void 0 !== o && r(o.call(t)), e
            }
          }
        }, {
          "./_an-object": 69
        }],
        101: [function(t, e, n) {
          "use strict";
          var r = t("./_object-create"),
            i = t("./_property-desc"),
            o = t("./_set-to-string-tag"),
            a = {};
          t("./_hide")(a, t("./_wks")("iterator"), function() {
            return this
          }), e.localExports = function(t, e, n) {
            t.prototype = r(a, {
              next: i(1, n)
            }), o(t, e + " Iterator")
          }
        }, {
          "./_hide": 93,
          "./_object-create": 108,
          "./_property-desc": 120,
          "./_set-to-string-tag": 127,
          "./_wks": 141
        }],
        102: [function(t, e, n) {
          "use strict";
          var r = t("./_library"),
            i = t("./_export"),
            o = t("./_redefine"),
            a = t("./_hide"),
            s = t("./_iterators"),
            u = t("./_iter-create"),
            c = t("./_set-to-string-tag"),
            l = t("./_object-gpo"),
            h = t("./_wks")("iterator"),
            f = !([].keys && "next" in [].keys()),
            d = function() {
              return this
            };
          e.localExports = function(t, e, n, p, g, _, m) {
            u(n, e, p);
            var v, y, b, w = function(t) {
                if (!f && t in k) return k[t];
                switch (t) {
                  case "keys":
                  case "values":
                    return function() {
                      return new n(this, t)
                    }
                }
                return function() {
                  return new n(this, t)
                }
              },
              x = e + " Iterator",
              S = "values" == g,
              C = !1,
              k = t.prototype,
              E = k[h] || k["@@iterator"] || g && k[g],
              A = E || w(g),
              P = g ? S ? w("entries") : A : void 0,
              j = "Array" == e ? k.entries || E : E;
            if (j && (b = l(j.call(new t))) !== Object.prototype && b.next && (c(b, x, !0), r || "function" == typeof b[h] || a(b, h, d)), S && E && "values" !== E.name && (C = !0, A = function() {
              return E.call(this)
            }), r && !m || !f && !C && k[h] || a(k, h, A), s[e] = A, s[x] = d, g)
              if (v = {
                values: S ? A : w("values"),
                keys: _ ? A : w("keys"),
                entries: P
              }, m)
                for (y in v) y in k || o(k, y, v[y]);
              else i(i.P + i.F * (f || C), e, v);
            return v
          }
        }, {
          "./_export": 88,
          "./_hide": 93,
          "./_iter-create": 101,
          "./_iterators": 105,
          "./_library": 106,
          "./_object-gpo": 115,
          "./_redefine": 122,
          "./_set-to-string-tag": 127,
          "./_wks": 141
        }],
        103: [function(t, e, n) {
          var r = t("./_wks")("iterator"),
            i = !1;
          try {
            var o = [7][r]();
            o.return = function() {
              i = !0
            }, Array.from(o, function() {
              throw 2
            })
          } catch (t) {}
          e.localExports = function(t, e) {
            if (!e && !i) return !1;
            var n = !1;
            try {
              var o = [7],
                a = o[r]();
              a.next = function() {
                return {
                  done: n = !0
                }
              }, o[r] = function() {
                return a
              }, t(o)
            } catch (t) {}
            return n
          }
        }, {
          "./_wks": 141
        }],
        104: [function(t, e, n) {
          e.localExports = function(t, e) {
            return {
              value: e,
              done: !!t
            }
          }
        }, {}],
        105: [function(t, e, n) {
          e.localExports = {}
        }, {}],
        106: [function(t, e, n) {
          e.localExports = !0
        }, {}],
        107: [function(t, e, n) {
          var r = t("./_uid")("meta"),
            i = t("./_is-object"),
            o = t("./_has"),
            a = t("./_object-dp").f,
            s = 0,
            u = Object.isExtensible || function() {
              return !0
            },
            c = !t("./_fails")(function() {
              return u(Object.preventExtensions({}))
            }),
            l = function(t) {
              a(t, r, {
                value: {
                  i: "O" + ++s,
                  w: {}
                }
              })
            },
            h = e.localExports = {
              KEY: r,
              NEED: !1,
              fastKey: function(t, e) {
                if (!i(t)) return "symbol" == typeof t ? t : ("string" == typeof t ? "S" : "P") + t;
                if (!o(t, r)) {
                  if (!u(t)) return "F";
                  if (!e) return "E";
                  l(t)
                }
                return t[r].i
              },
              getWeak: function(t, e) {
                if (!o(t, r)) {
                  if (!u(t)) return !0;
                  if (!e) return !1;
                  l(t)
                }
                return t[r].w
              },
              onFreeze: function(t) {
                return c && h.NEED && u(t) && !o(t, r) && l(t), t
              }
            }
        }, {
          "./_fails": 89,
          "./_has": 92,
          "./_is-object": 99,
          "./_object-dp": 109,
          "./_uid": 137
        }],
        108: [function(t, e, n) {
          var r = t("./_an-object"),
            i = t("./_object-dps"),
            o = t("./_enum-bug-keys"),
            a = t("./_shared-key")("IE_PROTO"),
            s = function() {},
            u = function() {
              var e, n = t("./_dom-create")("iframe"),
                r = o.length;
              for (n.style.display = "none", t("./_html").appendChild(n), n.src = "javascript:", (e = n.contentWindow.document).open(), e.write("<script>document.F=Object<\/script>"), e.close(), u = e.F; r--;) delete u.prototype[o[r]];
              return u()
            };
          e.localExports = Object.create || function(t, e) {
            var n;
            return null !== t ? (s.prototype = r(t), n = new s, s.prototype = null, n[a] = t) : n = u(), void 0 === e ? n : i(n, e)
          }
        }, {
          "./_an-object": 69,
          "./_dom-create": 85,
          "./_enum-bug-keys": 86,
          "./_html": 94,
          "./_object-dps": 110,
          "./_shared-key": 128
        }],
        109: [function(t, e, n) {
          var r = t("./_an-object"),
            i = t("./_ie8-dom-define"),
            o = t("./_to-primitive"),
            a = Object.defineProperty;
          n.f = t("./_descriptors") ? Object.defineProperty : function(t, e, n) {
            if (r(t), e = o(e, !0), r(n), i) try {
              return a(t, e, n)
            } catch (t) {}
            if ("get" in n || "set" in n) throw TypeError("Accessors not supported!");
            return "value" in n && (t[e] = n.value), t
          }
        }, {
          "./_an-object": 69,
          "./_descriptors": 84,
          "./_ie8-dom-define": 95,
          "./_to-primitive": 136
        }],
        110: [function(t, e, n) {
          var r = t("./_object-dp"),
            i = t("./_an-object"),
            o = t("./_object-keys");
          e.localExports = t("./_descriptors") ? Object.defineProperties : function(t, e) {
            i(t);
            for (var n, a = o(e), s = a.length, u = 0; s > u;) r.f(t, n = a[u++], e[n]);
            return t
          }
        }, {
          "./_an-object": 69,
          "./_descriptors": 84,
          "./_object-dp": 109,
          "./_object-keys": 117
        }],
        111: [function(t, e, n) {
          var r = t("./_object-pie"),
            i = t("./_property-desc"),
            o = t("./_to-iobject"),
            a = t("./_to-primitive"),
            s = t("./_has"),
            u = t("./_ie8-dom-define"),
            c = Object.getOwnPropertyDescriptor;
          n.f = t("./_descriptors") ? c : function(t, e) {
            if (t = o(t), e = a(e, !0), u) try {
              return c(t, e)
            } catch (t) {}
            if (s(t, e)) return i(!r.f.call(t, e), t[e])
          }
        }, {
          "./_descriptors": 84,
          "./_has": 92,
          "./_ie8-dom-define": 95,
          "./_object-pie": 118,
          "./_property-desc": 120,
          "./_to-iobject": 133,
          "./_to-primitive": 136
        }],
        112: [function(t, e, n) {
          var r = t("./_to-iobject"),
            i = t("./_object-gopn").f,
            o = {}.toString,
            a = "object" == typeof window && window && Object.getOwnPropertyNames ? Object.getOwnPropertyNames(window) : [],
            s = function(t) {
              try {
                return i(t)
              } catch (t) {
                return a.slice()
              }
            };
          e.localExports.f = function(t) {
            return a && "[object Window]" == o.call(t) ? s(t) : i(r(t))
          }
        }, {
          "./_object-gopn": 113,
          "./_to-iobject": 133
        }],
        113: [function(t, e, n) {
          var r = t("./_object-keys-internal"),
            i = t("./_enum-bug-keys").concat("length", "prototype");
          n.f = Object.getOwnPropertyNames || function(t) {
            return r(t, i)
          }
        }, {
          "./_enum-bug-keys": 86,
          "./_object-keys-internal": 116
        }],
        114: [function(t, e, n) {
          n.f = Object.getOwnPropertySymbols
        }, {}],
        115: [function(t, e, n) {
          var r = t("./_has"),
            i = t("./_to-object"),
            o = t("./_shared-key")("IE_PROTO"),
            a = Object.prototype;
          e.localExports = Object.getPrototypeOf || function(t) {
            return t = i(t), r(t, o) ? t[o] : "function" == typeof t.constructor && t instanceof t.constructor ? t.constructor.prototype : t instanceof Object ? a : null
          }
        }, {
          "./_has": 92,
          "./_shared-key": 128,
          "./_to-object": 135
        }],
        116: [function(t, e, n) {
          var r = t("./_has"),
            i = t("./_to-iobject"),
            o = t("./_array-includes")(!1),
            a = t("./_shared-key")("IE_PROTO");
          e.localExports = function(t, e) {
            var n, s = i(t),
              u = 0,
              c = [];
            for (n in s) n != a && r(s, n) && c.push(n);
            for (; e.length > u;) r(s, n = e[u++]) && (~o(c, n) || c.push(n));
            return c
          }
        }, {
          "./_array-includes": 71,
          "./_has": 92,
          "./_shared-key": 128,
          "./_to-iobject": 133
        }],
        117: [function(t, e, n) {
          var r = t("./_object-keys-internal"),
            i = t("./_enum-bug-keys");
          e.localExports = Object.keys || function(t) {
            return r(t, i)
          }
        }, {
          "./_enum-bug-keys": 86,
          "./_object-keys-internal": 116
        }],
        118: [function(t, e, n) {
          n.f = {}.propertyIsEnumerable
        }, {}],
        119: [function(t, e, n) {
          var r = t("./_export"),
            i = t("./_core"),
            o = t("./_fails");
          e.localExports = function(t, e) {
            var n = (i.Object || {})[t] || Object[t],
              a = {};
            a[t] = e(n), r(r.S + r.F * o(function() {
              n(1)
            }), "Object", a)
          }
        }, {
          "./_core": 80,
          "./_export": 88,
          "./_fails": 89
        }],
        120: [function(t, e, n) {
          e.localExports = function(t, e) {
            return {
              enumerable: !(1 & t),
              configurable: !(2 & t),
              writable: !(4 & t),
              value: e
            }
          }
        }, {}],
        121: [function(t, e, n) {
          var r = t("./_hide");
          e.localExports = function(t, e, n) {
            for (var i in e) n && t[i] ? t[i] = e[i] : r(t, i, e[i]);
            return t
          }
        }, {
          "./_hide": 93
        }],
        122: [function(t, e, n) {
          e.localExports = t("./_hide")
        }, {
          "./_hide": 93
        }],
        123: [function(t, e, n) {
          "use strict";
          var r = t("./_export"),
            i = t("./_a-function"),
            o = t("./_ctx"),
            a = t("./_for-of");
          e.localExports = function(t) {
            r(r.S, t, {
              from: function(t) {
                var e, n, r, s, u = arguments[1];
                return i(this), (e = void 0 !== u) && i(u), void 0 == t ? new this : (n = [], e ? (r = 0, s = o(u, arguments[2], 2), a(t, !1, function(t) {
                  n.push(s(t, r++))
                })) : a(t, !1, n.push, n), new this(n))
              }
            })
          }
        }, {
          "./_a-function": 66,
          "./_ctx": 82,
          "./_export": 88,
          "./_for-of": 90
        }],
        124: [function(t, e, n) {
          "use strict";
          var r = t("./_export");
          e.localExports = function(t) {
            r(r.S, t, {
              of: function() {
                for (var t = arguments.length, e = new Array(t); t--;) e[t] = arguments[t];
                return new this(e)
              }
            })
          }
        }, {
          "./_export": 88
        }],
        125: [function(t, e, n) {
          var r = t("./_is-object"),
            i = t("./_an-object"),
            o = function(t, e) {
              if (i(t), !r(e) && null !== e) throw TypeError(e + ": can't set as prototype!")
            };
          e.localExports = {
            set: Object.setPrototypeOf || ("__proto__" in {} ? function(e, n, r) {
              try {
                (r = t("./_ctx")(Function.call, t("./_object-gopd").f(Object.prototype, "__proto__").set, 2))(e, []), n = !(e instanceof Array)
              } catch (t) {
                n = !0
              }
              return function(t, e) {
                return o(t, e), n ? t.__proto__ = e : r(t, e), t
              }
            }({}, !1) : void 0),
            check: o
          }
        }, {
          "./_an-object": 69,
          "./_ctx": 82,
          "./_is-object": 99,
          "./_object-gopd": 111
        }],
        126: [function(t, e, n) {
          "use strict";
          var r = t("./_global"),
            i = t("./_core"),
            o = t("./_object-dp"),
            a = t("./_descriptors"),
            s = t("./_wks")("species");
          e.localExports = function(t) {
            var e = "function" == typeof i[t] ? i[t] : r[t];
            a && e && !e[s] && o.f(e, s, {
              configurable: !0,
              get: function() {
                return this
              }
            })
          }
        }, {
          "./_core": 80,
          "./_descriptors": 84,
          "./_global": 91,
          "./_object-dp": 109,
          "./_wks": 141
        }],
        127: [function(t, e, n) {
          var r = t("./_object-dp").f,
            i = t("./_has"),
            o = t("./_wks")("toStringTag");
          e.localExports = function(t, e, n) {
            t && !i(t = n ? t : t.prototype, o) && r(t, o, {
              configurable: !0,
              value: e
            })
          }
        }, {
          "./_has": 92,
          "./_object-dp": 109,
          "./_wks": 141
        }],
        128: [function(t, e, n) {
          var r = t("./_shared")("keys"),
            i = t("./_uid");
          e.localExports = function(t) {
            return r[t] || (r[t] = i(t))
          }
        }, {
          "./_shared": 129,
          "./_uid": 137
        }],
        129: [function(t, e, n) {
          var r = t("./_core"),
            i = t("./_global"),
            o = i["__core-js_shared__"] || (i["__core-js_shared__"] = {});
          (e.localExports = function(t, e) {
            return o[t] || (o[t] = void 0 !== e ? e : {})
          })("versions", []).push({
            version: r.version,
            mode: t("./_library") ? "pure" : "global",
            copyright: "© 2018 Denis Pushkarev (zloirock.ru)"
          })
        }, {
          "./_core": 80,
          "./_global": 91,
          "./_library": 106
        }],
        130: [function(t, e, n) {
          var r = t("./_to-integer"),
            i = t("./_defined");
          e.localExports = function(t) {
            return function(e, n) {
              var o, a, s = String(i(e)),
                u = r(n),
                c = s.length;
              return u < 0 || u >= c ? t ? "" : void 0 : (o = s.charCodeAt(u)) < 55296 || o > 56319 || u + 1 === c || (a = s.charCodeAt(u + 1)) < 56320 || a > 57343 ? t ? s.charAt(u) : o : t ? s.slice(u, u + 2) : a - 56320 + (o - 55296 << 10) + 65536
            }
          }
        }, {
          "./_defined": 83,
          "./_to-integer": 132
        }],
        131: [function(t, e, n) {
          var r = t("./_to-integer"),
            i = Math.max,
            o = Math.min;
          e.localExports = function(t, e) {
            return (t = r(t)) < 0 ? i(t + e, 0) : o(t, e)
          }
        }, {
          "./_to-integer": 132
        }],
        132: [function(t, e, n) {
          var r = Math.ceil,
            i = Math.floor;
          e.localExports = function(t) {
            return isNaN(t = +t) ? 0 : (t > 0 ? i : r)(t)
          }
        }, {}],
        133: [function(t, e, n) {
          var r = t("./_iobject"),
            i = t("./_defined");
          e.localExports = function(t) {
            return r(i(t))
          }
        }, {
          "./_defined": 83,
          "./_iobject": 96
        }],
        134: [function(t, e, n) {
          var r = t("./_to-integer"),
            i = Math.min;
          e.localExports = function(t) {
            return t > 0 ? i(r(t), 9007199254740991) : 0
          }
        }, {
          "./_to-integer": 132
        }],
        135: [function(t, e, n) {
          var r = t("./_defined");
          e.localExports = function(t) {
            return Object(r(t))
          }
        }, {
          "./_defined": 83
        }],
        136: [function(t, e, n) {
          var r = t("./_is-object");
          e.localExports = function(t, e) {
            if (!r(t)) return t;
            var n, i;
            if (e && "function" == typeof(n = t.toString) && !r(i = n.call(t))) return i;
            if ("function" == typeof(n = t.valueOf) && !r(i = n.call(t))) return i;
            if (!e && "function" == typeof(n = t.toString) && !r(i = n.call(t))) return i;
            throw TypeError("Can't convert object to primitive value")
          }
        }, {
          "./_is-object": 99
        }],
        137: [function(t, e, n) {
          var r = 0,
            i = Math.random();
          e.localExports = function(t) {
            return "Symbol(".concat(void 0 === t ? "" : t, ")_", (++r + i).toString(36))
          }
        }, {}],
        138: [function(t, e, n) {
          var r = t("./_is-object");
          e.localExports = function(t, e) {
            if (!r(t) || t._t !== e) throw TypeError("Incompatible receiver, " + e + " required!");
            return t
          }
        }, {
          "./_is-object": 99
        }],
        139: [function(t, e, n) {
          var r = t("./_global"),
            i = t("./_core"),
            o = t("./_library"),
            a = t("./_wks-ext"),
            s = t("./_object-dp").f;
          e.localExports = function(t) {
            var e = i.Symbol || (i.Symbol = o ? {} : r.Symbol || {});
            "_" == t.charAt(0) || t in e || s(e, t, {
              value: a.f(t)
            })
          }
        }, {
          "./_core": 80,
          "./_global": 91,
          "./_library": 106,
          "./_object-dp": 109,
          "./_wks-ext": 140
        }],
        140: [function(t, e, n) {
          n.f = t("./_wks")
        }, {
          "./_wks": 141
        }],
        141: [function(t, e, n) {
          var r = t("./_shared")("wks"),
            i = t("./_uid"),
            o = t("./_global").Symbol,
            a = "function" == typeof o;
          (e.localExports = function(t) {
            return r[t] || (r[t] = a && o[t] || (a ? o : i)("Symbol." + t))
          }).store = r
        }, {
          "./_global": 91,
          "./_shared": 129,
          "./_uid": 137
        }],
        142: [function(t, e, n) {
          var r = t("./_classof"),
            i = t("./_wks")("iterator"),
            o = t("./_iterators");
          e.localExports = t("./_core").getIteratorMethod = function(t) {
            if (void 0 != t) return t[i] || t["@@iterator"] || o[r(t)]
          }
        }, {
          "./_classof": 75,
          "./_core": 80,
          "./_iterators": 105,
          "./_wks": 141
        }],
        143: [function(t, e, n) {
          var r = t("./_an-object"),
            i = t("./core.get-iterator-method");
          e.localExports = t("./_core").getIterator = function(t) {
            var e = i(t);
            if ("function" != typeof e) throw TypeError(t + " is not iterable!");
            return r(e.call(t))
          }
        }, {
          "./_an-object": 69,
          "./_core": 80,
          "./core.get-iterator-method": 142
        }],
        144: [function(t, e, n) {
          "use strict";
          var r = t("./_ctx"),
            i = t("./_export"),
            o = t("./_to-object"),
            a = t("./_iter-call"),
            s = t("./_is-array-iter"),
            u = t("./_to-length"),
            c = t("./_create-property"),
            l = t("./core.get-iterator-method");
          i(i.S + i.F * !t("./_iter-detect")(function(t) {
            Array.from(t)
          }), "Array", {
            from: function(t) {
              var e, n, i, h, f = o(t),
                d = "function" == typeof this ? this : Array,
                p = arguments.length,
                g = p > 1 ? arguments[1] : void 0,
                _ = void 0 !== g,
                m = 0,
                v = l(f);
              if (_ && (g = r(g, p > 2 ? arguments[2] : void 0, 2)), void 0 == v || d == Array && s(v))
                for (n = new d(e = u(f.length)); e > m; m++) c(n, m, _ ? g(f[m], m) : f[m]);
              else
                for (h = v.call(f), n = new d; !(i = h.next()).done; m++) c(n, m, _ ? a(h, g, [i.value, m], !0) : i.value);
              return n.length = m, n
            }
          })
        }, {
          "./_create-property": 81,
          "./_ctx": 82,
          "./_export": 88,
          "./_is-array-iter": 97,
          "./_iter-call": 100,
          "./_iter-detect": 103,
          "./_to-length": 134,
          "./_to-object": 135,
          "./core.get-iterator-method": 142
        }],
        145: [function(t, e, n) {
          "use strict";
          var r = t("./_add-to-unscopables"),
            i = t("./_iter-step"),
            o = t("./_iterators"),
            a = t("./_to-iobject");
          e.localExports = t("./_iter-define")(Array, "Array", function(t, e) {
            this._t = a(t), this._i = 0, this._k = e
          }, function() {
            var t = this._t,
              e = this._k,
              n = this._i++;
            return !t || n >= t.length ? (this._t = void 0, i(1)) : "keys" == e ? i(0, n) : "values" == e ? i(0, t[n]) : i(0, [n, t[n]])
          }, "values"), o.Arguments = o.Array, r("keys"), r("values"), r("entries")
        }, {
          "./_add-to-unscopables": 67,
          "./_iter-define": 102,
          "./_iter-step": 104,
          "./_iterators": 105,
          "./_to-iobject": 133
        }],
        146: [function(t, e, n) {
          "use strict";
          var r = t("./_collection-strong"),
            i = t("./_validate-collection");
          e.localExports = t("./_collection")("Map", function(t) {
            return function() {
              return t(this, arguments.length > 0 ? arguments[0] : void 0)
            }
          }, {
            get: function(t) {
              var e = r.getEntry(i(this, "Map"), t);
              return e && e.v
            },
            set: function(t, e) {
              return r.def(i(this, "Map"), 0 === t ? 0 : t, e)
            }
          }, r, !0)
        }, {
          "./_collection": 79,
          "./_collection-strong": 77,
          "./_validate-collection": 138
        }],
        147: [function(t, e, n) {
          var r = t("./_export");
          r(r.S, "Object", {
            create: t("./_object-create")
          })
        }, {
          "./_export": 88,
          "./_object-create": 108
        }],
        148: [function(t, e, n) {
          var r = t("./_export");
          r(r.S + r.F * !t("./_descriptors"), "Object", {
            defineProperty: t("./_object-dp").f
          })
        }, {
          "./_descriptors": 84,
          "./_export": 88,
          "./_object-dp": 109
        }],
        149: [function(t, e, n) {
          var r = t("./_is-object"),
            i = t("./_meta").onFreeze;
          t("./_object-sap")("freeze", function(t) {
            return function(e) {
              return t && r(e) ? t(i(e)) : e
            }
          })
        }, {
          "./_is-object": 99,
          "./_meta": 107,
          "./_object-sap": 119
        }],
        150: [function(t, e, n) {
          var r = t("./_to-iobject"),
            i = t("./_object-gopd").f;
          t("./_object-sap")("getOwnPropertyDescriptor", function() {
            return function(t, e) {
              return i(r(t), e)
            }
          })
        }, {
          "./_object-gopd": 111,
          "./_object-sap": 119,
          "./_to-iobject": 133
        }],
        151: [function(t, e, n) {
          var r = t("./_to-object"),
            i = t("./_object-keys");
          t("./_object-sap")("keys", function() {
            return function(t) {
              return i(r(t))
            }
          })
        }, {
          "./_object-keys": 117,
          "./_object-sap": 119,
          "./_to-object": 135
        }],
        152: [function(t, e, n) {
          var r = t("./_export");
          r(r.S, "Object", {
            setPrototypeOf: t("./_set-proto").set
          })
        }, {
          "./_export": 88,
          "./_set-proto": 125
        }],
        153: [function(t, e, n) {
          e.localExports = t(29)
        }, {}],
        154: [function(t, e, n) {
          "use strict";
          var r = t("./_collection-strong"),
            i = t("./_validate-collection");
          e.localExports = t("./_collection")("Set", function(t) {
            return function() {
              return t(this, arguments.length > 0 ? arguments[0] : void 0)
            }
          }, {
            add: function(t) {
              return r.def(i(this, "Set"), t = 0 === t ? 0 : t, t)
            }
          }, r)
        }, {
          "./_collection": 79,
          "./_collection-strong": 77,
          "./_validate-collection": 138
        }],
        155: [function(t, e, n) {
          var r = t("./_export"),
            i = t("./_to-absolute-index"),
            o = String.fromCharCode,
            a = String.fromCodePoint;
          r(r.S + r.F * (!!a && 1 != a.length), "String", {
            fromCodePoint: function(t) {
              for (var e, n = [], r = arguments.length, a = 0; r > a;) {
                if (e = +arguments[a++], i(e, 1114111) !== e) throw RangeError(e + " is not a valid code point");
                n.push(e < 65536 ? o(e) : o(55296 + ((e -= 65536) >> 10), e % 1024 + 56320))
              }
              return n.join("")
            }
          })
        }, {
          "./_export": 88,
          "./_to-absolute-index": 131
        }],
        156: [function(t, e, n) {
          "use strict";
          var r = t("./_string-at")(!0);
          t("./_iter-define")(String, "String", function(t) {
            this._t = String(t), this._i = 0
          }, function() {
            var t, e = this._t,
              n = this._i;
            return n >= e.length ? {
              value: void 0,
              done: !0
            } : (t = r(e, n), this._i += t.length, {
              value: t,
              done: !1
            })
          })
        }, {
          "./_iter-define": 102,
          "./_string-at": 130
        }],
        157: [function(t, e, n) {
          "use strict";
          var r = t("./_global"),
            i = t("./_has"),
            o = t("./_descriptors"),
            a = t("./_export"),
            s = t("./_redefine"),
            u = t("./_meta").KEY,
            c = t("./_fails"),
            l = t("./_shared"),
            h = t("./_set-to-string-tag"),
            f = t("./_uid"),
            d = t("./_wks"),
            p = t("./_wks-ext"),
            g = t("./_wks-define"),
            _ = t("./_enum-keys"),
            m = t("./_is-array"),
            v = t("./_an-object"),
            y = t("./_is-object"),
            b = t("./_to-iobject"),
            w = t("./_to-primitive"),
            x = t("./_property-desc"),
            S = t("./_object-create"),
            C = t("./_object-gopn-ext"),
            k = t("./_object-gopd"),
            E = t("./_object-dp"),
            A = t("./_object-keys"),
            P = k.f,
            j = E.f,
            I = C.f,
            L = r.Symbol,
            O = r.JSON,
            T = O && O.stringify,
            B = d("_hidden"),
            N = d("toPrimitive"),
            z = {}.propertyIsEnumerable,
            F = l("symbol-registry"),
            D = l("symbols"),
            M = l("op-symbols"),
            R = Object.prototype,
            U = "function" == typeof L,
            H = r.QObject,
            G = !H || !H.prototype || !H.prototype.findChild,
            q = o && c(function() {
              return 7 != S(j({}, "a", {
                get: function() {
                  return j(this, "a", {
                    value: 7
                  }).a
                }
              })).a
            }) ? function(t, e, n) {
              var r = P(R, e);
              r && delete R[e], j(t, e, n), r && t !== R && j(R, e, r)
            } : j,
            Z = function(t) {
              var e = D[t] = S(L.prototype);
              return e._k = t, e
            },
            W = U && "symbol" == typeof L.iterator ? function(t) {
              return "symbol" == typeof t
            } : function(t) {
              return t instanceof L
            },
            V = function(t, e, n) {
              return t === R && V(M, e, n), v(t), e = w(e, !0), v(n), i(D, e) ? (n.enumerable ? (i(t, B) && t[B][e] && (t[B][e] = !1), n = S(n, {
                enumerable: x(0, !1)
              })) : (i(t, B) || j(t, B, x(1, {})), t[B][e] = !0), q(t, e, n)) : j(t, e, n)
            },
            Y = function(t, e) {
              v(t);
              for (var n, r = _(e = b(e)), i = 0, o = r.length; o > i;) V(t, n = r[i++], e[n]);
              return t
            },
            K = function(t) {
              var e = z.call(this, t = w(t, !0));
              return !(this === R && i(D, t) && !i(M, t)) && (!(e || !i(this, t) || !i(D, t) || i(this, B) && this[B][t]) || e)
            },
            X = function(t, e) {
              if (t = b(t), e = w(e, !0), t !== R || !i(D, e) || i(M, e)) {
                var n = P(t, e);
                return !n || !i(D, e) || i(t, B) && t[B][e] || (n.enumerable = !0), n
              }
            },
            J = function(t) {
              for (var e, n = I(b(t)), r = [], o = 0; n.length > o;) i(D, e = n[o++]) || e == B || e == u || r.push(e);
              return r
            },
            Q = function(t) {
              for (var e, n = t === R, r = I(n ? M : b(t)), o = [], a = 0; r.length > a;) !i(D, e = r[a++]) || n && !i(R, e) || o.push(D[e]);
              return o
            };
          U || (s((L = function() {
            if (this instanceof L) throw TypeError("Symbol is not a constructor!");
            var t = f(arguments.length > 0 ? arguments[0] : void 0),
              e = function(n) {
                this === R && e.call(M, n), i(this, B) && i(this[B], t) && (this[B][t] = !1), q(this, t, x(1, n))
              };
            return o && G && q(R, t, {
              configurable: !0,
              set: e
            }), Z(t)
          }).prototype, "toString", function() {
            return this._k
          }), k.f = X, E.f = V, t("./_object-gopn").f = C.f = J, t("./_object-pie").f = K, t("./_object-gops").f = Q, o && !t("./_library") && s(R, "propertyIsEnumerable", K, !0), p.f = function(t) {
            return Z(d(t))
          }), a(a.G + a.W + a.F * !U, {
            Symbol: L
          });
          for (var $ = "hasInstance,isConcatSpreadable,iterator,match,replace,search,species,split,toPrimitive,toStringTag,unscopables".split(","), tt = 0; $.length > tt;) d($[tt++]);
          for (var et = A(d.store), nt = 0; et.length > nt;) g(et[nt++]);
          a(a.S + a.F * !U, "Symbol", {
            for: function(t) {
              return i(F, t += "") ? F[t] : F[t] = L(t)
            },
            keyFor: function(t) {
              if (!W(t)) throw TypeError(t + " is not a symbol!");
              for (var e in F)
                if (F[e] === t) return e
            },
            useSetter: function() {
              G = !0
            },
            useSimple: function() {
              G = !1
            }
          }), a(a.S + a.F * !U, "Object", {
            create: function(t, e) {
              return void 0 === e ? S(t) : Y(S(t), e)
            },
            defineProperty: V,
            defineProperties: Y,
            getOwnPropertyDescriptor: X,
            getOwnPropertyNames: J,
            getOwnPropertySymbols: Q
          }), O && a(a.S + a.F * (!U || c(function() {
            var t = L();
            return "[null]" != T([t]) || "{}" != T({
              a: t
            }) || "{}" != T(Object(t))
          })), "JSON", {
            stringify: function(t) {
              for (var e, n, r = [t], i = 1; arguments.length > i;) r.push(arguments[i++]);
              if (n = e = r[1], (y(e) || void 0 !== t) && !W(t)) return m(e) || (e = function(t, e) {
                if ("function" == typeof n && (e = n.call(this, t, e)), !W(e)) return e
              }), r[1] = e, T.apply(O, r)
            }
          }), L.prototype[N] || t("./_hide")(L.prototype, N, L.prototype.valueOf), h(L, "Symbol"), h(Math, "Math", !0), h(r.JSON, "JSON", !0)
        }, {
          "./_an-object": 69,
          "./_descriptors": 84,
          "./_enum-keys": 87,
          "./_export": 88,
          "./_fails": 89,
          "./_global": 91,
          "./_has": 92,
          "./_hide": 93,
          "./_is-array": 98,
          "./_is-object": 99,
          "./_library": 106,
          "./_meta": 107,
          "./_object-create": 108,
          "./_object-dp": 109,
          "./_object-gopd": 111,
          "./_object-gopn": 113,
          "./_object-gopn-ext": 112,
          "./_object-gops": 114,
          "./_object-keys": 117,
          "./_object-pie": 118,
          "./_property-desc": 120,
          "./_redefine": 122,
          "./_set-to-string-tag": 127,
          "./_shared": 129,
          "./_to-iobject": 133,
          "./_to-primitive": 136,
          "./_uid": 137,
          "./_wks": 141,
          "./_wks-define": 139,
          "./_wks-ext": 140
        }],
        158: [function(t, e, n) {
          t("./_set-collection-from")("Map")
        }, {
          "./_set-collection-from": 123
        }],
        159: [function(t, e, n) {
          t("./_set-collection-of")("Map")
        }, {
          "./_set-collection-of": 124
        }],
        160: [function(t, e, n) {
          var r = t("./_export");
          r(r.P + r.R, "Map", {
            toJSON: t("./_collection-to-json")("Map")
          })
        }, {
          "./_collection-to-json": 78,
          "./_export": 88
        }],
        161: [function(t, e, n) {
          t("./_set-collection-from")("Set")
        }, {
          "./_set-collection-from": 123
        }],
        162: [function(t, e, n) {
          t("./_set-collection-of")("Set")
        }, {
          "./_set-collection-of": 124
        }],
        163: [function(t, e, n) {
          var r = t("./_export");
          r(r.P + r.R, "Set", {
            toJSON: t("./_collection-to-json")("Set")
          })
        }, {
          "./_collection-to-json": 78,
          "./_export": 88
        }],
        164: [function(t, e, n) {
          t("./_wks-define")("asyncIterator")
        }, {
          "./_wks-define": 139
        }],
        165: [function(t, e, n) {
          t("./_wks-define")("observable")
        }, {
          "./_wks-define": 139
        }],
        166: [function(t, e, n) {
          t("./es6.array.iterator");
          for (var r = t("./_global"), i = t("./_hide"), o = t("./_iterators"), a = t("./_wks")("toStringTag"), s = "CSSRuleList,CSSStyleDeclaration,CSSValueList,ClientRectList,DOMRectList,DOMStringList,DOMTokenList,DataTransferItemList,FileList,HTMLAllCollection,HTMLCollection,HTMLFormElement,HTMLSelectElement,MediaList,MimeTypeArray,NamedNodeMap,NodeList,PaintRequestList,Plugin,PluginArray,SVGLengthList,SVGNumberList,SVGPathSegList,SVGPointList,SVGStringList,SVGTransformList,SourceBufferList,StyleSheetList,TextTrackCueList,TextTrackList,TouchList".split(","), u = 0; u < s.length; u++) {
            var c = s[u],
              l = r[c],
              h = l && l.prototype;
            h && !h[a] && i(h, a, c), o[c] = o.Array
          }
        }, {
          "./_global": 91,
          "./_hide": 93,
          "./_iterators": 105,
          "./_wks": 141,
          "./es6.array.iterator": 145
        }],
        167: [function(t, e, n) {
          (function() {
            var e, r, i, o;
            n.EncodeStream = t("./src/EncodeStream"), n.DecodeStream = t("./src/DecodeStream"), n.Array = t("./src/Array"), n.LazyArray = t("./src/LazyArray"), n.Bitfield = t("./src/Bitfield"), n.Boolean = t("./src/Boolean"), n.Buffer = t("./src/Buffer"), n.Enum = t("./src/Enum"), n.Optional = t("./src/Optional"), n.Reserved = t("./src/Reserved"), n.String = t("./src/String"), n.Struct = t("./src/Struct"), n.VersionedStruct = t("./src/VersionedStruct"), i = t("./src/Number");
            for (e in i) r = i[e], n[e] = r;
            o = t("./src/Pointer");
            for (e in o) r = o[e], n[e] = r
          }).call(this)
        }, {
          "./src/Array": 168,
          "./src/Bitfield": 169,
          "./src/Boolean": 170,
          "./src/Buffer": 171,
          "./src/DecodeStream": 172,
          "./src/EncodeStream": 173,
          "./src/Enum": 174,
          "./src/LazyArray": 175,
          "./src/Number": 176,
          "./src/Optional": 177,
          "./src/Pointer": 178,
          "./src/Reserved": 179,
          "./src/String": 180,
          "./src/Struct": 181,
          "./src/VersionedStruct": 182
        }],
        168: [function(t, e, n) {
          (function() {
            var n, r, i;
            r = t("./Number").Number, i = t("./utils"), n = function() {
              function t(t, e, n) {
                this.type = t, this.length = e, this.lengthType = null != n ? n : "count"
              }
              return t.prototype.decode = function(t, e) {
                var n, o, a, s, u, c;
                if (a = t.pos, s = [], n = e, null != this.length && (o = i.resolveLength(this.length, t, e)), this.length instanceof r && (Object.defineProperties(s, {
                  parent: {
                    value: e
                  },
                  _startOffset: {
                    value: a
                  },
                  _currentOffset: {
                    value: 0,
                    writable: !0
                  },
                  _length: {
                    value: o
                  }
                }), n = s), null == o || "bytes" === this.lengthType)
                  for (u = null != o ? t.pos + o : (null != e ? e._length : void 0) ? e._startOffset + e._length : t.length; t.pos < u;) s.push(this.type.decode(t, n));
                else
                  for (c = 0; c < o; c += 1) s.push(this.type.decode(t, n));
                return s
              }, t.prototype.size = function(t, e) {
                var n, o, a, s;
                if (!t) return this.type.size(null, e) * i.resolveLength(this.length, null, e);
                for (o = 0, this.length instanceof r && (o += this.length.size(), e = {
                  parent: e
                }), a = 0, s = t.length; a < s; a++) n = t[a], o += this.type.size(n, e);
                return o
              }, t.prototype.encode = function(t, e, n) {
                var i, o, a, s, u, c;
                for (i = n, this.length instanceof r && ((i = {
                  pointers: [],
                  startOffset: t.pos,
                  parent: n
                }).pointerOffset = t.pos + this.size(e, i), this.length.encode(t, e.length)), u = 0, c = e.length; u < c; u++) a = e[u], this.type.encode(t, a, i);
                if (this.length instanceof r)
                  for (o = 0; o < i.pointers.length;)(s = i.pointers[o++]).type.encode(t, s.val)
              }, t
            }(), e.localExports = n
          }).call(this)
        }, {
          "./Number": 176,
          "./utils": 183
        }],
        169: [function(t, e, n) {
          (function() {
            var t;
            t = function() {
              function t(t, e) {
                this.type = t, this.flags = null != e ? e : []
              }
              return t.prototype.decode = function(t) {
                var e, n, r, i, o, a, s;
                for (i = this.type.decode(t), r = {}, n = o = 0, a = (s = this.flags).length; o < a; n = ++o) null != (e = s[n]) && (r[e] = !!(i & 1 << n));
                return r
              }, t.prototype.size = function() {
                return this.type.size()
              }, t.prototype.encode = function(t, e) {
                var n, r, i, o, a, s;
                for (i = 0, r = o = 0, a = (s = this.flags).length; o < a; r = ++o) null != (n = s[r]) && e[n] && (i |= 1 << r);
                return this.type.encode(t, i)
              }, t
            }(), e.localExports = t
          }).call(this)
        }, {}],
        170: [function(t, e, n) {
          (function() {
            var t;
            t = function() {
              function t(t) {
                this.type = t
              }
              return t.prototype.decode = function(t, e) {
                return !!this.type.decode(t, e)
              }, t.prototype.size = function(t, e) {
                return this.type.size(t, e)
              }, t.prototype.encode = function(t, e, n) {
                return this.type.encode(t, +e, n)
              }, t
            }(), e.localExports = t
          }).call(this)
        }, {}],
        171: [function(t, e, n) {
          (function() {
            var n, r, i;
            i = t("./utils"), r = t("./Number").Number, n = function() {
              function t(t) {
                this.length = t
              }
              return t.prototype.decode = function(t, e) {
                var n;
                return n = i.resolveLength(this.length, t, e), t.readBuffer(n)
              }, t.prototype.size = function(t, e) {
                return t ? t.length : i.resolveLength(this.length, null, e)
              }, t.prototype.encode = function(t, e, n) {
                return this.length instanceof r && this.length.encode(t, e.length), t.writeBuffer(e)
              }, t
            }(), e.localExports = n
          }).call(this)
        }, {
          "./Number": 176,
          "./utils": 183
        }],
        172: [function(t, e, n) {
          (function(t) {
            (function() {
              var n, r;
              try {
                r = window.iconv
              } catch (t) {}
              n = function() {
                function e(t) {
                  this.buffer = t, this.pos = 0, this.length = this.buffer.length
                }
                var n;
                e.TYPES = {
                  UInt8: 1,
                  UInt16: 2,
                  UInt24: 3,
                  UInt32: 4,
                  Int8: 1,
                  Int16: 2,
                  Int24: 3,
                  Int32: 4,
                  Float: 4,
                  Double: 8
                };
                for (n in t.prototype) "read" === n.slice(0, 4) && function(t) {
                  var n;
                  n = e.TYPES[t.replace(/read|[BL]E/g, "")], e.prototype[t] = function() {
                    var e;
                    return e = this.buffer[t](this.pos), this.pos += n, e
                  }
                }(n);
                return e.prototype.readString = function(e, n) {
                  var i, o, a, s, u;
                  switch (null == n && (n = "ascii"), n) {
                    case "utf16le":
                    case "ucs2":
                    case "utf8":
                    case "ascii":
                      return this.buffer.toString(n, this.pos, this.pos += e);
                    case "utf16be":
                      for (a = s = 0, u = (i = new t(this.readBuffer(e))).length - 1; s < u; a = s += 2) o = i[a], i[a] = i[a + 1], i[a + 1] = o;
                      return i.toString("utf16le");
                    default:
                      if (i = this.readBuffer(e), r) try {
                        return r.decode(i, n)
                      } catch (t) {}
                      return i
                  }
                }, e.prototype.readBuffer = function(t) {
                  return this.buffer.slice(this.pos, this.pos += t)
                }, e.prototype.readUInt24BE = function() {
                  return (this.readUInt16BE() << 8) + this.readUInt8()
                }, e.prototype.readUInt24LE = function() {
                  return this.readUInt16LE() + (this.readUInt8() << 16)
                }, e.prototype.readInt24BE = function() {
                  return (this.readInt16BE() << 8) + this.readUInt8()
                }, e.prototype.readInt24LE = function() {
                  return this.readUInt16LE() + (this.readInt8() << 16)
                }, e
              }(), e.localExports = n
            }).call(this)
          }).call(this, t("buffer").Buffer)
        }, {
          buffer: 31
        }],
        173: [function(t, e, n) {
          (function(n) {
            (function() {
              var r, i, o, a, s = {}.hasOwnProperty,
                u = function(t, e) {
                  function n() {
                    this.constructor = t
                  }
                  for (var r in e) s.call(e, r) && (t[r] = e[r]);
                  return n.prototype = e.prototype, t.prototype = new n, t.__super__ = e.prototype, t
                };
              a = t("stream"), r = t("./DecodeStream");
              try {
                o = window.iconv
              } catch (t) {}
              i = function(t) {
                function e(t) {
                  null == t && (t = 65536), e.__super__.constructor.apply(this, arguments), this.buffer = new n(t), this.bufferOffset = 0, this.pos = 0
                }
                var i;
                u(e, t);
                for (i in n.prototype) "write" === i.slice(0, 5) && function(t) {
                  var n;
                  n = +r.TYPES[t.replace(/write|[BL]E/g, "")], e.prototype[t] = function(e) {
                    return this.ensure(n), this.buffer[t](e, this.bufferOffset), this.bufferOffset += n, this.pos += n
                  }
                }(i);
                return e.prototype._read = function() {}, e.prototype.ensure = function(t) {
                  if (this.bufferOffset + t > this.buffer.length) return this.flush()
                }, e.prototype.flush = function() {
                  if (this.bufferOffset > 0) return this.push(new n(this.buffer.slice(0, this.bufferOffset))), this.bufferOffset = 0
                }, e.prototype.writeBuffer = function(t) {
                  return this.flush(), this.push(t), this.pos += t.length
                }, e.prototype.writeString = function(t, e) {
                  var r, i, a, s, u;
                  switch (null == e && (e = "ascii"), e) {
                    case "utf16le":
                    case "ucs2":
                    case "utf8":
                    case "ascii":
                      return this.writeBuffer(new n(t, e));
                    case "utf16be":
                      for (a = s = 0, u = (r = new n(t, "utf16le")).length - 1; s < u; a = s += 2) i = r[a], r[a] = r[a + 1], r[a + 1] = i;
                      return this.writeBuffer(r);
                    default:
                      if (o) return this.writeBuffer(o.encode(t, e));
                      throw new Error("Install iconv-lite to enable additional string encodings.")
                  }
                }, e.prototype.writeUInt24BE = function(t) {
                  return this.ensure(3), this.buffer[this.bufferOffset++] = t >>> 16 & 255, this.buffer[this.bufferOffset++] = t >>> 8 & 255, this.buffer[this.bufferOffset++] = 255 & t, this.pos += 3
                }, e.prototype.writeUInt24LE = function(t) {
                  return this.ensure(3), this.buffer[this.bufferOffset++] = 255 & t, this.buffer[this.bufferOffset++] = t >>> 8 & 255, this.buffer[this.bufferOffset++] = t >>> 16 & 255, this.pos += 3
                }, e.prototype.writeInt24BE = function(t) {
                  return t >= 0 ? this.writeUInt24BE(t) : this.writeUInt24BE(t + 16777215 + 1)
                }, e.prototype.writeInt24LE = function(t) {
                  return t >= 0 ? this.writeUInt24LE(t) : this.writeUInt24LE(t + 16777215 + 1)
                }, e.prototype.fill = function(t, e) {
                  var r;
                  return e < this.buffer.length ? (this.ensure(e), this.buffer.fill(t, this.bufferOffset, this.bufferOffset + e), this.bufferOffset += e, this.pos += e) : ((r = new n(e)).fill(t), this.writeBuffer(r))
                }, e.prototype.end = function() {
                  return this.flush(), this.push(null)
                }, e
              }(a.Readable), e.localExports = i
            }).call(this)
          }).call(this, t("buffer").Buffer)
        }, {
          "./DecodeStream": 172,
          buffer: 31,
          stream: 207
        }],
        174: [function(t, e, n) {
          (function() {
            var t;
            t = function() {
              function t(t, e) {
                this.type = t, this.options = null != e ? e : []
              }
              return t.prototype.decode = function(t) {
                var e;
                return e = this.type.decode(t), this.options[e] || e
              }, t.prototype.size = function() {
                return this.type.size()
              }, t.prototype.encode = function(t, e) {
                var n;
                if (-1 === (n = this.options.indexOf(e))) throw new Error("Unknown option in enum: " + e);
                return this.type.encode(t, n)
              }, t
            }(), e.localExports = t
          }).call(this)
        }, {}],
        175: [function(t, e, n) {
          (function() {
            var n, r, i, o, a, s, u = {}.hasOwnProperty,
              c = function(t, e) {
                function n() {
                  this.constructor = t
                }
                for (var r in e) u.call(e, r) && (t[r] = e[r]);
                return n.prototype = e.prototype, t.prototype = new n, t.__super__ = e.prototype, t
              };
            n = t("./Array"), o = t("./Number").Number, s = t("./utils"), a = t("util").inspect, i = function(t) {
              function e() {
                return e.__super__.constructor.apply(this, arguments)
              }
              return c(e, n), e.prototype.decode = function(t, e) {
                var n, i, a;
                return i = t.pos, n = s.resolveLength(this.length, t, e), this.length instanceof o && (e = {
                  parent: e,
                  _startOffset: i,
                  _currentOffset: 0,
                  _length: n
                }), a = new r(this.type, n, t, e), t.pos += n * this.type.size(null, e), a
              }, e.prototype.size = function(t, n) {
                return t instanceof r && (t = t.toArray()), e.__super__.size.call(this, t, n)
              }, e.prototype.encode = function(t, n, i) {
                return n instanceof r && (n = n.toArray()), e.__super__.encode.call(this, t, n, i)
              }, e
            }(), r = function() {
              function t(t, e, n, r) {
                this.type = t, this.length = e, this.stream = n, this.ctx = r, this.base = this.stream.pos, this.items = []
              }
              return t.prototype.get = function(t) {
                var e;
                if (!(t < 0 || t >= this.length)) return null == this.items[t] && (e = this.stream.pos, this.stream.pos = this.base + this.type.size(null, this.ctx) * t, this.items[t] = this.type.decode(this.stream, this.ctx), this.stream.pos = e), this.items[t]
              }, t.prototype.toArray = function() {
                var t, e, n, r;
                for (r = [], t = e = 0, n = this.length; e < n; t = e += 1) r.push(this.get(t));
                return r
              }, t.prototype.inspect = function() {
                return a(this.toArray())
              }, t
            }(), e.localExports = i
          }).call(this)
        }, {
          "./Array": 168,
          "./Number": 176,
          "./utils": 183,
          util: 216
        }],
        176: [function(t, e, n) {
          (function() {
            var e, r, i, o = {}.hasOwnProperty,
              a = function(t, e) {
                function n() {
                  this.constructor = t
                }
                for (var r in e) o.call(e, r) && (t[r] = e[r]);
                return n.prototype = e.prototype, t.prototype = new n, t.__super__ = e.prototype, t
              };
            e = t("./DecodeStream"), i = function() {
              function t(t, e) {
                this.type = t, this.endian = null != e ? e : "BE", this.fn = this.type, "8" !== this.type[this.type.length - 1] && (this.fn += this.endian)
              }
              return t.prototype.size = function() {
                return e.TYPES[this.type]
              }, t.prototype.decode = function(t) {
                return t["read" + this.fn]()
              }, t.prototype.encode = function(t, e) {
                return t["write" + this.fn](e)
              }, t
            }(), n.Number = i, n.uint8 = new i("UInt8"), n.uint16be = n.uint16 = new i("UInt16", "BE"), n.uint16le = new i("UInt16", "LE"), n.uint24be = n.uint24 = new i("UInt24", "BE"), n.uint24le = new i("UInt24", "LE"), n.uint32be = n.uint32 = new i("UInt32", "BE"), n.uint32le = new i("UInt32", "LE"), n.int8 = new i("Int8"), n.int16be = n.int16 = new i("Int16", "BE"), n.int16le = new i("Int16", "LE"), n.int24be = n.int24 = new i("Int24", "BE"), n.int24le = new i("Int24", "LE"), n.int32be = n.int32 = new i("Int32", "BE"), n.int32le = new i("Int32", "LE"), n.floatbe = n.float = new i("Float", "BE"), n.floatle = new i("Float", "LE"), n.doublebe = n.double = new i("Double", "BE"), n.doublele = new i("Double", "LE"), r = function(t) {
              function e(t, n, r) {
                null == r && (r = t >> 1), e.__super__.constructor.call(this, "Int" + t, n), this._point = 1 << r
              }
              return a(e, i), e.prototype.decode = function(t) {
                return e.__super__.decode.call(this, t) / this._point
              }, e.prototype.encode = function(t, n) {
                return e.__super__.encode.call(this, t, n * this._point | 0)
              }, e
            }(), n.Fixed = r, n.fixed16be = n.fixed16 = new r(16, "BE"), n.fixed16le = new r(16, "LE"), n.fixed32be = n.fixed32 = new r(32, "BE"), n.fixed32le = new r(32, "LE")
          }).call(this)
        }, {
          "./DecodeStream": 172
        }],
        177: [function(t, e, n) {
          (function() {
            var t;
            t = function() {
              function t(t, e) {
                this.type = t, this.condition = null == e || e
              }
              return t.prototype.decode = function(t, e) {
                var n;
                if ("function" == typeof(n = this.condition) && (n = n.call(e, e)), n) return this.type.decode(t, e)
              }, t.prototype.size = function(t, e) {
                var n;
                return "function" == typeof(n = this.condition) && (n = n.call(e, e)), n ? this.type.size(t, e) : 0
              }, t.prototype.encode = function(t, e, n) {
                var r;
                if ("function" == typeof(r = this.condition) && (r = r.call(n, n)), r) return this.type.encode(t, e, n)
              }, t
            }(), e.localExports = t
          }).call(this)
        }, {}],
        178: [function(t, e, n) {
          (function() {
            var e, r, i;
            i = t("./utils"), e = function() {
              function t(t, e, n) {
                var r, i, o, a;
                this.offsetType = t, this.type = e, this.options = null != n ? n : {}, "void" === this.type && (this.type = null), null == (r = this.options).type && (r.type = "local"), null == (i = this.options).allowNull && (i.allowNull = !0), null == (o = this.options).nullValue && (o.nullValue = 0), null == (a = this.options).lazy && (a.lazy = !1), this.options.relativeTo && (this.relativeToGetter = new Function("ctx", "return ctx." + this.options.relativeTo))
              }
              return t.prototype.decode = function(t, e) {
                var n, r, o, a, s, u;
                return (o = this.offsetType.decode(t, e)) === this.options.nullValue && this.options.allowNull ? null : (s = function() {
                  switch (this.options.type) {
                    case "local":
                      return e._startOffset;
                    case "immediate":
                      return t.pos - this.offsetType.size();
                    case "parent":
                      return e.parent._startOffset;
                    default:
                      for (n = e; n.parent;) n = n.parent;
                      return n._startOffset || 0
                  }
                }.call(this), this.options.relativeTo && (s += this.relativeToGetter(e)), a = o + s, null != this.type ? (u = null, r = function(n) {
                  return function() {
                    var r;
                    return null != u ? u : (r = t.pos, t.pos = a, u = n.type.decode(t, e), t.pos = r, u)
                  }
                }(this), this.options.lazy ? new i.PropertyDescriptor({
                  get: r
                }) : r()) : a)
              }, t.prototype.size = function(t, e) {
                var n, i;
                switch (n = e, this.options.type) {
                  case "local":
                  case "immediate":
                    break;
                  case "parent":
                    e = e.parent;
                    break;
                  default:
                    for (; e.parent;) e = e.parent
                }
                if (null == (i = this.type)) {
                  if (!(t instanceof r)) throw new Error("Must be a VoidPointer");
                  i = t.type, t = t.value
                }
                return t && e && (e.pointerSize += i.size(t, n)), this.offsetType.size()
              }, t.prototype.encode = function(t, e, n) {
                var i, o, a;
                i = n;
                if (null != e) {
                  switch (this.options.type) {
                    case "local":
                      o = n.startOffset;
                      break;
                    case "immediate":
                      o = t.pos + this.offsetType.size(e, i);
                      break;
                    case "parent":
                      o = (n = n.parent).startOffset;
                      break;
                    default:
                      for (o = 0; n.parent;) n = n.parent
                  }
                  if (this.options.relativeTo && (o += this.relativeToGetter(i.val)), this.offsetType.encode(t, n.pointerOffset - o), null == (a = this.type)) {
                    if (!(e instanceof r)) throw new Error("Must be a VoidPointer");
                    a = e.type, e = e.value
                  }
                  return n.pointers.push({
                    type: a,
                    val: e,
                    parent: i
                  }), n.pointerOffset += a.size(e, i)
                }
                this.offsetType.encode(t, this.options.nullValue)
              }, t
            }(), r = function() {
              return function(t, e) {
                this.type = t, this.value = e
              }
            }(), n.Pointer = e, n.VoidPointer = r
          }).call(this)
        }, {
          "./utils": 183
        }],
        179: [function(t, e, n) {
          (function() {
            var n, r;
            r = t("./utils"), n = function() {
              function t(t, e) {
                this.type = t, this.count = null != e ? e : 1
              }
              return t.prototype.decode = function(t, e) {
                t.pos += this.size(null, e)
              }, t.prototype.size = function(t, e) {
                var n;
                return n = r.resolveLength(this.count, null, e), this.type.size() * n
              }, t.prototype.encode = function(t, e, n) {
                return t.fill(0, this.size(e, n))
              }, t
            }(), e.localExports = n
          }).call(this)
        }, {
          "./utils": 183
        }],
        180: [function(t, e, n) {
          (function(n) {
            (function() {
              var r, i, o;
              r = t("./Number").Number, o = t("./utils"), i = function() {
                function t(t, e) {
                  this.length = t, this.encoding = null != e ? e : "ascii"
                }
                return t.prototype.decode = function(t, e) {
                  var n, r, i, a, s;
                  return i = function() {
                    if (null != this.length) return o.resolveLength(this.length, t, e);
                    for (n = t.buffer, i = t.length, a = t.pos; a < i && 0 !== n[a];) ++a;
                    return a - t.pos
                  }.call(this), "function" == typeof(r = this.encoding) && (r = r.call(e, e) || "ascii"), s = t.readString(i, r), null == this.length && t.pos < t.length && t.pos++, s
                }, t.prototype.size = function(t, e) {
                  var i, a;
                  return t ? ("function" == typeof(i = this.encoding) && (i = i.call(null != e ? e.val : void 0, null != e ? e.val : void 0) || "ascii"), "utf16be" === i && (i = "utf16le"), a = n.byteLength(t, i), this.length instanceof r && (a += this.length.size()), null == this.length && a++, a) : o.resolveLength(this.length, null, e)
                }, t.prototype.encode = function(t, e, i) {
                  var o;
                  if ("function" == typeof(o = this.encoding) && (o = o.call(null != i ? i.val : void 0, null != i ? i.val : void 0) || "ascii"), this.length instanceof r && this.length.encode(t, n.byteLength(e, o)), t.writeString(e, o), null == this.length) return t.writeUInt8(0)
                }, t
              }(), e.localExports = i
            }).call(this)
          }).call(this, t("buffer").Buffer)
        }, {
          "./Number": 176,
          "./utils": 183,
          buffer: 31
        }],
        181: [function(t, e, n) {
          (function() {
            var n, r;
            r = t("./utils"), n = function() {
              function t(t) {
                this.fields = null != t ? t : {}
              }
              return t.prototype.decode = function(t, e, n) {
                var r, i;
                return null == n && (n = 0), r = this._setup(t, e, n), this._parseFields(t, r, this.fields), null != (i = this.process) && i.call(r, t), r
              }, t.prototype._setup = function(t, e, n) {
                var r;
                return r = {}, Object.defineProperties(r, {
                  parent: {
                    value: e
                  },
                  _startOffset: {
                    value: t.pos
                  },
                  _currentOffset: {
                    value: 0,
                    writable: !0
                  },
                  _length: {
                    value: n
                  }
                }), r
              }, t.prototype._parseFields = function(t, e, n) {
                var i, o, a;
                for (i in n) void 0 !== (a = "function" == typeof(o = n[i]) ? o.call(e, e) : o.decode(t, e)) && (a instanceof r.PropertyDescriptor ? Object.defineProperty(e, i, a) : e[i] = a), e._currentOffset = t.pos - e._startOffset
              }, t.prototype.size = function(t, e, n) {
                var r, i, o, a, s;
                null == t && (t = {}), null == n && (n = !0), r = {
                  parent: e,
                  val: t,
                  pointerSize: 0
                }, o = 0, s = this.fields;
                for (i in s) null != (a = s[i]).size && (o += a.size(t[i], r));
                return n && (o += r.pointerSize), o
              }, t.prototype.encode = function(t, e, n) {
                var r, i, o, a, s, u, c;
                null != (u = this.preEncode) && u.call(e, t), (r = {
                  pointers: [],
                  startOffset: t.pos,
                  parent: n,
                  val: e,
                  pointerSize: 0
                }).pointerOffset = t.pos + this.size(e, r, !1), c = this.fields;
                for (o in c) null != (s = c[o]).encode && s.encode(t, e[o], r);
                for (i = 0; i < r.pointers.length;)(a = r.pointers[i++]).type.encode(t, a.val, a.parent)
              }, t
            }(), e.localExports = n
          }).call(this)
        }, {
          "./utils": 183
        }],
        182: [function(t, e, n) {
          (function() {
            var n, r, i = {}.hasOwnProperty,
              o = function(t, e) {
                function n() {
                  this.constructor = t
                }
                for (var r in e) i.call(e, r) && (t[r] = e[r]);
                return n.prototype = e.prototype, t.prototype = new n, t.__super__ = e.prototype, t
              };
            n = t("./Struct"), r = function(t) {
              function e(t, e) {
                this.type = t, this.versions = null != e ? e : {}, "string" == typeof this.type && (this.versionGetter = new Function("parent", "return parent." + this.type), this.versionSetter = new Function("parent", "version", "return parent." + this.type + " = version"))
              }
              return o(e, n), e.prototype.decode = function(t, n, r) {
                var i, o, a;
                if (null == r && (r = 0), o = this._setup(t, n, r), "string" == typeof this.type ? o.version = this.versionGetter(n) : o.version = this.type.decode(t), this.versions.header && this._parseFields(t, o, this.versions.header), null == (i = this.versions[o.version])) throw new Error("Unknown version " + o.version);
                return i instanceof e ? i.decode(t, n) : (this._parseFields(t, o, i), null != (a = this.process) && a.call(o, t), o)
              }, e.prototype.size = function(t, e, n) {
                var r, i, o, a, s, u;
                if (null == n && (n = !0), !t) throw new Error("Not a fixed size");
                if (r = {
                  parent: e,
                  val: t,
                  pointerSize: 0
                }, a = 0, "string" != typeof this.type && (a += this.type.size(t.version, r)), this.versions.header) {
                  u = this.versions.header;
                  for (o in u) null != (s = u[o]).size && (a += s.size(t[o], r))
                }
                if (null == (i = this.versions[t.version])) throw new Error("Unknown version " + t.version);
                for (o in i) null != (s = i[o]).size && (a += s.size(t[o], r));
                return n && (a += r.pointerSize), a
              }, e.prototype.encode = function(t, e, n) {
                var r, i, o, a, s, u, c, l;
                if (null != (c = this.preEncode) && c.call(e, t), r = {
                  pointers: [],
                  startOffset: t.pos,
                  parent: n,
                  val: e,
                  pointerSize: 0
                }, r.pointerOffset = t.pos + this.size(e, r, !1), "string" != typeof this.type && this.type.encode(t, e.version), this.versions.header) {
                  l = this.versions.header;
                  for (a in l) null != (u = l[a]).encode && u.encode(t, e[a], r)
                }
                i = this.versions[e.version];
                for (a in i) null != (u = i[a]).encode && u.encode(t, e[a], r);
                for (o = 0; o < r.pointers.length;)(s = r.pointers[o++]).type.encode(t, s.val, s.parent)
              }, e
            }(), e.localExports = r
          }).call(this)
        }, {
          "./Struct": 181
        }],
        183: [function(t, e, n) {
          (function() {
            var e, r;
            e = t("./Number").Number, n.resolveLength = function(t, n, r) {
              var i;
              if ("number" == typeof t ? i = t : "function" == typeof t ? i = t.call(r, r) : r && "string" == typeof t ? i = r[t] : n && t instanceof e && (i = t.decode(n)), isNaN(i)) throw new Error("Not a fixed size");
              return i
            }, r = function() {
              return function(t) {
                var e, n;
                null == t && (t = {}), this.enumerable = !0, this.configurable = !0;
                for (e in t) n = t[e], this[e] = n
              }
            }(), n.PropertyDescriptor = r
          }).call(this)
        }, {
          "./Number": 176
        }],
        184: [function(t, e, n) {
          function r() {
            this.table = new Uint16Array(16), this.trans = new Uint16Array(288)
          }

          function i(t, e) {
            this.source = t, this.sourceIndex = 0, this.tag = 0, this.bitcount = 0, this.dest = e, this.destLen = 0, this.ltree = new r, this.dtree = new r
          }

          function o(t, e, n, r) {
            var i, o;
            for (i = 0; i < n; ++i) t[i] = 0;
            for (i = 0; i < 30 - n; ++i) t[i + n] = i / n | 0;
            for (o = r, i = 0; i < 30; ++i) e[i] = o, o += 1 << t[i]
          }

          function a(t, e, n, r) {
            var i, o;
            for (i = 0; i < 16; ++i) t.table[i] = 0;
            for (i = 0; i < r; ++i) t.table[e[n + i]]++;
            for (t.table[0] = 0, o = 0, i = 0; i < 16; ++i) C[i] = o, o += t.table[i];
            for (i = 0; i < r; ++i) e[n + i] && (t.trans[C[e[n + i]]++] = i)
          }

          function s(t) {
            t.bitcount-- || (t.tag = t.source[t.sourceIndex++], t.bitcount = 7);
            var e = 1 & t.tag;
            return t.tag >>>= 1, e
          }

          function u(t, e, n) {
            if (!e) return n;
            for (; t.bitcount < 24;) t.tag |= t.source[t.sourceIndex++] << t.bitcount, t.bitcount += 8;
            var r = t.tag & 65535 >>> 16 - e;
            return t.tag >>>= e, t.bitcount -= e, r + n
          }

          function c(t, e) {
            for (; t.bitcount < 24;) t.tag |= t.source[t.sourceIndex++] << t.bitcount, t.bitcount += 8;
            var n = 0,
              r = 0,
              i = 0,
              o = t.tag;
            do {
              r = 2 * r + (1 & o), o >>>= 1, ++i, n += e.table[i], r -= e.table[i]
            } while (r >= 0);
            return t.tag = o, t.bitcount -= i, e.trans[n + r]
          }

          function l(t, e, n) {
            var r, i, o, s, l, h;
            for (r = u(t, 5, 257), i = u(t, 5, 1), o = u(t, 4, 4), s = 0; s < 19; ++s) S[s] = 0;
            for (s = 0; s < o; ++s) {
              var f = u(t, 3, 0);
              S[w[s]] = f
            }
            for (a(x, S, 0, 19), l = 0; l < r + i;) {
              var d = c(t, x);
              switch (d) {
                case 16:
                  var p = S[l - 1];
                  for (h = u(t, 2, 3); h; --h) S[l++] = p;
                  break;
                case 17:
                  for (h = u(t, 3, 3); h; --h) S[l++] = 0;
                  break;
                case 18:
                  for (h = u(t, 7, 11); h; --h) S[l++] = 0;
                  break;
                default:
                  S[l++] = d
              }
            }
            a(e, S, 0, r), a(n, S, r, i)
          }

          function h(t, e, n) {
            for (;;) {
              var r = c(t, e);
              if (256 === r) return d;
              if (r < 256) t.dest[t.destLen++] = r;
              else {
                var i, o, a, s;
                for (i = u(t, m[r -= 257], v[r]), o = c(t, n), s = a = t.destLen - u(t, y[o], b[o]); s < a + i; ++s) t.dest[t.destLen++] = t.dest[s]
              }
            }
          }

          function f(t) {
            for (var e, n, r; t.bitcount > 8;) t.sourceIndex--, t.bitcount -= 8;
            if (e = t.source[t.sourceIndex + 1], e = 256 * e + t.source[t.sourceIndex], n = t.source[t.sourceIndex + 3], n = 256 * n + t.source[t.sourceIndex + 2], e !== (65535 & ~n)) return p;
            for (t.sourceIndex += 4, r = e; r; --r) t.dest[t.destLen++] = t.source[t.sourceIndex++];
            return t.bitcount = 0, d
          }
          var d = 0,
            p = -3,
            g = new r,
            _ = new r,
            m = new Uint8Array(30),
            v = new Uint16Array(30),
            y = new Uint8Array(30),
            b = new Uint16Array(30),
            w = new Uint8Array([16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15]),
            x = new r,
            S = new Uint8Array(320),
            C = new Uint16Array(16);
          ! function(t, e) {
            var n;
            for (n = 0; n < 7; ++n) t.table[n] = 0;
            for (t.table[7] = 24, t.table[8] = 152, t.table[9] = 112, n = 0; n < 24; ++n) t.trans[n] = 256 + n;
            for (n = 0; n < 144; ++n) t.trans[24 + n] = n;
            for (n = 0; n < 8; ++n) t.trans[168 + n] = 280 + n;
            for (n = 0; n < 112; ++n) t.trans[176 + n] = 144 + n;
            for (n = 0; n < 5; ++n) e.table[n] = 0;
            for (e.table[5] = 32, n = 0; n < 32; ++n) e.trans[n] = n
          }(g, _), o(m, v, 4, 3), o(y, b, 2, 1), m[28] = 0, v[28] = 258, e.localExports = function(t, e) {
            var n, r, o = new i(t, e);
            do {
              switch (n = s(o), u(o, 2, 0)) {
                case 0:
                  r = f(o);
                  break;
                case 1:
                  r = h(o, g, _);
                  break;
                case 2:
                  l(o, o.ltree, o.dtree), r = h(o, o.ltree, o.dtree);
                  break;
                default:
                  r = p
              }
              if (r !== d) throw new Error("Data error")
            } while (!n);
            return o.destLen < o.dest.length ? "function" == typeof o.dest.slice ? o.dest.slice(0, o.destLen) : o.dest.subarray(0, o.destLen) : o.dest
          }
        }, {}],
        185: [function(t, e, n) {
          e.localExports = {
            categories: ["Cc", "Zs", "Po", "Sc", "Ps", "Pe", "Sm", "Pd", "Nd", "Lu", "Sk", "Pc", "Ll", "So", "Lo", "Pi", "Cf", "No", "Pf", "Lt", "Lm", "Mn", "Me", "Mc", "Nl", "Zl", "Zp", "Cs", "Co"],
            combiningClasses: ["Not_Reordered", "Above", "Above_Right", "Below", "Attached_Above_Right", "Attached_Below", "Overlay", "Iota_Subscript", "Double_Below", "Double_Above", "Below_Right", "Above_Left", "CCC10", "CCC11", "CCC12", "CCC13", "CCC14", "CCC15", "CCC16", "CCC17", "CCC18", "CCC19", "CCC20", "CCC21", "CCC22", "CCC23", "CCC24", "CCC25", "CCC30", "CCC31", "CCC32", "CCC27", "CCC28", "CCC29", "CCC33", "CCC34", "CCC35", "CCC36", "Nukta", "Virama", "CCC84", "CCC91", "CCC103", "CCC107", "CCC118", "CCC122", "CCC129", "CCC130", "CCC132", "Attached_Above", "Below_Left", "Left", "Kana_Voicing", "CCC26", "Right"],
            scripts: ["Common", "Latin", "Bopomofo", "Inherited", "Greek", "Coptic", "Cyrillic", "Armenian", "Hebrew", "Arabic", "Syriac", "Thaana", "Nko", "Samaritan", "Mandaic", "Devanagari", "Bengali", "Gurmukhi", "Gujarati", "Oriya", "Tamil", "Telugu", "Kannada", "Malayalam", "Sinhala", "Thai", "Lao", "Tibetan", "Myanmar", "Georgian", "Hangul", "Ethiopic", "Cherokee", "Canadian_Aboriginal", "Ogham", "Runic", "Tagalog", "Hanunoo", "Buhid", "Tagbanwa", "Khmer", "Mongolian", "Limbu", "Tai_Le", "New_Tai_Lue", "Buginese", "Tai_Tham", "Balinese", "Sundanese", "Batak", "Lepcha", "Ol_Chiki", "Braille", "Glagolitic", "Tifinagh", "Han", "Hiragana", "Katakana", "Yi", "Lisu", "Vai", "Bamum", "Syloti_Nagri", "Phags_Pa", "Saurashtra", "Kayah_Li", "Rejang", "Javanese", "Cham", "Tai_Viet", "Meetei_Mayek", "null", "Linear_B", "Lycian", "Carian", "Old_Italic", "Gothic", "Old_Permic", "Ugaritic", "Old_Persian", "Deseret", "Shavian", "Osmanya", "Elbasan", "Caucasian_Albanian", "Linear_A", "Cypriot", "Imperial_Aramaic", "Palmyrene", "Nabataean", "Hatran", "Phoenician", "Lydian", "Meroitic_Hieroglyphs", "Meroitic_Cursive", "Kharoshthi", "Old_South_Arabian", "Old_North_Arabian", "Manichaean", "Avestan", "Inscriptional_Parthian", "Inscriptional_Pahlavi", "Psalter_Pahlavi", "Old_Turkic", "Old_Hungarian", "Brahmi", "Kaithi", "Sora_Sompeng", "Chakma", "Mahajani", "Sharada", "Khojki", "Multani", "Khudawadi", "Grantha", "Tirhuta", "Siddham", "Modi", "Takri", "Ahom", "Warang_Citi", "Pau_Cin_Hau", "Cuneiform", "Egyptian_Hieroglyphs", "Anatolian_Hieroglyphs", "Mro", "Bassa_Vah", "Pahawh_Hmong", "Miao", "Duployan", "SignWriting", "Mende_Kikakui"],
            eaw: ["N", "Na", "A", "W", "H", "F"]
          }
        }, {}],
        186: [function(t, e, n) {
          (function(e) {
            var r, i, o, a, s, u, c, l, h, f, d, p, g, _, m, v;
            p = t("unicode-trie"), _ = t("./data.json"), v = new p(e("ABEAAAAAAAAAEKiQ5ZlpWFPX2vd73lPrafWIdQBlirUqKggKIqOkWhVxgGJFZIyCiMgQIUCAQNIjRxEZoqIiQogaBgcGAZkhEZFEQIiMAQKJECHGEEIImciwX2x7TrGnp+u9ng/vl2dfXNlJdvZa6/7t//rf91pMDqHfPNNQBgL8sjccSNRdbRc9uWHX5MnvbXfYXDWY/3CyInqFXvTkQJk/Lf3j549vpiQStB13g03WhrM3LLJscN7NU6x2ssNko8yCPlOg8t3zg2vP9u3mOyqelpehLkSEi6dXxAvXyXP/ue/lhrwz71bdPN6781lk/Pu1a7LueJwcHLCxrq2JjfkS65ywd73fimPmEV8d2npe19PWgS33ZXfjRgxDHDed1T6xK3qZs2mYoe9Fof+2P2uKQq/uVw2QP4czqqdwLsxrV+788+Ykn1sbusdp/9HvD8s6UP/Rc1jwug3rN32z8dvCe3kPCu7nc76nP3/18vWLNmpnU2tLR/Ph6QTJl8lXnA62vtqy+dHDoHM8+RuLBRHi2EjIZHcY5fP7UctXfJ1x7cb1H//JJiN40b90SOf9vkNDPqhD8YeVv7b0wzHXnjfdovuBp874nT0d4M99+25sYnSjXDH7Z0P6CB3+e6CzS1OPvDZhC72I2X3RvzVU/I+fIaXmXLxx9e7l2+lau//67UqLJcZ6douNdKy0zJrM7rkc3Hdk76EDzr8wpCXl/uN6ctalW2mExIyU7KTMtzq9Rn8e0HIeKJ5LoHhUL+ZAEvr6jyMuCpnUz/Eetm/4nPLQ4Zuvd3y5Za3Noo2rLf++zQAW98WBT9SFOEIE0SgB0ch8A6LBB9HY+KeC+0jjGJBGEJBGKpDGCSCNQiANBoDGtfcgGquB2rgKpLERSKMcSGM/iEbpYxAN9x4QDeDM18yxIS+2zvfMhWOZyk74D5v5yXL5nzal/gvbVvrWvfoLEJnLQDI/Asnkg8gw+kFkgB4SBdRJHVAnu4E6IYNosL8D0UA+BNGwmpOKBWw3cuCUHBASFRjSSmBIj4AhAW0RCbTFapAtTv/1v7ie5jlSnYCs+rWrPaf//ucRU4KUVts/6Uo5wXb+fUgqL+5V8nUcgTFI7qS/Q3A9qkrFGxWMWLyAr9qviTL0U9oSr+EIOgNkMpuLYPdUlwqZCoZsQsBXIjWS3VJxVOie6ai051aMv8Sjil9IK9VnbxNxQuJztHTA5a3YshCCpnAWeOhsvwbSTAaHGcZ3UOAI1OTbYEO14q7xYgRqlM7wtpLfpWPJE0VNjIZZsaVsKXkik1hqc2ba0kV19qycUByudLLXjJCZaGUYW1BIQZa0yE19mhkSfSYjaq2CIVk9f14xf142f14yf148f/58/vwZk+ENh+7SkQiUVBXsS07oaBSXKiRSy6ROykTr/EVr6K4wDIEaUgWz6hI6js5f5Eotaa8pE9fnL+6A7mrNXyTP31md0DEyU6oYlFouVpZoNDKfbfI9Gq2D8kRyFLw87beHhXvjooobscJ3/UufOAFmIPejvNijUYmwkIR/7oPLjaZaDA//9nzJGWq4If/jw6PkVg+UhLBPU0YEv6ol6SRfS9Ev3vury40lxSv5t8LcexF4y6ptI1Yy3CuXOZmUwMNtJUJpTQ6filErqmi+u1k7lcOw8y/dYd+7YHdXcnc+gydzo9n2zpBE3BR9Zq4OzjQtQsismOrCe0oDg1xchPr4lMxgRJqA1ZVCa8crqW+TFD4r/qvvqE7DsVAE9NF8Rvv3fpwAc824OX3cRBTql8lWh6z7/WyDv9jx9S8NQleEc/Qm9K0XZZk/C5ndYLTgxxCc3dyO/Sh4Hnr/J3PLHZvEiK7/eRRbcEOsUqLnE9b8JFKsnC0lNvEVYXAyUwnNW8NB4u4XMIVFCSyoZcF0YRelfZyaCqOyiRaOsZ4SPZPT7P/rJOY1gipT0u88FtuLVnM/FN6plP48cniGfvN5wscHwWAMCYkX/WPsuRvlXVj2v6csfI4pKx4umVfMLFozN6+Ynt8XrtmVX/8BcE1VKcWF+dFOxExygppRrZbTULgRHFlgBLE0r99G6Tchx9UbAcP3ApVeD4E5ZBfQcL2AhksAGq4vyHCFwKocDarKp+1AKfUhsA7tBNLQAdJ4AqRxFEgDmH5kwPSDAZVe54Da8AXSQAJp4IE03IE0gFW5DFSVTy8GleXngNr4B4iGZh2IBu4piAZ8H4gGDFiVa4Gq8ukkkG+8B2rjayCNg0AaN4E0PIE0SCAa+D4QDf8/DBa6Uyqur/qtJ5ru3wBIwoFIYoBILgKRuIKQEPNASFxAi5PpQtB0eQ+cLif/IFa4tDBeYxE7tbArTlHGtStyWgKD/hTruaGfeOM6zoh2LKbo11K3Fp4BU1rF0X63Cad65LAERHsnkHYdkPb3QNqPALRPAZ06ELgUrADStgFqzxtIIxdIAwGigQCl8VPALK4D3DJIAZpTC5DGCiAN4CqSAlxFIkBp/FQ3iIY7cDvJGKgNNyCN80AaaUAawM01BCiNnwLaEgO41agH1EYakMYmII0KIA3g5hoblMZPdYFoVAN9gwnUhgGQxn/8J+M/aGQCaXgBaYDS+ClgFpcBfUMJ1IYaSOMvIBrQPRAN3EEQDXgRiAawwsMDfcMRqA1/II1wII1/AmkcA9IAbbaeAlb/LkDfCARqowJIwwZIowFIYw+IBgW42QqsN8yAvpEJ1IYxkIYbkMZdIA0fII37IBrAegMJ9I1qoDaYQBoGQBolQBouQBqlIBrAegMG9A0toDYcgTT8gTSSQTSg4yAauAIQDWC9QQT6hhlQG5lAGsZAGpVAGgeANJ6AaADrDTbQN4RAbWgBaTgCaWQAaXiAaEAPQDSA9QYF6BtsoDaEQBpaQBqFQBqHgTSKATTWyQ2bZBsAQHYDwzUB7ieeAIULzwaFSwQmDfrCpNEB9bDUL63jWLF+RikmN9zCnHJ8kFUZR9e3WWQIOmLQmMRF69ctdrX425vvpPeGP3+3ro362aJJ/a1Wf7WpeVfb21WrOBsn2xswdBn1JLGswP7Vi+826QXfTGt8dX9gZnLfq7gvVlp/98WrPYoZRN9hbY8NfNgTTyKCQ+ImEGUKiGymIPeNfEi0TkW+dNWnVXPsutJ8VdudH8DgacQWM7/lxBZEC8LxUa6GtBZPWu0yFtSwVhCjLXxZ35UMuimMfOzbuyJrT9GGXGp2V3qgyLlBj2B9pVl+QL8lPN6OvHLkfYsWZ8OcqEfuoVr/hchD5aaKuintxu3khD8bc7JPsyIZ0McIMVa24cuTRGnWVzny6Hijuq4UGNVpllMoqpDvXzpWIX8i528WFELnqJxzLRkxusgDdrktdqKwyLF1yzh64au88OcdXjxR/A0uiwmjrHbZxHQx4mX3cMbPO0w8WNE3kObZS/oaUwa7JM3VThVjjREr0aftMyfOOMyHSJqtnumL1KGq4YRZKJJZ6Htl37eUApmaEwLPDYGlzug1465vZrpchjI77av+Xso8YDii26rHsktzrS28dYDc5n+MbPHI7jHF4jWMAUmNBjXW2N2mzNcGopD7RodnrLZkhm/brTmThyqw5Dp9k1B+CudR66fH0Zj1IztuJuwaxEZXUYLmznRE7+JxWy/OtH+AexzTxOdmykTvbtjklLLHxd79kFvP0QmKrU90UcWD1yppxaIo7VteJwI9sqJojVNy7Vtrbb235zbbNHPYW3oRDbtx20Jus4ajymNynvS/C3DO9Ige2eZVIVF6zSoak/n9FMQyYQ1l6lB+ZYNF95285gbqu5Oke3fg9erOvWk2+bWRohizqp5ca2FwLDHb+pwkzNfOFnU51nHJTFLdSv4EooDyPD7LjQM70h0QVRCbv1HRYiuoVXcnORmZhiDJ/Y4Kfdu2hO1Hkxgtrp18hcY6/YCCYJFvr1zW/prW9a5uDSzYeSg2+kTVHWeltXOcT3PNZEwZJZZmdcrNLmWOYEAv3+HgZSzYJPD9xsehoBCVYGIYDMfaOpWOFXoxBh9jv2m8GyjbsHuzRBxr3pu1RpCJtS4TiEbOxvXVMQ2rI9ckhrAde9a8y4i7JuzeT6XZyfqtL/snVGwnJibTOKkyTH63HmpCzNJcCK/1U+zXrrQ6z28WSRc7UXRgLSmbIa1WfDVHLV9HthK5NlyZge2fEFO3d9jE2PGUGYIgRLPg9Iibq0ODnbESmR66vHima1FzYf0JRdAe1JjovecaJCw1oNFU0gS75clOwWvOHUcPSGvYE3nFzcW6DmalXlUWctLw13TxyBrHwakD8KFBoT1cyZp850GRaG5IYnBn64e3VqM/0Sxqu+Xani5xcek3+zNQqNbdO8gU7WG7nmDSsEH2hFY7Ge4eNsz+guESnpqBsWIKUmVbL3d1Bu7HDFBlufie0FdxzyoMSZFdUuWlBoXASrvX63Z6p1eQuVCsqcY1+rhwWR9CT7WiOR82w8Y1yYeO+1udd8UfmGzB3kzvpvWP63p/UDvdpaeVJZ7TjtQx/c5KwLqaGnBgjnKt+lV87UZJJ43dUH561qLfKxNlYZmmyYOiprqO+liaxtNMhnKnXBpfVfjY0Nch7SmTNoE88Zt73pErkswetaoc4hwG4VvuIJL2849Nj8WehqYns1DT1JdHRo5SrRocHOnj43scdEgLSDzKQDcPk9x9Mrs7f5gbsVmrR+0cHS8oC4EKis9j4hrWtFNVGdyMhoyLrKKKXV8FHxuGZhUtGu39ZVMPLLPXco6wx7udMUZbXdNGHu7frVumo3R9CMW8f/YMpRLL7R2SETTkvnSD1HaTKyfmDOyyJmGmkWWsEE15HKPysUBRZsI0FGjRoc1Q3il7KIAfcZrgkIC9PxxQFtKQua/2lhh26yE1rPeBYdpAinpzTr0fLBMf6DC0BR5tPgj3DiIP10lK/NyYLZz2ttwOSy4uB33sTf0pUd2RNp1OXJngyUvFGrry6Lse3OyTT0KWNW2USer8J/PYzhN9Wa8rMmYybUqrY36OGWuSmW7zc1N30EiqIr6TkVfDzqqHzLx6UhTtVJsedG1GxcJxHSQknla72NrRYLRSzk6sIRF9magMprrOOdxNDb5jau6F3YUjlPcIFA37x29LKjbjDHS4GPMuO6ZvvOrdC43rqMrsfP0AdTUp/uYn8VqrT3FjlputVxuYiGJuml4Nm2B3WBdSY5My75pVOBP4NcnSQG68dZas14k3ppsDI7KFJTVQvR3bLIoyo77EjyybHH0dU8ClZH/SbE2kPic6vaczfMimpDO0kCKy7HKhqF/Xw7MwcE7t6/isqA/etE0CM2O7NKwDRIs1shCbejZsMuJGnciB/BrHAyZoQ3pZudXYTtzxB7r1rilxO/3MpP4FaU+o69TLzFlNZ14nPovKUpjze2u1OrmYmF3sMlZqeJaYI1YmzreAaWdIZoJPRcdzE4za5r94uM8ymqQtOffSd5LGS4nX0FLkZ64F/iSXnJrC4K4p4/vu3txq5E8SNGe7pmafF5eTd22p7qy5KmpfJFNFdhyI4x6gxS1pM3lq3ZZvr3Dc+LhMr/Kh47dSP7h2an5tUUd+V5s3rIo1HN0kTMCFdCmMd5PzOqZqNAwKPLhAfXZeY6sWwFlz28BjlWCWkeuN7Il005Tf6c8qrX+tEvkpM9MCTiDD6t9qUeDmJQw74/qQBm5CJI0HhzRFTnoZm/Gsa8YkxL9FxjYdNhInRB1Y9tVdxoUfDhqRWXrZPM6R2gzRwiE6TB1Ph4TyNJkxDdqs4cuRHAoe2uFgWGCDZQXuUDefHrpqdGn2zNj0seaTbhMlHY5cPAXxQWW+tTlWc+pGp2JcFpg249JZjUOtJ64koaxHENaXFwnMdvhSJO3sS6I72r74/Cx+dGvZ4JyOMHGUrbPNlk5Z4+hBT+KceWAV6OqrEolFZd4/fqvzAXYbHwEtHNuxqtqXdf4EOCvbLvrYdjZ1ffuQZy/DNi4/xd+3W8agUxua5givK3Hbu4vt6zMv7zjKJd1hd9jar8o/ZhGE3iR0GXjsENL1063LZZuRrpoHce7FOMSwTMRmiFGIfi65BNLpGLz7rlF6hPXNiYG1x9ONf7OQ8LkHEarYBk3QuLw4xoy8lp0GowUtjVD13Im93ahpWNZfvvMxvebvgXymR8iK4g/Z4X/77Hljf04N1ktw/ttlwoPGua/tNegb9LhD+RO97PfHlTgLsn5wr5ehow35UYskzh9XNUc/q0PKrrMiWe36gKtzfkIifnxfHrT0GPpHsYSqbLye5i+b3tiTbb0lxCyifC8yDHMJp4wvHzF53ymAG+JF1XRIdSSounHlAvWcjJYeg6wl+B//4CWzz6zSvOQ+b7fFSJiAHBs96V369lwvzpiDX5AdghXyns7R3d2Ni/1rPJ74MTle7UJcTaRpcbwmvKuuq7MurL0G8m1qzUlR6NAQJnYI3BhF9bS3MzBMKTPDwqvQWhPS7O/Hu9hypoE6mXp+Ka7vV++MMseH3qEG0PqTSTdxB6ssNRtdHdqiViwYfHjWoPAtyRv9ff42/tmw6k0yb6uPf28HFGpGR8KuXpfaTLrT7Fc8v+b33NrwKtV41Nn2jmx9SNB+lAQpGyPgjLML/ECZaY/zrcBSmQ370LyyoMivIEr/GIQLuz8m6tZylCz2KPUwCpdfULvQjVB3iO6l13DlzzdAbM37bimXDq3jwhdklOt1HYf1i4wYU+uIRqTILMuhrGRP5Hq6dmXzNpstqSTr3L0aIrQ5Vycd/+H24coGeP+M5mS70j/3hwe/2ko32UvWR4nPnd0Qt9JoUWOt/3XJWs/VxZ6oA3YJI2ZGxrMnNt9UZlbAU4LxE3Yk+zmKZ+8BXHChbdyL4jRs38/TuaSSO2Sjh6zNmHNNPj95YNuHriuNA/hjRuPVpPp9qLxnbVc3Y4VLcWHdvFLRiEUmYishlb/NVVfN9OFelkyIL4emDgWtxt/6yt8rAv4ue9rB1vMe3LY/YTpd+2Tp6wn+AMWvkVmmrpxfXmQpI7oq3r3gwKha1NiR1ZKqMzyuG69ZXLsUgnfkh0IYt+5MC58AJidjWQ/pVm7x0xrWztt9dnCsnZqdKXJhnrzN0qjcO5Bug8KiC9AHemcwtvFyFCtLH1N4qmdOh7/Nl5HC6AiQMOwgrkSHQIuiwdOpX3R6bTu68wBk2nbSnyqXXdfLR76sdkTq5ndJVVjxkUdPE3JlPfJBD1yFrs6HW1/xaucvB87QmELnVe0OhFRBq7dm6/zqIB6TGWSS5R7kpFk0PkrRZeiqva6QBkJvpge3PnHUhbNS1KezRPICqhhz7MMyvjCvc5aNZz3EBj3rGxkIsfd95DEQVuSKTWDgKwKneMo5VUUMrqmsWyuwg7u6HT0x5CEXJ/JfNr2cZvMNkVZfxXKVhFbNce5eX/9ncGa/PC626nlTFvWiA57eltozKm7LWCKhK5EdqcGawG8Je3FiDrbuu3AsYa+6MFDwjBoLYabSEC8pyfCORnGQCjmoqZDjHniaGkD8PJwxj46ahNIoje62Grp/YIxw9xbJJw4lO6R2dEIky8jHPmixNkvsnBAbfOqxxhIvHb5WI1HgkuFM/JKfEYFIQWyiDK8ZgXok2WmjlDJsV9MibjBmO5eny0wpsiW/H6pzqhAHHanSC04PHilDJHjzg1Z1Uy/6py0K0OnzuUkxQWriPPpZY8HBsbDMysYQZ34JPQBVb4IjSBk+2e3SUv4mJgLd0V+M85p6TFSG9nGdnuzE9mcXWCk8ogZlL2odecqujQkVvDhkhTAUQWdWdYSbXQ0tpRVwGnkDH2JMGzCOv4R0F33pY0TunMpzVORL9hXKWkQ6Xabd0M6ukGqT9Z52T3N3WiV0HhBGyU6FabWpAl9U0SrOxz8ZPo5zN3AsU0VNVMbY61DcrbZZIaR1Yh+ZfZFXYFRtBzHTNFtWbv5khsusS7as/5JpSC/n4wYtd9Srl+ei3elKBwmllcs6igtDBNqjJQki85onsewj8OLuSTqi/xAj4Qt9jeG+ALlyVZmXpwqDKxyXtIeqh8PCutBjkduqZaja89QfOtbczreypJKG/jK9qFLNxr3VnLt1aAnFvyQ7DOPLRPQoK7RlAfrPpaFKNX8Xix12/mhJ1Vmm2qZ9Z1WC71S79QQNdWtH8Y/ckrGzSkF/7u6THBHasOj0a01CUeAltRsOHUxWPYhLOQLpa2rC0HnfNlrx3jStQAV4NaIyGi7JYh55zIZXR8aTEbzhw6Rw99uvC6+xSw3IvaYp2GK9jsPt3p4QnchupaBRHkWPoXKYkMsaKVBQvkXn7tLRz2b7jncsYoolhcri7nH49569WHRH+BElDuWERXXTR2il5xejx0yVc+vrS7kw8sGprYa3lu0yhyFMLW+wTdakU7Jnn6qeeEKTQnKRc2FC0VbZ2dOY40gj9QJDTuLIE6Vah3BvFNuDzBxM1K0MqOVVTePU/yGJcjT7i6H5dJsZ5G9eWhHrjMOKHmJfbbtDhemrT71z5RHK4tBjFyI15Ls3mVbkqfrBXE9V+qxLBH4Y3TIgpdzX7HeTawZjj9uWzJdy/gIhKdTHwl2taQjNpkbBV2ryIp5aJiv4Bf+q8Yz1vF2sQuSBx5NwhTyJulvkJfKNtyDrwVkwPJ/dvgaKtk/HwTWJcjsRjIpbBYU3aM7zam7NpskPqc6q+qcdBifmzGqQQuZcKafaggpt0ITZJ1eX90NB3ezkUiobj1sObc+lwq9CJiyM5IsJSd9cs6VGNaCx9j0re6v5KR/rZvf5Z//l+MmCT2vSGaqir/xvv/ifHs+PellpnNTT2pHIRz4fv5h6MUnHL/P15O8z4odTojLPpevnL30eV9dlUnMCM06K+2RVFX6CW0BCxGkTFt8xvD1X7NsN095Ji+Wvub0tqnr71NTAGf2RnB9NB3j9yoUrJsdHSjTGv1ZHx8NAOPxgSpca4FXr4FnrsMvFzr1IVp43Uht18ozriGSIotI3YIkU1lZpClUKh+2byij0snZM0pnl/9j4IlpFpAfKdG7VhhutbEwelTal1srzjttqrLEGhaL5VV7E+1Njog0++omzyoigzpNr0zKSmzNftkripfGN2U+69Ldm6goIC8v8Gb4wjdWxGWbaZWJtf9jINSxA9UlWNQ7GVMfpGOQYjyvKbqHeOA8ye5jPnR6pB/H99dGbujrlUF5EDPdqV+sAnfBytFnSw82wyXnd8cQqExdhyi3KED99FB7ZThiGL2hVaFIDFQr0x+O9e1OmbvhbtDneaqCcmNF4Brn/u/wyKYKnkoqYaobX2Bk/kcY2vIwYRY2IkCpiP12ZkV4o4Lq54gd93JhL7SUZiRdHNa3vhmxtjHtC7S+4xHF5b/YgpQMZrJm4lrq0bGiwMz5Hxk6bOAhX8tvFF0ooZRnJEy+nY9DrFXwH2oYvapdp1z7iuPMwnrg+UZ8wV0aTG1TZcf1qSUdZek8MSk5XKIVVR4U81g3BhKrRZ4qrXKgM9WEWvEqk+vL/XYnT5gM75x6hvKbKS+vNYYlUs39pf/FUBfW/3lXzANG3LHbNY+N0oqalpaFt9xqZz2ZkmXDOO469rc455lRBmSD86aDiLv7Eo4regh0HCbFLSDmieOFiTuQ2F6vNXtsFwR5YkbxcsNpzeKXQu/3oSynjs5/cDuUbNbuEEBUMX1omxmAKE+JQSAu/cAK611t/2zF/YqOn6MyzRhPbYlYEYXyuuCwcjnRusNshkD3mtYjocdVv7XFrdNrJtQfg37sYBRBzbZBC0RHYk06or2QJeXMn59ws24xbZ/u7LUyzzCG7hZLbi3FZMl8Q1MxOOqdyu3Necwkx6JsazjGuc6oSZ8uDeINDuxwpnjtGwm7n9msxuk2iGKYY4lLa7tmKttH+Vf5uWdn2vqkitTQmYvfS0tbEiobTOyuIT053Nr2aCz9+4Yfzq/hTBmETW6NKPhQot1ahR6pK67BWbsSkwNM7l5z1K/zFO/81P4JqI+eXP+QfbbHGrBQkFc2hhesm6rv404rPORqlP9BTj4pirpuP5yFfMkXY+OXsFPxlGqfn5qDT3C35iNwz3ljjoToTYz9RcOFpm5FjvoZWtrIyRuiVjzVi4UsTgnPQc03WuOkm+UCKFpWWaaCcTQwfGS0jkFC3bHrmxL5Qf03Hg9PK4taBidU0C5Nshb5Wgi4lPf6Dobe7jSDePDS42TLXQk+HiTlXxlNVI+Ua27QTDjpTnjuGBnHvS1ba6KAVNpgyKBWXEm2LoVVaJ+CE8sZSgY8++7H1ITtG5Fxxo+axeeLprP1dHSIMTygxPblKfXPkcji7o3sdU9YaX1TSM7x2UmIcPudXCsUu9TWpPaFN1VRgSlCllVIt2DPp7SMPhllI4b7f1qvyYDU/tvn9GRPZ4HwnWZmtm8Kf4UYJ4Zz3BS4/ZXbsgkNhH8SyKhLRQXuLIaVoOMFNX6yKT2EmepmnNmFRgU9x3snnc8gDcI39F6L1DmnHeIeTqt+fOlc4m8/5eYUW7qnpFnFdz+cPVxVdIZygGvQNEB628PTWYpaODTFwdpyaLS1S7Y5CgojGY67FLX3Q6zTo9bTHCS4sJK7Zt1HZ1zkcF0XuNTHIV/mcXOXIo2T7M3spASgRO2G+C7zSRiuDYzf4iQw+xBuWQu8O05AtGFGHfMqlk85dRzs8iNxvZxu+auQ9bZ1v3hEbnp4ougEGeykbI42K5DsDom9gN2KtyNrsqzht+FpDPKLgyEYekipsYXC0OEQaTAtPg66HQ/VyaOzwFgg9hh6jXIG2arLlhc07tMqXJZpJOlM3/TiQi+8qw9lugewandQojnm7DMm8JFpGk8PxtPjRAQqGbPHK84BlNGd2f2fU0rGWFEsH9he1SSdqdxoasCoJ3SSOLNowIAx/N//EzNSAM9+V/L3huN0G/3NGwojFwTdrTyMEt4ZwF0bjBoPgbTJHqiaaaphY7chBmlY6R3az289Fp3fkpx+T7jpCH+wi/fwEnOGvalP2NFw5ZhWAbLs4wCuA5h05B2umnuew7xExzmq0/H0gIWVXKgE7sbxvIK0Hb560Jn72/Rwdl5hKaB853zAzOR6er0D7Grb7F84eYtkhWjFcY8UUbjzm2uz+yWdtsTRjrkFpjqw+giVso/1aruiNx7tn4hHQIcUnmxENN5+tFrx/6RpJgtsbwgqLXcZcOD1r/l4kaXOa3cQbPfwQbYkT2QehHinEzLiNXNGtHJp7hCGqhPTL3l4C55cvEK2xr6OWs1OFVDxn5xc2mvVtxe5DQRWEXcz/eGmk/r3K/jIqJLDEf37p/Blh1ezEkZkksQpxGRXqrL+6ilaiS0gdrfJZMe5ckrEg3aJNa53TNVih91wdIm5JjkkrPod7f7ROP8Bn4Y74I0bO/DLdohPzLSPGCrXGS1ibT4zSs0tuXjyVd6/68k1lCmzbucJY135pA2sw6tgU1zZlwcbFqiFCKGVn/K6H+u6/lZycZ942Gntf9iN9ymphixWnXsSxTtuTTrmSVsLeQ0WtDCsvbp+P4quvYm0KE3NKw7Go+xUxkgu1PNH+8RN9PgGkuXZ4pqeN5sK4Db8v4yLLD9pK98Mp4rtm24vdxTmz53MzfDtQ3U9ineMs6U6lEza8PnujxrvcvJ8vYnhzlT2agdZX1sLpY9woHSH7mVsoHT/evSNwGy12vpJ5IVXopjI9GtiadljH61jFUK5JK2Invpas2YN8lFV1Qh+xmjCrfjo/wtvWW/JS2gLtZO5GDGpsfYdr3fo2wjBuYXXhHQEZ5OOT+Hn3rDjxWKPDbQF2wdiblvA2T3auYgc9vTS7IUkwD3JvmXd3ERRT7/G0i65sG/GGFjbiG6GW9bCbrweyi5ixtiO+69hfq3GV03aYs+o5D8qCRyoz86DwqevEdUsqEqRfOW+KWzLDnTF1+OutxZ/8jMZLccfD8c96TKw33/LFVAUQQrdm+gYvtE24c3vpuJpf2YBrvC0rZcxoJJ4sim+7khEcC8VtEyJKfUZlfr7tFtM6zwO6OsM/1gFbDj/oxhYj/l2AGKdva2cnuwlMt1qMIKp9y4Y7hRvVjeO0FOX+HqneJWxBwuptd+kq/QLaVVTWbUWPfKemn8llwvEuYwiX7vv4JQHsuRHGnFA9NVN5R6W6F9u0qUzAXzGVUZ/uPPexUK8pDVuf3r3ss8/80V+PzH3z2fPD3G4u0T4w9HCQXFaI+DQe7dR6m3LB+0BD5oV+CBqqP5cYtTaveLEAJr3dbusdub3QLtD7bMdmrQj1gd/uwm0nY10QDdH2V1w49DE6p0JO8T2imZoOLaKHEsXBjuJrsXql7NbmSEFwoVVhfVnphFLUdVX4ipl6ohOm1XyUQDnKZ7+UoHw16+Ly++kPbOKdre+iGOGfNUT2p4XiUQSbEIw+evL9mbweISHLhgXpBAac9ZabZvXxZk0tQyk9H3x2uk+UdOAD+dz3ziO++vkJ6xm9WV6+4sEBaaXE3GutXX53+CdPLZ9D50gIvy2e0ntOFpZuFE2mR069SrjjwtuYTT8at8uDGHhJ0H1RsF/ZojrK/fHu4UyPqPiueN8qcUVI2uHDM1a74fmYncR2KiJVuYuYKYizgIl3wMRZd6k+rwU8gw5eOfZ1j32HGEtH3Ul/4L21UjzFKtnHGmHGopHckUYCWhb97cwUq7MeoyRnGldmL/7suY6zcKO0vDOKgKqbUlCKwsQX+S8f1Jq0IxhRpB77z7/aVNYTZLjAJUi9NpPbKp2ftSVZaI+PFPjhegRjA7vW0gPEWUhMl61Ju9fNMFtN1JDXcVwGqiKMkO3JfJIr3M9veExkTkK2XVvhBrVx+vbbtRJUZvVHOZvm6sL0mEWUPvEPYTfTk6IXeBzcxF03O+jedXLVaVtaqIRCUPjalzINGWdRAxumJhxij+O7B9z8PGXf1HyQM7KgPn8mMeP5SEzgP0LxX/7EdKtb7B+TRf1yeyShJgzHMGivYqRnVwaFYBrMSEfH6kKRmBKmbzu/qkKgGOlTCeO80asZBvwqbtVIpcpNsPx/vnD8/3jsKncOwaT+7svn7UEZA9KToymv1Iv/8K4L9VWrmblWWkOa3Wv++pnWqxD9UE5X4RsrZsQPH/6i1RvF+ZNVxf+K49QZXabhH7P733JcwJkkQ7D/Cw==", "base64")), m = Math.log2 || function(t) {
              return Math.log(t) / Math.LN2
            }, r = (g = function(t) {
              return m(t) + 1 | 0
            })(_.categories.length - 1), a = g(_.combiningClasses.length - 1), h = g(_.scripts.length - 1), c = g(_.eaw.length - 1), o = a + h + c + 10, u = h + c + 10, d = c + 10, i = (1 << r) - 1, s = (1 << a) - 1, f = (1 << h) - 1, l = (1 << c) - 1, n.getCategory = function(t) {
              var e;
              return e = v.get(t), _.categories[e >> o & i]
            }, n.getCombiningClass = function(t) {
              var e;
              return e = v.get(t), _.combiningClasses[e >> u & s]
            }, n.getScript = function(t) {
              var e;
              return e = v.get(t), _.scripts[e >> d & f]
            }, n.getEastAsianWidth = function(t) {
              var e;
              return e = v.get(t), _.eaw[e >> 10 & l]
            }, n.getNumericValue = function(t) {
              var e, n, r, i, o;
              if (o = v.get(t), 0 === (r = 1023 & o)) return null;
              if (r <= 50) return r - 1;
              if (r < 480) return i = (r >> 4) - 12, e = 1 + (15 & r), i / e;
              if (r < 768) {
                for (o = (r >> 5) - 14, n = 2 + (31 & r); n > 0;) o *= 10, n--;
                return o
              }
              for (o = (r >> 2) - 191, n = 1 + (3 & r); n > 0;) o *= 60, n--;
              return o
            }, n.isAlphabetic = function(t) {
              var e;
              return "Lu" === (e = n.getCategory(t)) || "Ll" === e || "Lt" === e || "Lm" === e || "Lo" === e || "Nl" === e
            }, n.isDigit = function(t) {
              return "Nd" === n.getCategory(t)
            }, n.isPunctuation = function(t) {
              var e;
              return "Pc" === (e = n.getCategory(t)) || "Pd" === e || "Pe" === e || "Pf" === e || "Pi" === e || "Po" === e || "Ps" === e
            }, n.isLowerCase = function(t) {
              return "Ll" === n.getCategory(t)
            }, n.isUpperCase = function(t) {
              return "Lu" === n.getCategory(t)
            }, n.isTitleCase = function(t) {
              return "Lt" === n.getCategory(t)
            }, n.isWhiteSpace = function(t) {
              var e;
              return "Zs" === (e = n.getCategory(t)) || "Zl" === e || "Zp" === e
            }, n.isBaseForm = function(t) {
              var e;
              return "Nd" === (e = n.getCategory(t)) || "No" === e || "Nl" === e || "Lu" === e || "Ll" === e || "Lt" === e || "Lm" === e || "Lo" === e || "Me" === e || "Mc" === e
            }, n.isMark = function(t) {
              var e;
              return "Mn" === (e = n.getCategory(t)) || "Me" === e || "Mc" === e
            }
          }).call(this, t("buffer").Buffer)
        }, {
          "./data.json": 185,
          buffer: 31,
          "unicode-trie": 187
        }],
        187: [function(t, e, n) {
          var r, i;
          i = t("tiny-inflate"), r = function() {
            function t(t) {
              var e, n, r;
              (e = "function" == typeof t.readUInt32BE && "function" == typeof t.slice) || t instanceof Uint8Array ? (e ? (this.highStart = t.readUInt32BE(0), this.errorValue = t.readUInt32BE(4), n = t.readUInt32BE(8), t = t.slice(12)) : (r = new DataView(t.buffer), this.highStart = r.getUint32(0), this.errorValue = r.getUint32(4), n = r.getUint32(8), t = t.subarray(12)), t = i(t, new Uint8Array(n)), t = i(t, new Uint8Array(n)), this.data = new Uint32Array(t.buffer)) : (this.data = t.data, this.highStart = t.highStart, this.errorValue = t.errorValue)
            }
            return 11, 5, 6, 32, 64, 63, 2, 32, 31, 2048, 32, 2080, 2080, 32, 2112, 4, t.prototype.get = function(t) {
              var e;
              return t < 0 || t > 1114111 ? this.errorValue : t < 55296 || t > 56319 && t <= 65535 ? (e = (this.data[t >> 5] << 2) + (31 & t), this.data[e]) : t <= 65535 ? (e = (this.data[2048 + (t - 55296 >> 5)] << 2) + (31 & t), this.data[e]) : t < this.highStart ? (e = this.data[2080 + (t >> 11)], e = this.data[e + (t >> 5 & 63)], e = (e << 2) + (31 & t), this.data[e]) : this.data[this.data.length - 4]
            }, t
          }(), e.localExports = r
        }, {
          "tiny-inflate": 184
        }],
        188: [function(t, e, n) {
          n.read = function(t, e, n, r, i) {
            var o, a, s = 8 * i - r - 1,
              u = (1 << s) - 1,
              c = u >> 1,
              l = -7,
              h = n ? i - 1 : 0,
              f = n ? -1 : 1,
              d = t[e + h];
            for (h += f, o = d & (1 << -l) - 1, d >>= -l, l += s; l > 0; o = 256 * o + t[e + h], h += f, l -= 8);
            for (a = o & (1 << -l) - 1, o >>= -l, l += r; l > 0; a = 256 * a + t[e + h], h += f, l -= 8);
            if (0 === o) o = 1 - c;
            else {
              if (o === u) return a ? NaN : 1 / 0 * (d ? -1 : 1);
              a += Math.pow(2, r), o -= c
            }
            return (d ? -1 : 1) * a * Math.pow(2, o - r)
          }, n.write = function(t, e, n, r, i, o) {
            var a, s, u, c = 8 * o - i - 1,
              l = (1 << c) - 1,
              h = l >> 1,
              f = 23 === i ? Math.pow(2, -24) - Math.pow(2, -77) : 0,
              d = r ? 0 : o - 1,
              p = r ? 1 : -1,
              g = e < 0 || 0 === e && 1 / e < 0 ? 1 : 0;
            for (e = Math.abs(e), isNaN(e) || e === 1 / 0 ? (s = isNaN(e) ? 1 : 0, a = l) : (a = Math.floor(Math.log(e) / Math.LN2), e * (u = Math.pow(2, -a)) < 1 && (a--, u *= 2), (e += a + h >= 1 ? f / u : f * Math.pow(2, 1 - h)) * u >= 2 && (a++, u /= 2), a + h >= l ? (s = 0, a = l) : a + h >= 1 ? (s = (e * u - 1) * Math.pow(2, i), a += h) : (s = e * Math.pow(2, h - 1) * Math.pow(2, i), a = 0)); i >= 8; t[n + d] = 255 & s, d += p, s /= 256, i -= 8);
            for (a = a << i | s, c += i; c > 0; t[n + d] = 255 & a, d += p, a /= 256, c -= 8);
            t[n + d - p] |= 128 * g
          }
        }, {}],
        189: [function(t, e, n) {
          e.localExports = t(23)
        }, {}],
        190: [function(t, e, n) {
          (function() {
            n.OP = 0, n.CL = 1, n.CP = 2, n.QU = 3, n.GL = 4, n.NS = 5, n.EX = 6, n.SY = 7, n.IS = 8, n.PR = 9, n.PO = 10, n.NU = 11, n.AL = 12, n.HL = 13, n.ID = 14, n.IN = 15, n.HY = 16, n.BA = 17, n.BB = 18, n.B2 = 19, n.ZW = 20, n.CM = 21, n.WJ = 22, n.H2 = 23, n.H3 = 24, n.JL = 25, n.JV = 26, n.JT = 27, n.RI = 28, n.AI = 29, n.BK = 30, n.CB = 31, n.CJ = 32, n.CR = 33, n.LF = 34, n.NL = 35, n.SA = 36, n.SG = 37, n.SP = 38, n.XX = 39
          }).call(this)
        }, {}],
        191: [function(t, e, n) {
          (function() {
            var n, r, i, o, a, s, u, c, l, h, f, d, p, g, _, m, v, y, b, w, x, S, C, k, E, A, P;
            b = t("unicode-trie"), S = t("base64-js"), A = t("./classes"), o = A.BK, l = A.CR, d = A.LF, g = A.NL, a = A.CB, i = A.BA, y = A.SP, w = A.WJ, y = A.SP, o = A.BK, d = A.LF, g = A.NL, n = A.AI, r = A.AL, m = A.SA, v = A.SG, x = A.XX, u = A.CJ, A.ID, _ = A.NS, A.characterClasses, P = t("./pairs"), h = P.DI_BRK, f = P.IN_BRK, s = P.CI_BRK, c = P.CP_BRK, P.PR_BRK, E = P.pairTable, k = S.toByteArray("AA4IAAAAAAAAAhqg5VV7NJtZvz7fTC8zU5deplUlMrQoWqmqahD5So0aipYWrUhVFSVBQ10iSTtUtW6nKDVF6k7d75eQfEUbFcQ9KiFS90tQEolcP23nrLPmO+esr/+f39rr/a293t/e7/P8nmfvlz0O6RvrBJADtbBNaD88IOKTOmOrCqhu9zE770vc1pBV/xL5dxj2V7Zj4FGSomFKStCWNlV7hG1VabZfZ1LaHbFrRwzzLjzPoi1UHDnlV/lWbhgIIJvLBp/pu7AHEdRnIY+ROdXxg4fNpMdTxVnnm08OjozejAVsBqwqz8kddGRlRxsd8c55dNZoPuex6a7Dt6L0NNb03sqgTlR2/OT7eTt0Y0WnpUXxLsp5SMANc4DsmX4zJUBQvznwexm9tsMH+C9uRYMPOd96ZHB29NZjCIM2nfO7tsmQveX3l2r7ft0N4/SRJ7kO6Y8ZCaeuUQ4gMTZ67cp7TgxvlNDsPgOBdZi2YTam5Q7m3+00l+XG7PrDe6YoPmHgK+yLih7fAR16ZFCeD9WvOVt+gfNW/KT5/M6rb/9KERt+N1lad5RneVjzxXHsLofuU+TvrEsr3+26sVz5WJh6L/svoPK3qepFH9bysDljWtD1F7KrxzW1i9r+e/NLxV/acts7zuo304J9+t3Pd6Y6u8f3EAqxNRgv5DZjaI3unyvkvHPya/v3mWVYOC38qBq11+yHZ2bAyP1HbkV92vdno7r2lxz9UwCdCJVfd14NLcpO2CadHS/XPJ9doXgz5vLv/1OBVS3gX0D9n6LiNIDfpilO9RsLgZ2W/wIy8W/Rh93jfoz4qmRV2xElv6p2lRXQdO6/Cv8f5nGn3u0wLXjhnvClabL1o+7yvIpvLfT/xsKG30y/sTvq30ia9Czxp9dr9v/e7Yn/O0QJXxxBOJmceP/DBFa1q1v6oudn/e6qc/37dUoNvnYL4plQ9OoneYOh/r8fOFm7yl7FETHY9dXd5K2n/qEc53dOEe1TTJcvCfp1dpTC334l0vyaFL6mttNEbFjzO+ZV2mLk0qc3BrxJ4d9gweMmjRorxb7vic0rSq6D4wzAyFWas1TqPE0sLI8XLAryC8tPChaN3ALEZSWmtB34SyZcxXYn/E4Tg0LeMIPhgPKD9zyHGMxxhxnDDih7eI86xECTM8zodUCdgffUmRh4rQ8zyA6ow/Aei+01a8OMfziQQ+GAEkhwN/cqUFYAVzA9ex4n6jgtsiMvXf5BtXxEU4hSphvx3v8+9au8eEekEEpkrkne/zB1M+HAPuXIz3paxKlfe8aDMfGWAX6Md6PuuAdKHFVH++Ed5LEji94Z5zeiJIxbmWeN7rr1/ZcaBl5/nimdHsHgIH/ssyLUXZ4fDQ46HnBb+hQqG8yNiKRrXL/b1IPYDUsu3dFKtRMcjqlRvONd4xBvOufx2cUHuk8pmG1D7PyOQmUmluisVFS9OWS8fPIe8LiCtjwJKnEC9hrS9uKmISI3Wa5+vdXUG9dtyfr7g/oJv2wbzeZU838G6mEvntUb3SVV/fBZ6H/sL+lElzeRrHy2Xbe7UWX1q5sgOQ81rv+2baej4fP4m5Mf/GkoxfDtT3++KP7do9Jn26aa6xAhCf5L9RZVfkWKCcjI1eYbm2plvTEqkDxKC402bGzXCYaGnuALHabBT1dFLuOSB7RorOPEhZah1NjZIgR/UFGfK3p1ElYnevOMBDLURdpIjrI+qZk4sffGbRFiXuEmdFjiAODlQCJvIaB1rW61Ljg3y4eS4LAcSgDxxZQs0DYa15wA032Z+lGUfpoyOrFo3mg1sRQtN/fHHCx3TrM8eTrldMbYisDLXbUDoXMLejSq0fUNuO1muX0gEa8vgyegkqiqqbC3W0S4cC9Kmt8MuS/hFO7Xei3f8rSvIjeveMM7kxjUixOrl6gJshe4JU7PhOHpfrRYvu7yoAZKa3Buyk2J+K5W+nNTz1nhJDhRUfDJLiUXxjxXCJeeaOe/r7HlBP/uURc/5efaZEPxr55Qj39rfTLkugUGyMrwo7HAglfEjDriehF1jXtwJkPoiYkYQ5aoXSA7qbCBGKq5hwtu2VkpI9xVDop/1xrC52eiIvCoPWx4lLl40jm9upvycVPfpaH9/o2D4xKXpeNjE2HPQRS+3RFaYTc4Txw7Dvq5X6JBRwzs9mvoB49BK6b+XgsZVJYiInTlSXZ+62FT18mkFVcPKCJsoF5ahb19WheZLUYsSwdrrVM3aQ2XE6SzU2xHDS6iWkodk5AF6F8WUNmmushi8aVpMPwiIfEiQWo3CApONDRjrhDiVnkaFsaP5rjIJkmsN6V26li5LNM3JxGSyKgomknTyyrhcnwv9Qcqaq5utAh44W30SWo8Q0XHKR0glPF4fWst1FUCnk2woFq3iy9fAbzcjJ8fvSjgKVOfn14RDqyQuIgaGJZuswTywdCFSa89SakMf6fe+9KaQMYQlKxiJBczuPSho4wmBjdA+ag6QUOr2GdpcbSl51Ay6khhBt5UXdrnxc7ZGMxCvz96A4oLocxh2+px+1zkyLacCGrxnPzTRSgrLKpStFpH5ppKWm7PgMKZtwgytKLOjbGCOQLTm+KOowqa1sdut9raj1CZFkZD0jbaKNLpJUarSH5Qknx1YiOxdA5L6d5sfI/unmkSF65Ic/AvtXt98Pnrdwl5vgppQ3dYzWFwknZsy6xh2llmLxpegF8ayLwniknlXRHiF4hzzrgB8jQ4wdIqcaHCEAxyJwCeGkXPBZYSrrGa4vMwZvNN9aK0F4JBOK9mQ8g8EjEbIQVwvfS2D8GuCYsdqwqSWbQrfWdTRUJMqmpnWPax4Z7E137I6brHbvjpPlfNZpF1d7PP7HB/MPHcHVKTMhLO4f3CZcaccZEOiS2DpKiQB5KXDJ+Ospcz4qTRCRxgrKEQIgUkKLTKKwskdx2DWo3bg3PEoB5h2nA24olwfKSR+QR6TAvEDi/0czhUT59RZmO1MGeKGeEfuOSPWfL+XKmhqpZmOVR9mJVNDPKOS49Lq+Um10YsBybzDMtemlPCOJEtE8zaXhsaqEs9bngSJGhlOTTMlCXly9Qv5cRN3PVLK7zoMptutf7ihutrQ/Xj7VqeCdUwleTTKklOI8Wep9h7fCY0kVtDtIWKnubWAvbNZtsRRqOYl802vebPEkZRSZc6wXOfPtpPtN5HI63EUFfsy7U/TLr8NkIzaY3vx4A28x765XZMzRZTpMk81YIMuwJ5+/zoCuZj1wGnaHObxa5rpKZj4WhT670maRw04w0e3cZW74Z0aZe2n05hjZaxm6urenz8Ef5O6Yu1J2aqYAlqsCXs5ZB5o1JJ5l3xkTVr8rJQ09NLsBqRRDT2IIjOPmcJa6xQ1R5yGP9jAsj23xYDTezdyqG8YWZ7vJBIWK56K+iDgcHimiQOTIasNSua1fOBxsKMMEKd15jxTl+3CyvGCR+UyRwuSI2XuwRIPoNNclPihfJhaq2mKkNijwYLY6feqohktukmI3KDvOpN7ItCqHHhNuKlxMfBAEO5LjW2RKh6lE5Hd1dtAOopac/Z4FdsNsjMhXz/ug8JGmbVJTA+VOBJXdrYyJcIn5+OEeoK8kWEWF+wdG8ZtZHKSquWDtDVyhFPkRVqguKFkLkKCz46hcU1SUY9oJ2Sk+dmq0kglqk4kqKT1CV9JDELPjK1WsWGkEXF87g9P98e5ff0mIupm/w6vc3kCeq04X5bgJQlcMFRjlFWmSk+kssXCAVikfeAlMuzpUvCSdXiG+dc6KrIiLxxhbEVuKf7vW7KmDQI95bZe3H9mN3/77F6fZ2Yx/F9yClllj8gXpLWLpd5+v90iOaFa9sd7Pvx0lNa1o1+bkiZ69wCiC2x9UIb6/boBCuNMB/HYR0RC6+FD9Oe5qrgQl6JbXtkaYn0wkdNhROLqyhv6cKvyMj1Fvs2o3OOKoMYTubGENLfY5F6H9d8wX1cnINsvz+wZFQu3zhWVlwJvwBEp69Dqu/ZnkBf3nIfbx4TK7zOVJH5sGJX+IMwkn1vVBn38GbpTg9bJnMcTOb5F6Ci5gOn9Fcy6Qzcu+FL6mYJJ+f2ZZJGda1VqruZ0JRXItp8X0aTjIcJgzdaXlha7q7kV4ebrMsunfsRyRa9qYuryBHA0hc1KVsKdE+oI0ljLmSAyMze8lWmc5/lQ18slyTVC/vADTc+SNM5++gztTBLz4m0aVUKcfgOEExuKVomJ7XQDZuziMDjG6JP9tgR7JXZTeo9RGetW/Xm9/TgPJpTgHACPOGvmy2mDm9fl09WeMm9sQUAXP3Su2uApeCwJVT5iWCXDgmcuTsFgU9Nm6/PusJzSbDQIMfl6INY/OAEvZRN54BSSXUClM51im6Wn9VhVamKJmzOaFJErgJcs0etFZ40LIF3EPkjFTjGmAhsd174NnOwJW8TdJ1Dja+E6Wa6FVS22Haj1DDA474EesoMP5nbspAPJLWJ8rYcP1DwCslhnn+gTFm+sS9wY+U6SogAa9tiwpoxuaFeqm2OK+uozR6SfiLCOPz36LiDlzXr6UWd7BpY6mlrNANkTOeme5EgnnAkQRTGo9T6iYxbUKfGJcI9B+ub2PcyUOgpwXbOf3bHFWtygD7FYbRhb+vkzi87dB0JeXl/vBpBUz93VtqZi7AL7C1VowTF+tGmyurw7DBcktc+UMY0E10Jw4URojf8NdaNpN6E1q4+Oz+4YePtMLy8FPRP"), C = new b(k), p = function() {
              function t(t) {
                this.string = t, this.pos = 0, this.lastPos = 0, this.curClass = null, this.nextClass = null
              }
              var e, p, b;
              return t.prototype.nextCodePoint = function() {
                var t, e;
                return t = this.string.charCodeAt(this.pos++), e = this.string.charCodeAt(this.pos), 55296 <= t && t <= 56319 && 56320 <= e && e <= 57343 ? (this.pos++, 1024 * (t - 55296) + (e - 56320) + 65536) : t
              }, p = function(t) {
                switch (t) {
                  case n:
                    return r;
                  case m:
                  case v:
                  case x:
                    return r;
                  case u:
                    return _;
                  default:
                    return t
                }
              }, b = function(t) {
                switch (t) {
                  case d:
                  case g:
                    return o;
                  case a:
                    return i;
                  case y:
                    return w;
                  default:
                    return t
                }
              }, t.prototype.nextCharClass = function(t) {
                return null == t && (t = !1), p(C.get(this.nextCodePoint()))
              }, e = function() {
                return function(t, e) {
                  this.position = t, this.required = null != e && e
                }
              }(), t.prototype.nextBreak = function() {
                var t, n, r;
                for (null == this.curClass && (this.curClass = b(this.nextCharClass())); this.pos < this.string.length;) {
                  if (this.lastPos = this.pos, n = this.nextClass, this.nextClass = this.nextCharClass(), this.curClass === o || this.curClass === l && this.nextClass !== d) return this.curClass = b(p(this.nextClass)), new e(this.lastPos, !0);
                  if (null == (t = function() {
                    switch (this.nextClass) {
                      case y:
                        return this.curClass;
                      case o:
                      case d:
                      case g:
                        return o;
                      case l:
                        return l;
                      case a:
                        return i
                    }
                  }.call(this))) {
                    switch (r = !1, E[this.curClass][this.nextClass]) {
                      case h:
                        r = !0;
                        break;
                      case f:
                        r = n === y;
                        break;
                      case s:
                        if (!(r = n === y)) continue;
                        break;
                      case c:
                        if (n !== y) continue
                    }
                    if (this.curClass = this.nextClass, r) return new e(this.lastPos)
                  } else if (this.curClass = t, this.nextClass === a) return new e(this.lastPos)
                }
                if (this.pos >= this.string.length) return this.lastPos < this.string.length ? (this.lastPos = this.string.length, new e(this.string.length)) : null
              }, t
            }(), e.localExports = p
          }).call(this)
        }, {
          "./classes": 190,
          "./pairs": 192,
          "base64-js": 26,
          "unicode-trie": 214
        }],
        192: [function(t, e, n) {
          (function() {
            n.DI_BRK = 0, n.IN_BRK = 1, n.CI_BRK = 2, n.CP_BRK = 3, n.PR_BRK = 4, n.pairTable = [
              [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 4, 4, 4, 4, 4, 4, 4],
              [0, 4, 4, 1, 1, 4, 4, 4, 4, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [0, 4, 4, 1, 1, 4, 4, 4, 4, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [4, 4, 4, 1, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 2, 4, 1, 1, 1, 1, 1, 1],
              [1, 4, 4, 1, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 2, 4, 1, 1, 1, 1, 1, 1],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [1, 4, 4, 1, 1, 1, 4, 4, 4, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 4, 2, 4, 1, 1, 1, 1, 1, 0],
              [1, 4, 4, 1, 1, 1, 4, 4, 4, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [1, 4, 4, 1, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [1, 4, 4, 1, 1, 1, 4, 4, 4, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [1, 4, 4, 1, 1, 1, 4, 4, 4, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [0, 4, 4, 1, 0, 1, 4, 4, 4, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [0, 4, 4, 1, 0, 1, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [1, 4, 4, 1, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 2, 4, 1, 1, 1, 1, 1, 1],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 4, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0],
              [1, 4, 4, 1, 1, 1, 4, 4, 4, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 0],
              [1, 4, 4, 1, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 2, 4, 1, 1, 1, 1, 1, 1],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 1, 1, 0],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 1, 0],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 4, 2, 4, 1, 1, 1, 1, 0, 0],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 1, 1, 0],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 1, 0],
              [0, 4, 4, 1, 1, 1, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 4, 2, 4, 0, 0, 0, 0, 0, 1]
            ]
          }).call(this)
        }, {}],
        193: [function(t, e, n) {
          "use strict";
          var r = "undefined" != typeof Uint8Array && "undefined" != typeof Uint16Array && "undefined" != typeof Int32Array;
          n.assign = function(t) {
            for (var e = Array.prototype.slice.call(arguments, 1); e.length;) {
              var n = e.shift();
              if (n) {
                if ("object" != typeof n) throw new TypeError(n + "must be non-object");
                for (var r in n) n.hasOwnProperty(r) && (t[r] = n[r])
              }
            }
            return t
          }, n.shrinkBuf = function(t, e) {
            return t.length === e ? t : t.subarray ? t.subarray(0, e) : (t.length = e, t)
          };
          var i = {
              arraySet: function(t, e, n, r, i) {
                if (e.subarray && t.subarray) t.set(e.subarray(n, n + r), i);
                else
                  for (var o = 0; o < r; o++) t[i + o] = e[n + o]
              },
              flattenChunks: function(t) {
                var e, n, r, i, o, a;
                for (r = 0, e = 0, n = t.length; e < n; e++) r += t[e].length;
                for (a = new Uint8Array(r), i = 0, e = 0, n = t.length; e < n; e++) o = t[e], a.set(o, i), i += o.length;
                return a
              }
            },
            o = {
              arraySet: function(t, e, n, r, i) {
                for (var o = 0; o < r; o++) t[i + o] = e[n + o]
              },
              flattenChunks: function(t) {
                return [].concat.apply([], t)
              }
            };
          n.setTyped = function(t) {
            t ? (n.Buf8 = Uint8Array, n.Buf16 = Uint16Array, n.Buf32 = Int32Array, n.assign(n, i)) : (n.Buf8 = Array, n.Buf16 = Array, n.Buf32 = Array, n.assign(n, o))
          }, n.setTyped(r)
        }, {}],
        194: [function(t, e, n) {
          "use strict";
          e.localExports = function(t, e, n, r) {
            for (var i = 65535 & t | 0, o = t >>> 16 & 65535 | 0, a = 0; 0 !== n;) {
              n -= a = n > 2e3 ? 2e3 : n;
              do {
                o = o + (i = i + e[r++] | 0) | 0
              } while (--a);
              i %= 65521, o %= 65521
            }
            return i | o << 16 | 0
          }
        }, {}],
        195: [function(t, e, n) {
          "use strict";
          e.localExports = {
            Z_NO_FLUSH: 0,
            Z_PARTIAL_FLUSH: 1,
            Z_SYNC_FLUSH: 2,
            Z_FULL_FLUSH: 3,
            Z_FINISH: 4,
            Z_BLOCK: 5,
            Z_TREES: 6,
            Z_OK: 0,
            Z_STREAM_END: 1,
            Z_NEED_DICT: 2,
            Z_ERRNO: -1,
            Z_STREAM_ERROR: -2,
            Z_DATA_ERROR: -3,
            Z_BUF_ERROR: -5,
            Z_NO_COMPRESSION: 0,
            Z_BEST_SPEED: 1,
            Z_BEST_COMPRESSION: 9,
            Z_DEFAULT_COMPRESSION: -1,
            Z_FILTERED: 1,
            Z_HUFFMAN_ONLY: 2,
            Z_RLE: 3,
            Z_FIXED: 4,
            Z_DEFAULT_STRATEGY: 0,
            Z_BINARY: 0,
            Z_TEXT: 1,
            Z_UNKNOWN: 2,
            Z_DEFLATED: 8
          }
        }, {}],
        196: [function(t, e, n) {
          "use strict";
          var r = function() {
            for (var t, e = [], n = 0; n < 256; n++) {
              t = n;
              for (var r = 0; r < 8; r++) t = 1 & t ? 3988292384 ^ t >>> 1 : t >>> 1;
              e[n] = t
            }
            return e
          }();
          e.localExports = function(t, e, n, i) {
            var o = r,
              a = i + n;
            t ^= -1;
            for (var s = i; s < a; s++) t = t >>> 8 ^ o[255 & (t ^ e[s])];
            return -1 ^ t
          }
        }, {}],
        197: [function(t, e, n) {
          "use strict";

          function r(t, e) {
            return t.msg = P[e], e
          }

          function i(t) {
            return (t << 1) - (t > 4 ? 9 : 0)
          }

          function o(t) {
            for (var e = t.length; --e >= 0;) t[e] = 0
          }

          function a(t) {
            var e = t.state,
              n = e.pending;
            n > t.avail_out && (n = t.avail_out), 0 !== n && (C.arraySet(t.output, e.pending_buf, e.pending_out, n, t.next_out), t.next_out += n, e.pending_out += n, t.total_out += n, t.avail_out -= n, e.pending -= n, 0 === e.pending && (e.pending_out = 0))
          }

          function s(t, e) {
            k._tr_flush_block(t, t.block_start >= 0 ? t.block_start : -1, t.strstart - t.block_start, e), t.block_start = t.strstart, a(t.strm)
          }

          function u(t, e) {
            t.pending_buf[t.pending++] = e
          }

          function c(t, e) {
            t.pending_buf[t.pending++] = e >>> 8 & 255, t.pending_buf[t.pending++] = 255 & e
          }

          function l(t, e, n, r) {
            var i = t.avail_in;
            return i > r && (i = r), 0 === i ? 0 : (t.avail_in -= i, C.arraySet(e, t.input, t.next_in, i, n), 1 === t.state.wrap ? t.adler = E(t.adler, e, i, n) : 2 === t.state.wrap && (t.adler = A(t.adler, e, i, n)), t.next_in += i, t.total_in += i, i)
          }

          function h(t, e) {
            var n, r, i = t.max_chain_length,
              o = t.strstart,
              a = t.prev_length,
              s = t.nice_match,
              u = t.strstart > t.w_size - rt ? t.strstart - (t.w_size - rt) : 0,
              c = t.window,
              l = t.w_mask,
              h = t.prev,
              f = t.strstart + nt,
              d = c[o + a - 1],
              p = c[o + a];
            t.prev_length >= t.good_match && (i >>= 2), s > t.lookahead && (s = t.lookahead);
            do {
              if (n = e, c[n + a] === p && c[n + a - 1] === d && c[n] === c[o] && c[++n] === c[o + 1]) {
                o += 2, n++;
                do {} while (c[++o] === c[++n] && c[++o] === c[++n] && c[++o] === c[++n] && c[++o] === c[++n] && c[++o] === c[++n] && c[++o] === c[++n] && c[++o] === c[++n] && c[++o] === c[++n] && o < f);
                if (r = nt - (f - o), o = f - nt, r > a) {
                  if (t.match_start = e, a = r, r >= s) break;
                  d = c[o + a - 1], p = c[o + a]
                }
              }
            } while ((e = h[e & l]) > u && 0 != --i);
            return a <= t.lookahead ? a : t.lookahead
          }

          function f(t) {
            var e, n, r, i, o, a = t.w_size;
            do {
              if (i = t.window_size - t.lookahead - t.strstart, t.strstart >= a + (a - rt)) {
                C.arraySet(t.window, t.window, a, a, 0), t.match_start -= a, t.strstart -= a, t.block_start -= a, e = n = t.hash_size;
                do {
                  r = t.head[--e], t.head[e] = r >= a ? r - a : 0
                } while (--n);
                e = n = a;
                do {
                  r = t.prev[--e], t.prev[e] = r >= a ? r - a : 0
                } while (--n);
                i += a
              }
              if (0 === t.strm.avail_in) break;
              if (n = l(t.strm, t.window, t.strstart + t.lookahead, i), t.lookahead += n, t.lookahead + t.insert >= et)
                for (o = t.strstart - t.insert, t.ins_h = t.window[o], t.ins_h = (t.ins_h << t.hash_shift ^ t.window[o + 1]) & t.hash_mask; t.insert && (t.ins_h = (t.ins_h << t.hash_shift ^ t.window[o + et - 1]) & t.hash_mask, t.prev[o & t.w_mask] = t.head[t.ins_h], t.head[t.ins_h] = o, o++, t.insert--, !(t.lookahead + t.insert < et)););
            } while (t.lookahead < rt && 0 !== t.strm.avail_in)
          }

          function d(t, e) {
            for (var n, r;;) {
              if (t.lookahead < rt) {
                if (f(t), t.lookahead < rt && e === j) return ft;
                if (0 === t.lookahead) break
              }
              if (n = 0, t.lookahead >= et && (t.ins_h = (t.ins_h << t.hash_shift ^ t.window[t.strstart + et - 1]) & t.hash_mask, n = t.prev[t.strstart & t.w_mask] = t.head[t.ins_h], t.head[t.ins_h] = t.strstart), 0 !== n && t.strstart - n <= t.w_size - rt && (t.match_length = h(t, n)), t.match_length >= et)
                if (r = k._tr_tally(t, t.strstart - t.match_start, t.match_length - et), t.lookahead -= t.match_length, t.match_length <= t.max_lazy_match && t.lookahead >= et) {
                  t.match_length--;
                  do {
                    t.strstart++, t.ins_h = (t.ins_h << t.hash_shift ^ t.window[t.strstart + et - 1]) & t.hash_mask, n = t.prev[t.strstart & t.w_mask] = t.head[t.ins_h], t.head[t.ins_h] = t.strstart
                  } while (0 != --t.match_length);
                  t.strstart++
                } else t.strstart += t.match_length, t.match_length = 0, t.ins_h = t.window[t.strstart], t.ins_h = (t.ins_h << t.hash_shift ^ t.window[t.strstart + 1]) & t.hash_mask;
              else r = k._tr_tally(t, 0, t.window[t.strstart]), t.lookahead--, t.strstart++;
              if (r && (s(t, !1), 0 === t.strm.avail_out)) return ft
            }
            return t.insert = t.strstart < et - 1 ? t.strstart : et - 1, e === O ? (s(t, !0), 0 === t.strm.avail_out ? pt : gt) : t.last_lit && (s(t, !1), 0 === t.strm.avail_out) ? ft : dt
          }

          function p(t, e) {
            for (var n, r, i;;) {
              if (t.lookahead < rt) {
                if (f(t), t.lookahead < rt && e === j) return ft;
                if (0 === t.lookahead) break
              }
              if (n = 0, t.lookahead >= et && (t.ins_h = (t.ins_h << t.hash_shift ^ t.window[t.strstart + et - 1]) & t.hash_mask, n = t.prev[t.strstart & t.w_mask] = t.head[t.ins_h], t.head[t.ins_h] = t.strstart), t.prev_length = t.match_length, t.prev_match = t.match_start, t.match_length = et - 1, 0 !== n && t.prev_length < t.max_lazy_match && t.strstart - n <= t.w_size - rt && (t.match_length = h(t, n), t.match_length <= 5 && (t.strategy === R || t.match_length === et && t.strstart - t.match_start > 4096) && (t.match_length = et - 1)), t.prev_length >= et && t.match_length <= t.prev_length) {
                i = t.strstart + t.lookahead - et, r = k._tr_tally(t, t.strstart - 1 - t.prev_match, t.prev_length - et), t.lookahead -= t.prev_length - 1, t.prev_length -= 2;
                do {
                  ++t.strstart <= i && (t.ins_h = (t.ins_h << t.hash_shift ^ t.window[t.strstart + et - 1]) & t.hash_mask, n = t.prev[t.strstart & t.w_mask] = t.head[t.ins_h], t.head[t.ins_h] = t.strstart)
                } while (0 != --t.prev_length);
                if (t.match_available = 0, t.match_length = et - 1, t.strstart++, r && (s(t, !1), 0 === t.strm.avail_out)) return ft
              } else if (t.match_available) {
                if ((r = k._tr_tally(t, 0, t.window[t.strstart - 1])) && s(t, !1), t.strstart++, t.lookahead--, 0 === t.strm.avail_out) return ft
              } else t.match_available = 1, t.strstart++, t.lookahead--
            }
            return t.match_available && (r = k._tr_tally(t, 0, t.window[t.strstart - 1]), t.match_available = 0), t.insert = t.strstart < et - 1 ? t.strstart : et - 1, e === O ? (s(t, !0), 0 === t.strm.avail_out ? pt : gt) : t.last_lit && (s(t, !1), 0 === t.strm.avail_out) ? ft : dt
          }

          function g(t, e) {
            for (var n, r, i, o, a = t.window;;) {
              if (t.lookahead <= nt) {
                if (f(t), t.lookahead <= nt && e === j) return ft;
                if (0 === t.lookahead) break
              }
              if (t.match_length = 0, t.lookahead >= et && t.strstart > 0 && (i = t.strstart - 1, (r = a[i]) === a[++i] && r === a[++i] && r === a[++i])) {
                o = t.strstart + nt;
                do {} while (r === a[++i] && r === a[++i] && r === a[++i] && r === a[++i] && r === a[++i] && r === a[++i] && r === a[++i] && r === a[++i] && i < o);
                t.match_length = nt - (o - i), t.match_length > t.lookahead && (t.match_length = t.lookahead)
              }
              if (t.match_length >= et ? (n = k._tr_tally(t, 1, t.match_length - et), t.lookahead -= t.match_length, t.strstart += t.match_length, t.match_length = 0) : (n = k._tr_tally(t, 0, t.window[t.strstart]), t.lookahead--, t.strstart++), n && (s(t, !1), 0 === t.strm.avail_out)) return ft
            }
            return t.insert = 0, e === O ? (s(t, !0), 0 === t.strm.avail_out ? pt : gt) : t.last_lit && (s(t, !1), 0 === t.strm.avail_out) ? ft : dt
          }

          function _(t, e) {
            for (var n;;) {
              if (0 === t.lookahead && (f(t), 0 === t.lookahead)) {
                if (e === j) return ft;
                break
              }
              if (t.match_length = 0, n = k._tr_tally(t, 0, t.window[t.strstart]), t.lookahead--, t.strstart++, n && (s(t, !1), 0 === t.strm.avail_out)) return ft
            }
            return t.insert = 0, e === O ? (s(t, !0), 0 === t.strm.avail_out ? pt : gt) : t.last_lit && (s(t, !1), 0 === t.strm.avail_out) ? ft : dt
          }

          function m(t, e, n, r, i) {
            this.good_length = t, this.max_lazy = e, this.nice_length = n, this.max_chain = r, this.func = i
          }

          function v(t) {
            t.window_size = 2 * t.w_size, o(t.head), t.max_lazy_match = S[t.level].max_lazy, t.good_match = S[t.level].good_length, t.nice_match = S[t.level].nice_length, t.max_chain_length = S[t.level].max_chain, t.strstart = 0, t.block_start = 0, t.lookahead = 0, t.insert = 0, t.match_length = t.prev_length = et - 1, t.match_available = 0, t.ins_h = 0
          }

          function y() {
            this.strm = null, this.status = 0, this.pending_buf = null, this.pending_buf_size = 0, this.pending_out = 0, this.pending = 0, this.wrap = 0, this.gzhead = null, this.gzindex = 0, this.method = W, this.last_flush = -1, this.w_size = 0, this.w_bits = 0, this.w_mask = 0, this.window = null, this.window_size = 0, this.prev = null, this.head = null, this.ins_h = 0, this.hash_size = 0, this.hash_bits = 0, this.hash_mask = 0, this.hash_shift = 0, this.block_start = 0, this.match_length = 0, this.prev_match = 0, this.match_available = 0, this.strstart = 0, this.match_start = 0, this.lookahead = 0, this.prev_length = 0, this.max_chain_length = 0, this.max_lazy_match = 0, this.level = 0, this.strategy = 0, this.good_match = 0, this.nice_match = 0, this.dyn_ltree = new C.Buf16(2 * $), this.dyn_dtree = new C.Buf16(2 * (2 * J + 1)), this.bl_tree = new C.Buf16(2 * (2 * Q + 1)), o(this.dyn_ltree), o(this.dyn_dtree), o(this.bl_tree), this.l_desc = null, this.d_desc = null, this.bl_desc = null, this.bl_count = new C.Buf16(tt + 1), this.heap = new C.Buf16(2 * X + 1), o(this.heap), this.heap_len = 0, this.heap_max = 0, this.depth = new C.Buf16(2 * X + 1), o(this.depth), this.l_buf = 0, this.lit_bufsize = 0, this.last_lit = 0, this.d_buf = 0, this.opt_len = 0, this.static_len = 0, this.matches = 0, this.insert = 0, this.bi_buf = 0, this.bi_valid = 0
          }

          function b(t) {
            var e;
            return t && t.state ? (t.total_in = t.total_out = 0, t.data_type = Z, e = t.state, e.pending = 0, e.pending_out = 0, e.wrap < 0 && (e.wrap = -e.wrap), e.status = e.wrap ? ot : lt, t.adler = 2 === e.wrap ? 0 : 1, e.last_flush = j, k._tr_init(e), B) : r(t, z)
          }

          function w(t) {
            var e = b(t);
            return e === B && v(t.state), e
          }

          function x(t, e, n, i, o, a) {
            if (!t) return z;
            var s = 1;
            if (e === M && (e = 6), i < 0 ? (s = 0, i = -i) : i > 15 && (s = 2, i -= 16), o < 1 || o > V || n !== W || i < 8 || i > 15 || e < 0 || e > 9 || a < 0 || a > G) return r(t, z);
            8 === i && (i = 9);
            var u = new y;
            return t.state = u, u.strm = t, u.wrap = s, u.gzhead = null, u.w_bits = i, u.w_size = 1 << u.w_bits, u.w_mask = u.w_size - 1, u.hash_bits = o + 7, u.hash_size = 1 << u.hash_bits, u.hash_mask = u.hash_size - 1, u.hash_shift = ~~((u.hash_bits + et - 1) / et), u.window = new C.Buf8(2 * u.w_size), u.head = new C.Buf16(u.hash_size), u.prev = new C.Buf16(u.w_size), u.lit_bufsize = 1 << o + 6, u.pending_buf_size = 4 * u.lit_bufsize, u.pending_buf = new C.Buf8(u.pending_buf_size), u.d_buf = 1 * u.lit_bufsize, u.l_buf = 3 * u.lit_bufsize, u.level = e, u.strategy = a, u.method = n, w(t)
          }
          var S, C = t("../utils/common"),
            k = t("./trees"),
            E = t("./adler32"),
            A = t("./crc32"),
            P = t("./messages"),
            j = 0,
            I = 1,
            L = 3,
            O = 4,
            T = 5,
            B = 0,
            N = 1,
            z = -2,
            F = -3,
            D = -5,
            M = -1,
            R = 1,
            U = 2,
            H = 3,
            G = 4,
            q = 0,
            Z = 2,
            W = 8,
            V = 9,
            Y = 15,
            K = 8,
            X = 286,
            J = 30,
            Q = 19,
            $ = 2 * X + 1,
            tt = 15,
            et = 3,
            nt = 258,
            rt = nt + et + 1,
            it = 32,
            ot = 42,
            at = 69,
            st = 73,
            ut = 91,
            ct = 103,
            lt = 113,
            ht = 666,
            ft = 1,
            dt = 2,
            pt = 3,
            gt = 4,
            _t = 3;
          S = [new m(0, 0, 0, 0, function(t, e) {
            var n = 65535;
            for (n > t.pending_buf_size - 5 && (n = t.pending_buf_size - 5);;) {
              if (t.lookahead <= 1) {
                if (f(t), 0 === t.lookahead && e === j) return ft;
                if (0 === t.lookahead) break
              }
              t.strstart += t.lookahead, t.lookahead = 0;
              var r = t.block_start + n;
              if ((0 === t.strstart || t.strstart >= r) && (t.lookahead = t.strstart - r, t.strstart = r, s(t, !1), 0 === t.strm.avail_out)) return ft;
              if (t.strstart - t.block_start >= t.w_size - rt && (s(t, !1), 0 === t.strm.avail_out)) return ft
            }
            return t.insert = 0, e === O ? (s(t, !0), 0 === t.strm.avail_out ? pt : gt) : (t.strstart > t.block_start && (s(t, !1), t.strm.avail_out), ft)
          }), new m(4, 4, 8, 4, d), new m(4, 5, 16, 8, d), new m(4, 6, 32, 32, d), new m(4, 4, 16, 16, p), new m(8, 16, 32, 32, p), new m(8, 16, 128, 128, p), new m(8, 32, 128, 256, p), new m(32, 128, 258, 1024, p), new m(32, 258, 258, 4096, p)], n.deflateInit = function(t, e) {
            return x(t, e, W, Y, K, q)
          }, n.deflateInit2 = x, n.deflateReset = w, n.deflateResetKeep = b, n.deflateSetHeader = function(t, e) {
            return t && t.state ? 2 !== t.state.wrap ? z : (t.state.gzhead = e, B) : z
          }, n.deflate = function(t, e) {
            var n, s, l, h;
            if (!t || !t.state || e > T || e < 0) return t ? r(t, z) : z;
            if (s = t.state, !t.output || !t.input && 0 !== t.avail_in || s.status === ht && e !== O) return r(t, 0 === t.avail_out ? D : z);
            if (s.strm = t, n = s.last_flush, s.last_flush = e, s.status === ot)
              if (2 === s.wrap) t.adler = 0, u(s, 31), u(s, 139), u(s, 8), s.gzhead ? (u(s, (s.gzhead.text ? 1 : 0) + (s.gzhead.hcrc ? 2 : 0) + (s.gzhead.extra ? 4 : 0) + (s.gzhead.name ? 8 : 0) + (s.gzhead.comment ? 16 : 0)), u(s, 255 & s.gzhead.time), u(s, s.gzhead.time >> 8 & 255), u(s, s.gzhead.time >> 16 & 255), u(s, s.gzhead.time >> 24 & 255), u(s, 9 === s.level ? 2 : s.strategy >= U || s.level < 2 ? 4 : 0), u(s, 255 & s.gzhead.os), s.gzhead.extra && s.gzhead.extra.length && (u(s, 255 & s.gzhead.extra.length), u(s, s.gzhead.extra.length >> 8 & 255)), s.gzhead.hcrc && (t.adler = A(t.adler, s.pending_buf, s.pending, 0)), s.gzindex = 0, s.status = at) : (u(s, 0), u(s, 0), u(s, 0), u(s, 0), u(s, 0), u(s, 9 === s.level ? 2 : s.strategy >= U || s.level < 2 ? 4 : 0), u(s, _t), s.status = lt);
              else {
                var f = W + (s.w_bits - 8 << 4) << 8;
                f |= (s.strategy >= U || s.level < 2 ? 0 : s.level < 6 ? 1 : 6 === s.level ? 2 : 3) << 6, 0 !== s.strstart && (f |= it), f += 31 - f % 31, s.status = lt, c(s, f), 0 !== s.strstart && (c(s, t.adler >>> 16), c(s, 65535 & t.adler)), t.adler = 1
              }
            if (s.status === at)
              if (s.gzhead.extra) {
                for (l = s.pending; s.gzindex < (65535 & s.gzhead.extra.length) && (s.pending !== s.pending_buf_size || (s.gzhead.hcrc && s.pending > l && (t.adler = A(t.adler, s.pending_buf, s.pending - l, l)), a(t), l = s.pending, s.pending !== s.pending_buf_size));) u(s, 255 & s.gzhead.extra[s.gzindex]), s.gzindex++;
                s.gzhead.hcrc && s.pending > l && (t.adler = A(t.adler, s.pending_buf, s.pending - l, l)), s.gzindex === s.gzhead.extra.length && (s.gzindex = 0, s.status = st)
              } else s.status = st;
            if (s.status === st)
              if (s.gzhead.name) {
                l = s.pending;
                do {
                  if (s.pending === s.pending_buf_size && (s.gzhead.hcrc && s.pending > l && (t.adler = A(t.adler, s.pending_buf, s.pending - l, l)), a(t), l = s.pending, s.pending === s.pending_buf_size)) {
                    h = 1;
                    break
                  }
                  h = s.gzindex < s.gzhead.name.length ? 255 & s.gzhead.name.charCodeAt(s.gzindex++) : 0, u(s, h)
                } while (0 !== h);
                s.gzhead.hcrc && s.pending > l && (t.adler = A(t.adler, s.pending_buf, s.pending - l, l)), 0 === h && (s.gzindex = 0, s.status = ut)
              } else s.status = ut;
            if (s.status === ut)
              if (s.gzhead.comment) {
                l = s.pending;
                do {
                  if (s.pending === s.pending_buf_size && (s.gzhead.hcrc && s.pending > l && (t.adler = A(t.adler, s.pending_buf, s.pending - l, l)), a(t), l = s.pending, s.pending === s.pending_buf_size)) {
                    h = 1;
                    break
                  }
                  h = s.gzindex < s.gzhead.comment.length ? 255 & s.gzhead.comment.charCodeAt(s.gzindex++) : 0, u(s, h)
                } while (0 !== h);
                s.gzhead.hcrc && s.pending > l && (t.adler = A(t.adler, s.pending_buf, s.pending - l, l)), 0 === h && (s.status = ct)
              } else s.status = ct;
            if (s.status === ct && (s.gzhead.hcrc ? (s.pending + 2 > s.pending_buf_size && a(t), s.pending + 2 <= s.pending_buf_size && (u(s, 255 & t.adler), u(s, t.adler >> 8 & 255), t.adler = 0, s.status = lt)) : s.status = lt), 0 !== s.pending) {
              if (a(t), 0 === t.avail_out) return s.last_flush = -1, B
            } else if (0 === t.avail_in && i(e) <= i(n) && e !== O) return r(t, D);
            if (s.status === ht && 0 !== t.avail_in) return r(t, D);
            if (0 !== t.avail_in || 0 !== s.lookahead || e !== j && s.status !== ht) {
              var d = s.strategy === U ? _(s, e) : s.strategy === H ? g(s, e) : S[s.level].func(s, e);
              if (d !== pt && d !== gt || (s.status = ht), d === ft || d === pt) return 0 === t.avail_out && (s.last_flush = -1), B;
              if (d === dt && (e === I ? k._tr_align(s) : e !== T && (k._tr_stored_block(s, 0, 0, !1), e === L && (o(s.head), 0 === s.lookahead && (s.strstart = 0, s.block_start = 0, s.insert = 0))), a(t), 0 === t.avail_out)) return s.last_flush = -1, B
            }
            return e !== O ? B : s.wrap <= 0 ? N : (2 === s.wrap ? (u(s, 255 & t.adler), u(s, t.adler >> 8 & 255), u(s, t.adler >> 16 & 255), u(s, t.adler >> 24 & 255), u(s, 255 & t.total_in), u(s, t.total_in >> 8 & 255), u(s, t.total_in >> 16 & 255), u(s, t.total_in >> 24 & 255)) : (c(s, t.adler >>> 16), c(s, 65535 & t.adler)), a(t), s.wrap > 0 && (s.wrap = -s.wrap), 0 !== s.pending ? B : N)
          }, n.deflateEnd = function(t) {
            var e;
            return t && t.state ? (e = t.state.status) !== ot && e !== at && e !== st && e !== ut && e !== ct && e !== lt && e !== ht ? r(t, z) : (t.state = null, e === lt ? r(t, F) : B) : z
          }, n.deflateSetDictionary = function(t, e) {
            var n, r, i, a, s, u, c, l, h = e.length;
            if (!t || !t.state) return z;
            if (n = t.state, 2 === (a = n.wrap) || 1 === a && n.status !== ot || n.lookahead) return z;
            for (1 === a && (t.adler = E(t.adler, e, h, 0)), n.wrap = 0, h >= n.w_size && (0 === a && (o(n.head), n.strstart = 0, n.block_start = 0, n.insert = 0), l = new C.Buf8(n.w_size), C.arraySet(l, e, h - n.w_size, n.w_size, 0), e = l, h = n.w_size), s = t.avail_in, u = t.next_in, c = t.input, t.avail_in = h, t.next_in = 0, t.input = e, f(n); n.lookahead >= et;) {
              r = n.strstart, i = n.lookahead - (et - 1);
              do {
                n.ins_h = (n.ins_h << n.hash_shift ^ n.window[r + et - 1]) & n.hash_mask, n.prev[r & n.w_mask] = n.head[n.ins_h], n.head[n.ins_h] = r, r++
              } while (--i);
              n.strstart = r, n.lookahead = et - 1, f(n)
            }
            return n.strstart += n.lookahead, n.block_start = n.strstart, n.insert = n.lookahead, n.lookahead = 0, n.match_length = n.prev_length = et - 1, n.match_available = 0, t.next_in = u, t.input = c, t.avail_in = s, n.wrap = a, B
          }, n.deflateInfo = "pako deflate (from Nodeca project)"
        }, {
          "../utils/common": 193,
          "./adler32": 194,
          "./crc32": 196,
          "./messages": 201,
          "./trees": 202
        }],
        198: [function(t, e, n) {
          "use strict";
          e.localExports = function(t, e) {
            var n, r, i, o, a, s, u, c, l, h, f, d, p, g, _, m, v, y, b, w, x, S, C, k, E;
            n = t.state, r = t.next_in, k = t.input, i = r + (t.avail_in - 5), o = t.next_out, E = t.output, a = o - (e - t.avail_out), s = o + (t.avail_out - 257), u = n.dmax, c = n.wsize, l = n.whave, h = n.wnext, f = n.window, d = n.hold, p = n.bits, g = n.lencode, _ = n.distcode, m = (1 << n.lenbits) - 1, v = (1 << n.distbits) - 1;
            t: do {
              p < 15 && (d += k[r++] << p, p += 8, d += k[r++] << p, p += 8), y = g[d & m];
              e: for (;;) {
                if (b = y >>> 24, d >>>= b, p -= b, 0 === (b = y >>> 16 & 255)) E[o++] = 65535 & y;
                else {
                  if (!(16 & b)) {
                    if (0 == (64 & b)) {
                      y = g[(65535 & y) + (d & (1 << b) - 1)];
                      continue e
                    }
                    if (32 & b) {
                      n.mode = 12;
                      break t
                    }
                    t.msg = "invalid literal/length code", n.mode = 30;
                    break t
                  }
                  w = 65535 & y, (b &= 15) && (p < b && (d += k[r++] << p, p += 8), w += d & (1 << b) - 1, d >>>= b, p -= b), p < 15 && (d += k[r++] << p, p += 8, d += k[r++] << p, p += 8), y = _[d & v];
                  n: for (;;) {
                    if (b = y >>> 24, d >>>= b, p -= b, !(16 & (b = y >>> 16 & 255))) {
                      if (0 == (64 & b)) {
                        y = _[(65535 & y) + (d & (1 << b) - 1)];
                        continue n
                      }
                      t.msg = "invalid distance code", n.mode = 30;
                      break t
                    }
                    if (x = 65535 & y, b &= 15, p < b && (d += k[r++] << p, (p += 8) < b && (d += k[r++] << p, p += 8)), (x += d & (1 << b) - 1) > u) {
                      t.msg = "invalid distance too far back", n.mode = 30;
                      break t
                    }
                    if (d >>>= b, p -= b, b = o - a, x > b) {
                      if ((b = x - b) > l && n.sane) {
                        t.msg = "invalid distance too far back", n.mode = 30;
                        break t
                      }
                      if (S = 0, C = f, 0 === h) {
                        if (S += c - b, b < w) {
                          w -= b;
                          do {
                            E[o++] = f[S++]
                          } while (--b);
                          S = o - x, C = E
                        }
                      } else if (h < b) {
                        if (S += c + h - b, (b -= h) < w) {
                          w -= b;
                          do {
                            E[o++] = f[S++]
                          } while (--b);
                          if (S = 0, h < w) {
                            w -= b = h;
                            do {
                              E[o++] = f[S++]
                            } while (--b);
                            S = o - x, C = E
                          }
                        }
                      } else if (S += h - b, b < w) {
                        w -= b;
                        do {
                          E[o++] = f[S++]
                        } while (--b);
                        S = o - x, C = E
                      }
                      for (; w > 2;) E[o++] = C[S++], E[o++] = C[S++], E[o++] = C[S++], w -= 3;
                      w && (E[o++] = C[S++], w > 1 && (E[o++] = C[S++]))
                    } else {
                      S = o - x;
                      do {
                        E[o++] = E[S++], E[o++] = E[S++], E[o++] = E[S++], w -= 3
                      } while (w > 2);
                      w && (E[o++] = E[S++], w > 1 && (E[o++] = E[S++]))
                    }
                    break
                  }
                }
                break
              }
            } while (r < i && o < s);
            r -= w = p >> 3, d &= (1 << (p -= w << 3)) - 1, t.next_in = r, t.next_out = o, t.avail_in = r < i ? i - r + 5 : 5 - (r - i), t.avail_out = o < s ? s - o + 257 : 257 - (o - s), n.hold = d, n.bits = p
          }
        }, {}],
        199: [function(t, e, n) {
          "use strict";

          function r(t) {
            return (t >>> 24 & 255) + (t >>> 8 & 65280) + ((65280 & t) << 8) + ((255 & t) << 24)
          }

          function i() {
            this.mode = 0, this.last = !1, this.wrap = 0, this.havedict = !1, this.flags = 0, this.dmax = 0, this.check = 0, this.total = 0, this.head = null, this.wbits = 0, this.wsize = 0, this.whave = 0, this.wnext = 0, this.window = null, this.hold = 0, this.bits = 0, this.length = 0, this.offset = 0, this.extra = 0, this.lencode = null, this.distcode = null, this.lenbits = 0, this.distbits = 0, this.ncode = 0, this.nlen = 0, this.ndist = 0, this.have = 0, this.next = null, this.lens = new d.Buf16(320), this.work = new d.Buf16(288), this.lendyn = null, this.distdyn = null, this.sane = 0, this.back = 0, this.was = 0
          }

          function o(t) {
            var e;
            return t && t.state ? (e = t.state, t.total_in = t.total_out = e.total = 0, t.msg = "", e.wrap && (t.adler = 1 & e.wrap), e.mode = O, e.last = 0, e.havedict = 0, e.dmax = 32768, e.head = null, e.hold = 0, e.bits = 0, e.lencode = e.lendyn = new d.Buf32(lt), e.distcode = e.distdyn = new d.Buf32(ht), e.sane = 1, e.back = -1, C) : A
          }

          function a(t) {
            var e;
            return t && t.state ? (e = t.state, e.wsize = 0, e.whave = 0, e.wnext = 0, o(t)) : A
          }

          function s(t, e) {
            var n, r;
            return t && t.state ? (r = t.state, e < 0 ? (n = 0, e = -e) : (n = 1 + (e >> 4), e < 48 && (e &= 15)), e && (e < 8 || e > 15) ? A : (null !== r.window && r.wbits !== e && (r.window = null), r.wrap = n, r.wbits = e, a(t))) : A
          }

          function u(t, e) {
            var n, r;
            return t ? (r = new i, t.state = r, r.window = null, (n = s(t, e)) !== C && (t.state = null), n) : A
          }

          function c(t) {
            if (dt) {
              var e;
              for (h = new d.Buf32(512), f = new d.Buf32(32), e = 0; e < 144;) t.lens[e++] = 8;
              for (; e < 256;) t.lens[e++] = 9;
              for (; e < 280;) t.lens[e++] = 7;
              for (; e < 288;) t.lens[e++] = 8;
              for (m(y, t.lens, 0, 288, h, 0, t.work, {
                bits: 9
              }), e = 0; e < 32;) t.lens[e++] = 5;
              m(b, t.lens, 0, 32, f, 0, t.work, {
                bits: 5
              }), dt = !1
            }
            t.lencode = h, t.lenbits = 9, t.distcode = f, t.distbits = 5
          }

          function l(t, e, n, r) {
            var i, o = t.state;
            return null === o.window && (o.wsize = 1 << o.wbits, o.wnext = 0, o.whave = 0, o.window = new d.Buf8(o.wsize)), r >= o.wsize ? (d.arraySet(o.window, e, n - o.wsize, o.wsize, 0), o.wnext = 0, o.whave = o.wsize) : ((i = o.wsize - o.wnext) > r && (i = r), d.arraySet(o.window, e, n - r, i, o.wnext), (r -= i) ? (d.arraySet(o.window, e, n - r, r, 0), o.wnext = r, o.whave = o.wsize) : (o.wnext += i, o.wnext === o.wsize && (o.wnext = 0), o.whave < o.wsize && (o.whave += i))), 0
          }
          var h, f, d = t("../utils/common"),
            p = t("./adler32"),
            g = t("./crc32"),
            _ = t("./inffast"),
            m = t("./inftrees"),
            v = 0,
            y = 1,
            b = 2,
            w = 4,
            x = 5,
            S = 6,
            C = 0,
            k = 1,
            E = 2,
            A = -2,
            P = -3,
            j = -4,
            I = -5,
            L = 8,
            O = 1,
            T = 2,
            B = 3,
            N = 4,
            z = 5,
            F = 6,
            D = 7,
            M = 8,
            R = 9,
            U = 10,
            H = 11,
            G = 12,
            q = 13,
            Z = 14,
            W = 15,
            V = 16,
            Y = 17,
            K = 18,
            X = 19,
            J = 20,
            Q = 21,
            $ = 22,
            tt = 23,
            et = 24,
            nt = 25,
            rt = 26,
            it = 27,
            ot = 28,
            at = 29,
            st = 30,
            ut = 31,
            ct = 32,
            lt = 852,
            ht = 592,
            ft = 15,
            dt = !0;
          n.inflateReset = a, n.inflateReset2 = s, n.inflateResetKeep = o, n.inflateInit = function(t) {
            return u(t, ft)
          }, n.inflateInit2 = u, n.inflate = function(t, e) {
            var n, i, o, a, s, u, h, f, lt, ht, ft, dt, pt, gt, _t, mt, vt, yt, bt, wt, xt, St, Ct, kt, Et = 0,
              At = new d.Buf8(4),
              Pt = [16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15];
            if (!t || !t.state || !t.output || !t.input && 0 !== t.avail_in) return A;
            (n = t.state).mode === G && (n.mode = q), s = t.next_out, o = t.output, h = t.avail_out, a = t.next_in, i = t.input, u = t.avail_in, f = n.hold, lt = n.bits, ht = u, ft = h, St = C;
            t: for (;;) switch (n.mode) {
              case O:
                if (0 === n.wrap) {
                  n.mode = q;
                  break
                }
                for (; lt < 16;) {
                  if (0 === u) break t;
                  u--, f += i[a++] << lt, lt += 8
                }
                if (2 & n.wrap && 35615 === f) {
                  n.check = 0, At[0] = 255 & f, At[1] = f >>> 8 & 255, n.check = g(n.check, At, 2, 0), f = 0, lt = 0, n.mode = T;
                  break
                }
                if (n.flags = 0, n.head && (n.head.done = !1), !(1 & n.wrap) || (((255 & f) << 8) + (f >> 8)) % 31) {
                  t.msg = "incorrect header check", n.mode = st;
                  break
                }
                if ((15 & f) !== L) {
                  t.msg = "unknown compression method", n.mode = st;
                  break
                }
                if (f >>>= 4, lt -= 4, xt = 8 + (15 & f), 0 === n.wbits) n.wbits = xt;
                else if (xt > n.wbits) {
                  t.msg = "invalid window size", n.mode = st;
                  break
                }
                n.dmax = 1 << xt, t.adler = n.check = 1, n.mode = 512 & f ? U : G, f = 0, lt = 0;
                break;
              case T:
                for (; lt < 16;) {
                  if (0 === u) break t;
                  u--, f += i[a++] << lt, lt += 8
                }
                if (n.flags = f, (255 & n.flags) !== L) {
                  t.msg = "unknown compression method", n.mode = st;
                  break
                }
                if (57344 & n.flags) {
                  t.msg = "unknown header flags set", n.mode = st;
                  break
                }
                n.head && (n.head.text = f >> 8 & 1), 512 & n.flags && (At[0] = 255 & f, At[1] = f >>> 8 & 255, n.check = g(n.check, At, 2, 0)), f = 0, lt = 0, n.mode = B;
              case B:
                for (; lt < 32;) {
                  if (0 === u) break t;
                  u--, f += i[a++] << lt, lt += 8
                }
                n.head && (n.head.time = f), 512 & n.flags && (At[0] = 255 & f, At[1] = f >>> 8 & 255, At[2] = f >>> 16 & 255, At[3] = f >>> 24 & 255, n.check = g(n.check, At, 4, 0)), f = 0, lt = 0, n.mode = N;
              case N:
                for (; lt < 16;) {
                  if (0 === u) break t;
                  u--, f += i[a++] << lt, lt += 8
                }
                n.head && (n.head.xflags = 255 & f, n.head.os = f >> 8), 512 & n.flags && (At[0] = 255 & f, At[1] = f >>> 8 & 255, n.check = g(n.check, At, 2, 0)), f = 0, lt = 0, n.mode = z;
              case z:
                if (1024 & n.flags) {
                  for (; lt < 16;) {
                    if (0 === u) break t;
                    u--, f += i[a++] << lt, lt += 8
                  }
                  n.length = f, n.head && (n.head.extra_len = f), 512 & n.flags && (At[0] = 255 & f, At[1] = f >>> 8 & 255, n.check = g(n.check, At, 2, 0)), f = 0, lt = 0
                } else n.head && (n.head.extra = null);
                n.mode = F;
              case F:
                if (1024 & n.flags && ((dt = n.length) > u && (dt = u), dt && (n.head && (xt = n.head.extra_len - n.length, n.head.extra || (n.head.extra = new Array(n.head.extra_len)), d.arraySet(n.head.extra, i, a, dt, xt)), 512 & n.flags && (n.check = g(n.check, i, dt, a)), u -= dt, a += dt, n.length -= dt), n.length)) break t;
                n.length = 0, n.mode = D;
              case D:
                if (2048 & n.flags) {
                  if (0 === u) break t;
                  dt = 0;
                  do {
                    xt = i[a + dt++], n.head && xt && n.length < 65536 && (n.head.name += String.fromCharCode(xt))
                  } while (xt && dt < u);
                  if (512 & n.flags && (n.check = g(n.check, i, dt, a)), u -= dt, a += dt, xt) break t
                } else n.head && (n.head.name = null);
                n.length = 0, n.mode = M;
              case M:
                if (4096 & n.flags) {
                  if (0 === u) break t;
                  dt = 0;
                  do {
                    xt = i[a + dt++], n.head && xt && n.length < 65536 && (n.head.comment += String.fromCharCode(xt))
                  } while (xt && dt < u);
                  if (512 & n.flags && (n.check = g(n.check, i, dt, a)), u -= dt, a += dt, xt) break t
                } else n.head && (n.head.comment = null);
                n.mode = R;
              case R:
                if (512 & n.flags) {
                  for (; lt < 16;) {
                    if (0 === u) break t;
                    u--, f += i[a++] << lt, lt += 8
                  }
                  if (f !== (65535 & n.check)) {
                    t.msg = "header crc mismatch", n.mode = st;
                    break
                  }
                  f = 0, lt = 0
                }
                n.head && (n.head.hcrc = n.flags >> 9 & 1, n.head.done = !0), t.adler = n.check = 0, n.mode = G;
                break;
              case U:
                for (; lt < 32;) {
                  if (0 === u) break t;
                  u--, f += i[a++] << lt, lt += 8
                }
                t.adler = n.check = r(f), f = 0, lt = 0, n.mode = H;
              case H:
                if (0 === n.havedict) return t.next_out = s, t.avail_out = h, t.next_in = a, t.avail_in = u, n.hold = f, n.bits = lt, E;
                t.adler = n.check = 1, n.mode = G;
              case G:
                if (e === x || e === S) break t;
              case q:
                if (n.last) {
                  f >>>= 7 & lt, lt -= 7 & lt, n.mode = it;
                  break
                }
                for (; lt < 3;) {
                  if (0 === u) break t;
                  u--, f += i[a++] << lt, lt += 8
                }
                switch (n.last = 1 & f, f >>>= 1, lt -= 1, 3 & f) {
                  case 0:
                    n.mode = Z;
                    break;
                  case 1:
                    if (c(n), n.mode = J, e === S) {
                      f >>>= 2, lt -= 2;
                      break t
                    }
                    break;
                  case 2:
                    n.mode = Y;
                    break;
                  case 3:
                    t.msg = "invalid block type", n.mode = st
                }
                f >>>= 2, lt -= 2;
                break;
              case Z:
                for (f >>>= 7 & lt, lt -= 7 & lt; lt < 32;) {
                  if (0 === u) break t;
                  u--, f += i[a++] << lt, lt += 8
                }
                if ((65535 & f) != (f >>> 16 ^ 65535)) {
                  t.msg = "invalid stored block lengths", n.mode = st;
                  break
                }
                if (n.length = 65535 & f, f = 0, lt = 0, n.mode = W, e === S) break t;
              case W:
                n.mode = V;
              case V:
                if (dt = n.length) {
                  if (dt > u && (dt = u), dt > h && (dt = h), 0 === dt) break t;
                  d.arraySet(o, i, a, dt, s), u -= dt, a += dt, h -= dt, s += dt, n.length -= dt;
                  break
                }
                n.mode = G;
                break;
              case Y:
                for (; lt < 14;) {
                  if (0 === u) break t;
                  u--, f += i[a++] << lt, lt += 8
                }
                if (n.nlen = 257 + (31 & f), f >>>= 5, lt -= 5, n.ndist = 1 + (31 & f), f >>>= 5, lt -= 5, n.ncode = 4 + (15 & f), f >>>= 4, lt -= 4, n.nlen > 286 || n.ndist > 30) {
                  t.msg = "too many length or distance symbols", n.mode = st;
                  break
                }
                n.have = 0, n.mode = K;
              case K:
                for (; n.have < n.ncode;) {
                  for (; lt < 3;) {
                    if (0 === u) break t;
                    u--, f += i[a++] << lt, lt += 8
                  }
                  n.lens[Pt[n.have++]] = 7 & f, f >>>= 3, lt -= 3
                }
                for (; n.have < 19;) n.lens[Pt[n.have++]] = 0;
                if (n.lencode = n.lendyn, n.lenbits = 7, Ct = {
                  bits: n.lenbits
                }, St = m(v, n.lens, 0, 19, n.lencode, 0, n.work, Ct), n.lenbits = Ct.bits, St) {
                  t.msg = "invalid code lengths set", n.mode = st;
                  break
                }
                n.have = 0, n.mode = X;
              case X:
                for (; n.have < n.nlen + n.ndist;) {
                  for (; Et = n.lencode[f & (1 << n.lenbits) - 1], _t = Et >>> 24, mt = Et >>> 16 & 255, vt = 65535 & Et, !(_t <= lt);) {
                    if (0 === u) break t;
                    u--, f += i[a++] << lt, lt += 8
                  }
                  if (vt < 16) f >>>= _t, lt -= _t, n.lens[n.have++] = vt;
                  else {
                    if (16 === vt) {
                      for (kt = _t + 2; lt < kt;) {
                        if (0 === u) break t;
                        u--, f += i[a++] << lt, lt += 8
                      }
                      if (f >>>= _t, lt -= _t, 0 === n.have) {
                        t.msg = "invalid bit length repeat", n.mode = st;
                        break
                      }
                      xt = n.lens[n.have - 1], dt = 3 + (3 & f), f >>>= 2, lt -= 2
                    } else if (17 === vt) {
                      for (kt = _t + 3; lt < kt;) {
                        if (0 === u) break t;
                        u--, f += i[a++] << lt, lt += 8
                      }
                      lt -= _t, xt = 0, dt = 3 + (7 & (f >>>= _t)), f >>>= 3, lt -= 3
                    } else {
                      for (kt = _t + 7; lt < kt;) {
                        if (0 === u) break t;
                        u--, f += i[a++] << lt, lt += 8
                      }
                      lt -= _t, xt = 0, dt = 11 + (127 & (f >>>= _t)), f >>>= 7, lt -= 7
                    }
                    if (n.have + dt > n.nlen + n.ndist) {
                      t.msg = "invalid bit length repeat", n.mode = st;
                      break
                    }
                    for (; dt--;) n.lens[n.have++] = xt
                  }
                }
                if (n.mode === st) break;
                if (0 === n.lens[256]) {
                  t.msg = "invalid code -- missing end-of-block", n.mode = st;
                  break
                }
                if (n.lenbits = 9, Ct = {
                  bits: n.lenbits
                }, St = m(y, n.lens, 0, n.nlen, n.lencode, 0, n.work, Ct), n.lenbits = Ct.bits, St) {
                  t.msg = "invalid literal/lengths set", n.mode = st;
                  break
                }
                if (n.distbits = 6, n.distcode = n.distdyn, Ct = {
                  bits: n.distbits
                }, St = m(b, n.lens, n.nlen, n.ndist, n.distcode, 0, n.work, Ct), n.distbits = Ct.bits, St) {
                  t.msg = "invalid distances set", n.mode = st;
                  break
                }
                if (n.mode = J, e === S) break t;
              case J:
                n.mode = Q;
              case Q:
                if (u >= 6 && h >= 258) {
                  t.next_out = s, t.avail_out = h, t.next_in = a, t.avail_in = u, n.hold = f, n.bits = lt, _(t, ft), s = t.next_out, o = t.output, h = t.avail_out, a = t.next_in, i = t.input, u = t.avail_in, f = n.hold, lt = n.bits, n.mode === G && (n.back = -1);
                  break
                }
                for (n.back = 0; Et = n.lencode[f & (1 << n.lenbits) - 1], _t = Et >>> 24, mt = Et >>> 16 & 255, vt = 65535 & Et, !(_t <= lt);) {
                  if (0 === u) break t;
                  u--, f += i[a++] << lt, lt += 8
                }
                if (mt && 0 == (240 & mt)) {
                  for (yt = _t, bt = mt, wt = vt; Et = n.lencode[wt + ((f & (1 << yt + bt) - 1) >> yt)], _t = Et >>> 24, mt = Et >>> 16 & 255, vt = 65535 & Et, !(yt + _t <= lt);) {
                    if (0 === u) break t;
                    u--, f += i[a++] << lt, lt += 8
                  }
                  f >>>= yt, lt -= yt, n.back += yt
                }
                if (f >>>= _t, lt -= _t, n.back += _t, n.length = vt, 0 === mt) {
                  n.mode = rt;
                  break
                }
                if (32 & mt) {
                  n.back = -1, n.mode = G;
                  break
                }
                if (64 & mt) {
                  t.msg = "invalid literal/length code", n.mode = st;
                  break
                }
                n.extra = 15 & mt, n.mode = $;
              case $:
                if (n.extra) {
                  for (kt = n.extra; lt < kt;) {
                    if (0 === u) break t;
                    u--, f += i[a++] << lt, lt += 8
                  }
                  n.length += f & (1 << n.extra) - 1, f >>>= n.extra, lt -= n.extra, n.back += n.extra
                }
                n.was = n.length, n.mode = tt;
              case tt:
                for (; Et = n.distcode[f & (1 << n.distbits) - 1], _t = Et >>> 24, mt = Et >>> 16 & 255, vt = 65535 & Et, !(_t <= lt);) {
                  if (0 === u) break t;
                  u--, f += i[a++] << lt, lt += 8
                }
                if (0 == (240 & mt)) {
                  for (yt = _t, bt = mt, wt = vt; Et = n.distcode[wt + ((f & (1 << yt + bt) - 1) >> yt)], _t = Et >>> 24, mt = Et >>> 16 & 255, vt = 65535 & Et, !(yt + _t <= lt);) {
                    if (0 === u) break t;
                    u--, f += i[a++] << lt, lt += 8
                  }
                  f >>>= yt, lt -= yt, n.back += yt
                }
                if (f >>>= _t, lt -= _t, n.back += _t, 64 & mt) {
                  t.msg = "invalid distance code", n.mode = st;
                  break
                }
                n.offset = vt, n.extra = 15 & mt, n.mode = et;
              case et:
                if (n.extra) {
                  for (kt = n.extra; lt < kt;) {
                    if (0 === u) break t;
                    u--, f += i[a++] << lt, lt += 8
                  }
                  n.offset += f & (1 << n.extra) - 1, f >>>= n.extra, lt -= n.extra, n.back += n.extra
                }
                if (n.offset > n.dmax) {
                  t.msg = "invalid distance too far back", n.mode = st;
                  break
                }
                n.mode = nt;
              case nt:
                if (0 === h) break t;
                if (dt = ft - h, n.offset > dt) {
                  if ((dt = n.offset - dt) > n.whave && n.sane) {
                    t.msg = "invalid distance too far back", n.mode = st;
                    break
                  }
                  dt > n.wnext ? (dt -= n.wnext, pt = n.wsize - dt) : pt = n.wnext - dt, dt > n.length && (dt = n.length), gt = n.window
                } else gt = o, pt = s - n.offset, dt = n.length;
                dt > h && (dt = h), h -= dt, n.length -= dt;
                do {
                  o[s++] = gt[pt++]
                } while (--dt);
                0 === n.length && (n.mode = Q);
                break;
              case rt:
                if (0 === h) break t;
                o[s++] = n.length, h--, n.mode = Q;
                break;
              case it:
                if (n.wrap) {
                  for (; lt < 32;) {
                    if (0 === u) break t;
                    u--, f |= i[a++] << lt, lt += 8
                  }
                  if (ft -= h, t.total_out += ft, n.total += ft, ft && (t.adler = n.check = n.flags ? g(n.check, o, ft, s - ft) : p(n.check, o, ft, s - ft)), ft = h, (n.flags ? f : r(f)) !== n.check) {
                    t.msg = "incorrect data check", n.mode = st;
                    break
                  }
                  f = 0, lt = 0
                }
                n.mode = ot;
              case ot:
                if (n.wrap && n.flags) {
                  for (; lt < 32;) {
                    if (0 === u) break t;
                    u--, f += i[a++] << lt, lt += 8
                  }
                  if (f !== (4294967295 & n.total)) {
                    t.msg = "incorrect length check", n.mode = st;
                    break
                  }
                  f = 0, lt = 0
                }
                n.mode = at;
              case at:
                St = k;
                break t;
              case st:
                St = P;
                break t;
              case ut:
                return j;
              case ct:
              default:
                return A
            }
            return t.next_out = s, t.avail_out = h, t.next_in = a, t.avail_in = u, n.hold = f, n.bits = lt, (n.wsize || ft !== t.avail_out && n.mode < st && (n.mode < it || e !== w)) && l(t, t.output, t.next_out, ft - t.avail_out) ? (n.mode = ut, j) : (ht -= t.avail_in, ft -= t.avail_out, t.total_in += ht, t.total_out += ft, n.total += ft, n.wrap && ft && (t.adler = n.check = n.flags ? g(n.check, o, ft, t.next_out - ft) : p(n.check, o, ft, t.next_out - ft)), t.data_type = n.bits + (n.last ? 64 : 0) + (n.mode === G ? 128 : 0) + (n.mode === J || n.mode === W ? 256 : 0), (0 === ht && 0 === ft || e === w) && St === C && (St = I), St)
          }, n.inflateEnd = function(t) {
            if (!t || !t.state) return A;
            var e = t.state;
            return e.window && (e.window = null), t.state = null, C
          }, n.inflateGetHeader = function(t, e) {
            var n;
            return t && t.state ? 0 == (2 & (n = t.state).wrap) ? A : (n.head = e, e.done = !1, C) : A
          }, n.inflateSetDictionary = function(t, e) {
            var n, r, i = e.length;
            return t && t.state ? 0 !== (n = t.state).wrap && n.mode !== H ? A : n.mode === H && (r = 1, (r = p(r, e, i, 0)) !== n.check) ? P : l(t, e, i, i) ? (n.mode = ut, j) : (n.havedict = 1, C) : A
          }, n.inflateInfo = "pako inflate (from Nodeca project)"
        }, {
          "../utils/common": 193,
          "./adler32": 194,
          "./crc32": 196,
          "./inffast": 198,
          "./inftrees": 200
        }],
        200: [function(t, e, n) {
          "use strict";
          var r = t("../utils/common"),
            i = [3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31, 35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258, 0, 0],
            o = [16, 16, 16, 16, 16, 16, 16, 16, 17, 17, 17, 17, 18, 18, 18, 18, 19, 19, 19, 19, 20, 20, 20, 20, 21, 21, 21, 21, 16, 72, 78],
            a = [1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193, 257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145, 8193, 12289, 16385, 24577, 0, 0],
            s = [16, 16, 16, 16, 17, 17, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24, 25, 25, 26, 26, 27, 27, 28, 28, 29, 29, 64, 64];
          e.localExports = function(t, e, n, u, c, l, h, f) {
            var d, p, g, _, m, v, y, b, w, x = f.bits,
              S = 0,
              C = 0,
              k = 0,
              E = 0,
              A = 0,
              P = 0,
              j = 0,
              I = 0,
              L = 0,
              O = 0,
              T = null,
              B = 0,
              N = new r.Buf16(16),
              z = new r.Buf16(16),
              F = null,
              D = 0;
            for (S = 0; S <= 15; S++) N[S] = 0;
            for (C = 0; C < u; C++) N[e[n + C]]++;
            for (A = x, E = 15; E >= 1 && 0 === N[E]; E--);
            if (A > E && (A = E), 0 === E) return c[l++] = 20971520, c[l++] = 20971520, f.bits = 1, 0;
            for (k = 1; k < E && 0 === N[k]; k++);
            for (A < k && (A = k), I = 1, S = 1; S <= 15; S++)
              if (I <<= 1, (I -= N[S]) < 0) return -1;
            if (I > 0 && (0 === t || 1 !== E)) return -1;
            for (z[1] = 0, S = 1; S < 15; S++) z[S + 1] = z[S] + N[S];
            for (C = 0; C < u; C++) 0 !== e[n + C] && (h[z[e[n + C]]++] = C);
            if (0 === t ? (T = F = h, v = 19) : 1 === t ? (T = i, B -= 257, F = o, D -= 257, v = 256) : (T = a, F = s, v = -1), O = 0, C = 0, S = k, m = l, P = A, j = 0, g = -1, L = 1 << A, _ = L - 1, 1 === t && L > 852 || 2 === t && L > 592) return 1;
            for (;;) {
              0, y = S - j, h[C] < v ? (b = 0, w = h[C]) : h[C] > v ? (b = F[D + h[C]], w = T[B + h[C]]) : (b = 96, w = 0), d = 1 << S - j, k = p = 1 << P;
              do {
                c[m + (O >> j) + (p -= d)] = y << 24 | b << 16 | w | 0
              } while (0 !== p);
              for (d = 1 << S - 1; O & d;) d >>= 1;
              if (0 !== d ? (O &= d - 1, O += d) : O = 0, C++, 0 == --N[S]) {
                if (S === E) break;
                S = e[n + h[C]]
              }
              if (S > A && (O & _) !== g) {
                for (0 === j && (j = A), m += k, I = 1 << (P = S - j); P + j < E && !((I -= N[P + j]) <= 0);) P++, I <<= 1;
                if (L += 1 << P, 1 === t && L > 852 || 2 === t && L > 592) return 1;
                c[g = O & _] = A << 24 | P << 16 | m - l | 0
              }
            }
            return 0 !== O && (c[m + O] = S - j << 24 | 64 << 16 | 0), f.bits = A, 0
          }
        }, {
          "../utils/common": 193
        }],
        201: [function(t, e, n) {
          "use strict";
          e.localExports = {
            2: "need dictionary",
            1: "stream end",
            0: "",
            "-1": "file error",
            "-2": "stream error",
            "-3": "data error",
            "-4": "insufficient memory",
            "-5": "buffer error",
            "-6": "incompatible version"
          }
        }, {}],
        202: [function(t, e, n) {
          "use strict";

          function r(t) {
            for (var e = t.length; --e >= 0;) t[e] = 0
          }

          function i(t, e, n, r, i) {
            this.static_tree = t, this.extra_bits = e, this.extra_base = n, this.elems = r, this.max_length = i, this.has_stree = t && t.length
          }

          function o(t, e) {
            this.dyn_tree = t, this.max_code = 0, this.stat_desc = e
          }

          function a(t) {
            return t < 256 ? et[t] : et[256 + (t >>> 7)]
          }

          function s(t, e) {
            t.pending_buf[t.pending++] = 255 & e, t.pending_buf[t.pending++] = e >>> 8 & 255
          }

          function u(t, e, n) {
            t.bi_valid > G - n ? (t.bi_buf |= e << t.bi_valid & 65535, s(t, t.bi_buf), t.bi_buf = e >> G - t.bi_valid, t.bi_valid += n - G) : (t.bi_buf |= e << t.bi_valid & 65535, t.bi_valid += n)
          }

          function c(t, e, n) {
            u(t, n[2 * e], n[2 * e + 1])
          }

          function l(t, e) {
            var n = 0;
            do {
              n |= 1 & t, t >>>= 1, n <<= 1
            } while (--e > 0);
            return n >>> 1
          }

          function h(t) {
            16 === t.bi_valid ? (s(t, t.bi_buf), t.bi_buf = 0, t.bi_valid = 0) : t.bi_valid >= 8 && (t.pending_buf[t.pending++] = 255 & t.bi_buf, t.bi_buf >>= 8, t.bi_valid -= 8)
          }

          function f(t, e) {
            var n, r, i, o, a, s, u = e.dyn_tree,
              c = e.max_code,
              l = e.stat_desc.static_tree,
              h = e.stat_desc.has_stree,
              f = e.stat_desc.extra_bits,
              d = e.stat_desc.extra_base,
              p = e.stat_desc.max_length,
              g = 0;
            for (o = 0; o <= H; o++) t.bl_count[o] = 0;
            for (u[2 * t.heap[t.heap_max] + 1] = 0, n = t.heap_max + 1; n < U; n++)(o = u[2 * u[2 * (r = t.heap[n]) + 1] + 1] + 1) > p && (o = p, g++), u[2 * r + 1] = o, r > c || (t.bl_count[o]++, a = 0, r >= d && (a = f[r - d]), s = u[2 * r], t.opt_len += s * (o + a), h && (t.static_len += s * (l[2 * r + 1] + a)));
            if (0 !== g) {
              do {
                for (o = p - 1; 0 === t.bl_count[o];) o--;
                t.bl_count[o]--, t.bl_count[o + 1] += 2, t.bl_count[p]--, g -= 2
              } while (g > 0);
              for (o = p; 0 !== o; o--)
                for (r = t.bl_count[o]; 0 !== r;)(i = t.heap[--n]) > c || (u[2 * i + 1] !== o && (t.opt_len += (o - u[2 * i + 1]) * u[2 * i], u[2 * i + 1] = o), r--)
            }
          }

          function d(t, e, n) {
            var r, i, o = new Array(H + 1),
              a = 0;
            for (r = 1; r <= H; r++) o[r] = a = a + n[r - 1] << 1;
            for (i = 0; i <= e; i++) {
              var s = t[2 * i + 1];
              0 !== s && (t[2 * i] = l(o[s]++, s))
            }
          }

          function p() {
            var t, e, n, r, o, a = new Array(H + 1);
            for (n = 0, r = 0; r < z - 1; r++)
              for (rt[r] = n, t = 0; t < 1 << K[r]; t++) nt[n++] = r;
            for (nt[n - 1] = r, o = 0, r = 0; r < 16; r++)
              for (it[r] = o, t = 0; t < 1 << X[r]; t++) et[o++] = r;
            for (o >>= 7; r < M; r++)
              for (it[r] = o << 7, t = 0; t < 1 << X[r] - 7; t++) et[256 + o++] = r;
            for (e = 0; e <= H; e++) a[e] = 0;
            for (t = 0; t <= 143;) $[2 * t + 1] = 8, t++, a[8]++;
            for (; t <= 255;) $[2 * t + 1] = 9, t++, a[9]++;
            for (; t <= 279;) $[2 * t + 1] = 7, t++, a[7]++;
            for (; t <= 287;) $[2 * t + 1] = 8, t++, a[8]++;
            for (d($, D + 1, a), t = 0; t < M; t++) tt[2 * t + 1] = 5, tt[2 * t] = l(t, 5);
            ot = new i($, K, F + 1, D, H), at = new i(tt, X, 0, M, H), st = new i(new Array(0), J, 0, R, q)
          }

          function g(t) {
            var e;
            for (e = 0; e < D; e++) t.dyn_ltree[2 * e] = 0;
            for (e = 0; e < M; e++) t.dyn_dtree[2 * e] = 0;
            for (e = 0; e < R; e++) t.bl_tree[2 * e] = 0;
            t.dyn_ltree[2 * Z] = 1, t.opt_len = t.static_len = 0, t.last_lit = t.matches = 0
          }

          function _(t) {
            t.bi_valid > 8 ? s(t, t.bi_buf) : t.bi_valid > 0 && (t.pending_buf[t.pending++] = t.bi_buf), t.bi_buf = 0, t.bi_valid = 0
          }

          function m(t, e, n, r) {
            _(t), r && (s(t, n), s(t, ~n)), P.arraySet(t.pending_buf, t.window, e, n, t.pending), t.pending += n
          }

          function v(t, e, n, r) {
            var i = 2 * e,
              o = 2 * n;
            return t[i] < t[o] || t[i] === t[o] && r[e] <= r[n]
          }

          function y(t, e, n) {
            for (var r = t.heap[n], i = n << 1; i <= t.heap_len && (i < t.heap_len && v(e, t.heap[i + 1], t.heap[i], t.depth) && i++, !v(e, r, t.heap[i], t.depth));) t.heap[n] = t.heap[i], n = i, i <<= 1;
            t.heap[n] = r
          }

          function b(t, e, n) {
            var r, i, o, s, l = 0;
            if (0 !== t.last_lit)
              do {
                r = t.pending_buf[t.d_buf + 2 * l] << 8 | t.pending_buf[t.d_buf + 2 * l + 1], i = t.pending_buf[t.l_buf + l], l++, 0 === r ? c(t, i, e) : (c(t, (o = nt[i]) + F + 1, e), 0 !== (s = K[o]) && u(t, i -= rt[o], s), c(t, o = a(--r), n), 0 !== (s = X[o]) && u(t, r -= it[o], s))
              } while (l < t.last_lit);
            c(t, Z, e)
          }

          function w(t, e) {
            var n, r, i, o = e.dyn_tree,
              a = e.stat_desc.static_tree,
              s = e.stat_desc.has_stree,
              u = e.stat_desc.elems,
              c = -1;
            for (t.heap_len = 0, t.heap_max = U, n = 0; n < u; n++) 0 !== o[2 * n] ? (t.heap[++t.heap_len] = c = n, t.depth[n] = 0) : o[2 * n + 1] = 0;
            for (; t.heap_len < 2;) o[2 * (i = t.heap[++t.heap_len] = c < 2 ? ++c : 0)] = 1, t.depth[i] = 0, t.opt_len--, s && (t.static_len -= a[2 * i + 1]);
            for (e.max_code = c, n = t.heap_len >> 1; n >= 1; n--) y(t, o, n);
            i = u;
            do {
              n = t.heap[1], t.heap[1] = t.heap[t.heap_len--], y(t, o, 1), r = t.heap[1], t.heap[--t.heap_max] = n, t.heap[--t.heap_max] = r, o[2 * i] = o[2 * n] + o[2 * r], t.depth[i] = (t.depth[n] >= t.depth[r] ? t.depth[n] : t.depth[r]) + 1, o[2 * n + 1] = o[2 * r + 1] = i, t.heap[1] = i++, y(t, o, 1)
            } while (t.heap_len >= 2);
            t.heap[--t.heap_max] = t.heap[1], f(t, e), d(o, c, t.bl_count)
          }

          function x(t, e, n) {
            var r, i, o = -1,
              a = e[1],
              s = 0,
              u = 7,
              c = 4;
            for (0 === a && (u = 138, c = 3), e[2 * (n + 1) + 1] = 65535, r = 0; r <= n; r++) i = a, a = e[2 * (r + 1) + 1], ++s < u && i === a || (s < c ? t.bl_tree[2 * i] += s : 0 !== i ? (i !== o && t.bl_tree[2 * i]++, t.bl_tree[2 * W]++) : s <= 10 ? t.bl_tree[2 * V]++ : t.bl_tree[2 * Y]++, s = 0, o = i, 0 === a ? (u = 138, c = 3) : i === a ? (u = 6, c = 3) : (u = 7, c = 4))
          }

          function S(t, e, n) {
            var r, i, o = -1,
              a = e[1],
              s = 0,
              l = 7,
              h = 4;
            for (0 === a && (l = 138, h = 3), r = 0; r <= n; r++)
              if (i = a, a = e[2 * (r + 1) + 1], !(++s < l && i === a)) {
                if (s < h)
                  do {
                    c(t, i, t.bl_tree)
                  } while (0 != --s);
                else 0 !== i ? (i !== o && (c(t, i, t.bl_tree), s--), c(t, W, t.bl_tree), u(t, s - 3, 2)) : s <= 10 ? (c(t, V, t.bl_tree), u(t, s - 3, 3)) : (c(t, Y, t.bl_tree), u(t, s - 11, 7));
                s = 0, o = i, 0 === a ? (l = 138, h = 3) : i === a ? (l = 6, h = 3) : (l = 7, h = 4)
              }
          }

          function C(t) {
            var e;
            for (x(t, t.dyn_ltree, t.l_desc.max_code), x(t, t.dyn_dtree, t.d_desc.max_code), w(t, t.bl_desc), e = R - 1; e >= 3 && 0 === t.bl_tree[2 * Q[e] + 1]; e--);
            return t.opt_len += 3 * (e + 1) + 5 + 5 + 4, e
          }

          function k(t, e, n, r) {
            var i;
            for (u(t, e - 257, 5), u(t, n - 1, 5), u(t, r - 4, 4), i = 0; i < r; i++) u(t, t.bl_tree[2 * Q[i] + 1], 3);
            S(t, t.dyn_ltree, e - 1), S(t, t.dyn_dtree, n - 1)
          }

          function E(t) {
            var e, n = 4093624447;
            for (e = 0; e <= 31; e++, n >>>= 1)
              if (1 & n && 0 !== t.dyn_ltree[2 * e]) return I;
            if (0 !== t.dyn_ltree[18] || 0 !== t.dyn_ltree[20] || 0 !== t.dyn_ltree[26]) return L;
            for (e = 32; e < F; e++)
              if (0 !== t.dyn_ltree[2 * e]) return L;
            return I
          }

          function A(t, e, n, r) {
            u(t, (T << 1) + (r ? 1 : 0), 3), m(t, e, n, !0)
          }
          var P = t("../utils/common"),
            j = 4,
            I = 0,
            L = 1,
            O = 2,
            T = 0,
            B = 1,
            N = 2,
            z = 29,
            F = 256,
            D = F + 1 + z,
            M = 30,
            R = 19,
            U = 2 * D + 1,
            H = 15,
            G = 16,
            q = 7,
            Z = 256,
            W = 16,
            V = 17,
            Y = 18,
            K = [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0],
            X = [0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13],
            J = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 7],
            Q = [16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15],
            $ = new Array(2 * (D + 2));
          r($);
          var tt = new Array(2 * M);
          r(tt);
          var et = new Array(512);
          r(et);
          var nt = new Array(256);
          r(nt);
          var rt = new Array(z);
          r(rt);
          var it = new Array(M);
          r(it);
          var ot, at, st, ut = !1;
          n._tr_init = function(t) {
            ut || (p(), ut = !0), t.l_desc = new o(t.dyn_ltree, ot), t.d_desc = new o(t.dyn_dtree, at), t.bl_desc = new o(t.bl_tree, st), t.bi_buf = 0, t.bi_valid = 0, g(t)
          }, n._tr_stored_block = A, n._tr_flush_block = function(t, e, n, r) {
            var i, o, a = 0;
            t.level > 0 ? (t.strm.data_type === O && (t.strm.data_type = E(t)), w(t, t.l_desc), w(t, t.d_desc), a = C(t), i = t.opt_len + 3 + 7 >>> 3, (o = t.static_len + 3 + 7 >>> 3) <= i && (i = o)) : i = o = n + 5, n + 4 <= i && -1 !== e ? A(t, e, n, r) : t.strategy === j || o === i ? (u(t, (B << 1) + (r ? 1 : 0), 3), b(t, $, tt)) : (u(t, (N << 1) + (r ? 1 : 0), 3), k(t, t.l_desc.max_code + 1, t.d_desc.max_code + 1, a + 1), b(t, t.dyn_ltree, t.dyn_dtree)), g(t), r && _(t)
          }, n._tr_tally = function(t, e, n) {
            return t.pending_buf[t.d_buf + 2 * t.last_lit] = e >>> 8 & 255, t.pending_buf[t.d_buf + 2 * t.last_lit + 1] = 255 & e, t.pending_buf[t.l_buf + t.last_lit] = 255 & n, t.last_lit++, 0 === e ? t.dyn_ltree[2 * n]++ : (t.matches++, e--, t.dyn_ltree[2 * (nt[n] + F + 1)]++, t.dyn_dtree[2 * a(e)]++), t.last_lit === t.lit_bufsize - 1
          }, n._tr_align = function(t) {
            u(t, B << 1, 3), c(t, Z, $), h(t)
          }
        }, {
          "../utils/common": 193
        }],
        203: [function(t, e, n) {
          "use strict";
          e.localExports = function() {
            this.input = null, this.next_in = 0, this.avail_in = 0, this.total_in = 0, this.output = null, this.next_out = 0, this.avail_out = 0, this.total_out = 0, this.msg = "", this.state = null, this.data_type = 2, this.adler = 0
          }
        }, {}],
        204: [function(t, e, n) {
          (function(n) {
            (function() {
              var r, i;
              r = t("fs"), i = t("zlib"), e.localExports = function() {
                function t(t) {
                  var e, r, i, o, a, s, u, c, l, h;
                  for (this.data = t, this.pos = 8, this.palette = [], this.imgData = [], this.transparency = {}, this.text = {};;) {
                    switch (e = this.readUInt32(), function() {
                      var t, e;
                      for (e = [], i = t = 0; t < 4; i = ++t) e.push(String.fromCharCode(this.data[this.pos++]));
                      return e
                    }.call(this).join("")) {
                      case "IHDR":
                        this.width = this.readUInt32(), this.height = this.readUInt32(), this.bits = this.data[this.pos++], this.colorType = this.data[this.pos++], this.compressionMethod = this.data[this.pos++], this.filterMethod = this.data[this.pos++], this.interlaceMethod = this.data[this.pos++];
                        break;
                      case "PLTE":
                        this.palette = this.read(e);
                        break;
                      case "IDAT":
                        for (i = c = 0; c < e; i = c += 1) this.imgData.push(this.data[this.pos++]);
                        break;
                      case "tRNS":
                        switch (this.transparency = {}, this.colorType) {
                          case 3:
                            if (this.transparency.indexed = this.read(e), (s = 255 - this.transparency.indexed.length) > 0)
                              for (i = l = 0; 0 <= s ? l < s : l > s; i = 0 <= s ? ++l : --l) this.transparency.indexed.push(255);
                            break;
                          case 0:
                            this.transparency.grayscale = this.read(e)[0];
                            break;
                          case 2:
                            this.transparency.rgb = this.read(e)
                        }
                        break;
                      case "tEXt":
                        o = (u = this.read(e)).indexOf(0), a = String.fromCharCode.apply(String, u.slice(0, o)), this.text[a] = String.fromCharCode.apply(String, u.slice(o + 1));
                        break;
                      case "IEND":
                        return this.colors = function() {
                          switch (this.colorType) {
                            case 0:
                            case 3:
                            case 4:
                              return 1;
                            case 2:
                            case 6:
                              return 3
                          }
                        }.call(this), this.hasAlphaChannel = 4 === (h = this.colorType) || 6 === h, r = this.colors + (this.hasAlphaChannel ? 1 : 0), this.pixelBitlength = this.bits * r, this.colorSpace = function() {
                          switch (this.colors) {
                            case 1:
                              return "DeviceGray";
                            case 3:
                              return "DeviceRGB"
                          }
                        }.call(this), void(this.imgData = new n(this.imgData));
                      default:
                        this.pos += e
                    }
                    if (this.pos += 4, this.pos > this.data.length) throw new Error("Incomplete or corrupt PNG file")
                  }
                }
                return t.decode = function(e, n) {
                  return r.readFile(e, function(e, r) {
                    return new t(r).decode(function(t) {
                      return n(t)
                    })
                  })
                }, t.load = function(e) {
                  var n;
                  return n = r.readFileSync(e), new t(n)
                }, t.prototype.read = function(t) {
                  var e, n;
                  for (n = [], e = 0; 0 <= t ? e < t : e > t; 0 <= t ? ++e : --e) n.push(this.data[this.pos++]);
                  return n
                }, t.prototype.readUInt32 = function() {
                  var t, e, n, r;
                  return t = this.data[this.pos++] << 24, e = this.data[this.pos++] << 16, n = this.data[this.pos++] << 8, r = this.data[this.pos++], t | e | n | r
                }, t.prototype.readUInt16 = function() {
                  var t, e;
                  return t = this.data[this.pos++] << 8, e = this.data[this.pos++], t | e
                }, t.prototype.decodePixels = function(t) {
                  var e = this;
                  return i.inflate(this.imgData, function(r, i) {
                    var o, a, s, u, c, l, h, f, d, p, g, _, m, v, y, b, w, x, S, C, k, E, A;
                    if (r) throw r;
                    for (b = (_ = e.pixelBitlength / 8) * e.width, m = new n(b * e.height), l = i.length, y = 0, v = 0, a = 0; v < l;) {
                      switch (i[v++]) {
                        case 0:
                          for (u = S = 0; S < b; u = S += 1) m[a++] = i[v++];
                          break;
                        case 1:
                          for (u = C = 0; C < b; u = C += 1) o = i[v++], c = u < _ ? 0 : m[a - _], m[a++] = (o + c) % 256;
                          break;
                        case 2:
                          for (u = k = 0; k < b; u = k += 1) o = i[v++], s = (u - u % _) / _, w = y && m[(y - 1) * b + s * _ + u % _], m[a++] = (w + o) % 256;
                          break;
                        case 3:
                          for (u = E = 0; E < b; u = E += 1) o = i[v++], s = (u - u % _) / _, c = u < _ ? 0 : m[a - _], w = y && m[(y - 1) * b + s * _ + u % _], m[a++] = (o + Math.floor((c + w) / 2)) % 256;
                          break;
                        case 4:
                          for (u = A = 0; A < b; u = A += 1) o = i[v++], s = (u - u % _) / _, c = u < _ ? 0 : m[a - _], 0 === y ? w = x = 0 : (w = m[(y - 1) * b + s * _ + u % _], x = s && m[(y - 1) * b + (s - 1) * _ + u % _]), h = c + w - x, f = Math.abs(h - c), p = Math.abs(h - w), g = Math.abs(h - x), d = f <= p && f <= g ? c : p <= g ? w : x, m[a++] = (o + d) % 256;
                          break;
                        default:
                          throw new Error("Invalid filter algorithm: " + i[v - 1])
                      }
                      y++
                    }
                    return t(m)
                  })
                }, t.prototype.decodePalette = function() {
                  var t, e, r, i, o, a, s, u, c;
                  for (r = this.palette, a = this.transparency.indexed || [], o = new n(a.length + r.length), i = 0, r.length, t = 0, e = s = 0, u = r.length; s < u; e = s += 3) o[i++] = r[e], o[i++] = r[e + 1], o[i++] = r[e + 2], o[i++] = null != (c = a[t++]) ? c : 255;
                  return o
                }, t.prototype.copyToImageData = function(t, e) {
                  var n, r, i, o, a, s, u, c, l, h, f;
                  if (r = this.colors, l = null, n = this.hasAlphaChannel, this.palette.length && (l = null != (f = this._decodedPalette) ? f : this._decodedPalette = this.decodePalette(), r = 4, n = !0), i = (null != t ? t.data : void 0) || t, c = i.length, a = l || e, o = s = 0, 1 === r)
                    for (; o < c;) u = l ? 4 * e[o / 4] : s, h = a[u++], i[o++] = h, i[o++] = h, i[o++] = h, i[o++] = n ? a[u++] : 255, s = u;
                  else
                    for (; o < c;) u = l ? 4 * e[o / 4] : s, i[o++] = a[u++], i[o++] = a[u++], i[o++] = a[u++], i[o++] = n ? a[u++] : 255, s = u
                }, t.prototype.decode = function(t) {
                  var e, r = this;
                  return e = new n(this.width * this.height * 4), this.decodePixels(function(n) {
                    return r.copyToImageData(e, n), t(e)
                  })
                }, t
              }()
            }).call(this)
          }).call(this, t("buffer").Buffer)
        }, {
          buffer: 31,
          fs: 29,
          zlib: 28
        }],
        205: [function(t, e, n) {
          function r() {}
          var i = e.localExports = {};
          i.nextTick = function() {
            var t = "undefined" != typeof window && window.setImmediate,
              e = "undefined" != typeof window && window.postMessage && window.addEventListener;
            if (t) return function(t) {
              return window.setImmediate(t)
            };
            if (e) {
              var n = [];
              return window.addEventListener("message", function(t) {
                var e = t.source;
                e !== window && null !== e || "process-tick" !== t.data || (t.stopPropagation(), n.length > 0 && n.shift()())
              }, !0),
                function(t) {
                  n.push(t), window.postMessage("process-tick", "*")
                }
            }
            return function(t) {
              setTimeout(t, 0)
            }
          }(), i.title = "browser", i.browser = !0, i.env = {}, i.argv = [], i.on = r, i.addListener = r, i.once = r, i.off = r, i.removeListener = r, i.removeAllListeners = r, i.emit = r, i.binding = function(t) {
            throw new Error("process.binding is not supported")
          }, i.cwd = function() {
            return "/"
          }, i.chdir = function(t) {
            throw new Error("process.chdir is not supported")
          }
        }, {}],
        206: [function(t, e, n) {
          function r(t) {
            if (!(this instanceof r)) return new r(t);
            s.call(this, t), u.call(this, t), t && !1 === t.readable && (this.readable = !1), t && !1 === t.writable && (this.writable = !1), this.allowHalfOpen = !0, t && !1 === t.allowHalfOpen && (this.allowHalfOpen = !1), this.once("end", i)
          }

          function i() {
            if (!this.allowHalfOpen && !this._writableState.ended) {
              var t = this;
              a(function() {
                t.end()
              })
            }
          }
          e.localExports = r;
          var o = t("inherits"),
            a = t("process/browser.js").nextTick,
            s = t("./readable.js"),
            u = t("./writable.js");
          o(r, s), r.prototype.write = u.prototype.write, r.prototype.end = u.prototype.end, r.prototype._write = u.prototype._write
        }, {
          "./readable.js": 210,
          "./writable.js": 212,
          inherits: 189,
          "process/browser.js": 208
        }],
        207: [function(t, e, n) {
          function r() {
            i.call(this)
          }
          e.localExports = r;
          var i = t("events").EventEmitter;
          t("inherits")(r, i), r.Readable = t("./readable.js"), r.Writable = t("./writable.js"), r.Duplex = t("./duplex.js"), r.Transform = t("./transform.js"), r.PassThrough = t("./passthrough.js"), r.Stream = r, r.prototype.pipe = function(t, e) {
            function n(e) {
              t.writable && !1 === t.write(e) && c.pause && c.pause()
            }

            function r() {
              c.readable && c.resume && c.resume()
            }

            function o() {
              l || (l = !0, t.end())
            }

            function a() {
              l || (l = !0, "function" == typeof t.destroy && t.destroy())
            }

            function s(t) {
              if (u(), 0 === i.listenerCount(this, "error")) throw t
            }

            function u() {
              c.removeListener("data", n), t.removeListener("drain", r), c.removeListener("end", o), c.removeListener("close", a), c.removeListener("error", s), t.removeListener("error", s), c.removeListener("end", u), c.removeListener("close", u), t.removeListener("close", u)
            }
            var c = this;
            c.on("data", n), t.on("drain", r), t._isStdio || e && !1 === e.end || (c.on("end", o), c.on("close", a));
            var l = !1;
            return c.on("error", s), t.on("error", s), c.on("end", u), c.on("close", u), t.on("close", u), t.emit("pipe", c), t
          }
        }, {
          "./duplex.js": 206,
          "./passthrough.js": 209,
          "./readable.js": 210,
          "./transform.js": 211,
          "./writable.js": 212,
          events: 32,
          inherits: 189
        }],
        208: [function(t, e, n) {
          var r = e.localExports = {};
          r.nextTick = function() {
            var t = "undefined" != typeof window && window.setImmediate,
              e = "undefined" != typeof window && window.postMessage && window.addEventListener;
            if (t) return function(t) {
              return window.setImmediate(t)
            };
            if (e) {
              var n = [];
              return window.addEventListener("message", function(t) {
                var e = t.source;
                e !== window && null !== e || "process-tick" !== t.data || (t.stopPropagation(), n.length > 0 && n.shift()())
              }, !0),
                function(t) {
                  n.push(t), window.postMessage("process-tick", "*")
                }
            }
            return function(t) {
              setTimeout(t, 0)
            }
          }(), r.title = "browser", r.browser = !0, r.env = {}, r.argv = [], r.binding = function(t) {
            throw new Error("process.binding is not supported")
          }, r.cwd = function() {
            return "/"
          }, r.chdir = function(t) {
            throw new Error("process.chdir is not supported")
          }
        }, {}],
        209: [function(t, e, n) {
          function r(t) {
            if (!(this instanceof r)) return new r(t);
            i.call(this, t)
          }
          e.localExports = r;
          var i = t("./transform.js");
          t("inherits")(r, i), r.prototype._transform = function(t, e, n) {
            n(null, t)
          }
        }, {
          "./transform.js": 211,
          inherits: 189
        }],
        210: [function(t, e, n) {
          (function(n) {
            function r(e, n) {
              var r = (e = e || {}).highWaterMark;
              this.highWaterMark = r || 0 === r ? r : 16384, this.highWaterMark = ~~this.highWaterMark, this.buffer = [], this.length = 0, this.pipes = null, this.pipesCount = 0, this.flowing = !1, this.ended = !1, this.endEmitted = !1, this.reading = !1, this.calledRead = !1, this.sync = !0, this.needReadable = !1, this.emittedReadable = !1, this.readableListening = !1, this.objectMode = !!e.objectMode, this.defaultEncoding = e.defaultEncoding || "utf8", this.ranOut = !1, this.awaitDrain = 0, this.readingMore = !1, this.decoder = null, this.encoding = null, e.encoding && (S || (S = t("string_decoder").StringDecoder), this.decoder = new S(e.encoding), this.encoding = e.encoding)
            }

            function i(t) {
              if (!(this instanceof i)) return new i(t);
              this._readableState = new r(t, this), this.readable = !0, k.call(this)
            }

            function o(t, e, n, r, i) {
              var o = c(e, n);
              if (o) t.emit("error", o);
              else if (null === n || void 0 === n) e.reading = !1, e.ended || l(t, e);
              else if (e.objectMode || n && n.length > 0)
                if (e.ended && !i) {
                  s = new Error("stream.push() after EOF");
                  t.emit("error", s)
                } else if (e.endEmitted && i) {
                  var s = new Error("stream.unshift() after end event");
                  t.emit("error", s)
                } else !e.decoder || i || r || (n = e.decoder.write(n)), e.length += e.objectMode ? 1 : n.length, i ? e.buffer.unshift(n) : (e.reading = !1, e.buffer.push(n)), e.needReadable && h(t), d(t, e);
              else i || (e.reading = !1);
              return a(e)
            }

            function a(t) {
              return !t.ended && (t.needReadable || t.length < t.highWaterMark || 0 === t.length)
            }

            function s(t) {
              if (t >= P) t = P;
              else {
                t--;
                for (var e = 1; e < 32; e <<= 1) t |= t >> e;
                t++
              }
              return t
            }

            function u(t, e) {
              return 0 === e.length && e.ended ? 0 : e.objectMode ? 0 === t ? 0 : 1 : isNaN(t) || null === t ? e.flowing && e.buffer.length ? e.buffer[0].length : e.length : t <= 0 ? 0 : (t > e.highWaterMark && (e.highWaterMark = s(t)), t > e.length ? e.ended ? e.length : (e.needReadable = !0, 0) : t)
            }

            function c(t, e) {
              var n = null;
              return E.isBuffer(e) || "string" == typeof e || null === e || void 0 === e || t.objectMode || n || (n = new TypeError("Invalid non-string/buffer chunk")), n
            }

            function l(t, e) {
              if (e.decoder && !e.ended) {
                var n = e.decoder.end();
                n && n.length && (e.buffer.push(n), e.length += e.objectMode ? 1 : n.length)
              }
              e.ended = !0, e.length > 0 ? h(t) : b(t)
            }

            function h(t, e) {
              var n = t._readableState;
              n.needReadable = !1, n.emittedReadable || (n.emittedReadable = !0, n.sync ? A(function() {
                f(t)
              }) : f(t))
            }

            function f(t) {
              t.emit("readable")
            }

            function d(t, e) {
              e.readingMore || (e.readingMore = !0, A(function() {
                p(t, e)
              }))
            }

            function p(t, e) {
              for (var n = e.length; !e.reading && !e.flowing && !e.ended && e.length < e.highWaterMark && (t.read(0), n !== e.length);) n = e.length;
              e.readingMore = !1
            }

            function g(t) {
              return function() {
                var e = t._readableState;
                e.awaitDrain--, 0 === e.awaitDrain && _(t)
              }
            }

            function _(t) {
              function e(t, e, i) {
                !1 === t.write(n) && r.awaitDrain++
              }
              var n, r = t._readableState;
              for (r.awaitDrain = 0; r.pipesCount && null !== (n = t.read());)
                if (1 === r.pipesCount ? e(r.pipes) : w(r.pipes, e), t.emit("data", n), r.awaitDrain > 0) return;
              if (0 === r.pipesCount) return r.flowing = !1, void(C.listenerCount(t, "data") > 0 && v(t));
              r.ranOut = !0
            }

            function m() {
              this._readableState.ranOut && (this._readableState.ranOut = !1, _(this))
            }

            function v(t, e) {
              if (t._readableState.flowing) throw new Error("Cannot switch to old mode now.");
              var n = e || !1,
                r = !1;
              t.readable = !0, t.pipe = k.prototype.pipe, t.on = t.addListener = k.prototype.on, t.on("readable", function() {
                r = !0;
                for (var e; !n && null !== (e = t.read());) t.emit("data", e);
                null === e && (r = !1, t._readableState.needReadable = !0)
              }), t.pause = function() {
                n = !0, this.emit("pause")
              }, t.resume = function() {
                n = !1, r ? A(function() {
                  t.emit("readable")
                }) : this.read(0), this.emit("resume")
              }, t.emit("readable")
            }

            function y(t, e) {
              var n, r = e.buffer,
                i = e.length,
                o = !!e.decoder,
                a = !!e.objectMode;
              if (0 === r.length) return null;
              if (0 === i) n = null;
              else if (a) n = r.shift();
              else if (!t || t >= i) n = o ? r.join("") : E.concat(r, i), r.length = 0;
              else if (t < r[0].length) n = (l = r[0]).slice(0, t), r[0] = l.slice(t);
              else if (t === r[0].length) n = r.shift();
              else {
                n = o ? "" : new E(t);
                for (var s = 0, u = 0, c = r.length; u < c && s < t; u++) {
                  var l = r[0],
                    h = Math.min(t - s, l.length);
                  o ? n += l.slice(0, h) : l.copy(n, s, 0, h), h < l.length ? r[0] = l.slice(h) : r.shift(), s += h
                }
              }
              return n
            }

            function b(t) {
              var e = t._readableState;
              if (e.length > 0) throw new Error("endReadable called on non-empty stream");
              !e.endEmitted && e.calledRead && (e.ended = !0, A(function() {
                e.endEmitted || 0 !== e.length || (e.endEmitted = !0, t.readable = !1, t.emit("end"))
              }))
            }

            function w(t, e) {
              for (var n = 0, r = t.length; n < r; n++) e(t[n], n)
            }

            function x(t, e) {
              for (var n = 0, r = t.length; n < r; n++)
                if (t[n] === e) return n;
              return -1
            }
            e.localExports = i, i.ReadableState = r;
            var S, C = t("events").EventEmitter,
              k = t("./index.js"),
              E = t("buffer").Buffer,
              A = t("process/browser.js").nextTick;
            t("inherits")(i, k), i.prototype.push = function(t, e) {
              var n = this._readableState;
              return "string" != typeof t || n.objectMode || (e = e || n.defaultEncoding) !== n.encoding && (t = new E(t, e), e = ""), o(this, n, t, e, !1)
            }, i.prototype.unshift = function(t) {
              return o(this, this._readableState, t, "", !0)
            }, i.prototype.setEncoding = function(e) {
              S || (S = t("string_decoder").StringDecoder), this._readableState.decoder = new S(e), this._readableState.encoding = e
            };
            var P = 8388608;
            i.prototype.read = function(t) {
              var e = this._readableState;
              e.calledRead = !0;
              var n = t;
              if (("number" != typeof t || t > 0) && (e.emittedReadable = !1), 0 === t && e.needReadable && (e.length >= e.highWaterMark || e.ended)) return h(this), null;
              if (0 === (t = u(t, e)) && e.ended) return 0 === e.length && b(this), null;
              var r = e.needReadable;
              e.length - t <= e.highWaterMark && (r = !0), (e.ended || e.reading) && (r = !1), r && (e.reading = !0, e.sync = !0, 0 === e.length && (e.needReadable = !0), this._read(e.highWaterMark), e.sync = !1), r && !e.reading && (t = u(n, e));
              var i;
              return null === (i = t > 0 ? y(t, e) : null) && (e.needReadable = !0, t = 0), e.length -= t, 0 !== e.length || e.ended || (e.needReadable = !0), e.ended && !e.endEmitted && 0 === e.length && b(this), i
            }, i.prototype._read = function(t) {
              this.emit("error", new Error("not implemented"))
            }, i.prototype.pipe = function(t, e) {
              function r(t) {
                t === l && o()
              }

              function i() {
                t.end()
              }

              function o() {
                t.removeListener("close", s), t.removeListener("finish", u), t.removeListener("drain", d), t.removeListener("error", a), t.removeListener("unpipe", r), l.removeListener("end", i), l.removeListener("end", o), t._writableState && !t._writableState.needDrain || d()
              }

              function a(e) {
                c(), 0 === p && 0 === C.listenerCount(t, "error") && t.emit("error", e)
              }

              function s() {
                t.removeListener("finish", u), c()
              }

              function u() {
                t.removeListener("close", s), c()
              }

              function c() {
                l.unpipe(t)
              }
              var l = this,
                h = this._readableState;
              switch (h.pipesCount) {
                case 0:
                  h.pipes = t;
                  break;
                case 1:
                  h.pipes = [h.pipes, t];
                  break;
                default:
                  h.pipes.push(t)
              }
              h.pipesCount += 1;
              var f = (!e || !1 !== e.end) && t !== n.stdout && t !== n.stderr ? i : o;
              h.endEmitted ? A(f) : l.once("end", f), t.on("unpipe", r);
              var d = g(l);
              t.on("drain", d);
              var p = C.listenerCount(t, "error");
              return t.once("error", a), t.once("close", s), t.once("finish", u), t.emit("pipe", l), h.flowing || (this.on("readable", m), h.flowing = !0, A(function() {
                _(l)
              })), t
            }, i.prototype.unpipe = function(t) {
              var e = this._readableState;
              if (0 === e.pipesCount) return this;
              if (1 === e.pipesCount) return t && t !== e.pipes ? this : (t || (t = e.pipes), e.pipes = null, e.pipesCount = 0, this.removeListener("readable", m), e.flowing = !1, t && t.emit("unpipe", this), this);
              if (!t) {
                var n = e.pipes,
                  r = e.pipesCount;
                e.pipes = null, e.pipesCount = 0, this.removeListener("readable", m), e.flowing = !1;
                for (var i = 0; i < r; i++) n[i].emit("unpipe", this);
                return this
              }
              return -1 === (i = x(e.pipes, t)) ? this : (e.pipes.splice(i, 1), e.pipesCount -= 1, 1 === e.pipesCount && (e.pipes = e.pipes[0]), t.emit("unpipe", this), this)
            }, i.prototype.on = function(t, e) {
              var n = k.prototype.on.call(this, t, e);
              if ("data" !== t || this._readableState.flowing || v(this), "readable" === t && this.readable) {
                var r = this._readableState;
                r.readableListening || (r.readableListening = !0, r.emittedReadable = !1, r.needReadable = !0, r.reading ? r.length && h(this) : this.read(0))
              }
              return n
            }, i.prototype.addListener = i.prototype.on, i.prototype.resume = function() {
              v(this), this.read(0), this.emit("resume")
            }, i.prototype.pause = function() {
              v(this, !0), this.emit("pause")
            }, i.prototype.wrap = function(t) {
              var e = this._readableState,
                n = !1,
                r = this;
              t.on("end", function() {
                if (e.decoder && !e.ended) {
                  var t = e.decoder.end();
                  t && t.length && r.push(t)
                }
                r.push(null)
              }), t.on("data", function(i) {
                e.decoder && (i = e.decoder.write(i)), i && (e.objectMode || i.length) && (r.push(i) || (n = !0, t.pause()))
              });
              for (var i in t) "function" == typeof t[i] && void 0 === this[i] && (this[i] = function(e) {
                return function() {
                  return t[e].apply(t, arguments)
                }
              }(i));
              return w(["error", "close", "destroy", "pause", "resume"], function(e) {
                t.on(e, function(t) {
                  return r.emit.apply(r, e, t)
                })
              }), r._read = function(e) {
                n && (n = !1, t.resume())
              }, r
            }, i._fromList = y
          }).call(this, t("v3go1D"))
        }, {
          "./index.js": 207,
          buffer: 31,
          events: 32,
          inherits: 189,
          "process/browser.js": 208,
          string_decoder: 30,
          v3go1D: 205
        }],
        211: [function(t, e, n) {
          function r(t, e) {
            this.afterTransform = function(t, n) {
              return i(e, t, n)
            }, this.needTransform = !1, this.transforming = !1, this.writecb = null, this.writechunk = null
          }

          function i(t, e, n) {
            var r = t._transformState;
            r.transforming = !1;
            var i = r.writecb;
            if (!i) return t.emit("error", new Error("no writecb in Transform class"));
            r.writechunk = null, r.writecb = null, null !== n && void 0 !== n && t.push(n), i && i(e);
            var o = t._readableState;
            o.reading = !1, (o.needReadable || o.length < o.highWaterMark) && t._read(o.highWaterMark)
          }

          function o(t) {
            if (!(this instanceof o)) return new o(t);
            s.call(this, t);
            this._transformState = new r(t, this);
            var e = this;
            this._readableState.needReadable = !0, this._readableState.sync = !1, this.once("finish", function() {
              "function" == typeof this._flush ? this._flush(function(t) {
                a(e, t)
              }) : a(e)
            })
          }

          function a(t, e) {
            if (e) return t.emit("error", e);
            var n = t._writableState,
              r = (t._readableState, t._transformState);
            if (n.length) throw new Error("calling transform done when ws.length != 0");
            if (r.transforming) throw new Error("calling transform done when still transforming");
            return t.push(null)
          }
          e.localExports = o;
          var s = t("./duplex.js");
          t("inherits")(o, s), o.prototype.push = function(t, e) {
            return this._transformState.needTransform = !1, s.prototype.push.call(this, t, e)
          }, o.prototype._transform = function(t, e, n) {
            throw new Error("not implemented")
          }, o.prototype._write = function(t, e, n) {
            var r = this._transformState;
            if (r.writecb = n, r.writechunk = t, r.writeencoding = e, !r.transforming) {
              var i = this._readableState;
              (r.needTransform || i.needReadable || i.length < i.highWaterMark) && this._read(i.highWaterMark)
            }
          }, o.prototype._read = function(t) {
            var e = this._transformState;
            e.writechunk && e.writecb && !e.transforming ? (e.transforming = !0, this._transform(e.writechunk, e.writeencoding, e.afterTransform)) : e.needTransform = !0
          }
        }, {
          "./duplex.js": 206,
          inherits: 189
        }],
        212: [function(t, e, n) {
          function r(t, e, n) {
            this.chunk = t, this.encoding = e, this.callback = n
          }

          function i(t, e) {
            var n = (t = t || {}).highWaterMark;
            this.highWaterMark = n || 0 === n ? n : 16384, this.objectMode = !!t.objectMode, this.highWaterMark = ~~this.highWaterMark, this.needDrain = !1, this.ending = !1, this.ended = !1, this.finished = !1;
            var r = !1 === t.decodeStrings;
            this.decodeStrings = !r, this.defaultEncoding = t.defaultEncoding || "utf8", this.length = 0, this.writing = !1, this.sync = !0, this.bufferProcessing = !1, this.onwrite = function(t) {
              d(e, t)
            }, this.writecb = null, this.writelen = 0, this.buffer = []
          }

          function o(t) {
            if (!(this instanceof o || this instanceof S.Duplex)) return new o(t);
            this._writableState = new i(t, this), this.writable = !0, S.call(this)
          }

          function a(t, e, n) {
            var r = new Error("write after end");
            t.emit("error", r), C(function() {
              n(r)
            })
          }

          function s(t, e, n, r) {
            var i = !0;
            if (!k.isBuffer(n) && "string" != typeof n && null !== n && void 0 !== n && !e.objectMode) {
              var o = new TypeError("Invalid non-string/buffer chunk");
              t.emit("error", o), C(function() {
                r(o)
              }), i = !1
            }
            return i
          }

          function u(t, e, n) {
            return t.objectMode || !1 === t.decodeStrings || "string" != typeof e || (e = new k(e, n)), e
          }

          function c(t, e, n, i, o) {
            n = u(e, n, i);
            var a = e.objectMode ? 1 : n.length;
            e.length += a;
            var s = e.length < e.highWaterMark;
            return e.needDrain = !s, e.writing ? e.buffer.push(new r(n, i, o)) : l(t, e, a, n, i, o), s
          }

          function l(t, e, n, r, i, o) {
            e.writelen = n, e.writecb = o, e.writing = !0, e.sync = !0, t._write(r, i, e.onwrite), e.sync = !1
          }

          function h(t, e, n, r, i) {
            n ? C(function() {
              i(r)
            }) : i(r), t.emit("error", r)
          }

          function f(t) {
            t.writing = !1, t.writecb = null, t.length -= t.writelen, t.writelen = 0
          }

          function d(t, e) {
            var n = t._writableState,
              r = n.sync,
              i = n.writecb;
            if (f(n), e) h(t, n, r, e, i);
            else {
              var o = m(t, n);
              o || n.bufferProcessing || !n.buffer.length || _(t, n), r ? C(function() {
                p(t, n, o, i)
              }) : p(t, n, o, i)
            }
          }

          function p(t, e, n, r) {
            n || g(t, e), r(), n && v(t, e)
          }

          function g(t, e) {
            0 === e.length && e.needDrain && (e.needDrain = !1, t.emit("drain"))
          }

          function _(t, e) {
            e.bufferProcessing = !0;
            for (var n = 0; n < e.buffer.length; n++) {
              var r = e.buffer[n],
                i = r.chunk,
                o = r.encoding,
                a = r.callback;
              if (l(t, e, e.objectMode ? 1 : i.length, i, o, a), e.writing) {
                n++;
                break
              }
            }
            e.bufferProcessing = !1, n < e.buffer.length ? e.buffer = e.buffer.slice(n) : e.buffer.length = 0
          }

          function m(t, e) {
            return e.ending && 0 === e.length && !e.finished && !e.writing
          }

          function v(t, e) {
            var n = m(t, e);
            return n && (e.finished = !0, t.emit("finish")), n
          }

          function y(t, e, n) {
            e.ending = !0, v(t, e), n && (e.finished ? C(n) : t.once("finish", n)), e.ended = !0
          }
          e.localExports = o, o.WritableState = i;
          var b = "undefined" != typeof Uint8Array ? function(t) {
              return t instanceof Uint8Array
            } : function(t) {
              return t && t.constructor && "Uint8Array" === t.constructor.name
            },
            w = "undefined" != typeof ArrayBuffer ? function(t) {
              return t instanceof ArrayBuffer
            } : function(t) {
              return t && t.constructor && "ArrayBuffer" === t.constructor.name
            },
            x = t("inherits"),
            S = t("./index.js"),
            C = t("process/browser.js").nextTick,
            k = t("buffer").Buffer;
          x(o, S), o.prototype.pipe = function() {
            this.emit("error", new Error("Cannot pipe. Not readable."))
          }, o.prototype.write = function(t, e, n) {
            var r = this._writableState,
              i = !1;
            return "function" == typeof e && (n = e, e = null), !k.isBuffer(t) && b(t) && (t = new k(t)), w(t) && "undefined" != typeof Uint8Array && (t = new k(new Uint8Array(t))), k.isBuffer(t) ? e = "buffer" : e || (e = r.defaultEncoding), "function" != typeof n && (n = function() {}), r.ended ? a(this, 0, n) : s(this, r, t, n) && (i = c(this, r, t, e, n)), i
          }, o.prototype._write = function(t, e, n) {
            n(new Error("not implemented"))
          }, o.prototype.end = function(t, e, n) {
            var r = this._writableState;
            "function" == typeof t ? (n = t, t = null, e = null) : "function" == typeof e && (n = e, e = null), void 0 !== t && null !== t && this.write(t, e), r.ending || r.finished || y(this, r, n)
          }
        }, {
          "./index.js": 207,
          buffer: 31,
          inherits: 189,
          "process/browser.js": 208
        }],
        213: [function(t, e, n) {
          e.localExports = t(184)
        }, {}],
        214: [function(t, e, n) {
          e.localExports = t(187)
        }, {
          "tiny-inflate": 213
        }],
        215: [function(t, e, n) {
          e.localExports = t(24)
        }, {}],
        216: [function(t, e, n) {
          e.localExports = t(25)
        }, {
          "./support/isBuffer": 215,
          inherits: 189,
          v3go1D: 205
        }]
      }, {}, [2])(2)
    })
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(t) {
    "use strict";
    ! function(t) {
      t[t.Butt = 0] = "Butt", t[t.Round = 1] = "Round", t[t.Square = 2] = "Square"
    }(t.PdfLineCapStyle || (t.PdfLineCapStyle = {}));
    ! function(t) {
      t[t.Miter = 0] = "Miter", t[t.Round = 1] = "Round", t[t.Bevel = 2] = "Bevel"
    }(t.PdfLineJoinStyle || (t.PdfLineJoinStyle = {}));
    ! function(t) {
      t[t.NonZero = 0] = "NonZero", t[t.EvenOdd = 1] = "EvenOdd"
    }(t.PdfFillRule || (t.PdfFillRule = {}));
    ! function(t) {
      t[t.Portrait = 0] = "Portrait", t[t.Landscape = 1] = "Landscape"
    }(t.PdfPageOrientation || (t.PdfPageOrientation = {}));
    ! function(t) {
      t[t.Left = 0] = "Left", t[t.Center = 1] = "Center", t[t.Right = 2] = "Right"
    }(t.PdfImageHorizontalAlign || (t.PdfImageHorizontalAlign = {}));
    ! function(t) {
      t[t.Top = 0] = "Top", t[t.Center = 1] = "Center", t[t.Bottom = 2] = "Bottom"
    }(t.PdfImageVerticalAlign || (t.PdfImageVerticalAlign = {}));
    ! function(t) {
      t[t.Left = 0] = "Left", t[t.Center = 1] = "Center", t[t.Right = 2] = "Right", t[t.Justify = 3] = "Justify"
    }(t.PdfTextHorizontalAlign || (t.PdfTextHorizontalAlign = {}));
    ! function(t) {
      t[t.Top = 0] = "Top", t[t.Alphabetic = 1] = "Alphabetic"
    }(t._PdfTextBaseline || (t._PdfTextBaseline = {}));
    ! function(t) {
      t[t.A0 = 0] = "A0", t[t.A1 = 1] = "A1", t[t.A2 = 2] = "A2", t[t.A3 = 3] = "A3", t[t.A4 = 4] = "A4", t[t.A5 = 5] = "A5", t[t.A6 = 6] = "A6", t[t.A7 = 7] = "A7", t[t.A8 = 8] = "A8", t[t.A9 = 9] = "A9", t[t.A10 = 10] = "A10", t[t.B0 = 11] = "B0", t[t.B1 = 12] = "B1", t[t.B2 = 13] = "B2", t[t.B3 = 14] = "B3", t[t.B4 = 15] = "B4", t[t.B5 = 16] = "B5", t[t.B6 = 17] = "B6", t[t.B7 = 18] = "B7", t[t.B8 = 19] = "B8", t[t.B9 = 20] = "B9", t[t.B10 = 21] = "B10", t[t.C0 = 22] = "C0", t[t.C1 = 23] = "C1", t[t.C2 = 24] = "C2", t[t.C3 = 25] = "C3", t[t.C4 = 26] = "C4", t[t.C5 = 27] = "C5", t[t.C6 = 28] = "C6", t[t.C7 = 29] = "C7", t[t.C8 = 30] = "C8", t[t.C9 = 31] = "C9", t[t.C10 = 32] = "C10", t[t.RA0 = 33] = "RA0", t[t.RA1 = 34] = "RA1", t[t.RA2 = 35] = "RA2", t[t.RA3 = 36] = "RA3", t[t.RA4 = 37] = "RA4", t[t.SRA0 = 38] = "SRA0", t[t.SRA1 = 39] = "SRA1", t[t.SRA2 = 40] = "SRA2", t[t.SRA3 = 41] = "SRA3", t[t.SRA4 = 42] = "SRA4", t[t.Executive = 43] = "Executive", t[t.Folio = 44] = "Folio", t[t.Legal = 45] = "Legal", t[t.Letter = 46] = "Letter", t[t.Tabloid = 47] = "Tabloid"
    }(t.PdfPageSize || (t.PdfPageSize = {}))
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(t) {
    "use strict";
    t._Errors = {
      InvalidArg: function(t) {
        return 'Invalid argument: "' + t + '".'
      },
      InvalidFormat: function(t) {
        return '"' + t + '" is not in the correct format.'
      },
      ValueCannotBeEmpty: function(t) {
        return 'Value cannot be empty: "' + t + '".'
      },
      PathStarted: "This method can not be used until the current path is finished.",
      BufferPagesMustBeEnabled: "The bufferPages property must be enabled to render headers and footers.",
      AbstractMethod: "This is an abstract method, it should not be called.",
      FontNameMustBeSet: "The font name must be set.",
      FontSourceMustBeStringArrayBuffer: "The font source must be of type string or ArrayBuffer.",
      FontSourceMustBeString: "The font source must be of type string.",
      FontSourceMustBeArrayBuffer: "The font source must be of type ArrayBuffer.",
      EmptyUrl: "URL can not be empty.",
      UndefinedMimeType: "MIME-type must be set.",
      InvalidImageDataUri: "Invalid Data URI. It should be base64 encoded string that represents JPG or PNG image.",
      InvalidImageFormat: "Invalid image format. Only JPG and PNG formats are supported."
    }
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = {
        aliceblue: "#f0f8ff",
        antiquewhite: "#faebd7",
        aqua: "#00ffff",
        aquamarine: "#7fffd4",
        azure: "#f0ffff",
        beige: "#f5f5dc",
        bisque: "#ffe4c4",
        black: "#000000",
        blanchedalmond: "#ffebcd",
        blue: "#0000ff",
        blueviolet: "#8a2be2",
        brown: "#a52a2a",
        burlywood: "#deb887",
        cadetblue: "#5f9ea0",
        chartreuse: "#7fff00",
        chocolate: "#d2691e",
        coral: "#ff7f50",
        cornflowerblue: "#6495ed",
        cornsilk: "#fff8dc",
        crimson: "#dc143c",
        cyan: "#00ffff",
        darkblue: "#00008b",
        darkcyan: "#008b8b",
        darkgoldenrod: "#b8860b",
        darkgray: "#a9a9a9",
        darkgreen: "#006400",
        darkgrey: "#a9a9a9",
        darkkhaki: "#bdb76b",
        darkmagenta: "#8b008b",
        darkolivegreen: "#556b2f",
        darkorange: "#ff8c00",
        darkorchid: "#9932cc",
        darkred: "#8b0000",
        darksalmon: "#e9967a",
        darkseagreen: "#8fbc8f",
        darkslateblue: "#483d8b",
        darkslategray: "#2f4f4f",
        darkslategrey: "#2f4f4f",
        darkturquoise: "#00ced1",
        darkviolet: "#9400d3",
        deeppink: "#ff1493",
        deepskyblue: "#00bfff",
        dimgray: "#696969",
        dimgrey: "#696969",
        dodgerblue: "#1e90ff",
        firebrick: "#b22222",
        floralwhite: "#fffaf0",
        forestgreen: "#228b22",
        fuchsia: "#ff00ff",
        gainsboro: "#dcdcdc",
        ghostwhite: "#f8f8ff",
        gold: "#ffd700",
        goldenrod: "#daa520",
        gray: "#808080",
        green: "#008000",
        greenyellow: "#adff2f",
        grey: "#808080",
        honeydew: "#f0fff0",
        hotpink: "#ff69b4",
        indianred: "#cd5c5c",
        indigo: "#4b0082",
        ivory: "#fffff0",
        khaki: "#f0e68c",
        lavender: "#e6e6fa",
        lavenderblush: "#fff0f5",
        lawngreen: "#7cfc00",
        lemonchiffon: "#fffacd",
        lightblue: "#add8e6",
        lightcoral: "#f08080",
        lightcyan: "#e0ffff",
        lightgoldenrodyellow: "#fafad2",
        lightgray: "#d3d3d3",
        lightgreen: "#90ee90",
        lightgrey: "#d3d3d3",
        lightpink: "#ffb6c1",
        lightsalmon: "#ffa07a",
        lightseagreen: "#20b2aa",
        lightskyblue: "#87cefa",
        lightslategray: "#778899",
        lightslategrey: "#778899",
        lightsteelblue: "#b0c4de",
        lightyellow: "#ffffe0",
        lime: "#00ff00",
        limegreen: "#32cd32",
        linen: "#faf0e6",
        magenta: "#ff00ff",
        maroon: "#800000",
        mediumaquamarine: "#66cdaa",
        mediumblue: "#0000cd",
        mediumorchid: "#ba55d3",
        mediumpurple: "#9370db",
        mediumseagreen: "#3cb371",
        mediumslateblue: "#7b68ee",
        mediumspringgreen: "#00fa9a",
        mediumturquoise: "#48d1cc",
        mediumvioletred: "#c71585",
        midnightblue: "#191970",
        mintcream: "#f5fffa",
        mistyrose: "#ffe4e1",
        moccasin: "#ffe4b5",
        navajowhite: "#ffdead",
        navy: "#000080",
        oldlace: "#fdf5e6",
        olive: "#808000",
        olivedrab: "#6b8e23",
        orange: "#ffa500",
        orangered: "#ff4500",
        orchid: "#da70d6",
        palegoldenrod: "#eee8aa",
        palegreen: "#98fb98",
        paleturquoise: "#afeeee",
        palevioletred: "#db7093",
        papayawhip: "#ffefd5",
        peachpuff: "#ffdab9",
        peru: "#cd853f",
        pink: "#ffc0cb",
        plum: "#dda0dd",
        powderblue: "#b0e0e6",
        purple: "#800080",
        red: "#ff0000",
        rosybrown: "#bc8f8f",
        royalblue: "#4169e1",
        saddlebrown: "#8b4513",
        salmon: "#fa8072",
        sandybrown: "#f4a460",
        seagreen: "#2e8b57",
        seashell: "#fff5ee",
        sienna: "#a0522d",
        silver: "#c0c0c0",
        skyblue: "#87ceeb",
        slateblue: "#6a5acd",
        slategray: "#708090",
        slategrey: "#708090",
        snow: "#fffafa",
        springgreen: "#00ff7f",
        steelblue: "#4682b4",
        tan: "#d2b48c",
        teal: "#008080",
        thistle: "#d8bfd8",
        tomato: "#ff6347",
        turquoise: "#40e0d0",
        violet: "#ee82ee",
        wheat: "#f5deb3",
        white: "#ffffff",
        whitesmoke: "#f5f5f5",
        yellow: "#ffff00",
        yellowgreen: "#9acd32"
      },
      r = function(e) {
        function r(t) {
          return e.call(this, t) || this
        }
        return __extends(r, e), r.prototype._parse = function(t) {
          return e.prototype._parse.call(this, n[t.toLowerCase()] || t)
        }, r.fromString = function(e) {
          var n = new r(null);
          return n._parse(t.asString(e)) ? n : null
        }, r
      }(t.Color);
    e._SafeColor = r
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";

    function n(e) {
      return .75 * t.asNumber(e)
    }

    function r(e, n) {
      if (t.isObject(e) && t.isObject(n)) {
        for (var i in e)
          if (!i || "_" !== i[0]) {
            var o = e[i];
            if (!(o && t.isFunction(o.equals) ? o.equals(n[i]) : r(o, n[i]))) return !1
          }
        return !0
      }
      if (t.isArray(e) && t.isArray(n)) {
        if (e.length !== n.length) return !1;
        for (var a = 0; a < e.length; a++)
          if (!r(e[a], n[a])) return !1;
        return !0
      }
      return e === n
    }
    e._IE = "undefined" != typeof window && "ActiveXObject" in window;
    var i = {
      "xx-small": 7,
      "x-small": 7.5,
      small: 10,
      medium: 12,
      large: 13.5,
      "x-large": 18,
      "xx-large": 24
    };
    e.saveBlob = function(t, e) {
      if (t && t instanceof Blob && e)
        if (navigator.msSaveBlob) navigator.msSaveBlob(t, e);
        else {
          var n = document.createElement("a"),
            r = function(t) {
              var e = document.createEvent("MouseEvents");
              e.initMouseEvent("click", !0, !1, window, 0, 0, 0, 0, 0, !1, !1, !1, !1, 0, null), t.dispatchEvent(e)
            };
          if ("download" in n) {
            var i = window.URL || window.webkitURL || window,
              o = i.createObjectURL(t);
            n.href = o, n.target = "_blank", n.download = e, r(n), n = null, window.setTimeout(function() {
              i.revokeObjectURL(o)
            }, 3e4)
          } else {
            var a = new FileReader;
            a.onloadend = function(t) {
              n.download = e, n.href = a.result, r(n), n = null
            }, a.readAsDataURL(t)
          }
        }
    }, e.ptToPx = function(e) {
      return t.asNumber(e) / .75
    }, e.pxToPt = n, e._asColor = function(n, r) {
      void 0 === r && (r = !0);
      var i;
      return i = n ? n instanceof t.Color ? r ? t.Color.fromRgba(n.r, n.g, n.b, n.a) : n : e._SafeColor.fromString(n) : t.Color.fromRgba(0, 0, 0), t.assert(i instanceof t.Color, e._Errors.InvalidArg("colorOrString")), i
    }, e._asPdfPen = function(n, r) {
      return void 0 === r && (r = !0), (t.isString(n) || n instanceof t.Color) && (n = new e.PdfPen(n)), t.assert(null == n && r || n instanceof e.PdfPen, e._Errors.InvalidArg("penOrColor")), n
    }, e._asPdfBrush = function(n, r) {
      return void 0 === r && (r = !0), (t.isString(n) || n instanceof t.Color) && (n = new e.PdfSolidBrush(n)), t.assert(null == n && r || n instanceof e.PdfBrush, e._Errors.InvalidArg("brushOrColor")), n
    }, e._asPdfFont = function(n, r) {
      return void 0 === r && (r = !0), t.assert(null == n && r || n instanceof e.PdfFont, e._Errors.InvalidArg("font")), n
    }, e._asPt = function(r, o, a) {
      void 0 === o && (o = !0), void 0 === a && (a = 0);
      var s = !r && 0 !== r;
      if (t.assert(!s || o, e._Errors.ValueCannotBeEmpty("value")), s) return a;
      if (t.isNumber(r)) {
        if (r === r) return r
      } else if (t.isString(r)) {
        if (i[r]) return i[r];
        var u = parseFloat(r);
        if (u === u) {
          if (r.match(/(px)$/i)) return n(u);
          if (r == u || r.match(/(pt)$/i)) return u
        }
      }
      t.assert(!1, e._Errors.InvalidFormat(r))
    }, e._formatMacros = function(t, e) {
      var n = {},
        r = 0;
      return t = t.replace(/&&/g, function(t, e, i) {
        return n[e - 2 * r + r] = !0, r++, "&"
      }), t = t.replace(/&\[(\S+?)\]/g, function(t, r, i, o) {
        var a = e[r];
        return a && !n[i] ? a : t
      })
    }, e._compare = r, e._shallowCopy = function(t) {
      var e = {};
      if (t)
        for (var n in t) e[n] = t[n];
      return e
    }, e._toTitleCase = function(t) {
      return t ? t.charAt(0).toUpperCase() + t.slice(1).toLowerCase() : t
    }
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function() {
      function e(t, e, n) {
        void 0 === t && (t = null), void 0 === e && (e = t), void 0 === n && (n = 0), this.dash = t, this.gap = e, this.phase = n
      }
      return Object.defineProperty(e.prototype, "dash", {
        get: function() {
          return this._dash
        },
        set: function(e) {
          this._dash = t.asNumber(e, !0, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "gap", {
        get: function() {
          return this._gap
        },
        set: function(e) {
          this._gap = t.asNumber(e, !0, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "phase", {
        get: function() {
          return this._phase
        },
        set: function(e) {
          this._phase = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), e.prototype.clone = function() {
        return new e(this._dash, this._gap, this._phase)
      }, e.prototype.equals = function(t) {
        return t instanceof e && this._dash === t.dash && this._gap === t.gap && this._phase === t.phase
      }, e
    }();
    e.PdfDashPattern = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(t) {
    "use strict";
    var e = function() {
      function e() {}
      return e.prototype.clone = function() {
        throw t._Errors.AbstractMethod
      }, e.prototype.equals = function(e) {
        throw t._Errors.AbstractMethod
      }, e.prototype._getBrushObject = function(e) {
        throw t._Errors.AbstractMethod
      }, e
    }();
    t.PdfBrush = e
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function() {
      function n(e, n, r) {
        this.offset = e || 0, this.color = n || t.Color.fromRgba(0, 0, 0), this.opacity = null == r ? 1 : r
      }
      return Object.defineProperty(n.prototype, "offset", {
        get: function() {
          return this._offset
        },
        set: function(e) {
          this._offset = t.clamp(t.asNumber(e, !1, !0), 0, 1)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "color", {
        get: function() {
          return this._color
        },
        set: function(t) {
          this._color = e._asColor(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "opacity", {
        get: function() {
          return this._opacity
        },
        set: function(e) {
          this._opacity = t.clamp(t.asNumber(e, !1, !0), 0, 1)
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.clone = function() {
        return new n(this.offset, this.color, this.opacity)
      }, n.prototype.equals = function(t) {
        return t instanceof n && this._offset === t._offset && this._color.equals(t._color) && this._opacity === t._opacity
      }, n
    }();
    e.PdfGradientStop = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function(n) {
      function r(t, e) {
        var r = n.call(this) || this;
        return r.stops = t || [], r.opacity = null == e ? 1 : e, r
      }
      return __extends(r, n), Object.defineProperty(r.prototype, "opacity", {
        get: function() {
          return this._opacity
        },
        set: function(e) {
          this._opacity = t.clamp(t.asNumber(e, !1, !0), 0, 1)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "stops", {
        get: function() {
          return this._stops
        },
        set: function(n) {
          t.assert(t.isArray(n), e._Errors.InvalidArg("value")), this._stops = this._cloneStopsArray(n)
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.equals = function(t) {
        return t instanceof r && this._opacity === t.opacity && e._compare(this._stops, t._stops)
      }, r.prototype._cloneStopsArray = function(n) {
        for (var r = [], i = 0; i < n.length; i++) {
          var o = n[i];
          t.assert(o instanceof e.PdfGradientStop, e._Errors.InvalidArg("stops[" + i + "]")), r.push(n[i].clone())
        }
        return r
      }, r
    }(e.PdfBrush);
    e.PdfGradientBrush = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function(e) {
      function n(t, n, r, i, o, a) {
        var s = e.call(this, o, a) || this;
        return s.x1 = t, s.y1 = n, s.x2 = r, s.y2 = i, s
      }
      return __extends(n, e), Object.defineProperty(n.prototype, "x1", {
        get: function() {
          return this._x1
        },
        set: function(e) {
          this._x1 = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "y1", {
        get: function() {
          return this._y1
        },
        set: function(e) {
          this._y1 = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "x2", {
        get: function() {
          return this._x2
        },
        set: function(e) {
          this._x2 = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "y2", {
        get: function() {
          return this._y2
        },
        set: function(e) {
          this._y2 = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.clone = function() {
        return new n(this._x1, this._y1, this._x2, this._y2, this.stops, this.opacity)
      }, n.prototype.equals = function(t) {
        return t instanceof n && this._x1 === t._x1 && this._y1 === t._y1 && this._x2 === t._x2 && this._y2 === t._y2 && e.prototype.equals.call(this, t)
      }, n.prototype._getBrushObject = function(t) {
        for (var e = t.document._document.linearGradient(this._x1 + t._offset.x, this._y1 + t._offset.y, this._x2 + t._offset.x, this._y2 + t._offset.y), n = this.stops, r = 0; r < n.length; r++) {
          var i = n[r];
          i && e.stop(i.offset, [i.color.r, i.color.g, i.color.b], i.color.a)
        }
        return e
      }, n
    }(e.PdfGradientBrush);
    e.PdfLinearGradientBrush = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function(e) {
      function n(t, n, r, i, o, a, s, u) {
        var c = e.call(this, s, u) || this;
        return c.x1 = t, c.y1 = n, c.r1 = r, c.x2 = i, c.y2 = o, c.r2 = a, c
      }
      return __extends(n, e), Object.defineProperty(n.prototype, "x1", {
        get: function() {
          return this._x1
        },
        set: function(e) {
          this._x1 = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "y1", {
        get: function() {
          return this._y1
        },
        set: function(e) {
          this._y1 = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "r1", {
        get: function() {
          return this._r1
        },
        set: function(e) {
          this._r1 = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "x2", {
        get: function() {
          return this._x2
        },
        set: function(e) {
          this._x2 = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "y2", {
        get: function() {
          return this._y2
        },
        set: function(e) {
          this._y2 = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "r2", {
        get: function() {
          return this._r2
        },
        set: function(e) {
          this._r2 = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.clone = function() {
        return new n(this._x1, this._y1, this._r1, this._x2, this._y2, this._r2, this.stops, this.opacity)
      }, n.prototype.equals = function(t) {
        return t instanceof n && this._x1 === t._x1 && this._y1 === t._y1 && this._r1 === t._r1 && this._x2 === t._x2 && this._y2 === t._y2 && this._r2 === t._r2 && e.prototype.equals.call(this, t)
      }, n.prototype._getBrushObject = function(t) {
        for (var e = t.document._document.radialGradient(this._x1 + t._offset.x, this._y2 + t._offset.y, this._r1, this._x2 + t._offset.x, this._y2 + t._offset.y, this._r2), n = this.stops, r = 0; r < n.length; r++) {
          var i = n[r];
          i && e.stop(i.offset, [i.color.r, i.color.g, i.color.b], i.color.a)
        }
        return e
      }, n
    }(e.PdfGradientBrush);
    e.PdfRadialGradientBrush = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function(n) {
      function r(e) {
        var r = n.call(this) || this;
        return r.color = e || t.Color.fromRgba(0, 0, 0), r
      }
      return __extends(r, n), Object.defineProperty(r.prototype, "color", {
        get: function() {
          return this._color
        },
        set: function(t) {
          this._color = e._asColor(t)
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.clone = function() {
        return new r(this._color)
      }, r.prototype.equals = function(t) {
        return t instanceof r && this._color.equals(t._color)
      }, r.prototype._getBrushObject = function(t) {
        return this._color
      }, r
    }(e.PdfBrush);
    e.PdfSolidBrush = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function() {
      function n(n, r, i, o, a, s) {
        if (null == n && (n = t.Color.fromRgba(0, 0, 0)), null == r && (r = 1), null == i && (i = new e.PdfDashPattern(null, null, 0)), null == o && (o = e.PdfLineCapStyle.Butt), null == a && (a = e.PdfLineJoinStyle.Miter), null == s && (s = 10), !t.isObject(n) || n instanceof t.Color || n instanceof e.PdfBrush) n instanceof e.PdfBrush ? this.brush = n : this.color = n, this.width = r, this.cap = o, this.join = a, this.miterLimit = s, this.dashPattern = i;
        else {
          var u = n;
          this.color = u.color, this.brush = u.brush, this.width = null != u.width ? u.width : r, this.cap = null != u.cap ? u.cap : o, this.join = null != u.join ? u.join : a, this.miterLimit = null != u.miterLimit ? u.miterLimit : s, this.dashPattern = u.dashPattern || i
        }
        this._color = this._color || t.Color.fromRgba(0, 0, 0)
      }
      return Object.defineProperty(n.prototype, "color", {
        get: function() {
          return this._color
        },
        set: function(t) {
          this._color = e._asColor(t)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "brush", {
        get: function() {
          return this._brush
        },
        set: function(t) {
          t = e._asPdfBrush(t, !0), this._brush = t ? t.clone() : null
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "width", {
        get: function() {
          return this._width
        },
        set: function(e) {
          this._width = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "cap", {
        get: function() {
          return this._cap
        },
        set: function(n) {
          this._cap = t.asEnum(n, e.PdfLineCapStyle)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "join", {
        get: function() {
          return this._join
        },
        set: function(n) {
          this._join = t.asEnum(n, e.PdfLineJoinStyle)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "miterLimit", {
        get: function() {
          return this._miterLimit
        },
        set: function(e) {
          this._miterLimit = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "dashPattern", {
        get: function() {
          return this._dashPattern
        },
        set: function(n) {
          t.assert(n instanceof e.PdfDashPattern, e._Errors.InvalidArg("value")), this._dashPattern = n.clone()
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.clone = function() {
        var t = new n(this._color, this._width, this._dashPattern, this._cap, this._join, this._miterLimit);
        return t.brush = this._brush, t
      }, n.prototype.equals = function(t) {
        return t instanceof n && this._color.equals(t._color) && (this._brush ? this._brush.equals(t._brush) : this._brush === t._brush) && this._width === t._width && this._cap === t._cap && this._join === t._join && this._miterLimit === t._miterLimit && this._dashPattern.equals(t._dashPattern)
      }, n
    }();
    e.PdfPen = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function() {
      function n(t, e, n, r) {
        void 0 === t && (t = "times"), void 0 === e && (e = 10), void 0 === n && (n = "normal"), void 0 === r && (r = "normal"), this.family = t, this.size = e, this.style = n, this.weight = r
      }
      return Object.defineProperty(n.prototype, "family", {
        get: function() {
          return this._family
        },
        set: function(e) {
          this._family = t.asString(e, !1)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "size", {
        get: function() {
          return this._size
        },
        set: function(e) {
          this._size = t.asNumber(e, !1, !0)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "style", {
        get: function() {
          return this._style
        },
        set: function(r) {
          (r = t.asString(r, !1)) && t.assert(!!n._KNOWN_STYLES[(r || "").toLowerCase()], e._Errors.InvalidArg("value")), this._style = r
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "weight", {
        get: function() {
          return this._weight
        },
        set: function(r) {
          (r = t.asString(r, !1)) && t.assert(!!n._KNOWN_WEIGHTS[(r || "").toLowerCase()], e._Errors.InvalidArg("value")), this._weight = r
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.clone = function() {
        return new n(this.family, this.size, this.style, this.weight)
      }, n.prototype.equals = function(t) {
        return t instanceof n && this._family === t._family && this._size === t._size && this._style === t._style && this._weight === t._weight
      }, n._DEF_NATIVE_NAME = "Times-Roman", n._DEF_FAMILY_NAME = "times", n._KNOWN_WEIGHTS = {
        normal: 1,
        bold: 1,
        100: 1,
        200: 1,
        300: 1,
        400: 1,
        500: 1,
        600: 1,
        700: 1,
        800: 1,
        900: 1
      }, n._KNOWN_STYLES = {
        normal: 1,
        italic: 1,
        oblique: 1
      }, n._DEF_PDFKIT_FONT = new n("helvetica", 12), n._DEF_FONT = new n, n
    }();
    e.PdfFont = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function() {
        function t(t) {
          if (this._values = [], this._keys = {}, t)
            for (var e = 0; e < t.length; e++) {
              var n = t[e];
              this._keys[n.key] = e, this._values.push({
                key: n.key,
                value: n.value
              })
            }
        }
        return t.prototype.hasKey = function(t) {
          var e = this._keys[t];
          return void 0 !== e ? this._values[e].value : null
        }, t.prototype.add = function(t, e) {
          return this.hasKey(t) ? null : (this._keys[t] = this._values.length, this._values.push({
            key: t,
            value: e
          }), e)
        }, t.prototype.each = function(t) {
          if (t)
            for (var e = 0; e < this._values.length; e++) {
              var n = this._values[e];
              if (!1 === t(n.key, n.value)) break
            }
        }, t.prototype.eachReverse = function(t) {
          if (t)
            for (var e = this._values.length - 1; e >= 0; e--) {
              var n = this._values[e];
              if (!1 === t(n.key, n.value)) break
            }
        }, t
      }(),
      r = function() {
        function r(t) {
          var e = this;
          this._fonts = new n([{
            key: "zapfdingbats",
            value: {
              attributes: {
                fantasy: !0
              },
              normal: {
                400: "ZapfDingbats"
              }
            }
          }, {
            key: "symbol",
            value: {
              attributes: {
                serif: !0
              },
              normal: {
                400: "Symbol"
              }
            }
          }, {
            key: "courier",
            value: {
              attributes: {
                serif: !0,
                monospace: !0
              },
              normal: {
                400: "Courier",
                700: "Courier-Bold"
              },
              oblique: {
                400: "Courier-Oblique",
                700: "Courier-BoldOblique"
              }
            }
          }, {
            key: "helvetica",
            value: {
              attributes: {
                sansSerif: !0
              },
              normal: {
                400: "Helvetica",
                700: "Helvetica-Bold"
              },
              oblique: {
                400: "Helvetica-Oblique",
                700: "Helvetica-BoldOblique"
              }
            }
          }, {
            key: "times",
            value: {
              attributes: {
                serif: !0
              },
              normal: {
                400: "Times-Roman",
                700: "Times-Bold"
              },
              italic: {
                400: "Times-Italic",
                700: "Times-BoldItalic"
              }
            }
          }]), this._weightNameToNum = {
            normal: 400,
            bold: 700
          }, this._findFontCache = {}, this._internalFontNames = {}, this._doc = t, this._fonts.each(function(t, n) {
            var r = function(t) {
              for (var n in t) e._internalFontNames[t[n]] = 1
            };
            r(n.normal) || r(n.italic) || r(n.oblique)
          })
        }
        return r.prototype.registerFont = function(n) {
          t.assert(!!n, e._Errors.ValueCannotBeEmpty("font")), t.asString(n.name), t.assert(n.source instanceof ArrayBuffer, e._Errors.FontSourceMustBeArrayBuffer), n = e._shallowCopy(n);
          var r = this._normalizeFontSelector(n.name, n.style, n.weight),
            i = this._fonts.hasKey(r.name);
          i || (i = this._fonts.add(r.name, {
            attributes: n
          }));
          var o = i[r.style];
          o || (o = i[r.style] = {});
          var a = this._makeInternalName(r);
          return o[r.weight] || (this._doc.registerFont(a, n.source, n.family), this._findFontCache = {}, o[r.weight] = a, this._internalFontNames[a] = 1), a
        }, r.prototype.findFont = function(t, n, r) {
          var i = this._normalizeFontSelector(t, n, r),
            o = this._makeInternalName(i);
          if (this._findFontCache[o]) return this._findFontCache[o];
          i.name += "," + e.PdfFont._DEF_FAMILY_NAME;
          for (var a = 0, s = i.name.split(","); a < s.length; a++) {
            var u = this._findFont(s[a].replace(/["']/g, "").trim(), i.style, i.weight);
            if (u) return this._findFontCache[o] = u
          }
          return this._findFontCache[o] = this._internalFontNames[t] ? t : e.PdfFont._DEF_NATIVE_NAME
        }, r.prototype._normalizeFontSelector = function(t, n, r) {
          return {
            name: (t || "").toLowerCase(),
            style: (n || e.PdfFont._DEF_FONT.style).toLowerCase(),
            weight: parseInt(this._weightNameToNum[r] || r) || parseInt(this._weightNameToNum[e.PdfFont._DEF_FONT.weight])
          }
        }, r.prototype._findFont = function(t, e, n) {
          var r, i = this,
            o = [];
          switch (e) {
            case "italic":
              o = ["italic", "oblique", "normal"];
              break;
            case "oblique":
              o = ["oblique", "italic", "normal"];
              break;
            default:
              o = ["normal", "oblique", "italic"]
          }
          switch (t) {
            case "cursive":
            case "fantasy":
            case "monospace":
            case "serif":
            case "sans-serif":
              this._fonts.eachReverse(function(e, a) {
                var s = "sans-serif" === t ? "sansSerif" : t;
                if (a.attributes[s])
                  for (var u = 0; u < o.length; u++)
                    if (r = i._findFontWeightFallback(e, o[u], n)) return !1
              });
              break;
            default:
              if (this._fonts.hasKey(t))
                for (var a = 0; a < o.length && !r; a++) r = this._findFontWeightFallback(t, o[a], n)
          }
          return r
        }, r.prototype._findFontWeightFallback = function(t, e, n, r) {
          var i = this._fonts.hasKey(t);
          if (i && i[e]) {
            var o = i[e];
            if (o[n]) return o[n];
            if (!r) {
              r = [];
              for (var a in o) r.push(parseFloat(a));
              r.sort(function(t, e) {
                return t - e
              })
            }
            if (n > 500) {
              for (var s = 0, u = 0; u < r.length; u++) {
                if ((l = r[u]) > n) return o[l];
                s = l
              }
              if (s) return o[s]
            } else {
              if (!(n < 400)) return 400 == n ? o[500] ? o[500] : this._findFontWeightFallback(t, e, 300, r) : o[400] ? o[400] : this._findFontWeightFallback(t, e, 300, r);
              for (var c = 0, u = r.length - 1; u >= 0; u--) {
                var l = r[u];
                if (l < n) return o[l];
                c = l
              }
              if (c) return o[c]
            }
          }
          return null
        }, r.prototype._makeInternalName = function(t) {
          return t.name + "-" + t.style + "-" + t.weight
        }, r
      }();
    e._PdfFontRegistrar = r
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function() {
      function n() {
        this._ctxProps = {
          xo: 0,
          yo: 0,
          lineGap: 0
        }
      }
      return Object.defineProperty(n.prototype, "x", {
        get: function() {
          this._switchCtx();
          var t = this._pdfdoc._document.x - this._offset.x;
          return this._saveCtx(), t
        },
        set: function(e) {
          e = t.asNumber(e), this._switchCtx(), this._pdfdoc._document.x = e + this._offset.x, this._saveCtx()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "y", {
        get: function() {
          this._switchCtx();
          var t = this._pdfdoc._document.y - this._offset.y;
          return this._saveCtx(), t
        },
        set: function(e) {
          e = t.asNumber(e), this._switchCtx(), this._pdfdoc._document.y = e + this._offset.y, this._saveCtx()
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "lineGap", {
        get: function() {
          return this._ctxProps.lineGap
        },
        set: function(e) {
          this._ctxProps.lineGap = e = t.asNumber(e, !1, !0), this._pdfdoc && this._pdfdoc._document && (this._switchCtx(), this._pdfdoc._document.lineGap(e), this._saveCtx())
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "height", {
        get: function() {
          var t = this._pdfdoc._document.page;
          return Math.max(0, t.height - t.margins.top - t.margins.bottom)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "width", {
        get: function() {
          var t = this._pdfdoc._document.page;
          return Math.max(t.width - t.margins.left - t.margins.right)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "paths", {
        get: function() {
          return this._graphics
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "document", {
        get: function() {
          return this._pdfdoc
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.drawText = function(n, r, i, o) {
        if (this._assertPathStarted(), n = t.asString(n)) {
          o = o || {};
          var a, s = this._pdfdoc,
            u = s._document,
            c = o.stroke && o.fill ? 2 : o.stroke ? 1 : 0;
          !o.strike && !o.underline || o.stroke || (c = 2), this._switchCtx();
          try {
            this._drawingText = !0, 1 & c || s._toggleBrush(e._asPdfBrush(o.brush)), 3 & c && s._togglePen(e._asPdfPen(o.pen)), s._toggleFont(e._asPdfFont(o.font));
            var l = this._textOptionsToNative(o),
              h = o._baseline === e._PdfTextBaseline.Alphabetic ? u.currentFontAscender() : 0;
            null == r ? (u.y -= h, a = u.textAndMeasure(n, null, null, l)) : a = u.textAndMeasure(n, t.asNumber(r) + this._offset.x, t.asNumber(i) + this._offset.y - h, l)
          } finally {
            this._drawingText = !1, this._saveCtx()
          }
          return {
            charCount: a.charCount || 0,
            size: new t.Size(a.width || 0, a.height || 0)
          }
        }
      }, n.prototype.drawImage = function(n, r, i, o) {
        if (this._assertPathStarted(), !n) return this;
        t.isString(n) && (n = e._PdfImageHelper.getDataUri(n)), this._switchCtx();
        try {
          var a = {};
          if (o) {
            switch (t.asEnum(o.align, e.PdfImageHorizontalAlign, !0)) {
              case e.PdfImageHorizontalAlign.Center:
                a.align = "center";
                break;
              case e.PdfImageHorizontalAlign.Right:
                a.align = "right";
                break;
              default:
                a.align = "left"
            }
            switch (t.asEnum(o.vAlign, e.PdfImageVerticalAlign, !0)) {
              case e.PdfImageVerticalAlign.Center:
                a.valign = "center";
                break;
              case e.PdfImageVerticalAlign.Bottom:
                a.valign = "bottom";
                break;
              default:
                a.valign = "top"
            }
            var s = t.asNumber(o.width, !0, !0),
              u = t.asNumber(o.height, !0, !0);
            s && u && t.asBoolean(o.stretchProportionally, !0) ? a.fit = [s, u] : (a.width = s, a.height = u)
          }
          null == r ? this._pdfdoc._document.image(n, a) : this._pdfdoc._document.image(n, t.asNumber(r) + this._offset.x, t.asNumber(i) + this._offset.y, a)
        } finally {
          this._saveCtx()
        }
        return this
      }, n.prototype.drawSvg = function(n, r, i, o) {
        if (o = o || {}, this._assertPathStarted(), !(n = t.asString(n))) return this;
        var a;
        if (n.indexOf("data:image/svg") >= 0) a = function(t) {
          return decodeURIComponent(Array.prototype.map.call(atob(t), function(t) {
            return "%" + ("00" + t.charCodeAt(0).toString(16)).slice(-2)
          }).join(""))
        }(n.substring(n.indexOf(",") + 1));
        else {
          var s;
          if (a = t.pdf._XhrHelper.text(n, function(t) {
            return s = t.statusText
          }), s) throw s
        }
        if (!a) return this;
        var u, c, l = new e._SvgRenderer(a, this, t.asFunction(o.urlResolver)),
          h = null == i,
          r = null != r ? r : this.x,
          i = null != i ? i : this.y,
          f = this.y,
          d = this.x,
          p = t.asNumber(o.width, !0, !0),
          g = t.asNumber(o.height, !0, !0),
          _ = l.root.width.hasVal ? l.root.width.val : void 0,
          m = l.root.height.hasVal ? l.root.height.val : void 0;
        if ((p || g) && _ && m)
          if (u = p / _, c = g / m, p && g) {
            if (o.stretchProportionally) {
              var v = Math.min(u, c);
              if (u === v) switch (t.asEnum(o.vAlign, e.PdfImageVerticalAlign, !0)) {
                case e.PdfImageVerticalAlign.Center:
                  i += g / 2 - m * u / 2;
                  break;
                case e.PdfImageVerticalAlign.Bottom:
                  i += g - m * u
              }
              if (c === v) switch (t.asEnum(o.align, e.PdfImageHorizontalAlign, !0)) {
                case e.PdfImageHorizontalAlign.Center:
                  r += p / 2 - _ * c / 2;
                  break;
                case e.PdfImageHorizontalAlign.Right:
                  r += p - _ * c
              }
              u = c = v
            }
          } else o.width ? c = u : u = c;
        u = u || 1, c = c || 1, this._switchCtx(), this._pdfdoc.saveState();
        try {
          this.translate(r, i), this.scale(u, c), l.render()
        } finally {
          this._pdfdoc.restoreState(), this._saveCtx()
        }
        if (this.x = d, this.y = f, h) {
          var y = null != g ? g : null != m ? m * c : void 0;
          this.y += y || 0
        }
        return this
      }, n.prototype.lineHeight = function(t) {
        var n = this._pdfdoc;
        n._toggleFont(e._asPdfFont(t)), this._switchCtx();
        var r = n._document.currentLineHeight();
        return this._saveCtx(), r
      }, n.prototype.measureText = function(n, r, i) {
        var o = {};
        if (n = t.asString(n)) {
          var a = this._pdfdoc;
          a._toggleFont(e._asPdfFont(r)), this._switchCtx();
          try {
            o = a._document.textAndMeasure(n, null, null, this._textOptionsToNative(i), !0)
          } finally {
            this._saveCtx()
          }
        }
        return {
          charCount: o.charCount || 0,
          size: new t.Size(o.width || 0, o.height || 0)
        }
      }, n.prototype.moveDown = function(n, r) {
        if (void 0 === n && (n = 1), n = t.asNumber(n, !1, !0)) {
          var i = this._pdfdoc;
          i._toggleFont(e._asPdfFont(r)), this._switchCtx();
          try {
            i._document.moveDown(n)
          } finally {
            this._saveCtx()
          }
        }
        return this
      }, n.prototype.moveUp = function(n, r) {
        if (void 0 === n && (n = 1), n = t.asNumber(n, !1, !0)) {
          var i = this._pdfdoc;
          i._toggleFont(e._asPdfFont(r)), this._switchCtx();
          try {
            i._document.moveUp(n)
          } finally {
            this._saveCtx()
          }
        }
        return this
      }, n.prototype.openImage = function(n) {
        return (n = t.asString(n)) ? (n = e._PdfImageHelper.getDataUri(n), this._pdfdoc._document.openImage(n)) : null
      }, n.prototype.scale = function(e, n, r) {
        void 0 === n && (n = e), this._assertPathStarted(), r = r || new t.Point(0, 0);
        var i = t.asNumber(r.x) + this._offset.x,
          o = t.asNumber(r.y) + this._offset.y;
        return e = t.asNumber(e, !1), n = t.asNumber(n, !1), this._pdfdoc._document.scale(e, n, {
          origin: [i, o]
        }), this
      }, n.prototype.translate = function(e, n) {
        return this._assertPathStarted(), e = t.asNumber(e), n = t.asNumber(n), this._pdfdoc._document.translate(e, n), this
      }, n.prototype.transform = function(e, n, r, i, o, a) {
        this._assertPathStarted(), e = t.asNumber(e), n = t.asNumber(n), r = t.asNumber(r), i = t.asNumber(i), o = t.asNumber(o), a = t.asNumber(a);
        var s = this._offset.x,
          u = this._offset.y;
        return this._pdfdoc._document.transform(e, n, r, i, o - e * s + s - r * u, a - n * s - i * u + u), this
      }, n.prototype.rotate = function(e, n) {
        this._assertPathStarted(), n = n || new t.Point(0, 0);
        var r = t.asNumber(n.x) + this._offset.x,
          i = t.asNumber(n.y) + this._offset.y;
        return e = t.asNumber(e), this._pdfdoc._document.rotate(e, {
          origin: [r, i]
        }), this
      }, n.prototype._assertPathStarted = function() {
        t.assert(!this.paths._hasPathBuffer(), e._Errors.PathStarted)
      }, n.prototype._initialize = function(n, r, i) {
        this._pdfdoc = n, this._offset = new t.Point(r, i), this._ctxProps = {
          xo: r,
          yo: i,
          lineGap: this._ctxProps.lineGap
        }, this._graphics = new e.PdfPaths(this._pdfdoc, this._offset)
      }, n.prototype._isDrawingText = function() {
        return this._drawingText
      }, n.prototype._switchCtx = function() {
        this._pdfdoc._switchTextFlowCtx(this._ctxProps)
      }, n.prototype._saveCtx = function() {
        this._ctxProps = this._pdfdoc._getTextFlowCtxState()
      }, n.prototype._textOptionsToNative = function(n) {
        n = n || {};
        var r = e._shallowCopy(n);
        return null != n.align && (r.align = (e.PdfTextHorizontalAlign[t.asEnum(n.align, e.PdfTextHorizontalAlign)] || "").toLowerCase()), r
      }, n
    }();
    e.PdfPageArea = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function() {
      function n(t, n, r) {
        this.text = t || "", this.font = n || new e.PdfFont, this.brush = r || new e.PdfSolidBrush
      }
      return Object.defineProperty(n.prototype, "font", {
        get: function() {
          return this._font
        },
        set: function(t) {
          t = e._asPdfFont(t, !0), this._font = t ? t.clone() : t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "text", {
        get: function() {
          return this._text
        },
        set: function(e) {
          this._text = t.asString(e)
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(n.prototype, "brush", {
        get: function() {
          return this._brush
        },
        set: function(t) {
          t = e._asPdfBrush(t), this._brush = t ? t.clone() : t
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.clone = function() {
        return new n(this.text, this.font, this.brush)
      }, n.prototype.equals = function(t) {
        return t instanceof n && this._text === t.text && (this._brush ? this._brush.equals(t._brush) : this._brush === t._brush) && (this._font ? this._font.equals(t._font) : this._font === t._font)
      }, n
    }();
    e.PdfRunningTitleDeclarativeContent = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function(n) {
      function r(r) {
        var i = n.call(this) || this;
        return i._height = 24, i._declarative = new e.PdfRunningTitleDeclarativeContent, i._heightChanged = new t.Event, t.copy(i, r), i
      }
      return __extends(r, n), Object.defineProperty(r.prototype, "declarative", {
        get: function() {
          return this._declarative
        },
        set: function(n) {
          null != n && (t.assert(n instanceof e.PdfRunningTitleDeclarativeContent, e._Errors.InvalidArg("value")), n = n.clone()), this._declarative = n
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "height", {
        get: function() {
          return this._height
        },
        set: function(e) {
          e !== this._height && (this._height = t.asNumber(e, !1, !0), this._heightChanged.raise(this, t.EventArgs.empty))
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.drawText = function(t, e, r, i) {
        return i = i || {}, i.height = 1 / 0, n.prototype.drawText.call(this, t, e, r, i)
      }, r
    }(e.PdfPageArea);
    e.PdfRunningTitle = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function() {
      function n() {}
      return n.getDataUri = function(r) {
        if (t.assert(!!(r = t.asString(r)), e._Errors.EmptyUrl), n.DATAURI_CACHE[r]) return n.DATAURI_CACHE[r];
        var i = "";
        if (0 === r.indexOf("data:")) {
          if (!r.match(/^data:image\/(png|jpeg|jpg);base64,/)) throw e._Errors.InvalidImageDataUri;
          i = r
        } else {
          var o, a = e._XhrHelper.arrayBuffer(r, function(t) {
            return o = t.statusText
          });
          if (o) throw o;
          try {
            var s = new Uint16Array(a, 0, 2);
            if (55551 !== s[0] && (20617 !== s[0] || 18254 !== s[1])) throw "";
            var u = n._toBase64(a);
            i = "data:" + (55551 === s[0] ? "image/jpeg" : "image/png") + ";base64," + u
          } catch (t) {
            throw e._Errors.InvalidImageFormat
          }
        }
        return n.DATAURI_CACHE[r] = i
      }, n._toBase64 = function(t) {
        for (var e = "", n = new Uint8Array(t), r = 0; r < n.byteLength; r++) e += String.fromCharCode(n[r]);
        return window.btoa(e)
      }, n.DATAURI_CACHE = {}, n
    }();
    e._PdfImageHelper = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function() {
      function n(t, e) {
        this._pathBuffer = [], this._doc = t, this._offset = e
      }
      return n.prototype.moveTo = function(e, n) {
        return this._pathBuffer.push({
          func: this._doc._document.moveTo,
          params: [t.asNumber(e) + this._offset.x, t.asNumber(n) + this._offset.y]
        }), this
      }, n.prototype.lineTo = function(e, n) {
        return this._pathBuffer.push({
          func: this._doc._document.lineTo,
          params: [t.asNumber(e) + this._offset.x, t.asNumber(n) + this._offset.y]
        }), this
      }, n.prototype.quadraticCurveTo = function(e, n, r, i) {
        return this._pathBuffer.push({
          func: this._doc._document.quadraticCurveTo,
          params: [t.asNumber(e) + this._offset.x, t.asNumber(n) + this._offset.y, t.asNumber(r) + this._offset.x, t.asNumber(i) + this._offset.y]
        }), this
      }, n.prototype.bezierCurveTo = function(e, n, r, i, o, a) {
        return this._pathBuffer.push({
          func: this._doc._document.bezierCurveTo,
          params: [t.asNumber(e) + this._offset.x, t.asNumber(n) + this._offset.y, t.asNumber(r) + this._offset.x, t.asNumber(i) + this._offset.y, t.asNumber(o) + this._offset.x, t.asNumber(a) + this._offset.y]
        }), this
      }, n.prototype.svgPath = function(n) {
        if (n) {
          var r = e._PdfSvgPathHelper.offset(t.asString(n), this._offset);
          this._pathBuffer.push({
            func: this._doc._document.path,
            params: [t.asString(r)]
          })
        }
        return this
      }, n.prototype.closePath = function() {
        return this._writePathBuffer(), this._doc._document.closePath(), this
      }, n.prototype.rect = function(e, n, r, i) {
        return this._pathBuffer.push({
          func: this._doc._document.rect,
          params: [t.asNumber(e) + this._offset.x, t.asNumber(n) + this._offset.y, t.asNumber(r, !1, !0), t.asNumber(i, !1, !0)]
        }), this
      }, n.prototype.roundedRect = function(e, n, r, i, o) {
        return void 0 === o && (o = 0), this._pathBuffer.push({
          func: this._doc._document.roundedRect,
          params: [t.asNumber(e) + this._offset.x, t.asNumber(n) + this._offset.y, t.asNumber(r, !1, !0), t.asNumber(i, !1, !0), t.asNumber(o, !1, !0)]
        }), this
      }, n.prototype.ellipse = function(e, n, r, i) {
        return void 0 === i && (i = r), this._pathBuffer.push({
          func: this._doc._document.ellipse,
          params: [t.asNumber(e) + this._offset.x, t.asNumber(n) + this._offset.y, t.asNumber(r, !1, !0), t.asNumber(i, !1, !0)]
        }), this
      }, n.prototype.circle = function(e, n, r) {
        return this._pathBuffer.push({
          func: this._doc._document.circle,
          params: [t.asNumber(e) + this._offset.x, t.asNumber(n) + this._offset.y, t.asNumber(r, !1, !0)]
        }), this
      }, n.prototype.polygon = function(t) {
        if (t)
          for (var e = 0; e < t.length; e++) {
            var n = t[e];
            n[0] = n[0] + this._offset.x, n[1] = n[1] + this._offset.y
          }
        return this._pathBuffer.push({
          func: this._doc._document.polygon,
          params: t
        }), this
      }, n.prototype.clip = function(t) {
        return void 0 === t && (t = e.PdfFillRule.NonZero), this._writePathBuffer(), this._doc._document.clip(t === e.PdfFillRule.EvenOdd ? "even-odd" : "non-zero"), this
      }, n.prototype.fill = function(t, n) {
        return this._doc._toggleBrush(e._asPdfBrush(t)), this._writePathBuffer(), this._doc._document.fill(n === e.PdfFillRule.EvenOdd ? "even-odd" : "non-zero"), this
      }, n.prototype.fillAndStroke = function(t, n, r) {
        return this._doc._toggleBrush(e._asPdfBrush(t)), this._doc._togglePen(e._asPdfPen(n)), this._writePathBuffer(), this._doc._document.fillAndStroke(r === e.PdfFillRule.EvenOdd ? "even-odd" : "non-zero"), this
      }, n.prototype.stroke = function(t) {
        return this._doc._togglePen(e._asPdfPen(t)), this._writePathBuffer(), this._doc._document.stroke(), this
      }, n.prototype._hasPathBuffer = function() {
        return this._pathBuffer.length > 0
      }, n.prototype._writePathBuffer = function() {
        for (var t = this._doc._document, e = 0; e < this._pathBuffer.length; e++) {
          var n = this._pathBuffer[e];
          n.func.apply(t, n.params)
        }
        this._pathBuffer = []
      }, n
    }();
    e.PdfPaths = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function() {
      function e() {}
      return e.offset = function(t, e) {
        var n = this;
        return this._processPath(t, function(t, r, i, o) {
          return t = n._updateOffset(t, e, r, i, o)
        })
      }, e.scale = function(t, e) {
        return this._processPath(t, function(t, n, r, i) {
          if ("a" === n || "A" === n) {
            var o = i % 7;
            if (o >= 2 && o <= 4) return t
          }
          return t * e
        })
      }, e._processPath = function(e, n) {
        for (var r, i = this._getTokenizer(e), o = "", a = "", s = -1, u = -1; r = i();) {
          if (1 === r.length && /[a-zA-Z]/.test(r)) u++, o = r, s = -1;
          else {
            s++;
            var c = n(parseFloat(r), o, u, s);
            r = t.toFixed(c, 7, !1) + ""
          }
          a += r + " "
        }
        return a
      }, e._getTokenizer = function(t) {
        var e = t.length,
          n = 0;
        return function() {
          if (n >= e) return "";
          for (; n < e && (/\s/.test(t[n]) || "," == t[n]);) n++;
          for (var r = n, i = !1, o = !1, a = !1; n < e;) {
            var s = t[n];
            if ("-" === s || "+" === s) {
              if ((a || o || i) && (!(n > 1) || "e" !== t[n - 1] && "E" !== t[n - 1])) break;
              a = !0
            } else if ("." === s) {
              if (o) break;
              o = !0
            } else {
              if (!/[0-9eE]/.test(s)) break;
              i = !0
            }
            n++
          }
          return n != r ? t.substr(r, n - r) : t.substr(n++, 1)
        }
      }, e._updateOffset = function(t, e, n, r, i) {
        var o = 0;
        switch (n) {
          case "m":
            0 === r && (0 === i ? o = -1 : 1 === i && (o = 1));
            break;
          case "L":
          case "M":
          case "C":
          case "S":
          case "Q":
          case "T":
            o = i % 2 == 0 ? -1 : 1;
            break;
          case "A":
            i % 7 == 5 ? o = -1 : i % 7 == 6 && (o = 1);
            break;
          case "H":
            o = -1;
            break;
          case "V":
            o = 1
        }
        return o ? -1 === o ? t + e.x : t + e.y : t
      }, e
    }();
    e._PdfSvgPathHelper = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = !!(new XMLHttpRequest).overrideMimeType,
      r = function() {
        function e() {}
        return e.arrayBufferAsync = function(t, e, n) {
          var r = {
            method: "GET",
            responseType: "arraybuffer",
            async: !0
          };
          this._getData(t, r, e, n)
        }, e.arrayBuffer = function(e, r) {
          var i, o = {
            method: "GET",
            async: !1
          };
          return t.pdf._IE || !n ? (o.responseType = "arraybuffer", this._getData(e, o, function(t, e) {
            i = e
          }, r)) : (o.overrideMimeType = "text/plain; charset=x-user-defined", this._getData(e, o, function(t, e) {
            i = new ArrayBuffer(e.length);
            for (var n = new Uint8Array(i), r = 0, o = e.length; r < o; r++) n[r] = 255 & e.charCodeAt(r)
          }, r)), i
        }, e.text = function(t, e) {
          var n = {
              method: "GET",
              async: !1
            },
            r = "";
          return this._getData(t, n, function(t, e) {
            return r = e
          }, e), r
        }, e._getData = function(t, e, n, r) {
          var i = new XMLHttpRequest;
          if (e = e || {}, i.open(e.method, t, e.async, e.user, e.password), i.addEventListener("load", function() {
            if (4 === i.readyState) {
              var t = i.status;
              t >= 200 && t < 300 || 304 === t ? n && n(i, i.response) : r && r(i)
            }
          }), e.headers)
            for (var o in e.headers) i.setRequestHeader(o, e.headers[o]);
          e.responseType && (i.responseType = e.responseType), e.overrideMimeType && i.overrideMimeType && i.overrideMimeType(e.overrideMimeType), i.send(e.data)
        }, e
      }();
    e._XhrHelper = r
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function(t) {
      function e(e) {
        var n = t.call(this) || this;
        return n._chunks = e, n
      }
      return __extends(e, t), Object.defineProperty(e.prototype, "blob", {
        get: function() {
          return this._blob || (this._blob = new Blob(this._chunks, {
            type: "application/pdf"
          })), this._blob
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(e.prototype, "chunks", {
        get: function() {
          return this._chunks
        },
        enumerable: !0,
        configurable: !0
      }), e
    }(t.EventArgs);
    e.PdfDocumentEndedEventArgs = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n = function(n) {
      function r(r) {
        var i = n.call(this) || this;
        i._docInitialized = !1, i._compress = !0, i._bufferPages = !0, i._chunks = [], i._pageIndex = -1, i._graphicsStack = [], i._currentGS = {}, i.info = {
          author: void 0,
          creationDate: void 0,
          keywords: void 0,
          modDate: void 0,
          subject: void 0,
          title: void 0
        }, i.pageSettings = {
          layout: e.PdfPageOrientation.Portrait,
          size: e.PdfPageSize.Letter,
          margins: {
            top: 72,
            left: 72,
            bottom: 72,
            right: 72
          },
          _copy: function(t, e) {
            if ("size" === t) return this.size = e, !0
          }
        }, i.ended = new t.Event, i.pageAdded = new t.Event, i._runtimeProperties = ["pageIndex", "x", "y"], t.copy(i, r);
        var o = function(t) {
            i._doc = t, i._fontReg = new e._PdfFontRegistrar(i._doc)
          },
          a = function() {
            i.setPen(i._currentGS[i._pageIndex].pen), i.setBrush(i._currentGS[i._pageIndex].brush), i._curFont = e.PdfFont._DEF_PDFKIT_FONT, i.setFont(new e.PdfFont)
          },
          s = !1,
          u = {
            compress: i._compress,
            bufferPages: i._bufferPages,
            pageAdding: i._ehOnPageAdding = function(t, e) {
              i._docInitialized || (s = !0, o(t)), i._onPageAdding(t, e)
            },
            pageAdded: i._ehOnPageAdded = function(t) {
              var n = i._isDrawingText() ? i._currentGS[i._pageIndex].brush : new e.PdfSolidBrush;
              i._currentGS[++i._pageIndex] = {
                pen: new e.PdfPen,
                brush: n
              }, i._docInitialized || a(), i._onPageAdded(t)
            }
          };
        return i._doc = new PDFDocument(u), s || (o(i._doc), a()), i._doc.on("data", i._ehOnDocData = function(t) {
          i._onDocData(t)
        }).on("ending", i._ehOnDocEnding = function() {
          i._onDocEnding()
        }).on("end", i._ehOnDocEnded = function() {
          i._onDocEnded()
        }), i._docInitialized = !0, i
      }
      return __extends(r, n), Object.defineProperty(r.prototype, "compress", {
        get: function() {
          return this._compress
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "bufferPages", {
        get: function() {
          return this._bufferPages
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "header", {
        get: function() {
          var t = this;
          return this._header || (this._header = new e.PdfRunningTitle({
            _heightChanged: function() {
              t._docInitialized && t._resetAreasOffset(t._doc)
            }
          })), this._header
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "footer", {
        get: function() {
          var t = this;
          return this._footer || (this._footer = new e.PdfRunningTitle({
            _heightChanged: function() {
              t._docInitialized && t._resetAreasOffset(t._doc)
            }
          })), this._footer
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "pageIndex", {
        get: function() {
          return this._pageIndex
        },
        set: function(e) {
          e = t.asNumber(e, !1, !0), this._pageIndex !== e && (this._doc.switchToPage(e), this._pageIndex = e)
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.onEnded = function(t) {
        this.ended && this.ended.raise(this, t)
      }, r.prototype.onPageAdded = function(t) {
        this.pageAdded && this.pageAdded.raise(this, t)
      }, r.prototype.dispose = function() {
        this._doc && (this._doc.removeListener("data", this._ehOnDocData).removeListener("ending", this._ehOnDocEnding).removeListener("end", this._ehOnDocEnded).removeListener("pageAdding", this._ehOnPageAdding).removeListener("pageAdded", this._ehOnPageAdded), this._doc = null, this._chunks = null)
      }, Object.defineProperty(r.prototype, "currentPageSettings", {
        get: function() {
          var n = this._doc.page;
          return {
            layout: "landscape" === n.layout ? e.PdfPageOrientation.Landscape : e.PdfPageOrientation.Portrait,
            size: t.isArray(n.size) ? new t.Size(n.size[0], n.size[1]) : e.PdfPageSize[n.size.match(/\d+/) ? n.size : e._toTitleCase(n.size)],
            margins: {
              left: n.margins.left,
              right: n.margins.right,
              top: n.margins.top - this.header.height,
              bottom: n.margins.bottom - this.footer.height
            }
          }
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype.addPage = function(t) {
        var e = this._pageSettingsToNative(t || this.pageSettings);
        return this._doc.addPage(e), this
      }, r.prototype.bufferedPageRange = function() {
        return this._doc.bufferedPageRange()
      }, r.prototype.end = function() {
        this._doc.end()
      }, r.prototype.setBrush = function(t) {
        return this._assertAreasPathStarted(), this._setCurBrush(this._defBrush = e._asPdfBrush(t, !1).clone()), this
      }, r.prototype.setPen = function(t) {
        return this._assertAreasPathStarted(), this._setCurPen(this._defPen = e._asPdfPen(t, !1).clone()), this
      }, r.prototype.setFont = function(t) {
        return this._setCurFont(this._defFont = e._asPdfFont(t, !1).clone()), this
      }, r.prototype._getFont = function() {
        return this._curFont
      }, r.prototype.registerFont = function(n) {
        t.assert(!!n, e._Errors.ValueCannotBeEmpty("font"));
        var r;
        if (t.isString(n.source)) {
          var i;
          if (r = e._XhrHelper.arrayBuffer(n.source, function(t) {
            return i = t.statusText
          }), i) throw i
        } else {
          if (!(n.source instanceof ArrayBuffer)) throw e._Errors.FontSourceMustBeStringArrayBuffer;
          r = n.source
        }
        return n = e._shallowCopy(n), n.source = r, this._fontReg.registerFont(n), this
      }, r.prototype.registerFontAsync = function(n, r) {
        var i = this;
        t.assert("string" == typeof n.source, e._Errors.FontSourceMustBeString), t.asFunction(r, !1), e._XhrHelper.arrayBufferAsync(n.source, function(t, o) {
          var a = e._shallowCopy(n);
          a.source = o, i._fontReg.registerFont(a), r(n)
        })
      }, r.prototype.saveState = function() {
        return this._assertAreasPathStarted(), this._graphicsStack.push(this._currentGS[this._pageIndex].pen.clone(), this._defPen.clone(), this._currentGS[this._pageIndex].brush.clone(), this._defBrush.clone()), this.document._document.save(), this
      }, r.prototype.restoreState = function() {
        return this._assertAreasPathStarted(), this._graphicsStack.length && (this._defBrush = this._graphicsStack.pop(), this._currentGS[this._pageIndex].brush = this._graphicsStack.pop(), this._defPen = this._graphicsStack.pop(), this._currentGS[this._pageIndex].pen = this._graphicsStack.pop()), this.document._document.restore(), this
      }, r.prototype._copy = function(e, n) {
        return "compress" === e ? (this._compress = t.asBoolean(n), !0) : "bufferPages" === e ? (this._bufferPages = t.asBoolean(n), !0) : this._runtimeProperties.indexOf(e) >= 0
      }, Object.defineProperty(r.prototype, "_document", {
        get: function() {
          return this._doc
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype._switchTextFlowCtx = function(t) {
        this._doc.x = t.xo, this._doc.y = t.yo, this._doc.lineGap(t.lineGap)
      }, r.prototype._getTextFlowCtxState = function() {
        return {
          xo: this._doc.x,
          yo: this._doc.y,
          lineGap: this._doc.currentLineGap()
        }
      }, r.prototype._toggleBrush = function(t) {
        t ? this._setCurBrush(t) : this._setCurBrush(this._defBrush)
      }, r.prototype._togglePen = function(t) {
        t ? this._setCurPen(t) : this._setCurPen(this._defPen)
      }, r.prototype._toggleFont = function(t) {
        t ? this._setCurFont(t) : this._setCurFont(this._defFont)
      }, r.prototype._onDocData = function(t) {
        this._chunks.push(t)
      }, r.prototype._onDocEnding = function() {
        if (this._processHeadersFooters(), this.info) {
          var t;
          (t = this.info.author) && (this._doc.info.Author = t), (t = this.info.creationDate) && (this._doc.info.CreationDate = t), (t = this.info.keywords) && (this._doc.info.Keywords = t), (t = this.info.modDate) && (this._doc.info.ModDate = t), (t = this.info.subject) && (this._doc.info.Subject = t), (t = this.info.title) && (this._doc.info.Title = t)
        }
      }, r.prototype._onDocEnded = function() {
        if (e._IE && this._chunks.length && !this._chunks[0].buffer)
          for (var t = 0; t < this._chunks.length; t++) {
            for (var n = this._chunks[t], r = new Uint8Array(n.length), i = 0; i < n.length; i++) r[i] = n[i];
            this._chunks[t] = r.buffer
          }
        this.onEnded(new e.PdfDocumentEndedEventArgs(this._chunks)), this._chunks = []
      }, r.prototype._onPageAdding = function(t, e) {
        if (this.pageSettings) {
          var n = this._pageSettingsToNative(this.pageSettings);
          e.layout = t.options.layout = n.layout, e.margins = t.options.margins = n.margins, e.size = t.options.size = n.size
        }
      }, r.prototype._onPageAdded = function(n) {
        n.page.originalMargins = e._shallowCopy(n.page.margins), this._resetAreasOffset(n), this.onPageAdded(t.EventArgs.empty)
      }, r.prototype._assertAreasPathStarted = function() {
        this._docInitialized && (this._assertPathStarted(), this.header._assertPathStarted(), this.footer._assertPathStarted())
      }, r.prototype._pageSettingsToNative = function(n) {
        var r = {};
        if (n) {
          var i = t.asEnum(n.layout, e.PdfPageOrientation, !0);
          null != i && (r.layout = (e.PdfPageOrientation[i] || "").toLowerCase());
          var o = n.margins;
          o && (r.margins = {
            left: t.asNumber(o.left, !1, !0),
            right: t.asNumber(o.right, !1, !0),
            top: t.asNumber(o.top, !1, !0),
            bottom: t.asNumber(o.bottom, !1, !0)
          });
          var a = n.size;
          null != a && (a instanceof t.Size ? r.size = [t.asNumber(a.width, !1, !0), t.asNumber(a.height, !1, !0)] : (a = t.asEnum(a, e.PdfPageSize), r.size = (e.PdfPageSize[a] || "").toUpperCase()))
        }
        return r
      }, r.prototype._processHeadersFooters = function() {
        var n = this.header,
          r = this.footer;
        if (n.height > 0 || r.height > 0) {
          var i = this._doc;
          t.assert(i.options.bufferPages, e._Errors.BufferPagesMustBeEnabled);
          for (var o = i.bufferedPageRange(), a = o.start; a < o.count; a++) {
            var s = {
              Page: a + 1,
              Pages: o.count
            };
            this.pageIndex = a, this._renderHeaderFooter(n, s, !0), this._renderHeaderFooter(r, s, !1)
          }
        }
      }, r.prototype._renderHeaderFooter = function(t, n, r) {
        if (t.height > 0 && t.declarative && t.declarative.text) {
          var i = e._formatMacros(t.declarative.text, n).split("\t");
          i.length > 0 && i[0] && this._renderHeaderFooterPart(t, i[0], e.PdfTextHorizontalAlign.Left, r), i.length > 1 && i[1] && this._renderHeaderFooterPart(t, i[1], e.PdfTextHorizontalAlign.Center, r), i.length > 2 && i[2] && this._renderHeaderFooterPart(t, i[2], e.PdfTextHorizontalAlign.Right, r)
        }
      }, r.prototype._renderHeaderFooterPart = function(t, e, n, r) {
        var i = {
          font: t.declarative.font,
          brush: t.declarative.brush,
          width: t.width,
          height: t.height,
          align: n
        };
        if (r) this.header.drawText(e, 0, 0, i);
        else {
          i.includeLastLineExternalLeading = !1;
          var o = this.footer.measureText(e, i.font, i);
          this.footer.drawText(e, 0, this.footer.height - o.size.height, i)
        }
      }, r.prototype._setCurBrush = function(t) {
        this._currentGS[this.pageIndex].brush.equals(t) || (this._setNativeDocBrush(t, !1), this._currentGS[this.pageIndex].brush = t.clone())
      }, r.prototype._setCurFont = function(t) {
        if (!this._curFont.equals(t)) {
          var n = this._fontReg.findFont(t.family, t.style, t.weight);
          this._doc.font(n, t.size || e.PdfFont._DEF_FONT.size), this._curFont = t.clone()
        }
      }, r.prototype._setCurPen = function(t) {
        var e = this._doc,
          n = this._currentGS[this.pageIndex].pen;
        !t.brush || n.brush && n.brush.equals(t.brush) ? (n.brush && !t.brush || !n.brush && !n.color.equals(t.color)) && e.strokeColor([t.color.r, t.color.g, t.color.b], t.color.a) : this._setNativeDocBrush(t.brush, !0), n.width !== t.width && e.lineWidth(t.width), n.miterLimit !== t.miterLimit && e.miterLimit(t.miterLimit), n.cap !== t.cap && e.lineCap(t.cap), n.join !== t.join && e.lineJoin(t.join), n.dashPattern.equals(t.dashPattern) || (null != t.dashPattern.dash ? e.dash(t.dashPattern.dash, {
          space: t.dashPattern.gap,
          phase: t.dashPattern.phase
        }) : null != n.dashPattern.dash && e.undash()), this._currentGS[this.pageIndex].pen = t.clone()
      }, r.prototype._setNativeDocBrush = function(n, r) {
        var i = this._doc,
          o = n._getBrushObject(this),
          a = 1;
        o instanceof t.Color ? (a = o.a, o = [o.r, o.g, o.b]) : n instanceof e.PdfGradientBrush && (a = n.opacity), r ? i.strokeColor(o, a) : i.fillColor(o, a)
      }, r.prototype._resetAreasOffset = function(t) {
        t.page.margins.top = t.page.originalMargins.top + this.header.height, t.y = t.page.margins.top, t.page.margins.bottom = t.page.originalMargins.bottom + this.footer.height, this._header._initialize(this, t.page.margins.left, t.page.originalMargins.top), this._initialize(this, t.page.margins.left, t.page.margins.top), this._footer._initialize(this, t.page.margins.left, t.page.height - t.page.margins.bottom)
      }, r
    }(e.PdfPageArea);
    e.PdfDocument = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(t) {
    "use strict";

    function e(t) {
      return t && (t = t.trim().replace(/\s+/gm, " ")), t
    }

    function n(t, e) {
      return t && e && !/(^[a-z][a-z0-9]*:)?\/\//i.test(t) && (t = e(t)), t
    }
    t._compressSpaces = e, t._resolveUrlIfRelative = n;
    var r = function() {
      function t(t, e) {
        this.selector = t, this.declarations = {}, this._fillDeclarations(e)
      }
      return t.prototype._fillDeclarations = function(t) {
        var e = this;
        t && t.split(";").forEach(function(t) {
          if (t) {
            var n = t.split(":");
            if (2 === n.length) {
              var r = n[0].trim().toLowerCase(),
                i = n[1].trim();
              if (r && i) {
                var o = /!important$/i.test(i);
                o && (i = i.replace(/!important$/i, "").trim()), i && (e.declarations[r] = {
                  value: i,
                  important: o
                })
              }
            }
          }
        })
      }, t
    }();
    t._SvgCssRule = r;
    var i = function() {
      function t() {}
      return t.matchesSelector = function(t, e) {
        var n = !1;
        try {
          n = (t.matches || t.msMatchesSelector || t.webkitMatchesSelector || t.mozMatchesSelector).call(t, e)
        } catch (t) {}
        return n
      }, t.getSpecificity = function(t) {
        var e = 0,
          n = 0,
          r = 0,
          i = function(e) {
            var n = (t.match(e) || []).length;
            return n && (t = t.replace(e, "")), n
          };
        return t = t.replace(/:not\(([^\)]*)\)/g, function(t, e) {
          return " " + e + " "
        }), n += i(/(\[[^\]]+\])/g), e += i(/(#[^\s\+>~\.\[:]+)/g), n += i(/(\.[^\s\+>~\.\[:]+)/g), r += i(/(::[^\s\+>~\.\[:]+|:first-line|:first-letter|:before|:after)/gi), n += i(/(:[\w-]+\([^\)]*\))/gi), n += i(/(:[^\s\+>~\.\[:]+)/g), t = t.replace(/[\*\s\+>~]/g, " "), t = t.replace(/[#\.]/g, " "), r += i(/([^\s\+>~\.\[:]+)/g), e << 16 | n << 8 | r
      }, t.getComputedStyle = function(t, n) {
        var i = this,
          o = {},
          a = [];
        if (t.className)
          for (var a = [], s = 0, u = Object.keys(n); s < u.length; s++) {
            var c = u[s];
            this.matchesSelector(t, c) && a.push(n[c])
          }
        a.sort(function(t, e) {
          return i.getSpecificity(t.selector) - i.getSpecificity(e.selector)
        });
        var l = t.getAttribute("style");
        l && a.push(new r("_inline_", e(l)));
        for (s = 0; s < a.length; s++)
          for (var h = a[s], f = 0, u = Object.keys(h.declarations); f < u.length; f++) {
            var d = u[f],
              p = h.declarations[d];
            null != o[d] && !p.important && o[d].important || (o[d] = p)
          }
        for (var g = {}, s = 0, u = Object.keys(o); s < u.length; s++) g[d = u[s]] = o[d].value;
        return g
      }, t.registerFontFace = function(t, e, r) {
        var i = e.declarations;
        i["font-family"] && i.src && i.src.value.split(",").every(function(e) {
          if (e.match(/format\(\s*['"]?truetype['"]?\s*\)/i)) {
            var o = e.match(/url\(\s*['"]?([^'"\)]+)['"]?\s*\)/i);
            if (o) {
              var a = o[1].trim(),
                s = !1;
              if (a = n(a, r)) {
                var u = {
                  name: i["font-family"].value,
                  source: a,
                  weight: i["font-weight"] ? i["font-weight"].value.toLowerCase() : "normal",
                  style: i["font-style"] ? i["font-style"].value.toLowerCase() : "normal"
                };
                try {
                  t.registerFont(u), s = !0
                } catch (t) {}
              }
              return !s
            }
          }
          return !0
        })
      }, t
    }();
    t._SvgCssHelper = i
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n;
    ! function(t) {
      t[t.Default = 1] = "Default", t[t.None = 2] = "None", t[t.Px = 3] = "Px"
    }(n = e._SvgNumConversion || (e._SvgNumConversion = {}));
    var r;
    ! function(t) {
      t[t.Width = 1] = "Width", t[t.Height = 2] = "Height", t[t.Other = 3] = "Other"
    }(r = e._SvgLengthContext || (e._SvgLengthContext = {}));
    var i;
    ! function(t) {
      t[t.Number = 1] = "Number", t[t.String = 2] = "String"
    }(i = e._SvgAttrType || (e._SvgAttrType = {}));
    var o = function() {
      function o(i, o, a, s, u, c, l) {
        void 0 === s && (s = void 0), void 0 === u && (u = n.Default), void 0 === c && (c = r.Other), void 0 === l && (l = !1), t.assert(!!i, e._Errors.ValueCannotBeEmpty("owner")), t.assert(!!o, e._Errors.ValueCannotBeEmpty("propName")), this._owner = i, this._propName = o, this._propType = a, this._defValue = s, this._inheritable = l, this._nc = u, this._pCtx = c, this._searchValue = !0
      }
      return o.parseValue = function(t, o, a, s, u) {
        if (null == t) return t;
        if (o & i.Number) {
          var c, l;
          if ("number" == typeof t) c = t;
          else {
            var h = t.match(/^([\+-]?[\d\.]+)(em|ex|px|pt|pc|cm|mm|in|%)?$/);
            h && (c = parseFloat(h[1]), l = h[2])
          }
          if (null != c && c === c) {
            if (u !== n.Default) {
              if (l) throw e._Errors.InvalidFormat(t);
              if (u === n.None) return c
            }
            switch (l) {
              case "mm":
                return 72 * c / 25.4;
              case "cm":
                return 72 * c / 2.54;
              case "in":
                return 72 * c;
              case "pt":
                return c;
              case "pc":
                return 12 * c;
              case "%":
                switch (s) {
                  case r.Height:
                    c *= a.height / 100;
                    break;
                  case r.Width:
                    c *= a.width / 100;
                    break;
                  case r.Other:
                    c *= Math.sqrt(a.width * a.width + a.height * a.height) / Math.sqrt(2) / 100
                }
                return c;
              case "px":
              default:
                return .75 * c
            }
          }
        }
        if (o & i.String) return t + "";
        throw e._Errors.InvalidFormat(t)
      }, Object.defineProperty(o.prototype, "hasVal", {
        get: function() {
          return null != this._val
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "val", {
        get: function() {
          if (null != this._val) return this._val;
          var e = t.isFunction(this._defValue) ? this._defValue.call(this, this._owner.ctx) : this._defValue;
          return this._parse(e)
        },
        set: function(t) {
          this._searchValue = !1, this._value = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(o.prototype, "_val", {
        get: function() {
          if (this._searchValue) {
            this._searchValue = !1;
            for (var t, e = this._owner; e && (t = e.attr(this._propName), this._inheritable && (null == t || "inherit" == t)); e = e.parent);
            this._value = "inherit" === t ? void 0 : this._parse(t)
          }
          return this._value
        },
        enumerable: !0,
        configurable: !0
      }), o.prototype.reset = function() {
        this._value = void 0, this._searchValue = !0
      }, o.prototype._parse = function(t, e) {
        return t = o.parseValue(t, this._propType, this._owner.viewport, this._pCtx, e || this._nc)
      }, o
    }();
    e._SvgAttr = o;
    var a = function(t) {
      function e(e, o, a, s, u, c) {
        return void 0 === a && (a = void 0), void 0 === s && (s = n.Default), void 0 === u && (u = r.Other), t.call(this, e, o, i.Number, a, s, u, c) || this
      }
      return __extends(e, t), e
    }(o);
    e._SvgNumAttr = a;
    var s = function(t) {
      function e(e, n, r, o) {
        return t.call(this, e, n, i.String, r, void 0, void 0, o) || this
      }
      return __extends(e, t), e
    }(o);
    e._SvgStrAttr = s;
    var u = function(t) {
      function e(e, o, a, s) {
        return void 0 === a && (a = void 0), void 0 === s && (s = !0), t.call(this, e, o, i.String, a, n.None, r.Other, s) || this
      }
      return __extends(e, t), e.prototype.asHref = function() {
        var t = this.val.match(/url\((.+)\)/);
        return t ? t[1] : null
      }, e.prototype._parse = function(e) {
        if ("" !== e && "null" !== e && "undefined" !== e) return t.prototype._parse.call(this, e)
      }, e
    }(o);
    e._SvgColorAttr = u;
    var c = function(t) {
      function e(e) {
        return t.call(this, e, "stroke-dasharray", i.Number, void 0, n.Px, r.Other, !0) || this
      }
      return __extends(e, t), e.prototype._parse = function(e) {
        var n, r = (e || "").trim().split(/[\s,]+/);
        if (r.length) {
          n = [];
          try {
            for (var i = 0; i < r.length; i++) r[i] && n.push(t.prototype._parse.call(this, r[i]))
          } catch (t) {
            return
          }
          return n.length ? n : void 0
        }
        return n
      }, e
    }(o);
    e._SvgDashArrayAttr = c;
    var l = function(n) {
      function r(t, r) {
        return n.call(this, t, r, i.String, e.PdfFillRule.NonZero, void 0, void 0, !0) || this
      }
      return __extends(r, n), r.prototype._parse = function(n) {
        if (t.isNumber(n)) return n;
        var r = (n || "").match(/(nonzero|evenodd)/i);
        return r ? "nonzero" === r[1] ? e.PdfFillRule.NonZero : e.PdfFillRule.EvenOdd : void 0
      }, r
    }(o);
    e._SvgFillRuleAttr = l;
    var h = function(t) {
      function e(e, n) {
        return t.call(this, e, n) || this
      }
      return __extends(e, t), e.prototype._parse = function(t) {
        var e = (t = (t || "").trim()).match(/url\((.+)\)/);
        return e && (t = e[1].trim()), t = t.replace(/["']/g, "")
      }, e
    }(s);
    e._SvgHRefAttr = h;
    var f = function(t) {
      function e() {
        return null !== t && t.apply(this, arguments) || this
      }
      return __extends(e, t), e.prototype._parse = function(e) {
        if ((e = t.prototype._parse.call(this, e)) && "#" === e[0]) return e.substring(1)
      }, e
    }(h);
    e._SvgIdRefAttr = f;
    var d = function(e) {
      function r(t, r) {
        return e.call(this, t, r, i.Number, void 0, n.Px) || this
      }
      return __extends(r, e), r.prototype._parse = function(n) {
        var r, i = (n || "").trim().split(/[\s,]+/),
          o = 2 * Math.floor(i.length / 2);
        if (o) {
          r = [];
          try {
            for (var a = 0; a < o - 1; a += 2) r.push(new t.Point(e.prototype._parse.call(this, i[a]), e.prototype._parse.call(this, i[a + 1])))
          } catch (t) {
            return
          }
        }
        return r
      }, r
    }(o);
    e._SvgPointsArrayAttr = d;
    var p = function(e) {
      function r(t) {
        return e.call(this, t, "transform", i.Number, void 0, n.None) || this
      }
      return __extends(r, e), r.prototype.apply = function(t) {
        var e = t.ctx.area;
        this.hasVal && this.val.forEach(function(t) {
          t(e)
        })
      }, r.prototype._parse = function(r) {
        var i = this,
          o = [],
          a = (r || "").match(/((matrix|translate|scale|rotate|skewX|skewY)\([^\)]+\))+/g);
        if (a)
          for (var s = 0; s < a.length; s++) {
            var u = a[s].match(/(\w+)\(([^\)]+)\)/),
              c = [];
            try {
              u[2].trim().split(/[\s,]+/).forEach(function(t) {
                t && c.push(e.prototype._parse.call(i, t, n.None))
              })
            } catch (t) {
              return
            }
            if (c.length) switch (u[1]) {
              case "matrix":
                o.push(function(t, e, n, r, i, o) {
                  return function(a) {
                    a.transform(t, e, n, r, i, o)
                  }
                }(c[0], c[1], c[2], c[3], e.prototype._parse.call(this, c[4], n.Px), e.prototype._parse.call(this, c[5], n.Px)));
                break;
              case "translate":
                o.push(function(t, e) {
                  return function(n) {
                    n.translate(t, e)
                  }
                }(e.prototype._parse.call(this, c[0], n.Px), e.prototype._parse.call(this, c[1] || 0, n.Px)));
                break;
              case "scale":
                o.push(function(t, e) {
                  return function(n) {
                    n.scale(t, e)
                  }
                }(c[0], c[1]));
                break;
              case "rotate":
                o.push(function(t, e) {
                  return function(n) {
                    n.rotate(t, e)
                  }
                }(c[0], new t.Point(e.prototype._parse.call(this, c[1] || 0, n.Px), e.prototype._parse.call(this, c[2] || 0, n.Px))));
                break;
              case "skewX":
                o.push(function(t) {
                  return function(e) {
                    e.transform(1, 0, t, 1, 0, 0)
                  }
                }(Math.tan(c[0] * Math.PI / 180)));
                break;
              case "skewY":
                o.push(function(t) {
                  return function(e) {
                    e.transform(1, t, 0, 1, 0, 0)
                  }
                }(Math.tan(c[0] * Math.PI / 180)))
            }
          }
        return o.length ? o : void 0
      }, r
    }(o);
    e._SvgTransformAttr = p;
    var g = function(t) {
      function e(e) {
        return t.call(this, e, "text-decoration", i.String, void 0, n.None) || this
      }
      return __extends(e, t), e.prototype._parse = function(t) {
        var e, n = (t || "").trim().toLowerCase().split(/[\s,]+/);
        if (n.length) {
          e = [];
          for (var r = 0; r < n.length; r++) /line-through|overline|underline/.test(n[r]) && e.push(n[r])
        }
        return e && e.length ? e : void 0
      }, e
    }(o);
    e._SvgTextDecorationAttr = g;
    var _ = function(t) {
      function e(e) {
        return t.call(this, e, "viewBox", i.Number, void 0, n.Px) || this
      }
      return __extends(e, t), e.prototype._parse = function(e) {
        var n, r = (e || "").trim().split(/[\s,]+/);
        return 4 === r.length && (n = {
          minX: t.prototype._parse.call(this, r[0]),
          minY: t.prototype._parse.call(this, r[1]),
          width: t.prototype._parse.call(this, r[2]),
          height: t.prototype._parse.call(this, r[3])
        }), n
      }, e
    }(o);
    e._SvgViewboxAttr = _;
    var m = function(t) {
      function e(e) {
        return t.call(this, e, "preserveAspectRatio", i.Number, "xMidYMid meet") || this
      }
      return __extends(e, t), e.prototype._parse = function(t) {
        var e;
        if ("string" == typeof t) {
          var n = t.replace(/^defer\s+/, "").trim().split(/\s+/);
          e = {
            align: n[0],
            meet: !("slice" === n[1])
          }
        } else e = t;
        return e
      }, e
    }(o);
    e._SvgPreserveAspectRatioAttr = m;
    var v = function() {
      function e(t) {
        this._owner = t, this.aspect = new m(this._owner), this.viewBox = new _(this._owner)
      }
      return e.prototype.apply = function(e) {
        var n = e.ctx.area,
          r = e.viewport,
          i = this.viewBox.val;
        if (r && i) {
          if (i.width && i.height) {
            var o = this.aspect.val,
              a = r.width / i.width,
              s = r.height / i.height,
              u = Math.min(a, s),
              c = Math.max(a, s),
              l = i.width * (o.meet ? u : c),
              h = i.height * (o.meet ? u : c);
            if ("none" === o.align) n.scale(a, s);
            else {
              var f = o.meet ? u : c,
                d = 0,
                p = 0;
              o.align.match(/^xMid/) && f === s ? d = r.width / 2 - l / 2 : o.align.match(/^xMax/) && f === s && (d = r.width - l), o.align.match(/YMid$/) && f === a ? p = r.height / 2 - h / 2 : o.align.match(/YMax$/) && f === a && (p = r.height - h), (d || p) && n.translate(d, p), o.meet ? n.scale(u, u) : n.scale(c, c), (i.minX || i.minY) && n.translate(-i.minX, -i.minY)
            }
          }
          return new t.Size(i.width, i.height)
        }
        return r
      }, e
    }();
    e._SvgScaleAttributes = v;
    var y = function() {
      function t(t) {
        this._owner = t, this.color = new u(this._owner, "stroke", "none"), this.dashArray = new c(this._owner), this.dashOffset = new a(this._owner, "stroke-dashoffset", 0, n.Default, r.Other, !0), this.lineCap = new s(this._owner, "stroke-linecap", "butt", !0), this.lineJoin = new s(this._owner, "stroke-linejoin", "miter", !0), this.miterLimit = new a(this._owner, "stroke-miterlimit", 4, n.None, r.Other, !0), this.opacity = new a(this._owner, "stroke-opacity", 1, n.None, r.Other, !0), this.width = new a(this._owner, "stroke-width", 1, n.Default, r.Other, !0)
      }
      return t.prototype.toPen = function(t) {
        var n = new e._SafeColor(this.color.val);
        this.opacity.hasVal && (n.a = this.opacity.val);
        var r = new e.PdfPen(n, this.width.val);
        if (this.dashArray.hasVal) {
          var i = this.dashArray.val;
          i.length && (r.dashPattern = new e.PdfDashPattern(i[0], i.length > 1 ? i[1] : void 0, this.dashOffset.val))
        }
        switch (this.lineCap.val) {
          case "butt":
            r.cap = e.PdfLineCapStyle.Butt;
            break;
          case "round":
            r.cap = e.PdfLineCapStyle.Round;
            break;
          case "square":
            r.cap = e.PdfLineCapStyle.Square
        }
        switch (this.lineJoin.val) {
          case "miter":
            r.join = e.PdfLineJoinStyle.Miter;
            break;
          case "round":
            r.join = e.PdfLineJoinStyle.Round;
            break;
          case "bevel":
            r.join = e.PdfLineJoinStyle.Bevel
        }
        return r.miterLimit = this.miterLimit.val, r
      }, t
    }();
    e._SvgStrokeAttributes = y;
    var b = function() {
      function t(t) {
        this._owner = t, this.color = new u(this._owner, "fill", "black"), this.opacity = new a(this._owner, "fill-opacity", 1, n.None, void 0, !0), this.rule = new l(this._owner, "fill-rule")
      }
      return t.prototype.toBrush = function(t) {
        var n, r = this.color.asHref();
        if (r && t) {
          var i = t.ctx.getElement(r);
          if (i instanceof e._SvgLinearGradientElementImpl) return i.toBrush(t)
        }
        return n = new e._SafeColor(this.color.val), this.opacity.hasVal && (n.a = this.opacity.val), new e.PdfSolidBrush(n)
      }, t
    }();
    e._SvgFillAttributes = b;
    var w = function() {
      function n(t) {
        this._owner = t, this.family = new s(this._owner, "font-family", function(t) {
          var e = t.area.document._getFont();
          return e ? e.family : void 0
        }, !0), this.size = new o(this._owner, "font-size", i.Number | i.String, "medium", void 0, r.Other, !0), this.style = new s(this._owner, "font-style", "normal", !0), this.weight = new s(this._owner, "font-weight", "normal", !0)
      }
      return n.prototype.toFont = function() {
        var n = t.pdf._asPt(this.size.val);
        return new e.PdfFont(this.family.val, n, this.style.val, this.weight.val)
      }, n
    }();
    e._SvgFontAttributes = w;
    var x = function() {
      function n(t) {
        this._owner = t, this.clipRule = new l(this._owner, "clip-rule"), this.fill = new b(this._owner), this.font = new w(this._owner), this.stroke = new y(this._owner)
      }
      return n.prototype.apply = function(n, r, i) {
        var o = n.ctx.area;
        n.renderMode === e._SvgRenderMode.Clip || (r && i && "none" !== this.fill.color.val && "none" !== this.stroke.color.val ? o.paths.fillAndStroke(this.fill.toBrush(n), this.stroke.toPen(n), this.fill.rule.val) : r && "none" !== this.fill.color.val ? o.paths.fill(this.fill.toBrush(n), this.fill.rule.val) : i && "none" !== this.stroke.color.val ? o.paths.stroke(this.stroke.toPen(n)) : o.paths.stroke(t.Color.fromRgba(0, 0, 0, 0)))
      }, n
    }();
    e._SvgStyleAttributes = x
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var __extends = this && this.__extends || function() {
    var t = Object.setPrototypeOf || {
        __proto__: []
      }
      instanceof Array && function(t, e) {
        t.__proto__ = e
      } || function(t, e) {
        for (var n in e) e.hasOwnProperty(n) && (t[n] = e[n])
      };
    return function(e, n) {
      function r() {
        this.constructor = e
      }
      t(e, n), e.prototype = null === n ? Object.create(n) : (r.prototype = n.prototype, new r)
    }
  }(),
  wijmo;
! function(t) {
  ! function(e) {
    "use strict";
    var n;
    ! function(t) {
      t[t.Render = 0] = "Render", t[t.Ignore = 1] = "Ignore", t[t.Clip = 2] = "Clip"
    }(n = e._SvgRenderMode || (e._SvgRenderMode = {}));
    var r = function() {
      function t(t, e, r) {
        void 0 === r && (r = n.Render), this._children = [], this._attributes = {}, this._defRenderMode = r, this._ctx = t
      }
      return Object.defineProperty(t.prototype, "children", {
        get: function() {
          return this._children
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "ctx", {
        get: function() {
          return this._ctx
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "parent", {
        get: function() {
          return this._parent
        },
        set: function(t) {
          this._parent = t
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "style", {
        get: function() {
          return this._style || (this._style = new e._SvgStyleAttributes(this)), this._style
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(t.prototype, "viewport", {
        get: function() {
          return this._viewport
        },
        set: function(t) {
          this._viewport = t.clone()
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype.attr = function(t, e) {
        return t = t.toLowerCase(), arguments.length > 1 && (this._attributes[t] = e), this._attributes[t]
      }, t.prototype.appendNode = function(t) {
        t && t !== this && t.parent !== this && (t.remove(), this.children.push(t), t.parent = this)
      }, t.prototype.copyAttributesFrom = function(t, e) {
        if (t) {
          var n = t._attributes,
            r = this._attributes;
          for (var i in n) n.hasOwnProperty(i) && null == r[i] && (!e || e.indexOf(i) < 0) && (r[i] = n[i])
        }
      }, t.prototype.clone = function() {
        var t = new(Function.prototype.bind.call(this.constructor, null, this.ctx, null));
        return t.copyAttributesFrom(this), this._children.forEach(function(e) {
          t.appendNode(e.clone())
        }), t
      }, t.prototype.remove = function() {
        var t = this.parent;
        if (t) {
          for (var e = 0; e < t.children.length; e++)
            if (t.children[e] === this) {
              t.children.splice(e, 1);
              break
            }
          this.parent = null
        }
      }, t.prototype.clearAttr = function(t) {
        delete this._attributes[t.toLowerCase()]
      }, t.prototype.render = function(t, e) {
        this._viewport = t.clone(), (this._curRenderMode = e || this._defRenderMode) !== n.Ignore && this._render()
      }, Object.defineProperty(t.prototype, "renderMode", {
        get: function() {
          return this._curRenderMode
        },
        enumerable: !0,
        configurable: !0
      }), t.prototype._render = function() {
        this._renderContent()
      }, t.prototype._renderContent = function() {
        for (var t = 0; t < this._children.length; t++) this._children[t].render(this.viewport, this.renderMode)
      }, t
    }();
    e._SvgElementBase = r;
    var i = function(t) {
      function r(r, i, o) {
        void 0 === o && (o = n.Render);
        var a = t.call(this, r, i, o) || this;
        return a._clipPath = new e._SvgIdRefAttr(a, "clip-path"), a
      }
      return __extends(r, t), r.prototype._render = function() {
        var e, r = this.ctx.area;
        if (this._clipPath.val) {
          var i = this.ctx.getElement(this._clipPath.val);
          (e = !!(i && i instanceof p)) && (r.document.saveState(), i.render(this.viewport, n.Clip), r.paths.clip(this.style.clipRule.val))
        }
        t.prototype._render.call(this), e && r.document.restoreState()
      }, r
    }(r);
    e._SvgClippableElementBase = i;
    var o = function(t) {
      function r(n, r) {
        var i = t.call(this, n, r) || this;
        return i._transform = new e._SvgTransformAttr(i), i
      }
      return __extends(r, t), r.prototype._render = function() {
        var e = this._transform.hasVal && this.renderMode !== n.Clip;
        e && (this.ctx.area.document.saveState(), this._transform.apply(this)), t.prototype._render.call(this), e && this.ctx.area.document.restoreState()
      }, r
    }(i);
    e._SvgTransformableElementBase = o;
    var a = function(n) {
      function r() {
        var t = null !== n && n.apply(this, arguments) || this;
        return t._fill = !0, t._stroke = !0, t
      }
      return __extends(r, n), r.prototype._renderContent = function() {
        this._draw(), this.style.apply(this, this._fill, this._stroke)
      }, r.prototype._draw = function() {
        t.assert(!1, e._Errors.AbstractMethod)
      }, r
    }(o);
    e._SvgShapeElementBase = a;
    var s = function(t) {
      function n() {
        return null !== t && t.apply(this, arguments) || this
      }
      return __extends(n, t), n.prototype._draw = function() {
        var t = new e._SvgNumAttr(this, "r", 0).val;
        if (t > 0) {
          var n = new e._SvgNumAttr(this, "cx", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width).val,
            r = new e._SvgNumAttr(this, "cy", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height).val;
          this.ctx.area.paths.circle(n, r, t)
        }
      }, n
    }(a);
    e._SvgCircleElementImpl = s;
    var u = function(t) {
      function n() {
        return null !== t && t.apply(this, arguments) || this
      }
      return __extends(n, t), n.prototype._draw = function() {
        var t = new e._SvgNumAttr(this, "rx", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width).val,
          n = new e._SvgNumAttr(this, "ry", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height).val;
        if (t > 0 && n > 0) {
          var r = new e._SvgNumAttr(this, "cx", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width).val,
            i = new e._SvgNumAttr(this, "cy", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height).val;
          this.ctx.area.paths.ellipse(r, i, t, n)
        }
      }, n
    }(a);
    e._SvgEllipseElementImpl = u;
    var c = function(t) {
      function n(e, n) {
        var r = t.call(this, e, n) || this;
        return r._fill = !1, r
      }
      return __extends(n, t), n.prototype._draw = function() {
        var t = new e._SvgNumAttr(this, "x1", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width).val,
          n = new e._SvgNumAttr(this, "y1", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height).val,
          r = new e._SvgNumAttr(this, "x2", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width).val,
          i = new e._SvgNumAttr(this, "y2", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height).val;
        this.ctx.area.paths.moveTo(t, n).lineTo(r, i)
      }, n
    }(a);
    e._SvgLineElementImpl = c;
    var l = function(t) {
      function r(n, r) {
        var i = t.call(this, n, r) || this;
        return i._d = new e._SvgStrAttr(i, "d"), i
      }
      return __extends(r, t), r.prototype._renderContent = function() {
        var r = this.ctx.area;
        if (this.renderMode === n.Clip) {
          if (this._d.hasVal) {
            var i = e._PdfSvgPathHelper.scale(this._d.val, .75);
            this.attr("d", i), this._d.reset()
          }
          t.prototype._renderContent.call(this)
        } else r.document.saveState(), r.scale(.75), t.prototype._renderContent.call(this), r.document.restoreState()
      }, r.prototype._draw = function() {
        this._d.hasVal && this.ctx.area.paths.svgPath(this._d.val)
      }, r
    }(a);
    e._SvgPathElementImpl = l;
    var h = function(t) {
      function n() {
        return null !== t && t.apply(this, arguments) || this
      }
      return __extends(n, t), n.prototype._draw = function() {
        var t = new e._SvgPointsArrayAttr(this, "points");
        if (t.hasVal) {
          var n = t.val,
            r = this.ctx.area;
          if (n.length > 1) {
            for (var i = 0; i < n.length; i++) 0 == i ? r.paths.moveTo(n[i].x, n[i].y) : r.paths.lineTo(n[i].x, n[i].y);
            return !0
          }
        }
        return !1
      }, n
    }(a);
    e._SvgPolylineElementImpl = h;
    var f = function(t) {
      function e() {
        return null !== t && t.apply(this, arguments) || this
      }
      return __extends(e, t), e.prototype._draw = function() {
        return !!t.prototype._draw.call(this) && (this.ctx.area.paths.closePath(), !0)
      }, e
    }(h);
    e._SvgPolygonElementImpl = f;
    var d = function(t) {
      function n() {
        return null !== t && t.apply(this, arguments) || this
      }
      return __extends(n, t), n.prototype._draw = function() {
        var t = new e._SvgNumAttr(this, "width", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width).val,
          n = new e._SvgNumAttr(this, "height", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height).val;
        if (t > 0 && n > 0) {
          var r = new e._SvgNumAttr(this, "x", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width).val,
            i = new e._SvgNumAttr(this, "y", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height).val,
            o = Math.max(new e._SvgNumAttr(this, "rx", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width).val, 0),
            a = Math.max(new e._SvgNumAttr(this, "ry", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height).val, 0),
            s = this.ctx.area.paths;
          o || a ? (o = Math.min(o || a, t / 2), a = Math.min(a || o, n / 2), s.moveTo(r + o, i), s.lineTo(r + t - o, i), s.quadraticCurveTo(r + t, i, r + t, i + a), s.lineTo(r + t, i + n - a), s.quadraticCurveTo(r + t, i + n, r + t - o, i + n), s.lineTo(r + o, i + n), s.quadraticCurveTo(r, i + n, r, i + n - a), s.lineTo(r, i + a), s.quadraticCurveTo(r, i, r + o, i)) : s.rect(r, i, t, n)
        }
      }, n
    }(a);
    e._SvgRectElementImpl = d;
    var p = function(t) {
      function e(e, r) {
        return t.call(this, e, r, n.Ignore) || this
      }
      return __extends(e, t), e
    }(r);
    e._SvgClipPathElementImpl = p;
    var g = function(t) {
      function e(e, r) {
        return t.call(this, e, r, n.Ignore) || this
      }
      return __extends(e, t), e
    }(i);
    e._SvgDefsElementImpl = g;
    var _ = function(t) {
      function e() {
        return null !== t && t.apply(this, arguments) || this
      }
      return __extends(e, t), e
    }(o);
    e._SvgGElementImpl = _;
    var m = function(r) {
      function i(t, i) {
        var o = r.call(this, t, i, n.Ignore) || this;
        return o._x1 = new e._SvgStrAttr(o, "x1", "0%"), o._x2 = new e._SvgStrAttr(o, "x2", "100%"), o._y1 = new e._SvgStrAttr(o, "y1", "0%"), o._y2 = new e._SvgStrAttr(o, "y2", "0%"), o._gradientUnits = new e._SvgStrAttr(o, "gradientUnits", "objectBoundingBox"), o
      }
      return __extends(i, r), i.prototype.toBrush = function(n) {
        for (var r = new t.Rect(new e._SvgNumAttr(n, "x", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width).val, new e._SvgNumAttr(n, "y", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height).val, new e._SvgNumAttr(n, "width", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width).val, new e._SvgNumAttr(n, "height", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height).val), i = "objectBoundingBox" === this._gradientUnits.val ? new t.Size(r.width, r.height) : n.viewport.clone(), o = e._SvgNumAttr.parseValue(this._x1.val, e._SvgAttrType.Number, i, e._SvgLengthContext.Width, e._SvgNumConversion.Default), a = e._SvgNumAttr.parseValue(this._x2.val, e._SvgAttrType.Number, i, e._SvgLengthContext.Width, e._SvgNumConversion.Default), s = e._SvgNumAttr.parseValue(this._y1.val, e._SvgAttrType.Number, i, e._SvgLengthContext.Height, e._SvgNumConversion.Default), u = e._SvgNumAttr.parseValue(this._y2.val, e._SvgAttrType.Number, i, e._SvgLengthContext.Height, e._SvgNumConversion.Default), c = [], l = 0; l < this.children.length; l++)
          if (this.children[l] instanceof v) {
            var h = this.children[l];
            c.push(new e.PdfGradientStop(e._SvgNumAttr.parseValue(h.offset.val, e._SvgAttrType.Number, i, e._SvgLengthContext.Other, e._SvgNumConversion.Default), h.color.val, h.opacity.val))
          }
        return new e.PdfLinearGradientBrush(r.left + o, r.top + s, r.left + a, r.top + u, c)
      }, i
    }(r);
    e._SvgLinearGradientElementImpl = m;
    var v = function(t) {
      function r(r, i) {
        var o = t.call(this, r, i, n.Ignore) || this;
        return o.color = new e._SvgColorAttr(o, "stop-color", "black"), o.opacity = new e._SvgNumAttr(o, "stop-opacity", 1, e._SvgNumConversion.None, void 0, !0), o.offset = new e._SvgStrAttr(o, "offset", "0"), o
      }
      return __extends(r, t), r
    }(r);
    e._SvgStopElementImpl = v;
    var y = function(n) {
      function r(t, r) {
        var i = n.call(this, t, r) || this;
        return i._x = new e._SvgNumAttr(i, "x", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width), i._y = new e._SvgNumAttr(i, "y", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height), i._width = new e._SvgNumAttr(i, "width", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width), i._height = new e._SvgNumAttr(i, "height", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height), i._href = new e._SvgHRefAttr(i, "xlink:href"), i._par = new e._SvgPreserveAspectRatioAttr(i), i
      }
      return __extends(r, n), r.prototype._renderContent = function() {
        var n = this._width.val,
          r = this._height.val;
        if (n > 0 && r > 0 && this._href.hasVal) {
          var i = e._resolveUrlIfRelative(this._href.val, this.ctx.urlResolver);
          if (i) {
            this.ctx.area.document.saveState(), (this._x.val || this._y.val) && this.ctx.area.translate(this._x.val, this._y.val), this.viewport = new t.Size(n, r);
            try {
              this._href.val.match(/\.svg$/i) ? this._renderSvgImage(i) : this._renderRasterImage(i)
            } catch (t) {}
            this.ctx.area.document.restoreState()
          }
        }
      }, r.prototype._renderSvgImage = function(n) {
        var r, i = t.pdf._XhrHelper.text(n, function(t) {
          return r = t.statusText
        });
        if (r) throw r;
        var o = new e._SvgRenderer(i, this.ctx.area),
          a = o.root;
        this.attr("viewBox", a.attr("viewBox")), a.clearAttr("viewBox"), a.clearAttr("x"), a.clearAttr("y"), a.clearAttr("width"), a.clearAttr("height"), a.clearAttr("preserveAspectRatio"), a.clearAttr("clip"), a.clearAttr("overflow"), this.ctx.area.paths.rect(0, 0, this.viewport.width, this.viewport.height).clip();
        var s = new e._SvgScaleAttributes(this);
        o.render(s.apply(this))
      }, r.prototype._renderRasterImage = function(n) {
        var r = this.ctx.area.openImage(e._PdfImageHelper.getDataUri(n)),
          i = this._par.val,
          o = this.viewport.width,
          a = this.viewport.height,
          s = {
            width: o,
            height: a,
            align: e.PdfImageHorizontalAlign.Left,
            vAlign: e.PdfImageVerticalAlign.Top
          };
        if ("none" === i.align) s.stretchProportionally = !1;
        else if (s.stretchProportionally = !0, i.meet) i.align.match(/^xMid/) ? s.align = e.PdfImageHorizontalAlign.Center : i.align.match(/^xMax/) && (s.align = e.PdfImageHorizontalAlign.Right), i.align.match(/YMid$/) ? s.vAlign = e.PdfImageVerticalAlign.Center : i.align.match(/YMax$/) && (s.vAlign = e.PdfImageVerticalAlign.Bottom);
        else {
          var u = t.pdf.pxToPt(r.width),
            c = t.pdf.pxToPt(r.height),
            l = o / u,
            h = a / c;
          l > h ? (s.width = u * l, s.height = c * l) : (s.width = u * h, s.height = c * h);
          var f = 0,
            d = 0;
          i.align.match(/^xMid/) ? f = -s.width / 2 + o / 2 : i.align.match(/^xMax/) && (f = -s.width + o), i.align.match(/YMid$/) ? d = -s.height / 2 + a / 2 : i.align.match(/YMax$/) && (d = -s.height + a), this.ctx.area.paths.rect(0, 0, this.viewport.width, this.viewport.height).clip(), (f || d) && this.ctx.area.translate(f, d)
        }
        this.ctx.area.drawImage(r, 0, 0, s)
      }, r
    }(o);
    e._SvgImageElementImpl = y;
    var b = function(t) {
      function r(r, i) {
        var o = t.call(this, r, i, n.Ignore) || this;
        if (i && (!i.type || "text/css" === i.type)) {
          for (var a = "", s = 0; s < i.childNodes.length; s++) a += i.childNodes[s].textContent;
          var u = (a = (a = e._compressSpaces(a)).replace(/\/\*([^*]|\*+[^*/])*\*+\//gm, "")).match(/[^{}]*{[^}]*}/g);
          if (u)
            for (s = 0; s < u.length; s++) {
              var c = u[s].match(/([^{}]*){([^}]*)}/);
              if (c) {
                var l = c[1].trim().split(","),
                  h = c[2].trim();
                l.length && h && l.forEach(function(t) {
                  (t = t.trim()) && o.ctx.registerCssRule(new e._SvgCssRule(t, h))
                })
              }
            }
        }
        return o
      }
      return __extends(r, t), r
    }(r);
    e._SvgStyleElementImpl = b;
    var w = function(n) {
      function r(t, r) {
        var i = n.call(this, t, r) || this;
        return i._x = new e._SvgNumAttr(i, "x", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width), i._y = new e._SvgNumAttr(i, "y", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height), i._width = new e._SvgNumAttr(i, "width", "100%", e._SvgNumConversion.Default, e._SvgLengthContext.Width), i._height = new e._SvgNumAttr(i, "height", "100%", e._SvgNumConversion.Default, e._SvgLengthContext.Height), i._scale = new e._SvgScaleAttributes(i), i._overflow = new e._SvgStrAttr(i, "overflow", "hidden"), i
      }
      return __extends(r, n), Object.defineProperty(r.prototype, "width", {
        get: function() {
          return this._width
        },
        enumerable: !0,
        configurable: !0
      }), Object.defineProperty(r.prototype, "height", {
        get: function() {
          return this._height
        },
        enumerable: !0,
        configurable: !0
      }), r.prototype._render = function() {
        var e = this.ctx.area;
        e.document.saveState();
        var r = this._width.val,
          i = this._height.val,
          o = this._x.val,
          a = this._y.val;
        this.parent && (o || a) && e.translate(o, a), this.viewport = new t.Size(r, i), "visible" !== this._overflow.val && e.paths.rect(0, 0, r, i).clip(), this.viewport = this._scale.apply(this), this.viewport.width > 0 && this.viewport.height > 0 && n.prototype._render.call(this), e.document.restoreState()
      }, r
    }(i);
    e._SvgSvgElementImpl = w;
    var x = function(t) {
      function e(e, r) {
        return t.call(this, e, r, n.Ignore) || this
      }
      return __extends(e, t), e
    }(i);
    e._SvgSymbolElementImpl = x;
    var S = function(n) {
      function r(t, r) {
        var i = n.call(this, t, r) || this;
        return i._xlink = new e._SvgIdRefAttr(i, "xlink:href"), i
      }
      return __extends(r, n), r.prototype._render = function() {
        var e, n;
        if (this._xlink.hasVal && (e = this.ctx.getElement(this._xlink.val))) {
          var r = new _(this.ctx, null);
          if (r.parent = this.parent, r.copyAttributesFrom(this, ["x", "y", "width", "height", "xlink:href"]), null != this.attr("x") || null != this.attr("y")) {
            var i = t.format("translate({x},{y})", {
              x: this.attr("x") || 0,
              y: this.attr("y") || 0
            });
            r.attr("transform", (n = r.attr("transform")) ? n + " " + i : i)
          }
          if (e instanceof x) {
            var o = new w(this.ctx, null);
            o.copyAttributesFrom(e);
            for (var a = 0; a < e.children.length; a++) o.appendNode(e.children[a].clone());
            r.appendNode(o), o.attr("width", this.attr("width") || "100%"), o.attr("height", this.attr("height") || "100%")
          } else e = e.clone(), r.appendNode(e), e instanceof w && (null != (n = this.attr("width")) && e.attr("width", n), null != (n = this.attr("height")) && e.attr("height", n));
          r.render(this.viewport, this.renderMode)
        }
      }, r
    }(r);
    e._SvgUseElementImpl = S;
    var C = function(t) {
      function r(n, r) {
        var i = t.call(this, n, r) || this;
        return i._x = new e._SvgNumAttr(i, "x", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width), i._y = new e._SvgNumAttr(i, "y", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height), i._dx = new e._SvgNumAttr(i, "dx", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width), i._dy = new e._SvgNumAttr(i, "dy", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height), i._textDecoration = new e._SvgTextDecorationAttr(i), i
      }
      return __extends(r, t), r.prototype._render = function() {
        this.renderMode === n.Render && t.prototype._render.call(this)
      }, r.prototype._renderContent = function() {
        var t = this;
        this._prepareNodes();
        for (var e = this._x.val + this._dx.val, n = this._y.val + this._dy.val, r = function(i, o) {
          if (i._x.hasVal && (e = i._x.val), i._y.hasVal && (n = i._y.val), e += i._dx.val, n += i._dy.val, i._text) i._cx = e, i._cy = n, i._setDecorators(o), i.render(t.viewport, t.renderMode), e += t.ctx.area.measureText(i._text, i.style.font.toFont(), {
            width: 1 / 0,
            height: 1 / 0,
            includeLastLineExternalLeading: !1
          }).size.width;
          else
            for (var a = 0; a < i.children.length; a++) {
              var s = o.slice();
              s.push({
                decoration: i._textDecoration,
                style: i.style
              }), r(i.children[a], s)
            }
        }, i = 0; i < this.children.length; i++) r(this.children[i], [{
          decoration: this._textDecoration,
          style: this.style
        }])
      }, r.prototype._prepareNodes = function() {
        var t = function(e) {
            for (var n = 0; n < e.children.length; n++) {
              var r = e.children[n];
              !r._text && t(r) && r.remove()
            }
            return 0 === e.children.length
          },
          e = [],
          n = function(t) {
            for (var r = 0; r < t.children.length; r++) {
              var i = t.children[r];
              i._text ? e.push(i) : n(i)
            }
          };
        t(this), n(this);
        for (var r = 0; r < e.length; r++) {
          var i = e.length;
          " " === e[r]._text && (0 === r || r === i - 1 || r < i - 1 && " " === e[r + 1]._text) && (e[r].remove(), e.splice(r, 1), r--)
        }
      }, r
    }(o);
    e._SvgTextElementImpl = C;
    var k = function(n) {
      function r(r, i, o) {
        var a = n.call(this, r, i) || this;
        return a._textDecoration = new e._SvgTextDecorationAttr(a), a._text = t.asString(o), a._x = new e._SvgNumAttr(a, "x", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width), a._y = new e._SvgNumAttr(a, "y", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height), a._dx = new e._SvgNumAttr(a, "dx", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Width), a._dy = new e._SvgNumAttr(a, "dy", 0, e._SvgNumConversion.Default, e._SvgLengthContext.Height), a._textDecoration = new e._SvgTextDecorationAttr(a), a
      }
      return __extends(r, n), r.prototype.clone = function() {
        var t = n.prototype.clone.call(this);
        return t._text = this._text, t
      }, r.prototype._setDecorators = function(t) {
        this._decorators = t
      }, r.prototype._renderContent = function() {
        if (this._text) {
          var t = {
            font: this.style.font.toFont(),
            width: 1 / 0,
            height: 1 / 0,
            lineBreak: !1,
            fill: "none" !== this.style.fill.color.val,
            stroke: "none" !== this.style.stroke.color.val,
            _baseline: e._PdfTextBaseline.Alphabetic
          };
          this._decorate(), (t.fill || t.stroke) && (t.fill && (t.brush = this.style.fill.toBrush(this)), t.stroke && (t.pen = this.style.stroke.toPen(this)), this.ctx.area.drawText(this._text, this._cx, this._cy, t))
        }
      }, r.prototype._decorate = function() {
        var t = this.ctx.area,
          e = !1;
        this._decorators.push({
          decoration: this._textDecoration,
          style: this.style
        });
        for (var n = 0; n < this._decorators.length && !e; n++) e = null != this._decorators[n].decoration.val;
        if (e) {
          t.document.saveState();
          for (var r, i = t.document._document, o = t.measureText(this._text, this.style.font.toFont(), {
            width: 1 / 0,
            height: 1 / 0,
            includeLastLineExternalLeading: !1
          }).size, a = Math.max(i.currentFontSize() / 20, .1), s = i.currentFontAscender(), u = this._cx; r = this._decorators.shift();) {
            var c = r.decoration.val;
            if (c) {
              for (var l = 0; l < c.length; l++) {
                var h = this._cy - s;
                switch (c[l]) {
                  case "line-through":
                    h = h + o.height / 2 - a / 2;
                    break;
                  case "overline":
                    h -= i.currentFontBBox().ury - i.currentFontAscender();
                    break;
                  case "underline":
                    h = h + o.height - 1.5 * a
                }
                t.paths.rect(u, h, o.width, a)
              }
              r.style.apply(this, !0, !0)
            }
          }
          t.document.restoreState()
        }
      }, r
    }(i);
    e._SvgTspanElementImpl = k
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
var wijmo;
! function(t) {
  ! function(e) {
    "use strict;";
    var n = function() {
      function n(n, r, i) {
        var o = this;
        this._elementsById = {}, this._registeredCssRules = {}, t.assert(null != r, e._Errors.ValueCannotBeEmpty("svgString")), this._doc = r.document;
        var a = this._parse(n);
        a && (this._svg = new e._SvgSvgElementImpl({
          area: r,
          urlResolver: i,
          getElement: this._getElementById.bind(this),
          registerCssRule: function(t) {
            o._registerCssRule(t, i)
          }
        }, null), this._copyAttributes(a, this._svg), this._buildTree(a, this._svg), this._svg.viewport = new t.Size(r.width, r.height))
      }
      return Object.defineProperty(n.prototype, "root", {
        get: function() {
          return this._svg
        },
        enumerable: !0,
        configurable: !0
      }), n.prototype.render = function(t) {
        this._svg && this._svg.render(t || this._svg.viewport)
      }, n.prototype._parse = function(t) {
        if (t) {
          var e = new DOMParser;
          return e.async = !1, e.parseFromString(t, "text/xml").getElementsByTagName("svg")[0]
        }
      }, n.prototype._buildTree = function(n, r, i) {
        for (var o = 0; n.childNodes && o < n.childNodes.length; o++) {
          var a = n.childNodes.item(o),
            s = a.nodeName;
          if (1 === a.nodeType) {
            var u = this._getClassName(s);
            if (t.pdf[u]) {
              var c = new t.pdf[u](r.ctx, a);
              this._copyAttributes(a, c), r.appendNode(c);
              var l;
              (l = a.getAttribute("id")) && (this._elementsById[l] = c), this._buildTree(a, c, "text" === s || i && "tspan" === s)
            }
          } else if (3 === a.nodeType && i) {
            var h = a.textContent.trim();
            if (h) {
              0 != o && 1 === n.childNodes[o - 1].nodeType && a.textContent.match(/^\s/) && r.appendNode(new e._SvgTspanElementImpl(r.ctx, null, " "));
              var f = e._compressSpaces(a.textContent);
              r.appendNode(new e._SvgTspanElementImpl(r.ctx, null, f))
            }
            h && !a.textContent.match(/\s$/) || r.appendNode(new e._SvgTspanElementImpl(r.ctx, null, " "))
          }
        }
      }, n.prototype._getClassName = function(t) {
        return "_Svg" + t.charAt(0).toUpperCase() + t.substring(1) + "ElementImpl"
      }, n.prototype._copyAttributes = function(t, n) {
        for (o = 0; o < t.attributes.length; o++) {
          var r = t.attributes.item(o);
          n.attr(r.name, r.value)
        }
        for (var i = e._SvgCssHelper.getComputedStyle(t, this._registeredCssRules), o = 0, a = Object.keys(i); o < a.length; o++) {
          var s = a[o];
          n.attr(s, i[s])
        }
      }, n.prototype._getElementById = function(t) {
        return t = (t || "").replace("#", ""), this._elementsById[t]
      }, n.prototype._registerCssRule = function(t, n) {
        "@" !== t.selector[0] ? this._registeredCssRules[t.selector] = t : "@font-face" === t.selector && e._SvgCssHelper.registerFontFace(this._doc, t, n)
      }, n
    }();
    e._SvgRenderer = n
  }(t.pdf || (t.pdf = {}))
}(wijmo || (wijmo = {}));
