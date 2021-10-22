package com.example.postdemojetpackcompose

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.postdemojetpackcompose.network.model.UserPostResponseItem
import com.example.postdemojetpackcompose.ui.theme.viewmodel.PostListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: PostListViewModel by viewModels()

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.PostList.route) {
                composable(route = Screen.PostList.route) {
                    PostListScreen(
                        onNavigateToPostDetailScreen = navController::navigate,
                        viewModel = vm,
                    )
                }
                composable(
                    route = Screen.PostDetail.route + "/{postId}",
                    arguments = listOf(navArgument("postId") {
                        type = NavType.IntType
                    })
                ) { navBackStackEntry ->
                    val postId = navBackStackEntry.arguments?.getInt("postId")
                    PostDetail(vm, postId) {
                    }

                }
            }

        }
    }
}

@Composable
fun PostListScreen(
    onNavigateToPostDetailScreen: (String) -> Unit,
    viewModel: PostListViewModel,
) {
    Log.d(TAG, "PostListScreen: ${viewModel}")

    val posts =
        viewModel.posts.value
    val userPostIndex =
        (1..10).random()  //Every Time Fetch by random UserId from 1 to 10 if not exist
    viewModel.getPostLists(userPostIndex)
    RecipeList(
        posts = posts,
        onNavigateToRecipeDetailScreen = onNavigateToPostDetailScreen
    )
}


@Composable
fun RecipeList(
    posts: List<UserPostResponseItem>,
    onNavigateToRecipeDetailScreen: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
    ) {


        LazyColumn {
            itemsIndexed(
                items = posts
            ) { _, post ->

                //    onChangeScrollPosition(index)
                RecipeCard(
                    post = post,
                    onClick = {
                        val route = Screen.PostDetail.route + "/${post.id}"
                        onNavigateToRecipeDetailScreen(route)
                    }
                )
            }
        }
    }
}


@Composable
fun RecipeCard(
    post: UserPostResponseItem,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = post.title,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h5
                )
                val body = post.body
                Text(
                    text = body,
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
fun PostDetail(
    vm: PostListViewModel,
    postId: Int?,
    onClick: () -> Unit
) {
    postId?.let { vm.getDetailScreenData(postId = it) }
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
    ) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = vm.postDetailUI.value.userData?.name.toString(),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h5
                )
                val body = vm.postDetailUI.value.post?.title.toString()
                Text(
                    text = body,
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = vm.postDetailUI.value.comments?.size.toString(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Preview
@Composable
fun ShowCardPreview() {
    RecipeCard(UserPostResponseItem(1, "sa", "sasdas", 2), {})
}

sealed class Screen(
    val route: String,
) {
    object PostList : Screen("postList")

    object PostDetail : Screen("postDetail")
}
