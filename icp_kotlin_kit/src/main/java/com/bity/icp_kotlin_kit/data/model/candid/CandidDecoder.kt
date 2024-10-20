package com.bity.icp_kotlin_kit.data.model.candid

import com.bity.icp_kotlin_kit.data.model.candid.model.CandidKey
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidRecord
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidType
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidVariant
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidVector
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import java.lang.RuntimeException
import java.math.BigInteger
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.valueParameters

object CandidDecoder {

    inline fun <reified T : Any>decodeNotNull(candidValue: CandidValue): T =
        decode<T>(candidValue) ?: throw RuntimeException("Value cannot be null")

    inline fun <reified T : Any>decode(candidValue: CandidValue?): T? {
        candidValue ?: return null
        val clazz = T::class
        val res = when(candidValue) {
            is CandidValue.Blob -> candidValue.data
            is CandidValue.Bool -> candidValue.bool
            CandidValue.Empty -> TODO()
            is CandidValue.Float32 -> candidValue.float
            is CandidValue.Float64 -> candidValue.double
            is CandidValue.Function -> TODO()
            is CandidValue.Integer -> candidValue.bigInt
            is CandidValue.Integer16 -> candidValue.int16
            is CandidValue.Integer32 -> candidValue.int32
            is CandidValue.Integer64 -> candidValue.int64
            is CandidValue.Integer8 -> candidValue.int8
            is CandidValue.Natural -> candidValue.bigUInt
            is CandidValue.Natural16 -> candidValue.uInt16
            is CandidValue.Natural32 -> candidValue.uInt32
            is CandidValue.Natural64 -> candidValue.uInt64
            is CandidValue.Natural8 -> candidValue.uInt8
            CandidValue.Null -> TODO()
            is CandidValue.Option -> getOptionValue(
                candidValue = candidValue.option.value,
                constructor = T::class.constructors.firstOrNull()
            )
            is CandidValue.Record -> {
                val kClass: KClass<T> = T::class
                buildObject(
                    candidRecord = candidValue.record,
                    constructor = kClass.primaryConstructor!!
                )
            }

            CandidValue.Reserved -> TODO()
            is CandidValue.Text -> candidValue.string
            is CandidValue.Variant -> {
                buildSealedClass(
                    candidVariant = candidValue.variant,
                    subclasses = T::class.nestedClasses.toList()
                )
            }

            is CandidValue.Vector -> {
                require(clazz.java.isArray)
                val componentType = clazz.java.componentType.kotlin
                buildArray(
                    candidVector = candidValue.vector,
                    componentType = componentType
                )
            }

            is CandidValue.Principal -> TODO()
            is CandidValue.Service -> TODO()
        }
        return res as T
    }

    fun getOptionValue(
        candidValue: CandidValue?,
        constructor: KFunction<*>?
    ): Any? {
        candidValue ?: return null
        return when(candidValue) {
            is CandidValue.Blob -> candidValue.data
            is CandidValue.Bool -> candidValue.bool
            CandidValue.Empty -> TODO()
            is CandidValue.Float32 -> candidValue.float
            is CandidValue.Float64 -> candidValue.double
            is CandidValue.Function -> TODO()
            is CandidValue.Integer -> candidValue.bigInt
            is CandidValue.Integer16 -> candidValue.int16
            is CandidValue.Integer32 -> candidValue.int32
            is CandidValue.Integer64 -> candidValue.int64
            is CandidValue.Integer8 -> candidValue.int8
            is CandidValue.Natural -> candidValue.bigUInt
            is CandidValue.Natural16 -> candidValue.uInt16
            is CandidValue.Natural32 -> candidValue.uInt32
            is CandidValue.Natural64 -> candidValue.uInt64
            is CandidValue.Natural8 -> candidValue.uInt8
            CandidValue.Null -> null
            is CandidValue.Option -> candidValue.option.value?.let { value -> getOptionValue(value, constructor) }
            is CandidValue.Principal -> TODO()
            is CandidValue.Record -> TODO()
            CandidValue.Reserved -> TODO()
            is CandidValue.Service -> TODO()
            is CandidValue.Text -> candidValue.string
            is CandidValue.Variant -> TODO()
            is CandidValue.Vector -> TODO()
        }
    }

