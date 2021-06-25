package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arialyy.aria.core.Aria
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.smtt.sdk.QbSdk
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseApplication
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.PathSelector
import com.xiaoneng.ss.common.utils.endIsImage
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.activity.ImageScaleActivity
import com.xiaoneng.ss.module.circular.adapter.NoticeFileAdapter
import com.xiaoneng.ss.module.school.model.FileInfoBean
import com.xiaoneng.ss.module.school.model.LogBean
import com.xiaoneng.ss.module.school.view.TaskDetailActivity
import java.io.File


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class TaskLogAdapter(layoutId: Int, listData: MutableList<LogBean>?, activity: TaskDetailActivity) :
    BaseQuickAdapter<LogBean, BaseViewHolder>(layoutId, listData) {
    var activity = activity
    private var isOperator: Boolean = false
    var idString = ""

    override fun convert(viewHolder: BaseViewHolder?, item: LogBean?) {
        viewHolder?.let { holder ->
            var textView0 = holder.getView<TextView>(R.id.tvAction0Log)
            var textView1 = holder.getView<TextView>(R.id.tvAction1Log)
            var textView2 = holder.getView<TextView>(R.id.tvAction2Log)
            var ivMark = holder.getView<ImageView>(R.id.ivMark)
            holder.addOnClickListener(R.id.tvAction1Log)
            holder.addOnClickListener(R.id.tvAction2Log)
            holder.addOnClickListener(R.id.tvAction0Log)
            holder.setText(R.id.tvName4, item?.username)
                .setText(R.id.tvTime4, item?.updatetime)
                .setText(R.id.tvIntro4, item?.feedback)
            textView1.visibility = View.GONE
            textView2.visibility = View.GONE

            if (isOperator) {
                textView0.visibility = View.GONE
                ivMark.visibility = View.GONE

//                textView1.visibility = View.VISIBLE
//                textView2.visibility = View.VISIBLE
                textView1.visibility = View.GONE
                textView2.visibility = View.GONE
            } else {
//                textView0.visibility = View.VISIBLE
                textView0.visibility = View.GONE


                textView1.visibility = View.GONE
                textView2.visibility = View.GONE
            }

            when (item?.examinestatus) {
                "2" -> {
                    ivMark.visibility = View.GONE
                    ivMark.setImageResource(R.drawable.ic_refuse)
                    textView0.visibility = View.GONE
                    textView1.visibility = View.GONE
                    textView2.visibility = View.GONE
                }
                "1" -> {
                    ivMark.visibility = View.GONE
                    ivMark.setImageResource(R.drawable.ic_pass)
                    textView0.visibility = View.GONE
                    textView1.visibility = View.GONE
                    textView2.visibility = View.GONE
                }
                else -> {
                    ivMark.visibility = View.GONE
                }
            }
//            holder.setText(R.id.tvAction, item?.title)
            initAdapterFile(holder, item)
        }
    }

    private fun initAdapterFile(holder: BaseViewHolder, item: LogBean?) {
        var rvTaskFile = holder.getView<RecyclerView>(R.id.rvTaskFile)
        var mDataFile = ArrayList<FileInfoBean>()
        rvTaskFile.visibility = View.GONE
        var files = ArrayList<FileInfoBean>()
        val resultType = object : TypeToken<ArrayList<FileInfoBean>>() {}.type
        val gson = Gson()
        try {
            files = gson.fromJson<ArrayList<FileInfoBean>>(item?.fileinfo, resultType)
        } catch (e: Exception) {

        }
        mDataFile.addAll(files)
        if (mDataFile.size > 0) {
            rvTaskFile.visibility = View.VISIBLE
            var mAdapterFile = NoticeFileAdapter(R.layout.item_notice_file, mDataFile)
            rvTaskFile.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setAdapter(mAdapterFile)
            }
            mAdapterFile.setOnItemClickListener { _, view, position ->
                if (mDataFile[position].url.endIsImage()) {
                    mStartActivity<ImageScaleActivity>(mContext) {
                        putExtra(
                            Constant.DATA,
                            UserInfo.getUserBean().domain + mDataFile[position].url
                        )
                    }
                } else {
                    var path = PathSelector(BaseApplication.instance).getDownloadsDirPath()
                    var name = idString + mDataFile[position].name
                    var filePath = path + File.separator + name
                    var filename = File(filePath)
                    if (filename.exists()) {
                        doOpen(filePath)
                    } else {
                        doDown(mDataFile[position].url, name)
                    }
                }
            }
        }

    }

    private fun doDown(url: String?, fileName: String?) {
        val url = UserInfo.getUserBean().domain + url
        //获取应用外部照片储存路径
        val filePath =
            com.xiaoneng.ss.common.utils.PathSelector(BaseApplication.instance).getDownloadsDirPath() + File.separator + fileName
        Aria.download(this)
            .load(url) //读取下载地址
            .setFilePath(filePath) //设置文件保存的完整路径
            .create() //创建并启动下载
    }

    private fun doOpen(filePath: String) {
        QbSdk.openFileReader(mContext, filePath, null, null)
    }

    fun setIsOperator(operator: Boolean) {
        isOperator = operator
    }
}