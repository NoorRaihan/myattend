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
    <script src="https://cdn.jsdelivr.net/npm/theme-change@2.5.0/index.js"></script>
  </head>
  <body class="bg-neutral min-h-screen">
    <div class="fixed inset-x-0 w-full bg-primary min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <jsp:include page="header.jsp">
          <jsp:param name="title" value="Home" />
        </jsp:include>
        <div class="w-auto my-10">
          <section class="mx-4" aria-labelledby="profile-overview-title">
            <div
              class="overflow-hidden rounded-xl bg-gradient-to-tl from-secondary to-white shadow-lg"
            >
              <div class="p-6">
                <div class="sm:flex sm:items-center sm:justify-between">
                  <div class="sm:flex sm:space-x-5">
                    <div class="flex-shrink-0">
                      <img
                        class="mx-auto size-20 rounded-full"
                        src="data:image/png;charset=utf-8;base64,${profilePicture}"
                        alt=""
                      />
                    </div>
                    <div class="mt-4 text-center sm:mt-0 sm:pt-1 sm:text-left">
                      <p class="text-sm font-medium text-gray-600">Welcome,</p>
                      <p class="text-xl font-bold text-gray-900 sm:text-2xl">
                        ${userFullname}
                      </p>
                      <p class="text-sm font-medium capitalize text-gray-600">
                        ${userRolename}
                      </p>
                    </div>
                  </div>
                  <div class="mt-5 flex justify-center sm:mt-0">
                    <a
                      href="#"
                      class="btn btn-sm btn-primary rounded-full text-white"
                      >Edit Profile</a
                    >
                  </div>
                </div>
              </div>
            </div>
          </section>
          <div
            class="flex flex-wrap mt-4 mx-4 gap-5 items-start transition-all"
          >
            <a href="/qrscan" class="basis-full">
              <div
                id="clsActive"
                class="card bg-gradient-to-br from-green-300 to-emerald-500 shadow-lg overflow-hidden"
              >
                <div class="card-body min-w-full">
                  <h1 class="card-title z-10">Active Class</h1>
                  <div class="flex flex-row justify-between z-10">
                    <div class="flex flex-col">
                      <p class="text-3xl font-bold">CSC584</p>
                      <p class="text-xl">Enterprise Programming</p>
                      <p class="text-sm">17 Jan 2024 | 10:00 AM - 11:00 AM</p>
                    </div>
                    <div class="flex flex-col">
                      <button
                        class="btn btn-success rounded-full z-10 text-white"
                      >
                        Register Attendance
                      </button>
                    </div>
                  </div>
                  <div class="absolute -inset-y-16 -right-10 sm:right-20">
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
                </div>
              </div>
            </a>
            <div
              id="clsList"
              class="card card-compact md:basis-2/4 basis-full bg-white shadow-lg grow transition-all"
            >
              <div class="card-body">
                <h1 class="card-title">Classes List</h1>
                <div class="-mx-4 sm:-mx-0">
                  <table class="min-w-full divide-y divide-gray-300">
                    <thead>
                      <tr>
                        <th
                          scope="col"
                          class="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-0"
                        >
                          Course Code
                        </th>
                        <th
                          scope="col"
                          class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 lg:table-cell"
                        >
                          Course Name
                        </th>
                        <th
                          scope="col"
                          class="hidden px-3 py-3.5 text-left text-sm font-semibold text-gray-900 sm:table-cell"
                        >
                          Date
                        </th>
                        <th
                          scope="col"
                          colspan="2"
                          class="px-3 py-3.5 text-center text-sm font-semibold text-gray-900"
                        >
                          Time
                        </th>
                      </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200 bg-white">
                      <tr>
                        <td
                          class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
                        >
                          CSC584
                          <dl class="font-normal lg:hidden">
                            <dd class="mt-1 truncate text-gray-700">
                              Enterprise Programming
                            </dd>
                            <dd class="mt-1 truncate text-gray-500 sm:hidden">
                              17 Jan 2024
                            </dd>
                          </dl>
                        </td>
                        <td
                          class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                        >
                          Enterprise Programming
                        </td>
                        <td
                          class="hidden px-3 py-4 text-sm text-gray-500 sm:table-cell"
                        >
                          17 Jan 2024
                        </td>
                        <td
                          class="px-3 py-4 text-nowrap text-right text-sm text-gray-500"
                        >
                          10:00 AM
                        </td>
                        <td
                          class="py-4 pr-4 text-nowrap text-left text-sm text-gray-500 pl-0"
                        >
                          11:00 AM
                        </td>
                      </tr>

                      <tr>
                        <td
                          class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
                        >
                          ICT502
                          <dl class="font-normal lg:hidden">
                            <dd class="mt-1 truncate text-gray-700">
                              Database Engineering
                            </dd>
                            <dd class="mt-1 truncate text-gray-500 sm:hidden">
                              17 Jan 2024
                            </dd>
                          </dl>
                        </td>
                        <td
                          class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                        >
                          Database Engineering
                        </td>
                        <td
                          class="hidden px-3 py-4 text-sm text-gray-500 sm:table-cell"
                        >
                          17 Jan 2024
                        </td>
                        <td
                          class="px-3 py-4 text-nowrap text-right text-sm text-gray-500"
                        >
                          11:00 AM
                        </td>
                        <td
                          class="py-4 pr-4 text-nowrap text-left text-sm text-gray-500 pl-0"
                        >
                          12:00 PM
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <div
              id="clsPerf"
              class="card card-compact md:basis-1/4 basis-full bg-white shadow-lg grow"
            >
              <div class="card-body">
                <h1 class="card-title">Attendance Perfomance</h1>
                <div class="flex flex-wrap">
                  <div class="flex flex-row min-w-fit justify-center mt-5 mx-4">
                    <div
                      class="radial-progress text-info"
                      style="--value: 75"
                      role="progressbar"
                    >
                      75%
                    </div>
                    <div class="flex flex-col ml-4">
                      <p class="text-xl font-bold">CSC584</p>
                      <p class="text-sm">Enterprise Programming</p>
                    </div>
                  </div>
                  <div class="flex flex-row min-w-fit justify-center mt-5 mx-4">
                    <div
                      class="radial-progress text-warning"
                      style="--value: 50"
                      role="progressbar"
                    >
                      50%
                    </div>
                    <div class="flex flex-col ml-4">
                      <p class="text-xl font-bold">ICT502</p>
                      <p class="text-sm">Database Engineering</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <%@ include file="drawer.jsp" %>
    </div>
    <c:remove var="error" scope="session" />
    <c:remove var="success" scope="session" />
  </body>
</html>
