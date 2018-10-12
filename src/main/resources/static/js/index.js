
function getWeather(){


    var searchBar = document.getElementById("search");

    var parts = searchBar.value.split(",");


    if(parts.length != 3)
        searchBar.placeholder = "Vales formaadis sisend!"
    else
        window.location.href = "http://localhost:8080/data/weather?country=" + parts[0] + "&regionName=" + parts[1] + "&city=" + parts[2];

}




