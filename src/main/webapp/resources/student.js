$(document).ready(function () {
  // Attach a click event to elements with class "edit"
  $(document).on("click", ".edit", function () {
    // Get the data-id attribute value of the clicked element
    var id = $(this).data("id");
    // Call the studDetails function with the id as parameter
    studDetails(id);
  });

  // Attach a click event to elements with class "delete"
  $(document).on("click", ".delete", function () {
    // Get the data-id attribute value of the clicked element
    var id = $(this).data("id");
    // Log the id to the console
    console.log(id);
    // Set the value of the input with id "deleteId" to the id
    $("#deleteId").val(id);
  });

  // Initialize the DataTable for the element with id "studentDT"
  $("#studentDT").DataTable();
});

// Define the studDetails function with parameter id
function studDetails(id) {
  // Make an AJAX GET request
  $.ajax({
    // Specify the HTTP method
    method: "GET",
    // Specify the URL for the request
    url: "/student/detail",
    // Send the uid parameter with the id value
    data: { uid: id },
    // Specify the expected data type of the response
    dataType: "json",
    // Define the success callback function with response parameter
    success: function (response) {
      // Check if the response status is "error"
      if (response.respStatus == "error") {
        // Construct a message with response code and message
        let msg = "(" + response.respCode + ") " + response.respMessage;
        // Set the message to the element with id "alertMsg"
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        // Set the values of various input fields based on the response data
        $("#id").val(response.data.user.id);
        $("#stud_id").val(response.data.stud_id);
        $("#fullname").val(response.data.user.fullname);
        $("#program").val(response.data.program);
        $("#intake").val(response.data.intake);
        $("#semester").val(response.data.semester);
      }
    },
    // Define the error callback function with response parameter
    error: function (response) {
      // Construct a message with response code and message
      let msg = "(" + response.respCode + ") " + response.respMessage;
      // Set the message to the element with id "alertMsg"
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
