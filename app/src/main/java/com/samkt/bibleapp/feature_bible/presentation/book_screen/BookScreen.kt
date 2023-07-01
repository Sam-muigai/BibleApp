package com.samkt.bibleapp.feature_bible.presentation.book_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun BookScreen(
    viewModel: BookScreenViewModel = hiltViewModel(),
    onClick: (String, String) -> Unit
) {
    val state = viewModel.screenState.collectAsState().value
    BookScreenContent(state = state, onClick = onClick)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreenContent(
    state: BookScreenState,
    onClick: (String, String) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        AnimatedVisibility(
            visible = state.loading,
            enter = fadeIn(
                initialAlpha = 1f,
                animationSpec = tween(2000)
            ),
            exit = fadeOut(
                targetAlpha = 1f,
                animationSpec = tween(2000)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(strokeWidth = 1.dp, color = Color.Red)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            state.errorMessage?.let {
                Text(text = it)
            }
            LazyVerticalGrid(
                contentPadding = PaddingValues(4.dp),
                columns = GridCells.Fixed(2),
                content = {
                    items(state.data) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clickable {
                                    onClick.invoke(it.id, it.name)
                                },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = it.name, style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                }
            )
        }
    }
}
















