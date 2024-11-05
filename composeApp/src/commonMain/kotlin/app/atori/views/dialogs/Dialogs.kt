package app.atori.views.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.atori.BuildConstants
import app.atori.resources.*
import app.atori.resources.Res
import app.atori.resources.about_summary
import app.atori.resources.app_name
import app.atori.resources.ic_atori_icon
import app.atori.utils.ResUtils.text
import app.atori.utils.ResUtils.vector
import org.jetbrains.compose.resources.DrawableResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogBase(onDismissRequest: () -> Unit = {}, content: @Composable () -> Unit) =
    BasicAlertDialog(onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            content()
        }
    }

@Composable
fun OneAtoriDialog(showAboutAction: () -> Unit = {}) =
    Column(Modifier.padding(vertical = 8.dp), Arrangement.spacedBy(2.dp)) {
        @Composable
        fun ListItem(
            icon: DrawableResource,
            title: String,
            exPaddin: Boolean = false,
            onClick: () -> Unit = {},
            content: @Composable () -> Unit = {}
        ) =
            Row(
                Modifier.height(IntrinsicSize.Min).fillMaxWidth().clickable(onClick = onClick)
                    .padding(if (exPaddin) 32.dp else 24.dp, 12.dp),
                Arrangement.spacedBy(16.dp)
            ) {
                Icon(icon.vector, title, Modifier.size(24.dp), MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    title,
                    Modifier.weight(1F),
                    MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge
                )
                content()
            }

        // 上半
        Column(
            Modifier.padding(horizontal = 8.dp).fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
        ) {
            ListItem(Res.drawable.ic_atori_logo_24px, Res.string.add_contact.text)
            ListItem(Res.drawable.ic_atori_logo_24px, Res.string.scan.text)
            ListItem(Res.drawable.ic_atori_logo_24px, Res.string.file_transfer.text)
        }

        // 中间
        Column(
            Modifier.padding(horizontal = 8.dp).fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainerLow)
        ) {
            // TODO: 下拉展示账号
            ListItem(Res.drawable.ic_atori_logo_24px, Res.string.num_accounts.text("0"))
        }

        // 下半
        Column(
            Modifier.padding(horizontal = 8.dp).fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
        ) {
            ListItem(Res.drawable.ic_atori_logo_24px, Res.string.archived_spam_blocked_etc.text)
        }

        // 无边框
        Column(Modifier.fillMaxWidth()) {
            ListItem(Res.drawable.ic_atori_logo_24px, Res.string.settings.text, true)
            ListItem(Res.drawable.ic_atori_logo_24px, Res.string.help_feedback.text, true)
            ListItem(Res.drawable.ic_atori_logo_24px, Res.string.close_atori.text, true, showAboutAction)
        }
    }

@Composable
fun AboutDialog(isDarkMode: Boolean = isSystemInDarkTheme()) =
    Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                (if (isDarkMode) Res.drawable.ic_atori_icon_dark else Res.drawable.ic_atori_icon).vector,
                "App Icon",
                Modifier.size(48.dp).clip(MaterialTheme.shapes.medium)
            )
            Column {
                Text(
                    Res.string.app_name.text,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    Res.string.about_summary.text(BuildConstants.VERSION_NAME),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Text(
            Res.string.about_extra.text,
            Modifier.padding(start = 64.dp),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
