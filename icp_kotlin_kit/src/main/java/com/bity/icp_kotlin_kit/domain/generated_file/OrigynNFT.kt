package com.bity.icp_kotlin_kit.domain.generated_file

import com.bity.icp_kotlin_kit.data.datasource.api.model.ICPPrincipalApiModel
import java.math.BigInteger

/**
 * type AskConfigShared = opt AskFeatureArray;
 */
// typealias AskConfigShared = AskFeatureArray?

/**
 * type AskFeatureArray = vec AskFeature;
 */
// typealias AskFeatureArray = kotlin.Array<AskFeature>

/**
 * type AskSubscribeResponse = bool;
 */
typealias AskSubscribeResponse = Boolean

/**
 * type BidConfigShared = opt vec BidFeature;
 */
// typealias BidConfigShared = kotlin.Array<BidFeature>?

/**
 * type Caller = opt principal;
 */
typealias Caller = ICPPrincipalApiModel?

/**
 * type DistributeSaleResponse = vec Result;
 */
typealias DistributeSaleResponse = kotlin.Array<Result>

/**
 * type EXTMemo = vec nat8;
 */
typealias EXTMemo = kotlin.Array<UByte>

/**
 * type EXTSubAccount = vec nat8;
 */
// typealias EXTSubAccount = kotlin.Array<UByte>

/**
 * type FeeAccountsParams = vec FeeName;
 */
// typealias FeeAccountsParams = kotlin.Array<FeeName>

/**
 * type GetArchivesResult = vec GetArchivesResultItem;
 */
// typealias GetArchivesResult = kotlin.Array<GetArchivesResultItem>

/**
 * type InstantConfigShared = opt vec InstantFeature;
 */
// typealias InstantConfigShared = kotlin.Array<InstantFeature>?

/**
 * type NFTUpdateResponse = bool;
 */
typealias NFTUpdateResponse = Boolean

/**
 * type Subaccount = vec nat8;
 */
// typealias Subaccount = kotlin.Array<UByte>

/**
 * type TransferResult = vec opt TransferResultItem;
 */
// typealias TransferResult = kotlin.Array<TransferResultItem?>

/**
 * type canister_id = principal;
 */
typealias canister_id = ICPPrincipalApiModel