
//show password when click on eye icon
var eyeIcon = document.getElementsByClassName('eye-icon');
var passwordInput = document.getElementsByClassName('password-input');
eyeIcon[0].addEventListener('click', function () {
  if (passwordInput[0].type == "password") {
    passwordInput[0].type = "text";
    eyeIcon[0].classList.add('eye-icon--active');
    eyeIcon[0].classList.add('fa-eye-slash');
    eyeIcon[0].classList.remove('fa-eye');
  } else {
    passwordInput[0].type = "password";
    eyeIcon[0].classList.remove('eye-icon--active');
    eyeIcon[0].classList.remove('fa-eye-slash');
    eyeIcon[0].classList.add('fa-eye');
  }
});

//if login form problem alert is not empty, empty it when click on text field
var loginForm = document.getElementById('login_form');
var loginFormInput = loginForm.getElementsByTagName('input');
var loginFormProblem = loginForm.getElementsByClassName(
    'form-problem-alert')[0];
loginFormInput[0].addEventListener('click', function () {
  if (loginFormProblem.innerHTML != "") {
    loginFormProblem.innerHTML = "";
  }
});
loginFormInput[1].addEventListener('click', function () {
  if (loginFormProblem.innerHTML != "") {
    loginFormProblem.innerHTML = "";
  }
});