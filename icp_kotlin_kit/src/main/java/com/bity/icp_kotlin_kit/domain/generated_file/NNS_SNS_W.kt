package com.bity.icp_kotlin_kit.domain.generated_file

import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import com.bity.icp_kotlin_kit.data.model.ValueToEncode
import com.bity.icp_kotlin_kit.data.model.candid.CandidDecoder
import com.bity.icp_kotlin_kit.data.repository.ICPQuery
import com.bity.icp_kotlin_kit.domain.model.ICPPrincipal
import com.bity.icp_kotlin_kit.domain.model.ICPSigningPrincipal
import com.bity.icp_kotlin_kit.domain.model.enum.ICPRequestCertification
import com.bity.icp_kotlin_kit.domain.model.request.PollingValues

/**
 * File generated using ICP Kotlin Kit Plugin
 */
object NNS_SNS_W {

    class AddWasmRequest(
        val hash: ByteArray,
        val wasm: SnsWasm?
    )

    class AddWasmResponse(
        val result: Result?
    )

    class AirdropDistribution(
        val airdrop_neurons: kotlin.Array<NeuronDistribution>
    )

    class Canister(
        val id: ICPPrincipalApiModel?
    )

    class Countries(
        val iso_codes: kotlin.Array<String>
    )

    class DappCanisters(
        val canisters: kotlin.Array<Canister>
    )

    class DappCanistersTransferResult(
        val restored_dapp_canisters: kotlin.Array<Canister>,
        val nns_controlled_dapp_canisters: kotlin.Array<Canister>,
        val sns_controlled_dapp_canisters: kotlin.Array<Canister>
    )

    class DeployNewSnsRequest(
        val sns_init_payload: SnsInitPayload?
    )

    class DeployNewSnsResponse(
        val dapp_canisters_transfer_result: DappCanistersTransferResult?,
        val subnet_id: ICPPrincipalApiModel?,
        val error: SnsWasmError?,
        val canisters: SnsCanisterIds?
    )

    class DeployedSns(
        val root_canister_id: ICPPrincipalApiModel?,
        val governance_canister_id: ICPPrincipalApiModel?,
        val index_canister_id: ICPPrincipalApiModel?,
        val swap_canister_id: ICPPrincipalApiModel?,
        val ledger_canister_id: ICPPrincipalApiModel?
    )

    class DeveloperDistribution(
        val developer_neurons: kotlin.Array<NeuronDistribution>
    )

    class FractionalDeveloperVotingPower(
        val treasury_distribution: TreasuryDistribution?,
        val developer_distribution: DeveloperDistribution?,
        val airdrop_distribution: AirdropDistribution?,
        val swap_distribution: SwapDistribution?
    )

    class GetAllowedPrincipalsResponse(
        val allowed_principals: kotlin.Array<ICPPrincipalApiModel>
    )

    class GetDeployedSnsByProposalIdRequest(
        val proposal_id: ULong
    )

    class GetDeployedSnsByProposalIdResponse(
        val get_deployed_sns_by_proposal_id_result: GetDeployedSnsByProposalIdResult?
    )

    sealed class GetDeployedSnsByProposalIdResult {
        class Error(
            val snsWasmError: SnsWasmError
        ): GetDeployedSnsByProposalIdResult()
        class DeployedSns(
            val deployedSns: DeployedSns
        ): GetDeployedSnsByProposalIdResult()
    }

    class GetNextSnsVersionRequest(
        val governance_canister_id: ICPPrincipalApiModel?,
        val current_version: SnsVersion?
    )

    class GetNextSnsVersionResponse(
        val next_version: SnsVersion?
    )

    class GetProposalIdThatAddedWasmRequest(
        val hash: ByteArray
    )

    class GetProposalIdThatAddedWasmResponse(
        val proposal_id: ULong?
    )

    class GetSnsSubnetIdsResponse(
        val sns_subnet_ids: kotlin.Array<ICPPrincipalApiModel>
    )

    class GetWasmMetadataRequest(
        val hash: ByteArray?
    )

    class GetWasmMetadataResponse(
        val result: Result_1?
    )

    class GetWasmRequest(
        val hash: ByteArray
    )

    class GetWasmResponse(
        val wasm: SnsWasm?
    )

    class IdealMatchedParticipationFunction(
        val serialized_representation: String?
    )

