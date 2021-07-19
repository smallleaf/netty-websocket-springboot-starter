function gen(){
    var list = new Array();
    for (var i = 0; i <1000 ; i++) {
        var data = {
            "id":i,
            "wsUrl":"ws://127.0.0.1:8888?userId="+i
        };
        list.push(data);
    };
    return JSON.stringify(list);
}