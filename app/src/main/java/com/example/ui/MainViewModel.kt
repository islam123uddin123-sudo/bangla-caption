package com.example.ui

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.FavoriteEntity
import com.example.data.model.Caption
import com.example.data.model.Category
import com.example.data.repository.CaptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class NavSection(val title: String) {
    HOME("হোম"),
    CATEGORIES("ক্যাটাগরি"),
    POPULAR("জনপ্রিয়"),
    FAVORITES("পছন্দের"),
    ABOUT("আমাদের সম্পর্কে")
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteDao = AppDatabase.getDatabase(application).favoriteDao()

    private val _currentSection = MutableStateFlow(NavSection.HOME)
    val currentSection: StateFlow<NavSection> = _currentSection.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()

    private val _randomCaption = MutableStateFlow<Caption?>(null)
    val randomCaption: StateFlow<Caption?> = _randomCaption.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    val favoriteIds: StateFlow<Set<Int>> = favoriteDao.getAllFavoriteIds()
        .map { it.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val favoriteCaptions: StateFlow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered captions for the current view
    val displayedCaptions: StateFlow<List<Caption>> = combine(
        _searchQuery,
        _selectedCategoryId,
        _currentSection
    ) { query, categoryId, section ->
        val trimmedQuery = query.trim()

        if (trimmedQuery.isNotEmpty()) {
            CaptionRepository.allCaptions.filter {
                it.text.contains(trimmedQuery, ignoreCase = true) ||
                it.categoryName.contains(trimmedQuery, ignoreCase = true)
            }
        } else {
            when (section) {
                NavSection.HOME -> {
                    if (categoryId != null) {
                        CaptionRepository.allCaptions.filter { it.categoryId == categoryId }
                    } else {
                        CaptionRepository.allCaptions
                    }
                }
                NavSection.CATEGORIES -> {
                    if (categoryId != null) {
                        CaptionRepository.allCaptions.filter { it.categoryId == categoryId }
                    } else {
                        CaptionRepository.allCaptions
                    }
                }
                NavSection.POPULAR -> {
                    CaptionRepository.allCaptions.filter { it.isPopular }
                }
                NavSection.FAVORITES -> {
                    emptyList() // Handled via favoriteCaptions
                }
                NavSection.ABOUT -> {
                    emptyList()
                }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CaptionRepository.allCaptions)

    fun onSectionSelected(section: NavSection) {
        _currentSection.value = section
        if (section != NavSection.CATEGORIES && section != NavSection.HOME) {
            _selectedCategoryId.value = null
        }
        if (section != NavSection.HOME) {
            _searchQuery.value = ""
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.isNotEmpty() && _currentSection.value != NavSection.HOME) {
            _currentSection.value = NavSection.HOME
        }
    }

    fun onCategorySelected(categoryId: String?) {
        _selectedCategoryId.value = categoryId
        _searchQuery.value = ""
    }

    fun triggerRandomCaption() {
        _randomCaption.value = CaptionRepository.getRandomCaption()
    }

    fun dismissRandomCaption() {
        _randomCaption.value = null
    }

    fun toggleFavorite(caption: Caption) {
        viewModelScope.launch {
            val isFav = favoriteIds.value.contains(caption.id)
            if (isFav) {
                favoriteDao.deleteFavorite(caption.id)
            } else {
                favoriteDao.insertFavorite(
                    FavoriteEntity(
                        id = caption.id,
                        text = caption.text,
                        categoryId = caption.categoryId,
                        categoryName = caption.categoryName
                    )
                )
            }
        }
    }

    fun copyToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("বাংলা ক্যাপশন", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "ক্যাপশন কপি হয়েছে ✅", Toast.LENGTH_SHORT).show()
        _toastMessage.value = "ক্যাপশন কপি হয়েছে ✅"
    }

    fun shareCaption(context: Context, text: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "বাংলা ক্যাপশন")
                putExtra(Intent.EXTRA_TEXT, "$text\n\n— বাংলা ক্যাপশন")
            }
            context.startActivity(Intent.createChooser(intent, "ক্যাপশন শেয়ার করুন"))
        } catch (e: Exception) {
            Toast.makeText(context, "শেয়ার করতে সমস্যা হয়েছে", Toast.LENGTH_SHORT).show()
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}
