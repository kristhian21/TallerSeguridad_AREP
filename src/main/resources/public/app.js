var app = (function(){

    function sendCos(){
        var numberInput = document.getElementById("inputNumber").value;
        console.log(numberInput);
        $.ajax({
            url: "https://localhost:35000/cos",
            type:'GET',
            data: {
                value: numberInput
            }
        }).then(function(data) {
            console.log(data);
            var respuesta = document.getElementById("textoRespuesta");
            respuesta.innerHTML = JSON.stringify(data);
        });
    }

    function sendLog(){
        var numberInput = document.getElementById("inputNumber").value;
        console.log(numberInput);
        $.ajax({
            url: "https://localhost:35000//sen",
            type:'GET',
            data: {
                value: numberInput
            }
        }).then(function(data) {
            console.log(data);
            var respuesta = document.getElementById("textoRespuesta");
            respuesta.innerHTML = JSON.stringify(data);
        });
    }

    return{
        sendCos: sendCos,
        sendLog: sendLog
    };

})();