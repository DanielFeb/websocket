function svc_connectPlatform() {  
    var topSid = $("#myTopic").val();
    var wsServer = 'ws://127.0.0.1:4444/'+topSid;  
    try {  
        svc_websocket = new WebSocket(wsServer);  
    } catch (evt) {  
        console.log("new WebSocket error:" + evt.data);  
        svc_websocket = null;  
        if (typeof(connCb) != "undefined" && connCb != null)  
        connCb("-1", "connect error!");  
        return;  
    }
    svc_websocket.onopen = svc_onOpen;
    svc_websocket.onclose = svc_onClose;
    svc_websocket.onmessage = svc_onMessage;
    svc_websocket.onerror = svc_onError;
}

function svc_close(evt) {
    console.log("close");
    svc_websocket.close();
}

function svc_onOpen(evt) {
    console.log("Connected to WebSocket server.");
}

function svc_onClose(evt) {
    console.log("Disconnected");
}

function svc_onMessage(evt) {
    var output = $("#output");
    output.val(output.val() + evt.data);
    console.log('Retrieved data from server: ' + evt.data);
}

function svc_onError(evt) {
    var output = $("#output");
    output.val(output.val() + evt.data);
    console.log('Error occured: ' + evt.data);
}
  
  
function svc_send() {
    var msg = $("#myMessage").val();
    if (svc_websocket.readyState == WebSocket.OPEN) {
        svc_websocket.send(msg);
    } else {  
        console.log("send failed. websocket not open. please check.");
    }  
}  