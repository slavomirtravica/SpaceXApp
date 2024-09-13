package hr.algebra.spacexapp.api

import com.google.gson.annotations.SerializedName

data class SpacexItem(
    @SerializedName("last_ais_update") val lastAisUpdate : String,
    @SerializedName("legacy_id") val legacyId : String,
    @SerializedName("model") val model : String,
    @SerializedName("type") val type : String,
    @SerializedName("roles") val roles : List<String>,
    @SerializedName("imo") val imo : Int,
    @SerializedName("mmsi") val mmsi : Int,
    @SerializedName("abs") val abs : Int,
    @SerializedName("class") val `class`: Int,
    @SerializedName("mass_kg") val massKg : Int,
    @SerializedName("mass_lbs") val massLbs : Int,
    @SerializedName("year_built") val yearBuilt : Int,
    @SerializedName("home_port") val homePort : String,
    @SerializedName("status") val status : String,
    @SerializedName("speed_kn") val speedKn : String,
    @SerializedName("course_deg") val courseDeg : String,
    @SerializedName("latitude") val latitude : String,
    @SerializedName("longitude") val longitude : String,
    @SerializedName("link") val link : String,
    @SerializedName("image") val image : String,
    @SerializedName("name") val name : String,
    @SerializedName("active") val active : Boolean,
    @SerializedName("launches") val launches : List<String>,
    @SerializedName("id") val id : String
)
