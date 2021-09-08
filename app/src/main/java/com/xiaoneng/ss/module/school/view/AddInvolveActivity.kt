package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.model.StudentBean
import com.xiaoneng.ss.module.school.adapter.DepartmentAdapter
import com.xiaoneng.ss.module.school.adapter.InvolveLabelAdapter
import com.xiaoneng.ss.module.school.adapter.InvolvePerson2Adapter
import com.xiaoneng.ss.module.school.adapter.QueryDepartAdapter
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_involve.*

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
    private lateinit var mAdapterQuery: QueryDepartAdapter
    private lateinit var mAdapterDepartment: DepartmentAdapter
    private lateinit var mAdapterLabel: InvolveLabelAdapter
    private lateinit var mAdapterInvolve: InvolvePerson2Adapter
    var mDataQuery = ArrayList<UserBeanSimple>()
    var mDataDepartment: ArrayList<DepartmentBean>? = ArrayList()
    var mDataLabel: ArrayList<InvolveLabelBean> = ArrayList()
    var mDataClasses: ArrayList<DepartmentBean>? = ArrayList()
    var mDataDepartment2 = ArrayList<DepartmentBean>()
    var mDataClasses2 = ArrayList<DepartmentBean>()
    var mDataInvolve: MutableList<StudentBean> = ArrayList()
    var label: String = ""
    var mReceiveList: MutableList<UserBeanSimple>? = ArrayList()
    var isManage = false
    lateinit var chosenDay: String


    override fun getLayoutId(): Int = R.layout.activity_add_involve


    override fun initView() {
        super.initView()
        mDataDepartment = intent.getParcelableArrayListExtra(Constant.DATA) ?: ArrayList()
        mDataClasses = intent.getParcelableArrayListExtra(Constant.DATA2) ?: ArrayList()
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
        mDataLabel.clear()
        mDataLabel.add(InvolveLabelBean(label = "all",name = "所有人"))
        mDataLabel.add(InvolveLabelBean(label = "teacher",name = "所有老师"))
        mDataLabel.add(InvolveLabelBean(label = "classmaster",name = "所有班主任"))
        mDataLabel.add(InvolveLabelBean(label = "students",name = "所有学生"))
        initAdapterLabel()
        initAdapterQuery()
        initTab()
        initAdapterDepart()
        initAdapterInvolve()
        etSearch.setOnEditorActionListener { teew, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if (etSearch.text.toString().isNotEmpty()) {
                        rvSearchInvolve.visibility = View.VISIBLE
                        llContent.visibility = View.GONE
                        tvSearchClear.visibility = View.VISIBLE
                        rvSearchInvolve.showLoadingView()
                        mViewModel.listByDepartment(realName = etSearch.text.toString())
                    }
                }

            }
            return@setOnEditorActionListener false
        }
        tvSearchClear.setOnClickListener {
            rvSearchInvolve.visibility = View.GONE
            llContent.visibility = View.VISIBLE
            tvSearchClear.visibility = View.GONE
            etSearch.setText("")
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
            var label2:String?=null
            var labelStr:String=""

            mDataLabel.forEach {
                if (it.checked){
                    label = label+it.label+","
                    labelStr = labelStr+it.name+","
                }
            }
            if (!label.isNullOrEmpty()){
                label2 = label.substring(0,label.length-1)
                labelStr = labelStr.substring(0,labelStr.length-1)

            }

            Log.e("=====", label2.toString() )
            var mIntent = intent
            mIntent.putParcelableArrayListExtra(Constant.DATA, mDataDepartment)
            mIntent.putParcelableArrayListExtra(Constant.DATA2, mDataClasses)
            mIntent.putExtra(Constant.DATA3, label2)
            mIntent.putExtra(Constant.DATA4, labelStr)
            setResult(Activity.RESULT_OK, mIntent)
            finish()
        }

        if (intent.getIntExtra(Constant.TYPE, 0) == 1) {//来自云盘配置
            ctbAddInvolve.setTitle(getString(R.string.diskTitle))
            rlClass.visibility = View.GONE
        } else if (intent.getIntExtra(Constant.TYPE, 0) == 2) {//来自选择学生
            llLabel.visibility = View.GONE
            ctbAddInvolve.setTitle("选择学生")
            rlTeacher.visibility = View.GONE
            currentTab = tab2
            tvInvolveTab2.setChecked(true)
            tvInvolveTab1.setChecked(false)
            checkSecondTab()
        }

    }

    override fun initData() {
        super.initData()
        if (mDataDepartment?.size ?: 0 > 0 || mDataClasses?.size ?: 0 > 0) {
            mAdapterDepartment.notifyDataSetChanged()
            mDataDepartment?.forEach {
                if (it.num!!.toInt() > 0) {
                    mDataInvolve.addAll(it.list)
                }
            }
            mDataClasses?.forEach {
                if (it.num!!.toInt() > 0) {
                    mDataInvolve.addAll(it.list)
                }
            }
            mAdapterInvolve.notifyDataSetChanged()
            setPersonNum()
        } else {
            showLoading()
            mViewModel.queryDepartments()
        }

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
        if (mDataClasses?.size ?: 0 <= 0) {
            rvDepartment.showEmptyView()
        } else {
            rvDepartment.showContentView()
        }
    }

    private fun initAdapterQuery() {
        mAdapterQuery = QueryDepartAdapter(R.layout.item_query_student, mDataQuery)
        rvSearchInvolve.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterQuery)
        }
        mAdapterQuery.setOnItemClickListener { _, view, position ->
            mShowDialog(position)
        }
    }

    private fun initAdapterLabel() {
        mAdapterLabel = InvolveLabelAdapter(R.layout.item_involve_label, mDataLabel)
        rvLabel.apply {
            layoutManager = GridLayoutManager(context, 3)
            setAdapter(mAdapterLabel)
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
                    mDataDepartment?.let {
                        putExtra(Constant.ID, it[position]?.id)
                        putParcelableArrayListExtra(Constant.DATA, it[position].list)
                        putExtra(Constant.TYPE, tab1)
                        currentItemId = it[position].id ?: ""
                    }

                } else {
                    mDataClasses?.let {
                        putExtra(Constant.ID, it[position].id)
                        putParcelableArrayListExtra(Constant.DATA, it[position].list)
                        putExtra(Constant.TYPE, tab2)
                        currentItemId = it[position].id ?: ""
                    }
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

                mDataDepartment?.forEach {
                    if (it.id == mDataInvolve[position].parentId) {
                        if (it.num!!.toInt() > 0) {
                            it.list.remove(mDataInvolve[position])
                            it.num = (it.num!!.toInt() - 1).toString()
                        }
                    }
                }
                mDataClasses?.forEach {
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
        mDataQuery[position].let { bean ->
            var pId = ""
            var studentBean = StudentBean(
                uid = bean.uid ?: "",
                realname = bean.realname ?: "",
                usertype = bean.usertype ?: "",
                topdepartid = bean.deps?.get(0)?.topdepartid ?: "",
                secdepartid = bean.deps?.get(0)?.secdepartid ?: "",
                choice = "1",
                parentId = pId
            )
            var msg =
                mDataQuery[position].realname + ""
            mAlert(msg, "请确认身份") {

                if (studentBean.topdepartid.isNullOrEmpty()) {
                    //学生
                    studentBean.parentId = tab2 + "_" + bean.classid

                    mDataClasses?.forEach {
                        if (studentBean.parentId == it.id) {
                            var bb = false
                            it.list.forEach {
                                if (it.uid == studentBean.uid) {
                                    bb = true
                                }
                            }
                            if (!bb) {
                                if (it.num == "0") {
                                    it.list.clear()
                                    it.list.add(studentBean)
                                } else {
                                    it.list.add(studentBean)
                                }
                                it.num = ((it.num ?: "0").toInt() + 1).toString()
                            }
                        }
                    }
                } else {
                    studentBean.parentId = tab1 + "_" + studentBean.topdepartid
                    mDataDepartment?.forEach {
                        if (studentBean.parentId == it.id) {
                            var bb = false
                            it.list.forEach {
                                if (it.uid == studentBean.uid) {
                                    bb = true
                                }
                            }
                            if (!bb) {
                                if (it.num == "0") {
                                    it.list.clear()
                                    it.list.add(studentBean)
                                } else {
                                    it.list.add(studentBean)
                                }
                                it.num = ((it.num ?: "0").toInt() + 1).toString()
                            }
                        }
                    }
                }
                var bb = false
                mDataInvolve.forEach {
                    if (it.uid == studentBean.uid && it.parentId == studentBean.parentId) {
                        bb = true
                    }
                }
                if (!bb) {
                    rvDepartment.notifyDataSetChanged()
                    mDataInvolve.add(
                        studentBean
                    )
                    rvInvolve.notifyDataSetChanged()
                    setPersonNum()
                }

                rvSearchInvolve.visibility = View.GONE
                llContent.visibility = View.VISIBLE
            }
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
                    data.getParcelableArrayListExtra(Constant.DATA)!!
                mDataInvolve.forEach {
                    if (it.parentId == currentItemId) {
                        removeList.add(it)
                    }
                }
                if (currentTab == tab1) {
                    mDataDepartment?.forEach {
                        if (it.id == currentItemId) {
                            it.list = receiveList
                            it.num = receiveList.size.toString()
                        }
                    }
                } else {
                    mDataClasses?.forEach {
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
                netResponseFormat<DepartmentPersonResp>(it)?.let {

                    it.data?.let { it1 ->
                        mDataQuery.addAll(it1)
                        rvSearchInvolve.notifyDataSetChanged()
                    }
                }
            }
        })

        mViewModel.mDepartmentPersonData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<DepartmentPersonResp>(it)?.let {
                    mDataQuery.clear()
                    it.data?.let {
                        mDataQuery.addAll(it)
                        mViewModel.getStudentsByClass(realName = etSearch.text.toString())
                    }
                }
            }
        })

        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<ArrayList<ClassesResponse>>(it)?.let {
                    mDataClasses?.clear()
                    it.forEach {
                        it.list.forEach {

                            mDataClasses?.add(DepartmentBean(it.id, it.levelclass))
                        }

                    }
                    mDataClasses?.forEach {
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
                    if (intent.getIntExtra(Constant.TYPE, 0) == 2) {//来自选择学生
                        checkSecondTab()
                    }

                }
            }
        })

        mViewModel.mDepartmentsData.observe(this, Observer { response ->
            response?.let {
                mViewModel.getClassesByTea()
                netResponseFormat<ArrayList<DepartmentBean>>(it)?.let {
                    mDataDepartment?.clear()
                    mDataDepartment?.addAll(it)
                    mDataDepartment?.forEach {
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
                    if (intent.getIntExtra(Constant.TYPE, 0) != 2) {//来自选择学生
                        rvDepartment.notifyDataSetChanged()
                    }
                }
            }
        })

    }

}