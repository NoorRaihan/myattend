$.fn.extend({
  showModal: function () {
    return this.each(function () {
      if (this.tagName === "DIALOG") {
        this.showModal();
      }
    });
  },
});

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

const html5QrCode = new Html5Qrcode("qrscan");
const qrCodeSuccessCallback = (decodedText, decodedResult) => {
  /* handle success */
  html5QrCode.stop();
  var check = decodedText.split(".");
  var sid = $("#id").data("id");
  let data = decodedText + "." + sid;
  console.log(data);

  if (check[0] === "myattend") {
    $.ajax({
      method: "POST",
      url: "/class/attend",
      data: { data: data },
      dataType: "json",
      success: function (response) {
        $("#icn").attr({
          src: "https://cdn.lordicon.com/cdfdnjgp.json",
          trigger: "in",
          delay: "500",
          stroke: "bold",
          state: "in-reveal",
          colors: "primary:#16c72e,secondary:#16c72e",
          style: "width:150px;height:150px",
        });
        $(".teMsg").html("SUCCESS");
        let msg = "Congratulations! " + response.respMessage;
        $(".eMsg").html(msg);
        $("#attendMsg").showModal();
      },
      error: function (response) {
        $("#icn").attr({
          src: "https://cdn.lordicon.com/gzaeqxlp.json",
          trigger: "in",
          delay: "500",
          stroke: "bold",
          state: "in-reveal",
          colors: "primary:#e83a30,secondary:#e83a30",
          style: "width:150px;height:150px",
        });
        $(".teMsg").html("ERROR");
        let msg = "(" + response.respCode + ") " + response.respMessage;
        $(".eMsg").html(msg);
        $("#attendMsg").showModal();
      },
    });
  } else {
    $("#icn").attr({
      src: "https://cdn.lordicon.com/ywkmznae.json",
      trigger: "in",
      delay: "500",
      stroke: "bold",
      state: "in-reveal",
      colors: "primary:#e86830,secondary:#e88c30",
      style: "width:150px;height:150px",
    });
    $(".teMsg").html("ERROR");
    $(".eMsg").html("Sorry, this QR code is invalid");
    $("#attendMsg").showModal();
  }
};

const config = { fps: 10, qrbox: { width: 250, height: 250 } };

// If you want to prefer front camera
html5QrCode.start(
  { facingMode: { exact: "environment" } },
  config,
  qrCodeSuccessCallback
);
