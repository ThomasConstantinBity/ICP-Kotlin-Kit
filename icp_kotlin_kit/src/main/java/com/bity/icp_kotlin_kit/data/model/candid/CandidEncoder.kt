package com.bity.icp_kotlin_kit.data.model.candid

import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidOption
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidPrincipal
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidRecord
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidType
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidVector
import java.math.BigInteger
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.jvmErasure

internal object CandidEncoder {

    private fun encodeSealedClass(valueToEncode: ValueToEncode): CandidValue {
        TODO()
    }

    operator fun invoke(valueToEncode: ValueToEncode): CandidValue {

        if(valueToEncode.arg == null) {
            return CandidValue.Option(
                option = CandidOption.None(
                    type = candidPrimitiveTypeForClass(valueToEncode.expectedClass)
                )
            )
        }

        if(valueToEncode.expectedClass.isSealed)
            return encodeSealedClass(valueToEncode)

        val candidValue = when(val arg = valueToEncode.arg) {

            // Unsigned value
            is UByte -> CandidValue.Natural8(arg)
            is UShort -> CandidValue.Natural16(arg)
            is UInt -> CandidValue.Natural32(arg)
            is ULong -> CandidValue.Natural64(arg)

            // Signed value
            is Byte -> CandidValue.Integer8(arg)
            is Short -> CandidValue.Integer16(arg)
            is Int -> CandidValue.Integer32(arg)
            is Long -> CandidValue.Integer64(arg)

            is Float -> CandidValue.Float32(arg)
            is Double -> CandidValue.Float64(arg)

            is BigInteger -> CandidValue.Natural(arg)
            is Boolean -> CandidValue.Bool(arg)
            is String -> CandidValue.Text(arg)
            is ByteArray -> CandidValue.Blob(arg)

            is Array<*> -> {
                if(arg.isNotEmpty()) {
                    val firstArg = arg.first()
                    requireNotNull(firstArg)
                    CandidValue.Vector(
                        CandidVector(
                            values = arg.map { CandidEncoder(
                                 ValueToEncode(
                                    arg = it!!,
                                    arrayType = valueToEncode.arrayType,
                                     arrayTypeNullable = valueToEncode.arrayTypeNullable
                                )
                            ) },
                            containedType = candidPrimitiveTypeForClass(firstArg::class)
                        )
                    )
                } else {
                    val arrayType = valueToEncode.arrayType
                    requireNotNull(arrayType)
                    CandidValue.Vector(
                        CandidVector(
                            values = emptyList(),
                            containedType = candidPrimitiveTypeForClass(arrayType)
                        )
                    )
                }
            }

            is ICPPrincipalApiModel -> CandidValue.Principal(
                candidPrincipal = CandidPrincipal(
                    string = arg.string,
                    bytes = arg.bytes
                )
            )

            else -> {
                val dictionary = arg::class.memberProperties.associate {
                    // Required if obfuscation is enabled
                    it.isAccessible = true
                    it.name to CandidEncoder(
                        ValueToEncode(
                            arg = it.getter.call(arg),
                            expectedClass = it.returnType.jvmErasure,
                            expectedClassNullable = it.returnType.isMarkedNullable
                        )
                    )
                }.toMap()
                CandidValue.Record(CandidRecord.init(dictionary))
            }
        }
        return if(valueToEncode.expectedClassNullable)
            CandidValue.Option(CandidOption.Some(candidValue))
        else
            candidValue
    }

    // TODO return CandidValue.Option
    private fun candidPrimitiveTypeForClass(clazz: KClass<*>): CandidType {
        return when(clazz) {

            Byte::class -> CandidType.Integer8
            BigInteger::class -> CandidType.Natural

            UByte::class -> CandidType.Natural8
            ULong::class -> CandidType.Natural64

            String::class -> CandidType.Text
            ByteArray::class -> CandidType.Vector(CandidType.Integer8)

            /**
            // Unsigned Value
            UByte::class.java-> CandidType.Primitive(CandidPrimitiveType.NATURAL8)
            UShort::class.java -> CandidType.Primitive(CandidPrimitiveType.NATURAL16)
            UInt::class.java -> CandidType.Primitive(CandidPrimitiveType.NATURAL32)
            ULong::class.java -> CandidType.Primitive(CandidPrimitiveType.NATURAL64)

            // Signed Value
            Byte::class.java-> CandidType.Primitive(CandidPrimitiveType.INTEGER8)
            Short::class.java -> CandidType.Primitive(CandidPrimitiveType.INTEGER16)
            Int::class.java -> CandidType.Primitive(CandidPrimitiveType.INTEGER32)
            Long::class.java -> CandidType.Primitive(CandidPrimitiveType.INTEGER64)

            Float::class.java -> CandidType.Primitive(CandidPrimitiveType.FLOAT32)
            Double::class.java -> CandidType.Primitive(CandidPrimitiveType.FLOAT64)

            Boolean::class.java -> CandidType.Primitive(CandidPrimitiveType.BOOL)

            **/

            else -> TODO("not implemented for $clazz")
        }
    }
}