package com.bity.icp_kotlin_kit.candid.model

import com.bity.icp_kotlin_kit.data.model.CandidVectorError
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidType
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidValue
import com.bity.icp_kotlin_kit.data.model.candid.model.CandidVector
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CandidVectorTest {

    @Test
    fun `constructor with type and sequence`() {
        val type = CandidType.Empty
        val candidVector = CandidVector(
            containedType = type,
            values = listOf(CandidValue.Empty)
        )
        assertEquals(type, candidVector.containedType)
        assertTrue(candidVector.values.isNotEmpty())
    }

    @Test
    fun `constructor with wrong candid type`() {
        val type = CandidType.Integer8
        assertThrows<CandidVectorError.WrongCandidType> {
            CandidVector(
                containedType = type,
                values = listOf(CandidValue.Text(""))
            )
        }
    }
}