package com.pngelectoral.ui.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**Model class created for table in database*/
@Entity(tableName = "clanlist")
public class ClanListData : Serializable {

    @PrimaryKey
    @ColumnInfo(name = "clanId")
    var clanId: Int = 0
	
	@ColumnInfo(name = "wardId")
    var wardId: Int? = 0

    @ColumnInfo(name = "clanName")
    var clanName: String = ""

    @ColumnInfo(name = "description")
    var description: String? = ""
    
}