package com.example.apptodo.presentation

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.apptodo.R
import com.example.apptodo.data.entity.Relevance
import com.example.apptodo.data.entity.TodoItem
import com.example.apptodo.presentation.custom_components.BasicAlertDialogCompose
import com.example.apptodo.presentation.custom_components.Divider
import com.example.apptodo.presentation.custom_components.PrimaryBodyText
import com.example.apptodo.presentation.custom_components.Shadow
import com.example.apptodo.presentation.navigation.DestinationEnum
import com.example.apptodo.presentation.ui.theme.ToDoAppTheme
import com.example.apptodo.presentation.ui_state.UIState
import com.example.apptodo.presentation.viewmodels.RedactorViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.UUID


/**
 * Composable function representing the screen for editing or adding a new todo item.
 *
 * @param navController NavController for navigating between screens.
 * @param redactorViewModel ViewModel for managing item editing and saving.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedactorScreen(
    navController: NavController,
    redactorViewModel: RedactorViewModel
) {

    val currentItem by redactorViewModel.item.collectAsState()

    val uiState by redactorViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var textValue by remember { mutableStateOf(currentItem?.text.orEmpty()) }
    var relevance by remember { mutableStateOf(currentItem?.relevance ?: Relevance.BASIC) }
    var deadline by remember { mutableStateOf(currentItem?.deadline.orEmpty()) }

    val showLoadingDialog = remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    BasicAlertDialogCompose(isLoading = showLoadingDialog.value)

    LaunchedEffect(uiState) {
        when (uiState) {
            is UIState.Loading -> showLoadingDialog.value = true
            is UIState.Success -> {
                showLoadingDialog.value = false
            }
            is UIState.Error -> {
                showLoadingDialog.value = false
                snackbarHostState.showSnackbar("Кажется, возникла ошибка. Пожалуйста, попробуйте повторить запрос позже")
            }
            else -> {}
        }
    }

    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState()
    ToDoAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ){
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                topBar = {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = if (scrollState.value > 0) 6.dp else 0.dp,
                                shape = RoundedCornerShape(0.dp)
                            )
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        TopAppBar(
                            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
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
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                            actions = {
                                var isClicked by remember { mutableStateOf(false) }
                                Text(
                                    text = stringResource(R.string.save),
                                    modifier = Modifier
                                        .padding(end = 26.dp, top = 6.dp)
                                        .clickable {
                                            isClicked = true
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
                                                creationDate = LocalDate
                                                    .now()
                                                    .toString(),
                                                changeDate = LocalDate
                                                    .now()
                                                    .toString()
                                            )

                                            redactorViewModel.setItem(newTodoItem)
                                            redactorViewModel.saveItem(newTodoItem)
                                            navController.navigate(DestinationEnum.LIST_SCREEN.destString)
                                        },
                                    color = if (!isClicked) MaterialTheme.colorScheme.tertiary else Color.Green,
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
                        .background(MaterialTheme.colorScheme.surface)
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 26.dp)
                        ) {
                            PrimaryBodyText(
                                text = stringResource(R.string.importance),
                                modifier = Modifier.clickable {
                                    expanded.value = true
                                },
                            )

                            DropdownMenu(
                                modifier = Modifier.padding(start = 10.dp),
                                expanded = expanded.value,
                                onDismissRequest = { expanded.value = false },
                            ) {
                                Relevance.entries.forEach { curImportance ->
                                    DropdownMenuItem(
                                        onClick = {
                                            relevance = curImportance
                                            redactorViewModel.updateRelevance(curImportance)
                                            expanded.value = false
                                        },
                                        text = {
                                            val relevanceText = when (curImportance) {
                                                Relevance.LOW -> "Низкая"
                                                Relevance.BASIC -> "Нет"
                                                Relevance.IMPORTANT -> "Высокая"
                                            }
                                            Text(text = relevanceText)
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 26.dp)
                                .padding(bottom = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text =
                                if (relevance == Relevance.IMPORTANT) "Высокая"
                                else if (relevance == Relevance.LOW) "Низкая"
                                else "Нет",
                                modifier = Modifier
                                    .clickable {
                                        expanded.value = true
                                    },
                                style = MaterialTheme.typography.bodySmall,
                                color = if (relevance == Relevance.LOW) MaterialTheme.colorScheme.tertiary
                                else if (relevance == Relevance.IMPORTANT) {
                                    MaterialTheme.colorScheme.error
                                } else {
                                    MaterialTheme.colorScheme.onTertiary
                                }
                            )

                            Text(
                                text = "Выбрать",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clickable {
                                        isSheetOpen = true
                                    },
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Divider(modifier = Modifier.padding(vertical = 5.dp, horizontal = 26.dp))

                    }
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
                                    redactorViewModel.updateDeadline(null)
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
                    var buttonState by remember { mutableStateOf(false) }

                    Button(
                        onClick = {
                            buttonState = !buttonState
                            if (currentItem != null) {
                                redactorViewModel.deleteItem()
                            }
                            navController.navigateUp()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (buttonState) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.background,
                            contentColor = if (buttonState) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier
                            .padding(top = 20.dp, start = 26.dp, bottom = 70.dp)
                            .size(width = 210.dp, height = 50.dp)
                            .shadow(elevation = 2.dp, shape = RoundedCornerShape(percent = 50))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.baseline_delete_24),
                                contentDescription = stringResource(R.string.delete_task),
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(24.dp),
                                colorFilter = ColorFilter.tint(if (buttonState) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.error)
                            )
                            Text(
                                text = stringResource(R.string.delete_task),
                                color = if (buttonState) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
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



                    if (isSheetOpen){
                        ModalBottomSheet(
                            sheetState = bottomSheetState,
                            onDismissRequest = { isSheetOpen = false},
                            scrimColor = Color.Black.copy(alpha = 0.32f)
                        ) {
                            BottomSheetContent(
                                onItemSelected = {}
                            )
                        }
                    }
                }


            }
        }
    }
}

@Composable
fun ImportanceItem(relevance: Relevance, onClick: () -> Unit) {
    val text = when (relevance) {
        Relevance.BASIC -> "Нет"
        Relevance.LOW -> "Низкая"
        Relevance.IMPORTANT -> "Высокая"
    }

    val defaultBackgroundColor = MaterialTheme.colorScheme.surface
    val highlightedBackgroundColor = Color.Red

    var isHighlighted by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isHighlighted) highlightedBackgroundColor else defaultBackgroundColor,
        animationSpec = tween(durationMillis = 1000)
    )

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                if (relevance == Relevance.IMPORTANT) {
                    scope.launch {
                        isHighlighted = true
                        onClick()
                        delay(300)
                        isHighlighted = false
                    }
                } else {
                    onClick()
                }
            }
            .background(backgroundColor)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 15.dp),
            style = MaterialTheme.typography.bodyMedium
        )

        Divider(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(horizontal = 25.dp)
        )
    }
}

@Composable
fun BottomSheetContent(onItemSelected: (Relevance) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ImportanceItem(relevance = Relevance.BASIC) { onItemSelected(Relevance.BASIC) }
        ImportanceItem(relevance = Relevance.LOW) { onItemSelected(Relevance.LOW) }
        ImportanceItem(relevance = Relevance.IMPORTANT) { onItemSelected(Relevance.IMPORTANT) }
    }
}