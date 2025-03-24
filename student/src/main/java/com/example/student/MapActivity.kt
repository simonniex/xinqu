package com.example.student
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClient.*
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps2d.*
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.Marker
import com.amap.api.maps2d.model.MarkerOptions
import com.amap.api.maps2d.model.MyLocationStyle
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.poisearch.*
import com.example.core.other.util.compressAndCacheAvatar
import com.example.core.state.userState
import kotlinx.coroutines.delay

var poiItems: List<PoiItem>? = null
var poiDetails by mutableStateOf<List<PoiItem>>(emptyList())

var imageIcon :String?=null
var username :String = ""

class MapActivity : ComponentActivity(), PoiSearch.OnPoiSearchListener, LocationSource, AMap.OnMyLocationChangeListener {
    // 定义定位权限请求的代码
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    private  val REQUEST_PERMISSION = 1001

    private lateinit var poiSearch: PoiSearch
    private var poiResult: PoiResult? = null
    private var isLoading by mutableStateOf(false)

    // 地图视图和 AMap 相关引用
    private lateinit var mapView: MapView
    private lateinit var aMap: AMap
    private var locationMarker: Marker? = null
    private var locationClient: AMapLocationClient? = null
    private lateinit var locationOption: AMapLocationClientOption

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置高德地图的 API Key
        setApiKey("56efdaaf545fd26aa8aea454b71c85be")
        updatePrivacyAgree(this ,true)
        updatePrivacyShow(this  ,true,true)
        // 检查定位权限，如果未授予则请求权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            // 如果权限已授予，初始化地图和定位功能
            initializeMapAndLocation()
        }

        // Jetpack Compose 的UI内容
        setContent {
            // 显示搜索 POI 的界面
            // 使用 Coroutine 延迟关闭 MapActivity
            SplashScreen(){
                performNearbyPoiSearch()
                finish()
            }

        }
