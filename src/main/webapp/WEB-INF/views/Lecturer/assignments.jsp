<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Assignment Management - myAttend+</title>
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/qrious/4.0.2/qrious.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/theme-change@2.5.0/index.js"></script>
    <script src="${contextPath}/resources/assignment.js"></script>
  </head>
  <body class="bg-neutral min-h-screen">
    <div class="fixed inset-x-0 w-full bg-primary min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <jsp:include page="../Home/header.jsp">
          <jsp:param
            name="menu"
            value="<li class='sm:hidden'>Lecturer</li><li>Assignment Management</li>"
          />
          <jsp:param name="title" value="Assignment Management" />
        </jsp:include>
        <div class="w-auto my-10">
          <section
            class="flex ml-2 overflow-x-auto scrollbar-hide"
            aria-labelledby="profile-overview-title"
          >
            <c:forEach var="course" items="${courses}">
              <div
                id="course"
                class="cursor-pointer"
                onclick="loadClsList('${course.getId()}'); $('.newCls').removeClass('hidden'); $('#panelAttend').addClass('hidden'); $('#c_id').val('${course.getId()}');"
              >
                <div
                  class="mx-2 card ${course.getColorConfig()} shadow-lg hover:shadow-2xl overflow-hidden w-72 flex-shrink-0"
                >
                  <div class="card-body">
                    <div class="flex flex-row justify-between z-10">
                      <div class="flex flex-col">
                        <p class="text-2xl font-bold">
                          ${course.getCourse_code()}
                        </p>
                        <p class="text-lg font-semibold">
                          ${course.getCourse_name()}
                        </p>
                        <p class="text-sm">
                          ${course.getCredit_hour()} Credit Hours
                        </p>
                      </div>
                    </div>
                    <div class="absolute -top-7 -right-5">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 24 24"
                        fill="currentColor"
                        class="size-28 fill-slate-200"
                      >
                        <path
                          d="M11.7 2.805a.75.75 0 0 1 .6 0A60.65 60.65 0 0 1 22.83 8.72a.75.75 0 0 1-.231 1.337 49.948 49.948 0 0 0-9.902 3.912l-.003.002c-.114.06-.227.119-.34.18a.75.75 0 0 1-.707 0A50.88 50.88 0 0 0 7.5 12.173v-.224c0-.131.067-.248.172-.311a54.615 54.615 0 0 1 4.653-2.52.75.75 0 0 0-.65-1.352 56.123 56.123 0 0 0-4.78 2.589 1.858 1.858 0 0 0-.859 1.228 49.803 49.803 0 0 0-4.634-1.527.75.75 0 0 1-.231-1.337A60.653 60.653 0 0 1 11.7 2.805Z"
                        />
                        <path
                          d="M13.06 15.473a48.45 48.45 0 0 1 7.666-3.282c.134 1.414.22 2.843.255 4.284a.75.75 0 0 1-.46.711 47.87 47.87 0 0 0-8.105 4.342.75.75 0 0 1-.832 0 47.87 47.87 0 0 0-8.104-4.342.75.75 0 0 1-.461-.71c.035-1.442.121-2.87.255-4.286.921.304 1.83.634 2.726.99v1.27a1.5 1.5 0 0 0-.14 2.508c-.09.38-.222.753-.397 1.11.452.213.901.434 1.346.66a6.727 6.727 0 0 0 .551-1.607 1.5 1.5 0 0 0 .14-2.67v-.645a48.549 48.549 0 0 1 3.44 1.667 2.25 2.25 0 0 0 2.12 0Z"
                        />
                        <path
                          d="M4.462 19.462c.42-.419.753-.89 1-1.395.453.214.902.435 1.347.662a6.742 6.742 0 0 1-1.286 1.794.75.75 0 0 1-1.06-1.06Z"
                        />
                      </svg>
                    </div>
                  </div>
                </div>
              </div>
            </c:forEach>
          </section>
          <div
            class="flex flex-wrap mt-4 mx-4 gap-5 items-start transition-all"
          >
            <div
              id="clsList"
              class="card card-compact md:basis-2/4 basis-full bg-white shadow-lg grow transition-all"
            >
              <div class="card-body">
                <div class="card-title justify-between">
                  <h1>Assignments</h1>
                  <button
                    class="btn btn-sm btn-primary hidden newCls"
                    onclick="newClass.showModal(); classAdd.reset();"
                  >
                    New Assignment
                  </button>
                </div>
                <div id="classList" class="-mx-4 sm:-mx-0">
                  <div class="w-full text-center justify-center py-10">
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
                        d="M4.26 10.147a60.438 60.438 0 0 0-.491 6.347A48.62 48.62 0 0 1 12 20.904a48.62 48.62 0 0 1 8.232-4.41 60.46 60.46 0 0 0-.491-6.347m-15.482 0a50.636 50.636 0 0 0-2.658-.813A59.906 59.906 0 0 1 12 3.493a59.903 59.903 0 0 1 10.399 5.84c-.896.248-1.783.52-2.658.814m-15.482 0A50.717 50.717 0 0 1 12 13.489a50.702 50.702 0 0 1 7.74-3.342M6.75 15a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5Zm0 0v-3.675A55.378 55.378 0 0 1 12 8.443m-7.007 11.55A5.981 5.981 0 0 0 6.75 15.75v-1.5"
                      />
                    </svg>
                    <h3 class="mt-2 text-sm font-semibold text-gray-400">
                      Please select a course to view assignments
                    </h3>
                  </div>
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
        <script>
          let error = "${sessionScope.error}";
          if (error != "") {
            $("#alertMsg").html(error);
            $("#alert").show().delay(3000).fadeOut();
          }
          let succ = "${sessionScope.success}";
          if (succ != "") {
            $("#succMsg").html(succ);
            $("#succ").show().delay(3000).fadeOut();
          }
        </script>
      </div>

      <%@ include file="../Home/drawer.jsp" %>
    </div>
    <dialog id="newClass" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Add New Assignment</h3>
        <form id="classAdd" action="" method="post">
          <input
            type="hidden"
            name="course_id"
            id="c_id"
            value="7f12b098-35e2-4e4e-bca3-27d74412d73b"
          />
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Class Name/Description</span>
              </div>
              <input
                type="text"
                name="class_desc"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Date</span>
              </div>
              <input
                type="date"
                name="class_date"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Start Time</span>
              </div>
              <input
                type="time"
                name="start_time"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">End Time</span>
              </div>
              <input
                type="time"
                name="end_time"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Venue</span>
              </div>
              <input
                type="text"
                name="venue"
                class="input input-primary input-sm input-bordered"
              />
            </label>
          </div>
          <div class="modal-action">
            <button type="submit" class="btn btn-sm btn-primary">Save</button>
          </div>
        </form>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>

    <dialog id="editClass" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Edit Class</h3>
        <form id="classEdt" action="/class/update" method="post">
          <input type="hidden" name="cid" id="c_id" value="1" />
          <input type="hidden" name="id" id="cls_id" value="1" />
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Class Name/Description</span>
              </div>
              <input
                type="text"
                id="cls_desc"
                name="class_desc"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Date</span>
              </div>
              <input
                type="date"
                id="cls_date"
                name="class_date"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Start Time</span>
              </div>
              <input
                type="time"
                id="cls_sTime"
                name="start_time"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">End Time</span>
              </div>
              <input
                type="time"
                id="cls_eTime"
                name="end_time"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Venue</span>
              </div>
              <input
                type="text"
                id="cls_venue"
                name="venue"
                class="input input-primary input-sm input-bordered"
              />
            </label>
          </div>
          <div class="modal-action">
            <button type="submit" class="btn btn-sm btn-primary">Save</button>
          </div>
        </form>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>

    <dialog id="deleteClass" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Delete Class</h3>
        <p class="py-4">Do you really want to delete this class ?</p>
        <form action="/class/delete" method="post">
          <input type="hidden" name="id" id="del_id" value="1" />
          <div class="modal-action">
            <button type="submit" class="btn btn-sm btn-error text-white">
              Yes
            </button>
          </div>
        </form>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>

    <dialog id="qrModal" class="modal">
      <div class="modal-box">
        <h3 class="text-2xl font-bold text-center" id="qrTitle"></h3>
        <p class="mt-2 text-lg font-semibold text-center" id="qrDate"></p>
        <p class="font-semibold text-center" id="qrTime"></p>
        <p class="text-center" id="qrVenue"></p>
        <div class="mt-4 flex justify-center items-center">
          <img class="w-3/5" id="qrImage" alt="QR Code" />
        </div>
        <div class="modal-action justify-center">
          <form method="dialog">
            <button class="btn btn-sm btn-primary" id="qrDone">Done</button>
          </form>
        </div>
      </div>
    </dialog>

    <c:remove var="error" scope="session" />
    <c:remove var="success" scope="session" />
  </body>
</html>