    sealed class InitialTokenDistribution {
        class FractionalDeveloperVotingPower(
            val fractionalDeveloperVotingPower: FractionalDeveloperVotingPower
        ): InitialTokenDistribution()
    }

    class InsertUpgradePathEntriesRequest(
        val upgrade_path: kotlin.Array<SnsUpgrade>,
        val sns_governance_canister_id: ICPPrincipalApiModel?
    )

    class InsertUpgradePathEntriesResponse(
        val error: SnsWasmError?
    )

    class LinearScalingCoefficient(
        val slope_numerator: ULong?,
        val intercept_icp_e8s: ULong?,
        val from_direct_participation_icp_e8s: ULong?,
        val slope_denominator: ULong?,
        val to_direct_participation_icp_e8s: ULong?
    )

    class ListDeployedSnsesResponse(
        val instances: kotlin.Array<DeployedSns>
    )

    class ListUpgradeStep(
        val pretty_version: PrettySnsVersion?,
        val version: SnsVersion?
    )

    class ListUpgradeStepsRequest(
        val limit: UInt,
        val starting_at: SnsVersion?,
        val sns_governance_canister_id: ICPPrincipalApiModel?
    )

    class ListUpgradeStepsResponse(
        val steps: kotlin.Array<ListUpgradeStep>
    )

    class MetadataSection(
        val contents: ByteArray?,
        val name: String?,
        val visibility: String?
    )

    class NeuronBasketConstructionParameters(
        val dissolve_delay_interval_seconds: ULong,
        val count: ULong
    )

    class NeuronDistribution(
        val controller: ICPPrincipalApiModel?,
        val dissolve_delay_seconds: ULong,
        val memo: ULong,
        val stake_e8s: ULong,
        val vesting_period_seconds: ULong?
    )

    class NeuronsFundParticipationConstraints(
        val coefficient_intervals: kotlin.Array<LinearScalingCoefficient>,
        val max_neurons_fund_participation_icp_e8s: ULong?,
        val min_direct_participation_threshold_icp_e8s: ULong?,
        val ideal_matched_participation_function: IdealMatchedParticipationFunction?
    )

    class Ok(
        val sections: kotlin.Array<MetadataSection>
    )

    class PrettySnsVersion(
        val archive_wasm_hash: String,
        val root_wasm_hash: String,
        val swap_wasm_hash: String,
        val ledger_wasm_hash: String,
        val governance_wasm_hash: String,
        val index_wasm_hash: String
    )

    sealed class Result {
        class Error(
            val snsWasmError: SnsWasmError
        ): Result()
        class Hash(
            val byteArray: ByteArray
        ): Result()
    }

    sealed class Result_1 {
        class Ok(
            val ok: NNS_SNS_W.Ok
        ): Result_1()
        class Error(
            val snsWasmError: SnsWasmError
        ): Result_1()
    }

    class SnsCanisterIds(
        val root: ICPPrincipalApiModel?,
        val swap: ICPPrincipalApiModel?,
        val ledger: ICPPrincipalApiModel?,
        val index: ICPPrincipalApiModel?,
        val governance: ICPPrincipalApiModel?
    )

    class SnsInitPayload(
        val url: String?,
        val max_dissolve_delay_seconds: ULong?,
        val max_dissolve_delay_bonus_percentage: ULong?,
        val nns_proposal_id: ULong?,
        val neurons_fund_participation: Boolean?,
        val min_participant_icp_e8s: ULong?,
        val neuron_basket_construction_parameters: NeuronBasketConstructionParameters?,
        val fallback_controller_principal_ids: kotlin.Array<String>,
        val token_symbol: String?,
        val final_reward_rate_basis_points: ULong?,
        val max_icp_e8s: ULong?,
        val neuron_minimum_stake_e8s: ULong?,
        val confirmation_text: String?,
        val logo: String?,
        val name: String?,
        val swap_start_timestamp_seconds: ULong?,
        val swap_due_timestamp_seconds: ULong?,
        val initial_voting_period_seconds: ULong?,
        val neuron_minimum_dissolve_delay_to_vote_seconds: ULong?,
        val description: String?,
        val max_neuron_age_seconds_for_age_bonus: ULong?,
        val min_participants: ULong?,
        val initial_reward_rate_basis_points: ULong?,
        val wait_for_quiet_deadline_increase_seconds: ULong?,
        val transaction_fee_e8s: ULong?,
        val dapp_canisters: DappCanisters?,
        val neurons_fund_participation_constraints: NeuronsFundParticipationConstraints?,
        val max_age_bonus_percentage: ULong?,
        val initial_token_distribution: InitialTokenDistribution?,
        val reward_rate_transition_duration_seconds: ULong?,
        val token_logo: String?,
        val token_name: String?,
        val max_participant_icp_e8s: ULong?,
        val min_direct_participation_icp_e8s: ULong?,
        val proposal_reject_cost_e8s: ULong?,
        val restricted_countries: Countries?,
        val min_icp_e8s: ULong?,
        val max_direct_participation_icp_e8s: ULong?
    )

