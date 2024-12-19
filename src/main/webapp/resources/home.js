$(document).ready(function () {
  $(document).on("click", "#profileBtn", function () {
    // Hide the profileBtn and show the dashboardBtn
    $("#profileBtn").addClass("hidden");
    $("#dashboardBtn").removeClass("hidden");
    // Hide the dashboard and show the profile section
    $("#dashboard").addClass("hidden");
    $("#profile").removeClass("hidden");
  });

  $(document).on("click", "#dashboardBtn", function () {
    // Hide the dashboardBtn and show the profileBtn
    $("#dashboardBtn").addClass("hidden");
    $("#profileBtn").removeClass("hidden");
    // Hide the profile and show the dashboard section
    $("#profile").addClass("hidden");
    $("#dashboard").removeClass("hidden");
  });

  // Set up event handlers for elements with the prflBtn class and qr class
  $(document).on("click", ".prflBtn", function () {
    // Get the data-id attribute of the clicked element and call usrDetails function with the id
    var id = $(this).data("id");
    usrDetails(id);
  });

  $(document).on("click", ".qr", function () {
    // Get the data-id attribute of the clicked element and call generateQR function with the id
    var id = $(this).data("id");
    getClass(id);
    generateQR(id);
    refreshQR = setInterval(() => generateQR(id), 60000);
  });

  $(document).on("click", "#qrDone", function () {
    clearInterval(refreshQR);
  });
});

// Function to make an AJAX request to fetch user details by id
function usrDetails(id) {
  $.ajax({
    method: "GET",
    url: "/user/profile",
    data: { uid: id },
    dataType: "json",
    success: function (response) {
      // If the response status is error, show an alert message with the error code and message
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        // Populate the form fields with the fetched user details
        $("#fullname").val(response.data.fullname);
        $("#username").val(response.data.username);
        $("#gender option[value='" + response.data.gender + "']").prop(
          "selected",
          true
        );
        $("#birthdate").val(response.data.formBirthDate);
        $("#email").val(response.data.email);
        $("#dpImage").val(response.data.profile_pic);
      }
    },
    // If there is an error, show an alert message with the error code and message
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}

function getClass(id) {
  $.ajax({
    url: "/class/generateQR",
    method: "GET",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      // If the response status is error, show an alert message with the error code and message
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        // Extract details from the response
        var details = response.data;

        // Create a Date object for start and end time
        var strt = new Date(details.class.start_time);
        var end = new Date(details.class.end_time);

        // Format the date and time for display
        let date = strt.toLocaleDateString("en-GB", {
          weekday: "long",
          year: "numeric",
          month: "long",
          day: "numeric",
        });

        strt = strt.toLocaleTimeString("en-GB", {
          hour: "numeric",
          minute: "numeric",
          hour12: true,
        });

        end = end.toLocaleTimeString("en-GB", {
          hour: "numeric",
          minute: "numeric",
          hour12: true,
        });

        $("#qrTitle").html(details.class.class_desc);
        $("#qrDate").html(date);
        $("#qrTime").html(strt + " - " + end);
        $("#qrVenue").html(details.class.venue);
      }
    },
    error: function (response) {
      // Displays an alert with a message formatted with response code and message
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}

// Function to make an AJAX request to generate a QR code for a given id
function generateQR(id) {
  $.ajax({
    url: "/class/generateQR",
    method: "GET",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      // If the response status is error, show an alert message with the error code and message
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        // Extract details from the response
        var details = response.data;

        // Generate a QR code
        var qr = new QRious({
          size: 500,
          level: "H",
          foreground: "#872f7b",
          padding: 25,
          value: details.attendanceURL,
        });

        $("#qrImage").prop("src", qr.toDataURL());
      }
    },
    error: function (response) {
      // Displays an alert with a message formatted with response code and message
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
