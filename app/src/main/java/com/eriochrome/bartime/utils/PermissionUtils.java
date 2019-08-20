package com.eriochrome.bartime.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PermissionUtils {

    private Context context;
    private AppCompatActivity activityActual;

    private PermissionResultCallback permissionResultCallback;


    private ArrayList<String> listaPermisos =new ArrayList<>();
    private ArrayList<String> listaPermisosNecesarios =new ArrayList<>();

    private String contenidoDialogo = "";
    private int requestCode;

    public PermissionUtils(Context context) {
        this.context=context;
        this.activityActual = (AppCompatActivity) context;
        permissionResultCallback= (PermissionResultCallback) context;
    }

    public PermissionUtils(Context context, PermissionResultCallback callback) {
        this.context = context;
        this.activityActual = (AppCompatActivity) context;
        permissionResultCallback= callback;
    }


    /**
     * Check the API Level & Permission
     */
    public void check_permission(ArrayList<String> permissions, String contenidoDialogo, int requestCode) {
        this.listaPermisos = permissions;
        this.contenidoDialogo = contenidoDialogo;
        this.requestCode = requestCode;

        if(Build.VERSION.SDK_INT >= 23) {
            if (checkAndRequestPermissions(permissions, requestCode)) {
                permissionResultCallback.permissionGranted(requestCode);
                Log.i("all permissions", "granted");
                Log.i("proceed", "to callback");
            }
        }
        else {
            permissionResultCallback.permissionGranted(requestCode);
            Log.i("all permissions", "granted");
            Log.i("proceed", "to callback");
        }

    }


    /**
     * Check and request the Permissions
     */

    private  boolean checkAndRequestPermissions(ArrayList<String> permissions,int request_code) {
        if(permissions.size()>0) {
            listaPermisosNecesarios = new ArrayList<>();
            for(int i=0;i<permissions.size();i++) {
                int hasPermission = ContextCompat.checkSelfPermission(activityActual,permissions.get(i));
                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    listaPermisosNecesarios.add(permissions.get(i));
                }
            }
            if (!listaPermisosNecesarios.isEmpty()) {
                ActivityCompat.requestPermissions(activityActual, listaPermisosNecesarios.toArray(new String[listaPermisosNecesarios.size()]),request_code);
                return false;
            }
        }
        return true;
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if(grantResults.length>0) {
                    Map<String, Integer> perms = new HashMap<>();
                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                    }
                    final ArrayList<String> pending_permissions=new ArrayList<>();
                    for (int i = 0; i < listaPermisosNecesarios.size(); i++) {
                        if (perms.get(listaPermisosNecesarios.get(i)) != PackageManager.PERMISSION_GRANTED) {
                            if(ActivityCompat.shouldShowRequestPermissionRationale(activityActual, listaPermisosNecesarios.get(i)))
                                pending_permissions.add(listaPermisosNecesarios.get(i));
                            else {
                                Log.i("Go to settings","and enable permissions");
                                permissionResultCallback.neverAskAgain(this.requestCode);
                                Toast.makeText(activityActual, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                    }

                    if(pending_permissions.size()>0) {
                        showMessageOKCancel(contenidoDialogo,
                                (dialog, which) -> {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            check_permission(listaPermisos, contenidoDialogo, this.requestCode);
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Log.i("permisson","not fully given");
                                            if(listaPermisos.size() == pending_permissions.size())
                                                permissionResultCallback.permissionDenied(this.requestCode);
                                            else
                                                permissionResultCallback.partialPermissionGranted(this.requestCode, pending_permissions);
                                            break;
                                    }
                                });
                    }
                    else  {
                        Log.i("all","permissions granted");
                        Log.i("proceed","to next step");
                        permissionResultCallback.permissionGranted(this.requestCode);
                    }
                }
                break;
        }
    }


    /**
     * Explain why the app needs permissions
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activityActual)
                .setMessage(message)
                .setPositiveButton("Ok", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    public interface PermissionResultCallback
    {
        void permissionGranted(int requestCode);
        void partialPermissionGranted(int requestCode, ArrayList<String> granted_permissions);
        void permissionDenied(int requestCode);
        void neverAskAgain(int requestCode);
    }
}
