package com.xiaoneng.ss.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.xiaoneng.ss.base.repository.BaseRepository
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.StateType
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.network.response.BaseResponse
import kotlinx.coroutines.launch

/**
 * Created with Android Studio.
 * Description:数据解析扩展函数
 * @author: Burning
 * @CreateDate: 2020/4/19 17:35
 */

fun <T> BaseResponse<T>.dataConvert(
    loadState: MutableLiveData<State>
): T {
    when (respCode) {
        Constant.SUCCESS -> {
            if (respResult is List<*>) {
                if ((respResult as List<*>).isEmpty()) {
                    loadState.postValue(State(StateType.EMPTY))
                }
            }
            loadState.postValue(State(StateType.SUCCESS))
            return respResult
        }
        Constant.NOT_LOGIN -> {
            loadState.postValue(State(StateType.NOT_LOGIN, message = "请重新登录"))
            return respResult
        }
        else -> {
            loadState.postValue(State(StateType.ERROR, message = respMsg))
            return respResult
        }
    }
}


fun <T : BaseRepository> BaseViewModel<T>.initiateRequest(
    block: suspend () -> Unit,
    loadState: MutableLiveData<State>
) {
    viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {

        }.onFailure {
            NetExceptionHandle.handleException(it, loadState)
        }
    }
}
