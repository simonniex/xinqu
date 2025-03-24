package com.example.teacher.presentation.teachermine.detail.viewmodel
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class XinCheng(
    val place:String,//上课地点
    val time:String,//上课时间
    val course:String,//上课科目
    val context:String//上课内容
)
class TeacherMineXcViewModel(private val repository: XinChengRepository) : ViewModel() {
    private val _xinCheng = MutableStateFlow(XinCheng("", "", "", ""))
    val xinCheng: StateFlow<XinCheng> get() = _xinCheng

    init {
        viewModelScope.launch {
            repository.readXinCheng.collect { data ->
                _xinCheng.value = data
            }
        }
    }

    fun updateXinCheng(updated: XinCheng) {
        _xinCheng.value = updated
    }

    fun saveXinCheng() {
        viewModelScope.launch {
            repository.saveXinCheng(_xinCheng.value)
        }
    }
}


// 声明 DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "xin_cheng_data_store")

class ViewModelFactory(private val repository: XinChengRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeacherMineXcViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeacherMineXcViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class XinChengRepository(private val context: Context) {
    private val dataStore = context.dataStore

    suspend fun saveXinCheng(xinCheng: XinCheng) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("place")] = xinCheng.place
            preferences[stringPreferencesKey("time")] = xinCheng.time
            preferences[stringPreferencesKey("course")] = xinCheng.course
            preferences[stringPreferencesKey("context")] = xinCheng.context
        }
    }

    val readXinCheng: Flow<XinCheng> = dataStore.data
        .map { preferences ->
            XinCheng(
                place = preferences[stringPreferencesKey("place")] ?: "",
                time = preferences[stringPreferencesKey("time")] ?: "",
                course = preferences[stringPreferencesKey("course")] ?: "",
                context = preferences[stringPreferencesKey("context")] ?: ""
            )
        }
}

