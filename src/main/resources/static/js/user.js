function displayLogoutMessage() {
    window.alert("You have successfully logged out.");
}

function displayMessage(message) {
 alert(message);
}

function rowClicked(userLocation) {
    var parts = userLocation.split(",");
    location.href = "https://weatherly.me/data/weather?country=" + parts[0] + "&regionName=" + parts[1] + "&city=" + parts[2];
}