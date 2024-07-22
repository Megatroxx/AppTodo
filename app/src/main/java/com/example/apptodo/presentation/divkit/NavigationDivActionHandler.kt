package com.example.apptodo.presentation.divkit

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class NavigationDivActionHandler(private val navController: NavController) : DivActionHandler() {
    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        val url = action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)


        return if (url.scheme == SCHEME_NAVIGATION && handleSampleAction(url, view.view.context)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }
    }

    private fun handleSampleAction(action: Uri, context: Context): Boolean {
        return when (action.host) {
            "navigate" -> {
                navController.popBackStack()
                true
            }
            else -> false
        }
    }

    companion object {
        const val SCHEME_NAVIGATION = "navigation-action"
    }
}
