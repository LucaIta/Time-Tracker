// function startTime() {
//     var today = new Date();
//     var h = today.getHours();
//     var m = today.getMinutes();
//     var s = today.getSeconds();
//     m = checkTime(m);
//     s = checkTime(s);
//     document.getElementById('test').innerHTML =
//     h + ":" + m + ":" + s;
//     var t = setTimeout(startTime, 500);
// }
//
// function checkTime(i) {
//     if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
//     return i;
// }
var h = 0;
var m = 0;
var s = 0;
var ten = 1;

function startTime() {
    ten++;
    if (ten >= 10) {
      ten -= 10;
      s += 1;
    }
    if (s >= 60) {
      s -= 60;
      m += 1;
    }
    if (m >= 60) {
      h += 1;
    }
    var hour = h;
    var min = checkTime(m);
    var sec = checkTime(s);
    //var tenths = checkTime(ten);

    document.getElementById('test').innerHTML =
    hour + ":" + min + ":" + sec + "." + ten;
    var t = setTimeout(startTime, 100);
}

function checkTime(i) {
    if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
    return i;
}
