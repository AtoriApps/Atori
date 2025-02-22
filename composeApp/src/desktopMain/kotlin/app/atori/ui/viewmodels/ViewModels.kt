package app.atori.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SidePanelViewModel(): ViewModel() {
    val sidePanelExpanded = mutableStateOf(true)
}