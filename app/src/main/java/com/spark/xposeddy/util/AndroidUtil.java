package com.spark.xposeddy.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AndroidUtil {

    public static int getVersionCode(Context context) {
        int versionCode = 0;

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    public static String getVersionName(Context context) {
        String versionName = null;

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    public static int getVersionCode(Context context, String packageName) {
        int versionCode = 0;

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    public static String getVersionName(Context context, String packageName) {
        String versionName = null;

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    public static long getApkFirstInstallTime(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            try {
                SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                TraceUtil.e("firstInstallTime = " + data.format(new Date(packageInfo.firstInstallTime)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return packageInfo.firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static long getApkLastUpdateTime(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            try {
                SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                TraceUtil.e("lastUpdateTime = " + data.format(new Date(packageInfo.lastUpdateTime)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return packageInfo.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * ?????????debug??????(?????????release??????apk???debug???false???????????????eclipse???????????????debug???true)
     * ???????????????????????? AndroidManifest.xml ???application ???????????? debuggable ??????????????????debuggable??????
     *
     * @param context
     * @return
     */
    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

    public static boolean isInstall(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }

        //??????packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //???????????????????????????????????????
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //??????????????????????????????????????????
        List<String> packageNames = new ArrayList<>();
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //??????packageNames?????????????????????????????????
        return packageNames.contains(packageName);
    }

    public static boolean installApp(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        }

        return false;
    }

}
