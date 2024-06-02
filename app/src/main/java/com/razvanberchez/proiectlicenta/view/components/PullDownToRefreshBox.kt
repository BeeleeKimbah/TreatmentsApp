package com.razvanberchez.proiectlicenta.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullDownToRefreshBox(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues? = null,
    onRefresh: () -> Unit,
    loading: Boolean,
    content: @Composable () -> Unit,
) {
    val pullRefreshState = rememberPullToRefreshState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues ?: PaddingValues())
            .nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {
        if (!loading) {
            content()
        }

        if (pullRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                onRefresh()
            }
        }

        LaunchedEffect(loading) {
            if (loading) {
                pullRefreshState.startRefresh()
            } else {
                pullRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullRefreshState,
            modifier = modifier.align(Alignment.TopCenter),
            indicator = {
                if (pullRefreshState.isRefreshing)
                    CircularProgressIndicator()
                else
                    CircularProgressIndicator(
                        progress = { it.progress }
                    )
            }
        )
    }
}