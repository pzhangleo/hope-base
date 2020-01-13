package hope.base.kserialization

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

class BooleanJsonAdapter : KSerializer<Boolean> {
    override val descriptor: SerialDescriptor
        get() = StringDescriptor.withName("BooleanJsonAdapter")

    override fun deserialize(decoder: Decoder): Boolean {
        return decoder.decodeInt() > 0
    }

    override fun serialize(encoder: Encoder, obj: Boolean) {
        encoder.encodeString(
                if (obj) {
                    "1"
                } else {
                    "0"
                }
        )
    }

}