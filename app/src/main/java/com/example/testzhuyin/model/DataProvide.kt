package com.example.testzhuyin.data

import com.example.testzhuyin.R
import com.example.testzhuyin.model.GroupContent
import com.example.testzhuyin.model.NoticeContent
import com.example.testzhuyin.model.UserContent

object DataProvide {
    val note =
        Note(
            id = 1,
            title = "【藏族】1111旅行笔记",
            time = "2023-1-28"
        )

    val notelist = listOf(
        note,
        Note(
            id = 2,
            title = "【汉族】2222旅行笔记",
            time = "2023-1-31"
        ),
        Note(
            id = 3,
            title = "【苗族】3333旅行笔记",
            time = "2023-2-2"
        ),
        Note(
            id = 4,
            title = "【白族】4444旅行笔记",
            time = "2023-1-8"
        ),
        Note(
            id = 5,
            title = "【壮族】5555旅行笔记",
            time = "2023-1-18"
        ),
        Note(
            id = 6,
            title = "【藏族】xxxx旅行笔记",
            time = "2022-12-28"
        ),
        Note(
            id = 7,
            title = "【藏族】xxxx旅行笔记",
            time = "2023-12-30"
        ),
        Note(
            id = 8,
            title = "【藏族】xxxx旅行笔记",
            time = "2022-12-23"
        ),
        Note(
            id = 9,
            title = "【藏族】xxxx旅行笔记",
            time = "2023-2-1"
        ),
        Note(
            id = 10,
            title = "【藏族】xxxx旅行笔记",
            time = "2023-1-8"
        ),
    )
    val comcontent =
        UserContent(
            id = 1,
            drawable=R.drawable.home_content_21,
            user = R.drawable.home_content_21,
            text="1",
            name = "user1",
            time = "2022.11.11",
            Slocation = "湖南",
            Nationality = "侗族"
        )
    val comcontentlist = listOf(
        comcontent,
        UserContent(
            id = 2,
            drawable=R.drawable.home_content_22,
            user = R.drawable.home_content_22,
            text="2",
            name = "user2",
            time = "2022.2.2",
            Slocation = "广西",
            Nationality = "壮族"
        ),
        UserContent(
            id = 3,
            drawable=R.drawable.home_content_23,
            user = R.drawable.home_content_23,
            text="3",
            name = "user3",
            time = "2022.3.3",
            Slocation = "xx",
            Nationality = "xx"
        ),
        UserContent(
            id = 4,
            drawable=R.drawable.home_content_24,
            user = R.drawable.home_content_24,
            text="4",
            name = "user4",
            time = "2022.4.4",
            Slocation = "xx",
            Nationality = "xx"
        ),
        UserContent(
            id = 5,
            drawable=R.drawable.home_content_25,
            user = R.drawable.home_content_25,
            text="5",
            name = "user5",
            time = "2022.5.5",
            Slocation = "xx",
            Nationality = "xx"
        ),
        UserContent(
            id = 6,
            drawable=R.drawable.home_content_26,
            user = R.drawable.home_content_26,
            text="6",
            name = "user6",
            time = "2022.6.6",
            Slocation = "xx",
            Nationality = "xx"
        ),
    )

    val Notice= NoticeContent(
        id = "嘻嘻哈哈嘿嘿",
        time = "15:46",
        text = "小姐姐衣服链接有吗",
        drawable = R.drawable.notice1,
    )
    val noticelist= listOf(
        Notice,
        NoticeContent(
            id = "小李有点甜",
            time = "13:20",
            text = "这是哪里呀好好看",
            drawable = R.drawable.notice8,
        ),
        NoticeContent(
            id = "未眠",
            time = "11:50",
            text = "周末有一个回族聚餐",
            drawable = R.drawable.notice2,
        ),
        NoticeContent(
            id = "半城烟雨",
            time = "9:34",
            text = "交个朋友吗，我也是侗族的",
            drawable = R.drawable.notice3,
        ),
        NoticeContent(
            id = "什么时候去大理",
            time = "7:20",
            text = "好的！",
            drawable = R.drawable.notice4,
        ),

        NoticeContent(
            id = "水色心情",
            time = "7:20",
            text = "那就这么说",
            drawable = R.drawable.notice5,
        ),
        NoticeContent(
            id = "去有风的地方",
            time = "7:20",
            text = "稍等",
            drawable = R.drawable.notice6,
        ),
        NoticeContent(
            id = "快乐再出发",
            time = "7:20",
            text = "那也太棒了吧",
            drawable = R.drawable.notice7,
        )
    )

    val Group=GroupContent(
        id = "维吾尔族在安徽",
        text ="安徽六安有无小伙伴",
        drawable =R.drawable.pic_group_4,
        num = 2345
    )
    val grouplist= listOf(
        Group,
        GroupContent(
            id = "回族",
            text ="找在广州的穆斯林",
            drawable =R.drawable.pic_group_5,
            num = 2843
        ),
        GroupContent(
            id = "广州藏族",
            text ="找一个一起逛街的藏族伙伴",
            drawable =R.drawable.pic_group_1,
            num = 3820
        ),
        GroupContent(
            id = "哈萨克族在江苏",
            text ="南京哪里有哈萨克族的正宗美食鸭",
            drawable =R.drawable.pic_group_2,
            num = 3492
        ),
        GroupContent(
            id = "苗族在大理",
            text ="去有风的地方真好看啊",
            drawable =R.drawable.pic_group_3,
            num = 768
        ),

        )

    val ContentData = listOf(
        R.drawable.home_content_11 to R.string.home_content_11,
        R.drawable.home_content_12 to R.string.home_content_12,
        R.drawable.home_content_13 to R.string.home_content_13,
        R.drawable.home_content_14 to R.string.home_content_14,
        R.drawable.home_content_15 to R.string.home_content_15,
        R.drawable.home_content_16 to R.string.home_content_16
    ).map { HomeContent(it.first, it.second) }
}

//    val CommunityContentData = listOf(
//        R.drawable.home_content_21 to R.string.home_content_11 to R.drawable.home_content_11 to R.string.home_content_11,
//        R.drawable.home_content_22 to R.string.home_content_12,
//        R.drawable.home_content_23 to R.string.home_content_13,
//        R.drawable.home_content_24 to R.string.home_content_14,
//        R.drawable.home_content_25 to R.string.home_content_15,
//        R.drawable.home_content_26 to R.string.home_content_16
//    ).map { UserContent(it.first) }
//}




