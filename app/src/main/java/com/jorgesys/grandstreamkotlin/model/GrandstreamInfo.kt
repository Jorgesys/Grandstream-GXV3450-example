package com.jorgesys.grandstreamkotlin.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.gs.account.api.BaseAccountApi
import com.gs.phone.api.call.BaseCallApi
import com.gs.phone.entity.DialingInfo

/*========================================================================================*/
val TAG : String = "GrandstreamInfo"

fun getAllSipAccounts() : String{
    try {
        val sipAccounts = BaseAccountApi.getAllSipAccounts().joinToString(",")
        Log.i(TAG, "getAllAccount() ${sipAccounts}")
        return sipAccounts
    }catch (e: Exception){
        Log.e(TAG, "getAllAccount() ${e.message}")
        return "Grandstream: emptyList()"
    }
}

fun getAllUsableSipAccounts() : String{
    try {
        val sipAccounts = BaseAccountApi.getUsableSipAccounts().joinToString(",")
        Log.i(TAG, "getAllUsableSipAccounts() ${sipAccounts}")
        return sipAccounts
    }catch (e: Exception){
        Log.e(TAG, "getAllUsableSipAccounts() ${e.message}")
        return "Grandstream: emptyList()"
    }
}

fun getAllAccounts() : String{
    try {
        val sipAccounts = BaseAccountApi.getAllAccounts().joinToString(",")
        Log.i(TAG, "getAllAccounts() ${sipAccounts}")
        return sipAccounts
    }catch (e: Exception){
        Log.e(TAG, "getAllAccounts() ${e.message}")
        return "Grandstream: emptyList()"
    }
}


fun getDefaultAccount() : String{
    try {
        val sipAccount = "${BaseAccountApi.getDefaultAccount().mAccountId}|${BaseAccountApi.getDefaultAccount().accountName}|${BaseAccountApi.getDefaultAccount().accountNumber}|${BaseAccountApi.getDefaultAccount().isAccountUsable}"
        Log.i(TAG, "getAllUsableSopAccounts() ${sipAccount}")
        return sipAccount
    }catch (e: Exception){
        Log.e(TAG, "getAllUsableSopAccounts() ${e.message}")
        return "Grandstream: emptyList()"
    }
}

fun getAllActivedAccounts() : String{
    try {
        val sipAccounts = BaseAccountApi.getAllActivedAccounts().joinToString(",")
        Log.i(TAG, "getAllActivedAccounts() ${sipAccounts}")
        return sipAccounts
    }catch (e: Exception){
        Log.e(TAG, "getAllActivedAccounts() ${e.message}")
        return "Grandstream: emptyList()"
    }
}

fun getFirstUsableAccountId() : String{
    try {
        val sipAccounts = BaseAccountApi.getFirstUsableAccountId().toString()
        Log.i(TAG, "getFirstUsableAccountId() ${sipAccounts}")
        return sipAccounts
    }catch (e: Exception){
        Log.e(TAG, "getFirstUsableAccountId() ${e.message}")
        return "Grandstream: emptyList()"
    }
}



/* ------------- Grandstream make a call ------------*/
fun callNumber(num: String?) {
    try {
        val dialingInfos: MutableList<DialingInfo> = ArrayList()
        val dialingInfo = DialingInfo()
        //replaced dialingInfo.setAccountID(BaseAccountApi.getDefaultAccount().getAccountID())
        dialingInfo.accountId = BaseAccountApi.getDefaultAccount().accountId
        dialingInfo.originNumber = num
        dialingInfos.add(dialingInfo)
        val dialResults = BaseCallApi.makeCalls(dialingInfos)
        val result = dialResults.getDialResult(dialingInfo)
        Log.e(TAG, "callNumber() result: $result")
    }catch (e: Exception){
        Log.e(TAG, "callNumber() ${e.message}")
    }
}

/* ------------- Grandstream get all accounts ------------*/
private fun getAllAccountList() : List<com.gs.account.entity.SipAccount>{
    try {
        val sipAccounts = BaseAccountApi.getAllSipAccounts()
        Log.i(TAG, "getAllAccountList() ${sipAccounts.joinToString("|")}")
        return sipAccounts
    }catch (e: Exception){
        Log.e(TAG, "getAllAccountList() ${e.message}")
        return emptyList()
    }

}

fun ShowToast(ctx: Context, msg: String) {
    Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()
}


