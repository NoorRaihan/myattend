<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="h-full bg-white" data-theme="light">
  <head>
    <title>Login - myAttend+</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
  </head>
  <body class="h-full">
    <div class="flex min-h-full">
      <div
        class="flex flex-1 flex-col justify-center px-4 py-12 sm:px-6 lg:flex-none lg:px-20 xl:px-24"
      >
        <div class="mx-auto w-full max-w-sm lg:w-96">
          <div>
            <img
              class="h-10 w-auto"
              src="https://seeklogo.com/images/U/UITM-logo-20DC2B3831-seeklogo.com.png"
              alt="UiTM"
            />
            <h2
              class="mt-8 text-2xl font-bold leading-7 tracking-tight text-gray-900"
            >
              Welcome to myAttend+
            </h2>
            <p class="mt-2 text-sm leading-6 text-gray-500">
              Please login to continue
            </p>
          </div>

          <div class="mt-10">
            <div>
              <form action="login" method="POST" class="space-y-6">
                <div>
                  <label
                    for="username"
                    class="block text-sm font-medium leading-6 text-gray-900"
                    >E-Mail</label
                  >
                  <div class="mt-2">
                    <input
                      id="username"
                      name="username"
                      type="email"
                      autocomplete="true"
                      required
                      class="block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 text-center form-control <?php echo (!empty($username_err)) ? 'is-invalid' : ''; ?>"
                    />
                  </div>
                </div>

                <div>
                  <label
                    for="password"
                    class="block text-sm font-medium leading-6 text-gray-900"
                    >Password</label
                  >
                  <div class="mt-2">
                    <input
                      id="password"
                      name="password"
                      type="password"
                      autocomplete="current-password"
                      required
                      class="block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6 text-center form-control <?php echo (!empty($password_err)) ? 'is-invalid' : ''; ?>"
                    />
                  </div>
                </div>
                <span class="mt-2 text-sm leading-3 text-red-500"
                  >${error}</span
                >
                <div class="flex items-center justify-between">
                  <div class="flex items-center">
                    <input
                      id="remember_me"
                      name="remember_me"
                      type="checkbox"
                      class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-600"
                    />
                    <label
                      for="remember_me"
                      class="ml-3 block text-sm leading-6 text-gray-700"
                      >Remember me</label
                    >
                  </div>

                  <div class="text-sm leading-6">
                    <a
                      href="/resetPass"
                      class="font-semibold text-indigo-600 hover:text-indigo-500"
                      >Forgot password ?</a
                    >
                  </div>
                </div>

                <div>
                  <button
                    type="submit"
                    value="Login"
                    class="flex w-full justify-center rounded-full bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                  >
                    Login
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div class="relative hidden w-0 flex-1 lg:block">
        <img
          class="absolute inset-0 h-full w-full object-cover"
          src="https://media.thevibes.com/images/uploads/covers/_large/uitm_bernama_pic_18062021.PNG"
          alt="UiTM"
        />
      </div>
    </div>
  </body>
</html>
