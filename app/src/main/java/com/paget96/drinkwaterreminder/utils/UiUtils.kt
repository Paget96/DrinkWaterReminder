package com.paget96.drinkwaterreminder.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ResolveInfo
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.transition.TransitionManager
import android.util.AndroidRuntimeException
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.paget96.drinkwaterreminder.BuildConfig
import com.paget96.drinkwaterreminder.R

object UiUtils {


    /**
     * Returns a color from an attribute reference.
     *
     * @param context Pass the activity context, not the application context
     * @param attr    The attribute reference to be resolved
     *
     * @return int array of color value
     */
    @ColorInt
    fun getColorFromAttr(context: Context, @AttrRes attr: Int): Int {
        return with(TypedValue()) {
            context.theme.resolveAttribute(attr, this, true)
            this.data
        }
    }

    /**
     * Create gradient color
     * GradientDrawable.Orientation.TOP_BOTTOM
     */
    fun createGradientDrawable(
        startColor: Int,
        endColor: Int,
        orientation: GradientDrawable.Orientation
    ): GradientDrawable {
        val gradientDrawable = GradientDrawable(
            orientation,
            intArrayOf(
                startColor,
                endColor
            )
        )
        gradientDrawable.cornerRadius = 0f

        return gradientDrawable
    }

    fun visibilityGoneWithAnimation(constraintLayoutHolder: ConstraintLayout, view: View) {
        TransitionManager.beginDelayedTransition(constraintLayoutHolder)
        view.visibility = View.GONE
    }

    fun visibilityVisibleWithAnimation(constraintLayoutHolder: ConstraintLayout, view: View) {
        TransitionManager.beginDelayedTransition(constraintLayoutHolder)
        view.visibility = View.VISIBLE
    }

    /**
     * Use this method to open a browser link
     *
     */
    private fun openBrowserLink(context: Context, link: String?) = try {
        val uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No Internet Browser installed", Toast.LENGTH_LONG).show()
    }

