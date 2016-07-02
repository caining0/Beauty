$(function(){
	$(".textbox").focus(function(){
//		$("#share").addClass("share_fs");
//	  	$("#share").text("发送");
	  	$("#share").hide();
	  	$("#reviewsender").show();
	});
	$("#parent").on("touchend",function(){
		var tmpval = $(".textbox").val();
		if (tmpval) {
			$(".textbox").val("");
		}
		$(".textbox").blur();
		if (tmpval) {
			$(".textbox").val(tmpval);
		}
	});
				   
	$(".textbox").blur(function(){
		var textvalue=$(this).val();
		if(textvalue==""){
			$("#share").show();
	  		setTimeout(function(){$("#reviewsender").hide();},500);
//			$("#share").removeClass("share_fs");
//	  		$("#share").html("<img src='images/fx_button.jpg'>");
		}
		else{
			return false;
		}
	});
		
		$("#reviewsender").click(function(){
			var conthei=$(".content_top").height();
			$(".box_samil").animate({scrollTop:conthei+"px"},500);
		});
	
});
//$(document).scroll(function(){
//	var sTop = $(window).scrollTop();
//	if(sTop>=43){
//		plus.webview.getWebviewById("fx.html").show("slide-in-bottom",300);
//		}
//	else{
//		plus.webview.getWebviewById("fx.html").hide("slide-out-bottom",300);
//		}
//});
