package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BurgundyDarker
import com.example.ui.theme.BurgundyDeep
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.CrimsonRedDark
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.EmeraldSuccessBg
import com.example.ui.theme.MetallicSilver
import com.example.ui.theme.MetallicSilverDark
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianBorderSubtle
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ObsidiaCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    borderColor: Color = ObsidianBorder,
    backgroundColor: Color = ObsidianCard,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val clickableModifier = if (onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else Modifier

    Surface(
        modifier = modifier
            .clip(shape)
            .border(BorderStroke(1.dp, borderColor), shape)
            .then(clickableModifier),
        shape = shape,
        color = backgroundColor,
        shadowElevation = 0.dp
    ) {
        Box(modifier = Modifier.padding(contentPadding)) {
            content()
        }
    }
}

@Composable
fun ObsidiaCrimsonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    testTag: String = "obsidia_crimson_btn"
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .heightIn(min = 52.dp)
            .testTag(testTag),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = TextPrimary,
            disabledContainerColor = ObsidianCardElevated,
            disabledContentColor = TextMuted
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (enabled) {
                        Brush.horizontalGradient(
                            colors = listOf(CrimsonRed, CrimsonRedDark, BurgundyDeep)
                        )
                    } else {
                        Brush.horizontalGradient(
                            colors = listOf(ObsidianCardElevated, ObsidianCardElevated)
                        )
                    },
                    shape = RoundedCornerShape(14.dp)
                )
                .border(
                    BorderStroke(1.dp, if (enabled) CrimsonRed else ObsidianBorderSubtle),
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (enabled) TextPrimary else TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = if (enabled) TextPrimary else TextMuted
                )
            }
        }
    }
}

@Composable
fun ObsidiaOutlinedBtn(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    testTag: String = "obsidia_outlined_btn"
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .heightIn(min = 48.dp)
            .testTag(testTag),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, ObsidianBorder),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = ObsidianCard,
            contentColor = TextPrimary
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MetallicSilver,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = TextPrimary
            )
        }
    }
}

@Composable
fun StatusBadge(
    text: String,
    color: Color = EmeraldSuccess,
    bgColor: Color = EmeraldSuccessBg
) {
    Box(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(20.dp))
            .border(BorderStroke(1.dp, color.copy(alpha = 0.5f)), RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = color,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CircularMetricGauge(
    valuePercent: Int?,
    label: String,
    subLabel: String,
    accentColor: Color,
    modifier: Modifier = Modifier,
    size: Dp = 110.dp
) {
    val targetVal = valuePercent?.toFloat()?.coerceIn(0f, 100f) ?: 0f
    val animatedPercent by animateFloatAsState(
        targetValue = targetVal,
        label = "gauge_anim"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.size(size),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = 10.dp.toPx()
                val radius = (this.size.minDimension - strokeWidth) / 2
                val center = Offset(this.size.width / 2, this.size.height / 2)

                // Track ring
                drawCircle(
                    color = ObsidianCardElevated,
                    radius = radius,
                    center = center,
                    style = Stroke(width = strokeWidth)
                )

                // Active arc if value is available
                if (valuePercent != null) {
                    val sweepAngle = (animatedPercent / 100f) * 360f
                    drawArc(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                accentColor.copy(alpha = 0.6f),
                                accentColor,
                                CrimsonRed
                            )
                        ),
                        startAngle = -90f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (valuePercent != null) "$valuePercent%" else "N/A",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (valuePercent != null) TextPrimary else MetallicSilverDark
                )
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subLabel,
            fontSize = 12.sp,
            color = MetallicSilverDark,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun SparklineWaveChart(
    points: List<Int>,
    modifier: Modifier = Modifier,
    lineColor: Color = CrimsonRed
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(ObsidianCardElevated, RoundedCornerShape(12.dp))
            .border(BorderStroke(1.dp, ObsidianBorderSubtle), RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        if (points.isEmpty()) {
            Text(
                text = "Collecting CPU history...",
                color = TextMuted,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val maxVal = 100f
                val minVal = 0f
                val width = size.width
                val height = size.height

                val stepX = if (points.size > 1) width / (points.size - 1) else width

                val path = Path()
                val fillPath = Path()

                points.forEachIndexed { index, value ->
                    val x = index * stepX
                    val normalizedY = 1f - ((value - minVal) / (maxVal - minVal)).coerceIn(0f, 1f)
                    val y = normalizedY * height

                    if (index == 0) {
                        path.moveTo(x, y)
                        fillPath.moveTo(x, height)
                        fillPath.lineTo(x, y)
                    } else {
                        path.lineTo(x, y)
                        fillPath.lineTo(x, y)
                    }

                    if (index == points.size - 1) {
                        fillPath.lineTo(x, height)
                        fillPath.close()
                    }
                }

                // Fill gradient underneath
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            lineColor.copy(alpha = 0.35f),
                            Color.Transparent
                        )
                    )
                )

                // Line stroke
                drawPath(
                    path = path,
                    color = lineColor,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }
    }
}
