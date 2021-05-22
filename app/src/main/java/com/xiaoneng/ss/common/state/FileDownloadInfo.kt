package com.xiaoneng.ss.common.state

import android.content.Context
import com.arialyy.aria.core.Aria
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.SPreference
import com.xiaoneng.ss.module.school.model.DiskFileBean

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/05/18
 * Time: 19:30
 */
object FileDownloadInfo {

    var gson = Gson()
    var emptyJson = gson.toJson(ArrayList<DiskFileBean>())
    var fileInfoJson: String by SPreference(Constant.FILE_DOWNLOAD_INFO, emptyJson)


    fun modifyFilesInfo(response: ArrayList<DiskFileBean>) {
        fileInfoJson = gson.toJson(response)
    }

    fun modifyFile(file: DiskFileBean) {
        var files = getFilesInfo()
        files.forEach {
            if (it.objectid == file.objectid){
                if (file.currentSize!=0L){
                    it.currentSize = file.currentSize
                }
                if (file.totalSize!=0L){
                    it.totalSize = file.totalSize
                }
                if (file.status!=-1){
                    it.status = file.status
                }
                if (file.progress!=0L){
                    it.progress = file.progress
                }
                
                return@forEach
            }
        }
        fileInfoJson = gson.toJson(files)
    }

    fun hasFile(file: DiskFileBean) :Boolean{
        var files = getFilesInfo()
        files.forEach {
            if (it.objectid == file.objectid){
                return true
            }
        }
        return false
    }

    fun checkComplete(context:Context) {
        var files = getFilesInfo()
        var completes = Aria.download(context).allCompleteTask
        files.forEach {
            completes?.let {c ->
                c.forEach {cc->
                    if (UserInfo.getUserBean().domain+it.objectid == cc.key){
                        it.status=2
                        return@forEach
                    }
                }
            }

        }
        fileInfoJson = gson.toJson(files)
    }

    fun reset() {
        var files = getFilesInfo()
        files.forEach {
            if (it.status != 2){
                it.status = 1
            }
        }
        fileInfoJson = gson.toJson(files)
    }

    fun addFile(file: DiskFileBean) {
        var arr = getFilesInfo()
        arr.add(file)
        fileInfoJson = gson.toJson(arr)
    }

    fun delFile(file: DiskFileBean) {
        var files = getFilesInfo()
        var the:DiskFileBean? = null
        files.forEach {
            if (it.objectid == file.objectid){
                the = it
                return@forEach
            }
        }
        the?.let {
        files.remove(it)
        }
        fileInfoJson = gson.toJson(files)
    }


    fun getFilesInfo(): ArrayList<DiskFileBean> {
        val resultType = object : TypeToken<ArrayList<DiskFileBean>>() {}.type
        return gson.fromJson<ArrayList<DiskFileBean>>(fileInfoJson, resultType)
    }


}