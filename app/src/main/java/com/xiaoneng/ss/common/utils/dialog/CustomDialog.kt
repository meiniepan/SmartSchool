package com.xiaoneng.ss.common.utils.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xiaoneng.ss.R

/**
 * Created by Burning on 2016/6/22.
 */
class CustomDialog : Dialog {
    constructor(context: Context?) : super(context!!) {
        setCancelable(false)
    }

    constructor(context: Context?, theme: Int) : super(context!!, theme) {
        setCancelable(false)
    }

    class Builder {
        private var color = 0
        private var fromIndex = 0
        private var toIndex = 0
        private var buttonNum = 0
        private var context: Context
        private var title: String? = null
        private var message: String? = null
        private var positiveButtonText: String? = null
        private var negativeButtonText: String? = null
        private var contentView: View? = null
        private var positiveButtonClickListener: DialogInterface.OnClickListener? = null
        private var negativeButtonClickListener: DialogInterface.OnClickListener? = null

        constructor(context: Context) {
            this.context = context
        }

        constructor(context: Context, buttonNum: Int) {
            this.buttonNum = buttonNum
            this.context = context
        }


        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }


        fun setTitle(title: String?): Builder {
            this.title = title
            return this
        }

        fun setContentView(v: View?): Builder {
            contentView = v
            return this
        }


        fun setPositiveButton(
            positiveButtonText: String?,
            listener: DialogInterface.OnClickListener?
        ): Builder {
            this.positiveButtonText = positiveButtonText
            positiveButtonClickListener = listener
            return this
        }



        fun setNegativeButton(
            negativeButtonText: String?,
            listener: DialogInterface.OnClickListener?
        ): Builder {
            this.negativeButtonText = negativeButtonText
            negativeButtonClickListener = listener
            return this
        }


        /*
        * Dialog的初始化
        * */
        fun create(): CustomDialog {
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialog = CustomDialog(context, R.style.Dialog)
            dialog.setCanceledOnTouchOutside(true)
            val layout: View
            when (buttonNum) {
                1 -> { //只有一个按钮的提示框
                    layout = inflater.inflate(R.layout.dialog_tip_i_know_layout, null)
                    dialog.addContentView(
                        layout,
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    )
                    val mBtn = layout.findViewById<TextView>(R.id.negativeButton)
                    if (negativeButtonText != null) {
                        mBtn.setOnClickListener {
                            negativeButtonClickListener!!.onClick(
                                dialog,
                                DialogInterface.BUTTON_POSITIVE
                            )
                        }
                    } else {
                        mBtn.setOnClickListener {
                            dialog.dismiss()
                        }
                    }

                    title?.let {
                        val tvTitle = layout.findViewById<TextView>(R.id.tv_title)
                        tvTitle.text = it
                    }
                    message?.let {
                        val tvMsg = layout.findViewById<TextView>(R.id.message)
                        tvMsg.text = it
                    }
                }
                2 -> {
                    layout = inflater.inflate(R.layout.dialog_tip_two_btn_layout, null)
                    dialog.addContentView(
                        layout,
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    )

                    val mPositiveBtn = layout.findViewById<TextView>(R.id.positiveButton)
                    mPositiveBtn.visibility = View.VISIBLE
                    positiveButtonText?.let {
                        mPositiveBtn.text = positiveButtonText
                    }
                    if (positiveButtonClickListener != null) {
                        mPositiveBtn.setOnClickListener {
                            positiveButtonClickListener!!.onClick(
                                dialog,
                                DialogInterface.BUTTON_POSITIVE
                            )
                        }
                    }
                    val mNegativeBtn = layout.findViewById<TextView>(R.id.negativeButton)
                    mNegativeBtn.visibility = View.VISIBLE
                    negativeButtonText?.let {

                        mNegativeBtn.text = negativeButtonText
                    }
                    if (negativeButtonClickListener != null) {
                        mNegativeBtn.setOnClickListener {
                            negativeButtonClickListener!!.onClick(
                                dialog,
                                DialogInterface.BUTTON_NEGATIVE
                            )
                        }
                    } else {
                        mNegativeBtn.setOnClickListener {
                            dialog.dismiss()
                        }
                    }

                    //set the title
                    title?.let {
                        val tvTitle = layout.findViewById<TextView>(R.id.tv_title)
                        tvTitle.text = it
                    }
                    message?.let {
                        val tvMsg = layout.findViewById<TextView>(R.id.message)
                        tvMsg.text = it
                    }
                }
                else -> {
                    layout = inflater.inflate(R.layout.dialog_tip_two_btn_layout, null)
                }
            }


            dialog.setContentView(layout)
            return dialog
        }
    }
}