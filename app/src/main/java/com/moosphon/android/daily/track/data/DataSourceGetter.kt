package com.moosphon.android.daily.track.data

import com.moosphon.android.daily.track.bean.SimplePostItem

/**
 * Data source for mocking.
 */
object DataSourceGetter {

    fun getGoodsListMockData(): Array<SimplePostItem> {
        return arrayOf(
            SimplePostItem(
                "毛衣",
                "#ffee67",
                "12671571",
                "clothes"
            ),
            SimplePostItem(
                "论自由民主专政",
                "#96e467",
                "12632572",
                "books"
            ),
            SimplePostItem(
                "拖鞋",
                "#e5ffe5",
                "126736730",
                "clothes"
            ),
            SimplePostItem(
                "MI 10",
                "#78ee67",
                "12671511",
                "device"
            ),
            SimplePostItem(
                "钢铁是怎样炼成的",
                "#6f45e8",
                "12124513",
                "books"
            )
        )
    }
}