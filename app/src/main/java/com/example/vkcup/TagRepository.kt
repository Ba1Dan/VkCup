package com.example.vkcup

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object TagRepository {

    private var tags = listOf<TagItem>(
        TagItem(0, "Юмор", false),
        TagItem(1, "Еда", false),
        TagItem(2, "Кино", false),
        TagItem(3, "Рестораны", false),
        TagItem(4, "Прогулки", false),
        TagItem(5, "Политика", false),
        TagItem(6, "Новости", false),
        TagItem(7, "Автомобили", false),
        TagItem(8, "Сериалы", false),
        TagItem(9, "Рецепты", false),
        TagItem(10, "Работа", false),
        TagItem(11, "Спорт", false),
        TagItem(12, "Отдых", false),
    )

    private val flowTags: MutableStateFlow<List<TagItem>> = MutableStateFlow(tags.sortedBy { it.name })

    fun getTags(): Flow<List<TagItem>> = flowTags

    fun selectTag(tagItem: TagItem) {
        CoroutineScope(Dispatchers.IO).launch {
            val newTags =
                tags.map { if (it.id == tagItem.id) it.copy(isSelected = !it.isSelected) else it }
            tags = newTags
            flowTags.emit(newTags.sortedWith(
                compareBy(
                    { !it.isSelected },
                    { it.name }
                )
            ))
        }
    }
}