package com.xiaoneng.ss.module.school.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.initSiteTimes
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.toIntSafe
import com.xiaoneng.ss.module.school.model.SiteBean
import com.xiaoneng.ss.module.school.view.AddBookSiteActivity
import com.xiaoneng.ss.module.school.view.RoomBookRecordsActivity


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class BookSiteAdapter(layoutId: Int, listData: MutableList<SiteBean>) :
    BaseQuickAdapter<SiteBean, BaseViewHolder>(layoutId, listData) {


    override fun convert(viewHolder: BaseViewHolder, item: SiteBean) {
        viewHolder.let { holder ->
            var time = ""
            if (item.ostime!=null&&item.oetime!=null){
                var l1 = item.ostime?.length!!
                var l2 = item.oetime?.length!!
            if (item.ostime?.length!! > 5&&item.oetime?.length!! > 5) {
                time = item.ostime!!.substring(l1-5, l1) + "-" + item.oetime!!.substring(l2-5, l2)
            }}
            holder.setText(R.id.tvSiteItemRoomName, item.classroomname)
                .setText(R.id.tvSiteItemTime, time)
                .setText(R.id.tvSiteItemRemark, item.remark)
                .setText(R.id.tvSiteItemRealName, item.operator?.realname)
            if (holder.adapterPosition%2==0){
                holder.setBackgroundColor(R.id.llBookSite,mContext.resources.getColor(R.color.white))
            }else{
                holder.setBackgroundColor(R.id.llBookSite,mContext.resources.getColor(R.color.grayF6))
            }
        }
    }

}