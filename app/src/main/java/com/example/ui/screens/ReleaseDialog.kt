package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.ObsidiaCard
import com.example.ui.components.ObsidiaCrimsonButton
import com.example.ui.components.ObsidiaOutlinedBtn
import com.example.ui.components.StatusBadge
import com.example.ui.theme.BurgundyDeep
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.EmeraldSuccessBg
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReleaseDialog(
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    var ownerRepo by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("zuccheroxix-lab/OBSIDIA-TOOLS") }
    val repoUrl = "https://github.com/${ownerRepo.trim()}"
    val releasesUrl = "https://github.com/${ownerRepo.trim()}/releases"
    val releaseDownloadUrl = "https://github.com/${ownerRepo.trim()}/releases/download/v1.0.1/ObsidiaTools-v1.0.1.apk"
    val debugDownloadUrl = "https://github.com/${ownerRepo.trim()}/releases/download/v1.0.1/ObsidiaTools-v1.0.1-debug.apk"
    val releaseSha256 = "3121219bcc78389834e9eb20f18b897c35eb88c13dc8a8f74c138a1538aa0a96"
    val debugSha256 = "19e6291e418cf1829848b107e9c4bbeac37d05211f58a879e0b14084aa6d9511"

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier.testTag("release_dialog")
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, ObsidianBorder, RoundedCornerShape(20.dp)),
            color = ObsidianBg
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Top Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DOWNLOAD & RELEASE",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = CrimsonRed,
                        letterSpacing = 1.sp
                    )

                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // GitHub Card & Repository Target
                ObsidiaCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = ObsidianBorder
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(ObsidianCardElevated, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = MetallicSilver,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "GitHub Target",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "Repository: $ownerRepo",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }

                            StatusBadge(
                                text = "Release v1.0.1",
                                color = EmeraldSuccess,
                                bgColor = EmeraldSuccessBg
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Editable repo field
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(ObsidianCardElevated)
                                .border(1.dp, ObsidianBorder, RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            androidx.compose.foundation.text.BasicTextField(
                                value = ownerRepo,
                                onValueChange = { ownerRepo = it },
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    color = TextPrimary,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Release Info Box
                ObsidiaCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = ObsidianBorder
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "v1.0.1 Production",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = TextPrimary
                            )
                            StatusBadge(
                                text = "Build Success",
                                color = EmeraldSuccess,
                                bgColor = EmeraldSuccessBg
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Release (19 MB) • Debug (25 MB) • Signed & Verified",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // DOWNLOAD RELEASE APK Button
                        ObsidiaCrimsonButton(
                            text = "DOWNLOAD RELEASE APK",
                            onClick = {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(releaseDownloadUrl))
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(releasesUrl))
                                        context.startActivity(intent)
                                    } catch (ex: Exception) {
                                        Toast.makeText(context, "Buka: $releasesUrl", Toast.LENGTH_LONG).show()
                                    }
                                }
                            },
                            icon = Icons.Default.Download,
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "download_release_apk_btn"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // DOWNLOAD DEBUG APK Button
                        ObsidiaOutlinedBtn(
                            text = "DOWNLOAD DEBUG APK",
                            onClick = {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(debugDownloadUrl))
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(releasesUrl))
                                        context.startActivity(intent)
                                    } catch (ex: Exception) {
                                        Toast.makeText(context, "Buka: $releasesUrl", Toast.LENGTH_LONG).show()
                                    }
                                }
                            },
                            icon = Icons.Default.Download,
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "download_debug_apk_btn"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // SHA-256 Checksum
                Text(
                    text = "Release APK SHA-256 Checksum",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(ObsidianCardElevated)
                        .border(1.dp, ObsidianBorder, RoundedCornerShape(10.dp))
                        .clickable {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("SHA256", releaseSha256))
                            Toast.makeText(context, "Checksum disalin ke clipboard!", Toast.LENGTH_SHORT).show()
                        }
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${releaseSha256.take(24)}...",
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            color = CrimsonRed
                        )
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = MetallicSilver,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Links
                LinkRow(
                    label = "Repository Link",
                    url = repoUrl,
                    onClick = {
                        try {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(repoUrl)))
                        } catch (e: Exception) {
                            Toast.makeText(context, repoUrl, Toast.LENGTH_LONG).show()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                LinkRow(
                    label = "Releases Link",
                    url = releasesUrl,
                    onClick = {
                        try {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(releasesUrl)))
                        } catch (e: Exception) {
                            Toast.makeText(context, releasesUrl, Toast.LENGTH_LONG).show()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Notice
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = MetallicSilver,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "APK dapat diunduh langsung dari halaman release di GitHub. Pastikan sumber terpercaya.",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bottom Brand Emblem
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_obsidia_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "OBSIDIA TOOLS",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary
                    )
                    Text(
                        text = "Built with Passion by ZUCCHERO XANN",
                        fontSize = 11.sp,
                        color = MetallicSilverDark
                    )
                }
            }
        }
    }
}

@Composable
private fun LinkRow(
    label: String,
    url: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Icon(
                imageVector = Icons.Outlined.OpenInNew,
                contentDescription = null,
                tint = CrimsonRed,
                modifier = Modifier.size(14.dp)
            )
        }
        Text(
            text = url,
            fontSize = 11.sp,
            color = Color(0xFF00E5FF)
        )
    }
}
