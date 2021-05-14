/**
 * Copyright 2021 Kenji Abe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.wcy.wanandroid.compose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

/**
 * A FlowLayout
 *
 * @param modifier The modifier to be applied to the layout.
 * @param verticalSpacing Vertical space between items.
 * @param horizontalSpacing Horizontal space between items.
 * @param content The children composable to be laid out.
 */
@Composable
public fun FlowLayout(
    modifier: Modifier = Modifier,
    verticalSpacing: Dp = 0.dp,
    horizontalSpacing: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        data class FlowContent(
            val placeable: Placeable,
            val x: Int,
            val y: Int
        )

        var y = 0
        var x = 0
        var rowMaxY = 0
        val flowContents = mutableListOf<FlowContent>()

        val verticalSpacingPx = verticalSpacing.roundToPx()
        val horizontalSpacingPx = horizontalSpacing.roundToPx()

        placeables.forEach { placeable ->
            if (placeable.width + x > constraints.maxWidth) {
                x = 0
                y += rowMaxY
                rowMaxY = 0
            }
            rowMaxY = max(placeable.height + verticalSpacingPx, rowMaxY)

            flowContents.add(FlowContent(placeable, x, y))
            x += placeable.width + horizontalSpacingPx
        }
        y += rowMaxY

        layout(constraints.maxWidth, y) {
            flowContents.forEach {
                it.placeable.place(it.x, it.y)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewFlowRow() {
    MaterialTheme {
        Surface {
            FlowLayout(
                modifier = Modifier.padding(8.dp),
                verticalSpacing = 8.dp,
                horizontalSpacing = 8.dp,
            ) {
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp)
                        .background(color = Color.Blue)
                )

                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp)
                        .background(color = Color.Red)
                )

                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp)
                        .background(color = Color.Blue)
                )

                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp)
                        .background(color = Color.Red)
                )

                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp)
                        .background(color = Color.Blue)
                )
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(40.dp)
                        .background(color = Color.Red)
                )
            }
        }
    }
}