package com.htetwunsan.tmdb.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming_remote_keys")
data class UpcomingRemoteKeys(
    @PrimaryKey val repoId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)