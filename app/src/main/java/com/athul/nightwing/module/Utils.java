package com.athul.nightwing.module;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AndroidAppHelper;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.XModuleResources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.athul.nightwing.R;
import com.athul.nightwing.activities.ProhibitiedActivity;
import com.athul.nightwing.beans.CustomObject;



import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
                       Log.e("FINISH","FOUND METHOD");
                       Activity act = (Activity)param.thisObject;
                       act.finish();
                       Log.e("FINISH","YOU ARE NOT ALLOWED");
                   }

                   @Override
                   protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                       Gson gson=new Gson();
                      /* for(Object object: (List<DashboardCategory>)param.args[0]){

                           Log.e("HELL",gson.toJson(object).toString());
                           Log.e("CATE", ((DashboardCategory) object).getTile(0).fragment);
                       } */

                   }
               }



       );


    }


    public static void restrictAppUninstallation(final XC_LoadPackage.LoadPackageParam loadPackageParam) {

       /* XposedHelpers.findAndHookMethod("android.content.pm.PackageInstaller", loadPackageParam.classLoader,
                "uninstall", String.class, IntentSender.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.e("OMKV","FOUND METHOD AND HOOKING");
                        XposedBridge.log("FOUND METHOD");
                        String packageName=(String)param.args[0];
                        if(packageName.equals("me.entri.entrime")){
                            Log.e("OMKV","PACKAGE FOUND");
                            XposedBridge.log("FOUND METHOD");
                            Activity act = (Activity)param.thisObject;
                            act.finish();
                            param.args[0]=null;
                            return;
                        }
                    }
                });  */

       XposedHelpers.findAndHookMethod("com.android.packageinstaller.UninstallerActivity", loadPackageParam.classLoader,
               "onCreate", Bundle.class, new XC_MethodHook() {

                   @Override
                   protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                       final Activity act = (Activity)param.thisObject;
                       Uri packageURI = act.getIntent().getData();
                       String packageName = packageURI.getEncodedSchemeSpecificPart();
                       Log.e("OMKV",packageName);
                       if(packageName.equals("me.entri.entrime")){
                           Log.e("OMKV","INSIDE PACKAGE");

                           try{
                               Context context = (Context) AndroidAppHelper.currentApplication();
                               Intent dialogIntent=new Intent();
                               dialogIntent.setComponent(new ComponentName("com.athul.nightwing","com.athul.nightwing.activities.ProhibitiedActivity"));
                               dialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               act.startActivity(dialogIntent);
                               act.finish();
                           }catch (Exception e){
                               Log.e("OMKV",e.getMessage());
                           }
                           act.finish();

                         /* try {
                              Log.e("OMKV","BUILDING CLASS");
                              AlertDialog.Builder builder= new AlertDialog.Builder(context.getApplicationContext());
                              builder.setTitle("#OMKV");
                              builder.setMessage("Not allowed");
                              builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {
                                      dialog.dismiss();
                                  }
                              }).show();
                              act.finish();
                          }
                          catch (Exception e){
                              Log.e("OMKV",e.toString());
                          }*/
                       }
                   }

               });



    }
}
