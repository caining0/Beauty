(function($, doc) {
	$.plusReady(function () {
		var _BARCODE = 'pgpassport',
		B = window.plus.bridge;
	    var pgpassport = {
	    		requireLogin: function (successCallback, errorCallback) {
	    			var success = typeof successCallback !== 'function' ? null : function(args) 
				{
					successCallback(args);
				},
				fail = typeof errorCallback !== 'function' ? null : function(code) 
				{
					errorCallback(code);
				};
				callbackID = B.callbackId(success, fail);
				
				return B.exec(_BARCODE, "requireLogin", [callbackID]);
	    		}
	    		/**
	    		 * 获取一些固定信息：MAPI_KEY，debug等
	    		 */
	    		, getInfo: function () {
	    			return B.execSync(_BARCODE, "getInfo", []);
	    		}
	    		/**
				* 设置当前屏幕方向
	    		*/
	    		,changeStatusBarOrientation:function(orientation){
	    			return B.execSync(_BARCODE,"changeStatusBarOrientation",[orientation]);
	    		}
	    		/**
				* 返回md5结果
	    		*/
	    		,md5:function(str){
	    			return B.execSync(_BARCODE,"md5",[str]);
	    		}
	    		/**
	    		 * 获取用户信息：未登录返回空；已登录，返回字典
	    		 */
	    		, getUserInfo: function () {
	    			var userInfo = B.execSync(_BARCODE, "getUserInfo", []);
	    			if (typeof(userInfo) != "object") {
	    				return false;
	    			}
	    			if (typeof(userInfo.id) == "undefined") {
	    				return false;
	    			}
	    			if (userInfo.id < 1) {
	    				return false;
	    			}
	    			return userInfo;
	    		}
	    		/**
	    		 * data参数:
	    		 * Type：json对象
	    		 * 格式：{
	    		 * 	tt: "标题"
	    		 *  , iu: "图片"
	    		 *  , shu: "分享地址"
	    		 *  , desc: "分享文案"
	    		 * }
	    		 */
	    		, requireShare: function (data, successCallback, errorCallback) {
	    			var success = typeof successCallback !== 'function' ? null : function(args) 
				{
					successCallback(args);
				},
				fail = typeof errorCallback !== 'function' ? null : function(code) 
				{
					errorCallback(code);
				};
				callbackID = B.callbackId(success, fail);
				
				return B.exec(_BARCODE, "requireShare", [callbackID, data]);
	    		}
	    };
	    window.plus.pgpassport = pgpassport;
	});
}(mui, document));