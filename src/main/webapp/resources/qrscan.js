Html5Qrcode.getCameras()
  .then((devices) => {
    /**
     * devices would be an array of objects of type:
     * { id: "id", label: "label" }
     */
    if (devices && devices.length) {
      var cameraId = devices[0].id;
      // .. use this to start scanning.
      console.log(`Selected Camera: ${cameraId}`);
    }
  })
  .catch((err) => {
    // handle err
    $("#alertMsg").html("Error getting cameras : ", err);
    $("#alert").show().delay(5000).fadeOut();
  });

const html5QrCode = new Html5Qrcode("reader");
const qrCodeSuccessCallback = (decodedText, decodedResult) => {
  /* handle success */
  html5QrCode.stop();
  var check = decodedText.split("/");
  var uid = $("#uid").data("id");
};

const config = { fps: 10, qrbox: { width: 250, height: 250 } };

// If you want to prefer front camera
html5QrCode.start(
  { facingMode: { exact: "environment" } },
  config,
  qrCodeSuccessCallback
);
