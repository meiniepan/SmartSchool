package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.module.school.interfaces.IPropertyRecord
import com.xiaoneng.ss.module.school.model.PropertyDetailBean
import com.xiaoneng.ss.module.school.model.RepairBody


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class PropertyRecordAdapter(
    layoutId: Int,
    listData: MutableList<PropertyDetailBean>,
    val listener: IPropertyRecord
) :
    BaseQuickAdapter<PropertyDetailBean, BaseViewHolder>(layoutId, listData) {
    var mType: String? = null


    override fun convert(viewHolder: BaseViewHolder, item: PropertyDetailBean) {
        viewHolder?.let { holder ->
            var view1 = holder.getView<TextView>(R.id.tvPropertyDetailAction1)
            var view2 = holder.getView<TextView>(R.id.tvPropertyDetailAction2)
            var llAction = holder.getView<View>(R.id.llPropertyDetailAction)
            var statusStr = ""
            holder.setText(R.id.tvPropertyDetailTime, item.reporttime)
                .setText(R.id.tvPropertyDetailCode, item.id)
                .setText(R.id.tvPropertyDetailPerson, item.repairerinfo?.realname)
                .setText(R.id.tvPropertyDetailTime2, item.completetime)
                .setText(R.id.tvPropertyDetailRemark, item.remark)//维修人逻辑

            //报修人逻辑
            //0撤销 1未接单 2接单 3完成
            when {

                item.status == "0" -> {
                    statusStr = "已撤销"
                }
                item.status == "1" -> {
                    statusStr = "未接单"
                }
                item.status == "2" -> {
                    if (item.isdelay == "1") {

                        statusStr = "已延期"
                    } else {

                        statusStr = "已接单"
                    }
                }
                item.status == "3" -> {
                    statusStr = "已完成"
                }
            }
            if (mType == "0") {
                //报修人逻辑
                //0撤销 1未接单 2接单 3完成
                holder.setText(R.id.tvPropertyDetailTypeKey, "报修类型")
                    .setText(R.id.tvPropertyDetailType, item.typename)
                if (item.status == "0") {
                    llAction.visibility = View.GONE
                } else if (item.status == "1") {
                    llAction.visibility = View.VISIBLE
                    view1.visibility = View.VISIBLE
                    view2.visibility = View.VISIBLE
                    view1.apply {
                        text = "撤销"
                        setOnClickListener {
                            doCancel(RepairBody(id = mData[holder.adapterPosition].id))
                        }
                    }
                    view2.apply {
                        text = "提醒"
                        setOnClickListener {
                            doRemind(mData[holder.adapterPosition])
                        }
                    }
                } else if (item.status == "2") {
                    llAction.visibility = View.VISIBLE
                    view1.visibility = View.GONE
                    view2.visibility = View.VISIBLE
                    view2.apply {
                        text = "提醒"
                        setOnClickListener {
                            doRemind(mData[holder.adapterPosition])
                        }
                    }
                } else if (item.status == "3") {
                    llAction.visibility = View.GONE

                }

            } else if (mType == "1") {
                //维修人逻辑
                if (item.status == "0") {
                    llAction.visibility = View.GONE
                } else if (item.status == "1") {
                    llAction.visibility = View.VISIBLE
                    view1.visibility = View.VISIBLE
                    view2.visibility = View.VISIBLE
                    view1.apply {
                        text = "转单"
                        setOnClickListener {
                            doShift(RepairBody(id = mData[holder.adapterPosition].id))
                        }
                    }
                    view2.apply {
                        text = "接单"
                        setOnClickListener {
                            doReceive(RepairBody(id = mData[holder.adapterPosition].id))
                        }
                    }
                } else if (item.status == "2") {
                    if (item.isdelay == "1") {

                        llAction.visibility = View.VISIBLE
                        view1.visibility = View.GONE
                        view2.visibility = View.VISIBLE
                        view2.apply {
                            text = "完成"
                            setOnClickListener {
                                doFinish(RepairBody(id = mData[holder.adapterPosition].id))
                            }
                        }
                    } else {
                        llAction.visibility = View.VISIBLE
                        view1.visibility = View.VISIBLE
                        view2.visibility = View.VISIBLE
                        view1.apply {
                            text = "延期"
                            setOnClickListener {
                                doDelay(RepairBody(id = mData[holder.adapterPosition].id))
                            }
                        }
                        view2.apply {
                            text = "完成"
                            setOnClickListener {
                                doFinish(RepairBody(id = mData[holder.adapterPosition].id))
                            }
                        }
                    }

                } else if (item.status == "3") {
                    llAction.visibility = View.GONE

                }

                holder.setText(R.id.tvPropertyDetailTypeKey, "发起人")
                    .setText(R.id.tvPropertyDetailType, item.reportinfo?.realname)


            }
            holder.setText(R.id.tvPropertyDetailResult, statusStr)
            initAdapter(holder, item)
        }

    }

    private fun doFinish(bean: RepairBody) {
        listener.doFinish(bean)
    }

    private fun doReceive(bean: RepairBody) {
        listener.doReceive(bean)
    }


    private fun doRemind(bean: PropertyDetailBean) {
        listener.doRemind(bean)
    }

    private fun doCancel(bean: RepairBody) {
        listener.doCancel(bean)
    }

    private fun doShift(bean: RepairBody) {
        listener.doShift(bean)
    }

    private fun doDelay(bean: RepairBody) {
        listener.doDelay(bean)
    }


    private fun initAdapter(holder: BaseViewHolder, item: PropertyDetailBean) {
        var mRecycler: RecyclerView = holder.getView(R.id.rvPropertyDetail)

        var eData: ArrayList<String> = ArrayList()
        item.fileinfo?.forEach {
            eData.add(UserInfo.getUserBean().domain + it.url)
        }

        var mAdapter = DisplayImgAdapter(R.layout.item_add_img, eData)

        mRecycler.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }

    fun setType(mType: String?) {
        this.mType = mType
    }

}