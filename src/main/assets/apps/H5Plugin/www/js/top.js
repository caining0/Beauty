$(".box_samil").scroll(function(){
	var heiimg=$(".exo_banner").find("img").height()+200;
	var sTop = $(this).scrollTop();
	if(sTop>=heiimg){
		$(".goto_top").addClass("ain");
		}
	else{
		$(".goto_top").removeClass("ain");
		}
});
$(function(){
	$(".goto_top").click(function(){
		var heiimg=$(".exo_banner").find("img").height();
		$(".box_samil").animate({scrollTop:heiimg},100);
	});
});
