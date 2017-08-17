package com.athul.nightwing.module;

import android.app.Activity;
import android.content.res.XModuleResources;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.athul.nightwing.R;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
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
        Log.e("FOUND","METHOD_ATHUL");
        XposedHelpers.findAndHookMethod("com.android.settings.Settings", loadPackageParam.classLoader, "insertAccountsHeaders", List.class, int.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                        XposedBridge.log("method replacement ran!");
                        Log.e("FOUND","METHOD_TWEAK");
                        PreferenceActivity activity = (PreferenceActivity) methodHookParam.thisObject;
                        List<PreferenceActivity.Header> headers = (List<PreferenceActivity.Header>) methodHookParam.args[0];
                        int accountButton = findHeaderIndex(activity, headers, "account_add");
                        headers.remove(accountButton-1);
                        int accountHead = findHeaderIndex(activity, headers, "account_settings");
                        headers.remove(accountHead-1);
                        return methodHookParam.args[1];
                    }
                });
    }

    public static int findHeaderIndex(Activity activity, List<PreferenceActivity.Header> headers, String headerName) {
        int headerIndex = -1;
        int resId = activity.getResources().getIdentifier(headerName, "id", activity.getPackageName());
        if (resId != 0) {
            int i = 0;
            int size = headers.size();
            while (i < size) {
                PreferenceActivity.Header header = headers.get(i);
                int id = (int) header.id;
                if (id == resId) {
                    headerIndex = i + 1;
                    break;
                }
                i++;
            }
        }
        return headerIndex;
    }
}