    fun <T> buildObject(
        candidRecord: CandidRecord,
        constructor: KFunction<T>,
    ): T {
        val params = constructor.valueParameters.mapIndexed { index, param ->
            val res = getValueForParam(param, candidRecord, index)
            param to res
        }.toMap()
        return constructor.callBy(params)
    }

    private fun getValueForParam(
        param: KParameter,
        candidRecord: CandidRecord,
        index: Int,
    ): Any? {
        val key = param.name
        requireNotNull(key)
        val value = candidRecord[key]
            ?: candidRecord[index.toULong()]
            ?: throw IllegalArgumentException("Missing value for parameter: ${param.name}")
        return decode(value, param.type)
    }

    fun decode(candidValue: CandidValue, type: KType): Any? {
         return when(candidValue) {
            is CandidValue.Blob -> candidValue.data
            is CandidValue.Bool -> candidValue.bool
            CandidValue.Empty -> TODO()
            is CandidValue.Float32 -> candidValue.float
            is CandidValue.Float64 -> candidValue.double
            is CandidValue.Function -> {
                val name = candidValue.function.method?.name
                val principalId = candidValue.function.method?.principal?.bytes?.let { ICPPrincipal(it) }
                val constructor = (type.classifier as KClass<*>).primaryConstructor
                requireNotNull(constructor)
                return constructor.call(name!!, principalId)
            }
            is CandidValue.Integer -> candidValue.bigInt
            is CandidValue.Integer16 -> candidValue.int16
            is CandidValue.Integer32 -> candidValue.int32
            is CandidValue.Integer64 -> candidValue.int64
            is CandidValue.Integer8 -> candidValue.int8
            is CandidValue.Natural -> candidValue.bigUInt
            is CandidValue.Natural16 -> candidValue.uInt16
            is CandidValue.Natural32 -> candidValue.uInt32
            is CandidValue.Natural64 -> candidValue.uInt64
            is CandidValue.Natural8 -> candidValue.uInt8
            CandidValue.Null -> TODO()
            is CandidValue.Option -> candidValue.option.value?.let {
                when(val optionType = it.candidType) {
                    is CandidType.Vector -> decodeCandidOptionIntoArray(
                        candidValue = candidValue,
                        vector = optionType,
                        clazz = type.classifier as? KClass<*>
                    )
                    else -> decode(it, type)
                }
            }

            is CandidValue.Principal ->
                candidValue.candidPrincipal?.bytes?.let {
                    ICPPrincipal(it)
                }
            is CandidValue.Record -> {
                buildObject(
                    candidRecord = candidValue.record,
                    constructor = (type.classifier as KClass<*>).primaryConstructor!!
                )
            }
            CandidValue.Reserved -> TODO()
            is CandidValue.Service -> TODO()
            is CandidValue.Text -> candidValue.string
            is CandidValue.Variant -> {
                // TODO, should have a name
                val kClass = type.classifier as? KClass<*>
                requireNotNull(kClass)
                require(kClass.isSealed)
                buildSealedClass(
                    candidVariant = candidValue.variant,
                    subclasses = kClass.sealedSubclasses
                )
            }
            is CandidValue.Vector -> {
                val componentType = type.arguments.firstOrNull()?.type?.classifier as? KClass<*>
                requireNotNull(componentType)
                buildArray(
                    candidVector = candidValue.vector,
                    componentType = componentType
                )
            }
        }
    }

