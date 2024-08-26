package com.fps69.abworkmanager.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.fps69.abworkmanager.R


object Utils {
    private var dialog :AlertDialog?= null

    fun showDialog(context: Context){
        dialog = AlertDialog.Builder(context).setView(R.layout.progress_dialog).setCancelable(false).create()
        dialog!!.show()
    }
    fun hideDialog(){
        dialog?.dismiss()
    }

    fun showToast(context: Context, message :String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
}

/*

1) Context Parameter (context: Context):
>>> The function takes a Context as a parameter. The Context is necessary for creating UI components
    like dialogs because it provides access to resources, themes, and other application-level information.
2) AlertDialog.Builder(context):
>>> AlertDialog.Builder is a helper class for building an AlertDialog. The context is passed to it,
    which allows the dialog to be attached to the correct window and theme.

3) setView(R.layout.progress_dialog):

>>> This method sets the content of the dialog to a custom layout. In this case, the layout file
    progress_dialog.xml (which should be located in the res/layout directory) is used. This layout
     typically contains a loading indicator, such as a ProgressBar.

4) setCancelable(false):

>>> This method ensures that the dialog cannot be dismissed by the user by pressing the back
    button or tapping outside the dialog. This is useful when you want to prevent the user from interacting
    with the app while a background operation is in progress.

5) create():

>>> The create() method builds the AlertDialog object based on the settings specified in the AlertDialog.Builder.

6) dialog!!.show():

>>> The show() method makes the dialog visible on the screen. The !! operator is used to assert
    that dialog is not null, which is safe in this context since it is being initialized
    right before this line.

**/