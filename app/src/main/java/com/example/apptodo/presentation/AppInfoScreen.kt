package com.example.apptodo.presentation

import android.content.Context
/*import androidx.appcompat.view.ContextThemeWrapper*/
import android.view.ContextThemeWrapper
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.apptodo.R
import com.example.apptodo.presentation.divkit.NavigationDivActionHandler
import com.yandex.div.core.Div2Context
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils
import com.yandex.div.DivDataTag
import com.yandex.div.core.DivConfiguration
import com.yandex.div.core.view2.Div2View
import com.yandex.div.data.DivParsingEnvironment
import com.yandex.div.json.ParsingErrorLogger
import com.yandex.div.picasso.PicassoDivImageLoader
import com.yandex.div2.DivData
import org.json.JSONObject

@Composable
fun AppInfoScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val contextWrapper = remember { ContextThemeWrapper(context, R.style.Theme_ToDoApp) }
    val configuration = remember { createDivConfiguration(contextWrapper, navController) }
    val divData = remember { getData(context) }
    AndroidView(
        factory = { context ->
            Div2View(
                Div2Context(
                    contextWrapper,
                    configuration,
                    R.style.Theme_ToDoApp,
                    context as LifecycleOwner
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { div2View ->
        div2View.setData(divData, DivDataTag("tag"))
    }
}


private fun createDivConfiguration(context: Context, navController: NavController): DivConfiguration {
    val imageLoader =  PicassoDivImageLoader(context)
    val configuration = DivConfiguration.Builder(imageLoader)
        .actionHandler(NavigationDivActionHandler(navController))
        .build()
    return configuration
}

private fun getData(context: Context): DivData? {
    return try {
        val data = IOUtils.toString(context.assets.open("info.json"))
        val jsonObject = JSONObject(data)
        val cardJson = jsonObject.getJSONObject("card")
        val environment = DivParsingEnvironment(ParsingErrorLogger.LOG)
        DivData(environment, cardJson)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}