$('#nav li:nth-of-type(5)').addClass('clicked');

const chart1 = document.querySelector('.doughnut1')

const makeChart = (percent, classname, color) => {
    let i = 1;
    let chartFn = setInterval(function () {
        if (i < percent) {
            colorFn(i, classname, color);
            i++;
        } else {
            clearInterval(chartFn);
        }
    }, 8);
}

const colorFn = (i, classname, color) => {
    classname.style.background = "conic-gradient(" + color + " 0% " + i + "%, #fff " + i + "% 100%)";
}

makeChart(105, chart1, "var(--green)");

$('.chartOK').hide()
$('.chartOK').delay(800).fadeIn()


$('#addPoint').delay(500).animate({ marginTop: 30 }, 1000).animate({ opacity: 0 })