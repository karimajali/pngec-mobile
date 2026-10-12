package com.pngelectoral.ui.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**Model class created for table in database*/
@Entity(tableName = "mkageolist")
public class MKAGeoListData : Serializable {

    /*constructor(actionBy: Int?, districtId: Int?, districtName: String?, llGs: String?) {
        this.actionBy = actionBy
        this.districtId = districtId
        this.districtName = districtName
        this.llGs = llGs
    }*/

    @ColumnInfo(name = "actionBy")
    var actionBy: Int? = 0

    @PrimaryKey
    @ColumnInfo(name = "districtId")
    var districtId: Int? = 0

    @ColumnInfo(name = "districtName")
    var districtName: String? = ""

    @ColumnInfo(name = "llGs")
    var llGs: String? = ""
}