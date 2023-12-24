<%@ page isELIgnored="false" language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" session="true" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - myAttend+</title>
    <link href="${contextPath}/resources/output.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
<div class="absolute w-full bg-blue-500 dark:hidden min-h-75"></div>
<div class="drawer lg:drawer-open">
  <input id="my-drawer" type="checkbox" class="drawer-toggle" />
  <div class="drawer-content">
    <?php include "src/hdr.php"; ?>

    <div id="tab-buttons" class="fixed w-11/12 h-16 max-w-lg -translate-x-1/2 bg-white border border-gray-200 rounded-full bottom-4 left-1/2 drop-shadow-xl backdrop-blur-md">
        <div class="grid h-full max-w-lg grid-cols-3 mx-auto">
            <a href="javascript:void(0)" role="button" onclick="changeTab(event,0)" class="inline-flex flex-col items-center justify-center px-5 rounded-l-full hover:bg-gray-50">
                <svg class="w-5 h-5 mb-1 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                    <path d="m19.707 9.293-2-2-7-7a1 1 0 0 0-1.414 0l-7 7-2 2a1 1 0 0 0 1.414 1.414L2 10.414V18a2 2 0 0 0 2 2h3a1 1 0 0 0 1-1v-4a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v4a1 1 0 0 0 1 1h3a2 2 0 0 0 2-2v-7.586l.293.293a1 1 0 0 0 1.414-1.414Z"/>
                </svg>
                <span class="text-xs">Utama</span>
            </a>
            <div class="flex items-center justify-center">
                <a role="button" data-tooltip-target="navbar-mid" href="scan.php" class="inline-flex items-center justify-center w-14 h-14 font-medium bg-blue-600 rounded-full hover:bg-blue-700 group focus:ring-4 focus:ring-blue-300 focus:outline-none dark:focus:ring-blue-800">
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6 text-white" fill="currentColor" class="bi bi-camera-fill" viewBox="0 0 16 16">
                        <path d="M10.5 8.5a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0z"/>
                        <path d="M2 4a2 2 0 0 0-2 2v6a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V6a2 2 0 0 0-2-2h-1.172a2 2 0 0 1-1.414-.586l-.828-.828A2 2 0 0 0 9.172 2H6.828a2 2 0 0 0-1.414.586l-.828.828A2 2 0 0 1 3.172 4H2zm.5 2a.5.5 0 1 1 0-1 .5.5 0 0 1 0 1zm9 2.5a3.5 3.5 0 1 1-7 0 3.5 3.5 0 0 1 7 0z"/>
                    </svg>
                </a>
            </div>
            <div id="navbar-mid" role="tooltip" class="absolute z-10 invisible inline-block px-3 py-2 text-sm font-medium text-white transition-opacity duration-300 bg-gray-900 rounded-lg shadow-sm opacity-0 tooltip dark:bg-gray-700">
                Imbas QR
                <div class="tooltip-arrow" data-popper-arrow></div>
            </div>
            <a href="javascript:void(0)" role="button" onclick="changeTab(event,1)" class="inline-flex flex-col items-center justify-center px-5 rounded-r-full hover:bg-gray-50">
                <svg xmlns="http://www.w3.org/2000/svg"  fill="currentColor" class="w-5 h-5 text-gray-500 mb-1" viewBox="0 0 16 16">
                    <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zm.995-14.901a1 1 0 1 0-1.99 0A5.002 5.002 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901z"/>
                </svg>
                <span class="text-xs">Makluman</span>
            </a>
        </div>
    </div>

    <div class="w-screen lg:w-auto my-10">
        <section class="mx-6" aria-labelledby="profile-overview-title">
            <div class="overflow-hidden rounded-3xl bg-white shadow-md">
                <div class="bg-gradient-to-bl from-green-400 to-white to-50% p-6">
                    <div class="sm:flex sm:items-center sm:justify-between">
                        <div class="sm:flex sm:space-x-5">
                            <div class="flex-shrink-0">
                                <img class="mx-auto h-20 w-20 rounded-full" src="<?php echo $_SESSION['pic']; ?>" alt="">
                            </div>
                            <div class="mt-4 text-center sm:mt-0 sm:pt-1 sm:text-left">
                                <p class="text-sm font-medium text-gray-600">Selamat Datang,</p>
                                <p class="text-xl font-bold text-gray-900 sm:text-2xl"><?php echo $_SESSION['name']; ?></p>
                                <p class="text-sm font-medium text-gray-600"><?php echo $_SESSION['pos']; ?></p>
                            </div>
                        </div>
                        <div class="mt-5 flex justify-center sm:mt-0">
                            <a href="#" class="flex items-center justify-center rounded-full bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">Buka profail</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="hidden grid grid-cols-1 divide-y divide-gray-200 border-t border-gray-200 bg-gray-50 sm:grid-cols-3 sm:divide-x sm:divide-y-0">
                    <div class="px-6 py-5 text-center text-sm font-medium">
                        <span class="text-gray-600">Baki Cuti Rehat :</span>
                        <span class="text-gray-900">12</span>
                    </div>
                    <div class="px-6 py-5 text-center text-sm font-medium">
                        <span class="text-gray-600">Baki Cuti Sakit :</span>
                        <span class="text-gray-900">4</span>
                    </div>
                    <div class="px-6 py-5 text-center text-sm font-medium">
                        <span class="text-gray-600">Jumlah Aktiviti Terlibat :</span>
                        <span class="text-gray-900">2</span>
                    </div>
            </div>
        </section>
        <div id="tab-panels">
            <div id="utama" class="flex flex-col mx-4">
                <h1 class="text-2xl font-bold mt-6 ml-4">PROGRAM TERDEKAT</h1>
                <div id="evtSumm" class="container flex overflow-x-auto">
                </div>
                <h1 class="text-2xl font-bold mt-6 ml-4">STATISTIK SEMASA</h1>
                <div id="statSumm" class="container flex overflow-x-auto">
                    <div class="w-screen text-center justify-center py-10">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="mx-auto w-12 h-12 text-gray-400">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M10.5 6a7.5 7.5 0 107.5 7.5h-7.5V6z" />
                            <path stroke-linecap="round" stroke-linejoin="round" d="M13.5 10.5H21A7.5 7.5 0 0013.5 3v7.5z" />
                        </svg>
                        <h3 class="mt-2 text-sm font-semibold text-gray-400">Tiada Statistik</h3>
                    </div>
                </div>
            </div>

            <div id="makluman" class="flex flex-col hidden">
                <h1 class="text-2xl text-center font-bold mt-6">MAKLUMAN</h1>
                <div id="noti">
                    <div class="w-auto text-center my-20">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="mx-auto w-12 h-12 text-gray-400">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M14.857 17.082a23.848 23.848 0 005.454-1.31A8.967 8.967 0 0118 9.75v-.7V9A6 6 0 006 9v.75a8.967 8.967 0 01-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 01-5.714 0m5.714 0a3 3 0 11-5.714 0" />
                        </svg>
                        <h3 class="mt-2 text-sm font-semibold text-gray-400">Tiada makluman setakat ini</h3>
                    </div>
                </div>
            </div>
        </div>
    </div>
  </div>

  <?php include "./src/mnu.php"; ?>
</div>
</body>
</html>