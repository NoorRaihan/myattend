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

  var refreshQR = null;
  // When an element with class "qr" is clicked, perform the following actions
  $(document).on("click", ".qr", function () {
    // Get the data attribute "id" from the clicked element and call the generateQR function with the extracted id
    var id = $(this).data("id");
    getClass(id);
    generateQR(id);
    refreshQR = setInterval(() => generateQR(id), 60000);
  });

  $(document).on("click", "#qrDone", function () {
    clearInterval(refreshQR);
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
        var classList;
        if (response.data.classes.length == 0) {
          classList = `<div class="w-full text-center justify-center py-10">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="mx-auto size-12 text-gray-400"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="m3 3 1.664 1.664M21 21l-1.5-1.5m-5.485-1.242L12 17.25 4.5 21V8.742m.164-4.078a2.15 2.15 0 0 1 1.743-1.342 48.507 48.507 0 0 1 11.186 0c1.1.128 1.907 1.077 1.907 2.185V19.5M4.664 4.664 19.5 19.5"
            />
          </svg>
          <h3 class="mt-2 text-sm font-semibold text-gray-400">
            No classes found. Please add a new class.
          </h3>
        </div>`;
        } else {
          classList = "<table class='min-w-full divide-y divide-gray-300'>";
          classList +=
            "<thead><tr><th scope='col' class='py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-0'>Class</th><th scope='col' class='hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell'>Date</th><th scope='col' class='hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 sm:table-cell'>Time</th><th scope='col' class='px-3 py-3.5 text-left text-sm font-semibold text-gray-900'>Venue</th><th scope='col' class='px-3 py-3.5 text-left text-sm font-semibold text-gray-900'>Session</th></tr></thead>";
          classList += "<tbody class='divide-y divide-gray-200 bg-white'>";
          for (var dataItem of response.data.classes) {
            classList +=
              "<tr class='cursor-pointer classF' data-cid='" +
              dataItem.id +
              "'>" +
              "<td class='w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0'>" +
              dataItem.class_desc +
              "<dl class='font-normal lg:hidden'>" +
              "<dd class='mt-1 truncate text-gray-700'>" +
              dataItem.formattedClassDate +
              "</dd>" +
              "<dd class='mt-1 truncate text-gray-500 sm:hidden'>" +
              dataItem.formStartTime +
              " - " +
              dataItem.formEndTime +
              "</dd>" +
              "</dl>" +
              "</td>" +
              "<td class='hidden px-3 py-4 text-sm text-gray-500 lg:table-cell'>" +
              dataItem.formattedClassDate +
              "</td>" +
              "<td class='hidden px-3 py-4 text-sm text-gray-500 sm:table-cell'>" +
              dataItem.formStartTime +
              " - " +
              dataItem.formEndTime +
              "</td>" +
              "<td class='px-3 py-4 text-nowrap text-sm text-gray-500'>" +
              dataItem.venue +
              "</td>" +
              "<td class='px-3 py-4 text-nowrap text-sm text-gray-500'>" +
              dataItem.sessionModel.sessionName +
              "</td>" +
              "<td class='py-4 pr-4 text-nowrap text-left text-sm text-gray-500 pl-0'>" +
              "<div class='dropdown dropdown-left dropdown-hover'>" +
              "<div tabindex='0' role='button' class='btn btn-ghost btn-xs'>" +
              "<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='currentColor' class='size-6'>" +
              "<path fill-rule='evenodd' d='M10.5 6a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0Zm0 6a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0Zm0 6a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0Z' clip-rule='evenodd'/>" +
              "</svg>" +
              "</div>" +
              "<ul tabindex='0' class='dropdown-content z-[1] menu p-2 shadow-lg bg-neutral rounded-box w-fit'>" +
              "<li><a onclick='qrModal.showModal()' data-id='" +
              dataItem.id +
              "' class='font-bold qr'>Attendance QR</a></li>" +
              "<li><a onclick='editClass.showModal()' data-id='" +
              dataItem.id +
              "' class='font-bold edit'>Edit</a></li>" +
              "<li><a onclick='deleteClass.showModal()' class='text-red-600 font-bold delete' data-id='" +
              dataItem.id +
              "'>Delete</a></li>" +
              "</ul>" +
              "</div>" +
              "</td>" +
              "</tr>";
          }
          classList += "</tbody></table>";
        }
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
