package com.webaddicted.newhiltproject.data.model.other

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TileModel(
    @SerializedName("tileName")
    var tileName: String? = "",
    @SerializedName("tileIcon") var tileIcon: Int = 0,
    @SerializedName("desc")
    var desc: String? = "",
) : Serializable {

}