    // Parses and open links.
    @JvmStatic
    fun openLink(context: Context, link: String?, useCustomTabs: Boolean) {
        if (useCustomTabs) {
            showCusomTabs(context, Uri.parse(link))
        } else {
            openBrowserLink(context, link)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun launchNativeApi30(context: Context, uri: Uri?): Boolean {
        val nativeAppIntent = Intent(Intent.ACTION_VIEW, uri)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
            )
        return try {
            context.startActivity(nativeAppIntent)
            true
        } catch (ex: ActivityNotFoundException) {
            false
        }
    }

    private fun launchNativeBeforeApi30(context: Context, uri: Uri): Boolean {
        val pm = context.packageManager

        // Get all Apps that resolve a generic url
        val browserActivityIntent = Intent()
            .setAction(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setData(Uri.fromParts("http", "", null))
        val genericResolvedList: Set<String> = extractPackageNames(
            pm.queryIntentActivities(browserActivityIntent, 0)
        )

        // Get all apps that resolve the specific Url
        val specializedActivityIntent = Intent(Intent.ACTION_VIEW, uri)
            .addCategory(Intent.CATEGORY_BROWSABLE)
        val resolvedSpecializedList = extractPackageNames(
            pm.queryIntentActivities(specializedActivityIntent, 0)
        )

        // Keep only the Urls that resolve the specific, but not the generic
        // urls.
        resolvedSpecializedList.removeAll(genericResolvedList)

        // If the list is empty, no native app handlers were found.
        if (resolvedSpecializedList.isEmpty()) {
            return false
        }

        // We found native handlers. Launch the Intent.
        specializedActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(specializedActivityIntent)
        return true
    }

    fun getNativeAppPackage(context: Context, uri: Uri?): Set<String> {
        val pm = context.packageManager

        //Get all Apps that resolve a generic url
        val browserActivityIntent = Intent(Intent.ACTION_VIEW, uri)
        val genericResolvedList: Set<String> =
            extractPackageNames(pm.queryIntentActivities(browserActivityIntent, 0))

        //Get all apps that resolve the specific Url
        val specializedActivityIntent = Intent(Intent.ACTION_VIEW, uri)
        val resolvedSpecializedList =
            extractPackageNames(pm.queryIntentActivities(specializedActivityIntent, 0))

        //Keep only the Urls that resolve the specific, but not the generic urls
        resolvedSpecializedList.removeAll(genericResolvedList)
        return resolvedSpecializedList
    }

    private fun extractPackageNames(resolveInfos: List<ResolveInfo>): MutableSet<String> {
        val packageNameSet: MutableSet<String> = HashSet()
        for (ri in resolveInfos) {
            packageNameSet.add(ri.activityInfo.packageName)
        }
        return packageNameSet
    }

    private fun showCusomTabs(context: Context?, uri: Uri?) {
        val launched = if (Build.VERSION.SDK_INT >= 30)
            launchNativeApi30(context!!, uri)
        else
            launchNativeBeforeApi30(context!!, uri!!)

        if (!launched) {
            try {
                CustomTabsIntent.Builder()
                    .build()
                    .launchUrl(context, uri!!)
            } catch (e: ActivityNotFoundException) {
                openBrowserLink(context, uri.toString())
            }
        }
    }

    @JvmStatic
    fun hideKeyboard(activity: Activity, view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }

    fun expandView(
        constraintLayoutHolder: ConstraintLayout,
        expandedLayout: View,
        animateArrow: View
    ) {
        TransitionManager.beginDelayedTransition(constraintLayoutHolder)
        expandedLayout.visibility = View.VISIBLE
        try {
            animateArrow.animate().rotation(180f).setDuration(500).start()
        } catch (are: AndroidRuntimeException) {
            are.printStackTrace()
        }
    }

    fun collapseView(
        constraintLayoutHolder: ConstraintLayout,
        expandedLayout: View,
        animateArrow: View
    ) {
        TransitionManager.beginDelayedTransition(constraintLayoutHolder)
        expandedLayout.visibility = View.GONE
        try {
            animateArrow.animate().rotation(0f).setDuration(500).start()
        } catch (are: AndroidRuntimeException) {
            are.printStackTrace()
        }
    }

    @JvmStatic
    fun expandCollapseView(
        constraintLayoutHolder: ConstraintLayout,
        expandedLayout: View,
        animateArrow: View
    ) {
        if (expandedLayout.isShown) {
            collapseView(constraintLayoutHolder, expandedLayout, animateArrow)
        } else {
            expandView(constraintLayoutHolder, expandedLayout, animateArrow)
        }
    }

    @JvmStatic
    fun createExplanationDialog(context: Context?, title: Int, message: Int) {
        val mBottomSheetDialog = BottomSheetDialog(context!!)
        mBottomSheetDialog.dismissWithAnimation = true
        val inflater = LayoutInflater.from(context)
        val sheetView = inflater.inflate(R.layout.bottom_sheet_explanation_dialog, null, false)
        mBottomSheetDialog.setContentView(sheetView)

        val titleTv: TextView? = mBottomSheetDialog.findViewById(R.id.tv_title)
        titleTv!!.setText(title)

        val messageTv: TextView? = mBottomSheetDialog.findViewById(R.id.tv_detail)
        messageTv!!.setText(message)

        val ok: MaterialButton? = mBottomSheetDialog.findViewById(R.id.button_ok)
        ok!!.setOnClickListener { mBottomSheetDialog.dismiss() }
        mBottomSheetDialog.show()
    }

    // This part of code handles app rate dialog which will appear inside app
    @JvmStatic
    fun rateApp(context: Context, sharedPreferences: SharedPreferences) {
        var totalCount = sharedPreferences.getInt("counter", 0)
        totalCount++
        sharedPreferences.edit().putInt("counter", totalCount).apply()
        if (!sharedPreferences.getBoolean("app_rate", false) && totalCount % 10 == 0) {
            val mBottomSheetDialog = BottomSheetDialog(context)
            mBottomSheetDialog.dismissWithAnimation = true

            val inflater = LayoutInflater.from(context)
            val sheetView = inflater.inflate(R.layout.bottom_sheet_rate_app, null)
            mBottomSheetDialog.setContentView(sheetView)

            val rate: MaterialButton? = mBottomSheetDialog.findViewById(R.id.rate)
            val later: MaterialButton? = mBottomSheetDialog.findViewById(R.id.later)

            rate?.setOnClickListener {
                val uri = Uri.parse("market://details?id=" + context.packageName)
                val openPlayStore = Intent(Intent.ACTION_VIEW, uri)
                openPlayStore.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                try {
                    context.startActivity(openPlayStore)
                } catch (e: ActivityNotFoundException) {
                    openLink(context, Links.MAIN_STORE_LINK, true)
                }
                sharedPreferences.edit().putBoolean("app_rate", true).apply()
                mBottomSheetDialog.dismiss()
            }

            later?.setOnClickListener {
                sharedPreferences.edit().putBoolean("app_rate", false).apply()
                sharedPreferences.edit().putInt("counter", 0).apply()
                mBottomSheetDialog.dismiss()
            }
            mBottomSheetDialog.show()
        }
    }

    @JvmStatic
    fun showWhatsNew(context: Context?, sharedPreferences: SharedPreferences) {
        if (sharedPreferences.getString("app_version", "v1.0") != BuildConfig.VERSION_NAME) {
            val mBottomSheetDialog = BottomSheetDialog(context!!)
            mBottomSheetDialog.dismissWithAnimation = true
            val inflater = LayoutInflater.from(context)

            val sheetView = inflater.inflate(R.layout.bottom_sheet_explanation_dialog, null, false)
            mBottomSheetDialog.setContentView(sheetView)

            val titleTv: TextView? = mBottomSheetDialog.findViewById(R.id.tv_title)
            titleTv!!.setText(R.string.whats_new)

            val messageTv: TextView? = mBottomSheetDialog.findViewById(R.id.tv_detail)
            messageTv!!.setText(R.string.whats_new_list)

            val ok: MaterialButton? = mBottomSheetDialog.findViewById(R.id.button_ok)
            ok!!.setOnClickListener { mBottomSheetDialog.dismiss() }
            mBottomSheetDialog.setOnDismissListener {
                sharedPreferences.edit().putString("app_version", BuildConfig.VERSION_NAME).apply()
            }
            mBottomSheetDialog.show()
        }
    }
}