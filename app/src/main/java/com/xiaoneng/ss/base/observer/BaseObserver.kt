//package com.xiaoneng.ss.base.observer
//
//import androidx.lifecycle.MutableLiveData
//import io.reactivex.Observer
//import com.xiaoneng.ss.network.response.BaseResponse
//import com.xiaoneng.ss.base.repository.BaseRepository
//import com.xiaoneng.ss.common.state.State
//import com.xiaoneng.ss.common.state.StateType
//import com.xiaoneng.ss.common.state.UserInfo
//import com.xiaoneng.ss.common.utils.Constant
//import io.reactivex.disposables.Disposable
//
///**
// * Created with Android Studio.
// * Description:
// * @author: Burning
// * @date: 2020/02/25
// * Time: 20:44
// */
//
//class BaseObserver<T : BaseResponse<*>>(
//    val liveData: MutableLiveData<T>,
//    val loadState: MutableLiveData<State>,
//    val repository: BaseRepository
//) : Observer<T> {
//    override fun onNext(response: T) {
//        when (response.respCode) {
//            Constant.SUCCESS -> {
//                if (response.respResult is List<*>) {
//                    if ((response.respResult as List<*>).isEmpty()) {
//                        loadState.postValue(State(StateType.EMPTY))
//                        return
//                    }
//                }
//                loadState.postValue(State(StateType.SUCCESS))
//                liveData.postValue(response)
//            }
//            Constant.NOT_LOGIN -> {
//                UserInfo.logoutSuccess()
//                loadState.postValue(State(StateType.ERROR, message = "请重新登录"))
//            }
//            else -> {
//                loadState.postValue(State(StateType.ERROR, message = response.respMsg))
//            }
//        }
//    }
//
//    override fun onSubscribe(disposable: Disposable) {
//        repository.addSubscribe(disposable)
//    }
//
//    override fun onError(p0: Throwable) {
//        loadState.postValue(State(StateType.NETWORK_ERROR))
//    }
//
//    override fun onComplete() {}
//}