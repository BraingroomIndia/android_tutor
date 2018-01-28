package com.braingroom.tutor.viewmodel.activity

import android.databinding.ObservableField
import android.util.Log
import com.braingroom.tutor.common.modules.HelperFactory
import com.braingroom.tutor.model.data.ListDialogData
import com.braingroom.tutor.model.resp.CommonIdResp
import com.braingroom.tutor.utils.*
import com.braingroom.tutor.view.fragment.FragmentHelper
import com.braingroom.tutor.viewmodel.ViewModel
import com.braingroom.tutor.viewmodel.fragment.SearchSelectListViewModel
import com.braingroom.tutor.viewmodel.item.ListDialogViewModel
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/*
 * Created by godara on 08/01/18.
 */

class BroadcastMessageViewModel(helperFactory: HelperFactory, val fragmentHelper: FragmentHelper) : ViewModel(helperFactory) {
    val classListTitle = "Class List"
    val userListTitle = "User List"
    val userTypeTitle = "Learner Type"
    var type: Int? = null
    var classId: String? = null
    val message by lazy {
        ObservableField<String>("")
    }
    var senderId: String? = null
    val onSendClicked by lazy {
        Action {
            if (message.get().isNullOrBlank())
                messageHelper.showMessage("Cant send empty message")
            else if (senderId.isNullOrBlank())
                messageHelper.showMessage("Please select Learner")
            else {
                apiService.postReply(senderId!!, message.get()).subscribe(
                        { resp ->
                            messageHelper.showDismissInfo("", resp.resMsg, "Dismiss")
                        },
                        { throwable ->
                            Log.e(TAG, throwable.message, throwable)
                        }
                )
            }
        }

    }

    val learnerTypeData: LinkedHashMap<Int, String> by lazy {
        val temp = LinkedHashMap<Int, String>()
        temp.put(pastId, pastValue)
        temp.put(currentId, currentValue)
        temp.put(upComingId, upComingValue)
        temp.put(4, "All")
        temp
    }
    val learnerType by lazy {
        ListDialogViewModel(helperFactory, "Learner Type", Observable.just(ListDialogData(learnerTypeData)), HashMap(), false,
                Consumer { selectedData ->
                    selectedData.keys.forEach { type = it }
                    userList.refreshDataMap(getUserList())
                }
                , "", "Okay")
    }
    val classList by lazy {
        SearchSelectListViewModel(helperFactory, classListTitle, "search by class name", "", true, apiService.getClassList().map(this::respToHashMap), Consumer { selectedData ->
            classId = selectedData.getId()
            userList.refreshDataMap(getUserList())
        }, HashMap())
    }
    val userList by lazy {
        SearchSelectListViewModel(helperFactory, userListTitle, "search by learner's name", "", true, getUserList(), Consumer { selectedData ->
            senderId = selectedData.getId()
        }, HashMap())
    }

    private fun getUserList() = apiService.getLearner(type, classId).map(this::respToHashMap)

    private fun respToHashMap(resp: CommonIdResp): HashMap<Int, String> {
        val list: HashMap<Int, String> = HashMap()
        resp.data.forEach { snippet -> list.put(snippet.id, snippet.textValue) }
        return list
    }
}

