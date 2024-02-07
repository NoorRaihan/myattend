$(document).ready(function () {
  $(document).on("click", "#profileBtn", function () {
    $("#profileBtn").addClass("hidden");
    $("#dashboardBtn").removeClass("hidden");
    $("#dashboard").addClass("hidden");
    $("#profile").removeClass("hidden");
  });

  $(document).on("click", "#dashboardBtn", function () {
    $("#dashboardBtn").addClass("hidden");
    $("#profileBtn").removeClass("hidden");
    $("#profile").addClass("hidden");
    $("#dashboard").removeClass("hidden");
  });

  $(document).on("click", ".prflBtn", function () {
    var id = $(this).data("id");
    usrDetails(id);
  });

  $(document).on("click", ".qr", function () {
    var id = $(this).data("id");
    generateQR(id);
  });
});

function usrDetails(id) {
  $.ajax({
    method: "GET",
    url: "/user/profile",
    data: { uid: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
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
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}

function generateQR(id) {
  $.ajax({
    url: "/class/generateQR",
    method: "GET",
    data: { id: id },
    dataType: "json",
    success: function (response) {
      if (response.respStatus == "error") {
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $("#alertMsg").html(msg);
        $("#alert").show().delay(5000).fadeOut();
      } else {
        var details = response.data;

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
        //printWindow.document.close();

        // Trigger the print dialog
        //printWindow.print();
        //printWindow.close();
      }
    },
    error: function (response) {
      let msg = "(" + response.respCode + ") " + response.respMessage;
      $("#alertMsg").html(msg);
      $("#alert").show().delay(5000).fadeOut();
    },
  });
}
