<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Course Management - myAttend+</title>
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/theme-change@2.5.0/index.js"></script>
    <script src="${contextPath}/resources/course.js"></script>
  </head>
  <body class="bg-neutral min-h-screen">
    <div class="fixed inset-x-0 w-full bg-primary min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <div class="drawer drawer-end">
          <input id="detail-drawer" type="checkbox" class="drawer-toggle" />
          <div class="drawer-content">
            <jsp:include page="../Home/header.jsp">
              <jsp:param
                name="menu"
                value="<li class='sm:hidden'>Admin Menu</li><li>Course Management</li>"
              />
              <jsp:param name="title" value="Course Management" />
            </jsp:include>
            <div class="w-auto my-10">
              <div
                class="flex flex-wrap-reverse mx-4 gap-5 items-end transition-all"
              >
                <div class="basis-3/5 grow">
                  <div class="card card-compact bg-base-100 shadow-xl">
                    <div class="card-body">
                      <h2 class="card-title">Courses List</h2>
                      <div class="-mx-4 sm:-mx-0">
                        <table class="min-w-full divide-y divide-gray-300">
                          <thead>
                            <tr>
                              <th
                                scope="col"
                                class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-0"
                              >
                                Course Name
                              </th>
                              <th
                                scope="col"
                                class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                              >
                                Course Code
                              </th>
                              <th
                                scope="col"
                                class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                              >
                                Assigned Lecturer
                              </th>
                              <th
                                scope="col"
                                class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                              >
                                Credit Hours
                              </th>
                              <th
                                scope="col"
                                class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 sm:table-cell"
                              >
                                Color
                              </th>
                              <th
                                scope="col"
                                class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
                              >
                                Status
                              </th>
                              <th
                                scope="col"
                                class="relative py-3.5 pl-3 pr-4 sm:pr-0"
                              >
                                <span class="sr-only">Edit</span>
                              </th>
                            </tr>
                          </thead>
                          <tbody class="divide-y divide-gray-200 bg-white">
                            <c:forEach var="course" items="${courses}">
                              <tr>
                                <td
                                  class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
                                >
                                  ${course.getCourse_name()}
                                  <dl class="font-normal lg:hidden">
                                    <dd class="mt-1 truncate text-gray-700">
                                      ${course.getCourse_code()}
                                    </dd>
                                    <dd
                                      class="mt-1 truncate text-gray-500 sm:hidden"
                                    >
                                      ${course.getCredit_hour()}
                                    </dd>
                                  </dl>
                                </td>
                                <td
                                  class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                                >
                                  ${course.getCourse_code()}
                                </td>
                                <td
                                  class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                                >
                                  ${course.getUser().getFullname()}
                                </td>
                                <td
                                  class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                                >
                                  ${course.getCredit_hour()}
                                </td>
                                <td
                                  class="hidden px-3 py-4 text-sm text-gray-500 sm:table-cell"
                                >
                                  <div class="avatar placeholder">
                                    <div
                                      class="${course.getColorConfig()} rounded-full w-6"
                                    ></div>
                                  </div>
                                </td>
                                <td
                                  class="px-3 py-4 text-right text-sm text-gray-500"
                                >
                                  <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    viewBox="0 0 24 24"
                                    fill="currentColor"
                                    class="size-8 fill-blue-500"
                                  >
                                    <path
                                      fill-rule="evenodd"
                                      d="M8.603 3.799A4.49 4.49 0 0 1 12 2.25c1.357 0 2.573.6 3.397 1.549a4.49 4.49 0 0 1 3.498 1.307 4.491 4.491 0 0 1 1.307 3.497A4.49 4.49 0 0 1 21.75 12a4.49 4.49 0 0 1-1.549 3.397 4.491 4.491 0 0 1-1.307 3.497 4.491 4.491 0 0 1-3.497 1.307A4.49 4.49 0 0 1 12 21.75a4.49 4.49 0 0 1-3.397-1.549 4.49 4.49 0 0 1-3.498-1.306 4.491 4.491 0 0 1-1.307-3.498A4.49 4.49 0 0 1 2.25 12c0-1.357.6-2.573 1.549-3.397a4.49 4.49 0 0 1 1.307-3.497 4.49 4.49 0 0 1 3.497-1.307Zm7.007 6.387a.75.75 0 1 0-1.22-.872l-3.236 4.53L9.53 12.22a.75.75 0 0 0-1.06 1.06l2.25 2.25a.75.75 0 0 0 1.14-.094l3.75-5.25Z"
                                      clip-rule="evenodd"
                                    />
                                  </svg>
                                </td>
                                <td
                                  class="py-4 pl-3 pr-4 text-center text-sm font-medium sm:pr-0"
                                >
                                  <div
                                    class="dropdown dropdown-left dropdown-hover"
                                  >
                                    <div
                                      tabindex="0"
                                      role="button"
                                      class="btn btn-ghost btn-xs"
                                    >
                                      <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        viewBox="0 0 24 24"
                                        fill="currentColor"
                                        class="size-6"
                                      >
                                        <path
                                          fill-rule="evenodd"
                                          d="M10.5 6a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0Zm0 6a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0Zm0 6a1.5 1.5 0 1 1 3 0 1.5 1.5 0 0 1-3 0Z"
                                          clip-rule="evenodd"
                                        />
                                      </svg>
                                    </div>
                                    <ul
                                      tabindex="0"
                                      class="dropdown-content z-[1] menu p-2 shadow-lg bg-neutral rounded-box w-fit"
                                    >
                                      <li>
                                        <label
                                          for="detail-drawer"
                                          data-id="${course.getId()}"
                                          class="dtl"
                                          >Details</label
                                        >
                                      </li>
                                      <li>
                                        <a
                                          onclick="editCourse.showModal()"
                                          data-id="${course.getId()}"
                                          class="edit"
                                          >Edit</a
                                        >
                                      </li>
                                      <li>
                                        <a
                                          onclick="disableCourse.showModal()"
                                          class="text-red-600 disable"
                                          data-id="${course.getId()}"
                                          >Disable</a
                                        >
                                      </li>
                                    </ul>
                                  </div>
                                </td>
                              </tr>
                              <!-- More people... -->
                            </c:forEach>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="basis-1/5 grow">
                  <div class="card card-compact bg-base-100 shadow-xl">
                    <div
                      class="card-body flex md:flex-col justify-between flex-row"
                    >
                      <div
                        class="stat place-items-center basis-1/4 sm:basis-full"
                      >
                        <div class="stat-title">Total Courses</div>
                        <div class="stat-value text-primary">
                          ${totalCourse}
                        </div>
                        <div class="stat-desc">myAttend+</div>
                      </div>
                      <div class="flex justify-center">
                        <button
                          class="btn btn-primary rounded-full my-auto"
                          onclick="newCourse.showModal(); courseAdd.reset();"
                        >
                          Add New Course
                        </button>
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
          <div class="drawer-side z-50 overflow-x-hidden">
            <label
              for="detail-drawer"
              aria-label="close sidebar"
              class="drawer-overlay"
            ></label>
            <div
              class="p-4 w-screen max-w-md min-h-full bg-white text-base-content"
            >
              <h2 class="text-base font-semibold">Course Details</h2>
              <div class="relative h-40 -mx-4 mt-4 overflow-hidden">
                <div
                  class="absolute h-full w-full bg-gradient-to-br from-green-300 to-emerald-500 -z-10"
                ></div>
                <div class="absolute -inset-y-16 -right-10">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    fill="currentColor"
                    class="size-72 fill-slate-200"
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
                <div class="absolute p-4 bottom-0 left-0">
                  <p id="courseName" class="text-lg font-semibold">Course 12</p>
                  <p id="courseCode" class="text-base">CSC691</p>
                  <p id="courseLecturer" class="text-sm italic">Lecturer 1</p>
                </div>
              </div>
              <div role="tablist" class="mt-3 tabs tabs-boxed w-full">
                <input
                  type="radio"
                  name="my_tabs_1"
                  role="tab"
                  class="tab"
                  aria-label="Classes"
                  checked
                />
                <div role="tabpanel" id="classList" class="tab-content">
                  <ul
                    role="list"
                    class="flex-1 divide-y divide-gray-200 overflow-y-auto"
                  >
                    <li>
                      <div class="group relative flex items-center px-5 py-6">
                        <div class="-m-1 block flex-1 p-1">
                          <div
                            class="absolute inset-0 group-hover:bg-neutral"
                            aria-hidden="true"
                          ></div>
                          <div
                            class="relative flex min-w-0 flex-1 items-center"
                          >
                            <div class="ml-4 truncate">
                              <p
                                class="truncate text-sm font-medium text-gray-900"
                              >
                                Class ID
                              </p>
                              <p class="truncate text-sm text-gray-500">
                                17 Jan 2024
                              </p>
                              <p class="truncate text-sm text-gray-500">
                                10:00 AM - 11:00 AM
                              </p>
                            </div>
                          </div>
                        </div>
                      </div>
                    </li>

                    <!-- More classes... -->
                  </ul>
                </div>

                <input
                  type="radio"
                  name="my_tabs_1"
                  role="tab"
                  class="tab"
                  aria-label="Registered Students"
                />
                <div role="tabpanel" id="studentList" class="tab-content">
                  <ul
                    role="list"
                    class="flex-1 divide-y divide-gray-200 overflow-y-auto"
                  >
                    <li>
                      <div class="group relative flex items-center px-5 py-6">
                        <div class="-m-1 block flex-1 p-1">
                          <div
                            class="absolute inset-0 group-hover:bg-neutral"
                            aria-hidden="true"
                          ></div>
                          <div
                            class="relative flex min-w-0 flex-1 items-center"
                          >
                            <!--<span
                              class="relative inline-block flex-shrink-0"
                            >
                              <img
                                class="h-10 w-10 rounded-full"
                                src="${contextPath}/resources/img/MJJ.png"
                                alt=""
                              />
                            </span>-->
                            <div class="ml-4 truncate">
                              <p
                                class="truncate text-sm font-medium text-gray-900"
                              >
                                Student Name
                              </p>
                              <p class="truncate text-sm text-gray-500">
                                Student ID
                              </p>
                            </div>
                          </div>
                        </div>
                        <div
                          class="relative ml-2 inline-block flex-shrink-0 text-left"
                        >
                          <button
                            type="button"
                            class="group relative inline-flex h-8 w-8 items-center justify-center rounded-full bg-white focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 rmStud"
                            onclick="removeStudent.showModal()"
                            data-id="1"
                          >
                            <span class="absolute -inset-1.5"></span>
                            <span
                              class="flex h-full w-full items-center justify-center rounded-full"
                            >
                              <svg
                                xmlns="http://www.w3.org/2000/svg"
                                viewBox="0 0 24 24"
                                fill="currentColor"
                                class="w-6 h-6 text-red-500"
                              >
                                <path
                                  fill-rule="evenodd"
                                  d="M16.5 4.478v.227a48.816 48.816 0 0 1 3.878.512.75.75 0 1 1-.256 1.478l-.209-.035-1.005 13.07a3 3 0 0 1-2.991 2.77H8.084a3 3 0 0 1-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 0 1-.256-1.478A48.567 48.567 0 0 1 7.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 0 1 3.369 0c1.603.051 2.815 1.387 2.815 2.951Zm-6.136-1.452a51.196 51.196 0 0 1 3.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 0 0-6 0v-.113c0-.794.609-1.428 1.364-1.452Zm-.355 5.945a.75.75 0 1 0-1.5.058l.347 9a.75.75 0 1 0 1.499-.058l-.346-9Zm5.48.058a.75.75 0 1 0-1.498-.058l-.347 9a.75.75 0 0 0 1.5.058l.345-9Z"
                                  clip-rule="evenodd"
                                />
                              </svg>
                            </span>
                          </button>
                        </div>
                      </div>
                    </li>

                    <!-- More people... -->
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <%@ include file="../Home/drawer.jsp" %>
    </div>

    <dialog id="newCourse" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Add New Course</h3>
        <form id="courseAdd" action="/course/create" method="post">
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-full">
              <div class="label">
                <span class="label-text">Course Name</span>
              </div>
              <input
                type="text"
                name="c_name"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Course Code</span>
              </div>
              <input
                type="text"
                name="c_code"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Assigned Lecturer</span>
              </div>
              <select
                name="c_lect"
                id="c_lect"
                class="select select-primary select-sm select-bordered"
              >
                <option disabled selected>Pick one</option>
                <c:forEach var="lecturer" items="${lecturersAll}">
                  <option value="${lecturer.getUser().getId()}">
                    ${lecturer.getUser().getFullname()}
                  </option>
                </c:forEach>
              </select>
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Credit Hours</span>
              </div>
              <input
                type="number"
                name="c_credit"
                class="input input-primary input-sm input-bordered"
                step="0.1"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Color</span>
              </div>
              <select
                name="c_color"
                id="c_color"
                class="select select-primary select-sm select-bordered"
              >
                <option disabled selected>Pick one</option>
                <option value="GREEN1">Green</option>
                <option value="LIME1">Light Green</option>
                <option value="PURPLE1">Purple</option>
                <option value="BLUE1">Blue</option>
                <option value="CYAN1">Light Blue</option>
                <option value="FUCHSIA1">Fuchsia</option>
                <option value="PINK1">Pink</option>
                <option value="YELLOW1">Yellow</option>
                <option value="ORANGE1">Orange</option>
                <option value="RED1">Red</option>
              </select>
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
    <dialog id="editCourse" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Edit Course</h3>
        <form id="courseAdd" action="/course/update" method="post">
          <input type="hidden" name="id" id="c_id" value="1" />
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-full">
              <div class="label">
                <span class="label-text">Course Name</span>
              </div>
              <input
                type="text"
                name="c_name"
                id="c_name"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Course Code</span>
              </div>
              <input
                type="text"
                name="c_code"
                id="c_code"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Assigned Lecturer</span>
              </div>
              <select
                name="c_lect"
                id="c_lectedt"
                class="select select-primary select-sm select-bordered"
              >
                <option disabled selected>Pick one</option>
                <c:forEach var="lecturer" items="${lecturersAll}">
                  <option value="${lecturer.getUser().getId()}">
                    ${lecturer.getUser().getFullname()}
                  </option>
                </c:forEach>
              </select>
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Credit Hours</span>
              </div>
              <input
                type="number"
                name="c_credit"
                id="c_credit"
                class="input input-primary input-sm input-bordered"
                step="0.1"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Color</span>
              </div>
              <select
                name="c_color"
                id="c_coloredt"
                class="select select-primary select-sm select-bordered"
              >
                <option disabled selected>Pick one</option>
                <option value="GREEN1">Green</option>
                <option value="LIME1">Light Green</option>
                <option value="PURPLE1">Purple</option>
                <option value="BLUE1">Blue</option>
                <option value="CYAN1">Light Blue</option>
                <option value="FUCHSIA1">Fuchsia</option>
                <option value="PINK1">Pink</option>
                <option value="YELLOW1">Yellow</option>
                <option value="ORANGE1">Orange</option>
                <option value="RED1">Red</option>
              </select>
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
    <dialog id="disableCourse" class="modal">
      <div class="modal-box text-center">
        <h3 class="font-bold text-lg">Disable Course</h3>
        <p class="py-4">Do you really want to disable this course ?</p>
        <div class="modal-action justify-between">
          <form action="#" method="post">
            <input type="hidden" name="c_id" id="c_id" value="1" />
            <button type="submit" class="btn btn-sm btn-ghost text-red-600">
              Delete Instead
            </button>
          </form>
          <form action="#" method="post">
            <input type="hidden" name="c_id" id="c_id" value="1" />
            <button type="submit" class="btn btn-sm btn-error text-white">
              Yes
            </button>
          </form>
        </div>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>
    <dialog id="enableCourse" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Enable Course</h3>
        <p class="py-4">Do you really want to enable this course ?</p>
        <form action="/course/enable" method="post">
          <input type="hidden" name="id" id="enable_id" value="1" />
          <div class="modal-action">
            <button type="submit" class="btn btn-sm btn-success text-white">
              Yes
            </button>
          </div>
        </form>
      </div>
      <form method="dialog" class="modal-backdrop">
        <button>close</button>
      </form>
    </dialog>
    <dialog id="removeStudent" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Remove Student</h3>
        <p class="py-4">
          Do you really want to remove this student form this course ?
        </p>
        <form action="#" method="post">
          <input type="hidden" name="s_id" id="s_id" value="1" />
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
    <c:remove var="error" scope="session" />
    <c:remove var="success" scope="session" />
  </body>
</html>
