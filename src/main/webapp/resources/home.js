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
    generateQR(id);
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

        // Create a Date object for start and end time
        var strt = new Date(details.class.start_time);
        var end = new Date(details.class.end_time);
        var options = {
          weekday: "long",
          year: "numeric",
          month: "long",
          day: "numeric",
          hour: "numeric",
          minute: "numeric",
          hour12: true,
        };
        // Format the date and time for display
        var date = strt
          .toLocaleDateString("en-GB", options)
          .replace(",", " | ");

        // Generate a QR code
        var qr = new QRious({
          element: document.getElementById("qrCode"),
          size: 500,
          level: "H",
          foreground: "#872f7b",
          padding: 25,
          value: details.attendanceURL,
        });
        console.log(details.attendanceURL);

        // Create a print-friendly window with the class details and QR code
        var printWindow = window.open("", "_blank");
        printWindow.document.open();
        printWindow.document.write('<html data-theme="light">');
        printWindow.document.write(
          '<html data-theme="light"><head><title>Class QR</title>'
        );
        printWindow.document.write(
          '<link href="/resources/output.css" rel="stylesheet">'
        );
        printWindow.document.write(
          '<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/paper-css/0.3.0/paper.css">'
        );
        printWindow.document.write("<style>@page { size: A4 }</style>");
        printWindow.document.write("</head>");
        printWindow.document.write(`<body class="A4" >`);
        printWindow.document.write(
          `<section class="sheet padding-10mm" style="background-image: url('./img/eKehadiran.jpg'); background-size: cover;">`
        );
        printWindow.document.write(
          '<div class="mt-52 flex flex-col justify-center">'
        );
        printWindow.document.write(
          '<h1 class="text-2xl font-bold text-center">' +
            details.class.class_desc +
            "</h1>"
        );
        printWindow.document.write(
          '<p class="text-lg font-semibold text-center">' + date + "</p>"
        );
        printWindow.document.write(
          '<p class="text-center">' + details.class.venue + "</p>"
        );
        printWindow.document.write(
          '<img src="' +
            qr.toDataURL() +
            '" class="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 mt-10 w-96" />'
        );
        printWindow.document.write("</div></section></body></html>");
      }
    },
    // Displays an alert with a message formatted with response code and message
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
