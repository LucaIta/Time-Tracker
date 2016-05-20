var h = 0;
var m = 0;
var s = 0;
var ten = 0;

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
      m -= 60;
    }


    document.getElementById('clock').innerHTML =
    h + ":" + checkTime(m) + ":" + checkTime(s) + "." + ten;
    var t = setTimeout(startTime, 100);
}

function checkTime(i) {
    if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
    return i;
}
