package app.atori.ui.views.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.atori.BuildConstants
import app.atori.resources.*
import app.atori.resources.Res
import app.atori.resources.about_summary
import app.atori.resources.app_name
import app.atori.utils.ComposeUtils.only
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
    Column(Modifier.padding(vertical = 8.dp), Arrangement.spacedBy(8.dp)) {
        @Composable
        fun ListItem(
            icon: DrawableResource,
            title: String,
            standAlone: Boolean = false,
            onClick: () -> Unit = {},
            content: @Composable () -> Unit = {}
        ) =
            Row(
                Modifier.height(IntrinsicSize.Min).fillMaxWidth()
                    .only(!standAlone) { background(MaterialTheme.colorScheme.surfaceContainerLow) }
                    .clickable(onClick = onClick).padding(if (standAlone) 32.dp else 24.dp, 12.dp),
                Arrangement.spacedBy(16.dp),
                Alignment.CenterVertically
            ) {
                Icon(
                    icon.vector,
                    title,
                    Modifier.size(24.dp),
                    MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    title,
                    Modifier.weight(1F),
                    MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge
                )
                content()
            }

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            // 上半
            Column(
                Modifier.padding(horizontal = 8.dp).fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)), Arrangement.spacedBy(2.dp)
            ) {
                ListItem(Res.drawable.ic_add_person_24px, Res.string.add_contact.text)
                ListItem(Res.drawable.ic_scan_24px, Res.string.scan.text)
                ListItem(Res.drawable.ic_file_transfer_24px, Res.string.file_transfer.text)
            }

            // 下半
            Column(
                Modifier.padding(horizontal = 8.dp).fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)), Arrangement.spacedBy(2.dp)
            ) {
                // TODO：下拉展示工作区
                ListItem(Res.drawable.ic_workspaces_24px, Res.string.num_workspaces.text("114514"))
                ListItem(Res.drawable.ic_manage_workspaces_24px, Res.string.manage_workspaces.text)
                // TODO: 下拉展示账号
                ListItem(Res.drawable.ic_accounts_24px, Res.string.num_accounts.text("1919810"))
                ListItem(Res.drawable.ic_manage_accounts_24px, Res.string.manage_accounts.text)
            }
        }

        // 无边框
        Column(Modifier.fillMaxWidth()) {
            // TODO：是否还要已存档、骚扰信息 ListItem(Res.drawable.ic_atori_logo_24px, Res.string.archived_spam_blocked_etc.text, true)
            ListItem(Res.drawable.ic_settings_24px, Res.string.settings.text, true)
            ListItem(Res.drawable.ic_help_24px, Res.string.help_feedback.text, true)
            ListItem(Res.drawable.ic_logout_24px, Res.string.close_atori.text, true)
        }
    }

@Composable
fun AboutDialog(isDarkMode: Boolean = isSystemInDarkTheme()) =
    Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.size(48.dp).clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary), Alignment.Center
            ) {
                Icon(
                    Res.drawable.ic_atori_logo_24px.vector,
                    Res.string.app_name.text,
                    Modifier.size(32.dp),
                    MaterialTheme.colorScheme.onPrimary
                )
            }
            Column {
                Text(
                    Res.string.app_name.text,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "${Res.string.about_summary.text}\n${BuildConstants.VERSION_NAME}",
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
