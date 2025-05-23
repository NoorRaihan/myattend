<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html data-theme="blue">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Record Attendance - myAttend+</title>
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/theme-change@2.5.0/index.js"></script>
    <script src="https://cdn.lordicon.com/lordicon-1.1.0.js"></script>
    <script
      src="${contextPath}/resources/html5-qrcode.min.js"
      type="text/javascript"
    ></script>
  </head>
  <body class="bg-natural min-h-screen">
    <div class="fixed inset-x-0 w-full bg-primary min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <jsp:include page="../Home/header.jsp">
          <jsp:param name="menu" value="<li>Record Attendance</li>" />
          <jsp:param name="title" value="Record Attendance" />
        </jsp:include>
        <div class="w-auto my-10">
          <div
            class="flex flex-wrap-reverse mx-4 gap-5 items-baseline transition-all"
          >
            <div class="basis-full">
              <div class="card card-compact bg-base-100 shadow-xl">
                <div class="card-body items-center text-center">
                  <h2 class="card-title">Attendance</h2>
                  <p class="text-sm mb-6">
                    Scan your lecturer's provided QR code to record your class
                    attendance
                  </p>
                  <div id="qrscan" class="container mx-6 w-full"></div>
                  <input
                    type="hidden"
                    class="id"
                    name="id"
                    id="id"
                    data-id="${sessionScope.sid}"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div id="alert" class="toast toast-top toast-end z-50 hidden">
          <div class="alert alert-warning">
            <span id="alertMsg">Error</span>
          </div>
        </div>
        <div id="succ" class="toast toast-top toast-end z-50 hidden">
          <div class="alert alert-success">
            <span id="succMsg">Success</span>
          </div>
        </div>
      </div>

      <dialog id="attendMsg" class="modal">
        <form method="dialog" class="modal-box flex flex-col justify-center">
          <lord-icon id="icn" class="mx-auto"></lord-icon>
          <h3 class="font-bold text-center text-lg teMsg"></h3>
          <p class="text-md text-center eMsg"></p>
          <div class="modal-action justify-center">
            <a href="/" id="evtId" class="btn btn-primary id">Done</a>
          </div>
        </form>
      </dialog>

      <%@ include file="../Home/drawer.jsp" %>
    </div>
  </body>
  <script src="${contextPath}/resources/qrscan.js"></script>
</html>
