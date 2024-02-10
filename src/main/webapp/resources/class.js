// When the document is ready, set up event listeners for various actions
$(document).ready(function () {
  // When an element with class "classF" is clicked, perform the following actions
  $(document).on("click", ".classF", function () {
    // Get the data attribute "cid" from the clicked element
    var id = $(this).data("cid");
    // Remove the "hidden" class from the element with id "panelAttend"
    $("#panelAttend").removeClass("hidden");
    // Call the classAttend function with the extracted id
    classAttend(id);
  });

  // When an element with class "edit" is clicked, perform the following actions
  $(document).on("click", ".edit", function () {
    // Get the data attribute "id" from the clicked element
    var id = $(this).data("id");
    // Call the classDetails function with the extracted id
    classDetails(id);
  });

  // When an element with class "delete" is clicked, perform the following actions
  $(document).on("click", ".delete", function () {
    // Get the data attribute "id" from the clicked element and set it as the value of element with id "del_id"
    var id = $(this).data("id");
    $("#del_id").val(id);
  });

  // When an element with class "qr" is clicked, perform the following actions
  $(document).on("click", ".qr", function () {
    // Get the data attribute "id" from the clicked element and call the generateQR function with the extracted id
    var id = $(this).data("id");
    generateQR(id);
  });
});

// Function to retrieve class details using AJAX
function classDetails(id) {
  $.ajax({
    method: "GET",
    url: "/class/detail",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      // If the response status is "error", display an alert message
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        // Populate the form fields with the retrieved class details
        $("#cls_id").val(response.data.id);
        $("#cls_desc").val(response.data.class_desc);
        $("#c_id").val(response.data.course_id);
        $("#cls_date").val(response.data.formClassDate);
        $("#cls_sTime").val(response.data.formStartTime);
        $("#cls_eTime").val(response.data.formEndTime);
        $("#cls_venue").val(response.data.venue);
      }
    },
    error: function (response) {
      // If there's an error, display an alert message
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}

// Function to load the list of classes for a course using AJAX
function loadClsList(id) {
  // AJAX request to retrieve the list of classes for a course
  $.ajax({
    method: "GET",
    url: "/class/course",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      // If the response status is "error", display an alert message
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        // Generate the HTML for the class list based on the retrieved data
        // ...
        // Populate the #classList element with the generated class list
        $("#classList").html(classList);
      }
    },
    error: function (response) {
      // If there's an error, display an alert message
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}

// Function to handle class attendance using AJAX
function classAttend(id) {
  $.ajax({
    method: "GET",
    url: "/class/attendList",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      // If the response status is "error", display an alert message
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        // Populate the #c_id element with the attendance list
        $("#c_id").val(response.data.id);
        var studList =
          "<ul role='list' class='flex-1 divide-y divide-gray-200 overflow-y-auto'>";
        for (var stuData of response.data) {
          studList +=
            `<li>
            <div class='group relative flex items-center px-5 py-6'>
              <div class='-m-1 block flex-1 p-1'>
                <div class='absolute inset-0' aria-hidden='true'></div>
                <div class='relative flex min-w-0 flex-1 items-center'>
                  <div class='truncate'>
                    <p class='truncate text-sm font-medium text-gray-900'>` +
            stuData.student.user.fullname +
            `</p>
                    <p class='truncate text-sm text-gray-500'>` +
            stuData.student.stud_id +
            `</p>
                  </div>
                </div>
              </div>
              `;
          if (stuData.status == "C") {
            studList += `<div class='relative ml-2 inline-block flex-shrink-0 tooltip' data-tip='Present'>
                <svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='currentColor' class='size-7 fill-green-500'>
                  <path fill-rule='evenodd' d='M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12Zm13.36-1.814a.75.75 0 1 0-1.22-.872l-3.236 4.53L9.53 12.22a.75.75 0 0 0-1.06 1.06l2.25 2.25a.75.75 0 0 0 1.14-.094l3.75-5.25Z' clip-rule='evenodd'>
                </svg>
              </div>`;
          } else if (stuData.status == "AB") {
            studList += `<div
            class="relative ml-2 inline-block flex-shrink-0 tooltip"
            data-tip="Absent"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="currentColor"
              class="size-7 fill-red-500"
            >
              <path
                fill-rule="evenodd"
                d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25Zm-1.72 6.97a.75.75 0 1 0-1.06 1.06L10.94 12l-1.72 1.72a.75.75 0 1 0 1.06 1.06L12 13.06l1.72 1.72a.75.75 0 1 0 1.06-1.06L13.06 12l1.72-1.72a.75.75 0 1 0-1.06-1.06L12 10.94l-1.72-1.72Z"
                clip-rule="evenodd"
              />
            </svg>
          </div>`;
          } else {
            studList += `<div
            class="relative ml-2 inline-block flex-shrink-0 tooltip"
            data-tip="Absent with reason"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="currentColor"
              class="size-7 fill-blue-500"
            >
              <path
                fill-rule="evenodd"
                d="M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12ZM12 8.25a.75.75 0 0 1 .75.75v3.75a.75.75 0 0 1-1.5 0V9a.75.75 0 0 1 .75-.75Zm0 8.25a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5Z"
                clip-rule="evenodd"
              />
            </svg>
          </div>`;
          }
          studList += `</div></li>`;
        }
        studList += "</ul>";
        $("#attendList").html(studList);
      }
    },
    error: function (response) {
      // If there's an error, display an alert message
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

        // Create a print-friendly window
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
    error: function (response) {
      // Displays an alert with a message formatted with response code and message
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
