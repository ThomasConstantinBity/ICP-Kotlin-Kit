package com.bity.icp_kotlin_kit.util.jackson.read_state

import com.bity.icp_kotlin_kit.data.datasource.api.enum.HashTreeNodeType
import com.bity.icp_kotlin_kit.data.datasource.api.model.HashTreeNode
import com.bity.icp_kotlin_kit.data.datasource.api.response.ReadStateResponse
import com.bity.icp_kotlin_kit.data.datasource.api.response.StateCertificateResponse
import com.bity.icp_kotlin_kit.data.model.ParsingError
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.ResponseBody
import retrofit2.Converter

// TODO: Next version - parse delegation
class ReadStateResponseBodyConverter(
    private val objectMapper: ObjectMapper
) : Converter<ResponseBody, Any> {

    override fun convert(body: ResponseBody): StateCertificateResponse {
        body.use {
            val readStateResponse = objectMapper.readValue(it.bytes(), ReadStateResponse::class.java)
            val certificate = objectMapper.readValue(readStateResponse.certificate, HashMap::class.java)

            val signature = certificate["signature"] as? ByteArray
                ?: throw ParsingError.InvalidCertificateStructure("Missing signature")
            val root = certificate["tree"]
                ?: throw ParsingError.InvalidCertificateStructure("Missing tree")

            val stateCertificateResponse =  StateCertificateResponse(
                signature = signature,
                tree = buildTree(root)
            )
            // TODO, remove comment
            // stateCertificateResponse.verifySignature()
            return stateCertificateResponse
        }
    }

    private fun buildTree(cbor: Any): HashTreeNode {
        val array = cbor as? ArrayList<*>
            ?: throw ParsingError.InvalidCertificateStructure("Unable to get array")
        val hashTreeType = array.first() as? Int
            ?: throw ParsingError.InvalidCertificateStructure("Unable to get hashTreeType")
        val nodeType = HashTreeNodeType.fromValue(hashTreeType)
            ?: throw ParsingError.InvalidCertificateStructure("Invalid hashTreeType")
        return when(nodeType) {
            HashTreeNodeType.Empty -> HashTreeNode.Empty
            HashTreeNodeType.Fork -> {
                require(array.size == 3) {
                    throw ParsingError.InvalidCertificateStructure("Invalid Fork node")
                }
                HashTreeNode.Fork(
                    left = buildTree(array[1]),
                    right = buildTree(array[2])
                )
            }
            HashTreeNodeType.Labeled -> {
                val labelData = (array[1] as? ByteArray)
                require(array.size == 3 && labelData != null) {
                    throw ParsingError.InvalidCertificateStructure("Invalid Labeled node")
                }
                HashTreeNode.Labeled(
                    labelData,
                    buildTree(array[2])
                )
            }
            HashTreeNodeType.Leaf -> {
                val stateData = array[1] as? ByteArray
                require(array.size == 2 && stateData != null) {
                    throw ParsingError.InvalidCertificateStructure("Invalid Leaf node")
                }
                HashTreeNode.Leaf(stateData)
            }
            HashTreeNodeType.Pruned -> {
                val hash = array[1] as? ByteArray
                require(array.size == 2 && hash != null) {
                    throw ParsingError.InvalidCertificateStructure("Invalid Pruned node")
                }
                HashTreeNode.Pruned(hash)
            }
        }
    }
}