
function getWeather(){

    var result = document.getElementById("search").value;

    var parts = result.split(",");

    alert(parts + " length: " + parts.length);

    if(parts.length != 3)
        alert("Sisend vales formaadis.")
    else
        window.location.href = "http://weatherly.me/data/weather?country=" + parts[0] + "&regionName=" + parts[1] + "&city=" + parts[2];

}




