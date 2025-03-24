package com.example.student.presentation.virtue.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.core.data.model.Book
import com.example.core.other.util.internet.fetchBook
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VirtuePeopleViewModel : ViewModel() {

    private val _bookList = MutableStateFlow<List<Book>>(emptyList())
    val bookList: StateFlow<List<Book>> get() = _bookList

    private val _searchIsbn = MutableStateFlow("")
    val searchIsbn: StateFlow<String> get() = _searchIsbn

    init {
        // 默认显示三本书籍
        _bookList.value = listOf(
            Book(
                title = "上升的一切必将汇合",
                img = "http://static.tanshuapi.com/isbn/202438/17269990637934e7.jpg",
                author = "[美] 弗兰纳里·奥康纳",
                isbn = "9787559676573",
                isbn10 = "null",
                publisher = "北京联合出版公司",
                pubdate = "null",
                keyword = "null",
                pages = "null",
                price = "null",
                binding = "null",
                summary = "☆ 美国国家图书奖60年唯一最佳小说奖得主    ☆ 斯蒂芬·金等125位作家评选，20世纪十大文学作品之一、美国十大文学作品之一    ☆ 万人想读高分译本修订再版，九读·好译本名著系列，全本无删减    ————————    读完此书，更能理解人性的幽深和命运的无常！    ————————    “对于耳背的人，你得大声喊叫他才能听见；对于接近失明的人，你得把人物画得大而惊人他才能看清。”    ·    在奥康纳生命的最后十年，她以笔为炬，写就这部短篇小说集《上升的一切必将汇合》，照亮美国南方那些怪诞却真实的角落。种族、信仰、道德、人性……奥康纳用她独有的黑色幽默和强烈讽刺，以美国南方这片充满矛盾的土地为舞台，创造出一个个古怪、偏执、自私或虚伪的人物，导演了一幕幕荒诞离奇的戏码。最终，所有的故事在强烈的情感冲击中戛然而止，落下帷幕。    ————————    这版译本对我来说大概是非常容易接受和..."
            ),
            Book(
                title = "冷到下雪",
                img = "https://img1.doubanio.com/view/subject/l/public/s34902988.jpg",
                author = "[澳] 欧健梅",
                isbn = "9780987654321",
                isbn10 = "7115545139",
                publisher = "上海译文出版社",
                pubdate = "2021-02-01",
                keyword = "关键字2",
                pages = "250",
                price = "40.00",
                binding = "精装",
                summary = "一个十月的雨天，一对母女分别离开自己生活的国家，到东京见面：她们漫步在河道旁， 躲避台风，分享咖啡馆和餐厅的美食，参观画廊，欣赏城市中最激进的现代艺术。与此同时，她们聊天气、星座、服装和物品，乃至家庭、距离和记忆。"
            ),
            Book(
                title = "夜空穿透伤",
                img = "https://img3.doubanio.com/view/subject/l/public/s34917337.jpg",
                author = "[美] 王鸥行",
                isbn = "9787559677044",
                isbn10 = "7115545140",
                publisher = "北京联合出版公司",
                pubdate = "2021-03-01",
                keyword = "关键字3",
                pages = "220",
                price = "30.00",
                binding = "平装",
                summary = "本书是越南裔美国诗人王鸥行的首部诗集，以其独特的文化背景和深邃的个人经历，构建了一个充满情感张力和历史深度的文学世界。诗集中的每首诗都是一次心灵的探索，从越南战争的阴影到移民美国的挑战，从家族历史的传承到个人身份的追寻，王鸥行以细腻的笔触和敏锐的观察，描绘了一幅跨越时空与文化的记忆图景。"
            )
        )
    }

    // 更新 ISBN 输入值
    fun onIsbnChange(newIsbn: String) {
        _searchIsbn.value = newIsbn
    }

    // 调用 fetchBook() 获取书籍数据
    fun searchBooksByIsbn() {
        viewModelScope.launch {
            try {
                val book = fetchBook(_searchIsbn.value)
                // 添加新的书籍到现有列表
                _bookList.value = _bookList.value + book
            } catch (e: Exception) {
                // 处理异常
                _bookList.value = emptyList()
            }
        }
    }
}