    class SnsUpgrade(
        val next_version: SnsVersion?,
        val current_version: SnsVersion?
    )

    class SnsVersion(
        val archive_wasm_hash: ByteArray,
        val root_wasm_hash: ByteArray,
        val swap_wasm_hash: ByteArray,
        val ledger_wasm_hash: ByteArray,
        val governance_wasm_hash: ByteArray,
        val index_wasm_hash: ByteArray
    )

    class SnsWasm(
        val wasm: ByteArray,
        val proposal_id: ULong?,
        val canister_type: Int
    )

    class SnsWasmCanisterInitPayload(
        val allowed_principals: kotlin.Array<ICPPrincipalApiModel>,
        val access_controls_enabled: Boolean,
        val sns_subnet_ids: kotlin.Array<ICPPrincipalApiModel>
    )

    class SnsWasmError(
        val message: String
    )

    class SwapDistribution(
        val total_e8s: ULong,
        val initial_swap_amount_e8s: ULong
    )

    class TreasuryDistribution(
        val total_e8s: ULong
    )

    class UpdateAllowedPrincipalsRequest(
        val added_principals: kotlin.Array<ICPPrincipalApiModel>,
        val removed_principals: kotlin.Array<ICPPrincipalApiModel>
    )

    class UpdateAllowedPrincipalsResponse(
        val update_allowed_principals_result: UpdateAllowedPrincipalsResult?
    )

    sealed class UpdateAllowedPrincipalsResult {
        class Error(
            val snsWasmError: SnsWasmError
        ): UpdateAllowedPrincipalsResult()
        class AllowedPrincipals(
            val getAllowedPrincipalsResponse: GetAllowedPrincipalsResponse
        ): UpdateAllowedPrincipalsResult()
    }

    class UpdateSnsSubnetListRequest(
        val sns_subnet_ids_to_add: kotlin.Array<ICPPrincipalApiModel>,
        val sns_subnet_ids_to_remove: kotlin.Array<ICPPrincipalApiModel>
    )

    class UpdateSnsSubnetListResponse(
        val error: SnsWasmError?
    )

