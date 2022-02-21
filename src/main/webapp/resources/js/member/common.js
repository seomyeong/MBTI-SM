
var list01 = document.querySelectorAll('#nav li');

var list = document.querySelectorAll('#nav li');

function activeLink() {
    list01.forEach((item) =>
        item.classList.remove('clicked'));
    this.classList.add('clicked');
}
list01.forEach((item) =>
    item.addEventListener('click', activeLink));





