package com.zaidi.lib

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.zaidi.lib.dailog.PermantlyDeniedDailog

object PermissionsUtil {


    private var dialogModel:DialogModel?= DialogModel()
    fun PermissionSetup(
        isDialogEnable: Boolean? = true,
        dialogBackgroundColor: Int? = null,
        dialogTxtColor: Int? = null,
        dialogTxtTitle: String? = null,
        dialogTxtDescription: String? = null,
        dialogCancelBg: Int? = null,
        dialogSettingBg: Int? = null
    ) {
        dialogModel= DialogModel(isDialogEnable,dialogBackgroundColor,dialogTxtColor,dialogTxtTitle,dialogTxtDescription,dialogCancelBg,dialogSettingBg)

    }

    @JvmInline
    value class Permissions(val result: ActivityResultLauncher<Array<String>>)

    @JvmInline
    value class Permission(val result: ActivityResultLauncher<String>)
    sealed class PermissionState {
        object Granted : PermissionState()
        object Denied : PermissionState()
        object PermanentlyDenied : PermissionState()
    }

    private fun getPermissionState(
        activity: Activity,
        result: Map<String, Boolean>,
    ): PermissionState {
        val deniedList = result.filter {
            it.value.not()
        }.map {
            it.key
        }

        var state = when (deniedList.isEmpty()) {
            false -> PermissionState.Denied
            true -> PermissionState.Granted
        }

        if (state == PermissionState.Denied) {
            val permanentlyMappedList = deniedList.map {
                ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
            }
            if (permanentlyMappedList.contains(false)) {
                state = PermissionState.PermanentlyDenied
                if (dialogModel==null){
                    dialogModel=DialogModel()
                    if (dialogModel?.isDialogEnable==true){
                        PermantlyDeniedDailog.show(activity, dialogModel!!) {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri: Uri = Uri.fromParts("package", activity.packageName, null)
                            intent.data = uri
                            ContextCompat.startActivity(activity, intent, null)
                        }
                    }
                }else{
                    if (dialogModel?.isDialogEnable==true){
                        PermantlyDeniedDailog.show(activity, dialogModel!!) {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri: Uri = Uri.fromParts("package", activity.packageName, null)
                            intent.data = uri
                            ContextCompat.startActivity(activity, intent, null)
                        }
                    }
                }

            }
        }

        return state
    }

    private fun getPermissionState(
        activity: Activity,
        permissionGranted: Boolean,
        permission: String,
    ): PermissionState {
        var state = if (permissionGranted) {
            PermissionState.Granted
        } else {
            PermissionState.Denied
        }

        if (!permissionGranted) {
            val permanentlyDenied =
                !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
            if (permanentlyDenied) {
                state = PermissionState.PermanentlyDenied
                if (dialogModel==null){
                    dialogModel= DialogModel()
                    if (dialogModel?.isDialogEnable==true){
                        PermantlyDeniedDailog.show(activity, dialogModel!!) {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri: Uri = Uri.fromParts("package", activity.packageName, null)
                            intent.data = uri
                            ContextCompat.startActivity(activity, intent, null)
                        }
                    }
                }else{
                    if (dialogModel?.isDialogEnable==true){
                        PermantlyDeniedDailog.show(activity, dialogModel!!) {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri: Uri = Uri.fromParts("package", activity.packageName, null)
                            intent.data = uri
                            ContextCompat.startActivity(activity, intent, null)
                        }
                    }
                }

            }
        }

        return state
    }

    fun AppCompatActivity.registerPermissions(permissionResult: (PermissionState) -> Unit): Permissions {
        return Permissions(registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            permissionResult(getPermissionState(this, it))
        })
    }

    fun Fragment.registerPermissions(permissionResult: (PermissionState) -> Unit): Permissions {
        return Permissions(registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            permissionResult(getPermissionState(requireActivity(), it))
        })
    }

    fun AppCompatActivity.registerPermission(
        permissionResult: (PermissionState) -> Unit,
        permission: String,
    ): Permission {
        return Permission(registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            permissionResult(getPermissionState(this, it, permission))
        })
    }

    fun Fragment.registerPermission(
        permissionResult: (PermissionState) -> Unit,
        permission: String,
    ): Permission {
        return Permission(registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            permissionResult(getPermissionState(requireActivity(), it, permission))
        })
    }

    fun Permissions.launchMultiplePermission(permissionList: Array<String>) {
        this.result.launch(permissionList)
    }

    fun Permission.launchSinglePermission(permission: String) {
        this.result.launch(permission)
    }
}