$(document).ready(function () {
  // When an element with class "edit" is clicked, call the studDetails function with the clicked element's data-id
  $(document).on("click", ".edit", function () {
    var id = $(this).data("id");
    studDetails(id);
  });

  // When an element with class "delete" is clicked, set the value of the element with id "uid" to the clicked element's data-id
  $(document).on("click", ".delete", function () {
    var id = $(this).data("id");
    $("#uid").val(id);
  });

  // Initialize the DataTable for the element with id "lecturerDT"
  $("#lecturerDT").DataTable();
});

// Define the studDetails function with id as a parameter
function studDetails(id) {
  // Make an AJAX GET request to the "/lecturer/detail" endpoint with the id as a parameter
  $.ajax({
    method: "GET",
    url: "/lecturer/detail",
    data: { uid: id },
    dataType: "json",
    // If the request is successful, show or set various elements' values based on the response data
    success: function (response) {
      if (response.respStatus == "error") {
        // If the response status is "error", show an error message and then fade it out after 5 seconds
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        // If the response status is not "error", set various elements' values using the response data
        $("#id").val(response.data.user.id);
        $("#lect_id").val(response.data.lect_id);
        $("#fullname").val(response.data.user.fullname);
        $("#startDate").val(response.data.formStartDate);
        $("#qualify").val(response.data.qualification);
        $("#salary").val(response.data.salary);
        $("#sv").val(response.data.supervisor_id);
      }
    },
    // If the request encounters an error, show an error message and then fade it out after 5 seconds
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
