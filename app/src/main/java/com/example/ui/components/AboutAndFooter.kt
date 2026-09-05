package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.NavSection
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.PolishBorder
import com.example.ui.theme.PolishSurface
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800

@Composable
fun AboutSection(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("about_section"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = PolishSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, PolishBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Emerald50,
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "🌿", fontSize = 22.sp)
                    }
                }
                Column {
                    Text(
                        text = "আমাদের সম্পর্কে",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Slate800
                    )
                    Text(
                        text = "বাংলা ক্যাপশন প্ল্যাটফর্ম",
                        style = MaterialTheme.typography.bodySmall,
                        color = Emerald600,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "বাংলা ক্যাপশন হলো বাংলা ভাষায় সুন্দর ও অনুভূতিপূর্ণ ক্যাপশন খুঁজে পাওয়ার একটি সহজ প্ল্যাটফর্ম। এখানে বিভিন্ন অনুভূতি ও পরিস্থিতির জন্য ক্যাপশন পাওয়া যাবে।",
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = 26.sp,
                    fontSize = 15.sp
                ),
                color = Slate600
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Stats / Highlights
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HighlightPill(modifier = Modifier.weight(1f), emoji = "🎯", text = "১৫০+ ক্যাপশন")
                HighlightPill(modifier = Modifier.weight(1f), emoji = "⚡", text = "কপি ও শেয়ার")
                HighlightPill(modifier = Modifier.weight(1f), emoji = "💚", text = "পছন্দের তালিকা")
            }
        }
    }
}

@Composable
private fun HighlightPill(emoji: String, text: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Emerald50,
        border = BorderStroke(1.dp, Emerald100),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = emoji, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = Emerald800
            )
        }
    }
}

@Composable
fun SocialMediaAndFooter(
    onNavigate: (NavSection) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val openSocialLink = { url: String, platform: String ->
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "$platform লিংক খোলা যাচ্ছে না", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PolishSurface,
                shape = RoundedCornerShape(26.dp)
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Social Media Section
        Text(
            text = "আমাদের অনুসরণ করুন",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Slate800
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Facebook Button
            Button(
                onClick = { openSocialLink("https://www.facebook.com", "Facebook") },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1877F2),
                    contentColor = Color.White
                ),
                modifier = Modifier.testTag("social_facebook")
            ) {
                Text(text = "Facebook", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }

            // YouTube Button
            Button(
                onClick = { openSocialLink("https://www.youtube.com", "YouTube") },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF0000),
                    contentColor = Color.White
                ),
                modifier = Modifier.testTag("social_youtube")
            ) {
                Text(text = "YouTube", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }

            // Instagram Button
            Button(
                onClick = { openSocialLink("https://www.instagram.com", "Instagram") },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE4405F),
                    contentColor = Color.White
                ),
                modifier = Modifier.testTag("social_instagram")
            ) {
                Text(text = "Instagram", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider(color = Slate200)
        Spacer(modifier = Modifier.height(20.dp))

        // Brand & Slogan
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Emerald600,
                modifier = Modifier.size(24.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "ব", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
            Text(
                text = "বাংলা ক্যাপশন",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Emerald800
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "“আপনার অনুভূতি, আমাদের ক্যাপশন।”",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Slate600,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Footer Navigation Links
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FooterLink(text = "হোম") { onNavigate(NavSection.HOME) }
            Text(text = " • ", color = Slate400)
            FooterLink(text = "ক্যাটাগরি") { onNavigate(NavSection.CATEGORIES) }
            Text(text = " • ", color = Slate400)
            FooterLink(text = "জনপ্রিয়") { onNavigate(NavSection.POPULAR) }
            Text(text = " • ", color = Slate400)
            FooterLink(text = "আমাদের সম্পর্কে") { onNavigate(NavSection.ABOUT) }
            Text(text = " • ", color = Slate400)
            FooterLink(text = "যোগাযোগ") {
                Toast.makeText(context, "যোগাযোগ: support@banglacaption.app", Toast.LENGTH_LONG).show()
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Copyright
        Text(
            text = "© 2026 বাংলা ক্যাপশন. সর্বস্বত্ব সংরক্ষিত।",
            style = MaterialTheme.typography.labelSmall,
            color = Slate400,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun FooterLink(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = Emerald600,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 2.dp)
    )
}
