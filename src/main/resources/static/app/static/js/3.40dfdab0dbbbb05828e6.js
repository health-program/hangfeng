webpackJsonp([3],{KXv6:function(e,t){},L4kW:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=r("Au9i"),n={data:function(){return{reward:""}},components:{contentedit:r("lHs2").a},methods:{submitBtn:function(){var e=localStorage.getItem("userId"),t=new FormData;t.append("userId",e),t.append("reward",this.reward);var r=this;this.$http({method:"post",url:"/org/user/self/app/update/resume",data:t}).then(function(e){Object(a.Toast)("保存成功"),r.$router.push("/accounts/index")})}},created:function(){var e=this,t=localStorage.getItem("userId");this.$http.get("/org/user/self/get?id="+t).then(function(t){e.reward=t.reward})}},s={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",[r("ptheader",{staticClass:"rpublicity_header"},[e._v("历年奖励情况")]),e._v(" "),r("contentedit",{staticClass:"areaDiv",model:{value:e.reward,callback:function(t){e.reward=t},expression:"reward"}}),e._v(" "),r("footer",{on:{click:e.submitBtn}},[e._v("\n      提交\n    ")])],1)},staticRenderFns:[]};var o=r("VU/8")(n,s,!1,function(e){r("KXv6")},"data-v-56e0b41c",null);t.default=o.exports}});
//# sourceMappingURL=3.40dfdab0dbbbb05828e6.js.map