$(document).ready(function() {
  alert("Success!");

  $("form").submit(function(event) {
    event.preventDefault();
    //nothing yet
  });

  $("#test").click(function() {
    alert("Test Success");
    location.reload();
  });
});
