package ru.easycode.zerotoheroandroidtdd.folder.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import ru.easycode.zerotoheroandroidtdd.folder.core.Folder
import ru.easycode.zerotoheroandroidtdd.folder.core.FolderLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.folder.core.FoldersRepository
import ru.easycode.zerotoheroandroidtdd.folder.details.FolderDetailsScreen
import ru.easycode.zerotoheroandroidtdd.folder.list.FoldersListScreen
import ru.easycode.zerotoheroandroidtdd.folder.create.CreateFolderScreen
import ru.easycode.zerotoheroandroidtdd.main.Navigation

class FolderListViewModel(
    private val repository: FoldersRepository.ReadList,
    private val listLiveDataWrapper: FolderListLiveDataWrapper.UpdateListAndRead,
    private val folderLiveDataWrapper: FolderLiveDataWrapper.Update,
    private val navigation: Navigation.Update,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun init() {
        viewModelScope.launch(dispatcher) {
            val folders = repository.folders()
            val folderUis = folders.map { folder ->
                FolderUi(
                    id = folder.id,
                    title = folder.title,
                    notesCount = folder.notesCount
                )
            }
            listLiveDataWrapper.update(folderUis)
        }
    }

    fun addFolder() {
        navigation.update(CreateFolderScreen)
    }

    fun folderDetails(folderUi: FolderUi) {
        folderLiveDataWrapper.update(folderUi)
        navigation.update(FolderDetailsScreen)
    }
}
