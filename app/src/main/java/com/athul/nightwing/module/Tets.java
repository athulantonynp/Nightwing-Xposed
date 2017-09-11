package com.athul.nightwing.module;

import android.app.AndroidAppHelper;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.XModuleResources;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import dalvik.system.DexFile;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
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
    XSharedPreferences xSharedPreferences;

    //TODO external storage can be blocked on Environment class
    //TODO SIM card details should be blocked
    //TODO Un appropirate app launches should be blocked by finding category
    //TODO Youtube should be blocked, give it a try.
    //TODO Call blocker in an efficient way


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


        if(loadPackageParam.packageName.equals("android")&&loadPackageParam.processName.equals("android")){
            final Class<?> packageParserClass = XposedHelpers.findClass(
                    "android.content.pm.PackageParser", loadPackageParam.classLoader);
            Log.e("WTKLV","BEFORE");
         /*  try{
               Log.e("WTKLV","EX");
               Class<?> intentClass = XposedHelpers.findClass(
                       "android.content.Intent", loadPackageParam.classLoader);
               for(Method method:intentClass.getMethods()){
                   Log.e("WTKLV",method.getName());
               }
           }catch (Exception e){
               Log.e("WTKLV",e.getLocalizedMessage());
           } */


            XposedBridge.hookAllMethods(packageParserClass, "parseBaseApk",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {


                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                        }
                    });
            XposedBridge.hookAllMethods(packageParserClass, "loadApkIntoAssetManager",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                           try{
                               if(param.args[1].toString().contains(".tmp")){
                                   Log.e("WTKLV","COntains .tmp file");
                                   /*param.args[2]=0;
                                   param.args[1]="podaa"; */
                                   xSharedPreferences=new XSharedPreferences("com.android.providers.media",Constants.sharedPreferenceName);
                                   xSharedPreferences.makeWorldReadable();
                                   Log.e("WTKLV",xSharedPreferences.getString(Constants.downloadIdentifierKey,"NOt VALID"));
                               }
                           }catch (Exception e){
                               Log.e("WTKLV",e.getLocalizedMessage());
                           }
                        }
                    });
          /*  XposedBridge.hookAllMethods(packageParserClass, "isApkPath",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                            Log.e("WTKLV",param.args[0].toString());
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                        }
                    }); */


        }


        switch (loadPackageParam.packageName){
            case "android":
                Utils.notificationHook(loadPackageParam);
                Utils.USBMenuHook(loadPackageParam);

                break;
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
            case "com.android.launcher3":
                Utils.gsbHook(loadPackageParam);
                Utils.launcherHook(loadPackageParam);
                break;
            case "com.android.systemui":
                Utils.recentsHook(loadPackageParam);
                break;
            case "com.android.dialer":
                Utils.phoneHook(loadPackageParam);
                break;
            case "com.android.incallui":
                //TODO this package needs a revision
                Utils.inCallHook(loadPackageParam);
                break;
            case "com.android.contacts":
                Utils.contactsHook(loadPackageParam);
                break;
            case "com.android.phone":
                Log.e("WTKLV","BEFORE");
                Utils.incomingHook(loadPackageParam);
                break;
            case "com.android.camera":

                Utils.cameraAppHook(loadPackageParam);
                break;
            case "com.android.providers.media":
                xSharedPreferences=new XSharedPreferences("com.android.providers.media",Constants.sharedPreferenceName);
                xSharedPreferences.makeWorldReadable();
                break;
            case "me.entri.entrime":
                Utils.NotificationContentHook(loadPackageParam);
                break;
            case "com.athul.nightwing":
                Utils.NotificationContentHook(loadPackageParam);
                break;
            case "com.kingroot.kinguser":
                Utils.hookAppLaunching(loadPackageParam);
                break;
        }


    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;

        /*XposedBrcom.android.cameraidge.hookAllMethods(DownloadManager.class, "enqueue", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.e("WTKLV","DOWNLOAD HEADER");
                super.beforeHookedMethod(param);
            }
        }); */




    }




}
