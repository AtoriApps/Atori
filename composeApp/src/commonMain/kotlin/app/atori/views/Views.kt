package app.atori.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.atori.standardIconButtonColors
import app.atori.utils.ResUtils.vector
import app.atori.resources.Res
import app.atori.resources.ic_atori_logo_24px
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun TopBarAtoriIcon(){
    Icon(
        Res.drawable.ic_atori_logo_24px.vector,
        "Atori",
        Modifier
            .size(24.dp),
        MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun TopBarControlButton(icon: DrawableResource, contentDescription: String, onClick: () -> Unit = {}) {
    // IconButton的主题色必须套在Surface里才能生效
    Button(
        onClick, Modifier.size(48.dp),
        contentPadding = PaddingValues(0.dp),
        colors = standardIconButtonColors,
    ) {
        Icon(
            icon.vector,
            contentDescription,
        )
    }
}