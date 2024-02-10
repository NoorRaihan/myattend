// Extend jQuery to add a new function called showModal
$.fn.extend({
  showModal: function () {
    // For each matched element, if it is a DIALOG tag, show the modal
    return this.each(function () {
      if (this.tagName === "DIALOG") {
        this.showModal();
      }
    });
  },
});

// Get the list of available cameras using Html5Qrcode library
Html5Qrcode.getCameras()
  .then((devices) => {
    /**
     * devices would be an array of objects of type:
     * { id: "id", label: "label" }
     */
    // If there are devices available, select the first camera and log its ID
    if (devices && devices.length) {
      var cameraId = devices[0].id;
      // Log the selected camera ID
      console.log(`Selected Camera: ${cameraId}`);
    }
  })
  .catch((err) => {
    // Handle any errors that occur during camera retrieval
    $("#alertMsg").html("Error getting cameras : ", err);
    $("#alert").show().delay(5000).fadeOut();
  });

// Create a new instance of Html5Qrcode with the element ID "qrscan"
const html5QrCode = new Html5Qrcode("qrscan");

// Define a success callback function for when a QR code is decoded
const qrCodeSuccessCallback = (decodedText, decodedResult) => {
  // Stop the QR code scanning
  html5QrCode.stop();

  // Split the decoded text by the period character
  var check = decodedText.split(".");

  // Get the value of the HTML element with the ID "id"
  var sid = $("#id").data("id");

  // Concatenate the decoded text with the value of "sid" and store it in the variable "data"
  let data = decodedText + "." + sid;

  // Log the value of "data" to the console
  console.log(data);

  // Check if the first part of the decoded text is "myattend"
  if (check[0] === "myattend") {
    // Send a POST request to the server
    $.ajax({
      method: "POST",
      url: "/class/attend",
      data: { data: data },
      dataType: "json",
      // If the request is successful, handle the response
      success: function (response) {
        // If the response status is "error"
        if (response.respStatus == "error") {
          // Set the source and other attributes of an image element
          $("#icn").attr({
            src: "https://cdn.lordicon.com/gzaeqxlp.json",
            trigger: "in",
            delay: "500",
            stroke: "bold",
            state: "in-reveal",
            colors: "primary:#e83a30,secondary:#e83a30",
            style: "width:150px;height:150px",
          });
          // Set the HTML content of an element to "ERROR"
          $(".teMsg").html("ERROR");
          // Set the HTML content of an element to the error message from the response
          let msg = "(" + response.respCode + ") " + response.respMessage;
          $(".eMsg").html(msg);
          // Show a modal with the ID "attendMsg"
          $("#attendMsg").showModal();
        } else {
          // Set the source and other attributes of an image element
          $("#icn").attr({
            src: "https://cdn.lordicon.com/cdfdnjgp.json",
            trigger: "in",
            delay: "500",
            stroke: "bold",
            state: "in-reveal",
            colors: "primary:#16c72e,secondary:#16c72e",
            style: "width:150px;height:150px",
          });
          // Set the HTML content of an element to "SUCCESS"
          $(".teMsg").html("SUCCESS");
          // Set the HTML content of an element to the success message from the response
          let msg = "Congratulations! " + response.respMessage;
          $(".eMsg").html(msg);
          // Show a modal with the ID "attendMsg"
          $("#attendMsg").showModal();
        }
      },
      // If the request encounters an error, handle the error response
      error: function (response) {
        // Set the source and other attributes of an image element
        $("#icn").attr({
          src: "https://cdn.lordicon.com/gzaeqxlp.json",
          trigger: "in",
          delay: "500",
          stroke: "bold",
          state: "in-reveal",
          colors: "primary:#e83a30,secondary:#e83a30",
          style: "width:150px;height:150px",
        });
        // Set the HTML content of an element to "ERROR"
        $(".teMsg").html("ERROR");
        // Set the HTML content of an element to the error message from the response
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $(".eMsg").html(msg);
        // Show a modal with the ID "attendMsg"
        $("#attendMsg").showModal();
      },
    });
  } else {
    // Set the source and other attributes of an image element
    $("#icn").attr({
      src: "https://cdn.lordicon.com/ywkmznae.json",
      trigger: "in",
      delay: "500",
      stroke: "bold",
      state: "in-reveal",
      colors: "primary:#e86830,secondary:#e88c30",
      style: "width:150px;height:150px",
    });
    // Set the HTML content of an element to "ERROR"
    $(".teMsg").html("ERROR");
    // Set the HTML content of an element to a message indicating an invalid QR code
    $(".eMsg").html("Sorry, this QR code is invalid");
    // Show a modal with the ID "attendMsg"
    $("#attendMsg").showModal();
  }
};

// Define configuration options for the QR code scanning
const config = { fps: 10, qrbox: { width: 250, height: 250 } };

// Make the QR scanner prefer the rear-facing camera
html5QrCode.start(
  { facingMode: { exact: "environment" } },
  config,
  qrCodeSuccessCallback
);
