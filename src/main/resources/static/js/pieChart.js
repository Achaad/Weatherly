


var browserCanvas = document.getElementById("browserCanvas");
var osCanvas = document.getElementById("osCanvas");
var browserLegend = document.getElementById("browserLegend");
var osLegend = document.getElementById("osLegend");


browserCanvas.width = 400;
browserCanvas.height = 400;

osCanvas.width = 400;
osCanvas.height = 400;

function drawPieSlice(ctx,centerX, centerY, radius, startAngle, endAngle, color ){
    ctx.fillStyle = color;
    ctx.beginPath();
    ctx.moveTo(centerX,centerY);
    ctx.arc(centerX, centerY, radius, startAngle, endAngle);
    ctx.closePath();
    ctx.fill();
}

var Piechart = function(options){
    this.options = options;
    this.canvas = options.canvas;
    this.ctx = this.canvas.getContext("2d");
    this.colors = options.colors;

    this.draw = function(){
        var total_value = 0;
        var color_index = 0;
        for (var categ in this.options.data){
            var val = this.options.data[categ];
            total_value += val;
        }

        var start_angle = 0;
        for (categ in this.options.data){
            val = this.options.data[categ];
            var slice_angle = 2 * Math.PI * val / total_value;

            drawPieSlice(
                this.ctx,
                this.canvas.width/2,
                this.canvas.height/2,
                Math.min(this.canvas.width/2,this.canvas.height/2),
                start_angle,
                start_angle+slice_angle,
                this.colors[color_index%this.colors.length]
            );

            start_angle += slice_angle;
            color_index++;
        }

        if (this.options.legend){
            color_index = 0;
            var legendHTML = "";
            for (categ in this.options.data){
                legendHTML += "<div><span style='display:inline-block;width:20px;background-color:"+this.colors[color_index++]+";'>&nbsp;</span> "+categ+"</div>";
            }
            this.options.legend.innerHTML = legendHTML;
        }

    }
}

function makeBrowserPie() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if (xmlhttp.status === 200) {
                var myArr = JSON.parse(this.responseText);

                var browserPieChart = new Piechart(
                    {
                        canvas:browserCanvas,
                        data:myArr,
                        colors:["#fde23e","#f16e23", "#57d9ff","#937e88"],
                        legend:browserLegend
                    }
                );
                browserPieChart.draw();
            }
        }
    };

    xmlhttp.open("GET", "/data/browser", true);
    xmlhttp.send();
}

function makeOsPie() {
    var xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === XMLHttpRequest.DONE) {
            if (xmlhttp.status === 200) {
                var myArr = JSON.parse(this.responseText);

                var osPieChart = new Piechart(
                    {
                        canvas:osCanvas,
                        data:myArr,
                        colors:["#fde23e","#f16e23", "#57d9ff","#937e88", "#937e00", "#000000"],
                        legend:osLegend
                    }
                )
                osPieChart.draw();
            }
        }
    };

    xmlhttp.open("GET", "/data/os", true);
    xmlhttp.send();
}


makeBrowserPie();
makeOsPie();