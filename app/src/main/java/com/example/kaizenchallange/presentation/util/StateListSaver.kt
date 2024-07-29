package com.example.kaizenchallange.presentation.util

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList

fun <T: Any> snapshotStateListSaver() = listSaver<SnapshotStateList<T>, Any>(
    save = { stateList -> stateList.toList() },
    restore = { value ->
        @Suppress("UNCHECKED_CAST")
        (value as? List<T>)?.toMutableStateList() ?: mutableStateListOf()
    }
)