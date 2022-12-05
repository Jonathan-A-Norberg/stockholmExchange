package com.example.network.data

import kotlinx.serialization.Serializable


@Serializable
data class RemotePhotosRoot(
    var photos: RemotePhotos,
)

@Serializable
data class RemotePhotos(
    var page: Int,
    var pages: Int,
    var photo: List<RemotePhoto> = emptyList(),
)

@Serializable
data class RemotePhoto(
    var title: String,
    var id: String,
    var secret: String,
    var server: String,
)

/*
<photos page="2" pages="89" perpage="10" total="881">
	<photo id="2636" owner="47058503995@N01"
		secret="a123456" server="2" title="test_04"
		ispublic="1" isfriend="0" isfamily="0" />
	<photo id="2635" owner="47058503995@N01"
		secret="b123456" server="2" title="test_03"
		ispublic="0" isfriend="1" isfamily="1" />
	<photo id="2633" owner="47058503995@N01"
		secret="c123456" server="2" title="test_01"
		ispublic="1" isfriend="0" isfamily="0" />
	<photo id="2610" owner="12037949754@N01"
		secret="d123456" server="2" title="00_tall"
		ispublic="1" isfriend="0" isfamily="0" />
</photos>*/