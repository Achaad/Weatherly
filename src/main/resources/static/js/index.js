

function getWeather(){


    var searchBar = document.getElementById("search");
    var locale = $("html").attr("lang");
    var parts = searchBar.value.split(",");

    var message;

    if (locale === "et") {
        message = "Vales formaadis sisend!";
    } else{
        message = "Wrong format!"
    }

    if(parts.length != 3) {
        searchBar.placeholder = message;
    }
    else {
        window.location.href = "https://weatherly.me/data/weather?country=" + parts[0] + "&regionName=" + parts[1] + "&city=" + parts[2];
    }

}




