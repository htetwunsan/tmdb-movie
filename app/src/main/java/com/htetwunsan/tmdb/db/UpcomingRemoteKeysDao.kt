package com.htetwunsan.tmdb.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UpcomingRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<UpcomingRemoteKeys>)

    @Query("SELECT * FROM upcoming_remote_keys WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: Long): UpcomingRemoteKeys?

    @Query("DELETE FROM upcoming_remote_keys")
    suspend fun clearRemoteKeys()
}