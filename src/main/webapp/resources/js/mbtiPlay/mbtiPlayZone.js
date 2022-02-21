$('#nav li:nth-of-type(5)').addClass('clicked');

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