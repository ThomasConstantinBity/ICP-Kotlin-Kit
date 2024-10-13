![IcpKit](readme_utils/IcpKit.png)

A comprehensive kotlin package for writing applications that interact with the Internet Computer Protocol (ICP).
Icp Kotlin Kit aims at facilitating the interaction with the ICP blockchain.

For more information about ICP Development, we recommend starting from https://internetcomputer.org/docs/current/references/

## Contributors
This package has been developed by [Thomas Constantin](https://github.com/0xTommy). All code and functionality are the result of my work.

## Acknowledgments
I would like to express my gratitude to [Konstantinos Gaitanis](https://github.com/kgaitanis) for his support during the initial project study and other contributions that helped shape this library.

This Package has been built by [Bity SA](https://bity.com) with the help of the [DFinity Foundation Developer Grant Program](https://dfinity.org/grants).

## License
**MIT License** is applicable for all Kotlin Code (see [LICENSE](LICENSE)).

BLS12381 Rust Library is licensed by Levi Feldman (see [LICENSE](readme_utils/bls12381_LICENSE)).

## Library Overview
IcpKit will take care of all the encoding, serialisation and cryptography required to communicate with ICP allowing
developers to focus on the real functionality of their app and bootstrapping their development cycle.
### Main functionalities
- Cryptographic methods applicable to ICP such as signing and signature verification.
- CBOR serialisation
- Basic ICP Models for transactions, accounts, self-authenticating principals etc.
- `CandidEncoder` and `CandidDecoder` for converting any kotlin class/values to a CandidValue
- This library provides a set of pre-generated files: 
  - `DIP20.kt` 
  - `ICRC1.kt`
  - `ICRC1IndexCanister.kt`
  - `LedgerCanister.kt`
  - `NNS_SNS_W.kt`
  - `NNSICPIndexCanister.kt`
  - `Tokens.kt`

This files can be directly used to interact with canisters on the Internet Computer. These files include the most 
commonly needed functionality for operations such as fetching balances, sending ICP or ICP tokens, 
and retrieving NFT information.

If you need to interact with other canisters using custom candid files, you can easily add a Gradle task to 
generate the necessary bindings from your own candid files. For more details, see the [Installation](#Installation) 
section.

## Installation

### Use pre generated files
#### Add the JitPack repository to your build file

Add in your `settings.gradle.kts` file:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven{ url = uri("https://jitpack.io") }
    }
}
```

#### Add the dependency
Add in your application `build.gradle.kts` file:
```kotlin
dependencies {
    implementation("com.github.ThomasConstantinBity:ICP-Kotlin-Kit:Tag")
}
```

#### Add BLS12381 rust library

### Generate custom files with gradle task
Add in your app `build.gradle.kts` file:
```kotlin
buildscript {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("com.github.ThomasConstantinBity:ICP-Kotlin-Kit:Tag")
    }
}
```

## Usage
To enable signature verification, this library requires the BLS12381 Rust library. You’ll need to compile the Rust project located in the `rust/bls12381` folder. 

### Steps:

1. Navigate to the `rust/bls12381` directory.
2. Compile the Rust project according to your target platform.

> **Note:** You must provide separate libraries for each platform or device you want to support (e.g., Android, iOS).

### Android Support

For Android projects, you can use [cargo-ndk-android-gradle](https://github.com/willir/cargo-ndk-android-gradle) to simplify the process of building Rust libraries for Android. This tool helps compile Rust libraries into native code that can be used within Android applications.

Make sure to configure the build process for each platform and link the resulting native libraries accordingly.

### Use pre generated files
For this example, we’ll use the `ICRC1.kt` file to demonstrate how to interact with the service.
#### Service Initialization
First, initialize the service with your canister principal:

```kotlin
val canister: ICPPrincipal = // Define your principal
val service = ICRC1.ICRC1Service(canister)
```
You can initialize `ICPPrincipal` by using the principal string 
(e.g., `ryjl3-tyaaa-aaaaa-aaaba-cai` for the ICP Ledger canister). 
Alternatively, check the `ICPSystemCanisters` enum class for predefined canisters.

Once the service is initialized, you can call **any** of the available functions.

#### Performing a Simple Query
To perform a query, you can use `service.query_name`. The service will call the corresponding method on the canister, retrieve the response, and parse it into a Kotlin value or class according to the function’s definition.
For query methods, there are some optional parameters you can pass:
- **Certification**: Use `ICPRequestCertification.Certified` if you want to verify the response’s signature. 
Be aware that this process involves JNI and will be slower due to the additional verification step. Default value: ICPRequestCertification.Unertified.
- **Sender**: An interface to implement if the query requires a signature.
- Polling values: This is relevant for certified requests, as the service needs to poll the canister while awaiting a response.
Default values: 
  - Polling interval: 2 seconds
  - Polling timeout: 2 minutes

#### Calling a Function That Requires a Signature
Here’s an example of how to call a function that requires a signature:

Implement `ICPSigningPrincipal`
```kotlin
val sender = object : ICPSigningPrincipal {
    override val principal: ICPPrincipal = this@TmpTest.principal
    override val rawPublicKey: ByteArray = publicKey
    override suspend fun sign(message: ByteArray): ByteArray {
        val hashedMessage = SHA256.sha256(message)
        val signature = EllipticSign(
            messageToSign = hashedMessage,
            privateKey = BigInteger(privateKey)
        )
        signature[64] = (signature[64] + 0x1b).toByte()
        return signature.dropLast(1).toByteArray()
    }
}
```
Now, you can make a transfer using the service:
```kotlin
val transferArgs = ICRC1.TransferArgs(
    ...
)
val transferResult = service.icrc1_transfer(
    transferArgs = transferArgs,
    sender = sender
)

val blockIndex = when (transferResult) {
    is ICRC1.TransferResult.Err -> throw transferResult.transferError.toDataModel()
    is ICRC1.TransferResult.Ok -> transferResult.bigInteger
}
```

### Generate custom files with gradle task
You can use `KotlinFileGenerator` class inside gradle tasks. Here is a **demo** example
```kotlin
tasks.register("parseCandidFiles") {
    val inputFolder = file("./candid_files")
    require(inputFolder.isDirectory)
    inputFolder.listFiles { it -> it.extension == "did" }?.forEach { file ->
        val fileName = file.name.removeSuffix(".did")
        val kotlinFileGenerator = KotlinFileGenerator(
            fileName = fileName,
            packageName = "com.bity.demo_app.generated_files",
            didFileContent = file.readText(Charsets.UTF_8)
        )
        val outputFile = file("./src/main/java/com/bity/demo_app/generated_files/${fileName}.kt")
        outputFile.writeText(kotlinFileGenerator.generateKotlinFile())
    }
}
```