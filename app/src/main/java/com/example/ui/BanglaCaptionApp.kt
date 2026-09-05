package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.Caption
import com.example.data.repository.CaptionRepository
import com.example.ui.components.AboutSection
import com.example.ui.components.CaptionCard
import com.example.ui.components.HeroSection
import com.example.ui.components.RandomCaptionDialog
import com.example.ui.components.SocialMediaAndFooter
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.PolishBackground
import com.example.ui.theme.PolishBorder
import com.example.ui.theme.PolishSurface
import com.example.ui.theme.RedFavorite
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BanglaCaptionApp(
    viewModel: MainViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val listState = rememberLazyListState()

    val currentSection by viewModel.currentSection.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategoryId by viewModel.selectedCategoryId.collectAsState()
    val displayedCaptions by viewModel.displayedCaptions.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()
    val favoriteCaptions by viewModel.favoriteCaptions.collectAsState()
    val randomCaption by viewModel.randomCaption.collectAsState()

    // Random Dialog
    randomCaption?.let { caption ->
        val isFav = favoriteIds.contains(caption.id)
        RandomCaptionDialog(
            caption = caption,
            isFavorite = isFav,
            onDismiss = { viewModel.dismissRandomCaption() },
            onNextRandom = { viewModel.triggerRandomCaption() },
            onCopy = { viewModel.copyToClipboard(context, it) },
            onShare = { viewModel.shareCaption(context, it) },
            onToggleFavorite = { viewModel.toggleFavorite(it) }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerContainerColor = PolishSurface
            ) {
                // Drawer Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Emerald50)
                        .padding(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Emerald600,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "ব",
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "বাংলা ক্যাপশন",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Emerald800
                            )
                            Text(
                                text = "মনের কথা বলুন সুন্দর ক্যাপশনে",
                                style = MaterialTheme.typography.labelSmall,
                                color = Emerald600
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Menu items
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("হোম", fontWeight = FontWeight.SemiBold) },
                    selected = currentSection == NavSection.HOME,
                    onClick = {
                        viewModel.onSectionSelected(NavSection.HOME)
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Emerald50,
                        selectedTextColor = Emerald800,
                        selectedIconColor = Emerald600
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Category, contentDescription = null) },
                    label = { Text("ক্যাটাগরি", fontWeight = FontWeight.SemiBold) },
                    selected = currentSection == NavSection.CATEGORIES,
                    onClick = {
                        viewModel.onSectionSelected(NavSection.CATEGORIES)
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Emerald50,
                        selectedTextColor = Emerald800,
                        selectedIconColor = Emerald600
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = null) },
                    label = { Text("জনপ্রিয় ক্যাপশন", fontWeight = FontWeight.SemiBold) },
                    selected = currentSection == NavSection.POPULAR,
                    onClick = {
                        viewModel.onSectionSelected(NavSection.POPULAR)
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Emerald50,
                        selectedTextColor = Emerald800,
                        selectedIconColor = Emerald600
                    )
                )

                NavigationDrawerItem(
                    icon = {
                        BadgedBox(badge = {
                            if (favoriteIds.isNotEmpty()) {
                                Badge(containerColor = RedFavorite) {
                                    Text("${favoriteIds.size}", color = Color.White)
                                }
                            }
                        }) {
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = RedFavorite)
                        }
                    },
                    label = { Text("আমার পছন্দের ক্যাপশন", fontWeight = FontWeight.SemiBold) },
                    selected = currentSection == NavSection.FAVORITES,
                    onClick = {
                        viewModel.onSectionSelected(NavSection.FAVORITES)
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Emerald50,
                        selectedTextColor = Emerald800,
                        selectedIconColor = Emerald600
                    )
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    label = { Text("আমাদের সম্পর্কে", fontWeight = FontWeight.SemiBold) },
                    selected = currentSection == NavSection.ABOUT,
                    onClick = {
                        viewModel.onSectionSelected(NavSection.ABOUT)
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Emerald50,
                        selectedTextColor = Emerald800,
                        selectedIconColor = Emerald600
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                // Random Button inside Drawer
                Button(
                    onClick = {
                        viewModel.triggerRandomCaption()
                        scope.launch { drawerState.close() }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("🎲 একটি ক্যাপশন দেখান", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                // Header with Professional Polish Styling
                Surface(
                    color = PolishSurface,
                    border = BorderStroke(1.dp, PolishBorder),
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left: Logo & Title
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Emerald600,
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "ব",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Text(
                                text = "বাংলা ক্যাপশন",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    letterSpacing = (-0.5).sp
                                ),
                                color = Emerald800
                            )
                        }

                        // Right: Actions (Random + Hamburger)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Quick Random Action
                            Surface(
                                shape = CircleShape,
                                color = Emerald50,
                                modifier = Modifier
                                    .size(42.dp)
                                    .testTag("top_bar_random_button")
                                    .clickable { viewModel.triggerRandomCaption() }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(text = "🎲", fontSize = 18.sp)
                                }
                            }

                            // Hamburger Menu Button (Circle with Emerald50)
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } },
                                modifier = Modifier
                                    .size(42.dp)
                                    .testTag("hamburger_menu"),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Emerald50,
                                    contentColor = Emerald700
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "মেনু খুলুন",
                                    tint = Emerald700,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            },
            bottomBar = {
                // Bottom Navigation in Professional Polish Style
                Surface(
                    color = PolishSurface,
                    border = BorderStroke(1.dp, Slate100),
                    shadowElevation = 8.dp
                ) {
                    NavigationBar(
                        containerColor = PolishSurface,
                        tonalElevation = 0.dp
                    ) {
                        NavigationBarItem(
                            selected = currentSection == NavSection.HOME,
                            onClick = { viewModel.onSectionSelected(NavSection.HOME) },
                            icon = {
                                Icon(
                                    imageVector = if (currentSection == NavSection.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                                    contentDescription = "হোম",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = "হোম",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Emerald600,
                                selectedTextColor = Emerald600,
                                unselectedIconColor = Slate400,
                                unselectedTextColor = Slate400,
                                indicatorColor = Emerald50
                            )
                        )

                        NavigationBarItem(
                            selected = currentSection == NavSection.CATEGORIES,
                            onClick = { viewModel.onSectionSelected(NavSection.CATEGORIES) },
                            icon = {
                                Icon(
                                    imageVector = if (currentSection == NavSection.CATEGORIES) Icons.Filled.Category else Icons.Outlined.Category,
                                    contentDescription = "ক্যাটাগরি",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = "ক্যাটাগরি",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Emerald600,
                                selectedTextColor = Emerald600,
                                unselectedIconColor = Slate400,
                                unselectedTextColor = Slate400,
                                indicatorColor = Emerald50
                            )
                        )

                        NavigationBarItem(
                            selected = currentSection == NavSection.POPULAR,
                            onClick = { viewModel.onSectionSelected(NavSection.POPULAR) },
                            icon = {
                                Icon(
                                    imageVector = if (currentSection == NavSection.POPULAR) Icons.Filled.Star else Icons.Outlined.StarOutline,
                                    contentDescription = "জনপ্রিয়",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = "জনপ্রিয়",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Emerald600,
                                selectedTextColor = Emerald600,
                                unselectedIconColor = Slate400,
                                unselectedTextColor = Slate400,
                                indicatorColor = Emerald50
                            )
                        )

                        NavigationBarItem(
                            selected = currentSection == NavSection.FAVORITES,
                            onClick = { viewModel.onSectionSelected(NavSection.FAVORITES) },
                            icon = {
                                BadgedBox(badge = {
                                    if (favoriteIds.isNotEmpty()) {
                                        Badge(containerColor = RedFavorite) {
                                            Text("${favoriteIds.size}", color = Color.White)
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = if (currentSection == NavSection.FAVORITES) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = "পছন্দ",
                                        tint = if (currentSection == NavSection.FAVORITES) RedFavorite else Slate400,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = "পছন্দ",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = RedFavorite,
                                selectedTextColor = RedFavorite,
                                unselectedIconColor = Slate400,
                                unselectedTextColor = Slate400,
                                indicatorColor = Emerald50
                            )
                        )
                    }
                }
            }
        ) { paddingValues ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(PolishBackground)
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Section: HOME
                if (currentSection == NavSection.HOME) {
                    item {
                        HeroSection(
                            searchQuery = searchQuery,
                            onSearchChange = { viewModel.onSearchQueryChange(it) },
                            selectedCategoryId = selectedCategoryId,
                            onCategorySelected = { viewModel.onCategorySelected(it) },
                            onRandomClick = { viewModel.triggerRandomCaption() }
                        )
                    }

                    // Section Heading: "আজকের বাছাইকৃত" with Random badge
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp, vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val headerTitle = when {
                                searchQuery.isNotEmpty() -> "অনুসন্ধানের ফলাফল (${displayedCaptions.size})"
                                selectedCategoryId != null -> {
                                    val cat = CaptionRepository.categories.find { it.id == selectedCategoryId }
                                    "${cat?.name ?: "ক্যাটাগরি"} (${displayedCaptions.size})"
                                }
                                else -> "আজকের বাছাইকৃত"
                            }

                            Text(
                                text = headerTitle,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                ),
                                color = Slate800
                            )

                            Surface(
                                shape = RoundedCornerShape(100.dp),
                                color = Emerald50,
                                border = BorderStroke(1.dp, Emerald100),
                                modifier = Modifier.clickable { viewModel.triggerRandomCaption() }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(text = "🎲", fontSize = 11.sp)
                                    Text(
                                        text = "র্যান্ডম",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Emerald700,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }

                    // Empty Search State
                    if (displayedCaptions.isEmpty()) {
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 24.dp),
                                shape = RoundedCornerShape(24.dp),
                                colors = CardDefaults.cardColors(containerColor = PolishSurface),
                                border = BorderStroke(1.dp, PolishBorder)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.SearchOff,
                                        contentDescription = null,
                                        tint = Slate400,
                                        modifier = Modifier.size(54.dp)
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Text(
                                        text = "দুঃখিত, এই শব্দের কোনো ক্যাপশন পাওয়া যায়নি।",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium,
                                        color = Slate600,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = {
                                            viewModel.onSearchQueryChange("")
                                            viewModel.onCategorySelected(null)
                                        },
                                        shape = RoundedCornerShape(14.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Emerald600)
                                    ) {
                                        Text("সব ক্যাপশন দেখুন")
                                    }
                                }
                            }
                        }
                    } else {
                        // Caption list items
                        itemsIndexed(
                            items = displayedCaptions,
                            key = { _, caption -> caption.id }
                        ) { index, caption ->
                            CaptionCard(
                                caption = caption,
                                index = index,
                                isFavorite = favoriteIds.contains(caption.id),
                                onCopy = { viewModel.copyToClipboard(context, it) },
                                onShare = { viewModel.shareCaption(context, it) },
                                onToggleFavorite = { viewModel.toggleFavorite(it) }
                            )
                        }
                    }

                    // About Section in Home
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        AboutSection()
                    }

                    // Social & Footer in Home
                    item {
                        SocialMediaAndFooter(onNavigate = { viewModel.onSectionSelected(it) })
                    }
                }

                // Section: CATEGORIES
                if (currentSection == NavSection.CATEGORIES) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = PolishSurface),
                            border = BorderStroke(1.dp, PolishBorder)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    text = "ক্যাটাগরি সমূহ (১০টি ক্যাটাগরি)",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate800
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "আপনার পছন্দসই ক্যাটাগরি নির্বাচন করে ক্যাপশন পড়ুন ও শেয়ার করুন।",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Slate600
                                )
                            }
                        }
                    }

                    CaptionRepository.categories.forEach { category ->
                        val count = CaptionRepository.allCaptions.count { it.categoryId == category.id }
                        val isSelected = selectedCategoryId == category.id

                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.onCategorySelected(if (isSelected) null else category.id)
                                    },
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) Emerald50 else PolishSurface
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) Emerald600 else PolishBorder
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(14.dp),
                                            color = if (isSelected) Emerald600 else Emerald50,
                                            modifier = Modifier.size(46.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(text = category.iconEmoji, fontSize = 24.sp)
                                            }
                                        }
                                        Column {
                                            Text(
                                                text = category.name,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isSelected) Emerald800 else Slate800
                                            )
                                            Text(
                                                text = category.description,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Slate600
                                            )
                                        }
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(100.dp),
                                        color = if (isSelected) Emerald600 else Emerald50
                                    ) {
                                        Text(
                                            text = "$count টি",
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.White else Emerald700
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (selectedCategoryId != null) {
                        val filteredList = CaptionRepository.allCaptions.filter { it.categoryId == selectedCategoryId }
                        item {
                            Text(
                                text = "নির্বাচিত ক্যাটাগরির ক্যাপশন (${filteredList.size} টি)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Slate800,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }

                        itemsIndexed(
                            items = filteredList,
                            key = { _, caption -> caption.id }
                        ) { index, caption ->
                            CaptionCard(
                                caption = caption,
                                index = index,
                                isFavorite = favoriteIds.contains(caption.id),
                                onCopy = { viewModel.copyToClipboard(context, it) },
                                onShare = { viewModel.shareCaption(context, it) },
                                onToggleFavorite = { viewModel.toggleFavorite(it) }
                            )
                        }
                    }

                    item {
                        SocialMediaAndFooter(onNavigate = { viewModel.onSectionSelected(it) })
                    }
                }

                // Section: POPULAR
                if (currentSection == NavSection.POPULAR) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = PolishSurface),
                            border = BorderStroke(1.dp, PolishBorder)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(text = "🔥", fontSize = 24.sp)
                                    Text(
                                        text = "জনপ্রিয় ক্যাপশন",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate800
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "সবচেয়ে বেশি পছন্দ করা এবং ট্রেন্ডিং বাংলা ক্যাপশনগুলোর বিশেষ সংগ্রহ।",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Slate600
                                )
                            }
                        }
                    }

                    val popularList = CaptionRepository.allCaptions.filter { it.isPopular }
                    itemsIndexed(
                        items = popularList,
                        key = { _, caption -> caption.id }
                    ) { index, caption ->
                        CaptionCard(
                            caption = caption,
                            index = index,
                            isFavorite = favoriteIds.contains(caption.id),
                            onCopy = { viewModel.copyToClipboard(context, it) },
                            onShare = { viewModel.shareCaption(context, it) },
                            onToggleFavorite = { viewModel.toggleFavorite(it) }
                        )
                    }

                    item {
                        SocialMediaAndFooter(onNavigate = { viewModel.onSectionSelected(it) })
                    }
                }

                // Section: FAVORITES
                if (currentSection == NavSection.FAVORITES) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = PolishSurface),
                            border = BorderStroke(1.dp, PolishBorder)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(text = "❤️", fontSize = 24.sp)
                                    Text(
                                        text = "আমার পছন্দের ক্যাপশন",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate800
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "আপনার সংরক্ষিত পছন্দের ক্যাপশনগুলো এখানে রাখা আছে (${favoriteCaptions.size} টি)।",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Slate600
                                )
                            }
                        }
                    }

                    if (favoriteCaptions.isEmpty()) {
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp),
                                shape = RoundedCornerShape(24.dp),
                                colors = CardDefaults.cardColors(containerColor = PolishSurface),
                                border = BorderStroke(1.dp, PolishBorder)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "🤍", fontSize = 48.sp)
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "এখনো কোনো ক্যাপশন পছন্দ করেননি।",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Slate800,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "যেকোনো ক্যাপশন কার্ডের ❤️ বাটনে চাপ দিয়ে এখানে চিরতরে সংরক্ষণ করুন।",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Slate600,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Button(
                                        onClick = { viewModel.onSectionSelected(NavSection.HOME) },
                                        shape = RoundedCornerShape(14.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Emerald600)
                                    ) {
                                        Text("ক্যাপশন ব্রাউজ করুন")
                                    }
                                }
                            }
                        }
                    } else {
                        itemsIndexed(
                            items = favoriteCaptions,
                            key = { _, fav -> fav.id }
                        ) { index, fav ->
                            val asCaption = Caption(
                                id = fav.id,
                                text = fav.text,
                                categoryId = fav.categoryId,
                                categoryName = fav.categoryName
                            )
                            CaptionCard(
                                caption = asCaption,
                                index = index,
                                isFavorite = true,
                                onCopy = { viewModel.copyToClipboard(context, it) },
                                onShare = { viewModel.shareCaption(context, it) },
                                onToggleFavorite = { viewModel.toggleFavorite(it) }
                            )
                        }
                    }

                    item {
                        SocialMediaAndFooter(onNavigate = { viewModel.onSectionSelected(it) })
                    }
                }

                // Section: ABOUT
                if (currentSection == NavSection.ABOUT) {
                    item {
                        AboutSection()
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = PolishSurface),
                            border = BorderStroke(1.dp, PolishBorder)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    text = "আমাদের বৈশিষ্ট্যসমূহ",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Emerald700
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                FeatureItem(emoji = "✨", title = "১০টি জনপ্রিয় ক্যাটাগরি", subtitle = "হাসি, কষ্ট, ভালোবাসা, ইসলাম, জীবন সহ সব অনুভূতি")
                                FeatureItem(emoji = "✍️", title = "১৫০+ মৌলিক বাংলা ক্যাপশন", subtitle = "সম্পূর্ণ নিজস্ব এবং কপিরাইট-মুক্ত রচনা")
                                FeatureItem(emoji = "⚡", title = "এক ট্যাপে কপি ও সোশ্যাল শেয়ার", subtitle = "ফেসবুক, হোয়াটসঅ্যাপ এবং ইনস্টাগ্রামে সরাসরি শেয়ার")
                                FeatureItem(emoji = "💾", title = "অফলাইন ফেভারিট সিস্টেম", subtitle = "পছন্দের ক্যাপশনগুলো স্থায়ীভাবে সংরক্ষিত থাকবে")
                                FeatureItem(emoji = "🎲", title = "র‍্যান্ডম ক্যাপশন জেনারেটর", subtitle = "এক ক্লিকেই নতুন নতুন চমকপ্রদ ক্যাপশন আবিষ্কার")
                            }
                        }
                    }

                    item {
                        SocialMediaAndFooter(onNavigate = { viewModel.onSectionSelected(it) })
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureItem(emoji: String, title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Emerald50,
            modifier = Modifier.size(38.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = emoji, fontSize = 18.sp)
            }
        }
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Slate800
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Slate600
            )
        }
    }
}
