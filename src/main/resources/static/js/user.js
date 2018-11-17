function submitLocation() {
    window.alert("executed");
    var data = document.getElementById("search");
    $.post("/user/submitLocation", data, function (data, status) {
        window.alert("Data: " + data + "\nStatus: " + status);
    });
    return true;
}

function logout() {
    window.alert("You are about to be logged out");
    $.post("/logout");
    return true;
}