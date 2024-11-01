package app.atori.multi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.atori.multi.utils.ResUtils.vector
import atorimulti.composeapp.generated.resources.Res
import atorimulti.composeapp.generated.resources.ic_atori_logo_24px

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 一个KotlinApi干碎德国佬十年老马
        setContent {
        }
    }
}

@Preview
@Composable
fun CanOnlyPreviewHere() {
    // 另外，咱不知道是咱的问题还是它的问题，导致默认主题的IconButton颜色和水波颜色都不对
    IconButton(onClick = { /* doSomething() */ }) {
        Icon(Res.drawable.ic_atori_logo_24px.vector, contentDescription = "Localized description")
    }
}