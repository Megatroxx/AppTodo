package com.example.apptodo.presentation.custom_components

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.apptodo.R
import com.example.apptodo.data.entity.Relevance
import com.example.apptodo.data.entity.TodoItem
import com.example.apptodo.presentation.ui.theme.ToDoAppTheme
import com.example.apptodo.presentation.viewmodels.ItemListViewModel
import java.time.format.DateTimeFormatter


/**
 * Composable function representing a single todo item card.
 *
 * @param todoItem The todo item to display.
 * @param itemListViewModel The ViewModel managing the list of todo items.
 * @param onCardClick Callback function triggered when the card is clicked.
 */


@Composable
fun TodoItem(
    todoItem: TodoItem,
    itemListViewModel: ItemListViewModel,
    onCardClick: () -> Unit,
) {

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
                },
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
                Box {
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
                    } else if (!checkedState && todoItem.relevance == Relevance.LOW) {
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