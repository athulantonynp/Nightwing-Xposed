package com.athul.nightwing.module;

import android.app.DownloadManager;
import android.content.SharedPreferences;
import android.content.res.XModuleResources;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;
import java.util.Set;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by athul on 17/8/17.
 */

public class Tets implements IXposedHookZygoteInit,IXposedHookInitPackageResources ,IXposedHookLoadPackage{

    private static String MODULE_PATH = null;

    private static String packages="null";



    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam initPackageResourcesParam) throws Throwable {
        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, initPackageResourcesParam.res);
        Utils.changeDrawerIcon(initPackageResourcesParam,modRes);

    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

       /* if (loadPackageParam.packageName.equals("com.android.settings")){
            Utils.removeFieldsFromSettings(loadPackageParam,loadPackageParam.classLoader);
        }



        if(loadPackageParam.packageName.equals("android.content.pm")){
            Log.e("OMKV","PACKAGE");
            XposedBridge.log("FOUND METHOD");
            for(int i=0;i<1000;i++){
                Log.e("OMKV","PACKAGE");
            }
            Utils.restrictAppUninstallation(loadPackageParam);
        } */


        switch (loadPackageParam.packageName){
            case "com.android.settings":
                //Utils.removeFieldsFromSettings(loadPackageParam,loadPackageParam.classLoader);
                break;
            case "com.android.packageinstaller":
               // Utils.restrictAppUninstallation(loadPackageParam);
                break;

           /* case "android.app":
                Utils.hookDownloadManager(loadPackageParam);
                break; */
           /* case "android.provider":
                break; */
           /* case "android":
               // Utils.hookAppInstallation(loadPackageParam);
            case "com.android.providers.downloads":
                //Utils.hookOnAppInstallation(loadPackageParam);
                break; */
            case "com.android.providers.downloads":
                Utils.newDownloadHook(loadPackageParam);
                break;

        }

    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH=startupParam.modulePath;
        //Class<?> download= XposedHelpers.findClass("android.app.DownloadManager",loadPackageParam.classLoader);

        /*XposedBridge.hookAllMethods(DownloadManager.class, "enqueue", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.e("WTKLV","DOWNLOAD HEADER");
                super.beforeHookedMethod(param);
            }
        }); */
    }
}