//        lifecycleScope.launch {
//            delay(3000) // 延迟三秒
//            finish() // 关闭 MapActivity
//        }
    }

    // 初始化地图和定位功能
    private fun initializeMapAndLocation() {
        mapView = MapView(this)
        mapView.onCreate(null) // 必须在生命周期内调用
        aMap = mapView.map

        setupLocationStyle()
        aMap.setLocationSource(this)
        aMap.isMyLocationEnabled = true
        aMap.setOnMyLocationChangeListener(this)
    }
    @Composable
    fun SplashScreen(onTimeout: () -> Unit) {
        var timeLeft by remember { mutableStateOf(3) }
        val coroutineScope = rememberCoroutineScope()

        // 启动一个协程进行倒计时
        LaunchedEffect(Unit) {
            println(userState.getUsernameId()+"username")
            println(userState.getAvatarUri()+"image")
            imageIcon = userState.getAvatarUri()
            username = userState.getUsernameId().toString()
            while (timeLeft > 0) {
                delay(1000) // 每秒更新
                timeLeft--
            }
            onTimeout() // 时间到后执行
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // 图标
                Image(
                    painter = rememberImagePainter(R.drawable.teacher_ic_launcher), // 替换为你的图标
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                // 应用名称
                Text(
                    text = "心驱",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }

            // 右下角的倒计时
            Text(
                text = "倒计时: $timeLeft",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
    // 搜索 POI 的界面
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PoiSearchScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                Button(
                    onClick = {
                        isLoading = true
                        performNearbyPoiSearch()// 执行 POI 搜索
                    },
                    modifier = Modifier.fillMaxWidth().zIndex(2f)
                ) {
                    Text("搜索附近的 POI")
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isLoading) {
                    CircularProgressIndicator()
                }
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(poiDetails) { poiItem ->
                        PoiItemView(poiItem) { latLng ->
                            // 在点击 POI 时，更新地图标记的位置并聚焦
                            updateMapWithPoiLocation(latLng)
                        } // 显示每个 POI 的详情
                    }
                }
                // 地图视图的容器
                AndroidView(factory = { mapView }) {
                    it.onResume() // 必须确保地图生命周期同步
                }
            }
        }
    }

    // 定义POI条目的UI
    @Composable
    fun PoiItemView(poiItem: PoiItem,onClick: (LatLng) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    val latLng = LatLng(poiItem.latLonPoint.latitude, poiItem.latLonPoint.longitude)
                    onClick(latLng) // 点击时传递 POI 的坐标
                }
        ) {
            val imageUrl = poiItem.photos?.getOrNull(0)?.url // 获取第一个照片
            if (imageUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "POI 图片",
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(poiItem.title, style = MaterialTheme.typography.bodyMedium)
                Text(poiItem.snippet,  style = MaterialTheme.typography.bodyMedium)
                Text(poiItem.tel,  style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

    // 设置蓝点样式
    private fun setupLocationStyle() {
        val myLocationStyle = MyLocationStyle()
            .myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) // 设置蓝点跟随模式
            .interval(2000) // 每2秒更新一次位置
            .strokeColor(R.color.purple_200) // 蓝点精度圈边框颜色
            .radiusFillColor(R.color.purple_500) // 蓝点精度圈填充颜色
            .strokeWidth(2f) // 精度圈边框宽度

        aMap.setMyLocationStyle(myLocationStyle)
        aMap.uiSettings.isMyLocationButtonEnabled = true // 显示定位按钮
    }

    // 执行附近 POI 搜索
    private fun performNearbyPoiSearch() {
        val location = locationMarker?.position
        if (location != null) {
            val latLonPoint = LatLonPoint(location.latitude, location.longitude)
            val query = PoiSearch.Query("自习室", "", "")
            query.extensions = "all"
            query.pageSize = 10
            query.pageNum = 0

            poiSearch = PoiSearch(this, query)
            poiSearch.bound = PoiSearch.SearchBound(latLonPoint, 10000)
            poiSearch.setOnPoiSearchListener(this)
            poiSearch.searchPOIAsyn()
        } else {
            isLoading = false
            poiDetails = emptyList()
        }
    }

    // POI 搜索结果回调
    override fun onPoiSearched(result: PoiResult?, rCode: Int) {
        isLoading = false
        if (rCode == 1000 && result != null) {
            poiResult = result
            poiItems = result.pois

            // 更新POI列表
            poiDetails = poiItems ?: emptyList()

            // 清除旧的地图标记
            aMap.clear()

            // 为每个 POI 添加标记
            poiItems?.forEach { poi ->
                val latLng = LatLng(poi.latLonPoint.latitude, poi.latLonPoint.longitude)
                aMap.addMarker(MarkerOptions().position(latLng).title(poi.title))
            }
        } else {
            poiDetails = emptyList()
        }
    }
    // 定位激活时的回调
    override fun activate(listener: LocationSource.OnLocationChangedListener?) {
        locationClient = AMapLocationClient(applicationContext)
        locationOption = AMapLocationClientOption().apply {
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            interval = 2000
        }

        locationClient?.setLocationListener { location ->
            listener?.onLocationChanged(location)
            val latLng = LatLng(location.latitude, location.longitude)
            if (locationMarker == null) {
                locationMarker = aMap.addMarker(MarkerOptions().position(latLng).title("当前位置"))
            } else {
                locationMarker?.position = latLng
            }
        }

        locationClient?.setLocationOption(locationOption)
        locationClient?.startLocation()
    }
    // 定位停用时的回调
    override fun deactivate() {
        locationClient?.stopLocation()
        locationClient?.onDestroy()
        locationClient = null
    }

    // 我的位置变化时的回调（可选实现）
    override fun onMyLocationChange(location: Location?) {}
    // 单个 POI 条目搜索回调（此处暂未使用）
    override fun onPoiItemSearched(poiItem: PoiItem?, rCode: Int) {
    }
    // 更新地图标记位置并聚焦
    private fun updateMapWithPoiLocation(latLng: LatLng) {
        aMap.clear() // 清除旧的标记
        aMap.addMarker(MarkerOptions().position(latLng).title("当前位置")) // 添加新的标记
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f)) // 聚焦到该位置，缩放级别为15
    }
}
