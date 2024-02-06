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
});
