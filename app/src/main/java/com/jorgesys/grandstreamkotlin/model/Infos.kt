package com.jorgesys.grandstreamkotlin.model

import com.jorgesys.grandstreamkotlin.R
import com.jorgesys.grandstreamkotlin.model.data.Info

val infos = listOf(
    Info(
        0,
        "AllSipAccounts",
        getAllSipAccounts(),
        R.drawable.ic_launcher_foreground,
    ),
    Info(
        1,
        "getDefaultAccount",
        getDefaultAccount(),
        R.drawable.ic_launcher_foreground,
    ),
    Info(
        2,
        "getAllUsableSipAccounts",
        getAllUsableSipAccounts(),
        R.drawable.ic_launcher_foreground,
    ),
    Info(
        3,
        "getAllAccounts",
        getAllAccounts(),
        R.drawable.ic_launcher_foreground,
    ),
    Info(
        4,
        "getAllActivedAccounts",
        getAllActivedAccounts(),
        R.drawable.ic_launcher_foreground,
    ),
    Info(
        5,
        "getFirstUsableAccountId",
        getFirstUsableAccountId(),
        R.drawable.ic_launcher_foreground,
    ),
)