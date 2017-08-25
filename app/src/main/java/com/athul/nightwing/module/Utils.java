package com.athul.nightwing.module;

import android.app.Activity;
import android.app.AndroidAppHelper;
import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.XModuleResources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.text.LoginFilter;
import android.util.Log;

import com.athul.nightwing.R;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by athul on 17/8/17.
 */

public class Utils {

    public static void changeDrawerIcon(XC_InitPackageResources.InitPackageResourcesParam resparam, XModuleResources modRes) {

        if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.android.launcher3") != 0) {
            resparam.res.setReplacement("com.android.launcher3", "drawable", "ic_allapps", modRes.fwd(R.drawable.drawer));
            resparam.res.setReplacement("com.android.launcher3", "drawable", "ic_allapps_pressed", modRes.fwd(R.drawable.drawer_pressed));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.android.launcher") != 0) {
            resparam.res.setReplacement("com.android.launcher", "drawable", "ic_allapps", modRes.fwd(R.drawable.drawer));
            resparam.res.setReplacement("com.android.launcher", "drawable", "ic_allapps_pressed", modRes.fwd(R.drawable.drawer_pressed));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.cyanogenmod.trebuchet") != 0) {
            resparam.res.setReplacement("com.cyanogenmod.trebuchet", "drawable", "ic_allapps", modRes.fwd(R.drawable.drawer));
            resparam.res.setReplacement("com.cyanogenmod.trebuchet", "drawable", "ic_allapps_pressed", modRes.fwd(R.drawable.drawer_pressed));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.anddoes.launcher") != 0) {
            resparam.res.setReplacement("com.anddoes.launcher", "drawable", "ic_allapps", modRes.fwd(R.drawable.drawer));
            resparam.res.setReplacement("com.anddoes.launcher", "drawable", "ic_allapps_pressed", modRes.fwd(R.drawable.drawer_pressed));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.google.android.googlequicksearchbox") != 0) {
            resparam.res.setReplacement("com.google.android.googlequicksearchbox", "drawable", "ic_allapps", modRes.fwd(R.drawable.drawer));
            resparam.res.setReplacement("com.google.android.googlequicksearchbox", "drawable", "ic_allapps_pressed", modRes.fwd(R.drawable.drawer_pressed));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.slim.slimlauncher") != 0) {
            resparam.res.setReplacement("com.slim.slimlauncher", "drawable", "ic_allapps", modRes.fwd(R.drawable.drawer));
            resparam.res.setReplacement("com.slim.slimlauncher", "drawable", "ic_allapps_pressed", modRes.fwd(R.drawable.drawer_pressed));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.microsoft.launcher") != 0) {
            resparam.res.setReplacement("com.microsoft.launcher", "drawable", "ic_allapps", modRes.fwd(R.drawable.drawer));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.blackberry.blackberrylauncher") != 0) {
            resparam.res.setReplacement("com.blackberry.blackberrylauncher", "drawable", "ic_allapps", modRes.fwd(R.drawable.drawer));
        }
    }

    public static void removeFieldsFromSettings(final XC_LoadPackage.LoadPackageParam loadPackageParam, ClassLoader classLoader) {
       /* XposedHelpers.findAndHookMethod("com.android.settings.SettingsActivity", loadPackageParam.classLoader, "loadCategoriesFromResource",  int.class,List.class,
                }); */

       XposedHelpers.findAndHookMethod("com.android.settings.SettingsActivity", loadPackageParam.classLoader, "onCreate",Bundle.class,
               new XC_MethodHook() {
                   @Override
                   protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                       Activity act = (Activity)param.thisObject;
                       act.finish();
                   }

               }



       );


    }


    public static void restrictAppUninstallation(final XC_LoadPackage.LoadPackageParam loadPackageParam) {

       XposedHelpers.findAndHookMethod("com.android.packageinstaller.UninstallerActivity", loadPackageParam.classLoader,
               "onCreate", Bundle.class, new XC_MethodHook() {

                   @Override
                   protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                       final Activity act = (Activity)param.thisObject;
                       Uri packageURI = act.getIntent().getData();
                       String packageName = packageURI.getEncodedSchemeSpecificPart();

                       if((Arrays.asList(Constants.restrictedApps).contains(packageName))){

                           try{
                               Context context = (Context) AndroidAppHelper.currentApplication();
                               Intent dialogIntent=new Intent();
                               dialogIntent.setComponent(new ComponentName("com.athul.nightwing","com.athul.nightwing.activities.ProhibitiedActivity"));
                               dialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               act.startActivity(dialogIntent);
                               act.finish();
                           }catch (Exception e){
                           }
                           act.finish();

                       }
                   }

               });

        XposedHelpers.findAndHookMethod("com.android.packageinstaller.PackageInstallerActivity",
                loadPackageParam.classLoader, "initiateInstall", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                        PackageInfo mPakcageInfoNew=(PackageInfo)XposedHelpers.getObjectField(param.thisObject,"mPkgInfo");
                        final Activity act = (Activity)param.thisObject;
                        if(!(Arrays.asList(Constants.restrictedApps).contains(mPakcageInfoNew.packageName))){

                            try{
                                Context context = (Context) AndroidAppHelper.currentApplication();
                                Intent dialogIntent=new Intent();
                                dialogIntent.setComponent(new ComponentName("com.athul.nightwing","com.athul.nightwing.activities.ProhibitiedActivity"));
                                dialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                act.startActivity(dialogIntent);
                                act.finish();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            act.finish();

                        }
                    }
                });



    }

    public static void hookOnAppInstallation(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        Class<?> traceClass=XposedHelpers.findClass("com.android.defcontainer.DefaultContainerService",loadPackageParam.classLoader);

        XposedHelpers.findAndHookMethod(traceClass, "copyResourceInner", Uri.class, String.class,
                String.class, String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {


                    }
                });


    }


    public static void hookOnLoad(XC_LoadPackage.LoadPackageParam loadPackageParam) {
    }

    public static void hookAppInstallation(XC_LoadPackage.LoadPackageParam loadPackageParam) {


        try{
            Class<?> traceClass=XposedHelpers.findClass("android.content.pm.PackageInfoLite",loadPackageParam.classLoader);
            Class<?> pm=XposedHelpers.findClass("android.content.pm.PackageInstaller",loadPackageParam.classLoader);
            Class<?> download=XposedHelpers.findClass("android.app.DownloadManager",loadPackageParam.classLoader);
            XposedHelpers.findAndHookConstructor(traceClass, Parcel.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Parcel parcel=(Parcel)param.args[0];

                }
            });

            /*XposedHelpers.findAndHookMethod(download, "request", Uri.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Uri request=(Uri)param.args[0];
                    Log.e("WTKLV",new Gson().toJson(request).toString());
                }
            }); */
            XposedHelpers.findAndHookConstructor(download, ContentResolver.class, String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                }
            });

            XposedHelpers.findAndHookConstructor(pm, Context.class, PackageManager.class, "android.content.pm.IPackageInstaller",
                    String.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                        }
                    });
        }catch (Exception e){

        }

    }

    /**
     * This method will hook on google playstore app download
     * proceess
     * @param loadPackageParam
     */
    public static void newDownloadHook(final XC_LoadPackage.LoadPackageParam loadPackageParam) {


        XposedHelpers.findAndHookMethod("com.android.providers.downloads.DownloadThread", loadPackageParam.classLoader,
                "executeDownload", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Log.e("WTKLV","HOOKED METHOD LOAD");
                        Object fieldValue = XposedHelpers.getObjectField(param.thisObject, "mInfoDelta");
                        Object uri= XposedHelpers.getObjectField(fieldValue, "mUri");

                        URL urlNew=new URL((String) uri);

                        if(!(urlNew.toString().contains("play.googleapis.com/market/download")&&
                                urlNew.toString().contains("package.i.want.to.avoid"))) {

                            final Activity act = (Activity) param.thisObject;


                            try {
                                Context context = (Context) AndroidAppHelper.currentApplication();
                                Intent dialogIntent = new Intent();
                                dialogIntent.setComponent(new ComponentName("com.athul.nightwing", "com.athul.nightwing.activities.ProhibitiedActivity"));
                                dialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                act.startActivity(dialogIntent);
                                act.finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            act.finish();
                           Log.e("WTKLV","IN CONDITION");
                            ((Activity) (Context)XposedHelpers.getObjectField(param.thisObject,"mContext")).finish();
                            return;


                        }
                    }
                });

        /*XposedHelpers.findAndHookMethod("android.app.DownloadManager", loadPackageParam.classLoader
                , "enqueue", "android.app.DownloadManager.DownloadManager$Request",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Log.e("WTKLV","FOUND METHOD");
                    }
                });

      /*  try{
            Log.e("WTKLV","IN TRY");
            Class<?> download=XposedHelpers.findClass("android.app.DownloadManager",loadPackageParam.classLoader);
            Log.e("WTKLV","SUCESS");

        }catch (Exception e){
                Log.e("WTKLV",e.getLocalizedMessage());
        }

        /*Class<?> request=XposedHelpers.findClass("android.app.DownloadManager.DownloadManager$Request",
                loadPackageParam.classLoader);
        Method method=download.getMethod("enqueue", request);
        Log.e("WTKLV",method.toGenericString());
        Log.e("WTKLV", method.toString());
        XposedHelpers.findAndHookMethod(download, "enqueue", "android.app.DownloadManager$Request", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.e("WTKLV",param.toString());
            }
        }); */
    }



}
