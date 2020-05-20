//package hope.base.kserialization
//
//import kotlinx.serialization.Decoder
//import kotlinx.serialization.Encoder
//import kotlinx.serialization.KSerializer
//import kotlinx.serialization.PrimitiveDescriptor
//import kotlinx.serialization.PrimitiveKind
//import kotlinx.serialization.SerialDescriptor
//
//class BooleanJsonAdapter : KSerializer<Boolean> {
//    override val descriptor: SerialDescriptor
//        get() = PrimitiveDescriptor("BooleanJsonAdapter", PrimitiveKind.STRING)
//
//    override fun deserialize(decoder: Decoder): Boolean {
//        return decoder.decodeInt() > 0
//    }
//
//    override fun serialize(encoder: Encoder, value: Boolean) {
//        encoder.encodeString(
//                if (value) {
//                    "1"
//                } else {
//                    "0"
//                })
//    }
//}
