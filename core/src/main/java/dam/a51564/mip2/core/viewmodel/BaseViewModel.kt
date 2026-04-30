package dam.a51564.mip2.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a51564.mip2.core.state.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun <T> executeCall(
        apiCall: Flow<Resource<T>>,
        stateFlow: MutableStateFlow<Resource<T>>
    ) {
        viewModelScope.launch {
            apiCall.collect { resource ->
                stateFlow.value = resource
            }
        }
    }
}