    private fun decodeCandidOptionIntoArray(
        candidValue: CandidValue.Option,
        vector: CandidType.Vector,
        clazz: KClass<*>?
    ): Any =
        when(vector.candidType) {
            CandidType.Natural8 -> {
                when(clazz) {
                    ByteArray::class -> (candidValue.option.value as CandidValue.Blob).data
                    else -> (candidValue.option.value as CandidValue.Blob).data.map { it.toUByte() }.toTypedArray()
                }
            }
            else -> TODO()
        }

    fun buildSealedClass(
        candidVariant: CandidVariant,
        subclasses: List<KClass<out Any>>
    ): Any {
        val targetClass = subclasses.find { clazz ->
            val className = clazz.simpleName ?: return false
            candidVariant.key.longValue.toULong() == CandidKey.candidHash(className)
        }
        requireNotNull(targetClass)
        return when(val value = candidVariant.value) {

            CandidValue.Empty -> TODO()
            is CandidValue.Function -> TODO()

            is CandidValue.Text,
            is CandidValue.Blob,
            is CandidValue.Bool,
            is CandidValue.Float32,
            is CandidValue.Float64,
            is CandidValue.Integer,
            is CandidValue.Integer16,
            is CandidValue.Integer32,
            is CandidValue.Integer64,
            is CandidValue.Integer8,
            is CandidValue.Natural,
            is CandidValue.Natural16,
            is CandidValue.Natural32,
            is CandidValue.Natural64,
            is CandidValue.Natural8 -> {
                val constructor = targetClass.primaryConstructor
                requireNotNull(constructor)
                constructor.call(getPrimitiveValue(value))
            }
            CandidValue.Null -> {
                val objectInstance = targetClass.objectInstance
                requireNotNull(objectInstance)
                objectInstance
            }
            is CandidValue.Option -> TODO()
            is CandidValue.Principal -> TODO()
            is CandidValue.Record -> {
                val constructor = targetClass.primaryConstructor
                requireNotNull(constructor)
                buildDataClass(
                    candidRecord = value.record,
                    constructor = constructor
                )
            }
            CandidValue.Reserved -> TODO()
            is CandidValue.Service -> TODO()
            is CandidValue.Variant -> {
                val constructor = targetClass.primaryConstructor
                requireNotNull(constructor)
                val paramClass = constructor.parameters.first().type.classifier as? KClass<*>
                requireNotNull(paramClass)
                require(paramClass.isSealed)
                constructor.call(
                    buildSealedClass(
                        candidVariant = value.variant,
                        subclasses = paramClass.sealedSubclasses
                    )
                )
            }
            is CandidValue.Vector -> TODO()
        }
    }

    private fun buildDataClass(
        candidRecord: CandidRecord,
        constructor: KFunction<*>
    ): Any {
        val params = constructor.parameters.associateWith { param ->
            decode(candidRecord, param)
        }
        val clazz = constructor.callBy(params)
        requireNotNull(clazz)
        return clazz
    }

    private fun getPrimitiveValueForKey(candidRecord: CandidRecord, key: String): Any? {
        val value = candidRecord[key]
        requireNotNull(value)
        return getPrimitiveValue(value)
    }

    private fun getPrimitiveValue(candidValue: CandidValue): Any? =
        when(candidValue) {
            is CandidValue.Blob -> candidValue.data
            is CandidValue.Bool -> candidValue.bool
            CandidValue.Empty -> TODO()
            is CandidValue.Float32 -> candidValue.float
            is CandidValue.Float64 -> candidValue.double
            is CandidValue.Integer -> candidValue.bigInt
            is CandidValue.Integer16 -> candidValue.int16
            is CandidValue.Integer32 -> candidValue.int32
            is CandidValue.Integer64 -> candidValue.int64
            is CandidValue.Integer8 -> candidValue.int8
            is CandidValue.Natural -> candidValue.bigUInt
            is CandidValue.Natural16 -> candidValue.uInt16
            is CandidValue.Natural32 -> candidValue.uInt32
            is CandidValue.Natural64 -> candidValue.uInt64
            is CandidValue.Natural8 -> candidValue.uInt8
            CandidValue.Null -> null
            is CandidValue.Option -> {
                candidValue.option.value?.let {
                    getPrimitiveValueForKey(
                        CandidRecord.init(mapOf("key" to it)),
                        "key"
                    )
                }
            }
            is CandidValue.Principal -> TODO()
            CandidValue.Reserved -> TODO()
            is CandidValue.Text -> candidValue.string

            else -> throw RuntimeException("Can't get primitive type from ${candidValue::class.java.simpleName}")
        }

