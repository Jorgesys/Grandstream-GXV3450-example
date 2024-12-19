package com.jorgesys.grandstreamkotlin

//Grandstream API
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.gs.account.api.BaseAccountApi
import com.gs.account.context.AccountContext
import com.gs.account.listener.AccountStatusListener
import com.gs.common.client.ApiClient
import com.gs.phone.api.audio.AudioRouteApi
import com.gs.phone.api.call.BaseCallApi
import com.gs.phone.api.line.BaseLineApi
import com.gs.phone.entity.DialingInfo
import com.gs.sms.api.SmsManagerApi
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.padding


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jorgesys.appcompose.ui.theme.AppGSTheme
import com.jorgesys.grandstreamkotlin.model.infos
import com.jorgesys.grandstreamkotlin.theme.AllInfos


class MainActivity : AppCompatActivity() {

    lateinit var mAccountStatusListener : AccountStatusListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeGrandstreamAPI()
        enableEdgeToEdge()
        /*PRE COMPOSE setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/


        setContent {
            AppGSTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    // surfaceColor color will be changing gradually from primary to surface
                    color = MaterialTheme.colorScheme.primary,
                    // animateContentSize will change the Surface size gradually
                    modifier = Modifier.animateContentSize().padding(1.dp)) {
                    AllInfos(infos)
                }
            }
        }
    }

    /*========================================================================================*/
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light mode")
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark mode")
    @Composable
    fun PlanetPreview() {
        AppGSTheme {
            AllInfos(infos)
        }
    }

    fun initializeGrandstreamAPI(){
        /* ------------- Grandstream initialize ApiClient ------------*/
        if(ApiClient.builder != null) {
            ApiClient.builder.setContext(applicationContext)
                .addApi(BaseAccountApi.API)
                .addApi(BaseCallApi.API)
                .addApi(BaseLineApi.API)
                .addApi(SmsManagerApi.API)
                .addApi(AudioRouteApi.API)
                .build();
            ShowToast(applicationContext, "GrandstreamAPI was initialized =-)...")
        }else{
            Log.e(TAG, "ApiClient.builder NOT initialized...")
            ShowToast(applicationContext, "initializeGrandstreamAPI API NOT initialized =-(...")
        }
        /* ---------------------------------------------------------*/
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
        ShowToast(applicationContext, "callNumber() result: $result")
        }catch (e: Exception){
            Log.e(TAG, "callNumber() ${e.message}")
            ShowToast(applicationContext, "${e.message}")
        }

    }


    /* ------------- Grandstream get all accounts ------------*/
    private fun getAllAccount() : List<com.gs.account.entity.SipAccount>{
        try {
            val sipAccounts = BaseAccountApi.getAllSipAccounts()
            Log.i(TAG, "getAllAccount() ${sipAccounts.joinToString(",")}")
            ShowToast(applicationContext, "getAllAccount() ${sipAccounts.joinToString(",")}")
            return sipAccounts
        }catch (e: Exception){
            Log.e(TAG, "getAllAccount() ${e.message}")
            ShowToast(applicationContext, "getAllAccount() ${e.message}")
            return emptyList()
        }

    }

    private fun getAllSipAccounts() : String{
        try {
            val sipAccounts = BaseAccountApi.getAllSipAccounts().joinToString(",")
            Log.i(TAG, "getAllSipAccounts() ${sipAccounts}")
            ShowToast(applicationContext, "getAllSipAccounts() ${sipAccounts}")
            return sipAccounts
        }catch (e: Exception){
            Log.e(TAG, "getAllSipAccounts() ${e.message}")
            ShowToast(applicationContext, "getAllSipAccounts() ${e.message}")
            return "emptyList()"
        }

    }

    /* ------------- Grandstream Start Account Status Monitor --------------*/
    private fun startMonitorAccountChange() {
        try {
        mAccountStatusListener = AccountStatusListener()
        BaseAccountApi.addStatusListener(
            "MonitorAccount",
            mAccountStatusListener.callback,
            AccountContext.ListenType.SIP_ACCOUNT_STATUS, false
        )
        }catch (e: Exception){
            Log.e(TAG, "startMonitorAccountChange() ${e.message}")
            ShowToast(applicationContext, "startMonitorAccountChange() ${e.message}")
        }
    }


    /* ------------- Grandstream Stop Account Status Monitor --------------*/
private fun stopMonitorAccountChange() {
        try {
    BaseAccountApi.removeStatusListener(mAccountStatusListener.callback)
    //*** Replaced mAccountStatusListener.callback.destroy()
        mAccountStatusListener.destroy()
    mAccountStatusListener.callback = null
    }catch (e: Exception){
        Log.e(TAG, "stopMonitorAccountChange() ${e.message}")
        ShowToast(applicationContext, "stopMonitorAccountChange() ${e.message}")
    }
}

    fun ShowToast(ctx: Context, msg: String) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val TAG = "MainActivity"

    }
}