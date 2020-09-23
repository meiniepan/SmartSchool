package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.model.StudentBean
import com.xiaoneng.ss.module.school.adapter.DepartmentAdapter
import com.xiaoneng.ss.module.school.adapter.InvolvePerson2Adapter
import com.xiaoneng.ss.module.school.adapter.QueryStudentAdapter
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_involve.*
import kotlinx.android.synthetic.main.activity_add_student.etSearch

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AddInvolveActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private val tab1 = "1"
    private val tab2 = "2"
    private var currentItemId: String = ""
    private var currentTab: String = "1"
    private lateinit var mAdapterQuery: QueryStudentAdapter
    private lateinit var mAdapterDepartment: DepartmentAdapter
    private lateinit var mAdapterInvolve: InvolvePerson2Adapter
    var mDataQuery = ArrayList<StudentBean>()
    var mDataDepartment = ArrayList<DepartmentBean>()
    var mDataClasses = ArrayList<DepartmentBean>()
    var mDataDepartment2 = ArrayList<DepartmentBean>()
    var mDataClasses2 = ArrayList<DepartmentBean>()
    var mDataInvolve: MutableList<StudentBean> = ArrayList()
    var mReceiveList: MutableList<UserBeanSimple>? = ArrayList()
    var isManage = false
    lateinit var chosenDay: String


    override fun getLayoutId(): Int = R.layout.activity_add_involve


    override fun initView() {
        super.initView()
        mDataDepartment = intent.getParcelableArrayListExtra(Constant.DATA)
        mDataClasses = intent.getParcelableArrayListExtra(Constant.DATA2)
        mReceiveList = intent.getParcelableArrayListExtra(Constant.DATA3)
        mReceiveList?.let {
            if (it.size > 0) {
                it.forEach {
                    var pId = ""
                    if (it.topdepartid == "grade0") {
                        pId = tab2 + "_" + it.secdepartid
                    } else {
                        pId = tab1 + "_" + it.topdepartid
                    }
                    mDataInvolve.add(
                        StudentBean(
                            uid = it.uid ?: "",
                            realname = it.realname ?: "",
                            usertype = it.usertype ?: "",
                            topdepartid = it.topdepartid ?: "",
                            secdepartid = it.secdepartid ?: "",
                            choice = "1",
                            parentId = pId
                        )
                    )
                }
                var mMap1 = HashMap<String, ArrayList<StudentBean>>()
                var mMap2 = HashMap<String, ArrayList<StudentBean>>()
                mDataInvolve.forEach {
                    if (it.topdepartid == "grade0") {
                        if (mMap2.get(it.secdepartid) == null) {
                            var mList = ArrayList<StudentBean>()
                            mList.add(it)
                            it.secdepartid?.let { it1 -> mMap2.put(it1, mList) }
                        } else {

                            it.secdepartid?.let { it1 ->
                                var mList = mMap2.get(it1)
                                mList!!.add(it)
                                mMap2.put(it1, mList)
                            }
                        }
                    } else {
                        if (mMap1.get(it.topdepartid) == null) {
                            var mList = ArrayList<StudentBean>()
                            mList.add(it)
                            it.topdepartid?.let { it1 -> mMap1.put(it1, mList) }
                        } else {

                            it.topdepartid?.let { it1 ->
                                var mList = mMap1.get(it1)
                                mList!!.add(it)
                                mMap1.put(it1, mList)
                            }
                        }
                    }
                }
                mMap1.forEach {
                    mDataDepartment2.add(DepartmentBean(it.key, "", it.value))
                }
                mMap2.forEach {
                    mDataClasses2.add(DepartmentBean(it.key, "", it.value))
                }
                setPersonNum()
            }
        }
        initAdapterQuery()
        initTab()
        initAdapterDepart()
        initAdapterInvolve()
        etSearch.setOnEditorActionListener { teew, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if (etSearch.text.toString().isNotEmpty()) {
                        showLoading()
//                        mViewModel.queryStudent(etSearch.text.toString())
                        mViewModel.listByDepartment(realName = etSearch.text.toString())
                    }
                }

            }
            return@setOnEditorActionListener false
        }

        tvManage.apply {
            setOnClickListener {
                isManage = !isManage
                mAdapterInvolve.setManage(isManage)
                mAdapterInvolve.notifyDataSetChanged()
                setAdapterItemClick()
                if (isManage) {
                    this.text = "完成"
                } else {
                    this.text = "管理"
                }
            }
        }

        tvConfirm.setOnClickListener {
            var mIntent = intent
            mIntent.putParcelableArrayListExtra(Constant.DATA, mDataDepartment)
            mIntent.putParcelableArrayListExtra(Constant.DATA2, mDataClasses)
            setResult(Activity.RESULT_OK, mIntent)
            finish()
        }

    }

    override fun initData() {
        super.initData()
        if (mDataDepartment.size > 0 || mDataClasses.size > 0) {
            mAdapterDepartment.notifyDataSetChanged()
            mDataDepartment.forEach {
                if (it.list.size > 0) {
                    mDataInvolve.addAll(it.list)
                }
            }
            mDataClasses.forEach {
                if (it.list.size > 0) {
                    mDataInvolve.addAll(it.list)
                }
            }
            mAdapterInvolve.notifyDataSetChanged()
            setPersonNum()
        } else {

            showLoading()
            mViewModel.queryDepartments()
        }
//        mDataDepartment.add(DepartmentBean("", "国际部门", "5"))
//        mDataDepartment.add(DepartmentBean("", "国际部门", "5"))
//        mDataDepartment.add(DepartmentBean("", "国际部门", "5"))
//        mDataDepartment.add(DepartmentBean("", "国际部门", "5"))
//        mDataDepartment.add(DepartmentBean("", "国际部门", "5"))
//        mDataDepartment.add(DepartmentBean("", "国际部门", "5"))
//        mDataDepartment.add(DepartmentBean("", "国际部门", "5"))
//        mDataDepartment.add(DepartmentBean("", "国际部门", "5"))


    }

    private fun initTab() {
        tvInvolveTab1.setOnClickListener {
            checkFirsTab()
        }
        tvInvolveTab2.setOnClickListener {
            checkSecondTab()
        }

    }

    private fun checkFirsTab() {
        currentTab = tab1
        tvInvolveTab1.setChecked(true)
        tvInvolveTab2.setChecked(false)
        mAdapterDepartment.setNewData(mDataDepartment)
    }

    private fun checkSecondTab() {
        currentTab = tab2
        tvInvolveTab2.setChecked(true)
        tvInvolveTab1.setChecked(false)

        mAdapterDepartment.setNewData(mDataClasses)
    }

    private fun initAdapterQuery() {
        mAdapterQuery = QueryStudentAdapter(R.layout.item_query_student, mDataQuery)
        rvSearchInvolve.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterQuery)
        }
        mAdapterQuery.setOnItemClickListener { _, view, position ->
            mShowDialog(position)
        }
    }

    private fun initAdapterDepart() {
        mAdapterDepartment = DepartmentAdapter(R.layout.item_department, mDataDepartment)
        rvDepartment.apply {
            layoutManager = GridLayoutManager(context, 3)
            setAdapter(mAdapterDepartment)
        }
        mAdapterDepartment.setOnItemClickListener { _, view, position ->

            mStartForResult<InvolvePersonActivity>(this, Constant.REQUEST_CODE_COURSE) {
                if (currentTab == tab1) {
                    putExtra(Constant.ID, mDataDepartment[position].id)
                    putParcelableArrayListExtra(Constant.DATA, mDataDepartment[position].list)
                    putExtra(Constant.TYPE, tab1)
                    currentItemId = mDataDepartment[position].id ?: ""
                } else {
                    putExtra(Constant.ID, mDataClasses[position].id)
                    putParcelableArrayListExtra(Constant.DATA, mDataClasses[position].list)
                    putExtra(Constant.TYPE, tab2)
                    currentItemId = mDataClasses[position].id ?: ""
                }
            }
        }
    }


    private fun initAdapterInvolve() {
        mAdapterInvolve = InvolvePerson2Adapter(R.layout.item_involve2, mDataInvolve)
        rvInvolve.apply {
            layoutManager = GridLayoutManager(context, 5)
            setAdapter(mAdapterInvolve)
        }
    }

    private fun setAdapterItemClick() {
        if (isManage) {
            mAdapterInvolve.setOnItemClickListener { adapter, view, position ->

                mDataDepartment.forEach {
                    if (it.id == mDataInvolve[position].parentId) {
                        if (it.num!!.toInt() > 0) {
                            it.list.remove(mDataInvolve[position])
                            it.num = (it.num!!.toInt() - 1).toString()
                        }
                    }
                }
                mDataClasses.forEach {
                    if (it.id == mDataInvolve[position].parentId) {
                        if (it.num!!.toInt() > 0) {
                            it.list.remove(mDataInvolve[position])
                            it.num = (it.num!!.toInt() - 1).toString()
                        }
                    }
                }
                mDataInvolve.removeAt(position)
                mAdapterInvolve.notifyItemRemoved(position)
                mAdapterDepartment.notifyDataSetChanged()
                setPersonNum()
            }
        } else {
            mAdapterInvolve.setOnItemClickListener { _, view, position ->
            }
        }
        rvInvolve.setAdapter(mAdapterInvolve)
    }


    private fun mShowDialog(position: Int) {
        // 弹出对话框
        chosenDay = DateUtil.formatDate()
        var msg =
            mDataQuery[position].cno + mDataQuery[position].realname + "\n" + mDataQuery[position].levelname + mDataQuery[position].classname
        mAlert(msg, "请确认学生身份") {
            mViewModel.addAttendance(
                LeaveBean(
                    UserInfo.getUserBean().token, type = "1", status = "0",
                    uid = mDataQuery[position].uid, atttime = chosenDay, crsid = "", remark = "lai"
                )
            )
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE_COURSE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                var removeList = ArrayList<StudentBean>()
                var receiveList: ArrayList<StudentBean> =
                    data.getParcelableArrayListExtra(Constant.DATA)
                mDataInvolve.forEach {
                    if (it.parentId == currentItemId) {
                        removeList.add(it)
                    }
                }
                if (currentTab == tab1) {
                    mDataDepartment.forEach {
                        if (it.id == currentItemId) {
                            it.list = receiveList
                            it.num = receiveList.size.toString()
                        }
                    }
                } else {
                    mDataClasses.forEach {
                        if (it.id == currentItemId) {
                            it.list = receiveList
                            it.num = receiveList.size.toString()
                        }
                    }
                }
                mAdapterDepartment.notifyDataSetChanged()
                mDataInvolve.removeAll(removeList)
                mDataInvolve.addAll(receiveList)
                mAdapterInvolve.notifyDataSetChanged()
                setPersonNum()
            }
        }
    }

    private fun setPersonNum() {

        if (mDataInvolve.size > 0) {
            tvConfirm.text = "确定(" + mDataInvolve.size + ")"
            llManage.visibility = View.VISIBLE
        } else {
            tvConfirm.text = "确定"
            llManage.visibility = View.GONE
        }
    }

    override fun initDataObserver() {
        mViewModel.mStudentData.observe(this, Observer { response ->
            response?.let {
                rvSearchInvolve.visibility = View.VISIBLE
                mDataQuery.clear()
                mDataQuery.addAll(it.data)
                rvSearchInvolve.notifyDataSetChanged()
            }
        })

        mViewModel.mDepartmentPersonData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<ArrayList<DepartmentPersonBean>>(it)?.let {

                }
            }
        })

        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<ArrayList<ClassesResponse>>(it)?.let {
                    mDataClasses.clear()
                    it.forEach {
                        it.list.forEach {

                            mDataClasses.add(DepartmentBean(it.id, it.classname))
                        }

                    }
                    mDataClasses.forEach {
                        if (mDataClasses2.size > 0) {
                            mDataClasses2.forEach { it2 ->
                                if (it2.id == it.id) {
                                    it.list = it2.list
                                    it.num = it2.list.size.toString()
                                }
                            }
                        }
                        it.id = tab2 + "_" + it.id
                    }

                }
            }
        })

        mViewModel.mDepartmentsData.observe(this, Observer { response ->
            response?.let {
                mViewModel.getClassesByTea()
                netResponseFormat<ArrayList<DepartmentBean>>(it)?.let {
                    mDataDepartment.clear()
                    mDataDepartment.addAll(it)
                    mDataDepartment.forEach {
                        if (mDataDepartment2.size > 0) {
                            mDataDepartment2.forEach { it2 ->
                                if (it2.id == it.id) {
                                    it.list = it2.list
                                    it.num = it2.list.size.toString()
                                }
                            }
                        }
                        it.id = tab1 + "_" + it.id
                    }

                    rvDepartment.notifyDataSetChanged()
                }
            }
        })

    }

}