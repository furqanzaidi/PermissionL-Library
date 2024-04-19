package com.example.permissionlibrary

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.permissionlibrary.databinding.ActivityMainBinding
import com.zaidi.lib.PermissionsUtil
import com.zaidi.lib.PermissionsUtil.launchMultiplePermission
import com.zaidi.lib.PermissionsUtil.launchSinglePermission
import com.zaidi.lib.PermissionsUtil.registerPermission
import com.zaidi.lib.PermissionsUtil.registerPermissions


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding?=null
    private var multiplePermissionLauncher: PermissionsUtil.Permissions?=null
    private var singlePermissionLauncher: PermissionsUtil.Permission?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        PermissionsUtil.PermissionSetup(true,dialogBackgroundColor = R.drawable.card_bg, dialogTxtTitle = "Denied Permission", dialogTxtColor = resources.getColor(R.color.white))
        binding?.multiplePermission?.setOnClickListener {
           multiplePermissionLauncher?.launchMultiplePermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION))
        }
        binding?.singlePermission?.setOnClickListener {
            singlePermissionLauncher?.launchSinglePermission(Manifest.permission.CAMERA)
        }

        multiplePermissionLauncher = registerPermissions {
            when(it){
                PermissionsUtil.PermissionState.Denied -> {
                    Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show()
                }
                PermissionsUtil.PermissionState.Granted -> {
                    Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show()
                }
                PermissionsUtil.PermissionState.PermanentlyDenied -> {
                    Toast.makeText(this, "Permantly denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
        singlePermissionLauncher = registerPermission({
            when(it){
                PermissionsUtil.PermissionState.Denied -> {
                    Toast.makeText(this,"Denied",Toast.LENGTH_SHORT).show()
                }
                PermissionsUtil.PermissionState.Granted -> {
                    Toast.makeText(this,"Granted",Toast.LENGTH_SHORT).show()
                }
                PermissionsUtil.PermissionState.PermanentlyDenied -> {
                    Toast.makeText(this,"PermanentlyDenied",Toast.LENGTH_SHORT).show()
                }
            }
        },Manifest.permission.CAMERA)
    }

}