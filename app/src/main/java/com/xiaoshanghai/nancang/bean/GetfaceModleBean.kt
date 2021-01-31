package com.xiaoshanghai.nancang.bean

 data class GetfaceModleBean(
    val face_list: List<Face>,
    val face_num: Int
)

data class Face(
    val age: Double,
    val angle: Angle,
    val beauty: Double,
    val expression: Expression,
    val face_probability: Int,
    val face_shape: FaceShape,
    val face_token: String,
    val gender: Gender,
    val glasses: Glasses,
    val landmark: List<Landmark>,
    val landmark72: List<Landmark72>,
    val location: Location,
    val quality: Quality
)

data class Angle(
    val pitch: Double,
    val roll: Double,
    val yaw: Double
)

data class Expression(
    val probability: Double,
    val type: String
)

data class FaceShape(
    val probability: Double,
    val type: String
)

data class Gender(
    val probability: Double,
    val type: String
)

data class Glasses(
    val probability: Double,
    val type: String
)

data class Landmark(
    val x: Double,
    val y: Double
)

data class Landmark72(
    val x: Double,
    val y: Double
)

data class Location(
    val height: Int,
    val left: Int,
    val rotation: Int,
    val top: Int,
    val width: Int
)

data class Quality(
    val blur: Double,
    val completeness: Int,
    val illumination: Int,
    val occlusion: Occlusion
)

data class Occlusion(
    val chin: Int,
    val left_cheek: Double,
    val left_eye: Int,
    val mouth: Int,
    val nose: Int,
    val right_cheek: Double,
    val right_eye: Int
)