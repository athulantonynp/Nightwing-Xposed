package com.athul.nightwing.module;

import android.app.Activity;
import android.app.AndroidAppHelper;
import android.app.Application;
import android.app.DownloadManager;
import android.app.Instrumentation;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XModuleResources;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.provider.Settings;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RemoteViews;

import com.athul.nightwing.R;
import com.athul.nightwing.activities.Splash;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getObjectField;

/**
 * Created by athul on 17/8/17.
 */

public class Utils {

    private static Field itemInfoTitleField;
    private static Field appInfoComponentNameField;
    public static final String ITEM_INFO_CLASS = "com.android.launcher3.ItemInfo";
    public static final String APP_INFO_CLASS = "com.android.launcher3.AppInfo";


    public static void changeDrawerIcon(XC_InitPackageResources.InitPackageResourcesParam resparam, XModuleResources modRes) {

        if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.android.launcher3") != 0) {
            resparam.res.setReplacement("com.android.launcher3", "drawable", "ic_allapps", modRes.fwd(R.drawable.ic_action_name));
            resparam.res.setReplacement("com.android.launcher3", "drawable", "ic_allapps_pressed", modRes.fwd(R.drawable.ic_action_press));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.android.launcher") != 0) {
            resparam.res.setReplacement("com.android.launcher", "drawable", "ic_allapps", modRes.fwd(R.drawable.ic_action_name));
            resparam.res.setReplacement("com.android.launcher", "drawable", "ic_allapps_pressed", modRes.fwd(R.drawable.ic_action_name));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.cyanogenmod.trebuchet") != 0) {
            resparam.res.setReplacement("com.cyanogenmod.trebuchet", "drawable", "ic_allapps", modRes.fwd(R.drawable.ic_action_name));
            resparam.res.setReplacement("com.cyanogenmod.trebuchet", "drawable", "ic_allapps_pressed", modRes.fwd(R.drawable.ic_action_press));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.anddoes.launcher") != 0) {
            resparam.res.setReplacement("com.anddoes.launcher", "drawable", "ic_allapps", modRes.fwd(R.drawable.ic_action_name));
            resparam.res.setReplacement("com.anddoes.launcher", "drawable", "ic_allapps_pressed", modRes.fwd(R.drawable.ic_action_press));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.google.android.googlequicksearchbox") != 0) {
            resparam.res.setReplacement("com.google.android.googlequicksearchbox", "drawable", "ic_allapps", modRes.fwd(R.drawable.ic_action_name));
            resparam.res.setReplacement("com.google.android.googlequicksearchbox", "drawable", "ic_allapps_pressed", modRes.fwd(R.drawable.ic_action_press));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.slim.slimlauncher") != 0) {
            resparam.res.setReplacement("com.slim.slimlauncher", "drawable", "ic_allapps", modRes.fwd(R.drawable.ic_action_name));
            resparam.res.setReplacement("com.slim.slimlauncher", "drawable", "ic_allapps_pressed", modRes.fwd(R.drawable.ic_action_press));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.microsoft.launcher") != 0) {
            resparam.res.setReplacement("com.microsoft.launcher", "drawable", "ic_allapps", modRes.fwd(R.drawable.ic_action_name));
        } else if (resparam.res.getIdentifier("ic_allapps", "drawable", "com.blackberry.blackberrylauncher") != 0) {
            resparam.res.setReplacement("com.blackberry.blackberrylauncher", "drawable", "ic_allapps", modRes.fwd(R.drawable.ic_action_name));
        }
    }

    public static void removeFieldsFromSettings(final XC_LoadPackage.LoadPackageParam loadPackageParam, ClassLoader classLoader) {
       /* XposedHelpers.findAndHookMethod("com.android.settings.SettingsActivity", loadPackageParam.classLoader, "loadCategoriesFromResource",  int.class,List.class,
                }); */

       XposedHelpers.findAndHookMethod("com.android.settings.SettingsActivity", loadPackageParam.classLoader, "onCreate",Bundle.class,
               new XC_MethodHook() {
                   @Override
                   protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                      /* Activity act = (Activity)param.thisObject;
                       act.finish(); */
                       XposedHelpers.setBooleanField(param.thisObject,"mDisplaySearch",false);

                   }

                   @Override
                   protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                       XposedHelpers.setBooleanField(param.thisObject,"mDisplaySearch",false);
                   }
               }



       );
        XposedHelpers.findAndHookMethod("com.android.settings.SettingsActivity", loadPackageParam.classLoader, "updateTilesList",List.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                      /* Activity act = (Activity)param.thisObject;
                       act.finish(); */
                        ((List)param.args[0]).remove(3);
                    }

                }


        );
        XposedHelpers.findAndHookMethod("com.android.settings.dashboard.DashboardCategory", loadPackageParam.classLoader,
                "getTile",int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                      /* Activity act = (Activity)param.thisObject;
                       act.finish(); */

                        if(((int)param.args[0])!=0){
                           param.args[0]=0;
                        }
                    }

                }



        );
        XposedHelpers.findAndHookMethod("com.android.settings.dashboard.DashboardCategory", loadPackageParam.classLoader,
                "getTilesCount", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                        param.setResult(1);
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



    public static void hookOnLoad(XC_LoadPackage.LoadPackageParam loadPackageParam) {
    }


    /**
     * This method will hook on google playstore app download
     * proceess
     * @param loadPackageParam
     */
    public static void newDownloadHook(final XC_LoadPackage.LoadPackageParam loadPackageParam) {

        findAndHookMethod(Notification.Builder.class, "setContentTitle", CharSequence.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String urlNew = param.args[0].toString();

                if (urlNew.toString().contains("Entri") ||
                        urlNew.toString().contains("Playstore") ||
                        urlNew.toString().contains("Playservices") ||
                        urlNew.toString().contains("Play Services")) {

                    packageDownloadHook("entri");
                } else {
                    /*Application app=AndroidAppHelper.currentApplication();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    app.startActivity(intent); */
                    packageDownloadHook("block");
                }


                Context context = (Context) AndroidAppHelper.currentApplication();
                Log.e("WTKLV", "ORIGINAL" + context.getPackageName());
                super.beforeHookedMethod(param);
            }


            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });

        findAndHookMethod(NotificationManager.class, "notify", String.class, int.class, Notification.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Notification notification = (Notification) param.args[2];
                /*Context context = (Context) getObjectField(param.thisObject, "mContext");
                String appName = context.getPackageManager().getApplicationInfo(loadPackageParam.packageName, 0).loadLabel(context.getPackageManager()).toString();
                Resources modRes = context.getPackageManager().getResourcesForApplication("com.athul.nightwing");
                String replacement = modRes.getString(modRes.getIdentifier("notification_hidden_by_maxlock", "string", "com.athul.nightwing"));
                Notification.Builder b = new Notification.Builder(context).setContentTitle(appName).setContentText(replacement);
                notification.contentView = b.build().contentView;
                notification.bigContentView = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    notification.headsUpContentView = null;
                notification.tickerText = replacement;
                Intent notificationIntent = new Intent(context, Splash.class);
                PendingIntent contentIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, notificationIntent, 0);

                ((Notification)param.args[2]).contentIntent=contentIntent;
                //((Notification)param.args[2]).contentView=null; */

            }

        });


        /*XposedHelpers.findAndHookMethod("com.android.providers.downloads.DownloadThread", loadPackageParam.classLoader,
                "executeDownload", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        int i=0;
                        Object fieldValue = XposedHelpers.getObjectField(param.thisObject, "mInfoDelta");
                        Object uri= XposedHelpers.getObjectField(fieldValue, "mUri");
                        URL urlNew=new URL((String) uri);

                        try{
                            Object downloadInfo = XposedHelpers.getObjectField(param.thisObject, "mInfo");
                            Log.e("WTKLV",fieldValue.toString());
                            Log.e("WTKLV",urlNew.toString());
                        }catch (Exception e){
                            Log.e("WTKLV",e.getLocalizedMessage());
                        }


                        if((urlNew.toString().contains("play.googleapis.com/market/download"))) {

                            if(urlNew.toString().contains("me.entri.entrime")||
                                    urlNew.toString().contains("com.google.android.gms")||
                                    urlNew.toString().contains("com.android.vending")){
                                Log.e("WTKLV","ENTRI APP IS DOWNLOADING");
                                packageDownloadHook("entri");
                            }else {
                                Log.e("WTKLV","BLOCK APP IS DOWNLOADING");
                                packageDownloadHook("block");
                            }


                          }else {
                            Log.e("WTKLV","NORMAL APP IS DOWNLOADING");
                            packageDownloadHook("normal");
                        }

                    }
                }); */


    }

    private static void packageDownloadHook(String packageName) {
        try{

            Context context = (Context) AndroidAppHelper.currentApplication();
            SharedPreferences sharedPreferences=context.getSharedPreferences(Constants.sharedPreferenceName,
                    Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(Constants.downloadIdentifierKey,packageName);
            editor.apply();
            editor.commit();
        }catch (Exception e){
            Log.e("WTKLV",e.getLocalizedMessage());
        }
    }

    /**
     * Method will show only few apps in launcher3 apk's apps list.
     * @param loadPackageParam
     */


    public static void launcherHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        String APPS_CUSTOMIZE_PAGED_VIEW_CLASS = "com.android.launcher3.AppsCustomizePagedView";
        String ITEM_INFO_CLASS = "com.android.launcher3.ItemInfo";
        String APP_INFO_CLASS = "com.android.launcher3.AppInfo";

        try {
            final Class<?> itemInfoClass = XposedHelpers.findClass(ITEM_INFO_CLASS, loadPackageParam.classLoader);
            itemInfoTitleField = itemInfoClass.getDeclaredField("title");
            itemInfoTitleField.setAccessible(true);

            final Class<?> appInfoClass = XposedHelpers.findClass(APP_INFO_CLASS, loadPackageParam.classLoader);
            appInfoComponentNameField = appInfoClass.getDeclaredField("componentName");
            appInfoComponentNameField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.e("WTKLV",e.getLocalizedMessage());
        }


        XposedHelpers.findAndHookMethod("com.android.launcher3.AppsCustomizePagedView", loadPackageParam.classLoader,
                "setApps", ArrayList.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        removeAppsFromDrawerMenu((ArrayList)param.args[0]);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        removeAppsFromDrawerMenu((ArrayList)XposedHelpers.getObjectField(ArrayList.class,"mApps"));
                    }
                });

        XposedHelpers.findAndHookMethod("com.android.launcher3.AppsCustomizePagedView", loadPackageParam.classLoader,
                "addApps", ArrayList.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        removeAppsFromDrawerMenu((ArrayList)param.args[0]);
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        removeAppsFromDrawerMenu((ArrayList)XposedHelpers.getObjectField(ArrayList.class,"mApps"));
                    }
                });

        XposedHelpers.findAndHookMethod("com.android.launcher3.AppsCustomizePagedView", loadPackageParam.classLoader,
                "updateApps", ArrayList.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        removeAppsFromDrawerMenu((ArrayList)param.args[0]);
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        removeAppsFromDrawerMenu((ArrayList)XposedHelpers.getObjectField(ArrayList.class,"mApps"));
                    }
                });


        XposedHelpers.findAndHookMethod("com.android.launcher3.Launcher", loadPackageParam.classLoader, "isWorkspaceLocked", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });



    }

    @SuppressWarnings("rawtypes")
    private static void removeAppsFromDrawerMenu(ArrayList arg) throws IllegalAccessException {
        XSharedPreferences   appShared;
        appShared=new XSharedPreferences("com.athul.nightwing","my");
        appShared.makeWorldReadable();
        Iterator appIter = arg.iterator();
        while (appIter.hasNext()) {
            Object app = appIter.next();
            String label = (String) itemInfoTitleField.get(app);
            String packageName = ((ComponentName) appInfoComponentNameField.get(app)).getPackageName();

            if(appShared.getBoolean("unlock",false)){
                Log.e("WTKLV","UNLOCK MODE");
                switch (packageName){
                    case "me.entri.entrime":
                        break;
                    case "com.athul.nightwing":
                        break;
                    case "de.robv.android.xposed.installer":
                        break;
                    case "com.android.vending":
                        break;
                    case "com.android.settings":
                        Log.e("WTKLV","settings FOUND");
                        break;
                    default:
                        appIter.remove();
                        break;
                }
            }else {
                Log.e("WTKLV","LOCK MODE");
                switch (packageName){
                    case "me.entri.entrime":
                        break;
                    case "com.android.vending":
                        break;
                    case "com.android.settings":
                        break;
                    default:
                        appIter.remove();
                        break;
                }
            }



        }

    }

    public static void gsbHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {

       XposedHelpers.findAndHookMethod("com.android.launcher3.Launcher", loadPackageParam.classLoader,"onCreate",
               Bundle.class, new XC_MethodHook() {
                   @Override
                   protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                       ((View)XposedHelpers.getObjectField(param.thisObject,"mSearchDropTargetBar"))
                               .setVisibility(View.GONE);
                   }
               });
    }

    /**
     * Method to remove Google Search bar from recent apps screen.
     * @param loadPackageParam
     */
    public static void recentsHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {



        XposedHelpers.findAndHookMethod("com.android.systemui.recents.RecentsActivity", loadPackageParam.classLoader,
                "addSearchBarAppWidgetView", new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        ((View)XposedHelpers.getObjectField(param.thisObject,"mSearchAppWidgetHostView"))
                                .setVisibility(View.GONE);

                    }
                });

        XposedHelpers.findAndHookMethod("com.android.systemui.recents.views.RecentsView", loadPackageParam.classLoader,
                "setSearchBarVisibility", int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        if( ((View)XposedHelpers.getObjectField(param.thisObject,"mSearchBar"))!=null){
                            ((View) XposedHelpers.getObjectField(param.thisObject,"mSearchBar")).setVisibility(View.GONE);
                        }
                    }
                });
        try{
            XposedHelpers.findAndHookMethod("com.android.systemui.recents.views.RecentsView", loadPackageParam.classLoader,
                    "setSearchBar", View.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            ((View)param.args[0]).setVisibility(View.GONE);
                        }
                    });
            XposedHelpers.findAndHookMethod("com.android.systemui.recents.RecentsActivity", loadPackageParam.classLoader,
                    "bindSearchBarAppWidget", new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            ((View)XposedHelpers.getObjectField(param.thisObject,"mSearchAppWidgetHostView"))
                                    .setVisibility(View.GONE);

                        }
                    });
            XposedHelpers.findAndHookMethod("com.android.systemui.recents.RecentsActivity", loadPackageParam.classLoader,
                    "onCreate",Bundle.class, new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            ((View)XposedHelpers.getObjectField(param.thisObject,"mSearchAppWidgetHostView"))
                                    .setVisibility(View.GONE);

                        }
                    });
        }catch (Exception e){
            Log.e("WTKLV",e.getLocalizedMessage());
        }

    }

    public static void phoneHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod("com.android.dialer.DialtactsActivity", loadPackageParam.classLoader,
                "onCreate", Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        final Activity act = (Activity)param.thisObject;

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
                });
    }

    public static void inCallHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        XposedHelpers.findAndHookMethod("com.android.incallui.InCallActivity", loadPackageParam.classLoader, "onCreate",
                Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        final Activity act = (Activity)param.thisObject;

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

                });
    }

    public static void contactsHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod("com.android.contacts.activities.PeopleActivity", loadPackageParam.classLoader,
                "onCreate", Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        finishAndShowNativeDialog(param);
                    }
                });
    }

    private static void finishAndShowNativeDialog(XC_MethodHook.MethodHookParam param) {
        final Activity act = (Activity)param.thisObject;

        try{
            Context context = (Context) AndroidAppHelper.currentApplication();
            Intent dialogIntent=new Intent();
            dialogIntent.setComponent(new ComponentName("com.athul.nightwing","com.athul.nightwing.activities.ProhibitiedActivity"));
            dialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            act.startActivity(dialogIntent);
            act.finish();
        }catch (Exception e){
            Log.e("WTKLV",e.getLocalizedMessage());
        }
        act.finish();
    }

    /*
    Method descriptions will be added later.
    please be patient.
     */

    public static void externalSdCardHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        XposedHelpers.findAndHookMethod("android.support.v4.content.ContextCompat", loadPackageParam.classLoader,
                "getExternalFilesDirs", Context.class, String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Log.e("WTKLV","BEFORE EXT");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.e("WTKLV","AFTER EXT");
                        File[] resultArray=(File[])param.getResult();
                        File[] finalArray=new File[1];
                        for(File file:resultArray){
                            try
                            {
                                Log.e("WTKLV",file.getName());
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }

                });
    }

    public static void notificationHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        final Class<?> traceClass = XposedHelpers.findClass("android.app.NotificationManager", null);

        try{
            XposedHelpers.findAndHookMethod("android.app.NotificationManager", loadPackageParam.classLoader,
                    "notify", int.class, Notification.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                        }
                    });

            XposedHelpers.findAndHookMethod("android.provider.Settings.Global", loadPackageParam.classLoader, "getInt", ContentResolver.class, String.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("hideUSBDebugging: hook Settings.Global.getInt method");
                    if (param.args[1].equals(Settings.Global.ADB_ENABLED)) {
                        param.setResult(0);
                    }

                }
            });

            XposedHelpers.findAndHookMethod("android.provider.Settings.Secure", loadPackageParam.classLoader, "getInt", ContentResolver.class, String.class, int.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("hideUSBDebugging: hook Settings.Secure.getInt method");
                    if (param.args[1].equals(Settings.Secure.ADB_ENABLED)) {
                        param.setResult(0);
                    }
                    if(param.args[1].equals("device_provisioned")){
                        param.setResult(0);
                    }
                }
            });

            XposedHelpers.findAndHookMethod("android.provider.Settings.Secure", loadPackageParam.classLoader, "getInt", ContentResolver.class, String.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("hideUSBDebugging: hook Settings.Secure.getInt method");
                    if (param.args[1].equals(Settings.Secure.ADB_ENABLED)) {
                        param.setResult(0);
                    }
                    if(param.args[1].equals("device_provisioned")){
                        param.setResult(0);
                    }
                }
            });

           /* XposedHelpers.findAndHookMethod("android.app.ActivityManager", loadPackageParam.classLoader, "startActivity",
                    Context.class, Intent.class, Bundle.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.e("WTKLV",((Intent)param.args[1]).toString());
                        }
                    }); */
        }catch (Exception e){
            Log.e("WTKLV",e.getLocalizedMessage());
        }

    }

    public static void incomingHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod("com.android.phone.CallNotifier", loadPackageParam.classLoader, "onNewRingingConnection",
                "android.os.AsyncResult", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }
                });

        XposedHelpers.findAndHookMethod("com.android.phone.CallController", loadPackageParam.classLoader, "placeCall", Intent.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                        ((Intent)param.args[0]).setAction(null);
                        ((Intent)param.args[0]).setData(null);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });
    }

    public static void USBMenuHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        String[] classNames = {
                "com.android.server.usb.UsbDeviceManager$UsbHandler",
                "com.android.server.usb.UsbDeviceManagerEx$UsbHandlerEx"
        };

        for (String className : classNames) {
            Class<?> cls;
            try {
                cls = XposedHelpers.findClass(className, loadPackageParam.classLoader);
            } catch (XposedHelpers.ClassNotFoundError e) {
                continue;
            }



            try {
                XposedHelpers.findAndHookMethod(cls, "updateAdbNotification", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(null);
                    }
                });

                XposedHelpers.findAndHookMethod(cls, "updateUsbNotification", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(null);
                    }
                });
            } catch (Throwable e) {
                Log.e("WTKLV",e.getLocalizedMessage());
            }
        }
    }

    public static void cameraHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        try{
            XposedHelpers.findAndHookConstructor(Camera.class, int.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.e("WTKLV","FOUND CAMERA");
                    if (param.hasThrowable()) {
                        return;
                    }

                    final int sourceId = (int) param.args[0];
                    final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                    Camera.getCameraInfo(sourceId, cameraInfo);

                    String facingStr;
                    switch (cameraInfo.facing) {
                        case Camera.CameraInfo.CAMERA_FACING_FRONT:
                            facingStr = "front";
                            break;
                        case Camera.CameraInfo.CAMERA_FACING_BACK:
                            facingStr = "back";
                            break;
                        default:
                            facingStr = "unknown";
                            break;
                    }


                }
            });

        }catch (Exception e){
            Log.e("WTKLV",e.getLocalizedMessage());
        }



    }

    public static void cameraAppHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        Log.e("WTKLV","CAMERA APP");
        XposedHelpers.findAndHookMethod("com.android.camera.CameraActivity", loadPackageParam.classLoader, "onCreate",
                Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        finishAndShowNativeDialog(param);
                    }
                });
    }

    public static void NotificationContentHook(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
        //TODO MODIFY CONTENT OF NOTIFICATION IF ANY
        findAndHookMethod(NotificationManager.class, "notify", String.class, int.class, Notification.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Notification notification = (Notification) param.args[2];

                    Context context = (Context) getObjectField(param.thisObject, "mContext");
                    String appName = context.getPackageManager().getApplicationInfo(loadPackageParam.packageName, 0).loadLabel(context.getPackageManager()).toString();
                    Resources modRes = context.getPackageManager().getResourcesForApplication("com.athul.nightwing");
                    String replacement = modRes.getString(modRes.getIdentifier("notification_hidden_by_maxlock", "string", "com.athul.nightwing"));
                    Notification.Builder b = new Notification.Builder(context).setContentTitle(appName).setContentText(replacement);
                    notification.contentView = b.build().contentView;
                    notification.bigContentView = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        notification.headsUpContentView = null;
                    notification.tickerText = replacement;
                Intent notificationIntent = new Intent(context, Splash.class);
                PendingIntent contentIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, notificationIntent, 0);

                ((Notification)param.args[2]).contentIntent=contentIntent;
                ((Notification)param.args[2]).contentView=null;

                }

        });
    }

    public static void hookAppLaunching(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        findAndHookMethod(Activity.class, "onStart", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                final Activity activity = (Activity) param.thisObject;
                String activityName = activity.getClass().getName();
                Log.e("WTKLV","LOCKED PACKAGE ACCESS "+activityName);
                activity.finish();

            }
        });
    }

    public static void playStoreHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {

        try {
            Field f = loadPackageParam.classLoader.getClass().getDeclaredField("classes");
            f.setAccessible(true);

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Vector<Class> classes =  (Vector<Class>) f.get(classLoader);
            for (Class classFile:classes){
                Log.e("WTKLV",classFile.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public  boolean belowLollipop(){

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }else {
            return false;
        }
    }
}
