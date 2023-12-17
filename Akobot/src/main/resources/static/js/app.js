var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {

    if (stompClient !== null && stompClient.connected) {
        // 이미 연결된 상태라면 연결 시도를 하지 않음
        return;
    }

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/answers', function (answering) {
            showGreeting(JSON.parse(answering.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {

    reply = "<div class=\"bubble reply reply-freeform say\">"
        + "<span class=\"bubble-content\">" + $("#ask").val() + "</span>"
        + "</div>"
    $("#greetings").append(reply);

    stompClient.send("/app/question", {}, JSON.stringify({'ask': $("#ask").val()}));
}

function showGreeting(messages) {

    for(i = 0; i < messages.length; i++){
        for(j = 0; j < messages[i].says.length; j++){
            if(messages[i].says[j] === null) continue;
            if(messages[i].says[j] === "\"\"") continue;
            if(messages[i].says[j] === "") continue;

            messageToken = messages[i].says[j].split('\\n')
            for(k = 0; k < messageToken.length; k++){
                str = "<div class=\"bubble say\">"
                    + "<span class=\"bubble-content\">" + messageToken[k] + "</span>"
                    + "</div>";
                $("#greetings").append(str);
            }
        }
    }
}

$(function () {
    connect(); //웹페이지에 접속하면 기본으로 연결 상태가 되도록 함

    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() {
        connect();
    });
    $( "#disconnect" ).click(function() {
        disconnect();
    });
    $( "#send" ).click(function() { sendName(); });

    $( "#btn-modal" ).click(function() {
        connect();
    });
});