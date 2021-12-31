package com.mujeeb.weatherapp.common

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mujeeb.weatherapp.R
import kotlinx.android.synthetic.main.dialog_custom.*


open class CustomDialog(
        ctx: Context,
        @DrawableRes private val icon: Int? = null,
        @StringRes private val title: Int,
        private val body: String = "",
        private val primaryText: String,
        private val primaryListener: (() -> Unit)? = null,
        private val secondaryText: String? = null,
        private val secondaryListener: (() -> Unit)? = null
) : Dialog(ctx) {

    @DrawableRes
    var primaryButtonBackground: Int = R.drawable.button_sea_blue_solid_rounded

    @ColorRes
    var primaryButtonTextColor: Int = R.color.button_text_white

    constructor(ctx: Context,
                @DrawableRes icon: Int?,
                @StringRes title: Int,
                body: String = "",
                @StringRes primaryText: Int,
                primaryListener: (() -> Unit)? = null,
                @StringRes secondaryText: Int? = null,
                secondaryListener: (() -> Unit)? = null) :
            this(ctx, icon, title, body, ctx.getString(primaryText), primaryListener,
                    if (secondaryText == null) null else ctx.getString(secondaryText),
                secondaryListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_custom)

        icon?.let { icon_image_view.setImageResource(it) }
        title_text_view.setText(title)
        body_text_view.text = body

        primary_button.visibility = View.VISIBLE
        primary_button.text = primaryText
        primary_button.setOnClickListener {
            dismiss()
            primaryListener?.invoke()
        }

        primary_button.background = context.getDrawable(primaryButtonBackground)
        primary_button.setTextColor(context.getColor(primaryButtonTextColor))

        if (secondaryText != null) {
            secondary_button.visibility = View.VISIBLE
            secondary_button.text = secondaryText
            secondary_button.setOnClickListener {
                dismiss()
                secondaryListener?.invoke()
            }
        }
    }

    fun show(onDismiss: () -> Unit) {
        setOnDismissListener { onDismiss() }
        show()
    }

    companion object {


        fun showSomethingWentWrongDialog(
            context: Context,
            onDismiss: (() -> Unit)? = null
        ) {
            val dialog = CustomDialog(
                ctx = context,
                icon = null,
                title = R.string.something_went_error,
                primaryText = R.string.ok,
                primaryListener = null
            )
            dialog.setCanceledOnTouchOutside(false)
            dialog.show {
                onDismiss?.invoke()
            }
        }




        fun showNoNetworkConnectionDialog(
            context: Context,
            onDismissListener: (() -> Unit) = {}
        ) {
            CustomDialog(
                ctx = context,
                icon = R.drawable.ic_baseline_wifi_off_24,
                title = R.string.no_network_title,
                body = context.resources.getString(R.string.no_network_subtitle),
                primaryText = R.string.dismiss_dialog_button_text ,
            ).show {
                onDismissListener.invoke()
            }
        }


    }
}
