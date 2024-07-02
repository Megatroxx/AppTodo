package com.example.apptodo.presentation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import com.example.apptodo.R
import com.example.apptodo.presentation.custom_components.Shadow
import com.example.apptodo.presentation.ui.theme.ToDoAppTheme
import com.example.apptodo.domain.entity.TodoItem
import com.example.apptodo.presentation.navigation.DestinationEnum
import com.example.apptodo.presentation.viewmodels.ItemListViewModel
import com.example.apptodo.presentation.viewmodels.RedactorViewModel
import com.example.apptodo.domain.entity.Relevance
import kotlinx.coroutines.flow.merge
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    navController: NavController,
    itemListViewModel : ItemListViewModel,
    redactorViewModel: RedactorViewModel,
) {
    val context = LocalContext.current
    val todoList by itemListViewModel.items.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val isVisible = itemListViewModel.isVisible.collectAsState()
    val doneCounter = itemListViewModel.counter.collectAsState()
    val errorState = merge(itemListViewModel.errorFlow, redactorViewModel.errorFlow)

    LaunchedEffect(itemListViewModel) {
        itemListViewModel.getItems()
        errorState.collect {
            if (it != null)
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


    ToDoAppTheme {

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = if (scrollBehavior.state.collapsedFraction > 0.5) 6.dp else 0.dp,
                            shape = RoundedCornerShape(0.dp)
                        )
                ) {
                    LargeTopAppBar(
                        title = {
                            Column {
                                if (scrollBehavior.state.collapsedFraction < 0.5) {
                                    Text(text =
                                    stringResource(R.string.my_items),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(start = 26.dp)
                                    )
                                } else {
                                    Text(
                                        stringResource(R.string.my_items),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(start = 26.dp)
                                    )
                                }

                            }
                        },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,

                            ),
                        actions = {
                            if (scrollBehavior.state.collapsedFraction > 0.8) {
                                IconButton(
                                    onClick = { itemListViewModel.changeVisible(isVisible.value) },
                                    Modifier.padding(end = 10.dp)
                                ) {
                                    if (isVisible.value){
                                        Icon(
                                            modifier = Modifier
                                                .size(30.dp),
                                            painter = painterResource(id = R.drawable.baseline_visibility_24),
                                            tint = MaterialTheme.colorScheme.tertiary,
                                            contentDescription = ""
                                        )
                                    }
                                    else {
                                        Icon(
                                            modifier = Modifier
                                                .size(30.dp),
                                            painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                                            tint = MaterialTheme.colorScheme.tertiary,
                                            contentDescription = ""
                                        )
                                    }

                                }
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    onClick = {
                        redactorViewModel.clearItem()
                        navController.navigate(DestinationEnum.REDACTOR_SCREEN.destString)
                    },
                    shape = CircleShape,
                    modifier = Modifier.padding(bottom = 18.dp, end = 18.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_add_24),
                        contentDescription = stringResource(R.string.add_new_task),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }

        ) { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp)
                    ),
            )
            {
                item {
                    AnimatedVisibility(
                        visible = scrollBehavior.state.collapsedFraction < 0.5,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 44.dp, end = 34.dp, bottom = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "${stringResource(R.string.done)} ${doneCounter.value}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            IconButton(
                                onClick = { itemListViewModel.changeVisible(isVisible.value) },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            ) {
                                if (isVisible.value){
                                    Icon(
                                        modifier = Modifier
                                            .size(30.dp),
                                        painter = painterResource(id = R.drawable.baseline_visibility_24),
                                        tint = MaterialTheme.colorScheme.tertiary,
                                        contentDescription = ""
                                    )
                                }
                                else {
                                    Icon(
                                        modifier = Modifier
                                            .size(30.dp),
                                        painter = painterResource(id = R.drawable.baseline_visibility_off_24),
                                        tint = MaterialTheme.colorScheme.tertiary,
                                        contentDescription = ""
                                    )
                                }

                            }
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(
                                    topStart = 12.dp,
                                    topEnd = 12.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 0.dp
                                )
                            )
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        )
                    }
                }
                items(todoList){ item ->
                    TodoItem(
                        todoItem = item,
                        itemListViewModel = itemListViewModel,
                        onCardClick = {
                            redactorViewModel.setItem(item)
                            navController.navigate(DestinationEnum.REDACTOR_SCREEN.destString)
                        }
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.new_),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 30.dp)
                            .Shadow(offsetY = 2.5.dp, blurRadius = 2.5.dp, borderRadius = 10.dp)
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 12.dp,
                                    bottomEnd = 12.dp
                                )
                            )
                            .padding(bottom = 20.dp, start = 48.dp, top = 18.dp)
                            .clickable {
                                redactorViewModel.clearItem()
                                navController.navigate(DestinationEnum.REDACTOR_SCREEN.destString)
                            },
                        color = MaterialTheme.colorScheme.onTertiary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}




@Composable
fun TodoItem(
    todoItem: TodoItem,
    itemListViewModel: ItemListViewModel,
    onCardClick: () -> Unit,
){

    ToDoAppTheme {

        val checkedState by rememberUpdatedState(newValue = todoItem.flagAchievement)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .Shadow(offsetY = 3.dp, blurRadius = 2.5.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 4.dp, end = 6.dp)
                .clickable {
                    onCardClick()
                }
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Checkbox(

                checked = todoItem.flagAchievement,
                onCheckedChange = {
                    itemListViewModel.checkItem(todoItem, it)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.scrim,
                    uncheckedColor =
                    if (todoItem.relevance == Relevance.URGENT) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onTertiary,
                    checkmarkColor = MaterialTheme.colorScheme.surface,
                    disabledCheckedColor = MaterialTheme.colorScheme.scrim,
                    disabledUncheckedColor = if (todoItem.relevance == Relevance.URGENT) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onTertiary,
                ),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 10.dp)
            ) {
                Box{
                    var itemText = todoItem.text
                    if (!checkedState && todoItem.relevance == Relevance.URGENT) {
                        Image(
                            painter = painterResource(R.drawable.hug),
                            contentDescription = stringResource(R.string.task_has_status_high),
                            modifier = Modifier
                                .padding(top = 4.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error)
                        )
                        itemText = "    $itemText"
                    } else if(!checkedState && todoItem.relevance == Relevance.LOW){
                        Image(
                            painter = painterResource(R.drawable.low),
                            contentDescription = stringResource(R.string.task_has_status_low),
                            modifier = Modifier
                                .padding(top = 4.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outlineVariant)
                        )
                        itemText = "     $itemText"
                    }
                    Text(
                        text = itemText,
                        modifier = Modifier
                            .padding(top = 2.dp),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        textDecoration = if (checkedState) TextDecoration.LineThrough
                        else TextDecoration.None,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (checkedState) MaterialTheme.colorScheme.onTertiary
                        else MaterialTheme.colorScheme.onPrimary,
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                todoItem.deadline?.let {
                    Text(
                        text = it.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
            Image(
                painter = painterResource(R.drawable.info),
                contentDescription = stringResource(R.string.show_information),
                modifier = Modifier
                    .padding(start = 8.dp, end = 10.dp, top = 12.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiary)
            )
        }

    }

}