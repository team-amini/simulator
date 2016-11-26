package amini.contract;

import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.Future;
import org.web3j.abi.Contract;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modifiy!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.</p>
 */
public final class Regulated extends Contract {
    private static final String BINARY = "606060405260088060106000396000f36060604052600256";

    private Regulated(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public EventValues processRegulatedTransactionEvent(TransactionReceipt transactionReceipt) {
        Event event = new Event("RegulatedTransaction", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
        return extractEventParameters(event, transactionReceipt);
    }

    public static Future<Regulated> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue) {
        return deployAsync(Regulated.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialValue);
    }

    public static Regulated load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Regulated(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }
}