    private fun decode(candidRecord: CandidRecord, param: KParameter): Any? {

        val key = param.name
        requireNotNull(key)

        return when(val classifier = param.type.classifier as KClass<*>) {

            ByteArray::class,
            Byte::class,
            Short::class,
            Int::class,
            Long::class,
            UByte::class,
            UShort::class,
            UInt::class,
            ULong::class,
            Float::class,
            Double::class,
            String::class,
            Boolean::class,
            BigInteger::class,
            Char::class -> getPrimitiveValueForKey(candidRecord, key)

            // TODO, could class be Array<ByteArray>::class

            else -> {
                return when(val candidValue = candidRecord[key]) {
                    is CandidValue.Option -> {
                        if(candidValue.option.value == null) null else TODO()
                    }
                    is CandidValue.Record -> decode(
                        candidValue = candidValue,
                        type = param.type
                    )
                    null -> createClass(candidRecord, classifier)
                    else -> TODO("Need to implement for ${candidValue::class}")
                }
            }
        }
    }

    private fun createClass(
        candidRecord: CandidRecord,
        classifier: KClass<*>
    ): Any {
        val constructor = classifier.primaryConstructor
        requireNotNull(constructor)
        val params = constructor.parameters.associateWith {
            val key = it.name
            requireNotNull(key)
            val candidValue = candidRecord[key]
            requireNotNull(candidValue)
            decode(
                candidValue = candidValue,
                type = it.type
            )
        }
        return constructor.callBy(params)
    }

    fun buildArray (
        candidVector: CandidVector,
        componentType: KClass<*>
    ): Any? {
        return if (candidVector.values.isEmpty())
            java.lang.reflect.Array.newInstance(componentType.java, 0)
        else {
            val newArray = java.lang.reflect.Array.newInstance(
                componentType.java,
                candidVector.values.size
            ) as Array<Any?>
            val arrayValues = candidVector.values.map { value ->
                when(value) {
                    is CandidValue.Blob -> value.data
                    is CandidValue.Bool -> value.bool
                    CandidValue.Empty -> TODO()
                    is CandidValue.Float32 -> value.float
                    is CandidValue.Float64 -> value.double
                    is CandidValue.Function -> TODO()
                    is CandidValue.Integer -> value.bigInt
                    is CandidValue.Integer16 -> value.int16
                    is CandidValue.Integer32 -> value.int32
                    is CandidValue.Integer64 -> value.int64
                    is CandidValue.Integer8 -> value.int8
                    is CandidValue.Natural -> value.bigUInt
                    is CandidValue.Natural16 -> value.uInt16
                    is CandidValue.Natural32 -> value.uInt32
                    is CandidValue.Natural64 -> value.uInt64
                    is CandidValue.Natural8 -> value
                    CandidValue.Null -> null
                    is CandidValue.Option -> TODO()
                    is CandidValue.Record -> {
                        buildObject(
                            candidRecord = value.record,
                            constructor = componentType.constructors.first()
                        )
                    }
                    CandidValue.Reserved -> TODO()
                    is CandidValue.Text -> value.string
                    is CandidValue.Variant -> TODO()
                    is CandidValue.Vector -> TODO()
                    is CandidValue.Principal -> TODO()
                    is CandidValue.Service -> TODO()
                }
            }.toTypedArray()
            arrayValues.copyInto(newArray, 0, 0, candidVector.values.size)
        }
    }
}