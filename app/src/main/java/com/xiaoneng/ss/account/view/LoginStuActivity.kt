package com.xiaoneng.ss.account.view

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.view.View
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseActivity
import kotlinx.android.synthetic.main.activity_login_stu.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/12 6:51 PM
 */
class LoginStuActivity : BaseActivity(), View.OnClickListener {


    private var isHideFirst: Boolean = true

    override fun getLayoutId() = R.layout.activity_login_stu
    override fun initView() {
        super.initView()
        tv_stu_register.setOnClickListener(this)
        iv_eye.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_stu_register -> {

            }
            R.id.iv_eye -> {
                if (isHideFirst) {
                    iv_eye.setImageResource(R.drawable.ic_eye);
                    //密文
                    var method1: HideReturnsTransformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    et_pwd.transformationMethod = method1;
                    isHideFirst = false;
                } else {
                    iv_eye.setImageResource(R.drawable.ic_eye_no);
                    //密文
                    var method: TransformationMethod = PasswordTransformationMethod.getInstance();
                    et_pwd.transformationMethod = method;
                    isHideFirst = true;

                }
                // 光标的位置

                // 光标的位置
                val index: Int = et_pwd.getText().toString().length
                et_pwd.setSelection(index)
            }

        }
    }

}