    class nns_sns_wService(
        private val canister: ICPPrincipal
    ) {
        suspend fun add_wasm (
            addWasmRequest: AddWasmRequest,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): AddWasmResponse {
            val icpQuery = ICPQuery(
                methodName = "add_wasm",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(
                        arg = addWasmRequest,
                        expectedClass = AddWasmRequest::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun deploy_new_sns (
            deployNewSnsRequest: DeployNewSnsRequest,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): DeployNewSnsResponse {
            val icpQuery = ICPQuery(
                methodName = "deploy_new_sns",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(
                        arg = deployNewSnsRequest,
                        expectedClass = DeployNewSnsRequest::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun get_allowed_principals (
            unnamedClass0: UnnamedClass0,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): GetAllowedPrincipalsResponse {
            val icpQuery = ICPQuery(
                methodName = "get_allowed_principals",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(
                        arg = unnamedClass0,
                        expectedClass = UnnamedClass0::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        class UnnamedClass0()

        suspend fun get_deployed_sns_by_proposal_id (
            getDeployedSnsByProposalIdRequest: GetDeployedSnsByProposalIdRequest,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): GetDeployedSnsByProposalIdResponse {
            val icpQuery = ICPQuery(
                methodName = "get_deployed_sns_by_proposal_id",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(
                        arg = getDeployedSnsByProposalIdRequest,
                        expectedClass = GetDeployedSnsByProposalIdRequest::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun get_latest_sns_version_pretty (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): kotlin.Array<UnnamedClass1> {
            val icpQuery = ICPQuery(
                methodName = "get_latest_sns_version_pretty",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = null,
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        class UnnamedClass1(
            val string_1: String,
            val string_2: String
        )

        suspend fun get_next_sns_version (
            getNextSnsVersionRequest: GetNextSnsVersionRequest,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): GetNextSnsVersionResponse {
            val icpQuery = ICPQuery(
                methodName = "get_next_sns_version",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(
                        arg = getNextSnsVersionRequest,
                        expectedClass = GetNextSnsVersionRequest::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun get_proposal_id_that_added_wasm (
            getProposalIdThatAddedWasmRequest: GetProposalIdThatAddedWasmRequest,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): GetProposalIdThatAddedWasmResponse {
            val icpQuery = ICPQuery(
                methodName = "get_proposal_id_that_added_wasm",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(
                        arg = getProposalIdThatAddedWasmRequest,
                        expectedClass = GetProposalIdThatAddedWasmRequest::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun get_sns_subnet_ids (
            unnamedClass2: UnnamedClass2,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): GetSnsSubnetIdsResponse {
            val icpQuery = ICPQuery(
                methodName = "get_sns_subnet_ids",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(
                        arg = unnamedClass2,
                        expectedClass = UnnamedClass2::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        class UnnamedClass2()

        suspend fun get_wasm (
            getWasmRequest: GetWasmRequest,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): GetWasmResponse {
            val icpQuery = ICPQuery(
                methodName = "get_wasm",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(
                        arg = getWasmRequest,
                        expectedClass = GetWasmRequest::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun get_wasm_metadata (
            getWasmMetadataRequest: GetWasmMetadataRequest,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): GetWasmMetadataResponse {
            val icpQuery = ICPQuery(
                methodName = "get_wasm_metadata",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(
                        arg = getWasmMetadataRequest,
                        expectedClass = GetWasmMetadataRequest::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun insert_upgrade_path_entries (
            insertUpgradePathEntriesRequest: InsertUpgradePathEntriesRequest,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): InsertUpgradePathEntriesResponse {
            val icpQuery = ICPQuery(
                methodName = "insert_upgrade_path_entries",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(
                        arg = insertUpgradePathEntriesRequest,
                        expectedClass = InsertUpgradePathEntriesRequest::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun list_deployed_snses (
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): ListDeployedSnsesResponse {
            val icpQuery = ICPQuery(
                methodName = "list_deployed_snses",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(
                        arg = EmptyClass(),
                        expectedClass = EmptyClass::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        class EmptyClass

        suspend fun list_upgrade_steps (
            listUpgradeStepsRequest: ListUpgradeStepsRequest,
            certification: ICPRequestCertification = ICPRequestCertification.Uncertified,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): ListUpgradeStepsResponse {
            val icpQuery = ICPQuery(
                methodName = "list_upgrade_steps",
                canister = canister
            )
            val result = icpQuery.invoke(
                values = listOf(
                    ValueToEncode(
                        arg = listUpgradeStepsRequest,
                        expectedClass = ListUpgradeStepsRequest::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
                certification = certification
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun update_allowed_principals (
            updateAllowedPrincipalsRequest: UpdateAllowedPrincipalsRequest,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): UpdateAllowedPrincipalsResponse {
            val icpQuery = ICPQuery(
                methodName = "update_allowed_principals",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(
                        arg = updateAllowedPrincipalsRequest,
                        expectedClass = UpdateAllowedPrincipalsRequest::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }

        suspend fun update_sns_subnet_list (
            updateSnsSubnetListRequest: UpdateSnsSubnetListRequest,
            sender: ICPSigningPrincipal? = null,
            pollingValues: PollingValues = PollingValues()
        ): UpdateSnsSubnetListResponse {
            val icpQuery = ICPQuery(
                methodName = "update_sns_subnet_list",
                canister = canister
            )
            val result = icpQuery.callAndPoll(
                values = listOf(
                    ValueToEncode(
                        arg = updateSnsSubnetListRequest,
                        expectedClass = UpdateSnsSubnetListRequest::class,
                        expectedClassNullable = false
                    )
                ),
                sender = sender,
                pollingValues = pollingValues,
            ).getOrThrow()
            return CandidDecoder.decodeNotNull(result.first())
        }
    }
}