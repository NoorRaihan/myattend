<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="h-full bg-white" data-theme="blue">
  <head>
    <title>Login - myAttend+</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="${contextPath}/resources/output.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
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
                      class="input input-primary input-sm w-full"
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
                      class="input input-primary input-sm w-full"
                    />
                  </div>
                </div>
                <div class="flex items-center justify-between">
                  <div class="flex items-center">
                    <input
                      id="remember_me"
                      name="remember_me"
                      type="checkbox"
                      class="checkbox checkbox-primary"
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
                      class="font-semibold text-primary"
                      >Forgot password ?</a
                    >
                  </div>
                </div>

                <div>
                  <button
                    type="submit"
                    value="Login"
                    class="btn btn-wide btn-primary rounded-full w-full"
                  >
                    Login
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
        <div id="alert" class="toast toast-top toast-start z-50 hidden">
          <div class="alert alert-error text-white">
            <span id="alertMsg">Error</span>
          </div>
        </div>
      </div>
      <div class="relative hidden w-0 flex-1 lg:block">
        <img
          class="absolute inset-0 h-full w-full object-cover"
          src="https://miro.medium.com/v2/resize:fit:2000/0*EpckQb9WE2b6kWWI"
          alt="banner"
        />
      </div>
    </div>
  </body>
  <script>
    let alert = "${error}";
    if (alert != "") {
      $("#alertMsg").html(alert);
      $("#alert").show().delay(3000).fadeOut();
    }
  </script>
</html>
