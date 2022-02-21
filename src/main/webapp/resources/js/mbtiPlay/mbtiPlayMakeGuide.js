$('#nav li:nth-of-type(5)').addClass('clicked');

$('#guideTxt p').hide()
$('#guideBtn').hide()

$('#guideTxt p:eq(0)').delay(1000).fadeIn(1000)
$('#guideTxt p:eq(1)').delay(2000).fadeIn(1000)
$('#guideTxt p:eq(2)').delay(3200).fadeIn(1000)
$('#guideTxt p:eq(3)').delay(3200).fadeIn(1000)
$('#guideTxt p:eq(4)').delay(3200).fadeIn(1000)
$('#guideBtn').delay(3200).fadeIn(1000)

function decoMove1() {
    $('#deco1').animate({ bottom: "30px" }, 2000).animate({ bottom: "0px" }, 2000)
}
function decoMove2() {
    $('#deco2').animate({ top: "80px" }, 2500).animate({ top: "50px" }, 2500)
}
function decoMove3() {
    $('#deco3').animate({ bottom: "180px" }, 3000).animate({ bottom: "200px" }, 3500)
}

setInterval(decoMove1, 2200);
setInterval(decoMove2, 2500);
setInterval(decoMove3, 1000);