


var browserCanvas = document.getElementById("browserCanvas");
var osCanvas = document.getElementById("osCanvas");
var browserLegend = document.getElementById("browserLegend");
var osLegend = document.getElementById("osLegend");


browserCanvas.width = 400;
browserCanvas.height = 400;

osCanvas.width = 400;
osCanvas.height = 400;



var browsers = {
    "Chrome": 10,
    "Firefox": 14,
    "Safari": 2,
    "Unknown": 12
};

var operationSystems = {
    "Windows": 1,
    "Mac": 1,
    "Unix": 2,
    "Android": 3,
    "IPhone": 1,
    "Unknown": 4
};

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



var browserPieChart = new Piechart(
    {
        canvas:browserCanvas,
        data:browsers,
        colors:["#fde23e","#bf80ff", "#1aff1a","#ffcc00", "#660066", "#e60000"],
        legend:browserLegend
    }
);

var osPieChart = new Piechart(
    {
        canvas:osCanvas,
        data:operationSystems,
        colors:["#33adff","#f16e23", "#57d9ff","#937e88", ],
        legend:osLegend
    }
);



browserPieChart.draw();
osPieChart.draw();