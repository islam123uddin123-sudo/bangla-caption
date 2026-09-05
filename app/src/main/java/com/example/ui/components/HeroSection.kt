package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CaptionRepository
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald200
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.PastelAmber
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelCyan
import com.example.ui.theme.PastelEmerald
import com.example.ui.theme.PastelOrange
import com.example.ui.theme.PastelPink
import com.example.ui.theme.PastelPurple
import com.example.ui.theme.PastelRed
import com.example.ui.theme.PastelYellow
import com.example.ui.theme.PolishBorder
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900

@Composable
fun HeroSection(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    selectedCategoryId: String?,
    onCategorySelected: (String?) -> Unit,
    onRandomClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("hero_section"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Gradient Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Emerald600, Emerald700)
                        )
                    )
                    .padding(22.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Top tag / random button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(100.dp),
                            color = Color.White.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = "✨ সেরা বাংলা ক্যাপশন সংগ্রহ",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp
                            )
                        }

                        // Random action chip
                        Surface(
                            onClick = onRandomClick,
                            shape = RoundedCornerShape(100.dp),
                            color = Color.White,
                            modifier = Modifier.testTag("random_caption_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(text = "🎲", fontSize = 12.sp)
                                Text(
                                    text = "র্যান্ডম",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Emerald700,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "মনের কথা বলুন...",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "সেরা সব বাংলা ক্যাপশন এখন আপনার হাতের মুঠোয়।",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        ),
                        color = Emerald50.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Polished Search Input
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = onSearchChange,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("search_input"),
                            placeholder = {
                                Text(
                                    text = "ক্যাপশন খুঁজুন...",
                                    color = Emerald100.copy(alpha = 0.85f),
                                    fontSize = 15.sp
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "অনুসন্ধান",
                                    tint = Emerald100
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { onSearchChange("") }) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "মুছে ফেলুন",
                                            tint = Color.White
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(20.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.35f),
                                focusedTextColor = Slate900,
                                unfocusedTextColor = Color.White,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White.copy(alpha = 0.15f)
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
                        )

                        Button(
                            onClick = { focusManager.clearFocus() },
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Emerald700
                            ),
                            modifier = Modifier
                                .height(54.dp)
                                .testTag("search_button"),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                        ) {
                            Text(
                                text = "খুঁজুন",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }

        // Category Horizontal Strip Section
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ক্যাটাগরি",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    ),
                    color = Slate800
                )

                if (selectedCategoryId != null) {
                    Text(
                        text = "সব দেখুন",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        ),
                        color = Emerald600,
                        modifier = Modifier
                            .clickable { onCategorySelected(null) }
                            .padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Horizontal Scroll of Category Icon Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val categoryColors = listOf(
                    PastelOrange,   // হাসির
                    PastelPurple,   // কষ্টের
                    PastelRed,      // ভালোবাসা
                    PastelBlue,     // Attitude
                    PastelCyan,     // বন্ধুত্ব
                    PastelEmerald,  // ইসলামিক
                    PastelPink,     // জীবন
                    PastelAmber,    // পরিবার
                    PastelYellow,   // একাকীত্ব
                    PastelEmerald   // সুন্দর মুহূর্ত
                )

                CaptionRepository.categories.forEachIndexed { index, category ->
                    val isSelected = selectedCategoryId == category.id
                    val bgColor = if (isSelected) Emerald600 else categoryColors[index % categoryColors.size]
                    val textColor = if (isSelected) Emerald600 else Slate600

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(18.dp))
                            .clickable {
                                if (isSelected) onCategorySelected(null) else onCategorySelected(category.id)
                            }
                            .padding(4.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = bgColor,
                            border = BorderStroke(
                                1.5.dp,
                                if (isSelected) Emerald700 else PolishBorder
                            ),
                            modifier = Modifier.size(56.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = category.iconEmoji,
                                    fontSize = 24.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = category.name.replace(" ক্যাপশন", "").replace(" নিয়ে ক্যাপশন", ""),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            ),
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}
