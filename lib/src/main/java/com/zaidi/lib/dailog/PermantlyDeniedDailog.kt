package com.zaidi.lib.dailog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import com.zaidi.lib.DialogModel
import com.zaidi.lib.R
import com.zaidi.lib.databinding.DenidDailogBinding

object PermantlyDeniedDailog {
    fun show(context: Context,dialogModel: DialogModel,openSettings:()->Unit){
        val dialog = Dialog(context, R.style.Theme_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        var bind:DenidDailogBinding= DenidDailogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(bind.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bind.root.setBackgroundResource(dialogModel.dialogBackgroundColor?:context.resources.getColor(R.color.white))
        bind.title.text=dialogModel.dialogTxtTitle?:"Permission Denied"
        bind.title.setTextColor(dialogModel.dialogTxtColor?:R.color.black)
        bind.etNote.setTextColor(dialogModel.dialogTxtColor?:R.color.black)
        bind
       bind. btnDiscardCancel.setOnClickListener {
            dialog.dismiss()
        }
        bind.btnDiscard.setOnClickListener {
            dialog.dismiss()
            openSettings.invoke()
        }
        dialog.show()
    }
}