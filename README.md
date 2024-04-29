A Kotlin library which helps to request runtime permissions in Android.

An Android library totally written in Kotlin that helps to request runtime permissions. This library is compatible also below Android M (API 23) where runtime permissions doesn't exist, so you haven't to handle them separately.

**Basic usage:**
First you have to create object of PermissionUtils.Permission than call launch permissions
**for Single permission:**
`private var singlePermissionLauncher : PermissionsUtil.Permission?=null`


`singlePermissionLauncher = registerPermission({
         
          when(it){
           
              `PermissionsUtil.PermissionState.Denied -> {
                
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
       
       
 Launch permission On click Listener:
 
`binding?.view?.setOnClickListener {

            singlePermissionLauncher?.launchSinglePermission(Manifest.permission.CAMERA)
            
        }`
       
       
 **for Multiple permissions:**
 
 `private var multiplePermissionLauncher: PermissionsUtil.Permissions?=null`
 
`multiplePermissionLauncher = registerPermissions {when(it){`
            
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
            
        }`
  
 **NOTE:**
 
 request all file permission call same as you request single permission:
 
 **Extras:**
 
 If user permanently denied permission that a popup dialog appear which navigate user to the app settings
 
 if you want that dialog does not appear that just call
 
 `PermissionsUtil.PermissionSetup(false)`
 
 
