package com.example.student.presentation.plan

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import com.example.core.data.model.CardData
import com.example.core.data.model.CardType
import com.example.student.R
import com.example.student.component.CalendarWeekView
import com.example.student.domain.model.CalendarDay
import com.example.student.util.getFestival
import com.example.student.util.getLunarDate
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PlanScreen(
    tabIndex: StateFlow<Int>,
    onTabSelected: (Int) -> Unit,
    viewModel: PlanViewModel= hiltViewModel()
) {
    val selectedTabIndex by tabIndex.collectAsState()
    val pagerState = rememberPagerState(initialPage = selectedTabIndex)
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != selectedTabIndex) {
            onTabSelected(pagerState.currentPage)
        }
    }

    LaunchedEffect(selectedTabIndex) {
        if (pagerState.currentPage != selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
    }
        Column(modifier = Modifier.fillMaxSize().background(Color(0xfff8f8fc))) {
            HorizontalPager(
                count = 2,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> DailyLearn(viewModel)
                    1 -> LongPlanScreen()
                }
            }
        }
}


@OptIn(ExperimentalPagerApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyLearn(viewModel:PlanViewModel){
    var selectedDate by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }

    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val daysOfWeek = DayOfWeek.values().map { dayOfWeek ->
        // 计算每个星期几对应的日期
        val dayDiff = dayOfWeek.ordinal - currentDate.dayOfWeek.ordinal
        val dateForDayOfWeek = currentDate.plus(dayDiff, DateTimeUnit.DAY)
        // 动态获取农历日期和节日信息
        val lunar = getLunarDate(dateForDayOfWeek)
        // 计算农历日期
        val festival = getFestival(dateForDayOfWeek, lunar)
        // 获取节日信息
        CalendarDay(
            date = dateForDayOfWeek,
            dayOfWeek = dayOfWeek,
            lunar = lunar,
            festival = festival
        )
    }

    val monthInChinese = when (selectedDate.month.value) {
        1 -> "1"
        2 -> "2"
        3 -> "3"
        4 -> "4"
        5 -> "5"
        6 -> "6"
        7 -> "7"
        8 -> "8"
        9 -> "9"
        10 -> "10"
        11 -> "11"
        12 -> "12"
        else -> ""
    }
    val dayOfWeekText = when (selectedDate.dayOfWeek) {
        DayOfWeek.MONDAY -> "星期一"
        DayOfWeek.TUESDAY -> "星期二"
        DayOfWeek.WEDNESDAY -> "星期三"
        DayOfWeek.THURSDAY -> "星期四"
        DayOfWeek.FRIDAY -> "星期五"
        DayOfWeek.SATURDAY -> "星期六"
        DayOfWeek.SUNDAY -> "星期天"
    }
    // 通过ViewModel获取数据库中的数据
    LaunchedEffect(selectedDate) {
        viewModel.getCardDataForDay(dayOfWeekText)
    }
    val cardData by viewModel.cardData.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        androidx.compose.material3.Text(
            text = "2024年"+monthInChinese +"月",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 16.dp).padding(start = 8.dp),
            color = Color.Black
        )
        Row(
            modifier =Modifier.fillMaxWidth().padding(start = 4.dp).background(Color.White),
            verticalAlignment = Alignment.Top
        ) {
            CalendarWeekView(
                days = daysOfWeek,
                selectedDate = selectedDate,
                currentDate = currentDate,
                onDateSelected = { date -> selectedDate = date }
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth().padding(8.dp)
        ) {
            item {
                AddItemRow(viewModel = viewModel, dayOfWeek = dayOfWeekText)
            }
            items(cardData) { data ->
                when (data.type) {
                    CardType.NO_IMAGE -> NoImageCard(data.toCardData())
                    CardType.ONE_IMAGE -> OneImageCard(data.toCardData())
                    CardType.MULTIPLE_IMAGES -> MultipleImagesCard(data.toCardData())
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemRow(viewModel: PlanViewModel, dayOfWeek: String) {
    val context = LocalContext.current
    val text = viewModel.textFieldValue
    val selectedImageUri = viewModel.selectedImageUri

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        uri?.let {
            Log.d("ImageUri", "Selected URI: $it")
            viewModel.onImageSelected(it.toString()) // 确保 URI 被正确转换为字符串
        }

    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(onClick = { launcher.launch("image/*") }) {
            Icon(
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "Add",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        TextField(
            value = text,
            onValueChange = { newText -> viewModel.onTextFieldValueChange(newText) },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            placeholder = { Text("Enter text") },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )

        IconButton(onClick = {
            viewModel.saveCardData(dayOfWeek)
            viewModel.getCardDataForDay(dayOfWeek)
            viewModel.resetFields()
        }
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Confirm",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun NoImageCard(card: CardData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = card.title, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = card.description, fontSize = 16.sp)
        }
    }
}

@Composable
private fun OneImageCard(card: CardData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            card.imageId?.let { imageUri ->
                // 使用ContentResolver确保URI可用
                val painter = rememberAsyncImagePainter(
                    model = imageUri,
                    placeholder = painterResource(R.drawable.pic_quesheng), // 占位图
                    error = painterResource(R.drawable.pic_quesheng) // 错误图
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f)
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = card.title, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = card.description, fontSize = 16.sp)
            }
        }
    }
}



@Composable
private fun MultipleImagesCard(card: CardData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = card.title, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = card.description, fontSize = 16.sp)
            Row(modifier = Modifier.heightIn(min = 100.dp)) {
                // 使用card.images加载对应的多张图片
                card.images?.forEach { imageId ->
                    Image(
                        painter = painterResource(id = imageId.toInt()), // 假设imageId是资源ID的字符串形式
                        contentDescription = null,
                        modifier = Modifier
                            .width(100.dp)
                            .aspectRatio(1f)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}




fun getRealPathFromURI(contentUri: Uri, context: Context): String? {
    if (DocumentsContract.isDocumentUri(context, contentUri)) {
        val docId = DocumentsContract.getDocumentId(contentUri)
        val split = docId.split(":").toTypedArray()
        val type = split[0]

        var uri: Uri? = null
        if ("image" == type) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val selection = "${MediaStore.Images.Media._ID}=?"
        val selectionArgs = arrayOf(split[1])

        return uri?.let {
            context.contentResolver.query(it, null, selection, selectionArgs, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.getString(columnIndex)
                } else {
                    null
                }
            }
        }
    }

    return null
}
