package app.atori.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import app.atori.misc.DemoData
import app.atori.models.DemoMessageBodyType
import app.atori.models.DemoMessageBodyType.*
import app.atori.models.DemoMessageModel
import app.atori.models.DemoMessageReactionModel
import app.atori.models.DemoMessageType
import app.atori.resources.Res
import app.atori.resources.add_reaction
import app.atori.resources.call
import app.atori.resources.ic_add_reaction_20px
import app.atori.resources.illu_read_to_here
import app.atori.resources.illu_reply_arrow
import app.atori.resources.img_avatar_demo
import app.atori.resources.read_to_here
import app.atori.resources.reply_arrow
import app.atori.ui.components.AtoriIconButton
import app.atori.ui.components.AtoriIconButtonStyles
import app.atori.ui.components.IconChip
import app.atori.ui.components.IconStringChip
import app.atori.utils.ComposeUtils
import app.atori.utils.ComposeUtils.maybeHover
import app.atori.utils.ResUtils.imgBmp
import app.atori.utils.ResUtils.text
import app.atori.utils.ResUtils.vector
import app.atori.utils.TimestampUtils.timeStr
import app.atori.utils.TimestampUtils.timestamp
import org.jetbrains.compose.resources.DrawableResource
import kotlin.collections.forEach
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class)
@Composable
fun DemoChatView() {
    @Composable
    fun TheLine(modifier: Modifier) {
        val lineColor = MaterialTheme.colorScheme.outlineVariant

        Spacer(modifier.drawWithCache {
            val start = Offset(0f, size.height / 2)
            val end = Offset(size.width, size.height / 2)
            val lineWidth = 1.dp.toPx()
            onDrawBehind { drawLine(lineColor, start, end, lineWidth) }
        })
    }

    @Composable
    fun ReplyBlock(replyMsg: String) = Row(
        Modifier.fillMaxWidth().clickable(onClick = {})
            .padding(16.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Res.drawable.illu_reply_arrow.vector,
            Res.string.reply_arrow.text,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "@${DemoData.userName}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                replyMsg,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    @Composable
    fun TimeLineBlock(timestamp: Long) = Row(
        Modifier.fillMaxWidth().clickable(onClick = {}).padding(16.dp, 12.dp),
        Arrangement.spacedBy(8.dp),
        Alignment.CenterVertically
    ) {
        val lineModifier = Modifier.weight(1F)

        TheLine(lineModifier)
        Text(
            timestamp.timeStr("yyyy/MM/dd HH:mm"),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        TheLine(lineModifier)
    }

    @Composable
    fun ReadToHereBlock() = Row(
        Modifier.fillMaxWidth().clickable(onClick = {}).padding(16.dp, 12.dp),
        Arrangement.spacedBy(8.dp),
        Alignment.CenterVertically
    ) {
        Icon(
            Res.drawable.illu_read_to_here.vector,
            Res.string.read_to_here.text,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            Res.string.read_to_here.text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        TheLine(Modifier.weight(1F))
    }

    @Composable
    fun MessageBody(
        messageData: Any,
        messageType: DemoMessageBodyType,
        reactions: List<DemoMessageReactionModel>
    ) {
        when (messageType) {
            TEXT -> Text(
                messageData as String,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            IMAGE -> Image(
                (messageData as DrawableResource).imgBmp,
                DemoData.userName,
                Modifier.size(400.dp, 280.dp).clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            NONE -> IllegalStateException("Message type is NONE.")
        }
        if (reactions.isNotEmpty()) FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Reactions
            reactions.forEach { reaction ->
                IconStringChip(reaction.useClicked) {
                    Text(reaction.emoji)
                    Text(reaction.count.toString())
                }
            }

            // Add reaction
            IconChip(Res.drawable.ic_add_reaction_20px.vector, Res.string.add_reaction.text)
        }
    }

    @Composable
    fun FirstMessageBlock(
        timestamp: Long, messageData: Any, messageType: DemoMessageBodyType,
        reactions: List<DemoMessageReactionModel>
    ) = Row(
        Modifier.fillMaxWidth().clickable(onClick = {}).padding(16.dp, 4.dp),
        Arrangement.spacedBy(8.dp),
        Alignment.Top
    ) {
        Box(Modifier.width(64.dp), Alignment.CenterStart) {
            Image(
                DemoData.userAvatar, DemoData.userName,
                Modifier.size(40.dp).clip(CircleShape)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    DemoData.userName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    timestamp.timeStr("yyyy/MM/dd HH:mm"),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            MessageBody(messageData, messageType, reactions)
        }
    }

    @Composable
    fun HeadlessBlock(
        timestamp: Long, messageData: Any, messageType: DemoMessageBodyType,
        reactions: List<DemoMessageReactionModel>
    ) = Row(
        Modifier.fillMaxWidth().clickable(onClick = {}).padding(16.dp, 4.dp),
        Arrangement.spacedBy(8.dp),
        Alignment.Top
    ) {
        var isHovered by remember { mutableStateOf(false) }

        Box(
            Modifier.size(64.dp, 24.dp).maybeHover(object : ComposeUtils.HoverHandler() {
                override fun onEnter(): Boolean {
                    isHovered = true
                    return false
                }

                override fun onExit(): Boolean {
                    isHovered = false
                    return false
                }
            })
            /*.pointerMoveFilter( // TODO: 这是个待移除的Api
                onEnter = {
                    isHovered = true
                    false
                },
                onExit = {
                    isHovered = false
                    false
                }
            )*/,
            Alignment.CenterStart
        ) {
            if (isHovered) Text(
                timestamp.timeStr("HH:mm"),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelLarge
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            MessageBody(messageData, messageType, reactions)
        }
    }

    val demoMessages = listOf(
        DemoMessageModel(
            "度尽劫波兄弟在\n相逢一笑泯恩仇",
            "2024-11-11 11:45:14".timestamp,
            DemoMessageType.FIRST_MESSAGE, DemoMessageBodyType.TEXT
        ),
        DemoMessageModel(
            Res.drawable.img_avatar_demo,
            "2024-11-11 11:45:14".timestamp,
            DemoMessageType.HEADLESS, DemoMessageBodyType.IMAGE,
            listOf(
                DemoMessageReactionModel("😄", 2, true),
                DemoMessageReactionModel("😅"),
            )
        ),
        DemoMessageModel(
            null,
            "1919-11-11 11:45:14".timestamp,
            DemoMessageType.TIME_LINE
        ),
        DemoMessageModel(
            "所以我说度尽劫波兄弟在，相逢一笑泯恩仇",
            "2024-11-11 11:45:14".timestamp,
            DemoMessageType.REPLY
        ),
        DemoMessageModel(
            "度尽劫波兄弟在",
            "1919-08-10 11:45:14".timestamp,
            DemoMessageType.FIRST_MESSAGE, DemoMessageBodyType.TEXT
        ),
        DemoMessageModel(
            null,
            "2024-08-10 11:45:14".timestamp,
            DemoMessageType.READ_TO_HERE
        ),
    )

    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)) {
        items(demoMessages) { msg ->
            when (msg.messageType) {
                DemoMessageType.FIRST_MESSAGE -> FirstMessageBlock(
                    msg.timestamp, msg.messageBodyData!!, msg.messageBodyType, msg.reactions
                )

                DemoMessageType.HEADLESS -> HeadlessBlock(
                    msg.timestamp, msg.messageBodyData!!, msg.messageBodyType, msg.reactions
                )

                DemoMessageType.TIME_LINE -> TimeLineBlock(msg.timestamp)
                DemoMessageType.REPLY -> ReplyBlock(msg.messageBodyData as String)
                DemoMessageType.READ_TO_HERE -> ReadToHereBlock()
            }
        }
    }
}


@Composable
internal fun TitledActionIconButtonForCallPage(
    icon: DrawableResource,
    title: String,
    isOn: Boolean = false,
    onClick: () -> Unit
) = Column(Modifier, Arrangement.spacedBy(12.dp), Alignment.CenterHorizontally) {
    AtoriIconButton(
        icon, title, 48,
        if (isOn) AtoriIconButtonStyles.Filled else AtoriIconButtonStyles.SpecialNotActivated,
        onClick = onClick
    )
    Text(
        title,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
internal fun DraggableImageInBoxForVideoCallPage(
    boxDadModifier: Modifier,
    imgSize: DpSize // 多2Dp
) {
    var boxWidthPx = 0
    var boxHeightPx = 0

    val r18 = RoundedCornerShape(18.dp)

    // 保存偏移量的状态
    val imageOffsetX = remember { mutableStateOf(0f) }
    val imageOffsetY = remember { mutableStateOf(0f) }

    // Image 的修饰符
    val imageModifier = Modifier
        .offset {
            // 获取最新的偏移量
            IntOffset(imageOffsetX.value.roundToInt(), imageOffsetY.value.roundToInt())
        }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                // FIXME: It maybe a bug of Compose Desktop, open an issue for it

                // 计算新的偏移量
                val newOffsetX = imageOffsetX.value + dragAmount.x
                val newOffsetY = imageOffsetY.value + dragAmount.y

                // 计算边界吸附效果
                val maxOffsetX = boxWidthPx - imgSize.width.toPx()
                val maxOffsetY = boxHeightPx - imgSize.height.toPx()

                // 吸附的阈值
                val snapThreshold = 20f

                // 左右边界吸附
                imageOffsetX.value = when {
                    newOffsetX < snapThreshold -> 0f
                    newOffsetX > maxOffsetX - snapThreshold -> maxOffsetX
                    else -> newOffsetX
                }

                // 上下边界吸附
                imageOffsetY.value = when {
                    newOffsetY < snapThreshold -> 0f
                    newOffsetY > maxOffsetY - snapThreshold -> maxOffsetY
                    else -> newOffsetY
                }

                change.consume()
            }
        }
        .size(imgSize)
        .clip(r18)
        .border(2.dp, MaterialTheme.colorScheme.outlineVariant, r18)

    Box(boxDadModifier.onSizeChanged {
        boxWidthPx = it.width
        boxHeightPx = it.height
    }) {// Image 组件
        Image(
            Res.drawable.img_avatar_demo.imgBmp,
            Res.string.call.text,
            imageModifier,
            contentScale = ContentScale.Crop
        )
    }
}