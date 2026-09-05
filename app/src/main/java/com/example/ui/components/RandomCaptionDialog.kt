package com.example.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Caption
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.PolishBorder
import com.example.ui.theme.PolishSurface
import com.example.ui.theme.RedFavorite
import com.example.ui.theme.RedFavoriteBg
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800

@Composable
fun RandomCaptionDialog(
    caption: Caption,
    isFavorite: Boolean,
    onDismiss: () -> Unit,
    onNextRandom: () -> Unit,
    onCopy: (String) -> Unit,
    onShare: (String) -> Unit,
    onToggleFavorite: (Caption) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = PolishSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            border = BorderStroke(1.dp, PolishBorder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .testTag("random_caption_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Emerald50,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = "🎲", fontSize = 16.sp)
                            }
                        }
                        Text(
                            text = "একটি ক্যাপশন",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Slate800
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "বন্ধ করুন",
                            tint = Slate400
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Category Badge
                Surface(
                    shape = RoundedCornerShape(100.dp),
                    color = Emerald50,
                    border = BorderStroke(1.dp, Emerald100)
                ) {
                    Text(
                        text = "#${caption.categoryName}",
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Emerald700,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Caption Bengali Text
                Text(
                    text = "“${caption.text}”",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    textAlign = TextAlign.Center,
                    color = Slate700,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp)
                )

                Spacer(modifier = Modifier.height(22.dp))

                // Action Row (Copy, Share, Favorite)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Copy
                    Button(
                        onClick = { onCopy(caption.text) },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Emerald600,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "কপি করুন",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "কপি", fontWeight = FontWeight.Bold)
                    }

                    // Share
                    OutlinedButton(
                        onClick = { onShare(caption.text) },
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier.size(44.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "শেয়ার করুন",
                            modifier = Modifier.size(16.dp),
                            tint = Slate600
                        )
                    }

                    // Favorite
                    IconButton(
                        onClick = { onToggleFavorite(caption) },
                        modifier = Modifier.size(44.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "পছন্দ",
                            tint = if (isFavorite) RedFavorite else Slate400,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Next Random Button
                Button(
                    onClick = onNextRandom,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Emerald50, contentColor = Emerald700),
                    border = BorderStroke(1.dp, Emerald100),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "অন্য ক্যাপশন",
                        modifier = Modifier.size(18.dp),
                        tint = Emerald700
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "আরেকটি ক্যাপশন দেখুন",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
