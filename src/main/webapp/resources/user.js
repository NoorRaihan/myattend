// When the document is ready, set up event listeners for the "edit" and "delete" buttons
$(document).ready(function () {
  $(document).on("click", ".edit", function () {
    // Get the id from the clicked element and call usrDetails function with the id
    var id = $(this).data("id");
    usrDetails(id);
  });

  $(document).on("click", ".delete", function () {
    // Get the id from the clicked element and set the value to the input field with id "uid"
    var id = $(this).data("id");
    $("#uid").val(id);
  });

  // Initialize the DataTable for the element with id "userDT"
  $("#userDT").DataTable();
});

// Function to make an AJAX request to fetch user details
function usrDetails(id) {
  $.ajax({
    method: "GET",
    url: "/user/detail", // API endpoint for fetching user details
    data: { uid: id }, // Data to be sent with the request
    dataType: "json",
    success: function (response) {
      // If response status is "error", display an alert message with response code and message
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        // If response status is not "error", populate the form fields with the fetched user data
        $("#editId").val(response.data.id);
        $("#fullname").val(response.data.fullname);
        $("#username").val(response.data.username);
        $("#gender option[value='" + response.data.gender + "']").prop(
          "selected",
          true
        );
        $("#birthdate").val(response.data.formBirthDate);
        $("#email").val(response.data.email);
        $("#role option[value='" + response.data.role_id + "']").prop(
          "selected",
          true
        );
        $("#dpImage").val(response.data.profile_pic);
      }
    },
    error: function (response) {
      // If the request fails, display an alert message with response code and message
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
