package ru.easycode.zerotoheroandroidtdd

interface Repository {

    interface Read {
        fun list(): List<Item>
    }

    interface Add {
        fun add(value: String): Long
    }

    interface Delete {
        fun item(id: Long): Item
        fun delete(id: Long)
    }

    interface All : Read, Add, Delete

    class Base(
        private val dataSource: ItemsDao,
        private val now: Now
    ) : All {

        override fun list(): List<Item> {
            return dataSource.list().map { Item(id = it.id, text = it.text) }
        }

        override fun add(value: String): Long {
            val id = now.nowMillis()
            dataSource.add(ItemCache(id = id, text = value))
            return id
        }

        override fun item(id: Long): Item {
            val cache = dataSource.item(id)
            return Item(id = cache.id, text = cache.text)
        }

        override fun delete(id: Long) {
            dataSource.delete(id)
        }
    }
}
