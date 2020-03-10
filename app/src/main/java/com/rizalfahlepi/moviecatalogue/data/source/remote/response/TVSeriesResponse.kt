package com.rizalfahlepi.moviecatalogue.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TVSeriesResponse (
    var id: Int? = null,
    var name: String? = null,
    var firstAirDate: String? = null,
    var popularity: Double? = null,
    var voteCount: Int? = null,
    var posterPath: String? = null,
    var originalLanguage: String? = null,
    var voteAverage: Double? = null,
    var overview: String? = null
): Parcelable