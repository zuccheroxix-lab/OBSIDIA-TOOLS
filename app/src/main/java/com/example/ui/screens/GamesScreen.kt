package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.GameTarget
import com.example.ui.components.ObsidiaCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.AmberWarningBg
import com.example.ui.theme.BurgundyDeep
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.EmeraldSuccessBg
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCardElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.ObsidiaViewModel

@Composable
fun GamesScreen(
    viewModel: ObsidiaViewModel,
    modifier: Modifier = Modifier
) {
    val games by viewModel.gameList.collectAsState()
    val language by viewModel.language.collectAsState()
    val context = LocalContext.current
    val isIndo = language == AppLanguage.INDONESIAN

    var selectedFilter by remember { mutableIntStateOf(0) } // 0: All Games, 1: Installed
    var searchQuery by remember { mutableStateOf("") }

    val filteredGames = remember(games, selectedFilter, searchQuery) {
        games.filter { game ->
            val matchesFilter = if (selectedFilter == 1) game.isInstalled else true
            val matchesSearch = game.name.contains(searchQuery, ignoreCase = true) ||
                    game.developer.contains(searchQuery, ignoreCase = true)
            matchesFilter && matchesSearch
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.SportsEsports,
                    contentDescription = null,
                    tint = CrimsonRed,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Game Management",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = if (isIndo) "Pilih game dan atur preset grafis." else "Choose game and customize graphics preset.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        item {
            // Filter Pills: All Games / Installed
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ObsidianCardElevated)
                    .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(
                    if (isIndo) "All Games" else "All Games",
                    if (isIndo) "Installed" else "Installed"
                ).forEachIndexed { index, title ->
                    val isSelected = selectedFilter == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) CrimsonRed else Color.Transparent)
                            .clickable { selectedFilter = index }
                            .padding(vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) TextPrimary else TextSecondary
                        )
                    }
                }
            }
        }

        item {
            // Search Input Field
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ObsidianCardElevated)
                    .border(1.dp, ObsidianBorder, RoundedCornerShape(12.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MetallicSilver,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("game_search_input"),
                        textStyle = TextStyle(
                            color = TextPrimary,
                            fontSize = 14.sp
                        ),
                        cursorBrush = SolidColor(CrimsonRed),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = if (isIndo) "Cari game..." else "Search game...",
                                    color = TextMuted,
                                    fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }
        }

        if (filteredGames.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isIndo) "Tidak ada game yang ditemukan" else "No games found matching criteria",
                        color = TextMuted,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            items(filteredGames, key = { it.id }) { game ->
                GameItemRow(
                    game = game,
                    onOpenClicked = {
                        val result = viewModel.launchGame(game)
                        if (result.isSuccess) {
                            Toast.makeText(context, "Meluncurkan ${game.name}...", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.openLauncherForGame(game)
                        }
                    },
                    onCardClicked = {
                        viewModel.openLauncherForGame(game)
                    },
                    isIndo = isIndo
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun GameItemRow(
    game: GameTarget,
    onOpenClicked: () -> Unit,
    onCardClicked: () -> Unit,
    isIndo: Boolean
) {
    ObsidiaCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("game_item_${game.id}"),
        onClick = onCardClicked
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Game Avatar Badge
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(BurgundyDeep),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = game.name.take(2).uppercase(),
                        color = CrimsonRed,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = game.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "${game.developer} • ${game.playCategory}",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StatusBadge(
                            text = game.compatibility,
                            color = if (game.compatibility == "Compatible") EmeraldSuccess else AmberWarning,
                            bgColor = if (game.compatibility == "Compatible") EmeraldSuccessBg else AmberWarningBg
                        )
                        if (game.isInstalled) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Installed",
                                fontSize = 10.sp,
                                color = MetallicSilver,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Crimson Open Button
            Button(
                onClick = onOpenClicked,
                modifier = Modifier
                    .height(38.dp)
                    .testTag("open_game_${game.id}"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CrimsonRed,
                    contentColor = TextPrimary
                ),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Open",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
