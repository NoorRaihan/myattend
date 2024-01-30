<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Home - myAttend+</title>
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/qrious/4.0.2/qrious.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/theme-change@2.5.0/index.js"></script>
  </head>
  <body class="bg-neutral min-h-screen">
    <div class="fixed inset-x-0 w-full bg-primary min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <jsp:include page="../Home/header.jsp">
          <jsp:param
            name="menu"
            value="<li class='sm:hidden'>Lecturer</li><li>Class Management</li>"
          />
          <jsp:param name="title" value="Course Management" />
        </jsp:include>
        <div class="w-auto my-10">
          <section
            class="flex ml-2 overflow-x-auto scrollbar-hide"
            aria-labelledby="profile-overview-title"
          >
            <div
              class="mx-2 card bg-gradient-to-br from-blue-300 to-blue-500 shadow-lg hover:shadow-xl overflow-hidden w-72 flex-shrink-0 cursor-pointer course"
              data-id="1"
            >
              <div class="card-body">
                <div class="flex flex-row justify-between z-10">
                  <div class="flex flex-col">
                    <p class="text-2xl font-bold">CSC584</p>
                    <p class="text-lg font-semibold">Enterprise Programming</p>
                    <p class="text-sm">3.0 Credit Hours</p>
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

            <div
              class="mx-2 card bg-gradient-to-br from-orange-300 to-orange-500 shadow-lg hover:shadow-xl overflow-hidden w-72 flex-shrink-0 cursor-pointer course"
              data-id="1"
            >
              <div class="card-body">
                <div class="flex flex-row justify-between z-10">
                  <div class="flex flex-col">
                    <p class="text-2xl font-bold">ICT502</p>
                    <p class="text-lg font-semibold">Database Engineering</p>
                    <p class="text-sm">2.0 Credit Hours</p>
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
                  <h1>Classes List</h1>
                  <button
                    class="btn btn-sm btn-primary"
                    onclick="newClass.showModal(); classAdd.reset();"
                  >
                    New Class
                  </button>
                </div>
                <div class="-mx-4 sm:-mx-0">
                  <table class="min-w-full divide-y divide-gray-300">
                    <thead>
                      <tr>
                        <th
                          scope="col"
                          class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-0"
                        >
                          Class
                        </th>
                        <th
                          scope="col"
                          class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                        >
                          Date
                        </th>
                        <th
                          scope="col"
                          class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 sm:table-cell"
                        >
                          Time
                        </th>
                        <th
                          scope="col"
                          class="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
                        >
                          Venue
                        </th>
                      </tr>
                    </thead>
                    <tbody
                      id="classList"
                      class="divide-y divide-gray-200 bg-white"
                    >
                      <tr>
                        <td
                          class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
                        >
                          Seminar 1
                          <dl class="font-normal lg:hidden">
                            <dd class="mt-1 truncate text-gray-700">
                              17 Jan 2024
                            </dd>
                            <dd class="mt-1 truncate text-gray-500 sm:hidden">
                              10:00 AM - 11:00 AM
                            </dd>
                          </dl>
                        </td>
                        <td
                          class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                        >
                          17 Jan 2024
                        </td>
                        <td
                          class="hidden px-3 py-4 text-sm text-gray-500 sm:table-cell"
                        >
                          10:00 AM - 11:00 AM
                        </td>
                        <td class="px-3 py-4 text-nowrap text-sm text-gray-500">
                          Dewan Hall 1
                        </td>
                        <td
                          class="py-4 pr-4 text-nowrap text-left text-sm text-gray-500 pl-0"
                        >
                          <div class="dropdown dropdown-left dropdown-hover">
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
                                  data-id=""
                                  class="viewQR"
                                  >Attendance QR</label
                                >
                              </li>
                              <li>
                                <a
                                  onclick="editClass.showModal()"
                                  data-id=""
                                  class="edit"
                                  >Edit</a
                                >
                              </li>
                              <li>
                                <a
                                  onclick="deleteClass.showModal()"
                                  class="text-red-600 disable"
                                  data-id=""
                                  >Delete</a
                                >
                              </li>
                            </ul>
                          </div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <div
              class="card card-compact md:basis-1/4 basis-full bg-white shadow-lg grow"
            >
              <div class="card-body">
                <h1 class="card-title">Student Attendance</h1>
                <div id="attendence">
                  <ul
                    role="list"
                    class="flex-1 divide-y divide-gray-200 overflow-y-auto"
                  >
                    <li>
                      <div class="group relative flex items-center px-5 py-6">
                        <div class="-m-1 block flex-1 p-1">
                          <div
                            class="absolute inset-0"
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
                            <div class="truncate">
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
                          class="relative ml-2 inline-block flex-shrink-0 tooltip"
                          data-tip="Present"
                        >
                          <svg
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 24 24"
                            fill="currentColor"
                            class="size-7 fill-green-500"
                          >
                            <path
                              fill-rule="evenodd"
                              d="M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12Zm13.36-1.814a.75.75 0 1 0-1.22-.872l-3.236 4.53L9.53 12.22a.75.75 0 0 0-1.06 1.06l2.25 2.25a.75.75 0 0 0 1.14-.094l3.75-5.25Z"
                              clip-rule="evenodd"
                            />
                          </svg>
                        </div>
                        <!--<div
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
                        </div>
                        <div
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
                        </div>-->
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
    <dialog id="newClass" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Add New Class</h3>
        <form id="classAdd" action="" method="post">
          <input type="hidden" name="c_id" id="c_id" value="1" />
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Date</span>
              </div>
              <input
                type="date"
                name="cls_date"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Start Time</span>
              </div>
              <input
                type="time"
                name="cls_sTime"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">End Time</span>
              </div>
              <input
                type="time"
                name="cls_eTime"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Venue</span>
              </div>
              <input
                type="text"
                name="cls_venue"
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
        <form id="classEdt" action="" method="post">
          <input type="hidden" name="c_id" id="c_id" value="1" />
          <input type="hidden" name="cls_id" id="cls_id" value="1" />
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Date</span>
              </div>
              <input
                type="date"
                id="cls_date"
                name="cls_date"
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
                name="cls_sTime"
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
                name="cls_eTime"
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
                name="cls_venue"
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
        <form action="" method="post">
          <input type="hidden" name="id" id="enable_id" value="1" />
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
