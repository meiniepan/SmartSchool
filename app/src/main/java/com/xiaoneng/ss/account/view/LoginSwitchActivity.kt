package com.xiaoneng.ss.account.view

import android.app.Dialog
import android.view.*
import android.widget.TextView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseActivity
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.mine.view.UserProtocolActivity
import kotlinx.android.synthetic.main.activity_login_switch.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/12 6:51 PM
 */
class LoginSwitchActivity : BaseActivity(), View.OnClickListener {

    private lateinit var dialog: Dialog
    private var isAgree: Boolean by SPreference(Constant.AGREE_PROTOCOL, false)


    override fun getLayoutId() = R.layout.activity_login_switch

    override fun initView() {
        super.initView()
        alert()
        iv_item_par.setOnClickListener(this)
        iv_item_tea.setOnClickListener(this)
        iv_item_stu.setOnClickListener(this)
    }

    private fun alert() {
        if (!isAgree) {
            initDialogClass()
            dialog.show()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_item_par -> {
                mStartActivity<LoginTeacherActivity>(this) {
                    putExtra(Constant.FLAG, false)
                }
            }
            R.id.iv_item_tea -> {
                mStartActivity<LoginTeacherActivity>(this)
            }
            R.id.iv_item_stu -> {
                mStartActivity<LoginStuActivity>(this)
            }
        }
    }

    private fun initDialogClass() {

        // 底部弹出对话框
        dialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_user_protocol, null)
        dialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            this.resources.displayMetrics.widthPixels - dp2px(32f).toInt()
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)

        var protocol = contentView.findViewById<TextView>(R.id.tvProtocol)
        var cancel = contentView.findViewById<TextView>(R.id.tvCancel)
        var confirm = contentView.findViewById<TextView>(R.id.tvConfirm)
        confirm.setOnClickListener {
            isAgree = true
            dialog.dismiss()
        }
        cancel.setOnClickListener {
            AppManager.exitApp(this)
        }
        protocol.setOnClickListener {
            mStartActivity<UserProtocolActivity>(this)
        }

    }


}