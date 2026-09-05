package com.example.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Caption
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald600
import com.example.ui.theme.PolishBorder
import com.example.ui.theme.RedFavorite
import com.example.ui.theme.RedFavoriteBg
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800

@Composable
fun CaptionCard(
    caption: Caption,
    index: Int,
    isFavorite: Boolean,
    onCopy: (String) -> Unit,
    onShare: (String) -> Unit,
    onToggleFavorite: (Caption) -> Unit,
    modifier: Modifier = Modifier
) {
    val heartScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.25f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "heart_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("caption_card_${caption.id}"),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.5.dp,
            pressedElevation = 3.dp
        ),
        border = BorderStroke(1.dp, PolishBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
        ) {
            // Header Row: Pill Badge and Favorite Heart Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Pill Badge (#ক্যাটাগরি_সংখ্যা)
                Surface(
                    shape = RoundedCornerShape(100.dp),
                    color = Emerald50,
                    border = BorderStroke(1.dp, Emerald100)
                ) {
                    Text(
                        text = "#${caption.categoryName}_${String.format("%02d", (index % 99) + 1)}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Emerald600,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                // Heart Favorite Action
                IconButton(
                    onClick = { onToggleFavorite(caption) },
                    modifier = Modifier
                        .size(38.dp)
                        .scale(heartScale)
                        .testTag("favorite_button_${caption.id}"),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (isFavorite) RedFavoriteBg else Color.Transparent
                    )
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite) "পছন্দ থেকে বাদ দিন" else "পছন্দ তালিকায় রাখুন",
                        tint = if (isFavorite) RedFavorite else Slate400,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Caption Bengali Text
            Text(
                text = "“${caption.text}”",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 17.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = Slate700,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Bottom Action Bar (Full width Copy + Rounded Share)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Copy Button (Emerald Filled)
                Button(
                    onClick = { onCopy(caption.text) },
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .testTag("copy_button_${caption.id}"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Emerald600,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 1.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "কপি করুন",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "কপি করুন",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                // Share Button (Outlined Square Rounded)
                OutlinedButton(
                    onClick = { onShare(caption.text) },
                    modifier = Modifier
                        .size(46.dp)
                        .testTag("share_button_${caption.id}"),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Slate200),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Slate600
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "শেয়ার করুন",
                        modifier = Modifier.size(18.dp),
                        tint = Slate600
                    )
                }
            }
        }
    }
}
