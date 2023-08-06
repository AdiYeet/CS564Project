function validateForm() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    if (username == "" || password == "") {
        alert("Please enter a username and password.");
        return false;
    }

    return true;
}

document.getElementById("form").addEventListener("submit", validateForm);

function createAccount() {
    window.location.href = "userLogin.html";
}

document.getElementById("createAccount").addEventListener("click", createAccount);
