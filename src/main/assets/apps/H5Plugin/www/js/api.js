(function($, owner) {
	var api_prefix = "http://mapi.onlylady.com/beauty/";
//	api_prefix = "http://10.19.136.104/beauty/";
	
	/**
	 * 对请求参数进行签名
	 * @param {Object} params
	 * @return {String} sign 
	 */
	 function dosign(params) {
	 	var info = plus.pgpassport.getInfo();
		var MAPI_KEY = info.code;
		params = params || {};
		var param_values = [];
		$.each(params, function(idx, item) {
			param_values.push(item);
		});
		param_values.sort();
		var tmpsign = param_values.join('') + MAPI_KEY;
		var sign = plus.pgpassport.md5(tmpsign);
		return sign;
	};
	
	/**
	 * 对post请求的封装
	 * @param {String} version 版本路径
	 * @param {Object} request_params 请求参数
	 * @param {Function} successcb 成功回调
	 * @param {Funciton} errorcb 错误回调。如不设置时，有默认的错误处理逻辑
	 */
	function post(version, request_params, successcb, errorcb) {
		var options = {type: "post"};
		return request(version, request_params, options, successcb, errorcb);
	}
	
	/**
	 * 对get请求的封装
	 * @param {String} version 版本路径
	 * @param {Object} request_params 请求参数
	 * @param {Function} successcb 成功回调
	 * @param {Funciton} errorcb 错误回调。如不设置时，有默认的错误处理逻辑
	 */
	function get(version, request_params, successcb, errorcb) {
		var options = {type: "get"};
		return request(version, request_params, options, successcb, errorcb);
	}
	
	
	/**
	 * 对请求的封装
	 * @param {String} version 版本路径
	 * @param {Object} request_params 请求参数
	 * @params {Object} options 选项
	 * @param {Function} successcb 成功回调
	 * @param {Funciton} errorcb 错误回调。如不设置时，有默认的错误处理逻辑
	 */
	function request(version, request_params, options, successcb, errorcb) {
		var api_url = api_prefix + version;
		var options = options || {};
		options.headers = options.headers || {};
		
		request_params = request_params || {};
		var sign = dosign(request_params);
		request_params.olsign = sign;
		
//		api_prefix = "http://bms.17kb.com"
//		api_url = api_prefix + "/gxg.php";

		var tmpinfo = plus.pgpassport.getInfo();
		if (tmpinfo.debug == "true") {// debug模式
			// 加TESTENV
			options.headers = {"TESTENV": "1"};
		}
		options.headers.OLENV = tmpinfo.ENV;
		
		errorcb = errorcb || $.noop;
		successcb = successcb || $.noop;
		// 包装错误处理
		function wrapErrorCallback(errcode, data) {
			console.log("错误码: " + errcode);
//			App.print(data);
			var errorresult = errorcb(errcode, data);
			if (errorresult === false) {// 调用者的错误处理明确返回false，表示不再继续api层的错误处理逻辑
				return;
			}
		}
		
		// 包装成功处理
		function wrapSuccessCallback(data) {
			console.log("");
			console.log("");
			console.log("请求url: "+api_url);
			console.log("请求参数："+JSON.stringify(request_params));
			console.log("请求options: "+JSON.stringify(options));
			console.log("返回结果："+JSON.stringify(data));
			
			if (data.errcode !== 0) {// 发生错误
				wrapErrorCallback(owner.AJAX_ERROR_CODE.API_NOT_OK, data);
				return;
			}
			successcb(data);
		}
		console.log("打印请求参数：");
		App.print(request_params);
		if (options.type == "post") {
			owner.post(api_url, request_params, wrapSuccessCallback, wrapErrorCallback, options);
		} else {
			owner.get(api_url, request_params, wrapSuccessCallback, wrapErrorCallback, options);
		}
	}
	
	/**
	 * 获取文章和视频详情
	 * @param {Object} aid 文章id
	 * @param {Object} successcb
	 * @param {Object} errorcb
	 */
	function getArticle(aid, successcb, errorcb) {
		var version = "v100";
		get(version, {
			rd: 1017
			, aid: aid
		}, successcb, errorcb);
	}
	
	/**
	 * 
	 * 获取直播详情
	 * @param {Object} id 直播id
	 * @param {Object} successcb
	 * @param {Object} errorcb
	 */
	function getLive(id, successcb, errorcb) {
		var version = "v100";
		get(version, {
			rd: 1019
			, lid: id
		}, successcb, errorcb);
	}
	
	function postReview(data, successcb, errorcb) {
		var version = "v100";
		var ts = Date.parse(new Date())+"";
		ts = ts.substr(0, ts.length-3);
		
		post(version, {
			rd: 1020
			, aid: data.aid
			, ud: data.ud
			, un: data.token
			, cnt: data.cnt
			, ty: data.ty
			, olts: ts
		}, successcb, errorcb);
	}
	
	/**
	 * 获取评论
	 * @param {Object} type  1：视频和图文 2：直播 
	 * @param {Object} id 视频id或者直播id 
	 * @param {Object} page 分页数
	 * @param {Object} successcb
	 * @param {Object} errorcb
	 */
	function getReviews(type, id, page, successcb, errorcb) {
		var version = "v100";
		post(version, {
			rd: 1021
			, aid: id
			, ty: type
			, ie: page
		}, successcb, errorcb);
	}
	
	/**
	 * 举报评论
	 * @param {Object} data
	 * @param {Object} successcb
	 * @param {Object} errorcb
	 */
	function reportReview(data, successcb, errorcb) {
		var version = "v100";
		post(version, {
			rd: 1023
			, rid: data.id
			, ty: data.type
		}, successcb, errorcb);
	}
	
	/**
	 * 删除评论
	 * @param {Object} data
	 * @param {Object} successcb
	 * @param {Object} errorcb
	 */
	function deleteReview(data, successcb, errorcb) {
		var version = "v100";
		post(version, {
			rd: 1022
			, rid: data.id
			, ty: data.type
			, ud: data.ud
			, un: data.token
		}, successcb, errorcb);
	}
	
	
	
	// 对外公开的接口
	owner.Api = {
		getArticle: getArticle
		, getLive: getLive
		, postReview: postReview
		, getReviews: getReviews
		, reportReview: reportReview
		, deleteReview: deleteReview
	};
}(mui, window.App));