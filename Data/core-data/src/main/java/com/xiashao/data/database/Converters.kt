package com.xiashao.data.database

import androidx.room.TypeConverter
import com.xiashao.model.DataSourceType
import com.xiashao.model.asDataSourceType
import kotlinx.datetime.Instant

class InstantConverter {
    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.toEpochMilliseconds()
}

class DatasourceTypeConverter {
    @TypeConverter
    fun newsResourceTypeToString(value: DataSourceType?): String? =
        value?.let(DataSourceType::name)

    @TypeConverter
    fun stringToNewsResourceType(name: String?): DataSourceType =
        name.asDataSourceType()
}
