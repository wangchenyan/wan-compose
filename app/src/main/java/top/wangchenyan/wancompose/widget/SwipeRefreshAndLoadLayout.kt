package top.wangchenyan.wancompose.widget

import android.util.Log
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private const val TAG = "SwipeToRefreshAndLoad"

private val RefreshDistance = 80.dp
private val LoadDistance = 100.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeRefreshAndLoadLayout(
    refreshingState: Boolean,
    loadState: Boolean,
    onRefresh: () -> Unit,
    onLoad: () -> Unit,
    content: @Composable () -> Unit
) {
    val refreshDistance = with(LocalDensity.current) { RefreshDistance.toPx() }
    val loadDistance = with(LocalDensity.current) { LoadDistance.toPx() }
    val state = rememberSwipeableState(refreshingState) { newValue ->
        // compare both copies of the swipe state before calling onRefresh(). This is a workaround.
        if (newValue && !refreshingState) onRefresh()
        true
    }

    val loadRefreshState = rememberSwipeableState(loadState) { newValue ->
        // compare both copies of the swipe state before calling onRefresh(). This is a workaround.
        if (newValue && !loadState) onLoad()
        true
    }

    Box(
        modifier = Modifier
            .nestedScroll(state.PreUpPostDownNestedScrollConnection)
            .swipeable(
                state = state,
                anchors = mapOf(
                    -refreshDistance to false,
                    refreshDistance to true,
                ),
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Vertical
            )
            .nestedScroll(loadRefreshState.LoadPreUpPostDownNestedScrollConnection)
            .swipeable(
                state = loadRefreshState,
                anchors = mapOf(
                    loadDistance to false,
                    -loadDistance to true,
                ),
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Vertical
            )
            .fillMaxSize()
    ) {
        content()
        Box(
            Modifier
                .align(Alignment.TopCenter)
                .offset { IntOffset(0, state.offset.value.roundToInt()) }
        ) {
            if (state.offset.value != -refreshDistance) {
                Surface(elevation = 10.dp, shape = CircleShape) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(36.dp)
                            .padding(4.dp)
                    )
                }
            }
        }

        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .offset { IntOffset(0, loadRefreshState.offset.value.roundToInt()) }
        ) {
            if (loadRefreshState.offset.value != loadDistance) {
                Surface(elevation = 10.dp, shape = CircleShape) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(36.dp)
                            .padding(4.dp)
                    )
                }
            }
        }

        // TODO (https://issuetracker.google.com/issues/164113834): This state->event trampoline is a
        //  workaround for a bug in the SwipableState API. Currently, state.value is a duplicated
        //  source of truth of refreshingState.
        LaunchedEffect(refreshingState) { state.animateTo(refreshingState) }
        LaunchedEffect(loadState) { loadRefreshState.animateTo(loadState) }
    }
}

/**
 * Temporary workaround for nested scrolling behavior. There is no default implementation for
 * pull to refresh yet, this nested scroll connection mimics the behavior.
 */
@ExperimentalMaterialApi
private val <T> SwipeableState<T>.PreUpPostDownNestedScrollConnection: NestedScrollConnection
    get() = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.toFloat()
            return if (delta < 0 && source == NestedScrollSource.Drag) {
                Log.e(TAG, "PreUpPost onPreScroll111: delta:$delta")
                performDrag(delta).toOffset()
            } else {
                Log.e(TAG, "PreUpPost onPreScroll222: Offset.Zero:${Offset.Zero}")
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            return if (source == NestedScrollSource.Drag) {
                Log.e(TAG, "PreUpPost onPostScroll: available.toFloat():${available.toFloat()}")
                performDrag(available.toFloat()).toOffset()
            } else {
                Log.e(TAG, "PreUpPost onPostScroll: Offset.Zero:${Offset.Zero}")
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            val toFling = Offset(available.x, available.y).toFloat()
            return if (toFling < 0) {
                Log.e(TAG, "PreUpPost onPreFling: available$available")
                performFling(velocity = toFling)
                // since we go to the anchor with tween settling, consume all for the best UX
                // available
                Velocity.Zero
            } else {
                Log.e(TAG, "PreUpPost onPreFling: Offset.Zero:${Offset.Zero}")
                Velocity.Zero
            }
        }

        override suspend fun onPostFling(
            consumed: Velocity,
            available: Velocity
        ): Velocity {
            Log.e(TAG, "PreUpPost onPostFling: consumed:$consumed   available:$available")
            performFling(velocity = Offset(available.x, available.y).toFloat())
            return Velocity.Zero
        }

        private fun Float.toOffset(): Offset = Offset(0f, this)

        private fun Offset.toFloat(): Float = this.y
    }


/**
 * Temporary workaround for nested scrolling behavior. There is no default implementation for
 * pull to refresh yet, this nested scroll connection mimics the behavior.
 */
@ExperimentalMaterialApi
private val <T> SwipeableState<T>.LoadPreUpPostDownNestedScrollConnection: NestedScrollConnection
    get() = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.toFloat()
            return if (delta > 0 && source == NestedScrollSource.Drag) {
                Log.e(TAG, "LoadPreUp onPreScroll: delta:$delta")
                performDrag(delta).toOffset()
            } else {
                Log.e(TAG, "LoadPreUp onPreScroll222: Offset.Zero:${Offset.Zero}")
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            return if (source == NestedScrollSource.Drag) {
                Log.e(TAG, "LoadPreUp onPostScroll: available.toFloat():${available.toFloat()}")
                performDrag(available.toFloat()).toOffset()
            } else {
                Log.e(TAG, "LoadPreUp onPostScroll: Offset.Zero:${Offset.Zero}")
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            val toFling = Offset(available.x, available.y).toFloat()
            return if (toFling > 0) {
                Log.e(TAG, "LoadPreUp onPreFling: available$available")
                performFling(velocity = toFling)
                // since we go to the anchor with tween settling, consume all for the best UX
                // available
                Velocity.Zero
            } else {
                Log.e(TAG, "LoadPreUp onPreFling: Offset.Zero:${Offset.Zero}")
                Velocity.Zero
            }
        }

        override suspend fun onPostFling(
            consumed: Velocity,
            available: Velocity
        ): Velocity {
            Log.e(TAG, "LoadPreUp onPostFling: consumed:$consumed   available:$available")
            performFling(velocity = Offset(available.x, available.y).toFloat())
            return Velocity.Zero
        }

        private fun Float.toOffset(): Offset = Offset(0f, this)

        private fun Offset.toFloat(): Float = this.y
    }