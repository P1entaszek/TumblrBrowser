package com.prod.tumblrbrowser.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mikhaellopez.circularimageview.CircularImageView
import java.util.*

/**
 * Created by Piotr Jaszczurowski on 14.01.2020.
 */
object Utils {

    fun hideKeyboard(context: Context, v: View?) {
        if (v != null) {
            val keyboard =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            Objects.requireNonNull(keyboard).hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun showKeyboard(context: Context, v: View?) {
        if (v != null) {
            val keyboard =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            Objects.requireNonNull(keyboard).showSoftInput(v, 0)
        }
    }

    fun setImageFromUrl(context: Context, imageUrl: String, img: CircularImageView) {
        Glide
            .with(context)
            .load(imageUrl)
            .centerCrop()
            .into(img)
    }

    fun isInternetConnectionAvailable(activity: AppCompatActivity):Boolean{
        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

}