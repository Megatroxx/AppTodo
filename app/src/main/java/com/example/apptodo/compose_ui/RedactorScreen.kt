package com.example.apptodo.compose_ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apptodo.R
import com.example.apptodo.compose_ui.custom_components.Divider
import com.example.apptodo.compose_ui.custom_components.PrimaryBodyText
import com.example.apptodo.compose_ui.ui.theme.ToDoAppTheme
import com.example.apptodo.utils.Relevance
import com.example.apptodo.navigation.DestinationEnum
import com.example.apptodo.viewmodels.RedactorViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.example.apptodo.data.entity.TodoItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedactorScreen(
    navController: NavController,
    redactorViewModel: RedactorViewModel
) {

    val context = LocalContext.current


    val currentItem by redactorViewModel.item.collectAsState()

    var textValue by remember { mutableStateOf(currentItem?.text.orEmpty()) }
    var relevance by remember { mutableStateOf(currentItem?.relevance ?: Relevance.BASE) }
    var deadline by remember { mutableStateOf(currentItem?.deadline.orEmpty()) }

    LaunchedEffect(key1 = Unit) {
        redactorViewModel.errorFlow.collect {
            if (it != null)
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    val scrollState = rememberScrollState()
    ToDoAppTheme {
        Scaffold(
            topBar = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = if (scrollState.value > 0) 6.dp else 0.dp,
                            shape = RoundedCornerShape(0.dp)
                        )
                ) {
                    TopAppBar(
                        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                        navigationIcon = {
                            Image(
                                painter = painterResource(R.drawable.baseline_close_24),
                                contentDescription = stringResource(R.string.delete_task),
                                modifier = Modifier
                                    .padding(start = 26.dp, top = 6.dp)
                                    .clickable {
                                        redactorViewModel.clearItem()
                                        navController.navigate(DestinationEnum.LIST_SCREEN.destString)
                                    },
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                            )
                        },
                        title = {},
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        ),
                        actions = {
                            Text(
                                text = stringResource(R.string.save),
                                modifier = Modifier
                                    .padding(end = 26.dp, top = 6.dp)
                                    .clickable {
                                        val newTodoItem = currentItem?.copy(
                                            text = textValue,
                                            relevance = relevance,
                                            deadline = if (deadline != "") deadline else null,
                                            changeDate = LocalDate
                                                .now()
                                                .toString()
                                        ) ?: TodoItem(
                                            id = UUID
                                                .randomUUID()
                                                .toString(),
                                            text = textValue,
                                            relevance = relevance,
                                            deadline = deadline,
                                            flagAchievement = false,
                                            LocalDate
                                                .now()
                                                .toString(),
                                            null
                                        )

                                        redactorViewModel.setItem(newTodoItem)
                                        redactorViewModel.saveItem(newTodoItem)
                                        navController.navigate(DestinationEnum.LIST_SCREEN.destString)
                                    },
                                color = MaterialTheme.colorScheme.tertiary,
                            )

                        },
                    )

                }

            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                OutlinedTextField(
                    value = textValue.orEmpty(),
                    onValueChange = { newValue ->
                        textValue = newValue
                        currentItem?.let { item ->
                            redactorViewModel.updateText(newValue)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(150.dp, 1000.dp)
                        .padding(horizontal = 22.dp)
                        .shadow(2.dp, shape = RoundedCornerShape(12.dp), clip = false)
                        .padding(2.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = MaterialTheme.colorScheme.background,
                        focusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedBorderColor = MaterialTheme.colorScheme.background,
                        disabledBorderColor = MaterialTheme.colorScheme.background,
                        cursorColor = MaterialTheme.colorScheme.outlineVariant,

                        ),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.what_needs_to_be_done),
                            color = MaterialTheme.colorScheme.onTertiary,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                )
                Spacer(modifier = Modifier.padding(vertical = 12.dp))
                val expanded = remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 26.dp)
                ) {
                    PrimaryBodyText(
                        text = stringResource(R.string.importance),
                        modifier = Modifier
                            .clickable {
                                expanded.value = true
                            },

                        )

                    DropdownMenu(
                        modifier = Modifier.padding(start = 10.dp),
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                    ) {
                        (Relevance.entries.toTypedArray()).forEach { curImportance ->
                            DropdownMenuItem(
                                onClick = {
                                    relevance = curImportance
                                    redactorViewModel.updateRelevance(curImportance)
                                    expanded.value = false
                                },

                                text = {
                                    val relevanceText = when (curImportance) {
                                        Relevance.LOW -> "Низкая"
                                        Relevance.BASE -> "Нет"
                                        Relevance.URGENT -> "Высокая"
                                    }
                                    Text(text = relevanceText)
                                }
                            )
                        }
                    }
                }
                Text(
                    text =
                    if (relevance == Relevance.URGENT) "Высокая"
                    else if (relevance == Relevance.LOW) "Низкая"
                    else "Нет",
                    modifier = Modifier
                        .padding(horizontal = 26.dp, vertical = 2.dp)
                        .clickable {
                            expanded.value = true
                        },
                    style = MaterialTheme.typography.bodySmall,
                    color = if (relevance == Relevance.LOW) MaterialTheme.colorScheme.tertiary
                    else if (relevance == Relevance.URGENT)
                    {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onTertiary
                    }
                )
                Divider(modifier = Modifier.padding(vertical = 20.dp, horizontal = 26.dp))
                val deadlineIsSelected =
                    remember { mutableStateOf(currentItem?.deadline != null) }
                val isChecked = remember { mutableStateOf(currentItem?.deadline != null) }
                val formattedDate = remember(deadline) {
                    derivedStateOf {
                        if (deadline.isNotEmpty()) {
                            try {
                                val localDate = LocalDate.parse(deadline)
                                DateTimeFormatter.ofPattern("MMM dd yyyy").format(localDate)
                            } catch (e: DateTimeParseException) {
                                "Invalid date"
                            }
                        } else {
                            "No deadline"
                        }
                    }
                }
                val dateDialogState = rememberMaterialDialogState()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 26.dp)
                    ) {
                        PrimaryBodyText(
                            text = "Сделать до",
                            modifier = Modifier.clickable {
                                dateDialogState.show()
                            }
                        )
                        if (deadlineIsSelected.value && isChecked.value) {
                            Text(
                                text = formattedDate.value,
                                color = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.clickable {
                                    dateDialogState.show()
                                }
                            )
                        }
                    }
                    Switch(
                        modifier = Modifier.padding(end = 40.dp),
                        checked = isChecked.value,
                        onCheckedChange = {
                            isChecked.value = !isChecked.value
                            if (isChecked.value) {
                                dateDialogState.show()
                            }
                            else {
                                deadline = ""
                                redactorViewModel.updateDeadline(null) // Обновление дедлайна в ViewModel
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.tertiary,
                            uncheckedThumbColor = MaterialTheme.colorScheme.outlineVariant
                        )
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Divider(modifier = Modifier.padding(vertical = 10.dp, horizontal = 26.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 20.dp, start = 26.dp, bottom = 70.dp)
                        .clickable {
                            if (currentItem != null) {
                                redactorViewModel.deleteItem()
                            }
                            navController.navigateUp()
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_delete_24),
                        contentDescription = stringResource(R.string.delete_task),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(24.dp),
                        colorFilter = ColorFilter.tint(if (textValue.isEmpty()) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.error)
                    )
                    Text(
                        text = stringResource(R.string.delete_task),
                        color = if (textValue.isEmpty()) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                MaterialDialog(
                    dialogState = dateDialogState,
                    buttons = {
                        positiveButton(
                            text = stringResource(R.string.ready),
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary)
                        ) {
                            deadlineIsSelected.value = true
                        }
                        negativeButton(
                            text = stringResource(R.string.cancel),
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary)
                        ) {
                            if (!deadlineIsSelected.value) {
                                isChecked.value = false
                            }
                        }
                    }
                ) {
                    datepicker(
                        initialDate = LocalDate.now(),
                        title = DateTimeFormatter
                            .ofPattern("  yyyy")
                            .format(LocalDate.now()),
                        colors = DatePickerDefaults.colors(
                            headerBackgroundColor = MaterialTheme.colorScheme.tertiary,
                            dateActiveBackgroundColor = MaterialTheme.colorScheme.tertiary,

                            )
                    ) {
                        deadline = it.toString()
                    }
                }
            }
        }
    }
}