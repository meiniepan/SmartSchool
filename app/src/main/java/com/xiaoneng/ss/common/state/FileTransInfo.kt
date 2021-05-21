package com.xiaoneng.ss.common.state

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.SPreference
import com.xiaoneng.ss.module.school.model.DiskFileBean

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/03/11
 * Time: 19:30
 */
object FileTransInfo {

    var gson = Gson()
    var emptyJson = gson.toJson(ArrayList<DiskFileBean>())
    var fileInfoJson: String by SPreference(Constant.FILE_TRANS_INFO, emptyJson)


    fun modifyFilesInfo(response: ArrayList<DiskFileBean>) {
        fileInfoJson = gson.toJson(response)
    }

    fun modifyFile(file: DiskFileBean) {
        var files = getFilesInfo()
        files.forEach {
            if (it.objectKey == file.objectKey){
                if (file.currentSize!=0L){
                    it.currentSize = file.currentSize
                }
                if (file.totalSize!=0L){
                    it.totalSize = file.totalSize
                }
                if (file.status!=0){
                    it.status = file.status
                }
                if (file.task!=null){
                    it.task?.cancel()
                    it.task = file.task
                }
                return@forEach
            }
        }
        fileInfoJson = gson.toJson(files)
    }

    fun reset() {
        var files = getFilesInfo()
        files.forEach {
            if (it.status == 0){
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
        var files = FileDownloadInfo.getFilesInfo()
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
        FileDownloadInfo.fileInfoJson = FileDownloadInfo.gson.toJson(files)
    }


    fun getFilesInfo(): ArrayList<DiskFileBean> {
        val resultType = object : TypeToken<ArrayList<DiskFileBean>>() {}.type
        return gson.fromJson<ArrayList<DiskFileBean>>(fileInfoJson, resultType)
    }


}