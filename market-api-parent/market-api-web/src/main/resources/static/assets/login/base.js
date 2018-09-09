var baseUrl = 'market/';

// $(function () {
//     localStorage.setItem("name", name);
// });

!function(e, t, n) {
    "use strict";
    Object.defineProperty(t, "__esModule", {
        value: !0
    }),
        t.load = t.localstorage = t.GetQueryString = void 0;
    var r = n(29),
        a = function(e) {
            return e && e.__esModule ? e: {
                    default:
                    e
                }
        } (r);
    t.GetQueryString = function(e) {
        var t = new RegExp("(^|&)" + e + "=([^&]*)(&|$)"),
            n = window.location.search.substr(1).match(t);
        return null != n ? unescape(n[2]) : null
    },
        t.localstorage = {
            set: function(e, t) {
                localStorage.setItem(e, t)
            },
            get: function(e) {
                return localStorage.getItem(e) ? localStorage.getItem(e) : ""
            },
            clear: function() {
                localStorage.clear()
            }
        },
        t.load = function(e, t, n) {
            var r = (0, a.
                default)(e);
            a.
            default.ajax({
                url:
                t,
                type: "get",
                dataType: "text",
                async: !1,
                cache: !1,
                success: function(e) {
                    r.html(e),
                    n && n.apply(r)
                }
            })
        }
}