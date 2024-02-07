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
    <script src="https://unpkg.com/@barba/core"></script>
    <script src="${contextPath}/resources/home.js"></script>
  </head>
  <body class="bg-neutral min-h-screen">
    <c:set var="role" value="${sessionScope.common.getUser().getRole().getId()}" />
    <div class="fixed inset-x-0 w-full bg-primary min-h-52 z-0"></div>
    <div class="drawer xl:drawer-open">
      <input id="my-drawer" type="checkbox" class="drawer-toggle" />
      <div class="drawer-content">
        <jsp:include page="header.jsp">
          <jsp:param name="title" value="Home" />
        </jsp:include>
        <div class="w-auto my-10">
          <section class="mx-4 flex flex-wrap items-start gap-5" aria-labelledby="profile-overview-title">
            <div
              class="overflow-hidden rounded-xl bg-gradient-to-tl from-secondary to-white shadow-lg basis-2/5 grow"
            >
              <div class="p-6">
                <div id="prfl1" class="sm:flex sm:items-center sm:justify-between">
                  <div id="prfl2" class="sm:flex sm:space-x-5">
                    <div class="flex-shrink-0">
                      <img
                        class="mx-auto size-20 rounded-full"
                        src="data:image/png;charset=utf-8;base64,${profilePicture}"
                        alt=""
                      />
                    </div>
                    <div id="prfl3" class="mt-4 text-center sm:mt-0 sm:pt-1 sm:text-left">
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
                      id="profileBtn"
                      class="btn btn-sm btn-primary rounded-full text-white"
                      >View Profile</a
                    >
                    <a
                      id="dashboardBtn"
                      class="btn btn-sm btn-primary rounded-full text-white hidden"
                      >View Dashboard</a
                    >
                  </div>
                </div>
              </div>
            </div>
            <div id="profile" class="card basis-2/4 bg-white shadow-xl grow hidden">
              <div class="card-body">
                <div class="card-title justify-between">
                  <h2>User Profile</h2>
                  <button
                    class="btn btn-sm rounded-full btn-primary prflBtn"
                    onclick="editProfile.showModal()"
                  >
                    Edit Profile
                  </button>
                </div>
                <p class="mt-4 font-bold">User Information</p>
                <div class="flex flex-wrap gap-3">
                  <label class="form-control basis-full">
                    <div class="label">
                      <span class="label-text font-semibold text-primary">Full Name</span>
                    </div>
                    <input
                      type="text"
                      class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                      value="${userProfile.getFullname()}"
                      disabled
                    />
                  </label>
                  <label class="form-control basis-2/5">
                    <div class="label">
                      <span class="label-text font-semibold text-primary">Username</span>
                    </div>
                    <input
                      type="text"
                      class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                      value="${userProfile.getUsername()}"
                      disabled
                    />
                  </label>
                  <label class="form-control basis-2/5">
                    <div class="label">
                      <span class="label-text font-semibold text-primary">E-Mail</span>
                    </div>
                    <input
                      type="text"
                      class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                      value="${userProfile.getEmail()}"
                      disabled
                    />
                  </label>
                  <label class="form-control basis-2/5">
                    <div class="label">
                      <span class="label-text font-semibold text-primary">Gender</span>
                    </div>
                    <input
                      type="text"
                      class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                      value="${userProfile.getGender()}"
                      disabled
                    />
                  </label>
                  <label class="form-control basis-2/5">
                    <div class="label">
                      <span class="label-text font-semibold text-primary">Birthdate</span>
                    </div>
                    <input
                      type="text"
                      class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                      value="${userProfile.getFormatBirthDate()}"
                      disabled
                    />
                  </label>
                  <c:if test="${role == 3}">
                  <!-- if user a student -->
                  <div class="basis-full flex flex-wrap gap-3">
                    <p class="mt-4 font-bold basis-full">
                      Student Information
                    </p>
                    <label class="form-control basis-2/5">
                      <div class="label">
                        <span class="label-text font-semibold text-primary">Student ID</span>
                      </div>
                      <input
                        type="text"
                        class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                        value="${studentProfile.getStud_id()}"
                        disabled
                      />
                    </label>
                    <label class="form-control basis-2/5">
                      <div class="label">
                        <span class="label-text font-semibold text-primary">Program</span>
                      </div>
                      <input
                        type="text"
                        class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                        value="${studentProfile.getProgram()}"
                        disabled
                      />
                    </label>
                    <label class="form-control basis-2/5">
                      <div class="label">
                        <span class="label-text font-semibold text-primary">Intake</span>
                      </div>
                      <input
                        type="text"
                        class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                        value="${studentProfile.getIntake()}"
                        disabled
                      />
                    </label>
                    <label class="form-control basis-2/5">
                      <div class="label">
                        <span class="label-text font-semibold text-primary">Semester</span>
                      </div>
                      <input
                        type="text"
                        class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                        value="${studentProfile.getSemester()}"
                        disabled
                      />
                    </label>
                  </div>
                  </c:if>
                  <c:if test="${role == 2}">
                  <script src="https://cdnjs.cloudflare.com/ajax/libs/qrious/4.0.2/qrious.min.js"></script>
                  <!-- if user a lecturer -->
                  <div class="basis-full flex flex-wrap gap-3">
                    <p class="mt-4 font-bold basis-full">
                      Lecturer Information
                    </p>
                    <label class="form-control basis-2/5">
                      <div class="label">
                        <span class="label-text font-semibold text-primary">Lecturer ID</span>
                      </div>
                      <input
                        type="text"
                        class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                        value="${lecturerProfile.getLect_id()}"
                        disabled
                      />
                    </label>
                    <label class="form-control basis-2/5">
                      <div class="label">
                        <span class="label-text font-semibold text-primary">Supervisor Name</span>
                      </div>
                      <input
                        type="text"
                        class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                        value="${lecturerProfile.getSupervisor().getFullname()}"
                        disabled
                      />
                    </label>
                    <label class="form-control basis-2/5">
                      <div class="label">
                        <span class="label-text font-semibold text-primary">Start Date</span>
                      </div>
                      <input
                        type="text"
                        class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                        value="${lecturerProfile.getFormatStartDate()}"
                        disabled
                      />
                    </label>
                    <label class="form-control basis-2/5">
                      <div class="label">
                        <span class="label-text font-semibold text-primary">Qualification</span>
                      </div>
                      <input
                        type="text"
                        class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                        value="${lecturerProfile.getQualification()}"
                        disabled
                      />
                    </label>
                    <label class="form-control basis-2/5">
                      <div class="label">
                        <span class="label-text font-semibold text-primary">Salary</span>
                      </div>
                      <input
                        type="text"
                        class="input input-sm input-ghost cursor-not-allowed disabled:bg-white disabled:border-transparent disabled:text-slate-800"
                        value="${lecturerProfile.getSalary()}"
                        disabled
                      />
                    </label>
                  </div>
                  </c:if>
                </div>
              </div>
            </div>
          </section>
          <div
            id="dashboard"
            class="flex flex-wrap mt-4 mx-4 gap-5 items-start transition-all"
          >
            <c:forEach var="active" items="${activeList}">
              <c:choose>
                <c:when test="${role == 3}">
                  <a href="/qrscan" class="basis-full">
                    <div
                      id="clsActive"
                      class="card ${active.getCourse().getColorConfig()} shadow-lg overflow-hidden"
                    >
                      <div class="card-body min-w-full">
                        <h1 class="card-title z-10">Active Class</h1>
                        <div class="flex flex-row justify-between z-10">
                          <div class="flex flex-col">
                            <p class="text-3xl font-bold">
                              ${active.getCourse().getCourse_code()}
                            </p>
                            <p class="text-xl">
                              ${active.getCourse().getCourse_name()}
                            </p>
                            <p class="text-sm">
                              ${active.getFormattedClassDate()} |
                              ${active.getFormStartTime()} -
                              ${active.getFormEndTime()}
                            </p>
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
                </c:when>
                <c:when test="${role == 2}">
                  <a class="basis-full" onclick="generateQR('${active.getId()}')">
                    <div
                      id="clsActive"
                      class="card ${active.getCourse().getColorConfig()} shadow-lg overflow-hidden"
                    >
                      <div class="card-body min-w-full">
                        <h1 class="card-title z-10">Active Class</h1>
                        <div class="flex flex-row justify-between z-10">
                          <div class="flex flex-col">
                            <p class="text-3xl font-bold">
                              ${active.getCourse().getCourse_code()}
                            </p>
                            <p class="text-xl">
                              ${active.getCourse().getCourse_name()}
                            </p>
                            <p class="text-sm">
                              ${active.getFormattedClassDate()} |
                              ${active.getFormStartTime()} -
                              ${active.getFormEndTime()}
                            </p>
                          </div>
                          <div class="flex flex-col">
                            <button
                              class="btn btn-success rounded-full z-10 text-white qr"
                              data-id="${active.getId()}"
                            >
                              Attendance QR
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
                </c:when>
              </c:choose>
            </c:forEach>
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
                      <c:forEach var="today" items="${todayList}">
                        <tr>
                          <td
                            class="w-full max-w-0 py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:w-auto sm:max-w-none sm:pl-0"
                          >
                            ${today.getCourse().getCourse_code()}
                            <dl class="font-normal lg:hidden">
                              <dd class="mt-1 truncate text-gray-700">
                                ${today.getCourse().getCourse_name()}
                              </dd>
                              <dd class="mt-1 truncate text-gray-500 sm:hidden">
                                ${today.getFormattedClassDate()}
                              </dd>
                            </dl>
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 lg:table-cell"
                          >
                            ${today.getCourse().getCourse_name()}
                          </td>
                          <td
                            class="hidden px-3 py-4 text-sm text-gray-500 sm:table-cell"
                          >
                            ${today.getFormattedClassDate()}
                          </td>
                          <td
                            class="px-3 py-4 text-nowrap text-right text-sm text-gray-500"
                          >
                            ${today.getFormStartTime()}
                          </td>
                          <td
                            class="py-4 pr-4 text-nowrap text-left text-sm text-gray-500 pl-0"
                          >
                            ${today.getFormEndTime()}
                          </td>
                        </tr>
                      </c:forEach>
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
    <dialog id="editProfile" class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">Edit User</h3>
        <form action="/user/profile" method="post" enctype="multipart/form-data">
          <div class="flex flex-wrap gap-3">
            <label class="form-control basis-full">
              <div class="label">
                <span class="label-text">Full Name</span>
              </div>
              <input
                type="text"
                name="fullname"
                id="fullname"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Username</span>
              </div>
              <input
                type="text"
                name="username"
                id="username"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Gender</span>
              </div>
              <select
                name="gender"
                id="gender"
                class="select select-primary select-sm select-bordered"
              >
                <option value="M">Male</option>
                <option value="F">Female</option>
              </select>
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Birthdate</span>
              </div>
              <input
                type="date"
                name="birthdate"
                id="birthdate"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-1/4 grow">
              <div class="label">
                <span class="label-text">Email</span>
              </div>
              <input
                type="email"
                name="email"
                id="email"
                class="input input-primary input-sm input-bordered"
              />
            </label>
            <label class="form-control basis-full grow">
              <div class="label">
                <span class="label-text">Profile Picture</span>
              </div>
              <input
                type="file"
                name="dpImage"
                id="dpImage"
                class="file-input file-input-primary file-input-sm file-input-bordered file-input-primary"
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
    <c:remove var="error" scope="session" />
    <c:remove var="success" scope="session" />
  </body>
</html>
