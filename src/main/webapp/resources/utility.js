$(document).ready(function () {
  $(document).on("click", ".roleEdit", function () {
    // When "roleEdit" button is clicked, get the id from the data attribute and call roleDetails function with the id
    var id = $(this).data("id");
    roleDetails(id);
  });

  $(document).on("click", ".roleDelete", function () {
    // When "roleDelete" button is clicked, get the id from the data attribute and set it to the input field with id "roleid"
    var id = $(this).data("id");
    $("#roleid").val(id);
  });
});

// Define the roleDetails function that takes an id parameter
function roleDetails(id) {
  // Make an AJAX GET request to "/utility/detail" with the id as data
  $.ajax({
    method: "GET",
    url: "/utility/detail",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      // If the response status is "error", display the error message in the alert
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        // If the response status is not "error", set the values of input fields based on the response data
        $("#role_id").val(response.data.id);
        $("#oriId").val(response.data.id);
        $("#role_name").val(response.data.role_name);
      }
    },
    error: function (response) {
      // If the AJAX request encounters an error, display the error message in the alert
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
