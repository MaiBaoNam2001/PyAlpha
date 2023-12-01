var endpoint = "http://localhost:8080";

//toggle header__navbar--list when click on menu-icon
var menuButton = document.querySelector('.menu-icon');
var header__navbar__list = document.querySelector('.header__navbar--list');
var header = document.querySelector('.header');
menuButton.addEventListener('click', function () {
  header__navbar__list.classList.toggle('header__navbar--list--active');
  header.classList.toggle('dim');
  menuButton.classList.toggle('menu-icon__move');
});

//when min-width: 640px, if menu icon opened, close it
window.addEventListener('resize', function () {
  if (window.innerWidth >= 640) {
    header__navbar__list.classList.remove('header__navbar--list--active');
    header.classList.remove('dim');
    menuButton.classList.remove('opened');
    menuButton.classList.remove('menu-icon__move');
  }
});

//changePrimColor func
function changePrimColor(color) {
  document.documentElement.style.setProperty('--prim', color);
  setCookie('PRIMARY_COLOR', color, 365);
}

const PRIMARY_COLOR = getCookie('PRIMARY_COLOR');
changePrimColor(PRIMARY_COLOR !== '' ? PRIMARY_COLOR : '#ee4d2d');

function setCookie(key, value, expiryDays) {
  var now = new Date();
  var expiryDate = new Date(now.getTime() + (expiryDays * 24 * 60 * 60 * 1000))
  var expires = `expires=${expiryDate.toUTCString()}`;
  document.cookie = `${key}=${value};${expires};path=/`;
}

function getCookie(key) {
  var cookies = document.cookie.split(';');
  for (let i = 0; i < cookies.length; i++) {
    var keyValue = cookies[i].split('=');
    if (keyValue[0].trim() === key) {
      return keyValue[1];
    }
  }
  return '';
}

function deleteCookie(key) {
  document.cookie = `${key}=;expires=Thu, 01 Jan 1970 00:00:00 UTC;path=/`;
}

//auto expand text area
var textarea = document.querySelector('textarea');
textarea.addEventListener('keydown', autosize);

function autosize() {
  var el = this;
  setTimeout(function () {
    el.style.cssText = 'height:auto; padding:0';
    el.style.cssText = 'height:' + el.scrollHeight + 'px';
  }, 0);
}