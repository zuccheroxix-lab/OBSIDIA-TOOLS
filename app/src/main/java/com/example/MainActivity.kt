package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.GameLauncherScreen
import com.example.ui.screens.GamesScreen
import com.example.ui.screens.GraphicsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MonitorScreen
import com.example.ui.screens.ReleaseDialog
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.MetallicSilverDark
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidiaTheme
import com.example.ui.viewmodel.NavigationTab
import com.example.ui.viewmodel.ObsidiaViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ObsidiaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ObsidiaTheme {
                ObsidiaApp(viewModel = viewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startMonitoring()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseMonitoring()
    }
}

@Composable
fun ObsidiaApp(viewModel: ObsidiaViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val isLauncherOpen by viewModel.isLauncherOpen.collectAsState()
    val selectedGame by viewModel.selectedGame.collectAsState()
    val isReleaseDialogOpen by viewModel.isReleaseDialogOpen.collectAsState()

    // Handle back button on sub-screens
    BackHandler(enabled = isLauncherOpen || currentTab != NavigationTab.HOME) {
        if (isLauncherOpen) {
            viewModel.closeLauncher()
        } else if (currentTab != NavigationTab.HOME) {
            viewModel.setTab(NavigationTab.HOME)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ObsidianBg,
        bottomBar = {
            if (!isLauncherOpen) {
                ObsidiaBottomNavigation(
                    currentTab = currentTab,
                    onTabSelected = { viewModel.setTab(it) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLauncherOpen && selectedGame != null) {
                GameLauncherScreen(
                    viewModel = viewModel,
                    game = selectedGame!!
                )
            } else {
                AnimatedContent(
                    targetState = currentTab,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "tab_transition"
                ) { targetTab ->
                    when (targetTab) {
                        NavigationTab.HOME -> HomeScreen(viewModel = viewModel)
                        NavigationTab.GRAPHICS -> GraphicsScreen(viewModel = viewModel)
                        NavigationTab.MONITOR -> MonitorScreen(viewModel = viewModel)
                        NavigationTab.GAMES -> GamesScreen(viewModel = viewModel)
                        NavigationTab.SETTINGS -> SettingsScreen(viewModel = viewModel)
                    }
                }
            }

            if (isReleaseDialogOpen) {
                ReleaseDialog(
                    onDismissRequest = { viewModel.setReleaseDialogOpen(false) }
                )
            }
        }
    }
}

@Composable
fun ObsidiaBottomNavigation(
    currentTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ObsidianBg)
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(ObsidianCard)
                .border(1.dp, ObsidianBorder, RoundedCornerShape(18.dp))
                .padding(vertical = 6.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                title = "Home",
                selected = currentTab == NavigationTab.HOME,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                onClick = { onTabSelected(NavigationTab.HOME) },
                testTag = "nav_home"
            )

            BottomNavItem(
                title = "Graphics",
                selected = currentTab == NavigationTab.GRAPHICS,
                selectedIcon = Icons.Filled.Tune,
                unselectedIcon = Icons.Outlined.Tune,
                onClick = { onTabSelected(NavigationTab.GRAPHICS) },
                testTag = "nav_graphics"
            )

            BottomNavItem(
                title = "Monitor",
                selected = currentTab == NavigationTab.MONITOR,
                selectedIcon = Icons.Filled.Speed,
                unselectedIcon = Icons.Outlined.Speed,
                onClick = { onTabSelected(NavigationTab.MONITOR) },
                testTag = "nav_monitor"
            )

            BottomNavItem(
                title = "Games",
                selected = currentTab == NavigationTab.GAMES,
                selectedIcon = Icons.Filled.SportsEsports,
                unselectedIcon = Icons.Outlined.SportsEsports,
                onClick = { onTabSelected(NavigationTab.GAMES) },
                testTag = "nav_games"
            )

            BottomNavItem(
                title = "Settings",
                selected = currentTab == NavigationTab.SETTINGS,
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                onClick = { onTabSelected(NavigationTab.SETTINGS) },
                testTag = "nav_settings"
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    title: String,
    selected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    onClick: () -> Unit,
    testTag: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .testTag(testTag),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = if (selected) selectedIcon else unselectedIcon,
                contentDescription = title,
                tint = if (selected) CrimsonRed else MetallicSilverDark,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                color = if (selected) CrimsonRed else MetallicSilverDark
            )
        }
    }
}
